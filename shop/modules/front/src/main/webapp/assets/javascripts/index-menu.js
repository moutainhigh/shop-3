$(function() {
	var ctx = $('#ctx').val();
	var baseURL = $('#baseURL').val();
	var IMAGE_ROOT_PATH = $('#IMAGE_ROOT_PATH').val();
	var _self = window.location.href;
  	$('.menu-bottom-style a').each(function(){
		var _this = $(this);
  		if(_self == (baseURL + _this.attr('href'))) {
  			_this.addClass("menu-active");
  		}
  	});
    //团购时间倒计时
    updatetime();
    $('.menu-bottom-style').mouseenter(function(){
		var _this = $(this);
		
		_this.find('a').addClass("menu-active");
		if(_this.hasClass('daojushop')){
			$(".daoju-menu").show();
		}
  	}).mouseleave(function(){
  		var _this = $(this);
  		if(_self != (baseURL + _this.find('a').attr('href'))) {
  		  _this.find('a').removeClass("menu-active");
  		}
  		if(_this.hasClass('daojushop')){
			$(".daoju-menu").hide();
		}
  	});
    var daoju = $(".daojushop"),
    daoju_menu = $(".daoju-menu");
//    index_logo = $(".index-logo"),
//    tuangou = $(".tuangou"),
//    jifenshop = $(".jifenshop");
//    
//    index_logo.mouseenter(function(){
//    	$(".index-logo a").css("color","#E50055");
//	});
//    index_logo.mouseleave(function(){
//    	$(".index-logo a").css("color","#333");
//	});
//    
//	daoju.mouseenter(function(){
//		$(".daojushop a").css("color","#E50055");
//		daoju_menu.css("display","block");
//	});
//	daoju.mouseleave(function(){
//		$(".daojushop a").css("color","#333");
//		daoju_menu.css("display","none");
//	});
//	
	daoju_menu.mouseenter(function(){
		$('.daojushop a').addClass("menu-active");
		daoju_menu.css("display","block");
	});
	daoju_menu.mouseleave(function(){
		if(_self != (baseURL + $('.daojushop a').attr('href'))) {
  		  //_this.find('a').css('color', '');
  		  $('.daojushop a').removeClass("menu-active");
  		}
//		$('.daojushop a').css("color", "#333");
		daoju_menu.css("display","none");
	});
    
    function shopCar(main, content) {
        this.main = main;
        this.content = content;
    }
 
    shopCar.prototype.init = function () {
        var main = this.main,
            content = this.content;
        
        main.mouseenter(function() {
        	$.post(ctx + '/cart/load', {
            }, function(data){
            	if(data.s == 0) {
            		var carts = data.d.carts;
            		var totalPrice = data.d.totalPrice;
            		$('.shor-car-num').html(data.d.proNum);
            		/**
            		 * <div class="shop-car-list"><img src="image/shopcar1.jpg"><div class="shop-car-list-r"><a>袋鼠（AUSSIE）丰盈蓬松洗发水</a><p>¥288.00</p></div><div class="clear"></div></div>
            		 */
            		//shop-car-pro-list
            		$('.shop-car-pro-list').find('div').remove();
            		for(var i = 0; i < carts.length; i++) {
            			var cart = carts[i];
            			$('.shop-car-pro-list').append('<div class="shop-car-list"><a target="_blank" href="' + ctx + '/product/' + cart.productId + '"><img src="' + IMAGE_ROOT_PATH + "/" + cart.productImage + '"><div class="shop-car-list-r">' 
            					+ cart.productName + '</a><p>¥' + cart.nowPrice + ' <span> x '+ cart.quantity +'</span> ' + '</p></div><div class="clear"></div></div>');
            		}
            		$(".cart-pro-num span:eq(0)").text(data.d.proNum);
            		$(".cart-pro-num span:eq(1)").text("¥" + totalPrice);
            	}
            });
            content.css("display", "block");
        });

        main.mouseleave(function() {
            content.css("display", "none");
        });
    };
    var shopcar = new shopCar($('.shop-car'), $('.shop-car-main'));
	shopcar.init();
	
	$.post(ctx + '/cart/load', {
    }, function(data){

    	if(data.s == 0) {
    		var carts = data.d.carts;
    		$('.shor-car-num').html(data.d.proNum);
    		
    		$('.ibar_cart_total_quantity').html(data.d.proNum);
    		var totalPrice = data.d.totalPrice;
    		$(".ibar_cart_total_price").text("¥" + totalPrice);
    		/**
    		 * <div class="shop-car-list"><img src="image/shopcar1.jpg"><div class="shop-car-list-r"><a>袋鼠（AUSSIE）丰盈蓬松洗发水</a><p>¥288.00</p></div><div class="clear"></div></div>
    		 */
    		//shop-car-pro-list

    		$('.shop-car-pro-list').empty();
    		$(".ibar_cart").find('.ibar_cart_group_items').empty();
    		for(var i = 0; i < carts.length; i++) {
    			var cart = carts[i];
    			$('.shop-car-pro-list').append('<div class="shop-car-list"><a target="_blank" href="' + ctx + '/product/' + cart.productId + '"><img src="' + IMAGE_ROOT_PATH +"/"+ cart.productImage + '"><div class="shop-car-list-r">' 
    					+ cart.productName + '</a><p>¥' + cart.nowPrice + '</p></div><div class="clear"></div></div>');
    		}
    		
    	}
    });
    
    $('.search').focus(function(){
    	$('.search-box').fadeIn();
    }).blur(function(){
    	$('.search-box').fadeOut();
    });
    
//    $('.search-list').mouseenter(function(){
//    	var _this = $(this);
//    	$('.search').val(_this.attr('data-key'));
//    }).click(function(){
//    	var _this = $(this);
//    	$('.search').val(_this.attr('data-key'));
//    	$('#queryForm').attr('action', ctx + "/product/list");
//    });
    $('.search-list').click(function(){
    	var _this = $(this);
    	$('.search').val(_this.attr('data-key'));
    });
    $(".hotwords a").click(function(){
    	var _this = $(this);
    	$('.search').val(_this.text());
    	var form = document.getElementById('queryForm');
    	form.submit();
    });
    
    $('.search-img').click(function(){
    	var form = document.getElementById('queryForm');
    	form.submit();
    });
});
	
function updatetime() {
	var showTime = $(".showTime");
	if ($(".showTime") == null || $(".showTime").size() == 0) {
		return;
	}
	$(".showTime").each(function(i) {
		//pre 预告 ing 团购中
		var type = $(this).attr("data-type");
		var endtime = new Date($(this).attr("data-settingTime")).getTime();
		var nowtime = new Date().getTime();
		var	stime = endtime - nowtime;
		var lag = stime / 1000; //当前时间和结束时间之间的秒数
		if (lag > 0) {
			var second = Math.floor(lag % 60);
			var minite = Math.floor((lag / 60) % 60);
			var hour = Math.floor((lag / 3600) % 24);
			var day = Math.floor((lag / 3600) / 24);
			$(this).html(
					day + "天" + hour + "小时" + minite + "分" + second + "秒");
		} else {
			if (type == "pre") {
				//时间到达,移除预告
				$(this).parent().parent().parent().remove();
			} else if (type == "ing") {
				//时间到达,移除预告
				$(this).html("团购已结束！");
			}
			
		}
	});
	setTimeout("updatetime()", 1000);
}

