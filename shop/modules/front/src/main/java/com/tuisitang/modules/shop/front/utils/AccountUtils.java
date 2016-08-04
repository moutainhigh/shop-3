package com.tuisitang.modules.shop.front.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;

import com.google.common.collect.Maps;
import com.tuisitang.common.utils.CacheUtils;
import com.tuisitang.common.utils.CookieUtils;
import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.front.interceptor.LogInterceptor;
import com.tuisitang.modules.shop.front.security.FrontUserAuthorizingRealm.Principal;

/**    
 * @{#} AccountUtils.java  
 * 
 * AccountUtils
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class AccountUtils {

	public static final String CACHE_ACCOUNT = "account";

	/**
	 * 1.获得登录用户的Account
	 * 
	 * @return
	 */
	public static Account getAccount() {
		Account account = null;
		Principal principal = (Principal) SecurityUtils.getSubject()
				.getPrincipal();
		if (principal != null) {
			account = principal.getAccount();
		}
		if (account == null) {
			account = new Account();
			SecurityUtils.getSubject().logout();
		}
		return account;
	}
	
	/**
	 * 2. 判断用户是否登录
	 * 
	 * @return
	 */
	public static boolean isUserLogin() {
		Account account = getAccount();
		if (account == null || account.getId() == null) {
			return false;
		}
		return true;
	}

	/**
	 * 3. 更新用户信息
	 * 
	 * @param account
	 */
	public static void setAccount(Account account) {
		Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
		if (principal != null) {
			principal.setAccount(account);
		}
	}
	
	/**
	 * 4. 更新用户信息
	 * 
	 * @param principal
	 * @param account
	 */
	public static void setAccount(Principal principal, Account account) {
		if (principal != null) {
			principal.setAccount(account);
		}
	}
	
	/**
	 * 5. 获得sessionId
	 * 
	 * @param request
	 * @return
	 */
	public static String getSessionId(HttpServletRequest request) {
		return CookieUtils.getCookie(request, LogInterceptor.SESSION_NAME);
	}

	/**
	 * 6. 是否是验证码登录
	 * 
	 * @param accountuame
	 *            用户名
	 * @param isFail
	 *            计数加1
	 * @param clean
	 *            计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String accountuame, boolean isFail, boolean clean) {
		Map<String, Integer> loginFailMap = (Map<String, Integer>) CacheUtils.get("loginFailMap");
		if (loginFailMap == null) {
			loginFailMap = Maps.newHashMap();
			CacheUtils.put("loginFailMap", loginFailMap);
		}
		Integer loginFailNum = loginFailMap.get(accountuame);
		if (loginFailNum == null) {
			loginFailNum = 0;
		}
		if (isFail) {
			loginFailNum++;
			loginFailMap.put(accountuame, loginFailNum);
		}
		if (clean) {
			loginFailMap.remove(accountuame);
		}
		return loginFailNum >= 3;
	}
}
