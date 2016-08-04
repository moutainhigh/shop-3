package com.tuisitang.modules.shop.entity;

import java.io.Serializable;
import java.util.Date;

import net.jeeshop.core.dao.QueryModel;

public class ProfitLog extends QueryModel<ProfitLog> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long inviteId;
	private String inviteName;
	private Long inviteeId;
	private String inviteeName;
	private Date createDate;
	private String description;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getInviteId() {
		return inviteId;
	}
	public void setInviteId(Long inviteId) {
		this.inviteId = inviteId;
	}
	public String getInviteName() {
		return inviteName;
	}
	public void setInviteName(String inviteName) {
		this.inviteName = inviteName;
	}
	public Long getInviteeId() {
		return inviteeId;
	}
	public void setInviteeId(Long inviteeId) {
		this.inviteeId = inviteeId;
	}
	public String getInviteeName() {
		return inviteeName;
	}
	public void setInviteeName(String inviteeName) {
		this.inviteeName = inviteeName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Double getProfit() {
		return profit;
	}
	public void setProfit(Double profit) {
		this.profit = profit;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	private Double profit;
}
