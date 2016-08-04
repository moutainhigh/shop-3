<%@page import="net.jeeshop.services.manage.order.bean.Order"%>
<%@page import="net.jeeshop.core.KeyValueHelper"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page session="false"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/resource/common_html_meat.jsp"%>
<%@ include file="/manage/system/common.jsp"%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/PrintArea/css/jquery.printarea.css">
<script type="text/javascript" src="<%=request.getContextPath() %>/resource/PrintArea/js/jquery.printarea.js"></script>
<script type="text/javascript">
$(function(){
	$('#btnPrint').click(function(){
		$('#printArea').printArea();
	});
});
</script>
</head>
<body>
<div class="container">
	<div class="row">
		<div class="span12">
			<input type="button" id="btnPrint" value="打印" class="btn btn-primary"/>
		</div>
	</div>
	<div class="row" id="printArea">
		<div class="span12">
			<table class="table table-bordered">
				<tr><td colspan="11">订单信息</td></tr>
				<tr>
					<td nowrap="nowrap">订单号</td>
					<td nowrap="nowrap"><s:property value="e.no"/></td>
					<td nowrap="nowrap">订单总金额</td>
					<td nowrap="nowrap"><s:property value="e.amount"/></td>
					<td nowrap="nowrap">数量</td>
					<td nowrap="nowrap" colspan="5"><s:property value="e.quantity"/></td>
				</tr>
				<tr>
					<td nowrap="nowrap">下单日期</td>
					<td nowrap="nowrap"><s:property value="e.createdate"/></td>
					<td nowrap="nowrap">配送费</td>
					<td nowrap="nowrap"><s:property value="e.fee"/></td>
					<td nowrap="nowrap"></td>
					<td nowrap="nowrap" colspan="5"></td>
				</tr>
				<tr>
					<td nowrap="nowrap">交易平台</td>
					<td nowrap="nowrap" colspan="21"><%=SystemManager.systemSetting.getName() %>
						(<%=SystemManager.systemSetting.getWww() %>)
					</td>
				</tr>
				<tr>
					<td nowrap="nowrap">收货人</td>
					<td colspan="21">
						<s:property value="e.ordership.province" />,
						<s:property value="e.ordership.city" />,
						<s:property value="e.ordership.area" />,
						<s:property value="e.ordership.shipaddress" />,
						<s:property value="e.ordership.shipname" />(收)
					</td>
				</tr>
				<tr>
					<td>备注（客户详细需求）:</td>
					<td colspan="10"><s:property value="e.remark"/></td>
				</tr>
				<tr><td colspan="11">&nbsp;</td></tr>
				
				<tr>
					<td width="50px">序号</td>
		<!-- 			<td>商品编号</td> -->
					<td>商品名称</td>
					<td>商品图片</td>
					<td>数量</td>
					<td>单价</td>
					<td>配送费</td>
					<td>小计</td>
					
					<td>供货商</td>
					<td>物流公司</td>
					<td>物流编号</td>
				</tr>
				<s:iterator value="e.orderdetail" status="i" var="odetail">
					<tr>
						<td><s:property value="productID"/></td>
						<td width="200px"><s:property value="productName"/><br/><s:property value="specInfo"/></td>
						<td width="100px"><img width="100" height="100" alt="<s:property value="productName"/>" src="${IMAGE_ROOT_PATH }/<s:property value="picture"/>-px350"></td>
						<td><s:property value="number" /></td>
						<td>￥<s:property value="price" /></td>
						<td>￥<s:property value="fee" /></td>
						<td>￥<s:property value="total0" /></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
				</s:iterator>
			</table>
	
		</div>
	</div>	
</div>
	
</body>
</html>
