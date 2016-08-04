package com.tuisitang.modules.shop.entity;

import java.io.Serializable;

import net.jeeshop.core.dao.page.PagerModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tuisitang.common.cache.memcached.MemcachedObjectType;
import com.tuisitang.common.cache.memcached.SpyMemcachedClient;

/**    
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class IndexFloor extends PagerModel<IndexFloor> implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Long id;
	private String title;
	private String picture;
	private int sort;
	private String description;// 描述
	private String banner; //是否滚动图
	private String link;// 链接地址
	private String floor; //楼层
	
	public static final String FLOOR_1 = "1";
	public static final String FLOOR_2 = "2";
	public static final String FLOOR_3 = "3";
	public static final String FLOOR_4 = "4";
	public static final String FLOOR_5 = "5";
	public static final String FLOOR_6 = "6";
	
	public static final String CACHE_PROPERTY_FLOOR = "floor";// 缓存floor属性

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getBanner() {
		return banner;
	}

	public void setBanner(String banner) {
		this.banner = banner;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
	
	/**
	 * 获取key
	 * 
	 * @param id
	 * @return
	 */
	@JsonIgnore
	public static String getIndexFloorKey(String floor) {
		return MemcachedObjectType.INDEX_FLOOR.getPrefix() + IndexFloor.CACHE_PROPERTY_FLOOR + SpyMemcachedClient.SEP + floor;
	}

}
