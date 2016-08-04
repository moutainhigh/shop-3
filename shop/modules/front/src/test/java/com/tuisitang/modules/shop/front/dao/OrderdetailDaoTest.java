package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.OrderdetailDao;
import com.tuisitang.modules.shop.entity.Orderdetail;

/**    
 * @{#} OrderdetailDaoTest.java  
 * 
 * Orderdetail Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class OrderdetailDaoTest extends BaseDaoTest<Orderdetail> {

	@Autowired
	private OrderdetailDao orderdetailDao;

	private Orderdetail orderdetail;

	@Before
	public void setUp() {
		orderdetail = getOrderdetail();
		orderdetailDao.insert(orderdetail);

		Assert.assertNotNull(orderdetailDao);
	}
	
	public Orderdetail getOrderdetail() {
		Orderdetail orderdetail = new Orderdetail();
		orderdetail.setOrderID(1);
		orderdetail.setProductID(1);
		orderdetail.setNumber(1);
		orderdetail.setProductName("test");
		orderdetail.setIsComment("y");
		orderdetail.setLowStocks("y");
		orderdetail.setScore(1);
		orderdetail.setSpecInfo("test");
		orderdetail.setGiftID(null);
		
		return orderdetail;
	}
	
	@After
	public void tearDown() {

	}

	/**
	 * 1. 添加Orderdetail
	 */
	@Test
	@Override
	public void insert() {
		// TODO
		orderdetail = getOrderdetail();
		
		logger.info("添加Orderdetail {}", orderdetail);
		orderdetailDao.insert(orderdetail);
		Assert.assertNotNull(orderdetail.getId());
	}

	/**
	 * 2. 删除Orderdetail
	 */
	@Test
	@Override
	public void delete() {
		// TODO
		logger.info("删除Orderdetail {}", orderdetail);
		Assert.assertNotNull(orderdetail);
		Long id = orderdetail.getId();
		Assert.assertNotNull(id);
		orderdetailDao.delete(orderdetail);
		Assert.assertNull(orderdetailDao.selectById(id));
	}

	/**
	 * 3. 修改Orderdetail
	 */
	@Test
	@Override
	public void update() {
		// TODO
		logger.info("修改Orderdetail {}", orderdetail);
		Assert.assertNotNull(orderdetail);
		Long id = orderdetail.getId();
		Assert.assertNotNull(id);
		
		orderdetail.setOrderID(2);
		orderdetail.setProductID(2);
		orderdetail.setNumber(2);
		orderdetail.setProductName("test1");
		orderdetail.setIsComment("n");
		orderdetail.setLowStocks("n");
		orderdetail.setScore(2);
		orderdetail.setSpecInfo("test1");
		orderdetail.setGiftID(null);
		orderdetailDao.update(orderdetail);
		
		Assert.assertEquals(2,orderdetail.getOrderID());
		Assert.assertEquals(2,orderdetail.getProductID());
		Assert.assertEquals(2,orderdetail.getNumber());
		Assert.assertEquals("test1",orderdetail.getProductName());
		Assert.assertEquals("n",orderdetail.getIsComment());
		Assert.assertEquals("n",orderdetail.getLowStocks());
		Assert.assertEquals(2,orderdetail.getScore());
		Assert.assertEquals("test1",orderdetail.getSpecInfo());
		Assert.assertEquals("test1",orderdetail.getGiftID());
	}

	/**
	 * 4. 根据Orderdetail查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		// TODO
		logger.info("根据Orderdetail查询一条记录 {}", orderdetail);
		Assert.assertNotNull(orderdetail);
		Assert.assertNotNull(orderdetailDao.selectOne(orderdetail));
	}

	/**
	 * 5. 分页查询Orderdetail
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		logger.info("分页查询Orderdetail {}", orderdetail);
		Assert.assertNotNull(orderdetail);
		List<Orderdetail> list = orderdetailDao.selectPageList(orderdetail);
		Assert.assertNotNull(list);
	}

	/**
	 * 6. 根据条件查询Orderdetail
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询Orderdetail {}", orderdetail);
		Assert.assertNotNull(orderdetail);
		List<Orderdetail> list = orderdetailDao.selectList(orderdetail);
		Assert.assertEquals(1, list.size());
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		Long id = orderdetail.getId();
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		orderdetailDao.deleteById(id);
		
		Assert.assertNull(orderdetailDao.selectById(id));
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		Long id = orderdetail.getId();
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		Orderdetail entity = orderdetailDao.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count Orderdetail
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		logger.info("分页Count Orderdetail {}", orderdetail);
		Assert.assertNotNull(orderdetail);
		int count = orderdetailDao.selectPageCount(orderdetail);
		Assert.assertEquals(1, count);
	}
	
	/**
	 * 10.统计订单详情数量
	 */
	public void selectCount() {
		logger.info("统计订单详情数量 Orderdetail {}", orderdetail);
		Assert.assertNotNull(orderdetail);
		int count = orderdetailDao.selectCount(orderdetail.getId());
		Assert.assertEquals(1, count);
	}

}


