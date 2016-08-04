<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="decorator" content="account_default_new"/>
    <meta name="Keywords" content=""/>
    <meta name="Description" content=""/>
 	<link rel="stylesheet" type="text/css" href="${ctxAssets}/stylesheets/yaoqing1.css"/>
 	<link href="${ctxStatic }/css/pagination.css" rel="stylesheet" type="text/css"></link>	
  </head>
  <body>
    <div class="container-n">
      <div class="user-info">
        <div class="use-info-main">
          <div class="user-pic">
            <img src="${IMAGE_ROOT_PATH }/${account.picture}"/>
          </div>
          <div class="user-info-list">
            <p class="user-name">${account.nickname }</p>
            <p class="user-name-list-p">用户名:<em>${account.account}</em></p>
            <c:if test="${isMaster}">
            	<p class="user-name-list-p">收&nbsp;&nbsp;&nbsp;益:<em>￥${account.profit}</em></p>
            </c:if>
            <p class="user-name-list-p">邀请码:
              <em>${account.inviteCode}</em>
              <img src="${ctxAssets}/image/copy.png" class="copy"/>
              <span class="copy-code">复制邀请链接</span>
              <a id="inviteUrl" style="display:none">${inviteUrl}</a>
            </p>
          </div>
          <div class="code">
            <div class="code-img">
              <img src="${IMAGE_ROOT_PATH }/${account.twoDimensionUrl }"/>
            </div>
            <p>扫描接受邀请码</p>
          </div>
          <div class="clear"></div>
        </div>
        <div class="clear"></div>
      </div>
      <div class="statistics">
      	  <form id="searchForm" style="display:none;" action="${ctx }/i/invite" method="post">
			 <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize }"/>
			 <input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo }"/>
		  </form>
          <!-- Nav tabs -->
          <div class="filter">
				<a class="curr" href="#home">我的邀请(${page.total })</a>
				<c:if test="${isMaster}">
					<a href="${ctx }/i/inviteProfit">收益统计</a>
				</c:if>
		  </div>
          <!-- Tab panes -->
          <c:if test="${empty page.list }"><div class="null_info"><h2>您还没有任何人邀请喔！</h2></div></c:if>
          <c:if test="${not empty page.list }">
	          <table id="order-list" class="order_table">
			   	  <tbody>
					  <tr class="order_list_title">
					 	 <th>被邀人</th>
					  	 <th>手机号</th>
		                 <th>注册时间<span class="statistics-sort-down"></span></th>
		                 <th>最近登录</th>
		                 <c:if test="${isMaster}">
							 <th>收益</th>
			                 <th>操作</th>
		                 </c:if>
					  </tr>
					 <c:forEach items="${page.list}" var="item">	  
		                 <tr>
			                  <td>${empty item.trueName ? item.account : item.trueName}</td>
			                  <td>${item.mobile}</td>
			                  <td><fmt:formatDate value="${item.regeistDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			                  <td><fmt:formatDate value="${item.lastLoginTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			                  <c:if test="${isMaster}">
							  	    <td>￥${account.profit}</td>
			                 	    <td class="details"><a href="${ctx }/i/inviteProfit?aid=${item.id }">查看详情</a></td>
			                  </c:if>
		                 </tr>
	                 </c:forEach>	
	             </tbody>
		     </table>
	         <div id="pagination"></div>
         </c:if>
      </div>
    </div>
    <content tag="local_script">
    	<script type="text/javascript" src="${ctxStaticJS }/js/zclip/jquery.zclip.min.js"></script>
    	<script src="${ctxStaticJS }/js/jquery.pagination.js" type="text/javascript"></script>
    	 <script type="text/javascript">
	    	$(document).ready(function(){
	    	     $(".copy").zclip({ 
	    	 		path:'${ctxStaticJS }/js/zclip/ZeroClipboard.swf', 
	    	 		copy:$('#inviteUrl').text()
	    	 		
	    	    }); 
	    	    
	    	    $(".zclip").css({
	    	    	width: "15px",
	    	    	height: "15px",
	    	    	left: "120px",
	    	    	top: "5px"
	    	    })
	       }); 
	       
	       $("#pagination").pagination('${page.total}', {
		        callback: function(index, jq){
		        	$('#pageNo').val(Number(index));
		        	document.getElementById('searchForm').submit();
		        },
		        current_page:'${page.pageNo}',
				num_edge_entries:1,
				num_display_entries:3,
				link_to:"#",
				prev_text:"上一页",
				next_text:"下一页",
		    });
   		 </script>
    </content>
    
  </body>
</html>