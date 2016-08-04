package com.tuisitang.modules.shop.dao;

import com.tuisitang.modules.shop.entity.Pay;

/**    
 * @{#} PayDao.java  
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface PayDao extends BaseDao<Pay> {
	
	/**
	 * 10. 根据code获得Pay
	 * 
	 * @param code
	 * @return
	 */
	Pay getByCode(String code);
	
}
