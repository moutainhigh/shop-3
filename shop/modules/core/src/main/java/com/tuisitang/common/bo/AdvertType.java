package com.tuisitang.common.bo;

/**    
 * @{#} AdvertType.java  
 * 
 * 广告类型
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public enum AdvertType {
	Regist("advert_register_page"), // 注册页面广告
	Login("advert_login_page"), // 登陆页面广告
	IndexTop("index_top"), // 首页头部广告
	;

	private String type;

	AdvertType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
