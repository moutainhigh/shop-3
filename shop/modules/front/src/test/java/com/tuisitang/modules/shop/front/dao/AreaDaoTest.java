package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.AreaDao;
import com.tuisitang.modules.shop.entity.Area;

/**    
 * @{#} AreaDaoTest.java  
 * 
 * Area Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class AreaDaoTest extends BaseDaoTest<Area> {

	@Autowired
	private AreaDao areaDao;

	private Area area;

	@Before
	public void setUp() {
		area = new Area();

		Assert.assertNotNull(areaDao);
	}

	@After
	public void tearDown() {

	}

	/**
	 * 1. 添加Area
	 */
	@Test
	@Override
	public void insert() {
		area = new Area();
		area.setCode("100");
		area.setName("test");
		areaDao.insert(area);
		// TODO
		logger.info("添加Area {}", area);
		areaDao.insert(area);
		Assert.assertNotNull(area.getId());
	}

	/**
	 * 2. 删除Area
	 */
	@Test
	@Override
	public void delete() {
		area = new Area();
		area.setCode("100");
		area.setName("test");
		areaDao.insert(area);
		// TODO
		logger.info("删除Area {}", area);
		Assert.assertNotNull(area);
		Long id = area.getId();
		Assert.assertNotNull(id);
		areaDao.delete(area);
		Assert.assertNull(areaDao.selectById(id));
	}

	/**
	 * 3. 修改Area
	 */
	@Test
	@Override
	public void update() {
		area = new Area();
		area.setCode("100");
		area.setName("test");
		areaDao.insert(area);
		// TODO
		logger.info("修改Area {}", area);
		Assert.assertNotNull(area);
		Long id = area.getId();
		Assert.assertNotNull(id);
		area.setName("test2");
		areaDao.update(area);
		Assert.assertEquals("test2", areaDao.selectById(id).getName());
	}

	/**
	 * 4. 根据Area查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		area = new Area();
		area.setCode("100");
		area.setName("test");
		areaDao.insert(area);
		// TODO
		logger.info("根据Area查询一条记录 {}", area);
		Assert.assertNotNull(area);
		Assert.assertNotNull(areaDao.selectOne(area));
	}

	/**
	 * 5. 分页查询Area
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		logger.info("分页查询Area {}", area);
		Assert.assertNotNull(area);
		List<Area> list = areaDao.selectPageList(area);
		Assert.assertNotNull(list);
	}

	/**
	 * 6. 根据条件查询Area
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询Area {}", area);
		Assert.assertNotNull(area);
		List<Area> list = areaDao.selectList(area);
		Assert.assertNull(list);
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		area = new Area();
		area.setCode("100");
		area.setName("test");
		areaDao.insert(area);
		Long id = area.getId();
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		areaDao.deleteById(id);
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		area = new Area();
		area.setCode("100");
		area.setName("test");
		areaDao.insert(area);
		Long id = area.getId();
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		Area entity = areaDao.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count Area
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		logger.info("分页Count Area {}", area);
		Assert.assertNotNull(area);
		int count = areaDao.selectPageCount(area);
	}

}


