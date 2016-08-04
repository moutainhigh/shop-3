package com.tuisitang.common.sms.impl;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tuisitang.common.sms.SMSClient;
import com.tuisitang.modules.shop.utils.Global;

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
public abstract class AbstractSMSClient implements SMSClient {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	public static final String URL_SEND_SMS = "http://sdkhttp.eucp.b2m.cn/sdkproxy/sendsms.action";
	
	/**
	 * 获得key
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	protected static String getKey() throws UnsupportedEncodingException {
		return Global.getSmsSerialKey();
	}

	/**
	 * 获得password
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	protected static String getPassword() throws UnsupportedEncodingException {
		return Global.getSmsSerialPassword();
	}
	
	protected static String getSendSMSURL() throws UnsupportedEncodingException {
		return Global.getSmsSendSMSURL();
	}

}
