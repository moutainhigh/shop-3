package com.tuisitang.common.bo;


/**    
 * @{#} ErrorMsg.java  
 * 
 * 错误信息
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public enum ErrorMsg {
	NULL(0, ""), //
	MobileIsIncorrect(1001, "请输入正确的手机号"), // 非法的手机号
	MobileIsExist(1002, "该手机号已存在，如果您是该用户，请立刻<a href='login'>登录</a>"), // 手机号已存在
	
	VerifyCodeIsIncorrect(2001, "验证码错误"),
	MobileVerifyIsIncorrect(2002, "手机验证码错误"),
	
	LoginUserNamePasswordIsIncorrect(1, "用户名或密码错误"),
	
	AuthenticationUnknownAccountException(2, "你输入的登录名不存在，请核对后重新输入或<a href='regist'>注册</a>"),
	AuthenticationIncorrectCredentialsException(4, "你输入的密码错误，请核对后重新输入或<a href='forget'>找回密码</a>"),
	AuthenticationLockedAccountException(8, "账号已被锁定，请与管理员联系"),
	AuthenticationCaptchaException(16, "验证码输入错误，请重新输入"),
	AuthenticationMobileCaptchaException(32, "手机验证码输入错误，请重新输入"),
	AuthenticationException(99, "你还未授权"),
	
	ImageNotFoundException(-1, "没有上传图片"),//没有上传图片
	
	UsernameIsIncorrect(1, "应为4-16个中英文字符，不能以数字开头"), 
	BirthdayIsIncorrect(2, "请填写您的生日"), 
	
	ModifyMobileVerifyIsIncorrect(1, "请输入6位短信校验码"),
	ModifyOldPasswordIsIncorrect(2, "请输入您的登录密码"),
	ModifyNewPasswordIsEmpty(4, "密码不能为空"),
	ModifyNewPasswordIsIncorrect(8, "请输入6-16 个字符，需使用字母、数字或符号组合"),
	ModifyVerifyCodeIsEmpty(16, "请输入验证码"),
	ModifyVerifyCodeIsIncorrect(32, "按右图输入验证码，不区分大小写"),
	
	ErrorSetpException(99, ""),
	
	SMSSendError(999, "短信发送失败"),
	;

	private int errCode;
	private String errMsg;

	ErrorMsg(int errCode, String errMsg) {
		this.errCode = errCode;
		this.errMsg = errMsg;
	}

	public int getErrCode() {
		return errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

}
