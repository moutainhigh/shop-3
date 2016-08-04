package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.ExpressDao;
import com.tuisitang.modules.shop.entity.Express;

/**    
 * @{#} ExpressDaoTest.java  
 * 
 * Express Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class ExpressDaoTest extends BaseDaoTest<Express> {

	@Autowired
	private ExpressDao expressDao;

	private Express express;

	@Before
	public void setUp() {
		express = new Express();
		express.setId(1L);
		Assert.assertNotNull(expressDao);
	}

	@After
	public void tearDown() {
		
	}

	/**
	 * 1. 添加Express
	 */
	@Test
	@Override
	public void insert() {
		// TODO
		express = new Express();
		express.setCode("test");
		express.setName("test");
		express.setFee(10.00);
		logger.info("添加Express {}", express);
		expressDao.insert(express);
		Assert.assertNotNull(express.getId());
	}

	/**
	 * 2. 删除Express
	 */
	@Test
	@Override
	public void delete() {
		express = new Express();
		express.setCode("test");
		express.setName("test");
		express.setFee(10.00);
		expressDao.insert(express);
		// TODO
		logger.info("删除Express {}", express);
		Assert.assertNotNull(express);
		Long id = express.getId();
		Assert.assertNotNull(id);
		expressDao.delete(express);
		Assert.assertNull(expressDao.selectById(id));
	}

	/**
	 * 3. 修改Express
	 */
	@Test
	@Override
	public void update() {
		express = new Express();
		express.setCode("test");
		express.setName("test");
		express.setFee(10.00);
		expressDao.insert(express);
		express.setName("test2");
		// TODO
		logger.info("修改Express {}", express);
		Assert.assertNotNull(express);
		Long id = express.getId();
		Assert.assertNotNull(id);
		expressDao.update(express);
		Assert.assertEquals("test2", expressDao.selectById(id).getName());
	}

	/**
	 * 4. 根据Express查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		// TODO
		logger.info("根据Express查询一条记录 {}", express);
		Assert.assertNotNull(express);
		Assert.assertNotNull(expressDao.selectOne(express));
	}

	/**
	 * 5. 分页查询Express
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		express.setOffset(0);
		express.setPagerSize(10);
		// TODO
		logger.info("分页查询Express {}", express);
		Assert.assertNotNull(express);
		List<Express> list = expressDao.selectPageList(express);
		Assert.assertNotNull(list);
	}

	/**
	 * 6. 根据条件查询Express
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询Express {}", express);
		Assert.assertNotNull(express);
		List<Express> list = expressDao.selectList(express);
		Assert.assertNull(list);
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		express = new Express();
		express.setCode("test");
		express.setName("test");
		express.setFee(10.00);
		expressDao.insert(express);
		Long id = express.getId();
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		expressDao.deleteById(id);
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		express = new Express();
		express.setCode("test");
		express.setName("test");
		express.setFee(10.00);
		expressDao.insert(express);
		Long id = express.getId();
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		Express entity = expressDao.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count Express
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		logger.info("分页Count Express {}", express);
		Assert.assertNotNull(express);
		int count = expressDao.selectPageCount(express);
	}

}


