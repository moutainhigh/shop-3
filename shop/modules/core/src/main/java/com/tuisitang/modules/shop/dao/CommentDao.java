package com.tuisitang.modules.shop.dao;

import com.tuisitang.modules.shop.entity.Comment;

/**    
 * @{#} CommentDao.java  
 * 
 * 评论Dao
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface CommentDao extends BaseDao<Comment> {
	
	/**
	 * 10. 根据orderid统计
	 * 
	 * @param orderid
	 * @return
	 */
	int countByOrderid(Long orderid);
	
}
