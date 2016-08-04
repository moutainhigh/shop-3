package com.tuisitang.modules.shop.dao;

import org.apache.ibatis.annotations.Param;

import com.tuisitang.modules.shop.entity.Authentication;

/**    
 * @{#} AreaDao.java  
 * 
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface AuthenticationDao extends BaseDao<Authentication> {
	
	/**
	 * 统计证件号是否存在
	 * @param catNo
	 * @return
	 */
	public int countByCardNo(@Param("cardNo")String cardNo, @Param("type") int type);
	
	/**
	 * 根据账户Id和认证类型获取认证信息
	 */
	public Authentication getAuthenticationByAccount(@Param("accountId") Long accountId, @Param("type") int type);
	
}
