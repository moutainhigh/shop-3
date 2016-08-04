<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="ctxStatic" value="${pageContext.request.contextPath}/resource"/>
<html>
<head>
	<title> 登录</title>
	
	<meta http-equiv="Cache-Control" content="no-store" /><meta http-equiv="Pragma" content="no-cache" /><meta http-equiv="Expires" content="0" />
	<meta name="author" content="http://tuisitang.iteye.com"/><meta http-equiv="X-UA-Compatible" content="IE=7,IE=9,IE=10" />
	<script src="${ctxStatic}/js/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery/jquery-migrate-1.1.1.min.js" type="text/javascript"></script>
	<link href="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctxStatic}/jquery-validation/1.11.0/jquery.validate.method.min.js" type="text/javascript"></script>
	<link href="${ctxStatic}/bootstrap/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<style type="text/css">
      html,body,table{background-color:#f5f5f5;width:100%;height:500px;text-align:center;}.form-signin-heading{font-size:36px;margin-bottom:20px;color:#0663a2;}
      .form-signin{position:relative;text-align:left;width:300px;padding:25px 29px 29px;margin:0 auto 20px;background-color:#fff;border:1px solid #e5e5e5;
        	-webkit-border-radius:5px;-moz-border-radius:5px;border-radius:5px;-webkit-box-shadow:0 1px 2px rgba(0,0,0,.05);-moz-box-shadow:0 1px 2px rgba(0,0,0,.05);box-shadow:0 1px 2px rgba(0,0,0,.05);}
      .form-signin .checkbox{margin-bottom:10px;color:#0663a2;} .form-signin .input-label{font-size:16px;line-height:23px;color:#999;}
      .form-signin .input-block-level{font-size:16px;height:auto;margin-bottom:15px;padding:7px;*width:283px;*padding-bottom:0;_padding:7px 7px 9px 7px;}
      .form-signin .btn.btn-large{font-size:16px;}
      .form-signin div.validateCode {padding-bottom:15px;} .mid{vertical-align:middle;}
      .header{height:60px;padding-top:30px;} .alert{position:relative;width:300px;margin:0 auto;*padding-bottom:0px;}
      label.error{background:none;padding:2px;font-weight:normal;color:inherit;margin:0;}
    </style>
	<script type="text/javascript">
		$(document).ready(function() {
			$("form").validate({
				rules: {
				},
				messages: {
					username: {required: "请填写用户名."},
					password: {required: "请填写密码."}
				},
				errorLabelContainer: "#messageBox",
				errorPlacement: function(error, element) {
					error.appendTo($("#loginError").parent());
				} 
			});
		});
		// 如果在框架中，则跳转刷新上级页面
		if(self.frameElement && self.frameElement.tagName=="IFRAME"){
			parent.location.reload();
		}
	</script>
</head>
<body>
	<!--[if lte IE 6]><br/><div class='alert alert-block' style="text-align:left;padding-bottom:10px;"><a class="close" data-dismiss="alert">x</a><h4>温馨提示：</h4><p>你使用的浏览器版本过低。为了获得更好的浏览体验，我们强烈建议您 <a href="http://browsehappy.com" target="_blank">升级</a> 到最新版本的IE浏览器，或者使用较新版本的 Chrome、Firefox、Safari 等。</p></div><![endif]-->
	<div class="header"><%String error = (String) request.getAttribute("errorMsg");%>
		<div id="messageBox" class="alert alert-error <%=error==null?"hide":""%>"><button data-dismiss="alert" class="close">×</button>
			<label id="loginError" class="error"><%=error==null?"":"com.irtone.modules.sys.security.CaptchaException".equals(error)?"验证码错误, 请重试.":"用户或密码错误, 请重试." %></label>
		</div>
	</div>
	<h1 class="form-signin-heading">报喜了</h1>
	<%-- <form id="loginForm" class="form-signin" action="${ctx}/login" method="post"> --%>
	<s:form action="user!login.action" theme="simple" namespace="/manage" cssClass="form-signin">
		<label class="input-label" for="username">登录名</label>
		<input type="text" id="username" name="e.username" class="input-block-level required" value="${username}">
		<label class="input-label" for="password">密码</label>
		<input type="password" id="password" name="e.password" class="input-block-level required">
		<input class="btn btn-primary" type="submit" value="登 录"/>&nbsp;&nbsp;
		<label for="rememberMe" title="下次不需要再登录"><input type="checkbox" id="rememberMe" name="rememberMe"/> 记住我（公共场所慎用）</label>
	</s:form>
	Copyright &copy; 2015 <a href="${pageContext.request.contextPath}">报喜了</a> - Powered By <a href="http://www.baoxiliao.com" target="_blank">报喜了</a> 1.0
</body>
</html>