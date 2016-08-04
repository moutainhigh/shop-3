var t;
var wait = 60;
$(function(){
	var _m = $('#mobile');
	var _v = $('#verifyCode');
	var _mv = $('#mobileVerify');
	var _p = $('#password');
	var _cp = $('#confirmPwd');
	var _ctx = $('#ctx').val();
	
	_m.bind('focus', function(){
    	_m.next().hide();
    }).bind("blur", function () {
        var mobile = _m.val();
        if(mobile.isEmpty()) {
        	return;
        }
        if(!mobile.isMobile()) {
            log.error('请输入您的11位手机号');
            _m.next().find('div').html('请输入您的11位手机号');
            _m.next().show();
        } else {
        	$.post(_ctx + "/existMobile", {
                mobile: mobile
            }, function (data) {
                log.info(data);
                if(data.s == 0) {
                	_m.next().hide();
                } else {
                	_m.next().find('div').html(data.m);
                	_m.next().show();
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
            _v.parent().find('.invalid').find('div').html('请按右图输入验证码，不区分大小写');
            _v.parent().find('.invalid').show();
        } else {
            $.post(_ctx + "/checkVerifyCode", {
                verifyCode: verifyCode
            }, function (data) { 
                log.info(data);
                if(data.s == 0) {
                	_v.parent().find('.invalid').hide();
                } else {
                	_v.parent().find('.invalid').find('div').html(data.m);
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
            _mv.parent().find('.invalid').find('div').html('您输入的短信校验码格式有误，需为 6 位数字格式');
            _mv.parent().find('.invalid').show();
        }
    });
    _p.bind('focus', function(){
    	_p.parent().find('.invalid').hide();
    }).bind("blur", function () {
        var password = _p.val();
        if(!password.isEmpty() && password.length < 6) {
            log.error('为了您的账号安全，密码长度只能在 6-16 个字符之间');
            _p.parent().find('.invalid').find('div').html('为了您的账号安全，密码长度只能在 6-16 个字符之间');
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
            _cp.parent().find('.invalid').find('div').html('两次密码输入不一致，请重新输入');
            _cp.parent().find('.invalid').show();
        }
    });
    
    $('#firstBtn').click(function(){// 验证手机和验证码
    	var mobile = _m.val(), verifyCode = _v.val(), b = true;
    	if(mobile.isEmpty() || !mobile.isMobile()) {
            log.error('请输入您的11位手机号');
            _m.next().find('div').html('请输入您的11位手机号');
            _m.next().show();
            b = false;
        }
    	if(verifyCode.isEmpty() || !verifyCode.isVerifyCode()) {
            log.error('请按右图输入验证码，不区分大小写');
            _v.parent().find('.invalid').find('div').html('请按右图输入验证码，不区分大小写');
            _v.parent().find('.invalid').show();
            b = false;
        }
    	if(b) {
    		$.post(_ctx + "/forget/checkAccount", {
            	mobile: mobile,
                verifyCode: verifyCode
            }, function (data) {
                log.info(data);
                if(data.s != 0) {
                	if(data.c == 1001 || data.c == 2) {
                		_m.next().find('div').html(data.m);
        	            _m.next().show();
                	} else if(data.c == 99) {
                		window.location.href = _ctx;
                	} else {
                		_v.parent().find('.invalid').find('div').html(data.m);
                        _v.parent().find('.invalid').show();
                	}
                } else {
                	$('#mobileLabel').html(mobile);
                	$('#firstContainer').hide();
                	$('#secondContainer').fadeIn();
                }
            });
    	}
    });
    
    $('#mobileVerifyBtn').click(function(){
        var mobile = _m.val();
       	time(this);
        $.post(_ctx + "/getMcode", {
        	mobile: mobile,
            type: "2"
        }, function (data) {
            log.info(data);
            if(data.s == 1) {
          		_mv.parent().find('.msg').html(data.m);
                _mv.parent().find('.invalid').show();
            }
        });
    });
    
	$('#secondBtn').click(function(){// 验证手机验证码
		var mobileVerify = _mv.val(), b = true;
        if(mobileVerify.isEmpty() || !mobileVerify.isMobileVerify()) {
            log.error('您输入的短信校验码格式有误，需为 6 位数字格式');
            _mv.parent().find('.invalid').find('div').html('您输入的短信校验码格式有误，需为 6 位数字格式');
            _mv.parent().find('.invalid').show();
        } else {
       		$.post(_ctx + "/forget/auth", {
               	mobile: _m.val(),
               	mobileVerify: mobileVerify
            }, function (data) {
                log.info(data);
                if(data.s != 0) {
                	if(data.c == 99) {
                		window.location.href = _ctx;
                	} else {
                		_mv.parent().find('.invalid').find('div').html(data.m);
                        _mv.parent().find('.invalid').show();
                	}
                } else {
                	$('#secondContainer').hide();
                	$('#thirdContainer').fadeIn();
                }
            });
        }
    });
	
	$('#thirdBtn').click(function(){// 验证密码和确认密码
		var password = _p.val(), confirmPwd = _cp.val(), b = true;
		if(password.isEmpty() || password.length < 6) {
            log.error('为了您的账号安全，密码长度只能在 6-16 个字符之间');
            _p.parent().find('.invalid').find('div').html('为了您的账号安全，密码长度只能在 6-16 个字符之间');
            _p.parent().find('.invalid').show();
            b = false;
        }
		if(confirmPwd.isEmpty() || password != confirmPwd) {
            log.error('两次密码输入不一致，请重新输入');
            _cp.parent().find('.invalid').find('div').html('两次密码输入不一致，请重新输入');
            _cp.parent().find('.invalid').show();
            b = false;
        }
		if(b) {
			$.post(_ctx + "/forget/reset", {
               	mobile: _m.val(),
               	password: password
            }, function (data) {
                log.info(data);
                if(data.s != 0) {
                	if(data.c == 99) {
                		window.location.href = _ctx;
                	} else {
                		_p.parent().find('.invalid').find('div').html(data.m);
                        _p.parent().find('.invalid').show();
                	}
                } else {
                	$('#thirdContainer').hide();
                	$('#fourthContainer').fadeIn();
                	var redirect = getQueryString('redirect');
            		log.info(redirect);
            		if(!redirect) {
            			redirect = _ctx;
            		}
            		waiting(redirect);
                }
            });
		}
		
    });
	
	$('.forget_prep_btn').click(function(){//上一步
		$('#secondContainer').hide();
    	$('#firstContainer').fadeIn();
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

var w = 3;
function waiting(url) {
    if (w == 0) {
    	window.location.href = url;
    } else {
        w--;
        $('#t').html(w + '秒后自动返回');
        setTimeout(function() {
        	waiting(url);
        }, 1000);
    }
}
