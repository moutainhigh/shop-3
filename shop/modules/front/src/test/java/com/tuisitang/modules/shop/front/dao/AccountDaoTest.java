package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.AccountDao;
import com.tuisitang.modules.shop.entity.Account;

/**    
 * @{#} AccountDaoTest.java  
 * 
 * 账号Dao测试
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 *  
 */
public class AccountDaoTest extends BaseDaoTest<Account> {

	@Autowired
	private AccountDao accountDao;

	private Account account;

	@Before
	public void setUp() {
		account = new Account();

		Assert.assertNotNull(accountDao);
	}

	@After
	public void tearDown() {

	}

	/**
	 * 1. 添加Account
	 */
	@Test
	@Override
	public void insert() {
		account = new Account();
		account.setMobile("18088888888");
		account.setAccount("18088888888");
		// TODO
		logger.info("添加Account {}", account);
		accountDao.insert(account);
		Assert.assertNotNull(account.getId());
	}

	/**
	 * 2. 删除Account
	 */
	@Test
	@Override
	public void delete() {
		account = new Account();
		account.setMobile("18088888888");
		account.setAccount("18088888888");
		accountDao.insert(account);
		// TODO
		logger.info("删除Account {}", account);
		Assert.assertNotNull(account);
		Long id = account.getId();
		Assert.assertNotNull(id);
		accountDao.delete(account);
		Assert.assertNull(accountDao.selectById(id));
	}

	/**
	 * 3. 修改Account
	 */
	@Test
	@Override
	public void update() {
		account = new Account();
		account.setMobile("18088888888");
		account.setAccount("18088888888");
		accountDao.insert(account);
		// TODO
		logger.info("修改Account {}", account);
		Assert.assertNotNull(account);
		Long id = account.getId();
		Assert.assertNotNull(id);
		account.setNickname("Tom");
		accountDao.update(account);
		Assert.assertEquals("Tom", accountDao.selectById(id).getNickname());
	}

	/**
	 * 4. 根据Account查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		// TODO
		account = new Account();
		account.setId(43L);
		logger.info("根据Account查询一条记录 {}", account);
		Assert.assertNotNull(account);
		Assert.assertNotNull(accountDao.selectOne(account));
	}

	/**
	 * 5. 分页查询Account
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		logger.info("分页查询Account {}", account);
		Assert.assertNotNull(account);
		List<Account> list = accountDao.selectPageList(account);
		Assert.assertNotNull(list);
	}

	/**
	 * 6. 根据条件查询Account
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询Account {}", account);
		Assert.assertNotNull(account);
		List<Account> list = accountDao.selectList(account);
		Assert.assertNotNull(list);
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		Long id = null;
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		accountDao.deleteById(id);
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		Long id = null;
		account = new Account();
		account.setMobile("18088888888");
		account.setAccount("18088888888");
		accountDao.insert(account);
		id = account.getId();
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		Account entity = accountDao.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count Account
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		logger.info("分页Count Account {}", account);
		Assert.assertNotNull(account);
		int count = accountDao.selectPageCount(account);
	}
	
	/**
	 * 10. 根据条件统计会员账号数量
	 */
	@Test
	public void selectCount() {
		logger.info("根据条件统计会员账号数量 {}", account);
		Assert.assertNotNull(account);
		int count = accountDao.selectCount(account);
	}

	/**
	 * 11. 根据id更新会员密码
	 */
	@Test
	public void updatePasswordById() {
		logger.info(" 根据id更新会员密码 {}", account);
		account = accountDao.selectById(43L);
		Assert.assertNotNull(account);
		accountDao.updatePasswordById(account.getId(), "123456");
	}

	/**
	 * 12. 根据id更新会员Email
	 */
	@Test
	public void updateEmailById() {
		logger.info(" 根据id更新会员密码 {}", account);
		account = accountDao.selectById(43L);
		Assert.assertNotNull(account);
		accountDao.updateEmailById(account.getId(), "123456@qq.com");
	}

	/**
	 * 13. 根据会员id更新会员积分，如果rank变化了，同时更新rank
	 */
	@Test
	public void updateScoreById() {
		logger.info(" 根据id更新会员密码 {}", account);
		account = accountDao.selectById(43L);
		Assert.assertNotNull(account);
		accountDao.updateScoreById(account.getId(), 50, null);
	}

}
