<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>找回密码 - 报喜了</title>
    <link rel="stylesheet" href="${ctxStatic }/css/common.css" type="text/css" media="screen" charset="utf-8">
    <link rel="stylesheet" href="${ctxStatic }/css/all_sign.css" type="text/css" media="screen" charset="utf-8">
    <link rel="stylesheet" href="${ctxStatic }/css/forget.css" type="text/css" media="screen" charset="utf-8">
    <link rel="stylesheet" href="${ctxStatic }/css/style.css" type="text/css" media="screen" charset="utf-8">
    <style type="text/css">
        .login-ul li {
            float: left;
            margin-right: 30px;
            margin-bottom: 10px;
        }

        .login-ul li input {
            vertical-align: middle;
            margin-top: -2px;
            margin-bottom: 1px;
        }

        .auto_login {
            margin-bottom: 10px;
        }
    </style>
    <script type="text/javascript" src="${ctxStaticJS }/js/jquery-1.9.1.min.js"></script>
	<script type="text/javascript" src="${ctxStaticJS }/js/common.js"></script>
    <script type="text/javascript" src="${ctxStaticJS }/js/forget.js"></script>
</head>
<body style="background:white; width:960px;margin:0 auto;">
<div id="logo">
    <a style="background:url(${ctxAssets }/image/logo.png) no-repeat;" title="" id="home" href="${ctx }"></a>

    <div class="header_logo_box">
        <a target="_blank" class="top_link lightning" rel="nofollow" href=""></a>
        <a target="_blank" class="top_link gild" rel="nofollow" href=""></a>
        <a target="_blank" class="top_link credit" rel="nofollow" href=""></a>
    </div>
</div>

<div class="container">
    <div id="firstContainer" class="sign">
        <div class="sign_main resetreq_main">
            <div class="main">
                <div class="content_head">
                    <h1>找回密码</h1>
                    <ul class="forget_step">
                        <li class="step_item done first"><a class="step_number number_1"></a></li>
                        <li class="step_item"><span class="step_line"></span><a class="step_number number_2"></a></li>
                        <li class="step_item"><span class="step_line"></span><a class="step_number number_3"></a></li>
                        <li class="step_item"><span class="step_line"></span><a class="step_number number_4"></a></li>
                    </ul>
                    <div class="clear">
                    </div>
                    <ul class="step_text">
                        <li class="text_1 done">确认账号</li>
                        <li class="text_2">验证身份</li>
                        <li class="text_3">设置密码</li>
                        <li class="text_4">完成</li>
                    </ul>
                </div>
                <div class="ret_wrapper forget_content step_1">
                    <form id="resetreq-user-form" method="post" class="resetreq-user-form">
                        <div class="line tip">
                            <label></label>

                            <div class="textbox_ui">
                                请输入您的登录名，您的登录名可能是您的手机号、邮箱或用户名
                            </div>
                        </div>
                        <div class="line hastip">
                            <label>手机号</label>
                            <div class="textbox_ui">
                                <input placeholder="手机号" id="mobile" name="mobile" class="" type="text">
                                <div class="invalid" style="display:none;"><i></i>
                                    <div class="">手机号不能为空</div>
                                </div>
                            </div>
                        </div>
                        <div class="line verityWrap">
                            <label>验证码</label>
                            <div class="textbox_ui ">
                                <input placeholder="请输入验证码" id="verifyCode" name="verifyCode" class="" type="text">
                                <div class="focus_text">
                                    按右图填写，不区分大小写
                                </div>
                                <div class="invalid" style="display:none;"><i></i>
                                    <div class="">请输入验证码</div>
                                </div>
                            </div>
                            
                            <a href="javascript:" onclick="$(this).find('img').attr('src','${ctx}/servlet/validateCodeServlet?'+new Date().getTime());"><img src="${ctx }/servlet/validateCodeServlet" ><span></span><span> 换一张 </span></a>
                        </div>
                        <div class="act">
                            <input id="firstBtn" value="下一步" class="retbtn forget_btn" type="button">
                        </div>
                        <div class="line msg">
                            <label></label>

                            <div class="textbox_ui">
                                <span>如果您忘记了登录名，将无法找回您的账户信息，您还可以</span><a href="${ctx }/regist">重新注册</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="shadow_l">
            </div>
            <div class="shadow_r">
            </div>
        </div>
    </div>
</div>
<!--第一步-->

<div id="secondContainer" class="sign" style="display: none;width:100%">
    <div class="sign_main resetreq_main">
        <div class="main">
            <div class="content_head">
                <h1>找回密码</h1>
                <ul class="forget_step">
                    <li class="step_item done first"><a class="step_number number_1"></a></li>
                    <li class="step_item done"><span class="step_line"></span><a class="step_number number_2"></a></li>
                    <li class="step_item"><span class="step_line"></span><a class="step_number number_3"></a></li>
                    <li class="step_item"><span class="step_line"></span><a class="step_number number_4"></a></li>
                </ul>
                <div class="clear">
                </div>
                <ul class="step_text">
                    <li class="text_1 done">确认账号</li>
                    <li class="text_2 done">验证身份</li>
                    <li class="text_3">设置密码</li>
                    <li class="text_4">完成</li>
                </ul>
            </div>
            <div class="ret_wrapper forget_content step_2">
                <form id="resetreq-user-form" method="post" class="resetreq-user-form">
                    <div class="phone_content">
                        <div class="line">
                            <label>您的手机号码：</label>
                            <div class="textbox_ui">
                                <p id="mobileLabel">186****0000</p>
                            </div>
                        </div>
                        <div class="line line_get_verify_code">
                            <label>短信校验码：</label>
                            <div class="textbox_ui">
                                <input placeholder="请输入6位短信校验码" id="mobileVerify" name="mobileVerify" class="phone_verify" type="text">
                                <div class="focus_text">请输入6位短信校验码</div>
                                <div class="invalid" style="display:none;"><i></i>
                                    <div class=""></div>
                                </div>
                            </div>
                            <!-- <a class="retbtn verify_btn">免费获取短信校验码</a> -->
                            <input type="button" id="mobileVerifyBtn" class="retbtn verify_btn" value="免费获取短信校验码">
                        </div>
                        <noscript>
                        </noscript>
                        <div class="act clearfix">
                            <a class="fl retbtn forget_prep_btn" style="cursor:pointer;" href="javascript:void(0);">上一步</a>
                            <input id="secondBtn" value="下一步" class="fl retbtn forget_btn" type="button">
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <div class="help_message">
            <span>如有问题请随时拨打</span><span>400-6883-520</span>
        </div>
        <div class="shadow_l">
        </div>
        <div class="shadow_r">
        </div>
    </div>
</div>
<!--第二步-->

<div id="thirdContainer" class="sign" style="display: none;width:100%">
    <div class="sign_main resetreq_main">
        <div class="main">
            <div class="content_head">
                <h1>找回密码</h1>
                <ul class="forget_step">
                    <li class="step_item done first"><a class="step_number number_1"></a></li>
                    <li class="step_item done"><span class="step_line"></span><a class="step_number number_2"></a></li>
                    <li class="step_item done"><span class="step_line"></span><a class="step_number number_3"></a></li>
                    <li class="step_item"><span class="step_line"></span><a class="step_number number_4"></a></li>
                </ul>
                <div class="clear">
                </div>
                <ul class="step_text">
                    <li class="text_1 done">确认账号</li>
                    <li class="text_2 done">验证身份</li>
                    <li class="text_3 done">设置密码</li>
                    <li class="text_4">完成</li>
                </ul>
            </div>
            <div class="ret_wrapper forget_content step_3">
                <form id="resetreq-user-form" method="post" class="resetreq-user-form" action="/Reset/setPass">
                    <div class="line single">
                        <label>新登录密码</label>
                        <div class="textbox_ui">
                            <input placeholder="请输入新登录密码" id="password" name="password" class="" type="password">
                            <div class="focus_text">
                                <div>
                                    6-16 个字符，需使用字母、数字或符号组合，不能使用纯数字、纯字母、纯符号
                                </div>
                            </div>
                            <div class="invalid" style="display:none;"><i></i>
                                <div class="">为了您的账号安全，密码长度只能在6-16个字符之间</div>
                            </div>
                        </div>
                    </div>
                    <div class="line">
                        <label>确认新登录密码</label>
                        <div class="textbox_ui">
                            <input placeholder="请再次输入新登录密码" id="confirmPwd" name="password_confirm" class="" type="password">
                            <div class="focus_text">
                                请再次输入密码
                            </div>
                            <div class="invalid" style="display:none;"><i></i>
                                <div class="">两次密码输入不一致，请重新输入</div>
                            </div>
                        </div>
                    </div>
                    <div class="act">
                        <input id="thirdBtn" value="提交" class="retbtn forget_btn" type="button">
                    </div>
                </form>
            </div>
        </div>
        <div class="help_message">
            <span>如有问题请随时拨打</span><span>400-6883-520</span>
        </div>
        <div class="shadow_l">
        </div>
        <div class="shadow_r">
        </div>
    </div>
</div>
<!--第三步-->
<div id="fourthContainer" class="sign" style="display: none;width:100%">
    <div class="sign_main resetreq_main">
        <div class="main">
            <div class="content_head">
                <h1>找回密码</h1>
                <ul class="forget_step">
                    <li class="step_item done first"><a class="step_number number_1"></a></li>
                    <li class="step_item done"><span class="step_line"></span><a class="step_number number_2"></a></li>
                    <li class="step_item done"><span class="step_line"></span><a class="step_number number_3"></a></li>
                    <li class="step_item done"><span class="step_line"></span><a class="step_number number_4"></a></li>
                </ul>
                <div class="clear">
                </div>
                <ul class="step_text">
                    <li class="text_1 done">确认账号</li>
                    <li class="text_2 done">验证身份</li>
                    <li class="text_3 done">设置密码</li>
                    <li class="text_4 done">完成</li>
                </ul>
            </div>
            <div class="ret_wrapper forget_content step_4">
                <div class="line">
                    <div class="forget_success_ico"></div>
                    <div class="forget_success_text">
                        <p>您的新登录密码已设置成功。请记住它哟！</p>
                        <p>点击此处，立即返回之前的网页</p>
                        <p id="t">3秒后自动返回</p>
                    </div>
                </div>
            </div>
        </div>
        <div class="help_message">
            <span>如有问题请随时拨打</span><span>400-6883-520</span>
        </div>
        <div class="shadow_l">
        </div>
        <div class="shadow_r">
        </div>
    </div>
</div>
<!--完成-->
<input id="ctx" type="hidden" value="${ctx }">
<div class="clear"></div>
<div id="footerArea" style="background:white;">
    <div class="copyRight">Copyright 2015-2015 成都雷立风行电子商务有限公司  保留一切权利。蜀ICP备15020915号 客服热线：400-6883-520</div>
</div>

</body>
</html>