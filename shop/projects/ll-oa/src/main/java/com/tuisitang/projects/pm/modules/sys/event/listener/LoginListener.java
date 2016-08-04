package com.tuisitang.projects.pm.modules.sys.event.listener;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.tuisitang.common.bo.IpInfo;
import com.tuisitang.common.utils.AddressUtils;
import com.tuisitang.common.utils.DateUtils;
import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.projects.pm.modules.sys.entity.User;
import com.tuisitang.projects.pm.modules.sys.event.LoginEvent;
import com.tuisitang.projects.pm.modules.sys.service.SystemService;

/**    
 * @{#} LoginListener.java  
 * 
 * 用户登录侦听
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@Service
public class LoginListener extends AbstractApplicationListener implements ApplicationListener<LoginEvent> {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginListener.class);
	
	@Autowired
	private SystemService systemService;

	@Override
	public void onApplicationEvent(LoginEvent event) {
		Date sysDate = new Date(System.currentTimeMillis());
		User user = event.getUser();
		Map<String, Object> requestMap = event.getRequestMap();
		String loginIp = (String) requestMap.get("Host");
		IpInfo ipInfo = AddressUtils.getIpInfo(loginIp);
		String loginArea = ipInfo == null ? "" : ipInfo.getRegion() + ipInfo.getCity() + "[" + ipInfo.getIsp() + "]";
//		String diffAreaLogin = Account.DIFF_AREA_LOGIN_NO;
//		if (!lastLoginArea.equals(user.get)) {
//			diffAreaLogin = Account.DIFF_AREA_LOGIN_YES;
//		}
		logger.info("登录成功，更新User {}, requestMap {}", user, requestMap);
		String content = "用户" + user.getName() + "(" + user.getLoginName()
				+ ")于" + DateUtils.formatDate(sysDate, "yyyy/MM/dd HH:mm:ss")
				+ "登录OA，登录IP：" + loginIp + "，登录地区：" + loginArea;
		logger.info("{}", content);
		sendEmail("OA登录提醒", "admin@baoxiliao.com", content);
	}
	
}
