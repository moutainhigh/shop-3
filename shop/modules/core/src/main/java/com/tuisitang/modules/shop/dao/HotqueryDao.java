package com.tuisitang.modules.shop.dao;

import java.util.List;

import com.tuisitang.modules.shop.entity.Hotquery;

/**    
 * @{#} HotqueryDao.java  
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface HotqueryDao extends BaseDao<Hotquery> {
	/**
	 * 10. 根据type获得Hotquery列表
	 * 
	 * @param type
	 * @return
	 */
	List<Hotquery> findByType(String type);
	
}
