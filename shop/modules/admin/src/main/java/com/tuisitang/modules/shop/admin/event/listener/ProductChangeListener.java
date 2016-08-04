package com.tuisitang.modules.shop.admin.event.listener;

import java.util.Map;

import net.jeeshop.core.system.bean.User;
import net.jeeshop.services.manage.systemlog.SystemlogService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.tuisitang.common.cache.memcached.SpyMemcachedClient;
import com.tuisitang.modules.shop.admin.event.ProductChangeEvent;
import com.tuisitang.modules.shop.entity.Product;
import com.tuisitang.modules.shop.entity.Systemlog;

/**    
 * @{#} ProductChangeListener.java  
 * 
 * 产品变更侦听
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@Service
public class ProductChangeListener implements ApplicationListener<ProductChangeEvent> {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductChangeListener.class);
	
	@Autowired
	private SystemlogService systemlogService;
	@Autowired
	private SpyMemcachedClient memcachedClient;

	@Override
	public void onApplicationEvent(ProductChangeEvent event) {
		Long[] ids = event.getIds();
		User user = event.getUser();
		String action = event.getAction();
		Map<String, Object> requestMap = event.getRequestMap();
		
		logger.info("product.ids {}, user.id {}, user.username {}, action {}", ids, user.getId(), user.getUsername(), action);
//		Systemlog e = new Systemlog();
//		systemlogService.insert(e);
	}
	
}
