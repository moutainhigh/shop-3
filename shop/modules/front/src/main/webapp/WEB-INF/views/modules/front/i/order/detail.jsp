<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="decorator" content="account_default_new"/>
    <title>${accountTitle }</title>
    <content tag="local_script">
    <script type="text/javascript">
    $(function(){
    	$('#title').html('<a class="btn_mid_pink order_return" href="${ctx}/i/order">返回订单列表</a>${accountTitle }');
    });
    </script>
    </content>
</head>
<body style="background:white; width:960px;margin:0 auto;">
	<h4 class="order_num">
		<strong>交易单号：${order.id }</strong>
	</h4>
	<div class="notice_content clearFix">
		<div class="order_sta_l">
			交易单状态： <span class="status_green"><c:if test="${order.paystatus eq 'y' }">
						<c:if test="${order.status eq 'init' or order.status eq 'pass' }">等待发货</c:if>
						<c:if test="${order.status eq 'send' }">已发货</c:if>
						<c:if test="${order.status eq 'sign' }">已签收</c:if>
						<c:if test="${order.status eq 'file'}">交易完成</c:if>
					</c:if>
					<c:if test="${ order.status eq 'cancel' }">
						已取消
					</c:if>
					<c:if test="${order.paystatus eq 'n' and order.status ne 'cancel' }">
						等待付款
					</c:if></span>
		</div>
		<div class="order_sta_r">付款时间：<fmt:formatDate value="${order.payDate }" pattern="yyyy-MM-dd HH:mm:ss"/> </div>
		<div class="order_sta_l">
			交易金额：<span class="pink">${order.amount }元</span>
		</div>
		<div class="order_sta_r">发货时间：<c:if test="${order.deliveryDate != null}">
											<fmt:formatDate value="${order.deliveryDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
										 </c:if>
										 <c:if test="${order.deliveryDate == null}">
											未发货
										 </c:if>
		</div>
	</div>
	<div class="sub_container">
		<div class="sub_content">
			<div class="progress_bar">
				<div class="progress_outter">
					<c:if test="${order.paystatus eq 'y' }">
						<c:if test="${order.status eq 'init' or order.status eq 'pass' }">
							<div class="progress_inner" style="width: 390px"></div>
						</c:if>
						<c:if test="${order.status eq 'send' }">
							<div class="progress_inner" style="width: 590px"></div>
						</c:if>
						<c:if test="${order.status eq 'sign' or order.status eq 'file'}">
							<div class="progress_inner" style="width: 735px"></div>
						</c:if>
					</c:if>
					<c:if test="${empty order.paystatus or order.paystatus eq 'n'}">
					<div class="progress_inner" style="width: 20px"></div>
					</c:if>
				</div>
				<div class="progress_desc">
					<span class="step1">提交订单</span>
					<span class="step2">付款成功</span>
					<span class="step3">配货中</span>
					<span class="step4">已发货</span>
					<span class="step5">确认收货</span>
				</div>
			</div>
		</div>
	</div>
	<div class="clear"></div>

	<div class="sub_container" style="margin-top:30px;">
		<h4>配送信息</h4>
		<div class="sub_content">
			<table class="shipping_table">
				<tbody>
					<tr>
						<td class="detail_head">收货地址</td>
						<td class="order_info_td">${ordership.shipname }, ${ordership.phone }, ${ordership.province }-${ordership.city }-${ordership.area } ${ordership.shipaddress }</td>
						<%-- <c:if test="${order.osList.size() > 0 }">
						<td class="order_info_td">${order.osList[0].shipname }, 186****0051, 四川省-成都市-青羊区 青羊区青羊区青羊区青羊区</td>
						</c:if> --%>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
	<div class="sub_container" style="margin-top:30px;">
		<h4>商品信息</h4>
		<table class="order_items">
			<tbody>
				<tr>
					<th width="100">商家</th>
					<th>商品详情</th>
					<th width="120">商品规格</th>
					<th width="80">数量</th>
					<th width="80">单价</th>
					<th width="80">小计</th>
				</tr>
				<c:forEach items="${order.sellerList }" var="seller">
					<tr>
						<td rowspan="${seller.value.size()}">${seller.value[0].sellerName }</td>
						<c:forEach items="${seller.value }" var="od" varStatus="o">
							<c:if test="${o.index == 0 }">
								<td class="order_info_td"><a href="#" target="_blank" title="${od.productName }">${od.productName }</a></td>
								<td>
									<c:set value="${ fn:split(od.specInfo, ',') }" var="names" />
									<c:forEach items="${ names }" var="name">
										<p class="cart_item_prop"  style="margin-bottom:0; line-height:25px" title="${name }">${name }</p>
									</c:forEach>
								</td>
								<td>${od.number }</td>
								<td>¥${od.price }</td>
								<td class="td_price pink">¥${od.total0 }</td>
							</c:if>
							<c:if test="${o.index > 0 }">
								<tr>
									<td class="order_info_td"><a href="#" target="_blank" title="${od.productName }">${od.productName }</a></td>
									<td>
										<c:set value="${ fn:split(od.specInfo, ',') }" var="names" />
										<c:forEach items="${ names }" var="name">
											<p class="cart_item_prop"  style="margin-bottom:0; line-height:25px" title="${name }">${name }</p>
										</c:forEach>
									</td>
									<td>${od.number }</td>
									<td>¥${od.price }</td>
									<td class="td_price pink">¥${od.total0 }</td>
								</tr>
							</c:if>
						</c:forEach>
					</tr>
				</c:forEach>
				<!-- <tr>
					<td class="order_info_td"><a href="#" target="_blank"
						title="产品名称更新中,多款包装随机发货!">产品名称更新中,多款包装随机发货!</a></td>
					<td>1</td>
					<td>¥105.00</td>
					<td class="td_price pink">¥105.00</td>
				</tr> -->
			</tbody>
		</table>
	</div>

</body>
</html>