package com.tuisitang.common.bo;

/**    
 * @{#} OrderStatus.java  
 * 
 * 订单状态 
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public enum OrderStatus {
	INIT(0, "init", "已下单"), 
	PASS(1, "pass", "已审核"), 
	SEND(2, "send", "已发货"), 
	SIGN(3, "sign", "已签收"), 
	CANCEL(4, "cancel", "已取消"), 
	FILE(9, "file", "已归档"), 
	;

	private int status;
	private String code;
	private String label;

	OrderStatus(int status, String code, String label) {
		this.status = status;
		this.code = code;
		this.label = label;
	}

	public int getStatus() {
		return status;
	}

	public String getCode() {
		return code;
	}

	public String getLabel() {
		return label;
	}

}
