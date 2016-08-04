package com.tuisitang.modules.shop.api.web;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;

import com.aliyun.oss.common.utils.DateUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tuisitang.common.bo.Status;
import com.tuisitang.common.mapper.JsonMapper;
import com.tuisitang.common.utils.DateUtils;
import com.tuisitang.common.utils.Encodes;
import com.tuisitang.common.utils.SpringContextHolder;
import com.tuisitang.common.utils.StringUtils;
import com.tuisitang.modules.shop.api.bo.Authorization;
import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.entity.Address;
import com.tuisitang.modules.shop.entity.Cart;
import com.tuisitang.modules.shop.entity.Favorite;
import com.tuisitang.modules.shop.entity.IndexImg;
import com.tuisitang.modules.shop.entity.Order;
import com.tuisitang.modules.shop.entity.Orderdetail;
import com.tuisitang.modules.shop.entity.Product;
import com.tuisitang.modules.shop.utils.Global;

/**    
 * @{#} BaseApiController.java   
 * 
 * Api基础Controller
 * 
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: irtone</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 *  
 */
public abstract class BaseApiController {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public static final String SUCCESS = "0";// 成功
	public static final String FAILURE = "1";// 成功
	
	protected java.text.DecimalFormat  df = new java.text.DecimalFormat("#0.00");  
	
	/**
	 * 构建返回信息
	 * 
	 * @param isSuccess
	 * @param errMsg
	 * @return
	 */
	public Map<String, Object> buildReturnMap(String isSuccess, String errMsg) {
		Map<String, Object> returnMap = Maps.newHashMap();
		returnMap.put("s", isSuccess);
		returnMap.put("m", errMsg);
		return returnMap;
	}
	

	/**
	 * 创建返回对象
	 * 
	 * @param status
	 * @param msg
	 * @return
	 */
	protected Map<String, Object> buildReturnMap(Status status, String msg) {
		Map<String, Object> returnMap = Maps.newHashMap();
		returnMap.put("s", status.getStatus());
		returnMap.put("m", msg);
		return returnMap;
	}
	
	public Map<String, Object> buildReturnMap(String isSuccess, String errMsg, Map<String, Object> paramMap) {
		Map<String, Object> returnMap = Maps.newHashMap();
		returnMap.put("s", isSuccess);
		returnMap.put("m", errMsg);
		returnMap.put("p", paramMap);
		return returnMap;
	}
	
	/**
	 * 构建JSON
	 * 
	 * @param callback
	 * @param returnMap
	 * @return
	 */
	protected String buildJson(String callback, Map<String, Object> returnMap) {
//		JsonMapper mapper = JsonMapper.getInstance();
		JsonMapper mapper = JsonMapper.nonEmptyMapper();
		if (!StringUtils.isBlank(callback)) {
			return mapper.toJsonP(callback, returnMap);
		} else {
			return mapper.toJson(returnMap);
		}
	}
	
	/**
	 * 派发事件
	 * 
	 * @param event
	 */
	protected void publishEvent(ApplicationEvent event) {
		logger.info("派发事件 {}", event);
		SpringContextHolder.getApplicationContext().publishEvent(event);
	}
	
	/**
	 * IndexImg转简单的列表
	 * 
	 * @param request
	 * @param indexImgList
	 * @return
	 */
	protected List<Map<String, Object>> indexImgListToMapList(HttpServletRequest request, List<IndexImg> indexImgList) {
		String IMAGE_ROOT_PATH = (String) request.getSession().getServletContext().getAttribute(Global.IMAGE_ROOT_PATH);
		List<Map<String, Object>> list = Lists.newArrayList();
		for (IndexImg indexImg : indexImgList) {
			Map<String, Object> m = Maps.newHashMap();
//			m.put("id", indexImg.getId());
			m.put("title", indexImg.getTitle());
			m.put("sort", indexImg.getSort());
			m.put("picture", IMAGE_ROOT_PATH + "/" + indexImg.getPicture());
			list.add(m);
		}
		return list;
	}
	
	/**
	 * 产品列表转换成简单的产品列表
	 * 
	 * @param request
	 * @param productList
	 * @return
	 */
	protected List<Map<String, Object>> productListToMapList(HttpServletRequest request, List<Product> productList) {
		String IMAGE_ROOT_PATH = (String) request.getSession().getServletContext().getAttribute(Global.IMAGE_ROOT_PATH);
		List<Map<String, Object>> list = Lists.newArrayList();
		for (Product product : productList) {
			Map<String, Object> m = Maps.newHashMap();
			m.put("id", product.getId());
			m.put("name", product.getName());
			m.put("price", product.getNowPrice());
			m.put("sellCount", product.getSellcount());
			m.put("picture", IMAGE_ROOT_PATH + product.getPicture() + "-px350");
			list.add(m);
		}
		return list;
	}
	
	
	/**
	 * 购物车列表转换成简单的购物车列表
	 * 
	 * @param request
	 * @param productList
	 * @return
	 */
	protected Map<String, Object> cartListToMapList(HttpServletRequest request, Cart cart) {
		String IMAGE_ROOT_PATH = (String) request.getSession().getServletContext().getAttribute(Global.IMAGE_ROOT_PATH);
		Map<String, Object> m = Maps.newHashMap();
		m.put("id", cart.getId());
		m.put("productName", cart.getProductName());
		m.put("quantity", cart.getQuantity());
		m.put("productId", cart.getProductId());
		m.put("specId", cart.getSpecId());
		m.put("specName", cart.getSpecName());
		m.put("sellerId", cart.getSellerId());
		m.put("sellerName", cart.getSellerName());
		m.put("price", cart.getNowPrice());
		m.put("status", true);
		m.put("marketPrice", cart.getMarketPrice());
		m.put("picture", IMAGE_ROOT_PATH + cart.getProductImage() + "-px100");
		return m;
	}
	
	/**
	 * 账户信息转换为精简账户信息
	 * 
	 * @param request
	 * @param productList
	 * @return
	 */
	protected Map<String, Object> accountToMap(HttpServletRequest request, Account account) {
		String IMAGE_ROOT_PATH = (String) request.getSession().getServletContext().getAttribute(Global.IMAGE_ROOT_PATH);
		Map<String, Object> m = Maps.newHashMap();
		m.put("id", account.getId());
		m.put("mobile", account.getMobile());
		m.put("nickname", account.getAccount());
		m.put("sex", StringUtils.isBlank(account.getSex()) ? "s" : account.getSex());
		String birthday = "";
		if (account.getBirthday() != null) {
			birthday = DateUtils.formatDate(account.getBirthday(), "yyyy-MM-dd");
		}
		m.put("birthday", birthday);
		m.put("picture", IMAGE_ROOT_PATH + account.getPicture() + "-px350");
		m.put("rank", account.getRank());
		m.put("authStatus", account.getAuthStatus());
		m.put("isMaster", account.getIsMaster());
		return m;
	}
	
	/**
	 * 地址信息转换为精简账户信息
	 * 
	 * @param request
	 * @param productList
	 * @return
	 */
	protected Map<String, Object> addressToMap(Address address) {
		Map<String, Object> m = Maps.newHashMap();
		m.put("id", address.getId());
		m.put("name", address.getName());
		m.put("provinceCode", address.getProvinceCode());
		m.put("provinceName", address.getProvinceName());
		m.put("cityCode", address.getCityCode());
		m.put("cityName", address.getCityName());
		m.put("countyCode", address.getCountyCode());
		m.put("countyName", address.getCountyName());
		m.put("address", address.getAddress());
		m.put("mobile", address.getMobile());
		m.put("isDefault", address.getIsDefault());
		return m;
	}
	
	/**
	 * 地址信息转换为精简账户信息
	 * 
	 * @param request
	 * @param productList
	 * @return
	 */
	protected Map<String, Object> favoriteToMap(HttpServletRequest request, Favorite favorite) {
		String IMAGE_ROOT_PATH = (String) request.getSession().getServletContext().getAttribute(Global.IMAGE_ROOT_PATH);
		Map<String, Object> m = Maps.newHashMap();
		m.put("id", favorite.getId());
		m.put("productId", favorite.getProduct().getId());
		m.put("name", favorite.getProduct().getName());
		m.put("picture", IMAGE_ROOT_PATH + favorite.getProduct().getPicture() + "-px350");
		m.put("price", favorite.getProduct().getNowPrice());
		return m;
	}
	
	/**
	 * 订单信息转换为精简订单信息
	 * 
	 * @param request
	 * @param productList
	 * @return
	 */
	protected Map<String, Object> orderToMap(HttpServletRequest request, Order order) {
		Map<String, Object> m = Maps.newHashMap();
		m.put("id", order.getId());
		m.put("no", order.getNo());
		m.put("status", order.getStatus());
		m.put("amount", order.getAmount());
		m.put("fee", order.getFee());
		m.put("ptotal", order.getPtotal());
		m.put("quantity", order.getQuantity());
		m.put("payCode", order.getPayCode());
		m.put("remark", order.getRemark());
		m.put("score", order.getScore());
		m.put("ods", orderDetailListToMapList(request, order.getOdList()));
		return m;
	}
	
	/**
	 * 订单列表转换成简单的订单列表
	 * 
	 * @param request
	 * @param productList
	 * @return
	 */
	protected List<Map<String, Object>> orderDetailListToMapList(HttpServletRequest request, List<Orderdetail> orderDetailList) {
		String IMAGE_ROOT_PATH = (String) request.getSession().getServletContext().getAttribute(Global.IMAGE_ROOT_PATH);
		List<Map<String, Object>> list = Lists.newArrayList();
		for (Orderdetail detail : orderDetailList) {
			Map<String, Object> m = Maps.newHashMap();
			m.put("id", detail.getId());
			m.put("name", detail.getProductName());
			m.put("price", detail.getPrice());
			m.put("quantity", detail.getNumber());
			m.put("specName", detail.getSpecInfo());
			m.put("picture", IMAGE_ROOT_PATH + detail.getPicture() + "-px350");
			list.add(m);
		}
		return list;
	}
	
	protected Authorization getAuthorization(String authorization) {
		if (StringUtils.isBlank(authorization)) {
			logger.error("认证失败 authorization is null");
			return new Authorization("1", "认证失败");
		}
		if (authorization.indexOf("Basic") >= 0) {
			authorization = authorization.substring(5).trim();
		}
		try {
			authorization = new String(Encodes.decodeBase64(authorization), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			return new Authorization("1", "解析授权信息失败");
		}
		String[] ss = authorization.split(":");
		if (ss.length < 2) {
			return new Authorization("1", "认证失败");
		}
		String token = ss[0];
		String mobile = ss[1];
		
		return new Authorization(token, mobile, "0", null);
	}
}