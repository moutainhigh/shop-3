package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.NotifyTemplateDao;
import com.tuisitang.modules.shop.entity.NotifyTemplate;

/**    
 * @{#} NotifyTemplateDaoTest.java  
 * 
 * NotifyTemplate Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class NotifyTemplateDaoTest extends BaseDaoTest<NotifyTemplate> {

	@Autowired
	private NotifyTemplateDao notifyTemplateDao;

	private NotifyTemplate notifyTemplate;

	@Before
	public void setUp() {
		notifyTemplate = new NotifyTemplate();

		Assert.assertNotNull(notifyTemplateDao);
	}

	@After
	public void tearDown() {

	}

	/**
	 * 1. 添加NotifyTemplate
	 */
	@Test
	@Override
	public void insert() {
		notifyTemplate = new NotifyTemplate();
		notifyTemplate.setType(NotifyTemplate.TYPE_SMS);
		notifyTemplate.setCode(NotifyTemplate.email_reg);
		notifyTemplate.setName("test");
		notifyTemplate.setTemplate("hehe");
		// TODO
		logger.info("添加NotifyTemplate {}", notifyTemplate);
		notifyTemplateDao.insert(notifyTemplate);
		Assert.assertNotNull(notifyTemplate.getId());
	}

	/**
	 * 2. 删除NotifyTemplate
	 */
	@Test
	@Override
	public void delete() {
		notifyTemplate = new NotifyTemplate();
		notifyTemplate.setType(NotifyTemplate.TYPE_SMS);
		notifyTemplate.setCode(NotifyTemplate.email_reg);
		notifyTemplate.setName("test");
		notifyTemplate.setTemplate("hehe");
		notifyTemplateDao.insert(notifyTemplate);
		// TODO
		logger.info("删除NotifyTemplate {}", notifyTemplate);
		Assert.assertNotNull(notifyTemplate);
		Long id = notifyTemplate.getId();
		Assert.assertNotNull(id);
		notifyTemplateDao.delete(notifyTemplate);
		Assert.assertNull(notifyTemplateDao.selectById(id));
	}

	/**
	 * 3. 修改NotifyTemplate
	 */
	@Test
	@Override
	public void update() {
		notifyTemplate = new NotifyTemplate();
		notifyTemplate.setType(NotifyTemplate.TYPE_SMS);
		notifyTemplate.setCode(NotifyTemplate.email_reg);
		notifyTemplate.setName("test");
		notifyTemplate.setTemplate("hehe");
		notifyTemplateDao.insert(notifyTemplate);
		// TODO
		logger.info("修改NotifyTemplate {}", notifyTemplate);
		Assert.assertNotNull(notifyTemplate);
		Long id = notifyTemplate.getId();
		Assert.assertNotNull(id);
		notifyTemplateDao.update(notifyTemplate);
		Assert.assertEquals("", "");
	}

	/**
	 * 4. 根据NotifyTemplate查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		notifyTemplate = new NotifyTemplate();
		notifyTemplate.setType(NotifyTemplate.TYPE_SMS);
		notifyTemplate.setCode(NotifyTemplate.email_reg);
		notifyTemplate.setName("test");
		notifyTemplate.setTemplate("hehe");
		notifyTemplateDao.insert(notifyTemplate);
		// TODO
		logger.info("根据NotifyTemplate查询一条记录 {}", notifyTemplate);
		Assert.assertNotNull(notifyTemplate);
		Assert.assertNotNull(notifyTemplateDao.selectOne(notifyTemplate));
	}

	/**
	 * 5. 分页查询NotifyTemplate
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		logger.info("分页查询NotifyTemplate {}", notifyTemplate);
		Assert.assertNotNull(notifyTemplate);
		List<NotifyTemplate> list = notifyTemplateDao.selectPageList(notifyTemplate);
		Assert.assertNotNull(list);
	}

	/**
	 * 6. 根据条件查询NotifyTemplate
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询NotifyTemplate {}", notifyTemplate);
		Assert.assertNotNull(notifyTemplate);
		List<NotifyTemplate> list = notifyTemplateDao.selectList(notifyTemplate);
		Assert.assertNull(list);
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		notifyTemplate = new NotifyTemplate();
		notifyTemplate.setType(NotifyTemplate.TYPE_SMS);
		notifyTemplate.setCode(NotifyTemplate.email_reg);
		notifyTemplate.setName("test");
		notifyTemplate.setTemplate("hehe");
		notifyTemplateDao.insert(notifyTemplate);
		Long id = notifyTemplate.getId();
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		notifyTemplateDao.deleteById(id);
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		notifyTemplate = new NotifyTemplate();
		notifyTemplate.setType(NotifyTemplate.TYPE_SMS);
		notifyTemplate.setCode(NotifyTemplate.email_reg);
		notifyTemplate.setName("test");
		notifyTemplate.setTemplate("hehe");
		notifyTemplateDao.insert(notifyTemplate);
		Long id = notifyTemplate.getId();
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		NotifyTemplate entity = notifyTemplateDao.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count NotifyTemplate
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		logger.info("分页Count NotifyTemplate {}", notifyTemplate);
		Assert.assertNotNull(notifyTemplate);
		int count = notifyTemplateDao.selectPageCount(notifyTemplate);
	}

}


