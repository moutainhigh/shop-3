<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品图片管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic }/kindeditor/themes/default/default.css" />
	
	<style type="text/css">
	ol {
		list-style-type: none;
		margin: 0;
		width: 100%;
	}
	
	ol li {
		width: 180px;
		float: left;
	}
	</style>
	<script src="${ctxStatic }/kindeditor/kindeditor-min.js"></script>
	<script src="${ctxStatic }/kindeditor/lang/zh_CN.js"></script>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#name").focus();
			var message = $('#message').val();
			if(typeof message != 'undefined' && message)
				$("#messageBox").show();
			/* KindEditor.ready(function(K) {
                window.editor = K.create('#content', {
                	uploadJson: '${pageContext.request.contextPath}/servlet/upload?prefix=attached/images/${product.id }/',
                    fileManagerJson : '${pageContext.request.contextPath}/servlet/kindeditor?prefix=attached/images/${product.id }/',
                	allowFileManager : true
                });
	        }); */
	
		});
	</script>
	
</head>
<body>
	<form:form id="inputForm" modelAttribute="product" action="${ctx}/shop/product/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<input type="hidden" name="type" value="${type }">
		<input type="hidden" id="message" name="message" value="${message }">
		<%-- <tags:message content="${message}"/> --%>
		<div id="messageBox" class="alert alert-info hide"><button data-dismiss="alert" class="close">×</button>${message}</div>
		<div class="control-group">
			<label class="control-label"><c:if test="${'images' eq type }">产品主图</c:if><c:if test="${'details' eq type }">产品详情图</c:if>:</label>
			<div class="controls">
				<form:hidden path="${type }" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<tags:kindeditor input="${type }" prefix="attached/${type }/${product.id }/" base="${fns:getConfig('QN_DOMAIN') }/" size="50"></tags:kindeditor>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="shop:product:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>