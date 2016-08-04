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
	    url: ctx + "/cart/update",
    	beforeSend: function () {
	        $(".loading").show();
	    },
	    success: function (data) {
	    	log.info(data);
	    	if(typeof t != 'undefined' && t) {
	    		t.removeAttr('disabled');
	    	}
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
					r.find('.item_quantity').val(quantity);
					$('.total_amount').html(total);
					$('.total_price').html(totalPrice);
					$('.total_integral').html(totalIntegral);
					r.parent().parent().parent().find('.item_total_price').html(itemTotalPrice);
					r.parent().parent().parent().find('.item_saved_price').html(itemSavedPrice);
					if(quantity == 1) {
						r.find('.decrease_one').addClass('disabled');
					} else if(quantity > 1) {
						r.find('.decrease_one').removeClass('disabled');
					}
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
	
}

/**
 * Ajax Update Cart 
 * 
 * @param productId
 * @param action
 * @param quantity
 * @param r 
 */
function deleteBatch() {
	var ctx = $('#ctx').val();
	var itemId = [];
	var checkItems = $('input[name="id"]:checked');
	checkItems.each(function(){ 
		var cartId = $(this).attr('data-item-key');
    	itemId.push(cartId);
    });
	if (itemId.length == 0) {
		return;
	}
	var cartIds = itemId.join(",");
	$.ajax({
	    type: "post",
	    data: {
	    	cartIds: cartIds
    	},
	    url: ctx + "/cart/deleteBatch",
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
				$('.total_amount').html(total);
				$('.total_price').html(totalPrice);
				$('.total_integral').html(totalIntegral);
				checkItems.each(function(){ 
					var sellerTable = $(this).parents(".cart_group_item_product");
					var cartItem = sellerTable.find(".cart_item");
					if(cartItem == null || cartItem.size() == 1) {
						sellerTable.remove();
					}else {
						$(this).parent().parent().parent().remove();
					}
					var allCartItem = $('.cart_item');
					if(allCartItem == null || allCartItem.size()==0) {
						$(".content_show_wrapper").hide();
						$("#emptyCart").show();
					}
				});
	    	}
	    },
	    complete: function () {
	        $(".loading").hide();
	    },
	    error: function (data) {
	        log.error("error: " + data.responseText);
	    }
	});
	
}

/**
 * 更新购物车
 * @param o link object
 * @param action -1 decrease 1 increase
 * @param id Cart's id
 * @param productId Product's id
 * @param specId 
 */ 
function updateCart(o, action, productId, specId) {
	var _this = $(o);
	var _p = _this.parent();
	var _o = _p.find('.item_quantity');
	var productId = _p.attr('data-product-id');
	var quantity = _o.val();
	if (!/^\d*[1-9]\d*$/.test(quantity)) {
		return;
	}
	//alert('productId:' + productId + ',action:' + action + ',quantity:' + quantity);
	_this.attr('disabled', 'disabled');
	ajaxUpdate(productId, specId, action, 1, _p, _this);
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
	    url: ctx + "/cart/status",
    	beforeSend: function () {
	        $(".loading").show();
	    },
	    success: function (data) {
	    	log.info(data);
	    	if(data.s == 1) {
	    		alert(data.m);
	    	} else {
	    		var d = data.d;
	    		var totalPrice = d.totalPrice;
				var totalIntegral = d.totalIntegral;
				var total = d.total;
				$('.total_amount').html(total);
				$('.total_price').html(totalPrice);
				$('.total_integral').html(totalIntegral);
	    	}
	    },
	    complete: function () {
	        $(".loading").hide();
	    },
	    error: function (data) {
	        log.error("error: " + data.responseText);
	    }
	});
}

$(function(){
	$(".item_quantity").blur(function(){
    	var quantity = $(this).val();
    	var _p = $(this).parent();
    	if (!/^\d*[1-9]\d*$/.test(quantity)) {
    		return;
    	} else {
    		quantity = parseInt(quantity);
    		if (quantity == 0) {
    			return;
    		}
    		if (quantity > 100000) {
    			$(this).val(100000);
    		}
			
			// 检查库存 更新购物车
			var productId = _p.attr('data-product-id');
			var specId = _p.attr('data-spec-id');
			ajaxUpdate(productId, specId, 0, quantity, _p);
    	}
    });
    
	$('.delete_item').click(function(){// 删除
		var _this = $(this);
		var productId = _this.attr('data-product-id');
		var specId = _this.attr('data-spec-id');
		ajaxUpdate(productId, specId, 0, 0, _this);
	});
	
	$('.clear_cart_all').click(function(){// 清空选中商品
		deleteBatch();
	});
	
	if($('input[name="id"]:checked').length == $('input[name="id"]').length) {
		$('input[name="checkAll"]').prop("checked", true);
		$('input[name="checkSeller"]').prop("checked", true);
	} else {
		$('input[name="checkAll"]').prop("checked", false);
		$('input[name="checkSeller"]').prop("checked", false);
	}
	
	if($('input[name="id"]:checked').length == 0){
		$('#go_to_order').addClass('disabled');
	}
	
	$('input[name="id"]').click(function(){
		var _this = $(this);
		var status = this.checked;
		if($('input[name="id"]:checked').length == $('input[name="id"]').length) {
			$('input[name="checkAll"]').prop("checked", true);
		} else {
			$('input[name="checkAll"]').prop("checked", false);
		}
		if($('input[name="id"]:checked').length == 0) {
			$('#go_to_order').addClass('disabled');
		} else {
			$('#go_to_order').removeClass('disabled');
		}
		
		//商家产品选中数
		var proTable = _this.parents(".cart_group_item_product");
		var checkPro = proTable.find("input[name='id']:checked").length;
		var allPro = proTable.find("input[name='id']").length;
		if(checkPro == allPro) {
			proTable.find("input[name='checkSeller']").prop("checked", true);
		} else {
			proTable.find("input[name='checkSeller']").prop("checked", false);
		}
		
		if(status) {
			_this.parents(".cart_item").addClass("list-select");
		}else{
			_this.parents(".cart_item").removeClass("list-select");
		}
		updateStatus(_this.val(), status);
	});
	
	$('input[name="checkSeller"]').click(function(){
		var _this = $(this);
		var status = this.checked;
		
		//商家产品选中数
		var proTable = _this.parents(".cart_group_item_product");
		
		if(status) {
			proTable.find("input[name='id']").prop("checked", true);
			proTable.find(".cart_item").each(function(){
				$(this).addClass("list-select");
			});
		} else {
			proTable.find("input[name='id']").prop("checked", false);
			proTable.find(".cart_item").each(function(){
				$(this).removeClass("list-select");
			});
		}
		if($('input[name="id"]:checked').length == $('input[name="id"]').length) {
			$('input[name="checkAll"]').prop("checked", true);
		} else {
			$('input[name="checkAll"]').prop("checked", false);
		}
		if($('input[name="id"]:checked').length == 0) {
			$('#go_to_order').addClass('disabled');
		} else {
			$('#go_to_order').removeClass('disabled');
		}
		var ids = '';
		proTable.find('input[name="id"]').each(function(){
			ids += $(this).val() + ",";
		});
		updateStatus(ids, status);
	});
	
	$('input[name="checkAll"]').click(function(){
		var status = this.checked;
		$('input[name="id"]').prop("checked", status);
		$('input[name="checkSeller"]').prop("checked", status);
		$('input[name="checkAll"]').prop("checked", status);
		var ids = '';
		$('input[name="id"]').each(function(){
			ids += $(this).val() + ",";
		});
		if(!status) {
			$('#go_to_order').addClass('disabled');
		} else {
			$('#go_to_order').removeClass('disabled');
		}
		updateStatus(ids, status);
	});
	
	$('#go_to_order').click(function(){
		if($(this).hasClass('disabled')) {
			return;
		}
		var l = $('input[name="id"]:checked');
		if(l.length == 0) {
			return;
		}
		var form = document.getElementById('submitForm');
		form.submit();
	});
});

//增加购买商品数
function addFunc(obj,notifyCartFlg){
	var _obj = $(obj).parent().find("input[name=inputBuyNum]");
	var quantity = _obj.val();
	//console.log("_obj="+_obj+",notifyCartFlg="+notifyCartFlg+",quantity="+quantity+",pid="+_obj.attr("pid"));
	if (/^\d*[1-9]\d*$/.test(quantity)) {
		_obj.val(parseInt(quantity) + 1);
	} else {
		_obj.val(1);
	}
	if(notifyCartFlg){
		notifyCart(_obj);
	}
}
//减少购买商品数
function subFunc(obj,notifyCartFlg){
	var _obj = $(obj).parent().find("input");
	var quantity = _obj.val();
	if (/^\d*[1-9]\d*$/.test(quantity)) {
		if(quantity>1){
			_obj.val(parseInt(quantity) - 1);
		}else{
			_obj.val(1);
		}
	} else {
		_obj.val(1);
	}
	
	//console.log("notifyCartFlg="+notifyCartFlg);
	if(notifyCartFlg){
		notifyCart(_obj);
	}
}
//判断是否是正整数
function IsNum(s)
{
    if(s!=null){
        var r,re;
        re = /\d*/i; //\d表示数字,*表示匹配多个数字
        r = s.match(re);
        return (r==s)?true:false;
    }
    return false;
}

//检查库存是否超出数量
function checkStockFunc(){
	console.log($("#stock_span_id").text()+","+$("#inputBuyNum").val());
	if(parseInt($("#inputBuyNum").val())>parseInt($("#stock_span_id").text())){
		//alert("您所填写的商品数量超过库存！");
		$("#exceedSpanError").html("您所填写的商品数量超过库存！");
		$("#exceedDivError").show();
		
		//$('#addProductToCartErrorTips').tooltip('show');
		console.log("购买的商品数量大于库存数！");
		//$('#inputBuyNum').tooltip('show');
		return false;
	}
	$("#exceedSpanError").html("");
	$("#exceedDivError").hide();
	return true;
}

//通知购物车
function notifyCart(_obj){
	//var _url = "cart/notifyCart.html?currentBuyNumber="+_obj.val()+"&productID="+_obj.attr("pid")+"&date="+(new Date().getTime());
	var _url = "cart/notifyCart.html?currentBuyNumber="+_obj.val()+"&productID="+_obj.attr("pid")+"&radom="+Math.random();
	console.log("_url="+_url);
	$.ajax({
	  type: 'POST',
	  url: _url,
	  data: {},
	  cache:false,
	  success: function(data){
		  console.log("notifyCart.data="+data);
		  //console.log("notifyCart.data="+data+",data.amount="+data.amount+",data.code="+data.code);
		  //console.log("notifyCart.data="+data+",data.amount="+data["amount"]+",data.code="+data["code"]);
		  if(data.code=='notThisProduct'){
			  console.log("notifyCart.data.code=notThisProduct");
			  _obj.parent().find("a[name=stockErrorTips]").attr("data-original-title",data.msg).tooltip('show');
		  }else  if(data.code=='buyMore'){
			  console.log("notifyCart.data.code=buyMore");
			  _obj.parent().find("a[name=stockErrorTips]").attr("data-original-title",data.msg).tooltip('show');
		  }else  if(data.code=='ok'){
			  console.log("notifyCart.data.code=ok");
			  var _tips_obj = _obj.parent().find("a[name=stockErrorTips]");
			  _tips_obj.tooltip('hide');
			  _tips_obj.attr("data-original-title",'');
			  $("#totalPayMonery").text(data.amount);
			  $("#totalExchangeScore").text(data.amountExchangeScore);
			  
			  //console.log("_obj.parent().parent().html()="+_obj.parent().parent().html());
			  //console.log("_obj.parent().html()="+_obj.parent().html());
			  _obj.parent().parent().find("td[total0=total0]").text(data.total0);
		  }
	  },
	  dataType: "json",
	  error:function(er){
		  console.log("notifyCart.er="+er);
		  //$.each(er,function(index,value){
			//  console.log("index="+index+",value="+value);
		  //});
	  }
	});
}

//加入购物车
function addToCart(){
	var _specIdHidden = $("#specIdHidden").val();
	var specJsonStringVal = $("#specJsonString").val();
	//如果规格存在
	console.log("specIdHidden = " + _specIdHidden);
	if(specJsonStringVal && specJsonStringVal.length>0){
		if(!_specIdHidden || _specIdHidden==''){
			$("#addToCartBtn").attr("data-original-title","请选择商品规格！").tooltip('show');
			return;
		}
	}
	
	if(!checkStockFunc()){
		return false;
	}
	var _url = "cart!addToCart.action?productID="+$("#productID").val()+"&buyCount="+$("#inputBuyNum").val()+"&buySpecID="+$("#specIdHidden").val();
	$.ajax({
	  type: 'POST',
	  url: _url,
	  data: {},
	  success: function(data){
		  console.log("data="+data);
		if(data==0){
			$("#addToCartBtn").attr("data-original-title",data.tips).tooltip('destroy');
			$('#myModal').modal('toggle');
		}else{
			console.log("出现错误。data.tips="+data.tips);
			
			//$("a[name=stockErrorTips]").attr("data-original-title",data.tips).tooltip('show');
			$("#addToCartBtn").attr("data-original-title",data.tips).tooltip('show');
		}
	  },
	  dataType: "json",
	  error:function(e){
		  console.log("加入购物车失败！请联系站点管理员。异常:"+e);
		  $("#addToCartBtn").attr("data-original-title","加入购物车失败！请联系客服寻求解决办法。").tooltip('show');
	  }
	});
}

//最后一次检查库存
function checkStockLastTime(){
	var _url = "cart/checkStockLastTime.html?radom="+Math.random();
	console.log("_url="+_url);
	var result;
	$.ajax({
	  type: 'POST',
	  url: _url,
	  data: {},
	  async:false,
	  cache:false,
	  success: function(data){
		  console.log("notifyCart.data="+data);
		  
		  if(data=="-1"){
			  console.log("提示用户需要登录！");
			  $("#confirmOrderBtn").attr("data-original-title","需要先登陆，才能提交订单！").tooltip('show');
			  result = false;
			  
		  }else if(data.code=='login'){
			  console.log("notifyCart.data.code=login");
		  }else  if(data.code=='result'){
			  if(!data.list && !data.error){
				 console.log("true");
				 result = true;
			  }else{
				  $.each(data.list,function(index,value){
					  console.log("each>>"+index+","+value);
					  $("a[name=stockErrorTips]").each(function(){
						  console.log("each2>>"+value.id);
						  if($(this).attr("productid")==value.id){
							  $(this).attr("data-original-title",value.tips).tooltip('show');
						  }
					  });
				  });
				  console.log("false");
				  data.error = "按钮错误！";
				  $("#confirmOrderBtn").attr("data-original-title",data.error).tooltip('show');
				  result = false;
			  }
		  }
	  },
	  dataType: "json",
	  error:function(er){
		  console.log("notifyCart.er="+er);
		  result = false;
	  }
	});
	return result;
}