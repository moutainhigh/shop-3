package com.tuisitang.modules.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tuisitang.modules.shop.entity.BookingOrder;

/**    
 * @{#} BookingOrderDao.java  
 * 
 * 预约单Dao
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface BookingOrderDao extends BaseDao<BookingOrder> {

	/**
	 * 10. 根据accountId和type统计预约单数量
	 * 
	 * @param accountId
	 * @param type
	 * @return
	 */
	int count(@Param("accountId")Long accountId, @Param("type")int type);

	/**
	 * 11. 根据accountId和type查询预约单
	 * 
	 * @param accountId
	 * @param type
	 * @param offset
	 * @param pageSize
	 * @return
	 */
	List<BookingOrder> findPage(@Param("accountId")Long accountId, @Param("type")int type, @Param("offset")int offset, @Param("pageSize")int pageSize);
	
	/**
	 * 12. 更新BookingOrder状态
	 * 
	 * @param id
	 * @param status
	 */
	void updateStatus(@Param("id") Long id, @Param("status") int status);
}
