//package com.tuisitang.modules.shop.front.service;
//
//import java.util.Date;
//import java.util.List;
//
//import org.apache.commons.beanutils.ConvertUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.google.common.collect.Lists;
//import com.tuisitang.common.bo.ActivityStatus;
//import com.tuisitang.common.bo.CartAction;
//import com.tuisitang.common.bo.OrderStatus;
//import com.tuisitang.common.bo.Status;
//import com.tuisitang.common.cache.memcached.SpyMemcachedClient;
//import com.tuisitang.common.service.ServiceException;
//import com.tuisitang.common.utils.DateUtils;
//import com.tuisitang.modules.shop.dao.AccountRankDao;
//import com.tuisitang.modules.shop.dao.AddressDao;
//import com.tuisitang.modules.shop.dao.BookingOrderDao;
//import com.tuisitang.modules.shop.dao.BookingOrderLogDao;
//import com.tuisitang.modules.shop.dao.CartDao;
//import com.tuisitang.modules.shop.dao.ExpressDao;
//import com.tuisitang.modules.shop.dao.OrderDao;
//import com.tuisitang.modules.shop.dao.OrderdetailDao;
//import com.tuisitang.modules.shop.dao.OrderlogDao;
//import com.tuisitang.modules.shop.dao.OrderpayDao;
//import com.tuisitang.modules.shop.dao.OrdershipDao;
//import com.tuisitang.modules.shop.dao.PayLogDao;
//import com.tuisitang.modules.shop.entity.Account;
//import com.tuisitang.modules.shop.entity.AccountRank;
//import com.tuisitang.modules.shop.entity.Activity;
//import com.tuisitang.modules.shop.entity.Address;
//import com.tuisitang.modules.shop.entity.BookingOrder;
//import com.tuisitang.modules.shop.entity.BookingOrderLog;
//import com.tuisitang.modules.shop.entity.Cart;
//import com.tuisitang.modules.shop.entity.DeliveryDay;
//import com.tuisitang.modules.shop.entity.Express;
//import com.tuisitang.modules.shop.entity.Order;
//import com.tuisitang.modules.shop.entity.Orderdetail;
//import com.tuisitang.modules.shop.entity.Orderlog;
//import com.tuisitang.modules.shop.entity.Orderpay;
//import com.tuisitang.modules.shop.entity.Ordership;
//import com.tuisitang.modules.shop.entity.Pay;
//import com.tuisitang.modules.shop.entity.PayLog;
//import com.tuisitang.modules.shop.entity.Product;
//import com.tuisitang.modules.shop.entity.Spec;
//
///**    
// * @{#} OrderFrontService.java  
// * 
// * 前台订单Service
// *    
// * <p>Copyright: Copyright(c) 2013 </p> 
// * <p>Company: TST</p>
// * @version 1.0
// * @author <a href="mailto:paninxb@gmail.com">panin</a>   
// */
//@Service
//public class OrderFrontService {
//
//	private static final Logger logger = LoggerFactory.getLogger(AccountFrontService.class);
//
//	@Autowired
//	private OrderDao orderDao;
//	@Autowired
//	private OrderdetailDao orderdetailDao;
//	@Autowired
//	private OrderlogDao orderlogDao;
//	@Autowired
//	private OrderpayDao orderpayDao;
//	@Autowired
//	private OrdershipDao ordershipDao;
//	@Autowired
//	private CartDao cartDao;
//	@Autowired
//	private ProductFrontService productFrontService;
//	@Autowired
//	private ExpressDao expressDao;
//	@Autowired
//	private SpyMemcachedClient spyMemcachedClient;
//	@Autowired
//	private BookingOrderDao bookingOrderDao;
//	@Autowired
//	private BookingOrderLogDao bookingOrderLogDao;
//	@Autowired
//	private PayLogDao payLogDao;
//	@Autowired
//	private AddressDao addressDao;
//	@Autowired
//	private AccountRankDao accountRankDao;
//	
//	// --Order
//	/**
//	 * 1. 保存订单
//	 * 
//	 * @param order
//	 */
//	@Transactional(readOnly = false)
//	public void saveOrder(Order order) {
//		orderDao.insert(order);
//	}
//
//	/**
//	 * 2. 删除订单
//	 * 
//	 * @param id
//	 */
//	@Transactional(readOnly = false)
//	public void deleteOrder(Long id) {
//		orderDao.deleteById(id);
//	}
//
//	/**
//	 * 3. 修改订单
//	 * 
//	 * @param order
//	 */
//	@Transactional(readOnly = false)
//	public void updateOrder(Order order) {
//		orderDao.update(order);
//	}
//
//	/**
//	 * 4. 根据id获得Order
//	 * 
//	 * @param id
//	 */
//	public Order getOrder(Long id) {
//		return orderDao.selectById(id);
//	}
//	
//	/**
//	 * 5. 创建订单
//	 * 
//	 * @param order
//	 * @param orderdetailList
//	 * @param ordership
//	 */
//	@Transactional(readOnly = false)
//	public void createOrder(Order order, List<Orderdetail> orderdetailList, Ordership ordership) {
//		if (order == null || orderdetailList == null
//				|| orderdetailList.size() == 0 || ordership == null) {
//			throw new ServiceException("参数不能为空！");
//		}
//
//		// 创建订单
//		orderDao.insert(order);
//		Long orderId = order.getId();
//		logger.info("orderId  {}", orderId);
//
//		// 创建订单项
//		for (int i = 0; i < orderdetailList.size(); i++) {
//			Orderdetail orderdetail = orderdetailList.get(i);
//			orderdetail.setOrderID(orderId);
//			orderdetailDao.insert(orderdetail);
//		}
//
//		// 创建支付记录对象
//		Orderpay orderpay = new Orderpay();
//		orderpay.setOrderid(order.getId());
//		orderpay.setPaystatus(Orderpay.orderpay_paystatus_n);
//		orderpay.setPayamount(Double.valueOf(order.getAmount()));
//		orderpay.setPaymethod(Orderpay.orderpay_paymethod_alipayescow);
//		orderpayDao.insert(orderpay);
//		Long orderpayId = orderpay.getId();
//		logger.info("orderpayId {}", orderpayId);
////		order.setOrderpayID(orderpayId);
//
//		// 记录配送信息
//		ordership.setOrderid(orderId);
//		ordershipDao.insert(ordership);
//
//		// 记录订单创建日志
//		Orderlog orderlog = new Orderlog();
//		orderlog.setOrderid(orderId);
//		orderlog.setAccountId(order.getAccountId());
//		orderlog.setAccount(order.getAccount());
//		orderlog.setContent("【创建订单】用户创建订单。订单总金额：" + order.getAmount());
//		orderlog.setAccountType(Orderlog.orderlog_accountType_w);
//		orderlogDao.insert(orderlog);
//	}
//	
//	/**
//	 * 6. 取消订单
//	 * 
//	 * @param orderId
//	 * @param reasonId
//	 * @param reason
//	 * @param reasonDesc
//	 * @param account
//	 */
//	@Transactional(readOnly = false)
//	public void cancelOrder(Long orderId, int reasonId, String reason, String reasonDesc, Account account) {
//		orderDao.updateStatus(orderId, OrderStatus.CANCEL.getCode());
//		Orderlog orderlog = new Orderlog();
//		orderlog.setOrderid(orderId);
//		orderlog.setAccountId(account.getId());
//		orderlog.setAccount(account.getMobile());
//		orderlog.setContent("【取消订单】用户取消订单，取消原因：" + reason + "(" + reasonId + ")；" + reasonDesc);
//		orderlog.setAccountType(Orderlog.orderlog_accountType_w);
//		orderlogDao.insert(orderlog);
//	}
//	
//	/**
//	 * 6.1 确认收货
//	 * 
//	 * @param orderId
//	 * @param account
//	 */
//	@Transactional(readOnly = false)
//	public void confirmGoods(Long orderId, Account account) {
//		orderDao.updateStatus(orderId, OrderStatus.FILE.getCode());
//		Orderlog orderlog = new Orderlog();
//		orderlog.setOrderid(orderId);
//		orderlog.setAccountId(account.getId());
//		orderlog.setAccount(account.getMobile());
//		orderlog.setContent("【确认收货】用户确认收货");
//		orderlog.setAccountType(Orderlog.orderlog_accountType_w);
//		orderlogDao.insert(orderlog);
//	}
//	
//	/**
//	 * 7. 创建订单
//	 * 
//	 * @param account
//	 * @param sessionId
//	 * @param ids
//	 * @param address
//	 * @param express
//	 * @param pay
//	 */
//	@Transactional(readOnly = false)
//	public Order createOrder(Account account, String sessionId, String ids, Address address, Express express, Pay pay, DeliveryDay deliveryDay, String remark) {
//		Date systemTime = new Date(System.currentTimeMillis());
//		// 14 + 6
//		long num = spyMemcachedClient.incr(Order.getKey(), 1, 1);
//		String no = DateUtils.formatDate(systemTime, "yyyyMMddHHmmss") + String.format("%06d", num);
//		Long[] idss = (Long[]) ConvertUtils.convert(StringUtils.split(ids, ","), Long.class);
//		List<Cart> carts = cartDao.findBySessionIdOrAccountIdAndIds(sessionId, account.getId(), idss);
//		Order order = new Order();
//		order.setNo(no);
//		order.setAccountId(account.getId());
//		order.setAccount(account.getMobile());
//		order.setStatus(OrderStatus.INIT.getCode());
//		order.setPaystatus(Order.order_paystatus_n);
//		order.setLowStocks(Order.order_lowStocks_n);
//		order.setRemark(remark);
////		order.setFee(express.getFee());
//		
//		double totalPrice = 0.00;//商品总金额
//		double totalFreight = 0.00;//总运费
//		double totalDiscount = 0.00;//会员等级折扣
//		double totalPay = 0.00;//总支付金额
//		double score = 0.00;//积分
//		
//		double ptotal = 0.00;
//		int totalIntegral = 0;
//		int total = 0;
//		List<Orderdetail> odList = Lists.newArrayList();
//		for (Cart cart : carts) {
//			Orderdetail od = new Orderdetail();
//			od.setProductID(cart.getProductId());
//			od.setProductName(cart.getProductName());
//			od.setPicture(cart.getProductImage());
//			od.setNumber(cart.getQuantity());
//			od.setSpecId(cart.getSpecId());
//			od.setSpecInfo(cart.getSpecName());
//			od.setFee(0.00);
//			od.setPrice(cart.getNowPrice());
//			od.setTotal0(cart.getNowPrice() * cart.getQuantity());
//			od.setSellerId(cart.getSellerId());
//			od.setSellerName(cart.getSellerName());
//			odList.add(od);
//			
//			ptotal += cart.getMarketPrice() * cart.getQuantity();
//			totalPrice += cart.getNowPrice() * cart.getQuantity();
//			totalIntegral += cart.getRequiredIntegral() * cart.getQuantity();
//			total += cart.getQuantity();
//			
////			//计算运费
//////			Product prod = productFrontService.getProductById(cart.getProductId());
////			double freight = calculateFreight(cart.getNowPrice() * cart.getQuantity());
//			totalFreight += cart.getFee();
//			totalDiscount += cart.getDiscountPrice();
//		}
////		//计算会员等级折扣
////		if (account.getRank() != null && !account.getRank().equals(AccountRank.RANK_CODE_R1)) {
////			AccountRank rank = accountRankDao.getAccountRankByCode(account.getRank());
////			if (rank != null) {
////				totalDiscount = (1 - rank.getDiscount()) * totalPrice;
////				account.setAccountRank(rank);
////			}
////		}
//////		totalPay = (totalPrice - totalDiscount + totalFreight);
//		totalPay = (totalPrice - totalDiscount);
//		score = totalPrice - totalDiscount;
//		order.setAmount(totalPay);
//		order.setFee(totalFreight);
//		order.setAmountExchangeScore(totalIntegral);
//		order.setPtotal(ptotal);
//		order.setQuantity(total);
//		order.setExpressCode(express.getCode());
//		order.setExpressName(express.getName());
//		order.setPayCode(pay.getCode());
//		order.setPayName(pay.getName());
//		order.setDeliveryDayCode(deliveryDay.getCode());
//		order.setDeliveryDayName(deliveryDay.getName());
//		order.setScore(new Double(Math.floor(score)).intValue());
//		order.setOdList(odList);
//		
//		orderDao.insert(order);
//		Long orderId = order.getId();
//		logger.info("order.id {}", orderId);
//		orderdetailDao.batchSave(orderId, odList);
//		
//		Orderpay orderpay = new Orderpay();
//		orderpay.setOrderid(order.getId());
//		orderpay.setPaystatus(Orderpay.orderpay_paystatus_n);
//		orderpay.setPayamount(Double.valueOf(order.getAmount()));
//		orderpay.setPaymethod(Orderpay.orderpay_paymethod_alipayescow);
//		orderpayDao.insert(orderpay);
//		Long orderpayId = orderpay.getId();
//		logger.info("orderpayId {}", orderpayId);
//		order.setOrderpayId(orderpayId);
//		orderDao.update(order);
//
//		// 记录配送信息
//		Ordership ordership = new Ordership();
//		ordership.setAddressId(address.getId());
//		ordership.setShipname(address.getName());
//		ordership.setShipaddress(address.getAddress());
//		ordership.setProvince(address.getProvinceName());
//		ordership.setProvinceCode(address.getProvinceCode());
//		ordership.setCity(address.getCityName());
//		ordership.setCityCode(address.getCityCode());
//		ordership.setArea(address.getCountyName());
//		ordership.setAreaCode(address.getCountyCode());
//		ordership.setPhone(address.getMobile());
//		ordership.setTel(address.getPhone());
//		ordership.setOrderid(orderId);
//		ordershipDao.insert(ordership);
//		
//		// 记录订单创建日志
//		Orderlog orderlog = new Orderlog();
//		orderlog.setOrderid(orderId);
//		orderlog.setAccountId(order.getAccountId());
//		orderlog.setAccount(order.getAccount());
//		orderlog.setContent("【创建订单】用户创建订单。订单总金额：" + totalPay);
//		orderlog.setAccountType(Orderlog.orderlog_accountType_w);
//		orderlogDao.insert(orderlog);
//		cartDao.deleteByIds(idss);
//		return order;
//	}
//	
////	public double calculateFreight(Double nowPrice) {
////		double freight = 0d;
////		if (nowPrice < 500 && nowPrice > 100) {
////			Double factor = Math.ceil(nowPrice / 100);
////			freight = factor * 10;
////		} else if (nowPrice <= 100){
////			freight = 20d;
////		} 
////		return freight;
////	}
//	
//	public double calculateFreight(Double nowPrice) {
//		double freight = 0d;
//		if (nowPrice > 100) {
//			Double factor = Math.ceil(nowPrice / 100);
//			freight = factor * 10;
//		} else if (nowPrice <= 100){
//			freight = 20d;
//		} 
//		return freight;
//	}
//
//	// --Orderdetail
//
//	// --Orderlog
//	/**
//	 * 1. 保存Orderlog
//	 * 
//	 * @param orderlog
//	 */
//	@Transactional(readOnly = false)
//	public void saveOrderlog(Orderlog orderlog) {
//		orderlogDao.insert(orderlog);
//	}
//	
//	/**
//	 * 2. 根据id删除
//	 * 
//	 * @param id
//	 */
//	@Transactional(readOnly = false)
//	public void deleteOrderlog(Long id) {
//		orderlogDao.deleteById(id);
//	}
//	
//	/**
//	 * 3. 更新Orderlog
//	 * 
//	 * @param orderlog
//	 */
//	@Transactional(readOnly = false)
//	public void updateOrderlog(Orderlog orderlog) {
//		orderlogDao.update(orderlog);
//	}
//	
//	/**
//	 * 4. 根据id获得Orderlog
//	 * 
//	 * @param id
//	 * @return
//	 */
//	public Orderlog getOrderlog(Long id) {
//		return orderlogDao.selectById(id);
//	}
//
//	// --Orderpay
//	/**
//	 * 1. 保存Orderpay
//	 * 
//	 * @param orderpay
//	 */
//	@Transactional(readOnly = false)
//	public void saveOrderpay(Orderpay orderpay) {
//		orderpayDao.insert(orderpay);
//	}
//	
//	/**
//	 * 2. 根据id删除
//	 * 
//	 * @param id
//	 */
//	@Transactional(readOnly = false)
//	public void deleteOrderpay(Long id) {
//		orderpayDao.deleteById(id);
//	}
//	
//	/**
//	 * 3. 更新Orderpay
//	 * 
//	 * @param orderpay
//	 */
//	@Transactional(readOnly = false)
//	public void updateOrderpay(Orderpay orderpay) {
//		orderpayDao.update(orderpay);
//	}
//	
//	/**
//	 * 4. 根据id获得Orderpay
//	 * 
//	 * @param id
//	 * @return
//	 */
//	public Orderpay getOrderpay(Long id) {
//		return orderpayDao.selectById(id);
//	}
//
//	// --Ordership
//	/**
//	 * 1. 新增/修改Ordership
//	 * 
//	 * @param ordership
//	 */
//	@Transactional(readOnly = false)
//	public void saveOrdership(Ordership ordership, Account account) {
//		Orderlog orderlog = new Orderlog();
//		orderlog.setOrderid(ordership.getOrderid());
//		orderlog.setAccountId(account.getId());
//		orderlog.setAccount(account.getMobile());
//		orderlog.setAccountType(Orderlog.orderlog_accountType_w);
//		
//		if (ordership.getId() == null) {
//			ordershipDao.insert(ordership);
//			orderlog.setContent("【修改地址】用户修改订单地址：" + ordership.getProvince()
//					+ "-" + ordership.getCity() + "-" + ordership.getArea()
//					+ " " + ordership.getShipaddress());
//		} else {
//			ordershipDao.update(ordership);
//			orderlog.setContent("【选择地址】用户修改订单地址：" + ordership.getProvince()
//					+ "-" + ordership.getCity() + "-" + ordership.getArea()
//					+ " " + ordership.getShipaddress());
//		}
//		orderlogDao.insert(orderlog);
//	}
//	
//	// --Cart
//	/**
//	 * 1. 创建，更新购物车
//	 * 
//	 * @param product
//	 * @param account
//	 * @param sessionId
//	 * @param quantity
//	 * @param action  -1 删除 0 更新 1 增加
//	 */
//	@Transactional(readOnly = false)
//	public void saveCart(Product product, Long specId, Account account, String sessionId, int quantity, int action) {
//		Date sysTime = new Date(System.currentTimeMillis());
//		if (product == null || product.getId() == null) {
//			logger.error("商品不能为空");
//			throw new ServiceException("商品不能为空");
//		}
//		if (StringUtils.isBlank(sessionId)) {
//			logger.error("Session ID不能为空");
//			throw new ServiceException("Session ID不能为空");
//		}
//		if (quantity <= 0 && CartAction.Set.getAction() != action) {// 非更新操作，商品数量不能小于0
//			logger.error("商品数量不能小于0");
//			throw new ServiceException("商品数量不能小于0");
//		}
//		// Long specId = null;
//		String specName = null;
//		Spec spec = null;
//		if (specId == null || specId == 0L) {
//			// 规格Id
//			spec = product.getActiveSpec();
//			if (spec != null) {
//				specId = spec.getId();
//				specName = getSpecName(spec);
//			} else {
//				specId = null;
//			}
//		} else {
//			spec = productFrontService.getProductSpec(product.getId(), specId);
//			product.setActiveSpec(spec);
//			if (spec != null) {
//				specName = getSpecName(spec);
//			}
//		}
//		
//		if (CartAction.Set.getAction() == action && quantity == 0) {
//			logger.info("更新购物车");
//			cartDao.deleteByProductId(sessionId, account.getId(), product.getId(), specId);
//			return;
//		}
//		Cart cart = null;
//		if (account != null && account.getId() != null) {
//			cart = cartDao.getByProductIdAndAccountId(product.getId(), account.getId(), specId);
//		} else {
//			cart = cartDao.getByProductIdAndSessionId(product.getId(), sessionId, specId);
//		}
//		if (cart == null) {
//			cart = new Cart();
//		}
//		//设计产品数量
//		if (cart.getId() != null) {
//			if (spec != null) {
//				quantity = getQuantityByAction(action, spec.getMinSell(), quantity, cart.getQuantity());
//			} else {
//				quantity = getQuantityByAction(action, product.getMinSell(),quantity, cart.getQuantity());
//			}
//		}
//		
//		//根据规格设置产品价格
//		product = productFrontService.getProductSpecInfo(product);
//		
//		cart.setAccountId(account.getId());
//		cart.setSessionId(sessionId);
//		cart.setProductId(product.getId());
//		cart.setSpecId(specId);
//		cart.setSpecName(specName);
//		cart.setSellerId(product.getSellerID());
//		cart.setSellerName(product.getSeller().getName());
//		cart.setProductName(product.getName());
//		cart.setProductImage(product.getPicture());
//		cart.setMarketPrice(product.getPrice());
//		cart.setNowPrice(product.getNowPrice());// 如果是积分兑换，则直接
//		cart.setCreateTime(sysTime);
//		int stock = product.getStock();// 获得现有的商品库存
//
//		if (quantity <= 0) {
//			logger.error("商品数量不能小于0");
//			throw new ServiceException("商品数量不能小于0");
//		}
//		if (quantity > stock) {
//			logger.error("库存不足");
//			throw new ServiceException("库存不足");
//		}
//		cart.setQuantity(quantity);
//		Activity activity = productFrontService.getActivityById(product.getActivityID());
//		
//		if (activity == null) {// 该商品没有参加任何活动
//			logger.info("该商品【{}({})】没有参加活动", product.getName(), product.getId());
//			if (StringUtils.isNotBlank(account.getRank())) {
//				AccountRank ar = this.accountRankDao.getAccountRankByCode(account.getRank());
//				if (ar != null) {
//					cart.setDiscount(ar.getDiscount() * 100);
//				} else {
//					cart.setDiscount(100);
//				}
//				cart.setDiscountPrice((100 - cart.getDiscount()) * cart.getNowPrice() / 100);
//			} else {// 未登录情况
//				cart.setDiscount(100);
//				cart.setDiscountPrice(0);
//			}
//			
//			cart.setFee(calculateFreight(cart.getNowPrice() * cart.getQuantity()));
//			cart.setActivityStatus(ActivityStatus.NONE.getStatus());
////			// 如果为Set，则直接更新数量
////			if (CartAction.Set.getAction() == action && cart.getId() != null) {
////				cartDao.updateQuantity(cart.getId(), quantity);
////				return;
////			}
//		} else {
//			double finalPrice = product.getNowPrice();
//			String activityType = activity.getActivityType();
//			String discountType = activity.getDiscountType();
//			String accountRange = activity.getAccountRange();
//			String []ss = accountRange.split(",");
//			List<String> ranks = Lists.newArrayList();
//			for (String s : ss) {
//				if (!StringUtils.isBlank(s.trim()))
//					ranks.add(s.trim());
//			}
//			logger.info("该商品【{}({})】参加活动，活动类型【{}】，折扣类型【{}】", product.getName(), product.getId(), activityType, discountType);
//			logger.info("活动开始时间 {}, 活动结束时间 {}, 当前时间 {}, ",
//					DateUtils.formatDate(activity.getStartDate(), "yyyy-MM-dd HH:mm:ss"), 
//					DateUtils.formatDate(activity.getEndDate(), "yyyy-MM-dd HH:mm:ss"),
//					DateUtils.formatDate(sysTime, "yyyy-MM-dd HH:mm:ss"));
//			logger.info("参加活动的会员等级 {}, 当前会员等级 {}", accountRange, account.getRank());
//			// 活动类型 c:促销活动；j:积分兑换；t:团购活动
//			if (activity.getStartDate().getTime() > sysTime.getTime()) {
//				logger.info("活动还未开始");
//				cart.setActivityStatus(ActivityStatus.NOSTART.getStatus());
//			} else if (activity.getEndDate().getTime() < sysTime.getTime()) {
//				logger.info("活动已经结束");
//				cart.setActivityStatus(ActivityStatus.ISOVER.getStatus());
////			} else if (!StringUtils.isBlank(accountRange) && ("," + accountRange + ",").indexOf("," + account.getRank() + ",") < 0) {
//			} else if (!StringUtils.isBlank(accountRange) && !ranks.contains(account.getRank())) {
//				logger.info("没有权限");
//				cart.setActivityStatus(ActivityStatus.NOAUTH.getStatus());
//			} else {
//				if (activityType.equals(Activity.activity_activityType_c)) {// 1.促销活动
//					if (discountType.equals(Activity.activity_discountType_r)) {// 减免
//						finalPrice = product.getNowPrice() - activity.getDiscount();
//						if (activity.getMinprice() !=null && (finalPrice < activity.getMinprice().doubleValue())) {
//							finalPrice = activity.getMinprice();
//						}
//					} else if (discountType.equals(Activity.activity_discountType_d)) {// 折扣
//						finalPrice = product.getNowPrice() * activity.getDiscount() / 100d;
//						if (activity.getMinprice() !=null && (finalPrice < activity.getMinprice().doubleValue())) {
//							finalPrice = activity.getMinprice();
//						}
//					} else if (discountType.equals(Activity.activity_discountType_s)) {// 双倍积分 双倍积分的商品价格上不做优惠
//						finalPrice = product.getNowPrice();
//						cart.setPresentIntegral(product.getScore() * 2);// 获得的积分*2
//					}
//				} else if (activityType.equals(Activity.activity_activityType_j)) {// 2.积分活动
//					finalPrice = 0.00;// 最终价格等于0
//					cart.setRequiredIntegral(activity.getExchangeScore());
//				} else if (activityType.equals(Activity.activity_activityType_t)) {// 3.团购活动
//					finalPrice = activity.getTuanPrice();// 最终价格等于团购价
//				}
//				cart.setActivityId(activity.getId());
//				cart.setActivityName(activity.getName());
//				cart.setDiscountType(Activity.activity_discountType_r);
//				cart.setDiscount(activity.getDiscount());
//				cart.setActivityType(activityType);
//			}
//			cart.setNowPrice(finalPrice);
//		}
//		if (cart.getId() == null) {
//			cartDao.insert(cart);
//		} else {
//			cartDao.update(cart);
//		}
//	}
//	
//	/**
//	 * 2. 更新购物车
//	 * @param cart
//	 */
//	@Transactional(readOnly = false)
//	public void updateCart(Cart cart) {
//		cartDao.update(cart);
//	}
//	
////	private int getQuantityByAction(int action, int minSell, int setQuantity, int quantity) {
////		//判断最小购买数
////		if (action == CartAction.Add.getAction()) {
////			if (minSell == 0) {
////				quantity = quantity + 1;
////			} else {
////				quantity = quantity + minSell;
////				quantity = getQuantityMinSell(quantity, minSell);
////			}
////		} else if (action == CartAction.Sub.getAction()) {
////			if (minSell == 0) {
////				quantity = (quantity - 1) == 1 ? 1 : (quantity - 1);
////			} else {
////				quantity = (quantity - minSell) < minSell ? minSell : (quantity - minSell);
////				quantity = getQuantityMinSell(quantity, minSell);
////			}
////		} else if (action == CartAction.Set.getAction()){
////			if (minSell != 0) {
////				quantity = getQuantityMinSell(setQuantity, minSell);
////			}
////		}
////		return quantity;
////	}
//	
//	private int getQuantityByAction(int action, int minSell, int setQuantity, int quantity) {
//		// 判断最小购买数
//		if (action == CartAction.Add.getAction()) {
//			quantity = quantity + setQuantity;
//		} else if (action == CartAction.Sub.getAction()) {
//			quantity = (quantity - setQuantity) < 1 ? 1 : (quantity - 1);
//		} else if (action == CartAction.Set.getAction()) {
//			quantity = setQuantity;
//		}
//		return quantity > minSell ? quantity : minSell;
//	}
//	
////	private int getQuantityMinSell(int quantity, int minSell) {
////		int mod = quantity % minSell;
////		if (mod != 0) {
////			//199  100
////			quantity = (int) ((Math.floor(quantity / minSell) + 1) * minSell);
////		}
////		return quantity;
////	}
//	
//	/**
//	 * 根据购物车删除购物车产品
//	 */
//	@Transactional(readOnly = false)
//	public void deleteCartById(Long cartId){
//		cartDao.deleteById(cartId);
//	}
//	
//	/**
//	 * 根据购物车Ids批量删除购物车产品
//	 */
//	@Transactional(readOnly = false)
//	public void deleteCartById(Long[] ids){
//		cartDao.deleteByIds(ids);
//	}
//	
//	/**
//	 * 获取规格名称
//	 * @param spec
//	 * @return
//	 */
//	private String getSpecName(Spec spec) {
//		String specName = "";
//		if (StringUtils.isNotBlank(spec.getSpecType())) 
//			specName += ("类型: " + spec.getSpecType() + ",");
//		if (StringUtils.isNotBlank(spec.getSpecColor())) 
//			specName += ("颜色: " + spec.getSpecColor() + ",");
//		if (StringUtils.isNotBlank(spec.getSpecSize())) 
//			specName += ("尺寸: " + spec.getSpecSize() + ",");
//		if (StringUtils.isNotBlank(specName)) {
//			specName = specName.substring(0, specName.length() - 1);
//		}
//		return specName;
//	}
//
//	/**
//	 * 2. 根据sessionId或accountId获得购物车列表
//	 * 
//	 * @param sessionId
//	 * @return
//	 */
//	public List<Cart> findCart(String sessionId, Long accountId) {
//		return cartDao.findBySessionIdOrAccountId(sessionId, accountId);
//	}
//	
//	/**
//	 * 3. 根据sessionId或accountId删除购物车列表
//	 * 
//	 * @param sessionId
//	 * @param accountId
//	 */
//	@Transactional(readOnly = false)
//	public void deleteCart(String sessionId, Long accountId) {
//		cartDao.deleteBySessionIdOrAccountId(sessionId, accountId);
//	}
//	
//	/**
//	 * 4. 根据sessionId或accountId  以及ids获得购物车列表
//	 * 
//	 * @param sessionId
//	 * @param accountId
//	 * @param ids
//	 * @return
//	 */
//	public List<Cart> findCart(String sessionId, Long accountId, Long[] ids) {
//		return cartDao.findBySessionIdOrAccountIdAndIds(sessionId, accountId, ids);
//	}
//	
//	/**
//	 * 5. 根据ids更新购物车状态
//	 * 
//	 * @param ids
//	 * @param status
//	 */
//	@Transactional(readOnly = false)
//	public void updateCartStatus(String ids, String status) {
//		Long[] idss = (Long[]) ConvertUtils.convert(ids.split(","), Long.class);
//		cartDao.updateStatus(idss, status);
//	}
//	
//	/**
//	 * 6. 更新accountId
//	 * 
//	 * @param sessionId
//	 * @param accountId
//	 */
//	@Transactional(readOnly = false)
//	public void updateCart(String sessionId, Long accountId) {
//		cartDao.updateAccountId(sessionId, accountId);
//	}
//	
//	// --BookingOrder
//	/**
//	 * 1. 保存BookingOrder
//	 * 
//	 * @param bookingOrder
//	 */
//	@Transactional(readOnly = false)
//	public void saveBookingOrder(BookingOrder bookingOrder) {
//		Date systemTime = new Date();
//		long num = spyMemcachedClient.incr(BookingOrder.getKey(), 1, 1);
//		String no = DateUtils.formatDate(systemTime, "yyyyMMddHHmmss") + String.format("%06d", num);
//		bookingOrder.setNo(no);
//		bookingOrderDao.insert(bookingOrder);
//	}
//	
//	/**
//	 * 2. 根据id删除BookingOrder
//	 * 
//	 * @param bookingOrder
//	 */
//	@Transactional(readOnly = false)
//	public void deleteBookingOrder(Long id) {
//		bookingOrderDao.deleteById(id);
//	}
//	
//	/**
//	 * 3. 保存BookingOrder
//	 * 
//	 * @param bookingOrder
//	 */
//	@Transactional(readOnly = false)
//	public void updateBookingOrder(BookingOrder bookingOrder) {
//		bookingOrderDao.update(bookingOrder);
//	}
//	
//	/**
//	 * 4. 取消预约单
//	 */
//	@Transactional(readOnly = false)
//	public void cancelBookingOrder(Long bookingOrderId, String reasonId, String reason, String reasonDesc, Account account) {
//		BookingOrder bookingOrder = bookingOrderDao.selectById(bookingOrderId);
//		bookingOrder.setReasonId(reasonId);
//		bookingOrder.setReason(reason);
//		bookingOrder.setRemark(reasonDesc);
//		bookingOrder.setStatus(BookingOrder.STATUS_CANCEL);
//		bookingOrderDao.update(bookingOrder);
//		BookingOrderLog bookingOrderLog = new BookingOrderLog();
//		bookingOrderLog.setBookingOrderId(bookingOrderId);
//		bookingOrderLog.setCreateTime(new Date());
//		bookingOrderLog.setAction(BookingOrderLog.ACTION_CANCEL);
//		bookingOrderLog.setDescription("【取消预约单】用户取消预约单，取消原因：" + reason + "(" + reasonId + ")；" + reasonDesc);
//		bookingOrderLog.setOperateId(account.getId());
//		bookingOrderLog.setOperateType(BookingOrderLog.OPERATE_TYPE_FRONT);
//		bookingOrderLog.setOperateName(account.getNickname());
//		bookingOrderLogDao.insert(bookingOrderLog);
//	}
//	
//	/**
//	 * 5. 更新BookingOrder状态
//	 */
//	@Transactional(readOnly = false)
//	public void updateBookingOrderStatus(Long id, int status) {
//		bookingOrderDao.updateStatus(id, status);
//	}
//	
//	// --BookingOrderLog
//	/**
//	 * 1. 保存BookingOrderLog
//	 * 
//	 * @param bookingOrderLog
//	 */
//	@Transactional(readOnly = false)
//	public void saveBookingOrderLog(BookingOrderLog bookingOrderLog) {
//		bookingOrderLogDao.insert(bookingOrderLog);
//	}
//	
//	/**
//	 * 2. 根据id删除BookingOrderLog
//	 * 
//	 * @param bookingOrderLog
//	 */
//	@Transactional(readOnly = false)
//	public void deleteBookingOrderLog(Long id) {
//		bookingOrderLogDao.deleteById(id);
//	}
//	
//	/**
//	 * 3. 保存BookingOrderLog
//	 * 
//	 * @param bookingOrderLog
//	 */
//	@Transactional(readOnly = false)
//	public void updateBookingOrderLog(BookingOrderLog bookingOrderLog) {
//		bookingOrderLogDao.update(bookingOrderLog);
//	}
//	
//	// --PayLog
//	/**
//	 *1. 保存支付宝流水
//	 * 
//	 * @param 
//	 */
//	@Transactional(readOnly = false)
//	public void savePayLog(PayLog payLog) {
//		payLogDao.insert(payLog);
//	}
//	
//	
//}
