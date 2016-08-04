<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta name="decorator" content="default_new"/>
    <meta name="Keywords" content="${productVo.product.name }"/>
    <meta name="Description" content="${productVo.product.name }"/>
    <title>${productVo.product.name } - 产品详情</title>
    <link rel="stylesheet" type="text/css" href="${ctxAssets }/stylesheets/details.css"></link>
    <link rel="stylesheet" type="text/css" href="${ctxAssets }/stylesheets/fangda.css"></link>
    <content tag="local_script">
    	<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>
        <script type="text/javascript" src="${ctxAssetsJS }/javascripts/details.js"></script>
        <script type="text/javascript" src="${ctxAssetsJS }/javascripts/alert.js"></script>
	    <script type="text/javascript">
	    	$(document).ready(function(){
	    		$(".pro-color a").click(function() {
					var text = $(this).text();
					var dataType = $(this).attr("data-type");
					var curr = $(this).parents(".pro-color").find(".select-box");
					var currValue = curr.find("a").text();
					curr.removeClass("select-box");
					if (currValue == text) {
						return;
					} 
					$(this).parent().addClass("select-box");
					//处理当前选中属性
					dealSpec(text, dataType);
				});
				chooseDefualtSpec();
	    	});
	    	
	    	function chooseDefualtSpec() {
	    		var specJson = ${productVo.specJsonString};
				if(isEmpty(specJson)) {
					return;
				}
				var spec = specJson[0];
				if (!isEmpty(spec.specType)) {
					$(".spec-type a").each(function() {
						if ($(this).text() == spec.specType) {
							$(this).parent().addClass("select-box");
						}
					});
				}
				if (!isEmpty(spec.specColor)) {
					$(".spec-color a").each(function() {
						if ($(this).text() == spec.specColor) {
							$(this).parent().addClass("select-box");
						}
					});
				}
				if (!isEmpty(spec.specSize)) {
					$(".spec-size a").each(function() {
						if ($(this).text() == spec.specSize) {
							$(this).parent().addClass("select-box");
						}
					});
				}
				
				//设置信息
	    		if (!isEmpty(spec.specPrice)) {
	    			$(".pro-price").html("<em>¥</em>" + spec.specPrice);
	    			$(".pro-price").attr("data-value", spec.specPrice);
	    			
	    			if (!isEmpty(spec.minSell)) {
	    				$("#buy_number").val(spec.minSell);
	    				$("#minSell").val(spec.minSell);
	    			}
	    			setInitTotalPrice();
	    		}
	    		$("#specId").val(spec.id);
	    	}
	    	
	    	function dealSpec(text, dataType) {
				var specJson = ${productVo.specJsonString};
				if(isEmpty(specJson)) {
					return;
				}
				diableSpec(text, dataType, specJson);
				dealPrice(text, dataType, specJson);
	    	}
	    	//设置规格价格
	    	function dealPrice(text, dataType, specJson) {
	    		var specType = null;
				var specColor = null;
				var specSize = null;
				if (dataType == "type") {
					specType = text;
					specColor = $(".spec-color .select-box a").text();	
					specSize = $(".spec-size .select-box a").text();	
				} else if (dataType == "color") {
					specColor = text;
					specType = $(".spec-type .select-box a").text();	
					specSize = $(".spec-size .select-box  a").text();	
				} else if(dataType == "size") {
					specSize = text;
					specType = $(".spec-type .select-box a").text();	
					specColor = $(".spec-color .select-box a").text();	
				}
				var flag = false;//是否找到规格标示
				for(var i=0; i<specJson.length; i++) {
					var spec = specJson[i];
					//禁用没有选择属性的参数
					if (isCurrentSpec(specType, specColor, specSize, spec)) {
						setPrice(spec.specPrice);
						setSpec(spec.id);
						setMinSell(spec.minSell);
						$("#pro-props").removeClass("pro-parameter");
						$("#pro-props .pro-parameter-tips").hide();
						
						flag = true;
					}
				}
				//找不到设置默认初始价格
				if(!flag) {
					var price = ${productVo.product.nowPrice};
					setPrice(price);
				}
				
	    	}
	    	//禁用不满足条件规格
	    	function diableSpec(specParam, dataType, specJson) {
	    		var activeType = [];
	    		var activeColor = [];
	    		var activeSize = [];
	    		
	    		for(var i=0; i<specJson.length; i++) {
					var spec = specJson[i];
					if (dataType == "type") {
						if (specParam == spec.specType) {
							activeColor.push(spec.specColor);
							activeSize.push(spec.specSize);
						}
					} else if (dataType == "color") {
						if (specParam == spec.specColor) {
							activeType.push(spec.specType);
							activeSize.push(spec.specSize);
						}	
					} else if(dataType == "size") {
						if (specParam == spec.specSize) {
							activeColor.push(spec.specColor);
							activeType.push(spec.specType);
						}
					}
				}
				//禁用不活动的属性
				$(".pro-color .not-alw").removeClass("not-alw");
				$(".pro-color a").each(function() {
					var _text = $(this).text();
					var _dataType = $(this).attr("data-type");
					
					if (_dataType == "type") {
						for(var j=0; j<activeType.length; j++) {
							if($.inArray(_text, activeType) == -1) {
								$(this).parent().removeClass("select-box").addClass("not-alw");
							}
						}
					} else if (_dataType == "color") {
						for(var j=0; j<activeColor.length; j++) {
							if($.inArray(_text, activeColor) == -1) {
								$(this).parent().removeClass("select-box").addClass("not-alw");
							}
						}
					} else if (_dataType == "size") {
						for(var j=0; j<activeSize.length; j++) {
							if($.inArray(_text, activeSize) == -1) {
								$(this).parent().removeClass("select-box").addClass("not-alw");
							}
						}
					}
				});
	    	}
	    	
	    	function setPrice(price) {
	    		if (price != null && '' != price) {
	    			$(".pro-price").html("<em>¥</em>" + price);
	    			$(".pro-price").attr("data-value", price);
	    			
	    			setInitTotalPrice();
	    		}
	    	}
	    	
	    	function setMinSell(minSell) {
	    		if (minSell != null && '' != minSell) {
	    		    var curNum = $("#buy_number").val();
	    		    if (parseInt(curNum) < parseInt(minSell)) {
	    		    	$("#buy_number").val(minSell);
	    		    	$("#minSell").val(minSell);
	    		    }
	    		}
	    	}
	    	
	    	function setSpec(specId) {
	    		if (specId != null && '' != specId) {
	    			$("#specId").val(specId);
	    		}
	    	}
	    	
	    	function isEmpty(value) {
				if(typeof value == 'undefined') {
					return true;
				}
				if(!value || $.trim(value) == '' || $.trim(value) == 'Empty') {
					return true;
				}
				return false;
			}
	    	
	    	function isCurrentSpec(specType, specColor, specSize, spec) {
	    		if (!isEmpty(spec.specType)) {
	    			if (isEmpty(specType) || specType != spec.specType) {
		    			return false;
		    		} 
	    		}
	    		
	    		if (!isEmpty(spec.specColor)) {
	    			if (isEmpty(specColor) || specColor != spec.specColor) {
		    			return false;
		    		} 
	    		}
	    		
	    		if (!isEmpty(spec.specSize)) {
	    			if (isEmpty(specSize) || specSize != spec.specSize) {
		    			return false;
		    		} 
	    		}
	    		return true;
	    	}
	    </script>
	</content>
  </head>
  <body>
    <div class="nav-router container-n">
      <ol class="content-n margin-n">
        <li class=""><a href="${ctx }/index">道具商城</a></li>
        <c:forEach items="${productVo.catalogPath}" var="c" varStatus="i">
        	<li class="<c:if test="${i.index == (fn:length(productVo.catalogPath)-1)}">nav-router-active</c:if>"><a href="${ctx }/product/list?catalogId=${c.id}">${c.name }</a></li>
        </c:forEach>
      </ol>
    </div>
    <div class="product-intro container-n">
        <div class="content-n">
            <div class="product-img-left">
                <%-- <div class="product-img-big">
                	<c:if test="${fn:length(productVo.imageList) > 0}">
	                    <img src="${IMAGE_ROOT_PATH }/${productVo.imageList[0]}" class="product-img-big-left"/>
	                    <div class="product-img-big-move-div"></div>
	                    <div class="product-img-big-show">
	                        <img src="${IMAGE_ROOT_PATH }/${productVo.imageList[0]}"/>
	                    </div>
                    </c:if>
                </div> --%>
                <%-- <div class="product-img-small">
                    <ul>
                    	<c:forEach items="${productVo.imageList}" var="c" varStatus="i">
	                        <li class="<c:if test="${i.index == 0}">product-img-small-active</c:if>"><a href="javascript:void(0)"><img src="${IMAGE_ROOT_PATH }/${c}"/></a></li>
	                    </c:forEach>
                        <div class="clear"></div>
                    </ul>
                    <a href="javascript:void(0)" class="slider-left"><img src="${ctxAssets }/image/left.png"/></a>
                    <a href="javascript:void(0)" class="slider-right"><img src="${ctxAssets }/image/right.png"/></a>
                    <div class="clear"></div>
                </div> --%>
                <div class="fangda"> 
				  <!-- 大图begin -->
				  <c:if test="${fn:length(productVo.imageList) > 0}">
				  	  <div id="preview" class="spec-preview"> <span class="jqzoom"><img jqimg="${IMAGE_ROOT_PATH }/${productVo.imageList[0]}" src="${IMAGE_ROOT_PATH }/${productVo.imageList[0]}" alt=""></span> </div>
				  </c:if>
				
				  <!-- 大图end --> 
				
				  <!-- 缩略图begin -->
				
				  <div class="spec-scroll"> <a class="prev">&lt;</a> <a class="next">&gt;</a>
				
				    <div class="items">
				
				      <ul style="">
						<c:forEach items="${productVo.imageList}" var="c" varStatus="i">
	                        <li><img bimg="${IMAGE_ROOT_PATH }/${productVo.imageList[i.index]}" src="${IMAGE_ROOT_PATH }/${productVo.imageList[i.index]}" onmousemove="preview(this);"></li>
	                    </c:forEach>
				      </ul>
				    </div>
				  </div>
				</div>
                <div class="product-share">
	                <!-- JiaThis Button BEGIN -->
					<div class="jiathis_style">
						<span class="jiathis_txt">分享到：</span>
						<a class="jiathis_button_tools_1"></a>
						<a class="jiathis_button_tools_2"></a>
						<a class="jiathis_button_tools_3"></a>
						<a class="jiathis_button_tools_4"></a>
						<a href="http://www.jiathis.com/share" class="jiathis jiathis_txt jiathis_separator jtico jtico_jiathis" target="_blank">更多</a>
					</div>
					<script type="text/javascript" src="http://v3.jiathis.com/code_mini/jia.js" charset="utf-8"></script>
					<!-- JiaThis Button END -->
                </div>
            </div>
            <div class="product-sale-info">
                <div class="product-sale-name">
                    <p>${productVo.product.name }</p>
<!--                     <p class='title-price'>(1.25元一双 16g)</p> -->
                    <div class="clear"></div>
                </div>
                <div class="pro-other">
                    <div class="sale-total">
                        <img src="${IMAGE_ROOT_PATH }/${productVo.product.seller.logo}" alt=""  style="width: 100%;"/>
                    </div>
                    <div class="shoucang">
                        <p style="line-height: 80px; font-size: 18px;">${productVo.product.seller.name}</p>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="pro-sale-price">
                    <p class="pro-price" data-value="${productVo.product.nowPrice }"><em>¥</em>${productVo.product.nowPrice }</p>
                    <p class="pro-total-price">原价¥${productVo.product.price }</p>
                    <div class="clear"></div>
                </div>
                <div class="pro-other">
                    <div class="sale-total">
                        <p>销售量 <a>${productVo.product.sellcount }</a></p>
                    </div>
                    <div class="shoucang">
                        <p>收藏 <a id="favoriteCount">${productVo.productFavoriteCount }</a></p>
                    </div>
                    <div class="clear"></div>
                </div>
               <div id="pro-props">
               		<div style="display:none" class="pro-parameter-tips">
                        <p>请选择你想要的商品信息</p>
                    </div>
	                <c:if test="${fn:length(productVo.specType) != 0 }">
	                	<div class="pro-color">
		                    <p>类型:</p>
		                    <ul class='spec-type'>
		                    	<c:forEach items="${productVo.specType }" var="item">
		                    		<li>
		                    			<div class="">
		                    				<b></b><a href="javascript:void(0);" data-type="type" class="">${item }</a>
		                    			</div>
		                        	</li>
		                        </c:forEach>
		                    </ul>
		                    <div class="clear"></div>
		                </div>
	                </c:if>
	                <c:if test="${fn:length(productVo.specColor) != 0 }">
	                	<div class="pro-color">
		                    <p>颜色:</p>
		                    <ul class='spec-color'>
		                    	<c:forEach items="${productVo.specColor }" var="item">
		                    		<li>
		                    			<div class="">
		                            		<b></b><a  href="javascript:void(0);" data-type="color" class="">${item }</a>
		                            	</div>
		                        	</li>
		                        </c:forEach>
		                    </ul>
		                    <div class="clear"></div>
		                </div>
	                </c:if>
	                <c:if test="${fn:length(productVo.specSize) != 0 }">
	                	<div class="pro-color">
		                    <p>尺寸:</p>
		                    <ul class='spec-size'>
		                    	<c:forEach items="${productVo.specSize }" var="item">
		                    		<li>
		                            	<div class="">
		                            		<b></b><a  href="javascript:void(0);" data-type="size" class="">${item }</a>
		                            	</div>
		                        	</li>
		                        </c:forEach>
		                    </ul>
		                    <div class="clear"></div>
		                </div>
	                </c:if>
	            </div>
               	<c:if test="${productVo.product.activity != null }">
               	<div class="pro-unit"> 
					<p>促销:</p> 
                    <p class="unit"><fmt:formatDate value="${productVo.product.activity.startDate }" pattern="yyyy年MM月dd日"/> - <fmt:formatDate value="${productVo.product.activity.endDate }" pattern="yyyy年MM月dd日"/></p>
                    <div class="clear"></div>
               	</div>
               	</c:if> 
               <div class="pro-total-num">
                    <p>数量:</p>
                    <a href="javascript:void(0);" onclick="subFunc()" class="reduction">-</a>
                    <input type="text" id="buy_number" value="${productVo.product.minSell == 0 ? '1' : productVo.product.minSell}"/>
                    <a href="javascript:void(0);" onclick="addFunc()">+</a>
                    <p class="warning" style=""></p>
                    <div class="clear"></div>
                </div>
                <div class="pro-unit">
                    <p>单位:</p>
                    <p class="unit">${productVo.product.unit }</p>
                    <div class="clear"></div>
                </div>
                <div class="xiaoji">
                    <p>小计:</p>
                    <p class="xiaoji-price"><em>¥</em>0</p>
                    <div class="clear"></div>
                </div>
                <div class="put-shop-car">
                	<c:if test="${productVo.outOfStock }">
                    	${productVo.productSorryStr }
                    </c:if>
                    <c:if test="${!productVo.outOfStock }">
                    	<a id="shoppingCart" href="javascript:void(0);"><img src="${ctxAssets}/image/ok.png"/></a>
                    </c:if>
                    <c:if test="${productVo.favorite }">
                    	 <a class="mall_like_fav mall_like_faved" href="javascript:void(0);">
	                        <span id="ilike_text">已收藏</span>
	                    </a>
                    </c:if>
                    <c:if test="${!productVo.favorite }">
                    	 <a class="mall_like_fav" href="javascript:void(0);">
	                        <span id="ilike_text">收藏</span>
	                    </a>
                    </c:if>
                    <div class="fav_ibtn_back" style="display: none;">您还没有登录,&nbsp;<a class="txt-deco" href="${ctx }/login">登录</a> 后才能添加我收藏</div>
                </div>
                <div class="fuwu-main">
<!--                     <div class="fuwu"> -->
<!--                         <p>服务承诺</p> -->
<!--                     </div> -->
<!--                     <div class="fuwu-list"> -->
<!--                         <p>按时发货</p> -->
<!--                         <p>急速退款</p> -->
<!--                         <p>赠运费险</p> -->
<!--                         <p>七天无理由退货</p> -->
<!--                     </div> -->
<!--                     <div class="zhifufangshi"> -->
<!--                         <p>支付方式</p> -->
<!--                     </div> -->
                </div>
            </div>
            <div class="clear"></div>
        </div>
    </div>
    <div class="product-center-page container-n">
        <div class="content-n">
            <div class="content-left">
                <div class="tuijian">
                    <div class="tuijian-title">
                        <p>商品推荐</p>
                    </div>
                    <c:forEach items="${hotRecommendProduct}" var="c">
	                    <div class="tuijian-pro-list">
	                    	<a title="${c.name}" href="${ctx }/product/${c.id}">
	                    		<img src="${IMAGE_ROOT_PATH }/${c.picture}-px100"/>
	                    	</a>
	                        <div class="pro-tijian-name">
	                            <p><a title="${c.name}" href="${ctx }/product/${c.id}">${c.name }</a></p>
	                            <p class="pro-tuijian-pirce"><em>¥</em>${c.nowPrice }</p>
	                        </div>
	                    </div>
	                </c:forEach>
                </div>
<!--                 <div class="pro-guanggao"> -->
<!--                     <img src="image/guanggao.png"/> -->
<!--                 </div> -->
            </div>
            <div class="pro-introduction">
                <div class="pro-intro-top">
                    <div class="pro-intro-menu pro-intro-menu-active">
                        <a href="#product-params">产品参数</a>
                    </div>
                    <div class="pro-intro-menu">
                        <a href="#product-detail">商品详情</a>
                    </div>
                    <div class="clear"></div>
                </div>
                <div class="pro-intro-main">
                    <div id="product-params" class="pro-canshu">
                        <div class="pro-canshu-title">
                            <p class="">商品参数<em>PRODUCT PARAMETERS</em></p>
                        </div>
                        
                        <div class="pro-canshu-list">
                       		<p class="">产品名称:<em>${productVo.product.name }</em></p>
                        </div>
                        
                        <div class="pro-canshu-list">
                        	<c:forEach items="${productVo.parameterList}" var="c">
                        		<p class="">${c.name }:<em>${c.value }</em></p>
                        	</c:forEach>
                        </div>
                    </div>
                    <div id="product-detail" class="pro-details">
                        <div class="pro-details-title">
                            <p class="">商品详情<em>PRODUCT DETAILS</em></p>
                        </div>
                        <div class="pro-details-list">
                          	${fns:unescapeHtml(productVo.product.productHTML) }
                        </div>
                    </div>
                </div>
            </div>
            <div class="clear"></div>
        </div>
    </div>
    <img src="${IMAGE_ROOT_PATH }/${productVo.product.picture}-px100" class="img-circle put-in"/>
    <input type="hidden" id="ctx" name="ctx" value="${ctx }"/>
    <input type="hidden" id="productId" name="productId" value="${productVo.product.id }"/>
  	<input type="hidden" id="specId" name="specId"/>
  	<input type="hidden" id="minSell" name="minSell" value="${productVo.product.minSell }"/>
    
  </body>
</html>