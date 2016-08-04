<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="decorator" content="account_default_new" />
<title>${accountTitle }</title>
<link href="${ctxStatic }/css/pagination.css" rel="stylesheet" type="text/css"></link>
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
		<a href="${ctx }/i/booking">全部</a> 
		<a href="${ctx }/i/booking/1">已预约</a> 
		<a href="${ctx }/i/booking/2">已完成</a>
		<a href="${ctx }/i/booking/3">已取消</a>
	</div>
	<c:if test="${empty page.list }"><div class="null_info"><h2>您还没有任何订单喔！</h2></div></c:if>
	<c:if test="${not empty page.list }">
		<table id="order-list" class="order_table">
			<tbody>
				<tr class="order_list_title">
					<th style="width: 180px">预约单号</th>
					<th style="width: 191px">预约详情</th>
					<th style="width: 80px">预约人</th>
					<th style="width: 90px">预约时间</th>
					<th style="width: 200px">状态</th>
					<!-- <th style="width: 70px">商品操作</th> 
					<th style="width: 120px">订单状态</th> -->
					<th style="width: 90px">操作</th>
				</tr>
				<c:forEach items="${page.list }" var="order">
				<tr>
					<td>
						${order.no }
					</td>
					<td>
						${order.demand }
					</td>
					<td>${order.name }</td>
					<td><fmt:formatDate value="${order.bookingTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td> 
					<td><c:if test="${order.status eq 0 or order.status eq 1 }">已预约</c:if>
						<c:if test="${order.status eq 2}">已派单</c:if>
						<c:if test="${order.status eq 3 }">已完成</c:if>
						<c:if test="${order.status eq 9 }">已取消</c:if>
						<c:if test="${order.status eq 4 and order.bookingOrderLogList.size() > 0 }">
							已评价
							<br/>${order.bookingOrderLogList.get(order.bookingOrderLogList.size() - 1).description }
						</c:if>
					</td>
					<td>
						<a href="javascript:;" class="search-track" data-id="${order.id }">查看跟踪</a>
						<%-- <a href="javascript:;" class="evaluation-btn" data-id="${order.id }">评价</a> --%>
						<c:if test="${order.status eq 0 or order.status eq 1 }">
							<a href="javascript:;" name="cancel" data-id="${order.id }">取消</a>
						</c:if>
						<c:if test="${order.status eq 2}"><a href="javascript:;" class="evaluation-btn" data-id="${order.id }">评价</a></c:if>
						<c:if test="${order.status eq 3 or order.status eq 4 }"><a href="javascript:;" class="evaluation-btn" data-id="${order.id }">评价</a></c:if>
						<c:if test="${order.status eq 9 }">已取消</c:if>
						<!-- <a href="javascript:;" name="cancel">取消</a>
						<a href="javascript:;" class="evaluation-btn">评价</a> -->
						<div class="track">
					        <span class="triangle"><em></em></span>
					        <c:forEach items="${order.bookingOrderLogList }" var="o">
					        <div class="track-list clearfix track-list-active">
				        		<div class="track-list-title">
				        			<c:choose>
				        				<c:when test="${o.action eq 'new' }">新建</c:when>
				        				<c:when test="${o.action eq 'audit' }">审核</c:when>
				        				<c:when test="${o.action eq 'dispatch' }">派单</c:when>
				        				<c:when test="${o.action eq 'complete' }">完成</c:when>
				        				<c:when test="${o.action eq 'cancel' }">取消</c:when>
				        			</c:choose>
				        		</div>
				        		<div class="track-list-mian">
				        			<div class="time-node">
				        				<span class="time-node-img"></span>
				        				<div class="track-time"><fmt:formatDate value="${o.createTime }" pattern="yyyyMMdd HH:mm:ss"/></div>
				        			</div>
				        			<div class="track-content">
				        				<p>${o.description }</p>
				        			</div>
				        			
				        		</div>
				        	</div>
					        </c:forEach>
					    </div>
					</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div id="pagination"></div>
	</c:if>

<div title="取消预约单确认" class="dialog_s charge_dialog cancle_order_dialog">
	<input id="cancelOrderId" type="hidden">
       <div class="alert warning"></div>
       <div class="cont">
           <p class="alert_con"><span>您确定要取消该预约单吗？</span> <br>确认取消后，预约单不可恢复。</p>
           &#65279;<span class="spark">*</span> 
           <span class="select_ui">
           	<div class="select_arrow"></div>
			<div class="select_text_ui" style="min-width: 9.5em;">请选择取消理由</div>
			<select id="select_reasons"><option value="">请选择取消理由</option>
				<option value="F01">重复下单</option>
				<option value="F02">信息填写错误</option>
				<option value="F99">其他</option>
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

<div class="evaluation-popup"></div>
<div class="evaluation-popup-main">
	<input type="hidden" id="bookingOrderId" name="bookingOrderId" />
	<div class="evaluation-popup-top">
		<p class="evaluation-popup-title">评价</p>
		<img src="${ctxAssets }/image/close-png.png" class="evaluation-popup-close">
		<div class="clear"></div>
	</div>
	<div class="evaluation-popup-bottom">
		<div class="select-div clearfix">
			<div class="select-div-1 active">
				已有人员上门<img src="${ctxAssets }/image/active.png">
			</div>
			<div class="select-div-2">
				未有人员上门<img src="${ctxAssets }/image/active.png">
			</div>
		</div>
		<div class="select-star clearfix">
			<p class="service-point">
				<span>*</span>服务评分：
			</p>
			<div class="star clearfix">
				<img src="${ctxAssets }/image/star-off.png" class="star-img" /> 
				<img src="${ctxAssets }/image/star-off.png" class="star-img" /> 
				<img src="${ctxAssets }/image/star-off.png" class="star-img" /> 
				<img src="${ctxAssets }/image/star-off.png" class="star-img" /> 
				<img src="${ctxAssets }/image/star-off.png" class="star-img" />
				<div class="select-star-tips" style="display: none;">
					<img src="${ctxAssets }/image/warning.png">请给出服务评分
				</div>
			</div>
		</div>
		<div class="evaluation-word clearfix">
			<p class="service-point">
				<span>*</span>服务短评：
			</p>
			<div class="evaluation-text clearfix">
				<textarea></textarea>
				<p class="word-num">
					您还可以输入<span>500</span>字
				</p>
			</div>
		</div>
		<div class="clearfix">
			<div class="evaluation-submit">确认提交</div>
		</div>

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
		    url: "${ctx}/i/booking/cancel/" + orderId,
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
	
	var point;
	
	$('.star-img').mouseenter(function () {
		var indexNum = $(this).index();
		$('.star-img:lt(' + indexNum + 1 + ')').attr('src', '${ctxAssets }/image/star-on.png');
		$('.star-img:gt(' + indexNum + ')').attr('src', '${ctxAssets }/image/star-off.png');
	});
	
	$('.star-img').click(function() {
		point = $(this).index();
		/* point = point + 1; */
	});
	
	$('.star-img').mouseleave(function () {
		if (point == undefined) {
			$('.star-img').attr('src', '${ctxAssets }/image/star-off.png');
		} else {
			$('.star-img:lt(' + point + 1 + ')').attr('src', '${ctxAssets }/image/star-on.png');
			$('.star-img:gt(' + point + ')').attr('src', '${ctxAssets }/image/star-off.png');
		}	
	});
	
	$('.select-div div').click(function () {
		$('.select-div div').removeClass('active');
		$(this).addClass('active');
	});
	
	$('.evaluation-btn').click(function () {
		$('.evaluation-popup').css('display','block');
		$('.evaluation-popup-main').css('display','block');
		$('#bookingOrderId').val($(this).attr('data-id'));
	});
	
	$('.evaluation-popup-close').click(function () {
		$('.evaluation-popup').css('display','none');
		$('.evaluation-popup-main').css('display','none');
	});
	
	$('.evaluation-submit').click(function(){
		var orderId = $('#bookingOrderId').val();
		var isService = $('.select-div').find('.active').hasClass('select-div-1');
		var remarks = $('.evaluation-text textarea').val();
		alert('orderId:' + orderId + ',isService:' + isService + ',point:' + point + ',remarks:' + remarks);
		if(typeof point == 'undefined') {
			alert('请评分');
			return;
		}
		$.ajax({
		    type: "post",
		    data: {
		    	isService: isService,
		    	point: point + 1,
		    	remarks: remarks
        	},
		    url: "${ctx}/i/booking/evaluate/" + orderId,
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
	
	$('.search-track').mouseenter(function () {
		$(this).siblings(".track").css('display','block');
	});
	
	$('.search-track').mouseleave(function () {
		$(this).siblings(".track").css('display','none');
	});
});
</script>
</content>
</body>
</html>