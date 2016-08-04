package com.tuisitang.modules.shop.dao;

import com.tuisitang.modules.shop.entity.Email;

/**    
 * @{#} EmailDao.java  
 * 
 * 邮箱Dao
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface EmailDao extends BaseDao<Email> {
	
	/**
	 * 10. 用户注册的时候，如果发出了多封邮件，则激活的时候，此方法使所有发出的链接都失效
	 * 
	 * @param accountId
	 */
	void updateEmailInvalidWhenReg(long accountId);
	
}
