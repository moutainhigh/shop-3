package com.tuisitang.common.bo;

/**    
 * @{#} CustomerBehaviorAction.java  
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public enum CustomerBehaviorAction {
	Login("login", "客户登录"), 
	Browse("browse", "客户浏览商品"),
	Search("search", "客户查询商品"),
	Cart("cart", "客户购买商品"),
	Order("order", "客户下单"),
	Pay("pay", "客户支付"),
	;

	private String action;
	private String desc;

	CustomerBehaviorAction(String action, String desc) {
		this.action = action;
		this.desc = desc;
	}

	public String getAction() {
		return action;
	}

	public String getDesc() {
		return desc;
	}

}
