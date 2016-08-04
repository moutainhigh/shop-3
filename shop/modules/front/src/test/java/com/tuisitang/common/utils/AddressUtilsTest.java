package com.tuisitang.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tuisitang.common.bo.IpInfo;

/**    
 * @{#} AddressUtilsTest.java  
 * 
 * AddressUtils Test
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class AddressUtilsTest {
	
	private static final Logger logger = LoggerFactory.getLogger(AddressUtilsTest.class);

	public static void main(String[] args) {
//		String ip = "125.71.211.185";
//		ip = "117.176.255.129";
		
		String ip = "127.0.0.1";// AddressUtils.getHost(request);
		if ("127.0.0.1".equals(ip) || "localhost".equals(ip))
			ip = "182.150.21.119";
		IpInfo ipInfo = AddressUtils.getIpInfo(ip);
		logger.info("ip {}, ipInfo {}", ip, ipInfo);
		
	}

}
