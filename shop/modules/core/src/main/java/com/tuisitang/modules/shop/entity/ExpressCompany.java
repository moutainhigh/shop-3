package com.tuisitang.modules.shop.entity;

import com.tuisitang.common.cache.memcached.MemcachedObjectType;
import com.tuisitang.common.cache.memcached.SpyMemcachedClient;

import net.jeeshop.core.dao.QueryModel;

/**    
 * @{#} ExpressCompany.java   
 *    
 * 快递公司 Entity  
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class ExpressCompany extends QueryModel<ExpressCompany> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Long id;
	private String code;// 编码 zhongtong
	private String name;// 名称
	private String url;// 官网地址
	private String tel;// 官网客服电话

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}
	
	public static String getKey() {
		return MemcachedObjectType.ExpressCompany.getPrefix() + "all";
	}

	public static String getKey(Long id) {
		return MemcachedObjectType.ExpressCompany.getPrefix() + "id" + SpyMemcachedClient.SEP + id;
	}

	public static String getKey(String code) {
		return MemcachedObjectType.ExpressCompany.getPrefix() + "code" + SpyMemcachedClient.SEP + code;
	}

}
