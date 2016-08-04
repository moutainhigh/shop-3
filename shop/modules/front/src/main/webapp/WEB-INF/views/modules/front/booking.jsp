<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!doctype html> 
<html>
<head>
  <meta name="decorator" content="default_new"/>
  <meta name="Keywords" content=""/>
  <meta name="Description" content=""/>
  <link href="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/css/select2.min.css" rel="stylesheet" />
  <link href="${ctxAssets }/stylesheets/amazeui.datetimepicker.css" rel="stylesheet">
  <link href="${ctxAssets }/stylesheets/yuyue1.css" rel="stylesheet" type="text/css">
  <title>预约</title>
</head>
<body>
<div class="container-n">
	<div class="appointment-top-div-1">
		<div class="appointment-top-div-2">
			<div class="appointment-top clearfix">
	        <div class="heart">
	          <img src="${ctxAssets }/image/heart.png">
	        </div>
	        <div class="open-tips">
	          <p class="tips-p-1">尊敬的用户您好：<span> 如果您所在的城市不在上述城市中，您可以联系我们，或<a href="${ctx }/mall">直接购物</a>、<a href="${ctx }/regist">注册会员</a>享受更多优惠和服务！</span></p>
	          
	          <div class="open-city clearfix">
	            <p class="tips-p-2">目前已经开通预约服务的城市：</p><c:forEach items="${baList }" var="ba"><div data-key="${ba.cityId }" class="city-name <c:if test="${city != null and ba.cityId eq city.id }">city-select</c:if>">${ba.cityName }</div></c:forEach>
	          </div>
	          <!-- <p class="tips-p-2">
	           
	          </p> -->
	          <%-- <p class="tips-p-2">
	            <a href="${ctx }/regist">注册会员</a>享受更多优惠和服务！
	          </p> --%>
	          <p class="tips-p-2">
	            首次预约系统会自动为您注册会员！
	          </p>
	        </div>
	      </div>
		</div>
	</div>
	
  <div class="appointment-n">
    <div class="appointment-main">
      <%-- <div class="appointment-top clearfix">
        <div class="heart">
          <img src="${ctxAssets }/image/heart.png">
        </div>
        <div class="open-tips">
          <p class="tips-p-1">尊敬的用户您好：</p>
          <p class="tips-p-2">目前已经开通预约服务的城市：</p>
          <div class="open-city clearfix">
            <c:forEach items="${baList }" var="ba"><div data-key="${ba.cityId }" class="city-name <c:if test="${city != null and ba.cityId eq city.id }">city-select</c:if>">${ba.cityName }</div></c:forEach>
          </div>
          <p class="tips-p-2">
            如果您所在的城市不在上述城市中，您可以联系我们，或<a href="${ctx }/mall">直接购物</a>
          </p>
          <p class="tips-p-2">
            <a href="${ctx }/regist">注册会员</a>享受更多优惠和服务！
          </p>
          <p class="tips-p-2">
            首次预约系统会自动为您注册会员！
          </p>
        </div>
      </div> --%>
      
      <form id="bookingForm" action="${ctx }/booking" method="post" autocomplete="off">
      <input type="hidden" id="message" name="message" value="${message }">
      <div class="appointment-down">
        <%-- <div class="login-tips clearfix">
          <shiro:guest><p class="login-line f-l">如果您已拥有账号，则可<a href="${ctx }/login">在此登录</a></p></shiro:guest>
          <p class="must-option f-r"><em>*</em>必填项</p>
        </div> --%>
        <div class="name-option clearfix">
          <p class="option-p"><em>*</em>姓名</p>
          <div class="option-input">
            <input id="name" name="name" placeholder="姓名（必填）" value="${account.trueName }">
            <div class="info"><img src="${ctxAssets }/image/warning.png" class="warning-img"><p class="tips-p" style="display: none;">请输入你的姓名</p></div>
          </div>
        </div>
        <div class="phone-option clearfix">
          <p class="option-p"><em>*</em>手机号码</p>
          <div class="option-input">
            <input id="mobile" name="mobile" placeholder="手机号（必填）" value="${account.mobile }">
            <div class="info"><img src="${ctxAssets }/image/warning.png" class="warning-img"><p class="tips-p" style="display: none;">请输入11位手机号</p></div>
          </div>
        </div>
        <div class="verification-code-option clearfix">
          <p class="option-p"><em>*</em>验证码</p>
          <div class="option-input">
            <a class="change-code" href="javascript:" onclick="isVerifyCode=false;isSend=false$('#validateCode').attr('src','${ctx}/servlet/validateCodeServlet?'+new Date().getTime());">换一张</a>
            <img class="verification-code-img" id="validateCode" src="${ctx }/servlet/validateCodeServlet" onclick="isVerifyCode=false;isSend=false;$(this).attr('src','${ctx}/servlet/validateCodeServlet?'+new Date().getTime());">
            <input class="verification-input" id="verifyCode" name="verifyCode" placeholder="验证码（必填）">
            <div class="info"><img src="${ctxAssets }/image/warning.png" class="warning-img"><p class="tips-p" style="display: none;">按右图填写，不区分大小写</p></div>
          </div>
        </div>
        <div class="message-option clearfix">
          <p class="option-p"><em>*</em>短信验证码</p>
          <div class="option-input">
            <button class="btn btn-default submit-message" type="button" id="mobileVerifyBtn">获取验证码</button>
            <input class="message-input" id="mobileVerify" name="mobileVerify" placeholder="短信校验码（必填）">
            <div class="info"><img src="${ctxAssets }/image/warning.png" class="warning-img"><p class="tips-p" style="display: none;">请输入6位短信验证码</p></div>
          </div>
        </div>
        <div class="appointment-time-option clearfix">
          <p class="option-p"><em>*</em>预约时间</p>
          <div class="option-input">
            <input readonly id="time" name="time">
            <div class="info"><img src="${ctxAssets }/image/warning.png" class="warning-img"><p class="tips-p"  style="display: none;">请选择预约时间</p></div>
            <img src="${ctxAssets }/image/date-change.png" class="date-change-img">
          </div>
        </div>
        <div class="address-option clearfix">
          <p class="option-p"><em>*</em>详细地址</p>
          <div class="option-input xian">
            <div class="dropdown">
              <select id="countyId" name="countyId" style="width:100%;">
              	<option value="">区/县</option>
              	<c:forEach items="${counties }" var="p"><option value="${p.id }">${p.name }</option></c:forEach>
              </select>
            </div>
          </div>
          <div class="option-input shi">
            <div class="dropdown">
              <select id="cityId" name="cityId" style="width:100%;">
              	<option value="">市</option>
              	<c:forEach items="${cities }" var="p"><option value="${p.id }">${p.name }</option></c:forEach>
              </select>
            </div>
          </div>
          <div class="option-input sheng">
            <div class="dropdown">
              <select id="provinceId" name="provinceId" style="width:100%;">
			    <option value="">省/直辖市</option>
				<c:forEach items="${provinces }" var="p"><option value="${p.id }">${p.name }</option></c:forEach>
			  </select>
            </div>
          </div>
          <div id="mapContainer"></div>
          <div class="option-input address-search" id="tip">
            <input id="address" name="address" placeholder="请输入详细地址" value="${province.name }${city.name}">
            <div class="info"><img src="${ctxAssets }/image/warning.png" class="warning-img"><p class="tips-p" style="display: none;">请填写详细地址</p></div>
            <div id="result1" name="result1"></div>
            <img src="${ctxAssets }/image/date-change.png" class="date-change-img">
          </div>
        </div>
        <div class="demand-option clearfix">
          <p class="option-p">详细需求</p>
          <div class="option-input">
            <!-- <input readonly> --> 
            <textarea id="demand" name="demand" rows="4" cols="57" placeholder="详细需求" readonly></textarea>
            <div class="info"><p class="tips-p" style="display: none;">请填写详细需求</p></div>
          </div>
          <div class="demand-popup">
            
          </div>
          <div class="demand-popup-main">
          <img src="${ctxAssets }/image/request-close.png" class="request-close">
            <ul class="demand-request">
              <li>
                <img src="${ctxAssets }/image/check-img-no.png">
                <P>您好，我们对贵公司的产品非常感兴趣，但由于采购品种繁多，希望安排一名专业的业务员协助我们采购！谢谢！</P>
              </li>
              <li>
                <img src="${ctxAssets }/image/check-img-no.png">
                <P>您好，我们是贵公司的忠实客户，上次采购非常顺利，服务也非常到位，希望能再次安排业务员协助我们采购！谢谢！</P>
              </li>
              <li>
                <img src="${ctxAssets }/image/check-img-no.png">
                <textarea class="form-control request-text" rows="3" placeholder="请输入详细需求"></textarea>
              </li>
              <li class="clearfix">
                <div class="request-submit">选择</div>
              </li>
            </ul>
          </div>
        </div>
        <div class="submit-appointment clearfix">
          <button class="btn option-input" id="btnSubmit" type="button">预约</button>
        </div>
      </div>
 	  <input type="hidden" id="ctx" name="ctx" value="${ctx }">
	  <input type="hidden" id="province" name="province" value="${province.id }">
	  <input type="hidden" id="city" name="city" value="${city.id }">
	  <input type="hidden" id="provinceName" name="provinceName" value="${province.name }">
	  <input type="hidden" id="cityName" name="cityName" value="${city.name }">
	  <input type="hidden" id="countyName" name="countyName" value="">
	  <input type="hidden" id="location" name="location" value="">
	  <input type="hidden" id="errCode" name="errCode" value="${message }">
      </form>
    </div>
  </div>
</div>

<content tag="local_script">
<!-- <script src="http://webapi.amap.com/maps?v=1.3&key=4d7ab0f292a42a66274288758f46ba89" type="text/javascript"></script> -->
<script src="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/js/select2.min.js"></script>
<script src="${ctxAssetsJS }/javascripts/amazeui.datetimepicker.min.js" charset="UTF-8"></script>
<%-- <script src="${ctxAssetsJS }/javascripts/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script> --%>
<script src="http://webapi.amap.com/maps?v=1.3&key=095a06c099f777e63fa12f14c9d48578" type="text/javascript"></script>
<script src="${ctxAssetsJS }/javascripts/common.js" charset="UTF-8"></script>
<script src="${ctxAssetsJS }/javascripts/alert.js" charset="UTF-8"></script>
<script src="${ctxAssetsJS }/javascripts/yuyue.js" charset="UTF-8"></script>
</content>
</body>
</html>
