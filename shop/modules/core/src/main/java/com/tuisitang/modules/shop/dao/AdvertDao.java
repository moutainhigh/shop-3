package com.tuisitang.modules.shop.dao;

import com.tuisitang.modules.shop.entity.Advert;

/**    
 * @{#} AdvertDao.java  
 * 
 * 广告Dao
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface AdvertDao extends BaseDao<Advert> {

	/**
	 * 10. 根据code获得Advert
	 * 
	 * @param code
	 * @return
	 */
	Advert getByCode(String code);
	
}
