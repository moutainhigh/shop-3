<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html> 
<html>
<head>
  <meta name="decorator" content="default_new"/>
  <meta name="Keywords" content=""/>
  <meta name="Description" content=""/>
  <link href="${ctxAssets }/stylesheets/yuyue1.css" rel="stylesheet" type="text/css">
  <title>预约成功</title>
</head>
<body>

<div class="container-n">
  <div class="appointment-n">
    <div class="appointment-success">
      <img src="${ctxAssets }/image/yuyue.png" class="appointment-success-img">
      <div class="appointment-success-main">
        <p class="appointment-success-p">恭喜您！预约成功！</p>
        <div class="appointment-success-word">
        	<p>您的预约已提交，我们会尽快为您安排人员，请您耐心等候！</p>
        	<p style="margin-right: 50px;">来电咨询请拨打：400-6883-520</p><br>
        	<p><span id="seconds">5</span>秒后将自动返回首页</p>
        	<div class="clear"></div>
        </div>
        
        <div class="go-to-appointment">
        	<a href="${ctx }" class="goto-btn goto-btn-cuc">返回首页</a>
        	<a href="${ctx }/i/booking" class="goto-btn goto-btn-cuc">我的预约单</a>
        	<div class="clear"></div>
        </div>
      </div>
    </div>
  </div>
</div>
    
<content tag="local_script">
	<script type="text/javascript">
			$(function(){
				var w = 5;
				waiting();
				function waiting() {
				    if (w == 0) {
				    	window.location.href = "${ctx}";
				    } else {
				        w--;
				        $('#seconds').text(w);
				        setTimeout(function() {
				        	waiting();
				        }, 1000);
				    }
				}
			});
		</script>
</content>
</body>
</html>
