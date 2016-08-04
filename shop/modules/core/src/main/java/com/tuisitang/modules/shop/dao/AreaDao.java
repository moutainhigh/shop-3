package com.tuisitang.modules.shop.dao;

import java.util.List;

import com.tuisitang.modules.shop.entity.Area;

/**    
 * @{#} AreaDao.java  
 * 
 * 地区Dao
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface AreaDao extends BaseDao<Area> {

	/**
	 * 10. 根据type获得Area列表
	 * 
	 * @param type
	 * @return
	 */
	List<Area> findByType(String type);
	
	/**
	 * 11. 根据parentId获得Area列表
	 * 
	 * @param parentId
	 * @return
	 */
	List<Area> findByParentId(Long parentId);
	
	/**
	 * 12. 根据code获得Area
	 * 
	 * @param code
	 * @return
	 */
	Area getByCode(String code);
	
}
