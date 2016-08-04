<%@page import="net.jeeshop.core.KeyValueHelper"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page session="false"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/resource/common_html_meat.jsp"%>
<%@ include file="/manage/system/common.jsp"%>
<link href="<%=request.getContextPath()%>/resource/bootstrap-datetimepicker/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<script src="<%=request.getContextPath()%>/resource/jquery/jquery-migrate-1.1.1.min.js" type="text/javascript"></script>
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/resource/My97DatePicker/WdatePicker.js"></script> --%>
<script src="<%=request.getContextPath()%>/resource/bootstrap-datetimepicker/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
<script src="<%=request.getContextPath()%>/resource/bootstrap-datetimepicker/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<link href="<%=request.getContextPath()%>/resource/jquery-jbox/Skins/Bootstrap/jbox.css" rel="stylesheet" />
<script src="<%=request.getContextPath()%>/resource/jquery-jbox/jquery.jBox-2.3.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/resource/jquery-jbox/i18n/jquery.jBox-zh-CN.min.js" type="text/javascript"></script>
<style type="text/css">
.titleCss {
	background-color: #e6e6e6;
	border: solid 1px #e6e6e6;
	position: relative;
	margin: -1px 0 0 0;
	line-height: 32px;
	text-align: left;
}

.aCss {
	overflow: hidden;
	word-break: keep-all;
	white-space: nowrap;
	text-overflow: ellipsis;
	text-align: left;
	font-size: 12px;
}

.liCss {
	white-space: nowrap;
	text-overflow: ellipsis;
	overflow: hidden;
	height: 30px;
	text-align: left;
	margin-left: 10px;
	margin-right: 10px;
}
</style>
<script type="text/javascript">
$(function() {
	$('a[name="pass"]').click(function(){
		var _this = $(this);
		var key = _this.attr('data-key');
		$.jBox.confirm("是否审核通过该预约单？","系统提示",function(v,h,f){
			if(v=="ok"){
				//$("#searchForm").attr("action","${ctx}/sys/user/export");
				//$("#searchForm").submit();
				var _url = "bookingOrder!pass.action";
				$.ajax({
				  type: 'POST',
				  url: _url,
				  data: {
					  id: key
				  },
				  success: function(data){
					if(data && data.isSuccess){
						window.location.reload();
					}
				  },
				  dataType: "json",
				  error:function(){
				  	//alert("加载小类别失败，请联系管理员。");				  
				  }
				});
			}
		},{buttonsFocus:1});
		$('.jbox-body .jbox-icon').css('top','55px');
	});
	$('a[name="dispatch"]').click(function(){
		var _this = $(this);
		var key = _this.attr('data-key');
		$.jBox.open($('#dispatchBox').html(), "派单", $(top.document).width() - 720, 240, {
			buttons:{ '确定': 1, '取消': 0},
			loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
				h.find("#time").datetimepicker({
					format: 'yyyy/mm/dd hh:ii',
				    autoclose: true,
				    todayHighlight: true,
				    language: 'zh-CN',
				    startDate: new Date(),
				    minuteStep: 30,
				    forceParse: false
				}).on('changeDate', function(ev){
					var _this = $('#time');
				});
			},
			submit: function (v, h, f) {
				if (v == 0) {
                    return true; 
                } else {
                	var userId = h.find('#username').val();
                	if(userId == '') {
                		alert('请选择人员');
                		return false;
                	}
                	var username = h.find('#username option:selected').text();
                	var time = h.find('#time').val();
                	var remark = h.find('#remark').val();
                	//alert('userId:' + userId + ',username:' + username + ',time:' + time + ',remark:' + remark);
                	var _url = "bookingOrder!dispatch.action";
    				$.ajax({
    				  type: 'POST',
    				  url: _url,
    				  data: {
    					  id: key,
    					  userId: userId,
    					  username: username,
    					  time: time,
    					  remark: remark
    				  },
    				  success: function(data){
    					if(data && data.isSuccess){
    						window.location.reload();
    					}
    				  },
    				  dataType: "json",
    				  error:function(){
    				  	//alert("加载小类别失败，请联系管理员。");				  
    				  }
    				});
                	return true;
                }
			}
		});
		$('.jbox-body .jbox-icon').css('top','55px');
	});
	$('a[name="cancel"]').click(function(){
		var _this = $(this);
		var key = _this.attr('data-key');
		$.jBox.open($('#cancelBox').html(), "取消预约单", $(top.document).width() - 720, 200, {
			buttons:{ '确定': 1, '取消': 0},
			loaded:function(h){
				$(".jbox-content", top.document).css("overflow-y","hidden");
			},
			submit: function (v, h, f) {
				if (v == 0) {
                    return true; 
                } else {
                	var reasonId = h.find('#reason').val();
                	if(reasonId == '') {
                		alert('请选择取消原因');
                		return false;
                	}
                	var reason = h.find('#reason option:selected').text();
                	var remark = h.find('#remark').val();
                	//alert('reasonId:' + reasonId + ',reason:' + reason + ',remark:' + remark);
                	var _url = "bookingOrder!cancel.action";
    				$.ajax({
    				  type: 'POST',
    				  url: _url,
    				  data: {
    					  id: key,
    					  reasonId: reasonId,
    					  reason: reason,
    					  remark: remark
    				  },
    				  success: function(data){
    					if(data && data.isSuccess){
    						window.location.reload();
    					}
    				  },
    				  dataType: "json",
    				  error:function(){
    				  	//alert("加载小类别失败，请联系管理员。");				  
    				  }
    				});
                	return true;
                }
			}
		});
		
		$('.jbox-body .jbox-icon').css('top','55px');
	});
	
	$('#provinceId').change(function(){
		var _this = $(this);
		var parentId = _this.val();
		$.ajax({
		  type: 'POST',
		  url: 'bookingOrder!getArea.action',
		  data: {
			  parentId: parentId
		  },
		  success: function(data){
			$('#cityId').find('option:gt(0)').remove();
			for(var i=0;i < data.length; i++) {
				$('#cityId').append('<option value="' + data[i].id + '">' + data[i].name + '</option>');
			}
		  },
		  dataType: "json",
		  error:function(){
		  }
		});
	});
	
	$('#cityId').change(function(){
		var _this = $(this);
		var parentId = _this.val();
		$.ajax({
		  type: 'POST',
		  url: 'bookingOrder!getArea.action',
		  data: {
			  parentId: parentId
		  },
		  success: function(data){
			$('#countyId').find('option:gt(0)').remove();
			for(var i=0;i < data.length; i++) {
				$('#countyId').append('<option value="' + data[i].id + '">' + data[i].name + '</option>');
			}
		  },
		  dataType: "json",
		  error:function(){
		  }
		});
	});
});
</script>
</head>

<body>
	<s:form action="bookingOrder" namespace="/manage" method="post" theme="" cssClass="breadcrumb form-search">
		<%-- <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="orderBy" name="orderBy" type="hidden" value="${page.orderBy}"/> --%>
		<div>
			<label>预约人：</label><input maxlength="50" class="input-medium" name="e.name" value="${e.name }"/>
			<label>预约手机：</label><input maxlength="50" class="input-medium" name="e.mobile" value="${e.mobile }"/>
			<label>预约状态：</label><select name="e.status">
				<option value="">-全部-</option>
				<option value="0" <c:if test="${e.status eq '0' }">selected</c:if>>新建</option>
				<option value="1" <c:if test="${e.status eq '1' }">selected</c:if>>已收单</option>
				<option value="2" <c:if test="${e.status eq '2' }">selected</c:if>>已派单</option>
				<option value="3" <c:if test="${e.status eq '3' }">selected</c:if>>已完成</option>
				<option value="4" <c:if test="${e.status eq '4' }">selected</c:if>>已评价</option>
				<option value="9" <c:if test="${e.status eq '9' }">selected</c:if>>已取消</option>
			</select>
		</div>
		<div>
			<label>省/直辖市：</label><select id="provinceId" name="e.provinceId">
				<option value="">-全部-</option>
				<c:forEach items="${provinces }" var="p">
				<option value="${p.id }" <c:if test="${e.provinceId eq p.id }">selected</c:if>>${p.name }</option>
				</c:forEach>
			</select>
			<label>市：</label><select id="cityId" name="e.cityId">
				<option value="">-全部-</option>
				<c:forEach items="${cities }" var="p">
				<option value="${p.id }" <c:if test="${e.cityId eq p.id }">selected</c:if>>${p.name }</option>
				</c:forEach>
			</select>
			<label>区/县：</label><select id="countyId" name="e.countyId">
				<option value="">-全部-</option>
				<c:forEach items="${counties }" var="p">
				<option value="${p.id }" <c:if test="${e.countyId eq p.id }">selected</c:if>>${p.name }</option>
				</c:forEach>
			</select>
			&nbsp;
			<button method="bookingOrder!selectList.action" class="btn btn-primary" onclick="selectList(this)">
				<i class="icon-search icon-white"></i> 查询
			</button>
		</div>
		<table class="table table-bordered table-hover">
			<tr style="background-color: #dff0d8">
				<th width="20"><input type="checkbox" id="checkAll" /></th>
				<th>预约人</th>
				<th>预约手机</th>
				<th>省/市/区</th>
				<th>详细地址</th>
				<th>详细需求</th>
				<th>预约时间</th>
				<th>创建日期</th>
				<th>订单状态</th>
				<th width="60px">操作</th>
			</tr>
			<s:iterator value="pager.list" var="i">
				<tr>
					<td><input type="checkbox" name="ids" value="<s:property value="id"/>" /></td>
					<td><s:property value="name" /></td>
					<td><s:property value="mobile" /></td>
					<td><s:property value="provinceName" />-<s:property value="cityName" />-<s:property value="countyName" /></td>
					<td><s:property value="address" /></td>
					<td><s:property value="demand" /></td>
					<td><s:date name="bookingTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
					<c:if test="${i.status == 0 }">新建</c:if>
					<c:if test="${i.status == 1 }">已收单</c:if>
					<c:if test="${i.status == 2 }">已派单</c:if>
					<c:if test="${i.status == 3 }">已完成</c:if>
					<c:if test="${i.status == 4 }">已评价</c:if>
					<c:if test="${i.status == 9 }">已取消</c:if>
					</td>
					<td>
					<c:if test="${i.status == 0 }"><a href="javascript:;" name="pass" data-key="${i.id }">通过</a>
					<a href="javascript:;" name="cancel" data-key="${i.id }">取消</a></c:if>
					<c:if test="${i.status == 1 }"><a href="javascript:;" name="dispatch" data-key="${i.id }">派单</a>
					<a href="javascript:;" name="cancel" data-key="${i.id }">取消</a></c:if>
					<c:if test="${i.status == 2 }"><a href="javascript:;" name="dispatch" data-key="${i.id }">重新派单</a>
					<a href="javascript:;" name="cancel" data-key="${i.id }">取消</a></c:if>
					<s:a href="bookingOrder!show.action?id=%{id}">详情</s:a>
					</td>
				</tr>
			</s:iterator>
			<tr>
				<td colspan="10" style="text-align: center;"><%@ include file="/manage/system/pager.jsp"%></td>
			</tr>
		</table>
		
		<%-- <div class="alert alert-info" style="text-align: left;font-size: 14px;margin: 2px 0px;">
			图标含义：<BR>
			<img alt="新增" src="<%=request.getContextPath() %>/resource/images/action_add.gif">：未审核、未支付
			<img alt="已上架" src="<%=request.getContextPath() %>/resource/images/action_check.gif">：已归档
			<img alt="已下架" src="<%=request.getContextPath() %>/resource/images/action_delete.gif">：已取消
		</div> --%>

	</s:form>
	<div id="cancelBox" style="display: none;">
	<form id="cancelForm" action="" method="post" class="form-horizontal" >
		<div class="control-group">
			<label class="control-label">取消原因:</label>
			<div class="controls">
				<select id="reason" name="reason">
					<option value="">选择取消原因</option>
					<option value="B01">用户要求取消</option>
					<option value="B02">信息有问题，无法审核通过</option>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">取消详情:</label>
			<div class="controls">
				<textarea id="remark" name="remark" cols="60"></textarea>
			</div>
		</div>
	</form>
	</div>
	
	<div id="dispatchBox" style="display: none;">
	<form id="dispatchForm" action="" method="post" class="form-horizontal" >
		<div class="control-group">
			<label class="control-label">指定人员:</label>
			<div class="controls">
				<select id="username" name="username">
					<option value="">选择人员</option>
					<c:forEach items="${userList }" var="u">
					<option value="${u.id }">${u.username}(${u.nickname })</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">上门时间:</label>
			<div class="controls">
				<input readonly id="time" name="time">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<textarea id="remark" name="remark" cols="60"></textarea>
			</div>
		</div>
	</form>
	</div>
</body>
</html>
