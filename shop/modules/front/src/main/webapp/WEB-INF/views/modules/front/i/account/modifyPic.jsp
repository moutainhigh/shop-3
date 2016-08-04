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
    <link type="text/css" rel="stylesheet" href="${ctxStaticJS }/js/uploadify/uploadify.css" />
    <content tag="local_script">
    <script type="text/javascript" src="${ctxStaticJS }/js/uploadify/jquery.uploadify.min.js"></script>
    <script>
    $(document).ready(function(){
    	setTimeout(function(){
			$('#uploadPicture').uploadify({
				'buttonText' : '更新头像',
				'formData' : {
					id: '${id}',
					mobile: '${mobile}',
					sign: '${sign}'
				},
				'swf' : '${ctxStaticJS }/js/uploadify/uploadify.swf',
				'uploader' : '${ctx}/i/account/upload;jsessionid=<%=session.getId()%>',
				'onUploadSuccess':function(file, data, response){
		            data = eval('(' + data + ')');
		            if (data.s == 0) {
		            	//$('#picture').val(data.msg);
		            	$('#picture').attr('src','${IMAGE_ROOT_PATH}/'+ data.m);
		            	$('#small_picture').attr('src', '${IMAGE_ROOT_PATH}/' + data.m);
		            } else { 
		            	alert('上传图片失败！');
		            }
		        }
			});
		},10);
    	
    });
    </script>
    </content>
</head>
<body style="background:white; width:960px;margin:0 auto;">

<div class="content avatar">
	<p class="view">
		<c:if test="${account.picture != null and not empty account.picture }">
		<span><img id="picture" src="${IMAGE_ROOT_PATH }/${account.picture }" alt="大头像" width="200" height="200"></span>
		</c:if>
		<c:if test="${account.picture == null or empty account.picture }">
		<span><img id="picture" src="${ctxStatic }/images/avatar.png" alt="大头像" width="200" height="200"></span>
		</c:if>
		
		<c:if test="${account.smallPicture != null and not empty account.smallPicture }">
		<span><img id="small_picture" src="${IMAGE_ROOT_PATH }/${account.smallPicture }" alt="小头像" width="64" height="64"></span>
		</c:if>
		<c:if test="${account.smallPicture == null or empty account.smallPicture }">
		<span><img id="small_picture" src="${ctxStatic }/images/avatar_small.png" alt="小头像" width="64" height="64"></span>
		</c:if>
	</p>
	<form method="post">
		<p style="float:left;">
			<input id="uploadPicture" type="file" accept="image/*" name="avatar" class="file"></p>
            <p style="float:left; line-height:32px; margin-left:20px;">
			或者
			<a href="${ctx }/i/account/profile">编辑个人信息</a>
            </p>
	</form>
</div>

</body>
</html>