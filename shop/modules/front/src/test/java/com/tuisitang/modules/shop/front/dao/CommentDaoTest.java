package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.CommentDao;
import com.tuisitang.modules.shop.entity.Comment;

/**    
 * @{#} CommentDaoTest.java  
 * 
 * Comment Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class CommentDaoTest extends BaseDaoTest<Comment> {

	@Autowired
	private CommentDao commentDao;

	private Comment comment;

	@Before
	public void setUp() {
		comment = new Comment();

		Assert.assertNotNull(commentDao);
	}

	@After
	public void tearDown() {

	}

	/**
	 * 1. 添加Comment
	 */
	@Test
	@Override
	public void insert() {
		comment = new Comment();
		comment.setContent("test");
		// TODO
		logger.info("添加Comment {}", comment);
		commentDao.insert(comment);
		Assert.assertNotNull(comment.getId());
	}

	/**
	 * 2. 删除Comment
	 */
	@Test
	@Override
	public void delete() {
		comment = new Comment();
		comment.setContent("test");
		commentDao.insert(comment);
		// TODO
		logger.info("删除Comment {}", comment);
		Assert.assertNotNull(comment);
		Long id = comment.getId();
		Assert.assertNotNull(id);
		commentDao.delete(comment);
		Assert.assertNull(commentDao.selectById(id));
	}

	/**
	 * 3. 修改Comment
	 */
	@Test
	@Override
	public void update() {
		comment = new Comment();
		comment.setContent("test");
		commentDao.insert(comment);
		// TODO
		logger.info("修改Comment {}", comment);
		Assert.assertNotNull(comment);
		Long id = comment.getId();
		Assert.assertNotNull(id);
		comment.setContent("test2");
		commentDao.update(comment);
		Assert.assertEquals("test2", commentDao.selectById(id).getContent());
	}

	/**
	 * 4. 根据Comment查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		comment = new Comment();
		comment.setContent("test");
		commentDao.insert(comment);
		// TODO
		logger.info("根据Comment查询一条记录 {}", comment);
		Assert.assertNotNull(comment);
		Assert.assertNotNull(commentDao.selectOne(comment));
	}

	/**
	 * 5. 分页查询Comment
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		logger.info("分页查询Comment {}", comment);
		Assert.assertNotNull(comment);
		List<Comment> list = commentDao.selectPageList(comment);
		Assert.assertNotNull(list);
	}

	/**
	 * 6. 根据条件查询Comment
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询Comment {}", comment);
		Assert.assertNotNull(comment);
		List<Comment> list = commentDao.selectList(comment);
		Assert.assertNull(list);
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		comment = new Comment();
		comment.setContent("test");
		commentDao.insert(comment);
		Long id = comment.getId();
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		commentDao.deleteById(id);
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		comment = new Comment();
		comment.setContent("test");
		commentDao.insert(comment);
		Long id = comment.getId();
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		Comment entity = commentDao.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count Comment
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		logger.info("分页Count Comment {}", comment);
		Assert.assertNotNull(comment);
		int count = commentDao.selectPageCount(comment);
	}
	
	/**
	 * 10. 根据orderid统计
	 */
	@Test
	public void countByOrderid() {
		Long orderid = 1L;
		commentDao.countByOrderid(orderid);
	}

}


