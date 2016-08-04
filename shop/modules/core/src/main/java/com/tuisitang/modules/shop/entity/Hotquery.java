package com.tuisitang.modules.shop.entity;
import java.io.Serializable;

import com.tuisitang.common.cache.memcached.MemcachedObjectType;

import net.jeeshop.core.dao.page.PagerModel;

/**    
 * @{#} Hotquery.java  
 * 
 * Hotquery Entity
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class Hotquery extends PagerModel<Hotquery> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String TYPE_HOTQUERY = "0";// 热门查询
	public static final String TYPE_SEARCHLIST = "1";// 查询列表

	protected Long id;
	private String keyword;// 关键字
	private String type;// 类型：0 hotquery 1 search
	private int quantity;// 数量
	private String url;// /search?k=关键字

	public void clear() {
		super.clear();
		id = null;
		type = null;
		keyword = null;
		quantity = 0;
		url = null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public static String getKey() {
		return MemcachedObjectType.Hotquery.getPrefix() + "ALL";
	}
	
}