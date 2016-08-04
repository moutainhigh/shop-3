package com.tuisitang.projects.pm.modules.sys.service.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**    
 * @{#} FirstTask.java  
 * 
 * 第一个任务
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@Service
public class FirstTask {

	private static final Logger logger = LoggerFactory.getLogger(FirstTask.class);

	public void execute() {
		logger.info("First Task");
	}

}
