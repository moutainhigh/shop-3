package com.tuisitang.common.bo;

/**    
 * @{#} NotifyType.java  
 * 
 * 通知类型
 * 
 * 以前：orget：找回密码;reg:注册;product:商品价格变动;changeEmail
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public enum NotifyType {
	Regist("0"), // 注册发送短信验证码通知 
	Login("1"), // 登录发送短信验证码通知
	Forget("2"), // 找回密码发送短信验证码通知
	ModifyPwd("3"), // 修改密码短信验证码通知
	Confirm("4"), // 修改手机号短信验证码通知
	Rebind("5"), // 绑定新手机号短信验证码通知
	Booking("6"), // 预约手机验证码通知
	BookingNewAccount("10"), // 预约新注册用户短信通知
	BookingSuccess("11"), // 预约后通知用户
	BookingDispatch("12"),// 预约单派单后通知业务员
	BookingDispatchUser("13"),// 预约单派单后通知用户
	BookingComplete("14"),// 预约单完成后通知用户
	OrderSuccess("20"),// 下单后通知用户
	OrderComplete("21"),// 订单完成后通知用户
	;

	private String type;

	NotifyType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
