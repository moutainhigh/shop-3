<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/mobile/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>登录</title>
		<%@include file="/WEB-INF/views/mobile/include/head.jsp" %>
		<link rel="stylesheet" type="text/css" href="${ctxMS }/css/phone-login.css">
	</head>
	<body>
    <div class="am-g body-main">
        <div class="am-u-sm-12 logo am-vertical-align">
            <img src="${ctxMS }/imgs/logo.png" class="am-vertical-align-middle">
        </div>
        <div class="am-u-sm-12 account am-vertical-align">
            <div class="am-vertical-align-middle">
                <input type="number" class="" id="username" placeholder="请输入您的账号">
                <input type="password" class="" id="password" placeholder="请输入您的密码">
                <button type="button" class="am-btn am-btn-default am-round login-btn">登录</button>
                <button type="button" class="am-btn am-btn-default am-round register-btn">注册</button>
            </div>
            
        </div>
        <div class="am-u-sm-12 foot am-vertical-align">
            <div class="am-vertical-align-middle">
                <p class="forgot-password"><a href="javascript:;">忘记密码</a></p>
                <div class="third-login">
            
                    <div class="line"></div>
                    <p>第三方登录</p>
                </div>
                <div class="login-link am-cf">
                    <div class="qq">
                        <img src="${ctxMS }/imgs/qq.png">
                    </div>
                    <div class="weibo">
                        <img src="${ctxMS }/imgs/weibo.png">
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <div class="warning am-vertical-align">
        <p class="am-vertical-align-middle">您输入的信息有误</p>
    </div>
    <input type="hidden" id="openid" name="openid" value="${openid }">
    <script type="text/javascript">
        $('html').css("height", $(window).height());
        $(function(){
        	$('html').css("height", $(window).height());
        	$('.register-btn').click(function(){
        		window.location.href = 'regist';
        	});
        	$('.login-btn').click(function(){
        		var username = $('#username').val(),
        		    password = $('#password').val(),
                    reg = /(1[3-9]\d{9}$)/;
        		console.log('username:' + username + ',password:' + password);
             
                if (!username) {
                    verification("手机号不能为空");
                } else if (!reg.test(username)) {
                    verification("请输入11位手机号");
                } else if (!password) {
                    verification("请输入密码");
                } else {
                	$.ajax({
            		    type: "post",
            		    data: {
                    		username: username,
                    		password: password,
                    		loginType: '0',
                    		validateCode: '',
                    		mobileVerify: '',
                    		openid: $('#openid').val()
                    	},
            		    url: "ajaxLogin",
                    	beforeSend: function () {
                    		$('.login-btn').attr('disabled', 'disabled');
                    		$('.login-btn').html('登录中...');
            		    },
            		    success: function (data) {
            		    	console.info(data);
                    		if(data.s == 0) {
                    			var redirect = getQueryString('redirect');
                    			if(redirect) {
                    				window.location.href = encodeURI(redirect);
                    			} else {
                    				window.location.href = 'i/invite';
                    			}
                    		} else {
                    			verification('用户名密码错误');
                    		}
            		    },
            		    complete: function () {
            		        $('.login-btn').removeAttr('disabled');
                    		$('.login-btn').html('登 录');
            		    },
            		    error: function (data) {
            		        console.error("error: " + data.responseText);
            		    }
            		});
                }	
        	});
        });
    </script>
	</body>
</html>