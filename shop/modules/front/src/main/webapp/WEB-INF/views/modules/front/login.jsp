<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>登录 - 报喜了</title>
    <link rel="stylesheet" href="${ctxStatic }/css/common.css" type="text/css" media="screen" charset="utf-8">
    <link rel="stylesheet" href="${ctxStatic }/css/all_sign.css" type="text/css" media="screen" charset="utf-8">
    <link rel="stylesheet" href="${ctxStatic }/css/login.css" type="text/css" media="screen" charset="utf-8">
    <link rel="stylesheet" href="${ctxStatic }/css/style.css" type="text/css" media="screen" charset="utf-8">
    <style type="text/css">
        .login-ul li {
            float: left;
            margin-right: 30px;
            margin-bottom: 10px;
        }

        .login-ul li input {
            vertical-align: middle;
            margin-top: -2px;
            margin-bottom: 1px;
        }

        .auto_login {
            margin-bottom: 10px;
        }
    </style>
</head>
<body style="background:white; width:960px;margin:0 auto;">
<div id="logo">
    <a style="background:url(${ctxAssets }/image/logo.png) no-repeat;" title="" id="home" href="${ctx }/"></a>

    <div class="header_logo_box">
        <a class="top_link lightning" rel="nofollow" href="#"></a>
        <a class="top_link gild" rel="nofollow" href="#"></a>
        <a class="top_link credit" rel="nofollow" href="#"></a>
    </div>
</div>
<div class="sign">
    <div class="loginWrap">
        <div class="loginPic">
            <a href="" target="_blank" class="signup_link"><img src="${ctxStatic }/images/left_login.jpg" /></a>

            <div class="loginBord">
                <div class="loginTit">
                    <div class="tosignup">还没有账号？<a href="${ctx }/regist" class="seconds">30秒注册</a></div>
                    <h1><strong>登录</strong></h1>
                </div>

                <ul class="login-ul"> 
                    <li>
                        <input name="loginType" checked="checked" type="radio" value="0"/><span>普通登录</span>
                    </li>
                    <li>
                        <input name="loginType" type="radio" value="1"/><span>手机动态密码登录</span>
                    </li>
                </ul>
                <div class="clear"></div>
                <div class="Image">
                    <div class="textbox_ui user">
                        <input placeholder="手机号" name="username" autofocus="" autocomplete="off"
                               type="text"/>
                        <div class="focus_text">请输入您的11位手机号</div>
                        <div class="invalid" style="display: none;" >
                            <i></i>
                            <div class="msg"></div>
                        </div>
                    </div>
                    <div class="textbox_ui pass">
                        <input placeholder="密码" id="password" name="password" autocomplete="off" type="password"/>
                        <div class="focus_text">请输入您的密码，您的密码可能为字母、数字或符号的组合</div>
                        <div class="invalid" style="display: none;" >
                            <i></i>
                            <div class="msg"></div>
                        </div>
                    </div>
                    <div class="verityWrap">
                        <div class="textbox_ui">
                            <input placeholder="验证码" autocomplete="off" name="verifyCode" type="text"/>
                            <div class="focus_text">按右图填写，不区分大小写</div>
                            <div class="invalid" style="display: none;" >
	                            <i></i>
	                            <div class="msg"></div>
	                        </div>
                        </div>
                        <span id="change_verify_code">
                            <img src="${ctx }/servlet/validateCodeServlet" onclick="$(this).attr('src','${ctx}/servlet/validateCodeServlet?'+new Date().getTime());"/>
                            <a href="javascript:" onclick="$(this).prev().attr('src','${ctx}/servlet/validateCodeServlet?'+new Date().getTime());">看不清</a>
                        </span>
                    </div>
                    <p class="auto_login">
                        <a href="${ctx }/forget" class="fr">忘记密码?</a>
                        <label for="auto_login">
                            <input id="auto_login" checked="checked" type="checkbox"/>
                            &nbsp;自动登录
                        </label>
                    </p>
                    <input class="loginbtn submit_btn" value="登 录" style=" display: block;width: 100%;background-color:#E50055" type="submit">

                    <div id="errorMsg"></div>
                    <!--普通-->
                </div>
                <div class="Image" style="display:none;">
                    <div class="textbox_ui user">
                        <input name="username" placeholder="已注册的手机号码" autofocus="" autocomplete="off" type="text">
                        <div class="focus_text">请输入11位手机号码</div>
                        <div class="invalid">
                            <i></i>
                            <div class="msg"></div>
                        </div>
                    </div>
                    <div style="display: block;" class="verityWrap">
                        <div class="textbox_ui">
                            <input placeholder="验证码" autocomplete="off" name="verifyCode" type="text">
                            <div class="focus_text">按右图填写，不区分大小写</div>
                            <div class="invalid">
	                            <i></i>
	                            <div class="msg"></div>
	                        </div>
                        </div>
                        <span id="change_verify_code">
                            <img src="${ctx }/servlet/validateCodeServlet" onclick="$(this).attr('src','${ctx}/servlet/validateCodeServlet?'+new Date().getTime());">
                            <a href="javascript:" onclick="$(this).prev().attr('src','${ctx}/servlet/validateCodeServlet?'+new Date().getTime());">看不清</a>
                        </span>
                    </div>
                    <div class="dynamic_pass_wrap line">
                        <div class="textbox_ui dynamic_pass pass">
                            <input id="mobileVerify" placeholder="动态密码" autocomplete="off" type="text"> 

                            <div class="focus_text">请点击获取手机动态密码</div>
                            <div class="hint"></div>
                        </div>
                        <input type="" value="获取短信校验码" class="phonecode" id="mobileVerifyBtn">
                    </div>
                    <p class="auto_login">
                        <label>
                            <input checked="checked" type="checkbox">
                            &nbsp;自动登录
                        </label>
                    </p>
                    <input class="loginbtn submit_btn" value="登 录" style="display: block;width: 100%;background-color:#E50055" type="submit"> 

                </div>
                <!--登录-->

            </div>
        </div>
    </div>
</div>
<div class="clear"></div>
<div id="footerArea" style="background:white;">
    <div class="copyRight">Copyright 2015-2015 成都雷立风行电子商务有限公司  保留一切权利。蜀ICP备15020915号 客服热线：400-6883-520</div>
</div>

<!--loading-->
<div class="loading">
	<div align="center"><img src="${ctxStatic }/images/loading.gif"></div>
	<div class="loading_text">登录中......</div>
</div>

<script type="text/javascript" src="http://lib.sinaapp.com/js/jquery/1.9.1/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${ctxStaticJS }/js/common.js"></script>
<script type="text/javascript">
	var t;
	var wait = 60;
    $(document).ready(function(){
    	$('input[name="loginType"]:eq(0)').prop("checked", true);
        var _p = $('#password');
        var _mv = $('#mobileVerify');
        
        $('input[name="loginType"]').click(function(){
        	var _this = $(this);
        	$('.Image').hide();
        	$('.Image:eq(' + _this.val() + ')').show();
        });
        
        $('input[name="username"]').bind('focus', function(){
        	$(this).parent().find('.invalid').hide();
        }).bind("blur", function () {
            var mobile = $(this).val();
            if(mobile.isEmpty()) {
            	return;
            }
            if(!mobile.isMobile()) {
                log.error('请输入您的11位手机号');
                $(this).parent().find('.msg').html('请输入您的11位手机号');
                $(this).parent().find('.invalid').show();
            }
        });
        _p.bind('focus', function(){
        	$(this).parent().find('.invalid').hide();
        }).bind("blur", function () {
        });
        $('input[name="verifyCode"]').bind('focus', function(){
        	$(this).parent().find('.invalid').hide();
        }).bind("blur", function () {
        	var _this = $(this);
            var verifyCode = _this.val();
            if(verifyCode.isEmpty()) {
            	return;
            }
            if(!verifyCode.isVerifyCode()) {
                log.error('请按右图输入验证码，不区分大小写');
                _this.parent().find('.msg').html('请按右图输入验证码，不区分大小写');
                _this.parent().find('.invalid').show();
            } else {
                $.post("${ctx }/checkVerifyCode", {
                    verifyCode: verifyCode
                }, function (data) {
                    log.info(data);
                    if(data.s == 0) {
                    	_this.parent().find('.invalid').hide();
                    } else {
                    	_this.parent().find('.msg').html(data.m);
                    	_this.parent().find('.invalid').show();
                    }
                });
            }
        });
        _mv.bind('focus', function(){
        	_mv.parent().find('.invalid').hide();
        }).bind("blur", function () {
            var mobileVerify = _mv.val();
            if(!mobileVerify.isEmpty() && !mobileVerify.isMobileVerify()) {
                log.error('您输入的短信校验码格式有误，需为 6 位数字格式');
                _mv.parent().find('.msg').html('您输入的短信校验码格式有误，需为 6 位数字格式');
                _mv.parent().find('.invalid').show();
            }
        });
        $('#mobileVerifyBtn').click(function(){
            var mobile = '', loginType = $('input[name="loginType"]:checked').val(), verifyCode = '', b = true;
            var _m = $('input[name="username"]:eq(' + loginType +')');
        	var _v = $('input[name="verifyCode"]:eq(' + loginType +')');
        	mobile = _m.val();
        	verifyCode = _v.val();
        	log.info('mobile = ' + mobile + ',verifyCode = ' + verifyCode);
        	if(mobile.isEmpty()) {
                log.error('请输入您的11位手机号');
                _m.parent().find('.msg').html('请输入您的11位手机号');
                _m.parent().find('.invalid').show();
                b = false;
            } else if(!mobile.isMobile()) {
            	log.error('您输入的手机号码格式有误，需为 11 位数字格式');
                _m.parent().find('.msg').html('您输入的手机号码格式有误，需为 11 位数字格式');
                _m.parent().find('.invalid').show();
                b = false;
            }
            if(verifyCode.isEmpty() || !verifyCode.isVerifyCode()) {
                log.error('请按右图输入验证码，不区分大小写');
                _v.parent().find('.msg').html('请按右图输入验证码，不区分大小写');
                _v.parent().find('.invalid').show();
                b = false;
            }
            if(b) {
            	time(this);
                $.post("${ctx }/getMcode", {
                	mobile: mobile,
                    verifyCode: verifyCode,
                    type: "0"
                }, function (data) {
                    log.info(data);
                    if(data.s == 1) {
                    	if(data.c == 1001) {
                    		_m.parent().find('.msg').html(data.m);
                            _m.parent().find('.invalid').show();
                    	} else if(data.c == 2001) {
                    		_v.parent().find('.msg').html(data.m);
                            _v.parent().find('.invalid').show();
                    	}
                    }
                });
            }
        });
        
        function loginHandler(){
        	var mobile = '', verifyCode = '', password = _p.val(), loginType = $('input[name="loginType"]:checked').val(), mobileVerify = '', b = true;
        	log.info('password = ' + password + ',loginType = ' + loginType + ',mobileVerify = ' + mobileVerify);
        	var _m = $('input[name="username"]:eq(' + loginType +')');
        	var _v = $('input[name="verifyCode"]:eq(' + loginType +')');
        	mobile = _m.val();
            if(mobile.isEmpty()) {
                log.error('请输入您的11位手机号');
                _m.parent().find('.msg').html('请输入您的11位手机号');
                _m.parent().find('.invalid').show();
                b = false;
            } else if(!mobile.isMobile()) {
            	log.error('您输入的手机号码格式有误，需为 11 位数字格式');
                _m.parent().find('.msg').html('您输入的手机号码格式有误，需为 11 位数字格式');
                _m.parent().find('.invalid').show();
                b = false;
            }
            if('1' == loginType){
            	verifyCode = _v.val();
            	mobileVerify = _mv.val();
            	if(verifyCode.isEmpty() || !verifyCode.isVerifyCode()) {
                    log.error('请按右图输入验证码，不区分大小写');
                    _v.parent().find('.msg').html('请按右图输入验证码，不区分大小写');
                    _v.parent().find('.invalid').show();
                    b = false;
                }
            	if(mobileVerify.isEmpty() || !mobileVerify.isMobileVerify()) {
                    log.error('您输入的短信校验码格式有误，需为 6 位数字格式');
                    _mv.parent().find('.msg').html('您输入的短信校验码格式有误，需为 6 位数字格式');
                    _mv.parent().find('.invalid').show();
                    b = false;
                }
            } else {
            	if(password.isEmpty()) {
                    log.error('请输入密码，密码可能为字母、数字或符号的组合');
                    _p.parent().find('.msg').html('请输入密码，密码可能为字母、数字或符号的组合');
                    _p.parent().find('.invalid').show();
                    b = false;
                }
            }
        	if(b) {
        		$.ajax({
        		    type: "post",
        		    //contentType: "application/json",
        		    data: {
                		username: mobile,
                		password: password,
                		loginType: loginType,
                		validateCode: verifyCode,
                		mobileVerify: mobileVerify == null ? '' : mobileVerify
                	},
        		    url: "${ctx}/ajaxLogin",
                	beforeSend: function () {
                		$('.loginbtn').attr('disabled', 'disabled');
                		$('.loginbtn').val('登录中...');
        		        $(".loading").show();
        		    },
        		    success: function (data) {
        		    	log.info(data);
                		if(data.s == 0) {
                			var redirect = getQueryString('redirect');
                			log.info(redirect);
                			if(redirect) {
                				window.location.href = encodeURI(redirect);
                			} else {
                				window.location.href = '${ctx}';
                			}
                		} else {
    						if(data.c == 2) {
    							_m.parent().find('.msg').html(data.m);
                                _m.parent().find('.invalid').show();
    						} else if(data.c == 4) {
    							_p.parent().find('.msg').html(data.m);
                                _p.parent().find('.invalid').show();
    						} else if(data.c == 16) {
    							_v.parent().find('.msg').html(data.m);
                                _v.parent().find('.invalid').show();
    						} else if(data.c == 32) {
    							_mv.parent().find('.msg').html(data.m);
                                _mv.parent().find('.invalid').show();
    						}
                		}
        		    },
        		    complete: function () {
        		        $(".loading").hide();
        		        $('.loginbtn').removeAttr('disabled');
                		$('.loginbtn').val('登 录');
        		    },
        		    error: function (data) {
        		        log.error("error: " + data.responseText);
        		    }
        		});
        		
        	}
        };
        
        document.onkeydown = function(e){
        	var ev = document.all ? window.event : e;
            if(ev.keyCode==13) {
            	$('.loginbtn').blur();
            	loginHandler();
            }
        };
        
        $('.loginbtn').click(loginHandler);
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

</body>
</html>