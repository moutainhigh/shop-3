package com.tuisitang.modules.shop.front.event;

import java.util.Date;

import org.springframework.context.ApplicationEvent;

import com.tuisitang.modules.shop.entity.Account;

/**    
 * @{#} LoginEvent.java  
 * 
 * 短信发送事件
 * 
 * 1. 记录短信发送信息
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class SmsEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Account account;// 账号
	private String mobile;// 发送的手机号
	private String content;// 发送的内容
	private String type;// 发送的类型，参考 NotifyType
	private String platform;// 客户端类型，参考 SmsClientType
	private String sendStatus;// 发送状态
	private String returnMsg;// 发送短信返回的消息，根据消息进行解析
	private Date createTime;// 创建时间

	public SmsEvent(Object source, Account account, String mobile,
			String content, String type, String platform, String sendStatus,
			String returnMsg, Date createTime) {
		super(source);
		this.account = account;
		this.mobile = mobile;
		this.content = content;
		this.type = type;
		this.platform = platform;
		this.sendStatus = sendStatus;
		this.returnMsg = returnMsg;
		this.createTime = createTime;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
