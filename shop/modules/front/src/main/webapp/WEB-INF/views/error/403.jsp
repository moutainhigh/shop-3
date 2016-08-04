<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%response.setStatus(200);%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="decorator" content="account_default_new"/>
    <title>404 - 页面不存在</title>
</head>
<body style="background:white; width:960px;margin:0 auto;">
	<div class="four" align="center"><img src="${ctx }/images/403.jpg" width="717" height="453" /></div>
</body>
</html>