<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
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
    <title><sitemesh:title/> - 报喜了</title>
    <link rel="icon" type="image/png" href="${ctxAssets }/image/favicon.png">
    <link href="${ctxStatic }/css/usercenter.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${ctxAssets }/stylesheets/index.css">
     <link href="${ctxAssets }/stylesheets/shopcar1.css" rel="stylesheet" type="text/css">
    <sitemesh:head/>
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
    	<div class="menu-bottom-main ">
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
  </div>
  <div class="profile" style="border-top: 1px solid #DBD6D0;">
    <div class="nav">
        <div class="notice">
        	<c:if test="${account.smallPicture != null and not empty account.smallPicture }">
			<a href="${ctx }/i/account/modifyPic" target="_blank" style="border-bottom: 0;padding:0;"><img src="${IMAGE_ROOT_PATH}/${account.smallPicture }" alt=""></a>
			</c:if>
			<c:if test="${account.smallPicture == null or empty account.smallPicture }">
			<a href="${ctx }/i/account/modifyPic" target="_blank" style="border-bottom: 0;padding:0;"><img src="${ctxStatic }/images/avatar_small.png" alt=""></a>
			</c:if>
            <p class="nickname" style="margin-bottom:0px;"><a style="padding-left:0;line-height: 1.8;color:#ed145b" href="#" target="_blank"><shiro:principal property="username"/></a></p>
            <p style="margin-bottom:0px;">
            	<a style="padding-left:0;line-height: normal;text-align: left;border-bottom: 0;float: left;margin-right: 4px;" href="#" >
            		<c:choose>
            			<c:when test="${account.rank == 'R1' or empty account.rank }">普通会员</c:when>
            			<c:when test="${account.rank == 'R2' }">黄金会员</c:when>
            			<c:when test="${account.rank == 'R3' }">白金会员</c:when>
            			<c:when test="${account.rank == 'R4' }">钻石会员</c:when>
            		</c:choose>
            	</a>
            	<c:choose>
           			<c:when test="${empty account.authStatus or account.authStatus == '0' or account.authStatus == '2'}">
           				<img src="${ctxAssets}/image/non_person_auth.png" style="float:left;margin-right: 4px;width: 20px; height: auto;">
           			</c:when>
           			<c:otherwise>
           				<img src="${ctxAssets}/image/person_auth.png" style="float:left;margin-right: 4px;width: 20px; height: auto;">
           			</c:otherwise>
            	</c:choose>
            	<c:choose>
           			<c:when test="${empty account.authStatus or account.authStatus == '0' or account.authStatus == '1'}">
           				<img src="${ctxAssets}/image/non_enter_auth.png" style="float:left;margin-right: 4px;width: 20px; height: auto;">
           			</c:when>
           			<c:otherwise>
           				<img src="${ctxAssets}/image/enter_auth.png" style="float:left;margin-right: 4px;width: 20px; height: auto;">
           			</c:otherwise>
            	</c:choose>
            	<c:if test="${account.isMaster=='y' }">
            		<img src="${ctxAssets}/image/master_logo.png" style="float:left;margin-right: 4px;width: 20px; height: auto;">
            	</c:if>
            </p>
            <%-- <p class="uid" style="line-height: 14px;margin-top:5px;">用户ID: <shiro:principal property="id"/></p> --%>
        </div>
        <h2><b></b>我的婚庆</h2>
        <a href="${ctx }/i/order" class="list"><b></b>我的订单</a>
        <a href="${ctx }/i/booking/list" class="booking"><b></b>我的预约单</a>
        <a href="${ctx }/i/favorite" class="fav"><b></b>我的收藏</a>
<!--         <a href="${ctx }/i/membership" class="member"><b></b>我的会员等级</a> -->
        <a href="${ctx }/i/invite" class="invite"><b></b>我的邀请码</a>
<!--         <a href="${ctx }/i/account/subscribe" class="subscribe"><b></b>订阅短信</a> -->
        <h2><b></b>管理个人账户</h2>
<!--         <a href="${ctx }/i/account/balance" class="balance"><b></b>我的余额</a> -->
        <a href="${ctx }/i/account/profile" class="settings"><b></b>设置账户信息</a>
        <a href="${ctx }/i/account/personAuth" class="personAuth"><b></b>个人实名认证</a>
        <a href="${ctx }/i/account/enterpriseAuth" class="enterAuth"><b></b>企业认证</a>
        <a href="${ctx }/i/account/password" class="password"><b></b>修改密码</a>
        <a href="${ctx }/i/account/mobile" class="bind"><b></b>绑定手机</a>
        <a href="${ctx }/i/account/addresses" class="addr"><b></b>管理收货地址</a>
    </div>
    <h1 id="title">${accountTitle }</h1>
    <div class="content"><sitemesh:body/></div>
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
  <sitemesh:getProperty property="page.local_script"/>
  <script type="text/javascript">
  $(function(){
  	var _self = window.location.href;
  	$('.nav a').each(function(){
		var _this = $(this);
  		if(_self.indexOf(_this.attr('href')) >= 0) {
  			_this.addClass('selected');
  		}
  	});
  })
  </script>
  </body>
</html>