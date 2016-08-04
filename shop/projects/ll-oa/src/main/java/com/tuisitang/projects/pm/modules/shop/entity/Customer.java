package com.tuisitang.projects.pm.modules.shop.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tuisitang.projects.pm.common.persistence.DataEntity;
import com.tuisitang.projects.pm.common.util.excel.annotation.ExcelField;
import com.tuisitang.projects.pm.modules.sys.entity.User;

@Entity
@Table(name = "shop_customer")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Customer extends DataEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;// 编号
	private Long uid;// 用户编号 账号编号
	private String mobile;// 手机号
	private String name;// 姓名
	private String password;// 密码
	private String qq;// QQ
	private String tel;// 座机号
	private String email;// Email
	private String authStatus;// 认证状态：0 未认证 1 已经实名认证 2 已经企业认证 3 个人和企业认证成功 4
								// 个人实名认证失败 5 企业认证失败 6 个人实名认证成功，企业实名认证失败 7
								// 企业实名认证成功，个人实名认证失败 8 个人企业都认证失败
	private String company;// 公司名
	private String picture;// 头像
	private String idNo;// 身份证号
	private String province;// 省份
	private String provinceCode;// 省份编号
	private String city;// 城市
	private String cityCode;// 城市编号
	private String county;// 乡镇
	private String countyCode;// 乡镇编号
	private String address;// 详细地址
	private String enterpriseNo;// 企业
	private String personAuthPicture;// 个人认证图片
	private String enterpriseAuthPicture;// 企业认证图片
	private Date registDate;// 注册时间
	private String loginIp; // 最后登陆IP
	private Date loginDate; // 最后登陆日期
	private String loginArea;// 最后登陆地区
	private User salesman;// 业务员

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sys_user")
//	@SequenceGenerator(name = "seq_sys_user", sequenceName = "seq_sys_user")
	@ExcelField(title = "ID", type = 0, align = 2, sort = 1)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ExcelField(title = "用户ID", type = 0, align = 2, sort = 2)
	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	@ExcelField(title = "手机号", type = 0, align = 2, sort = 3)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@ExcelField(title = "姓名", type = 0, align = 2, sort = 4)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@ExcelField(title = "座机号", type = 0, align = 2, sort = 5)
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@ExcelField(title = "QQ号", type = 0, align = 2, sort = 6)
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@ExcelField(title = "EMail", type = 0, align = 2, sort = 7)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAuthStatus() {
		return authStatus;
	}

	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}

	@ExcelField(title = "公司名", type = 0, align = 2, sort = 8)
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	@ExcelField(title = "所在省份", type = 0, align = 2, sort = 9)
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

	@ExcelField(title = "所在城市", type = 0, align = 2, sort = 10)
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

	@ExcelField(title = "详细地址", type = 0, align = 2, sort = 11)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEnterpriseNo() {
		return enterpriseNo;
	}

	public void setEnterpriseNo(String enterpriseNo) {
		this.enterpriseNo = enterpriseNo;
	}

	public String getPersonAuthPicture() {
		return personAuthPicture;
	}

	public void setPersonAuthPicture(String personAuthPicture) {
		this.personAuthPicture = personAuthPicture;
	}

	public String getEnterpriseAuthPicture() {
		return enterpriseAuthPicture;
	}

	public void setEnterpriseAuthPicture(String enterpriseAuthPicture) {
		this.enterpriseAuthPicture = enterpriseAuthPicture;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="注册日期", type=1, align=1, sort=12)
	public Date getRegistDate() {
		return registDate;
	}

	public void setRegistDate(Date registDate) {
		this.registDate = registDate;
	}

	@ExcelField(title="最好登录IP", type=1, align=1, sort=13)
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="最后登录日期", type=1, align=1, sort=14)
	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	@ExcelField(title="最后登录地区", type=1, align=1, sort=15)
	public String getLoginArea() {
		return loginArea;
	}

	public void setLoginArea(String loginArea) {
		this.loginArea = loginArea;
	}

	@JsonIgnore
	@ManyToOne(fetch=FetchType.LAZY)
	@NotFound(action = NotFoundAction.IGNORE)
	public User getSalesman() {
		return salesman;
	}

	public void setSalesman(User salesman) {
		this.salesman = salesman;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", mobile=" + mobile + ", name=" + name
				+ ", password=" + password + ", qq=" + qq + ", tel=" + tel
				+ ", email=" + email + ", authStatus=" + authStatus
				+ ", company=" + company + ", picture=" + picture + ", idNo="
				+ idNo + ", province=" + province + ", provinceCode="
				+ provinceCode + ", city=" + city + ", cityCode=" + cityCode
				+ ", county=" + county + ", countyCode=" + countyCode
				+ ", address=" + address + ", enterpriseNo=" + enterpriseNo
				+ ", personAuthPicture=" + personAuthPicture
				+ ", enterpriseAuthPicture=" + enterpriseAuthPicture
				+ ", registDate=" + registDate + ", loginIp=" + loginIp
				+ ", loginDate=" + loginDate + ", loginArea=" + loginArea
				+ ", delFlag=" + delFlag + "]";
	}

}
