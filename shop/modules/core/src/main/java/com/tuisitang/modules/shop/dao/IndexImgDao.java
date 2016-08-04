package com.tuisitang.modules.shop.dao;

import java.util.List;

import com.tuisitang.modules.shop.entity.IndexImg;

/**    
 * @{#} IndexImgDao.java  
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface IndexImgDao extends BaseDao<IndexImg> {
	/**
	 *  根据type查询IndexImg
	 * 
	 * @param e
	 * @return
	 */
	List<IndexImg> getIndexImgByType(String type);
}
