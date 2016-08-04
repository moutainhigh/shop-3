package com.tuisitang.modules.shop.dao;

import com.tuisitang.modules.shop.entity.ProductSellCount;

/**    
 * @{#} ProductHitDao.java  
 * 
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface ProductSellCountDao extends BaseDao<ProductSellCount> {

	/**
	 * 10. 新增销售数
	 * 
	 * @param productId
	 */
	void incr(Long productId);
	
	/**
	 * 统计销售数量
	 * @param productId
	 * @return
	 */
	java.lang.Integer countProductSellCount(Long productId);
	
}
