package com.tuisitang.modules.shop.front.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.stereotype.Service;

/**    
 * @{#} FrontFormAuthenticationFilter.java   
 * 
 * 表单验证（包含验证码）过滤类
 *
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 *  
 */
@Service
public class FrontFormAuthenticationFilter extends FormAuthenticationFilter {

	public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;

	public String getCaptchaParam() {
		return captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}
	
	protected String getMobileVerify(ServletRequest request) {
		return WebUtils.getCleanParam(request, "mobileVerify");
	}
	
	protected String getLoginType(ServletRequest request) {
		return WebUtils.getCleanParam(request, "loginType");
	}  

	protected boolean isRegist(ServletRequest request) {
		String s = WebUtils.getCleanParam(request, "isRegist");
		return StringUtils.isBlank(s) ? false : new Boolean(s);
	}
	
	protected String getOpenid(ServletRequest request) {
		return WebUtils.getCleanParam(request, "openid");
	}
	
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		if (password == null) {
			password = "";
		}  
		boolean rememberMe = isRememberMe(request);
		String host = getHost(request);
		String loginType = getLoginType(request);
		String captcha = getCaptcha(request);
		String mobileVerify = getMobileVerify(request);
		boolean isRegist = isRegist(request);
		String openid = getOpenid(request);
		return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, 
				loginType, captcha, mobileVerify, isRegist, openid);
	}

}