package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.OrdershipDao;
import com.tuisitang.modules.shop.entity.Ordership;

/**    
 * @{#} OrdershipDaoTest.java  
 * 
 * Ordership Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class OrdershipDaoTest extends BaseDaoTest<Ordership> {

	@Autowired
	private OrdershipDao ordershipDao;

	private Ordership ordership;

	@Before
	public void setUp() {
		ordership = new Ordership();

		Assert.assertNotNull(ordershipDao);
	}
	
	public Ordership getOrderShip() {
		Ordership ordership = new Ordership();
		ordership.setOrderid(1l);
		ordership.setShipname("test");
		ordership.setShipaddress("test");
		ordership.setProvinceCode("test");
		ordership.setProvince("test");
		ordership.setCityCode("test");
		ordership.setCity("test");
		ordership.setAreaCode("test");
		ordership.setArea("test");
		ordership.setPhone("test");
		ordership.setTel("test");
		ordership.setZip("test");
		ordership.setSex("1");
		ordership.setRemark("test");
		
		return ordership;
	}
	
	@After
	public void tearDown() {

	}

	/**
	 * 1. 添加Ordership
	 */
	@Test
	@Override
	public void insert() {
		Ordership ordership = getOrderShip();
		ordershipDao.insert(ordership);
		// TODO
		logger.info("添加Ordership {}", ordership);
		ordershipDao.insert(ordership);
		Assert.assertNotNull(ordership.getId());
	}

	/**
	 * 2. 删除Ordership
	 */
	@Test
	@Override
	public void delete() {
		// TODO
		Ordership ordership = getOrderShip();
		ordershipDao.insert(ordership);
		
		logger.info("删除Ordership {}", ordership);
		Assert.assertNotNull(ordership);
		Long id = ordership.getId();
		Assert.assertNotNull(id);
		ordershipDao.delete(ordership);
		Assert.assertNull(ordershipDao.selectById(id));
	}

	/**
	 * 3. 修改Ordership
	 */
	@Test
	@Override
	public void update() {
		// TODO
		Ordership ordership = getOrderShip();
		ordershipDao.insert(ordership);
		
		logger.info("修改Ordership {}", ordership);
		Assert.assertNotNull(ordership);
		Long id = ordership.getId();
		Assert.assertNotNull(id);
		
		ordership.setOrderid(2l);
		ordership.setShipname("test1");
		ordership.setShipaddress("test1");
		ordership.setProvinceCode("test1");
		ordership.setProvince("test1");
		ordership.setCityCode("test1");
		ordership.setCity("test1");
		ordership.setAreaCode("test1");
		ordership.setArea("test1");
		ordership.setPhone("test1");
		ordership.setTel("test1");
		ordership.setZip("test1");
		ordership.setSex("2");
		ordership.setRemark("test1");
		
		ordershipDao.update(ordership);
		ordership = ordershipDao.selectById(ordership.getId());
		Assert.assertNotNull(ordership);
		
		Assert.assertEquals(2l,ordership.getOrderid().longValue());
		Assert.assertEquals("test1",ordership.getShipname());
		Assert.assertEquals("test1",ordership.getShipaddress());
		Assert.assertEquals("test1",ordership.getProvinceCode());
		Assert.assertEquals("test1",ordership.getProvince());
		Assert.assertEquals("test1",ordership.getCityCode());
		Assert.assertEquals("test1",ordership.getCity());
		Assert.assertEquals("test1",ordership.getAreaCode());
		Assert.assertEquals("test1",ordership.getArea());
		Assert.assertEquals("test1",ordership.getPhone());
		Assert.assertEquals("test1",ordership.getTel());
		Assert.assertEquals("test1",ordership.getZip());
		Assert.assertEquals("2",ordership.getSex());
		Assert.assertEquals("test1",ordership.getRemark());
	}

	/**
	 * 4. 根据Ordership查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		// TODO
		ordership = getOrderShip();
		ordershipDao.insert(ordership);
		
		logger.info("根据Ordership查询一条记录 {}", ordership);
		Assert.assertNotNull(ordership);
		Assert.assertNotNull(ordershipDao.selectOne(ordership));
	}

	/**
	 * 5. 分页查询Ordership
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		Ordership ordership = getOrderShip();
		ordershipDao.insert(ordership);
		
		logger.info("分页查询Ordership {}", ordership);
		Assert.assertNotNull(ordership);
		List<Ordership> list = ordershipDao.selectPageList(ordership);
		Assert.assertNotNull(list);
	}

	/**
	 * 6. 根据条件查询Ordership
	 */
	@Override
	public void selectList() {
		// TODO
		Ordership ordership = getOrderShip();
		ordershipDao.insert(ordership);
		
		logger.info("根据条件查询Ordership {}", ordership);
		Assert.assertNotNull(ordership);
		List<Ordership> list = ordershipDao.selectList(ordership);
		Assert.assertNull(list);
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		Ordership ordership = getOrderShip();
		ordershipDao.insert(ordership);
		
		Long id = ordership.getId();
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		ordershipDao.deleteById(id);
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		Ordership ordership = getOrderShip();
		ordershipDao.insert(ordership);
		
		Long id = ordership.getId();
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		Ordership entity = ordershipDao.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count Ordership
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		Ordership ordership = getOrderShip();
		ordershipDao.insert(ordership);
		
		logger.info("分页Count Ordership {}", ordership);
		Assert.assertNotNull(ordership);
		int count = ordershipDao.selectPageCount(ordership);
		Assert.assertEquals(1, count);
	}

}


