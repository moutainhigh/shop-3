package com.tuisitang.modules.shop.service;


import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tuisitang.common.cache.memcached.MemcachedObjectType;
import com.tuisitang.common.cache.memcached.SpyMemcachedClient;
import com.tuisitang.modules.shop.dao.AccountRankDao;
import com.tuisitang.modules.shop.dao.AreaDao;
import com.tuisitang.modules.shop.dao.BookingAreaDao;
import com.tuisitang.modules.shop.dao.DeliveryDayDao;
import com.tuisitang.modules.shop.dao.ExpressCompanyDao;
import com.tuisitang.modules.shop.dao.ExpressDao;
import com.tuisitang.modules.shop.dao.InviteCodeDao;
import com.tuisitang.modules.shop.dao.KeyvalueDao;
import com.tuisitang.modules.shop.dao.OssDao;
import com.tuisitang.modules.shop.dao.PayDao;
import com.tuisitang.modules.shop.dao.SmsDao;
import com.tuisitang.modules.shop.dao.SystemSettingDao;
import com.tuisitang.modules.shop.entity.AccountRank;
import com.tuisitang.modules.shop.entity.Area;
import com.tuisitang.modules.shop.entity.BookingArea;
import com.tuisitang.modules.shop.entity.DeliveryDay;
import com.tuisitang.modules.shop.entity.Express;
import com.tuisitang.modules.shop.entity.ExpressCompany;
import com.tuisitang.modules.shop.entity.InviteCode;
import com.tuisitang.modules.shop.entity.Keyvalue;
import com.tuisitang.modules.shop.entity.Oss;
import com.tuisitang.modules.shop.entity.Pay;
import com.tuisitang.modules.shop.entity.Sms;
import com.tuisitang.modules.shop.entity.SystemSetting;

/**    
 * @{#} CommonService.java  
 * 
 * Common Service
 * 
 * 管理Area/Keyvalue/...
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author stephen
 */
@Service
public class CommonService {
	private static final Logger logger = LoggerFactory.getLogger(CommonService.class);
	@Autowired(required = false)
	private SpyMemcachedClient memcachedClient;
	@Autowired
	private AreaDao areaDao;
	@Autowired
	private KeyvalueDao keyvalueDao;
	@Autowired
	private SystemSettingDao systemSettingDao;
	@Autowired
	private ExpressDao expressDao;
	@Autowired
	private ExpressCompanyDao expressCompanyDao;
	@Autowired
	private PayDao payDao;
	@Autowired
	private DeliveryDayDao deliveryDayDao;
	@Autowired
	private OssDao ossDao;
	@Autowired
	private SmsDao smsDao;
	@Autowired
	private InviteCodeDao inviteCodeDao;
	@Autowired
	private BookingAreaDao bookingAreaDao;
	@Autowired
	private AccountRankDao accountRankDao;
	
	// - Area
	/**
	 * 1. 获得所有省份
	 * 
	 * @return
	 */
	public List<Area> findProvinces() {
		logger.debug("findProvinces ");
		if (memcachedClient != null) {
			List<Area> provinces = memcachedClient.get(Area.getProvinceKey());
			if (provinces != null) {
				return provinces;
			}
		}
		List<Area> provinces = areaDao.findByType(Area.TYPE_PROVINCE);
		if (provinces != null && memcachedClient != null) {// 如果Product不为空，则缓存至Memcached
			memcachedClient.safeSet(Area.getProvinceKey(), MemcachedObjectType.Area.getExpiredTime(), provinces);
			cacheAreaList(provinces);
		}
		return provinces;
	}
	
	/**
	 * 2. 根据provinceId获得城市
	 * 
	 * @param provinceId
	 * @return
	 */
	public List<Area> findCities(Long provinceId) {
		logger.debug("cities ");
		if (memcachedClient != null) {
			List<Area> cities = memcachedClient.get(Area.getCityKey(provinceId));
			if (cities != null) {
				return cities;
			}
		}
		List<Area> cities = areaDao.findByParentId(provinceId);
		if (cities != null && memcachedClient != null) {// 如果Product不为空，则缓存至Memcached
			memcachedClient.safeSet(Area.getCityKey(provinceId), MemcachedObjectType.Area.getExpiredTime(), cities);
			cacheAreaList(cities);
		}
		return cities;
	}
	
	/**
	 * 3. 根据cityId获得乡镇
	 * 
	 * @param cityId
	 * @return
	 */
	public List<Area> findCounties(Long cityId) {
		logger.debug("counties");
		if (memcachedClient != null) {
			List<Area> counties = memcachedClient.get(Area.getCountyKey(cityId));
			if (counties != null) {
				return counties;
			}
		}
		List<Area> counties = areaDao.findByParentId(cityId);
		if (counties != null && memcachedClient != null) {// 如果Product不为空，则缓存至Memcached
			memcachedClient.safeSet(Area.getCountyKey(cityId), MemcachedObjectType.Area.getExpiredTime(), counties);
			cacheAreaList(counties);
		}
		return counties;
	}
	
	/**
	 * 4. 根据id获得Area
	 * 
	 * @param id
	 * @return
	 */
	public Area getArea(Long id) {
		if (memcachedClient != null) {
			Area area = memcachedClient.get(Area.getKey(id));
			if (area != null) {
				return area;
			}
		}
		Area area = areaDao.selectById(id);
		if (memcachedClient != null && area != null) {
			memcachedClient.safeSet(Area.getKey(id), MemcachedObjectType.Area.getExpiredTime(), area);
			memcachedClient.safeSet(Area.getKey(area.getCode()), MemcachedObjectType.Area.getExpiredTime(), area);
		}
		return area;
	}
	
	/**
	 * 5. 根据code获得Area
	 * 
	 * @param code
	 * @return
	 */
	public Area getArea(String code) {
		if (memcachedClient != null) {
			Area area = memcachedClient.get(Area.getKey(code));
			if (area != null) {
				return area;
			}
		}
		Area area = areaDao.getByCode(code);
		if (memcachedClient != null && area != null) {
			memcachedClient.safeSet(Area.getKey(area.getId()), MemcachedObjectType.Area.getExpiredTime(), area);
			memcachedClient.safeSet(Area.getKey(code), MemcachedObjectType.Area.getExpiredTime(), area);
		}
		return area;
	}
	
	/**
	 * 6. 获得所有Area
	 * 
	 * @param code
	 * @return
	 */
	public List<Area> getAllArea() {
		List<Area> areas = null;
		if (memcachedClient != null) {
			areas = memcachedClient.get(Area.getAllKey());
			if (areas != null && !areas.isEmpty()) {
				return areas;
			}
		}
		areas = areaDao.selectList(new Area());
		if (memcachedClient != null && areas != null && !areas.isEmpty()) {
			memcachedClient.safeSet(Area.getAllKey(), MemcachedObjectType.Area.getExpiredTime(), areas);
		}
		return areas;
	}
	
	private void cacheAreaList(List<Area> list) {
		if (memcachedClient != null && list != null && !list.isEmpty()) {
			for (Area area : list) {
				memcachedClient.safeSet(Area.getKey(area.getId()), MemcachedObjectType.Area.getExpiredTime(), area);
				memcachedClient.safeSet(Area.getKey(area.getCode()), MemcachedObjectType.Area.getExpiredTime(), area);
			}
		}
	}
	
	/**
	 * 获取
	 */
	
	// --Express
	/**
	 * 1. 获得所有的Express
	 * 
	 * @return
	 */
	public List<Express> getAllExpress() {
		if (memcachedClient != null) {
			List<Express> list = memcachedClient.get(Express.getKey());
			if (list != null) {
				return list;
			}
		}
		List<Express> list = expressDao.selectList(new Express());
		if (memcachedClient != null && list != null) {
			memcachedClient.safeSet(Express.getKey(), MemcachedObjectType.Express.getExpiredTime(), list);
			for (Express express : list) {
				memcachedClient.safeSet(Express.getKey(express.getId()),
						MemcachedObjectType.Express.getExpiredTime(), express);
				memcachedClient.safeSet(Express.getKey(express.getCode()),
						MemcachedObjectType.Express.getExpiredTime(), express);
			}
		}
		return list;
	}
	
	/**
	 * 2. 根据id获得Express
	 * 
	 * @param id
	 * @return
	 */
	public Express getExpress(Long id) {
		if (memcachedClient != null) {
			Express express = memcachedClient.get(Express.getKey(id));
			if (express != null) {
				return express;
			}
		}
		Express express = expressDao.selectById(id);
		if (memcachedClient != null && express != null) {
			memcachedClient.safeSet(Express.getKey(express.getId()),
					MemcachedObjectType.Express.getExpiredTime(), express);
			memcachedClient.safeSet(Express.getKey(express.getCode()),
						MemcachedObjectType.Express.getExpiredTime(), express);
		}
		return express;
	}
	
	/**
	 * 3. 根据code获得Express
	 * 
	 * @param code
	 * @return
	 */
	public Express getExpress(String code) {
		if (memcachedClient != null) {
			Express express = memcachedClient.get(Express.getKey(code));
			if (express != null) {
				return express;
			}
		}
		Express express = expressDao.getByCode(code);
		if (memcachedClient != null && express != null) {
			memcachedClient.safeSet(Express.getKey(express.getId()),
					MemcachedObjectType.Express.getExpiredTime(), express);
			memcachedClient.safeSet(Express.getKey(express.getCode()),
						MemcachedObjectType.Express.getExpiredTime(), express);
		}
		return express;
	}
	
	// --ExpressCompany
	/**
	 * 1. 获得所有的ExpressCompany
	 * 
	 * @return
	 */
	public List<ExpressCompany> getAllExpressCompany() {
		if (memcachedClient != null) {
			List<ExpressCompany> list = memcachedClient.get(ExpressCompany.getKey());
			if (list != null) {
				return list;
			}
		}
		List<ExpressCompany> list = expressCompanyDao.selectList(new ExpressCompany());
		if (memcachedClient != null && list != null) {
			memcachedClient.safeSet(ExpressCompany.getKey(), MemcachedObjectType.ExpressCompany.getExpiredTime(), list);
			for (ExpressCompany e : list) {
				memcachedClient.safeSet(ExpressCompany.getKey(e.getId()),
						MemcachedObjectType.ExpressCompany.getExpiredTime(), e);
				memcachedClient.safeSet(ExpressCompany.getKey(e.getCode()),
						MemcachedObjectType.ExpressCompany.getExpiredTime(), e);
			}
		}
		return list;
	}
	
	/**
	 * 2. 根据id获得ExpressCompany
	 * 
	 * @param id
	 * @return
	 */
	public ExpressCompany getExpressCompany(Long id) {
		if (memcachedClient != null) {
			ExpressCompany e = memcachedClient.get(ExpressCompany.getKey(id));
			if (e != null) {
				return e;
			}
		}
		ExpressCompany e = expressCompanyDao.selectById(id);
		if (memcachedClient != null && e != null) {
			memcachedClient.safeSet(ExpressCompany.getKey(e.getId()),
					MemcachedObjectType.ExpressCompany.getExpiredTime(), e);
			memcachedClient.safeSet(ExpressCompany.getKey(e.getCode()),
						MemcachedObjectType.ExpressCompany.getExpiredTime(), e);
		}
		return e;
	}
	
	/**
	 * 3. 根据code获得ExpressCompany
	 * 
	 * @param code
	 * @return
	 */
	public ExpressCompany getExpressCompany(String code) {
		if (memcachedClient != null) {
			ExpressCompany e = memcachedClient.get(ExpressCompany.getKey(code));
			if (e != null) {
				return e;
			}
		}
		ExpressCompany e = expressCompanyDao.getByCode(code);
		if (memcachedClient != null && e != null) {
			memcachedClient.safeSet(ExpressCompany.getKey(e.getId()),
					MemcachedObjectType.ExpressCompany.getExpiredTime(), e);
			memcachedClient.safeSet(ExpressCompany.getKey(e.getCode()),
						MemcachedObjectType.ExpressCompany.getExpiredTime(), e);
		}
		return e;
	}
	
	// --Keyvalue
	/**
	 * 根据id获得Keyvalue
	 * 
	 * @param id
	 * @return
	 */
	public Keyvalue getKeyvalueByKeyword(String keyword) {
		logger.debug("getKeyvalueBykeyword id = {}", keyword);
		Keyvalue keyvalue = null;
		if (memcachedClient != null) {
			keyvalue = memcachedClient.get(Keyvalue.getKeywordKey(keyword));
			if (keyvalue != null) {
				return keyvalue;
			}
		}
		keyvalue = keyvalueDao.selectByKeyword(keyword);
		if (keyvalue != null && memcachedClient != null) {// 如果Product不为空，则缓存至Memcached
			memcachedClient.safeSet(Keyvalue.getKeywordKey(keyword), 
					MemcachedObjectType.KeyValue.getExpiredTime(), keyvalue);
		}
		return keyvalue;
	}
	
	// - SystemSetting
	/**
	 * 1. 获得系统全局设定
	 */
	public SystemSetting getSystemSetting() {
		logger.debug("getSystemSetting");
		SystemSetting systemSetting = null;
		if (memcachedClient != null) {
			systemSetting = memcachedClient.get(SystemSetting.getKey());
			if (systemSetting != null) {
				return systemSetting;
			}
		}
		systemSetting = systemSettingDao.selectActiveSystemSetting();
		if (systemSetting != null && memcachedClient != null) {// 如果Product不为空，则缓存至Memcached
			memcachedClient.safeSet(SystemSetting.getKey(),
					MemcachedObjectType.SystemSetting.getExpiredTime(), systemSetting);
		}
		return systemSetting;
	}
	
	// - Pay
	/**
	 * 1. 获得所有的支付方式
	 * 
	 * @return
	 */
	public List<Pay> getAllPay() {
		if (memcachedClient != null) {
			List<Pay> list = memcachedClient.get(Pay.getKey());
			if (list != null) {
				return list;
			}
		}
		List<Pay> list = payDao.selectList(new Pay("y"));
		if (memcachedClient != null && list != null) {
			memcachedClient.safeSet(Pay.getKey(), MemcachedObjectType.Pay.getExpiredTime(), list);
			for (Pay pay : list) {
				memcachedClient.safeSet(Pay.getKey(pay.getId()),
						MemcachedObjectType.Pay.getExpiredTime(), pay);
				memcachedClient.safeSet(Pay.getKey(pay.getCode()),
						MemcachedObjectType.Pay.getExpiredTime(), pay);
			}
		}
		return list;
	}
	
	/**
	 * 2. 根据id获得Pay
	 * 
	 * @param id
	 * @return
	 */
	public Pay getPay(Long id) {
		if (memcachedClient != null) {
			Pay pay = memcachedClient.get(Pay.getKey(id));
			if (pay != null) {
				return pay;
			}
		}
		Pay pay = payDao.selectById(id);
		if (memcachedClient != null && pay != null) {
			memcachedClient.safeSet(Pay.getKey(pay.getId()),
					MemcachedObjectType.Pay.getExpiredTime(), pay);
			memcachedClient.safeSet(Pay.getKey(pay.getCode()),
						MemcachedObjectType.Pay.getExpiredTime(), pay);
		}
		return pay;
	}
	
	/**
	 * 3. 根据code获得Pay
	 * 
	 * @param code
	 * @return
	 */
	public Pay getPay(String code) {
		if (memcachedClient != null) {
			Pay pay = memcachedClient.get(Pay.getKey(code));
			if (pay != null) {
				return pay;
			}
		}
		Pay pay = payDao.getByCode(code);
		if (memcachedClient != null && pay != null) {
			memcachedClient.safeSet(Pay.getKey(pay.getId()),
					MemcachedObjectType.Pay.getExpiredTime(), pay);
			memcachedClient.safeSet(Pay.getKey(pay.getCode()),
						MemcachedObjectType.Pay.getExpiredTime(), pay);
		}
		return pay;
	}
	
	// -- DeliveryDay
	/**
	 * 1. 获得所有的配送日期
	 * 
	 * @return
	 */
	public List<DeliveryDay> findAllDeliveryDay() {
		if (memcachedClient != null) {
			List<DeliveryDay> list = memcachedClient.get(DeliveryDay.getKey());
			if (list != null) {
				return list;
			}
		}
		List<DeliveryDay> list = deliveryDayDao.findAll();
		if (memcachedClient != null && list != null) {
			memcachedClient.safeSet(DeliveryDay.getKey(), MemcachedObjectType.DeliveryDay.getExpiredTime(), list);
			for (DeliveryDay dd : list) {
				memcachedClient.safeSet(DeliveryDay.getKey(dd.getId()),
						MemcachedObjectType.DeliveryDay.getExpiredTime(), dd);
				memcachedClient.safeSet(DeliveryDay.getKey(dd.getCode()),
						MemcachedObjectType.DeliveryDay.getExpiredTime(), dd);
			}
		}
		return list;
	}
	
	public DeliveryDay getDeliveryDay(String code) {
		if (memcachedClient != null) {
			DeliveryDay dd = memcachedClient.get(DeliveryDay.getKey(code));
			if (dd != null) {
				return dd;
			}
		}
		DeliveryDay dd = deliveryDayDao.getByCode(code);
		if (memcachedClient != null && dd != null) {
			memcachedClient.safeSet(DeliveryDay.getKey(dd.getId()),
					MemcachedObjectType.DeliveryDay.getExpiredTime(), dd);
			memcachedClient.safeSet(DeliveryDay.getKey(dd.getCode()),
						MemcachedObjectType.DeliveryDay.getExpiredTime(), dd);
		}
		return dd;
	}
	
	//-- Oss
	/**
	 * 1. 根据id获得Oss
	 * 
	 * @param id
	 * @return
	 */
	public Oss getOss(Long id) {
		return ossDao.selectById(id);
	}
	
	/**
	 * 2. 根据code获得Oss
	 * 
	 * @param code
	 * @return
	 */
	public Oss getOss(String code) {
		return ossDao.getByCode(code);
	}
	
	//-- Sms
	/**
	 * 1. 保存Sms
	 * 
	 * @param sms
	 */
	@Transactional(readOnly = false)
	public void saveSms(Sms sms) {
		smsDao.insert(sms);
	}
	
	//-- InviteCode inviteCodeDao
	/**
	 * 1. 保存InviteCode
	 * 
	 * @param inviteCode
	 */
	@Transactional(readOnly = false)
	public void saveInviteCode(InviteCode inviteCode) {
		inviteCodeDao.insert(inviteCode);
	}
	
	/**
	 * 2. 批量保存InviteCode
	 * 
	 * @param set
	 */
	@Transactional(readOnly = false)
	public void saveInviteCode(Set<String> set) {
		List<String> l = Lists.newArrayList();
		for (String s : set) {
			if (l.size() < 200) {
				l.add(s);
			} else {
				inviteCodeDao.batchSave(l);
				l = Lists.newArrayList();
				l.add(s);
			}
		}
		if (!l.isEmpty()) {
			inviteCodeDao.batchSave(l);
		}
	}
	
	/**
	 * 3. 统计总数
	 * 
	 * @return
	 */
	public int countInviteCode() {
		return inviteCodeDao.selectPageCount(new InviteCode());
	}
	
	// -- BookingArea
	/**
	 * 1. 新增BookingArea
	 * 
	 * @param bookingArea
	 */
	@Transactional(readOnly = false)
	public void saveBookingArea(BookingArea bookingArea) {
		bookingAreaDao.insert(bookingArea);
	}
	
	/**
	 * 2. 根据id删除BookingArea
	 * 
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void deleteBookingArea(Long id) {
		bookingAreaDao.deleteById(id);
	}
	
	/**
	 * 3. 根据id获得BookingArea
	 * 
	 * @param id
	 * @return
	 */
	public BookingArea getBookingArea(Long id) {
		return bookingAreaDao.selectById(id);
	}
	
	/**
	 * 4. 获得所有BookingArea
	 * 
	 * @return
	 */
	public List<BookingArea> findBookingArea() {
		return bookingAreaDao.selectList(new BookingArea());
	}
	
	// -- AccountRank
	/**
	 * 根据code查询会员等级
	 */
	public AccountRank getAccountRankByCode(String code) {
		if (memcachedClient != null) {
			AccountRank ar = memcachedClient.get(AccountRank.getKey(code));
			if (ar != null) {
				return ar;
			}
		}
		AccountRank ar = accountRankDao.getAccountRankByCode(code);
		if (memcachedClient != null && ar != null) {
			memcachedClient.safeSet(AccountRank.getKey(ar.getId()),
					MemcachedObjectType.AccountRank.getExpiredTime(), ar);
			memcachedClient.safeSet(AccountRank.getKey(ar.getCode()),
					MemcachedObjectType.AccountRank.getExpiredTime(), ar);
		}
		return ar;
	}
	
	/**
	 * 根据code查询会员等级
	 */
	public List<AccountRank> getAllAccountRank() {
		return accountRankDao.selectList(new AccountRank());
	}
}
