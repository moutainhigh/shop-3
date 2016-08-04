package com.tuisitang.modules.shop.admin.service.task;

import java.util.List;

import net.jeeshop.services.manage.account.dao.AccountDao;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuisitang.common.cache.memcached.SpyMemcachedClient;
import com.tuisitang.common.mapper.JsonMapper;
import com.tuisitang.modules.shop.entity.Account;

/**    
 * @{#} ConfigurationRefreshTask.java   
 *    
 * 配置数据定时刷新任务
 * 
 * Advert            N
 * Area              N
 * BookingArea       N
 * DeliveryDay       N
 * Express           N
 * ExpressCompany    N
 * Hotquery          Y
 * Keyvalue          N
 * Notifytemplate    N
 * Oss               N
 * Pay               N
 * Systemsetting     N
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@Service
public class ConfigurationRefreshTask {
	
	private static final Logger logger = LoggerFactory.getLogger(ConfigurationRefreshTask.class);
	
	@Autowired
	private SpyMemcachedClient memcachedClient;

	public void execute() {
		JsonMapper mapper = JsonMapper.getInstance();
	}

}
