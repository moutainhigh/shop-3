package com.tuisitang.modules.shop.admin.event;

import java.util.Map;

import net.jeeshop.core.system.bean.User;

import org.springframework.context.ApplicationEvent;

import com.tuisitang.modules.shop.entity.Product;

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
public class ProductChangeEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String ACTION_INSERT = "insert";
	public static final String ACTION_UPDATE = "update";
	public static final String ACTION_UP = "up";
	public static final String ACTION_DOWN = "down";

	private Long[] ids;// 产品ids
	private User user;// 操作人
	private String action;// insert update up down
	private Map<String, Object> requestMap;

	public ProductChangeEvent(Object source, Long[] ids, User user, String action, Map<String, Object> requestMap) {
		super(source);
		this.ids = ids;
		this.user = user;
		this.action = action;
		this.requestMap = requestMap;
	}

	public Long[] getIds() {
		return ids;
	}

	public void setIds(Long[] ids) {
		this.ids = ids;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Map<String, Object> getRequestMap() {
		return requestMap;
	}

	public void setRequestMap(Map<String, Object> requestMap) {
		this.requestMap = requestMap;
	}

}
