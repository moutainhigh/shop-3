	<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!doctype html> 
<html>
<head>
    <meta name="decorator" content="cart_default_new"/>
    <title>订单确认</title>
    <link href="${ctxAssets }/stylesheets/shopcar2.css" rel="stylesheet" type="text/css">
    <link href="${ctxAssets }/stylesheets/zhifu.css" rel="stylesheet" type="text/css">
</head>
<body>

<div class="pay content-n">
  <div class="pay-main">
    <div class="cart_notice">
      <h2>还差最后一步，请尽快付款！</h2>
<!--       <div id="tomorrow_timer" diff="1799" style="margin: 15px 0;"> -->
<!--         请于 -->
<!--         <span class="bold">00</span> -->
<!--         时 -->
<!--         <span class="bold">15</span> -->
<!--         分 -->
<!--         <span class="bold">18</span> -->
<!--         秒内，在新开的页面完成支付 -->
<!--       </div> -->
      <p class="pink" style="font-weight: 300; padding: 0">
        请尽快完成付款，否则商品售光后您的交易单将被取消。
      </p>
    </div>
    <div class="option border_top">
      <div class="content">
        <p>
          收货信息：${order.ordership.shipname }&nbsp;&nbsp;-&nbsp;&nbsp;${order.ordership.province }-${order.ordership.city }-${order.ordership.area } ${order.ordership.shipaddress }，${order.ordership.phone }</p>
        <p>
          送货时间：
          <span>
            ${order.deliveryDayName }
          </span>
        </p>
      </div>
    </div>
    <div class="option clearfix" style="padding-top:0;padding-bottom:0;">
      <div class="pay_info">
        <div class="gateway_list" style="background: none; padding: 0; margin:0">
          <ul>
            <li cname="支付宝" style="margin-bottom: 0px">
              <span class="left">支付方式：</span>
              <label class="${order.payCode } bg"></label>
            </li>
          </ul>
          <div class="clear"></div>
          <p style="margin-top:20px;">
            应付金额：
            <span class="pink" style="font-size: 18px;font-family: Arial">
              ¥&nbsp;<span style="font-size: 20px; margin-left: -4px;">${order.amount + order.fee }</span>
            </span>
          </p>
        </div>
      </div>
      <div class="clear"></div>
    </div>
    
<!--     <div class="price_info"> -->
<!--       <p>应付总额：<span class="pink" style="color: #fc2e66; font-size: 20px">${order.amount }</span></p> -->
<!--     </div> -->
<!--     <div class="zhifufangshi" style="<c:if test="${showPay}">display:none</c:if>"> -->
<!--       <ul> -->
<!--         <c:forEach items="${payMap }" var="p" varStatus="status"> -->
<!--         <li class="getways"> -->
<!--           <input type="radio" name="pay" id="pay_${p.key }"> -->
<!--           <label class="tit" for="pay_${p.key }"><c:if test="${p.key eq 'COD' }">货到付款</c:if><c:if test="${p.key eq 'ONLINE' }">在线支付</c:if></label> -->
<!--           <ul class="third_ul clearfix g_ul"> -->
<!--             <c:forEach items="${p.value }"  var="pay"> -->
<!--             <li> -->
<!--               <div class="bd_wrap"> -->
<!--                 <input type="radio" value="${pay.code }" name="gateway" id="check-${pay.code }"> -->
<!--                 <label class="bg ${pay.code }" for="check-${pay.code }"></label> -->
<!--               </div> -->
<!--             </li> -->
<!--             </c:forEach> -->
<!--           </ul> -->
<!--         </li> -->
<!--         </c:forEach> -->
<!--       </ul> -->
<!--     </div> -->
    
    <div class="option clearfix paytype">
       <a id="order-pay-link" onclick="javascript:void(0);" class="btn_pink_big">立即付款</a>
       <div class="clear"></div>
    </div>
    <div id="an_trigger" style="border-bottom: 1px solid #dcdcdc;"></div>
  </div>
</div>

<div id="lightbox" style="left: 50%; margin: 0px 0px 0px -201px; top: 150px;display:none;">
  <div class="container-l">
    <div class="title">
      请付款 
      <div class="close_button"></div>
    </div>
    <div class="content" style="line-height:30px;">
      <h1 class="info">请您在新打开的页面上完成付款</h1>
        <p>付款完成前请不要关闭此窗口</p>
        <p>完成付款后请根据您的情况点击下面的按钮</p>
        <p class="ctrl">
          <input id="payment_successed" data-action="success" type="submit" class="formbutton" value="已完成付款">
          <input id="payment_failed" data-action="failure" type="submit" class="formbutton" value="付款遇到问题">
        </p>
        <div class="clear"></div>
<!--         <a id="payment_confirm_repay" href="${ctx }/order/pay?orderId=${order.id}" style="display: block; color: #fc2e66; margin-top: 10px; font-size: 14px;">» 返回选择其他支付方式</a> -->
    </div>
  </div>
</div>
<div class="light-box" style="display:none;">
</div>

<form id="order-pay-form" method="post" action="${ctx }/order/pay/forward" target="_blank" style="display:none;">
  <input type="hidden" id="orderId" name="orderId" value="${order.id }" />
  <%-- 
  <input type="hidden" name="default_login" value="N" />
  <input type="hidden" name="it_b_pay" value="1d" />
  <input type="hidden" name="jm_paymethod" value="Alipay" />
  <input type="hidden" name="out_trade_no" value="s85292098-14367770395980" />
  <input type="hidden" name="paymethod" value="bankPay" />
  <input type="hidden" name="quantity" value="1" />
  <input type="hidden" name="sign_id_ext" value="85292098" />
  <input type="hidden" name="sign_name_ext" value="JM180HCHA0877" />
  <input type="hidden" name="subject" value="聚美优品 - 购物车编号 s85292098-14367770395980" />
  <input type="hidden" name="timestamp" value="1436777039" />
  <input type="hidden" name="total_fee" value="110.00" />
  <input type="hidden" name="user" value="JMCart" />
  <input type="hidden" name="sign" value="312899096477a1087297fd406ebf2c74" />
   --%>
</form>
          
<content tag="local_script">
<script type="text/javascript">
$(function(){
  $('#order-pay-link').click(function(){
	var form = document.getElementById('order-pay-form');
	form.submit();
	$('#lightbox,.light-box').show();
  });
  $('.formbutton').click(function(){
	  var _this = $(this);
	  var action = _this.attr('data-action');
	  window.location.href = '${ctx}/order/pay/status?orderId=${order.id}&action=' + action;
  });
  
  function getPayWay(node) {
      this.node = node;
      this.init();
  }

//   getPayWay.prototype.init = function () {
//       var node = this.node;
//       node.find('.tit').click(function() {
//           node.removeClass('getopen');
//           node.children("input").prop("checked", false);
//           $("input[name='pay']").prop("checked", false);
//           console.log($(this).parent());
//           $(this).parent().addClass("getopen");
//           $(this).prev('input').prop("checked",true);
//           console.log($(this).prev('input'));

//       });
//   };

//   var getPayWay = new getPayWay($('.getways'));
  
});
</script>
</content>
</body>
</html>