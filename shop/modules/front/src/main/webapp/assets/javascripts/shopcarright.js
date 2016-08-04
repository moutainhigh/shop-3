
var ctx = '';
var IMAGE_ROOT_PATH = '';

function loadCart() {
	$.post(
			ctx + '/cart/load',
			{},
			function(data) {
				if (data.s == 0) {
					var carts = data.d.carts;
					$('.shor-car-num').html(
							data.d.proNum);

					$('.ibar_cart_total_quantity')
							.html(data.d.proNum);
					var totalPrice = data.d.totalPrice;
					$(".ibar_cart_total_price")
							.text("¥" + totalPrice);
					$(".ibar_cart").find('.ibar_cart_group_items').empty();
					for ( var i = 0; i < carts.length; i++) {
						var cart = carts[i];
						var html = '<li class="ibar_cart_item clearfix">'
								+ '<div class="ibar_cart_item_pic"> <a target="_blank" href="'
								+ ctx
								+ '/product/'
								+ cart.productId
								+ '">'
								+ '<img src="'
								+ IMAGE_ROOT_PATH + "/"
								+ cart.productImage +"-px100"
								+ '" alt="'
								+ cart.productName
								+ '"></a></div>'
								+
								'<div class="ibar_cart_item_desc"><span class="ibar_cart_item_name_wrapper">'
								+ '<a class="ibar_cart_item_name" target="_blank" title="'
								+ cart.productName
								+ '"  href="'
								+ ctx
								+ '/product/'
								+ cart.productId
								+ '">'
								+ cart.productName
								+ '</a></span>';
						// 规格信息
						var specInfo = cart.specName;
						if (specInfo != null
								&& specInfo != '') {
							var specArr = cart.specName
									.split(",");
							for ( var k = 0; k < specArr.length; k++) {
								html += ('<div class="ibar_cart_item_sku ibar_text_ellipsis"><span">'
										+ specArr[k] + '</span></div>');
							}
						}
						html += ('<div class="ibar_cart_item_price ibar_pink"><span class="unit_price">'
								+ cart.nowPrice
								+ '</span><span class="unit_plus"> x </span><span class="ibar_cart_item_count">'
								+ cart.quantity + '</span><span style="float: right;font-weight: 500"><a href="javascript:void(0);" onclick="deleteCart('+ cart.id +',this);">删除</a></span></div></div></li>');
						$(".ibar_cart").find(".ibar_cart_group_items").append(html);
					}

				}
			});
}

function loadFavorate() {
	$.post(
		ctx + '/i/favorate/load',
		{},
		function(data) {
			var favorates = data.d.favorates;
			if (data.s == 0) {
				$(".ibar_favorate").find('.ibar_cart_group_items').empty();
				for ( var i = 0; i < favorates.length; i++) {
					var favorate = favorates[i];
					var html = '<li class="ibar_cart_item clearfix">'
							+ '<div class="ibar_cart_item_pic"> <a target="_blank" href="'
							+ ctx
							+ '/product/'
							+ favorate.product.id
							+ '">'
							+ '<img src="'
							+ IMAGE_ROOT_PATH + "/"
							+ favorate.product.picture +"-px100"
							+ '" alt="'
							+ favorate.product.name
							+ '"></a></div>'
							+
							'<div class="ibar_cart_item_desc"><span class="ibar_cart_item_name_wrapper">'
							+ '<a class="ibar_cart_item_name" target="_blank" title="'
							+ favorate.product.name
							+ '"  href="'
							+ ctx
							+ '/product/'
							+ favorate.product.id
							+ '">'
							+ favorate.product.name
							+ '</a></span>'
							+ '<div class="ibar_cart_item_price ibar_pink">'
							+ '<span style="float: right;font-weight: 500"><a href="javascript:void(0);" onclick="deleteFavorate('+ favorate.id +',this);">删除</a></span>'
							+ '<div class="clear"></div>'
							+ '</div></li>';
					$(".ibar_favorate").find(".ibar_cart_group_items")
							.append(html);
				}
			}
		}
	);
}

function loadHistory() {
	$.post(
			ctx + '/i/history/load',
			{},
			function(data) {
				var historys = data.d.historys;
				if (data.s == 0) {
					$(".ibar_history").find('.ibar_cart_group_items').empty();
					for ( var i = 0; i < historys.length; i++) {
						var history = historys[i];
						var html = '<li class="ibar_cart_item clearfix">'
								+ '<div class="ibar_cart_item_pic"> <a target="_blank" href="'
								+ ctx
								+ '/product/'
								+ history.id
								+ '">'
								+ '<img src="'
								+ IMAGE_ROOT_PATH + "/"
								+ history.picture +"-px100"
								+ '" alt="'
								+ history.name
								+ '"></a></div>'
								+ '<div class="ibar_cart_item_desc"><span class="ibar_cart_item_name_wrapper">'
								+ '<a class="ibar_cart_item_name" target="_blank" title="'
								+ history.name
								+ '"  href="'
								+ ctx
								+ '/product/'
								+ history.id
								+ '">'
								+ history.name
								+ '</a></span>';
								+ '<div class="ibar_cart_item_price ibar_pink">'
								+ '<span style="float: right;font-weight: 500"><a href="javascript:;" onclick="deleteFavorate();">删除</a></span>'
								+ '<div class="clear"></div>'
								+ '</div></li>';
						$(".ibar_history").find(".ibar_cart_group_items").append(html);
					}
				}
			}
	);
}
function deleteCart(productId, obj) {
	$.ajax({
	    type: "post",
	    data: {
	    	productId: productId,
	    	action: 0,
	    	quantity: 0
    	},
	    url: ctx + "/cart/update",
    	beforeSend: function () {
	        $(".loading").show();
	    },
	    success: function (data) {
	    	log.info(data);
	    	if(data.s == 1) {
	    		alert(data.m);
	    	} else {
	    		var d = data.d;
	    		var del = d.del;
	    		var totalPrice = d.totalPrice;
				var totalIntegral = d.totalIntegral;
				var total = d.total;
				if(del) {
					//减少购物车数量
					var carNum = $(".shor-car-num");
					carNum.each(function(){
						$(this).text(total);
					});
					$(".ibar_cart_total_quantity").text(total);
					$(".ibar_cart_total_price").text('￥'+totalPrice);
					$(obj).parents(".ibar_cart_item").remove();
				} 
	    	}
	    },
	    complete: function () {
	        $(".loading").hide();
	    },
	    error: function (data) {
	        log.error("error: " + data.responseText);
	    }
	});
	$.post(
			ctx + '/cart/delete',
			{cartId: cartId},
			function(data) {
				if (data.s == 0) {
					//减少购物车数量
					var quantity = parseInt($(".ibar_cart_item_count").text());
					var carNum = $(".shor-car-num");
					carNum.each(function(){
						var num = (parseInt($(this).text())-quantity);
						$(this).text(num);
					});
					$(".ibar_cart_total_quantity").text(parseInt($(".ibar_cart_total_quantity").text()) - quantity);
					$(obj).parents(".ibar_cart_item").remove();
				} else {
					alert("删除失败");
				}
			}
	);
}

function deleteFavorate(id, obj) {
	$.post(
			ctx + '/i/favorite/delete',
			{id: id},
			function(data) {
				if (data.s == 0) {
					$(obj).parents(".ibar_cart_item").remove();
				} else {
					alert("删除失败");
				}
			}
	);
}

$(function() {
	ctx = $('#ctx').val();
	IMAGE_ROOT_PATH = $('#IMAGE_ROOT_PATH').val();
	
	$(".back-to-top").click(function() {
		$('body,html').animate({
			scrollTop : 0
		}, 600);
		return false;
	});
	
	$('#shopcart-right')
			.click(
					function() {
						if ($('.right-bar').hasClass("right-bar-open")) {
							if ($('.right-bar').hasClass("cart-open")) {
								$('.right-bar').removeClass("right-bar-open").removeClass("cart-open");
								hideBar();
							}else {
								$('.right-bar').removeClass("favorate-open").removeClass("history-open").addClass("cart-open");;
								loadCart();
								$(".ibar_cart_content").hide();
								$(".ibar_cart").show();
							}
						} else {
							$('.right-bar').addClass("right-bar-open").addClass("cart-open");
							$(".ibar_cart_content").hide();
							$(".ibar_cart").show();
							loadCart();
							showBar();
							
						}
					});
	
	$('#favorate-right').click(function() {
		if ($('.right-bar').hasClass("right-bar-open")) {
			if ($('.right-bar').hasClass("favorate-open")) {
				$('.right-bar').removeClass("right-bar-open").removeClass("favorate-open");
				hideBar();
			} else {
				$('.right-bar').removeClass("cart-open").removeClass("history-open").addClass("favorate-open");
				loadFavorate();
				$(".ibar_cart_content").hide();
				$(".ibar_favorate").show();
			}
		} else {
			 $('.right-bar').addClass("right-bar-open").addClass("favorate-open");
			 $(".ibar_cart_content").hide();
			 $(".ibar_favorate").show();
			 loadFavorate();
			 showBar();
		}
	});
	
	$('#history-right').click(function() {
		if ($('.right-bar').hasClass("right-bar-open")) {
			if ($('.right-bar').hasClass("history-open")) {
				$('.right-bar').removeClass("right-bar-open").removeClass("history-open");
				hideBar();
			} else {
				$('.right-bar').removeClass("cart-open").removeClass("favorate-open").addClass("history-open");
				loadHistory();
				$(".ibar_cart_content").hide();
				$(".ibar_history").show();
			}
		} else {
			 $('.right-bar').addClass("right-bar-open").addClass("history-open");	
			 $(".ibar_cart_content").hide();
			 $(".ibar_history").show();
			 loadHistory();
			 showBar();
		}
	});
	
	$('.ibar_closebtn').click(function() {
		$('.right-bar').removeClass("right-bar-open");
		$('.right-bar').animate({
			right : '-272px'
		}, 300);
	});
	
	$('.ibar_cart_group_items').css("height", $(window).height() - 130 + "px");
	
	$(window).resize(function() {
		$('.ibar_cart_group_items').css("height", $(window).height() - 130 + "px");
	});
	
	$('.ibar_cart_group_items').hover(function() {
		jQuery(window).scrollTop(0);
	}, function() {
		$('body').css('overflow', 'auto');
	});
	
	$(document).click(function(e) {
		if ($('.right-bar').hasClass("right-bar-open")){
			var target = e.target;
			var rightBar = $(target).parents(".right-bar");
			if (rightBar.length == 0) {
				$('.right-bar').removeClass("right-bar-open").removeClass("cart-open").removeClass("favorate-open").removeClass("history-open");
				$('.right-bar').animate({
					right : '-272px'
				}, 300);
			}
		}
	});
});

function showBar() {
	$('.right-bar').animate({
		right : '0px'
	}, 300);
}

function hideBar() {
	$('.right-bar').animate({
		right : '-272px'
	}, 300);
}
