package com.tuisitang.modules.shop.front.event.listener;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tuisitang.common.bo.SmsPlatformType;
import com.tuisitang.common.sms.SMSClient;
import com.tuisitang.common.sms.SMSClientFactory;
import com.tuisitang.common.utils.SpringContextHolder;
import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.entity.Sms;
import com.tuisitang.modules.shop.front.event.SmsEvent;

/**    
 * @{#} AbstractApplicationListener.java  
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public abstract class AbstractApplicationListener {
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 发送短信
	 * 
	 * @param account
	 * @param mobile
	 * @param type
	 * @param content
	 */
	protected void sendSms(Account account, String mobile, String type, String content) {
		Date systemTime = new Date(System.currentTimeMillis());
		try {
			SMSClient client = SMSClientFactory.getClient(SmsPlatformType.CL.getType());
			String msg = client.sendSMS(mobile, content);
			SpringContextHolder.getApplicationContext().publishEvent(new SmsEvent(this, account, mobile, content, type, 
					SmsPlatformType.CL.getType(), Sms.STATUS_SUCCESS, msg, systemTime));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{}", e);
			SpringContextHolder.getApplicationContext().publishEvent(new SmsEvent(this, account, mobile, content, type, 
					SmsPlatformType.CL.getType(), Sms.STATUS_EXCEPTION, e.getMessage(), systemTime));
		}
	}
	
}
