package com.tuisitang.modules.shop.front.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.core.kuaidi.Kuaidi100Info;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sun.misc.BASE64Decoder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;
import com.tuisitang.common.bo.ErrorMsg;
import com.tuisitang.common.bo.ModifyPasswordType;
import com.tuisitang.common.bo.NotifyType;
import com.tuisitang.common.bo.OrderStatus;
import com.tuisitang.common.bo.SmsPlatformType;
import com.tuisitang.common.bo.Status;
import com.tuisitang.common.mapper.JsonMapper;
import com.tuisitang.common.servlet.ValidateCodeServlet;
import com.tuisitang.common.sms.SMSClient;
import com.tuisitang.common.sms.SMSClientFactory;
import com.tuisitang.common.sms.SMSUtils;
import com.tuisitang.common.utils.DateUtils;
import com.tuisitang.common.utils.FreeMarkers;
import com.tuisitang.common.utils.Identities;
import com.tuisitang.common.utils.ShippingKit;
import com.tuisitang.common.utils.SpringContextHolder;
import com.tuisitang.common.utils.ValidateUtils;
import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.entity.AccountRank;
import com.tuisitang.modules.shop.entity.Address;
import com.tuisitang.modules.shop.entity.Area;
import com.tuisitang.modules.shop.entity.Authentication;
import com.tuisitang.modules.shop.entity.BookingOrder;
import com.tuisitang.modules.shop.entity.BookingOrderLog;
import com.tuisitang.modules.shop.entity.ExpressCompany;
import com.tuisitang.modules.shop.entity.Favorite;
import com.tuisitang.modules.shop.entity.NotifyTemplate;
import com.tuisitang.modules.shop.entity.Order;
import com.tuisitang.modules.shop.entity.Orderdetail;
import com.tuisitang.modules.shop.entity.Ordership;
import com.tuisitang.modules.shop.entity.Product;
import com.tuisitang.modules.shop.entity.ProfitLog;
import com.tuisitang.modules.shop.entity.QiniuOSS;
import com.tuisitang.modules.shop.entity.Sms;
import com.tuisitang.modules.shop.front.event.SmsEvent;
import com.tuisitang.modules.shop.front.service.ProductFrontService;
import com.tuisitang.modules.shop.front.service.SystemFrontService;
import com.tuisitang.modules.shop.front.utils.AccountUtils;
import com.tuisitang.modules.shop.service.AccountService;
import com.tuisitang.modules.shop.service.CommonService;
import com.tuisitang.modules.shop.service.OrderService;
import com.tuisitang.modules.shop.utils.Global;

import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;

/**    
 * @{#} AccountController.java   
 * 
 * 账号Controller
 * 
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@Controller
@RequestMapping("/i")
public class AccountController extends BaseController {
	
	public static final String TITLE_PROFILE = "设置账户信息";
	public static final String TITLE_MODIFY_PWD = "修改密码";
	public static final String TITLE_MODIFY_MOBILE = "修改手机号";
	public static final String TITLE_MODIFY_PICTURE = "修改头像";
	public static final String TITLE_MANAGE_ADDRESSES = "管理收货地址";
	public static final String TITLE_BIND_MOBILE = "绑定手机";
	
	public static final String TITLE_MEMBERSHIP = "我的会员等级";
	public static final String TITLE_ORDER = "我的订单";
	public static final String TITLE_ORDER_DETAIL = "订单详情";
	public static final String TITLE_MODIFY_SHIPPING = "修改地址";
	public static final String TITLE_SHIPPING_TRACE = "物流查询";
	//favorite subscribe
	public static final String TITLE_FAVORITE = "我的收藏";
	public static final String TITLE_SUBSCRIBE = "我的邀请码";
	public static final String TITLE_ENTERPRISE_AUTH = "企业认证";
	public static final String TITLE_PERSON_AUTH = "个人实名认证";
	public static final String TITLE_INVITE = "我的邀请";
	public static final String TITLE_BOOKING_ORDER = "我的预约单";
	
	@Autowired
	private SystemFrontService systemFrontService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private ProductFrontService productFrontService;
	
	/**
	 * 1. 首页
	 */
	@RequiresUser
	@RequestMapping(value = { "", "/", "home", "index" }, method = RequestMethod.GET)
	public String home(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("account index");
		
		return "modules/front/i/account/index";
	}
	
	/**
	 * 2.1 个人信息
	 */
	@RequiresUser
	@RequestMapping(value = { "account/profile" }, method = RequestMethod.GET)
	public String profile(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("profile");
		Account acc = AccountUtils.getAccount();
		Account account = systemFrontService.getAccountById(acc.getId());
		AccountUtils.setAccount(account);
		model.addAttribute("account", account);
		model.addAttribute("accountTitle", TITLE_PROFILE);
		model.addAttribute("mobile", account.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
		if (account.getBirthday() != null) {
			Date birthday = account.getBirthday();
			String birthdayYear = DateUtils.getYear(birthday);
			String birthdayMonth = DateUtils.getMonth(birthday);
			String birthdayDay = DateUtils.getDay(birthday);
			model.addAttribute("birthdayYear", birthdayYear);
			model.addAttribute("birthdayMonth", birthdayMonth);
			model.addAttribute("birthdayDay", birthdayDay);
		}
		return "modules/front/i/account/profile";
	}
	
	/**
	 * 2.2 保存个人信息
	 */
	@RequiresUser
	@RequestMapping(value = { "account/profile" }, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> profile(@Param("id")Long id, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		Account account = AccountUtils.getAccount();
		logger.info("save profile");
		String username = request.getParameter("username") ;
		String birthdayYear = request.getParameter("birthday_year");
		String birthdayMonth = request.getParameter("birthday_month");
		String birthdayDay = request.getParameter("birthday_day");
		String gender = request.getParameter("gender");
		logger.info("username = {} \ngender = {} \nbirthdayYear = {} \nbirthdayMonth {} \nbirthdayDay {} ", 
				username, gender, birthdayYear, birthdayMonth, birthdayDay);
		List<ErrorMsg> msgs = Lists.newArrayList();
		logger.info("username = {}, birthdayYear = {}, birthdayMonth = {} birthdayDay = {}", 
				username, birthdayYear, birthdayMonth, birthdayDay);
		if (StringUtils.isBlank(username) || username.trim().length() < 4 || username.trim().length() > 16) {
			logger.info("应为4-16个中英文字符，不能以数字开头");
			msgs.add(ErrorMsg.UsernameIsIncorrect);
		}
		if (!StringUtils.isBlank(birthdayYear)
		&& !StringUtils.isBlank(birthdayMonth)
		&& !StringUtils.isBlank(birthdayDay)) {
			Date birthday = DateUtils.parseDate(birthdayYear + "/" + birthdayMonth + "/" + birthdayDay);
			account.setBirthday(birthday);
		} else {
			logger.info("请填写您的生日");
			msgs.add(ErrorMsg.BirthdayIsIncorrect);
		}
		if (!StringUtils.isBlank(gender)) {
			account.setSex(gender);
		}
		
		
		if (msgs.size() == 0) {
			account.setAccount(username);
			systemFrontService.updateAccount(account);
			return buildReturnMap(Status.Success, ErrorMsg.NULL);
		} else {
			return buildReturnMap(Status.Failure, msgs);
		}
	}
	
	/**
	 * 2.3 检查用户名是否存在
	 */
	@RequiresUser
	@RequestMapping(value = { "account/profile/checkUsername" }, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> checkUsername(@Param("username")String username, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		logger.info("checkUsername");
		systemFrontService.getAccountByUsername(username);
		if (systemFrontService.getAccountByUsername(username) == null) {
			return buildReturnMap(Status.Success, ErrorMsg.NULL);
		} else {
			return buildReturnMap(Status.Failure, "用户名已存在");
		}
	}
	
	/**
	 * 3.1 修改密码
	 */
	@RequiresUser
	@RequestMapping(value = { "account/password" }, method = RequestMethod.GET)
	public String modifyPwd(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("modifyPwd");
		Account account = AccountUtils.getAccount();
		model.addAttribute("account", account);
		model.addAttribute("accountTitle", TITLE_MODIFY_PWD);
		model.addAttribute("mobile", account.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
		return "modules/front/i/account/password";
	}
	
	/**
	 * 3.2 修改密码 - 保存
	 */
	@RequiresUser
	@RequestMapping(value = { "account/password" }, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> modifyPwd(@Param("type")String type, HttpServletRequest request, HttpServletResponse response) {
		Account account = AccountUtils.getAccount();
		logger.info("modify password");
		String mobileVerify = request.getParameter("mobileVerify") ;
		String oldPassword = request.getParameter("oldPassword");
		String password = request.getParameter("password");
		String verifyCode = request.getParameter("verifyCode");
		logger.info("mobileVerify = {} \noldPassword = {} \npassword = {} \nverifyCode {} ", 
				mobileVerify, oldPassword, password, verifyCode);
		List<ErrorMsg> msgs = Lists.newArrayList();
		if (ModifyPasswordType.Password.getType().equals(type)) {// 密码验证
			if (StringUtils.isBlank(oldPassword)  || !SystemFrontService.validatePassword(oldPassword, account.getPassword())) {
				logger.info("旧密码错误");
				msgs.add(ErrorMsg.ModifyOldPasswordIsIncorrect);
			}
		} else {
			if (StringUtils.isBlank(mobileVerify) 
			||  !mobileVerify.equals(systemFrontService.getMobileVerify(account.getMobile()))) {
				logger.info("手机验证码验证码错误");
				msgs.add(ErrorMsg.ModifyMobileVerifyIsIncorrect);
			}
		}
		if (StringUtils.isBlank(password)) {
			logger.info("新密码为空");
			msgs.add(ErrorMsg.ModifyNewPasswordIsEmpty);
		} else if (password.trim().length() < 6 || password.trim().length() > 16) {
			logger.info("应为4-16个中英文字符，不能以数字开头");
			msgs.add(ErrorMsg.ModifyNewPasswordIsIncorrect);
		}
		String code = (String) request.getSession().getAttribute(ValidateCodeServlet.VALIDATE_CODE);
		logger.info("code {}, verifyCode {}", code, verifyCode);
		if (StringUtils.isBlank(verifyCode) || !verifyCode.toUpperCase().equals(code)) {
			logger.info("验证码错误");
			msgs.add(ErrorMsg.ModifyVerifyCodeIsIncorrect);
		}
		if (msgs.size() == 0) {
			systemFrontService.updateAccountPasswordById(account.getId(), password);
			return buildReturnMap(Status.Success, ErrorMsg.NULL);
		} else {
			return buildReturnMap(Status.Failure, msgs);
		}
	}
	
	/**
	 * 4.1 绑定的手机号
	 */
	@RequiresUser
	@RequestMapping(value = { "account/mobile" }, method = RequestMethod.GET)
	public String bindMobile(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("bindMobile");
		Account account = AccountUtils.getAccount();
		model.addAttribute("account", account);
		model.addAttribute("accountTitle", TITLE_BIND_MOBILE);
		model.addAttribute("mobile", account.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
		return "modules/front/i/account/bindMobile";
	}
	
	/**
	 * 4.2 修改绑定手机号
	 */
	@RequiresUser
	@RequestMapping(value = { "account/mobile/modify" }, method = RequestMethod.GET)
	public String modifyMobile(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("modifyMobile");
		Account account = AccountUtils.getAccount();
		model.addAttribute("accountTitle", TITLE_BIND_MOBILE);
		model.addAttribute("mobile", account.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
		request.getSession().setAttribute("mobile_bind_sign", SystemFrontService.entryptPassword(account.getMobile() + ":1"));
		return "modules/front/i/account/modifyMobile";
	}
	
	/**
	 * 4.3 确认绑定手机号
	 */
	@RequiresUser
	@RequestMapping(value = { "account/mobile/modify/confirm" }, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> confirmMobile(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("confirmMobile");
		Account account = AccountUtils.getAccount();
		String sign = (String) request.getSession().getAttribute(
				"mobile_bind_sign");
		if (StringUtils.isBlank(sign)
		|| !SystemFrontService.validatePassword(account.getMobile() + ":1", sign)) {
			return buildReturnMap(Status.Failure, ErrorMsg.ErrorSetpException);
		}
		String verifyCode = request.getParameter("verifyCode");
		String mobileVerify = request.getParameter("mobileVerify");
		
		String code = (String) request.getSession().getAttribute(ValidateCodeServlet.VALIDATE_CODE);
		logger.info("code {}, verifyCode {}", code, verifyCode);
		if (StringUtils.isBlank(verifyCode) || !verifyCode.toUpperCase().equals(code)) {
			logger.info("验证码错误");
			return buildReturnMap(Status.Failure, "验证码错误");
		}
		if (StringUtils.isBlank(mobileVerify) 
		||  !mobileVerify.equals(systemFrontService.getMobileVerify(account.getMobile()))) {
			logger.info("手机验证码错误");
			return buildReturnMap(Status.Failure, "手机验证码错误");
		}
		request.getSession().setAttribute("mobile_bind_sign", SystemFrontService.entryptPassword(account.getMobile() + ":2"));
		return buildReturnMap(Status.Success, ErrorMsg.NULL);
	}
	
	/**
	 * 4.4 重新绑定手机号
	 */
	@RequiresUser
	@RequestMapping(value = { "account/mobile/modify/rebind" }, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> rebindMobile(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("rebindMobile");
		Account account = AccountUtils.getAccount();
		String sign = (String) request.getSession().getAttribute("mobile_bind_sign");
		if (StringUtils.isBlank(sign)
		|| !SystemFrontService.validatePassword(account.getMobile() + ":2", sign)) {
			return buildReturnMap(Status.Failure, ErrorMsg.ErrorSetpException);
		}
		String mobile = request.getParameter("mobile");
		if (StringUtils.isBlank(mobile) || !ValidateUtils.isMobile(mobile)) {
			logger.info("手机号错误");
			return buildReturnMap(Status.Failure, "手机号错误");
		}
		if (systemFrontService.getAccountByMobile(mobile) != null) {
			logger.info("手机号已存在");
			return buildReturnMap(Status.Failure, "手机号已存在");
		}
		String mobileVerify = request.getParameter("mobileVerify");
		if (StringUtils.isBlank(mobileVerify) 
		||  !mobileVerify.equals(systemFrontService.getMobileVerify(mobile))) {
			logger.info("手机验证码错误");
			return buildReturnMap(Status.Failure, "手机验证码错误");
		}
		systemFrontService.updateAccountMobileById(account.getId(), account.getMobile(), mobile);
		request.getSession().removeAttribute("mobile_bind_sign");
		return buildReturnMap(Status.Success, ErrorMsg.NULL);
	}
	
	
	/**
	 * 5.1 修改头像
	 */
	@RequiresUser
	@RequestMapping(value = { "account/modifyPic" }, method = RequestMethod.GET)
	public String modifyPic(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("modifyPic");
		Account account = AccountUtils.getAccount();
		logger.info("account {}", account);
		model.addAttribute("id", account.getId());
		model.addAttribute("mobile", account.getMobile());
		model.addAttribute("account", account);
		model.addAttribute("sign", SystemFrontService.entryptPassword(account.getId() + ":" + account.getMobile()));
		model.addAttribute("accountTitle", TITLE_MODIFY_PICTURE);
		return "modules/front/i/account/modifyPic";
	}
	
	/**
	 * 5.2.上传图片
	 */
	@RequestMapping(value = "account/upload", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> uploadFile(MultipartHttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		String mobile = request.getParameter("mobile");
		String sign = request.getParameter("sign");
		logger.info("id {}, mobile {}, sign {}", id, mobile, sign);
		if (!SystemFrontService.validatePassword(id + ":" + mobile, sign)) {
			return buildReturnMap(Status.Failure, ErrorMsg.AuthenticationException);
		}
		Account account = systemFrontService.getAccountById(new Long(id));
		Iterator<String> fileNames = request.getFileNames();
		String fileName = null;
		if (fileNames.hasNext()) {
			fileName = fileNames.next();
		}
		if(StringUtils.isBlank(fileName)) {
			return buildReturnMap(Status.Failure, ErrorMsg.ImageNotFoundException);
		}
		
		MultipartFile file = request.getFile(fileName);
		String originalFileName = file.getOriginalFilename();
		String suffix = originalFileName.lastIndexOf(".") > 0 ? originalFileName.substring(originalFileName.lastIndexOf(".") + 1) : "";
		String newFileName = Identities.uuid2();
//		String thumbnailFileName = newFileName + "@200*200." + suffix;
		
//		AliyunOSS oss = (AliyunOSS) request.getSession().getServletContext().getAttribute(Global.ALIYUN_OSS_CONFIG);
//		String accessKeyId = oss.getACCESS_ID();
//		String endpoint = "http://oss.aliyuncs.com/";
//		String accessKeySecret = oss.getACCESS_KEY();
//		String bucketName = oss.getBucketName();
//		OSSClient client = OSSKit.getOSSClient(endpoint, accessKeyId, accessKeySecret);
//		client.createBucket(bucketName);
//        client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
		
//		String basePath = "upload/i/" + account.getId() + "/head/";
		
//		String key = basePath + newFileName + "." + suffix;
//		// 创建上传Object的Metadata
//		ObjectMetadata meta = new ObjectMetadata();
//		// 必须设置ContentLength
//		meta.setContentLength(file.getSize());
//		meta.setContentType(request.getContentType());
//		meta.setContentEncoding(request.getCharacterEncoding());
//		// 上传Object.
//		PutObjectResult result = client.putObject(bucketName, key, file.getInputStream(), meta);
		
		QiniuOSS oss = (QiniuOSS) request.getSession().getServletContext().getAttribute(Global.QINIU_OSS_CONFIG);
		String bucketName = oss.getBucketName();
//		String domain = oss.getDomain();
		
		String basePath = "upload/i/" + account.getId() + "/head/";
		String key = basePath + newFileName + "." + suffix;
		
		Config.ACCESS_KEY = oss.getACCESS_KEY();
        Config.SECRET_KEY = oss.getSECRET_KEY();
        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);

        PutPolicy putPolicy = new PutPolicy(bucketName);
        String uptoken = null;
		try {
			uptoken = putPolicy.token(mac);
		} catch (AuthException e) {
			return buildReturnMap(Status.Failure, ErrorMsg.ImageNotFoundException);
		} catch (JSONException e) {
			return buildReturnMap(Status.Failure, ErrorMsg.ImageNotFoundException);
		}
        PutExtra extra = new PutExtra();
        PutRet ret = IoApi.Put(uptoken, key, file.getInputStream(), extra);
        
		account.setPicture(key);
		account.setSmallPicture(key);
		systemFrontService.updateAccount(account);
		return buildReturnMap(Status.Success, key);
	}
	
	/**
	 * 6.1 管理收货地址
	 */
	@RequiresUser
	@RequestMapping(value = { "account/addresses" }, method = RequestMethod.GET)
	public String addresses(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("addresses");
		Account account = AccountUtils.getAccount();
		List<Address> list = accountService.findAddressByAccountId(account.getId());
		List<Area> provinces = commonService.findProvinces();
		model.addAttribute("provinces", provinces);
		model.addAttribute("list", list);
		model.addAttribute("account", account);
		model.addAttribute("accountTitle", TITLE_MANAGE_ADDRESSES);
		return "modules/front/i/account/addresses";
	}
	
	/**
	 * 6.2 保存收货地址
	 */
	@RequiresUser
	@RequestMapping(value = { "account/addresses" }, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addresses(HttpServletRequest request, HttpServletResponse response) {
		Account account = AccountUtils.getAccount();
		logger.info("modify password");
		Long id = StringUtils.isBlank(request.getParameter("id"))?null : new Long(request.getParameter("id"));
		String name = request.getParameter("name") ;
		Long provinceId = StringUtils.isBlank(request.getParameter("provinceId")) ? null : new Long(request.getParameter("provinceId"));
		String provinceName = request.getParameter("provinceName");
		Long cityId = StringUtils.isBlank(request.getParameter("cityId")) ? null : new Long(request.getParameter("cityId"));
		String cityName = request.getParameter("cityName");
		Long countyId = StringUtils.isBlank(request.getParameter("countyId")) ? null : new Long(request.getParameter("countyId"));
		String countyName = request.getParameter("countyName");
		String address = request.getParameter("address");
		String mobile = request.getParameter("mobile");
		String telArea = request.getParameter("telArea");
		String telNumber = request.getParameter("telNumber");
		String telExt = request.getParameter("telExt");
		String zip = request.getParameter("zip");
		
		logger.info("id = {} \nname = {} \nprovinceId = {} \nprovinceName {} \ncityId = {} \ncityName {} \ncountyId = {} \ncountyName {} \naddress {} \nmobile {} \ntelArea {} \ntelNumber {} \ntelExt {} \nzip {}",
				id, name, provinceId, provinceName, cityId, cityName, countyId,
				countyName, address, mobile, telArea, telNumber, telExt, zip);
		if (StringUtils.isBlank(name)) {
			logger.error("请输入收货人姓名");
			return buildReturnMap(Status.Failure, "请输入收货人姓名"); 
		}
		if (provinceId == null || cityId == null || countyId == null) {
			logger.error("请先选择完整收货地址");
			return buildReturnMap(Status.Failure, "请先选择完整收货地址"); 
		}
		if (StringUtils.isBlank(address)) {
			logger.error("详细地址不能为空");
			return buildReturnMap(Status.Failure, "详细地址不能为空"); 
		}
		if (StringUtils.isBlank(mobile)) {
			logger.error("请填写手机号码");
			return buildReturnMap(Status.Failure, "请填写手机号码"); 
		}
		Area province = commonService.getArea(provinceId);
		Area city = commonService.getArea(cityId);
		Area county = commonService.getArea(countyId);
		String phone = null;
		if (!StringUtils.isBlank(telNumber)) {
			if (StringUtils.isBlank(telArea)) {
				logger.error("固定号码请填写区号信息");
				return buildReturnMap(Status.Failure, "固定号码请填写区号信息"); 
			}
			phone = telArea + "-" + telNumber;
			if (!StringUtils.isBlank(telExt)) {
				phone = phone + "-" + telExt;
			}
		}
		Address a = null;
		if (id == null) {
			a = new Address();
			a.setAccountId(account.getId());
			a.setName(name);
			a.setProvinceId(provinceId);
			a.setProvinceCode(province.getCode());
			a.setProvinceName(province.getName());
			a.setCityId(cityId);
			a.setCityCode(city.getCode());
			a.setCityName(city.getName());
			a.setCountyId(countyId);
			a.setCountyCode(county.getCode());
			a.setCountyName(county.getName());
			a.setAddress(address);
			a.setMobile(mobile);
			a.setPhone(phone);
			a.setZip(zip);
			accountService.saveAddress(a);
		} else {
			a = accountService.getAddress(id);
			a.setAccountId(account.getId());
			a.setName(name);
			a.setProvinceId(provinceId);
			a.setProvinceCode(province.getCode());
			a.setProvinceName(provinceName);
			a.setCityId(cityId);
			a.setCityCode(city.getCode());
			a.setCityName(cityName);
			a.setCountyId(countyId);
			a.setCountyCode(county.getCode());
			a.setCountyName(countyName);
			a.setAddress(address);
			a.setMobile(mobile);
			a.setPhone(phone);
			a.setZip(zip);
			accountService.updateAddress(a);
		}
		return buildReturnMap(Status.Success, a.getId().toString());
	}
	
	/**
	 * 6.3 获得Address信息
	 */
	@RequiresUser
	@RequestMapping(value = { "account/addresses/{id}" }, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getAddress(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
		Address address = accountService.getAddress(id);
		if (address != null) {
			Map<String, Object> returnMap = Maps.newHashMap();
			List<Area> cities = commonService.findCities(address.getProvinceId());
			List<Area> counties = commonService.findCounties(address.getCityId());
			returnMap.put("s", Status.Success.getStatus());
			returnMap.put("address", address);
			returnMap.put("cities", cities);
			returnMap.put("counties", counties);
			return returnMap;
		} else {
			return buildReturnMap(Status.Failure, "地址不存在");
		}
	}
	
	/**
	 * 6.4 删除Address信息
	 */
	@RequiresUser
	@RequestMapping(value = { "account/addresses/delete/{id}" }, method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteAddress(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
		try {
			accountService.deleteAddress(id);
			return buildReturnMap(Status.Success, "");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("{}", e.getMessage());
			return buildReturnMap(Status.Failure, e.getMessage());
		}
	}
	
	/**
	 * 7.1 我的会员等级
	 */
	@RequiresUser
	@RequestMapping(value = { "membership" }, method = RequestMethod.GET)
	public String membership(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("membership");
		Account account = AccountUtils.getAccount();
		model.addAttribute("account", account);
		model.addAttribute("accountTitle", TITLE_MEMBERSHIP);
		return "modules/front/i/membership";
	}
	
	/**
	 * 8.1 我的订单
	 */
	@RequiresUser
	@RequestMapping(value = { "order", "order/list" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String order(HttpServletRequest request, HttpServletResponse response, Model model) {
		Account account = AccountUtils.getAccount();
		int pageSize = StringUtils.isBlank(request.getParameter("pageSize")) ? 5 : new Integer(request.getParameter("pageSize"));
		int pageNo = StringUtils.isBlank(request.getParameter("pageNo")) ? 0 : new Integer(request.getParameter("pageNo"));
//		List<Order> list = accountService.findOrder(account.getId(), 0);
		PagerModel<Order> page = accountService.findPageOrder(account.getId(), 0, pageSize, pageNo);
		page.setPagerUrl("/i/order");
		model.addAttribute("type", 0);
		model.addAttribute("page", page);
		model.addAttribute("account", account);
		model.addAttribute("accountTitle", TITLE_ORDER);
		return "modules/front/i/order/list";
	}
	
	/**
	 * 8.2 我的订单
	 */
	@RequiresUser
	@RequestMapping(value = "order/{type}", method = { RequestMethod.GET, RequestMethod.POST })
	public String order(@PathVariable("type") int type, HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("order list type {}", type);
		Account account = AccountUtils.getAccount();
		int pageSize = StringUtils.isBlank(request.getParameter("pageSize")) ? 5 : new Integer(request.getParameter("pageSize"));
		int pageNo = StringUtils.isBlank(request.getParameter("pageNo")) ? 0 : new Integer(request.getParameter("pageNo"));;
//		List<Order> list = accountService.findOrder(account.getId(), 0);
		PagerModel<Order> page = accountService.findPageOrder(account.getId(), type, pageSize, pageNo);
		page.setPagerUrl("/i/order/" + type);
		model.addAttribute("type", type);
		model.addAttribute("page", page);
		model.addAttribute("account", account);
		model.addAttribute("accountTitle", TITLE_ORDER);
		return "modules/front/i/order/list";
	}
	
	/**
	 * 8.3 取消订单
	 */
	@RequiresUser
	@RequestMapping(value = "order/cancel/{orderId}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> cancelOrder(@PathVariable("orderId") Long orderId, HttpServletRequest request, HttpServletResponse response) {
		try {
			Account account = AccountUtils.getAccount();
			int reasonId = StringUtils.isBlank(request.getParameter("reasonId")) ? null : new Integer(request.getParameter("reasonId"));
			String reason = request.getParameter("reason");
			String reasonDesc = request.getParameter("reasonDesc");
			orderService.cancelOrder(orderId, reasonId, reason, reasonDesc, account);
			return buildReturnMap(Status.Success, ErrorMsg.NULL);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{}", e);
			return buildReturnMap(Status.Failure, e.getMessage());
		}
	}
	
	/**
	 * 确认收货
	 */
	@RequiresUser
	@RequestMapping(value = "order/confirmGoods/{orderId}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> confirmGoods(@PathVariable("orderId") Long orderId, HttpServletRequest request, HttpServletResponse response) {
		try {
			Account account = AccountUtils.getAccount();
			orderService.confirmGoods(orderId, account);
			//更新用户积分
			Order order = orderService.getOrder(orderId);
			int score = account.getScore() + order.getScore();
			account.setScore(score);
			
			//判断用户等级是否升级
			List<AccountRank> ranks = commonService.getAllAccountRank();
			for (AccountRank rank : ranks) {
				if (score>= rank.getMinScore() && score <= rank.getMaxScore()) {
					account.setRank(rank.getCode());
					break;
				}
			}
			systemFrontService.updateAccount(account);
			return buildReturnMap(Status.Success, ErrorMsg.NULL);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{}", e);
			return buildReturnMap(Status.Failure, e.getMessage());
		}
	}
	
	/**
	 * 8.4 商品详情
	 */
	@RequiresUser
	@RequestMapping(value = "order/detail/{id}", method = RequestMethod.GET)
	public String orderDetail(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("order detail id {}", id);
		Account account = AccountUtils.getAccount();
		Order order = orderService.getOrder(id);
		
		//订单产品按照销售商分组
		Map<Long, List<Orderdetail>> group = Maps.newHashMap();
		for (Orderdetail orderDetail : order.getOdList()) {
			if (!group.containsKey(orderDetail.getSellerId())) {
				List<Orderdetail> ods = Lists.newArrayList();
				ods.add(orderDetail);
				group.put(orderDetail.getSellerId(), ods);
			} else {
				List<Orderdetail> ods = group.get(orderDetail.getSellerId());
				ods.add(orderDetail);
			}
		}
		order.setSellerList(group);
		
		Ordership ordership = order.getOrdership();
		if (ordership == null) {
			addMessage(model, "配送信息为空");
			model.addAttribute("ordership", new Ordership());
		} else {
			model.addAttribute("ordership", ordership);
		}
		model.addAttribute("order", order);
		model.addAttribute("account", account);
		model.addAttribute("accountTitle", TITLE_ORDER_DETAIL);
		return "modules/front/i/order/detail";
	}
	
	/**
	 * 8.5 修改配送方式
	 */
	@RequiresUser
	@RequestMapping(value = "order/{id}/shipping/modify", method = RequestMethod.GET)
	public String modifyShipping(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("修改地址 id {}", id);
		Account account = AccountUtils.getAccount();
		List<Address> addresses = accountService.findAddressByAccountId(account.getId());
		Order order = orderService.getOrder(id);
		Ordership ordership = order.getOrdership();
		if (ordership == null) {
			addMessage(model, "配送信息为空");
			model.addAttribute("ordership", new Ordership());
		} else {
			model.addAttribute("ordership", ordership);
		}
		model.addAttribute("order", order);
		model.addAttribute("account", account);
		model.addAttribute("addresses", addresses);
		model.addAttribute("accountTitle", TITLE_MODIFY_SHIPPING);
		model.addAttribute("provinces", commonService.findProvinces());

		return "modules/front/i/order/shipping";
	}
	
	/**
	 * 8.6 保存 地址修改
	 */
	@RequiresUser
	@RequestMapping(value = "order/{id}/shipping/modify", method = RequestMethod.POST)
	public String modifyShipping(@PathVariable("id") Long id, @RequestParam("addressId") Long addressId,
			HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		logger.info("保存修改地址 id {}, addressId {}", id, addressId);
		Account account = AccountUtils.getAccount();
		Order order = orderService.getOrder(id);
		Address address = accountService.getAddress(addressId);
		if (address == null) {
			logger.info("地址信息不存在");
			addMessage(redirectAttributes, "地址信息不存在");
			return "redirect:/i/order/" + id + "/shipping/modify";
		}
		Ordership ordership = order.getOrdership();
		if (ordership == null) {
			ordership = new Ordership();
		}
		ordership.setOrderid(id);
		ordership.setAddressId(addressId);
		ordership.setShipname(address.getName());
		ordership.setShipaddress(address.getAddress());
		ordership.setProvince(address.getProvinceName());
		ordership.setProvinceCode(address.getProvinceCode());
		ordership.setCity(address.getCityName());
		ordership.setCityCode(address.getCityCode());
		ordership.setArea(address.getCountyName());
		ordership.setAreaCode(address.getCountyCode());
		ordership.setPhone(address.getMobile());
		ordership.setZip(address.getZip());
		ordership.setTel(address.getPhone());
		orderService.saveOrdership(ordership, account);
		return "redirect:/i/order";
	}
	
	/**
	 * 8.7 物流信息跟踪
	 */
	@RequiresUser
	@RequestMapping(value = "shipping/trace", method = RequestMethod.GET)
	public String shippingTrace(@RequestParam("orderId") Long orderId, HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("物流信息 orderId {}", orderId);
		Account account = AccountUtils.getAccount();
		Order order = orderService.getOrder(orderId);
		String expressCode = order.getExpressCode();
		String expressCompanyName = order.getExpressCompanyName();
		String expressName = order.getExpressName();
		String expressNo = order.getExpressNo();
		if (OrderStatus.INIT.getCode().equals(order.getStatus())) {
			
		}
		logger.info("expressCode {}, expressCompanyName {}, expressName {}, expressNo {}", expressCode, expressCompanyName, expressName, expressNo);
		if (StringUtils.isBlank(expressCode)
		||  StringUtils.isBlank(expressName)
		||  StringUtils.isBlank(expressNo)) {
			return "redirect:/i/order";
		}
		List<ExpressCompany> ecList = commonService.getAllExpressCompany();
		Kuaidi100Info info = ShippingKit.getShippingInfo(expressCompanyName, expressNo);
		model.addAttribute("order", order);
		model.addAttribute("account", account);
		model.addAttribute("info", info);
		model.addAttribute("ecList", ecList);
		model.addAttribute("accountTitle", TITLE_SHIPPING_TRACE);
		return "modules/front/i/shipping/trace";
	}
	
	/**
	 * 8.78物流信息跟踪
	 */
	@RequiresUser
	@RequestMapping(value = "ship/trace", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Map<String, Object> shippingTraceAjax(@RequestParam("orderId") Long orderId, HttpServletRequest request, HttpServletResponse response) {
		logger.info("物流信息 orderId {}", orderId);
		Order order = orderService.getOrder(orderId);
		String expressCode = order.getExpressCode();
		String expressCompanyName = order.getExpressCompanyName();
		String expressName = order.getExpressName();
		String expressNo = order.getExpressNo();

		logger.info("expressCode {}, expressCompanyName {}, expressName {}, expressNo {}", expressCode, expressCompanyName, expressName, expressNo);
		if (StringUtils.isBlank(expressCode)
		||  StringUtils.isBlank(expressName)
		||  StringUtils.isBlank(expressNo)) {
			return buildReturnMap(Status.Success, ErrorMsg.NULL);
		}
		Kuaidi100Info info = ShippingKit.getShippingInfo(expressCompanyName, expressNo);
		logger.info("物流信息 {}", JsonMapper.getInstance().toJson(info));
		return buildReturnMap(Status.Success, JsonMapper.getInstance().toJson(info));
	}
	
	/**
	 * 9.1 我的收藏
	 */
	@RequiresUser
	@RequestMapping(value = "favorite", method = { RequestMethod.GET, RequestMethod.POST })
	public String favorite(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("我的收藏");
		Account account = AccountUtils.getAccount();
		int pageSize = StringUtils.isBlank(request.getParameter("pageSize")) ? 9 : new Integer(request.getParameter("pageSize"));
		int pageNo = StringUtils.isBlank(request.getParameter("pageNo")) ? 1 : new Integer(request.getParameter("pageNo"));
		PagerModel<Favorite> page = accountService.findPageFavorite(account.getId(),pageSize, pageNo);
		model.addAttribute("account", account);
		model.addAttribute("page", page);
		model.addAttribute("accountTitle", TITLE_FAVORITE);
		return "modules/front/i/product/favorite";
	}
	
	/**
	 * 9.2 我的收藏 - 删除
	 */
	@RequiresUser
	@RequestMapping(value = "favorite/delete", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> deleteFavorite(@RequestParam("id") Long id, HttpServletRequest request, HttpServletResponse response) {
		try {
			accountService.deleteFavorite(id);
			return buildReturnMap(Status.Success, ErrorMsg.NULL);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{}", e);
			return buildReturnMap(Status.Failure, e.getMessage());
		}
	}
	
	/**
	 * 10. 我的订阅
	 */
	@RequiresUser
	@RequestMapping(value = "account/subscribe", method = RequestMethod.GET)
	public String subscribe(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("我的订阅");
		model.addAttribute("accountTitle", TITLE_SUBSCRIBE);
		return "modules/front/i/account/subscribe";
	}
	
	/**
	 * 11. 企业认证
	 */
	@RequiresUser
	@RequestMapping(value = "account/enterpriseAuth", method = RequestMethod.GET)
	public String enterpriseAuth(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("企业认证");
		Account account = AccountUtils.getAccount();
		model.addAttribute("account", account);
		model.addAttribute("id", account.getId());
		model.addAttribute("accountName", account.getAccount());
		model.addAttribute("mobile", account.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
		String ua = request.getHeader("User-Agent");
		UserAgent userAgent = UserAgent.parseUserAgentString(ua);
		//验证是否已发起或通过个人认证,未认证需先提交个人认证
		Authentication personAuth = accountService.getAuthenticationByAccount(account.getId(), Authentication.PERSION_AUTH_TYPE);
		//TODO取消个人认证不认证没法进行企业认证
		/**
		if (personAuth == null) {
			model.addAttribute("accountTitle", "个人实名认证 &nbsp;&nbsp;&nbsp;请先提交个人实名认证再提交企业认证");
			if (DeviceType.TABLET.equals(userAgent.getOperatingSystem().getDeviceType())) {
				return "tablet/front/i/account/personAuth";
			} else {
				return "modules/front/i/account/personAuth";
			}
		}
		*/
		model.addAttribute("accountTitle", TITLE_ENTERPRISE_AUTH);
		//获取认证阶段
		Authentication auth = accountService.getAuthenticationByAccount(account.getId(), Authentication.ENTERPRISE_AUTH_TYPE);
		if (auth == null) {
			if (DeviceType.TABLET.equals(userAgent.getOperatingSystem().getDeviceType())) {
				return "tablet/front/i/account/enterpriseAuth";
			} else {
				return "modules/front/i/account/enterpriseAuth";
			}
		}
		//重新提交
		String recommit = request.getParameter("recommit");
		if (!StringUtils.isBlank(recommit) && "y".equals(recommit)) {
			//重新提交更新认证失败记录为废弃
			if (Authentication.REJECT_AUTH_STATUS == auth.getStatus()) {
				auth.setStatus(Authentication.ABANDON_AUTH_STATUS);
				accountService.updateAuthentication(auth);
				if (DeviceType.TABLET.equals(userAgent.getOperatingSystem().getDeviceType())) {
					return "tablet/front/i/account/enterpriseAuth";
				} else {
					return "modules/front/i/account/enterpriseAuth";
				}
			}
		}
		
		if (Authentication.REQUEST_AUTH_STATUS == auth.getStatus()) {
			model.addAttribute("status", Authentication.REQUEST_AUTH_STATUS);
			return "modules/front/i/account/waitingAuth";
		} else if (Authentication.PASSED_AUTH_STATUS == auth.getStatus()) {
			model.addAttribute("status", Authentication.PASSED_AUTH_STATUS);
			return "modules/front/i/account/waitingAuth";
		} else if (Authentication.REJECT_AUTH_STATUS == auth.getStatus()) {
			model.addAttribute("status", Authentication.REJECT_AUTH_STATUS);
			model.addAttribute("rejectReason", auth.getRejectReason());
			model.addAttribute("type", Authentication.ENTERPRISE_AUTH_TYPE);

			return "modules/front/i/account/waitingAuth";
		}  
//		return "modules/front/i/account/enterpriseAuth";
		
		if (DeviceType.TABLET.equals(userAgent.getOperatingSystem().getDeviceType())) {
			return "tablet/front/i/account/enterpriseAuth";
		} else {
			return "modules/front/i/account/enterpriseAuth";
		}
	}
	
	/**
	 * 12. 个人认证
	 */
	@RequiresUser
	@RequestMapping(value = "account/personAuth", method = RequestMethod.GET)
	public String personAuth(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("个人认证");
		model.addAttribute("accountTitle", TITLE_PERSON_AUTH);
		Account account = AccountUtils.getAccount();
		model.addAttribute("id", account.getId());
		model.addAttribute("accountName", account.getAccount());
		model.addAttribute("mobile", account.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
		model.addAttribute("account", account);
		String ua = request.getHeader("User-Agent");
		UserAgent userAgent = UserAgent.parseUserAgentString(ua);
		//获取认证阶段
		Authentication auth = accountService.getAuthenticationByAccount(account.getId(), Authentication.PERSION_AUTH_TYPE);
		if (auth == null) {
			if (DeviceType.TABLET.equals(userAgent.getOperatingSystem().getDeviceType())) {
				return "tablet/front/i/account/personAuth";
			} else {
				return "modules/front/i/account/personAuth";
			}
		}
		
		//重新提交
		String recommit = request.getParameter("recommit");
		if (!StringUtils.isBlank(recommit) && "y".equals(recommit)) {
			//重新提交更新认证失败记录为废弃
			if (Authentication.REJECT_AUTH_STATUS == auth.getStatus()) {
				if (DeviceType.TABLET.equals(userAgent.getOperatingSystem().getDeviceType())) {
					return "tablet/front/i/account/personAuth";
				} else {
					return "modules/front/i/account/personAuth";
				}
			}
		}
		
		if (Authentication.REQUEST_AUTH_STATUS == auth.getStatus()) {
			model.addAttribute("status", Authentication.REQUEST_AUTH_STATUS);
			return "modules/front/i/account/waitingAuth";
		} else if (Authentication.PASSED_AUTH_STATUS == auth.getStatus()) {
			model.addAttribute("status", Authentication.PASSED_AUTH_STATUS);
			return "modules/front/i/account/waitingAuth";
		} else if (Authentication.REJECT_AUTH_STATUS == auth.getStatus()) {
			model.addAttribute("status", Authentication.REJECT_AUTH_STATUS);
			model.addAttribute("type", Authentication.PERSION_AUTH_TYPE);
			model.addAttribute("rejectReason", auth.getRejectReason());
			return "modules/front/i/account/waitingAuth";
		}  
		
		if (DeviceType.TABLET.equals(userAgent.getOperatingSystem().getDeviceType())) {
			return "tablet/front/i/account/personAuth";
		} else {
			return "modules/front/i/account/personAuth";
		}
	}
	
	/**
	 * 12.1 证件号码验证
	 */
	@RequiresUser
	@RequestMapping(value = { "account/validateCardNo" }, method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> validateCardNo(@RequestParam("cardNo") String cardNo,@RequestParam("type") int type, HttpServletRequest request, HttpServletResponse response) {
		try {
			if (accountService.validateCardNoExsist(cardNo, type)) {
				return buildReturnMap(Status.Failure, "");
			}else {
				return buildReturnMap(Status.Success, "");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("{}", e.getMessage());
			return buildReturnMap(Status.Unknow, e.getMessage());
		}
	}
	
	/**
	 * 12.2 上传证件图片
	 */
	@RequestMapping(value = "account/uploadAuth", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> uploadAuthFile(MultipartHttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		Account account = systemFrontService.getAccountById(new Long(id));
		Iterator<String> fileNames = request.getFileNames();
		String fileName = null;
		if (fileNames.hasNext()) {
			fileName = fileNames.next();
		}
		if(StringUtils.isBlank(fileName)) {
			return buildReturnMap(Status.Failure, ErrorMsg.ImageNotFoundException);
		}
		
		MultipartFile file = request.getFile(fileName);
		String originalFileName = file.getOriginalFilename();
		String suffix = originalFileName.lastIndexOf(".") > 0 ? originalFileName.substring(originalFileName.lastIndexOf(".") + 1) : "";
		String newFileName = Identities.uuid2();
		
//		AliyunOSS oss = (AliyunOSS) request.getSession().getServletContext().getAttribute(Global.ALIYUN_OSS_CONFIG);
//		String accessKeyId = oss.getACCESS_ID();
//		String endpoint = "http://oss.aliyuncs.com/";
//		String accessKeySecret = oss.getACCESS_KEY();
//		String bucketName = oss.getBucketName();
//		OSSClient client = OSSKit.getOSSClient(endpoint, accessKeyId, accessKeySecret);
//		client.createBucket(bucketName);
//        client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
//		
//		String basePath = "upload/i/" + account.getId() + "/auth/";
//		
//		String key = basePath + newFileName + "." + suffix;
//		// 创建上传Object的Metadata
//		ObjectMetadata meta = new ObjectMetadata();
//		// 必须设置ContentLength
//		meta.setContentLength(file.getSize());
//		meta.setContentType(request.getContentType());
//		meta.setContentEncoding(request.getCharacterEncoding());
//		// 上传Object.
//		PutObjectResult result = client.putObject(bucketName, key, file.getInputStream(), meta);
//		
//		String url = Global.getImageRootPath() + "/" + key;//"http://" + bucketName+".oss-cn-hangzhou.aliyuncs.com/" + key;
		
		QiniuOSS oss = (QiniuOSS) request.getSession().getServletContext().getAttribute(Global.QINIU_OSS_CONFIG);
		String bucketName = oss.getBucketName();
		
		String basePath = "upload/i/" + account.getId() + "/auth/";
		String key = basePath + newFileName + "." + suffix;
		
		Config.ACCESS_KEY = oss.getACCESS_KEY();
        Config.SECRET_KEY = oss.getSECRET_KEY();
        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);

        PutPolicy putPolicy = new PutPolicy(bucketName);
        String uptoken = null;
		try {
			uptoken = putPolicy.token(mac);
		} catch (AuthException e) {
			return buildReturnMap(Status.Failure, ErrorMsg.AuthenticationException);
		} catch (JSONException e) {
			return buildReturnMap(Status.Failure, ErrorMsg.AuthenticationException);
		}
        PutExtra extra = new PutExtra();
        PutRet ret = IoApi.Put(uptoken, key, file.getInputStream(), extra);
        
		return buildReturnMap(Status.Success, key);
	}
	
	/**
	 * 12.3  保存认证信息
	 */
	@RequiresUser
	@RequestMapping(value = "account/saveAuth", method = RequestMethod.GET)
	public String saveAuth(HttpServletRequest request, HttpServletResponse response, Model model) {
		Account account = AccountUtils.getAccount();
		String cardName;
		try {
			cardName = URLDecoder.decode(request.getParameter("cardName"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			cardName = request.getParameter("cardName");
		}
		String cardNo = request.getParameter("cardNo");
		String type = request.getParameter("type");
		String authPicture = request.getParameter("authPicture");

		//重新提交标示
		String recommit = request.getParameter("recommit");
		if (!StringUtils.isBlank(recommit) && "y".equals(recommit)) {
			//重新提交更新认证失败记录为废弃
			Authentication auth = accountService.getAuthenticationByAccount(account.getId(), Integer.valueOf(type));
			if (Authentication.REJECT_AUTH_STATUS == auth.getStatus()) {
				auth.setStatus(Authentication.ABANDON_AUTH_STATUS);
				accountService.updateAuthentication(auth);
			}
		}
		Authentication auth = new Authentication(account.getId(), Integer.valueOf(type), cardNo, cardName, authPicture, null, new Date(), null, Authentication.REQUEST_AUTH_STATUS);
		accountService.saveAuth(auth);
		
		if (Integer.valueOf(type) == Authentication.PERSION_AUTH_TYPE) {
			return "redirect:/i/account/personAuth";
		} 
		return "redirect:/i/account/enterpriseAuth";
	}
	
	/**
	 * 上传图片
	 * 
	 * @param request
	 * @return
	 */
	@RequiresUser
	@RequestMapping(value = "account/auth/upload", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> uploadPic(HttpServletRequest request) {
		Map<String,Object> returnMap = Maps.newHashMap();
		boolean isSuccess = true;
		String msg = "";
		try {
//	        Member member = MemberUtils.getMember();
			Account account = AccountUtils.getAccount();
	        String data = request.getParameter("data");
	        String name = request.getParameter("name");
	        String type = request.getParameter("type");
	        Long size = StringUtils.isBlank(request.getParameter("size")) ? 0 : new Long(request.getParameter("size"));
	        String suffix = name.lastIndexOf(".") > 0 ? name.substring(name.lastIndexOf(".") + 1) : "";
	        String newFileName = Identities.uuid2();
	        logger.debug("before {}", data);
			if (data.indexOf("image/png;") >= 0) {
				suffix = "png";
			}
	        if (data.indexOf("base64,") >= 0) {
				data = data.substring(data.indexOf("base64,") + 7);
			}
			logger.info("");
			logger.debug("after {}", data);
//			BufferedImage img = ImageUtils.decodeToImage(data);
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] b = decoder.decodeBuffer(data);
			ByteArrayInputStream bis = new ByteArrayInputStream(b);
			
			QiniuOSS oss = (QiniuOSS) request.getSession().getServletContext().getAttribute(Global.QINIU_OSS_CONFIG);
			String bucketName = oss.getBucketName();
			
			String basePath = "upload/i/" + account.getId() + "/auth/";
			String key = basePath + newFileName + "." + suffix;
			
			Config.ACCESS_KEY = oss.getACCESS_KEY();
	        Config.SECRET_KEY = oss.getSECRET_KEY();
	        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);

	        PutPolicy putPolicy = new PutPolicy(bucketName);
	        String uptoken = null;
			try {
				uptoken = putPolicy.token(mac);
			} catch (AuthException e) {
				return buildReturnMap(Status.Failure, ErrorMsg.AuthenticationException);
			} catch (JSONException e) {
				return buildReturnMap(Status.Failure, ErrorMsg.AuthenticationException);
			}
	        PutExtra extra = new PutExtra();
	        PutRet ret = IoApi.Put(uptoken, key, bis, extra);
	        
//			MemberUtils.setMember(member);
//			returnMap.put("url", member.getPicture());
	        bis.close();
	        
	        return buildReturnMap(Status.Success, key);
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
			msg = e.getMessage();
			return buildReturnMap(Status.Failure, e.getMessage());
		}
    }
	
	/**
	 * 11. 我的邀请码
	 */
	@RequiresUser
	@RequestMapping(value = "invite", method = RequestMethod.GET)
	public String invite(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("我的邀请");
		Account account = AccountUtils.getAccount();
		boolean isMaster = Account.isMaster(account.getIsMaster());
		int pageSize = StringUtils.isBlank(request.getParameter("pageSize")) ? 10 : new Integer(request.getParameter("pageSize"));
		int pageNo = StringUtils.isBlank(request.getParameter("pageNo")) ? 0 : new Integer(request.getParameter("pageNo"));
		Map<String, Object> params = Maps.newHashMap();
		params.put("inviteId", account.getId());
		PagerModel<Account> page = accountService.getInviteeByAccount(params, pageNo, pageSize);
		
		//邀请地址
		String param = "from_code=" + account.getInviteCode() + "&from_mobile=" + account.getMobile() + "&channel=1";
		String inviteUrl = request.getScheme()+"://"+request.getServerName()+ ":" +request.getServerPort() + request.getContextPath() + "/regist?" + Identities.encodeBase64(param);
		model.addAttribute("inviteUrl", inviteUrl);
		
		//非大师不显示收益数据
		if (!isMaster) {
			for (Account invitee : page.getList()) {
				invitee.setProfit(null);
			}
		} 
		model.addAttribute("page", page);
		model.addAttribute("isMaster", isMaster);
		model.addAttribute("account", account);
		model.addAttribute("accountTitle", TITLE_SUBSCRIBE);
		return "modules/front/i/account/invite";
	}
	
	/**
	 * 11.1. 我的邀请收益
	 */
	@RequiresUser
	@RequestMapping(value = "inviteProfit", method = RequestMethod.GET)
	public String inviteProfit(HttpServletRequest request, HttpServletResponse response, Model model) {
		Account account = AccountUtils.getAccount();
		String aid = request.getParameter("aid");//被邀请人id
		if (!StringUtils.isBlank(aid)) {
			//验证改人员是否是此大师邀请
			Long inviteeId = (StringUtils.isBlank(aid)? null : Long.valueOf(aid));
			Account invitee = accountService.getAccountByInvite(inviteeId, account.getId());
			if (invitee != null) {
				if(Account.isMaster(account.getIsMaster())) {
					model.addAttribute("viewByMaster", true);
				}
				account = invitee;
			}
		}
		//统计邀请人数
		int count = accountService.countAccountInvite(account.getId());
		model.addAttribute("inviteTotal", count);
				
		int pageSize = StringUtils.isBlank(request.getParameter("pageSize")) ? 10 : new Integer(request.getParameter("pageSize"));
		int pageNo = StringUtils.isBlank(request.getParameter("pageNo")) ? 0 : new Integer(request.getParameter("pageNo"));
		//邀请地址
		String param = "from_code=" + account.getInviteCode() + "&from_mobile=" + account.getMobile() + "&channel=1";
		String inviteUrl = request.getScheme()+"://"+request.getServerName()+ ":" +request.getServerPort() + request.getContextPath() + "/regist?" + Identities.encodeBase64(param);
		model.addAttribute("inviteUrl", inviteUrl);
		//非大师不显示收益数据
		boolean isMaster = Account.isMaster(account.getIsMaster());
		if (isMaster) {
			//大师查询收益详情
			Map<String, Object> params = Maps.newHashMap();
			params.put("inviteId", account.getId());
			PagerModel<ProfitLog> profit = accountService.getProfitByAccount(params, pageNo, pageSize);
			model.addAttribute("profit", profit);
		}
		
		model.addAttribute("isMaster", isMaster);
		model.addAttribute("account", account);
		model.addAttribute("accountTitle", TITLE_SUBSCRIBE);
		return "modules/front/i/account/inviteProfit";
	}
	
	/**
	 * 11.2. 接收邀请
	 */
	@RequiresUser
	@RequestMapping(value = "invite/accept", method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> acceptInvite(String code, HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("接收邀请");
		Account account = AccountUtils.getAccount();
		code = code.toUpperCase();
		if (account.getInviteCode().equals(code)) {
			return buildReturnMap(Status.Failure, "自己不能邀请自己");
		}
		Account acc = systemFrontService.getAccountByInviteCode(code);
		if (acc == null) {
			return buildReturnMap(Status.Failure, "错误的邀请码");
		} else {
			account.setInviteId(acc.getId());
			systemFrontService.updateAccount(account);
			AccountUtils.setAccount(systemFrontService.getAccountById(account.getId()));
			return buildReturnMap(Status.Success, "");
		}
	}
	
	/**
	 * 12.1 我的预约单
	 */
	@RequiresUser
	@RequestMapping(value = { "booking", "booking/list" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String booking(HttpServletRequest request, HttpServletResponse response, Model model) {
		Account account = AccountUtils.getAccount();
		int pageSize = StringUtils.isBlank(request.getParameter("pageSize")) ? 5 : new Integer(request.getParameter("pageSize"));
		int pageNo = StringUtils.isBlank(request.getParameter("pageNo")) ? 0 : new Integer(request.getParameter("pageNo"));
		PagerModel<BookingOrder> page = accountService.findPageBookingOrder(account.getId(), 0, pageSize, pageNo);
		page.setPagerUrl("/i/booking/list");
		model.addAttribute("type", 0);
		model.addAttribute("page", page);
		model.addAttribute("account", account);
		model.addAttribute("accountTitle", TITLE_BOOKING_ORDER);
		return "modules/front/i/booking/list";
	}
	
	/**
	 * 12.2 我的预约单
	 */
	@RequiresUser
	@RequestMapping(value = "booking/{type}", method = { RequestMethod.GET, RequestMethod.POST })
	public String booking(@PathVariable("type") int type, HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("order list type {}", type);
		Account account = AccountUtils.getAccount();
		int pageSize = StringUtils.isBlank(request.getParameter("pageSize")) ? 5 : new Integer(request.getParameter("pageSize"));
		int pageNo = StringUtils.isBlank(request.getParameter("pageNo")) ? 0 : new Integer(request.getParameter("pageNo"));;
		PagerModel<BookingOrder> page = accountService.findPageBookingOrder(account.getId(), type, pageSize, pageNo);
		page.setPagerUrl("/i/booking/list" + type);
		model.addAttribute("type", type);
		model.addAttribute("page", page);
		model.addAttribute("account", account);
		model.addAttribute("accountTitle", TITLE_BOOKING_ORDER);
		return "modules/front/i/booking/list";
	}
	
	/**
	 * 12.3 取消预约单
	 */
	@RequiresUser
	@RequestMapping(value = "booking/cancel/{orderId}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> cancelBookingOrder(@PathVariable("orderId") Long orderId, HttpServletRequest request, HttpServletResponse response) {
		try {
			Account account = AccountUtils.getAccount();
			String reasonId = request.getParameter("reasonId");
			String reason = request.getParameter("reason");
			String reasonDesc = request.getParameter("reasonDesc");
			orderService.cancelBookingOrder(orderId, reasonId, reason, reasonDesc, account);
			return buildReturnMap(Status.Success, ErrorMsg.NULL);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{}", e);
			return buildReturnMap(Status.Failure, e.getMessage());
		}
	}
	
	/**
	 * 12.4 评价预约单
	 */
	@RequiresUser
	@RequestMapping(value = "booking/evaluate/{orderId}", method = RequestMethod.POST)
	public @ResponseBody
	Map<String, Object> evaluateBookingOrder(@PathVariable("orderId") Long orderId, HttpServletRequest request, HttpServletResponse response) {
		try {
			Account account = AccountUtils.getAccount();
			boolean isService = new Boolean(request.getParameter("isService"));
			String point = request.getParameter("point");
			String remarks = request.getParameter("remarks");
			logger.info("isService {}, point {}, remarks {}", isService, point, remarks);
			orderService.updateBookingOrderStatus(orderId,
					BookingOrder.STATUS_COMMENT);
			String description = "【客户评价】" + (isService ? "已" : "未")
					+ "上门服务，客户评分：" + point + "分，客户短评：" + remarks;
			orderService.saveBookingOrderLog(new BookingOrderLog(orderId, BookingOrderLog.ACTION_COMPLETE, description, account.getId(),
					account.getNickname(), BookingOrderLog.OPERATE_TYPE_FRONT));
			return buildReturnMap(Status.Success, ErrorMsg.NULL);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{}", e);
			return buildReturnMap(Status.Failure, e.getMessage());
		}
	}

	/**
	 * 99. 获得手机验证码
	 */
	@RequiresUser
	@RequestMapping(value = "account/getMcode", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody
	Map<String, Object> getMcode(@RequestParam("type") String type, HttpServletRequest request, HttpServletResponse response, Model model) {
		Date systemTime = new Date(System.currentTimeMillis());
		Account account = AccountUtils.getAccount();
		String mobile = request.getParameter("mobile");
		if (!StringUtils.isBlank(mobile)) {
			if (!ValidateUtils.isMobile(mobile)) {
				logger.info("请输入正确的手机号");
				return buildReturnMap(Status.Failure, "请输入正确的手机号");
			} else {
				if (NotifyType.Rebind.getType().equals(type)) {
					if (systemFrontService.getAccountByMobile(mobile) != null) {
						logger.info("手机号已存在");
						return buildReturnMap(Status.Failure, "手机号已存在");
					}
				}
			}
		} else {
			mobile = account.getMobile();
		}
		String template = systemFrontService.getNotifyTemplate(NotifyTemplate.TYPE_SMS, type);// 获得手机注册模板
		String mcode = Identities.randomValidateCode();
		systemFrontService.setMobileVerify(mobile, mcode);
		Map<String, String> m = Maps.newHashMap();
		m.put("mcode", mcode);
		String message = FreeMarkers.renderString(template, m);
		try {
			SMSClient client = SMSClientFactory.getClient(SmsPlatformType.CL.getType());
			String msg = client.sendSMS(mobile, message);
//			Sms sms = new Sms(mobile, message, type, "", "");
			SpringContextHolder.getApplicationContext().publishEvent(new SmsEvent(this, account, mobile, message, type, 
					SmsPlatformType.CL.getType(), Sms.STATUS_SUCCESS, msg, systemTime));
			if (!SMSUtils.checkReturnMsg(msg)) {
				return buildReturnMap(Status.Failure, ErrorMsg.SMSSendError);
			} else {
				return buildReturnMap(Status.Success, ErrorMsg.NULL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{}", e);
			SpringContextHolder.getApplicationContext().publishEvent(new SmsEvent(this, account, mobile, message, type, 
					SmsPlatformType.CL.getType(), Sms.STATUS_EXCEPTION, e.getMessage(), systemTime));
			return buildReturnMap(Status.Failure, e.getMessage());
		}
	}
	
	/**
	 * ajax加载我的收藏
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresUser
	@RequestMapping(value = "favorate/load", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxLoadFavorate(HttpServletRequest request, HttpServletResponse response) {
		Account account = AccountUtils.getAccount();
		Map<String, Object> data = Maps.newHashMap();
		int pageSize = StringUtils.isBlank(request.getParameter("pageSize")) ? 16 : new Integer(request.getParameter("pageSize"));
		int pageNo = StringUtils.isBlank(request.getParameter("pageNo")) ? 1 : new Integer(request.getParameter("pageNo"));
		PagerModel<Favorite> page = accountService.findPageFavorite(account.getId(),pageSize, pageNo);
		data.put("favorates", page.getList());
		return buildReturnMap(Status.Success, "", data);
	}
	
	/**
	 * ajax加载我的足迹
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresUser
	@RequestMapping(value = "history/load", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxLoadHistory(HttpServletRequest request, HttpServletResponse response) {
		logger.info("购物车");
		LinkedHashMap<Long, Product> historyMap = (LinkedHashMap<Long, Product>) request.getSession().getAttribute(Product.PRODUCT_HISTORY_LIST);
		Map<String, Object> data = Maps.newHashMap();
		data.put("historys", historyMap.values());
		return buildReturnMap(Status.Success, "", data);
	}
}
