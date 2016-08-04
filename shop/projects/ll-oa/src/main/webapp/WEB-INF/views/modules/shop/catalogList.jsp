<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>目录管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#treeTable").treeTable({expandLevel : 3});
		});
    	function updateSort() {
			loading('正在提交，请稍等...');
	    	$("#listForm").attr("action", "${ctx}/shop/catalog/updateSort");
	    	$("#listForm").submit();
    	}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/shop/catalog/">目录列表</a></li>
		<shiro:hasPermission name="shop:catalog:edit"><li><a href="${ctx}/shop/catalog/form">目录添加</a></li></shiro:hasPermission>
	</ul>
	<tags:message content="${message}"/>
	<form id="listForm" method="post">
		<table id="treeTable" class="table table-striped table-bordered table-condensed">
			<tr>
				<th>目录名称</th>
				<th>目录编码</th>
				<th style="text-align: center;">排序</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${list}" var="catalog">
				<tr id="${catalog.id}" pId="${catalog.parent.id ne 1?catalog.parent.id:'0'}">
					<td><a href="${ctx}/shop/catalog/form?id=${catalog.id}">${catalog.name}</a></td>
					<td><a href="${ctx}/shop/catalog/form?id=${catalog.id}">${catalog.code}</a></td>
					<td style="text-align:center;">
						<shiro:hasPermission name="shop:catalog:edit">
							<input type="hidden" name="ids" value="${catalog.id}"/>
							<input name="sorts" type="text" value="${catalog.sort}" style="width:50px;margin:0;padding:0;text-align:center;">
						</shiro:hasPermission><shiro:lacksPermission name="shop:catalog:edit">
							${catalog.sort}
						</shiro:lacksPermission>
					</td>
					<td>
						<shiro:hasPermission name="shop:catalog:edit">
							<a href="${ctx}/shop/catalog/form?id=${catalog.id}">修改</a>
							<a href="${ctx}/shop/catalog/delete?id=${catalog.id}" onclick="return confirmx('要删除该目录及所有子目录项吗？', this.href)">删除</a>
							<a href="${ctx}/shop/catalog/form?parent.id=${catalog.id}">添加下级目录</a> 
						</shiro:hasPermission>
					</td>
				</tr>
			</c:forEach>
		</table>
		<shiro:hasPermission name="shop:catalog:edit"><div class="form-actions pagination-left">
			<input id="btnSubmit" class="btn btn-primary" type="button" value="保存排序" onclick="updateSort();"/>
		</div></shiro:hasPermission>
	</form>
</body>
</html>