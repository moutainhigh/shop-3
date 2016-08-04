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
</head>

<body>
<s:form action="account" namespace="/manage" theme="simple" id="form">

<div style="text-align: center;border-bottom: 1px solid #ccc;padding: 5px;">
	<s:if test="e.id=='' or e.id==null">
		<s:submit method="insert" onclick="return onSubmit();"
				value="新增" cssClass="btn btn-primary"/>
	</s:if> 
	<s:else>
		<s:submit method="update" onclick="return onSubmit();"
			value="保存" cssClass="btn btn-primary"/>
	</s:else>
	<s:submit
		method="back" value="返回" cssClass="btn btn-inverse"/>
</div>


<div id="tabs">
	<ul>
		<li><a href="#tabs-1">基本信息</a></li>
	</ul>
	<div id="tabs-1">
		<table class="table table-condensed">
					<tr style="display: none;">
						<td>id</td>
						<td><s:hidden name="e.id" label="id" id="id"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">类型:</td>
						<td style="text-align: left;"><s:property value="e.accountTypeName"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">积分:</td>
						<td style="text-align: left;"><s:property value="e.score"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">等级:</td>
						<td style="text-align: left;"><s:property value="e.rankName"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">手机号:</td>
						<td style="text-align: left;"><s:textfield name="e.mobile" 
								id="mobile" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">昵称:</td>
						<td style="text-align: left;"><s:textfield name="e.nickname" 
								id="nickname" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">账号:</td>   
						<td style="text-align: left;"><s:textfield name="e.account" 
								id="account"></s:textfield></td>
					</tr>
					<tr>
						<td style="text-align: right;">城市:</td>
						<td style="text-align: left;"><s:textfield name="e.city" 
								id="city" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">联系地址:</td>
						<td style="text-align: left;"><s:textfield name="e.address" 
								id="address" /></td>
					</tr>
<!-- 					<tr> -->
<!-- 						<td style="text-align: right;">证件号码:</td> -->
<%-- 						<td style="text-align: left;"><s:textfield name="e.postcode"  --%>
<%-- 								id="postcode" /></td> --%>
<!-- 					</tr> -->
<!-- 					<tr> -->
<!-- 						<td style="text-align: right;">证件类型:</td> -->
<%-- 						<td style="text-align: left;"><s:textfield name="e.cardType"  --%>
<%-- 								id="cardType" /></td> --%>
<!-- 					</tr> -->
					<tr>
						<td style="text-align: right;">等级:</td>
						<td style="text-align: left;"><s:textfield name="e.grade" 
								id="grade" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">会员等级：</td>
						<td style="text-align: left;">
							<s:select list="#request.arList" id="rank" name="e.rank"
								listKey="code" listValue="name"  />
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">消费额:</td>
						<td style="text-align: left;"><s:textfield name="e.amount" 
								id="amount" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">电话：</td>
						<td style="text-align: left;"><s:textfield name="e.tel" 
								id="tel" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">Email地址：</td>
						<td style="text-align: left;"><s:textfield name="e.email" 
								id="email" /></td>
					</tr>
					<tr>
						<td style="text-align: right;">是否冻结：</td>
						<td style="text-align: left;">
							<s:select list="#{'y':'是','n':'否'}" id="freeze" name="e.freeze"
								listKey="key" listValue="value"  />
						</td>
					</tr>
					<tr>
						<td style="text-align: right;">邮箱是否激活：</td>
						<td style="text-align: left;"><s:select list="#{'y':'是','n':'否'}" id="freeze" name="e.emailIsActive"
								listKey="key" listValue="value"  /></td>
					</tr>
				</table>
	</div>
</div>
</s:form>

<script src="<%=request.getContextPath() %>/resource/js/jquery-1.9.1.min.js"></script>
<script src="<%=request.getContextPath() %>/resource/jquery-jquery-ui/ui/jquery.ui.core.js"></script>
<script src="<%=request.getContextPath() %>/resource/jquery-jquery-ui/ui/jquery.ui.widget.js"></script>
<script src="<%=request.getContextPath() %>/resource/jquery-jquery-ui/ui/jquery.ui.tabs.js"></script>

<script type="text/javascript">
$(function() {
	$( "#tabs" ).tabs({
		//event: "mouseover"
	});
	
	$("#title").focus();
	
	$("#superTypeID").change(function(){
		$("#smallTypeID").find("option").remove();
		$("#smallTypeID").append("<option value='0'>--请选择--</option>");
		var _url = "productType!getSmallTypeListJson.action?superTypeID="+$(this).val();
		$.ajax({
		  type: 'POST',
		  url: _url,
		  data: {},
		  success: function(data){
			  $.each(data,function(i,row){
				  $("#smallTypeID").append("<option value='"+row.id+"'>"+row.name+"</option>");
			  });
		  },
		  dataType: "json",
		  error:function(){
			alert("加载小类别失败，请联系管理员。");				  
		  }
		});
	});
	
	$("#smallTypeID").change(function(){
		$("#productTypeID").find("option").remove();
		$("#productTypeID").append("<option value='0'>--请选择--</option>");
		var _url = "goods!getProductTypeListJson.action?smallTypeID="+$(this).val();
		$.ajax({
		  type: 'POST',
		  url: _url,
		  data: {},
		  success: function(data){
			  $.each(data,function(i,row){
				  $("#productTypeID").append("<option value='"+row.id+"'>"+row.name+"</option>");
			  });
		  },
		  dataType: "json",
		  error:function(){
			alert("加载小类别失败，请联系管理员。");				  
		  }
		});
	});
});

</script>
</body>
</html>
