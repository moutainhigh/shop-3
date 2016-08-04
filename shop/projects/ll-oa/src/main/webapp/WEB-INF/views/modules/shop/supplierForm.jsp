<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>供应商管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#value").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/shop/supplier/">供应商列表</a></li>
		<li class="active"><a href="${ctx}/shop/supplier/form?id=${supplier.id}">供应商<shiro:hasPermission name="shop:supplier:edit">${not empty supplier.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="shop:supplier:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="supplier" action="${ctx}/shop/supplier/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">简称:</label>
			<div class="controls">
				<form:input path="shortName" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">级别:</label>
			<div class="controls">
				<form:select id="level" path="level" class="input-medium" style="width:220px;"><form:options items="${fns:getDictList('shop_supplier_level')}" htmlEscape="false" itemValue="value" itemLabel="label"/></form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">省/市/区:</label>
			<div class="controls">
				<form:select id="provinceId" path="province.id" class="input-medium" style="width:220px;"><form:option value="" label=""/><form:options items="${provinces}" htmlEscape="false" itemValue="id" itemLabel="name"/></form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">详细地址:</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">座机:</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" type="number" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系人:</label>
			<div class="controls">
				<form:input path="contactName" htmlEscape="false" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">联系人电话:</label>
			<div class="controls">
				<form:input path="contactMobile" htmlEscape="false" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">主营业务:</label>
			<div class="controls">
				<form:textarea path="business"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="shop:supplier:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>