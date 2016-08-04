<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/mobile/include/taglib.jsp"%>
<!doctype html> 
<html> 
  <head> 
    <meta charset="utf-8"> 
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <title>我的订单</title>
    <%@include file="/WEB-INF/views/mobile/include/head.jsp" %>
    <link rel="stylesheet" type="text/css" href="${ctxMS }/css/phone-order.css">
  </head>
  <body>
    <div class="am-g">
        <div class="am-u-sm-12 header">
            <div class="header-left am-fl am-vertical-align">
                <p class="am-vertical-align-middle">成都</p>
                <img src="${ctxMS }/imgs/down.png" class="am-vertical-align-middle">
            </div>
            <p class="title">提交订单</p>
            <div class="header-right am-fr am-vertical-align">
            </div>
        </div>
        <div class="am-u-sm-12 address">
            <div class="step1 border-1px">
                <div class="m step1-in ">
                    <a href="" class="s-href">
                        <div class="mt_new">                      
                            <div class="s1-name">
                                <i></i><span>${defaultAddress.name }</span>
                            </div>
                            <div class="s1-phone">
                                <i></i>${defaultAddress.mobile }
                            </div>
                        </div>
                        <div class="mc step1-in-con">
                            ${defaultAddress.provinceName }${defaultAddress.cityName }${defaultAddress.countyName }${defaultAddress.address }
                        </div>
                    </a>
                </div>
                <b class="s1-borderT"></b>
                <b class="s1-borderB"></b>
                <span class="s-point"></span>
            </div>
        </div>
        <c:set value="0" var="cartNum" />  
        <c:forEach items="${carts }" var="item">
        	<c:set value="${cartNum + item.value.size()}" var="index" />  
        </c:forEach>
        <c:if test="${cartNum == 1 }">
        	 <c:forEach items="${carts }" var="item">
        	 	<c:forEach items="${item.value }" var="cart">
		        	<div class="am-u-sm-12 pro-list">
			            <div class="step3 border-1px">
			                <a href="" class="s-href">
			                    <div class="s-item">
			                        <div class="sitem-l">
			                            <div class="sl-img">
			                                <img src="${IMAGE_ROOT_PAHT }${cart.productImage}">
			                            </div>
			                        </div>
			                        <div class="sitem-m">
			                            <p class="sitem-m-txt"><span>小米</span><span> 红米2A 白色 移动4G手机 双卡双待</span></p>
			                            <p>×1</p>
			                        </div>
			                        <div class="sitem-r">
			                            ￥515.00
			                        </div>
			                        <span class="s-point"></span>
			                    </div>
			                </a>
			            </div>
			        </div>
			   </c:forEach>
			</c:forEach>
        </c:if>
        <c:if test="${cartNum > 1 }">
	        <div class="am-u-sm-12 pro-list">
	            <div class="step3 border-1px step3-more">
	                <a href=""
	                class="s-href">
	                    <div class="s-item">
	                        <div class="sitem-l">
	                            <div class="sl-img">
	                                <img src="http://img10.360buyimg.com/n4/jfs/t877/62/834628938/131692/10a3953f/554c7629Nc668fc70.jpg">
	                            </div>
	                            <div class="sl-img">
	                                <img src="http://img10.360buyimg.com/n4/jfs/t928/55/723610254/137608/ce26a8e2/553f54e0N32edba9c.jpg">
	                            </div>
	                            <div class="sl-img">
	                                <img src="http://img10.360buyimg.com/n4/jfs/t1993/155/67386345/109533/b35cf716/55ec0c26Nfb910787.jpg">
	                            </div>
	                            <div class="sl-img">
	                                <img src="http://img10.360buyimg.com/n4/jfs/t931/223/717807696/137608/ce26a8e2/553f54efN37156a86.jpg">
	                            </div>
	                            <div class="sl-img">
	                                <img src="http://img10.360buyimg.com/n4/jfs/t1297/36/300283399/70352/49466a15/556539c6N570b3bc9.jpg">
	                            </div>
	                        </div>
	                        <div class="sitem-m">
	                        </div>
	                        <div class="sitem-r">
	                            共6件
	                        </div>
	                        <span class="s-point">
	                        </span>
	                    </div>
	                </a>
	            </div>
	        </div>
	    </c:if>
        <div class="am-u-sm-12 pay-type">
            <ul class="am-list am-list-static">
              <li class="type-title">支付方式</li>
<!--               <li class="alipay am-cf"> -->
<!--                   <img src="${ctxMS }/imgs/alipay.png" class="logo am-fl"> -->
<!--                   <div class="am-fl"> -->
<!--                       <p>支付宝支付</p> -->
<!--                       <p>推荐支付宝用户使用</p> -->
<!--                   </div> -->
<!--                   <label class="am-checkbox"> -->
<!--                       <input type="checkbox" value="" data-am-ucheck> -->
<!--                   </label> -->
<!--               </li> -->
              <li class="wxpay am-cf">
                  <img src="${ctxMS }/imgs/wxpay.png" class="logo am-fl">
                  <div class="am-fl">
                      <p>微信支付</p>
                  </div>
                  <label class="am-checkbox">
                      <input type="checkbox" checked="checked" data-am-ucheck>
                  </label>
              </li>
            </ul>
        </div>
        <div class="am-u-sm-12 message">
            <div class="message-title">
                留言
            </div>
            <div class="message-textarea"><textarea class="" id="doc-ta-1"></textarea></div>

        </div>
        <div class="am-u-sm-12 price">
            <div class="pro-price am-cf">
                <p>商品金额：</p>
                <p>￥${totalPrice }</p>
            </div>
             <div class="pro-costs am-cf">
                <p>会员折扣：</p>
                <p>￥${totalDiscount }</p>
            </div>
            <div class="pro-costs am-cf">
                <p>商品运费：</p>
                <p>￥${totalFreight }</p>
            </div>
        </div>
        <div class="am-u-sm-12 footer">
            <div class="price-total am-cf">
                <p class="am-fl">合计：</p>
                <span class="am-fl">￥${totalPay }</span>
            </div>
            <div class="go-to-settlement">
                提交订单
            </div>
        </div>
    </div>
    <input type="hidden" id="ctx" name="ctx" value="${ctx }"/>
    <input type="hidden" id="ids" name="ids" value="${ids }"/>
    <script type="text/javascript" src="${ctxMS }/js/phone-order.js"></script>
  </body>
</html>
