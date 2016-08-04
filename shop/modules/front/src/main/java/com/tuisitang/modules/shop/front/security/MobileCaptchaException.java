package com.tuisitang.modules.shop.front.security;

import org.apache.shiro.authc.AuthenticationException;

/**    
 * @{#} MobileCaptchaException.java   
 * 
 * 手机验证码异常处理类
 *
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class MobileCaptchaException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public MobileCaptchaException() {
		super();
	}

	public MobileCaptchaException(String message, Throwable cause) {
		super(message, cause);
	}

	public MobileCaptchaException(String message) {
		super(message);
	}

	public MobileCaptchaException(Throwable cause) {
		super(cause);
	}

}
