package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.PayDao;
import com.tuisitang.modules.shop.entity.Pay;

/**    
 * @{#} PayDaoTest.java  
 * 
 * Pay Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class PayDaoTest extends BaseDaoTest<Pay> {

	@Autowired
	private PayDao payDao;

	private Pay pay;

	@Before
	public void setUp() {
		pay = getPay();
		payDao.insert(pay);

		Assert.assertNotNull(payDao);
	}

	@After
	public void tearDown() {

	}
	
	public Pay getPay() {
		Pay pay = new Pay();
		pay.setName("test");
		pay.setCode("test");
		pay.setSeller("test");
		pay.setSort(1);
		pay.setStatus("y");
		pay.setPartner("test");
		pay.setKeyword("test");
		
		return pay;
	}
	
	/**
	 * 1. 添加Pay
	 */
	@Test
	@Override
	public void insert() {
		// TODO
		pay = getPay();
		logger.info("添加Pay {}", pay);
		payDao.insert(pay);
		Assert.assertNotNull(pay.getId());
	}

	/**
	 * 2. 删除Pay
	 */
	@Test
	@Override
	public void delete() {
		// TODO
		logger.info("删除Pay {}", pay);
		Assert.assertNotNull(pay);
		Long id = pay.getId();
		Assert.assertNotNull(id);
		payDao.delete(pay);
		Assert.assertNull(payDao.selectById(id));
	}

	/**
	 * 3. 修改Pay
	 */
	@Test
	@Override
	public void update() {
		// TODO
		logger.info("修改Pay {}", pay);
		Assert.assertNotNull(pay);
		Long id = pay.getId();
		Assert.assertNotNull(id);
		
		pay.setName("test1");
		pay.setCode("test1");
		pay.setSeller("test1");
		pay.setSort(2);
		pay.setStatus("y");
		pay.setPartner("test1");
		pay.setKeyword("test1");
		payDao.update(pay);
		Assert.assertEquals("test1",pay.getName());
		Assert.assertEquals("test1",pay.getCode());
		Assert.assertEquals("test1",pay.getSeller());
		Assert.assertEquals(2,pay.getSort());
		Assert.assertEquals("y",pay.getStatus());
		Assert.assertEquals("test1",pay.getPartner());
		Assert.assertEquals("test1",pay.getKeyword());
	}

	/**
	 * 4. 根据Pay查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		// TODO
		logger.info("根据Pay查询一条记录 {}", pay);
		Assert.assertNotNull(pay);
		Assert.assertNotNull(payDao.selectOne(pay));
	}

	/**
	 * 5. 分页查询Pay
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		logger.info("分页查询Pay {}", pay);
		Assert.assertNotNull(pay);
		List<Pay> list = payDao.selectPageList(pay);
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}

	/**
	 * 6. 根据条件查询Pay
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询Pay {}", pay);
		Assert.assertNotNull(pay);
		List<Pay> list = payDao.selectList(pay);
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		Long id = pay.getId();
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		payDao.deleteById(id);
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		Long id = pay.getId();
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		Pay entity = payDao.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count Pay
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		logger.info("分页Count Pay {}", pay);
		Assert.assertNotNull(pay);
		int count = payDao.selectPageCount(pay);
		Assert.assertEquals(1, count);
	}

}


