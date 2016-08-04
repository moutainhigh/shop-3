package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.FavoriteDao;
import com.tuisitang.modules.shop.entity.Favorite;

/**    
 * @{#} FavoriteDaoTest.java  
 * 
 * Favorite Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class FavoriteDaoTest extends BaseDaoTest<Favorite> {

	@Autowired
	private FavoriteDao favoriteDao;

	private Favorite favorite;

	@Before
	public void setUp() {
		favorite = new Favorite();

		Assert.assertNotNull(favoriteDao);
	}

	@After
	public void tearDown() {

	}

	/**
	 * 1. 添加Favorite
	 */
	@Test
	@Override
	public void insert() {
		favorite = new Favorite();
		favorite.setAccountId(43L);
		favorite.setProductId(1L);
		// TODO
		logger.info("添加Favorite {}", favorite);
		favoriteDao.insert(favorite);
		Assert.assertNotNull(favorite.getId());
	}

	/**
	 * 2. 删除Favorite
	 */
	@Test
	@Override
	public void delete() {
		favorite = new Favorite();
		favorite.setAccountId(43L);
		favorite.setProductId(1L);
		favoriteDao.insert(favorite);
		// TODO
		logger.info("删除Favorite {}", favorite);
		Assert.assertNotNull(favorite);
		Long id = favorite.getId();
		Assert.assertNotNull(id);
		favoriteDao.delete(favorite);
		Assert.assertNull(favoriteDao.selectById(id));
	}

	/**
	 * 3. 修改Favorite
	 */
	@Test
	@Override
	public void update() {
		favorite = new Favorite();
		favorite.setAccountId(43L);
		favorite.setProductId(1L);
		favoriteDao.insert(favorite);
		// TODO
		logger.info("修改Favorite {}", favorite);
		Assert.assertNotNull(favorite);
		Long id = favorite.getId();
		Assert.assertNotNull(id);
		favoriteDao.update(favorite);
		Assert.assertEquals("", "");
	}

	/**
	 * 4. 根据Favorite查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		favorite = new Favorite();
		favorite.setAccountId(43L);
		favorite.setProductId(1L);
		favoriteDao.insert(favorite);
		// TODO
		logger.info("根据Favorite查询一条记录 {}", favorite);
		Assert.assertNotNull(favorite);
		Assert.assertNotNull(favoriteDao.selectOne(favorite));
	}

	/**
	 * 5. 分页查询Favorite
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		logger.info("分页查询Favorite {}", favorite);
		Assert.assertNotNull(favorite);
		List<Favorite> list = favoriteDao.selectPageList(favorite);
		Assert.assertNotNull(list);
	}

	/**
	 * 6. 根据条件查询Favorite
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询Favorite {}", favorite);
		Assert.assertNotNull(favorite);
		List<Favorite> list = favoriteDao.selectList(favorite);
		Assert.assertNull(list);
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
		favoriteDao.deleteById(id);
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		favorite = new Favorite();
		favorite.setAccountId(43L);
		favorite.setProductId(1L);
		favoriteDao.insert(favorite);
		Long id = favorite.getId();
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		Favorite entity = favoriteDao.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count Favorite
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		logger.info("分页Count Favorite {}", favorite);
		Assert.assertNotNull(favorite);
		int count = favoriteDao.selectPageCount(favorite);
	}
	
	/**
	 * 10. 根据accountId productID统计
	 */
	@Test
	public void selectCount() {
		Long productID = 1L;
		Long accountId = 43L;
		favoriteDao.selectCount(accountId, productID);
	}

}


