$(function(){

    $('.submit-btn button').click(function(){
        var phone = $('#phone-num').val(),
            reg = /(1[3-9]\d{9}$)/;
        // console.log('username:' + username + ',password:' + password);
        // 校验：username password
     
        if (!phone) {
            verification("手机号不能为空");
        } else if (!reg.test(phone)) {
            verification("请输入11位手机号");
        }/* else if (!password) {
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
        } */  
    });

    $('.get-code').click(function () {
        var phone = $('#phone-num').val(),
            reg = /(1[3-9]\d{9}$)/;

        if (!phone) {
            verification("手机号不能为空");
        } else if (!reg.test(phone)) {
            verification("请输入11位手机号");
        } else {
            getMcode(phone, 0);
            time = self.setInterval('SetRemainTime()', 1000);
        } 
    })
});

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

var second = 60,
    time;

function SetRemainTime () {
    console.log('123');
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
