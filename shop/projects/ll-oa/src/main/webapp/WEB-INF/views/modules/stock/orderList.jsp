<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>库存订单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/stock/order/">库存订单列表</a></li>
		<shiro:hasPermission name="stock:order:edit"><li><a href="${ctx}/stock/order/form">库存订单添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="stockOrder" action="${ctx}/stock/order/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		&nbsp;&nbsp;<label>订单编号 ：</label><form:input path="no" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="60">ID</th>
				<th width="120">订单编号</th>
				<th width="60">出/入库</th>
				<th width="90">操作人</th>
				<th width="90">操作时间</th>
				<th width="180">备注</th>
				<shiro:hasPermission name="stock:order:edit">
					<th width="70">操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="stockOrder">
			<tr>
				<td>${stockOrder.id}</td>
				<td>${stockOrder.no}</td>
				<td>${stockOrder.type}</td>
				<td>${stockOrder.operator.name}</td>
				<td>${stockOrder.operateDate}</td>
				<td>${stockOrder.remarks}</td>
				<shiro:hasPermission name="stock:order:edit"><td>
    				<a href="${ctx}/stock/order/form?id=${stockOrder.id}">修改</a>
					<a href="${ctx}/stock/order/delete?id=${stockOrder.id}" onclick="return confirmx('确认要删除该库存订单吗？', this.href)">删除</a>
    				<%-- <a href="${ctx}/shop/sp/form?stockOrder.id=${stockOrder.id}">添加产品</a> --%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>