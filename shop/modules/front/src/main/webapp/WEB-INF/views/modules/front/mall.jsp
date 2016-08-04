<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="decorator" content="default_new"/>
    <title>道具商城</title>
    <link rel="stylesheet" type="text/css" href="${ctxAssets }/stylesheets/daojushop.css"/>
  </head>
  <body>
    <div class="menu-slider container-n">
      <div class="nav-center width-n">
        <div class="nav-left">
          <div class="nav-left-title nav-left-padding">
            <p>全部商品分类</p>
          </div>
          <c:forEach items="${PRODUCT_CATALOG_LIST}" var="c" varStatus="i">
	          <div class="nav-left-list nav-left-padding" id="nav-left-list-${i.index+1 }">
	            <a href="${ctx }/product/list?catalogId=${c.id}"><p>${c.name }</p></a>
	            <c:forEach items="${c.recommend}" var="r">
					<a target="_blank" href="${ctx}/product/list?catalogId=${r.id}" class="nav-left-list-a">${r.name}</a>
				</c:forEach>
	          </div>
	      </c:forEach>
	      <c:forEach items="${PRODUCT_CATALOG_LIST}" var="c" varStatus="i">
	          <div class="nav-left-next nav-left-next-${i.index+1 }">
	            <div class="nav-left-next-list">
	               <div class="list-pr-name-title">
	                  <p>${c.name }</p>
	               </div>
	               <c:if test="${not empty c.children and not empty c.children[0].children }">
		               <c:forEach items="${c.children}" var="d">
			              <div class="list-pr" style="border-top: none;">
			              		<div class="list-pr-name">
				                    <div><a href="${ctx }/product/list?catalogId=${d.id}">${d.name }</a></div>
				                </div>
				                <div class="list-pr-pr">
			                  		<div>
			                  			<c:forEach items="${d.children}" var="e">
						                    <div class="list-pr-btn">
						                      <a href="${ctx }/product/list?catalogId=${e.id}">${e.name }</a>
						                    </div>
					                     </c:forEach>
					                     <div class="clear"></div>
					                </div>
				                </div>
			              </div>
			           </c:forEach>
			     </c:if>
			     <c:if test="${not empty c.children and empty c.children[0].children }">
			     	<div class="list-pr" style="border-top: none;">
		                <div class="list-pr-pr">
	                  		<div>
	                  			<c:forEach items="${c.children}" var="d">
				                    <div class="list-pr-btn">
				                      <a href="${ctx }/product/list?catalogId=${d.id}">${d.name }</a>
				                    </div>
			                     </c:forEach>
			                     <div class="clear"></div>
			                </div>
		                </div>
		            </div>
			     </c:if>
                <div class="clear"></div>
              </div>
            </div>
            <div class="nav-left-next-img"></div>
        	</c:forEach>   
        </div>	
		                
        <div class="slider-right">
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
        </div>
        <div class="clear"></div>
      </div>  
    </div>
    <div class="tuijian container-n">
      <div class="tuijian-main width-n">
        <div class="tuijian-left">
          <img src="${ctxAssets }/image/tuijian-left.png"/>
        </div>
        <div class="tuijian-right">
          <div class="tuijian-right-main">
          	<div class="slider-<fmt:formatNumber type="number" value="${(fn:length(todayImgList)-fn:length(todayImgList)%4)/4 + 1}"/>">
	       		<c:choose>
	       			<c:when test="${(fn:length(todayImgList))%4 == 0}">
        				<c:forEach begin="${fn:length(todayImgList)-5}" end="${fn:length(todayImgList)}" step="1" varStatus="j">
        					<div class="slide-pic">
				                 <a target="_blank" href="${todayImgList[j.index].link}"><img src="${IMAGE_ROOT_PATH}/${todayImgList[j.index].picture}"/></a>
				            </div>
        				</c:forEach>
	       			</c:when>
	       			<c:otherwise>
	       				<c:forEach begin="${fn:length(todayImgList)-fn:length(todayImgList)%4}" end="${fn:length(todayImgList)-1}" step="1" varStatus="j">
        					<div class="slide-pic">
				                <a target="_blank" href="${todayImgList[j.index].link}"><img src="${IMAGE_ROOT_PATH}/${todayImgList[j.index].picture}"/></a>
				            </div>
        				</c:forEach>
	       			</c:otherwise>
	       		</c:choose>
	       	</div>
           	<c:forEach begin="0" end="${fn:length(todayImgList)/4}" var="c" varStatus="i">
           		<div class="slider-${i.index+1 }">
           			<c:forEach begin="${i.index*4}" end="${i.index*4 + 3}" step="1" varStatus="j">
           				<c:if test="${j.index < fn:length(todayImgList)}">
           					<div class="slide-pic">
				                 <a target="_blank" href="${todayImgList[j.index].link}"><img src="${IMAGE_ROOT_PATH}/${todayImgList[j.index].picture}"/></a>
				            </div>
           				</c:if>
           			</c:forEach>
           		</div>
           	</c:forEach>
            <div class="slider-1">
              <c:forEach items="${todayImgList}" begin="0" end="3" var="c" varStatus="i">
	              <div class="slide-pic">
	                <a target="_blank" href="${c.link}"> <img src="${IMAGE_ROOT_PATH}/${c.picture}"/></a>
	              </div>
	           </c:forEach>
            </div>
          </div>
          <div class="swich-left">
            <a href="javascript:void(0)"><img src="${ctxAssets}/image/left.png"/></a>
          </div>
          <div class="swich-right">
            <a href="javascript:void(0)"><img src="${ctxAssets}/image/right.png"/></a>
          </div>
          <input type="hidden" id="todayImgSize" value="<fmt:formatNumber type="number" value="${(fn:length(todayImgList)-fn:length(todayImgList)%4)/4 + 1}"/>"/>
        </div>
        <div class="clear"></div>
      </div>
    </div>
    <div class="firstF container-n">
    <div class="firstF-title">
      <span class="float-l f-1"></span>
      <p>最新上架</p>
      <div class="clear"></div>
    </div>
    <div class="container container-l">
      <div class="row">
      	<c:forEach items="${newProduct}" var="c" varStatus="i">
	        <div class="col-xs-4 padding-n product">
	          <a target="_blank" title="${c.introduce}" href="${ctx }/product/${c.id}">
	         	 <img src="${IMAGE_ROOT_PATH }/${c.picture}-px350"/>
	          </a>
	          <div class="deals_tags">
	          	 <span class="tags_list tags_new"></span>
	          </div>
	           <div class="product-word">
	            <p id="product-p-1">
	            	<a title="${c.introduce}" href="${ctx}/product/${c.id}">
		            	<span class="pink"><fmt:formatNumber value="${c.nowPrice*10/c.price}" pattern="##.#" minFractionDigits="1" ></fmt:formatNumber>折</span>
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
	              <a href="${ctx }/product/${c.id}" class="goto-btn">
	            	    去看看
	              </a>
	          </div> 
	        </div>
	    </c:forEach>
      </div>
    </div>
  </div>
    <div class="firstF container-n">
    <div class="firstF-title">
      <span class="float-l f-2"></span>
      <p>官方推荐</p>
      <div class="clear"></div>
    </div>
    <div class="container container-l">
      <div class="row">
      	<c:forEach items="${hotRecommendProduct}" var="c" varStatus="i">
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
		      <div class="word-b">
	              <p><em>¥</em>${c.nowPrice }<del>¥${c.price }</del></p>
<!-- 	              <span class="baoyou">包邮</span> -->
	              <a href="${ctx }/product/${c.id}" class="goto-btn">
	            	    去看看
	              </a>
	              <div class="clear"></div>
          	  </div> 
	        </div>
	    </c:forEach>
      </div>
    </div>
  </div>
  <div class="secondF container-n">
    <div class="secondF-title">
      <span class="float-l f-3"></span>
      <p>热卖榜</p>
      <div class="clear"></div>
    </div>
    <div class="container container-l">
      <div class="row">
      	<c:forEach items="${hotProduct}" var="c" varStatus="i">
	        <div class="col-xs-4 padding-n product">
	          <a target="_blank" title="${c.introduce}" href="${ctx }/product/${c.id}">
	          	<img src="${IMAGE_ROOT_PATH }/${c.picture}-px350"/>
	          </a>
	          <div class="deals_tags">
	          	 <span class="tags_list tags_hot"></span>
	          </div>
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
	              <a href="${ctx }/product/${c.id}" class="goto-btn">
	            	    去看看
	              </a>
	              <div class="clear"></div>
          	  </div> 
	      </div>
	    </c:forEach>
      </div>
    </div>
  </div>
  <div class="secondF container-n">
    <div class="firstF-title">
      <span class="float-l f-4"></span>
      <p>特价区</p>
      <div class="clear"></div>
    </div>
    <div class="container container-l">
      <div class="row">
      	<c:forEach items="${saleProduct}" var="c" varStatus="i">
	        <div class="col-xs-4 padding-n product">
	          <a target="_blank" title="${c.introduce}" href="${ctx }/product/${c.id}">
	          	<img src="${IMAGE_ROOT_PATH }/${c.picture}-px350"/>
	          </a>
	          <div class="deals_tags">
	          	 <span class="tags_list tags_sale"></span>
	          </div>
	          <div class="product-word">
	            <p id="product-p-1">
	            	<a title="${c.introduce}" href="${ctx}/product/${c.id}">
		            	<span class="pink"><fmt:formatNumber value="${c.nowPrice*10/c.price}" pattern="##.#" minFractionDigits="1" ></fmt:formatNumber>折</span>
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
	              <a href="${ctx }/product/${c.id}" class="goto-btn">
	            	    去看看
	              </a>
	           
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