package com.tuisitang.modules.shop.dao;

import java.util.List;

import com.tuisitang.modules.shop.entity.Address;

/**    
 * @{#} AddressDao.java  
 * 
 * 会员地址Dao
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface AddressDao extends BaseDao<Address> {

	/**
	 * 初始化所有的地址全部为不选择
	 * 
	 * @param accountId
	 */
	void initAllAddress(Long accountId);
	
	/**
	 * 11. 根据accountId获得Address列表
	 * 
	 * @param accountId
	 * @return
	 */
	List<Address> findByAccountId(Long accountId);
	
}
