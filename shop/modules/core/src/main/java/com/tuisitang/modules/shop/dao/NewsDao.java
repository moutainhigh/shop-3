package com.tuisitang.modules.shop.dao;

import java.util.List;

import com.tuisitang.modules.shop.entity.News;

/**    
 * @{#} NewsDao.java  
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface NewsDao extends BaseDao<News> {
	/**
	 * @param e
	 * @return
	 */
	List<News> selecIndexNews(News e);

	List<News> selectNoticeList(News news);

	News selectSimpleOne(News news);
}
