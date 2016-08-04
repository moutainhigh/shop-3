package com.tuisitang.common.bo;

/**    
 * @{#} LoginType.java  
 * 
 * 登录方式
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public enum LoginType {

	Normal("0"), // 普通登录
	Dynamic("1"),// 手机动态密码登录
	Auto("2"),// 自动登录
	Wechat("3"),// 微信登录
	;
	private String type;

	LoginType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
