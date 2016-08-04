<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/resource/common_html_meat.jsp"%>
<%@ include file="/manage/system/common.jsp"%>
<%@ include file="/resource/common_html_validator.jsp"%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/jquery-jquery-ui/themes/base/jquery.ui.all.css">
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/kindeditor-4.1.7/themes/default/default.css" />
<%-- <%@ include file="/resource/common_rateit_plug.jsp"%> --%>
</head>

<body>
<s:form action="auth" namespace="/manage" theme="simple" id="form">
		<div style="text-align: center;border-bottom: 1px solid #ccc;padding: 5px;">
			<s:submit method="passAuth" onclick="return onSubmit();"
					value="审核通过" cssClass="btn btn-primary"/>
			<s:submit method="back" value="返回" cssClass="btn btn-inverse"/>
			
			<a class="btn btn-primary" onclick="rejectAuth()">
				审核不通过
			</a>
			审核不通过原因:<s:textarea name="e.rejectReason"  id="rejectReason"/>
		</div>
		<div class="alert alert-info" style="margin-bottom: 2px;text-align: left;">
			<strong>审核申请信息：</strong>
		</div>
		<table class="table table-bordered">
					<tr style="display: none;">
						<td>id</td>
						<td><s:hidden name="e.id" label="id" id="id"/>
							<s:hidden name="e.type" label="type" id="type"/>
							<s:hidden name="e.status" label="status" id="status"/>
							<s:hidden name="e.accountId" label="accountId" id="accountId"/>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">申请人手机号</td>
						<td style="text-align: left;"><s:property value="e.account.mobile"/></td>
					</tr>
					<tr>
						<td style="text-align: right;width: 200px;">申请人账号</td>
						<td style="text-align: left;"><s:property value="e.account.account"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">申请人证件姓名</td>   
						<td style="text-align: left;"><s:property value="e.cardName"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">申请人证件号码</td>
						<td style="text-align: left;"><s:property value="e.cardNo"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">认证类型</td>
						<td style="text-align: left;">
							<s:if test="e.type == 1">
								个人实名认证
							</s:if>
							<s:elseif test="e.type == 2">
								企业实名认证
							</s:elseif>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">审核状态</td>
						<td>
							<s:if test="e.status == 1">
								申请认证
							</s:if>
							<s:elseif test="e.status == 2">
								审核通过
							</s:elseif>
							<s:elseif test="e.status == 3">
								审核不通过
							</s:elseif>
							<s:elseif test="e.status == 4">
								认证历史数据
							</s:elseif>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">申请日期</td>
						<td style="text-align: left;"><s:property value="e.requestDate"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">审核通过日期</td>
						<td style="text-align: left;"><s:property value="e.authDate"/>
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">审核失败日期</td>
						<td style="text-align: left;">
							<s:property value="e.rejectDate"/>
						</td>
					</tr>
					
					<tr>
						<td style="text-align: right;">认证失败原因</td>
						<td style="text-align: left;"><s:property value="e.rejectReason"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">认证证件图</td>
						<td><img src="${IMAGE_ROOT_PATH }/<s:property value="e.authPicture"/>"/> </td>
					</tr>
				</table>
				
</s:form>
<div id="dialog" title="审核不通过"></div>
<script src="<%=request.getContextPath() %>/resource/jquery-jquery-ui/ui/jquery.ui.core.js"></script>
<script src="<%=request.getContextPath() %>/resource/jquery-jquery-ui/ui/jquery.ui.widget.js"></script>

<script type="text/javascript">
	function rejectAuth() {
		if ($("#rejectReason").val() == null || $("#rejectReason").val() == '') {
			alert("审核不通过原因不能为空!");
			return;
		}
		$("#form").attr("action", "auth!rejectAuth.action");
		$("#form").submit();
	}
</script>
</body>
</html>
