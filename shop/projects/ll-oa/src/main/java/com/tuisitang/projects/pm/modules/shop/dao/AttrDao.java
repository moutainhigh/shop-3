package com.tuisitang.projects.pm.modules.shop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tuisitang.projects.pm.common.persistence.BaseDao;
import com.tuisitang.projects.pm.common.persistence.BaseDaoImpl;
import com.tuisitang.projects.pm.modules.shop.entity.Attr;

public interface AttrDao extends AttrDaoCustom, CrudRepository<Attr, Long> {
	@Query("from Attr a order by a.sort")
	List<Attr> findAllList();
}

/**
 * DAO自定义接口
 */
interface AttrDaoCustom extends BaseDao<Attr> {

}

/**
 * DAO自定义接口实现
 */
@Repository
class AttrDaoImpl extends BaseDaoImpl<Attr> implements AttrDaoCustom {
	
}
