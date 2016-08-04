package com.tuisitang.modules.shop.api.web;



import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jeeshop.core.dao.page.PagerModel;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;
import com.tuisitang.common.bo.Status;
import com.tuisitang.common.utils.DateUtils;
import com.tuisitang.common.utils.Identities;
import com.tuisitang.modules.shop.api.bo.Authorization;
import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.entity.Address;
import com.tuisitang.modules.shop.entity.Area;
import com.tuisitang.modules.shop.entity.Favorite;
import com.tuisitang.modules.shop.entity.Order;
import com.tuisitang.modules.shop.entity.Orderdetail;
import com.tuisitang.modules.shop.entity.Product;
import com.tuisitang.modules.shop.entity.QiniuOSS;
import com.tuisitang.modules.shop.service.AccountService;
import com.tuisitang.modules.shop.service.CommonService;
import com.tuisitang.modules.shop.service.OrderService;
import com.tuisitang.modules.shop.service.ProductService;
import com.tuisitang.modules.shop.service.SystemService;
import com.tuisitang.modules.shop.utils.Global;

/**    
 * @{#} AccountApiController.java   
 * 
 * 会员 Api Controller
 * 
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: irtone</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 *  
 */
@Controller
@RequestMapping(value = "/api/account")
public class AccountApiController extends BaseApiController {
	
	@Autowired
	private SystemService systemService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private ProductService productService;

//	/**
//	 * 1. subscribe
//	 * 订阅，保存设备信息
//	 * curl --insecure -X POST -H "Content-Type: application/json" -d "{\"token\":\"111\",\"mode\":\"iPhone\"}" https://127.0.0.1:8088/shop-api/api/account/subscribe | iconv -f utf-8 -t gbk
//	 * curl -X POST -H "Content-Type: application/json" -d "{\"token\":\"111\",\"mode\":\"iPhone\"}" http://127.0.0.1:8088/shop-api/api/account/subscribe | iconv -f utf-8 -t gbk
//	 */
//	@RequestMapping(value = "subscribe", method = RequestMethod.POST)
//	public @ResponseBody String subscribe(@RequestParam(value = "serialId", required = true) String serialId,
//			@RequestParam(value = "token", required = true) String token,
//			@RequestParam(value = "alias", required = false) String alias,
//			@RequestParam(value = "mode", required = true) String mode,
//			@RequestParam(value = "type", required = false) String type,
//			@RequestParam(value = "os", required = true) String os,
//			@RequestParam(value = "osVersion", required = true) String osVersion,
//			@RequestHeader(value = "Authorization", required = false) String authorization,
//			HttpServletRequest request, HttpServletResponse response) {
//		Date sysTime = new Date(System.currentTimeMillis());
//		logger.info("serialId=" + serialId + ",token=" + token + ",alias="
//				+ alias + ",mode=" + mode + ",type=" + type + ",os=" + os
//				+ ",osVersion=" + osVersion);
//		JsonMapper mapper = JsonMapper.getInstance();
//		try {
//			Device device = new Device(serialId, token, "", alias, mode, type,
//					os, osVersion, "0");
//			device.setCreateTime(sysTime);
//			logger.info("authorization {}", authorization);
////			systemService.saveDevice(device);
//			logger.info("device {}", device);
//			return mapper.toJson(buildReturnMap(SUCCESS, ""));
//		} catch (Exception e) {
//			e.printStackTrace();
//			return mapper.toJson(buildReturnMap(FAILURE, e.getMessage()));
//		}
//	}
//	
	
	/**
	 *个人中心首页
	 * @param authorization
	 * @param callback
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "index", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Map<String, Object> index(
			@RequestHeader(value = "Authorization", required = true) String authorization, 
			@RequestParam(required = false) String callback,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("购物车");
		Authorization auth = getAuthorization(authorization);
		if ("1".equals(auth.getErrorCode())) {
			return buildReturnMap(auth.getErrorCode(), auth.getErrorMsg());
		}
		Account account = systemService.getAccountByMobile(auth.getMobile());					
		Map<String, Object> paramMap = Maps.newHashMap();
		
		Map<String, Object> accountMap = accountToMap(request, account);
		paramMap.put("account", accountMap);
		//待付款
		int init = orderService.countOrderStatus(account.getId(), 1);
		//已完成
		int finish = orderService.countOrderStatus(account.getId(), 2);
		//已取消
		int cancel = orderService.countOrderStatus(account.getId(), 3);
		//已发货
		int send = orderService.countOrderStatus(account.getId(), 4);
		paramMap.put("initStatus", init);
		paramMap.put("finishStatus", finish);
		paramMap.put("cancelStatus", cancel);
		paramMap.put("sendStatus", send);
		return paramMap;
	}
	
	/**
	 * .上传图片
	 */
	@RequestMapping(value = "upload", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> uploadFile(
			@RequestHeader(value = "Authorization", required = true) String authorization, 
			MultipartHttpServletRequest request, HttpServletResponse response) throws IOException {
		Authorization auth = getAuthorization(authorization);
		if ("1".equals(auth.getErrorCode())) {
			return buildReturnMap(auth.getErrorCode(), auth.getErrorMsg());
		}
		Account account = systemService.getAccountByMobile(auth.getMobile());
		Iterator<String> fileNames = request.getFileNames();
		String fileName = null;
		if (fileNames.hasNext()) {
			fileName = fileNames.next();
		}
		if(StringUtils.isBlank(fileName)) {
			return buildReturnMap(FAILURE, "无上传图片");
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
			return buildReturnMap(FAILURE, "图片服务器授权失败");
		} catch (JSONException e) {
			return buildReturnMap(FAILURE, "图片上传失败");
		}
        PutExtra extra = new PutExtra();
        PutRet ret = IoApi.Put(uptoken, key, file.getInputStream(), extra);
        
		account.setPicture(key);
		account.setSmallPicture(key);
		systemService.updateAccount(account);
		return buildReturnMap(Status.Success, key);
	}
	
	/**
	 * 保存个人信息
	 */
	@RequestMapping(value = { "profile" }, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> profile(
			@RequestHeader(value = "Authorization", required = true) String authorization, 
			HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		logger.info("save profile");
		Authorization auth = getAuthorization(authorization);
		if ("1".equals(auth.getErrorCode())) {
			return buildReturnMap(auth.getErrorCode(), auth.getErrorMsg());
		}
		Account account = systemService.getAccountByMobile(auth.getMobile());

		String username = request.getParameter("username") ;
		String birthdayYear = request.getParameter("birthday_year");
		String birthdayMonth = request.getParameter("birthday_month");
		String birthdayDay = request.getParameter("birthday_day");
		String gender = request.getParameter("gender");
		logger.info("username = {} \ngender = {} \nbirthdayYear = {} \nbirthdayMonth {} \nbirthdayDay {} ", 
				username, gender, birthdayYear, birthdayMonth, birthdayDay);
		boolean updateFlag = false;//更新account标示
		if (!StringUtils.isBlank(username) && (username.trim().length() < 4 || username.trim().length() > 16)) {
			logger.info("应为4-16个中英文字符，不能以数字开头");
			return buildReturnMap(FAILURE, "昵称应为4-16个中英文字符，不能以数字开头");
		} else {
			account.setAccount(username);
			updateFlag = true;
		}
		if (!StringUtils.isBlank(birthdayYear)
		&& !StringUtils.isBlank(birthdayMonth)
		&& !StringUtils.isBlank(birthdayDay)) {
			Date birthday = DateUtils.parseDate(birthdayYear + "/" + birthdayMonth + "/" + birthdayDay);
			account.setBirthday(birthday);
			updateFlag = true;
		} 
		if (!StringUtils.isBlank(gender)) {
			account.setSex(gender);
			updateFlag = true;
		}
		if (updateFlag) {
			systemService.updateAccount(account);
		}
		return buildReturnMap(SUCCESS, "");
	}
	
	@RequestMapping(value = "order", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Map<String, Object> order( 
		@RequestHeader(value = "Authorization", required = true) String authorization, 
		HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("购物车");
		Authorization auth = getAuthorization(authorization);
		if ("1".equals(auth.getErrorCode())) {
			return buildReturnMap(auth.getErrorCode(), auth.getErrorMsg());
		}
		Account account = systemService.getAccountByMobile(auth.getMobile());					
		Map<String, Object> paramMap = Maps.newHashMap();
		
		int type = StringUtils.isBlank(request.getParameter("type")) ? 0 : new Integer(request.getParameter("type"));
		
		int pageSize = StringUtils.isBlank(request.getParameter("pageSize")) ? 5 : new Integer(request.getParameter("pageSize"));
		int pageNo = StringUtils.isBlank(request.getParameter("pageNo")) ? 0 : new Integer(request.getParameter("pageNo"));;
		PagerModel<Order> page = accountService.findPageOrder(account.getId(), type, pageSize, pageNo);
		List<Map<String, Object>> list = Lists.newArrayList();
		for (Order order : page.getList()) {
			Map<String, Object> orderMap = orderToMap(request, order);
			list.add(orderMap);
		}
		paramMap.put("list", list);
		return paramMap;
	}
	
	/**
	 * 个人地址列表
	 * @param authorization
	 * @param callback
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "addrs", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Map<String, Object> addrList(
			@RequestHeader(value = "Authorization", required = true) String authorization, 
			@RequestParam(required = false) String callback,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("购物车");
		Authorization auth = getAuthorization(authorization);
		if ("1".equals(auth.getErrorCode())) {
			return buildReturnMap(auth.getErrorCode(), auth.getErrorMsg());
		}
		Account account = systemService.getAccountByMobile(auth.getMobile());					
		Map<String, Object> paramMap = Maps.newHashMap();
		
		List<Address> addrs = accountService.findAddressByAccountId(account.getId());
		List<Map<String, Object>> list = Lists.newArrayList();
		for (Address address : addrs) {
			Map<String, Object> addrMap = addressToMap(address);
			list.add(addrMap);
		}
		paramMap.put("list", list);
		return paramMap;
	}
	
	/**
	 * 全国地区编码
	 * @param authorization
	 * @param callback
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "areas", method = { RequestMethod.GET})
	public @ResponseBody String areaList(
			@RequestParam(required = false) String callback,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> paramMap = Maps.newHashMap();
		List<Area> areas = commonService.getAllArea();
		paramMap.put("list", areas);
		return buildJson(callback, paramMap);
	}
	
	/**
	 * 6.2 保存收货地址
	 */
	@RequestMapping(value = { "addr/save" }, method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addresses(
			@RequestHeader(value = "Authorization", required = true) String authorization, 
			HttpServletRequest request, HttpServletResponse response) {
		Authorization auth = getAuthorization(authorization);
		if ("1".equals(auth.getErrorCode())) {
			return buildReturnMap(auth.getErrorCode(), auth.getErrorMsg());
		}
		Account account = systemService.getAccountByMobile(auth.getMobile());		
		
		Long id = StringUtils.isBlank(request.getParameter("id"))?null : new Long(request.getParameter("id"));
		String name = request.getParameter("name") ;
		Long provinceId = StringUtils.isBlank(request.getParameter("provinceId")) ? null : new Long(request.getParameter("provinceId"));
//		String provinceName = request.getParameter("provinceName");
		Long cityId = StringUtils.isBlank(request.getParameter("cityId")) ? null : new Long(request.getParameter("cityId"));
//		String cityName = request.getParameter("cityName");
		Long countyId = StringUtils.isBlank(request.getParameter("countyId")) ? null : new Long(request.getParameter("countyId"));
//		String countyName = request.getParameter("countyName");
		String address = request.getParameter("address");
		String mobile = request.getParameter("mobile");
//		String telArea = request.getParameter("telArea");
//		String telNumber = request.getParameter("telNumber");
//		String telExt = request.getParameter("telExt");
//		String zip = request.getParameter("zip");
		
		logger.info("id = {} \nname = {} \nprovinceId = {} \nprovinceName {} \ncityId = {} \ncityName {} \ncountyId = {} \ncountyName {} \naddress {} \nmobile {} \ntelArea {} \ntelNumber {} \ntelExt {} \nzip {}",
				id, name, provinceId, cityId, countyId, address, mobile);
		if (StringUtils.isBlank(name)) {
			logger.error("请输入收货人姓名");
			return buildReturnMap("1", "请输入收货人姓名"); 
		}
		if (provinceId == null || cityId == null || countyId == null) {
			logger.error("请先选择完整收货地址");
			return buildReturnMap("1", "请先选择完整收货地址"); 
		}
		if (StringUtils.isBlank(address)) {
			logger.error("详细地址不能为空");
			return buildReturnMap("1", "详细地址不能为空"); 
		}
		if (StringUtils.isBlank(mobile)) {
			logger.error("请填写手机号码");
			return buildReturnMap("1", "请填写手机号码"); 
		}
		Area province = commonService.getArea(provinceId);
		Area city = commonService.getArea(cityId);
		Area county = commonService.getArea(countyId);
		String phone = null;
//		if (!StringUtils.isBlank(telNumber)) {
//			if (StringUtils.isBlank(telArea)) {
//				logger.error("固定号码请填写区号信息");
//				return buildReturnMap(Status.Failure, "固定号码请填写区号信息"); 
//			}
//			phone = telArea + "-" + telNumber;
//			if (!StringUtils.isBlank(telExt)) {
//				phone = phone + "-" + telExt;
//			}
//		}
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
//			a.setPhone(phone);
//			a.setZip(zip);
			accountService.saveAddress(a);
		} else {
			a = accountService.getAddress(id);
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
//			a.setPhone(phone);
//			a.setZip(zip);
			accountService.updateAddress(a);
		}
		return buildReturnMap("0", a.getId().toString());
	}
	
	/**
	 * 6.4 删除Address信息
	 */
	@RequestMapping(value = { "addr/delete" }, method = RequestMethod.POST)
	public @ResponseBody Map<String,Object> deleteAddress(
				@RequestHeader(value = "Authorization", required = true) String authorization, 
				HttpServletRequest request, HttpServletResponse response) {
		try {
			Long id = StringUtils.isBlank(request.getParameter("id"))?null : new Long(request.getParameter("id"));
			Authorization auth = getAuthorization(authorization);
			if ("1".equals(auth.getErrorCode())) {
				return buildReturnMap(auth.getErrorCode(), auth.getErrorMsg());
			}
			accountService.deleteAddress(id);
			return buildReturnMap("0", "");
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("{}", e.getMessage());
			return buildReturnMap("1", e.getMessage());
		}
	}
	
	/**
	 * 9.1 我的收藏
	 */
	@RequestMapping(value = "favorite", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Map<String, Object> favorite(
			@RequestHeader(value = "Authorization", required = true) String authorization, 
			HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("我的收藏");
		Authorization auth = getAuthorization(authorization);
		if ("1".equals(auth.getErrorCode())) {
			return buildReturnMap(auth.getErrorCode(), auth.getErrorMsg());
		}
		Account account = systemService.getAccountByMobile(auth.getMobile());		
		int pageSize = StringUtils.isBlank(request.getParameter("pageSize")) ? 9 : new Integer(request.getParameter("pageSize"));
		int pageNo = StringUtils.isBlank(request.getParameter("pageNo")) ? 1 : new Integer(request.getParameter("pageNo"));
		PagerModel<Favorite> page = accountService.findPageFavorite(account.getId(),pageSize, pageNo);
		Map<String, Object> paramMap = Maps.newHashMap();
		List<Map<String, Object>> list = Lists.newArrayList();
		for (Favorite favorite : page.getList()) {
			Map<String, Object> favoriteMap = favoriteToMap(request, favorite);
			list.add(favoriteMap);
		}
		paramMap.put("list", list);
		return paramMap;
	}
	
	/**
	 * 9.2 我的收藏 - 删除
	 */
	@RequestMapping(value = "favorite/delete", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> deleteFavorite(
			@RequestHeader(value = "Authorization", required = true) String authorization, 
			@RequestParam(value = "id", required = true) Long id, HttpServletRequest request, HttpServletResponse response) {
		try {
			Authorization auth = getAuthorization(authorization);
			if ("1".equals(auth.getErrorCode())) {
				return buildReturnMap(auth.getErrorCode(), auth.getErrorMsg());
			}
			
			accountService.deleteFavorite(id);
			return buildReturnMap("0", "");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{}", e);
			return buildReturnMap("1", e.getMessage());
		}
	}
	
	/**
	 * 9.3添加产品到收藏
	 */
	@RequestMapping(value = "favorite/add", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addToFavorite(
			@RequestHeader(value = "Authorization", required = true) String authorization, 
			@RequestParam(value = "goodsId", required = true) Long productId,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		Authorization auth = getAuthorization(authorization);
		if ("1".equals(auth.getErrorCode())) {
			return buildReturnMap(auth.getErrorCode(), auth.getErrorMsg());
		}
		Account account = systemService.getAccountByMobile(auth.getMobile());	
		Favorite favorite = new Favorite(productId, account.getId());
		if (productService.isSavedInFavorite(favorite)) {
			return buildReturnMap("0", "该商品已经被收藏");
		} else {
			Product product = productService.getProductById(productId);
			favorite.setProductName(product.getName());
			favorite.setCreateTime(new Date());
			productService.saveFavorite(favorite);
			return buildReturnMap("0", "添加收藏成功");
		}
	}
	
	
}
