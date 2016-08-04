package com.tuisitang.modules.shop.dao;

import org.apache.ibatis.annotations.Param;

import com.tuisitang.modules.shop.entity.EmailNotifyProduct;

/**    
 * @{#} EmailNotifyProductDao.java  
 * 
 * 到货商品邮件通知Dao
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface EmailNotifyProductDao extends BaseDao<EmailNotifyProduct> {

	/**
	 * 10. 根据accountId productID统计
	 * 
	 * @param accountId
	 * @param productID
	 * @return
	 */
	int selectCount(@Param("accountId") Long accountId, @Param("productID") Long productID);

}
