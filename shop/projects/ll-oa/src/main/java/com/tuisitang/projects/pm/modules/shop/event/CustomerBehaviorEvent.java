package com.tuisitang.projects.pm.modules.shop.event;

import org.springframework.context.ApplicationEvent;

public class CustomerBehaviorEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long customerId;
	private String action;
	private String actionDate;
	private String actionDetail;

	public CustomerBehaviorEvent(Object source, Long customerId, String action,
			String actionDate, String actionDetail) {
		super(source);
		this.customerId = customerId;
		this.action = action;
		this.actionDate = actionDate;
		this.actionDetail = actionDetail;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getActionDate() {
		return actionDate;
	}

	public void setActionDate(String actionDate) {
		this.actionDate = actionDate;
	}

	public String getActionDetail() {
		return actionDetail;
	}

	public void setActionDetail(String actionDetail) {
		this.actionDetail = actionDetail;
	}

}
