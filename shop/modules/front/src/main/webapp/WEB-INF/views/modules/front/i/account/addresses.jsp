<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="decorator" content="account_default_new" />
<title>${accountTitle }</title>
<link href="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/css/select2.min.css" rel="stylesheet" />
<style>
.ui-district-panel {
	position: absolute;
	z-index: 1000006;
	width: 380px;
	height: auto;
	padding: 15px 5px 15px 15px;
	list-style: none;
	background: #fff;
	border: 1px solid #ccc;
}

.ui-district-panel li {
	float: left;
	width: 85px;
	margin-right: 10px;
	padding: 4px 0;
	color: #666;
	cursor: pointer;
}

.ui-district-panel li.current {
	color: #ed145b;
}

.ui-district-panel li.long {
	width: 180px;
}
</style>
<content tag="local_script">
<script src="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/js/select2.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$('select').select2();
	$('#provinceId').change(function(){
		var provinceId = $(this).val();
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
		    	$('#cityId option:gt(0)').remove();
		    	$('#countyId option:gt(0)').remove();
		    	for(var i = 0; i < data.length; i++) {
		    		var a = data[i];
		    		$('#cityId').append('<option value="' + a.id + '">' + a.name + '</option>');
		    	}
		    	$('#cityId').val('');
		    	$('#countyId').val('');
		    	$('#cityId').select2();
		    	$('#countyId').select2();
		    },
		    complete: function () {
		        $(".loading").hide();
		    },
		    error: function (data) {
		        log.error("error: " + data.responseText);
		    }
		});
	});
	
	$('#cityId').change(function(){
		var cityId = $(this).val();
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
		    	$('#countyId option:gt(0)').remove();
		    	for(var i = 0; i < data.length; i++) {
		    		var a = data[i];
		    		$('#countyId').append('<option value="' + a.id + '">' + a.name + '</option>');
		    	}
		    	$('#countyId').val('');
		    	$('#countyId').select2();
		    },
		    complete: function () {
		        $(".loading").hide();
		    },
		    error: function (data) {
		        log.error("error: " + data.responseText);
		    }
		});
	});
	
	$('#submitBtn').click(function(){
		var id = $('#id').val();
		var name = $('#name').val();
		log.info('name:' + name);
		if($.trim(name).isEmpty()) {
			alert('请输入收货人姓名');
			$('#name').focus();
			return;
		}
		var provinceId = $('#provinceId').val();
		var provinceName = $('#provinceId option:selected').text();
		var cityId = $('#cityId').val();
		var cityName = $('#cityId option:selected').text();
		var countyId = $('#countyId').val();
		var countyName = $('#countyId option:selected').text();
		log.info('provinceId:' + provinceId + ',provinceName:' + provinceName 
				+ 'cityId:' + cityId + ',cityName:' + cityName 
				+ 'countyId:' + countyId + ',countyName:' + countyName);
		if(!provinceId || !cityId || !countyId) {
			alert('请先选择完整收货地址');
			return;
		}
		var address = $('#address').val();
		log.info('address:' + address);
		if($.trim(address).isEmpty()) {
			alert('详细地址不能为空');
			$('#address').focus();
			return;
		}
		var mobile = $('#mobile').val();
		log.info('mobile:' + mobile);
		if($.trim(mobile).isEmpty() || !$.trim(mobile).isMobile()) {
			alert('请填写手机号码');
			$('#mobile').focus();
			return;
		}
		var telArea = $('#telArea').val();
		var telNumber = $('#telNumber').val();
		var telExt = $('#telExt').val();
		log.info('telArea:' + telArea + ',telNumber:' + telNumber + ',telExt:' + telExt);
		if(!$.trim(telNumber).isEmpty() && $.trim(telArea).isEmpty()) {
			alert('固定号码请填写区号信息');
			return;
		}
		$.ajax({
		    type: "post",
		    url: "${ctx}/i/account/addresses",
		    data: {
		    	id: id,
		    	name: name,
		    	provinceId: provinceId,
		    	provinceName: provinceName,
		    	cityId: cityId,
		    	cityName: cityName,
		    	countyId: countyId,
		    	countyName: countyName,
		    	address: address,
		    	mobile: mobile,
		    	telArea: telArea,
		    	telNumber: telNumber,
		    	telExt: telExt
		    },
	    	beforeSend: function () {
		        $(".loading").show();
		    },
		    success: function (data) {
		    	log.info(data);
		    	if(data.s == 0) {
		    		window.location.reload();
		    	} else {
		    		alert(data.m);
		    	}
		    },
		    complete: function () {
		        $(".loading").hide();
		    },
		    error: function (data) {
		        log.error("error: " + data.responseText);
		    }
		});
		
	});
	
	$('#cancelLink').click(function(){
		$('form')[0].reset(); 
		$(this).hide();
	});
});

function editAddress(o, id) {
	$.ajax({
	    type: "post",
	    url: "${ctx}/i/account/addresses/" + id,
    	beforeSend: function () {
	        $(".loading").show();
	    },
	    success: function (data) {
	    	log.info(data);
	    	if(data.s == 0) {
	    		var address = data.address;
	    		var cities = data.cities;
	    		var counties = data.counties;
	    		$('#cityId option:gt(0)').remove();
		    	$('#countyId option:gt(0)').remove();
		    	for(var i = 0; i < cities.length; i++) {
		    		var a = cities[i];
		    		$('#cityId').append('<option value="' + a.id + '">' + a.name + '</option>');
		    	}
		    	for(var i = 0; i < counties.length; i++) {
		    		var a = counties[i];
		    		$('#countyId').append('<option value="' + a.id + '">' + a.name + '</option>');
		    	}
		    	$('#provinceId').val(address.provinceId);
		    	$('#cityId').val(address.cityId);
		    	$('#countyId').val(address.countyId);
		    	$('#provinceId').select2();
		    	$('#cityId').select2();
		    	$('#countyId').select2();
		    	$('#name').val(address.name);
		    	$('#address').val(address.address);
		    	$('#mobile').val(address.mobile);
		    	$('#id').val(address.id);
		    	var phone = address.phone;
		    	if(!$.trim(phone).isEmpty()) {
		    		var ss = phone.split('-');
		    		if(ss.length == 2) {
		    			$('#telArea').val(ss[0]);
		    			$('#telNumber').val(ss[1]);
		    		} else if(ss.length == 3) {
		    			$('#telArea').val(ss[0]);
		    			$('#telNumber').val(ss[1]);
		    			$('#telExt').val(ss[2]);
		    		}
		    	}
		    	$('#cancelLink').show();
	    	} else {
	    		alert(data.m);
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

function deleteAddress(o, id) {
	var r=confirm("是否删除该地址？")
  	if (r==true) {
  		$.ajax({
  		    type: "post",
  		    url: "${ctx}/i/account/addresses/delete/" + id,
  	    	beforeSend: function () {
  		        $(".loading").show();
  		    },
  		    success: function (data) {
  		    	log.info(data);
  		    	if(data.s == 0) {
  		    		window.location.reload();
  		    	} else {
  		    		alert(data.m);
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
}
</script>
</content>
</head>
<body style="background: white; width: 960px; margin: 0 auto;">
	<h4>
		<span class="addAddress">新增</span><span class="modifyAddress" style="display: none;">修改</span>收货地址
	</h4>
	<form class="shipping_address" method="post">
		<input id="id" name="id" value="" type="hidden">
		<input id="provinceName" name="provinceName" value="" type="hidden">
		<input id="cityName" name="cityName" value="" type="hidden">
		<input id="countyName" name="countyName" value="" type="hidden">
		
		<div class="input_container" id="container-input">
			<label for="name"><span class="spark">*</span>收件人姓名：</label>
			<input size="20" class="t_input recipient_name" id="name" name="name" type="text"> 
		</div>
		<div class="input_container input_address_select" id="container-input">
			<label for="provinceId"><span class="spark">*</span>收货地址：</label>
			<div class="district_selector">
				<select id="provinceId" name="provinceId" class="select2" style="width:180px;">
					<option value="">--请选择--</option>
					<c:forEach items="${provinces }" var="p"><option value="${p.id }">${p.name }</option></c:forEach>
				</select>
				<select id="cityId" name="cityId" class="select2" style="width:180px;"><option value="">--请选择--</option></select>
				<select id="countyId" name="countyId" class="select2" style="width:180px;"><option value="">--请选择--</option></select>
			</div>
		</div>
		<div class="input_container" id="container-input">
			<label for="address"><span class="spark">*</span>详细地址：</label>
			<textarea rows="3" cols="60" class="t_input recipient_street" style="height: auto; resize: none; width: 600px;" id="address" name="address"></textarea>
		</div>
		<div class="input_container" style="margin-top: 30px;" id="container-input">
			<label for="mobile"><span class="spark">*</span>手机号码：</label>
			<input size="20" class="t_input recipient_hp" id="mobile" name="mobile" maxlength="11" type="tel"> 
			<label for="telArea" style="float: none; margin-left: 20px;">或固定号码：</label>
			<input size="5" class="t_input recipient_tel_area" id="telArea" name="telArea" maxlength="4" type="tel" style="width: 60px;"> 
			<span class="rod">-</span>
			<input size="10" class="t_input recipient_tel_number" id="telNumber" name="telNumber" maxlength="8" type="tel" style="width: 90px;"> 
			<span class="rod">-</span> 
			<input size="5" class="t_input recipient_tel_ext" id="telExt" name="telExt" maxlength="8" type="tel" style="width: 56px;">
		</div>
		<div class="act" id="container-input">
			<input value="保存收货地址" id="submitBtn" name="commit" id="shipping_address_submit" class="formbutton" type="button">
			<a id="cancelLink" href="javascript:void(0);" style="display:none;">取消</a>
		</div>
	</form>
	<h4>已保存的地址</h4>
	<div class="notice_content">最多保存10个有效地址。</div>
	<c:if test="${list.size() == 0 }">
	<div id="no_shipping_address_list">
		<div class="warning">没有收货地址信息。</div>
	</div>
	</c:if>
	<c:if test="${list.size() > 0 }">
	<div id="shipping_address_list">
		<table class="order-tablev2" id="order-list">
			<colgroup>
				<col class="name">
				<col class="address">
				<col class="code">
				<col class="hp">
				<col class="phone">
				<col class="action">
			</colgroup>
			<tbody>
				<tr class="order_list_title">
					<th>收货人</th>
					<th>收货地址</th>
					<th>手机</th>
					<th>固定电话</th>
					<th width="80">操作</th>
				</tr>
				<c:forEach items="${list }" var="a">
				<tr>
					<td>${a.name }</td>
					<td class="order_info_td">${a.address }</td>
					<td style="padding: 0;">${a.mobile }</td>
					<td>${a.phone }</td>
					<td><a href="javascript:void(0);" style="display: inline-block;" class="sp_address_edit" onclick="editAddress(this, '${a.id}')">修改</a> &nbsp; 
						<a href="javascript:void(0);" style="display: inline-block;" class="sp_address_delete" onclick="deleteAddress(this, '${a.id}')">删除</a></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	</c:if>
</body>
</html>