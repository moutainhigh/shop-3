package com.tuisitang.modules.shop.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tuisitang.common.cache.memcached.MemcachedObjectType;
import com.tuisitang.common.cache.memcached.SpyMemcachedClient;

import net.jeeshop.core.dao.page.PagerModel;

/**
 * 
 * @{#} Device.java Create on 2014-7-16 下午2:34:26    
 *    
 * 设备Entity
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: JSL</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 *
 */
public class Device extends PagerModel<Device> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Long id;// 编号
	private String serialId;// 设备注册id
	private String token;// 设备token
	private String mobile;// 绑定的手机号
	private String alias;// 设备别名
	private String mode;// 设备型号：iPhone Android
	private String type;// 设备类型
	private String os; // 操作系统
	private String osVersion; // 系统版本
	private String deviceSecret;// Device Secret : 128位随机码
	private String status;// 状态：0 正常 1 删除
	private Date createTime;// 创建时间

	public Device() {
		super();
	}

	public Device(Long id) {
		this();
		this.id = id;
	}

	public Device(String serialId, String token, String mobile, String alias, String mode, String type, String os, String osVersion, String deviceSecret, String status) {
		super();
		this.serialId = serialId;
		this.token = token;
		this.mobile = mobile;
		this.alias = alias;
		this.mode = mode;
		this.type = type;
		this.os = os;
		this.osVersion = osVersion;
		this.deviceSecret = deviceSecret;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public String getDeviceSecret() {
		return deviceSecret;
	}

	public void setDeviceSecret(String deviceSecret) {
		this.deviceSecret = deviceSecret;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 根据id获得memcached key
	 * 
	 * @param id
	 * @return
	 */
	@JsonIgnore
	public static String getKey(Long id) {
		return MemcachedObjectType.Device.getPrefix() + "id" + SpyMemcachedClient.SEP + id;
	}
	
	/**
	 * 根据token获得memcached key
	 * 
	 * @param token
	 * @return
	 */
	@JsonIgnore
	public static String getKey(String token) {
		return MemcachedObjectType.Device.getPrefix() + "token" + SpyMemcachedClient.SEP + token;
	}

}

