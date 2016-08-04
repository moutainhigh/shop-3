package com.tuisitang.common.bo;

/**    
 * @{#} SmsClientType.java  
 * 
 * SMS客户端类型
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public enum SmsPlatformType {
	CL("0", "创蓝"), // 创蓝
	;

	private String type;
	private String name;

	SmsPlatformType(String type, String name) {
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
