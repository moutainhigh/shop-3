<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="decorator" content="account_default_new"/>
    <title>${accountTitle }</title>
	<link href="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/css/select2.min.css" rel="stylesheet" />
    <content tag="local_script">
    <script src="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/js/select2.min.js"></script>
    <script type="text/javascript">
    $(function(){
    	$('input[name="address_id"][value="${ordership.addressId}"]').parent().addClass('selected');
    	
    	$('#submitBtn').click(function() {
    		var addressId = $('#addressId').val();
    		if(typeof addressId == 'undefined' || !addressId) {
    			alert('请选择地址');
    			return;
    		}
    		document.getElementById("submitForm").submit();
    	});
    	
    	$('.btn_new_address').click(function () {
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
    	})
    	
    	$('.cancel_btn').click(function() {
    		$('.add-new-pop').css('display','none');
    		$('.add-new-pop-main').css('display','none');
    	})
    	
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
			    url: "${ctx}/common/findCities",
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
			    url: "${ctx}/common/findCounties",
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
			
			//防止重复点击
			if (flag) {
				return;
			}
			flag = true;
			$.ajax({
			    type: "post",
			    url: "${ctx}/i/account/addresses",
			    data: {
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
			    			var addrId = data.m;
				    		$('.option_box').removeClass('selected');
				    		var html = '<div class="option_box selected" onclick="selectAddr(this);">' +
											'<input id="address_'+ addrId +'" value="'+ addrId +'" name="address_id" class="rdoAddress" checked="checked" type="radio"> ' +
											'<label for="address_'+ addrId +'" class="address_lbl">' +
												'<p>' +
													'<span class="addr_name">' + receiverName.val() + '</span>' +
													'<span class="addr_con">'+  provinceName +'-'+ cityName +'-'+ countyName +' '+ addressDetail.val() +'</span>' + 
													'<span class="addr_num">'+ mobile.val() +'</span>' +
												'</p>' +
											'</label>' +
											'<div class="clear"></div>' +
										'</div>';
							$('#order_address').prepend(html);
							
							$('.add-new-pop').css('display','none');
    						$('.add-new-pop-main').css('display','none');
			    	} else {
			    		alert(data.m);
			    	}
			    },
			    complete: function () {
			        $(".loading").hide();
			        flag = false;
			    },
			    error: function (data) {
			        log.error("error: " + data.m);
			        flag = false;
			    }
			});
		});
    });
    
    function selectAddr(o) {
    	$this = $(o);
    	$('.option_box').removeClass('selected');
   		$this.addClass('selected');
   		var addressId = $this.find('input').val();
   		$('#addressId').val(addressId);
    }
    
    function isEmpty(str){
	    if(str==''||str==null){
	        return true;
	    }else{
	        return false;
	    }
	}
    </script>
    </content>
</head>
<body style="background:white; width:960px;margin:0 auto;">
	<form id="submitForm" action="${ctx }/i/order/${order.id }/shipping/modify" method="post">
		<input type="hidden" id="addressId" name="addressId" value="${ordership.addressId }">
		<div id="order_address" class="sector">
			<c:forEach items="${addresses }" var="a">
			<div class="option_box" onclick="selectAddr(this);">
				<input id="address_${a.id }" value="${a.id }" name="address_id" class="rdoAddress" checked="checked" type="radio"> 
				<label for="address_${a.id }" class="address_lbl">
					<p>
						<span class="addr_name">${a.name }</span> 
						<span class="addr_con">${a.provinceName }-${a.cityName }-${a.countyName } ${a.address }</span> 
						<span class="addr_num">${a.mobile }</span>
					</p>
				</label>
				<div class="clear"></div>
			</div>
			</c:forEach>
		</div>
		<div class="profile_row">
			<a class="btn_new_address less_padding btn_mid_grey" href="javascript:void(0)">+ 使用新地址</a>
		</div>
		<div class="profile_row more_margin">
			<input id="submitBtn" value="确认修改" class="more_padding formbutton" style="margin-left: 15px;" type="button"> 
			<a class="less_padding btn_mid_grey" href="javascript:history.go(-1);">取消</a>
		</div>
	</form>
	<div class="add-new-address">
	  	<div class="add-new-pop">
	  	</div>
  		<div id="add-new-address-prop" class="add-address-from add-new-pop-main">
  			<div class="add-new-pop-main-title">添加新的地址</div>
           <div class="add-address-list">
             <div class="receiver-name clearfix-n">
               <div class="input-name fl">
                 <label>收件人：<span class="tips">*</span></label>
               </div>
               <div class="input-box fl">
                 <input id="id" name="id" value="" type="hidden">
                 <input type="text" class="input" autocomplete="off" placeholder="请输入收件人" name="receiver_name" maxlength="20">
               </div>
             </div>
             <p class="add-address-list-err"><img alt="" src="${ctxAssets }/image/warning.png">收件人不能为空</p>
           </div>
           <div class="add-address-list">
		      <div class="receiver-address clearfix-n">
		        <div class="input-name fl">
		          <label>收货地址：<span class="tips">*</span></label>
		        </div>
		        <select name="provinceId" autocomplete="off" class="select2" style="width:140px;">
				  <option value="">--请选择--</option>
				  <c:forEach items="${provinces }" var="p"><option value="${p.id }">${p.name }</option></c:forEach>
				</select>
				<select name="cityId" autocomplete="off" class="select2" style="width:140px;"><option value="">--请选择--</option></select>
				<select name="countyId" autocomplete="off" class="select2" style="width:140px;"><option value="">--请选择--</option></select>
		      </div>
		      <p class="add-address-list-err"><img alt="" src="${ctxAssets }/image/warning.png">请选择完整的地址信息</p>
		   </div>
		   <div class="add-address-list">
		      <div class="receiver-xiangxidizi clearfix-n">
		        <div class="input-name fl">
		          <label>详细地址：<span class="tips">*</span></label>
		        </div>
		        <div class="input-box fl">
		          <input type="text" autocomplete="off" name="addressDetail" maxlength="20" placeholder="请填写详细地址">
		        </div>
		      </div>
		      <p class="add-address-list-err"><img alt="" src="${ctxAssets }/image/warning.png">详细地址不能为空</p>
		   </div>
           <div class="add-address-list">
             <div class="receiver-phone clearfix-n">
               <div class="input-name fl">
                 <label>手机号码：<span class="tips">*</span></label>
               </div>
               <div class="input-box fl cellphone-box">
                 <input type="text" autocomplete="off" name="mobile" maxlength="20">
               </div>
               <div class="input-name guding-name fl">
                 <label class="">或固定电话：</label>
               </div>
               <div class="input-box fl guding-quhao">
                 <input type="text"  autocomplete="off" name="telArea" maxlength="20" placeholder="区号">
               </div>
               <div class="fl" style="margin: 0 5px;">-</div>
               <div class="input-box fl guding-num">
                 <input type="text" autocomplete="off" name="telNumber" maxlength="20" placeholder="电话号码">
               </div>
               <div class="fl" style="margin: 0 5px;">-</div>
               <div class="input-box fl guding-fenji">
                 <input type="text" name="telExt" autocomplete="off" maxlength="20" placeholder="分机">
               </div>
             </div>
             <p class="add-address-list-err"><img alt="" src="${ctxAssets }/image/warning.png">请填写11位手机号码</p>
           </div>
           <div class="clearfix-n" style="margin-left: 75px;padding-top: 10px;">
             <a href="javascript:;" data-type="2" class="submit_btn">确定</a>
             <a href="javascript:;" class="cancel_btn" style="width: auto;">取消</a>
           </div>
         </div>
	  </div>
	</div>
</body>
</html>