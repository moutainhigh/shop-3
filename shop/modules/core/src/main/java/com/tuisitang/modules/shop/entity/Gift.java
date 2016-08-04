package com.tuisitang.modules.shop.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tuisitang.common.cache.memcached.MemcachedObjectType;
import com.tuisitang.common.cache.memcached.SpyMemcachedClient;

import net.jeeshop.core.dao.page.PagerModel;

/**    
 * @{#} Gift.java   
 *    
 * Gift Entity  
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class Gift extends PagerModel<Gift> implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Long id;
	private String giftName;
	private String giftPrice;
	private String createAccount;
	private String createtime;
	private String updateAccount;
	private String updatetime;
	private String status;
	private String picture;

	public static String gift_status_up = "up";
	public static String gift_status_down = "down";
	public static final String CACHE_PROPERTY_ID = "id";// 缓存id属性
	
	public void clear() {
		super.clear();
		id = null;
		giftName = null;
		giftPrice = null;
		createAccount = null;
		createtime = null;
		updateAccount = null;
		updatetime = null;
		status = null;
		picture = null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	public String getGiftPrice() {
		return giftPrice;
	}

	public void setGiftPrice(String giftPrice) {
		this.giftPrice = giftPrice;
	}

	public String getCreateAccount() {
		return createAccount;
	}

	public void setCreateAccount(String createAccount) {
		this.createAccount = createAccount;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUpdateAccount() {
		return updateAccount;
	}

	public void setUpdateAccount(String updateAccount) {
		this.updateAccount = updateAccount;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	/**
	 * 根据id获得memcached key
	 * 
	 * @param id
	 * @return
	 */
	@JsonIgnore
	public static String getKey(Long id) {
		return MemcachedObjectType.Gift.getPrefix() + Gift.CACHE_PROPERTY_ID + SpyMemcachedClient.SEP + id;
	}

}