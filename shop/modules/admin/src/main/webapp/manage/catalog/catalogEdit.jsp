<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/resource/common_html_meat.jsp"%>
<%@ include file="/manage/system/common.jsp"%>
<%@ include file="/resource/common_html_validator.jsp"%>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/kindeditor-4.1.7/themes/default/default.css" />
<script charset="utf-8" src="<%=request.getContextPath() %>/resource/kindeditor-4.1.7/kindeditor-min.js"></script>
<script charset="utf-8" src="<%=request.getContextPath() %>/resource/kindeditor-4.1.7/lang/zh_CN.js"></script>
<script type="text/javascript">
	$(function() {
		$("#title").focus();
		selectDefaultCatalog();
		
		$("#name").blur(function(){
			getCode();
		});
		
		var id = '${e.id}';
		if(typeof id == 'undefined' || !id) {
			$('form').attr('action', 'catalog!insert.action');
		} else {
			$('form').attr('action', 'catalog!update.action');
		}
	});

	function onSubmit() {
		//if($("#pid").val()==''){
			//var t = $('#cc').combotree('tree');	// get the tree object
			//var n = t.tree('getSelected');		// get selected node
			//if(!n || !n.id){
				//alert("请选择父亲类别");
				//return false;
			//}
			//$("#pid").val(n.id);
		//}
		
		if ($.trim($("#name").val()) == "") {
			alert("名称不能为空!");
			$("#title").focus();
			return false;
		}
	}
	
	function selectDefaultCatalog(){
		var _catalogID = $("#catalogID").val();
		console.log("selectDefaultCatalog._catalogID="+_catalogID);
		//if(_catalogID!='' && _catalogID>0){
			$("#catalogSelect").val(_catalogID);
		//}
	}

	function catalogChange(obj){
		var _pid = $(obj).find("option:selected").attr("pid");
		console.log("_pid="+_pid);
		/* if(!(_pid && _pid==0)){
			alert("不能选择子类!");
			selectDefaultCatalog();
			return false;
		} */
	}

	function getCode(){
		var _name = $("#name").val();
		//var _url = "catalog!autoCode.action?e.name="+_name;
		var _url = "catalog!autoCode.action";
		$.ajax({
		  type: 'POST',
		  url: _url,
		  data: {"e.name":_name},
		  dataType:"text",
		  //async:false,
		  success: function(data){
			  if(!data){return null;}
			  console.log("data="+data);
			  $("#code").val(data);
		  },
		  error:function(){
			  console.log("加载数据失败，请联系管理员。");
		  }
		});
	}
</script>

<script type="text/javascript">
KindEditor.ready(function(K) {
	<%-- var editor = K.editor({
		fileManagerJson : '<%=request.getContextPath() %>/resource/kindeditor-4.1.7/jsp/file_manager_json.jsp?prefix='
		fileManagerJson : '<%=request.getContextPath() %>/resource/kindeditor-4.1.7/jsp/file_manager_json.jsp?prefix=upload/ctgIcon'
	}); --%>
	var editor = K.editor({
		uploadJson: '${pageContext.request.contextPath}/servlet/upload?prefix=/upload/ctgIcon',
		fileManagerJson : '${pageContext.request.contextPath}/servlet/kindeditor?prefix=/upload/ctgIcon',
		allowFileManager : true
	});
	
	K('input[name=filemanager]').click(function() {
		/**
		editor.loadPlugin('filemanager', function() {
			editor.plugin.filemanagerDialog({
				viewType : 'VIEW',
				dirName : 'image',
				clickFn : function(url, title) {
					$('#icon').val(url.substring(url.indexOf("/upload/ctgIcon/")));
					$('#icon').next().attr('src', url);
					editor.hideDialog();
				}
			});
		});
		*/
		editor.loadPlugin('image', function() {
			editor.plugin.imageDialog({
				clickFn : function(url, title, width, height, border, align) {
					$('#icon').val(url);
					$('#icon').next().attr('src', url);
					editor.hideDialog();
				}
			});
		});
		
	});
	
});
</script>

</head>

<body>
	<s:form action="catalog!update.action" namespace="/manage" theme="simple" id="form" name="form">
		<input id="catalogID" value="<s:property value="e.pid"/>" style="display: none;"/>
		<input id="catalogID_currentID" value="<s:property value="e.id"/>" style="display: none;"/>
		<s:hidden name="e.type" id="type"/>
		
		<table class="table table-bordered" style="width: 95%;margin: auto;">
			<tr style="background-color: #dff0d8">
				<td colspan="2" style="background-color: #dff0d8;text-align: center;">
					<strong>编辑分类</strong>
					
					<s:if test="e.type.equals(\"p\")">
						<span class="badge badge-important">商品分类</span>&nbsp;<strong>
					</s:if>
					<s:else>
						<span class="badge badge-success">文章分类</span>&nbsp;<strong>
					</s:else>
				</td>
			</tr>
			<tr style="display: none;">
				<td>id</td>
				<td><s:hidden name="e.id" label="id" /></td>
			</tr>
			<s:if test="e.id=='' or e.id==null">
				<tr>
			</s:if>
			<s:else>
				<tr>
			</s:else>
				<td style="text-align: right;">大类</td>
				<td style="text-align: left;">
					<%
					request.setAttribute("catalogs", null);
					%>
					<s:if test="e.type!=null and e.type.equals(\"p\")">
					<%
						request.setAttribute("catalogs", SystemManager.catalogs);
					%>
					</s:if>
					<s:elseif test="e.type!=null and e.type.equals(\"a\")">
					<%
						request.setAttribute("catalogs", SystemManager.catalogsArticle);
					%>
					</s:elseif>
					<select onchange="catalogChange(this)" name="e.pid" id="catalogSelect">
						<option></option>
						<s:iterator value="#request.catalogs">
							<option pid="0" value="<s:property value="id"/>"><font color='red'><s:property value="name"/></font></option>
							<s:iterator value="children">
								<option value="<s:property value="id"/>">&nbsp;&nbsp;&nbsp;&nbsp;<s:property value="name"/></option>
							</s:iterator>
						</s:iterator>
					</select>
				</td>
			</tr>
			<tr>
				<td style="text-align: right;">名称</td>
				<td style="text-align: left;"><s:textfield name="e.name" id="name" data-rule="名称;required;name;" size="20" maxlength="20" 
						/></td>
			</tr>
			<tr>
				<td style="text-align: right;">编码</td>
				<td style="text-align: left;">
				<s:textfield name="e.code" data-rule="编码;required;code;length[1~45];remote[catalog!unique.action?e.id=${e.id }]" size="45" maxlength="45" id="code" /></td>
			</tr>
			<tr>
				<td style="text-align: right;">顺序</td>
				<td style="text-align: left;"><s:textfield name="e.sort" data-rule="顺序;required;integer;sort;" size="20" maxlength="20" 
						id="sort" /></td>
			</tr>
			
			<tr>
				<td style="text-align: right;">图标</td>
				<td style="text-align: left;">
					<s:hidden name="e.icon" id="icon" />
					<img src="${IMAGE_ROOT_PATH }/${e.icon}" />		
					<input type="button" name="filemanager" value="浏览图片" class="btn btn-success"/>
				</td>
			</tr>
			
			<s:if test="e.type!=null and e.type.equals(\"p\")">
				<tr>
					<td style="text-align: right;">是否在导航条显示</td>
					<td style="text-align: left;">
						<s:select list="#{'n':'否','y':'是'}" id="showInNav" name="e.showInNav" listKey="key" listValue="value"  />
					</td>
				</tr>
			</s:if>  
			<s:if test="e.type!=null and e.type.equals(\"p\")">
				<tr>
					<td style="text-align: right;">是否推荐分类</td>
					<td style="text-align: left;">
						<s:select list="#{'n':'否','y':'是'}" id="isRecommend" name="e.isRecommend" listKey="key" listValue="value"  />
					</td>
				</tr>
			</s:if> 
			<tr>
				<td colspan="2" style="text-align: center;"><s:if test="e.id=='' or e.id==null">
						<button method="catalog!insert.action" class="btn btn-success">
							<i class="icon-ok icon-white"></i> 新增
						</button>
					</s:if> 
					<s:else>
						<button method="catalog!update.action" class="btn btn-success">
							<i class="icon-ok icon-white"></i> 保存
						</button>
					</s:else>
				</td>
			</tr>
		</table>
	</s:form>
	
</body>
</html>
