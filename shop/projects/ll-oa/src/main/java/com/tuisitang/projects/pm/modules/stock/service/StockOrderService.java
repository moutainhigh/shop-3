package com.tuisitang.projects.pm.modules.stock.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tuisitang.common.utils.StringUtils;
import com.tuisitang.projects.pm.common.persistence.Page;
import com.tuisitang.projects.pm.modules.stock.dao.StockOrderDao;
import com.tuisitang.projects.pm.modules.stock.dao.StockOrderDetailDao;
import com.tuisitang.projects.pm.modules.stock.entity.StockOrder;
import com.tuisitang.projects.pm.modules.stock.entity.StockOrderDetail;

@Service
@Transactional(readOnly = true)
public class StockOrderService {
	
	@Autowired
	private StockOrderDao stockOrderDao;
	@Autowired
	private StockOrderDetailDao stockOrderDetailDao;
	
	// -- StockOrder
	
	public StockOrder getStockOrder(Long id) {
		return stockOrderDao.findOne(id);
	}
	
	public List<StockOrder> findAllStockOrder(){
		return stockOrderDao.findAllList();
	}
	
	public Page<StockOrder> findStockOrder(Page<StockOrder> page, StockOrder stockOrder) {
		DetachedCriteria dc = stockOrderDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(stockOrder.getNo())) {
			dc.add(Restrictions.like("no", "%" + stockOrder.getNo() + "%"));
		}
//		if (stockOrder.getProvince() != null && stockOrder.getProvince().getId() != null) {
//			dc.add(Restrictions.eq("province.id", stockOrder.getProvince().getId()));
//		}
//		if (stockOrder.getCity() != null && stockOrder.getCity().getId() != null) {
//			dc.add(Restrictions.eq("city.id", stockOrder.getCity().getId()));
//		}
		dc.add(Restrictions.eq(StockOrder.DEL_FLAG, StockOrder.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("id"));
		return stockOrderDao.find(page, dc);
	}

	@Transactional(readOnly = false)
	public void saveStockOrder(StockOrder stockOrder) {
		stockOrderDao.clear();
		stockOrderDao.flush();
		stockOrderDao.save(stockOrder);
	}
	
	@Transactional(readOnly = false)
	public void deleteStockOrder(Long id) {
		stockOrderDao.deleteById(id);
	}
	
	@Transactional(readOnly = false)
	public void saveStockOrder(List<StockOrder> stockOrders) {
		stockOrderDao.clear();
		stockOrderDao.flush();
		stockOrderDao.save(stockOrders);
	}
	
	// -- StockOrderDetail
	
	public StockOrderDetail getStockOrderDetail(Long id) {
		return stockOrderDetailDao.findOne(id);
	}
	
	public List<StockOrderDetail> findStockOrderDetail(Long orderId){
		return stockOrderDetailDao.findAllList();
	}
	
	@Transactional(readOnly = false)
	public void saveStockOrderDetail(StockOrderDetail stockOrderDetail) {
		stockOrderDetailDao.clear();
		stockOrderDetailDao.flush();
		stockOrderDetailDao.save(stockOrderDetail);
	}
	
	@Transactional(readOnly = false)
	public void deleteStockOrderDetail(Long id) {
		stockOrderDetailDao.delete(id);
	}
	
	@Transactional(readOnly = false)
	public void saveStockOrderDetail(List<StockOrderDetail> stockOrderDetails) {
		stockOrderDetailDao.clear();
		stockOrderDetailDao.flush();
		stockOrderDetailDao.save(stockOrderDetails);
	}
	
}
