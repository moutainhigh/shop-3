$(function(){
	$('input[name="checkCart"]').click(function(){
		var _this = $(this);
		var status = this.checked;
		if($('input[name="checkCart"]:checked').length == $('input[name="checkCart"]').length) {
			$('input[name="checkAll"]').uCheck('check');
		} else {
			$('input[name="checkAll"]').uCheck('uncheck');
		}
		
		//商家产品选中数
		var proTable = _this.parents(".merchant");
		var checkPro = proTable.find("input[name='checkCart']:checked").length;
		var allPro = proTable.find("input[name='checkCart']").length;
		if(checkPro == allPro) {
			proTable.find("input[name='checkSeller']").uCheck('check');
		} else {
			proTable.find("input[name='checkSeller']").uCheck('uncheck');
		}
		
		updateStatus(_this.val(), status);
	});
	
	$('input[name="checkSeller"]').click(function(){
		var _this = $(this);
		var status = this.checked;
		
		//商家产品选中数
		var proTable = _this.parents(".merchant");
		
		if(status) {
			proTable.find("input[name='checkCart']").uCheck('check');
		} else {
			proTable.find("input[name='checkCart']").uCheck('uncheck');
		}
		if($('input[name="checkCart"]:checked').length == $('input[name="checkCart"]').length) {
			$('input[name="checkAll"]').uCheck('check');
		} else {
			$('input[name="checkAll"]').uCheck('uncheck');
		}
		
		var ids = '';
		proTable.find('input[name="checkCart"]').each(function(){
			ids += $(this).val() + ",";
		});
		updateStatus(ids, status);
	});
	
	$('input[name="checkAll"]').click(function(){
		var status = this.checked;
		$('input[name="checkCart"]').prop("checked", status);
		$('input[name="checkSeller"]').prop("checked", status);
		$('input[name="checkAll"]').prop("checked", status);
		var ids = '';
		$('input[name="checkCart"]').each(function(){
			ids += $(this).val() + ",";
		});
		updateStatus(ids, status);
	});
	
	$('.btn-add').click(function () {
		var _this = $(this);
		if (_this.hasClass("disable")) {
			return;
		}
		var _p = _this.parent();
		var _o = _p.find('.number');
		var productId = _this.attr('data-product-id');
		var specId = _this.attr('data-spec-id');
		var quantity = _o.val();
		if (!/^\d*[1-9]\d*$/.test(quantity)) {
			_o.val(1);
			return;
		}
		//alert('productId:' + productId + ',action:' + action + ',quantity:' + quantity);
		ajaxUpdate(productId, specId, 1, 1, _p, _this);
	})
	$('.btn-del').click(function () {
		var _this = $(this);
		if (_this.hasClass("disable")) {
			return;
		}
		var _p = _this.parent();
		var _o = _p.find('.number');
		var productId = _this.attr('data-product-id');
		var specId = _this.attr('data-spec-id');
		var quantity = _o.val();
		if (!/^\d*[1-9]\d*$/.test(quantity)) {
			_o.val(1);
			return;
		}
		//alert('productId:' + productId + ',action:' + action + ',quantity:' + quantity);
		ajaxUpdate(productId, specId, -1, 1, _p, _this);
	})
	
	$(".go-to-settlement").click(function(){
		var ctx = $('#ctx').val();
		var ids = [];
		$('input[name="checkCart"]:checked').each(function(){
			ids.push($(this).val());
		});
		
		window.location.href = ctx + "/wx/order/confirm?ids=" + ids.join(",");
	});
	
	$('.header-right p').click(function () {
		if ($(this).hasClass("header-edit")) {
			$(this).removeClass("header-edit").addClass("header-edit-complete").text("完成");
			$('.go-to-settlement').hide();
	        $('.delete-pro').show();
		} else if ($(this).hasClass("header-edit-complete")) {
			$(this).removeClass("header-edit-complete").addClass("header-edit").text("编辑");
			$('.go-to-settlement').show();
	        $('.delete-pro').hide();
		}
	})
});

/**
* Ajax Update Cart
* 
* @param productId
* @param action
* @param quantity
* @param r 
*/
function ajaxUpdate(productId, specId, action, quantity, r, t) {
	var ctx = $('#ctx').val();
	var progress = $.AMUI.progress.configure({
	    minimum: 0.08,
	    easing: 'ease',
	    positionUsing: '',
	    speed: 200,
	    trickle: true,
	    trickleRate: 0.02,
	    trickleSpeed: 800,
	    showSpinner: true,
	    barSelector: '[role="nprogress-bar"]',
	    spinnerSelector: '[role="nprogress-spinner"]',
	    parent: 'body',
	    template: '<div class="nprogress-bar" role="nprogress-bar">' +
	        '<div class="nprogress-peg"></div></div>' +
	        '<div class="nprogress-spinner" role="nprogress-spinner" style="top: 50%; right: 50%; margin-right: -9px; margin-top: -9px;">' +
	        '<div class="nprogress-spinner-icon"></div></div>'
    });
	if(typeof specId == 'undefined' || !specId) {
		specId = 0;
	}
	$.ajax({
	    type: "post",
	    data: {
	    	productId: productId,
	    	specId: specId,
	    	action: action,
	    	quantity: quantity
	   	},
		url: ctx + "/wx/cart/update",
	   	beforeSend: function () {
	   		progress.start();
		},
	    success: function (data) {
	    	if(data.s == 1) {
	    		alert(data.m);
	    	} else {
	    		var d = data.d;
	    		var del = d.del;
	    		var totalPrice = d.totalPrice;
				var totalIntegral = d.totalIntegral;
				var total = d.total;
				if(del) {
					$('.total_amount').html(total);
					$('.total_price').html(totalPrice);
					$('.total_integral').html(totalIntegral);
					var sellerTable = r.parents(".cart_group_item_product");
					var cartItem = sellerTable.find(".cart_item");
					if(cartItem == null || cartItem.size() == 1) {
						sellerTable.remove();
					}else {
						r.parent().parent().parent().remove();
					}
					var allCartItem = $('.cart_item');
					if(allCartItem == null || allCartItem.size()==0) {
						$(".content_show_wrapper").hide();
						$("#emptyCart").show();
					}
				} else {
					var quantity = d.quantity;
					var itemTotalPrice = d.itemTotalPrice;
					var itemSavedPrice = d.itemSavedPrice;
					var disableFlag = d.disableFlag;
					r.find('.number').val(quantity);
					$('.total_amount').html(total);
					r.parents(".merchant-pro-list").find('.merchant-pro-price-total').html(itemTotalPrice);
					$('.price-total span').html(totalPrice);
					
					if(disableFlag) {
						t.addClass('disable');
					}else {
						r.find(".btn-del").removeClass('disable');
					}
				}
	    	}
	    },
	    complete: function () {
	    	 progress.done();
	    },
	    error: function (data) {
	         progress.done();
	    }
	});
	
}

/**
 * 更新Status
 */ 
function updateStatus(ids, status) {
	//alert('ids:' + ids + ',status:' + status);
	var ctx = $('#ctx').val();
	$.ajax({
	    type: "post",
	    data: {
	    	ids: ids,
	    	status: status
    	},
	    url: ctx + "/wx/cart/status",
    	beforeSend: function () {
	    },
	    success: function (data) {
	    	if(data.s == 1) {
	    		alert(data.m);
	    	} else {
	    		var d = data.d;
	    		var totalPrice = d.totalPrice;
				var totalIntegral = d.totalIntegral;
				var total = d.total;
				$("#checkedNum").text(total);
				$('.price-total span').html(totalPrice);
	    	}
	    },
	    complete: function () {
	    },
	    error: function (data) {
	    }
	});
}
