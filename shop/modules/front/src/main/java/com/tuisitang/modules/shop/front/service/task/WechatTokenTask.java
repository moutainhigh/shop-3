package com.tuisitang.modules.shop.front.service.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuisitang.common.cache.memcached.SpyMemcachedClient;

/**    
 * @{#} WechatTokenTask.java  
 * 
 * 微信Token任务
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@Service
public class WechatTokenTask {
	
	private static final Logger logger = LoggerFactory.getLogger(WechatTokenTask.class);
	
	@Autowired
	private SpyMemcachedClient memcachedClient;
	
	public void execute() {
//		String token = memcachedClient.get(KEY);
//		logger.info("KEY {}, token {}", KEY, token);
//		String appid = ConfKit.get("AppId");
//		String secret = ConfKit.get("AppSecret");
//		logger.info("appid = {}, secret = {}", appid, secret);
//		try {
//			token = WeChat.getAccessToken(appid, secret);
//			logger.info("token {}", token);
//			memcachedClient.safeSet(KEY, 7200, token);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
