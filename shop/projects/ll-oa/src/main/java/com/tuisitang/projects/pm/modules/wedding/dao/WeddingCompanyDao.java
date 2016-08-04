package com.tuisitang.projects.pm.modules.wedding.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tuisitang.projects.pm.common.persistence.BaseDao;
import com.tuisitang.projects.pm.common.persistence.BaseDaoImpl;
import com.tuisitang.projects.pm.modules.wedding.entity.WeddingCompany;

public interface WeddingCompanyDao extends WeddingCompanyDaoCustom, CrudRepository<WeddingCompany, Long> {
	@Query("from WeddingCompany a order by a.id")
	List<WeddingCompany> findAllList();

	@Modifying
	@Query("update WeddingCompany set delFlag='" + WeddingCompany.DEL_FLAG_DELETE + "' where id = ?1")
	public int deleteById(Long id);
	
}

/**
 * DAO自定义接口
 */
interface WeddingCompanyDaoCustom extends BaseDao<WeddingCompany> {

}

/**
 * DAO自定义接口实现
 */
@Repository
class WeddingCompanyDaoImpl extends BaseDaoImpl<WeddingCompany> implements WeddingCompanyDaoCustom {

}
