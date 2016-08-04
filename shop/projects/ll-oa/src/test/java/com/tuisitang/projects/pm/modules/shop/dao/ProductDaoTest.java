package com.tuisitang.projects.pm.modules.shop.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.common.test.SpringTransactionalContextTests;
import com.tuisitang.projects.pm.modules.shop.entity.Product;

public class ProductDaoTest extends SpringTransactionalContextTests {

	@Autowired
	private ProductDao productDao;
	
	@Test
	public void find() {
		DetachedCriteria dc = productDao.createDetachedCriteria();

//		dc.createAlias("supplierList", "supplierList", JoinType.INNER_JOIN, Restrictions.eq("supplierList.id", 1L));
		dc.createAlias("supplierList", "supplierList", JoinType.INNER_JOIN);
		dc.add(Restrictions.eq("supplierList.id", 1L));
		dc.add(Restrictions.eq(Product.DEL_FLAG, Product.DEL_FLAG_NORMAL));
		
		List<Product> list = productDao.find(dc);
		
		logger.info("{}", list.size());
	}
}
