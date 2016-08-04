package com.tuisitang.modules.shop.dao;

import com.tuisitang.modules.shop.entity.WechatUser;

/**    
 * @{#} WechatUserDao.java  
 * 
 * 微信用户Dao
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface WechatUserDao extends BaseDao<WechatUser> {

	/**
	 * 10. 根据openid获得WechatUser
	 * 
	 * @param openid
	 * @return
	 */
	WechatUser getByOpenid(String openid);
	
	/**
	 * 11. 根据openid删除
	 * 
	 * @param openid
	 */
	void deleteByOpenid(String openid);
}
