<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>客户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
		$(function(){
			$('#provinceCode').change(function(){
				var _this = $(this);
				var code = _this.val();
				$.post('${ctx}/sys/area/findByParentCode', {
					parentCode: code
				}, function(data){
					$('#cityCode').find('option:gt(0)').remove();
					var s = '';
					for(var i=0; i<data.length; i++) {
						var area = data[i];
						s += '<option value="'+ area.code +'">'+area.name+'</option>';
					}
					$('#cityCode').append(s);
				});
			});
			
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出客户数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action", "${ctx}/shop/customer/export");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			
			$("#btnSync").click(function(){
				top.$.jBox.confirm("确认要同步客户数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action", "${ctx}/shop/customer/sync");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});
			
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/shop/customer/">客户列表</a></li>
		<shiro:hasPermission name="shop:customer:edit"><li><a href="${ctx}/shop/customer/form">客户添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="customer" action="${ctx}/shop/customer/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		&nbsp;&nbsp;<label>省份 ：</label><form:select id="provinceCode" path="provinceCode" class="input-medium" style="width:160px;"><form:option value="" label=""/><form:options items="${provinces}" htmlEscape="false" itemValue="code" itemLabel="name"/></form:select>
		&nbsp;&nbsp;<label>城市 ：</label><form:select id="cityCode" path="cityCode" class="input-medium" style="width:160px;"><form:option value="" label=""/><form:options items="${cities}" htmlEscape="false" itemValue="code" itemLabel="name"/></form:select>
		&nbsp;&nbsp;<label>姓名 ：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>
		<br/><label>手机号 ：</label><form:input path="mobile" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;&nbsp;<label>公司名 ：</label><form:input path="company" htmlEscape="false" maxlength="50" class="input-medium"/>
		&nbsp;&nbsp;<label>地址 ：</label><form:input path="address" htmlEscape="false" maxlength="50" class="input-medium" />
		&nbsp;<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>
		<shiro:hasPermission name="shop:customer:audit">
		&nbsp;<input id="btnSync" class="btn btn-primary" type="button" value="同步"/>
		&nbsp;<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>
		</shiro:hasPermission>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="90">手机号</th>
				<th width="90">姓名</th>
				<th width="150">公司名称</th>
				<th width="90">QQ号</th>
				<th width="180">详细地址</th>
				<th width="60">座机</th>
				<shiro:hasPermission name="shop:customer:edit">
					<th width="70">操作</th>
				</shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="customer">
			<tr>
				<td>${customer.mobile}</td>
				<td>${customer.name}</td>
				<td>${customer.company}</td>
				<td>${customer.qq}</td>
				<td>${customer.province}${customer.city}${customer.address}</td>
				<td>${customer.tel}</td>
				<shiro:hasPermission name="shop:customer:edit"><td>
    				<a href="${ctx}/shop/customer/form?id=${customer.id}">修改</a>
					<a href="${ctx}/shop/customer/delete?id=${customer.id}" onclick="return confirmx('确认要删除该客户吗？', this.href)">删除</a>
    				<%-- <a href="${ctx}/shop/sp/form?customer.id=${customer.id}">添加产品</a> --%>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>