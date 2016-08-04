package com.tuisitang.projects.pm.modules.wedding.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tuisitang.projects.pm.common.persistence.DataEntity;
import com.tuisitang.projects.pm.common.util.excel.annotation.ExcelField;

@Entity
@Table(name = "qx_wedding_company")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WeddingCompany extends DataEntity  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String VERSION_15 = "1.5";
	public static final String VERSION_20 = "2.0";

	private Long id;
	private String username;
	private String plainPassword;
	private String password;
	private String name;
	private String mobile;
	private String tel;
	private String qq;
	private String company;
	private String address;
	private String version;// 1.5 2.0
	private String province;
	private String provinceCode;
	private String city;
	private String cityCode;
	private String county;
	private String countyCode;
	private Date registDate;
	private String json;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sys_area")
//	@SequenceGenerator(name = "seq_sys_area", sequenceName = "seq_sys_area")
	@ExcelField(title="ID", type=1, align=2, sort=1)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@ExcelField(title="用户名", type=1, align=2, sort=2)
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPlainPassword() {
		return plainPassword;
	}

	public void setPlainPassword(String plainPassword) {
		this.plainPassword = plainPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@ExcelField(title="姓名", type=1, align=2, sort=3)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ExcelField(title="手机号", type=1, align=2, sort=4)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@ExcelField(title="座机号", type=1, align=2, sort=5)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@ExcelField(title="QQ号", type=1, align=2, sort=6)
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@ExcelField(title="公司名称", type=1, align=2, sort=7)
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	@ExcelField(title="详细地址", type=1, align=2, sort=11)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@ExcelField(title="软件版本", type=1, align=2, sort=12)
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@ExcelField(title="省", type=1, align=2, sort=8)
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	@ExcelField(title="市", type=1, align=2, sort=9)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	@ExcelField(title="县", type=1, align=2, sort=10)
	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getCountyCode() {
		return countyCode;
	}

	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="注册日期", type=1, align=1, sort=110)
	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WeddingCompany other = (WeddingCompany) obj;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
