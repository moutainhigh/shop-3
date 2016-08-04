package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.AttributeDao;
import com.tuisitang.modules.shop.entity.Attribute;

/**    
 * @{#} AttributeDaoTest.java  
 * 
 * Attribute Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class AttributeDaoTest extends BaseDaoTest<Attribute> {

	@Autowired
	private AttributeDao attributeDao;

	private Attribute attribute;

	@Before
	public void setUp() {
		attribute = new Attribute();

		Assert.assertNotNull(attributeDao);
	}

	@After
	public void tearDown() {

	}

	/**
	 * 1. 添加Attribute
	 */
	@Test
	@Override
	public void insert() {
		attribute = new Attribute();
		attribute.setName("test");
		attribute.setCatalogID(0L);
		// TODO
		logger.info("添加Attribute {}", attribute);
		attributeDao.insert(attribute);
		Assert.assertNotNull(attribute.getId());
	}

	/**
	 * 2. 删除Attribute
	 */
	@Test
	@Override
	public void delete() {
		attribute = new Attribute();
		attribute.setName("test");
		attribute.setCatalogID(0L);
		attributeDao.insert(attribute);
		// TODO
		logger.info("删除Attribute {}", attribute);
		Assert.assertNotNull(attribute);
		Long id = attribute.getId();
		Assert.assertNotNull(id);
		attributeDao.delete(attribute);
		Assert.assertNull(attributeDao.selectById(id));
	}

	/**
	 * 3. 修改Attribute
	 */
	@Test
	@Override
	public void update() {
		attribute = new Attribute();
		attribute.setName("test");
		attribute.setCatalogID(0L);
		attributeDao.insert(attribute);
		// TODO
		logger.info("修改Attribute {}", attribute);
		Assert.assertNotNull(attribute);
		Long id = attribute.getId();
		Assert.assertNotNull(id);
		attributeDao.update(attribute);
		Assert.assertEquals("", "");
	}

	/**
	 * 4. 根据Attribute查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		attribute = new Attribute();
		attribute.setName("test");
		attribute.setCatalogID(0L);
		attributeDao.insert(attribute);
		// TODO
		logger.info("根据Attribute查询一条记录 {}", attribute);
		Assert.assertNotNull(attribute);
		Assert.assertNotNull(attributeDao.selectOne(attribute));
	}

	/**
	 * 5. 分页查询Attribute
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		logger.info("分页查询Attribute {}", attribute);
		Assert.assertNotNull(attribute);
		List<Attribute> list = attributeDao.selectPageList(attribute);
		Assert.assertNotNull(list);
	}

	/**
	 * 6. 根据条件查询Attribute
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询Attribute {}", attribute);
		Assert.assertNotNull(attribute);
		List<Attribute> list = attributeDao.selectList(attribute);
		Assert.assertNull(list);
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		attribute = new Attribute();
		attribute.setName("test");
		attribute.setCatalogID(0L);
		attributeDao.insert(attribute);
		Long id = attribute.getId();
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		attributeDao.deleteById(id);
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		attribute = new Attribute();
		attribute.setName("test");
		attribute.setCatalogID(0L);
		attributeDao.insert(attribute);
		Long id = attribute.getId();
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		Attribute entity = attributeDao.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count Attribute
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		logger.info("分页Count Attribute {}", attribute);
		Assert.assertNotNull(attribute);
		int count = attributeDao.selectPageCount(attribute);
	}
	
	/**
	 * 10. 根据pid删除
	 */
	@Test
	public void deleteByPid() {
		attribute = new Attribute();
		attribute.setName("test");
		attribute.setCatalogID(0L);
		attribute.setPid(999L);
		attributeDao.insert(attribute);
		
		attributeDao.deleteByPid(attribute.getPid());
	}
	
	@Test
	public void selectAttributeByCatalog() {
		List<Attribute> list = attributeDao.selectAttributeByCatalog(39l);
		Assert.assertNotEquals(0, list.size());
	}
}


