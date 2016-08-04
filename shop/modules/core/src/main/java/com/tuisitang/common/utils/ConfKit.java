//package com.tuisitang.common.utils;
//
//import java.util.Map;
//
//import com.google.common.collect.Maps;
//
///**    
// * @{#} ConfKit.java  
// * 
// * 配置文件工具类
// *    
// * <p>Copyright: Copyright(c) 2013 </p> 
// * <p>Company: TST</p>
// * @version 1.0
// * @author <a href="mailto:paninxb@gmail.com">panin</a>   
// */
//public class ConfKit {
//
//	/**
//	 * 保存全局属性值
//	 */
//	private static Map<String, String> map = Maps.newHashMap();
//
//	/**
//	 * 属性文件加载对象
//	 */
//	private static PropertiesLoader propertiesLoader = new PropertiesLoader("application.properties", "wechat.properties");
//
//	/**
//	 * 获取配置
//	 */
//	public static String getConfig(String key) {
//		String value = map.get(key);
//		if (value == null) {
//			value = propertiesLoader.getProperty(key);
//			map.put(key, value);
//		}
//		return value;
//	}
//}
