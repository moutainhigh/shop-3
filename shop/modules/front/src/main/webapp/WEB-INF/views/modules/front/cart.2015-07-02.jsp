<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="Cache-Control" content="no-store" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <title>购物车</title>
    <link href="${ctxStatic }/css/shopping-cart.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic }/css/style.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic }/css/all.css" rel="stylesheet" type="text/css">
	<link href="${ctxStatic }/css/common.css" rel="stylesheet" type="text/css">

    <script type="text/javascript" src="${ctxStaticJS }/js/jquery-all.js"></script>
    <script type="text/javascript" src="${ctxStaticJS }/js/common.js"></script>
    <script type="text/javascript" src="${ctxStaticJS }/js/cart.js"></script>
	<style type="text/css">
	.cart_header {
		width: 960px;
		margin: 0 auto;
	}
	
	.cart_header_box {
		border-bottom: 2px solid #e5e5e5;
		box-shadow: 0px 1px 2px rgba(0, 0, 0, 0.1);
		padding-bottom: 15px;
	}
	
	.cart_header .logo_box {
		float: left;
	}
	
	.cart_header .order_path {
		float: right;
		width: 392px;
		height: 50px;
	}
	
	.cart_header .order_path_1 {
		background: url(images/order_path.jpg) no-repeat;
		background-position: 0 0;
	}
	
	.cart_header .order_path_2 {
		background: url(images/order_path.jpg) no-repeat;
		background-position: 0 -50px;
	}
	
	.cart_header .order_path_3 {
		background: url(images/order_path.jpg) no-repeat;
		background-position: 0 -100px;
	}
	
	.cart_top {
		position: relative;
		height: 32px;
		line-height: 32px;
		color: #999999;
		width: 960px;
		margin: 0 auto;
	}
	
	.cart_top .user_box {
		position: absolute;
		right: 0;
		top: 0;
	}
	
	.cart_top .user_box .tips {
		font-style: normal;
		color: #dddddd;
		padding: 0 5px;
	}
	
	.cart_top .user_box .out,.cart_top .user_box .query {
		color: #999999;
	}
	
	.cart_top .user_box a:hover {
		text-decoration: none;
		color: #ed145b;
	}
	</style>
	<script>
	$(function(){
		/**
		$('.decrease_one,.increase_one').click(function(){
			var _this = $(this);
			var _p = _this.parent();
			var clz = _this.attr('class');
			var productId = _p.attr('data-product-id');
			var action = clz == 'increase_one' ? 1 : -1;
			var quantity = _parent.find('.item_quantity').val();
			if(action == -1 && quantity == 1) {
				return;
			} 
			alert('productId:' + productId + ',action:' + action + ',quantity:' + quantity);
			quantity = action == -1 ? quantity -1 : quantity + 1;
			$.ajax({
    		    type: "post",
    		    data: {
    		    	productId: productId,
    		    	action: action,
    		    	quantity: 1
            	},
    		    url: "${ctx}/cart/update",
            	beforeSend: function () {
    		        $(".loading").show();
    		    },
    		    success: function (data) {
    		    	log.info(data);
    		    },
    		    complete: function () {
    		        $(".loading").hide();
    		    },
    		    error: function (data) {
    		        log.error("error: " + data.responseText);
    		    }
    		});
		});
		*/
	});
	</script>
</head>
<body class="full">
<div style="background: white;">
	<div class="cart_top">
		<div class="user_box">
            <shiro:guest>          
			<div>
				欢迎您，<a href="${ctx }/login">登录</a><i class="tips">|</i><a href="${ctx }/regist">免费注册</a>
			</div>
			</shiro:guest>
			<shiro:user>
			<div>
				欢迎您，<shiro:principal property="username"/>&nbsp;</a>[<a href="${ctx }/logout" class="track"> &nbsp;退出&nbsp;</a>]</span>
			</div>
			</shiro:user>
		</div>
	</div>
	<div class="cart_header_box">
		<div class="cart_header clearfix">
			<h1 class="logo_box">
				<a href="${ctx }" target="_blank" id="home"> <img src="${ctxStatic }/images/logo01.png" alt=""></a>
			</h1>
			<div class="order_path order_path_1 "></div>
		</div>
	</div>
</div>
<c:if test="${not empty carts }">
<div class="content_show_wrapper">
	<div class="cart_notification cart_error" style="">
		<div class="message">
			<p>111</p>
		</div>
	</div>
	<div id="group_show">
		<%-- 
		<div class="content_header clearfix">
			<div class="cart_timer_wrapper">
				<i class="icon_small png"></i><span class="cart_timer_counting"> 请在 <span class="cart_timer_text">19分34秒</span> 内去结账，并在下单后 <span class="pink">20</span> 分钟内完成支付 </span><span class="cart_timer_out"> 已超过购物车商品保留时间，请尽快结算。 </span> 
				</div>
			
			<div class="common_shippment">
				<i class="icon_small icon_baoyou png">包邮</i> 发货商品满2件或299元包邮,新用户首单全场满39元包邮
			</div>
		</div>
		 --%>
		<div class="groups_wrapper">
			<table class="cart_group_item cart_group_item_product">
			<thead>
			<tr>
				<th class="cart_overview">
					<div class="cart_group_header">
						<input class="js_group_selector cart_group_selector" checked="checked" type="checkbox">
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
			<tbody>
			<c:forEach items="${carts }" var="cart">
			<tr class="cart_item " product_type="product">
				<td valign="top">
					<div class="cart_item_desc clearfix">
						<input class="js_item_selector cart_item_selector" data-item-key="${cart.id }" checked="checked" type="checkbox">
						<div class="cart_item_desc_wrapper">
							<a class="cart_item_pic" href="${ctx }/product/${cart.productId}" target="_blank">
								<img src="${cart.productImage }" alt=""><span class="sold_out_pic png"></span></a>
							<a class="cart_item_link" title="${cart.productName }" href="${ctx }/product/${cart.productId}" target="_blank">${cart.productName }</a>
							<p class="sku_info"></p>
							<c:if test="${cart.activityType eq 'j' and cart.requiredIntegral gt 0 }">
							<div class="sale_info clearfix">
								<div class="tips_pop_full float_box JS_tips_pop_full">
									<div>
										<a class="sale_tag gift JS_sale_tag" data-promo-type="gift"> 积分兑换（${cart.requiredIntegral }积分兑换） <i class="icon_small png"></i></a>
									</div>
								</div>
							</div>
							</c:if>
							<%-- 
							<div class="sale_info clearfix">
								<div class="tips_pop_full float_box JS_tips_pop_full">
									<div>
										<a class="sale_tag gift JS_sale_tag" data-promo-type="gift"> 满赠 <i class="icon_small png"></i></a>
									</div>
									<div class="pop_box JS_pop_box">
										<div>
											<span class="arrow_t_1"><span class="arrow_t_2"></span></span>
										</div>
										<div>
											<a class="clearfix promo_sale_item promo_has_url" target="_blank" href="#"><span class="title">158赠128元旅行6件套</span><span class="tips">查看活动</span></a>
										</div>
									</div>
								</div>
							</div>
							 --%>
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
						<div class="item_quantity_editer clearfix" data-item-key="${cart.id }" data-product-id="${cart.productId }">
							<span class="decrease_one <c:if test="${cart.quantity == 1}">disabled</c:if>" onclick="updateCart(this, -1, '${cart.productId}')">-</span>
							<input class="item_quantity" value="${cart.quantity }" type="text">
							<span class="increase_one" onclick="updateCart(this, 1, '${cart.productId}')">+</span>
						</div>
						<div class="item_shortage_tip">
						</div>
					</div>
				</td>
				<td>
					<div class="cart_item_total">
						<p class="item_total_price">${cart.nowPrice * cart.quantity }</p>
						<p>
							省 <span class="item_saved_price">${cart.marketPrice * cart.quantity - cart.nowPrice * cart.quantity }</span>
						</p>
					</div>
				</td>
				<td>
					<div class="cart_item_option">
						<a class="icon_small delete_item png" data-item-key="${cart.id }" data-product-id="${cart.productId }" href="javascript:void(0);" title="删除"></a>
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
		</div>
		<div class="common_handler_anchor">
		</div>
		<div class="common_handler">
			<div class="right_handler">
				 共 <span class="total_amount">${total }</span> &nbsp;件商品 &nbsp;&nbsp; 
				 <c:if test="${totalIntegral gt 0 }">商品所需积分：<span class="total_integral">${totalIntegral }</span>&nbsp;&nbsp;</c:if>
				 商品应付总额：<span class="total_price">￥${totalPrice }</span><a id="go_to_order" class="btn" href="${ctx }/cart/submit">去结算</a>
			</div>
			<label for="js_all_selector" class="cart_all_selector_wrapper"><input checked="checked" class="js_all_selector all_selector" type="checkbox"> 全选 </label><a class="go_back_shopping" href="${ctx }/mall">继续购物</a><span class="seperate_line">|</span><a class="clear_cart_all" href="javascript:void(0);">清空选中商品</a>
		</div>
		<%-- 
		<div class="content_footer clearfix">
			<div class="cart_timer_wrapper">
				<i class="icon_small png"></i><span class="cart_timer_counting"> 请在 <span class="cart_timer_text">19分34秒</span> 内去结账，并在下单后 <span class="pink">20</span> 分钟内完成支付 </span><span class="cart_timer_out"> 已超过购物车商品保留时间，请尽快结算。 </span> 
				</div>
		</div>
		 --%>
	</div>
	<div id="cart_side_nav">
		<a href="#"></a>
	</div>
</div>
</c:if>
<c:if test="${empty carts }">
<div id="group_show">
	<div class="content_header clearfix">
	</div>
	<div class="cart_empty clearfix">
		<img class="cart_empty_logo" src="http://s0.jmstatic.com/assets/images/jumei/cart_v2/empty_icon.jpg" alt="">
		<div class="cart_empty_right">
			<h2>您的购物车中没有商品，请先去挑选心爱的商品吧！</h2>
			<p class="cart_empty_backtip">
				 您可以 <a class="btn" href="${ctx }">  返回首页  </a> 挑选喜欢的商品,或者
			</p>
			<div class="search_block">
				<form action="${ctx }/search" method="get" pos="top">
					<input name="filter" value="" type="hidden">
					<button type="submit" class="btn_cart_search">搜索</button>
					<input name="search" class="search_input" placeholder="搜搜您想要的商品" autocomplete="off" type="text">
				</form>
			</div>
		</div>
	</div>
	<div class="content_footer clearfix">
	</div>
</div>
</c:if>
    
<div id="footer" class="footer">
	<div id="footer_textarea">
		<div class="footer_top">
			<div class="footer_con footer_credit" id="footer_credit">
				<a href="#" class="foot_link mostmall" target="_blank" rel="nofollow">
				<span class="corn"></span><span class="con"><b>最大美妆电商</b></span>四千万用户信赖
				</a>
				<a href="#" class="foot_link quality" target="_blank" rel="nofollow">
				<span class="corn"></span><span class="con"><b>成功在美上市</b></span>股票代码NYSE:JMEI
				</a>
				<a href="#" class="foot_link back" rel="nofollow" target="_blank">
				<span class="corn"></span><span class="con"><b>品牌防伪码</b></span>支持品牌官网验真
				</a>
				<a href="#" class="foot_link depot" target="_blank" rel="nofollow">
				<span class="corn"></span><span class="con"><b>30天无理由退货</b></span>只要不满意无理由退货
				</a>
				<a href="#" class="foot_link consignment" target="_blank" rel="nofollow">
				<span class="corn"></span><span class="con"><b>百万用户口碑</b></span>帮你只选对的,不选贵的
				</a>
				<a href="#" class="foot_link packaging" target="_blank" rel="nofollow">
				<span class="corn"></span><span class="con"><b>订单闪电发货</b></span>1.5小时订单急速出库
				</a>
				<a href="#" class="foot_link confide" target="_blank" rel="nofollow">
				<span class="corn"></span><span class="con"><b>大牌明星热荐</b></span>打造精致明星脸
				</a>
			</div>
		</div>
		<div class="footer_top_sen">
			<div class="footer_con footer_links" id="footer_links">
				<ul class="linksa">
					<li class="links">服务保障</li>
					<li><a href="#" target="_blank" rel="nofollow">品牌真品防伪码</a></li>
					<li><a href="#" target="_blank" rel="nofollow">100%正品保证</a></li>
					<li><a href="#" target="_blank" rel="nofollow">30天无条件退货</a></li>
					<li><a href="#" target="_blank" rel="nofollow">7×24小时客服服务</a></li>
					<li><span class="footer_zcemail">总裁邮箱ceo@jumei.com</span></li>
				</ul>
				<ul class="linksb">
					<li class="links">使用帮助</li>
					<li><a href="#" target="_blank" rel="nofollow">新手指南</a></li>
					<li><a href="#" target="_blank" rel="nofollow">常见问题</a></li>
					<li><a href="#" target="_blank" rel="nofollow">帮助中心</a></li>
					<li><a href="#" target="_blank" rel="nofollow">用户协议</a></li>
					<li><a href="#" target="_blank" rel="nofollow">企业用户团购</a></li>
				</ul>
				<ul class="linksc">
					<li class="links">支付方式</li>
					<li><a href="#" target="_blank" rel="nofollow">货到付款</a></li>
					<li><a href="#" target="_blank" rel="nofollow">在线支付</a></li>
					<li><a href="#" target="_blank" rel="nofollow">余额支付</a></li>
					<li><a href="#" target="_blank" rel="nofollow">现金券支付</a></li>
				</ul>
				<ul class="linksd">
					<li class="links">配送方式</li>
					<li><a href="#" target="_blank" rel="nofollow">包邮政策</a></li>
					<li><a href="#" target="_blank" rel="nofollow">配送说明</a></li>
					<li><a href="#" target="_blank" rel="nofollow">运费说明</a></li>
					<li><a href="#" target="_blank" rel="nofollow">验货签收</a></li>
				</ul>
				<ul class="linkse">
					<li class="links">售后服务</li>
					<li><a href="#" target="_blank" rel="nofollow">正品承诺</a></li>
					<li><a href="#" target="_blank" rel="nofollow">售后咨询</a></li>
					<li><a href="#" target="_blank" rel="nofollow">退货政策</a></li>
					<li><a href="#" target="_blank" rel="nofollow">退货办理</a></li>
				</ul>
				<div class="links_er_box">
					<ul class="linksf">
						<li class="links">手机我们</li>
						<li><span class="link_bottom_pic"></span></li>
						<li>下载移动客户端</li>
					</ul>
					<ul class="linksg">
						<li class="links">我们微信</li>
						<li><span class="link_bottom_pic"></span></li>
						<li>我们官方微信</li>
					</ul>
				</div>
			</div>
		</div>
		<div class="footer_dy" id="footer_dy">
			<div class="play_box">
				<span class="play"></span>
			</div>
		</div>
		<div class="footer_center">
			<div class="footer_con" id="footer_link">
				<a href="#" target="_blank" rel="nofollow">关于</a>
				<a href="#" target="_blank" rel="nofollow">INVESTOR RELATIONS</a>
				<a href="#" target="_blank" rel="nofollow">商家入驻</a>
				<a href="#" target="_blank" rel="nofollow">获取更新</a>
				<a href="#" target="_blank" rel="nofollow">加入我们</a>
				<a href="#" target="_blank" rel="nofollow">品牌合作专区</a>
				<a href="#" target="_blank" rel="nofollow">网站联盟</a>
				<a href="#" target="_blank" rel="nofollow">媒体报道</a>
				<a href="#" target="_blank" rel="nofollow">商务合作</a>
			</div>
		</div>
		<div id="footer_copyright" class="footer_copyright">
			<div class="footer_con">
				<p class="footer_copy_con">
                    COPYRIGHT © 2015 有限公司 保留一切权利。
                    客服热线：028-8888888
					<br>
                    公网安备 11010102011111 | 
					<a href="#" target="_blank" rel="nofollow">ICP证111111号</a>
                    | 
					<a href="#" target="_blank" rel="nofollow">营业执照</a>
				</p>
				<p>
					<a href="#" class="footer_copy_logo logo01" rel="nofollow"></a>
					<a href="#" target="_blank" class="footer_copy_logo logo02" rel="nofollow"></a>
					<a href="#" class="footer_copy_logo logo03" rel="nofollow"></a>
					<a href="#" class="footer_copy_logo logo04" rel="nofollow"></a>
					<a href="#" target="_blank" class="footer_copy_logo logo05" rel="nofollow"></a>
				</p>
				<script>function change_urlknet(eleId){var str= document.getElementById(eleId).href;var str1 =str.substring(0,(str.length-6));str1+=RndNum(6);document.getElementById(eleId).href=str1;}function RndNum(k){var rnd="";for (var i=0;i<k;i++){rnd+=Math.floor(Math.random()*10);}return rnd;}</script>
			</div>
		</div>
	</div>
</div>
<input type="hidden" id="ctx" name="ctx" value="${ctx }"/>
</body>
</html>