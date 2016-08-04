package com.tuisitang.modules.shop.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tuisitang.modules.shop.entity.Account;

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
public interface AccountDao extends BaseDao<Account> {

	/**
	 * 10. 根据条件统计会员账号数量
	 * 
	 * @param e
	 * @return
	 */
	int selectCount(Account e);

	/**
	 * 11. 根据id更新会员密码
	 * 
	 * @param id
	 * @param password
	 */
	void updatePasswordById(@Param("id") Long id, @Param("password") String password);

	/**
	 * 12. 根据id更新会员Email
	 * 
	 * @param id
	 * @param email
	 */
	void updateEmailById(@Param("id") Long id, @Param("email") String email);

	/**
	 * 13. 根据会员id更新会员积分，如果rank变化了，同时更新rank
	 * 
	 * @param id
	 * @param score
	 * @param rank
	 */
	void updateScoreById(@Param("id") Long id, @Param("score") int score, @Param("rank") String rank);

	/**
	 * 14. 根据mobile获得Account
	 * 
	 * @param mobile
	 * @return
	 */
	Account getByMobile(@Param("mobile") String mobile);
	
	/**
	 * 15. 更新登录信息
	 * 
	 * @param id
	 * @param lastLoginIp
	 * @param lastLoginArea
	 * @param diffAreaLogin
	 */
	void updateLoginInfo(@Param("id") Long id,
			@Param("lastLoginIp") String lastLoginIp,
			@Param("lastLoginArea") String lastLoginArea,
			@Param("diffAreaLogin") String diffAreaLogin);
	
	/**
	 * 16. 根据id更新会员Mobile
	 * 
	 * @param id
	 * @param mobile
	 */
	void updateMobileById(@Param("id") Long id, @Param("mobile") String mobile);
	
	/**
	 * 17. 根据inviteCode查询Account
	 * 
	 * @param inviteCode
	 * @return
	 */
	Account getByInviteCode(String inviteCode);
	
	/**
	 * 18. 根据id更新会员的inviteCode
	 * 
	 * @param id
	 * @param inviteCode
	 * @param qrcodeUrl
	 */
	void updateInviteCodeById(@Param("id") Long id, @Param("inviteCode") String inviteCode, @Param("qrcodeUrl") String qrcodeUrl);
	
	/**
	 * 统计被邀请人信息
	 * @param params
	 * @return
	 */
	int countInviteeByAccount(Map<String, Object> params);
	
	/**
	 * 根据账户Id获取邀请人信息
	 */
	List<Account> getInviteeByAccount(Map<String, Object> params);
	
	/**
	 * 19. 根据openid获得Account
	 * 
	 * @param openid
	 * @return
	 */
	Account getByOpenid(@Param("openid") String openid);
	
	/**
	 * 20. 根据id更新会员的openid
	 * 
	 * @param id
	 * @param openid
	 */
	void updateOpenidById(@Param("id") Long id, @Param("openid") String openid);
	
	/**
	 * 21. 根据id更新会员的account
	 * 
	 * @param id
	 * @param account
	 */
	void updateAccountById(@Param("id") Long id, @Param("account") String account);
	
	/**
	 * 22. 根据account获得Account
	 * 
	 * @param account
	 * @return
	 */
	Account getByAccount(@Param("account") String account);
	
}
