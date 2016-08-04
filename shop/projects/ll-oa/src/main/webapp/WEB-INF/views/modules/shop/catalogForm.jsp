<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>目录管理</title>
	<meta name="decorator" content="default"/>
	
	<link rel="stylesheet" href="${ctxStatic }/kindeditor/themes/default/default.css" />
	<script src="${ctxStatic }/kindeditor/kindeditor-min.js"></script>
	<script src="${ctxStatic }/kindeditor/lang/zh_CN.js"></script>

	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
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
		<li><a href="${ctx}/shop/catalog/">目录列表</a></li>
		<li class="active"><a href="${ctx}/shop/catalog/form?id=${catalog.id}&parent.id=${catalog.parent.id}">目录<shiro:hasPermission name="shop:catalog:edit">${not empty catalog.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="shop:catalog:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="catalog" action="${ctx}/shop/catalog/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">上级目录:</label>
			<div class="controls">
                <tags:treeselect id="catalog" name="parent.id" value="${catalog.parent.id}" labelName="parent.name" labelValue="${catalog.parent.name}"
					title="目录" url="/shop/catalog/treeData" extId="${catalog.id}" cssClass="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">目录编码:</label>
			<div class="controls">
				<form:input path="code" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">目录名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">目录图片:</label>
			<div class="controls">
				<form:hidden path="image" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<tags:kindeditor input="image" prefix="attached/catalog/"></tags:kindeditor>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">描述:</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">关键字:</label>
			<div class="controls">
				<form:input path="keywords" htmlEscape="false" maxlength="200"/>
				<span class="help-inline">填写描述及关键字，有助于搜索引擎优化</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">排序:</label>
			<div class="controls">
				<form:input path="sort" htmlEscape="false" maxlength="11" class="required digits"/>
				<span class="help-inline">目录的排列次序</span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="shop:catalog:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>