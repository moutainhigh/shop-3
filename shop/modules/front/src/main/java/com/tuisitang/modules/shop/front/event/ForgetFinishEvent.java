package com.tuisitang.modules.shop.front.event;

import org.springframework.context.ApplicationEvent;

/**    
 * @{#} ForgetFinishEvent.java  
 * 
 * 找回密码完成事件
 * 
 * 1. 找回密码完成后通知用户
 * 2. 分析HttpServletRequest-》Map中的信息，评估是否是恶意操作
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class ForgetFinishEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String sessionId;
	private String mobile;

	public ForgetFinishEvent(Object source, String sessionId, String mobile) {
		super(source);
		this.sessionId = sessionId;
		this.mobile = mobile;
	}

}
