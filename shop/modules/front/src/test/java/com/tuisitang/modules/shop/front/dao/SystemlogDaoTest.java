package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.SystemlogDao;
import com.tuisitang.modules.shop.entity.Systemlog;

/**    
 * @{#} SystemlogDaoTest.java  
 * 
 * Systemlog Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class SystemlogDaoTest extends BaseDaoTest<Systemlog> {

	@Autowired
	private SystemlogDao systemlogDao;

	private Systemlog systemlog;

	@Before
	public void setUp() {
		systemlog = getSystemlog();
		systemlogDao.insert(systemlog);

		Assert.assertNotNull(systemlogDao);
	}

	@After
	public void tearDown() {

	}
	public Systemlog getSystemlog() {
		systemlog = new Systemlog();
		systemlog.setTitle("test");
		systemlog.setContent("test");
		
		return systemlog;
	}
	/**
	 * 1. 添加Systemlog
	 */
	@Test
	@Override
	public void insert() {
		// TODO
		systemlog = new Systemlog();
		systemlog.setTitle("test");
		systemlog.setContent("test");
		logger.info("添加Systemlog {}", systemlog);
		systemlogDao.insert(systemlog);
		Assert.assertNotNull(systemlog.getId());
	}

	/**
	 * 2. 删除Systemlog
	 */
	@Test
	@Override
	public void delete() {
		// TODO
		logger.info("删除Systemlog {}", systemlog);
		Assert.assertNotNull(systemlog);
		Long id = systemlog.getId();
		Assert.assertNotNull(id);
		systemlogDao.delete(systemlog);
		Assert.assertNull(systemlogDao.selectById(id));
	}

	/**
	 * 3. 修改Systemlog
	 */
	@Test
	@Override
	public void update() {
		// TODO
		logger.info("修改Systemlog {}", systemlog);
		Assert.assertNotNull(systemlog);
		Long id = systemlog.getId();
		Assert.assertNotNull(id);
		
		systemlog.setTitle("test1");
		systemlog.setContent("test1");
		systemlogDao.update(systemlog);
		Assert.assertEquals("test1",systemlog.getTitle());
		Assert.assertEquals("test1",systemlog.getContent());
	}

	/**
	 * 4. 根据Systemlog查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		// TODO
		logger.info("根据Systemlog查询一条记录 {}", systemlog);
		Assert.assertNotNull(systemlog);
		Assert.assertNotNull(systemlogDao.selectOne(systemlog));
	}

	/**
	 * 5. 分页查询Systemlog
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		logger.info("分页查询Systemlog {}", systemlog);
		Assert.assertNotNull(systemlog);
		List<Systemlog> list = systemlogDao.selectPageList(systemlog);
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}

	/**
	 * 6. 根据条件查询Systemlog
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询Systemlog {}", systemlog);
		Assert.assertNotNull(systemlog);
		List<Systemlog> list = systemlogDao.selectList(systemlog);
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		Long id = systemlog.getId();
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		systemlogDao.deleteById(id);
		Systemlog entity = systemlogDao.selectById(id);
		Assert.assertNull(entity);
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		Long id = systemlog.getId();
		
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		Systemlog entity = systemlogDao.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count Systemlog
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		logger.info("分页Count Systemlog {}", systemlog);
		Assert.assertNotNull(systemlog);
		int count = systemlogDao.selectPageCount(systemlog);
		Assert.assertEquals(1, count);
	}

}


