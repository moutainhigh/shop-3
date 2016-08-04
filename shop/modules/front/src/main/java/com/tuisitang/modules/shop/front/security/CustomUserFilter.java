package com.tuisitang.modules.shop.front.security;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tuisitang.common.utils.Encodes;

/**    
 * @{#} CustomUserFilter.java   
 * 
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 *  
 */
@Service
public class CustomUserFilter extends UserFilter {

	private static final Logger logger = LoggerFactory.getLogger(FrontFormAuthenticationFilter.class);
	
	@Override
	protected void redirectToLogin(ServletRequest request, ServletResponse response) throws IOException {
		HttpServletRequest req = (HttpServletRequest)request;
		String requestURI = req.getRequestURI();
		StringBuffer requestURL  = req.getRequestURL();
		logger.info("requestURI {}, requestURL {}", requestURI, requestURL);
        String loginUrl = getLoginUrl() + "?redirect=" + Encodes.urlEncode(requestURL.toString());
        WebUtils.issueRedirect(request, response, loginUrl);
    }
	
}
