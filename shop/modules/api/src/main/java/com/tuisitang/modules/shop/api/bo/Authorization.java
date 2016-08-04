package com.tuisitang.modules.shop.api.bo;

public class Authorization {
	private String token;
	private String mobile;
	private String errorCode;
	private String errorMsg;
	
	public Authorization(String errorCode,
			String errorMsg) {
		super();
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	
	public Authorization(String token, String mobile, String errorCode,
			String errorMsg) {
		super();
		this.token = token;
		this.mobile = mobile;
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
