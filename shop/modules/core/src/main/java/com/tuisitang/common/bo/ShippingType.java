package com.tuisitang.common.bo;

/**    
 * @{#} ShippingType.java  
 * 
 * 快递类型
 * 
 * EMS  
 * Express
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public enum ShippingType {
	EMS("EMS", "EMS"), 
	EXPRESS("EXPRESS", "快递");

	private String type;
	private String name;

	ShippingType(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}
 
}
