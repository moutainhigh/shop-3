package com.tuisitang.modules.shop.dao;

import com.tuisitang.modules.shop.entity.AccountRank;

/**    
 * @{#} AdvertDao.java  
 * 
 * 广告Dao
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface AccountRankDao extends BaseDao<AccountRank> {
	public AccountRank getAccountRankByCode(String code);
}
