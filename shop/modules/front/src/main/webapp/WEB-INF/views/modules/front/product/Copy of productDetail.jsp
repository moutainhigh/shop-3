<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>婚庆</title>
<link rel="stylesheet" href="${ctxStatic }/css/style.css" type="text/css" media="screen" charset="utf-8">
<link rel="stylesheet" href="${ctxStatic }/css/all.css" type="text/css" media="screen" charset="utf-8">
<link rel="stylesheet" href="${ctxStatic }/css/common.css" type="text/css" media="screen" charset="utf-8">
<link rel="stylesheet" href="${ctxStatic }/css/detail.css" type="text/css" media="screen" charset="utf-8">

<script type="text/javascript" src="${ctxStaticJS }/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${ctxStaticJS }/js/common.js"></script>
</head>
<body class="full">
<div id="Head">
<script type="text/javascript">
$(function(){
	void function(){
		var left=0,width=0,obj=$(".tips"),timer=null;
		$(".mainLeftNav").find("li").each(function(){
			if($(this).hasClass("cur")){
				left=$(this).position().left;
				width=$(this).outerWidth();
				setTimeout(function(){
					obj.stop(true,false).animate({left:left,width:width});
				},500);
			}
			$(this).hover(function(){
				var l=$(this).position().left,w=$(this).outerWidth();
				clearInterval(timer);
				obj.stop(true,false).animate({left:l,width:w});
			},function(){
				timer=setTimeout(function(){
					obj.stop(true,false).animate({left:left,width:width});
				},300);
			});
		});
		$(".subView").each(function(){
			var f_l=$(this).find(".f_l");
			f_l.each(function(i){
				if((i+1)%2==0){
					$(this).addClass("f_r");
				}
			});
		});
		$(".login_bar").hover(function(){
			$(this).children(".login_hide").show();
		},function(){
			$(this).children(".login_hide").hide();
		});
		$(".ewm").hover(function(){
			$(this).children("div").show();
		},function(){
			$(this).children("div").hide();
		});
		$(".shop_car").click(function(){
			$(".goods_ibuy").animate({left:-295});
		});
		$(".i_see").click(function(){
			$(".history_record").animate({left:-295});
		});
		$("span.close2").click(function(){
			$(".goods_ibuy").animate({left:40});
		});
		$("span.closen").click(function(){
			$(".history_record").animate({left:40});
		});
		$(".my_money,.my_favor").click(function(){
			$(this).children(".login_hide").show();
		});
		$(".close").click(function(e){
			e.stopPropagation();
			$(this).parent(".login_hide").hide();
		});
		$(".my_money,.my_favor,.i_see,.kefu,.backtop").hover(function(){
			$(this).children(".small_tips").show().stop(true,false).animate({left:-92,opacity:1});
		},function(){
			$(this).children(".small_tips").stop(true,false).animate({left:-130,opacity:0},function(){
				$(this).hide();
			});
			$(this).children(".login_hide").hide();
		});
		var scrollEvent=function(){
			$(window).scroll(function(){
				var top=$(document).scrollTop();
				if(top>0){
					$(".backtop").css("visibility","visible");
				}else{
					$(".backtop").css("visibility","hidden");
				}
			});
			$(".backtop").click(function(){
				$("body,html").animate({scrollTop:0});
			});
		}
		scrollEvent();
	}()
	window.clone=function(){
		$(".clone_here").html("");
		var clone=$(".havepruMid").clone(true);
		$(".clone_here").html(clone);
		if($(".clone_here").children().length==0){
			$(".clone_here").addClass("clone_here2");
		}else{
			$(".clone_here").removeClass("clone_here2");
		}
	}
	clone();
});
function deleteCartGoods(rec_id)
{
	Ajax.call('delete_cart_goods.php', 'id='+rec_id, deleteCartGoodsResponse, 'POST', 'JSON');
}
/**
 * 接收返回的信息
 */
function deleteCartGoodsResponse(res)
{
  if (res.error)
  {
    alert(res.err_msg);
  }
  else
  {
	  $("#shoppingCarNone").html(res.content);
	  window.clone();
  }
}
if (document.getElementById('history_list').innerHTML.replace(/\s/g,'').length<1)
{
   document.getElementById('history_list').innerHTML = '您还没有浏览记录';
}
function clear_history()
{
	Ajax.call('user.php','act=clear_history',clear_history_Response, 'GET', 'TEXT',1,1);
	var span=$(".many_goods").find("span");
	span.html(0);
}
function clear_history_Response(res)
{
document.getElementById('history_list').innerHTML = '您已清空最近浏览过的商品';
}
function tongJiNum(){
	var dl=$("#history_list").find("dl").length,span=$(".many_goods").find("span");
	span.html(dl);
}
tongJiNum();



//增加购买商品数
function addFunc(){
	var _obj = $("#buy_number");
	var quantity = _obj.val();
	//console.log("_obj="+_obj+",notifyCartFlg="+notifyCartFlg+",quantity="+quantity+",pid="+_obj.attr("pid"));
	if (/^\d*[1-9]\d*$/.test(quantity)) {
		_obj.val(parseInt(quantity) + 1);
	} else {
		_obj.val(1);
	}
}
//减少购买商品数
function subFunc(){
	var _obj = $("#buy_number");
	var quantity = _obj.val();
	if (/^\d*[1-9]\d*$/.test(quantity)) {
		if(quantity>1){
			_obj.val(parseInt(quantity) - 1);
		}else{
			_obj.val(1);
		}
	} else {
		_obj.val(1);
	}
}

//添加商品收藏
function addToFavorite(){
	var productId = ${prodcutVo.product.id};
	$.post("${ctx}/addToFavorite", {
    	productId: productId
    }, function (data) {
    	if(data.isSuccess) {
    		var _result = "商品已成功添加到收藏夹！";
    		if(data=="0"){
       	    	_result = "商品已成功添加到收藏夹！";
    		}else if(data=='1'){
    	    	_result = "已添加，无需重复添加！";
    		}else if(data=='-1'){//提示用户要先登陆
    	  	   _result = "使用此功能需要先登陆！";
    		}
    	}
// 		$('#addToFavoriteBtn').attr("data-content",_result).popover("toggle");
    });
}
</script>
<div class="mall_detail_wrap white_bg">
	<div style="display: block;" class="mall_detail_sub" id="mall_detail_sub">
		<a href="#" target="_blank" rel="nofollow">商品详情</a><em>&gt;</em><a href="#" target="_blank">商品</a>
	</div>
	<h1 class="mall_main_title">${productVo.product.name}</h1>
	<div class="mall_detail_all clearfix">
		<div id="albums" class="mall_detail_l jqZoomContent fl">
			<div class="jqSlide" style="display:none;">
				<span class="jqzoom_btn prev disabled"><i></i></span>
				<div class="jqSlide_list">
					<ul>
						<li class="hover"><img src="${systemSetting.imageRootPath}${productVo.product.picture}" width="60" height="60"><b class="arrow"></b></li>
					</ul>
				</div>
				<span class="jqzoom_btn next disabled"><i class="disabled"></i></span>
			</div>
			<div style="position: relative;" class="pro_img">
				<!-- 350*350 add small, 375*500 not -->
				<div class="jqzoom small">
					<img id="product_img" src="${systemSetting.imageRootPath}${productVo.product.picture}" alt="" width="350" height="350"><i style="display: inline;" class="zoom_icon"></i>
				</div>
			</div>
		</div>
		<div class="mall_detail_r fl">
			<div class="mall_price_detail">
				<div id="mall_price_detail" class="mall_price clearfix">
					<em class="yen">¥</em>
					<span class="price_num">${productVo.product.nowPrice }</span>
					<span class="label">价格</span>
					<span class="discount_price">(参考价<del>￥<span class="info_market_price">${productVo.product.price}</span></del>,为您节省<label>￥</label> ${productVo.product.price-productVo.product.nowPrice})</span>
					<span class="newuser_shop_free"></span>
				</div>
				<dl class="mall_sale_rules" id="mall_sale_rules">
				</dl>
			</div>
			<!--  暂时屏蔽
			<div style="display: block;" class="mall_detail_common mall_detail_koubei mar_t15 clearfix">
				<span class="label">口碑</span>
				<div class="mall_koubei_detail">
					<span class="mall_koubei_star fl"><i style="width:96%;"></i></span><span class="mall_koubei_rate fl"><a>4.7</a>分</span><span class="mall_koubei_num fl"> (共<a href="#">26</a>篇口碑<a href="#" target="_blank">3043</a>篇短评)</span>
				</div>
			</div>
			-->
			<div style="display: block;" class="mall_detail_common mall_detail_koubei mar_t15 clearfix">
				<span class="label">销量</span>
				<div class="mall_koubei_detail">
					<span class="mall_koubei_num fl">已售${productVo.product.sellcount}${productVo.product.unit}</span>
				</div>
			</div>
			<input id="mall_product_id" value="1028027" type="hidden">
			<div id="mall_sku_list" class="mall_detail_common mall_sku_list mar_t15 clearfix">
			</div>
			<div class="mall_detail_common mall_product_num mar_t15 clearfix">
				<span class="label">数量</span>
				<div class="num_detail" id="num_detail">
					<em title="减少" onclick="subFunc();" class="number_reduce fl"></em>
					<input value="1" id="buy_number" class="buy_number fl" maxlength="2" type="text">
					<em title="增加" onclick="addFunc();" class="number_add fl"></em>
				</div>
				<div class="">
					<span class="label" id="stock_span_id">(库存：${productVo.product.stock}${productVo.product.unit})</span>
				</div>
			</div>
			<div class="mall_info_button mar_t20 clearfix">
				<a href="#" class="mall_addcart_up"></a>
				<div class="mall_like_all">
					<div class="fav_ibtn_back">
			            您还没有登录,
						<a class="txt-deco" href="">登录</a>
						后才能添加我喜欢
		            </div>
					<a class="mall_like_fav" href="javascript:void(0);" onclick="addToFavorite()" >
					<span id="ilike_text">收藏</span>
					<span class="ilike_num">(<em id="ilike_num">${productVo.productFavoriteCount}</em>)</span>
					</a>
				</div>
			</div>
			<div class="mall_detail_common mall_product_service mar_t20 clearfix">
				<span class="label">服务</span>
				<div class="mall_serce_list">
					<a href="#" target="_blank" title="正品保证" class="con">正品保证</a>
					<a href="#" target="_blank" class="con" title="质量保险">质量保险</a>
					<a href="#" target="_blank" title="30天退货" class="con">30天无条件退货</a><a href="#" target="_blank" class="con" title="满2件或299元包邮">满2件或299元包邮</a><a href="#" target="_blank" class="con" title="闪电发货">闪电发货</a>
				</div>
			</div>
			<div class="shopname_self mar_t15">
				<span class="shopname_icon fl">自营</span><span class="shopname_word fl">本商品由<em class="self_mar">婚庆</em>拥有和销售</span>
			</div>
		</div>
	</div>
    
    <div id="mall_slidebar_l" class="mall_slidebar_l fl">
	<div class="mall_search mar_b10">
		<h2 class="black_title">本店搜索</h2>
		<div class="slidebar_content white_bg">
			<form class="mall_input_form" action="http://clarins.jumei.com/search.html?" method="get" target="_blank">
				<div class="mall_input_content">
					<span>关键词</span><input name="search" value="调和护理油" class="mall_search_input" type="text">
				</div>
				<input class="mall_submit_input" value="搜索" type="submit">
			</form>
		</div>
	</div>
	<div class="mall_shop_sortmenu mar_b10">
		<h2 class="black_title">按分类</h2>
		<div class="slidebar_content white_bg">
			<div class="mall_slidebar_parent">
				<h3 class="mall_slidebar_title"><i></i><a href="#">面部护肤</a></h3>
				<ul class="mall_slidebar_list">
					<li><a target="_blank" href="#">洁面</a></li>
					<li><a target="_blank" href="#">化妆水/爽肤水</a></li>
					<li><a target="_blank" href="#">精华</a></li>
					<li><a target="_blank" href="#">啫哩/凝露/凝胶</a></li>
					<li><a target="_blank" href="#">面膜</a></li>
					<li><a target="_blank" href="#">眼部护理</a></li>
					<li><a target="_blank" href="#">面霜</a></li>
					<li><a target="_blank" href="#">乳液</a></li>
					<li><a target="_blank" href="#">精油</a></li>
					<li><a target="_blank" href="#">护肤套装</a></li>
					<li><a target="_blank" href="#">唇部护理</a></li>
				</ul>
			</div>
			<div class="mall_slidebar_parent">
				<h3 class="mall_slidebar_title"><i></i><a href="#">身体护理</a></h3>
				<ul class="mall_slidebar_list">
					<li><a target="_blank" href="#">纤体/美体</a></li>
					<li><a target="_blank" href="#">润肤</a></li>
					<li><a target="_blank" href="#">颈部护理</a></li>
					<li><a target="_blank" href="#">手足护理</a></li>
					<li><a target="_blank" href="#">洗发</a></li>
					<li><a target="_blank" href="#">沐浴</a></li>
					<li><a target="_blank" href="#">身体护理套装</a></li>
					<li><a target="_blank" href="#">个人护理</a></li>
				</ul>
			</div>
			<div class="mall_slidebar_parent">
				<h3 class="mall_slidebar_title"><i></i><a href="#">男士护理</a></h3>
				<ul class="mall_slidebar_list">
					<li><a target="_blank" href="#">男士身体护理</a></li>
					<li><a target="_blank" href="#">男士护肤</a></li>
					<li><a target="_blank" href="#">男士套装</a></li>
				</ul>
			</div>
			<div class="mall_slidebar_parent">
				<h3 class="mall_slidebar_title"><i></i><a href="#">彩妆</a></h3>
				<ul class="mall_slidebar_list">
					<li><a target="_blank" href="#">卸妆</a></li>
					<li><a target="_blank" href="#">防晒</a></li>
				</ul>
			</div>
			<div class="mall_slidebar_parent">
				<h3 class="mall_slidebar_title"><i></i><a href="#">美容工具</a></h3>
				<ul class="mall_slidebar_list">
					<li><a target="_blank" href="#">美体工具</a></li>
					<li><a target="_blank" href="#">彩妆工具</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="mall_shop_use mar_b10">
		<h2 class="black_title">按功效</h2>
		<div class="slidebar_content white_bg">
			<ul class="mall_slidebar_list">
				<li><a target="_blank" href="#">紧致</a></li>
				<li><a target="_blank" href="#">均匀肤色</a></li>
				<li><a target="_blank" href="#">清洁</a></li>
				<li><a target="_blank" href="#">保湿</a></li>
				<li><a target="_blank" href="#">修护肌肤</a></li>
				<li><a target="_blank" href="#">眼部造型</a></li>
				<li><a target="_blank" href="#">抗皱</a></li>
				<li><a target="_blank" href="#">收敛毛孔</a></li>
				<li><a target="_blank" href="#">修护</a></li>
				<li><a target="_blank" href="#">抗衰老</a></li>
				<li><a target="_blank" href="#">瘦腿</a></li>
			</ul>
		</div>
	</div>
	<div class="mall_shop_allmenu mar_b10">
		<h2 class="black_title">按系列</h2>
		<div class="slidebar_content white_bg">
			<ul class="slidebar_series_nav">
				<li><a target="_blank" href="#"><img src="/5400590864640.jpg" border="0"></a></li>
				<li><a target="_blank" href="#"><img src="/540059246d456.jpg" border="0"></a></li>
				<li><a target="_blank" href="#"><img src="/5400593c9530c.jpg" border="0"></a></li>
			</ul>
		</div>
	</div>
	<div class="mall_shop_rank mar_b10">
		<h2 class="black_title">热销排行榜</h2>
		<div class="slidebar_content white_bg">
			<ul class="mall_rank_list">
				<li><a class="mall_rank_img fl" href="#"><img src="/779960_200_200.jpg"></a>
				<div class="mall_rank_word fl">
					<p class="mall_rank_a">
						<a href="#">纤妍紧致精华乳 50ml</a>
					</p>
					<p class="mall_rank_price">
						¥469.00
					</p>
				</div>
				</li>
				<li><a class="mall_rank_img fl" href="#"><img src="/18561_200_200.jpg"></a>
				<div class="mall_rank_word fl">
					<p class="mall_rank_a">
						<a href="#">天然身体调和护理油100ml 多款包装随机发货哦!</a>
					</p>
					<p class="mall_rank_price">
						¥399.00
					</p>
				</div>
				</li>
				<li><a class="mall_rank_img fl" href="#"><img src="/199146_200_200.jpg"></a>
				<div class="mall_rank_word fl">
					<p class="mall_rank_a">
						<a href="#">温和泡沫洁面膏 125ml 两款包装随机发货哦!</a>
					</p>
					<p class="mall_rank_price">
						¥199.00
					</p>
				</div>
				</li>
				<li><a class="mall_rank_img fl" href="#"><img src="/18563_200_200.jpg"></a>
				<div class="mall_rank_word fl">
					<p class="mall_rank_a">
						<a href="#">纤妍紧致慕丝面膜 75ml</a>
					</p>
					<p class="mall_rank_price">
						¥349.00
					</p>
				</div>
				</li>
				<li><a class="mall_rank_img fl" href="#"><img src="/18549_200_200.jpg"></a>
				<div class="mall_rank_word fl">
					<p class="mall_rank_a">
						<a href="#">花样年华纤体纤柔美腹霜 200ml</a>
					</p>
					<p class="mall_rank_price">
						¥529.00
					</p>
				</div>
				</li>
				<li><a class="mall_rank_img fl" href="#"><img src="/779960_200_200.jpg"></a>
				<div class="mall_rank_word fl">
					<p class="mall_rank_a">
						<a href="#">纤妍紧致精华乳 50ml</a>
					</p>
					<p class="mall_rank_price">
						¥469.00
					</p>
				</div>
				</li>
			</ul>
		</div>
	</div>
</div>
    <!--商品左侧--> 
    <div class="mall_slidebar_r fr white_bg">
<div class="slidebar_r_detail">
<div id="slidebarTabsFixed" class="slidebarTabsFixed">
    <div style="width: 100%;" class="slidebar_r_nav black_title">
        <ul class="slidebar_r_ul">
            <li class="tab_current">
                <a class="tab_menu_a" href="#shoppingParameter">商品参数</a>
            </li>
            <li>
                <a class="tab_menu_a" href="#shoppingDetail">商品详情</a>
            </li>
            <li>
                <a class="tab_menu_a" href="#goodsReality">商品实拍</a>
            </li>
                <li>
                <a class="tab_menu_a" href="#userKoubei">用户口碑</a>
            </li>
                <li id="recommendYouTab">
                <a class="tab_menu_a" href="#recommendYou">为您推荐</a>
            </li>
            <li class="mall_cart_icons">
                <span class="jm_price fl"><em class="jm_yen">¥</em>${productVo.product.nowPrice}</span>
                <a class="mall_bottom_cart" href="javascript:;"></a>
            </li>
        </ul>
    </div>
</div>
<!--right content-->
<div class="slidebar_pro_cn">

<!--商品参数-->
<div loaded="loaded" id="shoppingParameter" class="shopping_parameter w_700">
    <h2 class="detail_title"></h2>
    <div class="mall_detail_des">            
         <table class="parameter_table" border="0" cellpadding="0" cellspacing="0" width="700">
             <tbody>
	             <c:forEach items="${productVo.parameterList}" var="d">
	             	<tr>
	             		 <td width="85"><b>${d.name}：</b></td>
		                 <td width="250">
		                     <span>${d.value}</span>
		                 </td>
	             	</tr>
				</c:forEach>
            </tbody>
         </table>
    </div>
</div>
<!--商品参数 end-->

<!--使用方法-->
<div id="useMethod" class="use_method w_700">
    <h2 class="detail_title"></h2>
    
</div>
<!--使用方法 end-->
<!--商品实拍-->
<div id="goodsReality" class="goods_reality w_700">
    <h2 class="detail_title"></h2>
    <div class="mall_detail_des">
        ${productVo.product.productHTML}
    </div>
</div>
<!--商品实拍 end-->
<!--为您推荐-->
<div id="recommendYou" class="recommend_you w_700">
        <h2 class="detail_title"></h2>
        <div class="mall_detail_des">
	<h2 class="mar_t20">
	<div>
		买了此商品的用户还买了
	</div>
	</h2>
	<ul class="mar_t20 clearfix">
		<li><a href="#" title="娇韵诗&nbsp;(Clarins)抚纹身体霜&nbsp;200ml（新款）" target="_blank" class="pic"><img src="/803779_100_100.jpg" alt="" width="100" height="100"></a>
		<p class="tit">
			<a href="#" title="娇韵诗&nbsp;(Clarins)抚纹身体霜&nbsp;200ml（新款）" target="_blank">娇韵诗&nbsp;(Clarins)抚纹身体霜&nbsp;200ml（新款）</a>
		</p>
		<p class="price">
			¥406.00
		</p>
		<p class="count">
			322人购买
		</p>
		</li>
		<li><a href="#" title="舒缓柔肤水200ml" target="_blank" class="pic"><img src="/211944_100_100.jpg" alt="" width="100" height="100"></a>
		<p class="tit">
			<a href="#" title="舒缓柔肤水200ml" target="_blank">舒缓柔肤水200ml</a>
		</p>
		<p class="price">
			¥189.00
		</p>
		<p class="count">
			290篇评价
		</p>
		</li>
		<li><a href="#" title="雅漾舒护活泉喷雾300ml" target="_blank" class="pic"><img src="/162_100_100.jpg" alt="" width="100" height="100"></a>
		<p class="tit">
			<a href="#" title="雅漾舒护活泉喷雾300ml" target="_blank">雅漾舒护活泉喷雾300ml</a>
		</p>
		<p class="price">
			¥145
		</p>
		<p class="count">
			62796篇评价
		</p>
		</li>
		<li><a href="#" title="平衡柔肤水200ml" target="_blank" class="pic"><img src="/18552_100_100.jpg" alt="" width="100" height="100"></a>
		<p class="tit">
			<a href="#" title="平衡柔肤水200ml" target="_blank">平衡柔肤水200ml</a>
		</p>
		<p class="price">
			¥189.00
		</p>
		<p class="count">
			379篇评价
		</p>
		</li>
	</ul>
</div>
</div>
<!--为您推荐 end-->
</div>
<!--right content end-->
</div>
</div>
    <!--商品右侧--> 
</div>
<!--产品详情-->
<div class="clear"></div>  
<script>
	$(function(){
		function Options(id,cName,model){
			this.id=$("#"+id);
			this.li=this.id.find("."+cName).find("li");
			this.model=this.id.find("."+model);
			this.init();
		}
		Options.prototype.init=function(){
			var that=this;
			this.model.eq(0).css("display","block");
			this.li.eq(0).addClass("active");
			this.li.mouseover(function(){
				var index=that.li.index(this);
				that.li.removeClass("active");
				that.li.eq(index).addClass("active");
				that.model.css("display","none");
				that.model.eq(index).css("display","block");
			});	
		}
		//new Options("brand_zone","brand_part","brand_model");
		$(".today_tuijian").find("li").each(function(i){
			if((i+1)%3==0){
				$(this).addClass("noBorder");
			}
			$(this).hover(function(){
				$(this).addClass("active");
			},function(){
				$(this).removeClass("active");
			});
		});
		$(".hot_deals").find("li").each(function(i){
			$(this).hover(function(){
				$(this).addClass("active");
			},function(){
				$(this).removeClass("active");
			});
		});
		var fn=function(){
			var obj=$(".hot_model").find("li"),len=obj.length;
			if(len%3==0){
				for(var i=len-3;i<len;i++){
					obj.eq(i).addClass("last");
				}
			}else{
				var mo=len%3;
				for(var i=len-mo;i<len;i++){
					obj.eq(i).addClass("last");
				}
			}
			$(".promote_model").each(function(i){
				if((i+1)%3==0){
					$(this).addClass("margin0");
				}
			});
		}
		fn();
		var slide_move=function(){
			var a=$(".floor_list").find("a"),arr=[];
			a.click(function(){
				a.removeClass("active");
				$(this).addClass("active");
			});
			$(".floor_div").each(function(i){
				arr[i]=$(this).offset().top;
			});
			$(window).scroll(function(){
				var t=$(document).scrollTop();
				if(t<$("#content").offset().top){
					$(".slide_fixed").removeClass("apper").addClass("disapper");
				}else{
					$(".slide_fixed").removeClass("disapper").addClass("apper");
				}
				$.each(arr,function(k,v){
					if(t>=v){
						a.removeClass("active");
						a.eq(k).addClass("active");
					}
				});
			});
		}
		slide_move();
		var addLink=function(){
			$(".hot_deals").find("li").each(function(i){
				var fa=$(this).find("a:first"),la=$(this).find("a:last"),src=fa.attr("href");
				la.attr({href:src,target:"_blank"});
			});
		}
		addLink();
	});
</script>
</body>
</html>