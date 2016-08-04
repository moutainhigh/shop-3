package com.tuisitang.projects.pm.modules.shop.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tuisitang.common.utils.StringUtils;
import com.tuisitang.projects.pm.common.persistence.Page;
import com.tuisitang.projects.pm.modules.shop.dao.CustomerBehaviorDao;
import com.tuisitang.projects.pm.modules.shop.dao.CustomerDao;
import com.tuisitang.projects.pm.modules.shop.entity.Customer;
import com.tuisitang.projects.pm.modules.shop.entity.CustomerBehavior;

@Service
@Transactional(readOnly = true)
public class CustomerService {
	
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private CustomerBehaviorDao customerBehaviorDao;
	
	// -- Customer
	
	public Customer getCustomer(Long id) {
		return customerDao.findOne(id);
	}
	
	public List<Customer> findAllCustomer(){
		return customerDao.findAllList();
	}
	
	public Page<Customer> findCustomer(Page<Customer> page, Customer customer) {
		DetachedCriteria dc = customerDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(customer.getName())) {
			dc.add(Restrictions.like("name", "%" + customer.getName() + "%"));
		}
//		if (customer.getProvince() != null && customer.getProvince().getId() != null) {
//			dc.add(Restrictions.eq("province.id", customer.getProvince().getId()));
//		}
//		if (customer.getCity() != null && customer.getCity().getId() != null) {
//			dc.add(Restrictions.eq("city.id", customer.getCity().getId()));
//		}
		dc.add(Restrictions.eq(Customer.DEL_FLAG, Customer.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("id"));
		return customerDao.find(page, dc);
	}

	@Transactional(readOnly = false)
	public void saveCustomer(Customer customer) {
		customerDao.clear();
		customerDao.flush();
		customerDao.save(customer);
	}
	
	@Transactional(readOnly = false)
	public void deleteCustomer(Long id) {
		customerDao.deleteById(id);
	}
	
	@Transactional(readOnly = false)
	public void saveCustomer(List<Customer> customers) {
		customerDao.clear();
		customerDao.flush();
		customerDao.save(customers);
	}
	
	public Customer findCustomer(Long uid) {
		return customerDao.findByUid(uid);
	}
	
	public List<Customer> findCustomer(List<String> mobiles) {
		return customerDao.findByMobiles(mobiles);
	}
	
	// -- CustomerBehavior
	@Transactional(readOnly = false)
	public void saveCustomerBehavior(CustomerBehavior customerBehavior) {
		customerBehaviorDao.clear();
		customerBehaviorDao.flush();
		customerBehaviorDao.save(customerBehavior);
	}
	
	@Transactional(readOnly = false)
	public void deleteCustomerBehavior(Long id) {
		customerBehaviorDao.delete(id);
	}
	
	public List<CustomerBehavior> findCustomerBehavior(Long customerId) {
		return customerBehaviorDao.findByCustomerId(customerId);
	}
	
}
