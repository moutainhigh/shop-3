package com.tuisitang.modules.shop.dao;

import java.util.List;

import com.tuisitang.modules.shop.entity.Attribute;

/**    
 * @{#} AttributeDao.java  
 * 
 * 商品属性Dao
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface AttributeDao extends BaseDao<Attribute> {
	
	/**
	 * 10. 根据pid删除
	 * 
	 * @param pid
	 */
	void deleteByPid(Long pid);
	
	/**
	 * 11. 根据catalogId查询
	 * 
	 * @param pid
	 */
	List<Attribute> selectAttributeByCatalog(Long catalogId);
	
}	
