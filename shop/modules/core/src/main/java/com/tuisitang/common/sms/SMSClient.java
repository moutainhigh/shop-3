package com.tuisitang.common.sms;

/**    
 * @{#} SMSClient.java  
 * 
 * 短信客户端
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public interface SMSClient {

	/**
	 * 1. 注册
	 * 
	 * @return
	 */
	boolean regist();
	
	/**
	 * 2. 注销
	 * 
	 * @return
	 */
	boolean logout();
	
	/**
	 * 3. 发送消息
	 * 
	 * @param mobiles
	 * @param message
	 * @return
	 */
	String sendSMS(String mobiles, String message);
}
