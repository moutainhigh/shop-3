package com.tuisitang.projects.pm.modules.shop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tuisitang.projects.pm.common.persistence.BaseDao;
import com.tuisitang.projects.pm.common.persistence.BaseDaoImpl;
import com.tuisitang.projects.pm.modules.shop.entity.ProductSpec;

public interface ProductSpecDao extends ProductSpecDaoCustom, CrudRepository<ProductSpec, Long> {
	
}

/**
 * DAO自定义接口
 */
interface ProductSpecDaoCustom extends BaseDao<ProductSpec> {

}

/**
 * DAO自定义接口实现
 */
@Repository
class ProductSpecDaoImpl extends BaseDaoImpl<ProductSpec> implements ProductSpecDaoCustom {
	
}
