<%@page import="net.jeeshop.core.ManageContainer"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
.f {
	font-size: 12px;
	font-family: serif;
	color: red;
}

.len {
	width: 160px;
}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/js/jquery-1.9.1.min.js"></script>
<script src="<%=request.getContextPath()%>/resource/jquery/jquery-migrate-1.1.1.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$("#username").focus();

		if (top.location != self.location) {
			top.location = self.location;
		}
	});
</script>
</head>
<body>
	<jsp:forward page="manage/system/index.jsp"></jsp:forward>
</body>
</html>
