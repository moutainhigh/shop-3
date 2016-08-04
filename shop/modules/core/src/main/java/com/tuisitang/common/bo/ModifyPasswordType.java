package com.tuisitang.common.bo;

/**    
 * @{#} LoginType.java  
 * 
 * 修改手机验证方式
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public enum ModifyPasswordType {

	Mobile("0"), // 手机验证修改
	Password("1")// 密码验证修改
	;
	private String type;

	ModifyPasswordType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
