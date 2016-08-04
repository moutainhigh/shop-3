 $(function () {
    $('#parameter').on('open.offcanvas.amui', function() {
      if ($(".parameter-color .parameter-list-main").height() > 118) {
            $('.more-color').show();
        } else {
            $('.more-color').hide();
        }
        if ($(".parameter-type .parameter-list-main").height() > 118) {
            $('.more-size').show();
        } else {
            $('.more-size').hide();
        }
        if ($(".parameter-type .parameter-list-main").height() > 118) {
            $('.more-type').show();
        } else {
            $('.more-type').hide();
        }
    })
    var lock = false;
    $("#addCartButton").click(function(){
    	 if (!validateCart($(this))) return;
    	 var productId = $("#productId").val();
    	 var quantity = $("#number").val();
    	 var specId = $("#specId").val();
    	 var ctx = $("#ctx").val();
    	 var _this = $(this);
    	 if (lock) return;
    	 lock = true;
    	 $.post(ctx + '/cart/add?productId=' + productId +"&quantity=" + quantity + "&specId=" + specId, function(data){
			 if(data.s == "0") {
				 var cartNum = $("#carNum").text();
				 $("#carNum").text(parseInt(cartNum)+1);
				 $('#parameter').offCanvas(_this.data('rel'));
				 verification("添加成功");
			 } else {
				 $('#parameter').offCanvas(_this.data('rel'));
				 verification(data.m);
			 }
			 lock = false;
		 });
	});
    
    $(".cart").click(function(){
    	var ctx = $("#ctx").val();
    	window.location.href = ctx + "/cart";
    });
    
    $("#number").blur(function(){
    	var val = $(this).val();
    	var minSell = $("#minSell").val();
    	if (!/^\d*[1-9]\d*$/.test(val)) {
    		if (isEmpty(minSell)) {
    			$(this).val(1);
    		} else {
    			$(this).val(minSell);
    		}
    	} else {
    		if (parseInt(val) > 99) {
    			$(this).val("99+");
    		}
    		var price = $(".product-price-big").text();
    		if (!isEmpty(minSell)) {
    			if (parseInt(val) < parseInt(minSell)) {
        			$(this).val(minSell);
        		}
    		}
    	}
    })
    
    $('.more-color').click(function () {
        if ($('.more-color').hasClass('more-open')) {
            $('.more-color').text('更多');
            $('.parameter-color-list').addClass('parameter-color-list-hide');
            $('.more-color').removeClass('more-open');
            var parameter = $('.parameter-color .parameter-list-main').find($('.parameter-select')).clone(true);
            parameter.addClass('parameter-select');
            console.log(parameter);
            $('.parameter-color .parameter-list-main').find($('.parameter-select')).remove();
            $('.parameter-color .parameter-list-main').prepend(parameter);
        } else {
            $('.more-color').text('收起');
            $('.parameter-color-list').removeClass('parameter-color-list-hide');
            $('.more-color').addClass('more-open');  
        }
    })
    $('.more-size').click(function () {
        if ($('.more-size').hasClass('more-open')) {
            $('.more-size').text('更多');
            $('.parameter-size-list').addClass('parameter-size-list-hide');
            $('.more-size').removeClass('more-open');
            var parameter = $('.parameter-size .parameter-list-main').find($('.parameter-select')).clone(true);
            parameter.addClass('parameter-select');
            console.log(parameter);
            $('.parameter-size .parameter-list-main').find($('.parameter-select')).remove();
            $('.parameter-size .parameter-list-main').prepend(parameter);
        } else {
            $('.more-size').text('收起');
            $('.parameter-size-list').removeClass('parameter-size-list-hide');
            $('.more-size').addClass('more-open');
        }
    })
    $('.more-type').click(function () {
        if ($('.more-type').hasClass('more-open')) {
            $('.more-type').text('更多');
            $('.parameter-type-list').addClass('parameter-type-list-hide');
            $('.more-type').removeClass('more-open');
            var parameter = $('.parameter-type .parameter-list-main').find($('.parameter-select')).clone(true);
            parameter.addClass('parameter-select');
            console.log(parameter);
            $('.parameter-type .parameter-list-main').find($('.parameter-select')).remove();
            $('.parameter-type .parameter-list-main').prepend(parameter);
        } else {
            $('.more-type').text('收起');
            $('.parameter-type-list').removeClass('parameter-type-list-hide');
            $('.more-type').addClass('more-open');
        }
    })
   
    $('#plus').click(function () {
    	var _obj = $("#number");
    	var quantity = _obj.val();
    	var minSell = $("#minSell").val();
    	if (/^\d*[1-9]\d*$/.test(quantity)) {
    		if (parseInt(_obj) > 100000) {
    			_obj.val(100000);
    		}
    		var newQuan = (parseInt(quantity) + 1);
    		if (newQuan <= parseInt(minSell)) {
    			newQuan = parseInt(minSell);
    		}
    		_obj.val(newQuan);
    	} else {
    		if (isEmpty(minSell)) {
    			_obj.val(1);
    		} else {
    			_obj.val(minSell);
    		}
    	}
    })
    $('#minus').click(function () {
    	var _obj = $("#number");
    	var quantity = _obj.val();
    	var price = $(".product-price .product-price-big");
    	
    	var minSell = $("#minSell").val();
    	if (/^\d*[1-9]\d*$/.test(quantity)) {
    		var q = parseInt(quantity);
    		if (isEmpty(minSell)) {
    			if(q>1){
    				var newQuan = (q - 1);
    				_obj.val(newQuan);
    			}else{
    				_obj.val(1);
    			}
    		} else {
    			if (q <= parseInt(minSell)) {
    				_obj.val(minSell);
    				verification("不能小于起拍数");
    			} else {
    				if(q>parseInt(minSell)){
    					var newQuan = (q - parseInt(1));
    					_obj.val(newQuan);
    				}else{
    					_obj.val(minSell);
    					verification("不能小于起拍数");
    				}
    			}
    		}
    	} else {
    		if (isEmpty(minSell)) {
    			_obj.val(1);
    		} else {
    			_obj.val(minSell);
    		}
    	}
    });
    
    function validateCart(o) {
    	var specId = $("#specId").val();
    	var specSize = $(".parameter-list-main").length;
    	if (specSize > 0 && isEmpty(specId)) {
    		verification("请选择规格");
    		return false;
    	}
    	if ($(".addCartButton").hasClass("addCartDisable")) {
    		$('#parameter').offCanvas(o.data('rel'));
    		return false;
    	}
    	
    	return true;
    }
});
 
