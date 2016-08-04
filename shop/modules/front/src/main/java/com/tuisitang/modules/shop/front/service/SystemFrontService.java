package com.tuisitang.modules.shop.front.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tuisitang.common.cache.memcached.MemcachedObjectType;
import com.tuisitang.common.cache.memcached.SpyMemcachedClient;
import com.tuisitang.modules.shop.dao.AccountDao;
import com.tuisitang.modules.shop.dao.AddressDao;
import com.tuisitang.modules.shop.dao.AdvertDao;
import com.tuisitang.modules.shop.dao.AreaDao;
import com.tuisitang.modules.shop.dao.ExpressDao;
import com.tuisitang.modules.shop.dao.HotqueryDao;
import com.tuisitang.modules.shop.dao.IndexImgDao;
import com.tuisitang.modules.shop.dao.KeyvalueDao;
import com.tuisitang.modules.shop.dao.NavigationDao;
import com.tuisitang.modules.shop.dao.NotifyTemplateDao;
import com.tuisitang.modules.shop.dao.SessionDao;
import com.tuisitang.modules.shop.dao.SmsDao;
import com.tuisitang.modules.shop.dao.WechatUserDao;
import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.front.utils.AccountUtils;

/**    
 * @{#} SystemService.java  
 * 
 * 系统管理方面的Service
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@Service
public class SystemFrontService extends com.tuisitang.modules.shop.service.SystemService {
	
	private static final Logger logger = LoggerFactory.getLogger(SystemFrontService.class);
	
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;

	@Autowired(required = false)
	private SpyMemcachedClient memcachedClient;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AddressDao addressDao;
	@Autowired
	private AdvertDao advertDao;
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private ExpressDao expressDao;
	@Autowired
	private HotqueryDao hotqueryDao;
	@Autowired
	private IndexImgDao indexImgDao;
	@Autowired
	private KeyvalueDao keyvalueDao;
	@Autowired
	private NavigationDao navigationDao;
	@Autowired
	private NotifyTemplateDao notifyTemplateDao;
	@Autowired
	private SmsDao smsDao;
	@Autowired
	private SessionDao sessionDao;
	@Autowired
	private WechatUserDao wechatUserDao;

	// -- Account 会员
	/**
	 * 12. 更新Account的Mobile
	 * 
	 * @param id
	 * @param oldMobile
	 * @param newMobile
	 */
	@Transactional(readOnly = false)
	public void updateAccountMobileById(Long id, String oldMobile, String newMobile) {
		accountDao.updateMobileById(id, newMobile);
		if (memcachedClient != null) {
			memcachedClient.delete(Account.getKey(id));
			memcachedClient.delete(Account.getKey(oldMobile));
			Account account = accountDao.selectById(id);
			if (account != null) {// 如果Account不为空，则缓存至Memcached
				memcachedClient.safeSet(Account.getKey(account.getMobile()), 
						MemcachedObjectType.Account.getExpiredTime(), account);
				memcachedClient.safeSet(Account.getKey(id), 
						MemcachedObjectType.Account.getExpiredTime(), account);
			}
			Account act = AccountUtils.getAccount();
			if (act != null && act.getId() != null) {
				AccountUtils.setAccount(account);
			}
		}
	}
	
	/**
	 * 更新会员缓存
	 * 
	 * @param id
	 */
	protected void updateAccountCache(Long id) {
		if (memcachedClient != null) {
			Account account = accountDao.selectById(id);
			if (account != null) {// 如果Account不为空，则缓存至Memcached
				memcachedClient.safeSet(Account.getKey(account.getMobile()), 
						MemcachedObjectType.Account.getExpiredTime(), account);
				memcachedClient.safeSet(Account.getKey(id), 
						MemcachedObjectType.Account.getExpiredTime(), account);
			}
			Account act = AccountUtils.getAccount();
			if (act != null && act.getId() != null) {
				AccountUtils.setAccount(account);
			}
		}
	}
	
}
