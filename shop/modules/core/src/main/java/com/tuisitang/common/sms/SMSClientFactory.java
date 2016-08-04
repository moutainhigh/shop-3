package com.tuisitang.common.sms;

import com.tuisitang.common.bo.SmsPlatformType;
import com.tuisitang.common.sms.impl.CLSMSClient;

/**    
 * @{#} SMSClientFactory.java  
 * 
 * 短信客户端工厂类
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class SMSClientFactory {

	public static SMSClient getClient(String type) {
		if (SmsPlatformType.CL.getType().equals(type)) {
			return CLSMSClient.getSMSClient();
		}
		return null;
	}

}
