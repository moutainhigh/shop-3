package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.SystemSettingDao;
import com.tuisitang.modules.shop.entity.SystemSetting;

/**    
 * @{#} SystemSettingDaoTest.java  
 * 
 * SystemSetting Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class SystemSettingDaoTest extends BaseDaoTest<SystemSetting> {

	@Autowired
	private SystemSettingDao systemSettingDao;

	private SystemSetting systemSetting;

	@Before
	public void setUp() {
		systemSetting = getSystemsetting();
		systemSettingDao.insert(systemSetting);
		Assert.assertNotNull(systemSettingDao);
	}

	@After
	public void tearDown() {

	}
	public SystemSetting getSystemsetting() {
		SystemSetting systemSetting = new SystemSetting();
		systemSetting.setSystemCode("test");
		systemSetting.setName("test");
		systemSetting.setWww("test");
		systemSetting.setTitle("test");
		systemSetting.setDescription("test");
		systemSetting.setKeywords("test");
		systemSetting.setShortcuticon("test");
		systemSetting.setIcp("test");
		systemSetting.setIsopen("test");
		
		return systemSetting;
	}

	/**
	 * 1. 添加SystemSetting
	 */
	@Test
	@Override
	public void insert() {
		// TODO
		systemSetting = getSystemsetting();
		
		logger.info("添加SystemSetting {}", systemSetting);
		systemSettingDao.insert(systemSetting);
		Assert.assertNotNull(systemSetting.getId());
	}

	/**
	 * 2. 删除SystemSetting
	 */
	@Test
	@Override
	public void delete() {
		// TODO
		logger.info("删除SystemSetting {}", systemSetting);
		Assert.assertNotNull(systemSetting);
		Long id = systemSetting.getId();
		Assert.assertNotNull(id);
		systemSettingDao.delete(systemSetting);
		Assert.assertNull(systemSettingDao.selectById(id));
	}

	/**
	 * 3. 修改SystemSetting
	 */
	@Test
	@Override
	public void update() {
		// TODO
		logger.info("修改SystemSetting {}", systemSetting);
		Assert.assertNotNull(systemSetting);
		Long id = systemSetting.getId();
		Assert.assertNotNull(id);
		systemSetting.setSystemCode("test1");
		systemSetting.setName("test1");
		systemSetting.setWww("test1");
		systemSetting.setTitle("test1");
		systemSetting.setDescription("test1");
		systemSetting.setKeywords("test1");
		systemSetting.setShortcuticon("test1");
		systemSetting.setIcp("test1");
		systemSetting.setIsopen("test1");
		systemSettingDao.update(systemSetting);
		
		Assert.assertEquals("test1",systemSetting.getSystemCode());
		Assert.assertEquals("test1",systemSetting.getName());
		Assert.assertEquals("test1",systemSetting.getWww());
		Assert.assertEquals("test1",systemSetting.getTitle());
		Assert.assertEquals("test1",systemSetting.getDescription());
		Assert.assertEquals("test1",systemSetting.getKeywords());
		Assert.assertEquals("test1",systemSetting.getShortcuticon());
		Assert.assertEquals("test1",systemSetting.getIcp());
		Assert.assertEquals("test1",systemSetting.getIsopen());
	}

	/**
	 * 4. 根据SystemSetting查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		// TODO
		logger.info("根据SystemSetting查询一条记录 {}", systemSetting);
		Assert.assertNotNull(systemSetting);
		Assert.assertNotNull(systemSettingDao.selectOne(systemSetting));
	}

	/**
	 * 5. 分页查询SystemSetting
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		logger.info("分页查询SystemSetting {}", systemSetting);
		Assert.assertNotNull(systemSetting);
		List<SystemSetting> list = systemSettingDao.selectPageList(systemSetting);
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}

	/**
	 * 6. 根据条件查询SystemSetting
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询SystemSetting {}", systemSetting);
		Assert.assertNotNull(systemSetting);
		List<SystemSetting> list = systemSettingDao.selectList(systemSetting);
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		Long id = systemSetting.getId();
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		systemSettingDao.deleteById(id);
		Assert.assertNull(systemSettingDao.selectById(id));
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		// TODO
		Long id = systemSetting.getId();
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		SystemSetting entity = systemSettingDao.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count SystemSetting
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		logger.info("分页Count SystemSetting {}", systemSetting);
		Assert.assertNotNull(systemSetting);
		int count = systemSettingDao.selectPageCount(systemSetting);
		Assert.assertEquals(1, count);
	}

}


