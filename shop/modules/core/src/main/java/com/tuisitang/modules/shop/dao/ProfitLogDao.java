package com.tuisitang.modules.shop.dao;

import java.util.List;
import java.util.Map;

import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.entity.ProfitLog;

/**    
 * @{#} AccountDao.java  
 * 
 * AccountDao
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface ProfitLogDao extends BaseDao<ProfitLog> {
	
	/**
	 * 统计收益数量
	 * @param params
	 * @return
	 */
	int countProfitByAccount(Map<String, Object> params);
	
	/**
	 * 根据邀请人获取收益
	 * @param params
	 * @return
	 */
	List<ProfitLog> getProfitByAccount(Map<String, Object> params);

}
	
