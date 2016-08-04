package com.tuisitang.modules.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tuisitang.modules.shop.entity.Favorite;

/**    
 * @{#} FavoriteDao.java  
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface FavoriteDao extends BaseDao<Favorite> {

	/**
	 * 10. 根据accountId productId统计
	 * 
	 * @param accountId
	 * @param productId
	 * @return
	 */
	int selectCount(@Param("accountId") Long accountId, @Param("productId") Long productId);

	/**
	 * 11. 根据productId统计
	 * 
	 * @param accountId
	 * @param productId
	 * @return
	 */
	int selectCountByProductId(@Param("productId") Long productId);

	/**
	 * 12. 根据accountId统计收藏的产品数
	 * 
	 * @param accountId
	 * @return
	 */
	int count(@Param("accountId") Long accountId);

	/**
	 * 13. 根据accountId查询收藏的产品
	 * 
	 * @param accountId
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	List<Favorite> find(@Param("accountId") Long accountId, @Param("offset") int offset, @Param("pageSize") int pageSize);
	
}	
