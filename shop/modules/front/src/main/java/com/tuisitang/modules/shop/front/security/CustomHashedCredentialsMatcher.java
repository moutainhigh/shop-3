package com.tuisitang.modules.shop.front.security;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tuisitang.modules.shop.front.service.SystemFrontService;

/**    
 * @{#} CustomHashedCredentialsMatcher.java   
 * 
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: tst</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 *  
 */
public class CustomHashedCredentialsMatcher extends HashedCredentialsMatcher {

	private static final Logger logger = LoggerFactory.getLogger(CustomHashedCredentialsMatcher.class);
	
	private String password;
	
	public CustomHashedCredentialsMatcher(String password) {
		this.password = password;
	}
	
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
		String principal = (String) token.getPrincipal();
		String credentials = (String) info.getCredentials();
		logger.info("principal {}, credentials {}, password {}", principal, credentials, password);
		boolean b = SystemFrontService.validatePassword(password, credentials);
		if (!b) {
			logger.error("密码错误");
			throw new IncorrectCredentialsException("用户名密码错误");
		}
		return b;
	}

}
