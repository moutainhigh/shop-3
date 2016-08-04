package com.tuisitang.modules.shop.service;

import java.util.List;
import java.util.Map;

import net.jeeshop.core.dao.page.PagerModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tuisitang.common.cache.memcached.MemcachedObjectType;
import com.tuisitang.common.cache.memcached.SpyMemcachedClient;
import com.tuisitang.modules.shop.dao.AccountDao;
import com.tuisitang.modules.shop.dao.AddressDao;
import com.tuisitang.modules.shop.dao.AuthenticationDao;
import com.tuisitang.modules.shop.dao.BookingOrderDao;
import com.tuisitang.modules.shop.dao.ExpressDao;
import com.tuisitang.modules.shop.dao.FavoriteDao;
import com.tuisitang.modules.shop.dao.OrderDao;
import com.tuisitang.modules.shop.dao.ProfitLogDao;
import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.entity.Address;
import com.tuisitang.modules.shop.entity.Authentication;
import com.tuisitang.modules.shop.entity.BookingOrder;
import com.tuisitang.modules.shop.entity.Favorite;
import com.tuisitang.modules.shop.entity.Order;
import com.tuisitang.modules.shop.entity.Orderdetail;
import com.tuisitang.modules.shop.entity.ProfitLog;

/**    
 * @{#} AccountService.java  
 * 
 * 前台我的会员Service
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@Service
public class AccountService {
	private static final Logger logger = LoggerFactory.getLogger(AccountService.class);
	
	@Autowired(required = false)
	private SpyMemcachedClient memcachedClient;
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AddressDao addressDao;
	@Autowired
	private ExpressDao expressDao;
	@Autowired
	private FavoriteDao favoriteDao;
	@Autowired
	private AuthenticationDao authenticationDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private ProfitLogDao profitLogDao;
	@Autowired
	private BookingOrderDao bookingOrderDao;
	
	// -- Account
	
	// -- Address
	/**
	 * 1. 根据accountId获得Address列表
	 * 
	 * @param accountId
	 * @return
	 */
	public List<Address> findAddressByAccountId(Long accountId) {
		logger.debug("findAddressByAccountId accountId = {}", accountId);
		if (memcachedClient != null) {
			List<Address> list = memcachedClient.get(Address.getKeyByAccountId(accountId));
			logger.debug("findAddressByAccountId Address List = {}", list);
			if (list != null) {
				return list;
			}
		}
		List<Address> list = addressDao.findByAccountId(accountId);
		if (list != null && memcachedClient != null) {// 如果List不为空，则缓存至Memcached
			memcachedClient.safeSet(Address.getKeyByAccountId(accountId),
					MemcachedObjectType.Address.getExpiredTime(), list);
		}
		return list;
	}
	
	/**
	 * 2. 根据id获得Address
	 * 
	 * @param id
	 * @return
	 */
	public Address getAddress(Long id) {
		logger.debug("getAddress id = {}", id);
		if (memcachedClient != null) {
			Address address = memcachedClient.get(Address.getKeyById(id));
			logger.debug("getAddress Address = {}", address);
			if (address != null) {
				return address;
			}
		}
		Address address = addressDao.selectById(id);
		if (address != null && memcachedClient != null) {// 如果List不为空，则缓存至Memcached
			memcachedClient.safeSet(Address.getKeyById(id), MemcachedObjectType.Address.getExpiredTime(), address);
		}
		return address;
	}
	
	/**
	 * 3. 保存Address
	 * 
	 * @param address
	 */
	public void saveAddress(Address address) {
		addressDao.insert(address);
		if (memcachedClient != null) {
			memcachedClient.safeSet(Address.getKeyById(address.getId()), MemcachedObjectType.Address.getExpiredTime(), address);
			if (address.getAccountId() != null) {
				List<Address> list = memcachedClient.get(Address.getKeyByAccountId(address.getAccountId()));
				logger.debug("findAddressByAccountId Address List = {}", list);
				if (list != null) {
					list.add(address);
				} else {
					list = Lists.newArrayList();
					list.add(address);
				}
				memcachedClient.safeSet(Address.getKeyByAccountId(address.getAccountId()),
						MemcachedObjectType.Address.getExpiredTime(), list);
			}
		}
	}
	
	/**
	 * 4. 更新Address
	 * 
	 * @param address
	 */
	public void updateAddress(Address address) {
		addressDao.update(address);
		if (memcachedClient != null) {
			if (address.getId() != null) {
				address = addressDao.selectById(address.getId());
				memcachedClient.safeSet(Address.getKeyById(address.getId()), MemcachedObjectType.Address.getExpiredTime(), address);
			}
			if (address.getAccountId() != null) {
				List<Address> list = memcachedClient.get(Address.getKeyByAccountId(address.getAccountId()));
				logger.debug("findAddressByAccountId Address List = {}", list);
				if (list != null) {
					List<Address> l = Lists.newArrayList();
					for (Address a : list) {
						if (a.getId().equals(address.getId())) {
							l.add(address);
						} else {
							l.add(a);
						}
					}
					memcachedClient.safeSet(Address.getKeyByAccountId(address.getAccountId()),
							MemcachedObjectType.Address.getExpiredTime(), l);
				}
				
			}
		}
	}
	
	/**
	 * 5. 删除Address
	 * 
	 * @param id
	 */
	public void deleteAddress(Long id) {
		Address address = addressDao.selectById(id);
		addressDao.deleteById(id);
		if (address != null && memcachedClient != null) {
			memcachedClient.delete(Address.getKeyById(address.getId()));
			if (address.getAccountId() != null) {
				List<Address> list = memcachedClient.get(Address.getKeyByAccountId(address.getAccountId()));
				logger.debug("deleteAddress Address List = {}", list);
				if (list != null) {
					List<Address> l = Lists.newArrayList();
					for (Address a : list) {
						if (!a.getId().equals(address.getId())) {
							l.add(a);
						}
					}
					memcachedClient.safeSet(Address.getKeyByAccountId(address.getAccountId()),
							MemcachedObjectType.Address.getExpiredTime(), l);
				}
			}
		}
	}
	
	/**
	 * 6.更新默认地址为空
	 */
	public void setDefaultAddr(Long accountId, Address addr) {
		addressDao.initAllAddress(accountId);
		addr.setIsDefault(Address.DEFAULT_ADDRESS_Y);
		addressDao.update(addr);
		if (accountId != null && memcachedClient != null) {
			Address address = addressDao.selectById(addr.getId());
			memcachedClient.safeSet(Address.getKeyById(address.getId()), MemcachedObjectType.Address.getExpiredTime(), address);
			
			List<Address> list = addressDao.findByAccountId(accountId);
			if (list != null && list.size() > 0) {
				memcachedClient.safeSet(Address.getKeyByAccountId(accountId),
						MemcachedObjectType.Address.getExpiredTime(), list);
			}
		}
	}
	
	// -- Express
	
	// -- Favorite
	/**
	 * 1. 根据accountId获得Favorite列表
	 * select * from t_frvorite where account_id = ?
	 * 
	 * @param accountId
	 * @return
	 */
	public PagerModel<Favorite> findPageFavorite(Long accountId, int pageSize, int pageNo) {
		int total = favoriteDao.count(accountId);
		int offset = ((pageNo - 1) < 0 ? 0 : pageNo - 1) * pageSize;
		List<Favorite> list = favoriteDao.find(accountId, offset, pageSize);
		PagerModel<Favorite> page = new PagerModel<Favorite>();
		page.setList(list);
		page.setTotal(total);
		page.setOffset(offset);
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		page.initialize();
		return page;
	}
	
	/**
	 * 2. 根据id删除Favorite
	 * 
	 * @param accountId
	 * @param id
	 */
	public void deleteFavorite(Long id) {
		favoriteDao.deleteById(id);
	}
	
	// -- Order
	/**
	 * 1. findPageOrder
	 * 
	 * @param accountId
	 * @param type
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public PagerModel<Order> findPageOrder(Long accountId, int type, int pageSize, int pageNo) {
		int total = orderDao.count(accountId, type);
		int offset = ((pageNo - 1) < 0 ? 0 : pageNo) * pageSize;
		List<Order> list = orderDao.findPage(accountId, type, offset, pageSize);
		//订单产品按照销售商分组
		for (Order order : list) {
			Map<Long, List<Orderdetail>> group = Maps.newHashMap();
			for (Orderdetail orderDetail : order.getOdList()) {
				if (!group.containsKey(orderDetail.getSellerId())) {
					List<Orderdetail> ods = Lists.newArrayList();
					ods.add(orderDetail);
					group.put(orderDetail.getSellerId(), ods);
				} else {
					List<Orderdetail> ods = group.get(orderDetail.getSellerId());
					ods.add(orderDetail);
				}
			}
			order.setSellerList(group);
		}
		
		PagerModel<Order> page = new PagerModel<Order>();
		page.setList(list);
		page.setTotal(total);
		page.setOffset(offset);
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		return page;
	}
	
	/**
	 * 验证认证号码是否已存在
	 * @return
	 */
	public boolean validateCardNoExsist(String cardNo, int type) {
		int count = authenticationDao.countByCardNo(cardNo, type);
		if (count > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据账户获取认证信息
	 * @param accountId
	 * @param type
	 * @return
	 */
	public Authentication getAuthenticationByAccount(Long accountId, int type) {
		return authenticationDao.getAuthenticationByAccount(accountId, type);
	}
	
	/**
	 * 更新认证信息
	 */
	
	public void updateAuthentication(Authentication auth) {
		authenticationDao.update(auth);
	}
	
	/**
	 * 保存认证信息
	 * @return
	 */
	@Transactional(readOnly = false)
	public void saveAuth(Authentication auth) {
		logger.info("{}", auth);
		authenticationDao.insert(auth);
	}
	
	/**
	 * 根据账户Id获取邀请人列表
	 * @return 
	 * @return
	 */
	public PagerModel<Account> getInviteeByAccount(Map<String, Object> params, int pageNo, int pageSize) {
		int total = accountDao.countInviteeByAccount(params);
		int offset = ((pageNo - 1) < 0 ? 0 : pageNo) * pageSize;
		params.put("offset", offset);
		params.put("pageSize", pageSize);
		List<Account> list = accountDao.getInviteeByAccount(params);
		PagerModel<Account> page = new PagerModel<Account>();
		page.setList(list);
		page.setTotal(total);
		page.setOffset(offset);
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		page.setPagerSize((int) Math.ceil((double)total/(double)pageSize));
		return page;
	}
	
	public int countOrder(Long accountId, int type) {
		return orderDao.count(accountId, type);
	}
	
	public List<Order> findOrder(Long accountId, int type) {
		return orderDao.find(accountId, type);
	}

	/***
	 * 根据邀请人查询收益列表
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PagerModel<ProfitLog> getProfitByAccount(Map<String, Object> params, int pageNo, int pageSize) {
		int total = profitLogDao.countProfitByAccount(params);
		int offset = ((pageNo - 1) < 0 ? 0 : pageNo) * pageSize;
		params.put("offset", offset);
		params.put("pageSize", pageSize);
		List<ProfitLog> list = profitLogDao.getProfitByAccount(params);
		PagerModel<ProfitLog> page = new PagerModel<ProfitLog>();
		page.setList(list);
		page.setTotal(total);
		page.setOffset(offset);
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		page.setPagerSize((int) Math.ceil((double)total/(double)pageSize));
		return page;
	}
	
	/**
	 * 根据账户Id获取账户信息
	 */
	public Account getAccountById(Long accountId) {
		return accountDao.selectById(accountId);
	}
	
	/**
	 * 账户Id和邀请Id查询账户Id
	 */
	public Account getAccountByInvite(Long accountId, Long inviteId) {
		Account a = new Account();
		a.setId(accountId);
		a.setInviteId(inviteId);
		return accountDao.selectOne(a);
	}
	
	/**
	 * 统计账户邀请了人数
	 */
	public int countAccountInvite(Long accountId) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("inviteId", accountId);
		return accountDao.countInviteeByAccount(params);
	}
	
	// -- BookingOrder
	/**
	 * 1. 根据accountId和type查询BookingOrder
	 */
	public PagerModel<BookingOrder> findPageBookingOrder(Long accountId, int type, int pageSize, int pageNo) {
		int total = bookingOrderDao.count(accountId, type);
		int offset = ((pageNo - 1) < 0 ? 0 : pageNo - 1) * pageSize;
		List<BookingOrder> list = bookingOrderDao.findPage(accountId, type, offset, pageSize);
		PagerModel<BookingOrder> page = new PagerModel<BookingOrder>();
		page.setList(list);
		page.setTotal(total);
		page.setOffset(offset);
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		return page;
	}
	
}

