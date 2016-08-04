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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Where;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.tuisitang.projects.pm.common.persistence.DataEntity;

@Entity
@Table(name = "shop_catalog")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Catalog extends DataEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;// 编号
	private String code;// 编码
	private String name;// 目录名称
	private Catalog parent;// 父级目录
	private String parentIds;// 所有父级编号
	private int sort;// 排序
	private String image;// 图标地址
	private String description; // 描述，填写有助于搜索引擎优化
	private String keywords; // 关键字，填写有助于搜索引擎优化
	private List<Catalog> childList = Lists.newArrayList(); // 拥有子分类列表
	
	private List<Product> productList = Lists.newArrayList(); // 拥有角色列表

	public Catalog() {
		super();
	}
	
	public Catalog(Long id) {
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
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="parent_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@NotNull
	public Catalog getParent() {
		return parent;
	}

	public void setParent(Catalog parent) {
		this.parent = parent;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, fetch = FetchType.LAZY, mappedBy = "parent")
	@Where(clause = "del_flag='" + DEL_FLAG_NORMAL + "'")
	@OrderBy(value = "sort")
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public List<Catalog> getChildList() {
		return childList;
	}

	public void setChildList(List<Catalog> childList) {
		this.childList = childList;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "shop_product_catalog", joinColumns = { @JoinColumn(name = "catalog_id") }, inverseJoinColumns = { @JoinColumn(name = "product_id") })
	@Where(clause="del_flag='"+DEL_FLAG_NORMAL+"'")
	@OrderBy("id") @Fetch(FetchMode.SUBSELECT)
	@NotFound(action = NotFoundAction.IGNORE)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	@Transient
	public static void sortList(List<Catalog> list, List<Catalog> sourcelist, Long parentId){
		for (int i = 0; i < sourcelist.size(); i++) {
			Catalog e = sourcelist.get(i);
			if (e.getParent() != null && e.getParent().getId() != null && e.getParent().getId().equals(parentId)) {
				list.add(e);
				// 判断是否还有子节点, 有则继续获取子节点
				for (int j = 0; j < sourcelist.size(); j++) {
					Catalog child = sourcelist.get(j);
					if (child.getParent() != null
					&&  child.getParent().getId() != null
					&&  child.getParent().getId().equals(e.getId())) {
						sortList(list, sourcelist, e.getId());
						break;
					}
				}
			}
		}
	}
	
	@Transient
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	public String getIds() {
		return (this.getParentIds() !=null ? this.getParentIds().replaceAll(",", " ") : "") 
				+ (this.getId() != null ? this.getId() : "");
	}

	@Transient
	public boolean isRoot(){
		return isRoot(this.id);
	}
	
	@Transient
	public static boolean isRoot(Long id){
		return id != null && id.equals(1L);
	}
	
}
