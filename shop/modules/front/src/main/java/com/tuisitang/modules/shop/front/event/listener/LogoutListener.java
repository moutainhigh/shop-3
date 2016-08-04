package com.tuisitang.modules.shop.front.event.listener;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.front.event.LogoutEvent;
import com.tuisitang.modules.shop.front.service.SystemFrontService;

/**    
 * @{#} LogoutListener.java  
 * 
 * 用户登出侦听
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@Service
public class LogoutListener extends AbstractApplicationListener implements ApplicationListener<LogoutEvent> {
	
	private static final Logger logger = LoggerFactory.getLogger(LogoutListener.class);
	
	@Autowired
	private SystemFrontService systemFrontService;

	@Override
	public void onApplicationEvent(LogoutEvent event) {
		Account account = event.getAccount();
		String sessionId = event.getSessionId();
		logger.info("用户登出系统，Account {}", account);
		if (!StringUtils.isBlank(sessionId)) {
			systemFrontService.deleteSession(sessionId);
		}
	}

}
