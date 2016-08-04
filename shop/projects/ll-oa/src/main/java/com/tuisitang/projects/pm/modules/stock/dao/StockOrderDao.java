package com.tuisitang.projects.pm.modules.stock.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tuisitang.projects.pm.common.persistence.BaseDao;
import com.tuisitang.projects.pm.common.persistence.BaseDaoImpl;
import com.tuisitang.projects.pm.modules.shop.entity.Catalog;
import com.tuisitang.projects.pm.modules.stock.entity.StockOrder;

public interface StockOrderDao extends StockOrderDaoCustom, CrudRepository<StockOrder, Long> {
	@Query("from StockOrder a where a.delFlag = '"+StockOrder.DEL_FLAG_NORMAL+"' order by a.id")
	List<StockOrder> findAllList();
	
	@Modifying
	@Query("update StockOrder set delFlag='" + Catalog.DEL_FLAG_DELETE + "' where id = ?1")
	public int deleteById(Long id);
}

/**
 * DAO自定义接口
 */
interface StockOrderDaoCustom extends BaseDao<StockOrder> {

}

/**
 * DAO自定义接口实现
 */
@Repository
class StockOrderDaoImpl extends BaseDaoImpl<StockOrder> implements StockOrderDaoCustom {
	
}
