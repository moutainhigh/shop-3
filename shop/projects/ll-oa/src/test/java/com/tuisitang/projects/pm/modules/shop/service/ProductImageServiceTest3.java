package com.tuisitang.projects.pm.modules.shop.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sql.DataSource;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;

import com.google.common.collect.Lists;
import com.tuisitang.common.ds.SchemaContextHolder;
import com.tuisitang.common.service.ServiceException;
import com.tuisitang.common.test.SpringTransactionalContextTests;
import com.tuisitang.common.utils.SpringContextHolder;
import com.tuisitang.projects.pm.modules.shop.entity.Product;

public class ProductImageServiceTest3 extends SpringTransactionalContextTests {

	@Autowired
	private ProductService productService;
	
	@Test
	@Rollback(false)
	public void sync() {
		SchemaContextHolder.setSchema(SchemaContextHolder.SCHEMA_PRODUCTION);
		
		DataSource dataSource = SpringContextHolder.getBean("shopDataSource");
		final JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		String sql = "SELECT a.* FROM t_product a ";
		
		List<Map<String, Object>> l = jdbc.queryForList(sql);
		List<Product> products = Lists.newArrayList();
		for (Map<String, Object> m : l) {
			Integer id = (Integer) m.get("id");
			String name = (String) m.get("name");
			String productHTML = (String) m.get("productHTML");
			if (!StringUtils.isBlank(productHTML)) {
				productHTML = StringEscapeUtils.unescapeHtml(productHTML);
				
				Product p = productService.getProduct(new Long(id));
				if(p!=null && p.getId()!=null){
					p.setContent(productHTML);
					p.setDetails("");
					products.add(p);
				}
			}
		}
		
		productService.saveProduct(products);
		
	}
	
}
