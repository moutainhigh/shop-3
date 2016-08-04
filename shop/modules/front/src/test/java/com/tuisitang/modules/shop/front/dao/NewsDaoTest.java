package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.NewsDao;
import com.tuisitang.modules.shop.entity.News;

/**    
 * @{#} NewsDaoTest.java  
 * 
 * News Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class NewsDaoTest extends BaseDaoTest<News> {

	@Autowired
	private NewsDao newsDao;

	private News news;

	@Before
	public void setUp() {
		news = new News();

		Assert.assertNotNull(newsDao);
	}

	@After
	public void tearDown() {

	}

	/**
	 * 1. 添加News
	 */
	@Test
	@Override
	public void insert() {
		news = new News();
		news.setType(News.TYPE_NOTICE);
		news.setTitle("test");
		// TODO
		logger.info("添加News {}", news);
		newsDao.insert(news);
		Assert.assertNotNull(news.getId());
	}

	/**
	 * 2. 删除News
	 */
	@Test
	@Override
	public void delete() {
		news = new News();
		news.setType(News.TYPE_NOTICE);
		news.setTitle("test");
		newsDao.insert(news);
		// TODO
		logger.info("删除News {}", news);
		Assert.assertNotNull(news);
		Long id = news.getId();
		Assert.assertNotNull(id);
		newsDao.delete(news);
		Assert.assertNull(newsDao.selectById(id));
	}

	/**
	 * 3. 修改News
	 */
	@Test
	@Override
	public void update() {
		news = new News();
		news.setType(News.TYPE_NOTICE);
		news.setTitle("test");
		newsDao.insert(news);
		// TODO
		logger.info("修改News {}", news);
		Assert.assertNotNull(news);
		Long id = news.getId();
		Assert.assertNotNull(id);
		newsDao.update(news);
		Assert.assertEquals("", "");
	}

	/**
	 * 4. 根据News查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		news = new News();
		news.setType(News.TYPE_NOTICE);
		news.setTitle("test");
		newsDao.insert(news);
		// TODO
		logger.info("根据News查询一条记录 {}", news);
		Assert.assertNotNull(news);
		Assert.assertNotNull(newsDao.selectOne(news));
	}

	/**
	 * 5. 分页查询News
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		logger.info("分页查询News {}", news);
		Assert.assertNotNull(news);
		List<News> list = newsDao.selectPageList(news);
		Assert.assertNotNull(list);
	}

	/**
	 * 6. 根据条件查询News
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询News {}", news);
		Assert.assertNotNull(news);
		List<News> list = newsDao.selectList(news);
		Assert.assertNull(list);
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		news = new News();
		news.setType(News.TYPE_NOTICE);
		news.setTitle("test");
		newsDao.insert(news);
		Long id = news.getId();
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		newsDao.deleteById(id);
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		news = new News();
		news.setType(News.TYPE_NOTICE);
		news.setTitle("test");
		newsDao.insert(news);
		Long id = news.getId();
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		News entity = newsDao.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count News
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		logger.info("分页Count News {}", news);
		Assert.assertNotNull(news);
		int count = newsDao.selectPageCount(news);
	}

}


