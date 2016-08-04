package com.tuisitang.projects.pm.modules.shop.service;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.tuisitang.common.mapper.JsonMapper;
import com.tuisitang.common.test.SpringTransactionalContextTests;
import com.tuisitang.common.utils.StringUtils;
import com.tuisitang.projects.pm.modules.shop.dao.ProductDao;
import com.tuisitang.projects.pm.modules.shop.dao.SupplierDao;
import com.tuisitang.projects.pm.modules.shop.dao.SupplierProductDao;
import com.tuisitang.projects.pm.modules.shop.entity.Product;
import com.tuisitang.projects.pm.modules.shop.entity.ProductSpec;
import com.tuisitang.projects.pm.modules.shop.entity.SupplierProduct;

public class ProductServiceTest extends SpringTransactionalContextTests {

	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	private SupplierProductDao supplierProductDao;
	@Autowired
	private ProductDao productDao;
	
	@Test
	@Rollback(false)
	public void syncById() {
//		Long[] ids = { 804L, 805L, 806L };
//		#龙珠吊灯， 满天星，草坪地毯  仿真蝴蝶兰，花瓣，仿真绣球花，仿真玫瑰花，仿真玉兰花，水雾机，舞台，桁架，顺美：80号,81号
		/*
		 * 
		 */
		Long[] ids = { 813L };
		
		for (Long id : ids) {
			try {
				sync(id);
			} catch (org.springframework.transaction.TransactionSystemException e) {

			} catch (Throwable e) {

			}
		}
	}
	
	private void sync(Long id) {
		JsonMapper mapper = JsonMapper.getInstance();
		List<SupplierProduct> list = supplierProductDao.find("from SupplierProduct where id = ? and delFlag = ?", id,
				SupplierProduct.DEL_FLAG_NORMAL);
		if (!list.isEmpty()) {
			SupplierProduct sp = list.get(0);
			/**
			 * 整理产品信息
			 */
			String name = sp.getProductName();// 产品名称
			double price = sp.getPrice();// 原价
			String isRecommend = sp.getIsRecommend();// 是否首页推荐
			String unit = sp.getUnit();
			
			Product product = productDao.findBySourceId(id);
			logger.info("Product {}", product);
			if (product == null) {
				product = new Product();
			} else {
				
			}
			product.setSourceId(sp.getId());
			product.setSourceCode(sp.getProductCode());
			product.setIsRecommend(isRecommend);
			product.setName(name);
			product.setUnit(unit);
			
			product.getSupplierList().add(sp.getSupplier());
			
			String json = sp.getJson();
			if (price == 0.00 && StringUtils.isBlank(json)) {
				logger.info("供应商：{}, 产品名称：{},产品价格为空", sp.getSupplier().getName(), name);
			}
			
			product.setFactoryPrice(price);
			product.setPrice(price * 1.6);
			product.setIncreaseRate(0.6);
			
			if (StringUtils.isNotBlank(json)) {
				List<Map<String, Object>> l = mapper.fromJson(json, List.class);
				for (Map<String, Object> m : l) {
					double p = (Double) m.get("price");
					String color = (String) m.get("color");
					String size = (String) m.get("size");
					String type = (String) m.get("type");
					if (StringUtils.isBlank(color) && StringUtils.isBlank(size) && StringUtils.isBlank(type)) {
						logger.info("供应商：{}, 产品名称：{},属性配置错误（只配置了价格）", sp.getSupplier().getName(), name);
					}
					ProductSpec ps = new ProductSpec();
					ps.setColor(color);
					ps.setSize(size);
					ps.setType(type);
					ps.setPrice(p);
					ps.setNowPrice(1.6 * p);
					ps.setProduct(product);
					
					product.getPsList().add(ps);
				}
			}
			try {
				productDao.save(product);
			} catch (Throwable e) {
				e.printStackTrace();
				logger.warn("{}", product.getId());
			}
			
		}
	}
	
}
