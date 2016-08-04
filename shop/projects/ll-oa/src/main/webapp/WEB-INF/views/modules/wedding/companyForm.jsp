<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>婚庆公司管理</title>
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
			
			$('#cityCode').change(function(){
				var _this = $(this);
				var code = _this.val();
				$.post('${ctx}/sys/area/findByParentCode', {
					parentCode: code
				}, function(data){
					$('#countyCode').find('option:gt(0)').remove();
					var s = '';
					for(var i=0; i<data.length; i++) {
						var area = data[i];
						s += '<option value="'+ area.code +'">'+area.name+'</option>';
					}
					$('#countyCode').append(s);
				});
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/wedding/company/">婚庆公司列表</a></li>
		<li class="active"><a href="${ctx}/wedding/company/form?id=${weddingCompany.id}">婚庆公司<shiro:hasPermission name="wedding:company:edit">${not empty weddingCompany.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="wedding:company:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="weddingCompany" action="${ctx}/wedding/company/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">用户名:</label>
			<div class="controls">
				<label class="control-label">${weddingCompany.username }</label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">姓名:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">公司名称:</label>
			<div class="controls">
				<form:input path="company" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">手机号:</label>
			<div class="controls">
				<form:input path="mobile" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">QQ号:</label>
			<div class="controls">
				<form:input path="qq" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">座机号:</label>
			<div class="controls">
				<form:input path="tel" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">省/市/区:</label>
			<div class="controls">
				<form:select path="provinceCode" class="input-medium" style="width:220px;"><form:option value="" label=""/><form:options items="${provinces}" htmlEscape="false" itemValue="code" itemLabel="name"/></form:select>
				<form:select path="cityCode" class="input-medium" style="width:220px;"><form:option value="" label=""/><form:options items="${cities}" htmlEscape="false" itemValue="code" itemLabel="name"/></form:select>
				<form:select path="countyCode" class="input-medium" style="width:220px;"><form:option value="" label=""/><form:options items="${counties}" htmlEscape="false" itemValue="code" itemLabel="name"/></form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">详细地址:</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注:</label>
			<div class="controls">
				<form:textarea path="remarks"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">注册时间:</label>
			<div class="controls">
				<label class="lbl"><fmt:formatDate value="${weddingCompany.registDate}" type="both" dateStyle="full"/></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最后登陆:</label>
			<div class="controls">
				<label class="lbl">IP: &nbsp;&nbsp;&nbsp;&nbsp;时间：</label>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="wedding:company:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>