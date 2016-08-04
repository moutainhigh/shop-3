<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<html>
<head>
	<title>产品管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
	.table thead th {
		vertical-align: middle;
		text-align: center;
	}
	</style>
	<link href="//cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/bootstrap-editable/css/bootstrap-editable.css" rel="stylesheet"/>
	<script src="//cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.0/bootstrap-editable/js/bootstrap-editable.min.js"></script>
	<%-- <link rel="stylesheet" href="${ctxStatic }/kindeditor/themes/default/default.css" />
	<script src="${ctxStatic }/kindeditor/kindeditor-min.js"></script>
	<script src="${ctxStatic }/kindeditor/lang/zh_CN.js"></script> --%>
	
	<script type="text/javascript">
		$(function(){
			$('a[name="price"]').editable();
			
			$('select[name="increaseRate"]').change(function(){
				var value = $(this).val();
				var productId = $(this).attr('data-id');
				$.post('${ctx}/shop/pv/' + productId + '/modifyIncreaseRate', {
					value: value
				}, function(data) {
					if(data === true) {
						top.$.jBox.tip('保存上浮率成功');
						$('#searchForm').submit();
					} else {
						top.$.jBox.tip('保存上浮率失败');
					}
				});
			});
			
			$('select[name="expressPrice"]').change(function(){
				var value = $(this).val();
				var productId = $(this).attr('data-id');
				$.post('${ctx}/shop/pv/' + productId + '/modifyExpressPrice', {
					value: value
				}, function(data) {
					/* alert(data); */
					if(data === true) {
						top.$.jBox.tip('保存物流价成功');
					} else {
						top.$.jBox.tip('保存物流价失败');
					}
				});
			});
			
			$('a[name="delete"]').click(function(){
				var productName = $(this).attr('data-name');
				var productId = $(this).attr('data-id');
				
				top.$.jBox.confirm('是否删除产品--' + productName, '系统提示', function(v,h,f){
					if(v=='ok'){
						loading('正在提交，请稍等...');
						$.post('${ctx}/shop/pv/' + productId + '/delete', {
						}, function(data) {
							if(data === true) {
								top.$.jBox.tip('删除产品成功');
								$('#searchForm').submit();
							} else {
								top.$.jBox.tip('删除产品失败');
							}
						});
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
				
			});
			
			$('a[name="images"],a[name="details"]').click(function(){
				var id = $(this).attr('data-id');
				var name = $(this).attr('name');
				var title = "images" == name ? "产品主图" : "产品详情图";
				top.$.jBox.open('iframe:${ctx}/shop/pv/image?id=' + id + "&type=" + name, title, $(top.document).width()-220,$(top.document).height()-180,{
					buttons:{"关闭":true},
					loaded:function(h){
						$(".jbox-content", top.document).css("overflow-y","hidden");
						//$(".nav,.form-actions,[class=btn]", h.find("iframe").contents()).hide();
						$("body", h.find("iframe").contents()).css("margin","10px");
					}
				});
				
			});
			<%--
			KindEditor.ready(function(K) {
				$('a[name="images"]').click(function(){
					var _this = $(this);
					var productId = _this.attr('data-id');
					var prefix = 'attached/images/' + productId + '/';
					var editor = K.editor({
						uploadJson: '${pageContext.request.contextPath}/servlet/upload?prefix=' + prefix,
						fileManagerJson : '${pageContext.request.contextPath}/servlet/kindeditor?prefix=' + prefix,
						allowFileManager : true
					});
				
					editor.loadPlugin('image', function() {
						editor.plugin.imageDialog({
							imageUrl : K('#url1').val(),
							clickFn : function(url, title, width, height, border, align) {
								K('#url1').val(url);
								editor.hideDialog();
							}
						});
					});
				});
				
			});
			--%>
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/shop/product">产品列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="product" action="${ctx}/shop/pv/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>供应商：</label>
		<select id="supplierId" name="supplierId">
			<option value="">--全部--</option>
			<c:forEach items="${supplierList }" var="s">
			<option value="${s.id }" <c:if test="${supplierId eq s.id }">selected</c:if>>${s.name }</option>
			</c:forEach>
		</select>
		&nbsp;
		<label>产品名称：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>&nbsp;
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
		<%-- <label>状态：</label><form:radiobuttons onclick="$('#searchForm').submit();" path="delFlag" items="${fns:getDictList('shop_del_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/> --%>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<%--<td>
					<a name="images" href="javascript:;">主图</a><br/>
					<a name="details" href="javascript:;">详情图</a>
				</td> --%>
		<thead>
			<tr>
				<th width="160" rowspan="2">供应商</th>
				<th width="160" rowspan="2">名称</th>
				<th width="100" width="30" rowspan="2">图片</th>
				<th width="90" rowspan="2">出厂价</th>
				<th width="90" rowspan="2">售价</th>
				<th width="90" rowspan="2">价格上浮率</th>
				<th width="90" rowspan="2">物流费</th>
				<th rowspan="1" colspan="5">规格</th>
				<th width="60" rowspan="2">操作</th>
			</tr>
			<tr> 
				<th width="60">颜色</th>
				<th width="60">尺寸</th>
				<th width="60">类型</th>
				<th width="90">出厂价</th>
				<th width="90">售价</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="product">
			<c:set value="${fn:split(product.images, ',') }" var="images" />
			<c:if test="${fn:length(images) gt 0 }"> 
			<c:set value="${images[0] }" var="image" /> 
			</c:if>
			
			<c:if test="${fn:length(product.psList) eq 0}">
			<tr> 
				<td>${product.supplierNames }</td>
				<td><label title="${product.name}">${fns:abbr(product.name,20)}</label></td>
				<td><img src="${fns:getConfig('QN_DOMAIN') }/${image }-px100" height="100" width="100" alt="${product.name }"/></td>
				<td><a name="price" href="#" data-type="number" data-pk="${product.id }" data-url="${ctx }/shop/pv/${product.id }/price/modify" data-title="出厂价">${product.factoryPrice}</a></td>
				<td>${product.price }<br/>${product.price*0.9 }</td>
				<td>
					<input type="hidden" name="increaseRate" value="${product.increaseRate }">
					<select name="increaseRate" style="width: 90px" data-id="${product.id }">
						<option value="0"></option>
						<c:forEach items="${fns:getDictList('shop_price_increase_rate') }" var="v">
						<option value="${v.value }" <c:if test="${product.increaseRate eq v.value }">selected</c:if>>${v.label }</option>
						</c:forEach>
					</select>
				</td>
				<td>
					<input type="hidden" name="expressPrice" value="${product.expressPrice }">
					<select name="expressPrice" style="width: 90px" data-id="${product.id }">
						<option value="0"></option>
						<c:forEach items="${fns:getDictList('shop_price_express_price') }" var="v">
						<option value="${v.value }" <c:if test="${product.expressPrice eq v.value }">selected</c:if>>${v.label }</option>
						</c:forEach>
					</select>
				</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td>
					<a name="images" href="javascript:;" data-id="${product.id }">主图</a><br/>
					<a name="details" href="javascript:;" data-id="${product.id }">详情图</a><br/>
					<a name="delete" href="javascript:;" data-id="${product.id }" data-name="${product.name }">删除</a>
				</td>
			</tr>
			</c:if>
			
			<c:if test="${fn:length(product.psList) gt 0}">
			<c:forEach items="${product.psList }" var="ps" varStatus="s">
			<tr> 
				<c:if test="${s.index eq 0 }">
				<td rowspan="${fn:length(product.psList)}">${product.supplierNames }</td>
				<td rowspan="${fn:length(product.psList)}"><label title="${product.name}">${fns:abbr(product.name,40)}</label></td>
				<td rowspan="${fn:length(product.psList)}"><img src="${fns:getConfig('QN_DOMAIN') }/${image }-px100" height="100" width="100" alt="${product.name }"/></td>
				<td rowspan="${fn:length(product.psList)}"><a name="price" href="#" data-type="number" data-pk="${product.id }" data-url="${ctx }/shop/pv/${product.id }/price/modify" data-title="出厂价">${product.factoryPrice}</a></td>
				<td rowspan="${fn:length(product.psList)}">${product.price }<br/>${product.price*0.9 }</td>
				<td rowspan="${fn:length(product.psList)}">
					<input type="hidden" name="increaseRate" value="${product.increaseRate }">
					<select name="increaseRate" style="width: 90px" data-id="${product.id }">
						<option value="0"></option>
						<c:forEach items="${fns:getDictList('shop_price_increase_rate') }" var="v">
						<option value="${v.value }" <c:if test="${product.increaseRate eq v.value }">selected</c:if>>${v.label }</option>
						</c:forEach>
					</select>
				</td>
				<td rowspan="${fn:length(product.psList)}">
					<input type="hidden" name="expressPrice" value="${product.expressPrice }">
					<select name="expressPrice" style="width: 90px" data-id="${product.id }">
						<option value="0"></option>
						<c:forEach items="${fns:getDictList('shop_price_express_price') }" var="v">
						<option value="${v.value }" <c:if test="${product.expressPrice eq v.value }">selected</c:if>>${v.label }</option>
						</c:forEach>
					</select>
				</td>
				<td>${ps.color}</td>
				<td>${ps.size}</td>
				<td>${ps.type}</td>
				<td><a name="price" href="#" data-type="number" data-pk="${product.id }" data-url="${ctx }/shop/pv/${product.id }/${ps.id }/price/modify" data-title="出厂价">${ps.price}</a></td>
				<td>${ps.nowPrice}<br/>${ps.nowPrice*0.9 }</td>
				<td rowspan="${fn:length(product.psList)}">
					<a name="images" href="javascript:;" data-id="${product.id }">主图</a><br/>
					<a name="details" href="javascript:;" data-id="${product.id }">详情图</a><br/>
					<a name="delete" href="javascript:;" data-id="${product.id }" data-name="${product.name }">删除</a>
				</td>
				</c:if>
				<c:if test="${s.index gt 0 }">
				<td>${ps.color}</td>
				<td>${ps.size}</td>
				<td>${ps.type}</td>
				<td><a name="price" href="#" data-type="number" data-pk="${product.id }" data-url="${ctx }/shop/pv/${product.id }/${ps.id }/price/modify" data-title="出厂价">${ps.price}</a></td>
				<td>${ps.nowPrice}<br/>${ps.nowPrice*0.9 }</td>
				</c:if>
			</tr>
			</c:forEach>
			</c:if>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>