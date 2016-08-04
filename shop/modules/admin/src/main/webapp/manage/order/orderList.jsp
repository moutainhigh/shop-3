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
<script type="text/javascript"
	src="<%=request.getContextPath()%>/resource/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
.titleCss {
	background-color: #e6e6e6;
	border: solid 1px #e6e6e6;
	position: relative;
	margin: -1px 0 0 0;
	line-height: 32px;
	text-align: left;
}

.aCss {
	overflow: hidden;
	word-break: keep-all;
	white-space: nowrap;
	text-overflow: ellipsis;
	text-align: left;
	font-size: 12px;
}

.liCss {
	white-space: nowrap;
	text-overflow: ellipsis;
	overflow: hidden;
	height: 30px;
	text-align: left;
	margin-left: 10px;
	margin-right: 10px;
}
</style>
<script type="text/javascript">
	$(function() {
		function c1(f) {
			$(":checkbox").each(function() {
				$(this).attr("checked", f);
			});
		}
		$("#firstCheckbox").click(function() {
			if ($(this).attr("checked")) {
				c1(true);
			} else {
				c1(false);
			}
		});
	});
	function deleteSelect() {
		if ($("input:checked").size() == 0) {
			return false;
		}
		return confirm("确定删除选择的记录?");
	}
	function updateInBlackList() {
		if ($("input:checked").size() == 0) {
			return false;
		}
		return confirm("确定将选择的记录拉入新闻黑名单吗?");
	}
</script>
</head>

<body>
	<s:form action="order" namespace="/manage" method="post" theme="" cssClass="breadcrumb form-search">
		<div> 
			<label>订单号：</label><input maxlength="50" class="input-medium" name="e.id" value="${e.id }"/>
			<label>订单状态：</label><select name="e.status">
				<option value="">-全部-</option>
				<option value="init" <c:if test="${e.status eq 'init' }">selected</c:if>>未审核</option>
				<option value="pass" <c:if test="${e.status eq 'pass' }">selected</c:if>>已审核</option>
				<option value="send" <c:if test="${e.status eq 'send' }">selected</c:if>>已发货</option>
				<option value="sign" <c:if test="${e.status eq 'sign' }">selected</c:if>>已签收</option>
				<option value="cancel" <c:if test="${e.status eq 'cancel' }">selected</c:if>>已取消</option>
				<option value="file" <c:if test="${e.status eq 'file' }">selected</c:if>>已归档</option>
			</select>
			<label>用户：</label><select name="e.accountId">
				<option value="">-全部-</option>
				<c:forEach items="${accounts }" var="a">
				<option value="${a.id }" <c:if test="${e.accountId eq a.id }">selected</c:if>>${a.account }(${a.mobile })</option>
				</c:forEach>
			</select>
		</div>
		
		<div>
			<label>时间范围：</label><input id="d4311" class="Wdate input-medium" type="text" name="e.startDate" value="<s:property value="e.startDate" />"
					onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'2020-10-01\'}'})"/>
					~ 
					<input id="d4312" class="Wdate input-medium" type="text" name="e.endDate" value="<s:property value="e.endDate" />"
					onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d4311\')}',maxDate:'2020-10-01'})"/>
			<label>退款状态：</label><select name="e.refundStatus">
				<option value="">-全部-</option>
				<option value="WAIT_SELLER_AGREE" <c:if test="${e.refundStatus eq 'WAIT_SELLER_AGREE' }">selected</c:if>>等待卖家同意退款</option>
				<option value="WAIT_BUYER_RETURN_GOODS" <c:if test="${e.refundStatus eq 'WAIT_BUYER_RETURN_GOODS' }">selected</c:if>>卖家同意退款，等待买家退货</option>
				<option value="WAIT_SELLER_CONFIRM_GOODS" <c:if test="${e.refundStatus eq 'WAIT_SELLER_CONFIRM_GOODS' }">selected</c:if>>买家已退货，等待卖家收到退货</option>
				<option value="REFUND_SUCCESS" <c:if test="${e.refundStatus eq 'REFUND_SUCCESS' }">selected</c:if>>退款成功，交易关闭</option>
			</select>
			&nbsp;
			<button method="order!selectList.action" class="btn btn-primary" onclick="selectList(this)">
				<i class="icon-search icon-white"></i> 查询
			</button>
		</div>
	</s:form>
	<s:form action="order" namespace="/manage" method="post" theme="simple">
		<div class="alert alert-info" style="text-align: left;font-size: 14px;margin: 2px 0px;">
			<img alt="新增" src="<%=request.getContextPath() %>/resource/images/action_add.gif">：未审核、未支付
			<img alt="已上架" src="<%=request.getContextPath() %>/resource/images/action_check.gif">：已归档
			<img alt="已下架" src="<%=request.getContextPath() %>/resource/images/action_delete.gif">：已取消
		</div>
		<%@ include file="/manage/system/pager.jsp"%>		
		<table class="table table-bordered table-hover">
			<tr style="background-color: #dff0d8">
				<th width="20"><input type="checkbox" id="firstCheckbox" /></th>
				<th>订单号</th>
				<th>订单总金额</th>
				<th>商品总金额</th>
				<th>配送费</th>
				<th>数量</th>
				<th>会员</th>
				<th width="150px">创建日期</th>
				<th width="60px">订单状态</th>
				<th width="60px">支付状态</th>
				<th width="60px">操作</th>
			</tr>
			<s:iterator value="pager.list">
				<tr>
					<td><input type="checkbox" name="ids"
						value="<s:property value="id"/>" /></td>
					<td>
						<s:property value="no" />
						<s:if test="lowStocks.equals(\"y\")"><font color="red">【缺货】</font></s:if>
					</td>
					<td><s:property value="amount" />
						<s:if test="updateAmount.equals(\"y\")"><font color="red">【修】</font></s:if>
					</td>
					<td><s:property value="ptotal" /></td>
					<td><s:property value="fee" /></td>
					<td align="center"><s:property value="quantity" /></td>
					<td><s:a target="_blank" href="account!show.action?accountId=%{accountId}"><s:property value="account" /></s:a></td>
					<td><s:property value="createdate" /></td>
					<td><s:property value="statusStr" />
						<s:if test="status.equals(\"cancel\")">
							<img src="<%=request.getContextPath() %>/resource/images/action_delete.gif">
						</s:if>
						<s:elseif test="status.equals(\"file\")">
							<img src="<%=request.getContextPath() %>/resource/images/action_check.gif">
						</s:elseif>
						<s:elseif test="status.equals(\"init\")">
							<img src="<%=request.getContextPath() %>/resource/images/action_add.gif">
						</s:elseif>
					</td>
					<td><s:property value="paystatusStr" />
						<s:if test="paystatus.equals(\"y\")">
							<img src="<%=request.getContextPath() %>/resource/images/action_check.gif">
						</s:if>
						<s:elseif test="paystatus.equals(\"n\")">
							<img src="<%=request.getContextPath() %>/resource/images/action_add.gif">
						</s:elseif>
					</td>
					<td>
					<s:a target="_blank" href="order!toEdit.action?e.id=%{id}">编辑</s:a>|
					<s:a target="_blank" href="order!toPrint.action?e.id=%{id}">打印</s:a>
					</td>
				</tr>
			</s:iterator>

			<tr>
				<td colspan="55" style="text-align: center;"><%@ include file="/manage/system/pager.jsp"%></td>
			</tr>
		</table>
		
		<div class="alert alert-info" style="text-align: left;font-size: 14px;margin: 2px 0px;">
			<img alt="新增" src="<%=request.getContextPath() %>/resource/images/action_add.gif">：未审核、未支付
			<img alt="已上架" src="<%=request.getContextPath() %>/resource/images/action_check.gif">：已归档
			<img alt="已下架" src="<%=request.getContextPath() %>/resource/images/action_delete.gif">：已取消
		</div>

	</s:form>
</body>
</html>
