<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/mobile/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>注册</title>
		<%@include file="/WEB-INF/views/mobile/include/head.jsp" %>
		<link rel="stylesheet" type="text/css" href="${ctxMS }/css/phone-register.css">
	</head>
	<body>
    <div class="am-g">
        <div class="am-u-sm-12 title">
            <p>注册</p>
        </div>
        <c:if test="${fromMobile != null}">
		<div class="am-u-sm-12 invitation">
            <p>接受<span id="fromMobile">${fromMobile }</span>的邀请加入报喜了电子商务平台</p>
        </div>
		</c:if>
        <div class="am-u-sm-12 register-list">
            <input type="number" class="phone-num" id="phone-num" placeholder="请输入手机号">
            <input type="text" class="code" id="code" placeholder="请输入验证码">
            <div class="left-blank"></div>
            <div class="phone-img">
                <img src="${ctxMS }/imgs/phone.png">
            </div>
            <div class="key-img">
                <img src="${ctxMS }/imgs/key.png">
            </div>
            <button type="button" class="am-btn am-btn-default get-code">获取验证码</button>
        </div>
        <div class="am-u-sm-12 register-list">
            <input type="text" class="user-name" id="name" placeholder="请输入姓名">
            <input type="password" class="password" id="password" placeholder="请输入密码">
            <div class="left-blank"></div>
            <div class="phone-img">
                <img src="${ctxMS }/imgs/user.png">
            </div>
            <div class="key-img">
                <img src="${ctxMS }/imgs/password.png">
            </div>
        </div>
        <div class="am-u-sm-12 submit-btn">
            <button type="button" class="am-btn am-btn-default active">注  册</button>
        </div>
    </div>
    <div class="warning am-vertical-align">
        <p class="am-vertical-align-middle">您输入的信息有误</p>
    </div>
    <input type="hidden" id="fromCode" name="fromCode" value="${fromCode }"/>
    <input type="hidden" id="fromMobile" name="fromMobile" value="${fromMobile }"/>
    <input type="hidden" id="channel" name="channel" value="${fromCode }"/>
    <input type="hidden" id="openid" name="openid" value="${openid }">
    <script type="text/javascript">
    $(function(){
        $('.submit-btn button').click(function(){
            var phone = $('#phone-num').val(),
                reg = /(1[3-9]\d{9}$)/;
            var mobileVerify = $('#code').val();
            var password = $('#password').val();
            var name = $('#name').val();
            
            if (!phone) {
                verification("手机号不能为空");
            } else if (!reg.test(phone)) {
                verification("请输入11位手机号");
            } else if(!b) {
            	verification("请先获得验证码");
            } else if(!mobileVerify) {
            	verification("请输入验证码");
            } else if(!password) {
            	verification("请输入密码");
            } else if(!name) {
            	verification("请输入姓名");
            } else {
            	$.ajax({
        		    type: "post",
        		    data: {
        		    	mobile: phone,
                        verifyCode: '',
                        mobileVerify: mobileVerify,
                        password: password,
                        fromCode: $('#fromCode').val(),
            			fromMobile: $('#fromMobile').val(),
            			channel: $('#channel').val(),
            			openid: $('#openid').val(),
            			name: name
                	},
        		    url: "regist",
                	beforeSend: function () {
        		        $('.submit-btn button').attr('disabled', 'disabled');
        		        $('.submit-btn button').html('注册中...');
        		    },
        		    success: function (data) {
        		    	console.info(data);
                        if(data.s != 0) {
                        	if(data.c == 1001) {
                                verification(data.m);
                        	} else if(data.c == 1002) {
                        		verification('该手机号已存在');
                        	} else if(data.c == 2002) {
                        		verification(data.m);
                        	} else {
                        		verification(data.m);
                        	}
                        } else {
                        	var redirect = getQueryString('redirect');
                			if(redirect) {
                				window.location.href = encodeURI(redirect);
                			} else {
                				window.location.href = 'i/invite';
                			}
                        }
        		    },
        		    complete: function () {
        		        $('.submit-btn button').removeAttr('disabled');
        		        $('.submit-btn button').html('注 册');
        		    },
        		    error: function (data) {
        		        console.error("error: " + data.responseText);
        		    }
        		});
            }
            
        });

        $('.get-code').click(function () {
        	b = false;
            var phone = $('#phone-num').val(),
                reg = /(1[3-9]\d{9}$)/;
            if (!phone) {
                verification("手机号不能为空");
            } else if (!reg.test(phone)) {
                verification("请输入11位手机号");
            } else {
                // getMcode(phone, 0);
                $.post("getMcode", {
                	mobile: phone,
                    type: "0"
                }, function (data) {
                    if(data.s != 0) {
                    	verification(data.m);
                    } else {
                    	b = true;
                    }
                });
                time = self.setInterval('SetRemainTime()', 1000);
            } 
        });
    });

    var second = 60, time, b = false;

    function SetRemainTime() {
        $('.get-code').attr("disabled", "disabled");
        second = second - 1;
        $('.get-code').text(second + "秒之后获取");
        if (second === 0) {
            window.clearInterval(time);
            $('.get-code').text("获取验证码");
            $('.get-code').removeAttr("disabled");
            second = 60;
        }
    }
    </script>
  	</body>
</html>