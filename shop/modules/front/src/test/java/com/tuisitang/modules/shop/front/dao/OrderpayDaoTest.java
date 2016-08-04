package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.OrderpayDao;
import com.tuisitang.modules.shop.entity.Orderpay;

/**    
 * @{#} OrderpayDaoTest.java  
 * 
 * Orderpay Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class OrderpayDaoTest extends BaseDaoTest<Orderpay> {

	@Autowired
	private OrderpayDao orderpayDao;

	private Orderpay orderpay;

	@Before
	public void setUp() {
		orderpay = new Orderpay();

		Assert.assertNotNull(orderpayDao);
	}

	@After
	public void tearDown() {

	}
	
	public Orderpay getOrderPay() {
		Orderpay orderpay = new Orderpay();
		orderpay.setOrderid(1l);
		orderpay.setPaystatus("1");
		orderpay.setPaymethod("test");
		orderpay.setConfirmuser("test");
		orderpay.setRemark("test");
		orderpay.setTradeNo("test");
		
		return orderpay;
	}
	
	/**
	 * 1. 添加Orderpay
	 */
	@Test
	@Override
	public void insert() {
		// TODO
		orderpay = getOrderPay();
		orderpayDao.insert(orderpay);
		
		logger.info("添加Orderpay {}", orderpay);
		orderpayDao.insert(orderpay);
		Assert.assertNotNull(orderpay.getId());
	}

	/**
	 * 2. 删除Orderpay
	 */
	@Test
	@Override
	public void delete() {
		// TODO
		orderpay = getOrderPay();
		orderpayDao.insert(orderpay);
		
		logger.info("删除Orderpay {}", orderpay);
		Assert.assertNotNull(orderpay);
		Long id = orderpay.getId();
		Assert.assertNotNull(id);
		orderpayDao.delete(orderpay);
		Assert.assertNull(orderpayDao.selectById(id));
	}

	/**
	 * 3. 修改Orderpay
	 */
	@Test
	@Override
	public void update() {
		// TODO
		orderpay = getOrderPay();
		orderpayDao.insert(orderpay);
		
		logger.info("修改Orderpay {}", orderpay);
		Assert.assertNotNull(orderpay);
		Long id = orderpay.getId();
		Assert.assertNotNull(id);
		
		orderpay.setOrderid(2l);
		orderpay.setPaystatus("2");
		orderpay.setPaymethod("test1");
		orderpay.setConfirmuser("test1");
		orderpay.setRemark("test1");
		orderpay.setTradeNo("test1");
		
		orderpayDao.update(orderpay);
		
		Assert.assertEquals(2l,orderpay.getOrderid().longValue());
		Assert.assertEquals("2",orderpay.getPaystatus());
		Assert.assertEquals("test1",orderpay.getPaymethod());
		Assert.assertEquals("test1",orderpay.getConfirmuser());
		Assert.assertEquals("test1",orderpay.getRemark());
		Assert.assertEquals("test1",orderpay.getTradeNo());
	}

	/**
	 * 4. 根据Orderpay查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		// TODO
		orderpay = getOrderPay();
		orderpayDao.insert(orderpay);
		
		logger.info("根据Orderpay查询一条记录 {}", orderpay);
		Assert.assertNotNull(orderpay);
		Assert.assertNotNull(orderpayDao.selectOne(orderpay));
	}

	/**
	 * 5. 分页查询Orderpay
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		orderpay = getOrderPay();
		orderpayDao.insert(orderpay);
		
		logger.info("分页查询Orderpay {}", orderpay);
		Assert.assertNotNull(orderpay);
		List<Orderpay> list = orderpayDao.selectPageList(orderpay);
		Assert.assertNotNull(list);
	}

	/**
	 * 6. 根据条件查询Orderpay
	 */
	@Override
	public void selectList() {
		// TODO
		orderpay = getOrderPay();
		orderpayDao.insert(orderpay);
		
		logger.info("根据条件查询Orderpay {}", orderpay);
		Assert.assertNotNull(orderpay);
		List<Orderpay> list = orderpayDao.selectList(orderpay);
		Assert.assertNull(list);
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		orderpay = getOrderPay();
		orderpayDao.insert(orderpay);
		
		Long id = orderpay.getId();
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		orderpayDao.deleteById(id);
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		orderpay = getOrderPay();
		orderpayDao.insert(orderpay);
		
		Long id = orderpay.getId();
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		Orderpay entity = orderpayDao.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count Orderpay
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		orderpay = getOrderPay();
		orderpayDao.insert(orderpay);
		
		logger.info("分页Count Orderpay {}", orderpay);
		Assert.assertNotNull(orderpay);
		int count = orderpayDao.selectPageCount(orderpay);
		Assert.assertNotEquals(0, count);
	}

}


