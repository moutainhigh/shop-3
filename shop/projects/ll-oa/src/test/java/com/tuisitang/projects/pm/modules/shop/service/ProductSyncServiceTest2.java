package com.tuisitang.projects.pm.modules.shop.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.annotation.Rollback;

import com.google.common.collect.Lists;
import com.tuisitang.common.ds.SchemaContextHolder;
import com.tuisitang.common.test.SpringTransactionalContextTests;
import com.tuisitang.common.utils.SpringContextHolder;
import com.tuisitang.projects.pm.modules.shop.entity.Catalog;
import com.tuisitang.projects.pm.modules.shop.entity.Product;
import com.tuisitang.projects.pm.modules.shop.entity.ProductAttr;
import com.tuisitang.projects.pm.modules.shop.entity.ProductSpec;
import com.tuisitang.projects.pm.modules.shop.entity.Supplier;

public class ProductSyncServiceTest2 extends SpringTransactionalContextTests {

	@Autowired
	private ProductService productService;
	
	@Test
	@Rollback(false)
	public void sync() {
//		SchemaContextHolder.setSchema(SchemaContextHolder.SCHEMA_TEST);
		SchemaContextHolder.setSchema(SchemaContextHolder.SCHEMA_PRODUCTION);
		List<Product> list = productService.findAllProduct();
		logger.info("{}", list.size());
		DataSource dataSource = SpringContextHolder.getBean("shopDataSource");
		final JdbcTemplate jdbc = new JdbcTemplate(dataSource);
		
		String sql = "SELECT a.* FROM t_product a where a.id = ? ";
		
		List<Product> products = Lists.newArrayList();
		for (final Product product : list) {
			String picture = "";
			try {
				logger.info("product {}", product);
				/**
				 * 同步Product
				 * 	t_spec
				 *  t_attribute_link
				 *  t_product_catalog_rela
				 *  t_product_supplier
				 *  t_product : name price nowPrice isnew sale productHTML images minSell unit sellerID
				 */
				logger.info("images {} \ndetails {} \ncontent {}",
						product.getImages(), product.getDetails(), product.getContent());
				/**
				 * <img src="http://img.baoxiliao.com/attached/details/566/龙珠灯规格.png" alt="" width="800" height="423" title="" align="left" />
				 */
				String images = product.getImages();
				String details = product.getDetails();
				String content = product.getContent() == null ? "" : StringEscapeUtils.unescapeHtml(product.getContent());
				
				if (StringUtils.isNotBlank(images)) {
					StringBuffer sb = new StringBuffer();
					String[] ss = images.split(",");
					for (String s : ss) {
						if (StringUtils.isNotBlank(s.trim())) {
							sb.append(s + ",");
						}
					}
					String s = sb.toString();
					if (s.endsWith(","))
						s = s.substring(0, s.length() - 1);
					logger.info("images {}", s);
					product.setImages(s);
					picture = s.split(",").length > 0 ? s.split(",")[0] : "";
				}
				
				if (StringUtils.isNotBlank(details)) {
					StringBuffer sb = new StringBuffer();
					StringBuffer db = new StringBuffer(content);
					String[] ss = details.split(",");
					for (String s : ss) {
						if (StringUtils.isNotBlank(s.trim())) {
							sb.append(s + ",");
							db.append("<img src=\"http://img.baoxiliao.com/" + s + "\" alt=\"\" width=\"800\" title=\"\" align=\"center\" />");
						}
					}
					String s = sb.toString();
					if (s.endsWith(","))
						s = s.substring(0, s.length() - 1);
					logger.info("details {}", s);
					logger.info("content {}", db.toString());
					product.setDetails(s);
					product.setContent(StringEscapeUtils.escapeHtml(db.toString()));
				}
				
				products.add(product);
				
				final String pic = picture;
				
				Product p = jdbc.queryForObject(sql, new RowMapper<Product>() {
	
					@Override
					public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
						Long id = rs.getLong("id");
						/**
						 * 1. t_spec
						 */
						jdbc.update("DELETE FROM t_spec WHERE productID = ? ", id);
						List<ProductSpec> psList = product.getPsList();
						for (ProductSpec ps : psList) {
							jdbc.update("INSERT INTO t_spec(id, productID, specType, specColor, specSize, specStock, minSell, specPrice, specStatus) values(?, ?, ?, ?, ?, ?, ?, ?, ?)",
									ps.getId(), id, ps.getType(),
									ps.getColor(), ps.getSize(), 9999,
									ps.getMinSale(), ps.getNowPrice(), "y");
						}
						/**
						 * 2. t_attribute_link
						 */
						jdbc.update("DELETE FROM t_attribute_link WHERE productID = ? ", id);
						List<ProductAttr> paList = product.getPaList();
						for (ProductAttr pa : paList) {
							jdbc.update("INSERT INTO t_attribute_link(id, attrID, productID, value) values(?, ?, ?, ?)",
									pa.getId(), pa.getAttr().getId(), id,
									pa.getAttrVal());
						}
						/**
						 * 3. t_product_catalog_rela
						 */
						jdbc.update("DELETE FROM t_product_catalog_rela WHERE productId = ? ", id);
						List<Catalog> catalogList = product.getCatalogList();
						for (Catalog ctalog : catalogList) {
							jdbc.update("INSERT INTO t_product_catalog_rela(productId, catalogId) values(?, ?)",
									id, ctalog.getId());
						}
						
						/**
						 * 4. t_product_supplier
						 */
						jdbc.update("DELETE FROM t_product_supplier WHERE product_id = ? ", id);
						List<Supplier> supplierList = product.getSupplierList();
						for (Supplier supplier : supplierList) {
							jdbc.update("INSERT INTO t_product_supplier(product_id, product_name, supplier_id, supplier_name) values(?, ?, ?, ?)",
									product.getId(), product.getName(),
									supplier.getId(), supplier.getName());
						}
						
						/**
						 * 5. t_product
						 * name price nowPrice isnew sale productHTML images minSell unit sellerID
						 */
						StringBuffer sb = new StringBuffer();
						sb.append("UPDATE t_product set name = ?, price = ?, nowPrice = ?, productHTML = ?, picture = ?, images = ?, minSell = ?, unit = ? WHERE id = ? ");
						jdbc.update(sb.toString(), product.getName(),
								product.getPrice(), product.getPrice(),
								product.getContent(), pic,
								product.getImages(), product.getMinSale(),
								product.getUnit(), product.getId());
						
						return new Product(id);
					}
	
				}, product.getId());
				
			} catch(EmptyResultDataAccessException e) {
				logger.info("添加新产品");
				String insert = "insert into `t_product` (`id`,`name`,`introduce`,`price`,`nowPrice`,`picture`,`createtime`,`createAccount`,`updateAccount`,`updatetime`,`isnew`,`sale`,`hit`,`status`,`productHTML`,`maxPicture`,`images`,`catalogID`,`sellcount`,`stock`,`minSell`,`searchKey`,`title`,`description`,`keywords`,`activityID`,`unit`,`score`,`isTimePromotion`,`isRecommend`,`giftID`,`sellerID`)"
						+ "values(?,?,?,?,?,?,now(),1,1,now(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
				jdbc.update(insert, product.getId(), product.getName(),
						product.getDescription(), product.getPrice(),
						product.getPrice(), picture, "n", "n", 100, "2",
						product.getContent(), null, product.getImages(), 0, 99,
						9999, product.getMinSale(), "", product.getName(),
						product.getDescription(), "", null, product.getUnit(),
						0, "n", "n", null, 1L);
				
				Long id = product.getId();
				/**
				 * 1. t_spec
				 */
				jdbc.update("DELETE FROM t_spec WHERE productID = ? ", id);
				List<ProductSpec> psList = product.getPsList();
				for (ProductSpec ps : psList) {
					jdbc.update("INSERT INTO t_spec(id, productID, specType, specColor, specSize, specStock, minSell, specPrice, specStatus) values(?, ?, ?, ?, ?, ?, ?, ?, ?)",
							ps.getId(), id, ps.getType(),
							ps.getColor(), ps.getSize(), 9999,
							ps.getMinSale(), ps.getNowPrice(), "y");
				}
				/**
				 * 2. t_attribute_link
				 */
				jdbc.update("DELETE FROM t_attribute_link WHERE productID = ? ", id);
				List<ProductAttr> paList = product.getPaList();
				for (ProductAttr pa : paList) {
					jdbc.update("INSERT INTO t_attribute_link(id, attrID, productID, value) values(?, ?, ?, ?)",
							pa.getId(), pa.getAttr().getId(), id,
							pa.getAttrVal());
				}
				/**
				 * 3. t_product_catalog_rela
				 */
				jdbc.update("DELETE FROM t_product_catalog_rela WHERE productId = ? ", id);
				List<Catalog> catalogList = product.getCatalogList();
				for (Catalog ctalog : catalogList) {
					jdbc.update("INSERT INTO t_product_catalog_rela(productId, catalogId) values(?, ?)",
							id, ctalog.getId());
				}
				
				/**
				 * 4. t_product_supplier
				 */
				jdbc.update("DELETE FROM t_product_supplier WHERE product_id = ? ", id);
				List<Supplier> supplierList = product.getSupplierList();
				for (Supplier supplier : supplierList) {
					jdbc.update("INSERT INTO t_product_supplier(product_id, product_name, supplier_id, supplier_name) values(?, ?, ?, ?)",
							product.getId(), product.getName(),
							supplier.getId(), supplier.getName());
				}
				
			} catch(Throwable e) {
				e.printStackTrace();
			} 
		}
		
//		productService.saveProduct(products);
	}
	
}
