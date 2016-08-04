$(function(){
	var ctx = $("#ctx").val();
	$(".left-nav a").click(function(){
		var navList = $(this).find(".nav-list");
		if (navList.hasClass("nav-list-active")) {
			return;
		} else {
			$(".left-nav").find(".nav-list").removeClass("nav-list-active");
			navList.addClass("nav-list-active");
		}
		var catalogId = $(this).attr("data-pk");
		var progress = $.AMUI.progress.configure({
		    minimum: 0.08,
		    easing: 'ease',
		    positionUsing: '',
		    speed: 200,
		    trickle: true,
		    trickleRate: 0.02,
		    trickleSpeed: 800,
		    showSpinner: true,
		    barSelector: '[role="nprogress-bar"]',
		    spinnerSelector: '[role="nprogress-spinner"]',
		    parent: 'body',
		    template: '<div class="nprogress-bar" role="nprogress-bar">' +
		        '<div class="nprogress-peg"></div></div>' +
		        '<div class="nprogress-spinner" role="nprogress-spinner" style="top: 50%; right: 50%; margin-right: -9px; margin-top: -9px;">' +
		        '<div class="nprogress-spinner-icon"></div></div>'
	    });
		
		$.ajax({
		    type: "get",
		    data: {
		    	catalogId: catalogId
		   	},
			url: ctx + "/wx/subCategories",
		   	beforeSend: function () {
		   		progress.start();
			},
		    success: function (data) {
		    	var flag = data.flag;
		    	var categories = data.categories;
		    	if(data.s == 1) {
		    		alert(data.m);
		    	} else {
		    		dealCategories(categories, flag);
		    	}
		    },
		    complete: function () {
		    	 progress.done();
		    },
		    error: function (data) {
		         progress.done();
		    }
		});
	});
});

function dealCategories(categories, flag) {
	$(".classification-main").empty();
	var html = '<div class="am-u-sm-12 classification-block">';
	for (var i=0;i<categories.length; i++) {
		var catalog = categories[i];
		//无三级分类
		if (!flag) {
			html += '<div class="am-u-sm-4">' +
			            '<a href=""><img src="' + catalog.icon +'"></a>' +
			            '<p>' + catalog.name +'</p>' +
			        '</div>';
		}else {
			html +='<div class="am-u-sm-12 classification-block-title">' +
			                '<p>' + catalog.name +'</p>' +
			        '</div>';
			var subCategories = catalog.children;
			for ( var j=0; j<subCategories.length; j++) {
				var subCategory = subCategories[j];
		        html += '<div class="am-u-sm-4">' +
				            '<a href=""><img src="' + subCategory.icon +'"></a>' +
				            '<p>' + subCategory.name +'</p>' +
				        '</div>';
			}
		}
	}
	html += "</div>";
	$(".classification-main").append(html);
}