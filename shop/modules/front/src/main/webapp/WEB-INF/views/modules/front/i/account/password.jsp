<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta name="decorator" content="account_default_new" />
	<title>${accountTitle }</title>
	<content tag="local_script">
	<script type="text/javascript">
	var t;
	var wait = 60;
	$(function(){
		var _mv = $('#mobileVerify');// 手机验证码
		var _op = $('#oldPassword');// 旧密码
		var _p = $('#password');// 新密码
		var _cp = $('#confirmPwd');// 确认密码
		var _v = $('#verifyCode');// 验证码
		$('input[name="type"]:eq(0)').prop('checked', true); 
		$('input[name="type"]').change(function(){
			$('form').find('.customError').css('display', 'none');
			$('form').find('.valid').show();
			var type = $(this).val();
			if(type == 1) {
				$('#verifyWithPwd').show();
				$('#verifyWithMobile').hide();
			} else {
				$('#verifyWithPwd').hide();
				$('#verifyWithMobile').show();
			}
		});
		
		_mv.bind('focus', function(){
			_mv.parent().find('.customError').css('display', 'none');
			_mv.parent().find('.valid').show();
        }).bind("blur", function () {
            var mobileVerify = _mv.val();
            if(mobileVerify.isEmpty()) {
            	return;
            }
            if(!mobileVerify.isMobileVerify()) {
            	_mv.parent().find('.valid').hide();
            	_mv.parent().find('.customError').html('请输入6位短信校验码');
				_mv.parent().find('.customError').css('display', 'inline-block');
            }
        });
		
		_p.bind('focus', function(){
			_p.parent().find('.customError').css('display', 'none');
			_p.parent().find('.valid').show();
        }).bind("blur", function () {
            var password = _p.val();
            if(!password.isEmpty() && ($.trim(password).length < 6 || $.trim(password).length > 16 || !password.isPassword())) {
            	_p.parent().find('.valid').hide();
				_p.parent().find('.customError').html('请输入6-16 个字符，需使用字母、数字或符号组合');
				_p.parent().find('.customError').css('display', 'inline-block');
			}
        });
		
		_cp.bind('focus', function(){
			_cp.parent().find('.customError').css('display', 'none');
			_cp.parent().find('.valid').show();
        }).bind("blur", function () {
            var confirmPwd = _cp.val();
            var password = _p.val();
            if(confirmPwd != password) {
				_cp.parent().find('.customError').html('两次密码输入不一致，请重新输入');
				_cp.parent().find('.customError').css('display', 'inline-block');
				_cp.parent().find('.valid').hide();
			}
        });
		
		$('#mobileVerifyBtn').click(function(){
        	time(this);
            $.post("${ctx }/i/account/getMcode", {
                type: "3"
            }, function (data) {
                log.info(data);
                if(data.s == 1) {
                	_mv.parent().find('.valid').hide();
    				_mv.parent().find('.customError').html('获取验证码失败');
    				_mv.parent().find('.customError').css('display', 'inline-block');
                } else {
                	_mv.parent().find('.customError').css('display', 'none');
        			_mv.parent().find('.valid').show();
                }
            });
        });
		
		$('#submitBtn').click(function(){
			$('form').find('.customError').css('display', 'none');
			$('form').find('.valid').show();
			var type = $('input[name="type"]:checked').val();
			var mobileVerify = _mv.val(), oldPassword = _op.val(), password = _p.val(), confirmPwd = _cp.val(), verifyCode = _v.val();
			var b = true;
			log.info('mobileVerify=' + mobileVerify + ',oldPassword=' + oldPassword + 
					',password=' + password + ',confirmPwd=' + confirmPwd + ',verifyCode=' + verifyCode);
			if(type == 1) {// 
				if(oldPassword.isEmpty()) {
					_op.parent().find('.customError').html('请输入您的登录密码');
					_op.parent().find('.customError').css('display', 'inline-block');
					_op.parent().find('.valid').hide();
					b = false;
				}
			} else {
				if(mobileVerify.isEmpty() || !mobileVerify.isMobileVerify()) {
					_mv.parent().find('.customError').html('请输入6位短信校验码');
					_mv.parent().find('.customError').css('display', 'inline-block');
					_mv.parent().find('.valid').hide();
					b = false;
				}
			}
			if(password.isEmpty()) {
				_p.parent().find('.customError').html('密码不能为空');
				_p.parent().find('.customError').css('display', 'inline-block');
				_p.parent().find('.valid').hide();
				b = false;
			} else if(!password.isPassword()) {
				_p.parent().find('.customError').html('请输入6-16 个字符，需使用字母、数字或符号组合');
				_p.parent().find('.customError').css('display', 'inline-block');
				_p.parent().find('.valid').hide();
				b = false;
			}
			
			if(confirmPwd.isEmpty()) {
				_cp.parent().find('.customError').html('请再次输入密码');
				_cp.parent().find('.customError').css('display', 'inline-block');
				_cp.parent().find('.valid').hide();
				b = false;
			} else if(confirmPwd != password) {
				_cp.parent().find('.customError').html('两次密码输入不一致，请重新输入');
				_cp.parent().find('.customError').css('display', 'inline-block');
				_cp.parent().find('.valid').hide();
				b = false;
			}
			
			if(verifyCode.isEmpty()) {
				_v.parent().find('.customError').html('请输入验证码');
				_v.parent().find('.customError').css('display', 'inline-block');
				_v.parent().find('.valid').hide();
				b = false;
			}
			if(b) {
				$.ajax({
        		    type: "post",
        		    data: {
        		    	type: type,
        		    	mobileVerify: mobileVerify,
        		    	oldPassword: oldPassword,
        		    	password: password,
        		    	verifyCode: verifyCode
                	},
        		    url: "${ctx}/i/account/password",
                	beforeSend: function () {
        		        $(".loading").show();
        		    },
        		    success: function (data) {
        		    	log.info(data);
                        if(data.s != 0) {
                        	for(var i = 0; i < data.ms.length; i++) {
                        		var m = data.ms[i];
                        		log.info(m);
                        		for(var k in m) {
                        			log.info(k + ',' + m[k]);
                        			var o = null;
                        			if(k == 1) {
                        				o = _mv;
                        			} else if(k == 2) { 
                        				o = _op;
                        				/* _op.parent().find('.customError').html(m[k]);
                        				_op.parent().find('.customError').css('display', 'inline-block');
                        				_op.parent().find('.valid').hide(); */
                        			} else if(k == 4 || k == 8) { 
                        				o = _p;
                        				/* _p.parent().find('.customError').html(m[k]);
                        				_p.parent().find('.customError').css('display', 'inline-block');
                        				_p.parent().find('.valid').hide(); */
                        			} else {
                        				o = _v;
                        			}
                        			o.parent().find('.customError').html(m[k]);
                    				o.parent().find('.customError').css('display', 'inline-block');
                    				o.parent().find('.valid').hide();
                        		}
                        	}
                        } else {
                        	window.location.href = '${ctx}/i/account/password';
                        }
        		    },
        		    complete: function () {
        		        $(".loading").hide();
        		    },
        		    error: function (data) {
        		        log.error("error: " + data.responseText);
        		    }
        		});
			}
		});
	});
	
	function time(o) {
		var _this = $(o);
        if (wait == 0) {
            _this.attr('disabled', false);
            _this.val('获取验证码');
            wait = 60;
        } else {
            _this.attr('disabled', true);
            _this.val(wait + '秒后重新获取');
            wait--;
            t = setTimeout(function() {
                time(o);
            },
            1000);
        }
    }
	</script>
	</content>
</head>
<body style="background: white; width: 960px; margin: 0 auto;">
<!--修改密码-->
<div class="container">
	<div class="content">
		<form method="post">
			<div>
				<div>
					<div class="input_container shorter">
						<label style="margin-top:0;margin-bottom:0;"><span class="spark"> *</span><span>选择验证身份方式</span></label>
						<label style="margin-top:0;margin-bottom:0;" class="radio"><input class="radiobox" value="0" name="type" checked="checked" type="radio" style="position:static;margin:0;"><span>手机验证</span></label>
						<label style="margin-top:0;margin-bottom:0;" class="radio"><input class="radiobox" value="1" name="type" type="radio" style="position:static;margin:0;"><span>密码验证</span></label>
					</div>
				</div>
				<div id="verifyWithMobile">
					<div class="input_container shorter">
						<label for="mobile"><span class="spark">*</span><span>手机号</span></label>
						<span class="mobile_masked">
							<span>${mobile }</span>
							<span></span>
							<a href="${ctx }/i/account/mobile/modify" target="_blank" class="change_mobile">修改</a>
						</span>
					</div>
					<div class="input_container">
						<label for="mobileVerify"><span class="spark">* </span><span>短信校验码</span></label>
						<input value="" placeholder="" name="mobileVerify" class="" id="mobileVerify" autocomplete="off" type="text">
						<input type="button" value="获取短信校验码" class="btn_send_sms" id="mobileVerifyBtn" style="width:128px;background:#E50055">
						<div class="invalid validWrapper" style="display: block;">
							<span class="customError" style="display: none;">没有获取验证码</span>
							<span class="hint valid" style="margin-left:0"><span>请输入6位短信校验码</span></span>
						</div>
					</div>
				</div>
				
				<div id="verifyWithPwd" style="display: none;">
					<div class="input_container">
						<label for="oldPassword"><span class="spark">*</span><span>原密码</span></label>
						<input type="password" autocomplete="off" id="oldPassword" class="" name="oldPassword" placeholder="">
						<div style="display: block;" class="invalid validWrapper">
							<span class="customError" style="display: none;">请输入您的登录密码</span>
							<span class="hint valid"><span>请输入您的登录密码</span></span>
							<div class="clear"></div>
						</div>
					</div>
				</div>
			</div>
			<div class="input_container">
				<label for="password"><span class="spark">* </span><span>新密码</span></label>
				<input placeholder="" name="password" class="" id="password" autocomplete="off" type="password">
				<div class="invalid validWrapper" style="display: block;">
					<span class="customError" style="display: none;">密码不能为空</span>
					<span class="hint valid" style="margin-left:0"><span>6-16 个字符，需使用字母、数字或符号组合</span></span>
					<div class="clear"></div>
				</div>
			</div>
			<div class="input_container">
				<label for="confirmPwd"><span class="spark">*</span><span>重复新密码</span></label>
				<input placeholder="" name="confirmPwd" class="" id="confirmPwd" autocomplete="off" type="password">
				<div class="invalid validWrapper" style="display: block;">
					<span class="customError" style="display: none;">请再次输入密码</span>
					<span class="hint valid" style="margin-left:0"><span>请再次输入密码</span></span>
					<div class="clear"></div>
				</div>
			</div>
			<div class="input_container">
				<label for="verifyCode"><span class="spark">* </span><span>验证码</span></label>
				<input value="" placeholder="" name="verifyCode" class="" id="verifyCode" autocomplete="off" type="text">
				<!-- <a class="pic_verify_code"><img src="images/verifyCode.gif" /><span>换一张</span></a> -->
                <a class="pic_verify_code" href="javascript:" onclick="$(this).find('img').attr('src','${ctx}/servlet/validateCodeServlet?'+new Date().getTime());">
                	<img src="${ctx }/servlet/validateCodeServlet"><span>看不清</span></a>
				<div class="invalid validWrapper" style="display: block;">
					<span class="customError" style="display: none;">请输入验证码</span>
					<span class="hint valid" style="margin-left:0"><span>按右图输入验证码，不区分大小写</span></span>
					<div class="clear"></div>
				</div>
			</div>
			<div class="act">
				<input value="提交" id="submitBtn" class="formbutton" type="button" style="background:#E50055">
			</div>
		</form>
	</div>
</div>
</body>
</html>