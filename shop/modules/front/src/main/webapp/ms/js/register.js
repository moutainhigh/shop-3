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
