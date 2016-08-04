<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="decorator" content="account_default_new"/>
    <meta name="Keywords" content="">
    <meta name="Description" content="">
    <title>${accountTitle }</title>
</head>
<body style="background:white; width:960px;margin:0 auto;">

<div class="sector">
    <p style="padding:15px 30px;border-bottom: 1px solid #ccc;margin: 0px;">绑定手机是保护您的账户和资金安全的重要手段。绑定的手机号，会用于对余额支付订单进行安全校验。</p>
    <div class="status_box">
        <div class="status_title">
            <span class="success_icon">OK</span>
            <div class="success_text">
                <div>
                    您已成功绑定手机:
                    <span style="font-size: 18px;font-weight: bold;">${mobile }</span>
                    <a style="margin:4px 105px 0px 0px;float: right;font-size: 12px; color: #ed145b;" href="${ctx }/i/account/mobile/modify">更换绑定手机»</a>
                    <br>提示：如果您更换了绑定手机，登录时需要使用新绑定的手机号进行登录 如果您之前进行过手机短信订阅，绑定手机号将默认为您订阅时的手机号
                </div>
            </div>
        </div>
        <div class="clear"></div>
    </div>
</div>

</body>
</html>