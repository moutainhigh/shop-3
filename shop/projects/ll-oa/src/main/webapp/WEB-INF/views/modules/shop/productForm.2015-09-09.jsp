<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品管理</title>
	<meta name="decorator" content="default"/>
	<link rel="stylesheet" href="${ctxStatic }/kindeditor/themes/default/default.css" />
	<script src="${ctxStatic }/kindeditor/kindeditor-min.js"></script>
	<script src="${ctxStatic }/kindeditor/lang/zh_CN.js"></script>
	<%@include file="/WEB-INF/views/include/treeview.jsp" %>
	<script type="text/javascript">
		$(document).ready(function(){
			$("#name").focus();
			$("#inputForm").validate({
				rules: {
				},
				messages: {
				},
				submitHandler: function(form){
					if (CKEDITOR.instances.content.getData()==""){
						top.$.jBox.tip('请填写正文','warning');
					}
					
					var ids = [], nodes = tree.getCheckedNodes(true);
					for(var i=0; i<nodes.length; i++) {
						ids.push(nodes[i].id);
					}
					$("#catalogIds").val(ids);
					var ids2 = [], nodes2 = tree2.getCheckedNodes(true);
					for(var i=0; i<nodes2.length; i++) {
						ids2.push(nodes2[i].id);
					}
					$("#supplierIds").val(ids2);
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});

			var setting = {check:{enable:true,nocheckInherit:true},view:{selectedMulti:false},
					data:{simpleData:{enable:true}},callback:{beforeClick:function(id, node){
						tree.checkNode(node, !node.checked, true, true);
						return false;
					}}};
			
			// 用户-菜单
			var zNodes=[
					<c:forEach items="${catalogList}" var="catalog">{id:${catalog.id}, pId:${not empty catalog.parent.id?catalog.parent.id:0}, name:"${not empty catalog.parent.id?catalog.name:'商品目录列表'}"},
		            </c:forEach>];
			// 初始化树结构
			var tree = $.fn.zTree.init($("#catalogTree"), setting, zNodes);
			// 默认选择节点
			var ids = "${product.catalogIds}".split(",");
			for(var i=0; i<ids.length; i++) {
				var node = tree.getNodeByParam("id", ids[i]);
				try{tree.checkNode(node, true, false);}catch(e){}
			}
			// 默认展开全部节点
			tree.expandAll(false);
			
			// 用户-机构
			var zNodes2=[
					<c:forEach items="${supplierList}" var="supplier">{id:${supplier.id}, pId:0, name:"${supplier.name}"},
		            </c:forEach>];
			// 初始化树结构
			var tree2 = $.fn.zTree.init($("#supplierTree"), setting, zNodes2);
			// 不选择父节点
			tree2.setting.check.chkboxType = { "Y" : "s", "N" : "s" };
			// 默认选择节点
			var ids2 = "${product.supplierIds}".split(",");
			for(var i=0; i<ids2.length; i++) {
				var node = tree2.getNodeByParam("id", ids2[i]);
				try{tree2.checkNode(node, true, false);}catch(e){}
			}
			// 默认展开全部节点
			tree2.expandAll(false);
			// 刷新（显示/隐藏）机构
			refreshSupplierTree();
		});
		
		function refreshSupplierTree(){
			$("#supplierTree").show();
		}
	</script>
	
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/shop/product">产品列表</a></li>
		<li class="active"><a href="${ctx}/shop/product/form?id=${product.id}">产品<shiro:hasPermission name="shop:product:edit">${not empty product.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="shop:product:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="product" action="${ctx}/shop/product/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-xxlarge measure-input required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">在售价:</label>
			<div class="controls">
				<form:input path="price" htmlEscape="false" maxlength="200" class="input-xxlarge measure-input required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出厂价:</label>
			<div class="controls">
				<form:input path="factoryPrice" htmlEscape="false" maxlength="200" class="input-xxlarge measure-input required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">市场价:</label>
			<div class="controls">
				<form:input path="marketPrice" htmlEscape="false" maxlength="200" class="input-xxlarge measure-input required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规格:</label>
			<div class="controls">
				<table class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>颜色</th>
							<th>尺寸</th>
							<th>类型</th>
							<th width="220">价格</th>
							<th width="60">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${product.psList }" var="ps" varStatus="status">
						<tr>
							<td>${ps.color }</td>
							<td>${ps.size }</td>
							<td>${ps.type }</td>
							<td><input type="hidden" name="psList[${status.index }].id" value="${ps.id }"><input type="number" name="psList[${status.index }].price" value="${ps.price }"></td>
							<td></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">目录&供应商:</label>
			<div class="controls">
				<div id="catalogTree" class="ztree" style="margin-top:3px;float:left;"></div>
				<form:hidden path="catalogIds"/>
				<div id="supplierTree" class="ztree" style="margin-left:100px;margin-top:3px;float:left;"></div>
				<form:hidden path="supplierIds"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">关键字:</label>
			<div class="controls">
				<form:input path="keywords" htmlEscape="false" maxlength="200" class="input-xlarge"/>
				<span class="help-inline">多个关键字，用空格分隔。</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">摘要:</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="4" maxlength="200" class="input-xxlarge"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品详情:</label>
			<div class="controls">
				<form:textarea id="content" htmlEscape="true" path="content" rows="4" maxlength="200" class="input-xxlarge"/>
				<tags:ckeditor replace="content" uploadPath="/shop/product" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品图片:</label>
			<div class="controls">
				<form:hidden path="images" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<ol id="imagesPreview"></ol>
				<a href="javascript:" id="seleteImage" class="btn">选择</a>&nbsp;
				<a href="javascript:" onclick="imagesDelAll();" class="btn">清除</a>
				<%-- <tags:ckfinder input="image" type="images" uploadPath="/shop/catalog"/> --%>
				<script type="text/javascript">
				function imagesPreview(){
					var li, urls = $("#images").val().split("|");
					$("#imagesPreview").children().remove();
					for (var i=0; i<urls.length; i++){
						if (urls[i]!=""){
							li = "<li><img src=\""+urls[i]+"\" url=\""+urls[i]+"\" style=\"max-width:200px;max-height:200px;_height:200px;border:0;padding:3px;\">";
							li = "<li><a href=\""+urls[i]+"\" url=\""+urls[i]+"\" target=\"_blank\">"+decodeURIComponent(urls[i].substring(urls[i].lastIndexOf("/")+1))+"</a>";
							li += "&nbsp;&nbsp;<a href=\"javascript:\" onclick=\"imagesDel(this);\">×</a></li>";
							$("#imagesPreview").append(li);
						}
					}
				}
				
				function imagesDel(obj){
					var url = $(obj).prev().attr("url");
					$("#images").val($("#images").val().replace("|"+url,"","").replace(url+"|","","").replace(url,"",""));
					imagesPreview();
				}
				
				function imagesDelAll(){
					$("#images").val("");
					imagesPreview();
				}
				
				$(document).ready(function(){
					KindEditor.ready(function(K) {
						var editor = K.editor({
							fileManagerJson : '${pageContext.request.contextPath}/servlet/kindeditor?prefix=attached/'
						});
						K('a[id=seleteImage]').click(function() {
							editor.loadPlugin('filemanager', function() {
								editor.plugin.filemanagerDialog({
									viewType : 'VIEW',
									dirName : 'image',
									clickFn : function(url, title) {
										$("#images").val($("#images").val() + '|' + url);
										imagesPreview();
										editor.hideDialog();
									}
								});
							});
						});
						
					});
					
					imagesPreview();
				});
				</script>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">发布时间:</label>
			<div class="controls">
				<input id="createDate" name="createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${product.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="shop:product:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>