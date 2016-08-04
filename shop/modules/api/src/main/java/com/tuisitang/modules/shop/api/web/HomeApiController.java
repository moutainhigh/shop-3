package com.tuisitang.modules.shop.api.web;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jeeshop.core.dao.page.PagerModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.tuisitang.common.bo.ErrorMsg;
import com.tuisitang.common.bo.SmsPlatformType;
import com.tuisitang.common.cache.memcached.MemcachedObjectType;
import com.tuisitang.common.cache.memcached.SpyMemcachedClient;
import com.tuisitang.common.mapper.JsonMapper;
import com.tuisitang.common.sms.SMSClient;
import com.tuisitang.common.sms.SMSClientFactory;
import com.tuisitang.common.utils.Encodes;
import com.tuisitang.common.utils.FreeMarkers;
import com.tuisitang.common.utils.Identities;
import com.tuisitang.common.utils.StringUtils;
import com.tuisitang.common.utils.ValidateUtils;
import com.tuisitang.modules.shop.api.bo.Authorization;
import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.entity.Device;
import com.tuisitang.modules.shop.entity.IndexImg;
import com.tuisitang.modules.shop.entity.NotifyTemplate;
import com.tuisitang.modules.shop.entity.Product;
import com.tuisitang.modules.shop.service.ProductService;
import com.tuisitang.modules.shop.service.SystemService;
import com.tuisitang.modules.shop.utils.Global;

/**    
 * @{#} HomeApiController.java   
 * 
 * 首页 Api Controller
 * 
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: irtone</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 *  
 */
@Controller
@RequestMapping(value = "/api")
public class HomeApiController extends BaseApiController {
	
	@Autowired
	private SystemService systemService;
	@Autowired
	private ProductService productService;
	@Autowired
	private SpyMemcachedClient memcachedClient;

	/**
	 * 1. 订阅，保存设备信息
	 * 
	 * curl --insecure -X POST -H "Content-Type: application/json" -d "{\"token\":\"111\",\"mode\":\"iPhone\"}" https://127.0.0.1:8088/shop-api/api/account/subscribe | iconv -f utf-8 -t gbk
	 * curl -X POST -H "Content-Type: application/json" -d "{\"serialId\":\"111\", \"token\":\"111\",\"mode\":\"iPhone\"}" http://192.168.31.104:8080/shop-api/api/subscribe | iconv -f utf-8 -t gbk
	 * 
	 * curl -X POST -d "serialId=111&token=111&mode=iPhone" http://192.168.31.104:8080/shop-api/api/subscribe | iconv -f utf-8 -t gbk
	 */
	@RequestMapping(value = "subscribe", method = RequestMethod.POST)
	public @ResponseBody String subscribe(@RequestParam(value = "callback", required = false) String callback,
			@RequestParam(value = "serialId", required = true) String serialId,
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "alias", required = false) String alias,
			@RequestParam(value = "mode", required = true) String mode,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "os", required = false) String os,
			@RequestParam(value = "osVersion", required = false) String osVersion,
			@RequestHeader(value = "Authorization", required = false) String authorization,
			HttpServletRequest request, HttpServletResponse response) {
		Date sysTime = new Date(System.currentTimeMillis());
		logger.info("serialId=" + serialId + ",token=" + token + ",alias="
				+ alias + ",mode=" + mode + ",type=" + type + ",os=" + os
				+ ",osVersion=" + osVersion);
		Map<String, Object> returnMap = Maps.newHashMap();
		try {
			Device device = systemService.getDevice(token);
			if (device == null) {
				device = new Device(serialId, token, "", alias, mode, type, os, osVersion, Identities.randomBase62(128), "0");
				device.setCreateTime(sysTime);
				systemService.saveDevice(device);
			} else {
//				systemService.updateDevice(device);
			}
			logger.info("device {}", device);
			returnMap = buildReturnMap(SUCCESS, device.getDeviceSecret());
		} catch (Exception e) {
			e.printStackTrace();
			returnMap = buildReturnMap(FAILURE, e.getMessage());
		}
		return buildJson(callback, returnMap);
	}
	
	/**
	 * 2. 注册
	 * authorization Basic token:username:password
	 * 密码需要加密
	 */
	@RequestMapping(value = "regist", method = RequestMethod.POST)
	public @ResponseBody String regist(@RequestParam(value = "callback", required = false) String callback,
			@RequestParam(value = "mobileVerify", required = true) String mobileVerify,
			@RequestHeader(value = "Authorization", required = true) String authorization,
			HttpServletRequest request, HttpServletResponse response) {
		Date systemTime = new Date(System.currentTimeMillis());
		logger.info("authorization {}, mobileVerify {}", authorization, mobileVerify);
		Map<String, Object> returnMap = buildReturnMap(SUCCESS, "");
		try {
			if (authorization.indexOf("Basic") >= 0) {
				authorization = authorization.substring(5).trim();
			}
			authorization = new String(Encodes.decodeBase64(authorization), "UTF-8");
			String[] ss = authorization.split(":");
			String mobile = null;
			String password = null;
			String token = null;
			if (ss.length == 3) {
				token = ss[0];
				Device device = systemService.getDevice(token);
				mobile = ss[1];
				password = Encodes.rc4(new String(Encodes.decodeBase64(ss[2]), "UTF-8"), device.getDeviceSecret());
				String code = memcachedClient.get(MemcachedObjectType.VerifyCode.getPrefix() + token);//(String) request.getSession().getAttribute(ValidateCodeServlet.VALIDATE_CODE);
				logger.info("token {}, mobile {}, password {}, code {}", token, mobile, password, code);
				if (StringUtils.isBlank(mobile) || !ValidateUtils.isMobile(mobile)){
					logger.error("{}", ErrorMsg.MobileIsIncorrect);
					returnMap = buildReturnMap(FAILURE, ErrorMsg.MobileIsIncorrect.getErrMsg());
				} else if (systemService.getAccountByMobile(mobile) != null) {
					logger.error("{}", ErrorMsg.MobileIsExist);
					returnMap = buildReturnMap(FAILURE, ErrorMsg.MobileIsExist.getErrMsg());
//				} else if (StringUtils.isBlank(verifyCode) || !verifyCode.toUpperCase().equals(code)) {
//					logger.error("{}", ErrorMsg.VerifyCodeIsIncorrect);
//					returnMap = buildReturnMap(FAILURE, ErrorMsg.VerifyCodeIsIncorrect.getErrMsg());
				} else {
					String key = MemcachedObjectType.MCODE.getPrefix() + mobile;
					String mcode = memcachedClient.get(key);// 从缓存中获得验证码
					if (StringUtils.isBlank(mcode) || !mcode.equals(mobileVerify)) {
						logger.error("手机验证码错误.");
						returnMap = buildReturnMap(FAILURE, ErrorMsg.MobileVerifyIsIncorrect.getErrMsg());
					} else if (systemService.getAccountByMobile(mobile) != null) {
						logger.error("{}", ErrorMsg.MobileIsExist);
						returnMap = buildReturnMap(FAILURE, ErrorMsg.MobileIsExist.getErrMsg());
					} else {
						Account account = new Account();
						account.setMobile(mobile);
						account.setAccount(mobile);
						account.setPassword(SystemService.entryptPassword(password));
						account.setRegeistDate(systemTime);
						systemService.saveAccount(account);
						logger.info("注册成功");
						returnMap.put("account", account);
					}
				}
			} else {
				returnMap = buildReturnMap(FAILURE, "用户名密码错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMap = buildReturnMap(FAILURE, e.getMessage());
		}
		return buildJson(callback, returnMap);
	}
	
	/**
	 * 3.1 登录For APP
	 */
	@RequestMapping(value = "login", method = { RequestMethod.POST })
	public @ResponseBody String login(@RequestParam(value = "callback", required = false) String callback,
			@RequestHeader(value = "Authorization", required = true) String authorization,
			HttpServletRequest request, HttpServletResponse response) {
//		Date systemTime = new Date(System.currentTimeMillis());
		logger.info("authorization {}", authorization);
		Map<String, Object> returnMap = buildReturnMap(SUCCESS, "");
		try {
			if (authorization.indexOf("Basic") >= 0) {
				authorization = authorization.substring(5).trim();
			}
			authorization = new String(Encodes.decodeBase64(authorization), "UTF-8");
			String[] ss = authorization.split(":");
			String mobile = null;
			String password = null;
			String token = null;
			if (ss.length == 3) {
				token = ss[0];
				Device device = systemService.getDevice(token);
				mobile = ss[1];
				password = Encodes.rc4(new String(Encodes.decodeBase64(ss[2]), "UTF-8"), device.getDeviceSecret());
//				String code = memcachedClient.get(MemcachedObjectType.VerifyCode.getPrefix() + token);//(String) request.getSession().getAttribute(ValidateCodeServlet.VALIDATE_CODE);
				logger.info("token {}, mobile {}, password {}", token, mobile, password);
				if (StringUtils.isBlank(mobile) || !ValidateUtils.isMobile(mobile)){
					logger.error("{}", ErrorMsg.MobileIsIncorrect);
					returnMap = buildReturnMap(FAILURE, ErrorMsg.MobileIsIncorrect.getErrMsg());
				} else {
					Account account = systemService.getAccountByMobile(mobile);
					if (account == null || account.getId() == null) {
						returnMap = buildReturnMap(FAILURE, ErrorMsg.LoginUserNamePasswordIsIncorrect.getErrMsg());
					} else if (!SystemService.validatePassword(password, account.getPassword())) {
						returnMap = buildReturnMap(FAILURE, ErrorMsg.LoginUserNamePasswordIsIncorrect.getErrMsg());
					} else {
						logger.info("登录成功");
						returnMap.put("account", account);
					}
				}
			} else {
				returnMap = buildReturnMap(FAILURE, ErrorMsg.LoginUserNamePasswordIsIncorrect.getErrMsg());
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMap = buildReturnMap(FAILURE, e.getMessage());
		}
		return buildJson(callback, returnMap);
	}
	
	/**
	 * 3.2 登录For H5
	 */
	@RequestMapping(value = "ajaxLogin", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String ajaxLogin(@RequestParam(value = "callback", required = true) String callback,
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			HttpServletRequest request, HttpServletResponse response) {
//		Date systemTime = new Date(System.currentTimeMillis());
		logger.info("username {}, password {}", username, password);
		Map<String, Object> returnMap = buildReturnMap(SUCCESS, "");
		try {
//			if (StringUtils.isBlank(password)) {
//				logger.error("{}", "密码为空");
//				returnMap = buildReturnMap(FAILURE, "密码为空");
//			} 
//			password = new String( Encodes.decodeBase64(password), "UTF-8");
			logger.info("username {}, password {}", username, password);
			if (StringUtils.isBlank(username) || !ValidateUtils.isMobile(username)) {
				logger.error("{}", ErrorMsg.MobileIsIncorrect);
				returnMap = buildReturnMap(FAILURE, ErrorMsg.MobileIsIncorrect.getErrMsg());
			} else {
				Account account = systemService.getAccountByMobile(username);
				if (account == null || account.getId() == null) {
					returnMap = buildReturnMap(FAILURE, ErrorMsg.LoginUserNamePasswordIsIncorrect.getErrMsg());
				} else if (!SystemService.validatePassword(password, account.getPassword())) {
					returnMap = buildReturnMap(FAILURE, ErrorMsg.LoginUserNamePasswordIsIncorrect.getErrMsg());
				} else {
					logger.info("登录成功");
					returnMap.put("account", account);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			returnMap = buildReturnMap(FAILURE, e.getMessage());
		}
		return buildJson(callback, returnMap);
	}
	
	/**
	 * 4. 手机号是否存在
	 * 存在 true
	 * 不存在 false
	 */
	@RequestMapping(value = "existMobile", method = { RequestMethod.POST })
	public @ResponseBody String existMobile(@RequestParam(value = "callback", required = false) String callback, 
			@RequestParam("mobile") String mobile, HttpServletRequest request, HttpServletResponse response) {
		logger.info("checkMobile mobile {}", mobile);
		Map<String, Object> returnMap = buildReturnMap(SUCCESS, "");
		if (StringUtils.isBlank(mobile) || !ValidateUtils.isMobile(mobile)) {
			logger.error("{}", ErrorMsg.MobileIsIncorrect);
			returnMap = buildReturnMap(FAILURE, ErrorMsg.MobileIsIncorrect.getErrMsg());
		} else {
			returnMap.put("exist", systemService.getAccountByMobile(mobile) != null);
		}
		return buildJson(callback, returnMap);
	}
	
	/**
	 * 5. 获得手机验证码
	 * 接收页面验证码，验证码正确则发送短信验证码
	 */
	@RequestMapping(value = "getMcode", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getMcode(@RequestParam(value = "callback", required = false) String callback, 
			@RequestParam("token") String token, 
			@RequestParam("mobile") String mobile, 
			@RequestParam("type") String type, HttpServletRequest request, HttpServletResponse response) {
		Date systemTime = new Date(System.currentTimeMillis());
		Map<String, Object> returnMap = buildReturnMap(SUCCESS, "");
		logger.info("mobile {}, token {}, type {}", mobile, token, type);
//		String code = memcachedClient.get(MemcachedObjectType.VerifyCode.getPrefix() + token);//(String) request.getSession().getAttribute(ValidateCodeServlet.VALIDATE_CODE);
		String platformType = SmsPlatformType.CL.getType();
		
		if (StringUtils.isBlank(mobile) || !ValidateUtils.isMobile(mobile)){
			logger.error("{}", ErrorMsg.MobileIsIncorrect);
			returnMap = buildReturnMap(FAILURE, ErrorMsg.MobileIsIncorrect.getErrMsg());
//		} else if (!NotifyType.Forget.getType().equals(type) && (StringUtils.isBlank(verifyCode) || !verifyCode.toUpperCase().equals(code))){
//			logger.error("{}", ErrorMsg.VerifyCodeIsIncorrect);
//			returnMap = buildReturnMap(FAILURE, ErrorMsg.VerifyCodeIsIncorrect.getErrMsg());
		} else {
			String template = systemService.getNotifyTemplate(NotifyTemplate.TYPE_SMS, type);// 获得手机注册模板
			/**
			String mcode = systemService.getMobileVerify(mobile);// 从缓存中获得验证码
			if (!StringUtils.isBlank(mcode)) {// 如果验证码不为空则说明请求太频繁
				logger.error("验证码错误.");
				return buildReturnMap(FAILURE, ErrorMsg.MobileIsIncorrect);
			}
			mcode = Identities.randomValidateCode();
			*/
			String mcode = Identities.randomValidateCode();
			systemService.setMobileVerify(mobile, mcode);

			Map<String, String> m = Maps.newHashMap();
			m.put("mcode", mcode);
			String message = FreeMarkers.renderString(template, m);
			try {
				SMSClient client = SMSClientFactory.getClient(platformType);
				String msg = client.sendSMS(mobile, message);
				
//				SpringContextHolder.getApplicationContext().publishEvent(new SmsEvent(this, null, mobile, message, type, platformType,
//						Sms.STATUS_SUCCESS, msg, systemTime));
			} catch (Exception e) {
				e.printStackTrace();
//				SpringContextHolder.getApplicationContext().publishEvent(new SmsEvent(this, null, mobile, message, type, platformType,
//						Sms.STATUS_EXCEPTION, e.getMessage(), systemTime));
//				return buildReturnMap(Status.Success, ErrorMsg.NULL);
				returnMap = buildReturnMap(FAILURE, e.getMessage());
			}
		}
		
		return buildJson(callback, returnMap);
	}
	
	/**
	 * 6. 检查手机验证码
	 */
	@RequestMapping(value = "checkMobileVerify", method = { RequestMethod.POST })
	public @ResponseBody String checkMobileVerify(@RequestParam(value = "callback", required = false) String callback, 
			@RequestParam("mobile") String mobile, 
			@RequestParam("mobileVerify") String mobileVerify,
			@RequestParam("type") String type,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("checkMobile mobile {}, mobileVerify {}, type {}", mobile, mobileVerify, type);
		Map<String, Object> returnMap = buildReturnMap(SUCCESS, "");
		if (StringUtils.isBlank(mobile) || !ValidateUtils.isMobile(mobile)) {
			logger.error("{}", ErrorMsg.MobileIsIncorrect);
			returnMap = buildReturnMap(FAILURE, ErrorMsg.MobileIsIncorrect.getErrMsg());
		} else {
			String key = MemcachedObjectType.MCODE.getPrefix() + mobile;
			String mcode = memcachedClient.get(key);// 从缓存中获得验证码
			if (StringUtils.isBlank(mcode) || !mcode.equals(mobileVerify)) {
				logger.error("手机验证码错误.");
				returnMap = buildReturnMap(FAILURE, ErrorMsg.MobileVerifyIsIncorrect.getErrMsg());
			} 
		}
		return buildJson(callback, returnMap);
	}
	
	/**
	 * 7. 首页导航图
	 */
	@RequestMapping(value = "ad", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String ad(@RequestParam(value = "callback", required = false) String callback, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> returnMap = buildReturnMap(SUCCESS, "");
		//首页导航图片
		List<IndexImg> indexImgList = productService.getIndexImgByType(IndexImg.INDEX_IMG_TYPE_MALL);
		returnMap.put("adList", indexImgListToMapList(request, indexImgList));
		return buildJson(callback, returnMap);
	}
	
	/**
	 * 8. 加载首页商品
	 */
	@RequestMapping(value = "index", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String index(@RequestParam(value = "callback", required = false) String callback, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> returnMap = buildReturnMap(SUCCESS, "");
		// 加载热门推荐商品
		String imageRootPath = (String) request.getSession().getServletContext().getAttribute(Global.IMAGE_ROOT_PATH);
		//加载最新商品
		Map<String, Object> params = Maps.newHashMap();
		int pageSize = StringUtils.isBlank(request.getParameter("pageSize")) ? 4 : new Integer(request.getParameter("pageSize"));
		int pageNo = StringUtils.isBlank(request.getParameter("pageNo")) ? 0 : new Integer(request.getParameter("pageNo"));
		params.put("isRecommend", "y");
		params.put("orderBy", "sellCountDown");
		PagerModel<Product> page = productService.findPageProduct(params, pageNo, pageSize);
		returnMap.put("root", imageRootPath);

		returnMap.put("recommend", productListToMapList(request, page.getList()));
		return buildJson(callback, returnMap);
	}
	
	/**
	 * 9. 预订
	 * 
	 * areaCode 110101, address 都季商务快捷酒店, demand 枯藤老树昏鸦，小桥流水人家，古道西风瘦马。 —— 马致远《天净沙·秋思》 
	 * time 2015年09月07日09:00 
	 * longitude 116.405381, latitude 39.911428
	 * 
	 * name zhangsan 
	 * mobile 18011584712
	 * areaCode 110101, address 汉庭酒店(北京天安门店), demand 人生若只如初见，何事秋风悲画扇。 —— 纳兰性德《木兰词·拟古决绝词柬友》
	 * longitude 116.403409, latitude 39.912472
	 * Key address, Value [汉庭酒店(北京天安门店)]
	 * Key areaCode, Value [110101]
	 * Key demand, Value [人生若只如初见，何事秋风悲画扇。 —— 纳兰性德《木兰词·拟古决绝词柬友》]
	 * Key deviceToken, Value [111]
	 * Key latitude, Value [39.912472]
	 * Key longitude, Value [116.403409]
	 * Key mobile, Value [18011584712]
	 * Key mobileVerify, Value [614148]
	 * Key name, Value [zhangsan]
	 * Key time, Value [2015年09月06日17:30]
	 */
	@RequestMapping(value = "booking", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String booking(@RequestParam(value = "callback", required = false) String callback,
			@RequestHeader(value = "Authorization", required = false) String authorization,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "mobile", required = true) String mobile,
			@RequestParam(value = "deviceToken", required = true) String deviceToken,
			@RequestParam(value = "mobileVerify", required = true) String mobileVerify,
			@RequestParam(value = "areaCode", required = true) String areaCode,
			@RequestParam(value = "address", required = true) String address,
			@RequestParam(value = "time", required = true) String time,
			@RequestParam(value = "demand", required = true) String demand,
			@RequestParam(value = "longitude", required = true) Double longitude,
			@RequestParam(value = "latitude", required = true) Double latitude,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> returnMap = buildReturnMap(SUCCESS, "");
		logger.info("name {}", name);
		logger.info("mobile {}", mobile);
		logger.info("areaCode {}, address {}, demand {}", areaCode, address, demand);
		logger.info("time {}", time);
		logger.info("longitude {}, latitude {}", longitude, latitude);
		
		Authorization auth = getAuthorization(authorization);
		if (FAILURE.equals(auth.getErrorCode())) {
			//创建账户
			
		} else {
			Account account = systemService.getAccountByMobile(auth.getMobile());
		}
		
		Map<String, Object> parameterMap = request.getParameterMap();
		for (Entry<String, Object> entry : parameterMap.entrySet()) {
			logger.info("Key {}, Value {}", entry.getKey(), entry.getValue());
		}
		return buildJson(callback, returnMap);
	}
	
}
