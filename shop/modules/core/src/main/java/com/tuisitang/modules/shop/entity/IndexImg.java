package com.tuisitang.modules.shop.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tuisitang.common.cache.memcached.MemcachedObjectType;
import com.tuisitang.common.cache.memcached.SpyMemcachedClient;

import net.jeeshop.core.dao.page.PagerModel;

/**    
 * @{#} IndexImg.java  
 * 
 * IndexImg Entity
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class IndexImg extends PagerModel<IndexImg> implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Long id;
	private String title;
	private String picture;
	private int sort;
	private String description;// 描述
	private String link;// 链接地址
	private String type; //类型
	public static final String INDEX_IMG_TYPE_MALL = "0";// 首页
	public static final String MALL_IMG_TYPE_MALL = "1";// 道具商城
	public static final String GROUP_IMG_TYPE_MALL = "2";// 团购
	public static final String TODAY_IMG_TYPE_MALL = "3";// 今日推荐
	public static final String MOBILE_IMG_TYPE_MOBILE = "4";// 移动端首页
	public static final String SPECIAL_ZERO_IMG_TYPE_MALL = "11";// 零利专场

	public static final String CACHE_PROPERTY_TYPE = "type";// 缓存TYPE属性

	@Override
	public void clear() {
		this.id = null;
		this.title = null;
		this.picture = null;
		this.sort = 0;
		description = null;
		link = null;
	}

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
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 获取key
	 * 
	 * @param id
	 * @return
	 */
	@JsonIgnore
	public static String getIndexImgTypeKey(String type) {
		return MemcachedObjectType.INDEX_IMG_TYPE.getPrefix() + IndexImg.CACHE_PROPERTY_TYPE + SpyMemcachedClient.SEP + type;
	}

}
