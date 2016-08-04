package com.tuisitang.modules.shop.front.web;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.alipay.util.AlipaySubmit;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tuisitang.common.annotation.Token;
import com.tuisitang.common.bo.ActivityStatus;
import com.tuisitang.common.utils.AddressUtils;
import com.tuisitang.common.utils.CookieUtils;
import com.tuisitang.common.utils.DateUtils;
import com.tuisitang.common.utils.Identities;
import com.tuisitang.common.utils.SpringContextHolder;
import com.tuisitang.common.utils.StringUtils;
import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.entity.AccountRank;
import com.tuisitang.modules.shop.entity.Activity;
import com.tuisitang.modules.shop.entity.Address;
import com.tuisitang.modules.shop.entity.Cart;
import com.tuisitang.modules.shop.entity.DeliveryDay;
import com.tuisitang.modules.shop.entity.Express;
import com.tuisitang.modules.shop.entity.Order;
import com.tuisitang.modules.shop.entity.Orderdetail;
import com.tuisitang.modules.shop.entity.Orderlog;
import com.tuisitang.modules.shop.entity.Orderpay;
import com.tuisitang.modules.shop.entity.Pay;
import com.tuisitang.modules.shop.entity.PayLog;
import com.tuisitang.modules.shop.entity.Product;
import com.tuisitang.modules.shop.entity.Spec;
import com.tuisitang.modules.shop.front.event.OrderPaymentCompleteEvent;
import com.tuisitang.modules.shop.front.interceptor.LogInterceptor;
import com.tuisitang.modules.shop.front.service.ProductFrontService;
import com.tuisitang.modules.shop.front.utils.AccountUtils;
import com.tuisitang.modules.shop.service.AccountService;
import com.tuisitang.modules.shop.service.CommonService;
import com.tuisitang.modules.shop.service.OrderService;
import com.tuisitang.modules.shop.utils.Global;

/**    
 * @{#} OrderController.java   
 * 
 * 订单Controller
 * 
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@Controller
@RequestMapping("/order")
public class OrderController extends BaseController {

	@Autowired
	private AccountService accountService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductFrontService productFrontService;
	@Autowired
	private CommonService commonService;
	
	/**
	 * 1. 购物车提交 - 订单确认
	 */
	@RequiresUser
	@Token(save=true)
	@RequestMapping(value = { "confirm" })
	public String confirm(HttpServletRequest request, HttpServletResponse response, Model model) {
		Date sysTime = new Date(System.currentTimeMillis());
		String[] ids = request.getParameterValues("id");
		if (ids == null || ids.length == 0) {
			return "redirect:/cart";
		}
		for (String id : ids) {
			logger.info("id {}", id);
		}
		logger.info("订单确认");
		List<DeliveryDay> ddList = commonService.findAllDeliveryDay();// 获得所有的日期
		List<Express> expressList = commonService.getAllExpress();// 获得所有的快递方式
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
		Account account = AccountUtils.getAccount();
		String sessionId = CookieUtils.getCookie(request, LogInterceptor.SESSION_NAME);
		Long[] idss = (Long[]) ConvertUtils.convert(ids, Long.class);
//		List<Cart> carts = orderService.findCart(sessionId, account.getId());
		List<Cart> carts = orderService.findCart(sessionId, account.getId(), idss);
		double totalPrice = 0.00;//商品总金额
		double totalFreight = 0.00;//总运费
		double totalDiscount = 0.00;//会员等级折扣
		double totalPay = 0.00;//总支付金额
		
		int totalIntegral = 0;
		int total = 0;
		String s = "";
		for (Cart cart : carts) {
			logger.info("Cart {}", cart);
//			if (!cart.isChecked())
//				continue;
			Product product = productFrontService.getProductById(cart.getProductId());
			if (cart.getSpecId() != null && cart.getSpecId() != 0L) {
				Spec activeSpec = productFrontService.getProductSpec(product.getId(), cart.getSpecId());
				product.setActiveSpec(activeSpec);
				productFrontService.getProductSpecInfo(product);
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
				String []ss = accountRange.split(",");
				List<String> ranks = Lists.newArrayList();
				for (String s1 : ss) {
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
////			Product prod = productFrontService.getProductById(cart.getProductId());
////			double freight = orderService.calculateFreight(prod.getNowPrice());
//			double freight = orderService.calculateFreight(cart.getNowPrice() * cart.getQuantity());
			totalFreight += cart.getFee();
//			totalDiscount += cart.getDiscount() * cart.getQuantity();
			totalDiscount += cart.getDiscountPrice() * cart.getQuantity();
		}
//		//2999全场免运费
//		if (totalPrice > 2999) {
//			totalFreight = 0;
//		}
//		//计算会员等级折扣
//		model.addAttribute("discount", 1);
//		if (account.getRank() != null && !account.getRank().equals(AccountRank.RANK_CODE_R1)) {
//			AccountRank rank = accountService.getAccountRankByCode(account.getRank());
//			if (rank != null) {
//				totalDiscount = (1-rank.getDiscount()) * totalPrice;
//				account.setAccountRank(rank);
//				model.addAttribute("discount", rank.getDiscount());
//			}
//		}
		totalPay = (totalPrice - totalDiscount + totalFreight);
		
		//购物车按照销售商分组
		Map<Long, List<Cart>> group = Maps.newHashMap();
		for (Cart cart : carts) {
			if (!group.containsKey(cart.getSellerId())) {
				List<Cart> list = Lists.newArrayList();
				list.add(cart);
				group.put(cart.getSellerId(), list);
			} else {
				List<Cart> list = group.get(cart.getSellerId());
				list.add(cart);
			}
		}
				
		List<Address> addresses = accountService.findAddressByAccountId(account.getId());
		Address defaultAddress = null;
		for (Address address : addresses) {
			if ("y".equals(address.getIsDefault())) {
				defaultAddress = address;
			}
		}
		java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");  
		model.addAttribute("account", account);
		model.addAttribute("carts", group);
		model.addAttribute("totalPrice", df.format(totalPrice));
		model.addAttribute("totalIntegral", df.format(totalIntegral));
		model.addAttribute("totalFreight", df.format(totalFreight));
		model.addAttribute("totalDiscount", df.format(totalDiscount));
		model.addAttribute("totalPay", df.format(totalPay));
		model.addAttribute("total", total);
		model.addAttribute("ddList", ddList);
		model.addAttribute("expressList", expressList);
		model.addAttribute("payMap", payMap);
		model.addAttribute("addresses", addresses);
		model.addAttribute("provinces", commonService.findProvinces());
		model.addAttribute("addressId", defaultAddress == null ? null : defaultAddress.getId());
		model.addAttribute("expressCode", expressList.get(0).getCode());
		model.addAttribute("payCode", null);
		model.addAttribute("step", "2");
		request.getSession().setAttribute("token", Identities.encodeBase64(s));
		return "modules/front/order/confirm";
	}
	
	/**
	 * 2. 准备支付
	 * 生成订单，准备支付
	 */
	@RequiresUser
	@Token(remove=true)
	@RequestMapping(value = { "prepay" }, method = RequestMethod.POST)
	public String prepay(@RequestParam("addressId") Long addressId, @RequestParam("expressCode") String expressCode, @RequestParam("payCode") String payCode,
			@RequestParam("deliveryDayCode") String deliveryDayCode,
			@RequestParam(required = false, value = "remark") String remark,
			HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttr) {
		logger.info("支付,生成订单");
		String token = (String) request.getSession().getAttribute("token");
		String ids = Identities.decodeBase64(token);
		logger.info("token {}, addressId = {}, expressCode = {}, payCode = {}, ids = {}",
				token, addressId, expressCode, payCode, ids);
		logger.info("remark {}", remark);
		Account account = AccountUtils.getAccount();
		String sessionId = CookieUtils.getCookie(request, LogInterceptor.SESSION_NAME);
		Express express = commonService.getExpress(expressCode);
		Pay pay = commonService.getPay(payCode);
		DeliveryDay deliveryDay = commonService.getDeliveryDay(deliveryDayCode);
		Address address = accountService.getAddress(addressId);
		Order order = orderService.createOrder(account, sessionId, ids, address, express, pay, deliveryDay, remark);
		
		//跟新产品sellCount
		List<Orderdetail> ods = order.getOdList();
		for (Orderdetail orderdetail : ods) {
			productFrontService.incrSellCount(orderdetail.getProductID());
		}
		//设置当前地址为默认地址
		accountService.setDefaultAddr(account.getId(), address);
		
		redirectAttr.addFlashAttribute("showPay", true);
		return "redirect:/order/pay?orderId=" + order.getId();
	}
	
	/**
	 * 重复提交跳转到购物车
	 */
	@RequiresUser
	@RequestMapping(value = { "waiting" }, method = RequestMethod.GET)
	public String waiting(HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttr) {
		return "modules/front/order/waiting";
	}
	
	
	/**
	 * 3. 支付
	 */
	@RequiresUser
	@RequestMapping(value = { "pay" }, method = RequestMethod.GET)
	public String pay(@RequestParam("orderId") Long orderId, HttpServletRequest request, HttpServletResponse response, Model model) {
		Date systemTime = new Date(System.currentTimeMillis());
		logger.info("orderId {}", orderId);
		Order order = orderService.getOrder(orderId);
		logger.info("order {}", order);
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
		model.addAttribute("order", order);
		model.addAttribute("payMap", payMap);
		model.addAttribute("step", "3");
//		publishEvent(new OrderPaymentCompleteEvent(this, order, AccountUtils.getAccount(), systemTime));
		return "modules/front/order/pay";
	}
	
	/**
	 * 4. 跳转到支付宝进行支付
	 */
	@RequiresUser
	@RequestMapping(value = "pay/forward", method = RequestMethod.POST,produces = "application/json")  
	public String forward(@RequestParam("orderId") Long orderId, @RequestParam(required = false, value = "payCode") String payCode,
			HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
		logger.info("orderId {}, payCode {}", orderId, payCode);
		Order order = orderService.getOrder(orderId);
		logger.info("order {}", order);
		
		String result = "";
		String return_url = request.getScheme()+"://"+request.getServerName()+ ":" +request.getServerPort() + request.getContextPath() + "/order/pay/success";
		String notify_url = request.getScheme()+"://"+request.getServerName()+ ":" +request.getServerPort() + request.getContextPath() + "/order/pay/notify";
		String subject = "报喜了 - 订单号 "+ order.getNo() ; // 聚美优品 - 购物车编号 s85292098-14368647566821
//		String body = ServletRequestUtils.getStringParameter(request, "body", "test");
		
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "create_direct_pay_by_user");// 接口服务----即时到账
		sParaTemp.put("partner", Global.getAlipayPartner());// 支付宝PID
		sParaTemp.put("_input_charset", AlipayConfig.input_charset);// 统一编码
		sParaTemp.put("payment_type", "1");// 支付类型
		sParaTemp.put("notify_url", notify_url);// 服务器异步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数
		sParaTemp.put("return_url", return_url);// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，不能写成http://localhost/
		sParaTemp.put("seller_email", Global.SELLER_EMAIL);// 卖家支付宝账号
		sParaTemp.put("out_trade_no", order.getId() + "");// 商品订单编号
		sParaTemp.put("subject", subject);// 商品名称 订单名称
		sParaTemp.put("total_fee", (order.getAmount() + order.getFee()) + "");// 价格 = 商品价格 + 运费
		sParaTemp.put("body", subject);
		sParaTemp.put("show_url", "http://www.baoxiliao.com/product/list");// 商品展示地址
//		sParaTemp.put("show_url", "http://www.wedding-cloud.com");// 商品展示地址
		sParaTemp.put("anti_phishing_key", "");// 防钓鱼时间戳 若要使用请调用类文件submit中的query_timestamp函数
		sParaTemp.put("exter_invoke_ip", AddressUtils.getHost(request));// 客户端的外网IP地址

		// 建立请求
		try {
			//TODO 如果有多种支付方式，Orderpay会有所变化，那时需要重新生成Orderpay
			String sHtmlText = AlipaySubmit.buildRequest(sParaTemp, "post", "确认", "MD5");
			result = "{\"success\":true,\"msg\":\"跳转成功\"}";
			StringUtils.writeToWeb(sHtmlText, "html", response);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{}", e.getMessage());
			result = "{\"success\":false,\"msg\":\"跳转失败，请稍候再试！\"}";
			StringUtils.writeToWeb(result, "html", response);
			return null;
		}
	}
	
	/**
	 * 5. 查看支付状态 
	 * action: success/error
	 */
	@RequiresUser
	@RequestMapping(value = { "pay/status" }, method = RequestMethod.GET)
	public String payStatus(@RequestParam("orderId") Long orderId, @RequestParam("action") String action, HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("orderId {}, action {}", orderId, action);
		Order order = orderService.getOrder(orderId);
		logger.info("order {}", order);
		model.addAttribute("order", order);
		return "modules/front/order/status";
	}
	
    /**
     * 同步通知的页面的Controller
     */
    @RequestMapping(value="/pay/success")
    public String paySuccess(HttpServletRequest request,HttpServletResponse response){
    	Date systemTime = new Date(System.currentTimeMillis());
		// 商户订单号
		String outTradeNo = request.getParameter("out_trade_no");
		// 支付宝交易号
		String tradeNo = request.getParameter("trade_no");
		// 交易状态
		String tradeStatus = request.getParameter("trade_status");
		
		logger.info("outTradeNo {}, tradeNo {}, tradeStatus {}", outTradeNo, tradeNo, tradeStatus);
    			
    	Order order = orderService.getOrder(Long.valueOf(outTradeNo));
    	if (order != null && !Order.order_paystatus_y.equals(order.getPaystatus())) {
    		String amount = request.getParameter("total_fee");
    		PayLog payLog = new PayLog(AccountUtils.getAccount().getId(), Long.valueOf(outTradeNo), Double.valueOf(amount), tradeStatus);
    		orderService.savePayLog(payLog);
			if (order.getOrderpayId() != null) {
				Orderpay orderpay = orderService.getOrderpay(order.getOrderpayId());
				//更新支付记录为【成功支付】
				orderpay.setTradeNo(tradeNo);
				orderpay.setPaystatus(Orderpay.orderpay_paystatus_y);
				orderService.updateOrderpay(orderpay);
			}
			String content = "【支付宝同步通知】已付款，等待卖家发货(WAIT_SELLER_SEND_GOODS)。";
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
    		
    		publishEvent(new OrderPaymentCompleteEvent(this, order, AccountUtils.getAccount(), systemTime));
    	}
        return "redirect:/order/pay/status?orderId=" + outTradeNo + "&action=sucess";
    }
    
    /**
     * 异步通知付款状态的Controller
     */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "pay/notify", method = RequestMethod.GET)
	public String payNotify(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Date systemTime = new Date(System.currentTimeMillis());
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
		    		PayLog payLog = new PayLog(AccountUtils.getAccount().getId(), Long.valueOf(outTradeNo), Double.valueOf(amount), tradeStatus);
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
		    		
		    		publishEvent(new OrderPaymentCompleteEvent(this, order, AccountUtils.getAccount(), systemTime));
		    	}
				logger.error("ok.......");
			}
			response.getWriter().write("success");
			return "redirect:/order/pay/status?orderId=" + outTradeNo + "action='sucess'";
		} else {// 验证失败
			response.getWriter().write("fail");
			return "redirect:/order/pay/status?orderId=" + outTradeNo + "action='fail'";
		}

	}
	
	
	/**
	 * 处理会员是否升级
	 */
	public void accountUpgrade() {
		
	}
}
