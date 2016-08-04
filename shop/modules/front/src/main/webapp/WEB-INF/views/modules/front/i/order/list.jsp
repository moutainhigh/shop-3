<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="decorator" content="account_default_new" />
<title>${accountTitle }</title>
<link href="${ctxStatic }/css/pagination.css" rel="stylesheet" type="text/css"></link>
<!-- <link href="${ctxAssets }/stylesheets/orderlist.css" rel="stylesheet" type="text/css"></link> -->
<style type="text/css">
.profile .filter+table th {
	border-bottom: 1px solid #ccc;
}
</style>
</head>
<body style="background: white; width: 960px; margin: 0 auto;">
	<form id="searchForm" style="display:none;" action="${ctx }${page.pagerUrl }" method="post">
		<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize }"/>
		<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo }"/>
	</form>
	<div class="filter">
		<a href="${ctx }/i/order">全部订单</a> 
		<a href="${ctx }/i/order/1">等待付款</a> 
		<a href="${ctx }/i/order/2">交易完成</a>
		<a href="${ctx }/i/order/3">已取消</a>
	</div>
	<c:if test="${empty page.list }"><div class="null_info"><h2>您还没有任何订单喔！</h2></div></c:if>
	<c:if test="${not empty page.list }">
		<table id="order-list" class="order_table">
			<tbody>
				<tr class="order_list_title">
					<th style="width: 191px">订单信息</th>
					<th style="width: 80px">商家</th>
					<th style="width: 80px">订购商品</th>
					<th style="width: 80px">商品规格</th>
					<th style="width: 40px">件数</th>
					<th style="width: 80px">单价</th>
<!-- 					<th style="width: 70px">商品操作</th>  -->
					<th style="width: 120px">订单状态</th>
					<th style="width: 90px">订单操作</th>
				</tr>
				<c:forEach items="${page.list }" var="order">
				<tr>
					<td rowspan="${order.odList.size() }" class="order_info_td">
						<p>交易单号：${order.no }</p>
						<p>成交时间：${order.createdate }</p>
						<p>商品金额: ¥${order.ptotal }(<c:if test="${order.fee == 0 }">免运费</c:if><c:if test="${order.fee > 0 }">运费${order.fee }</c:if>)</p>
					</td>
					<c:forEach items="${order.sellerList }" var="seller" varStatus="i">
						<td rowspan="${seller.value.size()}">${seller.value[0].sellerName }</td>
						<c:forEach items="${seller.value }" var="od" varStatus="s">
							<c:if test="${s.index == 0 }">
								<td class="item_title">
									<a title="${od.productName }" target="_blank" href="${ctx }/product/${od.productID}"><img alt="${od.productName }" src="${IMAGE_ROOT_PATH }/${od.picture}"/></a>
								</td>
								<td>
									<c:set value="${ fn:split(od.specInfo, ',') }" var="names" />
									<c:forEach items="${ names }" var="name">
										<p class="cart_item_prop"  style="margin-bottom:0; line-height:25px" title="${name }">${name }</p>
									</c:forEach>
								</td>
								<td>${od.number }</td>
								<td>¥${od.price }</td>
								<c:if test="${i.index == 0 }">
									<td rowspan="${order.odList.size() }">
										<c:if test="${order.paystatus eq 'y' }">
											<c:if test="${order.status eq 'init' or order.status eq 'pass' }">等待发货</c:if>
											<c:if test="${order.status eq 'send' }">已发货</c:if>
											<c:if test="${order.status eq 'sign' }">已签收</c:if>
											<c:if test="${order.status eq 'file'}">交易完成</c:if>
										</c:if>
										<c:if test="${ order.status eq 'cancel' }">
											已取消
										</c:if>
										<c:if test="${order.paystatus eq 'n' and order.status ne 'cancel' }">
											<span class="">等待付款</span>
										</c:if>
										<c:if test="${order.paystatus eq 'y' and (order.status eq 'send' or order.status eq 'sign') }">
											<a target="_blank" href="${ctx }/i/shipping/trace?orderId=${order.id }" data-id= "${order.id }" class="kuaidi control-a">
												查看物流详情
												<div class="track kuaidi-main">
											        <span class="triangle"><em></em></span>
											    </div>
											</a>
											
									    </c:if>
								    </td>
								    <td rowspan="${order.odList.size() }">
										<p>
											<a href="${ctx }/i/order/detail/${order.id}" class="control-a">查看详情</a>
										</p>
										<p>
											<c:if test="${order.paystatus eq 'y' }">
												<c:if test="${order.status eq 'send' }"><a  data-id="${order.id}" name="confirm" href="javascript:void(0);" class="control-a">确认收货</a></c:if>
											</c:if>
											<c:if test="${order.paystatus ne 'y' and order.status eq 'cancel' }">
											</c:if>
											<c:if test="${order.paystatus ne 'y' and order.status ne 'cancel' }">
												<a href="${ctx }/order/pay?orderId=${order.id}" class="control-a">点击付款</a>
												<a data-id="${order.id}" name="cancel" href="javascript:void(0);" class="control-a">取消</a>
												<a href="${ctx }/i/order/${order.id}/shipping/modify" class="reset-address control-a">修改地址</a>
											</c:if>
										</p>
										<p>
											<c:if test="${order.status eq 'sign' or order.status eq 'file' }">
												<c:if test="${ order.closedComment ne 'y' }"><a target="_blank" href="" class="control-a ">我来评价</a></c:if>
											</c:if>
										</p>
									</td>
								</c:if>
							</c:if>
							<c:if test="${s.index > 0 }">
								<tr>
									<td class="item_title">
										<a title="${od.productName }" target="_blank" href="${ctx }/product/${od.productID}"><img alt="${od.productName }" src="${IMAGE_ROOT_PATH }/${od.picture}"/></a>
									</td>
									<td>
										<c:set value="${ fn:split(od.specInfo, ',') }" var="names" />
										<c:forEach items="${ names }" var="name">
											<p class="cart_item_prop"  style="margin-bottom:0; line-height:25px" title="${name }">${name }</p>
										</c:forEach>
									</td>
									<td>${od.number }</td>
									<td>¥${od.price }</td>
								</tr>
							</c:if>
						</c:forEach>
					</c:forEach>
				</tr>
<!-- 				<c:forEach items="${order.sellerList }" var="seller"> -->
<!-- 					<c:forEach items="${order.odList }" var="od" varStatus="s"> -->
<!-- 					<c:if test="${s.index > 0 }"> -->
<!-- 					<tr> -->
<!-- 						<td class="item_title"> -->
<!-- 							<a href="${ctx }/product/${od.productID}" target="_blank" title=""><img src="${IMAGE_ROOT_PATH }${od.picture }" alt="${od.productName }"></a> -->
<!-- 						</td> -->
<!-- 						<td> -->
<!-- 							<c:set value="${ fn:split(od.specInfo, ',') }" var="names" /> -->
<!-- 							<c:forEach items="${ names }" var="name"> -->
<!-- 								<p class="cart_item_prop"  style="margin-bottom:0; line-height:25px" title="${name }">${name }</p> -->
<!-- 							</c:forEach> -->
<!-- 						</td> -->
<!-- 						<td>${od.number }</td> -->
<!-- 						<td>¥${od.price }</td> -->
<!-- 						<td></td> -->
<!-- 					</tr> -->
<!-- 					</c:if> -->
<!-- 					</c:forEach> -->
<!-- 				</c:forEach> -->
				</c:forEach>
			</tbody>
		</table>
		<div id="pagination"></div>
	</c:if>

	<div title="取消订单确认" class="dialog_s charge_dialog cancle_order_dialog">
		<input id="cancelOrderId" type="hidden">
        <div class="alert warning"></div>
        <div class="cont">
            <p class="alert_con"><span>您确定要取消该订单吗？</span> <br>确认取消后，订单不可恢复，使用的现金券将会恢复正常。</p>
            &#65279;<span class="spark">*</span> 
            <span class="select_ui">
            	<div class="select_arrow"></div>
				<div class="select_text_ui" style="min-width: 9.5em;">请选择取消理由</div>
				<select id="select_reasons"><option value="">请选择取消理由</option>
					<option value="1">重复下单</option>
					<option value="2">买错了，重新拍</option>
					<option value="3">付款出现问题</option>
					<option value="4">地址填写错误</option>
					<option value="5">不想买了</option>
					<option value="9">其他</option>
				</select>
			</span>
			<textarea style="margin-top: 20px; color: rgb(170, 170, 170);"></textarea>
            <p class="input_error"></p>
        </div>
        <div class="btns">
            <a class="btn_mid_pink" href="javascript:void(0)">确认</a>
            <a class="btn_mid_grey" href="javascript:void(0)">取消</a>
        </div>
    </div>
    
    
<content tag="local_script">
<script src="${ctxStaticJS }/js/jquery.lightbox.js" type="text/javascript"></script>
<script src="${ctxStaticJS }/js/jquery.pagination.js" type="text/javascript"></script>
<script type="text/javascript">
$(function(){
	$('.filter').find('a:eq(${type})').addClass('curr');
	var d; 
	$('a[name="cancel"]').click(function(){
		if(!d) {
			d = $(".dialog_s").show().dialog();
			$('.btn_mid_grey').on('click', function(){
				d.hide();
			});
		}
		$('#cancelOrderId').val($(this).attr('data-id'));
		d.show();
	});
	
	$('a[name="confirm"]').click(function(){
		var orderId = $(this).attr("data-id");
		$.ajax({
		    type: "post",
		    url: "${ctx}/i/order/confirmGoods/" + orderId,
        	beforeSend: function () {
		        $(".loading").show();
		    },
		    success: function (data) {
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
	
	$('.btn_mid_pink').click(function(){
		var orderId = $('#cancelOrderId').val();
		var reasonId = $('#select_reasons').val();
		var reason = $('#select_reasons option:selected').text();
		var remarks = $('textarea').val();
		//alert('orderId:' + orderId + ',reasonId:' + reasonId + ',reason:' + reason + ',remarks:' + remarks);
		if(typeof reasonId == 'undefined' || !reasonId || reasonId == '0') {
			alert('请您选择取消订单的理由');
			return;
		}
		$.ajax({
		    type: "post",
		    data: {
		    	reasonId: reasonId,
		    	reason: reason,
		    	reasonDesc: remarks
        	},
		    url: "${ctx}/i/order/cancel/" + orderId,
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
	$('#select_reasons').change(function(){
		var _this = $(this);
		//alert('value:' + _this.val() + ',text:' + _this.find('option:selected').text());
		$('.select_text_ui').html(_this.find('option:selected').text());
	});
	
	$("#pagination").pagination('${page.total}', {
        callback: function(index, jq){
        	$('#pageNo').val(index);
        	document.getElementById('searchForm').submit();
        },
        current_page:'${page.pageNo}',
        items_per_page:'${page.pageSize }',
		num_edge_entries:1,
		num_display_entries:3,
		link_to:"#",
		prev_text:"上一页",
		next_text:"下一页",
    });
	
	$('.kuaidi').mouseenter(function () {
		var orderId = $(this).attr("data-id");
		$.ajax({
		    type: "get",
		    data: {
		    	orderId: orderId
        	},
		    url: "${ctx}/i/ship/trace?orderId=" + orderId,
        	beforeSend: function () {
		        $(".loading").show();
		    },
		    success: function (data) {
		    	var html = '';
		    	$(".kuaidi-main").find(".track-list").remove();
		    	if(data.s == "0") {
		    		var obj = eval('(' + data.m + ')');
		    		if ($.trim(data.m).isEmpty() || obj.status !='200') {
		    			html += '<div class="track-list clearfix track-list-active">'+
				        		'<div class="track-list-mian">'+
				        			'<div class="time-node">'+
				        				'<span class="time-node-img"></span>'+
				        				'<div class="track-time"></div>'+
				        			'</div>'+
				        			'<div class="track-content">'+
				        				'<p>暂无物流数据</p>'+
				        			'</div>'+
				        		'</div>'+
				        	'</div>';
				        	$(html).insertAfter(".triangle");
		    		} else {
		    			var items = obj.data;
		    			var length = items.length > 4 ? 4 : items.length;
			    		for(var i=0;i<length;i++) {
			    			if (i==0) {
			    				html += '<div class="track-list clearfix track-list-active">'+
				        		'<div class="track-list-mian">'+
				        			'<div class="time-node">'+
				        				'<span class="time-node-img"></span>'+
				        				'<div class="track-time">'+ items[i].time +'</div>'+
				        			'</div>'+
				        			'<div class="track-content">'+
				        				'<p>'+ items[i].context +'</p>'+
				        			'</div>'+
				        		'</div>'+
				        	'</div>';
			    			} else {
			    				html += '<div class="track-list clearfix">'+
							        		'<div class="track-list-mian">'+
							        			'<div class="time-node">'+
							        				'<span class="time-node-img"></span>'+
							        				'<div class="track-time">'+ items[i].time +'</div>'+
							        			'</div>'+
							        			'<div class="track-content">'+
							        				'<p>'+ items[i].context +'</p>'+
							        			'</div>'+
							        		'</div>'+
							        	'</div>';
			    			}
			    		}
			    		$(html).insertAfter(".triangle");
		    		}
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
		$(this).children(".track").css('display', 'block');
	})
	
	$('.kuaidi').mouseleave(function () {
		$(this).children(".track").css('display', 'none');
	})
	
	
});
</script>
</content>
</body>
</html>