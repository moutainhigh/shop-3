package com.tuisitang.modules.shop.front.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.gson.oauth.Oauth;
import com.tuisitang.common.bo.LoginType;
import com.tuisitang.common.cache.memcached.MemcachedObjectType;
import com.tuisitang.common.utils.AddressUtils;
import com.tuisitang.common.utils.CookieUtils;
import com.tuisitang.common.utils.Encodes;
import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.entity.Session;
import com.tuisitang.modules.shop.front.security.CaptchaException;
import com.tuisitang.modules.shop.front.security.MobileCaptchaException;
import com.tuisitang.modules.shop.front.security.UsernamePasswordToken;
import com.tuisitang.modules.shop.front.service.SystemFrontService;
import com.tuisitang.modules.shop.front.utils.AccountUtils;
import com.tuisitang.modules.shop.utils.Global;

import eu.bitwalker.useragentutils.DeviceType;
import eu.bitwalker.useragentutils.UserAgent;

/**    
 * @{#} LogInterceptor.java   
 * 
 * 系统拦截器
 *
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class LogInterceptor implements HandlerInterceptor {
	
	private static final Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
	
	public static final String SESSION_NAME = "LL_SESSION_ID";// 存放在cookie中的sessionKey的name
	
	public static final String FAVORITE_NAME = "LL_FAVORITE";// 存放在cookie中的favorite的name
	
	public static final String CART_NAME = "LL_CART";// 存放在cookie中的cart的name
	
	private SystemFrontService systemFrontService;
	
	public SystemFrontService getSystemFrontService() {
		return systemFrontService;
	}

	public void setSystemFrontService(SystemFrontService systemFrontService) {
		this.systemFrontService = systemFrontService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			String viewName = modelAndView.getViewName();
			String requestURI = request.getRequestURI();
			logger.info("viewName {}, UserAgent {}, requestURI {}", viewName, request.getHeader("User-Agent"), requestURI);
			UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
			if (viewName.startsWith("modules/") && DeviceType.MOBILE.equals(userAgent.getOperatingSystem().getDeviceType())) {
				if (requestURI.indexOf("login") >= 0 || requestURI.indexOf("regist") >= 0 || requestURI.indexOf("/i/") >= 0) {
					modelAndView.setViewName(viewName.replaceFirst("modules", "mobile"));
				}
			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception ex) throws Exception {
		String requestURI = request.getRequestURI();
		logger.info("requestURI {}", requestURI);
		if (requestURI.endsWith(".js") || requestURI.endsWith(".css")
		|| requestURI.endsWith(".png") || requestURI.endsWith(".jpg")
		|| requestURI.endsWith(".php") || requestURI.endsWith(".htm")
		|| requestURI.endsWith(".html") || requestURI.endsWith(".jsp")
		|| requestURI.endsWith(".ico")) {
			return;
		}
		/**
		 * 
		 */
		if ("GET".equals(request.getMethod())) {
			String ua = request.getHeader("User-Agent");
			UserAgent userAgent = UserAgent.parseUserAgentString(ua);
			logger.info("UserAgent {}, deviceType {}, isMicroMessenger {}", ua, userAgent.getOperatingSystem().getDeviceType(),
					ua.indexOf("MicroMessenger") >= 0);
			
			if (requestURI.indexOf("/i/") >= 0) {
				try {
					Account account = AccountUtils.getAccount();
					if (account != null && account.getId() != null) {
						AccountUtils.setAccount(systemFrontService.getAccountById(account.getId()));
					}
				} catch (Exception e) {

				}
			}
			
			if (DeviceType.MOBILE.equals(userAgent.getOperatingSystem().getDeviceType())) {
				if (ua.indexOf("MicroMessenger") >= 0) {
					logger.info("WeChat");
				}
				/**
				String code = request.getParameter("code");
				if (!StringUtil.isBlank(code)) {
					Oauth oauth = new Oauth();
					String json;
					try {
						json = oauth.getToken(code);
						logger.info("json {}", json);
						if (StringUtils.isNotEmpty(json)) {
							JSONObject o = JSONObject.parseObject(json);
							String accessToken = o.getString("access_token");
							String openid = o.getString("openid");
							logger.info("accessToken {}, openid {}", accessToken, openid);
							request.getSession().setAttribute(Global.SESSION_WECHAT_OPENID, openid);
							login(openid, "123", LoginType.Wechat.getType(), openid, request);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					String openid = request.getParameter("openid");
					if (!StringUtils.isBlank(openid)) {
						request.getSession().setAttribute(Global.SESSION_WECHAT_OPENID, openid);
						login(openid, "123", LoginType.Wechat.getType(), openid, request);
					}
				}
				*/
			} else {
				String sessionId = CookieUtils.getCookie(request, SESSION_NAME);// 获得Session ID
				String ip = AddressUtils.getHost(request);// 获得ip地址
				Session session = null;
				logger.info("ip {}, session.id {}, sessionId {}", ip, request.getSession().getId(), sessionId);
				if (StringUtils.isBlank(sessionId)) {//如果sessionKey为空则生成Session Key
					sessionId = SystemFrontService.entryptPassword(request.getSession().getId());
					session = systemFrontService.saveSession(sessionId, null, ip, MemcachedObjectType.Session.getExpiredTime() + System.currentTimeMillis());
					CookieUtils.setCookie(response, SESSION_NAME, sessionId, MemcachedObjectType.Session.getExpiredTime());
				} else {
					session = systemFrontService.getSession(sessionId);
					if (session == null) {
						session = systemFrontService.saveSession(sessionId, null, ip, MemcachedObjectType.Session.getExpiredTime() + System.currentTimeMillis());
					} else if (session != null && session.getAccountId() != null) {
						Account account = systemFrontService.getAccountById(session.getAccountId());
						if (account != null && account.getId() != null) {
							login(account.getMobile(), account.getPassword(), LoginType.Auto.getType(), null, request);
						}
					}
				}
				logger.info("session {}", session);
				
				String redirect = request.getHeader("referer");
				logger.info("redirect {}, method {}, request.getParameter {}", redirect, request.getMethod(), request.getParameter("redirect"));
				
				if ((requestURI.endsWith("regist") || requestURI.endsWith("login")) && "GET".equals(request.getMethod())
				&& StringUtils.isBlank(request.getParameter("redirect")) && !StringUtils.isBlank(redirect)
				&& !redirect.endsWith("regist") && !redirect.endsWith("login")) {
					if (redirect.indexOf("redirect=") >= 0) {
						response.sendRedirect(requestURI + "?" + redirect.substring(redirect.indexOf("redirect=")));
					} else {
						response.sendRedirect(requestURI + "?redirect=" + Encodes.urlEncode(redirect));
					}
				}
				
				if (requestURI.indexOf("/i/") < 0 && requestURI.indexOf("help") < 0
				&&  requestURI.indexOf("regist") < 0
				&&  requestURI.indexOf("login") < 0
				&&  requestURI.indexOf("forget") < 0) {// 用户中心，帮助中心，
				}
			}
		}// End GET.equals
	}
	
	private void login(String mobile, String password, String loginType, String openid, HttpServletRequest request) {
		Subject subject = SecurityUtils.getSubject();
		if (!subject.isAuthenticated()) {
			String host = AddressUtils.getHost(request);
			UsernamePasswordToken token = new UsernamePasswordToken(mobile, password.toCharArray(), true, host, loginType,
					"", "", false, openid);
			token.setRememberMe(true);
			try {
				subject.login(token);
			} catch (UnknownAccountException e) {
				e.printStackTrace();
				logger.error("账号错误 : {}", e.getMessage());
			} catch (IncorrectCredentialsException e) {
				e.printStackTrace();
				logger.error("密码错误 : {}", e.getMessage());
			} catch (LockedAccountException e) {
				e.printStackTrace();
				logger.error("账号已被锁定，请与管理员联系 : {}", e.getMessage());
			} catch(CaptchaException e) {
				e.printStackTrace();
				logger.error("验证码错误 {}", e.getMessage());
			} catch(MobileCaptchaException e) {
				e.printStackTrace();
				logger.error("手机验证码错误 {}", e.getMessage());
			} catch (AuthenticationException e) {
				e.printStackTrace();
				logger.error("你还未授权: {}", e.getMessage());
			}
		} 
	}

}
