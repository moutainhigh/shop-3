package net.jeeshop.services.manage.news;

import java.util.List;

import net.jeeshop.core.Services;
import com.tuisitang.modules.shop.entity.News;


public interface NewsService extends Services<News> {
	public List<News> selecIndexNews(News e);

	/**
	 * @param ids
	 * @param status 2:审核通过,4:审核未通过
	 */
	public void updateStatus(Long[] ids, String status);
	
	/**
	 * 更新指定的文章 显示/不显示
	 * @param news
	 */
	public void updateDownOrUp(News news);

	public int selectCount(News news);
}
