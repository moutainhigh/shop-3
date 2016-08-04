package com.tuisitang.projects.pm.modules.shop.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.google.common.collect.Lists;
import com.tuisitang.projects.pm.common.persistence.DataEntity;
import com.tuisitang.projects.pm.modules.sys.entity.Area;

/**
 CREATE TABLE `shop_supplier` (
	`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
	`short_name` varchar(30) NULL COMMENT '代理商简称',
	`name` varchar(200) NULL COMMENT '代理商名称',
	`level` tinyint NULL DEFAULT 3 COMMENT '代理商级别：1级 2级 3级',
	`province_id` bigint(20) NULL COMMENT '省份编号',
	`city_id` bigint(20) NULL COMMENT '城市编号',
	`county_id` bigint(20) NULL COMMENT '区/城镇编号',
	`address` varchar(255) NULL COMMENT '详细地址',
	`phone` varchar(30) NULL COMMENT '公司座机',
	`contact_name` varchar(30) NULL COMMENT '联系人',
	`contact_mobile` varchar(50) NULL COMMENT '联系人手机号，多个以逗号分隔',
	`business` varchar(255) NULL COMMENT '主营业务',
	`create_by` bigint(20) NULL COMMENT '创建者',
	`create_date` datetime NULL COMMENT '创建日期',
	`update_by` bigint(20) NULL COMMENT '更新者',
	`update_date` datetime NULL COMMENT '更新时间',
	`del_flag` char(1) NULL COMMENT '删除标记（0：正常；1：删除；2：审核）',
	PRIMARY KEY (`id`)
) ENGINE=InnoDB
COMMENT='代理商';
 *
 */
@Entity
@Table(name = "shop_supplier")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Supplier extends DataEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id; // 编号
	private String shortName;// 代理商简称
	private String name;// 代理商名称
	private short level;// 代理商级别：1级 2级 3级
	private Area province;// 省
	private Area city;// 市
	private Area county;// 区
	private String address;// 详细地址
	private String phone;// 公司座机
	private String contactName;// 联系人
	private String contactMobile;// 联系人手机号，多个以逗号分隔
	private String business;// 主营业务
	
	private String isSync;// 是否同步 0 未同步， 1 已同步
	private Double expressPrice;// 快递费，物流费
	private Double increaseRate;// 价格上调率

	private List<SupplierProduct> spList = Lists.newArrayList(); //

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sys_area")
//	@SequenceGenerator(name = "seq_sys_area", sequenceName = "seq_sys_area")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public short getLevel() {
		return level;
	}

	public void setLevel(short level) {
		this.level = level;
	}

//	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="province_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public Area getProvince() {
		return province;
	}

	public void setProvince(Area province) {
		this.province = province;
	}

//	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="city_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public Area getCity() {
		return city;
	}

	public void setCity(Area city) {
		this.city = city;
	}

//	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.ALL })
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="county_id")
	@NotFound(action = NotFoundAction.IGNORE)
	public Area getCounty() {
		return county;
	}

	public void setCounty(Area county) {
		this.county = county;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getIsSync() {
		return isSync;
	}

	public void setIsSync(String isSync) {
		this.isSync = isSync;
	}

	public Double getExpressPrice() {
		return expressPrice;
	}

	public void setExpressPrice(Double expressPrice) {
		this.expressPrice = expressPrice;
	}

	public Double getIncreaseRate() {
		return increaseRate;
	}

	public void setIncreaseRate(Double increaseRate) {
		this.increaseRate = increaseRate;
	}

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "supplier")
	@Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
	@OrderBy(value = "id")
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<SupplierProduct> getSpList() {
		return spList;
	}

	public void setSpList(List<SupplierProduct> spList) {
		this.spList = spList;
	}

}
