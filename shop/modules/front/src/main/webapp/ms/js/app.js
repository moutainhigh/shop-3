//var BASE_API_URL = 'http://127.0.0.1:8080/shop-api/api/';
var BASE_API_URL = 'http://test.baoxiliao.com/shop-api/api/';

/**
 * 发送验证码接口
 * 
 * @param mobile
 * @param type
 * @param callback
 */
function getMcode(mobile, type, callback) {
	// 校验mobile 和 type
	$.ajax({
		url: BASE_API_URL + 'getMcode',
		data: {
			mobile: mobile,
			type: type,
			token: mobile
		},
		dataType: "jsonp",
        jsonp: "callback",
        jsonpCallback: callback,
		type: 'POST',
		beforeSend: function(xhr) {
		},
		success: function(data) {
			
		}
	});
}

/**
 * 获得url的参数
 * 
 * @param name
 * @returns
 */
function getQueryString(name) {
   var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
   var r = window.location.search.substr(1).match(reg);
   if (r!=null) 
	   return unescape(r[2]); 
   return null;
}

function verification(word) {
    $('.warning p').text(word);
    $('.warning').addClass("am-animation-fade");
    $('.warning').css('display', "block");
    setTimeout("$('.warning').css('display', 'none')", 2000)
}