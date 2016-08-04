package com.tuisitang.projects.pm.modules.stock.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tuisitang.projects.pm.common.persistence.BaseDao;
import com.tuisitang.projects.pm.common.persistence.BaseDaoImpl;
import com.tuisitang.projects.pm.modules.stock.entity.StockOrderDetail;

public interface StockOrderDetailDao extends StockOrderDetailDaoCustom, CrudRepository<StockOrderDetail, Long> {
	@Query("from StockOrderDetail a order by a.sort")
	List<StockOrderDetail> findAllList();
}

/**
 * DAO自定义接口
 */
interface StockOrderDetailDaoCustom extends BaseDao<StockOrderDetail> {

}

/**
 * DAO自定义接口实现
 */
@Repository
class StockOrderDetailDaoImpl extends BaseDaoImpl<StockOrderDetail> implements StockOrderDetailDaoCustom {
	
}
