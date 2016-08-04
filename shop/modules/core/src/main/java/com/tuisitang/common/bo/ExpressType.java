package com.tuisitang.common.bo;

/**    
 * @{#} ExpressType.java  
 * 
 * 快递类型
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public enum ExpressType {
	EMS("EMS"), //
	EXPRESS("EXPRESS");

	private String type;

	ExpressType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
