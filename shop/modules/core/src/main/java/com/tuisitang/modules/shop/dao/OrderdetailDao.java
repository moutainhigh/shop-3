package com.tuisitang.modules.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tuisitang.modules.shop.entity.Orderdetail;

/**    
 * @{#} OrderdetailDao.java  
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface OrderdetailDao extends BaseDao<Orderdetail> {
	
	/**
	 * 10.
	 * 
	 * @param orderID
	 * @return
	 */
	int selectCount(Long orderID);
	
	/**
	 * 11. batchSave
	 * 
	 * @param orderId
	 * @param odList
	 */
	void batchSave(@Param("orderId") Long orderId, @Param("odList") List<Orderdetail> odList);
	
}
