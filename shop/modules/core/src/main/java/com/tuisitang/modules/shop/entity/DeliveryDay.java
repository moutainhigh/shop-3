package com.tuisitang.modules.shop.entity;

import java.io.Serializable;

import com.tuisitang.common.cache.memcached.MemcachedObjectType;
import com.tuisitang.common.cache.memcached.SpyMemcachedClient;

/**    
 * @{#} DeliveryDay.java  
 * 
 * 收货日期Entity
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class DeliveryDay implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String code;
	private String remarks;

	public DeliveryDay() {
		super();
	}

	public DeliveryDay(String name, String code, String remarks) {
		super();
		this.name = name;
		this.code = code;
		this.remarks = remarks;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public static String getKey() {
		return MemcachedObjectType.DeliveryDay.getPrefix() + "all";
	}

	public static String getKey(Long id) {
		return MemcachedObjectType.DeliveryDay.getPrefix() + "id" + SpyMemcachedClient.SEP + id;
	}

	public static String getKey(String code) {
		return MemcachedObjectType.DeliveryDay.getPrefix() + "code" + SpyMemcachedClient.SEP + code;
	}
}
