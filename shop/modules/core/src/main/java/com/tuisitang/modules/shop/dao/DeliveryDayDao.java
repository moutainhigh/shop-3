package com.tuisitang.modules.shop.dao;

import java.util.List;

import com.tuisitang.modules.shop.entity.DeliveryDay;

/**    
 * @{#} DeliveryDayDao.java  
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface DeliveryDayDao extends BaseDao<DeliveryDay> {
	
	/**
	 * 10. 获得所有的配送日期
	 * 
	 * @return
	 */
	List<DeliveryDay> findAll();
	
	/**
	 * 11. 根据code查询DeliveryDay
	 * 
	 * @param code
	 * @return
	 */
	DeliveryDay getByCode(String code);
}
