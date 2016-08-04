package com.tuisitang.projects.pm.modules.shop.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tuisitang.common.utils.StringUtils;
import com.tuisitang.projects.pm.common.persistence.Page;
import com.tuisitang.projects.pm.modules.shop.dao.AttrDao;
import com.tuisitang.projects.pm.modules.shop.dao.CatalogDao;
import com.tuisitang.projects.pm.modules.shop.dao.ProductAttrDao;
import com.tuisitang.projects.pm.modules.shop.dao.ProductDao;
import com.tuisitang.projects.pm.modules.shop.dao.ProductSpecDao;
import com.tuisitang.projects.pm.modules.shop.entity.Attr;
import com.tuisitang.projects.pm.modules.shop.entity.Catalog;
import com.tuisitang.projects.pm.modules.shop.entity.Product;
import com.tuisitang.projects.pm.modules.shop.entity.ProductAttr;
import com.tuisitang.projects.pm.modules.shop.entity.ProductSpec;

@Service
public class ProductService {

	@Autowired
	private CatalogDao catalogDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProductSpecDao productSpecDao;
	@Autowired
	private ProductAttrDao productAttrDao;
	@Autowired
	private AttrDao attrDao;

	// - Catalog
	public Catalog getCatalog(Long id) {
		return catalogDao.findOne(id);
	}

	public List<Catalog> findAllCatalog() {
		return catalogDao.findAllList();
	}
	
	public Page<Catalog> findCatalog(Page<Catalog> page, Catalog catalog) {
		DetachedCriteria dc = catalogDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(catalog.getName())) {
			dc.add(Restrictions.like("name", "%" + catalog.getName() + "%"));
		}
		dc.add(Restrictions.eq(Catalog.DEL_FLAG, Catalog.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("id"));
		return catalogDao.find(page, dc);
	}

	@Transactional(readOnly = false)
	public void saveCatalog(Catalog catalog) {
		catalog.setParent(this.getCatalog(catalog.getParent().getId()));
		String oldParentIds = catalog.getParentIds(); // 获取修改前的parentIds，用于更新子节点的parentIds
		catalog.setParentIds(catalog.getParent().getParentIds() + catalog.getParent().getId() + ",");
		catalogDao.clear();
		catalogDao.save(catalog);
		// 更新子节点 parentIds
		List<Catalog> list = catalogDao.findByParentIdsLike("%," + catalog.getId() + ",%");
		for (Catalog e : list) {
			e.setParentIds(e.getParentIds().replace(oldParentIds, catalog.getParentIds()));
		}
		catalogDao.save(list);
	}
	
	@Transactional(readOnly = false)
	public void deleteCatalog(Long id) {
		catalogDao.deleteById(id);
	}
	
	// - Product
	
	public Product getProduct(Long id) {
		productDao.clear();
		productDao.flush();
		return productDao.findOne(id);
	}

	public List<Product> findAllProduct() {
		return productDao.findAllList();
	}

	public Page<Product> findProduct(Page<Product> page, Product product, Long supplierId) {
		productDao.clear();
		productDao.flush();
		DetachedCriteria dc = productDao.createDetachedCriteria();

		dc.createAlias("supplierList", "supplierList", JoinType.INNER_JOIN);
		if (supplierId != null) {
			dc.add(Restrictions.eq("supplierList.id", supplierId));
		}
		dc.add(Restrictions.eq(Product.DEL_FLAG, Product.DEL_FLAG_NORMAL));

		if (StringUtils.isNotEmpty(product.getName())) {
			dc.add(Restrictions.like("name", "%" + product.getName() + "%"));
		}
		
		if (StringUtils.isNotEmpty(product.getIsNew())) {
			dc.add(Restrictions.eq("isNew", product.getIsNew()));
		}
		
		if (StringUtils.isNotEmpty(product.getIsRecommend())) {
			dc.add(Restrictions.eq("isRecommend", product.getIsRecommend()));
		}
		
		if (StringUtils.isNotEmpty(product.getIsSync())) {
			dc.add(Restrictions.eq("isSync", product.getIsSync()));
		}

		dc.add(Restrictions.eq(Catalog.DEL_FLAG, Catalog.DEL_FLAG_NORMAL));
		dc.addOrder(Order.desc("supplierList.id")).addOrder(Order.desc("id"));
		return productDao.find(page, dc);
	}

	@Transactional(readOnly = false)
	public void saveProduct(Product product) {
		productDao.clear();
		productDao.save(product);
	}
	
	@Transactional(readOnly = false)
	public void deleteProduct(Long id) {
		productDao.deleteById(id);
	}
	
	@Transactional(readOnly = false)
	public void saveProduct(List<Product> products) {
		productDao.clear();
		productDao.save(products);
	}
	
	public List<Product> findProduct(Long ...ids) {
		return productDao.findByIdIn(ids);
	}
	
	// - ProductSpec
	
	public ProductSpec getProductSpec(Long id) {
		return productSpecDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public void saveProductSpec(ProductSpec productSpec) {
		productSpecDao.save(productSpec);
	}

	@Transactional(readOnly = false)
	public void deleteProductSpec(Long id) {
		productSpecDao.delete(id);
	}
	
	public List<ProductSpec> findProductSpec(Long productId) {
		return productSpecDao.find("from ProductSpec where product.id = ?",
				productId);
	}
	
	// - ProductAttr
	
	public Attr getAttr(Long id) {
		return attrDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public void saveAttr(Attr attr) {
		attrDao.save(attr);
	}

	@Transactional(readOnly = false)
	public void deleteAttr(Long id) {
		attrDao.delete(id);
	}
	
	public Attr getAttr(String name) {
		List<Attr> l = attrDao.find("from Attr where name = ?", name);
		if (l.isEmpty()) {
			return null;
		} else {
			return l.get(0);
		}
	}
	
	public List<Attr> findAllAttr() {
		return attrDao.findAllList();
	}
	
	// - ProductAttr
	
	public ProductAttr getProductAttr(Long id) {
		return productAttrDao.findOne(id);
	}

	@Transactional(readOnly = false)
	public void saveProductAttr(ProductAttr productAttr) {
		productAttrDao.clear();
		productAttrDao.save(productAttr);
	}

	@Transactional(readOnly = false)
	public void deleteProductAttr(Long id) {
		productAttrDao.delete(id);
	}
	
	public List<ProductAttr> findProductAttr(Long productId) {
		return productAttrDao.find("from ProductAttr where product.id = ?",
				productId);
	}
	
}
