<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/mobile/include/taglib.jsp"%>
<html>
<head>
	<title>首页</title>
	<!-- <meta name="decorator" content="default_mb"/> -->
	<%@include file="/WEB-INF/views/mobile/include/head.jsp" %>
	 <link rel="stylesheet" type="text/css" href="${ctxMS }/css/phone-cart.css">
  </head>
  <body>
    <div class="am-g">
        <div class="am-u-sm-12 header">
            <div class="header-left am-fl am-vertical-align">
                <p class="am-vertical-align-middle">成都</p>
                <img src="${ctxMS }/imgs/down.png" class="am-vertical-align-middle">
            </div>
            <p class="title">购物车</p>
            <div class="header-right am-fr am-vertical-align">
                <p class="am-vertical-align-middle header-edit">编辑</p>
            </div>
        </div>
        <div class="am-u-sm-12 footer">
            <label class="am-checkbox">
              <input type="checkbox" name="checkAll" checked="checked" value="" data-am-ucheck>
            	  全选
            </label>
            <div class="price-total am-cf">
                <p class="am-fl">合计：</p>
                <span class="am-fl">${totalPrice }</span>
            </div>
            <div class="go-to-settlement">
           	     去结算(<span id="checkedNum">${total }</span>)
            </div>
            <div class="delete-pro">
             	删除
            </div>
        </div>
        <c:forEach items="${carts }" var="item">
	        <div class="am-u-sm-12 merchant">
	            <div class="am-u-sm-12 merchant-header">
	                <label class="am-checkbox">
	                  <input type="checkbox" name="checkSeller"  checked="checked" data-am-ucheck>
	                </label>
	                <p class="merchant-title">${item.value[0].sellerName}</p>
<!-- 	                <div class="merchant-img am-vertical-align"> -->
<!-- 	                    <img src="" class="am-vertical-align-middle"> -->
<!-- 	                </div> -->
	            </div>
	            <c:forEach items="${item.value }" var="cart">
		            <div class="am-u-sm-12 merchant-pro-list">
		                <label class="am-checkbox">
		                  <input type="checkbox" name="checkCart" checked="checked" value="${cart.id }" data-am-ucheck>
		                </label>
		                <div class="merchant-pro-info">
		                    <img src="${IMAGE_ROOT_PATH }${cart.productImage}">
		                </div>
		                <div class="merchant-pro-info-r">
		                    <div class="merchant-pro-title">
		                        <p>${cart.productName }</p>
		                    </div>
		                    <div class="num-price am-cf">
		                        <div class="merchant-pro-num am-fl">
		                          <a class="btn-del" data-product-id="${cart.productId }" data-spec-id="${cart.specId }">-</a>
		                          <input type="text" class="fm-txt number" value="${cart.quantity }">
		                          <a class="btn-add" data-product-id="${cart.productId }" data-spec-id="${cart.specId }">+</a>
		                        </div>
		                        <div class="unit-price">
		                            <p>￥${cart.nowPrice }</p>
		                        </div>
		                    </div>
		                </div>
		                <p class="merchant-pro-price-total">小计:<span><fmt:formatNumber value="${cart.quantity*cart.nowPrice }" pattern="##.##" minFractionDigits="2" ></fmt:formatNumber></span></p>
		            </div>
		        </c:forEach>
	        </div>
	    </c:forEach>
    </div>
    <div class="am-u-sm-12 cart-null">
        <div class="am-u-sm-12">
            <img src="${ctxMS }/imgs/cart-null.png">
        </div>
        <div class="am-u-sm-6">
            <a href=""><div class="cart-null-btn">逛逛热卖</div></a>
        </div>
        <div class="am-u-sm-6">
            <a href=""><div class="cart-null-btn">看看收藏</div></a>
        </div>
    </div>
    <input type="hidden" id="ctx" name="ctx" value="${ctx }"/>
    <script type="text/javascript" src="${ctxMS }/js/phone-cart.js"></script>
  </body>
</html>