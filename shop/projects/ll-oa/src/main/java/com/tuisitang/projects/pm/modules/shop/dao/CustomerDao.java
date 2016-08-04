package com.tuisitang.projects.pm.modules.shop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tuisitang.projects.pm.common.persistence.BaseDao;
import com.tuisitang.projects.pm.common.persistence.BaseDaoImpl;
import com.tuisitang.projects.pm.modules.shop.entity.Customer;

public interface CustomerDao extends CustomerDaoCustom, CrudRepository<Customer, Long> {
	
	@Query("from Customer a where a.delFlag = '0' order by a.registDate")
	List<Customer> findAllList();
	
	@Modifying
	@Query("update Customer set delFlag='" + Customer.DEL_FLAG_DELETE + "' where id = ?1")
	public int deleteById(Long id);
	
	@Query("from Customer a where a.delFlag = '0' and a.uid = ?1")
	Customer findByUid(Long uid);
	
	@Query("from Customer a where a.delFlag = '0' and a.mobile in (?1)")
	List<Customer> findByMobiles(List<String> mobiles);
}

/**
 * DAO自定义接口
 */
interface CustomerDaoCustom extends BaseDao<Customer> {

}

/**
 * DAO自定义接口实现
 */
@Repository
class CustomerDaoImpl extends BaseDaoImpl<Customer> implements CustomerDaoCustom {
	
}
