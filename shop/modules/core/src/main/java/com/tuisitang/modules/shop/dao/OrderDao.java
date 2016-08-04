package com.tuisitang.modules.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tuisitang.modules.shop.entity.Order;
import com.tuisitang.modules.shop.entity.OrderSimpleReport;

/**    
 * @{#} OrderDao.java  
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface OrderDao extends BaseDao<Order> {
	/**
	 * 10. selectOrderInfo
	 * 
	 * @param order
	 * @return
	 */
	List<Order> selectOrderInfo(Order order);

	/**
	 * 11. selectOrdersSimpleReport
	 * 
	 * @param accountId
	 * @return
	 */
	OrderSimpleReport selectOrdersSimpleReport(Long accountId);
	
	/**
	 * 12. countByAccountId
	 * 
	 * @param accountId
	 * @return
	 */
	int countByAccountId(@Param("accountId") Long accountId);

	/**
	 * 13. findByAccountId
	 * 
	 * @param accountId
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	List<Order> findByAccountId(@Param("accountId") Long accountId, @Param("offset") int offset, @Param("pageSize") int pageSize);
	
	/**
	 * 14. count
	 * 
	 * @param accountId
	 * @param type
	 * @return
	 */
	int count(@Param("accountId") Long accountId, @Param("type") int type);

	/**
	 * 15. findPage
	 * 
	 * @param accountId
	 * @param type
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	List<Order> findPage(@Param("accountId") Long accountId, @Param("type") int type, @Param("offset") int offset, @Param("pageSize") int pageSize);
	
	/**
	 * 16. findPage
	 * 
	 * @param accountId
	 * @param type
	 * @return
	 */
	List<Order> find(@Param("accountId") Long accountId, @Param("type") int type);
	
	/**
	 * 17. 更新订单状态
	 * 
	 * @param id
	 * @param status
	 */
	void updateStatus(@Param("id") Long id, @Param("status") String status);
	
}
