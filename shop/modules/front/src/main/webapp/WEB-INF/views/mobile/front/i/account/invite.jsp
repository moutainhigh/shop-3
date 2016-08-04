<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/mobile/include/taglib.jsp"%>
<!doctype html> 
<html> 
  <head> 
    <meta charset="utf-8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>我的邀请码</title>
    <%@include file="/WEB-INF/views/mobile/include/head.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctxMS }/css/phone-invitation-code.css">
  </head>
  <body>
        <div class="am-u-sm-12 title">
            <p>我的邀请码</p>
        </div>
        <div class="am-u-sm-12 title2">
            <p>分享你的注册码</p>
        </div>
        <div class="am-u-sm-12 code">
            <p>${account.inviteCode}</p>
        </div>
        <div class="am-u-sm-12 title3">
            <p>扫描二维码信息接受邀请</p>
        </div>
        <div class="am-u-sm-12 code-img">
            <img src="${IMAGE_ROOT_PATH }/${account.twoDimensionUrl }">
        </div>
        
        <div class="am-u-sm-12 inputs">
            <div class="submit-input">
            	<%-- <c:if test="${account.inviteId == null }">
                <input type="text" class="am-form-field am-round" maxlength="6" placeholder="输入邀请码接受邀请"/>
                <div class="submit-btn">
                    <img src="${ctxMS }/imgs/submit-r.png">
                </div>
                </c:if> --%>
            </div>
        </div>
        <div class="am-u-sm-12 foot am-vertical-align">
            <div class="am-vertical-align-middle">
                <div class="third-login">
                    <div class="line"></div>
                    <p>分享到第三方平台</p>
                </div>
                <div class="login-link am-cf">
                    <div class="qq">
                        <img src="${ctxMS }/imgs/qq-b.png">
                    </div>
                    <div class="weibo">
                        <img src="${ctxMS }/imgs/weixin-b.png">
                    </div>
                </div>
            </div>   
        </div>
        <div class="warning am-vertical-align">
	        <p class="am-vertical-align-middle">您输入的信息有误</p>
	    </div>
<%-- <c:if test="${account.inviteId == null }">	    
<script type="text/javascript">
$(function(){
	$('.submit-btn').click(function(){
		var code = $(".submit-input input").val();
		if(!code || code.length != 6) {
			verification('请输入6位邀请码');
		} else {
			$.post('${ctx}/i/invite/accept', {
				code: code
			}, function(data){
				if(data.s == 1) {
					verification(data.m);
				} else {
					window.location.reload();
				}
			});
		}
	});
});
</script>
</c:if> --%>
  </body>
</html>