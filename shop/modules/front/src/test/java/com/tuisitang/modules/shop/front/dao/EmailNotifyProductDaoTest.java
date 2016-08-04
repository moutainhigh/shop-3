package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.EmailNotifyProductDao;
import com.tuisitang.modules.shop.entity.EmailNotifyProduct;

/**    
 * @{#} EmailNotifyProductDaoTest.java  
 * 
 * EmailNotifyProduct Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class EmailNotifyProductDaoTest extends BaseDaoTest<EmailNotifyProduct> {

	@Autowired
	private EmailNotifyProductDao emailNotifyProductDao;

	private EmailNotifyProduct emailNotifyProduct;

	@Before
	public void setUp() {
		emailNotifyProduct = new EmailNotifyProduct();

		Assert.assertNotNull(emailNotifyProductDao);
	}

	@After
	public void tearDown() {

	}

	/**
	 * 1. 添加EmailNotifyProduct
	 */
	@Test
	@Override
	public void insert() {
		emailNotifyProduct = new EmailNotifyProduct();
		emailNotifyProduct.setAccountId(32L);
		emailNotifyProduct.setReceiveEmail("32@qq.com");
		emailNotifyProduct.setProductID(1L);
		emailNotifyProduct.setProductName("a");
		// TODO
		logger.info("添加EmailNotifyProduct {}", emailNotifyProduct);
		emailNotifyProductDao.insert(emailNotifyProduct);
		Assert.assertNotNull(emailNotifyProduct.getId());
	}

	/**
	 * 2. 删除EmailNotifyProduct
	 */
	@Test
	@Override
	public void delete() {
		emailNotifyProduct = new EmailNotifyProduct();
		emailNotifyProduct.setAccountId(32L);
		emailNotifyProduct.setReceiveEmail("32@qq.com");
		emailNotifyProduct.setProductID(1L);
		emailNotifyProduct.setProductName("a");
		emailNotifyProductDao.insert(emailNotifyProduct);
		// TODO
		logger.info("删除EmailNotifyProduct {}", emailNotifyProduct);
		Assert.assertNotNull(emailNotifyProduct);
		Long id = emailNotifyProduct.getId();
		Assert.assertNotNull(id);
		emailNotifyProductDao.delete(emailNotifyProduct);
		Assert.assertNull(emailNotifyProductDao.selectById(id));
	}

	/**
	 * 3. 修改EmailNotifyProduct
	 */
	@Test
	@Override
	public void update() {
		emailNotifyProduct = new EmailNotifyProduct();
		emailNotifyProduct.setAccountId(32L);
		emailNotifyProduct.setReceiveEmail("32@qq.com");
		emailNotifyProduct.setProductID(1L);
		emailNotifyProduct.setProductName("a");
		emailNotifyProductDao.insert(emailNotifyProduct);
		// TODO
		logger.info("修改EmailNotifyProduct {}", emailNotifyProduct);
		Assert.assertNotNull(emailNotifyProduct);
		Long id = emailNotifyProduct.getId();
		Assert.assertNotNull(id);
		emailNotifyProduct.setProductName("b");
		emailNotifyProductDao.update(emailNotifyProduct);
		Assert.assertEquals("b", emailNotifyProductDao.selectById(id).getProductName());
	}

	/**
	 * 4. 根据EmailNotifyProduct查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		emailNotifyProduct = new EmailNotifyProduct();
		emailNotifyProduct.setAccountId(32L);
		emailNotifyProduct.setReceiveEmail("32@qq.com");
		emailNotifyProduct.setProductID(1L);
		emailNotifyProduct.setProductName("a");
		emailNotifyProductDao.insert(emailNotifyProduct);
		// TODO
		logger.info("根据EmailNotifyProduct查询一条记录 {}", emailNotifyProduct);
		Assert.assertNotNull(emailNotifyProduct);
		Assert.assertNotNull(emailNotifyProductDao.selectOne(emailNotifyProduct));
	}

	/**
	 * 5. 分页查询EmailNotifyProduct
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		logger.info("分页查询EmailNotifyProduct {}", emailNotifyProduct);
		Assert.assertNotNull(emailNotifyProduct);
		List<EmailNotifyProduct> list = emailNotifyProductDao.selectPageList(emailNotifyProduct);
		Assert.assertNotNull(list);
	}

	/**
	 * 6. 根据条件查询EmailNotifyProduct
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询EmailNotifyProduct {}", emailNotifyProduct);
		Assert.assertNotNull(emailNotifyProduct);
		List<EmailNotifyProduct> list = emailNotifyProductDao.selectList(emailNotifyProduct);
		Assert.assertNull(list);
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		emailNotifyProduct = new EmailNotifyProduct();
		emailNotifyProduct.setAccountId(32L);
		emailNotifyProduct.setReceiveEmail("32@qq.com");
		emailNotifyProduct.setProductID(1L);
		emailNotifyProduct.setProductName("a");
		emailNotifyProductDao.insert(emailNotifyProduct);
		Long id = emailNotifyProduct.getId();
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		emailNotifyProductDao.deleteById(id);
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		emailNotifyProduct = new EmailNotifyProduct();
		emailNotifyProduct.setAccountId(32L);
		emailNotifyProduct.setReceiveEmail("32@qq.com");
		emailNotifyProduct.setProductID(1L);
		emailNotifyProduct.setProductName("a");
		emailNotifyProductDao.insert(emailNotifyProduct);
		Long id = emailNotifyProduct.getId();
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		EmailNotifyProduct entity = emailNotifyProductDao.selectById(id);
		logger.info("EmailNotifyProduct {}", emailNotifyProduct);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count EmailNotifyProduct
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		logger.info("分页Count EmailNotifyProduct {}", emailNotifyProduct);
		Assert.assertNotNull(emailNotifyProduct);
		int count = emailNotifyProductDao.selectPageCount(emailNotifyProduct);
	}
	
	/**
	 * 10. selectCount
	 */
	@Test
	public void selectCount() {
		Long accountId = 32L;
		Long productID = 2L;
		emailNotifyProductDao.selectCount(accountId , productID);
	}

}


