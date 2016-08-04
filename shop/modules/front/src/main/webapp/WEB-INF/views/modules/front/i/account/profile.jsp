<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="decorator" content="account_default_new"/>
    <title>${accountTitle }</title>
    <link href="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/css/select2.min.css" rel="stylesheet" />
    <content tag="local_script">
	<script src="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/js/select2.min.js"></script>
    <script type="text/javascript" src="${ctxStaticJS }/js/YMDClass.min.js"></script>
    <script type="text/javascript">
    $(document).ready(function(){
    	new YMDselect('birthday_year', 'birthday_month', 'birthday_day', 1990, 2, 10);
    	var birthdayYear = $('#birthdayYear').val();
    	var birthdayMonth = $('#birthdayMonth').val();
    	var birthdayDay = $('#birthdayDay').val();
    	log.info('birthdayYear = ' + birthdayYear + ', birthdayMonth = ' + birthdayMonth + ', birthdayDay = ' + birthdayDay);
    	$('#birthday_year').val(birthdayYear);
    	$('#birthday_month').val(parseInt(birthdayMonth));
    	$('#birthday_day').val(parseInt(birthdayDay));
    	$('select').select2();
    	$('input[name="gender"][value="${account.sex}"]').prop('checked', true);
    	var usernameExist = true;
    	$('#username').focus(function(){
    		$('.input_container .customError').css('display', 'none');
    	}).blur(function(){
    		var _this = $(this);
    		var username = _this.val();
    		var len = username.length;
    		if(/^(?!^\d)[\w\u4e00-\u9fa5\uF900-\uFA2D]+$/.test(username) && len >= 4 && len <= 16 ) {
    			$('.input_container .customError').css('display', 'none');
    			
    			$.post('${ctx}/i/account/profile/checkUsername', {
    				username: username
    			}, function(data){
    				if(data.s == '1') {
    					usernameExist = true;
    					$('.input_container .customError').html(data.m);
    					$('.input_container .customError').css('display', 'inline-block');
    				} else {
    					usernameExist = false;
    				}
    			});
    			
    		} else {
    			$('.input_container .customError').html('应为4-16个中英文字符，不能以数字开头');
    			$('.input_container .customError').css('display', 'inline-block');
    		}
    	});
    	
    	$('#submitBtn').click(function(){
    		var _u = $('#username');
    		var _y = $('#birthday_year'), _m = $('#birthday_month'), _d = $('#birthday_day');
    		var gender = $('input[name="gender"]:checked').val();
    		var username = _u.val();
    		var year = _y.val(), month = _m.val(), day = _d.val();
    		var b = true;
    		var len = username.length;
    		log.info('gender=' + gender + ',username=' + username + ',year=' + year + ',month=' + month + ',day=' + day);
    		if(usernameExist) {
    			// $('.input_container .customError').css('display', 'inline-block');
    			b = false;
    		}
    		if(/^(?!^\d)[\w\u4e00-\u9fa5\uF900-\uFA2D]+$/.test(username) && len >= 4 && len <= 16 ) {
    			$('.input_container .customError').css('display', 'none');
    		} else {
    			$('.input_container .customError').css('display', 'inline-block');
    			b = false;
    		}
    		if(!year || !month || !day) {
    			$('.input_container .otherError').css('display', 'inline-block');
    			b = false;
    		} else {
    			$('.input_container .otherError').css('display', 'none');
    		}
    		if(b) {
    			$.ajax({
        		    type: "post",
        		    data: {
        		    	username: username,
        		    	gender: gender,
        		    	birthday_year: year,
        		    	birthday_month: month,
        		    	birthday_day: day
                	},
        		    url: "${ctx}/i/account/profile",
                	beforeSend: function () {
        		        $(".loading").show();
        		    },
        		    success: function (data) {
        		    	log.info(data);
                        if(data.s != 0) {
                        	for(var i = 0; i < data.ms.length; i++) {
                        		var m = data.ms[i];
                        		log.info(m);
                        		for(var k in m) {
                        			log.info(k + ',' + m[k]);
                        			if(k == 1) {
                        				$('.input_container .customError').css('display', 'inline-block');
                        			} else if(k == 2) { 
                        				$('.input_container .otherError').css('display', 'inline-block');
                        			}
                        		}
                        	}
                        } else {
                        	window.location.href = '${ctx}/i/account/profile';
                        }
        		    },
        		    complete: function () {
        		        $(".loading").hide();
        		    },
        		    error: function (data) {
        		        log.error("error: " + data.responseText);
        		    }
        		});
    		}
    	});
    });
    </script>
    </content>
</head>
<body style="background:white; width:960px;margin:0 auto;">
	<form id="settings-form" method="post" action="${ctx }/i/account/profile">
		<div class="avatar_change">
			<c:if test="${account.picture != null and not empty account.picture }">
			<img src="${IMAGE_ROOT_PATH }/${account.picture }" alt="大头像"/>
			</c:if>
			<c:if test="${account.picture == null or empty account.picture }">
			<img src="${ctxStatic }/images/avatar.png" alt="大头像"/>
			</c:if>
			<a href="${ctx }/i/account/modifyPic" class="changeavatar">修改头像</a>
		</div>
		<div class="input_container">
			<label for="username"><span class="spark">*</span>用户名</label>
			<input required="" size="30" name="username" id="username" class="t_input" value="${account.account }" type="text"/>
			<span class="valueMissing">请填写您的用户名</span>
			<span style="display: none;" class="customError">应为4-16个中英文字符，不能以数字开头</span>
		</div>
		<dl class="formlist">
			<dt style="margin-top:10px;">手机号</dt>
			<dd>
            	<span class="data">${mobile }</span>
				<a href="${ctx }/i/account/mobile/modify" target="_target">修改</a>
				<span class="hint" style="margin-left:22px;">已验证</span>
            </dd>
		</dl>
		<div class="input_container">
			<label>性别</label> 
			<label class="radios" style="margin-top:0;margin-bottom:0;">
				<span class="radio_ui">
					<input name="gender" id="gender_f" autocomplete="off" value="f" type="radio"/>
					<b></b>
				</span>
				女
			</label>
			<label class="radios" style="margin-top:0;margin-bottom:0;">
				<span class="radio_ui">
					<input name="gender" id="gender_m" autocomplete="off" value="m" type="radio"/>
					<b></b>
				</span>
				男
			</label>
			<label class="radios" style="margin-top:0;margin-bottom:0;">
				<span class="radio_ui">
					<input name="gender" id="gender_s" autocomplete="off" value="s" type="radio"/>
					<b></b>
				</span>
				保密
			</label>
		</div>
		<div class="input_container">
			<label><span class="spark">*</span>生日</label>
			<input type="hidden" id="birthdayYear" name="birthdayYear" value="${birthdayYear }"/>
			<input type="hidden" id="birthdayMonth" name="birthdayMonth" value="${birthdayMonth }"/>
			<input type="hidden" id="birthdayDay" name="birthdayDay" value="${birthdayDay }"/>
            <!-- <select id="birthday_year" name="birthday_year" class="btn-select"></select>
            <select id="birthday_month" name="birthday_month" class="btn-select"></select>
            <select id="birthday_day" name="birthday_day" class="btn-select"></select> -->
            <select id="birthday_year" name="birthday_year"></select>
            <select id="birthday_month" name="birthday_month"></select>
            <select id="birthday_day" name="birthday_day"></select>
			<span style="display: none;" class="otherError">请填写您的生日</span>
			<div class="act">
				<span class="status_red" style="color:#E50055">准确填写生日，有机会获得更多惊喜哦！</span>
			</div>
		</div>
		<div class="act">
			<input value="保存修改" name="commit" id="submitBtn" type="button" style="background:#E50055">
		</div>
	</form>
</body>
</html>