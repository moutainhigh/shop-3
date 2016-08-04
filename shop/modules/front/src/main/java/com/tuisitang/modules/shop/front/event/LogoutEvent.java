package com.tuisitang.modules.shop.front.event;

import org.springframework.context.ApplicationEvent;

import com.tuisitang.modules.shop.entity.Account;

/**    
 * @{#} LoginEvent.java  
 * 
 * 用户注销事件
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class LogoutEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Account account;
	private String sessionId;

	public LogoutEvent(Object source, Account account, String sessionId) {
		super(source);
		this.account = account;
		this.sessionId = sessionId;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}
