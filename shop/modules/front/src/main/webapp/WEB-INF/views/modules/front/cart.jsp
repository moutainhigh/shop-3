
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!doctype html> 
<html>
<head>
    <meta name="decorator" content="cart_default_new"/>
    <title>购物车</title>
    <link href="${ctxStatic }/css/shopping-cart.css" rel="stylesheet" type="text/css">
</head>
<body class="full">
<c:if test="${not empty carts }">
<div class="content_show_wrapper">
	<div class="cart_notification cart_error" style="">
		<div class="message">
			<p>111</p>
		</div>
	</div>
	<%-- <form id="submitForm" action="${ctx }/cart/submit" method="post"> --%>
	<form id="submitForm" action="${ctx }/order/confirm" method="post">
	<div id="group_show">
		<div class="groups_wrapper">
			<table class="cart_group_item cart_group_item_product">
				<thead>
					<tr>
						<th class="cart_overview">
							<div class="cart_group_header">
								<input class="js_group_selector cart_group_selector" autocomplete="off" type="checkbox" name="checkAll">
								<h2>全选</h2>
							</div>
						</th>
						<th class="cart_price">
							价（元）
						</th>
						<th class="cart_num">
							数量
						</th>
						<th class="cart_total">
							小计（元）
						</th>
						<th class="cart_option">
							操作
						</th>
					</tr>
				</thead>
			</table>
			
			<c:forEach items="${carts }" var="item">
				<table class="cart_group_item cart_group_item_product">
					<thead>
						<tr>
							<th class="cart_overview">
								<div class="cart_group_header">
									<input class="js_group_selector cart_group_selector" type="checkbox" name="checkSeller">
									<h2 style="font-size: 12px;">${item.value[0].sellerName}</h2>
								</div>
							</th>
							<th class="cart_price">
							</th>
							<th class="cart_num">
							</th>
							<th class="cart_total">
							</th>
							<th class="cart_option">
							</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${item.value }" var="cart">
							<tr class="cart_item" product_type="product">
								<td valign="top">
									<div class="cart_item_desc clearfix">
										<input class="js_item_selector cart_item_selector" data-product-id="${cart.productId}" data-spec-id="${cart.specId}" data-item-key="${cart.id }"  type="checkbox" name="id" value="${cart.id }" checked="checked">
										<div class="cart_item_desc_wrapper">
											<a class="cart_item_pic" href="${ctx }/product/${cart.productId}" target="_blank">
												<img src="${IMAGE_ROOT_PATH }/${cart.productImage }" alt="">
												<span class="sold_out_pic png"></span>
											</a>
											<div style="float: left;">
												<a class="cart_item_link" title="${cart.productName }" href="${ctx }/product/${cart.productId}" target="_blank">${cart.productName }</a>
												<%-- <c:if test="${cart.activityType eq 'j' and cart.requiredIntegral gt 0 }">
												<div class="sale_info clearfix">
													<div class="tips_pop_full float_box JS_tips_pop_full">
														<div>
															<a class="sale_tag gift JS_sale_tag" data-promo-type="gift"> 积分兑换（${cart.requiredIntegral }积分兑换）</a>
														</div>
													</div>
												</div>
												</c:if> --%>
												<c:if test="${cart.activityType eq 'c'}">
												<div class="sale_info clearfix">
													<div class="tips_pop_full float_box JS_tips_pop_full">
														<div>
															<a class="sale_tag gift JS_sale_tag" data-promo-type="gift">${cart.activityName }</a>
														</div>
													</div>
												</div>
												</c:if>
											</div>
											<c:set value="${ fn:split(cart.specName, ',') }" var="names" />
											<div class="cart_item_spec clearfix">
												<c:forEach items="${ names }" var="name">
													<p class="cart_item_prop" title="${name }">${name }</p>
												</c:forEach>
											 </div>
									</div>
								</div>
										
								</td>
									
								<td>
									<div class="cart_item_price">
										<p class="jumei_price">${cart.nowPrice }</p>
										<p class="market_price">${cart.marketPrice }</p>
									</div>
								</td>
								<td>
									<div class="cart_item_num ">
										<div class="item_quantity_editer clearfix" data-item-key="${cart.id }" data-product-id="${cart.productId }" data-spec-id="${cart.specId }">
											<span class="decrease_one <c:if test="${cart.quantity == 1}">disabled</c:if>" onclick="updateCart(this, -1, '${cart.productId}', '${cart.specId}')">-</span>
											<input class="item_quantity" value="${cart.quantity }" type="text">
											<span class="increase_one" onclick="updateCart(this, 1, '${cart.productId}', '${cart.specId}')">+</span>
										</div>
										<div class="item_shortage_tip">
										</div>
									</div>
								</td>
								<td>
									<div class="cart_item_total">
										<p class="item_total_price"><fmt:formatNumber value="${cart.nowPrice * cart.quantity }" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></p>
										<p>
											省 <span class="item_saved_price"><fmt:formatNumber value="${cart.marketPrice * cart.quantity - cart.nowPrice * cart.quantity }" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></span>
										</p>
									</div>
								</td>
								<td>
									<div class="cart_item_option">
										<a class="icon_small delete_item png" data-item-key="${cart.id }" data-spec-id="${cart.specId }" data-product-id="${cart.productId }" href="javascript:void(0);" title="删除"></a>
									</div>
								</td>
						</tr>
					</c:forEach>
			</tbody>
			<%-- <tfoot>
			<tr>
				<td colspan="5">
					 商品金额： <span class="group_total_price">￥${totalPrice }</span>
				</td>
			</tr>
			</tfoot> --%>
			</table>
			</c:forEach>
		</div>
		<div class="common_handler_anchor">
		</div>
		<div class="common_handler">
			<div class="right_handler">
				 共 <span class="total_amount">${total }</span> &nbsp;件商品 &nbsp;&nbsp; 
				 <c:if test="${totalIntegral gt 0 }">商品所需积分：<span class="total_integral">${totalIntegral }</span>&nbsp;&nbsp;</c:if>
				 商品总价（不含运费）：<span class="total_price">￥${totalPrice }</span><a id="go_to_order" class="btn" href="javascript:void(0);">去结算</a>
			</div>
			<label for="js_all_selector" class="cart_all_selector_wrapper"><input class="js_all_selector all_selector" type="checkbox" name="checkAll" style="float: left; margin: 19px 10px 0 5px;;">全选</label><a class="go_back_shopping" href="${ctx }/mall">继续购物</a><span class="seperate_line">|</span><a class="clear_cart_all" href="javascript:void(0);">清空选中商品</a>
		</div>
		<%-- 
		<div class="content_footer clearfix">
			<div class="cart_timer_wrapper">
				<i class="icon_small png"></i><span class="cart_timer_counting"> 请在 <span class="cart_timer_text">19分34秒</span> 内去结账，并在下单后 <span class="pink">20</span> 分钟内完成支付 </span><span class="cart_timer_out"> 已超过购物车商品保留时间，请尽快结算。 </span> 
				</div>
		</div>
		 --%>
	</div>
	</form>
</div>
</c:if>

<div id="emptyCart" style="<c:if test='${not empty carts }'>display:none</c:if>">
	<div class="content_header clearfix">
	</div>
	<div class="cart_empty clearfix">
		<img class="cart_empty_logo" src="${ctxAssets }/image/empty_icon.jpg" alt="">
		<div class="cart_empty_right">
			<h2>您的购物车中没有商品，请先去挑选心爱的商品吧！</h2>
			<p class="cart_empty_backtip">
				 您可以 <a class="btn" href="${ctx }/">  返回首页  </a> 挑选喜欢的商品
			</p>
<!-- 			<div class="search_block"> -->
<!-- 				<form action="${ctx }/search" method="get" pos="top"> -->
<!-- 					<input name="filter" value="" type="hidden"> -->
<!-- 					<button type="submit" class="btn_cart_search">搜索</button> -->
<!-- 					<input name="search" class="search_input" placeholder="搜搜您想要的商品" autocomplete="off" type="text"> -->
<!-- 				</form> -->
<!-- 			</div> -->
		</div>
	</div>
	<div class="content_footer clearfix">
	</div>
</div>
    
<input type="hidden" id="ctx" name="ctx" value="${ctx }"/>
<content tag="local_script">
<script type="text/javascript" src="${ctxAssetsJS }/javascripts/common.js"></script>
<script type="text/javascript" src="${ctxAssetsJS }/javascripts/cart.js"></script>
</content>
</body>
</html>