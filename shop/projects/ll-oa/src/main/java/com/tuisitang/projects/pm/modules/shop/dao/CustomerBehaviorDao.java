package com.tuisitang.projects.pm.modules.shop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tuisitang.projects.pm.common.persistence.BaseDao;
import com.tuisitang.projects.pm.common.persistence.BaseDaoImpl;
import com.tuisitang.projects.pm.modules.shop.entity.CustomerBehavior;

public interface CustomerBehaviorDao extends CustomerBehaviorDaoCustom, CrudRepository<CustomerBehavior, Long> {
	@Query("from CustomerBehavior a where a.customer.id = ?1 order by a.actionDate")
	List<CustomerBehavior> findByCustomerId(Long customerId);
}

/**
 * DAO自定义接口
 */
interface CustomerBehaviorDaoCustom extends BaseDao<CustomerBehavior> {

}

/**
 * DAO自定义接口实现
 */
@Repository
class CustomerBehaviorDaoImpl extends BaseDaoImpl<CustomerBehavior> implements CustomerBehaviorDaoCustom {
	
}
