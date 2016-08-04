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
		// 校验：username password
     
        if (!username) {
            /*alert("手机号不能为空！");
            return false;*/
            verification("手机号不能为空");
        } else if (!reg.test(username)) {
            /*alert("请输入正确格式的手机号码！");
            return false;*/
            verification("请输入11位手机号");
        } else if (!password) {
            /*alert("密码不能为空！");
            return false;*/
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
