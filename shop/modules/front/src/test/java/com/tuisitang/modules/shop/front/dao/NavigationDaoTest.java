package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.NavigationDao;
import com.tuisitang.modules.shop.entity.Navigation;

/**    
 * @{#} NavigationDaoTest.java  
 * 
 * Navigation Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class NavigationDaoTest extends BaseDaoTest<Navigation> {

	@Autowired
	private NavigationDao navigationDao;

	private Navigation navigation;

	@Before
	public void setUp() {
		navigation = new Navigation();

		Assert.assertNotNull(navigationDao);
	}

	@After
	public void tearDown() {

	}

	/**
	 * 1. 添加Navigation
	 */
	@Test
	@Override
	public void insert() {
		navigation = new Navigation();
		navigation.setName("test");
		navigation.setTarget("_blank");
		navigation.setPosition("bottom");
		// TODO
		logger.info("添加Navigation {}", navigation);
		navigationDao.insert(navigation);
		Assert.assertNotNull(navigation.getId());
	}

	/**
	 * 2. 删除Navigation
	 */
	@Test
	@Override
	public void delete() {
		navigation = new Navigation();
		navigation.setName("test");
		navigation.setTarget("_blank");
		navigation.setPosition("bottom");
		navigationDao.insert(navigation);
		// TODO
		logger.info("删除Navigation {}", navigation);
		Assert.assertNotNull(navigation);
		Long id = navigation.getId();
		Assert.assertNotNull(id);
		navigationDao.delete(navigation);
		Assert.assertNull(navigationDao.selectById(id));
	}

	/**
	 * 3. 修改Navigation
	 */
	@Test
	@Override
	public void update() {
		navigation = new Navigation();
		navigation.setName("test");
		navigation.setTarget("_blank");
		navigation.setPosition("bottom");
		navigationDao.insert(navigation);
		// TODO
		logger.info("修改Navigation {}", navigation);
		Assert.assertNotNull(navigation);
		Long id = navigation.getId();
		Assert.assertNotNull(id);
		navigationDao.update(navigation);
		Assert.assertEquals("", "");
	}

	/**
	 * 4. 根据Navigation查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		navigation = new Navigation();
		navigation.setName("test");
		navigation.setTarget("_blank");
		navigation.setPosition("bottom");
		navigationDao.insert(navigation);
		// TODO
		logger.info("根据Navigation查询一条记录 {}", navigation);
		Assert.assertNotNull(navigation);
		Assert.assertNotNull(navigationDao.selectOne(navigation));
	}

	/**
	 * 5. 分页查询Navigation
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		logger.info("分页查询Navigation {}", navigation);
		Assert.assertNotNull(navigation);
		List<Navigation> list = navigationDao.selectPageList(navigation);
		Assert.assertNotNull(list);
	}

	/**
	 * 6. 根据条件查询Navigation
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询Navigation {}", navigation);
		Assert.assertNotNull(navigation);
		List<Navigation> list = navigationDao.selectList(navigation);
		Assert.assertNull(list);
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		navigation = new Navigation();
		navigation.setName("test");
		navigation.setTarget("_blank");
		navigation.setPosition("bottom");
		navigationDao.insert(navigation);
		Long id = navigation.getId();
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		navigationDao.deleteById(id);
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		navigation = new Navigation();
		navigation.setName("test");
		navigation.setTarget("_blank");
		navigation.setPosition("bottom");
		navigationDao.insert(navigation);
		Long id = navigation.getId();
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		Navigation entity = navigationDao.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count Navigation
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		logger.info("分页Count Navigation {}", navigation);
		Assert.assertNotNull(navigation);
		int count = navigationDao.selectPageCount(navigation);
	}

}


