<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%response.setStatus(200);%>
<!doctype html> 
<html> 
  <head> 
    <meta charset="utf-8"> 
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="Cache-Control" content="no-store" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no" />
    <meta content="yes" name=" apple-mobile-web-app-capable" />
    <title>500 - 系统出错了 - 报喜了</title>
    <link rel="icon" type="image/png" href="${ctxAssets }/image/favicon.png">
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${ctxAssets }/stylesheets/index.css">
    <style type="text/css">
	.four { width:100%; background-color:#fff; font-size:16px; text-align:left;}
	.four_cont { width:717px; margin:0 auto; padding-bottom:40px;}
	.four_text { width:230px; margin:0 auto; line-height:24px;}
	.four_text a {width:717px; margin:0 auto;color:#F36;}
	</style>
  </head>
  <body>
  <c:if test="${indexTopAd != null }">${indexTopAd.html }</c:if>
  <div class="top-n container-n">
    <div>  
      <shiro:guest>                  
      <p class="welcome float-l">欢迎来到报喜了！</p>
      <a class="login float-l" href="${ctx }/login">请登录</a>
      <p class="line float-l">|</p>
      <a class="register float-l" href="${ctx }/regist">快速注册</a>
      </shiro:guest>
      <shiro:user>
      <p class="welcome float-l">欢迎你，<a href="${ctx }/i/order"><shiro:principal property="username"/></a></p>
      <p class="line float-l">|</p>
      <a class="logout float-l" href="${ctx }/logout">&nbsp;[退出]&nbsp;</a>
      </shiro:user>
      <p id='num' class="float-r">400-6883-520</p>
      <p class="hot-line float-r">服务热线：</p>
      <a href="${ctx }/i/order" class="dingdan float-r">订单查询</a>
      <a href="${ctx }/i/order" class="wodehunqing float-r">我的婚庆</a>
      <a href="${ctx }/i/favorite" class="wodeshoucang float-r">我的收藏</a>
      <img src="${ctxAssets }/image/xin.png" class="xin float-r">
      <div class="clear"></div>
    </div>
  </div>
  <div class="menu-n container-n">
    <div class="menu-top">
      <a href="${ctx }"><img src="${ctxAssets }/image/logo.png" class="logo"></a>
      <form id="queryForm" action="${ctx }/product/list" method="post">
	      <input class='search' name="k" autocomplete="off" placeholder="灯光..." value="${keyword}">
	      <img src="${ctxAssets }/image/search.png" class="search-img float-l">
      </form>
      <div class="shop-car">
        <img src="${ctxAssets }/image/cart.gif" class="float-l">
        <a href="${ctx }/cart"><p class="myshopcar">我的购物车</p></a>
        <img src="${ctxAssets }/image/down.png" class="down">
        <span class="shor-car-num">0</span>
        <div class="shop-car-main">
          <i class="cart-icons"></i>
          <div class="new-join">
            <p>最新加入的商品</p>
          </div>
          <div class="shop-car-pro-list">
          </div>
          
          <div class="go_to_pay">
            <div class="go_to_pay_btn"><a href="${ctx }/cart">去购物车结算</a></div>
            <div class="clear"></div>
          </div>
        </div>
      </div>
      <div class="hotwords">
      	<c:forEach items="${HOTQUERY_LIST }" var="h">
      		<a href="javascript:void(0);">${h.keyword }</a>
      	</c:forEach>
      </div>
      <div class="search-box" style="display: none;">
        <c:forEach items="${SEARCH_LIST }" var="h">
        <div class="search-list" data-key="${h.keyword }" data-url="${h.url }">
          <div>
            <p class="float-l">${h.keyword }</p>
            <p class="float-r">约${h.quantity }件</p>
            <div class="clear"></div>
          </div>
        </div>
        </c:forEach>
      </div>
      <div class="clear"></div>
    </div>
    <div class="menu-bottom">
      <div class="index-logo menu-bottom-style">
        <a href="${ctx }/">首页</a>
      </div>
      <div class="daojushop menu-bottom-style">
        <a href="${ctx }/mall">道具商城</a>
        <img src="${ctxAssets }/image/down.png">
        <div class="clear"></div>
      </div> 
      <div class="tuangou menu-bottom-style">
        <a href="${ctx }/booking">预约</a>
        <div class="clear"></div>
      </div>
      <%-- <div class="tuangou">
        <a href="${ctx }/groupon">团购</a>
        <div class="clear"></div>
      </div>
      <div class="jifenshop">
        <a href="${ctx }/jf">积分商城</a>
        <div class="clear"></div>
      </div> --%>
      
      <c:if test="${not empty PRODUCT_CATALOG_LIST }">
      <div class="daoju-menu">
        <div class="daoju-menu-container">
          <c:forEach items="${PRODUCT_CATALOG_LIST}" var="p" varStatus="i">
          <div class="menu-content-${i.index + 1 } float-l">
            <a href="${ctx }/product/list?catalogId=${p.id}"><p class="">${p.name }</p></a>
            <c:if test="${not empty p.children and empty p.children[0].children }">
            <div class="menu-content-${i.index + 1 }-list">
              <div class="float-l">
              <c:forEach items="${p.children }" var="c" varStatus="s">
              <a href="${ctx }/product/list?catalogId=${c.id}">${c.name }</a><br>
              </c:forEach>
              </div>
            </div>
            </c:if>
            <c:if test="${not empty p.children and not empty p.children[0].children }">
            <c:forEach items="${p.children }" var="c" varStatus="s">
            <c:if test="${s.index % 2 == 0 }"><div class="menu-content-${i.index + 1 }-list"></c:if>
              <div class="float-l">
              <c:forEach items="${c.children }" var="cc">
                <a href="${ctx }/product/list?catalogId=${cc.id}">${cc.name }</a><br>
              </c:forEach>
              </div>
              <c:if test="${s.index % 2 == 1 }"><div class="clear"></div></c:if>
            <c:if test="${s.index % 2 == 1 }"></div></c:if>
            </c:forEach>
            </c:if>
          </div>
          </c:forEach>
        </div>
      </div>
      </c:if>
      <div class="clear"></div>
    </div>
  </div>
  <div class="four">
	<div class="four_cont">
	  <img width="717" height="300" src="${ctxStatic }/images/500.jpg">
	  <div class="four_text">
		<p>系统出错了！</p>
		<a href="${ctx }">&gt;&gt;返回首页</a>
	  </div>
	</div>
  </div>
  <div class="bottom-icon container-n">
    <div class="container icon-container">
      <div class="row">
        <div class="col-xs-12">
          <img src="${ctxAssets }/image/bottom-img.png">
        </div>
      </div>
    </div>
  </div>
  <div class="footer container-n">
    <div class="container footer-container">
      <div class="row">
        <div class="col-xs-2">
          <p class="footer-p-top">购物指南</p>
          <p class="footer-p-bottom">购物流程</p>
          <p class="footer-p-bottom">会员介绍</p>
        </div>
        <div class="col-xs-2">
          <p class="footer-p-top">配送方式</p>
          <p class="footer-p-bottom footer-p-bottom1">上门自取</p>
          <p class="footer-p-bottom footer-p-bottom2">211限时达</p>
        </div>
        <div class="col-xs-2">
          <p class="footer-p-top">支付方式</p>
          <p class="footer-p-bottom">在线支付</p>
        </div>
        <div class="col-xs-2">
          <p class="footer-p-top">售后服务</p>
          <p class="footer-p-bottom">售后政策</p>
          <p class="footer-p-bottom">取消订单</p>
        </div>
        <%-- 
        <div class="col-xs-2">
          <p class="footer-p-top">手机我们</p>
          <img src="${ctxAssets }/image/code.png">
          <p class="footer-p-bottom3">下载移动客户端</p>
        </div>
         --%>
        <%-- <div class="col-xs-2">
          <p class="footer-p-top">订阅号</p>
          <img src="${ctxAssets }/image/code.png">
          <p class="footer-p-bottom4">关注微信公众号</p>
        </div>
        <div class="col-xs-2">
          <p class="footer-p-top">服务号</p>
          <img src="${ctxAssets }/image/code.png">
          <p class="footer-p-bottom4">关注微信公众号</p>
        </div> --%>
        <div class="col-xs-2">
          <p class="footer-p-top">服务号</p>
          <img src="${ctxAssets }/image/wf_code.jpg" style="width: 75px;height: 75px;">
          <p class="footer-p-bottom4">关注微信服务号</p>
        </div>
      </div>
    </div>
  </div>
  <div class="footer-bottom">
    <p>
      <a>关于我们</a>    |    
      <a>加入我们</a>    |    
      <a>联系我们</a>    |    
      <a>商务合作</a>
    </p>
    <p><div class="copyRight">Copyright 2015-2015 成都雷立风行电子商务有限公司  保留一切权利。蜀ICP备15020915号 客服热线：400-6883-520</div></p>
  </div>
  <input type="hidden" id="ctx" name="ctx" value="${ctx }">
  <input type="hidden" id="IMAGE_ROOT_PATH" name="IMAGE_ROOT_PATH" value="${IMAGE_ROOT_PATH }">
  <script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
  <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="${ctxAssetsJS }/javascripts/common.js"></script>
  <script type="text/javascript" src="${ctxAssetsJS }/javascripts/index-menu.js"></script>

  <input type="hidden" id="ctx" name="ctx" value="${ctx }">
  <%-- <input type="hidden" id="baseURL" name="baseURL" value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}"> --%>
  <input type="hidden" id="baseURL" name="baseURL" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}">
  </body>
</html>

