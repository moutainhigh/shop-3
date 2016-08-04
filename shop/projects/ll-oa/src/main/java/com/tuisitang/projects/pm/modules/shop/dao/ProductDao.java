package com.tuisitang.projects.pm.modules.shop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tuisitang.projects.pm.common.persistence.BaseDao;
import com.tuisitang.projects.pm.common.persistence.BaseDaoImpl;
import com.tuisitang.projects.pm.modules.shop.entity.Product;

public interface ProductDao extends ProductDaoCustom, CrudRepository<Product, Long> {
	
	@Modifying
	@Query("update Product set delFlag='" + Product.DEL_FLAG_DELETE + "' where id = ?1")
	public int deleteById(Long id);

	@Query("from Product where delFlag='" + Product.DEL_FLAG_NORMAL + "' order by id")
	public List<Product> findAllList();
	
	@Query("from Product where sourceId = ?1 and delFlag='" + Product.DEL_FLAG_NORMAL + "' order by id")
	public Product findBySourceId(Long sourceId);
	
//	@Query("from Product where delFlag='" + Product.DEL_FLAG_NORMAL + "' and id in (?1) order by id")
	public List<Product> findByIdIn(Long ...ids);
}

/**
 * DAO自定义接口
 */
interface ProductDaoCustom extends BaseDao<Product> {

}

/**
 * DAO自定义接口实现
 */
@Repository
class ProductDaoImpl extends BaseDaoImpl<Product> implements ProductDaoCustom {
	
}
