package net.jeeshop.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tuisitang.common.cache.memcached.MemcachedObjectType;
import com.tuisitang.common.cache.memcached.SpyMemcachedClient;
import com.tuisitang.common.utils.SpringContextHolder;
import com.tuisitang.modules.shop.entity.Keyvalue;

/**    
 * @{#} KeyValueHelper.java  
 * 
 * key-value工具类，页面获取中文文字描述都从此获取
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class KeyValueHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(KeyValueHelper.class);
	
//	private static Map<String, String> map = Collections.synchronizedMap(new HashMap<String, String>());
	
	private static MemcachedObjectType type = MemcachedObjectType.KeyValue;
	
	public static void load(List<Keyvalue> list) {
		SpyMemcachedClient client = SpringContextHolder.getBean(SpyMemcachedClient.class);
		if (list == null || list.size() == 0) {
//			map.clear();
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			Keyvalue keyvalue = list.get(i);
//			map.put(keyvalue.getKey1(), keyvalue.getValue());
			client.set(type.getPrefix() + keyvalue.getKeyword(), type.getExpiredTime(), keyvalue.getValue());
		}
		client.set(type.getPrefix(), type.getExpiredTime(), list);
	}
	
	/**
	 * 根据指定的key获取指定的value
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		if (StringUtils.isEmpty(key)) {
			throw new NullPointerException("key is not null.");
		}

//		return map.get(key);
		SpyMemcachedClient client = SpringContextHolder.getBean(SpyMemcachedClient.class);
		return client.get(type.getPrefix() + key);
	}
	
	/**
	 * key的规则是：table_column_key
	 * @param key  参数为table_column_
	 * @return
	 */
	public static Map<String,String> getMap(String key) {
		if (StringUtils.isEmpty(key)) {
			throw new NullPointerException("key is not null.");
		}
		SpyMemcachedClient client = SpringContextHolder.getBean(SpyMemcachedClient.class);
		Map<String, String> mapItem = new HashMap<String, String>();
		/**
		for(Iterator<Entry<String, String>> it = map.entrySet().iterator();it.hasNext();){
			Entry<String, String> entry = it.next();
			if(entry.getKey().indexOf(key)>=0){
				mapItem.put(entry.getKey(), entry.getValue());
			}
		}
		*/
		List<Keyvalue> list = client.get(type.getPrefix());
		for (Keyvalue m : list) {
			String k = m.getKeyword();
			String v = m.getValue();
			if (k.indexOf(key) >= 0) {
				mapItem.put(k, v);
			}
		}
		return mapItem;
	}
}
