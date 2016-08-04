package com.tuisitang.modules.shop.entity;

import java.io.Serializable;
import java.util.List;

import net.jeeshop.core.dao.QueryModel;

import com.google.common.collect.Lists;

/**    
 * @{#} Supplier.java   
 *    
 * 供货厂家/供货商 Entity  
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class Supplier extends QueryModel<Supplier> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Long id;
	private String shortName;// 供货厂家简称
	private String name;// 供货厂家名称
	private short level;// 供货厂家级别：1级 2级 3级
	private Long provinceId;// 省份编号
	private String provinceName;// 省份名称
	private Long cityId;// 城市编号
	private String cityName;// 城市名称
	private Long countyId;// 区县编号
	private String countyName;// 区县名称
	private String address;// 详细地址
	private double longitude;// 经度
	private double latitude;// 维度
	private String phone;// 公司座机
	private String contactName;// 联系人
	private String contactMobile;// 联系人手机号，多个以逗号分隔
	private String business;// 主营业务
	private String status;// 0 正常 1 删除
	private List<SupplierProduct> spList = Lists.newArrayList();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public short getLevel() {
		return level;
	}

	public void setLevel(short level) {
		this.level = level;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public Long getCountyId() {
		return countyId;
	}

	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<SupplierProduct> getSpList() {
		return spList;
	}

	public void setSpList(List<SupplierProduct> spList) {
		this.spList = spList;
	}

}
