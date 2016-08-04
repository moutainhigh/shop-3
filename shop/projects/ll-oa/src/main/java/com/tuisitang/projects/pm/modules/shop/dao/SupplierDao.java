package com.tuisitang.projects.pm.modules.shop.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tuisitang.projects.pm.common.persistence.BaseDao;
import com.tuisitang.projects.pm.common.persistence.BaseDaoImpl;
import com.tuisitang.projects.pm.modules.shop.entity.Supplier;

public interface SupplierDao extends SupplierDaoCustom, CrudRepository<Supplier, Long> {
	
	@Modifying
	@Query("update Supplier set delFlag='" + Supplier.DEL_FLAG_DELETE + "' where id = ?1")
	public int deleteById(Long id);

	@Query("from Supplier where delFlag='" + Supplier.DEL_FLAG_NORMAL + "' order by id")
	public List<Supplier> findAllList();
	
//
//	@Query("select type from Dict where delFlag='" + Dict.DEL_FLAG_NORMAL + "' group by type")
//	public List<String> findTypeList();
	
//	@Query("from Dict where delFlag='" + Dict.DEL_FLAG_NORMAL + "' and type=?1 order by sort")
//	public List<Dict> findByType(String type);
//	
//	@Query("select label from Dict where delFlag='" + Dict.DEL_FLAG_NORMAL + "' and value=?1 and type=?2")
//	public List<String> findValueByValueAndType(String value, String type);
	
}

/**
 * DAO自定义接口
 */
interface SupplierDaoCustom extends BaseDao<Supplier> {

}

/**
 * DAO自定义接口实现
 */
@Repository
class SupplierDaoImpl extends BaseDaoImpl<Supplier> implements SupplierDaoCustom {
	
}
