package com.tuisitang.modules.shop.front.event;

import org.springframework.context.ApplicationEvent;

/**    
 * @{#} RegistFinishEvent.java  
 * 
 * 用户注册完成事件
 * 
 * 1. 注册完成后通知用户
 * 2. 分析HttpServletRequest中的注册信息
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class RegistFinishEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String sessionId;
	private String mobile;

	public RegistFinishEvent(Object source, String sessionId, String mobile) {
		super(source);
		this.sessionId = sessionId;
		this.mobile = mobile;
	}

}
