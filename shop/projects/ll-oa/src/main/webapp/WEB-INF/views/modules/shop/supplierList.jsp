<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>供应商管理</title>
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
		<li class="active"><a href="${ctx}/shop/supplier/">供应商列表</a></li>
		<shiro:hasPermission name="shop:supplier:edit"><li><a href="${ctx}/shop/supplier/form">供应商添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="supplier" action="${ctx}/shop/supplier/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		&nbsp;&nbsp;<label>省份 ：</label><form:select id="provinceId" path="province.id" class="input-medium" style="width:220px;"><form:option value="" label=""/><form:options items="${provinces}" htmlEscape="false" itemValue="id" itemLabel="name"/></form:select>
		&nbsp;&nbsp;<label>名称 ：</label><form:input id="name" path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>名称</th>
				<th>简称</th>
				<th width="30">等级</th>
				<th>省份</th>
				<th>地址</th>
				<th width="60">座机</th>
				<shiro:hasPermission name="shop:supplier:edit">
					<th width="120">操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="supplier">
			<tr>
				<td><a href="javascript:" onclick="$('#name').val('${supplier.name}');$('#searchForm').submit();return false;">${supplier.name}</a></td>
				<td>${supplier.shortName}</td>
				<td><a href="javascript:" onclick="$('#level').val('${supplier.level}');$('#searchForm').submit();return false;">${fns:getDictLabel(supplier.level, 'shop_supplier_level', '')}</a></td>
				<td><a href="javascript:" onclick="$('#provinceId').val('${supplier.province.id}');$('#searchForm').submit();return false;">${supplier.province.name}</a></td>
				<td>${supplier.address}</td>
				<td>${supplier.phone}</td>
				<shiro:hasPermission name="shop:supplier:edit"><td>
    				<a href="${ctx}/shop/supplier/form?id=${supplier.id}">修改</a>
					<a href="${ctx}/shop/supplier/delete?id=${supplier.id}" onclick="return confirmx('确认要删除该供应商吗？', this.href)">删除</a>
    				<a href="${ctx}/shop/sp/form?supplier.id=${supplier.id}">添加产品</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>