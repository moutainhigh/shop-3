package com.tuisitang.projects.pm.modules.shop.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tuisitang.projects.pm.common.persistence.BaseDao;
import com.tuisitang.projects.pm.common.persistence.BaseDaoImpl;
import com.tuisitang.projects.pm.modules.shop.entity.SupplierProduct;

public interface SupplierProductDao extends SupplierProductDaoCustom, CrudRepository<SupplierProduct, Long> {
	
	@Modifying
	@Query("update SupplierProduct set delFlag='" + SupplierProduct.DEL_FLAG_DELETE + "' where id = ?1")
	public int deleteById(Long id);

//	@Query("from Dict where delFlag='" + Dict.DEL_FLAG_NORMAL + "' order by sort")
//	public List<Dict> findAllList();
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
interface SupplierProductDaoCustom extends BaseDao<SupplierProduct> {

}

/**
 * DAO自定义接口实现
 */
@Repository
class SupplierProductDaoImpl extends BaseDaoImpl<SupplierProduct> implements SupplierProductDaoCustom {
	
}
