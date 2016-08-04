package com.tuisitang.projects.pm.modules.sys.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tuisitang.projects.pm.modules.sys.service.AreaService;

/**    
 * @{#} SystemInitiationListener.java   
 * 
 * 系统初始化侦听
 *
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class SystemInitiationListener implements ServletContextListener {
	
	private static final Logger logger = LoggerFactory.getLogger(SystemInitiationListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("System Initiation Begin");
		ServletContext sc = sce.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
		AreaService areaService = ctx.getBean(AreaService.class);
		
		logger.info("System Initiation End");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
