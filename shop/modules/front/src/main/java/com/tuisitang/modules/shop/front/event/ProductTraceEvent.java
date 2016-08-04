package com.tuisitang.modules.shop.front.event;

import java.util.Map;

import org.springframework.context.ApplicationEvent;

import com.tuisitang.modules.shop.entity.Account;

/**    
 * @{#} ProductTraceEvent.java  
 * 
 * 商品浏览跟踪事件
 * 记录商品的
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class ProductTraceEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long productId;
	private String productName;
	private String sessionId;
	private Account account;
	private Map<String, Object> requstMap;

	public ProductTraceEvent(Object source, Long productId, String productName,
			String sessionId, Account account, Map<String, Object> requstMap) {
		super(source);
		this.productId = productId;
		this.productName = productName;
		this.sessionId = sessionId;
		this.account = account;
		this.requstMap = requstMap;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Map<String, Object> getRequstMap() {
		return requstMap;
	}

	public void setRequstMap(Map<String, Object> requstMap) {
		this.requstMap = requstMap;
	}

}
