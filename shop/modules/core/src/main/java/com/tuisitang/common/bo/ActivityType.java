package com.tuisitang.common.bo;

/**    
 * @{#} ActivityType.java  
 * 
 * 活动类型 c:促销活动；j:积分兑换；t:团购活动
 * c promotion 促销活动
 * j jifen 积分兑换
 * t groupon 团购活动
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public enum ActivityType {
	Promotion("c", "促销活动"), 
	Jifen("j", "积分兑换"),
	Groupon("t", "团购活动"),
	;

	private String type;
	private String name;

	ActivityType(String type, String name) {
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
