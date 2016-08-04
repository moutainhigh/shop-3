package com.tuisitang.modules.shop.dao;

import com.tuisitang.modules.shop.entity.ProductHit;

/**    
 * @{#} ProductHitDao.java  
 * 
 * 产品点击数 DAO
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface ProductHitDao extends BaseDao<ProductHit> {

	/**
	 * 10. 根据productId统计
	 * 
	 * @param productId
	 * @return
	 */
	Integer getHitByProductId(Long productId);
	
	/**
	 * 11. 新增点击数
	 * 
	 * @param productId
	 */
	void incr(Long productId);
	
}
