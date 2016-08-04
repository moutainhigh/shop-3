package com.tuisitang.projects.pm.modules.shop.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.tuisitang.common.utils.Collections3;
import com.tuisitang.projects.pm.common.persistence.DataEntity;

@Entity
@Table(name = "shop_product")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Product extends DataEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;// 编号
	private String sn;// 商品编号
	private String name;// 名称
	private String unit;// 单位
	private String content; // 内容
	private double factoryPrice;// 出厂价
	private double factoryPrice2;// 出厂价2
	private double marketPrice;// 市场价
	private double price;// 售价
	private String images;// 产品图片
	private String details;// 产品详情图
	private String description; // 描述，填写有助于搜索引擎优化
	private String keywords; // 关键字，填写有助于搜索引擎优化
	
	private String isRecommend;// 是否推荐：0 否 1 是
	private Long sourceId;// 原产品编号：SupplierProduct id
	private String sourceCode;// 原产品图片编码：SupplierProduct code
	
	private String isSync;// 是否同步 0 未同步， 1 已同步
	private Double expressPrice;// 快递费，物流费
	private Double increaseRate;// 价格上调率
	
	private Integer minSale;// 最低销售数
	
	private String isNew;// 是否新品：0 否 1 是
	private Integer weight;	// 权重，越大越靠前
	
	private Integer stock;// 库存
	private String status;// 状态：新建 删除
	
	// 包装规格
	private String grossWeight;// 毛重
	private String netWeight;// 净重
	private String packageSize;// 包装规格：长宽高

	private List<Catalog> catalogList = Lists.newArrayList();// 产品目录列表
	private List<Supplier> supplierList = Lists.newArrayList();// 供应商列表
	private List<ProductSpec> psList = Lists.newArrayList();// 产品规格列表
	
	private List<ProductAttr> paList = Lists.newArrayList();// 产品属性列表

	public Product() {
		super();
	}
	
	public Product(Long id) {
		this();
		this.id = id;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cms_category")
//	@SequenceGenerator(name = "seq_cms_category", sequenceName = "seq_cms_category")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public double getFactoryPrice() {
		return factoryPrice;
	}

	public void setFactoryPrice(double factoryPrice) {
		this.factoryPrice = factoryPrice;
	}

	public double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
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

	public String getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}

	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public Integer getMinSale() {
		return minSale;
	}

	public void setMinSale(Integer minSale) {
		this.minSale = minSale;
	}

	//	@ManyToMany(fetch = FetchType.EAGER)
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "shop_product_catalog", joinColumns = { @JoinColumn(name = "product_id") }, inverseJoinColumns = { @JoinColumn(name = "catalog_id") })
	@Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
	@OrderBy("id")
	@Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	public List<Catalog> getCatalogList() {
		return catalogList;
	}

	public void setCatalogList(List<Catalog> catalogList) {
		this.catalogList = catalogList;
	}

//	@ManyToMany(fetch = FetchType.EAGER)
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "shop_product_supplier", joinColumns = { @JoinColumn(name = "product_id") }, inverseJoinColumns = { @JoinColumn(name = "supplier_id") })
	@Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
	@OrderBy("id")
	@Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	public List<Supplier> getSupplierList() {
		return supplierList;
	}

	public void setSupplierList(List<Supplier> supplierList) {
		this.supplierList = supplierList;
	}

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "product")
	@Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
	@OrderBy(value = "id")
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<ProductSpec> getPsList() {
		return psList;
	}

	public void setPsList(List<ProductSpec> psList) {
		this.psList = psList;
	}

	@Transient
	@JsonIgnore
	public List<Long> getSupplierIdList() {
		List<Long> supplierIdList = Lists.newArrayList();
		for (Supplier supplier : supplierList) {
			supplierIdList.add(supplier.getId());
		}
		return supplierIdList;
	}

	@Transient
	public void setSupplierIdList(List<Long> supplierIdList) {
		supplierList = Lists.newArrayList();
		for (Long supplierId : supplierIdList) {
			Supplier supplier = new Supplier();
			supplier.setId(supplierId);
			supplierList.add(supplier);
		}
	}
	
	@Transient
	public String getCatalogIds() {
		List<Long> nameIdList = Lists.newArrayList();
		for (Catalog catalog : catalogList) {
			nameIdList.add(catalog.getId());
		}
		return StringUtils.join(nameIdList, ",");
	}
	
	@Transient
	public void setCatalogIds(String catalogIds) {
		catalogList = Lists.newArrayList();
		if (catalogIds != null){
			String[] ids = StringUtils.split(catalogIds, ",");
			for (String id : ids) {
				Catalog catalog = new Catalog();
				catalog.setId(new Long(id));
				catalogList.add(catalog);
			}
		}
	}
	
	@Transient
	public String getSupplierIds() {
		List<Long> nameIdList = Lists.newArrayList();
		for (Supplier supplier : supplierList) {
			nameIdList.add(supplier.getId());
		}
		return StringUtils.join(nameIdList, ",");
	}
	
	@Transient
	public void setSupplierIds(String supplierIds) {
		supplierList = Lists.newArrayList();
		if (supplierIds != null){
			String[] ids = StringUtils.split(supplierIds, ",");
			for (String id : ids) {
				Supplier supplier = new Supplier();
				supplier.setId(new Long(id));
				supplierList.add(supplier);
			}
		}
	}
	
	/**
	 * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
	 */
	@Transient
	public String getSupplierNames() {
		return Collections3.extractToString(supplierList, "name", ", ");
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}

	public String getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(String netWeight) {
		this.netWeight = netWeight;
	}

	public String getPackageSize() {
		return packageSize;
	}

	public void setPackageSize(String packageSize) {
		this.packageSize = packageSize;
	}

	public double getFactoryPrice2() {
		return factoryPrice2;
	}

	public void setFactoryPrice2(double factoryPrice2) {
		this.factoryPrice2 = factoryPrice2;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "product")
	@OrderBy(value = "id")
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<ProductAttr> getPaList() {
		return paList;
	}

	public void setPaList(List<ProductAttr> paList) {
		this.paList = paList;
	}
	
}
