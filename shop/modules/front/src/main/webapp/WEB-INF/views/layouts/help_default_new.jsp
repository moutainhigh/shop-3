<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html> 
<html> 
  <head> 
    <meta charset="utf-8"> 
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="Cache-Control" content="no-store" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no" />
    <meta content="yes" name=" apple-mobile-web-app-capable" />
    <title><sitemesh:title/> - 报喜了</title>
    <link rel="icon" type="image/png" href="${ctxAssets }/image/favicon.png">
    <sitemesh:head/>
  </head>
  <body>
  <c:if test="${indexTopAd != null }">${indexTopAd.html }</c:if>
  <div class="top-n container-n">
    <div>  
      <shiro:guest>                  
      <p class="welcome float-l">欢迎来到报喜了！</p>
      <a class="login float-l" href="${ctx }/login">请登录</a>
      <p class="line float-l">|</p>
      <a class="register float-l" href="${ctx }/regist">快速注册</a>
      </shiro:guest>
      <shiro:user>
      <p class="welcome float-l">欢迎你，<a href="${ctx }/i/order"><shiro:principal property="username"/></a></p>
      <p class="line float-l">|</p>
      <a class="logout float-l" href="${ctx }/logout">&nbsp;[退出]&nbsp;</a>
      </shiro:user>
      <p id='num' class="float-r">400-8000-800</p>
      <p class="hot-line float-r">服务热线：</p>
      <a href="${ctx }/i/order" class="dingdan float-r">订单查询</a>
      <a href="${ctx }/i/order" class="wodehunqing float-r">我的婚庆</a>
      <a href="${ctx }/i/favorite" class="wodeshoucang float-r">我的收藏</a>
      <img src="${ctxAssets }/image/xin.png" class="xin float-r">
      <div class="clear"></div>
    </div>
  </div>
  <div class="profile">
    <div class="nav">
      <h2>
        <b>
        </b>
        <a href="/help/main" class="curr">
          帮助中心
        </a>
      </h2>
      <dl class="">
        <dt>
          <b>
          </b>
          使用帮助
        </dt>
        <dd id="dd_ie_fix" style="display: none;">
          <a href="/help/guidebook">
            新手指南
          </a>
          <a href="/help/faq">
            常见问题
          </a>
          <a href="/help/get_update">
            获取更新
          </a>
          <a href="/help/user_terms">
            用户协议
          </a>
        </dd>
      </dl>
      <dl class="">
        <dt>
          <b>
          </b>
          账户相关
        </dt>
        <dd style="display: none;">
          <a href="/help/invite_friends">
            邀请好友
          </a>
          <a href="/help/member_level">
            会员等级
          </a>
          <a href="/help/gold_credit">
            金币积分（新）
          </a>
          <a href="/help/email_subscribe">
            邮件订阅
          </a>
          <a href="/help/msg_subscribe">
            短信订阅
          </a>
          <a href="/help/promocards">
            优惠卡券
          </a>
          <a href="/help/email/mail163">
            邮件加入白名单
          </a>
        </dd>
      </dl>
      <dl class="">
        <dt>
          <b>
          </b>
          支付相关
        </dt>
        <dd style="display: none;">
          <a href="/help/two_for_freeshipping">
            满159包邮
          </a>
          <a href="/help/pay_promocards">
            现金券支付
          </a>
          <a href="/help/pay_balance">
            余额支付
          </a>
          <a href="/help/pay_online">
            在线支付
          </a>
        </dd>
      </dl>
      <dl class="">
        <dt>
          <b>
          </b>
          配送相关
        </dt>
        <dd style="display: none;">
          <a href="/help/lighting_shipping">
            闪电发货
          </a>
          <a href="/help/delivery_state">
            配送说明
          </a>
          <a href="/help/delivery_fee">
            运费说明
          </a>
          <a href="/help/express_search">
            快递查询
          </a>
          <a href="/help/check_goods">
            验货签收
          </a>
        </dd>
      </dl>
      <dl class="">
        <dt>
          <b>
          </b>
          售后服务
        </dt>
        <dd style="display: none;">
          <a href="/activity_guarantee.html" target="_blank">
            正品保证
          </a>
          <a href="/help/refund_policies">
            退货政策
          </a>
          <a href="/help/refund_handle">
            退货办理
          </a>
          <a href="/help/refund_state">
            退款说明
          </a>
          <a href="/help/koubei">
            口碑中心
          </a>
        </dd>
      </dl>
      <dl class="">
        <dt>
          <b>
          </b>
          聚美手机版
        </dt>
        <dd style="display: none;">
          <a href="http://www.jumei.com/i/activity/download_app" target="_blank">
            手机客户端
          </a>
          <a href="http://m.jumei.com/" target="_blank">
            聚美WAP版
          </a>
        </dd>
      </dl>
      
      <dl class="">
        <dt>
          <b>
          </b>
          聚美优品介绍
        </dt>
        <dd style="display: none;">
          <a href="/about/about_us">
            关于我们
          </a>
          <a href="/about/founder">
            企业创始人
          </a>
          <a href="http://www.jumei.com/activity_meizhuang1218.html" target="_blank">
            明星热荐
          </a>
          <!--<a href="/about/progress">聚美历程回顾</a>-->
        </dd>
      </dl>
      <dl>
        <dt>
          <b>
          </b>
          携手聚美
        </dt>
        <dd style="display: none;">
          <a href="/about/business">
            商务合作
          </a>
          <a href="http://jumeizhaopin.hirede.com/">
            招贤纳士
          </a>
        </dd>
      </dl>
    </div>
    <input type="hidden" id="J_Page_Label" value="main">
    <div class="content">
      <h1>
        帮助中心
      </h1>
      <div class="sector">
        <h2>
          购物流程
        </h2>
        <div style="margin-bottom: 20px;">
          <img src="http://p2.jmstatic.com/help_center/help_steps.jpg" alt="" usemap="#Map">
          <map name="Map" id="Map">
            <area shape="rect" coords="1,0,148,66" target="_blank" href="http://www.jumei.com/help/guidebook#new_signup">
            <area shape="rect" coords="157,2,296,68" target="_blank" href="http://www.jumei.com/help/guidebook#pickup">
            <area shape="rect" coords="311,1,429,67" target="_blank" href="http://www.jumei.com/help/guidebook#add_to_cart">
            <area shape="rect" coords="462,5,586,64" target="_blank" href="http://www.jumei.com/help/guidebook#confirm_order">
            <area shape="rect" coords="609,4,735,66" target="_blank" href="http://www.jumei.com/help/guidebook#receive_comment">
          </map>
        </div>
        <h2>
          帮助导航
        </h2>
        <div class="help_nav">
          <ul>
            <li>
              使用帮助
            </li>
          </ul>
          <div class="nav_list">
            <a target="_blank" href="http://www.jumei.com/help/guidebook">
              新手指南
            </a>
            <a target="_blank" href="http://www.jumei.com/help/faq">
              常见问题
            </a>
            <a target="_blank" href="http://www.jumei.com/help/get_update">
              获取更新
            </a>
            <a target="_blank" href="http://www.jumei.com/help/user_terms">
              用户协议
            </a>
          </div>
        </div>
        <div class="help_nav">
          <ul>
            <li>
              账户相关
            </li>
          </ul>
          <div class="nav_list">
            <a target="_blank" href="http://www.jumei.com/help/invite_friends">
              邀请好友
            </a>
            <a target="_blank" href="http://www.jumei.com/help/member_level">
              会员等级
            </a>
            <a target="_blank" href="http://www.jumei.com/help/gold_credit">
              金币积分（新）
            </a>
            <a target="_blank" href="http://www.jumei.com/help/email_subscribe">
              邮件订阅
            </a>
            <a target="_blank" href="http://www.jumei.com/help/msg_subscribe">
              短信订阅
            </a>
            <a target="_blank" href="http://www.jumei.com/help/promocards">
              优惠卡券
            </a>
            <a target="_blank" href="http://www.jumei.com/help/email/mail163">
              邮件加入白名单
            </a>
          </div>
        </div>
        <div class="help_nav">
          <ul>
            <li>
              支付相关
            </li>
          </ul>
          <div class="nav_list">
            <a target="_blank" href="http://www.jumei.com/help/two_for_freeshipping">
              159包邮
            </a>
            <a target="_blank" href="http://www.jumei.com/help/pay_promocards">
              现金券支付
            </a>
            <a target="_blank" href="http://www.jumei.com/help/pay_balance">
              余额支付
            </a>
            <a target="_blank" href="http://www.jumei.com/help/pay_online">
              在线支付
            </a>
          </div>
        </div>
        <div class="help_nav">
          <ul>
            <li>
              配送相关
            </li>
          </ul>
          <div class="nav_list">
            <a target="_blank" href="http://www.jumei.com/help/lighting_shipping">
              闪电发货
            </a>
            <a target="_blank" href="http://www.jumei.com/help/delivery_state">
              配送说明
            </a>
            <a target="_blank" href="http://www.jumei.com/help/delivery_fee">
              运费说明
            </a>
            <a target="_blank" href="http://www.jumei.com/help/express_search">
              快递查询
            </a>
            <a target="_blank" href="http://www.jumei.com/help/check_goods">
              验货签收
            </a>
          </div>
        </div>
        <div class="help_nav">
          <ul>
            <li>
              售后服务
            </li>
          </ul>
          <div class="nav_list">
            <a target="_blank" href="http://www.jumei.com/activity_guarantee.html">
              正品保证
            </a>
            <a target="_blank" href="http://www.jumei.com/help/refund_policies">
              退货政策
            </a>
            <a target="_blank" href="http://www.jumei.com/help/refund_handle">
              退货办理
            </a>
            <a target="_blank" href="http://www.jumei.com/help/refund_state">
              退款说明
            </a>
            <a target="_blank" href="http://www.jumei.com/help/koubei">
              口碑中心
            </a>
          </div>
        </div>
        <div class="help_nav">
          <ul>
            <li>
              聚美手机版
            </li>
          </ul>
          <div class="nav_list">
            <a target="_blank" href="http://www.jumei.com/i/activity/download_app">
              手机客户端
            </a>
            <a target="_blank" href="http://m.jumei.com">
              聚美WAP版
            </a>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="bottom-icon container-n">
    <div class="container icon-container">
      <div class="row">
        <div class="col-xs-12">
          <img src="${ctxAssets }/image/bottom-img.png">
        </div>
      </div>
    </div>
  </div>
  <div class="footer container-n">
    <div class="container footer-container">
      <div class="row">
        <div class="col-xs-2">
          <p class="footer-p-top">购物指南</p>
          <p class="footer-p-bottom">购物流程</p>
          <p class="footer-p-bottom">会员介绍</p>
        </div>
        <div class="col-xs-2">
          <p class="footer-p-top">配送方式</p>
          <p class="footer-p-bottom footer-p-bottom1">上门自取</p>
          <p class="footer-p-bottom footer-p-bottom2">211限时达</p>
        </div>
        <div class="col-xs-2">
          <p class="footer-p-top">支付方式</p>
          <p class="footer-p-bottom">在线支付</p>
        </div>
        <div class="col-xs-2">
          <p class="footer-p-top">售后服务</p>
          <p class="footer-p-bottom">售后政策</p>
          <p class="footer-p-bottom">取消订单</p>
        </div>
        <%-- 
        <div class="col-xs-2">
          <p class="footer-p-top">手机我们</p>
          <img src="${ctxAssets }/image/code.png">
          <p class="footer-p-bottom3">下载移动客户端</p>
        </div>
         --%>
        <%-- <div class="col-xs-2">
          <p class="footer-p-top">订阅号</p>
          <img src="${ctxAssets }/image/code.png">
          <p class="footer-p-bottom4">关注微信公众号</p>
        </div>
        <div class="col-xs-2">
          <p class="footer-p-top">服务号</p>
          <img src="${ctxAssets }/image/code.png">
          <p class="footer-p-bottom4">关注微信公众号</p>
        </div> --%>
        <div class="col-xs-2">
          <p class="footer-p-top">服务号</p>
          <img src="${ctxAssets }/image/wf_code.jpg" style="width: 75px;height: 75px;">
          <p class="footer-p-bottom4">关注微信服务号</p>
        </div>
      </div>
    </div>
  </div>
  <div class="footer-bottom">
    <p>
      <a>关于我们</a>    |    
      <a>加入我们</a>    |    
      <a>联系我们</a>    |    
      <a>商务合作</a>
    </p>
    <p><div class="copyRight">Copyright 2015-2015 成都雷立风行电子商务有限公司  保留一切权利。蜀ICP备15020915号 客服热线：400-xxx-xxxx</div></p>
  </div>
  <input type="hidden" id="ctx" name="ctx" value="${ctx }">
  <input type="hidden" id="IMAGE_ROOT_PATH" name="IMAGE_ROOT_PATH" value="${IMAGE_ROOT_PATH }">
  <script src="http://libs.baidu.com/jquery/1.9.1/jquery.min.js"></script>
  <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  <script type="text/javascript" src="${ctxAssetsJS }/javascripts/common.js"></script>
  <script type="text/javascript" src="${ctxAssetsJS }/javascripts/index-menu.js"></script>
  <input type="hidden" id="ctx" name="ctx" value="${ctx }">
  <%-- <input type="hidden" id="baseURL" name="baseURL" value="${fn:replace(pageContext.request.requestURL, pageContext.request.requestURI, pageContext.request.contextPath)}"> --%>
  <input type="hidden" id="baseURL" name="baseURL" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}">
  <sitemesh:getProperty property="page.local_script"/>
  <script type="text/javascript">
  $(function(){
  	var _self = window.location.href;
  	$('.nav a').each(function(){
		var _this = $(this);
  		if(_self.indexOf(_this.attr('href')) >= 0) {
  			_this.addClass('selected');
  		}
  	});
  })
  </script>
  </body>
</html>