package com.tuisitang.projects.pm.modules.shop.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tuisitang.common.utils.StringUtils;
import com.tuisitang.projects.pm.common.persistence.Page;
import com.tuisitang.projects.pm.modules.shop.dao.SupplierDao;
import com.tuisitang.projects.pm.modules.shop.dao.SupplierProductDao;
import com.tuisitang.projects.pm.modules.shop.entity.Supplier;
import com.tuisitang.projects.pm.modules.shop.entity.SupplierProduct;

@Service
@Transactional(readOnly = true)
public class SupplierService {
	
	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	private SupplierProductDao supplierProductDao;
	
	// -- Supplier
	
	public Supplier getSupplier(Long id) {
		return supplierDao.findOne(id);
	}
	
	public List<Supplier> findAllSupplier(){
		return supplierDao.findAllList();
	}
	
	public Page<Supplier> findSupplier(Page<Supplier> page, Supplier supplier) {
		DetachedCriteria dc = supplierDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(supplier.getName())) {
			dc.add(Restrictions.like("name", "%" + supplier.getName() + "%"));
		}
		if (supplier.getProvince() != null && supplier.getProvince().getId() != null) {
			dc.add(Restrictions.eq("province.id", supplier.getProvince().getId()));
		}
		if (supplier.getCity() != null && supplier.getCity().getId() != null) {
			dc.add(Restrictions.eq("city.id", supplier.getCity().getId()));
		}
		dc.add(Restrictions.eq(Supplier.DEL_FLAG, Supplier.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("id"));
		return supplierDao.find(page, dc);
	}

	@Transactional(readOnly = false)
	public void saveSupplier(Supplier supplier) {
		if (supplier.getProvince() == null || supplier.getProvince().getId() == null) {
			supplier.setProvince(null);
		}
		if (supplier.getCity() == null || supplier.getCity().getId() == null) {
			supplier.setCity(null);
		}
		if (supplier.getCounty() == null || supplier.getCounty().getId() == null) {
			supplier.setCounty(null);
		}
		supplierDao.clear();
		supplierDao.flush();
		supplierDao.save(supplier);
	}
	
	@Transactional(readOnly = false)
	public void deleteSupplier(Long id) {
		supplierDao.deleteById(id);
	}
	
	// -- SupplierProduct
	
	public SupplierProduct getSupplierProduct(Long id) {
		return supplierProductDao.findOne(id);
	}
	
	public List<SupplierProduct> findAllSupplierProduct(){
		return Lists.newArrayList(supplierProductDao.findAll());
	}
	
	public Page<SupplierProduct> findSupplierProduct(Page<SupplierProduct> page, SupplierProduct supplierProduct) {
		DetachedCriteria dc = supplierProductDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(supplierProduct.getProductName())) {
			dc.add(Restrictions.like("productName", "%" + supplierProduct.getProductName() + "%"));
		}
		if (!StringUtils.isBlank(supplierProduct.getState())) {
			dc.add(Restrictions.eq("state", supplierProduct.getState()));
		}
		if (supplierProduct.getSupplier() != null && supplierProduct.getSupplier().getId() != null) {
			dc.add(Restrictions.eq("supplier.id", supplierProduct.getSupplier().getId()));
//			dc.add(Restrictions.eq("supplier.delFlag", Supplier.DEL_FLAG_NORMAL));
		}
		dc.createAlias("supplier", "supplier");
		dc.add(Restrictions.eq("supplier.delFlag", Supplier.DEL_FLAG_NORMAL));
		
		dc.add(Restrictions.eq(SupplierProduct.DEL_FLAG, SupplierProduct.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("supplier.id")).addOrder(Order.asc("id"));
		return supplierProductDao.find(page, dc);
	}

	@Transactional(readOnly = false)
	public void saveSupplierProduct(SupplierProduct supplierProduct) {
		if (supplierProduct.getSupplier() != null && supplierProduct.getSupplier().getId() != null) {
//			supplierProduct.setSupplier(supplier);
		}
		supplierProductDao.clear();
		supplierProductDao.flush();
		supplierProductDao.save(supplierProduct);
	}
	
	@Transactional(readOnly = false)
	public void deleteSupplierProduct(Long id) {
		supplierProductDao.deleteById(id);
	}
	
}
