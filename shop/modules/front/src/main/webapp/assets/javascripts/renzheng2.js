$(function() {
	var ctx = $("#ctx").val();
	var ctxStatic = $("#ctxStaticJS").val();
	var imagePath = $("#imagePath").val();
	var accountId = $("#id").val();
    $('.fangda').click(function() {
        if ($('.example-img').css("width") == "100px") {
            $('.example-img').css("width", "270px");
        } else {
            $('.example-img').css("width", "100px");
        }
    });
    $(".user-name input").blur(function(){
    	var username = $(this).val();
    	if (username == null || username == '' || username.length > 10) {
    		$(".user-name .warning").html("请填写姓名为1-10位中英文");
    		$(".user-name .warning").show();
    	} else {
    		$(".user-name .warning").hide();
    	}
    });
    	
    $(".user-id input").blur(function(){
    	var cardNo = $(this).val();
    	if (cardNo == null || cardNo == '') {
    		$(this).val('0000000000');
    		//$(".user-id .warning").html("企业营业执照号码不能为空");
    		//$(".user-id .warning").show();
    		//return;
    	} else {
    		$(".user-id .warning").hide();
    	}
    	$.post(ctx + "/i/account/validateCardNo", {
    		cardNo: cardNo,
    		type:"2"
        }, function (data) {
            if(data.s == 1) {
            	$(".user-id .warning").html("此企业营业执照号码已被认证");
        		$(".user-id .warning").show();
            }
        });
    });
    
    var hasUpload = false;
    setTimeout(function(){
	    $('#uploadPicture').uploadify({
			'buttonText' : '上传图片',
			'formData' : {
				id: accountId
			},
			'swf' : ctxStatic + '/js/uploadify/uploadify.swf',
			'uploader' : ctx + '/i/account/uploadAuth;jsessionid=' + $("#sesstionId").val(),
	        'hideButton'     : true,
	        'fileSizeLimit'  : '2MB',
	        'fileTypeExts'   :	'*.jpg;*.jpeg;*.bmp;*.gif',
	        'multi'			 : false,
			'onUploadSuccess':function(file, data, response){
	            data = eval('(' + data + ')');
	            if (data.s == 0) {
	            	//$('#picture').val(data.msg);
	            	$('#view').attr('src', imagePath +"/"+ data.m);
	            	$('#pictrueUrl').val(data.m);
	            	hasUpload = true;
	            } else { 
	            	alert('上传图片失败！');
	            	hasUpload = false;
	            }
	        },
			'onSelectError' : function (file, errorCode, errorMsg) {
            	//返回码。文件为空
            	if(errorCode == SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE){
            		this.queueData.errorMsg = "不能传空文件!!";
            	} else if(errorCode == SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT){
            		this.queueData.errorMsg = "上传图片大小不能超过2MB!";
            	}
            	hasUpload = false;
            }
		});
     },10);
    
    $("#submit").click(function(){
    	//验证输入数据
    	if (!validate()) return;
    	if (!hasUpload) {
    		$(".user-idImg .warning").html("请上传营业执照正面照片");
    		$(".user-idImg .warning").show();
    		return;
    	}else {
    		$(".user-idImg .warning").hide();
    	}

    	var cardName = encodeURI(encodeURI($("#cardName").val()));
    	var url = ctx + "/i/account/saveAuth"
    			+"?cardName="  + cardName
    			+"&cardNo=" + $("#cardNo").val()
    			+"&authPicture=" + $('#pictrueUrl').val()
    			+"&type=2";
    	window.location.href = url;
    });
});

function validate() {
	var username = $(".user-name input").val();
	if (username == null || username == '' || username.length > 10) {
		$(".user-name .warning").html("请填写姓名为1-10位中英文");
		$(".user-name .warning").show();
		$(".user-name input").focus();
		return false;
	} 
	var cardNo = $(".user-id input").val();
	if (cardNo == null || cardNo == '') {
		$(".user-id input").val('0000000000');
		//$(".user-id .warning").html("企业营业执照号码不能为空");
		//$(".user-id .warning").show();
		//$(".user-id input").focus();
		//return false;
	}
	$.ajax({  
        type : "post",  
         url : ctx + "/i/account/validateCardNo",
         data: {
        	 cardNo: cardNo,
        	 type: "2"
         },
         async : false,  
         success : function(data){  
        	 if(data.s == 1) {
        		$(".user-id .warning").html("此企业营业执照号码已被认证");
         		$(".user-id .warning").show();
         		$(".user-id input").focus();
             	return false;
             }
         }  
    }); 
	
	if (!$('#agreement').is(':checked')){
		$(".xieyi .warning").html("请仔细阅读服务协议并确认");
		$(".xieyi .warning").show();
		$("#agreement").focus();
		return false;
	}else {
		$(".xieyi .warning").hide();
	}
	return true;
}
