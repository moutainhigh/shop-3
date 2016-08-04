package com.tuisitang.common.utils;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Maps;

import eu.bitwalker.useragentutils.UserAgent;

/**    
 * @{#} HttpServletUtils.java  
 * 
 * HttpServletRequest 工具类，主要功能将HttpServletRequest转成Map
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class HttpServletRequestUtils {

	/**
	 * 获得UserAgent
	 * 
	 * @param request
	 * @return
	 */
	public static UserAgent getUserAgent(HttpServletRequest request) {
		return UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
	}

	/**
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, Object> getHttpServletRequestMap(HttpServletRequest request) {
		Map<String, Object> returnMap = Maps.newHashMap();
		UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
		String host = AddressUtils.getHost(request);
		returnMap.put("UserAgentString", request.getHeader("User-Agent"));
		returnMap.put("UserAgent", userAgent);
		returnMap.put("Host", host);
		returnMap.put("BasePath", request.getSession().getServletContext().getRealPath("/") + "upload/i/");
		return returnMap;
	}
	
	/**
	 * 获取ip
	 * @param request
	 * @return
	 */
	public static String getIp(HttpServletRequest request) {
		if (request == null)
			return "";
		String ip = request.getHeader("X-Requested-For");
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}
