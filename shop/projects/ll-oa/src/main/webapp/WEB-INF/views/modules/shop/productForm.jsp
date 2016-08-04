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
				submitHandler: function(form){
					/* if (CKEDITOR.instances.content.getData()==""){
						top.$.jBox.tip('请填写正文','warning');
					} */
					
					var s = editor.html();
		            $('#content').val(s);
					//alert($('#content').val());
					
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
			
			KindEditor.ready(function(K) {
				editor = K.create('#content', {
                	uploadJson: '${pageContext.request.contextPath}/servlet/upload?prefix=attached/details/${product.id }/',
                    fileManagerJson : '${pageContext.request.contextPath}/servlet/kindeditor?prefix=attached/details/${product.id }/',
                	allowFileManager : true,
                	//cssPath : '${ctxStatic}/kindeditor/themes/default/default.css',
                	afterCreate : function() {
                		this.html($('#content').val());
                	}
                });
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
			tree.expandAll(true);
			
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
			<label class="control-label">商品ID:</label>
			<div class="controls">
				${product.id }
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品编号:</label>
			<div class="controls">
				<form:input path="sn" htmlEscape="false" maxlength="200" class="input required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品名称:</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="200" class="input-xxlarge measure-input required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">商品状态：</label>
			<div class="controls">
				<form:radiobuttons path="status" items="${fns:getDictList('shop_product_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/><br/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单位:</label>
			<div class="controls">
				<form:input path="unit" htmlEscape="false" maxlength="20" class="input required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最低销售数:</label>
			<div class="controls">
				<form:input path="minSale" htmlEscape="false" maxlength="20" class="input required" type="number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">在售价:</label>
			<div class="controls">
				<input type="number" id="price" name="price" value="${product.price }" required="required" min="1">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">库存:</label>
			<div class="controls">
				<form:input path="stock" htmlEscape="false" maxlength="20" class="input required" type="number"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出厂价:</label>
			<div class="controls">
				<%-- <form:input path="factoryPrice" htmlEscape="false" class="input-xxlarge measure-input required"/> --%>
				<input type="number" id="factoryPrice" name="factoryPrice" value="${product.factoryPrice }" required="required" min="1">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">经销商出厂价:</label>
			<div class="controls">
				<input type="number" id="factoryPrice2" name="factoryPrice2" value="${product.factoryPrice2 }" required="required" min="1">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">建议售价:</label>
			<div class="controls">
				<input type="number" id="marketPrice" name="marketPrice" value="${product.marketPrice }" required="required">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">物流费:</label>
			<div class="controls">
				<input type="number" id="expressPrice" name="expressPrice" value="${product.expressPrice }" required="required">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">重量:</label>
			<div class="controls">
				<label>毛重：</label><form:input path="grossWeight" htmlEscape="false" maxlength="200" class="input"/><br/>
				<label>净重：</label><form:input path="netWeight" htmlEscape="false" maxlength="200" class="input"/>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">包装尺寸:</label>
			<div class="controls">
				<form:input path="packageSize" htmlEscape="false" maxlength="200" class="input"/><br/>
				<span class="help-inline">包装规格指的是打包以后的长宽高：例如，200cm*100cm*100cm</span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">产品属性:</label>
			<div class="controls">
				<input type="button" value="新增" class="btn btn-primary btn-small" onclick="editProductAttr('', '', '', '');">
				<table class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>属性</th>
							<th>属性值</th>
							<th>排序</th>
							<th width="60">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${paList }" var="pa" varStatus="status">
						<tr>
							<td>${pa.attr.name }</td>
							<td>${pa.attrVal }</td>
							<td></td>
							<td><a href="javascript:;" onclick="editProductAttr(this, ${pa.id}, '${pa.attr.name }', '${pa.attrVal }');">修改</a>
								<a href="javascript:;" onclick="deleteProductAttr(this,${pa.id});">删除</a>
							</td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<script type="text/javascript">
			/**
			 * 新增规格
			 */
			function editProductAttr(o, productAttrId, name, attrVal) {
				top.$.jBox.open($('#attrDiv').html(), "添加产品属性", 480, 200, {
					buttons:{ '确定': 1, '取消': 0},
					loaded:function(h){
						h.find('input[name="productAttrId"]').val(productAttrId);
						h.find('select[name="attr"]').val(name);
						h.find('input[name="attrVal"]').val(attrVal);
						$(".jbox-content", top.document).css("overflow-y","hidden");
					},
					submit: function (v, h, f) {
						if (v == 0) {
		                    return true; 
		                } else {
		                	var productAttrId = h.find('input[name="productAttrId"]').val();
		                	var attrName = h.find('select[name="attr"]').val();
		                	var attrVal = h.find('input[name="attrVal"]').val();
		                	if(typeof productAttrId == undefined || !productAttrId) {
		                		productAttrId = null;
		                	}
		                	if(!attrName) {
		                		top.$.jBox.error('请选择属性！');
		                		return false;
		                	}
		                	if(!attrVal) {
		                		top.$.jBox.error('请填写属性值！');
		                		return false;
		                	}
		                	$.post('${ctx}/shop/product/pa/save', {
		                		productId: '${product.id }',
		                		productAttrId: productAttrId,
		                		attrName: attrName,
		                		attrVal: attrVal
		    				}, function(data){
		    					if(data.isSuccess) {
		    						window.location.reload();
		    					} else {
		    						top.$.jBox.error(data.msg);
		    					}
		    				});
		                	return true;
		                }
					}
				});
				
			}
			
			/**
			 * 删除规格
			 */
			function deleteProductAttr(o, id) {
				$.post('${ctx}/shop/product/pa/delete/' + id, {
					
				}, function(data){
					if(data.isSuccess) {
						window.location.reload();
					} else {
						top.$.jBox.error(data.msg);
					}
				});
			}
			</script>
		</div>
		
		<div class="control-group">
			<label class="control-label">产品规格:</label>
			<div class="controls">
				<input type="button" value="新增" class="btn btn-primary btn-small" onclick="editProductSpec('', '', '', '', '', '', '', '')">
				<table class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>颜色</th>
							<th>尺寸</th>
							<th>类型</th>
							<th>出厂价格</th>
							<th>销售价格</th>
							<th>最低销售数</th>
							<th width="60">操作</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${psList }" var="ps" varStatus="status">
						<tr>
							<td>${ps.color }</td>
							<td>${ps.size }</td>
							<td>${ps.type }</td>
							<td>${ps.price }</td>
							<td>${ps.nowPrice }</td>
							<td>${ps.minSale }</td>
							<%-- <td><input type="hidden" name="psList[${status.index }].id" value="${ps.id }">
								<input type="number" name="psList[${status.index }].price" value="${ps.price }"></td> --%>
							<td><a href="javascript:;" onclick="editProductSpec(this, '${ps.id}', '${ps.color}', '${ps.size}', '${ps.type}', '${ps.price}', '${ps.nowPrice}', '${ps.minSale}')">修改</a>
								<a href="javascript:;" onclick="deleteProductSpec(this, '${ps.id}')">删除</a></td>
						</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			<script type="text/javascript">
			/**
			 * 新增规格
			 */
			function editProductSpec(o, productSpecId, color, size, type, factoryPrice, nowPrice, minSale) {
				top.$.jBox.open($('#specDiv').html(), "添加产品规格", 480, 360, {
					buttons:{ '确定': 1, '取消': 0},
					loaded:function(h){
						h.find('input[name="productSpecId"]').val(productSpecId);
						h.find('input[name="color"]').val(color);
						h.find('input[name="size"]').val(size);
						h.find('input[name="type"]').val(type);
						h.find('input[name="factoryPrice"]').val(factoryPrice);
						h.find('input[name="nowPrice"]').val(nowPrice);
						h.find('input[name="minSale"]').val(minSale);
						$(".jbox-content", top.document).css("overflow-y","hidden");
					},
					submit: function (v, h, f) {
						if (v == 0) {
		                    return true; 
		                } else {
		                	var productSpecId = h.find('input[name="productSpecId"]').val();
		                	var color = h.find('input[name="color"]').val();
		                	var size = h.find('input[name="size"]').val();
		                	var type = h.find('input[name="type"]').val();
		                	var factoryPrice = h.find('input[name="factoryPrice"]').val();
		                	var nowPrice = h.find('input[name="nowPrice"]').val();
		                	var minSale = h.find('input[name="minSale"]').val();
		                	if(typeof productSpecId == undefined || !productSpecId) {
		                		productSpecId = null;
		                	}
		                	if(!factoryPrice) {
		                		top.$.jBox.error('请填写出厂价！');
		                		return false;
		                	}
		                	if(!nowPrice) {
		                		top.$.jBox.error('请填写出售价！');
		                		return false;
		                	}
		                	if(!minSale) {
		                		minSale = 0;
		                	}
		                	
		                	$.post('${ctx}/shop/product/ps/save', {
		                		productId: '${product.id }',
		                		productSpecId: productSpecId,
		                		color: color,
		                		size: size,
		                		type: type,
		                		factoryPrice: factoryPrice,
		                		nowPrice: nowPrice,
		                		minSale: minSale
		    				}, function(data){
		    					if(data.isSuccess) {
		    						window.location.reload();
		    					} else {
		    						top.$.jBox.error(data.msg);
		    					}
		    				});
		                	return true;
		                }
					}
				});
				
			}
			
			/**
			 * 删除规格
			 */
			function deleteProductSpec(o, id) {
				$.post('${ctx}/shop/product/ps/delete/' + id, {
					
				}, function(data){
					if(data.isSuccess) {
						window.location.reload();
					} else {
						top.$.jBox.error(data.msg);
					}
				});
			}
			</script>
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
			<label class="control-label">权重:</label>
			<div class="controls">
				<form:input path="weight" htmlEscape="false" maxlength="200" class="input-mini required digits"/>&nbsp;
				<span>
					<input id="weightTop" type="checkbox" onclick="$('#weight').val(this.checked?'999':'0')"><label for="weightTop">置顶</label>
				</span>
				&nbsp;过期时间：
				<input id="weightDate" name="weightDate" type="text" readonly="readonly" maxlength="20" class="input-small Wdate"
					value="<fmt:formatDate value="${article.weightDate}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});"/>
				<span class="help-inline">数值越大排序越靠前，过期时间可为空，过期后取消置顶。</span>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label"></label>
			<div class="controls">
				<%-- <label>是否同步：</label><form:radiobuttons onclick="$('#searchForm').submit();" path="isSync" items="${fns:getDictList('shop_product_is_sync')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
				<label>是否新品：</label><form:radiobuttons path="isNew" items="${fns:getDictList('shop_product_is_new')}" itemLabel="label" itemValue="value" htmlEscape="false"/><br/>
				<label>是否推荐：</label><form:radiobuttons path="isRecommend" items="${fns:getDictList('shop_product_is_recommend')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				<!-- <span class="help-inline">新品，推荐。</span> -->
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
				<form:textarea id="content" htmlEscape="false" path="content" rows="4" maxlength="200" class="input-xxlarge" style="width:100%;height:500px;visibility:hidden;"/>
				<%-- <tags:ckeditor replace="content" uploadPath="/shop/product" /> --%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品主图:</label>
			<div class="controls">
				<form:hidden path="images" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<tags:kindeditor input="images" prefix="attached/images/${product.id }/" base="${fns:getConfig('QN_DOMAIN') }/" size="100"></tags:kindeditor>
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
	
	<div id="attrDiv" style="display: none;">
	<form class="form-horizontal">
		<input type="hidden" name="productAttrId">
		<div class="control-group">
			<label class="control-label">属性:</label>
			<div class="controls">
				<!-- <input type="text" maxlength="200" class="input required"> -->
				<select name="attr">
					<c:forEach items="${attrList }" var="a">
					<option value="${a.name }">${a.name }</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">属性值:</label>
			<div class="controls">
				<input name="attrVal" type="text" maxlength="200" class="input required">
			</div>
		</div>
	</form>
	</div>
	
	<div id="specDiv" style="display: none;">
	<form class="form-horizontal">
		<input type="hidden" name="productSpecId">
		<div class="control-group">
			<label class="control-label">颜色:</label>
			<div class="controls">
				<input type="text" name="color" maxlength="200" class="input required">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">尺寸:</label>
			<div class="controls">
				<input type="text" name="size" maxlength="200" class="input required">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">类型:</label>
			<div class="controls">
				<input type="text" name="type" maxlength="200" class="input required">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出厂价格:</label>
			<div class="controls">
				<input type="number" name="factoryPrice" maxlength="200" class="input required">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出售价格:</label>
			<div class="controls">
				<input type="number" name="nowPrice" maxlength="200" class="input required">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最少购买:</label>
			<div class="controls">
				<input type="number" name="minSale" maxlength="200" class="input required">
			</div>
		</div>
	</form>
	</div>
</body>
</html>