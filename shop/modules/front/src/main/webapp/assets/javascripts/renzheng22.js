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
    		$(".user-id .warning").html("企业营业执照号码不能为空");
    		$(".user-id .warning").show();
    		return;
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
    
    $("#fileToUpload").unbind("change").on("change", function(){
		lrz(this.files[0], {width: 400}, function (results) {
        	$.post(ctx + "/i/account/auth/upload",{ 
		   	    	type: results.origin.type,
		   	    	name: results.origin.name,
		   	  		size: results.base64.length,
		   	        data: results.base64
		   	    }, function(data){
   		   	  if (data.s == 0) {
	            	$('#view').attr('src', imagePath +"/"+ data.m);
	            	$('#pictrueUrl').val(data.m);
	            	hasUpload = true;
	            } else { 
	            	alert('上传图片失败！');
	            	hasUpload = false;
	            }
		   	    }, "json");
		});
  	});
    
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
		$(".user-id .warning").html("企业营业执照号码不能为空");
		$(".user-id .warning").show();
		$(".user-id input").focus();
		return false;
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
