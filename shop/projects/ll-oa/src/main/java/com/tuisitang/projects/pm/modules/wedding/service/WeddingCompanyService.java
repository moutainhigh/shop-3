package com.tuisitang.projects.pm.modules.wedding.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tuisitang.common.utils.StringUtils;
import com.tuisitang.projects.pm.common.persistence.Page;
import com.tuisitang.projects.pm.modules.wedding.dao.WeddingCompanyDao;
import com.tuisitang.projects.pm.modules.wedding.entity.WeddingCompany;

@Service
@Transactional(readOnly = true)
public class WeddingCompanyService {
	
	@Autowired
	private WeddingCompanyDao weddingCompanyDao;
	
	// -- WeddingCompany
	public WeddingCompany getWeddingCompany(Long id) {
		return weddingCompanyDao.findOne(id);
	}
	
	public List<WeddingCompany> findAllWeddingCompany(){
		return weddingCompanyDao.findAllList();
	}
	
	public Page<WeddingCompany> findWeddingCompany(Page<WeddingCompany> page, WeddingCompany weddingCompany) {
		DetachedCriteria dc = weddingCompanyDao.createDetachedCriteria();
		if (StringUtils.isNotEmpty(weddingCompany.getName())) {
			dc.add(Restrictions.like("name", "%" + weddingCompany.getName() + "%"));
		}
		if (StringUtils.isNotEmpty(weddingCompany.getUsername())) {
			dc.add(Restrictions.like("username", "%" + weddingCompany.getUsername() + "%"));
		}
		if (StringUtils.isNotEmpty(weddingCompany.getMobile())) {
			dc.add(Restrictions.like("mobile", "%" + weddingCompany.getMobile() + "%"));
		}
		if (StringUtils.isNotEmpty(weddingCompany.getTel())) {
			dc.add(Restrictions.like("tel", "%" + weddingCompany.getTel() + "%"));
		}
		if (StringUtils.isNotEmpty(weddingCompany.getCompany())) {
			dc.add(Restrictions.like("company", "%" + weddingCompany.getCompany() + "%"));
		}
		if (StringUtils.isNotEmpty(weddingCompany.getAddress())) {
			dc.add(Restrictions.like("address", "%" + weddingCompany.getAddress() + "%"));
		}
		if (StringUtils.isNotEmpty(weddingCompany.getVersion())) {
			dc.add(Restrictions.eq("version", weddingCompany.getVersion()));
		}
		if (StringUtils.isNotEmpty(weddingCompany.getProvinceCode())) {
			dc.add(Restrictions.eq("provinceCode", weddingCompany.getProvinceCode()));
		}
		if (StringUtils.isNotEmpty(weddingCompany.getCityCode())) {
			dc.add(Restrictions.eq("cityCode", weddingCompany.getCityCode()));
		}
		dc.add(Restrictions.eq(WeddingCompany.DEL_FLAG, WeddingCompany.DEL_FLAG_NORMAL));
		dc.addOrder(Order.asc("id"));
		return weddingCompanyDao.find(page, dc);
	}

	@Transactional(readOnly = false)
	public void saveWeddingCompany(WeddingCompany weddingCompany) {
		weddingCompanyDao.clear();
		weddingCompanyDao.flush();
		weddingCompanyDao.save(weddingCompany);
	}
	
	@Transactional(readOnly = false)
	public void saveWeddingCompany(List<WeddingCompany> list) {
		weddingCompanyDao.clear();
		weddingCompanyDao.flush();
		weddingCompanyDao.save(list);
	}
	
	@Transactional(readOnly = false)
	public void deleteWeddingCompany(Long id) {
		weddingCompanyDao.deleteById(id);
	}
	
	
}
