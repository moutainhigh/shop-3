<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>注册 - 报喜了</title>
    <link rel="stylesheet" href="${ctxStatic }/css/common.css" type="text/css" media="screen" charset="utf-8">
    <link rel="stylesheet" href="${ctxStatic }/css/regist.css" type="text/css" media="screen" charset="utf-8">
    <link rel="stylesheet" href="${ctxStatic }/css/all_sign.css" type="text/css" media="screen" charset="utf-8">
    <link rel="stylesheet" href="${ctxStatic }/css/style.css" type="text/css" media="screen" charset="utf-8">
</head>
<body style="background:white; width:960px;margin:0 auto;">
<c:if test="${fromMobile != null}">
	<h2 class="joinin">接受<span id="fromMobile">${fromMobile }</span>的邀请加入报喜了电子商务平台</h2>
</c:if>
<div id="logo">
    <a style="background:url(${ctxStatic }/images/logo.png) no-repeat;" title="" id="home" href="${ctx }/"></a>

    <div class="header_logo_box">
        <a target="_blank" class="top_link lightning" rel="nofollow" href=""></a>
        <a target="_blank" class="top_link gild" rel="nofollow" href=""></a>
        <a target="_blank" class="top_link credit" rel="nofollow" href=""></a>
    </div>
</div>
<div class="sign">
    <div class="loginWrap">
        <div class="loginPic ">
            <a href="" target="_blank" class="signup_link"><img src="${ctxStatic }/images/left_regist.jpg" /></a>

            <div class="loginBord">
                <div class="loginTit">
                    <div class="tosignup">已有账号<a href="${ctx }/login">在此登录</a></div>
                    <h1><strong>用户注册</strong></h1>
                </div>
                <form id="phone" method="post">
                    <div class="line">
                        <div class="textbox_ui">
                            <input id="mobile" name="mobile" placeholder="手机号" autofocus="" autocomplete="off" type="text">
                            <div class="focus_text">请输入11位手机号码</div>
                            <div class="invalid" style="display: none;">
                                <i></i>
                                <div class="msg"></div>
                            </div>
                            <i style="display: none; left:270px;" class="valid"></i>
                        </div>
                    </div>
                    <div class="line verityWrap">
                        <div class="textbox_ui">
                            <input placeholder="验证码" id="verifyCode" name="verifyCode1" autocomplete="off" type="text">
                            <div class="focus_text">按右图填写，不区分大小写</div>
                            <div class="invalid" style="display: none;">
                                <i></i>
                                <div class="msg"></div>
                            </div>
                        </div>
                        <span id="change_verify_code">
                            <img id="validateCode" src="${ctx }/servlet/validateCodeServlet" onclick="$(this).attr('src','${ctx}/servlet/validateCodeServlet?'+new Date().getTime());">
                            <a href="javascript:" onclick="$('#validateCode').attr('src','${ctx}/servlet/validateCodeServlet?'+new Date().getTime());">看不清</a>
                        </span>
                    </div>
                    <div class="line verityWrap">
                        <div class="textbox_ui sms_verify_wrapper">
                            <input value="" id="mobileVerify" name="mobileVerify" placeholder="短信校验码" autocomplete="off" type="text">
                            <div class="focus_text">请输入6位短信校验码</div>
                            <div class="invalid" style="display: none;">
                                <i></i>
                                <div class="msg"></div>
                            </div>
                        </div>
                        <!-- <a id="mobileVerifyBtn" href="javascript:;" class="phonecode"><strong>获取短信校验码</strong></a> -->
                        <!-- <button id="mobileVerifyBtn" class="phonecode"><strong>获取短信校验码</strong></button> -->
                        <input type="button" id="mobileVerifyBtn" class="phonecode" value="获取短信校验码"/>
                    </div>
                    <div class="line">
                        <div class="textbox_ui">
                            <input placeholder="密码" autocomplete="off" type="password" id="password" name="password">
                            <div class="focus_text">
                                <p class="default">6-16个字符，建议使用字母加数字或符号组合</p>
                            </div>
                            <div class="invalid" style="display: none;">
                                <i></i>
                                <div class="msg"></div>
                            </div>
                        </div>
                    </div>
                    <div class="line">
                        <div class="textbox_ui">
                            <input placeholder="重复密码" autocomplete="off" type="password" id="confirmPwd" name="confirmPwd">
                            <div class="focus_text">请再次输入密码</div>
                            <div class="invalid" style="display: none;">
                                <i></i>
                                <div class="msg"></div>
                            </div>
                        </div>
                    </div>
                    <div class="act" style="margin-left: 0px;">
                        <p>
                            <input id="registBtn" class="submit_btn" value="同意协议并注册" style="width: 100%;" type="button">
                        </p>
                        <p>
                            <a href="${ctx }/agreement" rel="nofollow" target="_blank" style="color:#ed145b;">《用户协议》</a>
                        </p>
                    </div>
                    <br>
                </form>
                <div class="shadow_l"></div>
                <div class="shadow_r"></div>
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
	<div class="loading_text">努力加载中......</div>
</div>

<script type="text/javascript" src="http://lib.sinaapp.com/js/jquery/1.9.1/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${ctxStaticJS }/js/common.js"></script>
<script type="text/javascript">
	var t;
	var wait = 60;
    $(document).ready(function(){
        var _m = $("#mobile");
        var _v = $('#verifyCode');
        var _p = $('#password');
        var _cp = $('#confirmPwd');
        var _mv = $('#mobileVerify');
        _m.bind('focus', function(){
        	_m.parent().find('.invalid').hide();
        }).bind("blur", function () {
            var mobile = _m.val();
            if(mobile.isEmpty()) {
            	return;
            }
            if(!mobile.isMobile()) {
                log.error('您输入的手机号码格式有误，需为 11 位数字格式');
                _m.parent().find('.msg').html('您输入的手机号码格式有误，需为 11 位数字格式');
                _m.parent().find('.invalid').show();
            } else {
            	$.post("${ctx }/checkMobile", {
                    mobile: mobile
                }, function (data) {
                    log.info(data);
                    if(data.s == 0) {
                    	_m.parent().find('.invalid').hide();
                    	_m.parent().find('.valid').css('display', 'inline');
                    } else {
                    	_m.parent().find('.msg').html(data.m);
                        _m.parent().find('.invalid').show();
                        _m.parent().find('.valid').css('display', 'none');
                    }
                });
            }
        });
        _v.bind('focus', function(){
        	_v.parent().find('.invalid').hide();
        }).bind("blur", function () {
            var verifyCode = _v.val();
            if(verifyCode.isEmpty()) {
            	return;
            }
            if(!verifyCode.isVerifyCode()) {
                log.error('请按右图输入验证码，不区分大小写');
                _v.parent().find('.msg').html('请按右图输入验证码，不区分大小写');
                _v.parent().find('.invalid').show();
            } else {
                $.post("${ctx }/checkVerifyCode", {
                    verifyCode: verifyCode
                }, function (data) {
                    log.info(data);
                    if(data.s == 0) {
                    	_v.parent().find('.invalid').hide();
                    } else {
                    	_v.parent().find('.msg').html(data.m);
                    	_v.parent().find('.invalid').show();
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
        _p.bind('focus', function(){
        	_p.parent().find('.invalid').hide();
        }).bind("blur", function () {
            var password = _p.val();
            if(!password.isEmpty() && password.length < 6) {
                log.error('为了您的账号安全，密码长度只能在 6-16 个字符之间');
                _p.parent().find('.msg').html('为了您的账号安全，密码长度只能在 6-16 个字符之间');
                _p.parent().find('.invalid').show();
            }
        });
        _cp.bind('focus', function(){
        	_cp.parent().find('.invalid').hide();
        }).bind("blur", function () {
            var password = _p.val();
            var confirmPwd = _cp.val();
            if(!confirmPwd.isEmpty() && password != confirmPwd) {
                log.error('两次密码输入不一致，请重新输入');
                _cp.parent().find('.msg').html('两次密码输入不一致，请重新输入');
                _cp.parent().find('.invalid').show();
            }
        });
        $('#mobileVerifyBtn').click(function(){
            var mobile = _m.val(), verifyCode = _v.val(), b = true;
            if(mobile.isEmpty() || !mobile.isMobile()) {
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
                    if(data.s != 0) {
                    	if(data.c == 1001) {
                    		_m.parent().find('.msg').html(data.m);
                            _m.parent().find('.invalid').show();
                    	}
                    }
                });
            }
        });
        
        function registHandler() {

        	var mobile = _m.val(), verifyCode = _v.val(), mobileVerify = _mv.val(), password = _p.val(), confirmPwd = _cp.val(), b = true;
        	if(mobile.isEmpty() || !mobile.isMobile()) {
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
            if(mobileVerify.isEmpty() || !mobileVerify.isMobileVerify()) {
                log.error('您输入的短信校验码格式有误，需为 6 位数字格式');
                _mv.parent().find('.msg').html('您输入的短信校验码格式有误，需为 6 位数字格式');
                _mv.parent().find('.invalid').show();
                b = false;
            }
            if(password.isEmpty() || password.length < 6 || !password.isPassword()) {
                log.error('为了您的账号安全，密码长度只能在 6-16 个字符之间');
                _p.parent().find('.msg').html('为了您的账号安全，密码长度只能在 6-16 个字符之间');
                _p.parent().find('.invalid').show();
                b = false;
            }
            if(password != confirmPwd) {
                log.error('两次密码输入不一致，请重新输入');
                _cp.parent().find('.msg').html('两次密码输入不一致，请重新输入');
                _cp.parent().find('.invalid').show();
                b = false;
            }
            if(b) {
            	$.ajax({
        		    type: "post",
        		    data: {
        		    	mobile: mobile,
                        verifyCode: verifyCode,
                        mobileVerify: mobileVerify,
                        password: password,
                        fromCode: '${fromCode}',
            			fromMobile: '${fromMobile}',
            			channel: '${channel}'
                	},
        		    url: "${ctx}/regist",
                	beforeSend: function () {
        		        $(".loading").show();
        		        $('#registBtn').attr('disabled', 'disabled');
        		        $('#registBtn').val('注册中...');
        		    },
        		    success: function (data) {
        		    	log.info(data);
                        if(data.s != 0) {
                        	if(data.c == 1001) {
                        		_m.parent().find('.msg').html(data.m);
                                _m.parent().find('.invalid').show();
                        	} if(data.c == 2002) {
                        		_mv.parent().find('.msg').html(data.m);
                                _mv.parent().find('.invalid').show();
                        	}
                        } else {
                        	var redirect = getQueryString('redirect');
                			log.info(redirect);
                			if(redirect) {
                				window.location.href = encodeURI(redirect);
                			} else {
                				window.location.href = '${ctx}';
                			}
                        }
        		    },
        		    complete: function () {
        		        $(".loading").hide();
        		        $('#registBtn').removeAttr('disabled');
        		        $('#registBtn').val('注 册');
        		    },
        		    error: function (data) {
        		        log.error("error: " + data.responseText);
        		    }
        		});
            }
        }
        
        document.onkeydown = function(e){
        	var ev = document.all ? window.event : e;
            if(ev.keyCode==13) {
            	$('#registBtn').focus();
            	registHandler();
            }
        };
        
        $('#registBtn').click(registHandler);
        
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