package com.tuisitang.common.bo;

/**    
 * @{#} ActivityStatus.java  
 * 
 * 活动状态 0 没有活动 1 参加活动 2 活动还没开始 3 活动已结束 4 不在会员等级范围内
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public enum ActivityStatus {
	NONE(0, "没有活动"), 
	IN(1, "参加活动"), 
	NOSTART(2, "活动未开始"), 
	ISOVER(3, "活动已结束"), 
	NOAUTH(4, "不在会员等级范围内");

	private int status;
	private String desc;

	ActivityStatus(int status, String desc) {
		this.status = status;
		this.desc = desc;
	}

	public int getStatus() {
		return status;
	}

	public String getDesc() {
		return desc;
	}

}
