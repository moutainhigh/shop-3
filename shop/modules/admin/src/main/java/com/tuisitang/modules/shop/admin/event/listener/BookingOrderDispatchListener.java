package com.tuisitang.modules.shop.admin.event.listener;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.impl.cookie.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.tuisitang.common.bo.NotifyType;
import com.tuisitang.common.bo.SmsPlatformType;
import com.tuisitang.common.cache.memcached.SpyMemcachedClient;
import com.tuisitang.common.sms.SMSClient;
import com.tuisitang.common.sms.SMSClientFactory;
import com.tuisitang.common.utils.ValidateUtils;
import com.tuisitang.modules.shop.admin.event.BookingOrderDispatchEvent;
import com.tuisitang.modules.shop.entity.Sms;
import com.tuisitang.modules.shop.entity.User;
import com.tuisitang.modules.shop.service.CommonService;
import com.tuisitang.modules.shop.service.SystemService;

/**    
 * @{#} ProductChangeListener.java  
 * 
 * 产品变更侦听
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@Service
public class BookingOrderDispatchListener implements ApplicationListener<BookingOrderDispatchEvent> {
	
	private static final Logger logger = LoggerFactory.getLogger(BookingOrderDispatchListener.class);
	
	@Autowired
	private SystemService systemService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private SpyMemcachedClient memcachedClient;

	@Override
	public void onApplicationEvent(BookingOrderDispatchEvent event) {
		Long userId = event.getUserId();
		Date bookingTime = event.getBookingTime();
		String content = event.getContent();
		logger.info("userId {}, bookingTime {}, content {}", userId, DateUtils.formatDate(bookingTime, "yyyyMMdd HH:mm:ss"), content);
		if(userId == null || bookingTime == null || content == null){
			return;
		}
		User user = systemService.getUser(userId);
		if (user != null && !StringUtils.isBlank(user.getMobile()) && ValidateUtils.isMobile(user.getMobile())) {
			Date createTime = new Date(System.currentTimeMillis());
			String mobile = user.getMobile();// 发送的手机号
			String type = NotifyType.BookingDispatch.getType();// 发送的类型，参考 NotifyType
			String platform = SmsPlatformType.CL.getType();// 客户端类型，参考 SmsClientType
			String returnCode = "";// 根据 returnMsg
			String sendStatus = Sms.STATUS_SUCCESS;
			String returnMsg = "";
			try {
				SMSClient client = SMSClientFactory.getClient(SmsPlatformType.CL.getType());
				returnMsg = client.sendSMS(user.getMobile(), content);
			} catch (Exception e) {
				e.printStackTrace();
				sendStatus = Sms.STATUS_EXCEPTION;
			}
			Sms sms = new Sms(mobile, type, platform, content, createTime, sendStatus, returnCode, returnMsg);
			logger.info("短信发送侦听，User {}\n, Sms {}", user, sms);
			commonService.saveSms(sms);
			logger.info("保存短信成功, Sms.id {} ", sms.getId());
		}
	}
	
}
