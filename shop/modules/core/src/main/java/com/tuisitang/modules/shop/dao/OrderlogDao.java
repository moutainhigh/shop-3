package com.tuisitang.modules.shop.dao;

import com.tuisitang.modules.shop.entity.Orderlog;

/**    
 * @{#} OrderlogDao.java  
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface OrderlogDao extends BaseDao<Orderlog> {
	
	int selectCount(Orderlog orderlog);
	
}
