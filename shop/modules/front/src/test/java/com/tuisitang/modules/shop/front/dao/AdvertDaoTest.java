package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.AdvertDao;
import com.tuisitang.modules.shop.entity.Advert;

/**    
 * @{#} AdvertDaoTest.java  
 * 
 * Advert Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class AdvertDaoTest extends BaseDaoTest<Advert> {

	@Autowired
	private AdvertDao advertDao;

	private Advert advert;

	@Before
	public void setUp() {
		advert = new Advert();

		Assert.assertNotNull(advertDao);
	}

	@After
	public void tearDown() {

	}

	/**
	 * 1. 添加Advert
	 */
	@Test
	@Override
	public void insert() {
		advert = new Advert();
		advert.setCode("index_top");
		advert.setTitle("首页头部广告");
		// TODO
		logger.info("添加Advert {}", advert);
		advertDao.insert(advert);
		Assert.assertNotNull(advert.getId());
	}

	/**
	 * 2. 删除Advert
	 */
	@Test
	@Override
	public void delete() {
		advert = new Advert();
		advert.setCode("index_top");
		advert.setTitle("首页头部广告");
		advertDao.insert(advert);
		// TODO
		logger.info("删除Advert {}", advert);
		Assert.assertNotNull(advert);
		Long id = advert.getId();
		Assert.assertNotNull(id);
		advertDao.delete(advert);
		Assert.assertNull(advertDao.selectById(id));
	}

	/**
	 * 3. 修改Advert
	 */
	@Test
	@Override
	public void update() {
		advert = new Advert();
		advert.setCode("index_top");
		advert.setTitle("首页头部广告");
		advertDao.insert(advert);
		// TODO
		logger.info("修改Advert {}", advert);
		Assert.assertNotNull(advert);
		Long id = advert.getId();
		Assert.assertNotNull(id);
		advert.setRemark("test");
		advertDao.update(advert);
		Assert.assertEquals("test", advertDao.selectById(id).getRemark());
	}

	/**
	 * 4. 根据Advert查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		advert = new Advert();
		advert.setCode("index_top");
		advert.setTitle("首页头部广告");
		advertDao.insert(advert);
		// TODO
		logger.info("根据Advert查询一条记录 {}", advert);
		Assert.assertNotNull(advert);
		Assert.assertNotNull(advertDao.selectOne(advert));
	}

	/**
	 * 5. 分页查询Advert
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		logger.info("分页查询Advert {}", advert);
		Assert.assertNotNull(advert);
		List<Advert> list = advertDao.selectPageList(advert);
		Assert.assertNotNull(list);
	}

	/**
	 * 6. 根据条件查询Advert
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询Advert {}", advert);
		Assert.assertNotNull(advert);
		List<Advert> list = advertDao.selectList(advert);
		Assert.assertNull(list);
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		advert = new Advert();
		advert.setCode("index_top");
		advert.setTitle("首页头部广告");
		advertDao.insert(advert);
		Long id = advert.getId();
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		advertDao.deleteById(id);
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		advert = new Advert();
		advert.setCode("index_top");
		advert.setTitle("首页头部广告");
		advertDao.insert(advert);
		Long id = advert.getId();
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		Advert entity = advertDao.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count Advert
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		logger.info("分页Count Advert {}", advert);
		Assert.assertNotNull(advert);
		int count = advertDao.selectPageCount(advert);
	}

}


