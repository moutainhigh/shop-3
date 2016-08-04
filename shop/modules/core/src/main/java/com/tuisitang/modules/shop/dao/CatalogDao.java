package com.tuisitang.modules.shop.dao;

import java.util.List;

import com.tuisitang.modules.shop.entity.Catalog;
import com.tuisitang.modules.shop.entity.ProductCatalogRela;

/**    
 * @{#} CatalogDao.java  
 * 
 * 分类Dao
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface CatalogDao extends BaseDao<Catalog> {
	/**
	 * 根据目录Id获取其子目录Id
	 * @param catalogId
	 * @return
	 */
	List<Catalog> getSubCatalog(Long catalogId);
	
	/**
	 * 根据产品ID获取其目录
	 */
	List<Catalog> getCatalogByProduct(Long productId);
}
