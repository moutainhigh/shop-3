package com.tuisitang.modules.shop.front.event;

import java.util.Date;

import org.springframework.context.ApplicationEvent;

import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.entity.Order;

/**    
 * @{#} OrderPaymentCompleteEvent.java  
 * 
 * 订单支付完成事件
 * 
 * 1. 通知用户，等待收货
 * 2. 分析HttpServletRequest-》Map中的信息
 * 3. 向后台发送订单跟踪的消息
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class OrderPaymentCompleteEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Order order;
	private Account account; 
	private Date systemTime;

	public OrderPaymentCompleteEvent(Object source, Order order, Account account, Date systemTime) {
		super(source);
		this.order = order;
		this.account = account;
		this.systemTime = systemTime;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Date getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(Date systemTime) {
		this.systemTime = systemTime;
	}

}
