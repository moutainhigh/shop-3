package com.tuisitang.modules.shop.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tuisitang.modules.shop.dao.ProductSupplierDao;
import com.tuisitang.modules.shop.dao.SupplierDao;
import com.tuisitang.modules.shop.entity.ProductSupplier;
import com.tuisitang.modules.shop.entity.Supplier;

/**    
 * @{#} SupplierService.java  
 * 
 * 供应商Service
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@Service(value = "supplierService")
public class SupplierService {

	private static final Logger logger = LoggerFactory.getLogger(SupplierService.class);

	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	private ProductSupplierDao productSupplierDao;

	// --Supplier
	/**
	 * 1. 保存供应商
	 * 
	 * @param supplier
	 */
	@Transactional(readOnly = false)
	public void saveSupplier(Supplier supplier) {
		supplierDao.insert(supplier);
	}
	
	/**
	 * 2. 根据id删除Supplier
	 * 
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void deleteSupplier(Long id) {
		supplierDao.deleteById(id);
	}
	
	/**
	 * 3. 更新Supplier
	 * 
	 * @param supplier
	 */
	@Transactional(readOnly = false)
	public void updateSupplier(Supplier supplier) {
		supplierDao.update(supplier);
	}
	
	/**
	 * 4. 根据id获得Supplier
	 * 
	 * @param id
	 * @return
	 */
	public Supplier getSupplier(Long id) {
		return supplierDao.selectById(id);
	}
	
	/**
	 * 5. 查询所有Supplier
	 * 
	 * @return
	 */
	public List<Supplier> findAllSupplier() {
		return supplierDao.selectList(new Supplier());
	}

	// --ProductSupplier
	
	/**
	 * 1. 保存供应商
	 * 
	 * @param productSupplier
	 */
	@Transactional(readOnly = false)
	public void saveProductSupplier(ProductSupplier productSupplier) {
		productSupplierDao.insert(productSupplier);
	}
	
	/**
	 * 2. 根据id删除ProductSupplier
	 * 
	 * @param id
	 */
	@Transactional(readOnly = false)
	public void deleteProductSupplier(Long id) {
		productSupplierDao.deleteById(id);
	}
	
	/**
	 * 3. 更新ProductSupplier
	 * 
	 * @param supplier
	 */
	@Transactional(readOnly = false)
	public void updateProductSupplier(ProductSupplier productSupplier) {
		productSupplierDao.update(productSupplier);
	}
	
	/**
	 * 4. 根据id获得ProductSupplier
	 * 
	 * @param id
	 * @return
	 */
	public ProductSupplier getProductSupplier(Long id) {
		return productSupplierDao.selectById(id);
	}
	
	/**
	 * 5. 查询所有ProductSupplier
	 * 
	 * @return
	 */
	public List<ProductSupplier> findProductSupplier(Long productId) {
		ProductSupplier ps = new ProductSupplier();
		ps.setProductId(productId);
		return productSupplierDao.selectList(ps);
	}

}
