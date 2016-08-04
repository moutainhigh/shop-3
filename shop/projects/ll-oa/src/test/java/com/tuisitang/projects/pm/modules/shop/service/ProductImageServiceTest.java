package com.tuisitang.projects.pm.modules.shop.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.annotation.Rollback;

import com.google.common.collect.Lists;
import com.tuisitang.common.ds.SchemaContextHolder;
import com.tuisitang.common.test.SpringTransactionalContextTests;
import com.tuisitang.common.utils.SpringContextHolder;
import com.tuisitang.projects.pm.modules.shop.entity.Product;

public class ProductImageServiceTest extends SpringTransactionalContextTests {

	@Autowired
	private ProductService productService;
	
	@Test
	@Rollback(false)
	public void sync() {
		SchemaContextHolder.setSchema(SchemaContextHolder.SCHEMA_TEST);
		List<Product> list = productService.findAllProduct();
		logger.info("{}", list.size());
		
		List<Product> products = Lists.newArrayList();
		for (final Product product : list) {
			String content = product.getContent();
			if (!StringUtils.isBlank(content)) {
				content = StringEscapeUtils.unescapeHtml(content);
				content = content.replaceAll("attached/details/", "http://img.baoxiliao.com/attached/details/");
				content = content.replaceAll("http://img.baoxiliao.com/http://img.baoxiliao.com/attached/details/", "http://img.baoxiliao.com/attached/details/");
				content = content.replaceAll("align=\"left\"", "align=\"center\"");
//				logger.info("{}", content);
				product.setContent(content);
				products.add(product);
			}
		}
		
//		productService.saveProduct(products);
		DataSource dataSource = SpringContextHolder.getBean("shopDataSource");
		final JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		String sql = "SELECT a.* FROM t_product a ";
		
		List<Map<String, Object>> l = jdbc.queryForList(sql);
		
		for (Map<String, Object> m : l) {
			Integer id = (Integer) m.get("id");
			String productHTML = (String) m.get("productHTML");
			if (!StringUtils.isBlank(productHTML)) {
				productHTML = StringEscapeUtils.unescapeHtml(productHTML);
				productHTML = productHTML.replaceAll("attached/details/",
						"http://img.baoxiliao.com/attached/details/");
				productHTML = productHTML.replaceAll(
								"http://img.baoxiliao.com/http://img.baoxiliao.com/attached/details/",
								"http://img.baoxiliao.com/attached/details/");
				productHTML = productHTML.replaceAll("align=\"left\"", "align=\"center\"");
				logger.info("{}", productHTML);
				productHTML = StringEscapeUtils.escapeHtml(productHTML);
				
				jdbc.update("update t_product set productHTML = ? where id = ? ", productHTML, id);
			}
		}
		
	}
	
}
