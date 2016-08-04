<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!doctype html> 
<html>
<head>
    <meta name="decorator" content="cart_default_new"/>
    <title>订单确认</title>
    <link href="${ctxAssets }/stylesheets/waiting.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div class="container-n">
         <div class="loading-n">
             <div class="loading">
                 <img src="${ctxAssets }/image/loading.gif">
                 <p>您的订单系统正在处理，请稍等片刻....</p>
                 <p><span id="seconds">5</span> 秒后将跳转至购物车，也可以直接<a href="${ctx }/cart"> 返回购物车>>></a></p>
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
				    	window.location.href = "${ctx}/cart";
				    } else {
				        w--;
				        $('#seconds').text(w);
				        console.log(w);
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