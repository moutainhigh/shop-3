package com.tuisitang.common.sms;

import com.tuisitang.common.sms.impl.CLSMSClient;

/**    
 * @{#} SMSClientTest.java  
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class SMSClientTest {

	public static void main(String[] args) {
		
		SMSClient client = CLSMSClient.getSMSClient();
		
		client.sendSMS("18020260877", "亲爱的用户，您的手机验证码是123456，此验证码5分钟内有效，用于修改密码。");
//		client.sendSMS("18080860877", "亲爱的用户，您的手机验证码是123456，此验证码5分钟内有效，用于修改密码。");
	}

}
