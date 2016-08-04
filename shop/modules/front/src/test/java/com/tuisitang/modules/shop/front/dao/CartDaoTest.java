package com.tuisitang.modules.shop.front.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.modules.shop.dao.CartDao;
import com.tuisitang.modules.shop.entity.Cart;
import com.tuisitang.modules.shop.entity.Cart;

/**    
 * @{#} CartDaoTest.java  
 * 
 * Cart Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">paninxb</a>   
 *  
 */
public class CartDaoTest extends BaseDaoTest<Cart> {

	@Autowired
	private CartDao cartDao;

	private Cart cart;

	@Before
	public void setUp() {
		cart = new Cart();

		Assert.assertNotNull(cartDao);
	}

	@After
	public void tearDown() {

	}

	@Override
	public void insert() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectOne() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectPageList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectList() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteById() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void selectById() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void selectPageCount() {
		// TODO Auto-generated method stub
		
	}
	
	@Test
	public void findBySessionIdOrAccountIdAndIds() {
		String sessionId = "QTNGODZCNTFGOEVBRkE1ODdCRjJERDcwMUM5M0U4NkQ6MTI3LjAuMC4x";
		Long accountId = 64L;
		Long[] ids = { 3L, 4L };
		List<Cart> list = cartDao.findBySessionIdOrAccountIdAndIds(sessionId,
				accountId, ids);
		for (Cart cart : list) {
			logger.info("Cart {}", cart);
		}
	}

}


