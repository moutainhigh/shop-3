package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.OrderDao;
import com.tuisitang.modules.shop.entity.Order;
import com.tuisitang.modules.shop.entity.OrderSimpleReport;

/**    
 * @{#} OrderDaoTest.java  
 * 
 * Order Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class OrderDaoTest extends BaseDaoTest<Order> {

	@Autowired
	private OrderDao orderDao;

	private Order order;

	@Before
	public void setUp() {
		order = getOrder();
		orderDao.insert(order);
		Assert.assertNotNull(orderDao);
	}
	public Order getOrder() {
		order = new Order();
		Order order = new Order();
		order.setAccountId(1l);
		order.setStatus("1");
		order.setRefundStatus("1");
		order.setQuantity(1);
		order.setPaystatus("1");
		order.setExpressCode("test");
		order.setExpressName("test");
		order.setOtherRequirement("test");
		order.setRemark("test");
		order.setExpressNo("test");
		order.setExpressCompanyName("test");
		order.setLowStocks("1");
		order.setConfirmSendProductRemark("test");
		order.setClosedComment("1");
		order.setScore(1);
		return order;
	}

	@After
	public void tearDown() {

	}

	/**
	 * 1. 添加Order
	 */
	@Test
	@Override
	public void insert() {
		// TODO
		order = getOrder();
		logger.info("添加Order {}", order);
		orderDao.insert(order);
		Assert.assertNotNull(order.getId());
	}

	/**
	 * 2. 删除Order
	 */
	@Test
	@Override
	public void delete() {
		// TODO
		logger.info("删除Order {}", order);
		Assert.assertNotNull(order);
		Long id = order.getId();
		Assert.assertNotNull(id);
		orderDao.delete(order);
		Assert.assertNull(orderDao.selectById(id));
	}

	/**
	 * 3. 修改Order
	 */
	@Test
	@Override
	public void update() {
		// TODO
		logger.info("修改Order {}", order);
		Assert.assertNotNull(order);
		Long id = order.getId();
		Assert.assertNotNull(id);
		
		order.setAccountId(2l);
		order.setStatus("2");
		order.setRefundStatus("2");
		order.setQuantity(2);
		order.setPaystatus("2");
		order.setExpressCode("test1");
		order.setExpressName("test1");
		order.setOtherRequirement("test1");
		order.setRemark("test1");
		order.setExpressNo("test1");
		order.setExpressCompanyName("test1");
		order.setLowStocks("2");
		order.setConfirmSendProductRemark("test1");
		order.setClosedComment("2");
		order.setScore(2);
		orderDao.update(order);
		
		order = orderDao.selectById(order.getId());
		Assert.assertEquals(2l,order.getAccountId().longValue());
		Assert.assertEquals("2",order.getStatus());
		Assert.assertEquals("2",order.getRefundStatus());
		Assert.assertEquals(2,order.getQuantity());
		Assert.assertEquals("2",order.getPaystatus());
		Assert.assertEquals("test1",order.getExpressCode());
		Assert.assertEquals("test1",order.getExpressName());
		Assert.assertEquals("test1",order.getOtherRequirement());
		Assert.assertEquals("test1",order.getRemark());
		Assert.assertEquals("test1",order.getExpressNo());
		Assert.assertEquals("test1",order.getExpressCompanyName());
		Assert.assertEquals("2",order.getLowStocks());
		Assert.assertEquals("test1",order.getConfirmSendProductRemark());
		Assert.assertEquals("2",order.getClosedComment());
		Assert.assertEquals(2,order.getScore());
	}

	/**
	 * 4. 根据Order查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		// TODO
		logger.info("根据Order查询一条记录 {}", order);
		Assert.assertNotNull(order);
		Assert.assertNotNull(orderDao.selectOne(order));
	}

	/**
	 * 5. 分页查询Order
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		logger.info("分页查询Order {}", order);
		Assert.assertNotNull(order);
		List<Order> list = orderDao.selectPageList(order);
		Assert.assertNotNull(list);
	}

	/**
	 * 6. 根据条件查询Order
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询Order {}", order);
		Assert.assertNotNull(order);
		List<Order> list = orderDao.selectList(order);
		Assert.assertNull(list);
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		Long id = order.getId();
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		orderDao.deleteById(id);
		
		Assert.assertNull(orderDao.selectById(id));
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		Long id = order.getId();
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		Order entity = orderDao.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count Order
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		logger.info("分页Count Order {}", order);
		Assert.assertNotNull(order);
		int count = orderDao.selectPageCount(order);
		Assert.assertNotEquals(0, count);
	}
	
	/**
	 * 10.查询用户订单信息
	 * @param order
	 * @return
	 */
	public void selectOrderInfo() {
		logger.info("查询用户订单信息 Order {}", order);
		Assert.assertNotNull(order);
		List<Order> orders = orderDao.selectOrderInfo(order);
		Assert.assertEquals(1l, orders.get(0).getAccountId().longValue());
	}
	
	/**
	 * 10.订单统计信息
	 * @param account
	 * @return
	 */
	public void selectOrdersSimpleReport() {
		logger.info("订单统计信息 Order {}", order);
		Assert.assertNotNull(order);
		OrderSimpleReport report = orderDao.selectOrdersSimpleReport(order.getAccountId());
		Assert.assertNotNull(report);
	}
}


