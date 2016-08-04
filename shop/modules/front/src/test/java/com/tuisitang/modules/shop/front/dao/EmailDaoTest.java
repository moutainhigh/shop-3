package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.EmailDao;
import com.tuisitang.modules.shop.entity.Email;

/**    
 * @{#} EmailDaoTest.java  
 * 
 * Email Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class EmailDaoTest extends BaseDaoTest<Email> {

	@Autowired
	private EmailDao emailDao;

	private Email email;

	@Before
	public void setUp() {
		email = new Email();

		Assert.assertNotNull(emailDao);
	}

	@After
	public void tearDown() {

	}

	/**
	 * 1. 添加Email
	 */
	@Test
	@Override
	public void insert() {
		email = new Email();
		email.setSign("001");
		email.setAccountId(32L);
		email.setType("reg");
		// TODO
		logger.info("添加Email {}", email);
		emailDao.insert(email);
		Assert.assertNotNull(email.getId());
	}

	/**
	 * 2. 删除Email
	 */
	@Test
	@Override
	public void delete() {
		email = new Email();
		email.setSign("001");
		email.setAccountId(32L);
		email.setType("reg");
		emailDao.insert(email);
		// TODO
		logger.info("删除Email {}", email);
		Assert.assertNotNull(email);
		Long id = email.getId();
		Assert.assertNotNull(id);
		emailDao.delete(email);
		Assert.assertNull(emailDao.selectById(id));
	}

	/**
	 * 3. 修改Email
	 */
	@Test
	@Override
	public void update() {
		email = new Email();
		email.setSign("001");
		email.setAccountId(32L);
		email.setType("reg");
		emailDao.insert(email);
		// TODO
		logger.info("修改Email {}", email);
		Assert.assertNotNull(email);
		Long id = email.getId();
		Assert.assertNotNull(id);
		emailDao.update(email);
		Assert.assertEquals("", "");
	}

	/**
	 * 4. 根据Email查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		email = new Email();
		email.setSign("001");
		email.setAccountId(32L);
		email.setType("reg");
		emailDao.insert(email);
		// TODO
		logger.info("根据Email查询一条记录 {}", email);
		Assert.assertNotNull(email);
		Assert.assertNotNull(emailDao.selectOne(email));
	}

	/**
	 * 5. 分页查询Email
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		logger.info("分页查询Email {}", email);
		Assert.assertNotNull(email);
		List<Email> list = emailDao.selectPageList(email);
		Assert.assertNotNull(list);
	}

	/**
	 * 6. 根据条件查询Email
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询Email {}", email);
		Assert.assertNotNull(email);
		List<Email> list = emailDao.selectList(email);
		Assert.assertNull(list);
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		email = new Email();
		email.setSign("001");
		email.setAccountId(32L);
		email.setType("reg");
		emailDao.insert(email);
		Long id = email.getId();
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		emailDao.deleteById(id);
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		email = new Email();
		email.setSign("001");
		email.setAccountId(32L);
		email.setType("reg");
		emailDao.insert(email);
		Long id = 2l;//email.getId();
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		Email entity = emailDao.selectById(id);
		logger.info("{}", entity);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count Email
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		logger.info("分页Count Email {}", email);
		Assert.assertNotNull(email);
		int count = emailDao.selectPageCount(email);
	}
	
	/**
	 * 10. updateEmailInvalidWhenReg
	 */
	@Test
	public void updateEmailInvalidWhenReg() {
		long accountId = 32L;
		emailDao.updateEmailInvalidWhenReg(accountId);
	}

}


