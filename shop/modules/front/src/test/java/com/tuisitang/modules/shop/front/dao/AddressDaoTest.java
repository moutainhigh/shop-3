package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.AddressDao;
import com.tuisitang.modules.shop.entity.Address;

/**    
 * @{#} AddressDaoTest.java  
 * 
 * Address Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class AddressDaoTest extends BaseDaoTest<Address> {

	@Autowired
	private AddressDao addressDao;

	private Address address;

	@Before
	public void setUp() {
		address = new Address();

		Assert.assertNotNull(addressDao);
	}

	@After
	public void tearDown() {

	}

	/**
	 * 1. 添加Address
	 */
	@Test
	@Override
	public void insert() {
		address = new Address();
		address.setAccountId(43L);
		address.setName("测试");
		address.setAddress("四川省成都市青羊区");
		address.setMobile("18089898989");
		// TODO
		logger.info("添加Address {}", address);
		addressDao.insert(address);
		Assert.assertNotNull(address.getId());
	}

	/**
	 * 2. 删除Address
	 */
	@Test
	@Override
	public void delete() {
		address = new Address();
		address.setAccountId(43L);
		address.setName("测试");
		address.setAddress("四川省成都市青羊区");
		address.setMobile("18089898989");
		addressDao.insert(address);
		// TODO
		logger.info("删除Address {}", address);
		Assert.assertNotNull(address);
		Long id = address.getId();
		Assert.assertNotNull(id);
		addressDao.delete(address);
		Assert.assertNull(addressDao.selectById(id));
	}

	/**
	 * 3. 修改Address
	 */
	@Test
	@Override
	public void update() {
		address = new Address();
		address.setAccountId(43L);
		address.setName("测试");
		address.setAddress("四川省成都市青羊区");
		address.setMobile("18089898989");
		addressDao.insert(address);
		// TODO
		logger.info("修改Address {}", address);
		Assert.assertNotNull(address);
		Long id = address.getId();
		Assert.assertNotNull(id);
		address.setPhone("65588888");
		addressDao.update(address);
		Assert.assertEquals("65588888", addressDao.selectById(id).getPhone());
	}

	/**
	 * 4. 根据Address查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		address = new Address();
		address.setAccountId(43L);
		address.setName("测试");
		address.setAddress("四川省成都市青羊区");
		address.setMobile("18089898989");
		addressDao.insert(address);
		// TODO
		logger.info("根据Address查询一条记录 {}", address);
		Assert.assertNotNull(address);
		Assert.assertNotNull(addressDao.selectOne(address));
	}

	/**
	 * 5. 分页查询Address
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		logger.info("分页查询Address {}", address);
		Assert.assertNotNull(address);
		List<Address> list = addressDao.selectPageList(address);
		Assert.assertNotNull(list);
	}

	/**
	 * 6. 根据条件查询Address
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询Address {}", address);
		Assert.assertNotNull(address);
		List<Address> list = addressDao.selectList(address);
		Assert.assertNull(list);
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		address = new Address();
		address.setAccountId(43L);
		address.setName("测试");
		address.setAddress("四川省成都市青羊区");
		address.setMobile("18089898989");
		addressDao.insert(address);
		Long id = address.getId();
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		addressDao.deleteById(id);
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		address = new Address();
		address.setAccountId(43L);
		address.setName("测试");
		address.setAddress("四川省成都市青羊区");
		address.setMobile("18089898989");
		addressDao.insert(address);
		Long id = address.getId();
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		Address entity = addressDao.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count Address
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		logger.info("分页Count Address {}", address);
		Assert.assertNotNull(address);
		int count = addressDao.selectPageCount(address);
	}

}


