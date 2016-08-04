<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>供应商-商品管理</title>
	<meta name="decorator" content="default"/>
	<%-- 
	<link href="${ctxStatic}/select2/select2.css" type="text/css" rel="stylesheet" />
	<link href="${ctxStatic}/select2/select2-bootstrap.css" type="text/css" rel="stylesheet" />
	<script src="${ctxStatic}/select2/select2.min.js" type="text/javascript"></script>
	 --%>
	
	<link href="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/css/select2.min.css" rel="stylesheet" />
	<script src="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/js/select2.min.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#value").focus();
			
			$('select[multiple="multiple"]').select2({
		    	tags: true,
		        tokenSeparators: [',', ' ']
		    });
			/**
		    $("#spec").select2({
		    	tags: true,
		        tokenSeparators: [',', ' ']
		    });
			*/
			$('#priceDiv').find('a').off('click').on('click', function(){
				var _this = $(this);
				//top.$.jBox.confirm('', function(){});
				_this.parent().parent().remove();
			});
			$("#inputForm").validate({
				submitHandler: function(form){
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
			
			$('#btnAdd').click(function(){
				top.$.jBox.open($('#lwhDiv').html(), "添加长宽高", 480, 200, {
					buttons:{ '确定': 1, '取消': 0},
					loaded:function(h) {
						$(".jbox-content", top.document).css("overflow-y","hidden");
					},
					submit: function (v, h, f) {
						if (v == 0) {
							return true;
						} else {
							var length = h.find('input[name="length"]').val();
							var width = h.find('input[name="width"]').val();
							var height = h.find('input[name="height"]').val();
							//alert('length:' + length + ',width:' + width + ',height:' + height);
							$('#tableLwh tbody').append('<tr><td><input type="number" name="l" value="' + length + '" style="width:120px;"/></td><td><input type="number" name="w" value="' + width + '" style="width:120px;"/></td><td><input type="number" name="h" value="' + height + '" style="width:120px;"/></td><td><a href="javascript:;" onclick="del(this);">删除</a></td></tr>');
							return true;
						}
					}
				});
			});
			
			$('select').change(function(){
				var _this = $(this);
				var id = _this.attr('id');
				if(id == 'color' || id == 'size' || id == 'spec') {
					var color = $('#color').val() == null ? '' : $.map($('#color').val(), function(val, index) { return val; }).join(",");
					var size = $('#size').val() == null ? '' : $.map($('#size').val(), function(val, index) { return val; }).join(",");//$('#size').val();
					var spec = $('#spec').val() == null ? '' : $.map($('#spec').val(), function(val, index) { return val; }).join(",");//$('#spec').val();
					//alert('color:' + color + ',size:' + size + ',spec:' + spec);
					$.post('${ctx}/shop/sp/spec', {
						type: id,
						color: color,
						size: size,
						spec: spec
					}, function(data){
						if(data.isSuccess) {
							//alert(data.table);
							$('#priceDiv').find('table').remove();
							$('#priceDiv').append(data.table);
							$('#priceDiv').find('a').off('click').on('click', function(){
								var _this = $(this);
								//top.$.jBox.confirm('', function(){});
								_this.parent().parent().remove();
							});
							/* var colors = data.colors;
							var sizes = data.sizes;
							var specs = data.specs;
							//alert('colors:' + colors + ',sizes:' + sizes + ',specs:' + specs);
							if(typeof colors != 'undefined' && !colors) {
								
							}
							if(typeof sizes != 'undefined' && !sizes) {
								
							}
							if(typeof specs != 'undefined' && !specs) {
								
							} */
						} else {
							alert(data.msg);
						}
					});
				}
			});
		});
		
		function del(o) {
			var _this = $(o);
			_this.parent().parent().remove();
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/shop/sp/">供应商-商品列表</a></li>
		<li class="active"><a href="${ctx}/shop/sp/form?id=${sp.id}">供应商-商品<shiro:hasPermission name="shop:sp:edit">${not empty sp.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="shop:sp:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="sp" action="${ctx}/shop/sp/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<tags:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">供应商:</label>
			<div class="controls">
				<form:select path="supplier.id" class="input-medium required" style="width:220px;"><form:option value="" label=""/><form:options items="${supplierList}" itemValue="id" itemLabel="name" htmlEscape="false"/></form:select>
				<%-- <c:if test="${sp.supplier == null || sp.supplier.id == null }">
				<form:select path="supplier.id" class="input-medium required" style="width:220px;"><form:option value="" label=""/><form:options items="${supplierList}" itemValue="id" itemLabel="name" htmlEscape="false"/></form:select>
				</c:if>
				<c:if test="${sp.supplier != null && sp.supplier.id != null }">
				<form:hidden path="supplier.id"/>
				<label class="control-label"><b>${sp.supplier.name }</b></label>
				</c:if> --%>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;态:</label>
			<div class="controls">
				<form:select path="state" style="width:220px;" class="required">
					<form:options items="${fns:getDictList('shop_product_state')}" itemValue="value" itemLabel="label"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品编码:</label>
			<div class="controls">
				<form:input path="productCode" htmlEscape="false" maxlength="50" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品名称:</label>
			<div class="controls">
				<form:input path="productName" htmlEscape="false" maxlength="50" class="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">首页推荐:</label>
			<div class="controls">
				<form:radiobutton path="isRecommend" value="1"/>是  
               	<form:radiobutton path="isRecommend" value="0"/>否
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">产品品牌:</label>
			<div class="controls">
				<form:input path="productBrand" htmlEscape="false" maxlength="50" />
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">计价单位:</label>
			<div class="controls">
				<form:input path="unit" htmlEscape="false" maxlength="50" />
				<span class="help-inline">个，箱，卷等</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">最少购买数:</label>
			<div class="controls">
				<form:input path="minSale" htmlEscape="false" maxlength="50" />
				<span class="help-inline">指的是该产品的最少拍买个数</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">出&nbsp;&nbsp;厂&nbsp;&nbsp;价:</label>
			<div class="controls">
				<div class="row">
					<div class="span6">
						<table class="table table-striped table-bordered table-condensed">
							<thead>
								<tr>
									<th>出厂价</th>
									<th>淘宝价</th>
									<th>中国婚庆道具网价</th>
									<th>婚品汇价</th>
									<th>海洲价</th>
									<th>实体价</th>
									<th>其它价</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td><form:input path="price" htmlEscape="false" maxlength="6" class="required number" style="width:60px;"/></td>
									<td><form:input path="price1" htmlEscape="false" maxlength="6" class="number" style="width:60px;"/></td>
									<td><form:input path="price2" htmlEscape="false" maxlength="6" class="number" style="width:60px;"/></td>
									<td><form:input path="price3" htmlEscape="false" maxlength="6" class="number" style="width:60px;"/></td>
									<td><form:input path="price4" htmlEscape="false" maxlength="6" class="number" style="width:60px;"/></td>
									<td><form:input path="price5" htmlEscape="false" maxlength="6" class="number" style="width:60px;"/></td>
									<td><form:input path="price99" htmlEscape="false" maxlength="6" class="number" style="width:60px;"/></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">颜&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;色:</label>
			<div class="controls">
				<select id="color" name="color" multiple="multiple" style="width:220px;">
					<c:forEach items="${fns:getDictList('shop_product_color')}" var="d">
					<option value="${d.value }" <c:if test="${fn:indexOf(sp.color, d.value) >= 0}">selected</c:if>>${d.label }</option>
					</c:forEach>
				</select> 
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">尺&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;寸:</label>
			<div class="controls">
				<select id="size" name="size" multiple="multiple" style="width:220px;">
					<c:forEach items="${fns:getDictList('shop_product_size')}" var="d">
					<option value="${d.value }" <c:if test="${fn:indexOf(sp.size, d.value) >= 0}">selected</c:if>>${d.label }</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">长&nbsp;&nbsp;宽&nbsp;&nbsp;高:</label>
			<div class="controls">
				<div class="row">
					<div class="span6">
						<input id="btnAdd" type="button" value="增加" class="btn btn-small btn-primary">
						<table id="tableLwh" class="table table-striped table-bordered table-condensed">
							<thead>
								<tr>
									<th>长(cm)</th>
									<th>宽(cm)</th>
									<th>高(cm)</th>
									<th>操作</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${lengths }" varStatus="s">
								<tr><td><input type="number" name="l" value="${lengths[s.index] }" style="width:120px;"/></td><td><input type="number" name="w" value="${widths[s.index] }" style="width:120px;"/></td><td><input type="number" name="h" value="${heights[s.index] }" style="width:120px;"/></td><td><a href="javascript:;" onclick="del(this);">删除</a></td></tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">材&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;质:</label>
			<div class="controls">
				<form:input path="material"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">规&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;格:</label>
			<div class="controls">
				<%-- <form:input path="spec" />	 --%>	
				<select id="spec" name="spec" multiple="multiple">
					<c:forEach items="${fns:getDictList('shop_product_spec')}" var="d">
					<option value="${d.value }" <c:if test="${fn:indexOf(sp.spec, d.value) >= 0}">selected</c:if>>${d.label }</option>
					</c:forEach>
				</select>		
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"></label>
			<div class="controls">
				<div class="row">
					<div id="priceDiv" class="span9">
					${ppList }
					<table class="table table-striped table-bordered table-condensed">
						<thead>
							<c:if test="${not empty ppList }">
							<tr>
								<c:if test="${ppList[0].color != null and ppList[0].color != '' }">
								<td>颜色</td>
								</c:if>
								<c:if test="${ppList[0].size != null and ppList[0].size != '' }">
								<td>尺寸</td>
								</c:if>
								<c:if test="${ppList[0].type != null and ppList[0].type != '' }">
								<td>规格</td>
								</c:if>
								<td>最少购买数</td>
								<td>价格</td>
								<td>操作</td>
							</tr>
							</c:if>
							<c:if test="${empty ppList }">
							<tr>
								<td>颜色</td><td>尺寸</td><td>规格</td><td>价格</td><td>操作</td>
							</tr>
							</c:if>
						</thead>
						<tbody>
							<c:forEach items="${ppList }" var="p">
							<tr>
								<c:if test="${p.color != null and p.color != '' }">
								<td>${p.color }<input type='hidden' name='pc' value='${p.color }' /></td>
								</c:if>
								<c:if test="${p.size != null and p.size != '' }">
								<td>${p.size }<input type='hidden' name='ps' value='${p.size }' /></td>
								</c:if>
								<c:if test="${p.type != null and p.type != '' }">
								<td>${p.type }<input type='hidden' name='pt' value='${p.type }' /></td>
								</c:if>
								<td><input type="number" name="pm" value="${p.minSale }"/></td>
								<td><input type="number" name="pp" value="${p.price }"/></td>
								<td><a href="javascript:void(0);">删除</a></td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
					<!-- <table class="table table-striped table-bordered table-condensed">
						<thead>
							<tr><th></th><th></th></tr>
						</thead>
						<tbody>
							<tr>
								<td rowspan="1"></td>
							</tr>
						</tbody>
					</table> -->
					</div>
				</div>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">产品图片:</label>
			<div class="controls">
				<form:hidden path="image"/>
				<tags:ckfinder input="image" type="images" uploadPath="/shop/product" selectMultiple="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注:</label>
			<div class="controls">
				<form:textarea path="remarks" cols="20" rows="5" style="width:480px;" />		
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="shop:sp:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
	
	<div id="lwhDiv" style="display: none;">
		<form class="form-horizontal">
			<div class="control-group">
				<label class="control-label">长:</label>
				<div class="controls">
					<input type="number" name="length" value="0.00" />cm
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">宽:</label>
				<div class="controls">
					<input type="number" name="width" value="0.00"/>cm
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">高:</label>
				<div class="controls">
					<input type="number" name="height" value="0.00"/>cm
				</div>
			</div>
		</form>
	</div>
	
</body>
</html>