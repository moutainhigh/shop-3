package com.tuisitang.modules.shop.admin.event;

import java.util.Date;

import org.springframework.context.ApplicationEvent;


/**    
 * @{#} ProductChangeEvent.java  
 * 
 * 产品新增/修改/上架/下架事件
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class BookingOrderDispatchEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long userId;// 派发的
	private Date bookingTime;// 预约时间
	private String content;// 内容

	public BookingOrderDispatchEvent(Object source, Long userId, Date bookingTime, String content) {
		super(source);
		this.userId = userId;
		this.bookingTime = bookingTime;
		this.content = content;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(Date bookingTime) {
		this.bookingTime = bookingTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
