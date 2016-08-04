package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.tuisitang.modules.shop.dao.ProductDao;
import com.tuisitang.modules.shop.entity.Product;
import com.tuisitang.modules.shop.entity.ProductStockInfo;

/**    
 * @{#} ProductDaoTest.java  
 * 
 * Product Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class ProductDaoTest extends BaseDaoTest<Product> {

	@Autowired
	private ProductDao productDao;

	private Product product;

	@Before
	public void setUp() {
		product = getProduct();
		productDao.insert(product);

		Assert.assertNotNull(productDao);
	}

	@After
	public void tearDown() {

	}
	
	public Product getProduct() {
		Product product = new Product();
		product.setName("test");
		product.setIntroduce("test");
		product.setPicture("test");
		product.setCreateAccount("test");
		product.setUpdateAccount("test");
		product.setIsnew("Y");
		product.setSale("Y");
		product.setHit(1);
		product.setStatus(1);
		product.setMaxPicture("test");
		product.setImages("test");
		product.setCatalogID(1l);
		product.setSellcount(1);
		product.setStock(1);
		product.setSearchKey("test");
		product.setTitle("test");
		product.setDescription("test");
		product.setKeywords("test");
		product.setActivityID(1l);
		product.setUnit("test");
		product.setProductHTML("test");
		product.setScore(1);
		product.setIsTimePromotion("Y");
//		product.setGiftID("test");
		product.setStatus(2);
		
		return product;
	}
	
	/**
	 * 1. 添加Product
	 */
	@Test
	@Override
	public void insert() {
		// TODO
		product = getProduct();
		
		logger.info("添加Product {}", product);
		productDao.insert(product);
		Assert.assertNotNull(product.getId());
	}

	/**
	 * 2. 删除Product
	 */
	@Test
	@Override
	public void delete() {
		// TODO
		logger.info("删除Product {}", product);
		Assert.assertNotNull(product);
		Long id = product.getId();
		Assert.assertNotNull(id);
		productDao.delete(product);
		Assert.assertNull(productDao.selectById(id));
	}

	/**
	 * 3. 修改Product
	 */
	@Test
	@Override
	public void update() {
		// TODO
		logger.info("修改Product {}", product);
		Assert.assertNotNull(product);
		Long id = product.getId();
		Assert.assertNotNull(id);
		
		product.setName("test1");
		product.setIntroduce("test1");
		product.setPicture("test1");
		product.setCreateAccount("test1");
		product.setUpdateAccount("test1");
		product.setIsnew("Y");
		product.setSale("Y");
		product.setHit(2);
		product.setStatus(2);
		product.setMaxPicture("test1");
		product.setImages("test1");
		product.setCatalogID(2l);
		product.setSellcount(2);
		product.setStock(2);
		product.setSearchKey("test1");
		product.setTitle("test1");
		product.setDescription("test1");
		product.setKeywords("test1");
		product.setActivityID(2l);
		product.setUnit("test1");
		product.setScore(2);
		product.setIsTimePromotion("Y");
//		product.setGiftID("test1");
		productDao.update(product);
		
		Assert.assertEquals("test1",product.getName());
		Assert.assertEquals("test1",product.getIntroduce());
		Assert.assertEquals("test1",product.getPicture());
		Assert.assertEquals("test1",product.getCreateAccount());
		Assert.assertEquals("test1",product.getUpdateAccount());
		Assert.assertEquals("Y",product.getIsnew());
		Assert.assertEquals("Y",product.getSale());
		Assert.assertEquals(2,product.getHit());
		Assert.assertEquals(2,product.getStatus());
		Assert.assertEquals("test1",product.getMaxPicture());
		Assert.assertEquals("test1",product.getImages());
		Assert.assertEquals(2l,product.getCatalogID().longValue());
		Assert.assertEquals(2,product.getSellcount());
		Assert.assertEquals(2,product.getStock());
		Assert.assertEquals("test1",product.getSearchKey());
		Assert.assertEquals("test1",product.getDescription());
		Assert.assertEquals("test1",product.getKeywords());
		Assert.assertEquals("test1",product.getUnit());
		Assert.assertEquals(2,product.getScore());
		Assert.assertEquals("Y",product.getIsTimePromotion());
		Assert.assertEquals("test1",product.getGiftID());
	}

	/**
	 * 4. 根据Product查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		// TODO
		logger.info("根据Product查询一条记录 {}", product);
		Assert.assertNotNull(product);
		Assert.assertNotNull(productDao.selectOne(product));
	}

	/**
	 * 5. 分页查询Product
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		product = getProduct();
		product.setActivityID(null);
		productDao.insert(product);
		
		logger.info("分页查询Product {}", product);
		Assert.assertNotNull(product);
		List<Product> list = productDao.selectPageList(product);
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}

	/**
	 * 6. 根据条件查询Product
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询Product {}", product);
		Assert.assertNotNull(product);
		List<Product> list = productDao.selectList(product);
		Assert.assertNotNull(list);
		Assert.assertEquals(1, list.size());
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		Long id = product.getId();
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		productDao.deleteById(id);
		Product entity = productDao.selectById(id);
		Assert.assertNotNull(entity);
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		Long id = product.getId();
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		Product entity = productDao.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count Product
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		product = getProduct();
		product.setActivityID(null);
		productDao.insert(product);
		logger.info("分页Count Product {}", product);
		Assert.assertNotNull(product);
		int count = productDao.selectPageCount(product);
		Assert.assertEquals(1, count);
	}
	/**
	 * 10. 分页Count Product
	 */
	@Test
	public void selectParameterList() {
		Long productId = 12313l;
//		List<Product> productList = productDao.selectParameterList(productId);
//		Assert.assertNotNull(productList);
	}
	/**
	 * 11. 查询产品库存 Product
	 */
	@Test
	public void selectStockList() {
		logger.info("查询产品库存 Product {}", product);
		Assert.assertNotNull(product);
		ProductStockInfo stokInfo = productDao.selectStockList(product.getId());
		Assert.assertNotNull(stokInfo);
		Assert.assertEquals(1, stokInfo.getStock());
	}
	
	
	/**
	 * 12. 查询产品简介 Product
	 */
	@Test
	public void selectListProductHTML(){
		logger.info(" Product {}", product);
		Assert.assertNotNull(product);
		product = productDao.selectListProductHTML(product.getId());
		Assert.assertNotNull(product);
		Assert.assertEquals("test", product.getProductHTML());
	}
	/**
	 * 13. 根据产品ID批量查询 Product
	 */
	@Test
	public void selectProductListByIds(){
		logger.info("根据产品ID批量查询 Product {}", product);
		Assert.assertNotNull(product);
		List<Long> productIds = Lists.newArrayList();
		productIds.add(product.getId());
		List<Product> products = productDao.selectProductListByIds(productIds);
		Assert.assertNotNull(products);
		Assert.assertEquals(1, products.size());
	}

	/**
	 * 14.支付成功后更新商品库存数据
	 * 
	 * @param product
	 */
	public void updateStockAfterPaySuccess() {
		Assert.assertNotNull(product);
		productDao.updateStockAfterPaySuccess(product.getId(), 2, 2);
		
		product = productDao.selectById(product.getId());
		Assert.assertEquals(2, product.getStock());
	}
	/**
	 * 15. 查询热门产品 Product
	 */
	@Test
	public void selectHotSearch() {
		Assert.assertNotNull(product);
		
		List<Long> productIds = Lists.newArrayList();
		productIds.add(product.getId());
		List<Product> products = productDao.selectHotSearch(productIds);
		Assert.assertNotNull(products);
		Assert.assertEquals(1, products.size());
	}
	/**
	 * 16.  Product
	 */
	@Test
	public void loadHotProductShowInSuperMenu() {
		Assert.assertNotNull(product);
		
		List<Long> catalogIds = Lists.newArrayList();
		catalogIds.add(product.getCatalogID());
		List<Product> products = productDao.loadHotProductShowInSuperMenu(catalogIds);
		Assert.assertNotNull(products);
	}
	/**
	 * 17. 更新点击数 Product
	 */
	@Test
	public void updateHit() {
		Assert.assertNotNull(product);
		
		product.setHit(2);
		productDao.updateHit(product.getId());
		
		product = productDao.selectById(product.getId());
		Assert.assertEquals(2, product.getHit());
	}
	/**
	 * 18.  Product
	 */
	@Test
	public void selectPageLeftHotProducts() {
		Assert.assertNotNull(product);
		
		List<Product> products = productDao.selectPageLeftHotProducts(26l, 10);
		Assert.assertNotNull(products);
		Assert.assertEquals(1, products.size());
	}
	/**
	 * 19. 分页Count Product
	 */
	@Test
	public void selectActivityProductList() {
		Assert.assertNotNull(product);
		
		List<Long> productIds = Lists.newArrayList();
		productIds.add(product.getId());
		List<Product> products = productDao.selectActivityProductList(productIds);
		Assert.assertNotNull(products);
		Assert.assertEquals(1, products.size());
	}
	
}


