package net.jeeshop.services.manage.cache.memcache.impl;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.Map;

import net.jeeshop.services.manage.cache.memcache.MemcacheService;
import net.spy.memcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tuisitang.common.cache.memcached.MemcachedObjectType;
import com.tuisitang.common.cache.memcached.SpyMemcachedClient;
import com.tuisitang.common.utils.MemcacheUtils;

public class MemcacheServiceImpl implements MemcacheService{
	private SpyMemcachedClient client;
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public void setClient(SpyMemcachedClient client) {
		this.client = client;
	}
	
	public String[] getAllMemcacheIds() {
		String json = client.get(MemcachedObjectType.KeyValue.getPrefix());
		logger.info("{}", json);
		String[] keys = null;
		MemcachedClient memcachedClient = client.getMemcachedClient();
		Map<SocketAddress, Map<String, String>> map = memcachedClient.getStats();
		for(Map.Entry<SocketAddress, Map<String, String>>entry : map.entrySet()){
			logger.info("{} \n {}", entry.getKey().getClass(), entry.getValue());
			InetSocketAddress isa = (InetSocketAddress)entry.getKey();
			keys = MemcacheUtils.allkeys(isa.getHostName(), isa.getPort()).split("\n");
	        Arrays.sort(keys);
		}
		return keys;
	}
	
}
