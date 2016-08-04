package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.OrderlogDao;
import com.tuisitang.modules.shop.entity.Orderlog;

/**    
 * @{#} OrderlogDaoTest.java  
 * 
 * Orderlog Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class OrderlogDaoTest extends BaseDaoTest<Orderlog> {

	@Autowired
	private OrderlogDao orderlogDao;

	private Orderlog orderlog;

	@Before
	public void setUp() {
		orderlog = getOrderlog();
		orderlogDao.insert(orderlog);

		Assert.assertNotNull(orderlogDao);
	}

	@After
	public void tearDown() {

	}
	public Orderlog getOrderlog() {
		Orderlog orderlog = new Orderlog();
		orderlog.setOrderid(1l);
		orderlog.setAccountId(1l);
//		orderlog.setAccount("test");
		orderlog.setContent("test");
		orderlog.setAccountType("1");
		return orderlog;
	}

	/**
	 * 1. 添加Orderlog
	 */
	@Test
	@Override
	public void insert() {
		// TODO
		orderlog = getOrderlog();
		
		logger.info("添加Orderlog {}", orderlog);
		orderlogDao.insert(orderlog);
		Assert.assertNotNull(orderlog.getId());
	}

	/**
	 * 2. 删除Orderlog
	 */
	@Test
	@Override
	public void delete() {
		// TODO
		logger.info("删除Orderlog {}", orderlog);
		Assert.assertNotNull(orderlog);
		Long id = orderlog.getId();
		Assert.assertNotNull(id);
		orderlogDao.delete(orderlog);
		Assert.assertNull(orderlogDao.selectById(id));
	}

	/**
	 * 3. 修改Orderlog
	 */
	@Test
	@Override
	public void update() {
		// TODO
		logger.info("修改Orderlog {}", orderlog);
		Assert.assertNotNull(orderlog);
		Long id = orderlog.getId();
		Assert.assertNotNull(id);
		
		orderlog.setOrderid(2l);
		orderlog.setAccountId(2l);
		orderlog.setContent("test1");
//		orderlog.setAccount("test1");
		orderlog.setAccountType("2");
		orderlogDao.update(orderlog);
		
		Assert.assertEquals(2l,orderlog.getOrderid().longValue());
		Assert.assertEquals(2l,orderlog.getAccountId().longValue());
//		Assert.assertEquals("test1",orderlog.getAccount());
		Assert.assertEquals("test1",orderlog.getContent());
		Assert.assertEquals("2",orderlog.getAccountType());
	}

	/**
	 * 4. 根据Orderlog查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		// TODO
		logger.info("根据Orderlog查询一条记录 {}", orderlog);
		Assert.assertNotNull(orderlog);
		Assert.assertNotNull(orderlogDao.selectOne(orderlog));
	}

	/**
	 * 5. 分页查询Orderlog
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		logger.info("分页查询Orderlog {}", orderlog);
		Assert.assertNotNull(orderlog);
		List<Orderlog> list = orderlogDao.selectPageList(orderlog);
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}

	/**
	 * 6. 根据条件查询Orderlog
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询Orderlog {}", orderlog);
		Assert.assertNotNull(orderlog);
		List<Orderlog> list = orderlogDao.selectList(orderlog);
		Assert.assertNull(list);
		Assert.assertEquals(1, list.size());
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		Long id = orderlog.getId();
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		orderlogDao.deleteById(id);
		
		Assert.assertNull(orderlogDao.selectById(id));
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		Long id = orderlog.getId();
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		Orderlog entity = orderlogDao.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count Orderlog
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		logger.info("分页Count Orderlog {}", orderlog);
		Assert.assertNotNull(orderlog);
		int count = orderlogDao.selectPageCount(orderlog);
		Assert.assertEquals(1, count);
	}

}


