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
    <title>认证审核</title>
    <link rel="stylesheet" type="text/css" href="${ctxAssets}/stylesheets/renzheng1.css"/>
    <content tag="local_script">
	    <script type="text/javascript">
	    	$(function(){
	    		var status = ${status};
		    	if (status == 1) {
		    		$("#request").show();
		    	} else if (status == 2) {
		    		$("#success").show();
		    	} else if (status == 3) {
		    	    $("#fail").show();
		    	}
	    	});
	    </script>
    </content>
  </head>
  <body>
    <div id="request" class="container-n" style="display:none">
      <div class="step-img">
        <img src="${ctxAssets }/image/step2.png"/>
      </div>
      <div class="info-after">
        <h2>您的信息已经成功提交，审核结果大概需要1个工作日，<br>请耐心等待！</br></h2>
        <p>您的认证账号是：<em>${accountName }</em>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="${ctx }/mall">去道具商城逛逛</a></p>
      </div>
    </div>
    
    <div id="success" class="container-n" style="display:none">
      <div class="step-img">
        <img src="${ctxAssets }/image/step3.png"/>
      </div>
      <div class="info-after">
        <div>
          <img src="${ctxAssets }/image/success.png"/>
          <h1>恭喜您，已认证成功！</h1>
          <div class="clear"></div>
        </div>
        <p>您的认证账号是：<em>${accountName }</em>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您的信息已经通过审核，感谢您的耐心等待！</p>
      </div>
    </div>
    
    <div id="fail" class="container-n" style="display:none">
      <div class="step-img">
        <img src="${ctxAssets }/image/step3.png"/>
      </div>
      <div class="info-after">
        <div>
          <img src="${ctxAssets }/image/fail.png"/>
          <h1>抱歉，认证失败！</h1>
          <div class="clear"></div>
        </div>
        <p> 您的认证账号是：<em>${accountName }</em></p>
       <p>认证失败原因：<em>${rejectReason }</em></p>
      </div>
      <div class="info-after-btn">
      	<c:if test="${type== 1 }">
      		<div class="re-sub f-l"><a href="${ctx }/i/account/personAuth?recommit=y">好了，我想重新提交</a></div>
      	</c:if>
      	<c:if test="${type== 2 }">
      	    <div class="re-sub f-l"><a href="${ctx }/i/account/enterpriseAuth?recommit=y">好了，我想重新提交</a></div>
      	</c:if>
        <div class="go_to_shop f-r"><a href="${ctx }/mall">算了，去道具商城逛逛</a></div>
        <div class="clear"></div>
      </div>
    </div>
  </body>
</html>