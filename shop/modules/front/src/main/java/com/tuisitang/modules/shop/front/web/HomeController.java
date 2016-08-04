package com.tuisitang.modules.shop.front.web;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tuisitang.common.bo.ErrorMsg;
import com.tuisitang.common.bo.IpInfo;
import com.tuisitang.common.bo.LoginType;
import com.tuisitang.common.bo.NotifyType;
import com.tuisitang.common.bo.SmsPlatformType;
import com.tuisitang.common.bo.Status;
import com.tuisitang.common.cache.memcached.MemcachedObjectType;
import com.tuisitang.common.cache.memcached.SpyMemcachedClient;
import com.tuisitang.common.servlet.ValidateCodeServlet;
import com.tuisitang.common.sms.SMSClient;
import com.tuisitang.common.sms.SMSClientFactory;
import com.tuisitang.common.utils.AddressUtils;
import com.tuisitang.common.utils.Encodes;
import com.tuisitang.common.utils.FreeMarkers;
import com.tuisitang.common.utils.HttpServletRequestUtils;
import com.tuisitang.common.utils.Identities;
import com.tuisitang.common.utils.SpringContextHolder;
import com.tuisitang.common.utils.ValidateUtils;
import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.entity.Activity;
import com.tuisitang.modules.shop.entity.Area;
import com.tuisitang.modules.shop.entity.BookingOrder;
import com.tuisitang.modules.shop.entity.Catalog;
import com.tuisitang.modules.shop.entity.IndexFloor;
import com.tuisitang.modules.shop.entity.IndexImg;
import com.tuisitang.modules.shop.entity.NotifyTemplate;
import com.tuisitang.modules.shop.entity.Product;
import com.tuisitang.modules.shop.entity.Sms;
import com.tuisitang.modules.shop.front.event.BookingOrderCreateEvent;
import com.tuisitang.modules.shop.front.event.SmsEvent;
import com.tuisitang.modules.shop.front.security.CaptchaException;
import com.tuisitang.modules.shop.front.security.MobileCaptchaException;
import com.tuisitang.modules.shop.front.security.UsernamePasswordToken;
import com.tuisitang.modules.shop.front.service.ProductFrontService;
import com.tuisitang.modules.shop.front.service.SystemFrontService;
import com.tuisitang.modules.shop.front.utils.AccountUtils;
import com.tuisitang.modules.shop.service.CommonService;
import com.tuisitang.modules.shop.service.OrderService;
import com.tuisitang.modules.shop.utils.Global;

import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;

/**    
 * @{#} HomeController.java   
 * 
 * 1. 首页 /
 * 2. 注册 /register          手机号(mobile)/验证码(validateCode)/短信验证码(mobileVerify)/密码/确认密码
 * 3. 登录 /login             已验证手机、邮箱、用户名/密码
 * 4. 查询 /search            关键字
 * 5. 商品列表 /list           product
 * 6. 商品详情 /detail
 * 7. 团购    /group /tuan
 * 8. 积分商城 /jf /jifen
 * 9. 购物车 /cart 
 * 10.订单确认 /order/confirm 
 * 11.付款确认 /pay/confirm 
 * 12.个人中心 - 我的
 * 12.1 我的订单 /i/order
 * 12.2 我的收藏 /i/favorite
 * 12.3 我的订阅 /i/subscribe
 * 12.4 我的会员等级 /i/membership
 * 13.个人中心 - 个人账户 /i/account
 * 13.1 我的余额 /i/account/balance
 * 13.2 设置账户信息 /i/account/settings
 * 13.3 修改密码 /i/account/password
 * 13.4 绑定手机 /i/account/mobile/bind  /i/account/mobile/bind/change
 * 13.5 收货地址 /i/account/addresses
 * 14.帮助中心 /help/
 * 
 * 首页Controller
 * 
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@Controller
public class HomeController extends BaseController {
	
	@Autowired
	private ProductFrontService productFrontService;
	@Autowired
	private SystemFrontService systemFrontService;
	@Autowired
	private SpyMemcachedClient memcachedClient;
	@Autowired
	private CommonService commonService;
	@Autowired
	private OrderService orderService;
	
	private static final int INDEX_GROUP_PRE_SIZE = 3;
	private static final int INDEX_GROUP_SIZE = 5;

	/**
	 * 1. 首页
	 */
	@RequestMapping(value = { "", "/", "home", "index" }, method = RequestMethod.GET)
	public String home(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("home");
		//首页导航图片
		List<IndexImg> indexImgList = productFrontService.getIndexImgByType(IndexImg.INDEX_IMG_TYPE_MALL);
		model.addAttribute("indexImgList", indexImgList);
		
		//首页目录数据
		List<Catalog> catalogs = getCatalogCacheList();
		for (Catalog catalog : catalogs) {
			if ("yishiqu".equals(catalog.getCode())) {
				model.addAttribute("catalog1", catalog.getChildren());
			}else if("qiandao/zhanshiqu".equals(catalog.getCode())) {
				model.addAttribute("catalog2", catalog.getChildren());
			}else if("tesetaoxiquyu".equals(catalog.getCode())) {
				model.addAttribute("catalog3", catalog.getChildren());
			}else if("xiaohaoqicai".equals(catalog.getCode())) {
				model.addAttribute("catalog4", catalog.getChildren());
			}else if("gongyizhuangshipinqu".equals(catalog.getCode())) {
				model.addAttribute("catalog5", catalog.getChildren());
			}
//			else if("hunliziliao".equals(catalog.getCode())) {
//				model.addAttribute("catalog6", catalog.getChildren());
//			}
		}
		
		//		model.addAttribute("catalogList", catalogList);
//		//加载热门推荐商品
//		List<Product> hotRecommendProduct = productFrontService.getHotRecommendProduct(null);
//		//加载热门商品
//		List<Product> hotProduct = productFrontService.getHotProduct();
//		//加载特价商品
//		List<Product> saleProduct = productFrontService.getSaleProduct();
//		//加载最新商品
//		List<Product> newProduct = productFrontService.getNewProduct();
		//首页产品数据
		List<IndexFloor> indexFloor1 = productFrontService.getIndexFloorByFloor(IndexFloor.FLOOR_1);
		List<IndexFloor> indexFloor2 = productFrontService.getIndexFloorByFloor(IndexFloor.FLOOR_2);
		List<IndexFloor> indexFloor3 = productFrontService.getIndexFloorByFloor(IndexFloor.FLOOR_3);
		List<IndexFloor> indexFloor4 = productFrontService.getIndexFloorByFloor(IndexFloor.FLOOR_4);
		List<IndexFloor> indexFloor5 = productFrontService.getIndexFloorByFloor(IndexFloor.FLOOR_5);
//		List<IndexFloor> indexFloor6 = productFrontService.getIndexFloorByFloor(IndexFloor.FLOOR_6);
		
		model.addAttribute("indexFloor1", indexFloor1);
		model.addAttribute("indexFloor2", indexFloor2);
		model.addAttribute("indexFloor3", indexFloor3);
		model.addAttribute("indexFloor4", indexFloor4);
		model.addAttribute("indexFloor5", indexFloor5);
//		model.addAttribute("indexFloor6", indexFloor6);
		
		
		
		//加载团购预告商品
		List<Product> groupProduct = productFrontService.getActivityProductByType(Activity.activity_activityType_t);
		List<Product> preProduct = getIndexPreGroupProduct(groupProduct);
		//加载团购中商品
		List<Product> groupIngProduct = getIndexGroupProduct(groupProduct);
		
		
//		model.addAttribute("hotRecommendProduct", hotRecommendProduct);
//		model.addAttribute("hotProduct", hotProduct);
//		model.addAttribute("saleProduct", saleProduct);
//		model.addAttribute("newProduct", newProduct);

		model.addAttribute("preProduct", preProduct);
		model.addAttribute("groupIngProduct", groupIngProduct);
		
		return "modules/front/index";
	}

	/**
	 * 获取首页团购预告产品 3个
	 */
	public List<Product> getIndexPreGroupProduct(List<Product> groupProduct) {
		List<Product> preProduct = Lists.newArrayList();
		for (Product product : groupProduct) {
			Activity activity = product.getActivity();
			if (activity != null && activity.getStartDate().after(new Date())) {
				preProduct.add(product);
			}
			if (preProduct.size() == INDEX_GROUP_PRE_SIZE) {
				break;
			}
		}
		
		return preProduct;
	}
	
	/**
	 * 获取首页团购中产品 5个
	 */
	public List<Product> getIndexGroupProduct(List<Product> groupProduct) {
		List<Product> groupIngProduct = Lists.newArrayList();
		for (Product product : groupProduct) {
			Activity activity = product.getActivity();
			if (activity != null && activity.getStartDate().before(new Date()) && activity.getEndDate().after(new Date())) {
				groupIngProduct.add(product);
			}
			if (groupIngProduct.size() == INDEX_GROUP_SIZE) {
				break;
			}
		}
		
		return groupIngProduct;
	}

	/**
	 * 2.1 登录
	 */
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) {
		Account account = AccountUtils.getAccount();
		// 如果已经登录，则跳转到管理首页
		if (account.getId() != null) {
			return "redirect:/";
		}
		String openid = request.getParameter("openid");
		if (!StringUtils.isBlank(openid)) {
			model.addAttribute("openid", openid);
		} else {
			model.addAttribute("openid", "");
		}
		return "modules/front/login";
	}
	
	/**
	 * 2.2 登录失败，真正登录的POST请求由Filter完成
	 */
	@RequestMapping(value = "ajaxLogin", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxLogin(@RequestParam("username") String username, 
			@RequestParam("password") String password,
			@RequestParam("loginType") String loginType,
			@RequestParam("validateCode") String validateCode,
			@RequestParam("mobileVerify") String mobileVerify,
			@RequestParam(required=false,value="rememberMe") boolean rememberMe,
			@RequestParam(required=false,value="openid") String openid,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("username {}, password {}, loginType {}, validateCode {}, mobileVerify {}",
				username, password, loginType, validateCode, mobileVerify);
		String code = (String) request.getSession().getAttribute(ValidateCodeServlet.VALIDATE_CODE);
//		String openid = request.getParameter("openid");
//		if (StringUtils.isBlank(openid)) {
//			openid = (String) request.getSession().getAttribute(Global.SESSION_WECHAT_OPENID);
//		}
//		logger.info("openid {}", openid);
		logger.info("code {}", code);
		Account account = AccountUtils.getAccount();
		if (account.getId() != null) {
			return buildReturnMap(Status.Success, ErrorMsg.NULL);
		}
		Subject subject = SecurityUtils.getSubject();
		if (!subject.isAuthenticated()) {
			String host = AddressUtils.getHost(request);
			UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, loginType,
					validateCode, mobileVerify, false, openid);
			token.setRememberMe(rememberMe);
			try {
				Session session = SecurityUtils.getSubject().getSession();
				session.setAttribute(ValidateCodeServlet.VALIDATE_CODE, code);
				subject.login(token);
				return buildReturnMap(Status.Success, ErrorMsg.NULL);
			} catch (UnknownAccountException e) {
				e.printStackTrace();
				logger.error("账号错误 : {}", e.getMessage());
				return buildReturnMap(Status.Failure, ErrorMsg.AuthenticationUnknownAccountException);
			} catch (IncorrectCredentialsException e) {
				e.printStackTrace();
				logger.error("密码错误 : {}", e.getMessage());
				return buildReturnMap(Status.Failure, ErrorMsg.AuthenticationIncorrectCredentialsException);
			} catch (LockedAccountException e) {
				e.printStackTrace();
				logger.error("账号已被锁定，请与管理员联系 : {}", e.getMessage());
				return buildReturnMap(Status.Failure, ErrorMsg.AuthenticationLockedAccountException);
			} catch(CaptchaException e) {
				e.printStackTrace();
				logger.error("验证码错误 {}", e.getMessage());
				return buildReturnMap(Status.Failure, ErrorMsg.AuthenticationCaptchaException);
			} catch(MobileCaptchaException e) {
				e.printStackTrace();
				logger.error("手机验证码错误 {}", e.getMessage());
				return buildReturnMap(Status.Failure, ErrorMsg.AuthenticationMobileCaptchaException);
			} catch (AuthenticationException e) {
				e.printStackTrace();
				logger.error("你还未授权: {}", e.getMessage());
				return buildReturnMap(Status.Failure, ErrorMsg.AuthenticationLockedAccountException);
			}
		} else {
			return buildReturnMap(Status.Success, ErrorMsg.NULL);
		}
	}
	
	/**
	 * 3.1 注册
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "regist", method = RequestMethod.GET)
	public String regist(HttpServletRequest request, HttpServletResponse response, Model model) {
		Account account = AccountUtils.getAccount();
		// 如果已经登录，则跳转到管理首页
		if (account.getId() != null) {
			return "redirect:/";
		}
		String openid = request.getParameter("openid");
		if (!StringUtils.isBlank(openid)) {
			model.addAttribute("openid", openid);
		} else {
			model.addAttribute("openid", "");
		}
		Map<String, String[]> parameterMap = request.getParameterMap();
		for (Entry<String, String[]> entry : parameterMap.entrySet()) {
			String key = entry.getKey();
			String[] value = entry.getValue();
			logger.info("key {}, value {}", key, value);
			if (value == null || value.length == 0 || (value.length == 1 && StringUtils.isBlank(value[0]))) {
				try {
					//from_code=EHPAOB&from_mobile=18080808080&channel=
					// ZnJvbV9jb2RlPVoxSk84NCZmcm9tX21vYmlsZT0xODA4MDg2MDg3NyZjaGFubmVsPWludml0ZV9mcm9tX3dlYl9jb3B5
					String s = Identities.decodeBase64(key);
					logger.info("{}", s);
					String[] params = s.split("&");
					String fromCode = null;
					String fromMobile = null;
					String channel = null;
					if (params.length == 3) {
						for (int i = 0; i < params.length; i++) {
							String[] ss = params[i].split("=");
							if (ss.length == 2) {
								if ("from_code".equals(ss[0])) {
									fromCode = ss[1];
								} else if ("from_mobile".equals(ss[0])) {
									fromMobile = ss[1]; 	
								} else if ("channel".equals(ss[0])) {
									channel = ss[1]; 	
								}
							}
						}
					}
					logger.info("fromCode {}, fromMobile {}, channel {}", fromCode, fromMobile, channel);
					if (!StringUtils.isBlank(fromCode) && !StringUtils.isBlank(fromMobile) && !StringUtils.isBlank(channel)) {
						Account fromAccount = systemFrontService.getAccountByMobile(fromMobile);
						logger.info("{}", fromAccount);
						if (fromAccount != null && fromCode.equals(fromAccount.getInviteCode())) {
							model.addAttribute("fromId", fromAccount.getId());
							model.addAttribute("fromCode", fromCode);
							model.addAttribute("fromMobile", fromMobile);
							model.addAttribute("channel", channel);
						}
					}
				} catch (Exception e) {
					logger.warn("{}", e);
				}
			}
		}
		return "modules/front/regist";
	}
	
	/**
	 * 3.2 注册 - 提交
	 */
	@RequestMapping(value = "regist", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> regist(@RequestParam("mobile") String mobile,
			@RequestParam("password") String password,
			@RequestParam(required = false, value = "verifyCode") String verifyCode,
			@RequestParam("mobileVerify") String mobileVerify,
			@RequestParam(required = false, value = "fromCode") String fromCode,
			@RequestParam(required = false, value = "fromMobile") String fromMobile,
			@RequestParam(required = false, value = "channel") String channel,
			@RequestParam(required=false,value="openid") String openid,
			@RequestParam(required=false,value="name") String name,
			HttpServletRequest request, HttpServletResponse response,
			Model model) {
		Date systemTime = new Date(System.currentTimeMillis());
		logger.info("mobile {} \npassword {} \nverifyCode {} \nmobileVerify {}",
				mobile, password, verifyCode, mobileVerify);
		
		String code = (String) request.getSession().getAttribute(ValidateCodeServlet.VALIDATE_CODE);
//		String openid = request.getParameter("openid");
//		if (StringUtils.isBlank(openid)) {
//			openid = (String) request.getSession().getAttribute(Global.SESSION_WECHAT_OPENID);
//		}
//		logger.info("openid {}", openid);
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		logger.info("DeviceType {}, isMobile {}", userAgent.getOperatingSystem().getDeviceType(), 
				DeviceType.MOBILE.equals(userAgent.getOperatingSystem().getDeviceType()) );
		if (StringUtils.isBlank(mobile) || !ValidateUtils.isMobile(mobile)){
			logger.error("{}", ErrorMsg.MobileIsIncorrect);
			return buildReturnMap(Status.Failure, ErrorMsg.MobileIsIncorrect);
		} else if (systemFrontService.getAccountByMobile(mobile) != null) {
			logger.error("{}", ErrorMsg.MobileIsExist);
			return buildReturnMap(Status.Failure, ErrorMsg.MobileIsExist);
		} else if (!DeviceType.MOBILE.equals(userAgent.getOperatingSystem().getDeviceType()) 
		&& (StringUtils.isBlank(verifyCode) || !verifyCode.toUpperCase().equals(code))) {
			logger.error("{}", ErrorMsg.VerifyCodeIsIncorrect);
			return buildReturnMap(Status.Failure, ErrorMsg.VerifyCodeIsIncorrect);
		} else {
			String key = MemcachedObjectType.MCODE.getPrefix() + mobile;
			String mcode = memcachedClient.get(key);// 从缓存中获得验证码
			if (StringUtils.isBlank(mcode) || !mcode.equals(mobileVerify)) {
				logger.error("手机验证码错误.");
				return buildReturnMap(Status.Failure, ErrorMsg.MobileVerifyIsIncorrect);
			} else {
				Account account = new Account();
				account.setMobile(mobile);
				account.setAccount("BXL" + mobile.substring(0, 3) + Identities.toSerialCode(Long.parseLong(mobile.substring(3, 7)), 4) + mobile.substring(mobile.length() - 4));
				account.setNickname(account.getAccount());// Nickname 作为系统唯一表示，用于物流配送
				account.setPassword(SystemFrontService.entryptPassword(password));
				account.setRegeistDate(systemTime);
				account.setOpenId(openid);
				account.setTrueName(name);
				logger.info("fromCode {}, fromMobile {}, channel {}", fromCode, fromMobile, channel);
				if (!StringUtils.isBlank(fromCode) && !StringUtils.isBlank(fromMobile) && !StringUtils.isBlank(channel)) {
					Account fromAccount = systemFrontService.getAccountByMobile(fromMobile);
					if (fromAccount != null && fromCode.equals(fromAccount.getInviteCode())) {
						logger.info("设置Account的邀请人ID {}", fromAccount.getId());
						account.setInviteId(fromAccount.getId());
					}
				}
				systemFrontService.saveAccount(account);
				Subject subject = SecurityUtils.getSubject();
				// 如果用户没有登录，模拟登陆
	        	UsernamePasswordToken token = new UsernamePasswordToken(account.getMobile(), password.toCharArray(), true,
						request.getRemoteHost(), LoginType.Normal.getType(), "", "", true, openid);
				try {
					subject.login(token);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return buildReturnMap(Status.Success, ErrorMsg.NULL);
			}
		}
	}
	
	/**
	 * 4. 获得手机验证码
	 * 接收页面验证码，验证码正确则发送短信验证码
	 */
	@RequestMapping(value = "getMcode", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Map<String, Object> getMcode(@RequestParam("mobile") String mobile, @RequestParam(required = false, value = "verifyCode") String verifyCode, 
			@RequestParam("type") String type, HttpServletRequest request, HttpServletResponse response, Model model) {
		Date systemTime = new Date(System.currentTimeMillis());
		logger.info("mobile {}, verfiCode {}, type {}", mobile, verifyCode, type);
		String code = (String) request.getSession().getAttribute(ValidateCodeServlet.VALIDATE_CODE);
		String platformType = SmsPlatformType.CL.getType();
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		if (StringUtils.isBlank(mobile) || !ValidateUtils.isMobile(mobile)){
			logger.error("{}", ErrorMsg.MobileIsIncorrect);
			return buildReturnMap(Status.Failure, ErrorMsg.MobileIsIncorrect);
		} else if (!DeviceType.MOBILE.equals(userAgent.getOperatingSystem().getDeviceType()) && !NotifyType.Forget.getType().equals(type) && (StringUtils.isBlank(verifyCode) || !verifyCode.toUpperCase().equals(code))){
			logger.error("{}", ErrorMsg.VerifyCodeIsIncorrect);
			return buildReturnMap(Status.Failure, ErrorMsg.VerifyCodeIsIncorrect);
		} else {
			String template = systemFrontService.getNotifyTemplate(NotifyTemplate.TYPE_SMS, type);// 获得手机注册模板
			/**
			String mcode = systemService.getMobileVerify(mobile);// 从缓存中获得验证码
			if (!StringUtils.isBlank(mcode)) {// 如果验证码不为空则说明请求太频繁
				logger.error("验证码错误.");
				return buildReturnMap(Status.Failure, ErrorMsg.MobileIsIncorrect);
			}
			mcode = Identities.randomValidateCode();
			*/
			String mcode = Identities.randomValidateCode();
			systemFrontService.setMobileVerify(mobile, mcode);

			Map<String, String> m = Maps.newHashMap();
			m.put("mcode", mcode);
			String message = FreeMarkers.renderString(template, m);
			try {
				SMSClient client = SMSClientFactory.getClient(platformType);
				String msg = client.sendSMS(mobile, message);
				
				SpringContextHolder.getApplicationContext().publishEvent(new SmsEvent(this, null, mobile, message, type, platformType,
						Sms.STATUS_SUCCESS, msg, systemTime));
				return buildReturnMap(Status.Success, ErrorMsg.NULL);
			} catch (Exception e) {
				e.printStackTrace();
				SpringContextHolder.getApplicationContext().publishEvent(new SmsEvent(this, null, mobile, message, type, platformType,
						Sms.STATUS_EXCEPTION, e.getMessage(), systemTime));
				return buildReturnMap(Status.Success, ErrorMsg.NULL);
			}
		}
	}
	
	/**
	 * 5. 异步验证验证码是否正确
	 */
	@RequestMapping(value = "checkVerifyCode", method = { RequestMethod.POST })
	public @ResponseBody
	Map<String, Object> checkVerifyCode(@RequestParam("verifyCode") String verifyCode, HttpServletRequest request, HttpServletResponse response, Model model) {
		String code = (String) request.getSession().getAttribute(ValidateCodeServlet.VALIDATE_CODE);
		logger.info("checkVerifyCode verfiCode {}, code {}", verifyCode, code);
		if (StringUtils.isBlank(verifyCode) || !verifyCode.toUpperCase().equals(code)){
			logger.error("验证码错误.");
			return buildReturnMap(Status.Failure, ErrorMsg.VerifyCodeIsIncorrect);
		}
		return buildReturnMap(Status.Success, ErrorMsg.NULL);
	}
	
	/**
	 * 6. 异步验证手机号
	 */
	@RequestMapping(value = "checkMobile", method = { RequestMethod.POST })
	public @ResponseBody
	Map<String, Object> checkMobile(@RequestParam("mobile") String mobile, HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("checkMobile mobile {}", mobile);
		if (StringUtils.isBlank(mobile) || !ValidateUtils.isMobile(mobile)) {
			logger.error("{}", ErrorMsg.MobileIsIncorrect);
			return buildReturnMap(Status.Failure, ErrorMsg.MobileIsIncorrect);
		}
		if (systemFrontService.getAccountByMobile(mobile) != null) {
			logger.error("{}", ErrorMsg.MobileIsExist);
			return buildReturnMap(Status.Failure, ErrorMsg.MobileIsExist);
		}
		return buildReturnMap(Status.Success, ErrorMsg.NULL);
	}
	
	/**
	 * 7.1 忘记密码/找回密码
	 */
	@RequestMapping(value = "forget", method = RequestMethod.GET)
	public String forget(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("forget");
		HttpSession session = request.getSession();
		String sign = SystemFrontService.entryptPassword(session.getId());
		logger.info("session.id {}, sign {}", session.getId(), sign);
		session.setAttribute("sign", sign);
		return "modules/front/forget";
	}
	
	/**
	 * 7.2 确认账号
	 */
	@RequestMapping(value = "forget/checkAccount", method = { RequestMethod.POST })
	public @ResponseBody
	Map<String, Object> checkAccount(@RequestParam("mobile") String mobile, @RequestParam("verifyCode") String verifyCode, HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("checkAccount mobile {}", mobile);
		HttpSession session = request.getSession();
		String sign = (String) session.getAttribute("sign");
		logger.info("session.id {}, sign {}, validate {}", session.getId(), sign, SystemFrontService.validatePassword(session.getId(), sign));
		if (StringUtils.isBlank(mobile) || !ValidateUtils.isMobile(mobile)) {
			logger.error("{}", ErrorMsg.MobileIsIncorrect);
			return buildReturnMap(Status.Failure, ErrorMsg.MobileIsIncorrect);
		}
		String code = (String) session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
		logger.info("checkVerifyCode verfiCode {}, code {}", verifyCode, code);
		if (StringUtils.isBlank(verifyCode) || !verifyCode.toUpperCase().equals(code)){
			logger.error("验证码错误.");
			return buildReturnMap(Status.Failure, ErrorMsg.VerifyCodeIsIncorrect);
		}
		if (systemFrontService.getAccountByMobile(mobile) == null) {// 账号不存在
			logger.error("{}", ErrorMsg.AuthenticationUnknownAccountException);
			return buildReturnMap(Status.Failure, ErrorMsg.AuthenticationUnknownAccountException);
		}
		if (StringUtils.isBlank(sign) || !SystemFrontService.validatePassword(session.getId(), sign)) {
			logger.error("{}", ErrorMsg.AuthenticationException);
			return buildReturnMap(Status.Failure, ErrorMsg.AuthenticationException);
		}
		session.setAttribute("sign", SystemFrontService.entryptPassword(mobile));
		return buildReturnMap(Status.Success, ErrorMsg.NULL);
	}
	
	/**
	 * 7.3 验证身份
	 */
	@RequestMapping(value = "forget/auth", method = { RequestMethod.POST })
	public @ResponseBody
	Map<String, Object> auth(@RequestParam("mobile") String mobile, @RequestParam("mobileVerify") String mobileVerify, HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("auth mobile {}", mobile);
		HttpSession session = request.getSession();
		String code = systemFrontService.getMobileVerify(mobile);
		logger.info("mobileVerify {}, {}", mobileVerify, code);
		if (StringUtils.isBlank(mobileVerify) || !mobileVerify.equals(code)) {
			logger.info("{}", ErrorMsg.MobileVerifyIsIncorrect);
			return buildReturnMap(Status.Failure, ErrorMsg.MobileVerifyIsIncorrect);
		}
		String sign = (String) session.getAttribute("sign");// 验证签名
		if (StringUtils.isBlank(sign) || !SystemFrontService.validatePassword(mobile, sign)) {
			logger.error("{}", ErrorMsg.AuthenticationException);
			return buildReturnMap(Status.Failure, ErrorMsg.AuthenticationException);
		}
		sign = Encodes.encodeBase64(sign.getBytes());
		session.setAttribute("sign", sign);
		return buildReturnMap(Status.Success, ErrorMsg.NULL);
	}
	
	/**
	 * 7.4 设置密码
	 */
	@RequestMapping(value = "forget/reset", method = { RequestMethod.POST })
	public @ResponseBody
	Map<String, Object> reset(@RequestParam("mobile") String mobile, @RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("checkMobile mobile {}", mobile);
		if (StringUtils.isBlank(mobile) || !ValidateUtils.isMobile(mobile)) {
			logger.error("{}", ErrorMsg.MobileIsIncorrect);
			return buildReturnMap(Status.Failure, ErrorMsg.MobileIsIncorrect);
		}
		Account account = systemFrontService.getAccountByMobile(mobile);
		if (account == null || account.getId() == null) {
			logger.error("{}", ErrorMsg.AuthenticationUnknownAccountException);
			return buildReturnMap(Status.Failure, ErrorMsg.AuthenticationUnknownAccountException);
		}
		HttpSession session = request.getSession();
		String sign = (String) session.getAttribute("sign");// 验证签名
		sign = new String(Encodes.decodeBase64(sign));
		if (StringUtils.isBlank(sign) || !SystemFrontService.validatePassword(mobile, sign)) {
			logger.error("{}", ErrorMsg.AuthenticationException);
			return buildReturnMap(Status.Failure, ErrorMsg.AuthenticationException);
		}
		systemFrontService.updateAccountPasswordById(account.getId(), password);
		return buildReturnMap(Status.Success, ErrorMsg.NULL);
	}
	
	/**
	 * 8. 手机号是否存在
	 */
	@RequestMapping(value = "existMobile", method = { RequestMethod.POST })
	public @ResponseBody
	Map<String, Object> existMobile(@RequestParam("mobile") String mobile, HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("checkMobile mobile {}", mobile);
		if (StringUtils.isBlank(mobile) || !ValidateUtils.isMobile(mobile)) {
			logger.error("{}", ErrorMsg.MobileIsIncorrect);
			return buildReturnMap(Status.Failure, ErrorMsg.MobileIsIncorrect);
		}
		if (systemFrontService.getAccountByMobile(mobile) == null) {
			logger.error("{}", ErrorMsg.AuthenticationUnknownAccountException);
			return buildReturnMap(Status.Failure, ErrorMsg.AuthenticationUnknownAccountException);
		}
		return buildReturnMap(Status.Success, ErrorMsg.NULL);
	}
	
	/**
	 * 9. 道具商城
	 */
	@RequestMapping("mall")
	public String mall(HttpServletRequest request, HttpServletResponse response, Model model) {
		//道具商城导航图片
		List<IndexImg> mallImgList = productFrontService.getIndexImgByType(IndexImg.MALL_IMG_TYPE_MALL);
		model.addAttribute("mallImgList", mallImgList);
		//加载今日推荐商品
		List<IndexImg> todayRecommendProduct = productFrontService.getIndexImgByType(IndexImg.TODAY_IMG_TYPE_MALL);
		model.addAttribute("todayImgList", todayRecommendProduct);
		//加载热门推荐商品
		List<Product> hotRecommendProduct = productFrontService.getHotRecommendProduct(null);
		//加载热门商品
		List<Product> hotProduct = productFrontService.getHotProduct();
		//加载特价商品
		List<Product> saleProduct = productFrontService.getSaleProduct();
		//加载最新商品
		List<Product> newProduct = productFrontService.getNewProduct();
		
		model.addAttribute("hotRecommendProduct", hotRecommendProduct);
		model.addAttribute("hotProduct", hotProduct);
		model.addAttribute("saleProduct", saleProduct);
		model.addAttribute("newProduct", newProduct);

		return "modules/front/mall";
	}
	
//	/**
//	 * 10. 商品检索
//	 */
//	@RequestMapping("search")
//	public String search(@RequestParam("k") String key, HttpServletRequest request, HttpServletResponse response, Model model) {
//		// ProductSearchEvent
//		return "modules/front/product/productList";
//	}
	
	/**
	 * 10.1 预约/预订
	 */
	@RequestMapping(value = "booking", method = { RequestMethod.GET })
	public String booking(HttpServletRequest request, HttpServletResponse response, Model model) {
		Account account = AccountUtils.getAccount();
		String ip = AddressUtils.getHost(request);
//		if ("127.0.0.1".equals(ip) || "localhost".equals(ip))
//			ip = "182.150.21.119";
		IpInfo ipInfo = AddressUtils.getIpInfo(ip);
		logger.info("ip {}, ipInfo {}", ip, ipInfo);
		List<Area> provinces = commonService.findProvinces();
		Area province = new Area();
		Area city = new Area();
		model.addAttribute("provinces", provinces);
		if (ipInfo != null && ipInfo.getRegionId() != null) {
			for (Area p : provinces) {
				if (p.getCode().equals(ipInfo.getRegionId().toString())) {
					province = p;
					break;
				}
			}
			if (province != null && province.getId() != null) {
				List<Area> cities = commonService.findCities(province.getId());
				model.addAttribute("cities", cities);
				if (ipInfo.getCityId() != null) {
					for (Area c : cities) {
						if (c.getCode().equals(ipInfo.getCityId().toString())) {
							city = c;
							break;
						}
					}
					if (city != null && city.getId() != null) {
						List<Area> counties = commonService.findCities(city.getId());
						model.addAttribute("counties", counties);
					}
				}
				
			}
		}
		model.addAttribute("baList", commonService.findBookingArea());
		model.addAttribute("province", province);
		model.addAttribute("city", city);
		model.addAttribute("account", account);
		return "modules/front/booking";
	}
	
	/**
	 * 10.2 预约/预订 保存
	 */
	@RequestMapping(value = "booking", method = { RequestMethod.POST })
	public @ResponseBody Map<String, Object> booking(@Param("name") String name, @Param("mobile") String mobile,
			@Param("verifyCode") String verifyCode, @Param("mobileVerify") String mobileVerify,
			@Param("provinceId") Long provinceId, @Param("provinceName") String provinceName,
			@Param("cityId") Long cityId, @Param("cityName") String cityName,
			@Param("countyId") Long countyId, @Param("countyName") String countyName,
			@Param("location") String location, @Param("address") String address, 
			@Param("time") String time, @Param("demand") String demand,
			HttpServletRequest request, HttpServletResponse response,
			Model model, RedirectAttributes redirectAttributes) {
		Date systemTime = new Date();
		Subject subject = SecurityUtils.getSubject();
		logger.info("name {}\nmobile {}\nverifyCode {}\nmobileVerify {}\n provinceId {}\n provinceName {}\n cityId {}\n cityName {}\n countyId {}\n countyName {}\n location {}\n address {}\n demand {}\ntime {}",
				name, mobile, verifyCode, mobileVerify, provinceId, provinceName, cityId, cityName, countyId, countyName, location,
				address, demand, time);
		String code = (String) request.getSession().getAttribute(ValidateCodeServlet.VALIDATE_CODE);
		if (!ValidateUtils.isMobile(mobile)) {
			logger.error("手机号错误");
//			addMessage(redirectAttributes, ErrorMsg.MobileIsIncorrect.getErrCode() + "");
//			return "redirect:/booking";
			return buildReturnMap(Status.Failure, ErrorMsg.MobileIsIncorrect);
		} 
		if (StringUtils.isBlank(verifyCode) || !verifyCode.toUpperCase().equals(code)) {
			logger.error("{}", ErrorMsg.VerifyCodeIsIncorrect);
//			addMessage(redirectAttributes, ErrorMsg.VerifyCodeIsIncorrect.getErrCode() + "");
//			return "redirect:/booking";
			return buildReturnMap(Status.Failure, ErrorMsg.VerifyCodeIsIncorrect);
		} 
		String key = MemcachedObjectType.MCODE.getPrefix() + mobile;
		String mcode = memcachedClient.get(key);// 从缓存中获得验证码
		if (StringUtils.isBlank(mcode) || StringUtils.isBlank(mobileVerify) || !mobileVerify.equals(memcachedClient.get(MemcachedObjectType.MCODE.getPrefix() + mobile))) {
			logger.error("手机验证码错误.");
//			addMessage(redirectAttributes, ErrorMsg.MobileVerifyIsIncorrect.getErrCode() + "");
//			return "redirect:/booking";
			return buildReturnMap(Status.Failure, ErrorMsg.MobileVerifyIsIncorrect);
		} 
		String sessionId = AccountUtils.getSessionId(request);
		Account account = AccountUtils.getAccount();
		if (account == null || account.getId() == null) {
			account = systemFrontService.getAccountByMobile(mobile);
			if (account == null) {
				String password = Identities.randomBase62(6);
				account = new Account();
				account.setMobile(mobile);
				account.setAccount(mobile);
				account.setPassword(SystemFrontService.entryptPassword(password));
				account.setRegeistDate(systemTime);
				account.setTrueName(name);
				systemFrontService.saveAccount(account);
				
				String type = NotifyType.BookingNewAccount.getType();
				String platformType = SmsPlatformType.CL.getType();
				String template = systemFrontService.getNotifyTemplate(NotifyTemplate.TYPE_SMS, type);// 获得手机注册模板

				Map<String, String> m = Maps.newHashMap();
				m.put("username", mobile);
				m.put("password", password);
				String message = FreeMarkers.renderString(template, m);
				try {
					SMSClient client = SMSClientFactory.getClient(platformType);
					String msg = client.sendSMS(mobile, message);
					
					SpringContextHolder.getApplicationContext().publishEvent(new SmsEvent(this, null, mobile, message, type, platformType,
							Sms.STATUS_SUCCESS, msg, systemTime));
				} catch (Exception e) {
					e.printStackTrace();
					SpringContextHolder.getApplicationContext().publishEvent(new SmsEvent(this, null, mobile, message, type, platformType,
							Sms.STATUS_EXCEPTION, e.getMessage(), systemTime));
				}
			}
		}
		double longitude = Double.parseDouble(location.split(",")[1]);
		double latitude = Double.parseDouble(location.split(",")[0]);
		Date bookingTime = com.tuisitang.common.utils.DateUtils.parseDate(time);
		BookingOrder bookingOrder = new BookingOrder(sessionId,
				account.getId(), name, mobile, systemTime, bookingTime, provinceId,
				provinceName, cityId, cityName, countyId, countyName, address,
				longitude, latitude, demand, BookingOrder.STATUS_NEW);
		orderService.saveBookingOrder(bookingOrder);
		
		if (!subject.isAuthenticated()) {
			// 如果用户没有登录，模拟登陆
			String openid = request.getParameter("openid");
			if (StringUtils.isBlank(openid)) {
				openid = (String) request.getSession().getAttribute(Global.SESSION_WECHAT_OPENID);
			}
	    	UsernamePasswordToken token = new UsernamePasswordToken(account.getMobile(), "".toCharArray(), true,
					request.getRemoteHost(), LoginType.Auto.getType(), "", "", true, openid);
			try {
				subject.login(token);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		publishEvent(new BookingOrderCreateEvent(this, account, bookingOrder, HttpServletRequestUtils.getHttpServletRequestMap(request)));
//		return "modules/front/booking-success";
		return buildReturnMap(Status.Success, ErrorMsg.NULL);
	}
	
	/**
	 * 10.3 预约/预订成功
	 */
	@RequestMapping(value = "booking/success", method = { RequestMethod.GET })
	public String bookingSuccess(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "modules/front/booking-success";
	}
	
	/**
	 * 11.1 绑定
	 */
	@RequestMapping(value = "bind", method = RequestMethod.GET)
	public String bind(HttpServletRequest request, HttpServletResponse response, Model model) {
//		Account account = AccountUtils.getAccount();
//		// 如果已经登录，则跳转到管理首页
//		if (account.getId() != null) {
//			return "redirect:/";
//		}
		String openid = request.getParameter("openid");
		if (StringUtils.isBlank(openid)) {
			openid = (String) request.getSession().getAttribute(Global.SESSION_WECHAT_OPENID);
		}
		logger.info("openid {}", openid);
		model.addAttribute("openid", openid);
		return "mobile/front/bind";
	}
	
	/**
	 * 11.2 绑定
	 */
	@RequestMapping(value = "bind", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> bind(@RequestParam("username") String username, 
			@RequestParam("password") String password,
			@RequestParam("loginType") String loginType,
			@RequestParam("validateCode") String validateCode,
			@RequestParam("mobileVerify") String mobileVerify,
			@RequestParam("openid") String openid,
			@RequestParam(required=false,value="rememberMe") boolean rememberMe,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("username {}, password {}, loginType {}, validateCode {}, mobileVerify {}, openid {}",
				username, password, loginType, validateCode, mobileVerify, openid);
		String code = (String) request.getSession().getAttribute(ValidateCodeServlet.VALIDATE_CODE);
		logger.info("code {}", code);
		Account account = AccountUtils.getAccount();
		if (account.getId() != null) {
			return buildReturnMap(Status.Success, ErrorMsg.NULL);
		} 
		Subject subject = SecurityUtils.getSubject();
		if (!subject.isAuthenticated()) {
			String host = AddressUtils.getHost(request);
			UsernamePasswordToken token = new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, loginType,
					validateCode, mobileVerify, false, openid);
			token.setRememberMe(rememberMe);
			try {
				Session session = SecurityUtils.getSubject().getSession();
				session.setAttribute(ValidateCodeServlet.VALIDATE_CODE, code);
				subject.login(token);
				return buildReturnMap(Status.Success, ErrorMsg.NULL);
			} catch (UnknownAccountException e) {
				e.printStackTrace();
				logger.error("账号错误 : {}", e.getMessage());
				return buildReturnMap(Status.Failure, ErrorMsg.AuthenticationUnknownAccountException);
			} catch (IncorrectCredentialsException e) {
				e.printStackTrace();
				logger.error("密码错误 : {}", e.getMessage());
				return buildReturnMap(Status.Failure, ErrorMsg.AuthenticationIncorrectCredentialsException);
			} catch (LockedAccountException e) {
				e.printStackTrace();
				logger.error("账号已被锁定，请与管理员联系 : {}", e.getMessage());
				return buildReturnMap(Status.Failure, ErrorMsg.AuthenticationLockedAccountException);
			} catch(CaptchaException e) {
				e.printStackTrace();
				logger.error("验证码错误 {}", e.getMessage());
				return buildReturnMap(Status.Failure, ErrorMsg.AuthenticationCaptchaException);
			} catch(MobileCaptchaException e) {
				e.printStackTrace();
				logger.error("手机验证码错误 {}", e.getMessage());
				return buildReturnMap(Status.Failure, ErrorMsg.AuthenticationMobileCaptchaException);
			} catch (AuthenticationException e) {
				e.printStackTrace();
				logger.error("你还未授权: {}", e.getMessage());
				return buildReturnMap(Status.Failure, ErrorMsg.AuthenticationLockedAccountException);
			}
		} else {
			return buildReturnMap(Status.Success, ErrorMsg.NULL);
		}
	}
	
	/**
	 * 专场special
	 * 
	 * 12.1 零利专场
	 */
	@RequestMapping("special/zero")
	public String zero(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<IndexImg> mallImgList = productFrontService.getIndexImgByType(IndexImg.SPECIAL_ZERO_IMG_TYPE_MALL);
		model.addAttribute("mallImgList", mallImgList);
		
		List<Product> list = productFrontService.getActivityProductByType(Activity.activity_activityType_c);
		
		model.addAttribute("list", list);

		return "modules/front/special/zero";
	}
	
	/**
	 * 99. 测试
	 */
	@RequestMapping(value = { "test" }, method = RequestMethod.GET)
	public String test(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("test");
		return "modules/front/test";
	}
	
}
