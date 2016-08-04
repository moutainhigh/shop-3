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
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/select2/select2.css">
<link href="<%=request.getContextPath() %>/resource/jquery-jbox/Skins/Bootstrap/jbox.css" rel="stylesheet" />

<style>
.simpleOrderReport{
	font-weight: 700;font-size: 16px;color: #f50;
}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resource/select2/select2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/resource/select2/select2_locale_zh-CN.js"></script>

<script type="text/javascript" src="<%=request.getContextPath() %>/resource/jquery-jbox/jquery.jBox-2.3.min.js"></script>
<script src="<%=request.getContextPath() %>/resource/jquery-jbox/i18n/jquery.jBox-zh-CN.min.js" type="text/javascript"></script>


</head>
<body>
<s:form action="order" namespace="/manage" method="post" theme="simple" name="form1">
	<s:hidden name="e.id"/>
		<div id="buttons" style="text-align: center;border-bottom: 1px solid #ccc;padding: 5px;">
		<div id="updateMsg"><font color='red'><s:property value="updateMsg" /></font></div>
			
			<s:if test="e.paystatus.equals(\"y\")">
				<s:if test="e.status.equals(\"init\")">
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=pass" onclick="return onSubmit(this);" 
						value="已审核" class="btn btn-primary"><%=Order.order_status_pass_chinese %></a>
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=send" onclick="return onSubmit(this);" 
						value="已发货" class="btn" disabled="disabled"><%=Order.order_status_send_chinese %></a>
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=sign" onclick="return onSubmit(this);" 
						value="已签收" class="btn" disabled="disabled"><%=Order.order_status_sign_chinese %></a>
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=file" onclick="return onSubmit(this);" 
						value="已归档" class="btn" disabled="disabled"><%=Order.order_status_file_chinese %></a>
					
					<input type="button" id="addPayBtn" onclick="return addPayFunc(this);" value="添加支付记录" class="btn btn-warning"/>
					<input type="button" id="updatePayMoneryBtn" onclick="return updatePayMoneryFunc(this);" value="修改订单总金额" class="btn btn-warning"/>
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=cancel" onclick="return onSubmit(this);" value="取消订单" class="btn btn-danger"><%=Order.order_status_cancel_chinese %></a>
				</s:if>
				<s:elseif test="e.status.equals(\"pass\")">
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=pass" onclick="return onSubmit(this);" 
						value="已审核" class="btn" disabled="disabled"><%=Order.order_status_pass_chinese %></a>
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=send" onclick="return onSubmit(this);" 
						value="已发货" class="btn btn-primary"><%=Order.order_status_send_chinese %></a>
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=sign" onclick="return onSubmit(this);" 
						value="已签收" class="btn" disabled="disabled"><%=Order.order_status_sign_chinese %></a>
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=file" onclick="return onSubmit(this);" 
						value="已归档" class="btn" disabled="disabled"><%=Order.order_status_file_chinese %></a>
					
					<input type="button" id="addPayBtn" onclick="return addPayFunc(this);" value="添加支付记录" class="btn btn-warning" disabled="disabled"/>
					<input type="button" id="updatePayMoneryBtn" onclick="return updatePayMoneryFunc(this);" value="修改订单总金额" class="btn btn-warning" disabled="disabled"/>
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=cancel" onclick="return onSubmit(this);" value="取消订单" class="btn btn-danger"><%=Order.order_status_cancel_chinese %></a>
				</s:elseif>
				<s:elseif test="e.status.equals(\"send\")">
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=pass" onclick="return onSubmit(this);" 
						value="已审核" class="btn" disabled="disabled"><%=Order.order_status_pass_chinese %></a>
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=send" onclick="return onSubmit(this);" 
						value="已发货" class="btn" disabled="disabled"><%=Order.order_status_send_chinese %></a>
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=sign" onclick="return onSubmit(this);" 
						value="已签收" class="btn" disabled="disabled"><%=Order.order_status_sign_chinese %></a>
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=file" onclick="return onSubmit(this);" 
						value="已归档" class="btn" disabled="disabled"><%=Order.order_status_file_chinese %></a>
					
					<input type="button" id="addPayBtn" onclick="return addPayFunc(this);" value="添加支付记录" class="btn btn-warning" disabled="disabled"/>
					<input type="button" id="updatePayMoneryBtn" onclick="return updatePayMoneryFunc(this);" value="修改订单总金额" class="btn btn-warning" disabled="disabled"/>
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=cancel" onclick="return onSubmit(this);" disabled="disabled" 
						value="取消订单" class="btn btn-danger"><%=Order.order_status_cancel_chinese %></a>
				</s:elseif>
				<s:elseif test="e.status.equals(\"sign\")">
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=pass" onclick="return onSubmit(this);" 
						value="已审核" class="btn" disabled="disabled"><%=Order.order_status_pass_chinese %></a>
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=send" onclick="return onSubmit(this);" 
						value="已发货" class="btn" disabled="disabled"><%=Order.order_status_send_chinese %></a>
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=sign" onclick="return onSubmit(this);" 
						value="已签收" class="btn" disabled="disabled"><%=Order.order_status_sign_chinese %></a>
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=file" onclick="return onSubmit(this);" 
						value="已归档" class="btn btn-primary"><%=Order.order_status_file_chinese %></a>
					
					<input type="button" id="addPayBtn" onclick="return addPayFunc(this);" value="添加支付记录" class="btn btn-warning" disabled="disabled"/>
					<input type="button" id="updatePayMoneryBtn" onclick="return updatePayMoneryFunc(this);" value="修改订单总金额" class="btn btn-warning" disabled="disabled"/>
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=cancel" onclick="return onSubmit(this);" disabled="disabled" 
						value="取消订单" class="btn btn-danger"><%=Order.order_status_cancel_chinese %></a>
				</s:elseif>
				<s:elseif test="e.status.equals(\"cancel\")">
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=pass" onclick="return onSubmit(this);" 
						value="已审核" class="btn" disabled="disabled"><%=Order.order_status_pass_chinese %></a>
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=send" onclick="return onSubmit(this);" 
						value="已发货" class="btn" disabled="disabled"><%=Order.order_status_send_chinese %></a>
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=sign" onclick="return onSubmit(this);" 
						value="已签收" class="btn" disabled="disabled"><%=Order.order_status_sign_chinese %></a>
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=file" onclick="return onSubmit(this);" 
						value="已归档" class="btn btn-primary" disabled="disabled"><%=Order.order_status_file_chinese %></a>
					
					<input type="button" id="addPayBtn" onclick="return addPayFunc(this);" value="添加支付记录" class="btn btn-warning" disabled="disabled"/>
					<input type="button" id="updatePayMoneryBtn" onclick="return updatePayMoneryFunc(this);" value="修改订单总金额" class="btn btn-warning" disabled="disabled"/>
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=cancel" onclick="return onSubmit(this);" disabled="disabled" 
						value="取消订单" class="btn btn-danger"><%=Order.order_status_cancel_chinese %></a>
				</s:elseif>
				<s:elseif test="e.status.equals(\"file\")">
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=pass" onclick="return onSubmit(this);" 
						value="已审核" class="btn" disabled="disabled"><%=Order.order_status_pass_chinese %></a>
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=send" onclick="return onSubmit(this);" 
						value="已发货" class="btn" disabled="disabled"><%=Order.order_status_send_chinese %></a>
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=sign" onclick="return onSubmit(this);" 
						value="已签收" class="btn" disabled="disabled"><%=Order.order_status_sign_chinese %></a>
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=file" onclick="return onSubmit(this);" 
						value="已归档" class="btn" disabled="disabled"><%=Order.order_status_file_chinese %></a>
					
					<input type="button" id="addPayBtn" onclick="return addPayFunc(this);" value="添加支付记录" class="btn btn-warning" disabled="disabled"/>
					<input type="button" id="updatePayMoneryBtn" onclick="return updatePayMoneryFunc(this);" value="修改订单总金额" class="btn btn-warning" disabled="disabled"/>
					<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=cancel" onclick="return onSubmit(this);" disabled="disabled" 
						value="取消订单" class="btn btn-danger"><%=Order.order_status_cancel_chinese %></a>
				</s:elseif>
			</s:if>
			<s:elseif test="e.status.equals(\"cancel\")">
				<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=pass" onclick="return onSubmit(this);" 
					value="已审核" class="btn" disabled="disabled"><%=Order.order_status_pass_chinese %></a>
				<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=send" onclick="return onSubmit(this);" 
					value="已发货" class="btn" disabled="disabled"><%=Order.order_status_send_chinese %></a>
				<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=sign" onclick="return onSubmit(this);" 
					value="已签收" class="btn" disabled="disabled"><%=Order.order_status_sign_chinese %></a>
				<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=file" onclick="return onSubmit(this);" 
					value="已归档" class="btn" disabled="disabled"><%=Order.order_status_file_chinese %></a>
				
				<input type="button" id="addPayBtn" onclick="return addPayFunc(this);" value="添加支付记录" class="btn btn-warning" disabled="disabled"/>
				<input type="button" id="updatePayMoneryBtn" onclick="return updatePayMoneryFunc(this);" value="修改订单总金额" class="btn btn-warning" disabled="disabled"/>
				<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=cancel" onclick="return onSubmit(this);" disabled="disabled" 
						value="取消订单" class="btn btn-danger"><%=Order.order_status_cancel_chinese %></a>
			</s:elseif>
			<s:else>
				<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=pass" onclick="return onSubmit(this);" 
					value="已审核" class="btn" disabled="disabled"><%=Order.order_status_pass_chinese %></a>
				<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=send" onclick="return onSubmit(this);" 
					value="已发货" class="btn" disabled="disabled"><%=Order.order_status_send_chinese %></a>
				<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=sign" onclick="return onSubmit(this);" 
					value="已签收" class="btn" disabled="disabled"><%=Order.order_status_sign_chinese %></a>
				<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=file" onclick="return onSubmit(this);" 
					value="已归档" class="btn" disabled="disabled"><%=Order.order_status_file_chinese %></a>
				
				<input type="button" id="addPayBtn" onclick="return addPayFunc(this);" value="添加支付记录" class="btn btn-warning"/>
				<input type="button" id="updatePayMoneryBtn" onclick="return updatePayMoneryFunc(this);" value="修改订单总金额" class="btn btn-warning"/>
				<a href="order!updateOrderStatus.action?e.id=<s:property value="e.id"/>&e.status=cancel" onclick="return onSubmit(this);" 
						value="取消订单" class="btn btn-danger"><%=Order.order_status_cancel_chinese %></a>
			</s:else>
		</div>
	
	<div id="addPayDiv" style="display: none;">
		<table class="table">
			<tr>
				<td colspan="2">
					<h4>添加支付记录</h4>
				</td>
			</tr>
			<tr>
				<td>支付方式</td>
				<td>
					<select name="e.orderpay.paymethod">
						<option value="zfb">支付宝</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>支付金额</td>
				<td>
					<input name="e.orderpay.payamount">
				</td>
			</tr>
			<tr>
				<td>备注</td>
				<td>
					<div class="controls"><input name="e.orderpay.remark" value="后台添加"></div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<s:submit type="button" method="insertOrderpay" onclick="return onSubmit(this);" value="确认" cssClass="btn btn-primary"/>
					<input id="cancelPayBtn" type="button" value="取消" class="btn"/>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="updatePayMoneryDiv" style="display: none;">
		<table class="table">
			<tr>
				<td colspan="2">
					<h4>修改订单总金额</h4>
				</td>
			</tr>
			<tr>
				<td>支付金额</td>
				<td>
					<input name="e.amount">
				</td>
			</tr>
			<tr>
				<td>备注</td>
				<td>
					<div class="controls"><input name="e.updatePayMoneryRemark" placeholder="修改订单金额备注"></div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<s:submit type="button" method="updatePayMonery" onclick="javascript:return confirm('确认本次操作?');" value="确认" cssClass="btn btn-primary"/>
					<input id="cancelUpdatePayMoneryBtn" type="button" value="取消" class="btn"/>
				</td>
			</tr>
		</table>
	</div>
	
	<ul class="nav nav-tabs" id="tab">
    	<li class="active"><a href="#info" data-toggle="tab">订单信息</a></li>
    	<li><a href="#detail" data-toggle="tab">订单明细<s:if test="e.lowStocks.equals(\"y\")"><font color="red">【缺货】</font></s:if></a></li>
    	<li><a href="#log" data-toggle="tab">订单日志</a></li>
    </ul>

	<div class="tab-content">
	<div id="info" class="tab-pane active" >
		<s:if test="e.refundStatusStr!=null and e.refundStatusStr!=''">
			<div class="alert alert-danger" style="margin-bottom: 2px;">
				<strong>退款状态：</strong><s:property value="e.refundStatusStr"/>(<s:property value="e.refundStatus"/>)
				<br>
				【请立刻去<a href="http://www.alipay.com" target="_blank">支付宝</a>处理此订单的退款事宜】
			</div>
		</s:if>
		
		<div class="alert alert-info" style="margin-bottom: 2px;">
			<strong>订单信息</strong>
			<s:if test="e.score>0">
				<span class="badge badge-success" style="margin-left:20px;">赠送<s:property value="e.score" />个积分点</span>
			</s:if>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<s:if test="e.amountExchangeScore>0">
				<span class="badge badge-default" style="margin-left:20px;">消耗掉会员<s:property value="e.amountExchangeScore" />个积分点</span>
			</s:if>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<s:if test="e.hasGift">
				<span class="badge badge-success" style="margin-left:20px;">【订单含赠品】</span>
			</s:if>
		</div>
		
		<table class="table table-bordered">
			<tr>
				<th width="120px">订单编号</th>
				<th width="40px">数量</th>
				<th width="120px">创建日期</th>
				<th width="60px">订单状态</th>
				<th width="60px">支付状态</th>
				<th>订单总金额</th>
				<th>商品总金额</th>
				<th>总配送费</th>
				<th>收货人信息</th>
				<th>收货人地址</th>
			</tr>
			<tr>
				<td>&nbsp;<s:property value="e.no" /></td>
				<td>&nbsp;<s:property value="e.quantity" /></td>
				<td>&nbsp;<s:property value="e.createdate" /></td>
				<td>
					<s:if test="e.status.equals(\"sign\") or e.status.equals(\"file\")">
						<span class="badge badge-success"><s:property value="e.statusStr" /></span>
					</s:if>
					<s:else>
						<span class="badge badge-important"><s:property value="e.statusStr" /></span>
					</s:else>
				</td>
				<td>
					<s:if test="e.paystatus.equals(\"y\")">
						<span class="badge badge-success"><s:property value="e.paystatusStr" /></span>
					</s:if>
					<s:else>
						<span class="badge badge-important"><s:property value="e.paystatusStr" /></span>
					</s:else>
				</td>
				<td>&nbsp;<span class="simpleOrderReport"><s:property value="e.amount" /></span>
					<s:if test="e.updateAmount.equals(\"y\")"><font color="red">【修】</font></s:if>
				</td>
				<td>&nbsp;<s:property value="e.ptotal" /></td>
				<td>&nbsp;<s:property value="e.fee" /></td>
				<td>
					姓名：<s:property value="e.ordership.shipname" /><br>
					性别：<s:property value="e.ordership.sex" /><br>
					手机：<s:property value="e.ordership.phone" /><br>
					座机：<s:property value="e.ordership.tel" /><br>
				</td>
				<td style="width: 200px;">
					省份：<s:property value="e.ordership.province" /><br>
					城市：<s:property value="e.ordership.city" /><br>
					区域：<s:property value="e.ordership.area" /><br>
					详细地址：<s:property value="e.ordership.shipaddress" /><br>
					邮编：<s:property value="e.ordership.zip" /><br>
					备注：<s:property value="e.ordership.remark" /><br>
				</td>
			</tr>
			<s:if test="e.otherRequirement!=null">
				<tr>
					<td colspan="20">附加要求:<s:property value="e.otherRequirement"/></td>
				</tr>
			</s:if>
			<tr>
				<td colspan="20">备注（客户详细需求）:<s:property value="e.remark"/></td>
			</tr>
		</table>
		
		<div class="alert alert-success" style="margin-bottom: 2px;">
			<strong>订单详情</strong>
		</div>
		<table class="table table-bordered">
			<tr>
				<td width="100px">商品名称</td>
				<td width="100px">商品图片</td>
				<td>数量</td>
				<td>单价</td>
				<td>配送费</td>
				<td>小计</td>
				<td width="160px">供货商</td>
				<!-- <td width="250px">省际物流</td>
				<td width="250px">省内物流</td> -->
				<td width="75px;">操作</td>
			</tr>
			<s:iterator value="e.orderdetail" status="i" var="odetail">
			<tr>
				<td width="200px" rowspan="${odetail.odeList.size() + 1 }"><a target="_blank" style="text-decoration: underline;" href="<%=SystemManager.systemSetting.getWww()%>/product/<s:property value="productID" />"><s:property value="productName"/><br/><s:property value="specInfo"/></a></td>
				<td width="100px"><a target="_blank" style="text-decoration: underline;" href="<%=SystemManager.systemSetting.getWww()%>/product/<s:property value="productID" />"><img width="100" height="100" alt="<s:property value="productName"/>" src="${IMAGE_ROOT_PATH }/<s:property value="picture"/>-px350"></a></td>
				<td><s:property value="number" /></td>
				<td>￥<s:property value="price" /></td>
				<td>￥<s:property value="fee" /></td>
				<td>￥<s:property value="total0" /></td>
				<td><s:if test="e.paystatus.equals(\"y\")">
					<select name="supplier" style="width:160px;" onchange="changeSupplier(this, '${odetail.id}')">
						<option value="">-请选择-</option>
						<c:forEach items="${suppliers }" var="s">
						<option value="${s.id }" <c:if test="${s.id eq odetail.supplierId }">selected="selected"</c:if>>${s.name }</option>
						</c:forEach>
					</select>
				</s:if></td>
				<td>
					<s:if test="e.paystatus.equals(\"y\")">
					<a href="javascript:;" onclick="editExpress(this, '', '${odetail.id}', '0');">新增省际物流</a><br/>
					<a href="javascript:;" onclick="editExpress(this, '', '${odetail.id}', '1');">新增省内物流</a>
					</s:if>
				</td>
			</tr>
			<c:forEach items="${odetail.odeList }" var="o">
			<tr>
				<td width="100px"><c:if test="${o.mode eq '0' }">省际物流</c:if><c:if test="${o.mode eq '1' }">省内物流</c:if></td>
				<td colspan="5">
					<b>物流方式：</b><c:if test="${o.expressType eq 'LOGISTICS' }">物流</c:if><c:if test="${o.expressType eq 'EXPRESS' }">快递</c:if> | 
					<b>物流公司：</b>${o.expressCompanyName } | 
					<b>物流单号：</b>${o.expressNo } | 
					<b>物流费用：</b>${o.expressFee } 
				</td>
				<td>
					<s:if test="e.paystatus.equals(\"y\")">
					<a href="javascript:;" onclick="editExpress(this, '${o.id }', '${odetail.id}', '${o.mode }', '${o.expressType }', '${o.expressCompanyName }', '${o.expressNo }', '${o.expressFee }');">修改</a><br/>
					</s:if>
				</td>
			</tr>
			</c:forEach>
			</s:iterator>
		</table>

		<div class="alert alert-success" style="margin-bottom: 2px;">
			<strong>订单支付记录</strong>
		</div>
		<table class="table table-bordered">
			<tr>
				<th width="50px">序号</th>
				<th nowrap="nowrap">支付方式</th>
				<th nowrap="nowrap">支付金额</th>
				<th nowrap="nowrap">支付时间</th>
				<th nowrap="nowrap">支付状态</th>
				<th nowrap="nowrap">支付宝交易号</th>
				<th nowrap="nowrap">备注</th>
			</tr>
			<s:iterator value="e.orderpayList" status="i" var="orderpay">
				<tr>
					<td>&nbsp;<s:property value="#i.index+1" /></td>
					<td><s:property value="paymethod" />
						<s:if test="paymethod.equals(\"n\")"> <%=KeyValueHelper.get("orderpay_paymethod_n")%>
						</s:if>
						<s:elseif test="paymethod.equals(\"y\")"> <%=KeyValueHelper.get("orderpay_paymethod_y")%>
						</s:elseif>
					</td>
					<td>&nbsp;<s:property value="payamount" /></td>
					<td>&nbsp;<s:property value="createtime" /></td>
					<td>
						<s:if test="paystatus.equals(\"n\")">
							<%=KeyValueHelper.get("orderpay_paystatus_n")%>
						</s:if>
						<s:elseif test="paystatus.equals(\"y\")">
							<span class="badge badge-success"><%=KeyValueHelper.get("orderpay_paystatus_y")%></span>
						</s:elseif>
					</td>
					<td>&nbsp;<s:property value="tradeNo" /></td>
					<td>&nbsp;<s:property value="remark" /></td>
				</tr>
			</s:iterator>
		</table>
		
	</div>
	
	<div id="detail" class="tab-pane" >
		<table class="table table-bordered">
			<tr style="background-color: #dff0d8">
				<th width="60px">商品编号</th>
				<th>商品名称</th>
				<th>商品图片</th>
				<th>购买的商品规格</th>
				<th>数量</th>
				<th>单价</th>
				<th>小计</th>
			</tr>
			<s:iterator value="e.orderdetail" status="i" var="odetail">
				<tr>
					<td nowrap="nowrap">
						<s:property value="productID" />
						<s:if test="lowStocks.equals(\"y\")"><font color="red">【缺货】</font></s:if>
					</td>
					<td><a target="_blank" style="text-decoration: underline;" href="<%=SystemManager.systemSetting.getWww()%>/product/<s:property value="productID" />"><s:property value="productName"/></a>
						<br>
						<s:if test="giftID!=null and giftID!=''">
							<s:a target="_blank" style="text-decoration: underline;" href="gift!show.action?e.id=%{giftID}">
								【查看赠品】
							</s:a>
						</s:if>
					</td>
					<td width="100px"><a target="_blank" style="text-decoration: underline;" href="<%=SystemManager.systemSetting.getWww()%>/product/<s:property value="productID" />"><img width="100" height="100" alt="<s:property value="productName"/>" src="${IMAGE_ROOT_PATH }/<s:property value="picture"/>-px350"></a></td>
					<td>&nbsp;<s:property value="specInfo" /></td>
					<td>&nbsp;<s:property value="number" /></td>
					<td>&nbsp;￥<s:property value="price" /></td>
					<td>&nbsp;￥<s:property value="total0" /></td>
					</td>
				</tr>
			</s:iterator>
		</table>
	</div>
	
	<div id="log" class="tab-pane" >
		<table class="table table-bordered">
			<tr style="background-color: #dff0d8">
				<th width="50px">序号</th>
				<th>操作人</th>
				<th>操作人类型</th>
				<th>时间</th>
				<th>日志</th>
			</tr>
			<s:iterator value="e.orderlogs" status="i" var="orderlog">
				<tr>
					<td>&nbsp;<s:property value="#i.index+1" /></td>
					<td nowrap="nowrap">&nbsp;
						<s:if test="accountType.equals(\"w\")">
							<s:a target="_blank" href="account!show.action?accountId=%{accountId}"><s:property value="account" /></s:a>
						</s:if>
						<s:elseif test="accountType.equals(\"m\")">
							<s:a target="_blank" href="user!show.action?account=%{account}"><s:property value="account" /></s:a>
						</s:elseif>
						<s:elseif test="accountType.equals(\"p\")">
							第三方支付系统
						</s:elseif>
						<s:else>
							未知
						</s:else>
					</td>
					<td>&nbsp;
						<s:if test="accountType.equals(\"w\")">
							客户
						</s:if>
						<s:elseif test="accountType.equals(\"m\")">
							<%=SystemManager.systemSetting.getSystemCode() %>(系统)
						</s:elseif>
						<s:elseif test="accountType.equals(\"p\")">
							支付宝
						</s:elseif>
						<s:else>
							未知
						</s:else>
					</td>
					<td>&nbsp;<s:property value="createdate" /></td>
					<td>&nbsp;<s:property value="content" /></td>
				</tr>
			</s:iterator>
		</table>
	</div>

	</div>
</s:form>

<div id="expressDiv" style="display: none;">
	<form class="form-horizontal">
		<input type="hidden" name="orderdetailId">
		<div class="control-group">
			<label class="control-label">物流方式:</label>
			<div class="controls">
				<select name="expressType">
					<c:forEach items="${expressList }" var="o">
					<option value="${o.code }">${o.name }</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">物流公司:</label>
			<div class="controls">
				<input type="text" name="expressCompanyName" maxlength="200" class="input required">
			</div>
		</div>
		<!-- <div class="control-group">
			<label class="control-label">起点城市:</label>
			<div class="controls">
				<input type="number" name="nowPrice" maxlength="200" class="input required">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">终点城市:</label>
			<div class="controls">
				<input type="number" name="minSale" maxlength="200" class="input required">
			</div>
		</div> -->
		<div class="control-group">
			<label class="control-label">物流单号:</label>
			<div class="controls">
				<input type="text" name="expressNo" maxlength="200" class="input required">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">物流费用:</label>
			<div class="controls">
				<input type="number" name="expressFee" class="input required" value="0.00">
			</div>
		</div>
	</form>
</div>
<script type="text/javascript">
$(function(){
	
});

function changeSupplier(o, orderdetailId) {
	var supplierId = $(o).val();
	var supplierName = $(o).find('option:selected').text();
	if( typeof supplierId == 'undefined' || !supplierId) {
		$.jBox.error('请选供应商！', '错误提示');
		return;
	}
	$.post('order!saveSupplier.action', {
		orderdetailId: orderdetailId,
		supplierId: supplierId,
		supplierName: supplierName
	}, function(data){
		data = eval('(' + data + ')');
		if(data.isSuccess) {
			window.location.reload();
		} else {
			$.jBox.error(data.msg, '错误提示');
		}
	});
}

function editExpress(o, id, orderdetailId, mode, expressType, expressCompanyName, expressNo, expressFee) {
	var title = "";
	if(mode == '0') {
		title = '新增省际物流';
	} else {
		title = '新增省内物流';
	}
	$.jBox.open($('#expressDiv').html(), title, 480, 320, {
		buttons:{ '确定': 1, '取消': 0},
		loaded:function(h){
			h.find('select[name="expressType"]').val(expressType);
        	h.find('input[name="expressCompanyName"]').val(expressCompanyName);
        	h.find('input[name="expressNo"]').val(expressNo);
        	h.find('input[name="expressFee"]').val(expressFee);
			$(".jbox-content", top.document).css("overflow-y","hidden");
		},
		submit: function (v, h, f) {
			if (v == 0) {
                return true; 
            } else {
            	var expressType = h.find('select[name="expressType"]').val();
            	var expressCompanyName = h.find('input[name="expressCompanyName"]').val();
            	var expressNo = h.find('input[name="expressNo"]').val();
            	var expressFee = h.find('input[name="expressFee"]').val();
            	if(typeof expressType == undefined || !expressType) {
            		$.jBox.error('请选择物流方式！', '错误提示');
            		return false;
            	}
            	if(!expressCompanyName) {
            		$.jBox.error('请填写物流公司！', '错误提示');
            		return false;
            	}
            	if(!expressNo) {
            		$.jBox.error('请填写物流单号！', '错误提示');
            		return false;
            	}
            	if(!expressFee || expressFee <= 0) {
            		$.jBox.error('请填写物流费用，物流费用大于0！', '错误提示');
            		return false;
            	}
            	$.post('order!saveExpress.action', {
            		id: id,
            		orderdetailId: orderdetailId,
            		mode: mode,
            		expressType: expressType,
            		expressCompanyName: expressCompanyName,
            		expressNo: expressNo,
            		expressFee: expressFee
				}, function(data){
					data = eval('(' + data + ')');
					if(data.isSuccess) {
						window.location.reload();
					} else {
						$.jBox.error(data.msg, '错误提示');
					}
				});
            	return true;
            }
		}
	});
}
</script>

<script>
$(function() {
	/* $('select[multiple="multiple"]').select2({
    	tags: true,
        tokenSeparators: [',', ' ']
    }); */
    
    $('#tab a').click(function (e) {
   		e.preventDefault();
   		$(this).tab('show');
   	});
	
	$("#cancelPayBtn").click(function(){
		$("#addPayDiv").slideUp();
		$("#addPayBtn").show();
		return false;
	});
	$("#cancelUpdatePayMoneryBtn").click(function(){
		$("#updatePayMoneryDiv").slideUp();
		$("#updatePayMoneryBtn").show();
		return false;
	});
});
function addPayFunc(){
	$("#addPayDiv").slideDown();
	$("#addPayBtn").hide();
	return false;
}
function updatePayMoneryFunc(){
	$("#updatePayMoneryDiv").slideDown();
	$("#updatePayMoneryBtn").hide();
	return false;
}
function onSubmit(obj){
	if($(obj).attr("disabled")=='disabled'){
		return false;
	}
	return confirm("确认本次操作?");
}
</script>
</body>
</html>
