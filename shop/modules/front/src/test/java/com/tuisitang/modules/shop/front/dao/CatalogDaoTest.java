package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.CatalogDao;
import com.tuisitang.modules.shop.entity.Catalog;

/**    
 * @{#} CatalogDaoTest.java  
 * 
 * Catalog Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class CatalogDaoTest extends BaseDaoTest<Catalog> {

	@Autowired
	private CatalogDao catalogDao;

	private Catalog catalog;

	@Before
	public void setUp() {
		catalog = new Catalog();

		Assert.assertNotNull(catalogDao);
	}

	@After
	public void tearDown() {

	}

	/**
	 * 1. 添加Catalog
	 */
	@Test
	@Override
	public void insert() {
		catalog = new Catalog();
		catalog.setName("TEST");
		// TODO
		logger.info("添加Catalog {}", catalog);
		catalogDao.insert(catalog);
		Assert.assertNotNull(catalog.getId());
	}

	/**
	 * 2. 删除Catalog
	 */
	@Test
	@Override
	public void delete() {
		catalog = new Catalog();
		catalog.setName("TEST");
		catalogDao.insert(catalog);
		// TODO
		logger.info("删除Catalog {}", catalog);
		Assert.assertNotNull(catalog);
		Long id = catalog.getId();
		Assert.assertNotNull(id);
		catalogDao.delete(catalog);
		Assert.assertNull(catalogDao.selectById(id));
	}

	/**
	 * 3. 修改Catalog
	 */
	@Test
	@Override
	public void update() {
		catalog = new Catalog();
		catalog.setName("TEST");
		catalogDao.insert(catalog);
		// TODO
		logger.info("修改Catalog {}", catalog);
		Assert.assertNotNull(catalog);
		Long id = catalog.getId();
		Assert.assertNotNull(id);
		catalog.setName("TEST2");
		catalogDao.update(catalog);
		Assert.assertEquals("TEST2", catalogDao.selectById(id).getName());
	}

	/**
	 * 4. 根据Catalog查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		catalog = new Catalog();
		catalog.setName("TEST");
		catalogDao.insert(catalog);
		// TODO
		logger.info("根据Catalog查询一条记录 {}", catalog);
		Assert.assertNotNull(catalog);
		Assert.assertNotNull(catalogDao.selectOne(catalog));
	}

	/**
	 * 5. 分页查询Catalog
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		logger.info("分页查询Catalog {}", catalog);
		Assert.assertNotNull(catalog);
		List<Catalog> list = catalogDao.selectPageList(catalog);
		Assert.assertNotNull(list);
	}

	/**
	 * 6. 根据条件查询Catalog
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询Catalog {}", catalog);
		Assert.assertNotNull(catalog);
		List<Catalog> list = catalogDao.selectList(catalog);
		Assert.assertNull(list);
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		catalog = new Catalog();
		catalog.setName("TEST");
		catalogDao.insert(catalog);
		Long id = catalog.getId();
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		catalogDao.deleteById(id);
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		catalog = new Catalog();
		catalog.setName("TEST");
		catalogDao.insert(catalog);
		Long id = catalog.getId();
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		Catalog entity = catalogDao.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count Catalog
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		logger.info("分页Count Catalog {}", catalog);
		Assert.assertNotNull(catalog);
		int count = catalogDao.selectPageCount(catalog);
	}

}


