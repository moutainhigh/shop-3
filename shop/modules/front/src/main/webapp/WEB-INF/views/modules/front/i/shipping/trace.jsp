<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="decorator" content="account_default_new" />
<title>首页</title>
</head>
<body style="background: white; width: 960px; margin: 0 auto;">
	<div id="success_show" style="text-align: center; font-size: 18px; padding-top: 10px;">
		<c:forEach items="${ecList }" var="ec">
		<c:if test="${ec.code eq order.expressCompanyName }"></c:if>
		官方快递查询：<a href="${ec.url }" target="_about">${ec.name }官方网站<br></a>
		快递客服电话 ：<span style="color: ##ed145b;">${ec.tel }</span>
		</c:forEach>
	</div>
	<c:if test="${info !=null and info.message eq 'ok' and info.data != null and info.data.size() > 0 }">
	<table style="margin-bottom: 20px;">
		<tbody>
			<tr>
				<th width="126">操作时间</th>
				<th>操作记录</th>
				<!-- <th>经手站点</th> -->
			</tr>
			<c:forEach items="${info.data }" var="d">
			<tr>
				<td>${d.time }</td>
				<td>${d.context }</td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	</c:if>
	<div class="track_tip">
		<p>本页面物流查询信息由快递公司提供</p>
		<ol>
			<li>物流快递信息有可能存在延迟，可能会导致您的物流信息长时间没有更新，敬请耐心等待。（延迟时间可能从1天到3天不等，EMS快递的物流配送信息可能最多可能有1周左右延迟）</li>
			<li>如果最先配送的物流没法送达配送地址，将对商品进行二次转运，转运后的商品可能暂时没有物流信息，请耐心等待，我们会尽快更新。</li>
		</ol>
	</div>
</body>
</html>