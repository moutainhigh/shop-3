<%@page import="net.jeeshop.core.ManageContainer"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/resource/common_html_meat.jsp"%>
<%@ include file="/manage/system/common.jsp"%>
</head>

<body>
	<table class="table table-bordered table-hover">
		<thead>
			<tr>
				<th>缓存Id</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${memcacheIdList}" var="d">
			<tr>
			    <td>${d}
                </td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
