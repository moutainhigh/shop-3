package com.tuisitang.modules.shop.entity;

import java.io.Serializable;
import java.util.Date;

import net.jeeshop.core.dao.QueryModel;

public class PayLog extends QueryModel<PayLog> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
	private Long accountId;
	private Long orderId;
	private double amount;
	private String tradeStatus;
	private Date createTime;
	
	public PayLog(Long accountId, Long orderId, double amount, String tradeStatus) {
		super();
		this.accountId = accountId;
		this.orderId = orderId;
		this.amount = amount;
		this.tradeStatus = tradeStatus;
		this.createTime = new Date();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(String tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
