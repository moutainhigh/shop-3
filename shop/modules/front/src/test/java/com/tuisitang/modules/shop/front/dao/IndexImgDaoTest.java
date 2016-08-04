package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.IndexImgDao;
import com.tuisitang.modules.shop.entity.IndexImg;

/**    
 * @{#} IndexImgDaoTest.java  
 * 
 * IndexImg Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class IndexImgDaoTest extends BaseDaoTest<IndexImg> {

	@Autowired
	private IndexImgDao indexImgDao;

	private IndexImg indexImg;

	@Before
	public void setUp() {
		indexImg = new IndexImg();

		Assert.assertNotNull(indexImgDao);
	}

	@After
	public void tearDown() {

	}

	/**
	 * 1. 添加IndexImg
	 */
	@Test
	@Override
	public void insert() {
		indexImg = new IndexImg();
		indexImg.setTitle("hi");
		indexImg.setPicture("p");
		indexImg.setSort(10);
		// TODO
		logger.info("添加IndexImg {}", indexImg);
		indexImgDao.insert(indexImg);
		Assert.assertNotNull(indexImg.getId());
	}

	/**
	 * 2. 删除IndexImg
	 */
	@Test
	@Override
	public void delete() {
		indexImg = new IndexImg();
		indexImg.setTitle("hi");
		indexImg.setPicture("p");
		indexImg.setSort(10);
		indexImgDao.insert(indexImg);
		// TODO
		logger.info("删除IndexImg {}", indexImg);
		Assert.assertNotNull(indexImg);
		Long id = indexImg.getId();
		Assert.assertNotNull(id);
		indexImgDao.delete(indexImg);
		Assert.assertNull(indexImgDao.selectById(id));
	}

	/**
	 * 3. 修改IndexImg
	 */
	@Test
	@Override
	public void update() {
		indexImg = new IndexImg();
		indexImg.setTitle("hi");
		indexImg.setPicture("p");
		indexImg.setSort(10);
		indexImgDao.insert(indexImg);
		// TODO
		logger.info("修改IndexImg {}", indexImg);
		Assert.assertNotNull(indexImg);
		Long id = indexImg.getId();
		Assert.assertNotNull(id);
		indexImgDao.update(indexImg);
		Assert.assertEquals("", "");
	}

	/**
	 * 4. 根据IndexImg查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		indexImg = new IndexImg();
		indexImg.setTitle("hi");
		indexImg.setPicture("p");
		indexImg.setSort(10);
		indexImgDao.insert(indexImg);
		// TODO
		logger.info("根据IndexImg查询一条记录 {}", indexImg);
		Assert.assertNotNull(indexImg);
		Assert.assertNotNull(indexImgDao.selectOne(indexImg));
	}

	/**
	 * 5. 分页查询IndexImg
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		logger.info("分页查询IndexImg {}", indexImg);
		Assert.assertNotNull(indexImg);
		List<IndexImg> list = indexImgDao.selectPageList(indexImg);
		Assert.assertNotNull(list);
	}

	/**
	 * 6. 根据条件查询IndexImg
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询IndexImg {}", indexImg);
		Assert.assertNotNull(indexImg);
		List<IndexImg> list = indexImgDao.selectList(indexImg);
		Assert.assertNull(list);
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		indexImg = new IndexImg();
		indexImg.setTitle("hi");
		indexImg.setPicture("p");
		indexImg.setSort(10);
		indexImgDao.insert(indexImg);
		Long id = indexImg.getId();
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		indexImgDao.deleteById(id);
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		indexImg = new IndexImg();
		indexImg.setTitle("hi");
		indexImg.setPicture("p");
		indexImg.setSort(10);
		indexImgDao.insert(indexImg);
		Long id = indexImg.getId();
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		IndexImg entity = indexImgDao.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count IndexImg
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		logger.info("分页Count IndexImg {}", indexImg);
		Assert.assertNotNull(indexImg);
		int count = indexImgDao.selectPageCount(indexImg);
	}

}


