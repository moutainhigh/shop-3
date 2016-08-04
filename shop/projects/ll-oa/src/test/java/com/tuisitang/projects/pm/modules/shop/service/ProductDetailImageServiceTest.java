package com.tuisitang.projects.pm.modules.shop.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.annotation.Rollback;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tuisitang.common.mapper.JsonMapper;
import com.tuisitang.common.test.SpringTransactionalContextTests;
import com.tuisitang.projects.pm.modules.shop.entity.Product;
import com.tuisitang.projects.pm.modules.shop.entity.ProductSpec;
import com.tuisitang.projects.pm.modules.shop.entity.SupplierProduct;

public class ProductDetailImageServiceTest extends SpringTransactionalContextTests {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private DataSource shopDataSource;
	
	JsonMapper mapper = JsonMapper.getInstance();
	
	@Test
	@Rollback(false)
	public void updateProductDetailImage() {
//		Page<Product> page = productService.findProduct(new Page<Product>(0, 1000), new Product(), null);
//		logger.info("{}", page.getCount());
		final List<Map<String, Object>> products = Lists.newArrayList();
		
		final JdbcTemplate jdbc = new JdbcTemplate(shopDataSource);
		String sql = "select * from t_product where id = 548";
		jdbc.query(sql, new RowMapper<Map<String, Object>>() {

			@Override
			public Map<String, Object> mapRow(ResultSet rs, int i)
					throws SQLException {
				Map<String, Object> m = Maps.newHashMap();
				long id = rs.getLong("id");
				logger.info("id {}", id);
				m.put("ID", id);
				products.add(m);
				
				Product p = productService.getProduct(id);
				if (StringUtils.isNotBlank(p.getDetails())) {
					String[] ss = p.getDetails().split(",");
					StringBuffer sb = new StringBuffer();
					for (String s : ss) {
						if (StringUtils.isNotBlank(s)) {
							//<img src="http://7xlmuf.com2.z0.glb.qiniucdn.com/attached/images/22/升降爱心路引.jpg" class="product-img-big-left">
//							sb.append("<img src=\"http://img.baoxiliao.com/" + s + "-px1000\"/>");
							sb.append("<img src=\"http://img.baoxiliao.com/" + s + "\"/>");
						}
					}

					logger.info("sb {}", sb.toString());
					if (StringUtils.isNotBlank(sb.toString())) {
//						jdbc.update("update t_product set productHTML = ? where id = ?", sb.toString(), id);
						p.setContent(sb.toString());
						productService.saveProduct(p);
					}
					
				}
				
				SupplierProduct sp = null;// supplierService.getSupplierProduct(p.getSourceId());
				if (sp != null) {
					Integer minSale = sp.getMinSale();
//					logger.info("$$$$$$$$$$$$$$$$$${}, {}, {}", p.getId(), p.getName(), minSale);
					if (minSale != null && minSale > 0) {
						p.setMinSale(minSale);
						
//						productService.saveProduct(p);
						
//						jdbc.update("update t_product set minSell = ? where id = ?", minSale, id);
					}
					
					
					if (StringUtils.isNotBlank(sp.getJson())) {
						List<ProductSpec> psList = productService.findProductSpec(p.getId());
						
						List<Map<String, Object>> l = mapper.fromJson(sp.getJson(), List.class);
						for (Map<String, Object> map : l) {
							Double price = (Double) map.get("price");
							String color = (String) map.get("color") == null ? "" : (String) map.get("color");
							String size = (String) map.get("size") == null ? "" : (String) map.get("size");
							String type = (String) map.get("type") == null ? "" : (String) map.get("type");
							minSale  = (Integer) map.get("minSale") == null ? null : (Integer) map.get("minSale");
							if (minSale != null && minSale > 0) {
//								logger.info("{}, {}, {}", p.getId(), p.getName(), minSale);
								for (ProductSpec ps : psList) {
									String c = ps.getColor() == null ? "" : ps.getColor();
									String s = ps.getSize() == null ? "" : ps.getSize();
									String t = ps.getType() == null ? "" : ps.getType();
									if (c.equals(color) && s.equals(size) && t.equals(type)) {
										logger.info("$$$$$$${}, {}, {}", p.getId(), p.getName(), minSale);
										ps.setMinSale(minSale);
										productService.saveProductSpec(ps);
										jdbc.update("update t_spec set minSell = ? where id = ?", minSale, ps.getId());
									}
								}
							}
						}
					}
					
//					/**
//					 * 1 长
//					 * 2 宽
//					 * 3 高
//					 * 4 颜色
//					 * 5 材质
//					 * 6 规格
//					 */
//					if (StringUtils.isNotBlank(sp.getLength())) {
//						String s = sp.getLength().endsWith(",") ? sp.getLength().substring(0, sp.getLength().length() - 1) : sp.getLength();
//						jdbc.update("insert into t_attribute_link(attrId, productId, value) values(?,?,?)",
//								1, id, s);
//					}
//					
//					if (StringUtils.isNotBlank(sp.getWidth())) {
//						String s = sp.getWidth().endsWith(",") ? sp.getWidth().substring(0, sp.getWidth().length() - 1) : sp.getWidth();
//						jdbc.update("insert into t_attribute_link(attrId, productId, value) values(?,?,?)",
//								2, id, s);
//					}
//					
//					if (StringUtils.isNotBlank(sp.getHeight())) {
//						String s = sp.getHeight().endsWith(",") ? sp.getHeight().substring(0, sp.getHeight().length() - 1) : sp.getHeight();
//						jdbc.update("insert into t_attribute_link(attrId, productId, value) values(?,?,?)",
//								3, id, s);
//					}
//					
//					if (StringUtils.isNotBlank(sp.getColor())) {
//						String s = sp.getColor().endsWith(",") ? sp.getColor().substring(0, sp.getColor().length() - 1) : sp.getColor();
//						jdbc.update("insert into t_attribute_link(attrId, productId, value) values(?,?,?)",
//								4, id, s);
//					}
//					
//					if (StringUtils.isNotBlank(sp.getMaterial())) {
//						String s = sp.getMaterial().endsWith(",") ? sp.getMaterial().substring(0, sp.getMaterial().length() - 1) : sp.getMaterial();
//						jdbc.update("insert into t_attribute_link(attrId, productId, value) values(?,?,?)",
//								5, id, s);
//					}
					
//					if (StringUtils.isNotBlank(sp.getSpec())) {
//						String s = sp.getSpec().endsWith(",") ? sp.getSpec().substring(0, sp.getSpec().length() - 1) : sp.getSpec();
//						jdbc.update("insert into t_attribute_link(attrId, productId, value) values(?,?,?)",
//								6, id, s);
//					}
						
				}
				
				return m;
			}
		});

		logger.info("products.size {}", products.size());
	}

}
