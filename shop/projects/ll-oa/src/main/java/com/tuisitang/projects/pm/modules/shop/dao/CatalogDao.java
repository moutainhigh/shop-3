package com.tuisitang.projects.pm.modules.shop.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tuisitang.projects.pm.common.persistence.BaseDao;
import com.tuisitang.projects.pm.common.persistence.BaseDaoImpl;
import com.tuisitang.projects.pm.modules.shop.entity.Catalog;

public interface CatalogDao extends CatalogDaoCustom, CrudRepository<Catalog, Long> {

	@Modifying
	@Query("update Catalog set delFlag='" + Catalog.DEL_FLAG_DELETE + "' where id = ?1")
	public int deleteById(Long id);

	@Query("from Catalog where delFlag='" + Catalog.DEL_FLAG_NORMAL + "' order by id")
	public List<Catalog> findAllList();

	public List<Catalog> findByParentIdsLike(String parentIds);

	@Query("from Catalog where delFlag='" + Catalog.DEL_FLAG_NORMAL + "' and parent.id=:parentId order by id, sort")
	public Page<Catalog> findByParentId(@Param("parentId") Long parentId, Pageable pageable);

	public List<Catalog> findByIdIn(Long[] ids);
	
	@Query("from Catalog where delFlag='" + Catalog.DEL_FLAG_NORMAL + "' and code = ?1")
	public Catalog findByCode(String code);
	
}

/**
 * DAO自定义接口
 */
interface CatalogDaoCustom extends BaseDao<Catalog> {

}

/**
 * DAO自定义接口实现
 */
@Repository
class CatalogDaoImpl extends BaseDaoImpl<Catalog> implements CatalogDaoCustom {
	
}
