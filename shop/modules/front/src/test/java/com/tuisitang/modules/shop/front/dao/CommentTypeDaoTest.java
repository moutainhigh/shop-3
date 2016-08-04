package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.CommentTypeDao;
import com.tuisitang.modules.shop.entity.CommentType;

/**    
 * @{#} CommentTypeDaoTest.java  
 * 
 * CommentType Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class CommentTypeDaoTest extends BaseDaoTest<CommentType> {

	@Autowired
	private CommentTypeDao commentTypeDao;

	private CommentType commentType;

	@Before
	public void setUp() {
		commentType = new CommentType();

		Assert.assertNotNull(commentTypeDao);
	}

	@After
	public void tearDown() {

	}

	/**
	 * 1. 添加CommentType
	 */
	@Test
	@Override
	public void insert() {
		commentType = new CommentType();
		commentType.setName("test");
		commentType.setCode("test");
		// TODO
		logger.info("添加CommentType {}", commentType);
		commentTypeDao.insert(commentType);
		Assert.assertNotNull(commentType.getId());
	}

	/**
	 * 2. 删除CommentType
	 */
	@Test
	@Override
	public void delete() {
		commentType = new CommentType();
		commentType.setName("test");
		commentType.setCode("test");
		commentTypeDao.insert(commentType);
		// TODO
		logger.info("删除CommentType {}", commentType);
		Assert.assertNotNull(commentType);
		Long id = commentType.getId();
		Assert.assertNotNull(id);
		commentTypeDao.delete(commentType);
		Assert.assertNull(commentTypeDao.selectById(id));
	}

	/**
	 * 3. 修改CommentType
	 */
	@Test
	@Override
	public void update() {
		commentType = new CommentType();
		commentType.setName("test");
		commentType.setCode("test");
		commentTypeDao.insert(commentType);
		// TODO
		logger.info("修改CommentType {}", commentType);
		Assert.assertNotNull(commentType);
		Long id = commentType.getId();
		Assert.assertNotNull(id);
		commentType.setName("test1");
		commentTypeDao.update(commentType);
		Assert.assertEquals("test1", commentTypeDao.selectById(id).getName());
	}

	/**
	 * 4. 根据CommentType查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		commentType = new CommentType();
		commentType.setName("test");
		commentType.setCode("test");
		commentTypeDao.insert(commentType);
		// TODO
		logger.info("根据CommentType查询一条记录 {}", commentType);
		Assert.assertNotNull(commentType);
		Assert.assertNotNull(commentTypeDao.selectOne(commentType));
	}

	/**
	 * 5. 分页查询CommentType
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		logger.info("分页查询CommentType {}", commentType);
		Assert.assertNotNull(commentType);
		List<CommentType> list = commentTypeDao.selectPageList(commentType);
		Assert.assertNotNull(list);
	}

	/**
	 * 6. 根据条件查询CommentType
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询CommentType {}", commentType);
		Assert.assertNotNull(commentType);
		List<CommentType> list = commentTypeDao.selectList(commentType);
		Assert.assertNull(list);
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		commentType = new CommentType();
		commentType.setName("test");
		commentType.setCode("test");
		commentTypeDao.insert(commentType);
		Long id = commentType.getId();
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		commentTypeDao.deleteById(id);
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		commentType = new CommentType();
		commentType.setName("test");
		commentType.setCode("test");
		commentTypeDao.insert(commentType);
		Long id = commentType.getId();
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		CommentType entity = commentTypeDao.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count CommentType
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		logger.info("分页Count CommentType {}", commentType);
		Assert.assertNotNull(commentType);
		int count = commentTypeDao.selectPageCount(commentType);
	}

}


