package com.tuisitang.modules.shop.utils;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;
import com.tuisitang.common.utils.Encodes;
import com.tuisitang.common.utils.PropertiesLoader;

/**
 * 全局配置类
 */
public class Global {
	
	private static final Logger logger = LoggerFactory.getLogger(Global.class);
	
	public static final String IMAGE_ROOT_PATH = "IMAGE_ROOT_PATH";// 图片根路径
	
	public static final String SYSTEM_SETTING = "SYSTEM_SETTING";// 系统设置
	
	public static final String PRODUCT_CATALOG_LIST = "PRODUCT_CATALOG_LIST";// 商品目录列表
	
	public static final String PRODUCT_CATALOG_HTML = "PRODUCT_CATALOG_HTML";// 商品目录 HTML
	
	public static final String ARTICLE_CATALOG_LIST = "ARTICLE_CATALOG_LIST";// 文章目录列表
	
	public static final String ALIYUN_OSS_CONFIG = "ALIYUN_OSS_CONFIG";// Aliyun OSS配置信息
	
	public static final String QINIU_OSS_CONFIG = "QINIU_OSS_CONFIG";// 七牛OSS配置信息
	
	public static final String SELLER_EMAIL = "1877534934@qq.com";// 支付宝邮箱账号
	
	public static final String INDEX_TOP_AD = "INDEX_TOP_AD";// 顶部广告
	
	public static final String HOTQUERY_LIST = "HOTQUERY_LIST";// 热门查询列表
	
	public static final String SEARCH_LIST = "SEARCH_LIST";// Auto Complete查询列表
	
	public static final String SESSION_WECHAT_OPENID = "WECHAT_OPENID";// 存放恶习
	
	public static final String AREA_PROVINCE_ALL = "AREA_PROVINCE_ALL";// 存放所有省份
	
	public static final String AREA_CITY = "AREA_CITY_";// 存放地市
	
	/**
	 * 保存全局属性值
	 */
	private static Map<String, String> map = Maps.newHashMap();
	
	/**
	 * 属性文件加载对象
	 */
	private static PropertiesLoader propertiesLoader = new PropertiesLoader("application.properties", "wechat.properties");
	
	/**
	 * 获取前端根路径
	 */
	public static String getFrontPath() {
		return getConfig("frontPath");
	}
	
	/**
	 * 获取URL后缀
	 */
	public static String getUrlSuffix() {
		return getConfig("urlSuffix");
	}
	
	/**
	 * 获取配置
	 */
	public static String getConfig(String key) {
		String value = map.get(key);
		if (value == null){
			value = propertiesLoader.getProperty(key);
			map.put(key, value);
		}
		return value;
	}
	
	/**
	 * 是否是演示模式，演示模式下不能修改用户、角色、密码、菜单、授权
	 */
	public static Boolean isDemoMode() {
		String dm = getConfig("demoMode");
		return "true".equals(dm) || "1".equals(dm);
	}

	/////////////////////////////////////////////////////////
	
	/**
	 * 获取管理端根路径
	 */
	public static String getAdminPath() {
		return getConfig("adminPath");
	}
	
	/**
	 * 获取图片根链接属性
	 */
	public static String getImageRootPath() {
		return getConfig("imageRootPath");
	}
	
	// ** Alipay 支付宝
	/**
	 * Alipay Partner
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getAlipayPartner() throws UnsupportedEncodingException {
		return Encodes.rc4(new String(Encodes.decodeBase64(getConfig("AlipayPartner")), "utf-8"),
				getConfig("AppKey"));
	}
	
	/**
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getAlipaySellerEmail() throws UnsupportedEncodingException {
		return Encodes.rc4(new String(Encodes.decodeBase64(getConfig("AlipaySellerEmail")), "utf-8"),
				getConfig("AppKey"));
	}
	
	/**
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getAlipayKey() throws UnsupportedEncodingException {
		return Encodes.rc4(new String(Encodes.decodeBase64(getConfig("AlipayKey")), "utf-8"),
				getConfig("AppKey"));
	}
	
	/**
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getAlipayPrivateKey() throws UnsupportedEncodingException {
		return Encodes.rc4(new String(Encodes.decodeBase64(getConfig("AlipayPrivateKey")), "utf-8"),
				getConfig("AppKey"));
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getAlipayPublicKey() {
		return "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	}
	
	// SMS 配置
	/**
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getSmsSerialKey() throws UnsupportedEncodingException {
		return Encodes.rc4(new String(Encodes.decodeBase64(getConfig("smsSerialKey")), "utf-8"),
				getConfig("AppKey"));
	}
	
	/**
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getSmsSerialPassword() throws UnsupportedEncodingException {
		return Encodes.rc4(new String(Encodes.decodeBase64(getConfig("smsSerialPassword")), "utf-8"),
				getConfig("AppKey"));
	}
	
	/**
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getSmsSendSMSURL() throws UnsupportedEncodingException {
		return Encodes.rc4(new String(Encodes.decodeBase64(getConfig("smsSendSMSURL")), "utf-8"),
				getConfig("AppKey"));
	}
	
	/**
	 * 获得七牛OSS ACCESS_KEY
	 */
	public static String getQiniuAccessKey() {
		return getConfig("QN_ACCESS_KEY");
	}
	
	/**
	 * 获得七牛OSS SECRET_KEY
	 */
	public static String getQiniuSecretKey() {
		return getConfig("QN_SECRET_KEY");
	}
	
	
	/**
	 * 获得七牛OSS BucketName
	 */
	public static String getQiniuBucketName() {
		return getConfig("QN_BUCKET_NAME");
	}
	
	/**
	 * 获得七牛OSS Domain
	 */
	public static String getQiniuDomain() {
		return getConfig("QN_DOMAIN");
	}
	
	/**
	 * 
	 */
	public static String getOaApiPath() {
		return getConfig("oaApiPath");
	}
	
}
