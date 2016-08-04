<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/resource/common_html_meat.jsp"%>
<%@ include file="/manage/system/common.jsp"%>
<%@ include file="/resource/common_html_validator.jsp"%>
</head>

<body>
	<s:form action="oss" namespace="/manage" theme="simple" id="form" name="form">
		<table class="table table-bordered">
			<tr style="background-color: #dff0d8">
				<td colspan="2" style="background-color: #dff0d8;text-align: center;">
					<strong>存储编辑</strong>
				</td>
			</tr>
			<tr style="display: none;">
				<td>id</td>
				<td><s:hidden name="e.id" label="id" /></td>
			</tr>
			<tr>
				<td style="text-align: right;width: 200px;">存储类型</td>
				<td style="text-align: left;"><s:select list="#{'aliyun':'阿里云存储','qiniu':'七牛存储'}" name="e.code" disabled="true"/></td>
			</tr>
<!-- 			<tr> -->
<!-- 				<td style="text-align: right;">配置信息</td>    -->
<!-- 				<td style="text-align: left;" > -->
<%-- 					<s:textarea name="e.ossJsonInfo" rows="3" cols="600" cssStyle="width:800px;" id="ossJsonInfo"  --%>
<%-- 					data-rule="配置信息;required;ossJsonInfo;length[1~500];"/> --%>
<!-- 				</td> -->
<!-- 			</tr> -->
			<c:if test="${'aliyun' eq e.code }">
			<tr>
				<td style="text-align: right;">ACCESS_ID</td>   
				<td style="text-align: left;">
					<s:textfield name="e.aliyunOSS.ACCESS_ID" id="ACCESS_ID" data-rule="ACCESS_ID:required;ACCESS_ID;length[1~50];"/>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">ACCESS_KEY</td>   
				<td style="text-align: left;" >
					<s:textfield name="e.aliyunOSS.ACCESS_KEY" id="ACCESS_KEY" data-rule="ACCESS_KEY:required;ACCESS_KEY;length[1~50];"/>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">OSS_ENDPOINT</td>   
				<td style="text-align: left;" >
					<s:textfield name="e.aliyunOSS.OSS_ENDPOINT" id="OSS_ENDPOINT" data-rule="OSS_ENDPOINT:required;OSS_ENDPOINT;length[1~50];"/>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">bucketName</td>   
				<td style="text-align: left;" >
					<s:textfield name="e.aliyunOSS.bucketName" id="bucketName" data-rule="bucketName:required;bucketName;length[1~50];"/>
				</td>
			</tr>
			</c:if>
	
			<c:if test="${'qiniu' eq e.code }">
			<tr>
				<td style="text-align: right;">ACCESS_KEY</td>   
				<td style="text-align: left;">
					<s:textfield name="e.qiniuOSS.ACCESS_KEY" id="ACCESS_KEY" data-rule="ACCESS_KEY:required;ACCESS_KEY;length[1~50];"/>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">SECRET_KEY</td>   
				<td style="text-align: left;" >
					<s:textfield name="e.qiniuOSS.SECRET_KEY" id="SECRET_KEY" data-rule="SECRET_KEY:required;SECRET_KEY;length[1~50];"/>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">domain</td>   
				<td style="text-align: left;" >
					<s:textfield name="e.qiniuOSS.domain" id="domain" data-rule="domain:required;domain;length[1~50];"/>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">bucketName</td>   
				<td style="text-align: left;" >
					<s:textfield name="e.qiniuOSS.bucketName" id="bucketName" data-rule="bucketName:required;bucketName;length[1~50];"/>
				</td>
			</tr>
			</c:if>
			
			<tr>
				<td style="text-align: right;">状态</td>
				<td style="text-align: left;">
					<s:select list="#{'y':'启用'}" name="e.status"/>
<%-- 					<s:select list="#{'y':'启用','n':'禁用'}" name="e.status"/> --%>
				</td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center;"><s:if test="e.id=='' or e.id==null">
<%-- 						<s:submit method="insert" value="新增" cssClass="btn btn-primary"/> --%>
						<button method="oss!insert.action" class="btn btn-success">
							<i class="icon-ok icon-white"></i> 新增
						</button>
					</s:if> 
					<s:else>
						<button method="oss!update.action" class="btn btn-success">
							<i class="icon-ok icon-white"></i> 保存
						</button>
					</s:else>
<%-- 					<s:submit method="back" value="返回" cssClass="btn btn-inverse"/> --%>
				</td>
			</tr>
		</table>
	</s:form>
</body>
</html>
