package com.tuisitang.modules.shop.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tuisitang.common.bo.IpInfo;
import com.tuisitang.common.cache.memcached.MemcachedObjectType;
import com.tuisitang.common.cache.memcached.SpyMemcachedClient;
import com.tuisitang.common.security.Digests;
import com.tuisitang.common.utils.AddressUtils;
import com.tuisitang.common.utils.Encodes;
import com.tuisitang.modules.shop.dao.AccountDao;
import com.tuisitang.modules.shop.dao.AddressDao;
import com.tuisitang.modules.shop.dao.AdvertDao;
import com.tuisitang.modules.shop.dao.AreaDao;
import com.tuisitang.modules.shop.dao.DeviceDao;
import com.tuisitang.modules.shop.dao.ExpressDao;
import com.tuisitang.modules.shop.dao.HotqueryDao;
import com.tuisitang.modules.shop.dao.IndexImgDao;
import com.tuisitang.modules.shop.dao.KeyvalueDao;
import com.tuisitang.modules.shop.dao.NavigationDao;
import com.tuisitang.modules.shop.dao.NotifyTemplateDao;
import com.tuisitang.modules.shop.dao.RoleDao;
import com.tuisitang.modules.shop.dao.SessionDao;
import com.tuisitang.modules.shop.dao.SmsDao;
import com.tuisitang.modules.shop.dao.UserDao;
import com.tuisitang.modules.shop.dao.WechatUserDao;
import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.entity.Address;
import com.tuisitang.modules.shop.entity.Advert;
import com.tuisitang.modules.shop.entity.Device;
import com.tuisitang.modules.shop.entity.Hotquery;
import com.tuisitang.modules.shop.entity.Keyvalue;
import com.tuisitang.modules.shop.entity.NotifyTemplate;
import com.tuisitang.modules.shop.entity.Role;
import com.tuisitang.modules.shop.entity.Session;
import com.tuisitang.modules.shop.entity.User;
import com.tuisitang.modules.shop.entity.WechatUser;

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
public class SystemService {
	
	private static final Logger logger = LoggerFactory.getLogger(SystemService.class);
	
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
	@Autowired
	private DeviceDao deviceDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;

	// -- Account 会员
	/**
	 * 1. 根据mobile获得Account
	 * 
	 * @param mobile
	 * @return
	 */
	public Account getAccountByMobile(String mobile) {
		logger.debug("getAccountByMobile mobile = {}", mobile);
		Account account = null;
		if (memcachedClient != null) {
			account = (Account) memcachedClient.get(Account.getKey(mobile));
			logger.debug("getAccountByMobile Account = {}", account);
			if (account != null) {
				return account;
			}
		}
		account = accountDao.getByMobile(mobile);
		if (account != null && memcachedClient != null) {// 如果Account不为空，则缓存至Memcached
			memcachedClient.safeSet(Account.getKey(mobile), MemcachedObjectType.Account.getExpiredTime(), account);
			memcachedClient.safeSet(Account.getKey(account.getId()), MemcachedObjectType.Account.getExpiredTime(), account);
		}
		return account;
	}
	
	/**
	 * 2. 根据id获得Account
	 * 
	 * @param id
	 * @return
	 */
	public Account getAccountById(Long id) {
		logger.debug("getAccountById id = {}", id);
		Account account = null;
		if (memcachedClient != null) {
			account = memcachedClient.get(Account.getKey(id));
			logger.debug("getAccountById Account = {}", account);
			if (account != null) {
				return account;
			}
		}
		account = accountDao.selectById(id);
		if (account != null && memcachedClient != null) {// 如果Account不为空，则缓存至Memcached
			memcachedClient.safeSet(Account.getKey(account.getMobile()), 
					MemcachedObjectType.Account.getExpiredTime(), account);
			memcachedClient.safeSet(Account.getKey(id), 
					MemcachedObjectType.Account.getExpiredTime(), account);
		}
		return account;
	}
	
	/**
	 * 3. 根据id更新密码为newPassword
	 * 
	 * @param id
	 * @param newPassword
	 */
	@Transactional(readOnly = false)
	public void updateAccountPasswordById(Long id, String newPassword) {
		accountDao.updatePasswordById(id, entryptPassword(newPassword));
		updateAccountCache(id);
	}
	
	/**
	 * 4. 更新Account登录信息
	 * 
	 * @param id
	 * @param request 
	 */
	@Transactional(readOnly = false)
	public void updateUserLoginInfo(Long id, String lastLoginIp) {
//		String lastLoginIp = AddressUtils.getHost(request);
		IpInfo ipInfo = AddressUtils.getIpInfo(lastLoginIp);
		String lastLoginArea = ipInfo == null ? "" : ipInfo.getRegion() + ipInfo.getCity() + "[" + ipInfo.getIsp() + "]";
		String diffAreaLogin = Account.DIFF_AREA_LOGIN_NO;
		Account account = accountDao.selectById(id);
		if (!lastLoginArea.equals(account.getLastLoginArea())) {
			diffAreaLogin = Account.DIFF_AREA_LOGIN_YES;
		}
		logger.debug("lastLoginIp {}, lastLoginArea {}, diffAreaLogin {}", lastLoginIp, lastLoginArea, diffAreaLogin);
		accountDao.updateLoginInfo(id, lastLoginIp, lastLoginArea, diffAreaLogin);
//		updateAccountCache(id);
	}
	
	/**
	 * 5. 保存Account
	 * 
	 * @param account
	 */
	@Transactional(readOnly = false)
	public void saveAccount(Account account) {
		accountDao.insert(account);
		updateAccountCache(account.getId());
	}
	
	/**
	 * 6.获得手机验证码
	 * 
	 * @param mobile
	 * @return
	 */
	public String getMobileVerify(String mobile) {
		return memcachedClient.get(MemcachedObjectType.MCODE.getPrefix()
				+ mobile);
	}
	
	/**
	 * 7.保存手机验证码
	 * 
	 * @param mobile
	 * @param mobileVerify
	 * @return
	 */
	public boolean setMobileVerify(String mobile, String mobileVerify) {
		return memcachedClient.safeSet(MemcachedObjectType.MCODE.getPrefix() + mobile, 
				MemcachedObjectType.MCODE.getExpiredTime(), mobileVerify);
	}
	
	/**
	 * 8. 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 * 
	 * @param plainPassword
	 * @return
	 */
	public static String entryptPassword(String plainPassword) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword);
	}
	
	/**
	 * 9. 验证密码
	 * 
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		byte[] salt = Encodes.decodeHex(password.substring(0, 16));
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
	}
	
	/**
	 * 10. 更新信息
	 * 
	 * @param account
	 */
	@Transactional(readOnly = false)
	public void updateAccount(Account account) {
		accountDao.update(account);
		updateAccountCache(account.getId());
	}
	
	/**
	 * 11. 根据inviteCode查询Account
	 * 
	 * @param inviteCode
	 * @return
	 */
	public Account getAccountByInviteCode(String inviteCode) {
		return accountDao.getByInviteCode(inviteCode);
	}
	
	/**
	 * 13. 根据openid获得Account
	 * 
	 * @param mobile
	 * @return
	 */
	public Account getAccountByOpenid(String openid) {
		logger.debug("getAccountByOpenid openid = {}", openid);
		Account account = null;
		if (memcachedClient != null) {
			account = memcachedClient.get(Account.getKeyByOpenid(openid));
			logger.debug("getAccountByMobile Account = {}", account);
			if (account != null) {
				return account;
			}
		}
		account = accountDao.getByOpenid(openid);
		if (account != null && memcachedClient != null) {// 如果Account不为空，则缓存至Memcached
			memcachedClient.safeSet(Account.getKeyByOpenid(openid), MemcachedObjectType.Account.getExpiredTime(), account);
			memcachedClient.safeSet(Account.getKey(account.getMobile()), MemcachedObjectType.Account.getExpiredTime(), account);
			memcachedClient.safeSet(Account.getKey(account.getId()), MemcachedObjectType.Account.getExpiredTime(), account);
		}
		return account;
	}
	
	/**
	 * 14. 根据id更新邀请码
	 * 
	 * @param id
	 * @param inviteCode
	 * @param qrcodeUrl
	 */
	@Transactional(readOnly = false)
	public void updateAccountInviteCodeById(Long id, String inviteCode, String qrcodeUrl) {
		accountDao.updateInviteCodeById(id, inviteCode, qrcodeUrl);
	}
	
	/**
	 * 15. 根据id更新openid
	 * 
	 * @param id
	 * @param openid
	 */
	@Transactional(readOnly = false)
	public void updateAccountOpenidById(Long id, String openid) {
		accountDao.updateOpenidById(id, openid);
	}
	
	/**
	 * 16. 根据id更新account
	 * 
	 * @param id
	 * @param account
	 */
	@Transactional(readOnly = false)
	public void updateAccountUsernameById(Long id, String account) {
		accountDao.updateAccountById(id, account);
	}
	
	/**
	 * 17. 根据username获得Account
	 * 
	 * @param username
	 * @return
	 */
	public Account getAccountByUsername(String username) {
		logger.debug("getAccountByUsername username = {}", username);
		return accountDao.getByAccount(username);
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
				if (StringUtils.isNotBlank(account.getOpenId())) {
					memcachedClient.safeSet(Account.getKeyByOpenid(account.getOpenId()), 
							MemcachedObjectType.Account.getExpiredTime(), account);
				}
			}
//			Account act = AccountUtils.getAccount();
//			if (act != null && act.getId() != null) {
//				AccountUtils.setAccount(account);
//			}
		}
	}
	
	// -- Address 会员地址
	/**
	 * 1. 根据accountId获得会员地址
	 * 
	 * @param accountId
	 * @return
	 */
	public List<Address> getAddressByAccountId(Long accountId) {
//		addressDao.
		return null;
	}
	
	
	// -- Advert 广告
	/**
	 * 1. 根据type获得Advert
	 * 
	 * @param type
	 * @return
	 */
	public Advert getAdvert(String code) {
		Advert advert = null;
		if (memcachedClient != null) {
			advert = memcachedClient.get(Advert.getKey(code));
			logger.debug("getAdvert Advert = {}", advert);
			if (advert != null) {
				return advert;
			}
		}
		advert = advertDao.getByCode(code);
		if (advert != null && memcachedClient != null) {
			memcachedClient.safeSet(Advert.getKey(code), MemcachedObjectType.Account.getExpiredTime(),
					advert);
			memcachedClient.safeSet(Advert.getKey(advert.getId()), MemcachedObjectType.Account.getExpiredTime(),
					advert);
		}
		return advert;
	}
	
	// -- Area 地区
	
	// -- Express 快递方式
	
	// -- Hotquery
	/**
	 * 1. 查询所有的Hotquery
	 * 
	 * @return
	 */
	public List<Hotquery> findAllHotquery() {
		List<Hotquery> list = null;
		if (memcachedClient != null) {
			list = memcachedClient.get(Hotquery.getKey());
		}
		if (list == null) {
			list = hotqueryDao.selectList(null);
			if (list != null && memcachedClient != null) {
				memcachedClient.safeSet(Hotquery.getKey(), MemcachedObjectType.Account.getExpiredTime(), list);
			}
		}
		return list;
	}
	
	/**
	 * 2. 根据type查询Hotquery
	 * 
	 * @param type
	 * @return
	 */
	public List<Hotquery> findHotquery(String type) {
		return hotqueryDao.findByType(type);
	}
	
	// -- IndexImg 
	
	// -- Keyvalue
	
	// -- Navigation
	
	// -- NotifyTemplate 通知模板
	/**
	 * 1. 根据type和code获得通知模板
	 * 
	 * @param type
	 * @param code
	 * @return
	 */
	public String getNotifyTemplate(String type, String code) {
		logger.debug("getNotifyTemplate type = {}, code = {}", type, code);
		String template = null;
		String key = NotifyTemplate.getKey(type, code);
		if (memcachedClient != null) {
//			template = memcachedClient.get(key);
//			logger.debug("getNotifyTemplate template = {}", template);
//			if (!StringUtils.isBlank(template)) {
//				return template;
//			}
		}
		template = notifyTemplateDao.getByTypeAndCode(type, code);
		if (memcachedClient != null && !StringUtils.isBlank(template)) {
			memcachedClient.safeSet(key, MemcachedObjectType.NotifyTemplate.getExpiredTime(), template);
		}
		return template;
	}
	
	// -- Session
	/**
	 * 1. 根据sessionKey获得Session
	 * 
	 * @param sessionId
	 * @return
	 */
	public Session getSession(String sessionId) {
		if (memcachedClient != null) {
			Session session = memcachedClient.get(Session.getKey(sessionId));
			if (session != null) {
				return session;
			}
		}
		Session session = sessionDao.get(sessionId);
		if (memcachedClient != null && session != null)
			memcachedClient.set(Session.getKey(sessionId), MemcachedObjectType.Session.getExpiredTime(), session);
		return session;
	}
	
	/**
	 * 2. 保存Session
	 * 
	 * @param sessionId
	 * @param accountId
	 * @param ip
	 * @param expiredTime
	 * @return
	 */
	@Transactional(readOnly = false)
	public Session saveSession(String sessionId, Long accountId, String ip, long expiredTime) {
		Session session = new Session();
		session.setSessionId(sessionId);
		session.setAccountId(accountId);
		session.setIp(ip);
		session.setExpiredTime(expiredTime);
		session.setData("");
		sessionDao.insert(session);
		if (memcachedClient != null)
			memcachedClient.set(Session.getKey(sessionId), MemcachedObjectType.Session.getExpiredTime(), session);
		return session;
	}
	
	/**
	 * 3. 根据sessionId删除Session
	 * 
	 * @param sessionId
	 */
	@Transactional(readOnly = false)
	public void deleteSession(String sessionId) {
		sessionDao.remove(sessionId);
		if (memcachedClient != null)
			memcachedClient.delete(Session.getKey(sessionId));
	}
	
	/**
	 * 4. 根据sessionId更新accountId
	 * 
	 * @param sessionId
	 * @param accountId
	 */
	@Transactional(readOnly = false)
	public void updateSession(String sessionId, Long accountId) {
		Session session = getSession(sessionId);
		session.setAccountId(accountId);
		sessionDao.update(session);
		if (memcachedClient != null && session != null) {
			memcachedClient.set(Session.getKey(sessionId), MemcachedObjectType.Session.getExpiredTime(), session);
		}
	}
	
	//--WechatUser
	/**
	 * 1. 保存/更新WechatUser
	 * 
	 * @param wu
	 * @return
	 */
	@Transactional(readOnly = false)
	public WechatUser saveWechatUser(String openid, int subscribe, String nickname, int sex, String language, String city,
			String province, String country, String headimgurl, Long subscribe_time, String unionid) {
		WechatUser wu = wechatUserDao.getByOpenid(openid);
		if (wu == null || wu.getId() == null) {
			wu = new WechatUser(subscribe, openid, nickname, sex, city,
					province, country, language, headimgurl, subscribe_time,
					unionid);
			wu.setStatus("0");
			wu.setMobile("");
			wu.setCreateTime(new Date(System.currentTimeMillis()));
			wechatUserDao.insert(wu);
		} else {
			wu.setSubscribe(subscribe);
			wu.setNickname(nickname);
			wu.setSex(sex);
			wu.setCity(city);
			wu.setProvince(province);
			wu.setCountry(country);
			wu.setLanguage(language);
			wu.setHeadimgurl(headimgurl);
			wu.setSubscribe_time(subscribe_time);
			wu.setUnionid(unionid);
			wu.setStatus("0");
//			wu.setMobile("");
			wechatUserDao.update(wu);
		}
		return wu;
	}

	/**
	 * 2. 根据id逻辑删除WechatUser
	 * 逻辑删除，status从0 - 1
	 * 
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void deleteWechatUser(Long id) {
		wechatUserDao.deleteById(id);
	}
	
	/**
	 * 3. 根据openid获得WechatUser
	 * 
	 * @param openid
	 * @return
	 */
	public WechatUser getWechatUserByOpenid(String openid) {
		return wechatUserDao.getByOpenid(openid);
	}
	
	/**
	 * 4. 根据id获得WechatUser
	 * 
	 * @param id
	 * @return
	 */
	public WechatUser getWechatUser(Long id) {
		return wechatUserDao.selectById(id);
	}
	
	/**
	 * 5. 根据openid逻辑删除WechatUser
	 * 
	 * @param openid
	 */
	@Transactional(readOnly = false)
	public void deleteWechatUserByOpenid(String openid) {
		wechatUserDao.deleteByOpenid(openid);
	}
	
	// --Device
	/**
	 * 1. 保存Device
	 * 
	 * @param device
	 */
	@Transactional(readOnly = false)
	public void saveDevice(Device device) {
		if (device.getId() == null) {
			deviceDao.insert(device);
		} else {
			deviceDao.update(device);
		}
		memcachedClient.set(Device.getKey(device.getId()), MemcachedObjectType.Device.getExpiredTime(), device);
		memcachedClient.set(Device.getKey(device.getToken()), MemcachedObjectType.Device.getExpiredTime(), device);
	}
//	@Transactional(readOnly = false)
//	public void saveDevice(Device device) {
//		Device d = this.memcachedClient.get(Device.getKey(device.getToken()));
//		if (d != null) {
//			if ("1".equals(d.getStatus())) {
//				d.setStatus("0");
//				deviceDao.update(d);
//				memcachedClient.set(Device.getKey(d.getId()), MemcachedObjectType.Device.getExpiredTime(), d);
//				memcachedClient.set(Device.getKey(d.getToken()), MemcachedObjectType.Device.getExpiredTime(), d);
//			}
//		} else {
//			d = deviceDao.selectByKeyword(device.getToken());
//			if (d == null) {
//				deviceDao.insert(device);
//				memcachedClient.set(Device.getKey(device.getId()), MemcachedObjectType.Device.getExpiredTime(), device);
//				memcachedClient.set(Device.getKey(device.getToken()), MemcachedObjectType.Device.getExpiredTime(), device);
//			} else {
//				if ("1".equals(d.getStatus())) {
//					d.setStatus("0");
//					deviceDao.update(d);
//				}
//				device = d;
//				memcachedClient.set(Device.getKey(d.getId()), MemcachedObjectType.Device.getExpiredTime(), d);
//				memcachedClient.set(Device.getKey(d.getToken()), MemcachedObjectType.Device.getExpiredTime(), d);
//			}
//		}
//	}

	/**
	 * 2. 删除Device
	 * 
	 * @param device
	 */
	@Transactional(readOnly = false)
	public void deleteDevice(Long id) {
		deviceDao.deleteById(id);
	}

	/**
	 * 3. 更新Device
	 * 
	 * @param device
	 */
	@Transactional(readOnly = false)
	public void updateDevice(Device device) {
		deviceDao.update(device);
	}

	/**
	 * 4. 根据id查询Device
	 * 
	 * @param id
	 * @return
	 */
	public Device getDevice(Long id) {
		Device device = this.memcachedClient.get(Device.getKey(id));
		if (device == null) {
			device = deviceDao.selectById(id);
			memcachedClient.set(Device.getKey(device.getId()), MemcachedObjectType.Device.getExpiredTime(), device);
			memcachedClient.set(Device.getKey(device.getToken()), MemcachedObjectType.Device.getExpiredTime(), device);
		}
		return device;
	}
	
	/**
	 * 5. 根据token查询Device
	 * 
	 * @param token
	 * @return
	 */
	public Device getDevice(String token) {
		Device device = this.memcachedClient.get(Device.getKey(token));
		if (device == null) {
			device = deviceDao.selectByKeyword(token);
			if (device != null) {
				memcachedClient.set(Device.getKey(device.getId()), MemcachedObjectType.Device.getExpiredTime(), device);
				memcachedClient.set(Device.getKey(device.getToken()), MemcachedObjectType.Device.getExpiredTime(), device);
			}
		}
		return device;
	}
	
	// -- User
	/**
	 * 1. 保存User
	 * 
	 * @param user
	 */
	@Transactional(readOnly = false)
	public void saveUser(User user) {
		userDao.insert(user);
	}
	
	/**
	 * 2. 根据id删除User
	 * 
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void deleteUser(Long id) {
		userDao.deleteById(id);
	}
	
	/**
	 * 3. 根据id获得User
	 * 
	 * @param id
	 * @return
	 */
	public User getUser(Long id) {
		return userDao.selectById(id);
	}
	
	/**
	 * 4. 查询所有的User
	 * 
	 * @return
	 */
	public List<User> findUser() {
		return userDao.selectList(new User());
	}
	
	// -- Role
	/**
	 * 1. 保存Role
	 * 
	 * @param role
	 */
	@Transactional(readOnly = false)
	public void saveRole(Role role) {
		roleDao.insert(role);
	}
	
	/**
	 * 2. 根据id删除Role
	 * 
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void deleteRole(Long id) {
		roleDao.deleteById(id);
	}
	
	/**
	 * 3. 根据id获得Role
	 * 
	 * @param id
	 * @return
	 */
	public Role getRole(Long id) {
		return roleDao.selectById(id);
	}
	
	/**
	 * 4. 获得所有Role
	 * 
	 * @return
	 */
	public List<Role> findRole() {
		return roleDao.selectList(new Role());
	}
	
	// -- KeyValue
	/**
	 * 1. 保存Role
	 * 
	 * @param Keyvalue
	 */
	@Transactional(readOnly = false)
	public void saveKeyvalue(Keyvalue keyvalue) {
		keyvalueDao.insert(keyvalue);
	}
	
	/**
	 * 2. 根据id删除Keyvalue
	 * 
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void deleteKeyvalue(Long id) {
		keyvalueDao.deleteById(id);
	}
	
	/**
	 * 3. 根据id获得Keyvalue
	 * 
	 * @param id
	 * @return
	 */
	public Keyvalue getKeyvalue(Long id) {
		return keyvalueDao.selectById(id);
	}
	
	/**
	 * 4. 获得所有Keyvalue
	 * 
	 * @return
	 */
	public List<Keyvalue> findKeyvalue() {
		return keyvalueDao.selectList(new Keyvalue());
	}
}
