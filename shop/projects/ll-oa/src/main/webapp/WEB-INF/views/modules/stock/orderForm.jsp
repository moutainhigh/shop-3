<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>客户管理</title>
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
				var name = $('#provinceCode option:selected').text();
				$('#province').val(name);
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
				var name = $('#cityCode option:selected').text();
				$('#city').val(name);
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
			
			$('#countyCode').change(function(){
				var _this = $(this);
				var code = _this.val();
				var name = $('#countyCode option:selected').text();
				$('#county').val(name);
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/shop/customer/">客户列表</a></li>
		<li class="active"><a href="${ctx}/shop/customer/form?id=${customer.id}">客户<shiro:hasPermission name="shop:customer:edit">${not empty customer.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="shop:customer:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="customer" action="${ctx}/shop/customer/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">编号:</label>
			<div class="controls">
				<label class="control-label">${customer.uid }</label>
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
				<form:input path="qq" htmlEscape="false" maxlength="50" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">座机号:</label>
			<div class="controls">
				<form:input path="tel" htmlEscape="false" maxlength="50" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">省/市/区:</label>
			<div class="controls">
				<form:select path="provinceCode" class="input-medium" style="width:220px;"><form:option value="" label=""/><form:options items="${provinces}" htmlEscape="false" itemValue="code" itemLabel="name"/></form:select>
				<form:select path="cityCode" class="input-medium" style="width:220px;"><form:option value="" label=""/><form:options items="${cities}" htmlEscape="false" itemValue="code" itemLabel="name"/></form:select>
				<form:select path="countyCode" class="input-medium" style="width:220px;"><form:option value="" label=""/><form:options items="${counties}" htmlEscape="false" itemValue="code" itemLabel="name"/></form:select>
				<form:hidden path="province"/>
				<form:hidden path="city"/>
				<form:hidden path="county"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">详细地址:</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">创建时间:</label>
			<div class="controls">
				<input id="createDate" name="createDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="<fmt:formatDate value="${customer.createDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属业务员:</label>
			<div class="controls">
				<form:radiobuttons path="salesman.id" items="${salesmans }" itemValue="id" itemLabel="name"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">注册时间:</label>
			<div class="controls">
				<label class="lbl"><fmt:formatDate value="${customer.registDate}" type="both" dateStyle="full"/></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最后登陆:</label>
			<div class="controls">
				<label class="lbl">IP: ${customer.loginIp }&nbsp;&nbsp;&nbsp;&nbsp;地区: ${customer.loginArea }&nbsp;&nbsp;&nbsp;&nbsp;时间：<fmt:formatDate value="${customer.loginDate}" type="both" dateStyle="full"/></label>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="shop:customer:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>