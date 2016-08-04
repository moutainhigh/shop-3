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
            <img src="${IMAGE_ROOT_PATH}/${account.picture}"/>
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
              <img src="${account.twoDimensionUrl }"/>
            </div>
            <p>扫描接受邀请码</p>
          </div>
          <div class="clear"></div>
        </div>
        <div class="clear"></div>
      </div>
      <div class="statistics">
      	  <form id="searchForm" style="display:none;" action="${ctx }/inviteProfit" method="post">
			 <input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize }"/>
			 <input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo }"/>
		  </form>
          <!-- Nav tabs -->
          <div class="filter">
          		<c:if test="${!viewByMaster}">
					<a href="${ctx }/i/invite">我的邀请(${inviteTotal})</a>
				</c:if>
				<c:if test="${isMaster or viewByMaster}">
					<a class="curr" href="#profile">收益统计</a>
				</c:if>
		  </div>
          <table id="order-list" class="order_table">
		   	  <tbody>
				  <tr class="order_list_title">
				 	  <th class="th-width">收益</th>
                      <th class="th-width">收益时间</th>
                      <th class="th-width">收益来自</th>
                      <th class="th-width">收益描述</th>
				  </tr>
				  <c:forEach items="${profit.list}" var="item">	 
	                  <tr>
	                    <td>${item.profit }</td>
	                    <td>
	                    	<fmt:formatDate value="${item.createDate }" pattern="yyyy-MM-dd HH:mm:ss"/> 
	                    </td>
	                    <td><a target="_blank" href="${ctx }/i/inviteProfit?aid=${item.inviteeId}">${item.inviteeName}</a></td>
	                    <td>${item.description }</td>
	                  </tr>
	              </c:forEach>
             </tbody>
         </table>
         <div id="pagination"></div>
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