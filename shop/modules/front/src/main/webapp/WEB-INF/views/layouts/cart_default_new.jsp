<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html> 
<html> 
  <head> 
    <meta charset="utf-8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no" />
    <meta content="yes" name=" apple-mobile-web-app-capable" />
    <title><sitemesh:title/> - 报喜了</title>
    <link rel="icon" type="image/png" href="${ctxAssets }/image/favicon.png">
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="${ctxAssets }/stylesheets/shopcar1.css">
    <%-- 
    <link rel="stylesheet" type="text/css" href="${ctxAssets }/stylesheets/shopcar1.css">
     --%>
    <sitemesh:head/>
  </head>
  <body>
    <div class="header-n container-n">
      <div class="header-top container-n">
      	<shiro:guest> 
      	<div class="header-top-main content-n clearfix-n">
          <div>
            欢迎您，
            <a href="${ctx }/login" class="user-name">登录</a>
            &nbsp;
            <a href="${ctx }/regist" class="user-logout">免费注册</a>
          </div>
        </div>
      	</shiro:guest>
      	<shiro:user> 
        <div class="header-top-main content-n clearfix-n">
          <div>
            欢迎您，
            <a href="${ctx }/i/order" class="user-name"><shiro:principal property="username"/></a>
            &nbsp;
            <a href="${ctx }/logout" class="user-logout">[退出]</a>
            <i>|</i>
            <a href="${ctx }/i/order" class="user-dingdan">订单查询</a>
          </div>
        </div>
        </shiro:user>
      </div>
      <div class="header-bottom">
        <div class="header-bottom container-n">
          <div class="header-bottom-main content-n clearfix-n">
            <a href="${ctx }/" class="header-logo"><img src="${ctxAssets }/image/logo.png"></a>
            <div class="order_path_${step }"></div>
          </div>
        </div>
      </div>
    </div>
    <div class="main container-n body-color">
      <div class="content-n">
      <sitemesh:body/>
      </div>
    </div>
    <script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <%-- <script type="text/javascript" src="${ctxAssetsJS }/javascripts/shopcar1.js"></script> --%>
    <sitemesh:getProperty property="page.local_script"/>
  </body>
</html>