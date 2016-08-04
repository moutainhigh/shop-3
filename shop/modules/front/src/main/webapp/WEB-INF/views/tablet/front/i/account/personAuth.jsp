<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="decorator" content="account_default_new"/>
    <meta name="Keywords" content=""/>
    <meta name="Description" content=""/>
    <title>个人认证</title>
    <link rel="stylesheet" type="text/css" href="${ctxAssets}/stylesheets/renzheng1.css"/>
    <link type="text/css" rel="stylesheet" href="${ctxStaticJS }/js/uploadify/uploadify.css" />
  </head>
  <body>
    <div class="container-n">
      <div class="step-img">
        <img src="${ctxAssets }/image/step1.png"/>
      </div>
      <div class="celphone">
        <div class="option-left f-l">
          <p class="option-left-word f-l">手机号：</p>
        </div>
        <div class="option-right">
          <p>${mobile}</p>
        </div>
        <!-- <div class="option-right">
          <input type="text">
        </div> -->
      </div>
<!--       <div class="zhengjian-type"> -->
<!--         <div class="option-left f-l"> -->
<!--           <p class="option-left-word f-l"><em>*</em>证件类型：</p> -->
<!--         </div> -->
<!--         <div class="option-right"> -->
<!--           <div class="btn-group"> -->
<!--             <button class="btn btn-default btn-sm dropdown-toggle" type="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> -->
<!--               身份证  -->
<!--               <span class="caret"></span> -->
<!--             </button> -->
<!--             <ul class="dropdown-menu"> -->
<!--               <li><a>军官证</a></li> -->
<!--               <li><a>港台护照</a></li> -->
<!--               <li><a>学生证</a></li> -->
<!--               <li><a>屌丝证</a></li> -->
<!--             </ul> -->
<!--           </div>   -->
<!--         </div> -->
<!--       </div> -->
      <div class="user-name">
        <div class="option-left f-l">
          <p class="option-left-word f-l"><em>*</em>身份证姓名：</p>
        </div>
        <div class="option-right">
          <input id="cardName" autocomplete="off" type="text"/>
          <p class="warning" style="display:none"></p>
          <p class="tips">信息审核成功后，身份姓名不可修改</p>
        </div>
      </div>
      <div class="user-id">
        <div class="option-left f-l">
          <p class="option-left-word f-l"><em>*</em>身份证号码：</p>
        </div>
        <div class="option-right">
          <input id="cardNo" autocomplete="off" type="text"/>
          <p class="warning" style="display:none"></p>
          <p class="tips">请您输入你的18位身份证号码，应和上传的身份证照片一致</p>
        </div>
      </div>
      <div class="user-idImg">
        <div class="option-left f-l">
          <p class="option-left-word f-l"><em>*</em>身份证照片：</p>
        </div>
        <div class="option-right border-r">
          <form>
          	<input type="hidden" id="ctx" value="${ctx}"/>
          	<input type="hidden" id="imagePath" value="${IMAGE_ROOT_PATH}"/>
          	<input type="hidden" id="ctxStatic" value="${ctxStatic}"/>
          	<input type="hidden" id="ctxAssets" value="${ctxAssets}"/>
          	<input type="hidden" id="ctxStaticJS" value="${ctxStaticJS}"/>
          	<input type="hidden" id="ctxAssetsJS" value="${ctxAssetsJS}"/>
          	<input type="hidden" id="id" value="${id}"/>
          	<input type="hidden" id="sesstionId" value="<%=session.getId()%>"/>
          	<div id="fileQueue"></div>
            <!-- <input id="uploadPicture" type="file" accept="image/*" name="avatar"  class="upload-input" /> -->
            
            <input type="file" capture="camera" accept="image/*" name="fileToUpload" id="fileToUpload" class="upload-input" />
            <div id="uploadPicture-button" class="uploadify-button " style="height: 30px; line-height: 30px; width: 120px;"><span class="uploadify-button-text">上传图片</span></div>
            
<!--             <div class="upload-div"> -->
<!--               <img src="${ctxAssets }/image/camera.png"/>上传图片 -->
<!--             </div> -->
          </form>
          <div class="yulan" style="margin-top:10px">
          	<img id="view"/>
          </div>
          <p style="display:none" class="warning"></p>
          <p class="tips">请上传姓名与号码清晰可见身份证正面照</p>
          <p class="tips">支持jpg,jpeg,bmp,gif格式照片，大小不超过2M</p>
        </div>
        <div class="example-idcar">
          <p>身份证示例图</p>
          <img src="${ctxAssets }/image/shenfenzheng.png" class="example-img"/>
          <img src="${ctxAssets }/image/fangda.png" class="fangda"/>
        </div>
       	<input id="pictrueUrl" type="hidden"/>
      </div>
      <div class="xieyi">
        <div class="option-left f-l">
        </div>
        <div class="option-right">
          <input id="agreement" autocomplete="off" type="checkbox" />
          <p class="">我同意并遵守<a>《报喜了服务协议》</a></p>
          <p class="warning" style="display:none"></p>
          <div class="next-step">
            <button id="submit" class="btn btn-default" type="submit">提交</button>
          </div>
        </div>
      </div>
    </div>
    <content tag="local_script">
	    <script type="text/javascript" src="${ctxAssetsJS }/javascripts/exif.js"></script>
  		<script type="text/javascript" src="${ctxAssetsJS }/javascripts/lrz.mobile.min.js"></script>
  		<script type="text/javascript" src="${ctxAssetsJS }/javascripts/renzheng12.js"></script>
  		<script type="text/javascript">
	    $(function(){
	    	
	    });
	    </script>
    </content>
  </body>
</html>