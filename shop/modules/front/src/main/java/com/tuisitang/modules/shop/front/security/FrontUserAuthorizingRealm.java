package com.tuisitang.modules.shop.front.security;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.subject.WebSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
import com.tuisitang.common.bo.LoginType;
import com.tuisitang.common.servlet.ValidateCodeServlet;
import com.tuisitang.common.utils.CookieUtils;
import com.tuisitang.common.utils.HttpServletRequestUtils;
import com.tuisitang.common.utils.SpringContextHolder;
import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.front.event.LoginEvent;
import com.tuisitang.modules.shop.front.interceptor.LogInterceptor;
import com.tuisitang.modules.shop.front.service.SystemFrontService;
import com.tuisitang.modules.shop.service.OrderService;

/**    
 * @{#} FrontUserAuthorizingRealm.java   
 * 
 * 用户安全认证实现类
 *
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 *  
 */
@Service
//@DependsOn({"memberDao"})
public class FrontUserAuthorizingRealm extends AuthorizingRealm {
	
	private static final Logger logger = LoggerFactory.getLogger(FrontUserAuthorizingRealm.class);

	private SystemFrontService systemFrontService;
	
	private OrderService orderService;

	/**
	 * 认证回调函数, 登录时调用
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		String mobileVerify = token.getMobileVerify();
		logger.info("token {}", token);
		Account account = null;
		if (LoginType.Wechat.getType().equals(token.getLoginType())) {// 如果是微信登录，根据openid查询
			account = getSystemFrontService().getAccountByOpenid(token.getUsername());
		} else {
			account = getSystemFrontService().getAccountByMobile(token.getUsername());
		}
		logger.info("account {}", account);
		
		if (account != null) {
			Subject subject = SecurityUtils.getSubject();
			HttpServletRequest request = (HttpServletRequest) ((WebSubject) subject).getServletRequest();
			if (LoginType.Dynamic.getType().equals(token.getLoginType())) {
				// 判断验证码
				Session session = subject.getSession();
				String code = (String) session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
				logger.info("code {}, {}", code, token.getCaptcha());
				if (token.getCaptcha() == null || !token.getCaptcha().toUpperCase().equals(code)) {
					logger.info("验证码错误");
					throw new CaptchaException("验证码错误");
				}
				logger.info("mobileVerify {}, {}", mobileVerify, getSystemFrontService().getMobileVerify(token.getUsername()));
				if (StringUtils.isBlank(mobileVerify) 
				||  !mobileVerify.equals(this.getSystemFrontService().getMobileVerify(token.getUsername()))) {
					logger.info("手机验证码验证码错误");
					throw new MobileCaptchaException("手机验证码验证码错误");
				}
				SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(new Principal(account), 
						SystemFrontService.entryptPassword(new String(token.getPassword())), getName());
				HashedCredentialsMatcher hcm = new CustomHashedCredentialsMatcher(new String(token.getPassword()));
				setCredentialsMatcher(hcm);
				updateAccountInfo(request, account, token.getLoginType(), token.getOpenid());
				return info;
			} else if (LoginType.Auto.getType().equals(token.getLoginType()) || LoginType.Wechat.getType().equals(token.getLoginType())) {
//				String sessionId = CookieUtils.getCookie(request, LogInterceptor.SESSION_NAME);// 获得Session ID
//				logger.info("sessionId {}", sessionId);
				SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(new Principal(account), 
						SystemFrontService.entryptPassword(new String(token.getPassword())), getName());
				HashedCredentialsMatcher hcm = new CustomHashedCredentialsMatcher(new String(token.getPassword()));
				setCredentialsMatcher(hcm);
				updateAccountInfo(request, account, token.getLoginType(), token.getOpenid());
				return info;
			} else {
				SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(new Principal(account), account.getPassword(), getName());
				HashedCredentialsMatcher hcm = new CustomHashedCredentialsMatcher(new String(token.getPassword()));
				setCredentialsMatcher(hcm);
				updateAccountInfo(request, account, token.getLoginType(), token.getOpenid());
				return info;
			}
		} else {
			logger.error("账号不存在");
			throw new UnknownAccountException("账号密码错误");
		}
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		Principal principal = (Principal) getAvailablePrincipal(principals);
		Account account = getSystemFrontService().getAccountByMobile(principal.getUsername());
		if (account != null) {
			logger.info("account {}", account);
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			info.setRoles(Sets.newHashSet("user"));
			return info;
		} else {
			return null;
		}
	}
	
	/**
	 * 清空用户关联权限认证，待下次使用时重新加载
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清空所有关联认证
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}

	public SystemFrontService getSystemFrontService() {
		if (systemFrontService == null) {
			systemFrontService = SpringContextHolder.getBean(SystemFrontService.class);
		}
		return systemFrontService;
	}
	
	public OrderService getOrderService() {
		if (orderService == null) {
			orderService = SpringContextHolder.getBean(OrderService.class);
		}
		return orderService;
	}
	
	/**
	 * 更新用户信息
	 */
	private void updateAccountInfo(HttpServletRequest request, Account account, String loginType, String openid) {
//		logger.info("登录成功");
//		// 1. 更新登录IP和登录时间
//		systemService.updateUserLoginInfo(account.getId());
//		// 2. 获得sessionKey
		String sessionId = CookieUtils.getCookie(request, LogInterceptor.SESSION_NAME);
//		logger.info("sessionId {}", sessionId);
//		// 3. 根据sessionKey和Account.id更新：1）购物车；2）我的收藏
//		if (!StringUtils.isBlank(sessionId)) {
//			getSystemService().updateSession(sessionId, account.getId());
//			getOrderService().updateCart(sessionId, account.getId());;
//		}
		Map<String, Object> requestMap = HttpServletRequestUtils.getHttpServletRequestMap(request);
		if (!StringUtils.isBlank(openid))
			requestMap.put("openid", openid);
		Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
		SpringContextHolder.getApplicationContext().publishEvent(new LoginEvent(this, sessionId, account, principal, loginType, 
				requestMap));
	}
	
	/**
	 * 授权用户信息
	 */
	public static class Principal implements Serializable {

		private static final long serialVersionUID = 1L;

		private Long id;
		private String username;
		private String name;
		private Map<String, Object> cacheMap;
		private Account account;

		public Principal(Account account) {
			this.id = account.getId();
			this.username = account.getMobile();
			this.name = account.getNickname();
			this.account = account;
		}

		public Long getId() {
			return id;
		}

		public String getUsername() {
			return username;
		}

		public String getName() {
			return name;
		}

		public Account getAccount() {
			return account;
		}
		
		public void setAccount(Account account) {
			this.account = account;
		}

		public Map<String, Object> getCacheMap() {
			if (cacheMap == null) {
				cacheMap = new HashMap<String, Object>();
			}
			return cacheMap;
		}

	}
}
