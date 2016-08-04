<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>产品管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		function viewComment(href){
			top.$.jBox.open('iframe:'+href,'查看评论',$(top.document).width()-220,$(top.document).height()-120,{
				buttons:{"关闭":true},
				loaded:function(h){
					$(".jbox-content", top.document).css("overflow-y","hidden");
					$(".nav,.form-actions,[class=btn]", h.find("iframe").contents()).hide();
					$("body", h.find("iframe").contents()).css("margin","10px");
				}
			});
			return false;
		}
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
		<shiro:hasPermission name="shop:product:edit"><li><a href="${ctx }/shop/product/form?id=${product.id}">产品添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="product" action="${ctx}/shop/product/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<label>供应商：</label>
		<select id="supplierId" name="supplierId">
			<option value="">--全部--</option>
			<c:forEach items="${supplierList }" var="s">
			<option value="${s.id }" <c:if test="${supplierId eq s.id }">selected</c:if>>${s.name }</option>
			</c:forEach>
		</select>
		<%-- <label>商品分类：</label>
		<select id="catalogId" name="catalogId">
			<option value="">--全部--</option>
			<c:forEach items="${catalogList }" var="s">
			<option value="${s.id }" <c:if test="${catalogId eq s.id }">selected</c:if>>${s.name }</option>
			</c:forEach>
		</select> --%>
		<label>名称：</label><form:input path="name" htmlEscape="false" maxlength="50" class="input-medium"/>&nbsp;
		<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/>&nbsp;&nbsp;
		<label>是否同步：</label><form:radiobuttons onclick="$('#searchForm').submit();" path="isSync" items="${fns:getDictList('shop_product_is_sync')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
		<label>是否新品：</label><form:radiobuttons onclick="$('#searchForm').submit();" path="isNew" items="${fns:getDictList('shop_product_is_new')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
		<label>是否推荐：</label><form:radiobuttons onclick="$('#searchForm').submit();" path="isRecommend" items="${fns:getDictList('shop_product_is_recommend')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
	</form:form>
	<tags:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th width="40">ID</th>
				<th width="80">编号</th>
				<th width="160">供应商</th>
				<th >名称</th>
				<th width="100">图片</th>
				<th width="30">单位</th>
				<shiro:hasPermission name="shop:product:audit">
				<th width="90">出厂价</th>
				</shiro:hasPermission>
				<th width="90">在售价</th>
				<th width="90">状态</th>
				<th width="90">操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="product">
			<c:set value="${fn:split(product.images, ',') }" var="images" />
			<c:if test="${fn:length(images) gt 0 }"> 
			<c:set value="${images[0] }" var="image" /> 
			</c:if>
			<tr>	
				<td>${product.id }</td>
				<td>${product.sn }</td>
				<td>${product.supplierNames }</td>
				<td><a href="${ctx}/shop/product/form?id=${product.id}" title="${product.name}">${fns:abbr(product.name,40)}</a>
				<c:if test="${product.isNew eq '1'}"><span class="label label-important">新品</span></c:if>
				<c:if test="${product.isRecommend eq '1'}"><span class="label label-info">推荐</span></c:if>
				<c:if test="${product.isSync eq '1'}"><span class="label label-success">已同步</span></c:if>
				<c:if test="${product.isSync ne '1'}"><span class="label label-inverse">未同步</span></c:if>
				</td>
				<td><img src="${fns:getConfig('QN_DOMAIN') }/${image }-px100" height="100" width="100" alt="${product.name }"/></td>
				<td>${product.unit}</td>
				<shiro:hasPermission name="shop:product:edit">
				<td>${product.factoryPrice}</td>
				</shiro:hasPermission>
				<td>${product.price}</td>
				<td>${fns:getDictLabel(product.status, 'shop_product_status', '无')}</td>
				<td>
					<shiro:hasPermission name="shop:product:edit">
	    				<a href="${ctx}/shop/product/form?id=${product.id}">修改</a>
	    				<shiro:hasPermission name="shop:product:edit">
							<a href="${ctx}/shop/product/delete?id=${product.id}" onclick="return confirmx('确认要${product.delFlag ne 0?'发布':'删除'}该产品吗？', this.href)" >${product.delFlag ne 0?'发布':'删除'}</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="shop:product:sync">
							<a name="link-sync" href="javascript:;" data-id="${product.id }">同步</a>
							<div id="syncDiv" style="display: none;">
							<form class="form-horizontal">
								<div class="control-group">
									<label class="control-label">环境:</label>
									<div class="controls">
										<label>测试环境</label><input type="radio" name="env" value="test" checked="checked">
										<label>生产环境</label><input type="radio" name="env" value="production">
									</div>
								</div>
							</form>
							</div>
							<script type="text/javascript">
							$(function(){
								$('a[name="link-sync"]').click(function(){
									var id = $(this).attr('data-id');
									
									$.post('${ctx}/shop/product/sync/' + id, {
										
									}, function(data){
										
									});
								});
							});
							</script>
						</shiro:hasPermission>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>