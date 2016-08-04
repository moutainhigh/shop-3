package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.KeyvalueDao;
import com.tuisitang.modules.shop.entity.Keyvalue;

/**    
 * @{#} KeyvalueDaoTest.java  
 * 
 * Keyvalue Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class KeyvalueDaoTest extends BaseDaoTest<Keyvalue> {

	@Autowired
	private KeyvalueDao keyvalueDao;

	private Keyvalue keyvalue;

	@Before
	public void setUp() {
		keyvalue = new Keyvalue();

		Assert.assertNotNull(keyvalueDao);
	}

	@After
	public void tearDown() {

	}

	/**
	 * 1. 添加Keyvalue
	 */
	@Test
	@Override
	public void insert() {
		keyvalue = new Keyvalue();
		keyvalue.setKeyword("test");
		keyvalue.setValue("test");
		// TODO
		logger.info("添加Keyvalue {}", keyvalue);
		keyvalueDao.insert(keyvalue);
		Assert.assertNotNull(keyvalue.getId());
	}

	/**
	 * 2. 删除Keyvalue
	 */
	@Test
	@Override
	public void delete() {
		keyvalue = new Keyvalue();
		keyvalue.setKeyword("test");
		keyvalue.setValue("test");
		keyvalueDao.insert(keyvalue);
		// TODO
		logger.info("删除Keyvalue {}", keyvalue);
		Assert.assertNotNull(keyvalue);
		Long id = keyvalue.getId();
		Assert.assertNotNull(id);
		keyvalueDao.delete(keyvalue);
		Assert.assertNull(keyvalueDao.selectById(id));
	}

	/**
	 * 3. 修改Keyvalue
	 */
	@Test
	@Override
	public void update() {
		keyvalue = new Keyvalue();
		keyvalue.setKeyword("test");
		keyvalue.setValue("test");
		keyvalueDao.insert(keyvalue);
		// TODO
		logger.info("修改Keyvalue {}", keyvalue);
		Assert.assertNotNull(keyvalue);
		Long id = keyvalue.getId();
		Assert.assertNotNull(id);
		keyvalueDao.update(keyvalue);
		Assert.assertEquals("", "");
	}

	/**
	 * 4. 根据Keyvalue查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		keyvalue = new Keyvalue();
		keyvalue.setKeyword("test");
		keyvalue.setValue("test");
		keyvalueDao.insert(keyvalue);
		// TODO
		logger.info("根据Keyvalue查询一条记录 {}", keyvalue);
		Assert.assertNotNull(keyvalue);
		Assert.assertNotNull(keyvalueDao.selectOne(keyvalue));
	}

	/**
	 * 5. 分页查询Keyvalue
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		logger.info("分页查询Keyvalue {}", keyvalue);
		Assert.assertNotNull(keyvalue);
		List<Keyvalue> list = keyvalueDao.selectPageList(keyvalue);
		Assert.assertNotNull(list);
	}

	/**
	 * 6. 根据条件查询Keyvalue
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询Keyvalue {}", keyvalue);
		Assert.assertNotNull(keyvalue);
		List<Keyvalue> list = keyvalueDao.selectList(keyvalue);
		Assert.assertNull(list);
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		keyvalue = new Keyvalue();
		keyvalue.setKeyword("test");
		keyvalue.setValue("test");
		keyvalueDao.insert(keyvalue);
		Long id = keyvalue.getId();
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		keyvalueDao.deleteById(id);
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		keyvalue = new Keyvalue();
		keyvalue.setKeyword("test");
		keyvalue.setValue("test");
		keyvalueDao.insert(keyvalue);
		Long id = keyvalue.getId();
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		Keyvalue entity = keyvalueDao.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count Keyvalue
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		logger.info("分页Count Keyvalue {}", keyvalue);
		Assert.assertNotNull(keyvalue);
		int count = keyvalueDao.selectPageCount(keyvalue);
	}

}


