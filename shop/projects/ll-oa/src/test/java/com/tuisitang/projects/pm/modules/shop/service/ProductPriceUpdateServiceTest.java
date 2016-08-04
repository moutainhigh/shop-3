package com.tuisitang.projects.pm.modules.shop.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.common.test.SpringTransactionalContextTests;
import com.tuisitang.projects.pm.common.persistence.Page;
import com.tuisitang.projects.pm.modules.shop.entity.Product;

public class ProductPriceUpdateServiceTest extends SpringTransactionalContextTests {

	@Autowired
	private ProductService productService;

	@Test
	public void updateProductPrice() {
		Page<Product> page = productService.findProduct(new Page<Product>(0, 1000), new Product(), null);
		logger.info("{}", page.getCount());
		
	}

}
