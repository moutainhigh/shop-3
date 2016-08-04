/*******init start*****/
var httpUrl="http://"+location.hostname;
/*******init end*****/

/*****宽窄屏幕自适应****/
if(screen.width > 1210 && $(window).width() > 1210)
{
	$("body").addClass("full");
}
$(function(){
	$("#myVancl").mouseenter(function(){
		$(this).removeClass("active");
		$(this).addClass("hover");
	})	
	
	$("#myVancl").mouseleave(function(){
		$(this).removeClass("hover");
		$(this).addClass("active");
	})	
	
	$(".vweixinbox").mouseenter(function(){
		$(".vweixinTip").show();
	})
	
	$(".vweixinbox").mouseleave(function(){
		$(".vweixinTip").hide();
	})		
	
	
	
	$("#shoppingCarNone").mouseenter(function(){
		$(this).removeClass("active");
		$(this).addClass("hover");
	})
	
	$("#shoppingCarNone").mouseleave(function(){
		$(this).removeClass("hover");
		$(this).addClass("active");
	})
	

	$(".allSortItem").mouseenter(function(){
		$(this).toggleClass("itemSelected");
		$("#V_Category").toggleClass("mainNavHover");
		$(this).find(".subCategory").show();
	})
	
	$(".allSortItem").mouseleave(function(){
		$(this).toggleClass("itemSelected");
		$("#V_Category").toggleClass("mainNavHover");
		$(this).find(".subCategory").hide();
	})
	
})


/*收藏夹功能*/
function bookmark() {
    var c;
    var a = /^http{1}s{0,1}:\/\/([a-z0-9_\\-]+\.)+(yihaodian|1mall|111|yhd){1}\.(com|com\.cn){1}\?(.+)+$/;
    if (a.test(httpUrl)) {
        c = "&ref=favorite"
    } else {
        c = "?ref=favorite"
    }
    var d = httpUrl + c;
    if ('undefined' == typeof (document.body.style.maxHeight)) {
        d = httpUrl
    }
    try {
        if (document.all) {
            window.external.AddFavorite(d, favorite)
        } else {
            try {
                window.sidebar.addPanel(favorite, d, "")
            } catch(b) {
                alert("抱歉，您所使用的浏览器无法完成此操作。\n\n加入收藏失败，请使用Ctrl+D进行添加")
            }
        }
    } catch(b) {
        alert("抱歉，您所使用的浏览器无法完成此操作。\n\n加入收藏失败，请使用Ctrl+D进行添加")
    }
}

/*****验证****/
/**
 * 是否是邮箱
 *
 * @returns {boolean}
 */
String.prototype.isEmail = function () {
    return /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(this);
}

/**
 * 是否是有效的身份证(中国)
 *
 * @returns {boolean}
 */
String.prototype.isIdCard = function () {
    var iSum = 0;
    var info = "";
    var sId = this;

    var aCity = {
        11: "北京",
        12: "天津",
        13: "河北",
        14: "山西",
        15: "内蒙古",
        21: "辽宁",
        22: "吉林",
        23: "黑龙江",
        31: "上海",
        32: "江苏",
        33: "浙江",
        34: "安徽",
        35: "福建",
        36: "江西",
        37: "山东",
        41: "河南",
        42: "湖北",
        43: "湖南",
        44: "广东",
        45: "广西",
        46: "海南",
        50: "重庆",
        51: "四川",
        52: "贵州",
        53: "云南",
        54: "西藏",
        61: "陕西",
        62: "甘肃",
        63: "青海",
        64: "宁夏",
        65: "新疆",
        71: "台湾",
        81: "香港",
        82: "澳门",
        91: "国外"
    };

    if (!/^\d{17}(\d|x)$/i.test(sId)) {
        return false;
    }
    sId = sId.replace(/x$/i, "a");
    //非法地区
    if (aCity[parseInt(sId.substr(0, 2))] == null) {
        return false;
    }

    var sBirthday = sId.substr(6, 4) + "-" + Number(sId.substr(10, 2)) + "-" + Number(sId.substr(12, 2));

    var d = new Date(sBirthday.replace(/-/g, "/"))

    //非法生日
    if (sBirthday != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate())) {
        return false;
    }
    for (var i = 17; i >= 0; i--) {
        iSum += (Math.pow(2, i) % 11) * parseInt(sId.charAt(17 - i), 11);
    }

    if (iSum % 11 != 1) {
        return false;
    }
    return true;

}

/**
 * 是否是数字
 *
 * @param flag
 * @returns {boolean}
 */
String.prototype.isNumeric = function (flag) {
    //验证是否是数字
    if (isNaN(this)) {
        return false;
    }
    switch (flag) {
        case null:        //数字
        case "":
            return true;
        case "+":        //正数
            return /(^\+?|^\d?)\d*\.?\d+$/.test(this);
        case "-":        //负数
            return /^-\d*\.?\d+$/.test(this);
        case "i":        //整数
            return /(^-?|^\+?|\d)\d+$/.test(this);
        case "+i":        //正整数
            return /(^\d+$)|(^\+?\d+$)/.test(this);
        case "-i":        //负整数
            return /^[-]\d+$/.test(this);
        case "f":        //浮点数
            return /(^-?|^\+?|^\d?)\d*\.\d+$/.test(this);
        case "+f":        //正浮点数
            return /(^\+?|^\d?)\d*\.\d+$/.test(this);
        case "-f":        //负浮点数
            return /^[-]\d*\.\d$/.test(this);
        default:        //缺省
            return true;
    }
}

/**
 * 是否是汉字
 *
 * @returns {boolean}
 */
String.prototype.isChinese = function () {
    return /^[\u0391-\uFFE5]+$/.test(this);
}

/**
 * 是否是手机号码
 *
 * @returns {boolean}
 */
String.prototype.isMobile = function () {
    return /^((13[0-9])|(147)|(15[^4,\D])|(17[0-9])|(18[0,0-9]))\d{8}$/.test(this);
}

/**
 * 是否是空字符串
 *
 * @returns {boolean}
 */
String.prototype.isEmpty = function() {
    return (this.length === 0);
}

/**
 * 是否是密码
 * 
 * @returns
 */
String.prototype.isPassword = function() {
	return /^[a-zA-Z0-9_!@#\$%\^&\*\(\)\\\|\/\?\.\<\>'"\{\}\[\]=\-\~\,\;\:\s]+$/.test(this);
}

/**
 * 是否是手机验证码
 * 
 * @returns
 */
String.prototype.isMobileVerify = function() {
	return /^\d{6}$/.test(this);
}

/**
 * 是否是验证码
 * 
 * @returns
 */
String.prototype.isVerifyCode = function() {
	return /^\w{4}$/.test(this);
}

/****log*****/
var log = {
    debug : function(msg) {
//        if(console) {
//            console.debug(msg);
//        }
    },
    info : function(msg) {
//        if(console) {
//            console.info(msg);
//        }
    },
    warn : function(msg) {
//        if(console) {
//            console.warn(msg);
//        }
    },
    error : function(msg) {
//        if(console) {
//            console.error(msg);
//        }
    }
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