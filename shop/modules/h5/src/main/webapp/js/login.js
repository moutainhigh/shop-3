$(function(){
	$('html').css("height", $(window).height());
	
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
                url: BASE_API_URL + 'ajaxLogin',
                data: {
                    username: username,
                    password: password
                },
                dataType: "jsonp",
                jsonp: "callback",
                jsonpCallback:"login",
                type: 'POST',
                beforeSend: function(xhr) {
                },
                success: function(data) {
                    
                }
            });
        }	
	});
});


/**
 * 登录成功后保存用户信息，跳转
 * 
 * @param data  { s: '0', m: '', account: {}}
 * s: 0 成功 1 失败
 * m: 失败信息
 * account: 用户信息
 */
function login(data) {
	// $('.warning').css('display', "none");
	if(data.s === '1') {// 失败
        $('.warning p').text(data.m);
        $('.warning').addClass("am-animation-fade");
        $('.warning').css('display', "block");
        setTimeout("$('.warning').css('display', 'none')", 2000)
		/*alert(data.m);*/
	} else {// 成功
		alert('登录成功');
		//getMcode('18020260877', '1', 'test');
	}
}

function test(data) {
	alert(data);
}

function verification (word) {
    $('.warning p').text(word);
    $('.warning').addClass("am-animation-fade");
    $('.warning').css('display', "block");
    setTimeout("$('.warning').css('display', 'none')", 2000)
}
