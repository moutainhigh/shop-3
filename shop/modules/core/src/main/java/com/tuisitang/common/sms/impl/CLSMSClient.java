package com.tuisitang.common.sms.impl;

import java.util.Map;

import com.google.common.collect.Maps;
import com.gson.util.HttpKit;
import com.tuisitang.common.service.ServiceException;
import com.tuisitang.common.sms.SMSClient;

/**    
 * @{#} CLSMSClient.java  
 * 
 * 创蓝短信客户端
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class CLSMSClient extends AbstractSMSClient {
	
	private static SMSClient client = getSMSClient();
	
	public static String URL;
	
	private CLSMSClient() {
		
	}

	public static SMSClient getSMSClient() {
		if (client == null) {
			client = new CLSMSClient();
		}
		return client;
	}

	@Override
	public boolean regist() {
		return true;
	}

	@Override
	public boolean logout() {
		return true;
	}

	@Override
	public String sendSMS(String mobiles, String message) {
		Map<String, String> params = Maps.newHashMap();
		try {
			params.put("account", getKey());
			params.put("pswd", getPassword());
			params.put("mobile", mobiles);
			params.put("msg", message);
			params.put("needstatus", "true");
			logger.info("sendSMS send msg --> {}", params);
			String r = HttpKit.post(getSendSMSURL(), params);
			logger.info("sendSMS return msg --> {}", r);
			return r;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

}
