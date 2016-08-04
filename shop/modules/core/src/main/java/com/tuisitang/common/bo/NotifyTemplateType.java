package com.tuisitang.common.bo;

/**    
 * @{#} NotifyTemplateType.java  
 * 
 * 通知模板类型
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public enum NotifyTemplateType {
	Sms("sms"), // 短信通知模板
	Email("email"), // 邮件通知模板
	Inbox("inbox")// 站内信
	;

	private String type;

	NotifyTemplateType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}
