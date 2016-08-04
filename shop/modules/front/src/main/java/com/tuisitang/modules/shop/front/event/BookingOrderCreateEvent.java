package com.tuisitang.modules.shop.front.event;

import java.util.Map;

import org.springframework.context.ApplicationEvent;

import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.entity.BookingOrder;

/**    
 * @{#} BookingOrderCreateEvent.java  
 * 
 * 预订单创建事件
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class BookingOrderCreateEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Account account;// 账号
	private BookingOrder bookingOrder;// 预订单
	private Map<String, Object> requestMap;//

	public BookingOrderCreateEvent(Object source, Account account,
			BookingOrder bookingOrder, Map<String, Object> requestMap) {
		super(source);
		this.account = account;
		this.bookingOrder = bookingOrder;
		this.requestMap = requestMap;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public BookingOrder getBookingOrder() {
		return bookingOrder;
	}

	public void setBookingOrder(BookingOrder bookingOrder) {
		this.bookingOrder = bookingOrder;
	}

	public Map<String, Object> getRequestMap() {
		return requestMap;
	}

	public void setRequestMap(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}

}
