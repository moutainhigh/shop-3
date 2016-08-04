<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="decorator" content="default_new"/>
    <title>零利专场</title>
    <link rel="stylesheet" type="text/css" href="${ctxAssets }/stylesheets/daojushop.css"/>
  </head>
  <body>
    <div class="menu-slider container-n">
      <div class="nav-center width-n">
          <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
          	<ol class="carousel-indicators">
	            <c:forEach items="${mallImgList}" var="c" varStatus="i">
				    <c:if test="${i.index == 0}">
		      	   	   <li data-target="#carousel-example-generic" data-slide-to="${i.index}" class="active"></li>
		      	    </c:if>
		      	    <c:if test="${i.index > 0}">
		               <li data-target="#carousel-example-generic" data-slide-to="${i.index}"></li>
		      	    </c:if>
		   		</c:forEach>
	   		</ol>
            <div class="carousel-inner" role="listbox">
               <c:forEach items="${mallImgList}" var="c" varStatus="i">
	      	   	   <div class="item <c:if test="${i.index == 0}">active</c:if>">
		                <a target="_blank" href="${ctx }${c.link}"><img src="${IMAGE_ROOT_PATH }/${c.picture}"/></a>
		           </div>
		      </c:forEach>
            </div>
          </div>
        <div class="clear"></div>
      </div>  
    </div>
    <div class="firstF container-n">
    <!-- <div class="firstF-title">
      <span class="float-l f-1"></span>
      <p>官方推荐</p>
      <div class="clear"></div>
    </div> --> 
    <div class="container container-l">
      <div class="row">
      	<c:forEach items="${list}" var="c" varStatus="i">
	        <div class="col-xs-4 padding-n product">
	          <a target="_blank" class="product-list-img" href="${ctx }/product/${c.id}">
	          	<img title="${c.introduce}"src="${IMAGE_ROOT_PATH }/${c.picture}-px350"/>
	          </a>
	          <div class="deals_tags">
	          	 <span class="tags_list tags_hot_recommend"></span>
	          </div>
	          <div class="product-word">
	             <p id="product-p-1">
	            	<a title="${c.introduce}" href="${ctx}/product/${c.id}">
		            	${c.name}
		            </a>
	           	 </p>
	          </div>
	          <div class="activity-word">
	              <p>活动时间:<fmt:formatDate value="${c.activity.startDate }" pattern="yyyy年MM月dd日"/> - <fmt:formatDate value="${c.activity.endDate }" pattern="yyyy年MM月dd日"/></p>
	              <div class="clear"></div>
          	  </div> 
		      <div class="word-b">
	              <p><em>¥</em>${c.nowPrice }<%-- <del>¥${c.price }</del> --%></p>
	              <span class="baoyou">促销</span>
	              <span class="baoyou">包邮</span> 
	              <c:set var="t" value="<%=System.currentTimeMillis()%>"></c:set> 
	              <c:if test="${c.activity.startDate.time lt t}">
	              <a href="${ctx }/product/${c.id}" class="goto-btn">去看看</a>
	              </c:if>
	              <c:if test="${c.activity.startDate.time gt t}">
	              <a class="goto-btn disabled" disabled="disabled">未开始</a> 
	              </c:if>
	              <c:if test="${c.activity.endDate.time lt t}">
	              <a class="goto-btn disabled" disabled="disabled">已结束</a> 
	              </c:if>
	              <div class="clear"></div>
          	  </div> 
	        </div>
	    </c:forEach>
      </div>
    </div>
  </div>
  <content tag="local_script">
      <script type="text/javascript" src="${ctxAssetsJS }/javascripts/daojushop.js"></script>
   </content>
  </body>
</html>