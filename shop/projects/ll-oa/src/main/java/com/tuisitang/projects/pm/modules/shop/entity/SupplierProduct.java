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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.google.common.collect.Lists;
import com.tuisitang.projects.pm.common.persistence.DataEntity;

@Entity
@Table(name = "shop_supplier_product")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SupplierProduct extends DataEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Supplier supplier;// 供货商
	private String productCode;// 产品编码
	private String productName;// 产品名称
	private String productBrand;// 产品品牌
	private String isRecommend;// 0 否 1 是
	private String unit;// 计价单位
	private double price;// 出厂价
	private double price1;// 淘宝价
	private double price2;// 中国道具网价
	private double price3;// 婚品汇价
	private double price4;// 海洲价
	private double price5;// 实体价
	private double price99;// 其它价价
	private String summary;// 产品简介
	private String color;// 颜色 多个颜色用逗号分隔
	private String size;// 尺寸：大 中 小 逗号分隔
	private String length;// 长cm 逗号分隔
	private String width;// 宽cm 逗号分隔
	private String height;// 高cm 逗号分隔
	private String material;// 材质 多个材质用逗号分隔
	private String spec;// 规格
	private String json;// 颜色 - 尺寸 - 规格 - 价格 列表组成的JSON 
	private String image;// 图片
	private Integer minSale;// 最少购买数
	private String state;// 状态 0 正常 1 异常 9 未知
	
	private List<SupplierProductImage> images = Lists.newArrayList(); //

	public SupplierProduct() {
		super();
		this.isRecommend = "0";
	}
	
	public SupplierProduct(Long id) {
		this();
		this.id = id;
	}

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="supplier_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@NotNull
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductBrand() {
		return productBrand;
	}

	public void setProductBrand(String productBrand) {
		this.productBrand = productBrand;
	}

	public String getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getPrice1() {
		return price1;
	}

	public void setPrice1(double price1) {
		this.price1 = price1;
	}

	public double getPrice2() {
		return price2;
	}

	public void setPrice2(double price2) {
		this.price2 = price2;
	}

	public double getPrice3() {
		return price3;
	}

	public void setPrice3(double price3) {
		this.price3 = price3;
	}

	public double getPrice4() {
		return price4;
	}

	public void setPrice4(double price4) {
		this.price4 = price4;
	}

	public double getPrice5() {
		return price5;
	}

	public void setPrice5(double price5) {
		this.price5 = price5;
	}

	public double getPrice99() {
		return price99;
	}

	public void setPrice99(double price99) {
		this.price99 = price99;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getMinSale() {
		return minSale;
	}

	public void setMinSale(Integer minSale) {
		this.minSale = minSale;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "product")
//	@Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
	@OrderBy(value = "id")
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<SupplierProductImage> getImages() {
		return images;
	}

	public void setImages(List<SupplierProductImage> images) {
		this.images = images;
	}

}
