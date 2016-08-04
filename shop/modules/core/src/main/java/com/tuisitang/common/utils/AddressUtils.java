package com.tuisitang.common.utils;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;

import com.gson.util.HttpKit;
import com.tuisitang.common.bo.IpInfo;
import com.tuisitang.common.mapper.JsonMapper;

/**    
 * @{#} AddressUtils.java  
 * 
 * 根据IP地址获取详细的地域信息
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class AddressUtils {

	private static final String IP_API_BASE_URL = "http://ip.taobao.com/service/getIpInfo.php?ip=%s";

	private static final JsonMapper mapper = JsonMapper.getInstance();

	/**
	 * 根据request信息获得IP/Host信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getHost(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 获得IP/Host
	 * 
	 * @return
	 */
	public static String getHost() {
		return SecurityUtils.getSubject().getSession().getHost();
	}

	/**
	 * 获取指定IP的区域位置
	 * 
	 * @param ip
	 * @return
	 */
	public static IpInfo getIpInfo(String ip) {
		try {
			if ("localhost".equals(ip) || "127.0.0.1".equals(ip)) {
				return null;
			}
			String json = HttpKit.post(String.format(IP_API_BASE_URL, ip),
					new HashMap<String, String>());
			Map<String, Object> map = mapper.fromJson(json, Map.class);
			Integer code = (Integer) map.get("code");
			if (code == 0) {
				Map<String, String> data = (Map<String, String>) map.get("data");
				if (!"IANA".equals(data.get("country_id").toString())) {
					IpInfo ipInfo = new IpInfo(data);
					return ipInfo;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return null;
	}

}