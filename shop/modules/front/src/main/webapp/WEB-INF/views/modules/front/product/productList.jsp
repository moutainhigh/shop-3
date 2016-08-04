<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="decorator" content="default_new"/>
    <meta name="Keywords" content=""/>
    <meta name="Description" content=""/>
    <title>产品列表</title>
    <link rel="stylesheet" type="text/css" href="${ctxAssets}/stylesheets/allpro.css"></link>
    <link href="${ctxStatic }/css/pagination.css" rel="stylesheet" type="text/css"></link>
    <content tag="local_script">
	<script src="${ctxStaticJS }/js/jquery.lightbox.js" type="text/javascript"></script>
	<script src="${ctxStaticJS }/js/jquery.pagination.js" type="text/javascript"></script>
    <script type="text/javascript" src="${ctxAssetsJS }/javascripts/allpro.js"></script>
    </content>
</head>
  <body>
    <div class="nav-router container-n">
      <ol class="content-n margin-n">
        <li class=""><a href="${ctx }/mall">道具商城</a></li>
        <c:forEach items="${catalogPath}" var="c" varStatus="i">
        	<li class="<c:if test="${i.index == (fn:length(catalogPath)-1)}">nav-router-active</c:if>"><a href="${ctx }/product/list?catalogId=${c.id}">${c.name }</a></li>
        </c:forEach>
         <form id="searchForm" action="${ctx }/product/list" method="post">
          	<span class="search_gt_1">&gt;</span>
         	<input type="text" id="keyword" name="k" placeholder="在当前条件下搜索" class="bread_search_input" value="${keyword }"/>
          	<input type="submit" value="" class="bread_search_img"/>
         	<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize }"/>
			<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo }"/>
			<input type="hidden" id="total" name="total" value="${page.total }"/>
			<input type="hidden" id="catalogId" name="catalogId" value="${catalogId}"/>
			<input type="hidden" id="price" name="price" value="${price}"/>
			<input type="hidden" id="orderKey" name="orderKey" value="${orderKey}"/>
			<input type="hidden" id="orderValue" name="orderValue" value="${orderValue}"/>
			<input type="hidden" id="ctx" name="ctx" value="${ctx}"/>
			<input type="hidden" id="pagerSize" name="pagerSize" value="${page.pagerSize}"/>
        </form>
      </ol> 
    </div>
    <div class="nav-map container-n">
      <div class="nav-map-main content-n margin-n">
        <div class="nav-map-center">
          <div class="nav-map-1-name nav-map-name-div">
            <p>分类：</p>
          </div>
          <div class="buxian nav-map-list nav-map-list-active">
            <p><a href="javascript:void(0)" data-key="${catalogId}" data-type="1" class="">不限</a></p>
          </div>
          <div class="nav-map-list-right">
          	<c:forEach items="${catalogClass}" var="c">
	            <div class="nav-map-list">
	              <p><a href="javascript:void(0)" data-key="${c.id}" data-type="1" class="">${c.name}</a></p>
	            </div>
	        </c:forEach>
          </div>
          <div class="clear"></div>
        </div>
        <div class="nav-map-center">
          <div class="nav-map-1-name nav-map-name-div">
            <p>价格：</p>
          </div>
          <div class="buxian nav-map-list nav-map-list-active">
            <p><a href="javascript:void(0)" data-key="" data-type="2" class="">不限</a></p>
          </div>
          <div class="nav-map-list-right">
            <div class="nav-map-list">
              <p><a href="javascript:void(0)" data-key="0-199" data-type="2" class="">0-199</a></p>
            </div>
            <div class="nav-map-list">
              <p><a href="javascript:void(0)" data-key="199-299" data-type="2" class="">199-299</a></p>
            </div>
            <div class="nav-map-list">
              <p><a href="javascript:void(0)" data-key="299-399" data-type="2" class="">299-399</a></p>
            </div>
            <div class="nav-map-list">
              <p><a href="javascript:void(0)" data-key="399-499" data-type="2" class="">399-499</a></p>
            </div>
            <div class="nav-map-list">
              <p><a href="javascript:void(0)" data-key="499-599" data-type="2" class="">499-599</a></p>
            </div>
            <div class="nav-map-list">
              <p><a href="javascript:void(0)" data-key="599-699" data-type="2" class="">599-699</a></p>
            </div>
            <div class="nav-map-list">
              <p><a href="javascript:void(0)" data-key="699" data-type="2" class="">699以上</a></p>
            </div>
          </div>
          <div class="clear"></div>
        </div>
<!--         <c:forEach items="${attributeList}" var="c"> -->
<!--         	<div class="nav-map-center"> -->
<!-- 	          <div class="nav-map-1-name nav-map-name-div"> -->
<!-- 	            <p>${c.name }：</p> -->
<!-- 	          </div> -->
<!-- 	          <div class="buxian nav-map-list nav-map-list-active"> -->
<!-- 	            <p><a href="" class="">不限</a></p> -->
<!-- 	          </div> -->
<!-- 	          <div class="nav-map-list-right"> -->
<!-- 	          	<c:forEach items="${c.attrList}" var="c1"> -->
<!-- 		            <div class="nav-map-list"> -->
<!-- 		              <p><a href="#" data-key="${c1.id }" data-type="3" class="">${c1.name}</a></p> -->
<!-- 		            </div> -->
<!-- 		        </c:forEach> -->
<!-- 	          </div> -->
<!-- 	          <div class="clear"></div> -->
<!-- 	        </div> -->
<!-- 	    </c:forEach> -->
      </div>
    </div>
    <div class="all-pro container-n margin-n">
      <div class="all-pro-main content-n">
        <div class="all-pro-main-top">
          <div class="all-pro-main-name all-pro-mian-name-active">
            <a href="javascript:void(0);">默认</a>
          </div>
          <div class="all-pro-main-name">
            <a href="javascript:void(0);" class="sort-up" data-key="2">销量</a>
          </div>
          <div class="all-pro-main-name">
            <a href="javascript:void(0);" class="sort-up" data-key="3">价格</a>
          </div>
          <div class="all-pro-main-name">
            <a href="javascript:void(0);" class="sort-up" data-key="4">人气</a>
          </div>
          <div class="all-pro-main-name">
            <a href="javascript:void(0);" data-key="1">最新上架</a>
          </div>
          <div class="all-pro-main-name">
            <a href="javascript:void(0);" data-key="5">只看待卖</a>
          </div>
          <div class="">
            <div class="top-page-slider">
              <div class="top-page-right <c:if test="${page.pageNo==(page.pagerSize-1)}">disabled</c:if>">
                <a  href="javascript:void(0);">&gt;</a>
              </div>
              <div class="top-page-left <c:if test="${page.pageNo==0}">disabled</c:if>">
                <a  href="javascript:void(0);">&lt;</a>
              </div>
            </div>
            <div class="page-num">
              <p class="page-num-total">${page.pagerSize }</p>
              <p>/</p>
              <p class="page-num-now">
              	 <c:if test="${not empty page.list}">${page.pageNo + 1}</c:if>
              	 <c:if test="${empty page.list}">0</c:if>
              </p>
              <div class="clear"></div>
            </div>
            <div class="pro-total">
              <p>个商品</p>
              <p class="pro-total-num">${page.total }</p>
              <p>共</p>
              <div class="clear"></div>
            </div>
            <div class="clear"></div>
          </div>
          <div class="clear"></div>
        </div>
        <div class="all-pro-list container">
          <div class="row">
          	<c:if test="${not empty page.list}">
	            <c:forEach items="${page.list}" var="c">
		            <div class="col-xs-3 padding-n">
		              <a target="_blank" href="${ctx }/product/${c.id}?catalogId=${catalogId}"><img src="${IMAGE_ROOT_PATH}/${c.picture}-px350" class="product-pic" style="height: 350px;"/></a>
		              <div class="product-word">
			             <p id="product-p-1">
			            	<a href="${ctx}/product/${c.id}" title="${c.introduce}">
				            	${c.name}
				            </a>
			           	 </p>
			          </div>
				      <div class="word-b">
			              <p><em>¥</em>${c.nowPrice }<del>¥${c.price }</del></p>
			              <div class="num_box">
							 销量：
							 <span class="buy_num">${c.sellcount }</span>
						  </div>
			              <div class="clear"></div>
		          	  </div> 
		            </div>
	            </c:forEach>
            	<div class="col-xs-12 padding-n" id="pagination"></div>
            </c:if>
            <c:if test="${empty page.list}">
            	<div class="notice-filter-noresult">
					<div class="nf-n-wrap clearfix">
						<span class="nf-icon"></span>
						<div class="nf-content">
							<span class="result">抱歉，没有找到相关的商品喔!</span>
						</div>
					</div>
				</div>
            </c:if>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>