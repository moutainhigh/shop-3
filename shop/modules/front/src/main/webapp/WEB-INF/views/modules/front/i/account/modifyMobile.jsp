<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="decorator" content="account_default_new" />
<meta name="Keywords" content="">
	<meta name="Description" content="">
	<title>${accountTitle }</title>
	<content tag="local_script">
	<script type="text/javascript">
	$(function(){
		$('#confirmBtn').click(function(){
			var verifyCode = $('#verifyCode').val();
			var mobileVerify = $('#mobileVerify').val();
			if($.trim(mobileVerify).isEmpty() || !mobileVerify.isMobileVerify()) {
				alert('请输入校验码');
				return;
			}
			if($.trim(verifyCode).isEmpty()) {
				alert('请输入验证码');
				return;
			}
			$.ajax({
    		    type: "post",
    		    data: {
    		    	mobileVerify: mobileVerify,
    		    	verifyCode: verifyCode
            	},
    		    url: "${ctx}/i/account/mobile/modify/confirm",
            	beforeSend: function () {
    		        $(".loading").show();
    		    },
    		    success: function (data) {
    		    	log.info(data);
                    if(data.s != 0) {
                    	alert(data.m);
                    } else {
                    	$('#step1Div').hide();
                    	$('#step2Div').fadeIn();
                    }
    		    },
    		    complete: function () {
    		        $(".loading").hide();
    		    },
    		    error: function (data) {
    		        log.error("error: " + data.responseText);
    		    }
    		});
		});
		
		$('#rebindBtn').click(function(){
			var mobile = $('#mobile').val();
			var mobileVerify = $('#confirmMobileVerify').val();
			if($.trim(mobile).isEmpty() || !mobile.isMobile()) {
				alert('请输入正确的11位手机号码');
				return;
			}
			if($.trim(mobileVerify).isEmpty() || !mobileVerify.isMobileVerify()) {
				alert('请输入校验码');
				return;
			}
			$.ajax({
    		    type: "post",
    		    data: {
    		    	mobileVerify: mobileVerify,
    		    	mobile: mobile
            	},
    		    url: "${ctx}/i/account/mobile/modify/rebind",
            	beforeSend: function () {
    		        $(".loading").show();
    		    },
    		    success: function (data) {
    		    	log.info(data);
                    if(data.s != 0) {
                    	alert(data.m);
                    } else {
                    	$('#step2Div').hide();
                    	$('#step3Div').fadeIn();
                    }
    		    },
    		    complete: function () {
    		        $(".loading").hide();
    		    },
    		    error: function (data) {
    		        log.error("error: " + data.responseText);
    		    }
    		});
		});
		
	});
	var t;
	var wait = 60;
	function getMobileVerify(o, type, m) {
		var mobile = null;
		if(typeof m != 'undefined' && m) {
			mobile = $.trim(m.val());
			if(mobile.isEmpty() || !mobile.isMobile()) {
				alert('请输入正确的11位手机号码');
				return;
			}
			m.attr('disabled', 'disabled');
		}
		time(o);
        $.post("${ctx }/i/account/getMcode", {
        	mobile: mobile,
            type: type
        }, function (data) {
            log.info(data);
            if(data.s == 1) {
            	m.removeAttr('disabled');
            	alert(data.m);
            }
        });
	}
	
	function time(o) {
		var _this = $(o);
        if (wait == 0) {
            _this.attr('disabled', false);
            _this.val('获取验证码');
            wait = 60;
        } else {
            _this.attr('disabled', true);
            _this.val(wait + '秒后重新获取');
            wait--;
            t = setTimeout(function() {
                time(o);
            },
            1000);
        }
    }
	</script>
	</content>
</head>
<body style="background: white; width: 960px; margin: 0 auto;">
	<div class="sector" id="step1Div">
		<div class="sector_container">
			<div class="notice_content">绑定手机后，可用手机号直接登录。绑定手机是保护您的账户安全的重要手段。</div>
			<form id="subscribe_form" action="post" class="mobile_subscribe">
				<dl class="datalist">
					<dt style="margin-top: 7px;font-weight: 600;">已绑定手机号：</dt>
					<dd>${mobile } <input value="获取手机校验码" type="button" onclick="getMobileVerify(this, 4)">
					</dd>
				</dl>
				<div class="input_container">
					<label for="confirm_code">校验码：</label> <input class="default_value" id="mobileVerify" type="text">
				</div>
				<div class="input_container">
					<label for="verify_code">验证码：</label> <input class="default_value" id="verifyCode" type="text">
				</div>
				<div class="act">
					<div class="input_container">
						<img id="validateCode" src="${ctx }/servlet/validateCodeServlet" onclick="$(this).attr('src','${ctx}/servlet/validateCodeServlet?'+new Date().getTime());">
                        <a href="javascript:" onclick="$('#validateCode').attr('src','${ctx}/servlet/validateCodeServlet?'+new Date().getTime());">看不清</a>
					</div>
					<div class="input_container">
						<input value="提交验证" class="submit_subscribe" id="confirmBtn" type="button">
					</div>
				</div>
				<br> <br> <br> <br>
				<h4 style="color: black;">如果您的原手机号已丢失或停用怎么办？</h4>
				<p style="line-height: 25px; margin-top: 5px;">
					您可以提供以下资料，发送到我们的客服邮箱： <a href="#">kefu@163.com</a> <br>
					登录名，身份证图片，原绑定手机号，需要新绑定的手机号码。 <br>
					请保证上述信息均填写正确，身份证图片清晰，我们的客服专员会在收到您的邮件的24小时内为您处理。 
				</p>
			</form>
		</div>
	</div>

	<div class="sector" id="step2Div" style="display:none;">
		<div class="sector_container">
			<div class="notice_content">绑定手机后，可用手机号直接登录。绑定手机是保护您的账户安全的重要手段。</div>
			<form id="subscribe_form" action="post" class="mobile_subscribe">
				<dl class="datalist">
					<dt>已绑定手机号：</dt>
					<dd>${mobile }</dd>
				</dl>
				<div class="input_container">
					<label for="mobile">手机号：</label> 
					<input class="default_value" name="mobile" id="mobile" maxlength="11" type="text"> 
					<input value="获取手机校验码" class="get_confirm_code" type="button" onclick="getMobileVerify(this, 5, $('#mobile'))">
					<div class="act grey">请输入正确的11位手机号码</div>
				</div>
				<div class="input_container">
					<label for="confirm_code">校验码：</label> 
					<input class="default_value" id="confirmMobileVerify" maxlength="6" type="text">
				</div>
				<div class="act">
					<input value="提交绑定手机" class="submit_subscribe" id="rebindBtn" type="button">
				</div>
			</form>
		</div>
	</div>

	<div class="sector" id="step3Div" style="display:none;">
		<div class="status_box">
			<div class="status_title">
				<span class="success_icon">OK</span>
				<div class="success_text">
					<div style="margin: 15px 0 10px 0;">恭喜您成功绑定手机号：</div>
					<div class="bold"></div>
				</div>
			</div>
			<div class="clear"></div>
			<div class="status_tips">绑定手机后，可用手机号直接登录。绑定手机是保护您的账户安全的重要手段。</div>
			<div style="text-align: center; margin: 30px 0;">
				<a href="${ctx }" class="back_to_home">返回首页</a>
			</div>
		</div>
	</div>

</body>
</html>