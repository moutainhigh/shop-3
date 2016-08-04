package com.tuisitang.modules.shop.front.event.listener;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.entity.Sms;
import com.tuisitang.modules.shop.front.event.SmsEvent;
import com.tuisitang.modules.shop.front.service.SystemFrontService;
import com.tuisitang.modules.shop.service.CommonService;

/**    
 * @{#} SmsListener.java  
 * 
 * 短信发送侦听
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@Service
public class SmsListener extends AbstractApplicationListener implements ApplicationListener<SmsEvent> {
	
	private static final Logger logger = LoggerFactory.getLogger(SmsListener.class);
	
	@Autowired
	private SystemFrontService systemFrontService;
	@Autowired
	private CommonService commonService;

	@Override
	public void onApplicationEvent(SmsEvent event) {
		Account account = event.getAccount();
		String mobile = event.getMobile();// 发送的手机号
		String content = event.getContent();// 发送的内容
		String type = event.getType();// 发送的类型，参考 NotifyType
		String platform = event.getPlatform();// 客户端类型，参考 SmsClientType
		String returnMsg = event.getReturnMsg();// 发送短信返回的消息，根据消息进行解析
		Date createTime = event.getCreateTime();// 创建时间
		String returnCode = "";// 根据 returnMsg
		Sms sms = new Sms(mobile, type, platform, content, createTime, event.getSendStatus(), returnCode, returnMsg);
		logger.info("短信发送侦听，Account {}\n, Sms {}", account, sms);
		commonService.saveSms(sms);
		logger.info("保存短信成功, Sms.id {} ", sms.getId());
	}

}
