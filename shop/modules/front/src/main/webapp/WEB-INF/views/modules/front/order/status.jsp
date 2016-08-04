<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!doctype html> 
<html>
<head>
    <meta name="decorator" content="cart_default_new"/>
    <title>支付状态</title>
    <link href="${ctxAssets }/stylesheets/shopcar3.css" rel="stylesheet" type="text/css">
</head>
<body>

<div class="content-n cart">
  <div class="cart_left">
    <div class="pay_status">
      <c:if test="${order.paystatus eq 'y' }">
      <div class="pay_status_img pay_status_success">
        <div style="line-height: 1.5;" class="status_text">
          <span class="pay_icon pay_success"></span>
          <h3 style="margin-top:10px;font-weight: 700">
              感谢您！支付成功！
          </h3>
          <p>共计支付<span class="pink">￥${order.amount }</span></p>
        </div>
      </div>
      </c:if>
      <c:if test="${order.paystatus ne 'y' }">
      <div class="pay_status_img">
        <div class="status_text">
          <span class="pay_icon pay_failed"></span>
          尚未成功支付！
          <p>如果您已付款，可能因交易量激增导致交易单延迟处理（最长数秒至数分钟）<br>
            您可稍后 
            <a href="javascript:location.reload();">刷新本页面</a> 
            或前往 
            <a href="${ctx }/i/order" target="_blank">我的订单</a> 查看支付情况
          </p>
        </div>
      </div>
      </c:if>
      <div class="clear"></div>
      <div class="list_status">
        <c:if test="${order.paystatus eq 'y' }">
        <h2 class="green">
          <span class="pay_icon"></span>
          购买商品已在处理中，我们将尽快为您发货！
        </h2>
        </c:if>
        <c:if test="${order.paystatus ne 'y' }">                           
        <h2 class="blue">
          <span class="pay_icon"></span>
          以下交易单尚未成功支付，请您尽快完成支付！
        </h2>
        </c:if>
        <table style="margin-bottom: 15px;">
          <tbody>
            <tr>
              <td>
                交易单号：<a href="${ctx }/i/order/detail/${order.id }" target="_blank">${order.no }</a>
              </td>
              <td>
                应付金额：￥<span class="pink bold">${order.amount }</span>
              </td>
              <td>
                <p>
                  <c:if test="${order.paystatus eq 'y' }">
                  <a href="${ctx }/i/order/detail/${order.id}" class="btn_pink_mid">查看详情</a>
                  </c:if>
                  <c:if test="${order.paystatus ne 'y' }">
                  <a href="${ctx }/order/pay?orderId=${order.id}" class="btn_pink_mid">点击付款</a>
                  </c:if>
                </p>           
              </td>
            </tr>
          </tbody>
        </table>
      </div>
      <c:if test="${order.paystatus eq 'y' }">
      <div style="height:40px; padding-top:10px">
        <div style="height:20px">
          <span style="padding-left:30px;" class="pink">重要提醒：报喜了不会以订单异常、系统升级等为由，要求您点击任何链接进行退款、重新付款、额外付款操作。</span><br>
        </div>
        <div style="height:20px"> 
          <span style="padding-left:30px;" class="pink">请认准报喜了唯一官方电话 400-6883-520</span>
        </div>
      </div>
      </c:if>
      <c:if test="${order.paystatus ne 'y' }">
      <div class="fail_reason">
        <h2 class="blue">付款遇到问题了？先看看是不是由于下面的原因：</h2>
        <ul>
          <li>
            <p class="bold">所需支付金额超过了银行支付限额</p>	
            <p>建议您登录网上银行提高上限额度，或是先分若干次充值到报喜了余额，即能轻松支付。</p>
          </li>
          <li>
            <p class="bold">支付宝、百度钱包或网银页面显示错误或者空白</p>
            <p>部分网银对不同浏览器的兼容性有限，导致无法正常支付，建议您使用IE浏览器进行支付操作。</p>
          </li>
          <li>
            <p class="bold"> 网上银行已扣款，报喜了交易单仍显示“未付款”</p>
            <p> 可能由于银行的数据没有即时传输，请不要担心，稍后刷新页面查看。如较长时间仍显示未付款，可联系报喜了客服(400-6883-520)为您解决。</p>
          </li>
        </ul>
      </div>
      </c:if>
      <div class="info">
        <p class="btn_container">
          <a class="btn_pink_big" href="${ctx }/mall">继续购物</a>
        </p>     
      </div>
    </div>
  </div>
</div>
<content tag="local_script">
<script type="text/javascript">
	$(function(){
		$('body').css("background-color", "#f0f0f0")
	});
</script>
</content>
</body>
</html>