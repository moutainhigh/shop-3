package com.tuisitang.modules.shop.entity;

import java.io.Serializable;

import com.tuisitang.common.cache.memcached.MemcachedObjectType;

/**    
 * @{#} Session.java  
 * 
 * Session Entity
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class Session implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String sessionId;// Session Key
	private Long accountId;// 账号id
	private String ip;// 登录ip
	private String data;//
	private long expiredTime;// 失效时间，时间戳

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public long getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(long expiredTime) {
		this.expiredTime = expiredTime;
	}

	public static String getKey(String sessionId) {
		return MemcachedObjectType.Session.getPrefix() + sessionId;
	}

	@Override
	public String toString() {
		return "Session [sessionId=" + sessionId + ", accountId=" + accountId
				+ ", ip=" + ip + ", data=" + data + ", expiredTime="
				+ expiredTime + "]";
	}
	
}
