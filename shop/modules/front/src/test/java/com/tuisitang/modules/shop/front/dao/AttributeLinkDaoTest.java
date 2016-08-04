package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.AttributeLinkDao;
import com.tuisitang.modules.shop.entity.AttributeLink;

/**    
 * @{#} AttributeLinkDaoTest.java  
 * 
 * AttributeLink Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class AttributeLinkDaoTest extends BaseDaoTest<AttributeLink> {

	@Autowired
	private AttributeLinkDao attributeLinkDao;

	private AttributeLink attributeLink;

	@Before
	public void setUp() {
		attributeLink = new AttributeLink();

		Assert.assertNotNull(attributeLinkDao);
	}

	@After
	public void tearDown() {

	}

	/**
	 * 1. 添加AttributeLink
	 */
	@Test
	@Override
	public void insert() {
		attributeLink = new AttributeLink();
		// TODO
		logger.info("添加AttributeLink {}", attributeLink);
		attributeLinkDao.insert(attributeLink);
		Assert.assertNotNull(attributeLink.getId());
	}

	/**
	 * 2. 删除AttributeLink
	 */
	@Test
	@Override
	public void delete() {
		attributeLink = new AttributeLink();
		attributeLinkDao.insert(attributeLink);
		// TODO
		logger.info("删除AttributeLink {}", attributeLink);
		Assert.assertNotNull(attributeLink);
		Long id = attributeLink.getId();
		Assert.assertNotNull(id);
		attributeLinkDao.delete(attributeLink);
		Assert.assertNull(attributeLinkDao.selectById(id));
	}

	/**
	 * 3. 修改AttributeLink
	 */
	@Test
	@Override
	public void update() {
		attributeLink = new AttributeLink();
		attributeLinkDao.insert(attributeLink);
		// TODO
		logger.info("修改AttributeLink {}", attributeLink);
		Assert.assertNotNull(attributeLink);
		Long id = attributeLink.getId();
		Assert.assertNotNull(id);
		attributeLinkDao.update(attributeLink);
		Assert.assertEquals("", "");
	}

	/**
	 * 4. 根据AttributeLink查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		attributeLink = new AttributeLink();
		attributeLinkDao.insert(attributeLink);
		// TODO
		logger.info("根据AttributeLink查询一条记录 {}", attributeLink);
		Assert.assertNotNull(attributeLink);
		Assert.assertNotNull(attributeLinkDao.selectOne(attributeLink));
	}

	/**
	 * 5. 分页查询AttributeLink
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		logger.info("分页查询AttributeLink {}", attributeLink);
		Assert.assertNotNull(attributeLink);
		List<AttributeLink> list = attributeLinkDao.selectPageList(attributeLink);
		Assert.assertNotNull(list);
	}

	/**
	 * 6. 根据条件查询AttributeLink
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询AttributeLink {}", attributeLink);
		Assert.assertNotNull(attributeLink);
		List<AttributeLink> list = attributeLinkDao.selectList(attributeLink);
		Assert.assertNull(list);
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		attributeLink = new AttributeLink();
		attributeLinkDao.insert(attributeLink);
		Long id = attributeLink.getId();
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		attributeLinkDao.deleteById(id);
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		attributeLink = new AttributeLink();
		attributeLinkDao.insert(attributeLink);
		Long id = attributeLink.getId();
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		AttributeLink entity = attributeLinkDao.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count AttributeLink
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		logger.info("分页Count AttributeLink {}", attributeLink);
		Assert.assertNotNull(attributeLink);
		int count = attributeLinkDao.selectPageCount(attributeLink);
	}

}


