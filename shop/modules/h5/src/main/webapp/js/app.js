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