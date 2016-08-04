$(function () {
		var ctx = $("#ctx").val();
        $("html").css('font-size', $(window).width()/10 + "px");
        
        $(".appointment").click(function(){
        	window.location.href="";
        });
        
        $(".wholesale").click(function(){
        	window.location.href="";
        });
        
        $(".activities-left").click(function(){
        	window.location.href="";
        });
        
        $(".activities-right-top").click(function(){
        	window.location.href="";
        });
        
        $(".activities-right-bottom").click(function(){
        	window.location.href="";
        });
        
        $("#classification").click(function(){
        	window.location.href = ctx + "/wx/categories";
        });
        
        $("#cart").click(function(){
        	window.location.href = ctx + "/wx/cart/list";
        });
        
        $("#cust-center").click(function(){
        	window.location.href = ctx + "/wx/cart/list";
        });
        
    });

	$(window).resize(function(){
	    $("html").css('font-size', $(window).width()/10 + "px");
	});