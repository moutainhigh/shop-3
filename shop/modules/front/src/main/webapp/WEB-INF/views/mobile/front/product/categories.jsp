<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/mobile/include/taglib.jsp"%>
<html>
<head>
	<title>首页</title>
	<!-- <meta name="decorator" content="default_mb"/> -->
	<%@include file="/WEB-INF/views/mobile/include/head.jsp" %>
	 <link rel="stylesheet" type="text/css" href="${ctxMS }/css/phone-classification.css">
  </head>
  <body>
    <div class="am-g">
        <div class="am-u-sm-12 header">
            <p class="title">分类</p>
        </div>
        <div class="left-nav">
        	<c:forEach items="${categories }" var="c" varStatus="i">
        		 <a href="javascript:void(0);" data-pk="${c.id }">
	                <div class="nav-list <c:if test="${i.index == 0 }">nav-list-active</c:if>">
	                   	 ${c.name }
	                </div>
	            </a>
        	</c:forEach>
        </div>
        <div class="am-u-sm-12 classification-main">
        	<c:if test="${not empty categories[0].children and empty categories[0].children[0].children }">
        		<c:forEach items="${categories[0].children }" var="c">
	        		<div class="am-u-sm-12 classification-block">
		                <div class="am-u-sm-4">
		                    <a href=""><img src="${IMAGE_ROOT_PAHT }${c.icon}"></a>
		                    <p>${c.name }</p>
		                </div>
		            </div>
	        	</c:forEach>
        	</c:if>
        	<c:if test="${not empty categories[0].children and not empty categories[0].children[0].children }">
        		<c:forEach items="${categories[0].children }" var="c">
	        		<div class="am-u-sm-12 classification-block">
	        			<div class="am-u-sm-12 classification-block-title">
		                    <p>${c.name}</p>
		                </div>
		                <c:forEach items="${c.children }" var="cc">
			                <div class="am-u-sm-4">
			                    <a href=""><img src="${IMAGE_ROOT_PAHT }${cc.icon}"></a>
			                    <p>${cc.name }</p>
			                </div>
		                </c:forEach>
		            </div>
	        	</c:forEach>
        	</c:if>
        </div>
    </div>
    <div class="am-g menu-fixed">
        <div class="am-u-sm-3">
            <div class="icon home"></div>
            <p>首页</p>
        </div>
        <div class="am-u-sm-3 active">
            <div class="icon classification "></div>
            <p>分类</p>
        </div>
        <div class="am-u-sm-3">
            <div class="icon cart"></div>
            <p>购物车</p>
        </div>
        <div class="am-u-sm-3">
            <div class="icon user"></div>
            <p>我的</p>
        </div>
    </div>
    <input type="hidden" id="ctx" name="ctx" value="${ctx }"/>
    <input type="hidden" id="IMAGE_ROOT_PAHT" name="IMAGE_ROOT_PAHT" value="${IMAGE_ROOT_PAHT }"/>
    <script type="text/javascript" src="${ctxMS }/js/phone-classification.js"></script>
  </body>
</html>