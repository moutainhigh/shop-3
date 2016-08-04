<%@page import="net.jeeshop.core.ManageContainer"%>
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
</head>
<body>
	<s:form action="auth!selectList" namespace="/manage" method="post" theme="simple">
		<table class="table table-bordered">
			<tr>
				<td style="text-align: right;" nowrap="nowrap">申请人手机号</td>
				<td style="text-align: left;"><s:textfield name="e.account.mobile" cssClass="input-medium"
						id="mobile" /></td>
				<td style="text-align: right;" nowrap="nowrap">申请账号</td>
				<td style="text-align: left;"><s:textfield name="e.account.account" cssClass="input-medium"
						id="account" /></td>
				<td style="text-align: right;" nowrap="nowrap">申请人证件姓名</td>
				<td style="text-align: left;"><s:textfield name="e.cardName" cssClass="input-medium"
						id="account" /></td>
				<td style="text-align: right;" nowrap="nowrap">申请人证件号码</td>
				<td style="text-align: left;"><s:textfield name="e.cardNo" cssClass="input-medium"
						id="nickname" /></td>
			</tr>
			<tr>
				<td style="text-align: right;" nowrap="nowrap">认证类型</td>
				<td style="text-align: left;">
					<s:select list="#{'':'','1':'个人实名认证','2':'企业实名认证'}" name="e.type"  cssClass="input-medium" 
						listKey="key" listValue="value"  />
				<td style="text-align: right;" nowrap="nowrap">状态</td>
				<td style="text-align: left;">
					<s:select list="#{'':'','1':'申请认证','2':'认证通过','3':'认证失败','4':'认证历史'}"  name="e.status"  cssClass="input-medium" 
						listKey="key" listValue="value"  />
				<td style="text-align: right;" nowrap="nowrap">申请日期</td>
				<td style="text-align: left;" colspan="3" nowrap="nowrap">
					<input id="d4311" class="Wdate input-medium" type="text" name="e.startDate"
					value="<s:property value="e.startDate" />"
					onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'2020-10-01\'}'})"/>
					~  
					<input id="d4312" class="Wdate input-medium" type="text" name="e.endDate" 
					value="<s:property value="e.endDate" />"
					onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d4311\')}',maxDate:'2020-10-01'})"/>
						</td>
			</tr>
			<tr>
				<td colspan="28">
					<button method="auth!selectList.action" class="btn btn-primary" onclick="selectList(this)">
						<i class="icon-search icon-white"></i> 查询
					</button>
					<div style="float: right;vertical-align: middle;bottom: 0px;top: 10px;">
						<%@ include file="/manage/system/pager.jsp"%>
					</div>
				</td>
			</tr>
		</table>
		
		<table class="table table-bordered">
			<tr style="background-color: #dff0d8">
				<th width="20"><input type="checkbox" id="firstCheckbox" /></th>
				<th nowrap="nowrap">申请人手机号</th>
				<th nowrap="nowrap">申请账号</th>
				<th nowrap="nowrap">申请人证件姓名</th>
				<th nowrap="nowrap">申请人证件号码</th>
				<th nowrap="nowrap">认证类型</th>
				<th nowrap="nowrap">审核状态</th>
				<th nowrap="nowrap">申请日期</th>
				<th nowrap="nowrap">审核通过日期</th>
				<th nowrap="nowrap">审核失败日期</th>
				<th nowrap="nowrap">审核失败原因</th>
				<th nowrap="nowrap">操作</th>
			</tr>
			<s:iterator value="pager.list">
				<tr>
					<td><input type="checkbox" name="ids"
						value="<s:property value="id"/>" /></td>
					<td nowrap="nowrap">&nbsp;<s:property value="account.mobile" /></td>
					<td nowrap="nowrap">&nbsp;<s:property value="account.account" /></td>
					<td nowrap="nowrap">&nbsp;<s:property value="cardName" /></td>
					<td nowrap="nowrap">&nbsp;<s:property value="cardNo" /></td>
					<td nowrap="nowrap">&nbsp;
						<s:if test="type == 1">
							个人实名认证
						</s:if>
						<s:elseif test="type == 2">
							企业实名认证
						</s:elseif>
					</td>
					<td nowrap="nowrap">&nbsp;
						<s:if test="status == 1">
							申请认证
						</s:if>
						<s:elseif test="status == 2">
							审核通过
						</s:elseif>
						<s:elseif test="status == 3">
							审核不通过
						</s:elseif>
						<s:elseif test="status == 4">
							认证历史数据
						</s:elseif>
					</td>
					<td nowrap="nowrap">&nbsp;<s:property value="requestDate" /></td>
					<td nowrap="nowrap">&nbsp;<s:property value="authDate" /></td>
					<td nowrap="nowrap">&nbsp;<s:property value="rejectDate" /></td>
					<td nowrap="nowrap">&nbsp;<s:property value="rejectReason" /></td>
					<td nowrap="nowrap">
 						<s:if test="status == 1">
							<s:a href="auth!toAuth.action?authId=%{id}">审核</s:a>
						</s:if>
						<s:a target="_blank" href="auth!show.action?authId=%{id}">查看</s:a>
					</td>
				</tr>
			</s:iterator>
			<tr>
				<td colspan="16" style="text-align: center;"><%@ include
						file="/manage/system/pager.jsp"%></td>
			</tr>
		</table>
	</s:form>
<script type="text/javascript" src="<%=request.getContextPath()%>/resource/My97DatePicker/WdatePicker.js"></script>
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
</script>
</body>
</html>
