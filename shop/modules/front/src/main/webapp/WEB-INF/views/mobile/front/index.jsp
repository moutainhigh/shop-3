<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/mobile/include/taglib.jsp"%>
<html>
<head>
	<title>首页</title>
	<!-- <meta name="decorator" content="default_mb"/> -->
	<%@include file="/WEB-INF/views/mobile/include/head.jsp" %>
	<link rel="stylesheet" type="text/css" href="${ctxMS }/css/phone-index.css">
	<script type="text/javascript"> 
		$(document).ready(function() {
		});
	</script>
</head>
  <body>
    <div class="am-g">
        <div class="am-u-sm-12 header">
            <div class="header-left am-fl am-vertical-align">
                <p class="am-vertical-align-middle">成都</p>
                <img src="${ctxMS}/imgs/down.png" class="am-vertical-align-middle">
            </div>
            <p class="title">报喜了</p>
            <div class="header-right am-fr am-vertical-align">
                <img src="${ctxMS}/imgs/search.png" class="am-vertical-align-middle">
            </div>
        </div>
        <div class="am-u-sm-12 slider">
            <div data-am-widget="slider" class="am-slider am-slider-a1" data-am-slider='{"directionNav":false}' >
              <ul class="am-slides">
              	  <c:forEach items="${indexImgList }" var="c">
	                  <li>
	                   	<img src="${IMAGE_ROOT_PATH }${c.picture}">
	                  </li>
                  </c:forEach>
              </ul>
            </div>
        </div>
        <div class="am-u-sm-12 functions">
            <div class="am-u-sm-6 appointment">
                <div class="appointment-img">
                    
                </div>
                <!-- <img src="imgs/appointment.png"> -->
                <p>在线预约</p>
            </div>
            <div class="am-u-sm-6 wholesale">
                <div class="wholesale-img">
                    
                </div>
                <p>批量采购</p>
            </div>
        </div>
        <div class="am-u-sm-12 activities">
            <div class="am-u-sm-6 activities-left">
                <!-- <img src="imgs/activities1.png"> -->
            </div>
            <div class="am-u-sm-6 activities-right">
                <div class="am-u-sm-12 activities-right-top">
                </div>
                <div class="am-u-sm-12 activities-right-bottom">
                </div>
            </div>
        </div>
        <div class="am-u-sm-12 recommend">
            <p class="recommend-title">热门推荐</p>
            <c:forEach items="${hotRecommendProduct }" var="c">
	            <div  class="am-u-sm-6">
	                <div class="product-block">
	                	<a href="${ctx }/wx/product/detail/${c.id }" >
	                    	<img src="${IMAGE_ROOT_PATH }${c.picture}"/>
	                    </a>
	                    <a href="${ctx }/wx/product/detail/${c.id }" >
		                    <div class="product-word am-text-break">
	                        	<p>${c.name }</p>
		                    </div>
	                    </a>
	                    <div class="price-detail am-cf">
	                        <div class="price">
	                            <p>${c.nowPrice }</p>
	                        </div>
	                        <div class="go-to-detail">
	                        	<a href="${ctx }/wx/product/detail/${c.id }" >
	                           	 <p>去看看</p>
	                            </a>
	                        </div>
	                    </div>
	                </div>      
	            </div>
            </c:forEach>
        </div>      
    </div>
    <div class="am-g menu-fixed">
        <div class="am-u-sm-3 active">
            <div class="icon home"></div>
            <p>首页</p>
        </div>
        <div class="am-u-sm-3" id="classification">
            <div class="icon classification"></div>
            <p>分类</p>
        </div>
        <div class="am-u-sm-3" id="cart">
            <div class="icon cart"></div>
            <p>购物车</p>
        </div>
        <div class="am-u-sm-3" id="cust-center">
            <div class="icon user"></div>
            <p>我的</p>
        </div>
    </div>
     <input type="hidden" id="ctx" name="ctx" value="${ctx }"/>
    <script type="text/javascript" src="${ctxMSJS }/js/index.js"></script>
  </body>
</html>