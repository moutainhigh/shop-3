package com.tuisitang.modules.shop.front.event;

import java.util.Map;

import org.springframework.context.ApplicationEvent;

import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.front.security.FrontUserAuthorizingRealm.Principal;

/**    
 * @{#} LoginEvent.java  
 * 
 * 用户登录事件
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class LoginEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String sessionId;
	private Account account;
	private Principal principal;
//	private boolean isRegist;
	private String loginType;// 登录方式
	private Map<String, Object> requestMap;

//	public LoginEvent(Object source, String sessionId, Account account, Principal principal, boolean isRegist, Map<String, Object> requestMap) {
	public LoginEvent(Object source, String sessionId, Account account, Principal principal, String loginType, Map<String, Object> requestMap) {
		super(source);
		this.sessionId = sessionId;
		this.account = account;
		this.principal = principal;
//		this.isRegist = isRegist;
		this.loginType = loginType;
		this.requestMap = requestMap;
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

	public Principal getPrincipal() {
		return principal;
	}

	public void setPrincipal(Principal principal) {
		this.principal = principal;
	}

	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public Map<String, Object> getRequestMap() {
		return requestMap;
	}

	public void setRequestMap(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}

}
