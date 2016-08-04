package com.tuisitang.projects.pm.modules.shop;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.google.common.collect.Lists;
import com.tuisitang.common.mapper.JsonMapper;
import com.tuisitang.common.test.SpringTransactionalContextTests;
import com.tuisitang.common.utils.StringUtils;
import com.tuisitang.projects.pm.modules.shop.dao.ProductDao;
import com.tuisitang.projects.pm.modules.shop.dao.SupplierProductDao;
import com.tuisitang.projects.pm.modules.shop.entity.Product;
import com.tuisitang.projects.pm.modules.shop.entity.ProductSpec;
import com.tuisitang.projects.pm.modules.shop.entity.Supplier;
import com.tuisitang.projects.pm.modules.shop.entity.SupplierProduct;

/**
 * 
 SELECT t.*
  FROM shop_supplier_product t JOIN shop_supplier s ON s.id= t.supplier_id
   AND s.del_flag= '0'
 WHERE t.del_flag= '0'
 ORDER BY s.id,
         t.id
         
DROP TABLE shop_product_supplier; 
DROP TABLE shop_product_catalog;
DROP TABLE shop_product_spec;
DROP TABLE shop_product;
         
CREATE TABLE `shop_product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(200) NOT NULL COMMENT '产品名称',
  `unit` varchar(30) DEFAULT NULL COMMENT '单位',
  `content` longtext COMMENT '产品详情',
  `factory_price` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '出厂价',
  `market_price` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '市场价',
  `price` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '售价',
  `images` varchar(2000) DEFAULT NULL COMMENT '产品图片',
  `is_recommend` char(1) DEFAULT NULL COMMENT '是否推荐：0 否 1 是',
  `source_id` bigint(20) DEFAULT NULL COMMENT '原产品编号：SupplierProduct id',
  `source_code` varchar(30) DEFAULT NULL COMMENT '原产品图片编码：SupplierProduct code',
  `description` varchar(255) DEFAULT NULL COMMENT '描述，填写有助于搜索引擎优化',
  `keywords` varchar(255) DEFAULT NULL COMMENT '关键字，填写有助于搜索引擎优化',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_date` datetime DEFAULT NULL COMMENT '创建日期',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_date` datetime DEFAULT NULL COMMENT '更新日期',
  `remarks` varchar(255) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标记（0：正常；1：删除；2：审核）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品表';

CREATE TABLE `shop_product_catalog` (
  `product_id` bigint(20) NOT NULL COMMENT '产品编号',
  `catalog_id` bigint(20) NOT NULL COMMENT '目录编号',
  PRIMARY KEY (`product_id`,`catalog_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品-目录';

CREATE TABLE `shop_product_spec` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `product_id` bigint(20) NOT NULL COMMENT '产品编号',
  `color` varchar(30) DEFAULT NULL COMMENT '颜色',
  `size` varchar(30) DEFAULT NULL COMMENT '尺寸',
  `type` varchar(30) DEFAULT NULL COMMENT '类型',
  `price` decimal(12,2) DEFAULT '0.00' COMMENT '价格',
  `del_flag` char(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品规格';

CREATE TABLE `shop_product_supplier` (
  `product_id` bigint(20) NOT NULL COMMENT '产品编号',
  `supplier_id` bigint(20) NOT NULL COMMENT '供应商编号',
  PRIMARY KEY (`product_id`,`supplier_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='产品-供应商';
        
 *
 */
public class SupplierProductDaoTest extends SpringTransactionalContextTests {
	
	@Autowired
	private SupplierProductDao supplierProductDao;
	@Autowired
	private ProductDao productDao;
	
	@Test
	@Rollback(false)
	public void find() {
		JsonMapper mapper = JsonMapper.getInstance();
		DetachedCriteria dc = supplierProductDao.createDetachedCriteria();
//		dc.add(Restrictions.eq("state", supplierProduct.getState()));
		dc.createAlias("supplier", "supplier");
		dc.add(Restrictions.eq("supplier.delFlag", Supplier.DEL_FLAG_NORMAL));
		dc.add(Restrictions.eq(SupplierProduct.DEL_FLAG, SupplierProduct.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("supplier.id"));
		List<SupplierProduct> list = supplierProductDao.find(dc);
		
		List<Product> products = Lists.newArrayList();// 产品列表
		
		for (SupplierProduct sp : list) {
//			logger.info("SupplierProduct {}", sp);
			/**
			 * 整理产品信息
			 */
			String name = sp.getProductName();// 产品名称
			double price = sp.getPrice();// 原价
			String isRecommend = sp.getIsRecommend();// 是否首页推荐
			String unit = sp.getUnit();
			
			Product product = new Product();
			product.setSourceId(sp.getId());
			product.setSourceCode(sp.getProductCode());
			product.setIsRecommend(isRecommend);
			product.setName(name);
			product.setUnit(unit);
			
			product.getSupplierList().add(sp.getSupplier());
			
			String json = sp.getJson();
			if (price == 0.00 && StringUtils.isBlank(json)) {
				logger.info("供应商：{}, 产品名称：{},产品价格为空", sp.getSupplier().getName(), name);
//				throw new ServiceException("供应商：" + sp.getSupplier().getName()
//						+ ", 产品名称：" + name + ",产品价格为空");
			}
			product.setFactoryPrice(price);
			
			if (StringUtils.isNotBlank(json)) {
				List<Map<String, Object>> l = mapper.fromJson(json, List.class);
				for (Map<String, Object> m : l) {
					double p = (Double) m.get("price");
					String color = (String) m.get("color");
					String size = (String) m.get("size");
					String type = (String) m.get("type");
					if (StringUtils.isBlank(color) && StringUtils.isBlank(size) && StringUtils.isBlank(type)) {
						logger.info("供应商：{}, 产品名称：{},属性配置错误（只配置了价格）", sp.getSupplier().getName(), name);
//						throw new ServiceException("供应商："
//								+ sp.getSupplier().getName() + ", 产品名称：" + name
//								+ ",属性配置错误（只配置了价格）");
					}
					ProductSpec ps = new ProductSpec();
					ps.setColor(color);
					ps.setSize(size);
					ps.setType(type);
					ps.setPrice(p);
					ps.setProduct(product);
					
					product.getPsList().add(ps);
				}
			}
			
			products.add(product);
		}
		
		logger.info("SupplierProduct.size {}", list.size());
		logger.info("products.size {}", products.size());
		productDao.clear();
		productDao.save(products);
	}
	
}
