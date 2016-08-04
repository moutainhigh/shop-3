<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html> 
<html>
<head>
  <meta name="decorator" content="default_animate"/>
  <meta name="Keywords" content=""/>
  <meta name="Description" content=""/>
  <title>首页</title>
   <link href="${ctxAssets }/stylesheets/index-new.css" rel="stylesheet" type="text/css">
</head>
<body>
  <div class="bananer container-n">
    <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
      <ol class="carousel-indicators">
      	<c:forEach items="${indexImgList}" varStatus="i">
      	   <c:if test="${i.index == 0}">
      	   	   <li data-target="#carousel-example-generic" data-slide-to="${i.index}" class="active"></li>
      	   </c:if>
      	   <c:if test="${i.index > 0}">
               <li data-target="#carousel-example-generic" data-slide-to="${i.index}"></li>
      	   </c:if>
	    </c:forEach>
      </ol>
      <div class="carousel-inner" role="listbox">
      	<c:forEach items="${indexImgList}" var="c" varStatus="i">
		   <c:if test="${i.index == 0}">
      	   	   <div class="item active img1" style="background-image:url(${IMAGE_ROOT_PATH}/${c.picture})">
           	   </div>
      	   </c:if>
      	   <c:if test="${i.index > 0}">
               <div class="item img1" style="background-image:url(${IMAGE_ROOT_PATH}/${c.picture})">
        	   </div>
      	   </c:if>
	    </c:forEach>
      </div>
    </div>
  </div>
  <!-- <div class="xianshiqianggou container-n">
    <div class="xianshi-container">
      <div class="xianshi-top">
        <p class="xianshi-p-1">限时抢购</p>
        <P class="xianshi-p-2">6月12日晚</P>
        <p class="xianshi-p-3">10:00</p>
        <p class="xianshi-p-4">开抢</p>
        <div class="clear"></div>
      </div>
      <div class="xianshi-bottom">
        <a href="">
          <img src="image/qianggou.png">
        </a>
      </div>
    </div>
  </div> -->
  <c:forEach items="${preProduct}" var="c" varStatus="i">
	  <div class="qianggouyugao container-n">
	    <div class="yugao-container">
	      <div class="yugao-top">
	        <p class="yugao-p-1">团购预告</p>
	        <p class="yugao-p-2">距离团购开始</p>
	        <p class="yugao-p-3 showTime"  data-settingTime="${c.activity.startDate}" data-type="pre"></p>
	        <div class="clear"></div>
	      </div>
	      <div class="yugao-bottom">
	      	<a target="_blank" class="yugao-img" href="${ctx }/product/${c.id}">
	       	   <img src="${IMAGE_ROOT_PATH}/${c.picture}" class='yugao-left'/>
	       	</a>
	        <div class="yugao-right">
<!-- 	          <div class="pinpai"> -->
<!-- 	            <div class="float-l"> -->
<!-- 	              <P class="pinpai-p">Korea</P> -->
<!-- 	              <p class="pinpai-p">韩国品牌</p> -->
<!-- 	            </div> -->
<!-- 	            <div class="clear"></div> -->
	            <!-- <p></p> -->
<!-- 	          </div> -->
	          <p class="product-name">${c.name}</p>
	          
	          <p class="product-right-word"><em class="discount-info"><fmt:formatNumber value="${c.nowPrice*10/c.price}" pattern="##.#" minFractionDigits="1" ></fmt:formatNumber>折/</em>${c.introduce }</p>
	          <div class="pr-price float-l">
	            <em>¥</em>
	            <p>${c.nowPrice }</p>
	            <del>¥${c.price }</del>
	            <span class="baoyou">包邮</span>
	            <div class="clear"></div>
	          </div>
	          <a href="${ctx}/product/${c.id}" class="goto-btn">
	                           去看看
	          </a>
	        </div>
	        <div class="clear"></div>
	      </div>
	    </div>
	  </div>
  </c:forEach>
  <c:forEach items="${groupIngProduct}" var="c" varStatus="i">
	  <div class="qianggouyugao container-n">
	    <div class="yugao-container">
	      <div class="yugao-top">
	        <p class="yugao-p-1">团购进行中</p>
	        <p class="yugao-p-2">距离团购结束</p>
	        <p class="yugao-p-3 showTime" data-settingTime="${c.activity.endDate}" data-type="ing"></p>
	        <div class="clear"></div>
	      </div>
	      <div class="yugao-bottom">
	      	<a target="_blank" class="yugao-img" href="${ctx }/product/${c.id}">
	            <img src="${IMAGE_ROOT_PATH}/${c.picture}" class='yugao-left'/>
	        </a>
	        <div class="yugao-right">
	          <p class="product-name">${c.name}</p>
	          <p class="product-right-word"><em class="discount-info"><fmt:formatNumber value="${c.nowPrice*10/c.price}" pattern="##.#" minFractionDigits="1" ></fmt:formatNumber>折/</em>${c.introduce }</p>
	          <div class="pr-price float-l">
	            <em>¥</em>
	            <p>${c.nowPrice }</p>
	            <del>¥${c.price }</del>
	            <span class="baoyou">包邮</span>
	            <div class="clear"></div>
	          </div>
	          <a href="${ctx}/product/${c.id}" class="goto-btn">
	                         去看看
	          </a>
	        </div>
	        <div class="clear"></div>
	      </div>
	    </div>
	  </div>
  </c:forEach>
  <div class="new-index">
        <div class="tabs first" data-example-id="togglable-tabs">
          <ul id="myTabs" class="nav nav-tabs" role="tablist"> 
          	<c:forEach items="${catalog1}" var="c" varStatus="i">
	            <li role="presentation" >
	              <a href="${ctx}/product/list?catalogId=${c.id}" role="tab" id="profile-tab" data-toggle="tab" aria-controls="profile">${c.name}</a>
	            </li>
	        </c:forEach>
            <img src="${ctxAssets }/image/index1.png" class="floor">
            <p class="floor-title">仪式区</p>
          </ul>
          <div id="myTabContent" class="tab-content">
            <div role="tabpanel" class="tab-pane fade in active" id="home" aria-labelledby="home-tab">
              <div class="left-menu">
                <img src="${ctxAssets }/image/left1.jpg">
                <div class="menu-select">
	              <c:forEach items="${catalog1}" var="c" varStatus="i">
		            <div class="select-list">
                    	<a href="${ctx}/product/list?catalogId=${c.id}">${c.name}</a>
                 	 </div>
		       	  </c:forEach>
                </div>
              </div>
              <div class="right-pro">
                <div id="carousel-example-generic-1" class="carousel slide" data-ride="carousel">
                  <ol class="carousel-indicators">
                  	<c:set value="0" var="index" />  
                  	<c:forEach items="${indexFloor1 }" var="floor">
                  		<c:if test="${floor.banner=='y'}">
                  			<c:if test="${index==0}">
                  				<li data-target="#carousel-example-generic-1" data-slide-to="0" class="active"></li>
	                  		</c:if>
	                  		<c:if test="${index!=0}">
	                  			 <li data-target="#carousel-example-generic-1" data-slide-to="${index }" class=""></li>
	                  		</c:if> 
	                  		<c:set value="${index+1}" var="index" />  
	                  	</c:if>
                  	</c:forEach>
                  </ol>
                  <div class="carousel-inner" role="listbox">
                  	<c:set value="0" var="index" />  
                  	<c:forEach items="${indexFloor1 }" var="floor">
                  		<c:if test="${floor.banner=='y'}">
                  			<c:if test="${index==0}">
                  				<div class="item active">
                  				  <a target="_blank" href="${ctx }${floor.link}" title="${floor.title }">
			                      	<img src="${IMAGE_ROOT_PATH }/${floor.picture}" data-holder-rendered="true">
			                      </a>
			                    </div>
	                  		</c:if>
	                  		<c:if test="${index!=0}">
	                  			 <div class="item">
	                  			   <a target="_blank" href="${ctx }${floor.link}" title="${floor.title }">
			                      	<img src="${IMAGE_ROOT_PATH }/${floor.picture}" data-holder-rendered="true">
			                       </a>
			                    </div>
	                  		</c:if> 
	                  		<c:set value="${index+1}" var="index" />  
	                  	</c:if>
                  	</c:forEach>
                  </div>
                </div>
                <c:set value="0" var="index" />  
                <c:forEach items="${indexFloor1 }" var="floor">
                	<c:if test="${floor.banner!='y' and index<8}">
	                	 <div class="sm-box">
	                	 	<a target="_blank" href="${ctx }${floor.link}" title="${floor.title }">
		                  		<img src="${IMAGE_ROOT_PATH }/${floor.picture}">
		                  	</a>
		                  	<c:set value="${index+1 }" var="index" /> 
		                 </div>
	                 </c:if>
                </c:forEach>
              </div>
            </div>
          </div>
        </div>
        <div class="tabs second" data-example-id="togglable-tabs">
          <ul id="myTabs" class="nav nav-tabs" role="tablist"> 
            <c:forEach items="${catalog2}" var="c" varStatus="i">
	            <li role="presentation" >
	              <a href="${ctx}/product/list?catalogId=${c.id}" role="tab" id="profile-tab" data-toggle="tab" aria-controls="profile">${c.name}</a>
	            </li>
	        </c:forEach>
            <img src="${ctxAssets}/image/index2.png" class="floor">
            <p class="floor-title">签到/展示区</p>
          </ul>
          <div id="myTabContent" class="tab-content">
            <div role="tabpanel" class="tab-pane fade in active" id="home" aria-labelledby="home-tab">
              <div class="left-menu">
                <img src="${ctxAssets }/image/left2.jpg">
                <div class="menu-select">
                  <c:forEach items="${catalog2}" var="c" varStatus="i">
			          <div class="select-list">
	                    <a href="${ctx}/product/list?catalogId=${c.id}">${c.name}</a>
	                  </div>
			      </c:forEach>
                </div>
              </div>
              <div class="right-pro">
                <div id="carousel-example-generic2" class="carousel slide" data-ride="carousel">
                  <ol class="carousel-indicators">
                    <c:set value="0" var="index" />  
                  	<c:forEach items="${indexFloor2 }" var="floor">
                  		<c:if test="${floor.banner=='y'}">
                  			<c:if test="${index==0}">
                  				<li data-target="#carousel-example-generic2" data-slide-to="0" class="active"></li>
	                  		</c:if>
	                  		<c:if test="${index!=0}">
	                  			 <li data-target="#carousel-example-generic2" data-slide-to="${index }" class=""></li>
	                  		</c:if> 
	                  		<c:set value="${index+1}" var="index" /> 
	                  	</c:if>
                  	</c:forEach>
                  </ol>
                  <div class="carousel-inner" role="listbox">
                   	<c:set value="0" var="index" />  
                  	<c:forEach items="${indexFloor2 }" var="floor">
                  		<c:if test="${floor.banner=='y'}">
                  			<c:if test="${index==0}">
                  				<div class="item active">
                  				  <a target="_blank" href="${ctx }${floor.link}" title="${floor.title }">
			                      	<img src="${IMAGE_ROOT_PATH }/${floor.picture}" data-holder-rendered="true">
			                      </a>
			                    </div>
	                  		</c:if>
	                  		<c:if test="${index!=0}">
	                  			 <div class="item">
	                  			   <a target="_blank" href="${ctx }${floor.link}" title="${floor.title }">
			                      	<img src="${IMAGE_ROOT_PATH }/${floor.picture}" data-holder-rendered="true">
			                       </a>
			                    </div>
	                  		</c:if> 
	                  		<c:set value="${index+1}" var="index" />
	                  	</c:if>
                  	</c:forEach>
                  </div>
                </div>
                <c:set value="0" var="index" />  
                <c:forEach items="${indexFloor2 }"  var="floor">
                	<c:if test="${floor.banner!='y' and index<6}">
                		<c:set value="${index+1}" var="index" /> 
	                	 <div class="sm-box">
	                	 	<a target="_blank" href="${ctx }${floor.link}" title="${floor.title }">
		                  		<img src="${IMAGE_ROOT_PATH }/${floor.picture}">
		                  	</a>
		                 </div>
		            </c:if>
                </c:forEach>
              </div>
            </div>
          </div>
        </div>
        <div class="tabs third" data-example-id="togglable-tabs">
          <ul id="myTabs" class="nav nav-tabs" role="tablist"> 
            <c:forEach items="${catalog3}" var="c" varStatus="i">
	            <li role="presentation" >
	              <a href="${ctx}/product/list?catalogId=${c.id}" role="tab" id="profile-tab" data-toggle="tab" aria-controls="profile">${c.name}</a>
	            </li>
	        </c:forEach>
            <img src="${ctxAssets}/image/index3.png" class="floor">
            <p class="floor-title">特色套系</p>
          </ul>
          <div id="myTabContent" class="tab-content">
            <div role="tabpanel" class="tab-pane fade in active" id="home" aria-labelledby="home-tab">
              <div class="left-menu">
                <img src="${ctxAssets}/image/left3.jpg">
                <div class="menu-select">
                  <c:forEach items="${catalog3}" var="c" varStatus="i">
			          <div class="select-list">
	                    <a href="${ctx}/product/list?catalogId=${c.id}">${c.name}</a>
	                  </div>
			      </c:forEach>
                </div>
              </div>
              <div class="right-pro">
              	<c:set value="0" var="index" />  
                <c:forEach items="${indexFloor3 }" var="floor">
                	<c:if test="${floor.banner!='y' and index<2}">
	                	 <div class="sm-box">
	                	 	<a target="_blank" href="${ctx }${floor.link}" title="${floor.title }">
		                  		<img src="${IMAGE_ROOT_PATH }/${floor.picture}">
		                  	</a>
		                  	<c:set value="${index+1 }" var="index" /> 
		                 </div>
	                 </c:if>
                </c:forEach>
                <div id="carousel-example-generic-3" class="carousel slide" data-ride="carousel">
                  <ol class="carousel-indicators">
                    <c:set value="0" var="index" />  
                  	<c:forEach items="${indexFloor3 }" var="floor">
                  		<c:if test="${floor.banner=='y'}">
                  			<c:if test="${index==0}">
                  				<li data-target="#carousel-example-generic-3" data-slide-to="0" class="active"></li>
	                  		</c:if>
	                  		<c:if test="${index!=0}">
	                  			 <li data-target="#carousel-example-generic-3" data-slide-to="${index }" class=""></li>
	                  		</c:if> 
	                  		<c:set value="${index+1}" var="index" />  
	                  	</c:if>
                  	</c:forEach>
                  </ol>
                  <div class="carousel-inner" role="listbox">
                    <c:set value="0" var="index" />  
                  	<c:forEach items="${indexFloor3 }" var="floor">
                  		<c:if test="${floor.banner=='y'}">
                  			<c:if test="${index==0}">
                  				<div class="item active">
                  				  <a target="_blank" href="${ctx }${floor.link}" title="${floor.title }">
			                      	<img src="${IMAGE_ROOT_PATH }/${floor.picture}" data-holder-rendered="true">
			                      </a>
			                    </div>
	                  		</c:if>
	                  		<c:if test="${index!=0}">
	                  			 <div class="item">
	                  			   <a target="_blank" href="${ctx }${floor.link}" title="${floor.title }">
			                      	<img src="${IMAGE_ROOT_PATH }/${floor.picture}" data-holder-rendered="true">
			                       </a>
			                    </div>
	                  		</c:if> 
	                  		<c:set value="${index+1}" var="index" />  
	                  	</c:if>
                  	</c:forEach>
                  </div>
                </div>
                <c:set value="0" var="index" />  
                <c:forEach items="${indexFloor3 }"  var="floor">
                	<c:if test="${floor.banner!='y' and index<8}">
                		<c:set value="${index+1}" var="index" /> 
                		<c:if test="${index>2}">
	                	 <div class="sm-box">
	                	 	<a target="_blank" href="${ctx }${floor.link}" title="${floor.title }">
		                  		<img src="${IMAGE_ROOT_PATH }/${floor.picture}">
		                  	</a>
		                 </div>
		                </c:if>
	                 </c:if>
                </c:forEach>
              </div>
            </div>
          </div>
        </div>
        <div class="tabs fourth" data-example-id="togglable-tabs">
          <ul id="myTabs" class="nav nav-tabs" role="tablist"> 
            <c:forEach items="${catalog4}" var="c" varStatus="i">
	            <li role="presentation" >
	              <a href="${ctx}/product/list?catalogId=${c.id}" role="tab" id="profile-tab" data-toggle="tab" aria-controls="profile">${c.name}</a>
	            </li>
	        </c:forEach>
            <img src="${ctxAssets }/image/index4.png" class="floor">
            <p class="floor-title">消耗器材</p>
          </ul>
          <div id="myTabContent" class="tab-content">
            <div role="tabpanel" class="tab-pane fade in active" id="home" aria-labelledby="home-tab">
              <div class="left-menu">
                <img src="${ctxAssets }/image/left4.jpg">
                <div class="menu-select">
                  <c:forEach items="${catalog4}" var="c" varStatus="i">
			          <div class="select-list">
	                    <a href="${ctx}/product/list?catalogId=${c.id}">${c.name}</a>
	                  </div>
			      </c:forEach>
                </div>
              </div>
              <div class="right-pro">
                <div id="carousel-example-generic4" class="carousel slide" data-ride="carousel">
                  <ol class="carousel-indicators">
                    <c:set value="0" var="index" />  
                  	<c:forEach items="${indexFloor4 }" var="floor">
                  		<c:if test="${floor.banner=='y'}">
                  			<c:if test="${index==0}">
                  				<li data-target="#carousel-example-generic4" data-slide-to="0" class="active"></li>
	                  		</c:if>
	                  		<c:if test="${index!=0}">
	                  			 <li data-target="#carousel-example-generic4" data-slide-to="${index }" class=""></li>
	                  		</c:if> 
	                  		<c:set value="${index+1}" var="index" />  
	                  	</c:if>
                  	</c:forEach>
                  </ol>
                  <div class="carousel-inner" role="listbox">
                    <c:set value="0" var="index" />  
                  	<c:forEach items="${indexFloor4 }" var="floor">
                  		<c:if test="${floor.banner=='y'}">
                  			<c:if test="${index==0}">
                  				<div class="item active">
                  				  <a target="_blank" href="${ctx }${floor.link}" title="${floor.title }">
			                      	<img src="${IMAGE_ROOT_PATH }/${floor.picture}" data-holder-rendered="true">
			                      </a>
			                    </div>
	                  		</c:if>
	                  		<c:if test="${index!=0}">
	                  			 <div class="item">
	                  			   <a target="_blank" href="${ctx }${floor.link}" title="${floor.title }">
			                      	<img src="${IMAGE_ROOT_PATH }/${floor.picture}" data-holder-rendered="true">
			                       </a>
			                    </div>
	                  		</c:if> 
	                  		<c:set value="${index+1}" var="index" />  
	                  	</c:if>
                  	</c:forEach>
                  </div>
                </div>
                <c:set value="0" var="index" />  
                <c:forEach items="${indexFloor4 }"  var="floor">
                	<c:if test="${floor.banner!='y' and index<6}">
                		<c:set value="${index+1}" var="index" /> 
	                	 <div class="sm-box">
	                	 	<a target="_blank" href="${ctx }${floor.link}" title="${floor.title }">
		                  		<img src="${IMAGE_ROOT_PATH }/${floor.picture}">
		                  	</a>
		                 </div>
	                 </c:if>
                </c:forEach>
              </div>
            </div>
          </div>
        </div>
        <div class="tabs fifth" data-example-id="togglable-tabs">
          <ul id="myTabs" class="nav nav-tabs" role="tablist"> 
            <c:forEach items="${catalog4}" var="c" varStatus="i">
	            <li role="presentation" >
	              <a href="${ctx}/product/list?catalogId=${c.id}" role="tab" id="profile-tab" data-toggle="tab" aria-controls="profile">${c.name}</a>
	            </li>
	        </c:forEach>
            <img src="${ctxAssets }/image/index5.png" class="floor">
            <p class="floor-title">工艺装饰品区</p>
          </ul>
          <div id="myTabContent" class="tab-content">
            <div role="tabpanel" class="tab-pane fade in active" id="home" aria-labelledby="home-tab">
              <div class="left-menu">
                <img src="${ctxAssets }/image/left5.jpg">
                <div class="menu-select">
                  <c:forEach items="${catalog5}" var="c" varStatus="i">
			          <div class="select-list">
	                    <a href="${ctx}/product/list?catalogId=${c.id}">${c.name}</a>
	                  </div>
			      </c:forEach>
                </div>
              </div>
              <div class="right-pro">
                <c:set value="0" var="index" />  
                <c:forEach items="${indexFloor5 }" var="floor">
                	<c:if test="${floor.banner!='y' and index<3}">
	                	 <div class="sm-box">
	                	 	<a target="_blank" href="${ctx }${floor.link}" title="${floor.title }">
		                  		<img src="${IMAGE_ROOT_PATH }/${floor.picture}">
		                  	</a>
		                  	<c:set value="${index+1 }" var="index" /> 
		                 </div>
	                 </c:if>
                </c:forEach>
                <div id="carousel-example-generic-5" class="carousel slide" data-ride="carousel">
                  <ol class="carousel-indicators">
                    <c:set value="0" var="index" />  
                  	<c:forEach items="${indexFloor5 }" var="floor">
                  		<c:if test="${floor.banner=='y'}">
                  			<c:if test="${index==0}">
                  				<li data-target="#carousel-example-generic-5" data-slide-to="0" class="active"></li>
	                  		</c:if>
	                  		<c:if test="${index!=0}">
	                  			 <li data-target="#carousel-example-generic-5" data-slide-to="${index }" class=""></li>
	                  		</c:if> 
	                  		<c:set value="${index+1}" var="index" />  
	                  	</c:if>
                  	</c:forEach>
                  </ol>
                  <div class="carousel-inner" role="listbox">
                    <c:set value="0" var="index" />  
                  	<c:forEach items="${indexFloor5 }" var="floor">
                  		<c:if test="${floor.banner=='y'}">
                  			<c:if test="${index==0}">
                  				<div class="item active">
                  				  <a target="_blank" href="${ctx }${floor.link}" title="${floor.title }">
			                      	<img src="${IMAGE_ROOT_PATH }/${floor.picture}" data-holder-rendered="true">
			                      </a>
			                    </div>
	                  		</c:if>
	                  		<c:if test="${index!=0}">
	                  			 <div class="item">
	                  			   <a target="_blank" href="${ctx }${floor.link}" title="${floor.title }">
			                      	<img src="${IMAGE_ROOT_PATH }/${floor.picture}" data-holder-rendered="true">
			                       </a>
			                    </div>
	                  		</c:if> 
	                  		<c:set value="${index+1}" var="index" />  
	                  	</c:if>
                  	</c:forEach>
                  </div>
                </div>
                <c:set value="0" var="index" />  
                <c:forEach items="${indexFloor5 }"  var="floor">
                	<c:if test="${floor.banner!='y' and index<8}">
                		<c:set value="${index+1}" var="index" /> 
                		<c:if test="${index>3}">
	                	 <div class="sm-box">
	                	 	<a target="_blank" href="${ctx }${floor.link}" title="${floor.title }">
		                  		<img src="${IMAGE_ROOT_PATH }/${floor.picture}">
		                  	</a>
		                 </div>
		                </c:if>
	                 </c:if>
                </c:forEach>
              </div>
            </div>
          </div>
        </div>
<!--         <div class="tabs sixth" data-example-id="togglable-tabs"> -->
<!--           <ul id="myTabs" class="nav nav-tabs" role="tablist">  -->
<!--             <c:forEach items="${catalog6}" var="c" varStatus="i"> -->
<!-- 	            <li role="presentation" > -->
<!-- 	              <a href="${ctx}/product/list?catalogId=${c.id}" role="tab" id="profile-tab" data-toggle="tab" aria-controls="profile">${c.name}</a> -->
<!-- 	            </li> -->
<!-- 	        </c:forEach> -->
<!--             <img src="${ctxAssets }/image/index6.png" class="floor"> -->
<!--             <p class="floor-title">婚礼资料</p> -->
<!--           </ul> -->
<!--           <div id="myTabContent" class="tab-content"> -->
<!--             <div role="tabpanel" class="tab-pane fade in active" id="home" aria-labelledby="home-tab"> -->
<!--               <div class="left-menu"> -->
<!--                 <img src="${ctxAssets }/image/floor1.jpg"> -->
<!--                 <div class="menu-select"> -->
<!--                   <c:forEach items="${catalog6}" var="c" varStatus="i"> -->
<!-- 			          <div class="select-list"> -->
<!-- 	                    <a href="${ctx}/product/list?catalogId=${c.id}">${c.name}</a> -->
<!-- 	                  </div> -->
<!-- 			      </c:forEach> -->
<!--                 </div> -->
<!--               </div> -->
<!--               <div class="right-pro"> -->
<!--                 <c:set value="0" var="index" />   -->
<!--                 <c:forEach items="${indexFloor6 }"  var="floor"> -->
<!--                 	<c:if test="${floor.banner!='y' and index<10}"> -->
<!--                 		<c:set value="${index+1}" var="index" />  -->
<!-- 	                	 <div class="sm-box"> -->
<!-- 	                	 	<a target="_blank" href="${ctx }${floor.link}" title="${floor.title }"> -->
<!-- 		                  		<img src="${IMAGE_ROOT_PATH }/${floor.picture}"> -->
<!-- 		                  	</a> -->
<!-- 		                 </div> -->
<!-- 	                 </c:if> -->
<!--                 </c:forEach> -->
<!--               </div> -->
<!--             </div> -->
<!--           </div> -->
<!--         </div> -->
<!--       </div> -->
<%-- <content tag="local_script">
<script type="text/javascript" src="${ctxAssetsJS }/javascripts/index-menu.js"></script>
</content> --%>
</body>
</html>
