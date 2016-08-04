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
		<li class="active"><a href="${ctx}/shop/sp/">供应商-商品列表</a></li>
		<shiro:hasPermission name="shop:sp:edit"><li><a href="${ctx}/shop/sp/form">供应商-商品添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="supplierProduct" action="${ctx}/shop/sp/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		&nbsp;&nbsp;<label>状态 ：</label><form:select path="state" style="width:220px;" class="required"><form:option value="" label=""/><form:options items="${fns:getDictList('shop_product_state')}" itemValue="value" itemLabel="label"/></form:select>
		&nbsp;&nbsp;<label>供应商 ：</label><form:select id="supplierId" path="supplier.id" class="input-medium" style="width:220px;"><form:option value="" label=""/><form:options items="${supplierList}" itemValue="id" itemLabel="name" htmlEscape="false"/></form:select>
		&nbsp;&nbsp;<label>产品名称 ：</label><form:input path="productName" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>供应商-商品</th>
				<th>产品名称</th>
				<th>产品编码</th>
				<th>出厂价</th>
				<th>淘宝价</th>
				<th>中国道具网价</th>
				<th>婚品汇价</th>
				<th>海洲价</th>
				<th>实体价</th>
				<th>其它价</th>
				<th>颜色</th>
				<th>尺寸</th>
				<th>材质</th>
				<th>长宽高</th>
				<shiro:hasPermission name="shop:sp:edit">
					<th>操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="sp">
			<tr class="<c:if test="${sp.state eq 0 }">success</c:if><c:if test="${sp.state eq 1 }">error</c:if><c:if test="${sp.state eq 9 }">warning</c:if>">
				<td><a href="javascript:" onclick="$('#supplierId').val('${sp.supplier.id}');$('#searchForm').submit();return false;">${sp.supplier.name}</a></td>
				<td>${sp.productName}</td>
				<td>${sp.productCode}</td>
				<td>${sp.price}</td>
				<td>${sp.price1}</td>
				<td>${sp.price2}</td>
				<td>${sp.price3}</td>
				<td>${sp.price4}</td>
				<td>${sp.price5}</td>
				<td>${sp.price99}</td>
				<td>${sp.color}</td>
				<td>${sp.size}</td>
				<td>${sp.material}</td>
				<td>${sp.length} ${sp.width} ${sp.height}</td>
				<shiro:hasPermission name="shop:sp:edit"><td>
    				<a href="${ctx}/shop/sp/form?id=${sp.id}">修改</a>
					<a href="${ctx}/shop/sp/delete?id=${sp.id}" onclick="return confirmx('确认要删除该供应商吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>