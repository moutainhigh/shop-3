package com.tuisitang.modules.shop.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tuisitang.modules.shop.entity.Product;
import com.tuisitang.modules.shop.entity.ProductAttribute;
import com.tuisitang.modules.shop.entity.ProductCatalogRela;
import com.tuisitang.modules.shop.entity.ProductStockInfo;

/**    
 * @{#} ProductDao.java  
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface ProductDao extends BaseDao<Product> {
	/**
	 * @param id
	 * @return
	 */
	List<ProductAttribute> selectParameterList(Long productId);

	ProductStockInfo selectStockList(Long productId);

	Product selectListProductHTML(Long productId);

	List<Product> selectProductListByIds(List<Long> productIds);

	/**
	 * 支付成功后更新商品库存数据
	 * 
	 * @param product
	 */
	void updateStockAfterPaySuccess(Long productId, int stock, int addSellcount);

	List<Product> selectHotSearch(List<Long> productIds);

	List<Product> loadHotProductShowInSuperMenu(List<Long> catalogIds);

	void updateHit(Long productId);

	List<Product> selectPageLeftHotProducts(@Param("catalogId") Long catalogId, @Param("limit") int limit);

	List<Product> selectActivityProductList(List<Long> productIds);
	
	/**
	 * 热门商品
	 * @return
	 */
	List<Product> selectHotProducts(@Param("limit") int limit);
	
	/**
	 * 特价商品
	 * @return
	 */
	List<Product> selectSaleProducts(@Param("limit") int limit);
	
	/**
	 * 最新商品
	 * @return
	 */
	List<Product> selectNewProducts(@Param("limit") int limit);
	
	/**
	 * 根据参数查询产品
	 * @param params
	 * @return
	 */
	List<Product> findPageProduct(Map<String, Object> params);
	
	/**
	 * 根据id获得Product 
	 * Product带有Activity
	 * 
	 * @param id
	 * @return
	 */
	Product getWithActivity(Long id);
	/**
	 * 根据参数统计产品数量
	 * @param params
	 * @return
	 */
	int countProduct(Map<String, Object> params);
	
	/**
	 * 获取指定类型活动
	 */
	List<Product> getActivityProductByType(String type);
	
}
