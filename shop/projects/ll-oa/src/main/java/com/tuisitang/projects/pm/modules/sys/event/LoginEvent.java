package com.tuisitang.projects.pm.modules.sys.event;

import java.util.Map;

import org.springframework.context.ApplicationEvent;

import com.tuisitang.projects.pm.modules.sys.entity.User;
import com.tuisitang.projects.pm.modules.sys.security.SystemAuthorizingRealm.Principal;

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

	private User user;
	private Principal principal;
	private Map<String, Object> requestMap;

	public LoginEvent(Object source, User user, Principal principal, Map<String, Object> requestMap) {
		super(source);
		this.user = user;
		this.principal = principal;
		this.requestMap = requestMap;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Principal getPrincipal() {
		return principal;
	}

	public void setPrincipal(Principal principal) {
		this.principal = principal;
	}

	public Map<String, Object> getRequestMap() {
		return requestMap;
	}

	public void setRequestMap(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}

}
