package com.tuisitang.modules.shop.front.security;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.tuisitang.common.bo.LoginType;

/**    
 * @{#} UsernamePasswordToken.java   
 * 
 * 用户和密码（包含验证码）令牌类
 *
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class UsernamePasswordToken extends org.apache.shiro.authc.UsernamePasswordToken {

	private static final long serialVersionUID = 1L;

	private String captcha;
	private String mobileVerify;
	private String loginType;// 登录类型
	private boolean isRegist;// 是否是新注册
	private String openid;// 微信的openid

	public String getCaptcha() {
		return captcha;
	}

	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	
	public String getMobileVerify() {
		return mobileVerify;
	}

	public void setMobileVerify(String mobileVerify) {
		this.mobileVerify = mobileVerify;
	}
	
	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	
	public boolean isRegist() {
		return isRegist;
	}

	public void setRegist(boolean isRegist) {
		this.isRegist = isRegist;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public UsernamePasswordToken() {
		super();
	}

	public UsernamePasswordToken(String username, char[] password, boolean rememberMe, String host, String loginType, String captcha, String mobileVerify, boolean isRegist, String openid) {
		super(username, password, rememberMe, host);
		this.loginType = loginType;
		this.captcha = captcha;
		this.mobileVerify = mobileVerify;
		this.isRegist = isRegist;
		this.openid = openid;
		if (LoginType.Dynamic.getType().equals(loginType)) {// 手机动态密码登录：验证手机号码，验证码和手机验证码以后，模拟密码123登录
			setPassword("123".toCharArray());
		}
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
}