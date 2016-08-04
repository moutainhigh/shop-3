<%@page import="net.jeeshop.core.PrivilegeUtil"%>
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

<script type="text/javascript" src="<%=request.getContextPath()%>/resource/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
.product-name {
	display: inline-block;
	width: 250px;
	overflow: hidden; /*注意不要写在最后了*/
	white-space: nowrap;
	-o-text-overflow: ellipsis;
	text-overflow: ellipsis;
}
</style>

</head>

<body>
	<s:form action="product" namespace="/manage" method="post" theme="simple">
		<input type="hidden" value="<s:property value="e.catalogID"/>" id="catalogID"/>
		<table class="table table-bordered table-condensed">
			<tr>
				<td style="text-align: right;">商品编号</td>
				<td style="text-align: left;"><s:textfield name="e.id" cssClass="search-query input-small"
						id="id" /></td>
				<td style="text-align: right;">状态</td>
				<td style="text-align: left;">
					<s:select list="#{0:'',1:'新增',2:'已上架',3:'已下架'}" id="status" name="e.status"  cssClass="input-small" 
						listKey="key" listValue="value"  />
				</td>
				<td style="text-align: right;">
					商品分类
				</td>
				<td>
					<%
					application.setAttribute("catalogs", SystemManager.catalogs);
					%>
					<select onchange="catalogChange(this)" name="e.catalogID" id="catalogSelect" class="input-medium">
						<option></option>
						<s:iterator value="#application.catalogs" var="c1">
							<option pid="0" value="<s:property value="#c1.id"/>"><font color='red'><s:property value="#c1.name"/></font></option>
							<s:iterator value="#c1.children" var="c2">
								<option value="<s:property value="#c2.id"/>">&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="#c2.name"/></option>
								<s:iterator value="#c2.children" var="c3">
									<option value="<s:property value="#c3.id"/>">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="#c3.name"/></option>
								</s:iterator>
							</s:iterator>
						</s:iterator>
					</select>
				</td>
				<td style="text-align: right;">新品</td>
				<td style="text-align: left;">
					<s:select list="#{'':'','y':'是','n':'否'}" id="isnew" name="e.isnew"  cssClass="input-small" 
						listKey="key" listValue="value"  />
				</td>
				<td style="text-align: right;">特价</td>
				<td style="text-align: left;" >
					<s:select list="#{'':'','y':'是','n':'否'}" id="sale" name="e.sale"  cssClass="input-small" 
						listKey="key" listValue="value"  />
				</td>
				<td style="text-align: right;">推荐</td>
				<td style="text-align: left;" >
					<s:select list="#{'':'','y':'是','n':'否'}" id="isRecommend" name="e.isRecommend"  cssClass="input-small" 
						listKey="key" listValue="value"  />
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">商品名称</td>
				<td style="text-align: left;" ><s:textfield name="e.name" cssClass="input-small"
						id="name" /></td>
				<td style="text-align: right;">录入日期</td>
				<td style="text-align: left;" colspan="9">
					<input id="d4311" class="Wdate search-query input-small" type="text" name="e.startDate"
					value="<s:property value="e.startDate" />"
					onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'d4312\')||\'2020-10-01\'}'})"/>
					~ 
					<input id="d4312" class="Wdate search-query input-small" type="text" name="e.endDate" 
					value="<s:property value="e.endDate" />"
					onFocus="WdatePicker({minDate:'#F{$dp.$D(\'d4311\')}',maxDate:'2020-10-01'})"/>
				</td>
			</tr>
			<tr>
				<td colspan="20">
					<%if(PrivilegeUtil.check(request.getSession(), "product!selectList.action")){%>
						<button method="product!selectList.action" class="btn btn-primary" onclick="selectList(this)">
							<i class="icon-search icon-white"></i> 查询
						</button>
					<%} %>
					
					<%if(PrivilegeUtil.check(request.getSession(), "product!updateUp.action")){%>
						<button method="product!updateUp.action" class="btn btn-warning" onclick="return submitIDs(this,'确定上架选择的记录?');">
							<i class="icon-arrow-up icon-white"></i> 上架
						</button>
					<%} %>
					
					<%if(PrivilegeUtil.check(request.getSession(), "product!updateDown.action")){%>
						<button method="product!updateDown.action" class="btn btn-warning" onclick="return submitIDs(this,'确定下架选择的记录?');">
							<i class="icon-arrow-down icon-white"></i> 下架
						</button>
					<%} %>
					<div style="float: right;vertical-align: middle;bottom: 0px;top: 10px;">
						<%@ include file="/manage/system/pager.jsp"%>
					</div>
				</td>
			</tr>
		</table>
		<div class="alert alert-info" style="text-align: left;font-size: 14px;margin: 2px 0px;">
			<img alt="新增" src="<%=request.getContextPath() %>/resource/images/action_add.gif">：新增商品
			<img alt="已上架" src="<%=request.getContextPath() %>/resource/images/action_check.gif">：商品已上架
			<img alt="已下架" src="<%=request.getContextPath() %>/resource/images/action_delete.gif">：商品已下架
		</div>
		<table class="table table-bordered table-condensed table-hover">
			<tr style="background-color: #dff0d8">
				<th width="20"><input type="checkbox" id="firstCheckbox" /></th>
				<th nowrap="nowrap">商品编号</th>
				<th>名称</th>
				<th>定价</th>
				<th>现价</th>
				<th>录入日期</th>
				<th>新品</th>
				<th>特价</th>
				<th>推荐</th>
				<th>浏览次数</th>
				<th>库存</th>
				<th>销量</th>
				<th>状态</th>
				<th width="60">操作</th>
			</tr>
			<s:iterator value="pager.list">
				<tr>
					<td><input type="checkbox" name="ids"
						value="<s:property value="id"/>" /></td>
					<td nowrap="nowrap">&nbsp;<s:property value="id" /></td>
					<td >
						<s:if test="giftID!=null and giftID!=''">
							【赠品】
						</s:if>
						<s:a cssClass="product-name" title="%{name}" href="product!toEdit.action?e.id=%{id}"><s:property value="name" /></s:a>
					</td>
					<td>&nbsp;<s:property value="price" /></td>
					<td>&nbsp;<s:property value="nowPrice" /></td>
					<td>&nbsp;<s:property value="createtime" /></td>
					<td>&nbsp;
						<s:if test="isnew.equals(\"n\")">
							<font color='red'></font>
						</s:if>
						<s:elseif test="isnew.equals(\"y\")">
<!-- 							<font color='blue'>新品</font> -->
							<img alt="新品" src="<%=request.getContextPath() %>/resource/images/action_check.gif">
						</s:elseif>
					</td>
					<td>&nbsp;
						<s:if test="sale.equals(\"n\")">
							<font color='red'></font>
						</s:if>
						<s:elseif test="sale.equals(\"y\")">
<!-- 							<font color='blue'>特价</font> -->
							<img alt="特价" src="<%=request.getContextPath() %>/resource/images/action_check.gif">
						</s:elseif>
					</td>
					<td>&nbsp;
						<s:if test="isRecommend.equals(\"n\")">
							<font color='red'></font>
						</s:if>
						<s:elseif test="isRecommend.equals(\"y\")">
							<img alt="推荐" src="<%=request.getContextPath() %>/resource/images/action_check.gif">
						</s:elseif>
					</td>
					<td>&nbsp;<s:property value="hit" /></td>
					<td>&nbsp;
						<s:if test="stock>0">
							<s:property value="stock" />
						</s:if>
						<s:else>
							<span class="badge badge-important">库存告急</span>
						</s:else>
					</td>
					<td>&nbsp;<s:property value="sellcount" /></td>
					<td>&nbsp;
						<s:if test="status==1">
							<img alt="新增" src="<%=request.getContextPath() %>/resource/images/action_add.gif">
						</s:if>
						<s:elseif test="status==2">
							<img alt="已上架" src="<%=request.getContextPath() %>/resource/images/action_check.gif">
						</s:elseif>
						<s:elseif test="status==3">
							<img alt="已下架" src="<%=request.getContextPath() %>/resource/images/action_delete.gif">
						</s:elseif>
					</td>
					<td ><s:a href="product!toEdit.action?e.id=%{id}">编辑</s:a>|
					<a target="_blank" href="<%=SystemManager.systemSetting.getWww()%>/product/<s:property value="id"/>.html">查看</a>
					</td>
				</tr>
			</s:iterator>

			<tr>
				<td colspan="70" style="text-align: center;"><%@ include
						file="/manage/system/pager.jsp"%></td>
			</tr>
		</table>
		<div class="alert alert-info" style="text-align: left;font-size: 14px;margin: 2px 0px;">
			<img alt="新增" src="<%=request.getContextPath() %>/resource/images/action_add.gif">：新增商品
			<img alt="已上架" src="<%=request.getContextPath() %>/resource/images/action_check.gif">：商品已上架
			<img alt="已下架" src="<%=request.getContextPath() %>/resource/images/action_delete.gif">：商品已下架
		</div>
	</s:form>
	
<script type="text/javascript">
	$(function() {
		selectDefaultCatalog();
	});
	function selectDefaultCatalog(){
		var _catalogID = $("#catalogID").val();
		if(_catalogID!='' && _catalogID>0){
			$("#catalogSelect").attr("value",_catalogID);
		}
	}
</script>
</body>
</html>
