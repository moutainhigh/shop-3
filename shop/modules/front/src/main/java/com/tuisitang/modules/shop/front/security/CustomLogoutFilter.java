package com.tuisitang.modules.shop.front.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.tuisitang.common.utils.CookieUtils;
import com.tuisitang.common.utils.SpringContextHolder;
import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.front.event.LogoutEvent;
import com.tuisitang.modules.shop.front.interceptor.LogInterceptor;
import com.tuisitang.modules.shop.front.security.FrontUserAuthorizingRealm.Principal;
import com.tuisitang.modules.shop.front.utils.AccountUtils;

/**    
 * @{#} CustomLogoutFilter.java   
 * 
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@Service
public class CustomLogoutFilter extends LogoutFilter {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomLogoutFilter.class);

	@Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request, response);
        String redirectUrl = getRedirectUrl(request, response, subject);
        try {
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			String sessionId = CookieUtils.getCookie(req, LogInterceptor.SESSION_NAME);
			if (!StringUtils.isBlank(sessionId)) {
				CookieUtils.getCookie(req, resp, LogInterceptor.SESSION_NAME, true);
//				SystemService systemService = SpringContextHolder.getBean(SystemService.class);
//				systemService.deleteSession(sessionId);
			}
//			if (subject.isAuthenticated()) {
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null && principal.getAccount() != null)
				SpringContextHolder.getApplicationContext().publishEvent(new LogoutEvent(this, principal.getAccount(), sessionId));
            subject.logout();
//			}
        } catch (SessionException ise) {
        	ise.printStackTrace();
            logger.debug("Encountered session exception during logout.  This can generally safely be ignored.", ise);
        }
        issueRedirect(request, response, redirectUrl);
        return false;
    }
	
}
