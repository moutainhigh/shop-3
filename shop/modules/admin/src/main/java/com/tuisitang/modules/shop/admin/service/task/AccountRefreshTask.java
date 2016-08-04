package com.tuisitang.modules.shop.admin.service.task;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuisitang.common.cache.memcached.MemcachedObjectType;
import com.tuisitang.common.cache.memcached.SpyMemcachedClient;
import com.tuisitang.modules.shop.dao.AccountDao;
import com.tuisitang.modules.shop.entity.Account;

/**    
 * @{#} AccountRefreshTask.java   
 *    
 * 会员账号定时刷新任务
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@Service
public class AccountRefreshTask {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountRefreshTask.class);
	
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private SpyMemcachedClient memcachedClient;

	public void execute() {
		Account e = new Account();
		List<Account> list = accountDao.selectList(e);
		for (Account a : list) {
			logger.info("Account {}", a);
			memcachedClient.set(Account.getKey(a.getId()), MemcachedObjectType.Account.getExpiredTime(), a);
			memcachedClient.set(Account.getKey(a.getMobile()), MemcachedObjectType.Account.getExpiredTime(), a);
			if (!StringUtils.isBlank(a.getOpenId())) {
				memcachedClient.set(Account.getKeyByOpenid(a.getOpenId()), MemcachedObjectType.Account.getExpiredTime(), a);
			}
		}
	}

}
