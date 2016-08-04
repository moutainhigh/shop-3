package com.tuisitang.modules.shop.api.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gson.WeChat;
import com.gson.oauth.WxPay;
import com.tuisitang.common.bo.ActivityStatus;
import com.tuisitang.common.bo.CartAction;
import com.tuisitang.common.cache.memcached.SpyMemcachedClient;
import com.tuisitang.common.utils.DateUtils;
import com.tuisitang.common.utils.Encodes;
import com.tuisitang.common.utils.HttpServletRequestUtils;
import com.tuisitang.modules.shop.api.bo.Authorization;
import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.entity.AccountRank;
import com.tuisitang.modules.shop.entity.Activity;
import com.tuisitang.modules.shop.entity.Address;
import com.tuisitang.modules.shop.entity.Cart;
import com.tuisitang.modules.shop.entity.DeliveryDay;
import com.tuisitang.modules.shop.entity.Device;
import com.tuisitang.modules.shop.entity.Express;
import com.tuisitang.modules.shop.entity.Order;
import com.tuisitang.modules.shop.entity.Orderdetail;
import com.tuisitang.modules.shop.entity.Orderlog;
import com.tuisitang.modules.shop.entity.Orderpay;
import com.tuisitang.modules.shop.entity.Pay;
import com.tuisitang.modules.shop.entity.PayLog;
import com.tuisitang.modules.shop.entity.Product;
import com.tuisitang.modules.shop.entity.Spec;
import com.tuisitang.modules.shop.service.AccountService;
import com.tuisitang.modules.shop.service.CommonService;
import com.tuisitang.modules.shop.service.OrderService;
import com.tuisitang.modules.shop.service.ProductService;
import com.tuisitang.modules.shop.service.SystemService;
import com.tuisitang.modules.shop.utils.Global;

/**    
 * @{#} OrderApiController.java   
 * 
 * 订单 Api Controller
 * 
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: irtone</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 *  
 */
@Controller
@RequestMapping(value = "/api/order")
public class OrderApiController extends BaseApiController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductService productService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private SpyMemcachedClient memcachedClient;
	
	/**
	 * 1. 购物车提交 - 订单确认
	 */
	@RequestMapping(value = { "confirm" })
	public @ResponseBody Map<String, Object> confirm(@RequestParam(value = "ids", required = true) String ids, 
			@RequestHeader(value = "Authorization", required = true) String authorization, 
			HttpServletRequest request, HttpServletResponse response) {
		try {
			Date sysTime = new Date(System.currentTimeMillis());
			logger.info("订单确认");
			Authorization auth = getAuthorization(authorization);
			if ("1".equals(auth.getErrorCode())) {
				return buildReturnMap(auth.getErrorCode(), auth.getErrorMsg());
			}
			Account account = systemService.getAccountByMobile(auth.getMobile());
	//		List<DeliveryDay> ddList = commonService.findAllDeliveryDay();// 获得所有的日期
	//		List<Express> expressList = commonService.getAllExpress();// 获得所有的快递方式
			List<Pay> payList = commonService.getAllPay();// 获得所有的支付方式
			Map<String, List<Pay>> payMap = Maps.newHashMap();
			for (Pay pay : payList) {
				String type = pay.getType();
				List<Pay> l = payMap.get(type);
				if (l == null || l.isEmpty()) {
					l = Lists.newArrayList();
					payMap.put(type, l);
				}
				l.add(pay);
			}
			
			Long[] idss = (Long[]) ConvertUtils.convert(ids.split(","), Long.class);
			List<Cart> carts = orderService.findCart(auth.getToken(), account.getId(), idss);
			double totalPrice = 0.00;//商品总金额
			double totalFreight = 0.00;//总运费
			double totalDiscount = 0.00;//会员等级折扣
			double totalPay = 0.00;//总支付金额
			
			int totalIntegral = 0;
			int total = 0;
			String s = "";
			for (Cart cart : carts) {
				logger.info("Cart {}", cart);
				Product product = productService.getProductById(cart.getProductId());
				if (cart.getSpecId() != null && cart.getSpecId() != 0L) {
					Spec activeSpec = productService.getProductSpec(product.getId(), cart.getSpecId());
					product.setActiveSpec(activeSpec);
					productService.getProductSpecInfo(product);
				}
				Activity activity = product.getActivity();
				if (activity == null) {// 该商品没有参加任何活动
					logger.info("该商品【{}({})】没有参加活动", product.getName(), product.getId());
					if (StringUtils.isNotBlank(account.getRank())) {
						AccountRank ar = commonService.getAccountRankByCode(account.getRank());
						if (ar != null) {
							cart.setDiscount(ar.getDiscount() * 100);
						} else {
							cart.setDiscount(100);
						}
						cart.setDiscountPrice((100 - cart.getDiscount()) * cart.getNowPrice() / 100);
					} else {// 未登录情况
						cart.setDiscount(100);
						cart.setDiscountPrice(0);
					}
					
					cart.setFee(orderService.calculateFreight(cart.getNowPrice() * cart.getQuantity()));
					cart.setActivityStatus(ActivityStatus.NONE.getStatus());
				} else {
					double finalPrice = product.getNowPrice();
					String activityType = activity.getActivityType();
					String discountType = activity.getDiscountType();
					String accountRange = activity.getAccountRange();
					String []ss1 = accountRange.split(",");
					List<String> ranks = Lists.newArrayList();
					for (String s1 : ss1) {
						if (!StringUtils.isBlank(s1.trim()))
							ranks.add(s1.trim());
					}
					logger.info("该商品【{}({})】参加活动，活动类型【{}】，折扣类型【{}】", product.getName(), product.getId(), activityType, discountType);
					logger.info("活动开始时间 {}, 活动结束时间 {}, 当前时间 {}, ",
							DateUtils.formatDate(activity.getStartDate(), "yyyy-MM-dd HH:mm:ss"), 
							DateUtils.formatDate(activity.getEndDate(), "yyyy-MM-dd HH:mm:ss"),
							DateUtils.formatDate(sysTime, "yyyy-MM-dd HH:mm:ss"));
					logger.info("参加活动的会员等级 {}, 当前会员等级 {}", accountRange, account.getRank());
					// 活动类型 c:促销活动；j:积分兑换；t:团购活动
					if (activity.getStartDate().getTime() > sysTime.getTime()) {
						logger.info("活动还未开始");
						cart.setActivityStatus(ActivityStatus.NOSTART.getStatus());
					} else if (activity.getEndDate().getTime() < sysTime.getTime()) {
						logger.info("活动已经结束");
						cart.setActivityStatus(ActivityStatus.ISOVER.getStatus());
					} else if (ranks.size() != 4 && !StringUtils.isBlank(accountRange) && !ranks.contains(account.getRank())) {
						logger.info("没有权限");
						cart.setActivityStatus(ActivityStatus.NOAUTH.getStatus());
					} else {
						if (activityType.equals(Activity.activity_activityType_c)) {// 1.促销活动
							if (discountType.equals(Activity.activity_discountType_r)) {// 减免
								finalPrice = product.getNowPrice() - activity.getDiscount();
								if (activity.getMinprice() !=null && (finalPrice < activity.getMinprice().doubleValue())) {
									finalPrice = activity.getMinprice();
								}
							} else if (discountType.equals(Activity.activity_discountType_d)) {// 折扣
								finalPrice = product.getNowPrice() * activity.getDiscount() / 100d;
								if (activity.getMinprice() !=null && (finalPrice < activity.getMinprice().doubleValue())) {
									finalPrice = activity.getMinprice();
								}
							} else if (discountType.equals(Activity.activity_discountType_s)) {// 双倍积分 双倍积分的商品价格上不做优惠
								finalPrice = product.getNowPrice();
								cart.setPresentIntegral(product.getScore() * 2);// 获得的积分*2
							}
						} else if (activityType.equals(Activity.activity_activityType_j)) {// 2.积分活动
							finalPrice = 0.00;// 最终价格等于0
							cart.setRequiredIntegral(activity.getExchangeScore());
						} else if (activityType.equals(Activity.activity_activityType_t)) {// 3.团购活动
							finalPrice = activity.getTuanPrice();// 最终价格等于团购价
						}
						cart.setActivityId(activity.getId());
						cart.setActivityName(activity.getName());
						cart.setDiscountType(Activity.activity_discountType_r);
						cart.setDiscount(activity.getDiscount());
						cart.setActivityType(activityType);
					}
					cart.setNowPrice(finalPrice);
				}
				orderService.updateCart(cart);
				totalPrice += cart.getNowPrice() * cart.getQuantity();
				totalIntegral += cart.getRequiredIntegral() * cart.getQuantity();
				total += cart.getQuantity();
				s += cart.getId() + ",";
				
				//计算运费
				totalFreight += cart.getFee();
				totalDiscount += cart.getDiscountPrice() * cart.getQuantity();
			}
			totalPay = (totalPrice - totalDiscount + totalFreight);
			
			List<Address> addresses = accountService.findAddressByAccountId(account.getId());
			Address defaultAddress = null;
			for (Address address : addresses) {
				if ("y".equals(address.getIsDefault())) {
					defaultAddress = address;
				}
			}
			Map<String, Object> paramMap = Maps.newHashMap();
			java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");  
			paramMap.put("fee", df.format(totalFreight));
			paramMap.put("addressId", defaultAddress == null ? "" : defaultAddress.getId());
			paramMap.put("totalPrice", df.format(totalPrice));
			paramMap.put("discountPrice", df.format(totalDiscount));
			paramMap.put("finalPrice", totalPay);
		
			return buildReturnMap(SUCCESS, "", paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{}", e.getMessage());
			return buildReturnMap(FAILURE, "系统错误");
		}
	}
	
	/**
	 * 2. 准备支付
	 * 生成订单，准备支付
	 */
	@RequestMapping(value = { "prepay" })
	public @ResponseBody Map<String, Object> prepay(@RequestParam("ids") String ids, 
			@RequestParam("addressId") Long addressId, 
			@RequestParam(value = "expressCode", required = false, defaultValue = "LOGISTICS") String expressCode, 
			@RequestParam(value = "payCode", required = true) String payCode,
			@RequestParam(value = "deliveryDayCode", required = false, defaultValue = "all") String deliveryDayCode,
			@RequestParam(required = false, value = "remark") String remark,
			@RequestHeader(value = "Authorization", required = true) String authorization,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			Date systemTime = new Date(System.currentTimeMillis());
			logger.info("支付,生成订单");
			logger.info("authorization {}, \naddressId {}, \nexpressCode {}, \npayCode {}, \ndeliveryDayCode {}, \nremark {}", 
					authorization, addressId, expressCode, payCode, deliveryDayCode, remark);
			Authorization auth = getAuthorization(authorization);
			if ("1".equals(auth.getErrorCode())) {
				return buildReturnMap(auth.getErrorCode(), auth.getErrorMsg());
			}
			Account account = systemService.getAccountByMobile(auth.getMobile());
//			String password = Encodes.rc4(new String(Encodes.decodeBase64(ss[2]), "UTF-8"), device.getDeviceSecret());
			
			Express express = commonService.getExpress(expressCode);
			Pay pay = commonService.getPay(payCode);
			DeliveryDay deliveryDay = commonService.getDeliveryDay(deliveryDayCode);
			Address address = accountService.getAddress(addressId);
			Order order = orderService.createOrder(account, auth.getToken(), ids, address, express, pay, deliveryDay, remark);
			
			Map<String, Object> paramMap = Maps.newHashMap();
	//		//跟新产品sellCount
	//		List<Orderdetail> ods = order.getOdList();
	//		for (Orderdetail orderdetail : ods) {
	//			productService.incrSellCount(orderdetail.getProductID());
	//		}
	//		//设置当前地址为默认地址
	//		accountService.setDefaultAddr(account.getId(), address);
			if (Pay.pay_code_alipayescow.equals(payCode)) {
				String subject = "报喜了 - 订单号 "+ order.getNo() ;
				String notify_url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/order/pay/notify";

				paramMap.put("partner", Global.getAlipayPartner());// 支付宝PID
				paramMap.put("seller_id", Global.SELLER_EMAIL);// 卖家支付宝账号
				paramMap.put("out_trade_no", order.getNo());// 商品订单编号
				paramMap.put("subject", subject);// 商品名称 订单名称
				paramMap.put("body", subject);
				paramMap.put("total_fee", (order.getAmount() + order.getFee()) + "");// 价格 = 商品价格 + 运费
				paramMap.put("notify_url", notify_url);// 服务器异步通知页面路径
				paramMap.put("service", "mobile.securitypay.pay");// 接口服务
				paramMap.put("payment_type", "1");// 支付类型
				paramMap.put("_input_charset", AlipayConfig.input_charset);// 统一编码
				paramMap.put("it_b_pay", "30m");
				paramMap.put("sygn_type", "RSA");
				paramMap.put("sign", "");
				paramMap.put("alipay_key", Global.getAlipayKey());
				paramMap.put("alipay_private_key", Global.getAlipayPrivateKey());
			} else if (Pay.pay_code_wx.equals(payCode)) {
				List<Orderdetail> ods = order.getOdList();
				StringBuffer sb = new StringBuffer();
				for (Orderdetail orderdetail : ods) {
					sb.append(orderdetail.getProductName() + ",");
				}
				String productName = sb.substring(0, sb.length() - 1).toString();
				String total_fee   = (new   Double((order.getAmount() + order.getFee()) * 100)).intValue() + "";
				String orderid     = order.getNo();
				
				// package 参数------------------------------  //
				// 对商品名截取, 去除空格 
				productName = productName.replaceAll(" ", "");
				productName = productName.length() > 17 ? productName.substring(0, 17) + "..." : productName;
				String nonceStr = RandomStringUtils.random(10, "123456789"); // 8位随机数
				String appid = Global.getConfig("AppId");
				String mch_id = Global.getConfig("partnerId");
				String notify_url = Global.getConfig("notify_url");
				String trade_type = "APP";
				String attach = "";
				String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);

				String spbill_create_ip = HttpServletRequestUtils.getIp(request);
				SortedMap<String, String> packageParams = new TreeMap<String, String>();
				
				packageParams.put("appid", appid);
				packageParams.put("mch_id", mch_id);
				packageParams.put("nonce_str", nonceStr);
				packageParams.put("body", productName);
				packageParams.put("out_trade_no", orderid);

				// 这里写的金额为1 分到时修改
				packageParams.put("total_fee", total_fee);
				packageParams.put("spbill_create_ip", spbill_create_ip);
				packageParams.put("notify_url", notify_url);
				packageParams.put("trade_type", trade_type);
				String sign = WxPay.getSign(packageParams, Global.getConfig("partnerKey"));
				
				String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
						+ mch_id + "</mch_id>" + "<nonce_str>" + nonceStr
						+ "</nonce_str>" + "<sign>" + sign + "</sign>"
						+ "<body><![CDATA[" + productName + "]]></body>" 
						+ "<out_trade_no>" + orderid
						+ "</out_trade_no>" + "<attach>" + attach + "</attach>"
						+ "<total_fee>" + total_fee + "</total_fee>"
						+ "<spbill_create_ip>" + spbill_create_ip
						+ "</spbill_create_ip>" + "<notify_url>" + notify_url
						+ "</notify_url>" + "<trade_type>" + trade_type
						+ "</trade_type>" 
						+ "</xml>";
				String prepay_id = WxPay.getPrepayId(xml);
				// appId
				paramMap.put("appid", appid);
				// partnerid
				paramMap.put("partnerid", Global.getConfig("partnerId"));
				// appId
				paramMap.put("prepayid", prepay_id);
				// timeStamp
				paramMap.put("timestamp", timeStamp);
				// nonceStr
				paramMap.put("noncestr", nonceStr);
				// package
				paramMap.put("package", "Sign=WXPay");
				// paySign
				String string1 = WxPay.paySign(timeStamp,prepay_id, nonceStr, "Sign=WXPay");
				paramMap.put("sign", string1);
			}
			
			return buildReturnMap(SUCCESS, "", paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{}", e.getMessage());
			return buildReturnMap(FAILURE, "系统错误");
		}
	}
	

	
	/**
	 * 3.1 直接购买 Step1
	 */
	@RequestMapping(value = { "direct/pre" })
	public @ResponseBody Map<String, Object> directPrePay(@RequestParam("productId") Long productId, 
			@RequestParam(value = "specId", required = true, defaultValue = "0") Long specId, 
			@RequestParam(value = "quantity", required = true, defaultValue = "1") Integer quantity,
			@RequestHeader(value = "Authorization", required = true) String authorization,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			Date sysTime = new Date(System.currentTimeMillis());
			logger.info("直接购买 Step1");
			logger.info("authorization {}, \nproductId {}, \nspecId {}, \nquantity {}", authorization, productId, specId, quantity);
			Authorization auth = getAuthorization(authorization);
			if ("1".equals(auth.getErrorCode())) {
				return buildReturnMap(auth.getErrorCode(), auth.getErrorMsg());
			}
			Account account = systemService.getAccountByMobile(auth.getMobile());
			if (account == null) {
				logger.error("认证失败 account is null");
				return buildReturnMap(FAILURE, "认证失败");
			}
			
			Product product = productService.getProductById(productId);
			if (specId != null && specId != 0L) {
				Spec activeSpec = productService.getProductSpec(productId, specId);
				product.setActiveSpec(activeSpec);
				productService.getProductSpecInfo(product);
				
				if (activeSpec.getMinSell() != 0 && quantity < activeSpec.getMinSell()) {
					logger.info("商品最少{}起拍", activeSpec.getMinSell());
					String unit = StringUtils.isBlank(product.getUnit()) ? "个" : product.getUnit();
					return buildReturnMap("1", "该商品至少需订购" + activeSpec.getMinSell() + unit);
				}
			} else if (product.getMinSell() != 0 && quantity < product.getMinSell()) {
				logger.info("商品最少{}起拍", product.getMinSell());
				String unit = StringUtils.isBlank(product.getUnit()) ? "个" : product.getUnit();
				return buildReturnMap("1", "该商品至少需订购" + product.getMinSell() + unit);
			} 
			
			double discount = 100.00;// 折扣
			double discountPrice = 0.00;// 折扣价格
			double fee = 0.00;// 物流费用
			double totalPrice = product.getNowPrice() * quantity;// 商品总价
			int activityStatus = ActivityStatus.NONE.getStatus();// 活动状态
			int requiredIntegral = 0;// 获得积分
			double finalPrice = product.getNowPrice() ;// 最终售价
			Activity activity = product.getActivity();
			if (activity == null) {// 该商品没有参加任何活动
				logger.info("该商品【{}({})】没有参加活动", product.getName(), product.getId());
				if (StringUtils.isNotBlank(account.getRank())) {
					AccountRank ar = commonService.getAccountRankByCode(account.getRank());
					discount = ar.getDiscount() * 100;
					discountPrice = (100 - discount) * product.getNowPrice() / 100;
				}  
				
				fee = orderService.calculateFreight(product.getNowPrice() * quantity);
//				cart.setActivityStatus(ActivityStatus.NONE.getStatus());
			} else {
				String activityType = activity.getActivityType();
				String discountType = activity.getDiscountType();
				String accountRange = activity.getAccountRange();
				String []ss1 = accountRange.split(",");
				List<String> ranks = Lists.newArrayList();
				for (String s1 : ss1) {
					if (!StringUtils.isBlank(s1.trim()))
						ranks.add(s1.trim());
				}
				logger.info("该商品【{}({})】参加活动，活动类型【{}】，折扣类型【{}】", product.getName(), product.getId(), activityType, discountType);
				logger.info("活动开始时间 {}, 活动结束时间 {}, 当前时间 {}, ",
						DateUtils.formatDate(activity.getStartDate(), "yyyy-MM-dd HH:mm:ss"), 
						DateUtils.formatDate(activity.getEndDate(), "yyyy-MM-dd HH:mm:ss"),
						DateUtils.formatDate(sysTime, "yyyy-MM-dd HH:mm:ss"));
				logger.info("参加活动的会员等级 {}, 当前会员等级 {}", accountRange, account.getRank());
				// 活动类型 c:促销活动；j:积分兑换；t:团购活动
				if (activity.getStartDate().getTime() > sysTime.getTime()) {
					logger.info("活动还未开始");
					activityStatus = ActivityStatus.NOSTART.getStatus();
				} else if (activity.getEndDate().getTime() < sysTime.getTime()) {
					logger.info("活动已经结束");
					activityStatus = ActivityStatus.ISOVER.getStatus();
				} else if (ranks.size() != 4 && !StringUtils.isBlank(accountRange) && !ranks.contains(account.getRank())) {
					logger.info("没有权限");
					activityStatus = ActivityStatus.NOAUTH.getStatus();
				} else {
					if (activityType.equals(Activity.activity_activityType_c)) {// 1.促销活动
						if (discountType.equals(Activity.activity_discountType_r)) {// 减免
							finalPrice = product.getNowPrice() - activity.getDiscount();
							if (activity.getMinprice() !=null && (finalPrice < activity.getMinprice().doubleValue())) {
								finalPrice = activity.getMinprice();
							}
						} else if (discountType.equals(Activity.activity_discountType_d)) {// 折扣
							finalPrice = product.getNowPrice() * activity.getDiscount() / 100d;
							if (activity.getMinprice() !=null && (finalPrice < activity.getMinprice().doubleValue())) {
								finalPrice = activity.getMinprice();
							}
						} else if (discountType.equals(Activity.activity_discountType_s)) {// 双倍积分 双倍积分的商品价格上不做优惠
							finalPrice = product.getNowPrice();
							requiredIntegral = product.getScore() * 2;// 获得的积分*2
						}
					} else if (activityType.equals(Activity.activity_activityType_j)) {// 2.积分活动
						finalPrice = 0.00;// 最终价格等于0
						requiredIntegral = activity.getExchangeScore();
					} else if (activityType.equals(Activity.activity_activityType_t)) {// 3.团购活动
						finalPrice = activity.getTuanPrice();// 最终价格等于团购价
					}
//					cart.setActivityId(activity.getId());
//					cart.setActivityName(activity.getName());
//					discountType = Activity.activity_discountType_r;
//					discount = activity.getDiscount();
//					cart.setActivityType(activityType);
				}
				discountPrice = totalPrice - finalPrice * quantity;
			}
			
			//计算物流费用
			finalPrice = (totalPrice - discountPrice + fee);
			
			List<Address> addresses = accountService.findAddressByAccountId(account.getId());
			Address defaultAddress = null;
			for (Address address : addresses) {
				if ("y".equals(address.getIsDefault())) {
					defaultAddress = address;
				}
			}
			
			Map<String, Object> paramMap = Maps.newHashMap();
			
//			paramMap.put("discount", discount);
			paramMap.put("discountPrice", df.format(discountPrice));
			paramMap.put("totalPrice", df.format(product.getNowPrice() * quantity));
			paramMap.put("addressId", defaultAddress == null ? "" : defaultAddress.getId());
			paramMap.put("fee", df.format(fee));
//			paramMap.put("activityStatus", activityStatus);
//			paramMap.put("requiredIntegral", requiredIntegral);
			paramMap.put("finalPrice", df.format(finalPrice));
//			paramMap.put("activity", activity);
			
			return buildReturnMap(SUCCESS, "", paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{}", e.getMessage());
			return buildReturnMap(FAILURE, "系统错误");
		}
	}
	
	/**
	 * 3.1 直接购买 Step2 准备支付
	 */
	@RequestMapping(value = { "direct/pay" })
	public @ResponseBody Map<String, Object> directPay(@RequestParam("productId") Long productId, 
			@RequestParam(value = "specId", required = true, defaultValue = "0") Long specId, 
			@RequestParam(value = "quantity", required = true, defaultValue = "1") Integer quantity,
			@RequestParam("addressId") Long addressId, 
			@RequestParam(value = "expressCode", required = false, defaultValue = "LOGISTICS") String expressCode, 
			@RequestParam(value = "payCode", required = true) String payCode,
			@RequestParam(value = "deliveryDayCode", required = false, defaultValue = "all") String deliveryDayCode,
			@RequestParam(required = false, value = "remark") String remark,
			@RequestHeader(value = "Authorization", required = true) String authorization,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			Date systemTime = new Date(System.currentTimeMillis());
			logger.info("支付,生成订单");
			logger.info("authorization {}, \naddressId {}, \nexpressCode {}, \npayCode {}, \ndeliveryDayCode {}, \nremark {}", 
					authorization, addressId, expressCode, payCode, deliveryDayCode, remark);
			if (StringUtils.isBlank(authorization)) {
				logger.error("认证失败 authorization is null");
				return buildReturnMap(FAILURE, "认证失败");
			}
			if (authorization.indexOf("Basic") >= 0) {
				authorization = authorization.substring(5).trim();
			}
			authorization = new String(Encodes.decodeBase64(authorization), "UTF-8");
			String[] ss = authorization.split(":");
			if (ss.length < 2) {
				return buildReturnMap(FAILURE, "认证失败");
			}
			
			String token = ss[0];
			Device device = systemService.getDevice(token);
			String mobile = ss[1];
//			String password = Encodes.rc4(new String(Encodes.decodeBase64(ss[2]), "UTF-8"), device.getDeviceSecret());
			
			Express express = commonService.getExpress(expressCode);
			Pay pay = commonService.getPay(payCode);
			DeliveryDay deliveryDay = commonService.getDeliveryDay(deliveryDayCode);
			Address address = accountService.getAddress(addressId);
			Account account = systemService.getAccountByMobile(mobile);
			
			Product product = productService.getProductById(productId);
			
			Cart cart = orderService.saveCart(product, specId, account, token, quantity, CartAction.Add.getAction());
			Order order = orderService.createOrder(account, token, cart.getId().toString(), address, express, pay, deliveryDay, remark);
			
	//		//跟新产品sellCount
	//		List<Orderdetail> ods = order.getOdList();
	//		for (Orderdetail orderdetail : ods) {
	//			productService.incrSellCount(orderdetail.getProductID());
	//		}
	//		//设置当前地址为默认地址
	//		accountService.setDefaultAddr(account.getId(), address);
			Map<String, Object> paramMap = Maps.newHashMap();
			if (Pay.pay_code_alipayescow.equals(payCode)) {
				String subject = "报喜了 - 订单号 "+ order.getNo() ;
				String notify_url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/order/pay/notify";
				paramMap.put("partner", Global.getAlipayPartner());// 支付宝PID
				paramMap.put("seller_id", Global.SELLER_EMAIL);// 卖家支付宝账号
				paramMap.put("out_trade_no", order.getNo());// 商品订单编号
				paramMap.put("subject", subject);// 商品名称 订单名称
				paramMap.put("body", subject);
				paramMap.put("total_fee", (order.getAmount() + order.getFee()) + "");// 价格 = 商品价格 + 运费
				paramMap.put("notify_url", notify_url);// 服务器异步通知页面路径
				paramMap.put("service", "mobile.securitypay.pay");// 接口服务
				paramMap.put("payment_type", "1");// 支付类型
				paramMap.put("_input_charset", AlipayConfig.input_charset);// 统一编码
				paramMap.put("it_b_pay", "30m");
				paramMap.put("sygn_type", "RSA");
				paramMap.put("sign", "");
				paramMap.put("alipay_key", Global.getAlipayKey());
				paramMap.put("alipay_private_key", Global.getAlipayPrivateKey());
			} else if (Pay.pay_code_wx.equals(payCode)) {
				List<Orderdetail> ods = order.getOdList();
				StringBuffer sb = new StringBuffer();
				for (Orderdetail orderdetail : ods) {
					sb.append(orderdetail.getProductName() + ",");
				}
				String productName = sb.substring(0, sb.length() - 1).toString();
				String total_fee   = (new   Double((order.getAmount() + order.getFee()) * 100)).intValue() + "";
				String orderid     = order.getNo();
				
				// package 参数------------------------------  //
				// 对商品名截取, 去除空格 
				productName = productName.replaceAll(" ", "");
				productName = productName.length() > 17 ? productName.substring(0, 17) + "..." : productName;
				String nonceStr = RandomStringUtils.random(10, "123456789"); // 8位随机数
				String appid = Global.getConfig("AppId");
				String mch_id = Global.getConfig("partnerId");
				String notify_url = Global.getConfig("notify_url");
				String trade_type = "APP";
				String attach = "";
				String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);

				String spbill_create_ip = HttpServletRequestUtils.getIp(request);
				SortedMap<String, String> packageParams = new TreeMap<String, String>();
				
				packageParams.put("appid", appid);
				packageParams.put("mch_id", mch_id);
				packageParams.put("nonce_str", nonceStr);
				packageParams.put("body", productName);
				packageParams.put("out_trade_no", orderid);

				// 这里写的金额为1 分到时修改
				packageParams.put("total_fee", total_fee);
				packageParams.put("spbill_create_ip", spbill_create_ip);
				packageParams.put("notify_url", notify_url);
				packageParams.put("trade_type", trade_type);
				String sign = WxPay.getSign(packageParams, Global.getConfig("partnerKey"));
				
				String xml = "<xml>" + "<appid>" + appid + "</appid>" + "<mch_id>"
						+ mch_id + "</mch_id>" + "<nonce_str>" + nonceStr
						+ "</nonce_str>" + "<sign>" + sign + "</sign>"
						+ "<body><![CDATA[" + productName + "]]></body>" 
						+ "<out_trade_no>" + orderid
						+ "</out_trade_no>" + "<attach>" + attach + "</attach>"
						+ "<total_fee>" + total_fee + "</total_fee>"
						+ "<spbill_create_ip>" + spbill_create_ip
						+ "</spbill_create_ip>" + "<notify_url>" + notify_url
						+ "</notify_url>" + "<trade_type>" + trade_type
						+ "</trade_type>" 
						+ "</xml>";
				String prepay_id = WxPay.getPrepayId(xml);
				// appId
				paramMap.put("appid", appid);
				// partnerid
				paramMap.put("partnerid", Global.getConfig("partnerId"));
				// appId
				paramMap.put("prepayid", prepay_id);
				// timeStamp
				paramMap.put("timestamp", timeStamp);
				// nonceStr
				paramMap.put("noncestr", nonceStr);
				// package
				paramMap.put("package", "Sign=WXPay");
				// paySign
				String string1 = WxPay.paySign(timeStamp,prepay_id, nonceStr, "Sign=WXPay");
				paramMap.put("sign", string1);
			}
			
			return buildReturnMap(SUCCESS, "", paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{}", e.getMessage());
			return buildReturnMap(FAILURE, "系统错误");
		}
	}
	
	/**
     * 异步通知付款状态的Controller
     */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "ali/notify", method = RequestMethod.GET)
	public String aliPayNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		// 商户订单号
		String outTradeNo = request.getParameter("out_trade_no");
		// 支付宝交易号
		String tradeNo = request.getParameter("out_trade_no");
		// 交易状态
		String tradeStatus = request.getParameter("trade_status");
		String amount = request.getParameter("total_fee");
				
		logger.info("outTradeNo {}, tradeNo {}, tradeStatus {}, amount {}", outTradeNo, tradeNo, tradeStatus, amount);
				
		// String notifyId = request.getParameter("notify_id");
		if (AlipayNotify.verify(params, "MD5")) {// 验证成功
			if (tradeStatus.equals("TRADE_FINISHED")
					|| tradeStatus.equals("TRADE_SUCCESS")) {
				Order order = orderService.getOrder(Long.valueOf(outTradeNo));
		    	if (order != null && !Order.order_paystatus_y.equals(order.getPaystatus())) {
		    		PayLog payLog = new PayLog(order.getAccountId(), Long.valueOf(outTradeNo), Double.valueOf(amount), tradeStatus);
		    		orderService.savePayLog(payLog);
					if (order.getOrderpayId() != null) {
						Orderpay orderpay = orderService.getOrderpay(order.getOrderpayId());
						//更新支付记录为【成功支付】
						orderpay.setTradeNo(tradeNo);
						orderpay.setPaystatus(Orderpay.orderpay_paystatus_y);
						orderService.updateOrderpay(orderpay);
					}
					String content = "【支付宝异步通知】已付款，等待卖家发货(WAIT_SELLER_SEND_GOODS)。";
					Orderlog orderlog = new Orderlog();
					orderlog.setOrderid(order.getId());//订单ID
					orderlog.setAccountId(0L);
					orderlog.setAccount("alipay_notify");//操作人账号
					orderlog.setContent(content);//日志内容
					orderlog.setAccountType(Orderlog.orderlog_accountType_p);
					orderService.saveOrderlog(orderlog);
					
		    		order.setPaystatus(Order.order_paystatus_y);
		    		order.setPayDate(new Date());
		    		orderService.updateOrder(order);
		    	}
				logger.error("ok.......");
			}
			response.getWriter().write("success");
			return null;
		} else {// 验证失败
			response.getWriter().write("fail");
			return null;
		}

	}
	
	/**
     * 异步通知付款状态的Controller
     */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "wx/notify", method = RequestMethod.GET)
	public String wxPayNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		// 商户订单号
		String outTradeNo = request.getParameter("out_trade_no");
		// 支付宝交易号
		String tradeNo = request.getParameter("out_trade_no");
		// 交易状态
		String tradeStatus = request.getParameter("trade_status");
		String amount = request.getParameter("total_fee");
				
		logger.info("outTradeNo {}, tradeNo {}, tradeStatus {}, amount {}", outTradeNo, tradeNo, tradeStatus, amount);
				
		// String notifyId = request.getParameter("notify_id");
		if (AlipayNotify.verify(params, "MD5")) {// 验证成功
			if (tradeStatus.equals("TRADE_FINISHED")
					|| tradeStatus.equals("TRADE_SUCCESS")) {
				Order order = orderService.getOrder(Long.valueOf(outTradeNo));
		    	if (order != null && !Order.order_paystatus_y.equals(order.getPaystatus())) {
		    		PayLog payLog = new PayLog(order.getAccountId(), Long.valueOf(outTradeNo), Double.valueOf(amount), tradeStatus);
		    		orderService.savePayLog(payLog);
					if (order.getOrderpayId() != null) {
						Orderpay orderpay = orderService.getOrderpay(order.getOrderpayId());
						//更新支付记录为【成功支付】
						orderpay.setTradeNo(tradeNo);
						orderpay.setPaystatus(Orderpay.orderpay_paystatus_y);
						orderService.updateOrderpay(orderpay);
					}
					String content = "【支付宝异步通知】已付款，等待卖家发货(WAIT_SELLER_SEND_GOODS)。";
					Orderlog orderlog = new Orderlog();
					orderlog.setOrderid(order.getId());//订单ID
					orderlog.setAccountId(0L);
					orderlog.setAccount("alipay_notify");//操作人账号
					orderlog.setContent(content);//日志内容
					orderlog.setAccountType(Orderlog.orderlog_accountType_p);
					orderService.saveOrderlog(orderlog);
					
		    		order.setPaystatus(Order.order_paystatus_y);
		    		order.setPayDate(new Date());
		    		orderService.updateOrder(order);
		    	}
				logger.error("ok.......");
			}
			response.getWriter().write("success");
			return null;
		} else {// 验证失败
			response.getWriter().write("fail");
			return null;
		}

	}
	
}
