package com.tuisitang.modules.shop.front.event;

import org.springframework.context.ApplicationEvent;

import com.tuisitang.modules.shop.entity.Account;

/**    
 * @{#} OrderPaymentCompleteEvent.java  
 * 
 * 产品订购事件：用户将产品加入购物车时触发；
 * 
 * 1. 记录用户行为，便于以后进行分析
 * 2. 分析HttpServletRequest-》Map中的信息
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class ProductOrderEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long productId;// 产品/商品编号
	private String sessionId;//
	private Account account;// 

	public ProductOrderEvent(Object source) {
		super(source);
	}

}
