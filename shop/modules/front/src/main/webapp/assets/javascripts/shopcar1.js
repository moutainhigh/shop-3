var ctx = '';
$(function() {
//    function getPayWay (node) {
//        this.node = node;
//        this.init();
//    }
    
//    getPayWay.prototype.init = function () {
//        var node = this.node;
//        node.find('.tit').click(function() {
//            node.removeClass('getopen');
//            node.children("input").prop("checked", false);
//            //$(":radio").prop("checked", false);
//            $("input[name='pay']").prop("checked", false);
//            // console.log($(this).prev())
//            console.log($(this).parent());
//            $(this).parent().addClass("getopen");
//            $(this).prev('input').prop("checked",true);
//            console.log($(this).prev('input'));
//
//        });

        // node.find('input').first().click(function() {
        //     node.addClass("getopen");
        //     $(this).attr("checked","checked");
        // })
//    };

//    var getPayWay = new getPayWay($('.getways'));
    ctx = $('#ctx').val();
    $('select').select2();
	
	$('select[name="provinceId"]').change(function(){
		var provinceId = $(this).val();
		var city = $(this).parents(".receiver-address").find('select[name="cityId"]');
		var cityId = city.val();
		var county = $(this).parents(".receiver-address").find('select[name="countyId"]');
		var countyId = county.val();
		if (isEmpty(provinceId) || isEmpty(cityId) || isEmpty(countyId)) {
			$(this).parents(".add-address-list").find(".add-address-list-err").show();
		} else {
			$(this).parents(".add-address-list").find(".add-address-list-err").hide();
		}
		$.ajax({
		    type: "post",
		    data: {
		    	provinceId: provinceId
        	},
		    url: ctx + "/common/findCities",
        	beforeSend: function () {
		        $(".loading").show();
		    },
		    success: function (data) {
		    	city.find('option:gt(0)').remove();
		    	county.find('option:gt(0)').remove();
		    	for(var i = 0; i < data.length; i++) {
		    		var a = data[i];
		    		city.append('<option value="' + a.id + '">' + a.name + '</option>');
		    	}
		    	city.val('');
		    	county.val('');
		    	city.select2();
		    	county.select2();
		    },
		    complete: function () {
		        $(".loading").hide();
		    },
		    error: function (data) {
		        log.error("error: " + data.responseText);
		    }
		});
	});
	
	$('select[name="cityId"]').change(function(){
		var cityId = $(this).val();
		var province = $(this).parents(".receiver-address").find('select[name="provinceId"]');
		var provinceId = province.val();
		var county = $(this).parents(".receiver-address").find('select[name="countyId"]');
		var countyId = county.val();
		if (isEmpty(provinceId) || isEmpty(cityId) || isEmpty(countyId)) {
			$(this).parents(".add-address-list").find(".add-address-list-err").show();
		} else {
			$(this).parents(".add-address-list").find(".add-address-list-err").hide();
		}
		$.ajax({
		    type: "post",
		    data: {
		    	cityId: cityId
        	},
		    url: ctx + "/common/findCounties",
        	beforeSend: function () {
		        $(".loading").show();
		    },
		    success: function (data) {
		    	county.find('option:gt(0)').remove();
		    	for(var i = 0; i < data.length; i++) {
		    		var a = data[i];
		    		county.append('<option value="' + a.id + '">' + a.name + '</option>');
		    	}
		    	county.val('');
		    	county.select2();
		    },
		    complete: function () {
		        $(".loading").hide();
		    },
		    error: function (data) {
		        log.error("error: " + data.responseText);
		    }
		});
	});
	
	$('select[name="countyId"]').change(function(){
		var countyId = $(this).val();
		var city = $(this).parents(".receiver-address").find('select[name="cityId"]');
		var cityId = city.val();
		var province = $(this).parents(".receiver-address").find('select[name="provinceId"]');
		var provinceId = province.val();
		if (isEmpty(provinceId) || isEmpty(cityId) || isEmpty(countyId)) {
			$(this).parents(".add-address-list").find(".add-address-list-err").show();
		} else {
			$(this).parents(".add-address-list").find(".add-address-list-err").hide();
		}
	});
	
	$('input[name="prefer_delivery_day"]').click(function(){
		$('input[name="prefer_delivery_day"]').parent().removeClass('selected');
		$(this).parent().addClass('selected');
	});
	
	$('input[name="express"]').click(function(){
		var _this = $(this);
		var totalPrice = $('#totalPrice').val();
		$('#expressFee').text(_this.val());
		$('.total-price').find('em').html(Number(totalPrice) + Number(_this.val()));
		$('#cart_total').html(Number(totalPrice) + Number(_this.val()));
		$('#expressCode').val(_this.attr('data-code'));
	});
	
	$('input[name="gateway"]').click(function(){
		$('#payCode').val($(this).val());
	});
	
	$('.add-new-address-btn').click(function () {
		$('.add-new-pop').css('display','block');
		$('.add-new-pop-main').css('display','block');
		
		var pop = $("#add-new-address-prop");
		pop.find("input").val("");
		var province = pop.find("select[name='provinceId']");
		var city = pop.find("select[name='cityId']");
		var country = pop.find("select[name='countryId']");
		province.val("");
		city.val("");
		country.val("");
		province.select2();
		city.select2();
		country.select2();
	});
	
	$('.cancel_btn').click(function() {
		$('.add-new-pop').css('display','none');
		$('.add-new-pop-main').css('display','none');
	});
	
	//收件人校检
	$("input[name='receiver_name']").blur(function(){
		if (isEmpty($(this).val())) {
			$(this).parents(".add-address-list").find(".add-address-list-err").show();
		} else {
			$(this).parents(".add-address-list").find(".add-address-list-err").hide();
		}
	});
	
	//详细地址
	$("input[name='addressDetail']").blur(function(){
		if (isEmpty($(this).val())) {
			$(this).parents(".add-address-list").find(".add-address-list-err").show();
		} else {
			$(this).parents(".add-address-list").find(".add-address-list-err").hide();
		}
	});
	
	//手机号
	$("input[name='mobile']").blur(function(){
		var val = $(this).val();
		if (isEmpty(val) || !/^[\d]{11}$/.test(val) || val.slice(0,1) != '1') {
			$(this).parents(".add-address-list").find(".add-address-list-err").show();
		} else {
			$(this).parents(".add-address-list").find(".add-address-list-err").hide();
		}
	});
	
	var flag = false;
	$('.submit_btn').click(function(){
		var actionType = $(this).attr("data-type");
		var receiverName = $(this).parents(".add-address-from").find("input[name='receiver_name']");
		if (isEmpty(receiverName.val())) {
			receiverName.parents(".add-address-list").find(".add-address-list-err").show();
			receiverName.focus();
			return
		}
		var province = $(this).parents(".add-address-from").find("select[name='provinceId']");
		var city = $(this).parents(".add-address-from").find("select[name='cityId']");
		var county = $(this).parents(".add-address-from").find("select[name='countyId']");
		var provinceId = province.val(),cityId = city.val(),countyId = county.val();
		var provinceName = province.find('option:selected').text(),
			cityName = city.find('option:selected').text(),
			countyName = county.find('option:selected').text();
		if (isEmpty(provinceId) || isEmpty(cityId) || isEmpty(countyId)) {
			province.parents(".add-address-list").find(".add-address-list-err").show();
			return;
		}
		
		var addressDetail = $(this).parents(".add-address-from").find("input[name='addressDetail']");
		if (isEmpty(addressDetail.val())) {
			$(this).parents(".add-address-list").find(".add-address-list-err").show();
			addressDetail.focus();
			return;
		} 
		
		var mobile =  $(this).parents(".add-address-from").find("input[name='mobile']");
		var val = mobile.val();
		if (isEmpty(val) || !/^[\d]{11}$/.test(val) || val.slice(0,1) != '1') {
			$(this).parents(".add-address-list").find(".add-address-list-err").show();
			mobile.focus();
			return;
		} 
		var telArea = $(this).parents(".add-address-from").find("input[name='telArea']");
		var telNumber = $(this).parents(".add-address-from").find("input[name='telNumber']");
		var telExt = $(this).parents(".add-address-from").find("input[name='telExt']");
		
		var id = $("#id").val();
		//防止重复点击
		if (flag) {
			return;
		}
		flag = true;
		$.ajax({
		    type: "post",
		    url: ctx + "/i/account/addresses",
		    data: {
		    	id: id,
		    	name: receiverName.val(),
		    	provinceId: provinceId,
		    	cityId: cityId,
		    	countyId: countyId,
		    	address: addressDetail.val(),
		    	mobile: mobile.val(),
		    	telArea: telArea.val(),
		    	telNumber: telNumber.val(),
		    	telExt: telExt.val()
		    },
	    	beforeSend: function () {
		        $(".loading").show();
		    },
		    success: function (data) {
		    	if(data.s == 0) {
		    		//设置addressId
		    		$('#addressId').val(data.m);
		    		if (isEmpty(id)) {
		    			var addrId = data.m;
			    		$('.address-div').removeClass('address-select');
			    		$('.address-div .btnEditAddress_new').hide();
			    		$('.address-div .btnEditAddress_del').hide();
			    		var html = '<div class="address-div address-select" data-id="' + addrId + '" onclick="selectAddr(this);">' +
						      '<div class="address-div-main" data-id="' + addrId + '">' +
						        '<span class="addr_name">' + receiverName.val() + '</span>' +
						        '<span class="addr_con">' + provinceName + '-' + cityName + '-' + countyName + ' ' + addressDetail.val() +'</span>' +
						        '<span class="addr_num">' + mobile.val() + '</span>' +
						        '<span class="btnEditAddress_new" title="修改地址" data-id="' + addrId + '"><a href="javascript:void(0);" data-id="' + addrId + '" onclick="modifyAddr(this, event);">修改</a></span>' +
						        '<span class="btnEditAddress_del" title="删除地址" data-id="' + addrId + '"><a href="javascript:void(0);" data-id="' + addrId + '" onclick="delAddr(this, event);">删除</a></span>' +
						      '</div>' +
						    '</div>';	
			    		if (actionType == '1') {
				    		$('.chose-add').prepend(html);
			    			$("#add-new-address-wrap").hide();
			    			$(".add-new-address").show();
			    		} else if (actionType=='2') {
				    		$('.chose-add').find(".address-div").first().before(html);
			    			$(".add-new-pop").hide();
				    		$(".add-new-pop-main").hide();
			    		}
		    		} else {
		    			if (actionType=='2') {
			    			$(".add-new-pop").hide();
				    		$(".add-new-pop-main").hide();
			    		}
		    		}
		    	} else {
		    		alert(data.m);
		    	}
		    },
		    complete: function () {
		        $(".loading").hide();
		        flag = false;
		    },
		    error: function (data) {
		        log.error("error: " + data.responseText);
		        flag = false;
		    }
		});
	});
});

function changeToZeroAddAddress(){
	var wrap = $("#add-new-address-wrap");
	wrap.find("input").val("");
	wrap.find(".select2").select2("val", "");
	wrap.show();
	$(".add-new-address").hide(); 
};

function getAddressCount() {
	return $(".chose-add").find(".address-div").size();
};


function modifyAddr(o, event) {
	$this = $(o);
	var addrId = $this.attr("data-id");
	var receiverName = $("#add-new-address-prop input[name='receiver_name']");
	var province = $("#add-new-address-prop select[name='provinceId']");
	var city = $("#add-new-address-prop select[name='cityId']");
	var country = $("#add-new-address-prop select[name='countyId']");
	var addressDetail = $("#add-new-address-prop input[name='addressDetail']");
	var mobile = $("#add-new-address-prop input[name='mobile']");
	var telArea = $("#add-new-address-prop input[name='telArea']");
	var telNumber = $("#add-new-address-prop input[name='telNumber']");
	var telExt = $("#add-new-address-prop input[name='telExt']");
	
	//设置地址Id
	$("#id").val(addrId);
	
	$.ajax({
	    type: "post",
	    url: ctx + "/i/account/addresses/" + addrId,
    	beforeSend: function () {
	        $(".loading").show();
	    },
	    success: function (data) {
	    	if(data.s == 0) {
	    		$("#add-new-address-prop .add-new-pop-main-title").html("修改地址");
	    		var cities = data.cities;
	    		var counties = data.counties;
	    		city.find('option:gt(0)').remove();
	    		country.find('option:gt(0)').remove();
		    	for(var i = 0; i < cities.length; i++) {
		    		var a = cities[i];
		    		city.append('<option value="' + a.id + '">' + a.name + '</option>');
		    	}
		    	for(var i = 0; i < counties.length; i++) {
		    		var a = counties[i];
		    		country.append('<option value="' + a.id + '">' + a.name + '</option>');
		    	}
		    	
	    		receiverName.val(data.address.name);
	    		province.val(data.address.provinceId);
	    		city.val(data.address.cityId);
	    		country.val(data.address.countyId);
	    		
	    		province.select2();
	    		city.select2();
	    		country.select2();
	    		addressDetail.val(data.address.address);
	    		mobile.val(data.address.mobile);
	    		var phone = data.address.phone;
	    		if (!isEmpty(phone)) {
	    			var pArr = phone.split("-");
	    			for (i=0;i<pArr.length;i++ ) {
	    				if(i==0) {
	    					telArea.val(pArr[i]);
	    				} else if (i==1){
	    					telNumber.val(pArr[i]);
	    				} else if (i==2){
	    					telExt.val(pArr[i]);
	    				}
	    			}
	    		}
	    		
	    		$('.add-new-pop').css('display','block');
	    		$('.add-new-pop-main').css('display','block');
	    	} else {
	    		alert(data.m);
	    	}
	    },
	    complete: function () {
	        $(".loading").hide();
	    },
	    error: function (data) {
	        log.error("error: " + data.m);
	    }
	});
	event.stopPropagation();
}

function delAddr(o, event) {
	$this = $(o);
	if(confirm("您确认要删除该地址吗？")){
		var addrId = $this.attr("data-id");
		if (isEmpty(addrId)) {
			return;
		}
		$.ajax({
		    type: "post",
		    url: ctx + "/i/account/addresses/delete/" + addrId,
	    	beforeSend: function () {
		        $(".loading").show();
		    },
		    success: function (data) {
		    	if(data.s == 0) {
		    		$this.parents(".address-div").remove();
		    		if (!getAddressCount()) {
		    			changeToZeroAddAddress();
		    		}
		    	} else {
		    		alert(data.m);
		    	}
		    },
		    complete: function () {
		        $(".loading").hide();
		    },
		    error: function (data) {
		        log.error("error: " + data.m);
		    }
		});
	}
	event.stopPropagation();
}

function isEmpty(str){
    if(str==''||str==null){
        return true;
    }else{
        return false;
    }
}

function selectAddr(o) {
	$this = $(o);
	$('.address-div').removeClass('address-select');
	$('.address-div .btnEditAddress_new').hide();
	$('.address-div .btnEditAddress_del').hide();
	
	$this.addClass('address-select');
	$('#addressId').val($this.attr('data-id'));
	
	$this.find('.btnEditAddress_new').show();
	$this.find('.btnEditAddress_del').show();
}

function check_pay() {
	var addressId = $('#addressId').val();
	if(typeof addressId == 'undefined' || !addressId) {
		alert('请选择地址！');
		return false;
	}
	var dd = $('input[name="prefer_delivery_day"]:checked').val();
	if(typeof dd == 'undefined' || !dd) {
		alert('请选择送货时间！');
		return false;
	} else {
		$('#deliveryDayCode').val(dd);
	}
//	var express = $('input[name="express"]:checked').attr('data-code');
//	if(typeof express == 'undefined' || !express) {
//		alert('请选择物流方式！');
//		return false;
//	}
	var gateway = $('input[name="gateway"]:checked').val();
	if(typeof gateway == 'undefined' || !gateway) {
		alert('请选择支付方式！');
		return false;
	}else {
		$('#payCode').val(gateway);
	}
	return true;
}