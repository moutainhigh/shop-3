package com.tuisitang.projects.pm.modules.shop.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tuisitang.projects.pm.common.persistence.BaseDao;
import com.tuisitang.projects.pm.common.persistence.BaseDaoImpl;
import com.tuisitang.projects.pm.modules.shop.entity.ProductAttr;

public interface ProductAttrDao extends ProductAttrDaoCustom, CrudRepository<ProductAttr, Long> {
	
}

/**
 * DAO自定义接口
 */
interface ProductAttrDaoCustom extends BaseDao<ProductAttr> {

}

/**
 * DAO自定义接口实现
 */
@Repository
class ProductAttrDaoImpl extends BaseDaoImpl<ProductAttr> implements ProductAttrDaoCustom {
	
}
