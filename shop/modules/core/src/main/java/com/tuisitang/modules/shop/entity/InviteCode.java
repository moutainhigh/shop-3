package com.tuisitang.modules.shop.entity;

import java.io.Serializable;
import java.util.Date;

/**    
 * @{#} InviteCode.java   
 *    
 * 邀请码 Entity  
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class InviteCode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String inviteCode;
	private Long accountId;
	private String mobile;
	private Date assignedTime;

	public InviteCode() {
		super();
	}

	public InviteCode(String inviteCode) {
		super();
		this.inviteCode = inviteCode;
	}
	
	public InviteCode(String inviteCode, Long accountId, String mobile) {
		super();
		this.inviteCode = inviteCode;
		this.accountId = accountId;
		this.mobile = mobile;
	}

	public InviteCode(String inviteCode, Long accountId, String mobile,
			Date assignedTime) {
		super();
		this.inviteCode = inviteCode;
		this.accountId = accountId;
		this.mobile = mobile;
		this.assignedTime = assignedTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getInviteCode() {
		return inviteCode;
	}

	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getAssignedTime() {
		return assignedTime;
	}

	public void setAssignedTime(Date assignedTime) {
		this.assignedTime = assignedTime;
	}

}
