$(function(){
	var pageSize = $("#pageSize").val();
	var pageNo = $("#pageNo").val();
	var total = $("#total").val();
	var price = $("#price").val();
    var catalogId = $("#catalogId").val();
    var orderKey = $("#orderKey").val(),  orderValue = $("#orderValue").val();
    var ctx = $("#ctx").val();
	$("#pagination").pagination(total, {
        callback: function(index, jq){
        	$('#pageNo').val(Number(index));
        	submitToList();
        },
        items_per_page:pageSize,
        current_page:pageNo,
		num_edge_entries:3,
		num_display_entries:4,
		link_to:"#",
		prev_text:"上一页",
		next_text:"下一页",
    });
    //翻页
	$(".top-page-right a").click(function(){
		if (Number($('#pageNo').val()) == (Number($("#pagerSize").val())-1)) {
			return;
		}
		$('#pageNo').val(Number($('#pageNo').val()) + 1);
		submitToList();
	});
	$(".top-page-left a").click(function(){
		if (Number($('#pageNo').val()) ==  0) {
			return;
		}
		$('#pageNo').val(Number($('#pageNo').val()) - 1);
		submitToList();
	});
	
    //当前选中样式设置
    $(".nav-map-list a").each(function(){
    	var key = $(this).attr("data-key");
    	if (price == key || catalogId == key) {
    		$(this).parents('.nav-map-center').find(".nav-map-list-active").removeClass('nav-map-list-active');
    		$(this).parent().addClass('nav-map-list-active');
    	}
    });
    $(".all-pro-main-name a").each(function(){
    	var key = $(this).attr("data-key");
    	if (orderKey == key) {
    		if (orderValue != null && orderKey != '') {
    			if ("up" == orderValue) {
    				$(this).removeClass("sort-down");
        			$(this).addClass("sort-up");
    			} else if ("down" == orderValue) {
    				$(this).removeClass("sort-up");
        			$(this).addClass("sort-down");
    			}
    		}
    		$(this).parents('.all-pro-main-top').find(".all-pro-mian-name-active").removeClass('all-pro-mian-name-active');
    		$(this).parent().addClass('all-pro-mian-name-active');
    	}
    });
    //
    //条件过滤栏
    $(".nav-map-list a").click(function(){
    	var type = $(this).attr("data-type");
    	var value = $(this).attr("data-key");
    	if (type == 1) {
    		$("#catalogId").val(value);
    	} else if (type == 2) {
    		$("#price").val(value);
    	}
    	submitToList();
    });
    
    //排序栏事件
    $(".all-pro-main-name a").click(function(){
    	$(this).parent().parent().find(".all-pro-mian-name-active").removeClass("all-pro-mian-name-active");
    	$(this).parent().addClass("all-pro-mian-name-active");
    	orderKey = $(this).attr("data-key");
    	//切换排序样式
    	var className = $(this).attr("class");
    	if (className != null && className !='') {
    		if (className == "sort-up") {
    			$(this).removeClass("sort-up");
    			$(this).addClass("sort-down");
    			$("#orderValue").val("down");
    		} else if (className == "sort-down") {
    			$(this).removeClass("sort-down");
    			$(this).addClass("sort-up");
    			$("#orderValue").val("up");
    		}
    	}
    	$("#orderKey").val(orderKey);
    	submitToList();
    });
   
    function submitToList() {
    	var url = ctx + "/product/list";
    	$('#searchForm').attr("action", url);
    	document.getElementById('searchForm').submit();
    }
    
});

