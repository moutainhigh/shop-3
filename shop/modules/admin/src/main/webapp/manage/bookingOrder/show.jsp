<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/resource/common_html_meat.jsp"%>
<%@ include file="/manage/system/common.jsp"%>
<%@ include file="/resource/common_html_validator.jsp"%>
</head>
<body>
<form id="inputForm" class="breadcrumb form-horizontal">
	<div class="control-group">
		<label class="control-label">预约人:</label>
		<div class="controls">
			<label class="lbl">${e.name}</label>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">手机号:</label>
		<div class="controls">
			<label class="lbl">${e.mobile}</label>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">预约时间:</label>
		<div class="controls">
			<label class="lbl"><fmt:formatDate value="${e.bookingTime}" type="both" dateStyle="full"/></label>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">预约地址:</label>
		<div class="controls">
			<label class="lbl">${e.provinceName}-${e.cityName}-${e.countyName} ${e.address}</label>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">详细需求:</label>
		<div class="controls">
			<label class="lbl">${e.demand}</label>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">预约状态:</label>
		<div class="controls">
			<label class="lbl"><c:if test="${e.status == 0 }">新建</c:if>
					<c:if test="${e.status == 1 }">已收单</c:if>
					<c:if test="${e.status == 2 }">已派单</c:if>
					<c:if test="${e.status == 3 }">已完成</c:if>
					<c:if test="${e.status == 4 }">已评价</c:if>
					<c:if test="${e.status == 9 }">已取消</c:if></label>
		</div>
	</div>
	<div class="control-group">
		<label class="control-label">创建信息:</label>
		<div class="controls">
			<label class="lbl">IP: ${e.id}&nbsp;&nbsp;&nbsp;&nbsp;创建时间：<fmt:formatDate value="${e.createTime}" type="both" dateStyle="full"/></label> 
		</div>
	</div>
</form>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
	<thead><tr><th>操作人</th><th>操作时间</th><th>操作内容</th></tr></thead>
	<tbody>
	<c:forEach items="${e.bookingOrderLogList}" var="b">
		<tr>
			<td>${b.operateName}</td>
			<td><fmt:formatDate value="${b.createTime}" type="both" dateStyle="full"/></td>
			<td>${b.description}</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
</body>
</html>
