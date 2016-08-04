<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/mobile/include/taglib.jsp"%>
<html>
<head>
	<title>首页</title>
	<!-- <meta name="decorator" content="default_mb"/> -->
	<%@include file="/WEB-INF/views/mobile/include/head.jsp" %>
	<link rel="stylesheet" type="text/css" href="${ctxMS }/css/phone-detail.css">
</head>
  <body>
    <div class="am-g">
        <div class="am-u-sm-12 header">
            <div class="header-left am-fl am-vertical-align">
                <p class="am-vertical-align-middle">成都</p>
                <img src="${ctxMS }/imgs/down.png" class="am-vertical-align-middle">
            </div>
            <p class="title">报喜了</p>
            <div class="header-right am-fr am-vertical-align">
                <img src="${ctxMS }/imgs/search.png" class="am-vertical-align-middle">
            </div>
        </div>
        <div class="am-u-sm-12 slider">
            <div data-am-widget="slider" class="am-slider am-slider-a1" data-am-slider='{"directionNav":false, "height": auto}' >
              <ul class="am-slides">
                  <c:forEach items="${productVo.imageList}" var="c" varStatus="i">
                       <li><img src="${IMAGE_ROOT_PATH }${productVo.imageList[i.index]}"></li>
                   </c:forEach>
              </ul>
            </div>
        </div>
        <div class="am-u-sm-12 product-name">
            <div class="product-name-p">
                <p class="am-serif">${productVo.product.name }</p>	
            </div>    
        </div>
        <div class="am-u-sm-12 product-price">
            <div class="product-price-big">
               	${productVo.product.nowPrice }
            </div>
            <del class="product-price-sm">
                ${productVo.product.price }
            </del> 
        </div>
        <hr data-am-widget="divider" style="" class="am-divider am-divider-default" />
        <div class="am-u-sm-12 merchant">
            <p>${productVo.product.seller.name}</p>
        </div>
        <div class="am-u-sm-12 parameter am-cf" data-am-offcanvas="{target: '#parameter'}">
       		<p><span id="choose">已选</span><span class="choose-specs"></span><span class="choose-amount"></span></p>
            <img src="${ctxMS }/imgs/right.png">
        </div>
        <div id="parameter" class="am-offcanvas">
          <div class="am-offcanvas-bar am-offcanvas-bar-flip">
            <div class="am-offcanvas-content am-cf">
              <div class="am-u-sm-12 top-info">
                  <div class="parameter-img">
                      <img src="${IMAGE_ROOT_PATH }${productVo.product.picture}">
                  </div>
                  <div class="parameter-info">
                      <div>
                          <p class="parameter-name">${productVo.product.name }</p>
                      </div>
                      <del>${productVo.product.price }</del>
                      <p class="parameter-price">${productVo.product.nowPrice }</p>
                  </div>
              </div>
              <c:if test="${fn:length(productVo.specType) != 0 }">
	              <div class="am-u-sm-12 parameter-type">
	                  <p class="parameter-type-title">类型</p>
	                  <div class="parameter-type-list am-fl am-cf parameter-type-list-hide">
	                    <div class="parameter-list-main am-cf">
	                      <c:forEach items="${productVo.specType }" var="item">
				           		 <div class="parameter-div am-fl">
	                      		 	${item }
                     			 </div>
				          </c:forEach>
	                    </div>
	                  </div>
	                  <div class="more-type am-fl">
	                     	 更多
	                  </div>
	              </div>
	          </c:if>
              <c:if test="${fn:length(productVo.specSize) != 0 }">
	              <div class="am-u-sm-12 parameter-size">
	                  <p class="parameter-size-title">尺寸</p>
	                  <div class="parameter-size-list am-fl am-cf parameter-size-list-hide">
	                    <div class="parameter-list-main am-cf">
	                      <c:forEach items="${productVo.specSize }" var="item">
				           		<div class="parameter-div am-fl">
	                          		${item }
	                      		</div>
				          </c:forEach>
	                    </div>
	                  </div>
	                  <div class="more-size am-fl">
	                     	 更多
	                  </div>
	              </div>
	          </c:if>
	          <c:if test="${fn:length(productVo.specColor) != 0 }">
	              <div class="am-u-sm-12 parameter-color">
	                  <p class="parameter-color-title">颜色</p>
	                  <div class="parameter-color-list am-fl am-cf parameter-color-list-hide">
	                    <div class="parameter-list-main am-cf">
	                      <c:forEach items="${productVo.specColor }" var="item">
			                    <div class="parameter-div am-fl">
	                          		${item }
	                      		</div>	
			              </c:forEach>
	                    </div>
	                  </div>
	                  <div class="more-color am-fl">
	                   	   更多
	                  </div>
	              </div>
              </c:if>
              <div class="am-u-sm-12 parameter-num am-cf">
                  <p class="parameter-num-title">数量</p>
                  <div class="parameter-num-div more-num am-fl">
                      <a class="btn-del" id="minus">-</a>
                      <input type="text" class="fm-txt" value="${productVo.product.minSell == 0 ? '1' : productVo.product.minSell}" id="number">
                      <a class="btn-add" id="plus">+</a>
                  </div>
                  <p class="parameter-unit-title">单位:</p>
                  <div class="parameter-unit-list parameter-unit-list-hide">
                    <div class="parameter-unit-list-main">
                      <div class="parameter-unit-div">
                         ${productVo.product.unit }
                      </div>
                    </div>
                  </div>
              </div>
              <div class="am-u-sm-12 parameter-minSell am-cf">
                  <div class="parameter-minSell-list am-fl am-cf parameter-unit-list-hide">
                      <div class="parameter-minSell-div am-fl">
                        <span>(该商品最少</span><span id="minSellNum">${productVo.product.minSell == 0 ? '1' : productVo.product.minSell}</span><span>件起拍)</span>
                      </div>
                  </div>
              </div>
              <div class="addCart" id="addCartButton" data-rel="close">
               		 加入购物车
              </div>
            </div>
          </div>
        </div>
        <div class="am-u-sm-12 go-to-detail am-cf" data-am-modal="{target: '#detail-main'}">
            <p>查看详情</p>
            <img src="${ctxMS }/imgs/right.png">
        </div>
        <div class="am-popup" id="detail-main">
          <div class="am-popup-inner">
            <div class="am-popup-hd">
              <h4 class="am-popup-title">详情介绍</h4>
              <span data-am-modal-close class="am-close">&times;</span>
            </div>
            <div class="am-popup-bd">
                <div data-am-widget="tabs" class="am-tabs am-tabs-default">
                  <ul class="am-tabs-nav am-cf">
                      <li class="am-active"><a href="[data-tab-panel-0]">图文详情</a></li>
                      <li class=""><a href="[data-tab-panel-1]">规格参数</a></li>
                  </ul>
                  <div class="am-tabs-bd">
                      <div data-tab-panel-0 class="am-tab-panel detail-img am-active">
                       		${fns:unescapeHtml(productVo.product.productHTML) }
                      </div>
                      <div data-tab-panel-1 class="am-tab-panel ">
                      	<c:if test="${productVo.parameterList.size() == 0}">
                      		无参数
                      	</c:if>
                      	<c:if test="${productVo.parameterList.size() != 0}">
                      		<c:forEach items="${productVo.parameterList}" var="i">
	                        	<p>【${i.name }】：${i.value }</p>
	                        </c:forEach>
                      	</c:if>
                      	
                      </div>
                  </div>
                </div>
            </div>
          </div>
        </div>
        <div class="am-u-sm-12 guessing">
            <div class="am-u-sm-12 guessing-title">
                <p>猜你喜欢</p>
            </div>
            <c:forEach items="${hotRecommendProduct}" var="c">
                <div class="am-u-sm-4 guessing-list">
	                <div class="guessing-list-main">
	                	<a href="${ctx }/wx/product/detail?goodsId=${c.id }" >
	                    	<img src="${IMAGE_ROOT_PATH }/${c.picture}-px100">
	                    </a>
	                    <a href="${ctx }/wx/product/detail?goodsId=${c.id }" >
		                    <div class="guessing-list-name am-text-break">
		                        <p>${c.name }</p>
		                    </div>
	                    </a>
	                    <div class="price">
	                        <p>${c.nowPrice }</p>
	                    </div>
	                </div>
	            </div>
            </c:forEach>
        </div>
    </div>
    <div class="am-g menu-fixed">
        <div class="am-u-sm-2 active">
            <div class="icon home"></div>
            <p>首页</p>
        </div>
        <div class="am-u-sm-2">
            <div class="icon user"></div>
            <p>我的</p>
        </div>
        <div class="am-u-sm-2">
            <div class="icon classification"></div>
            <p>关注</p>
        </div>
        <div class="am-u-sm-2">
            <div class="icon cart">
              <i class="order-numbers" id="carNum">0</i>
            </div>
            <p>购物车</p>
        </div>
        
        <div class="am-u-sm-4 addCart" data-am-offcanvas="{target: '#parameter'}">
         	   加入购物车
        </div>
    </div>
    <div class="warning am-vertical-align">
        <p class="am-vertical-align-middle">您输入的信息有误</p>
    </div>
    <input type="hidden" id="specId" name="specId"/>
  	<input type="hidden" id="minSell" name="minSell" value="${productVo.product.minSell }"/>
  	<input type="hidden" id="productId" name="productId" value="${productVo.product.id }"/>
  	<input type="hidden" id="ctx" name="ctx" value="${ctx }"/>
  	
    <script type="text/javascript">
    	$(document).ready(function(){
    		$(".parameter-div").click(function() {
				var text = $.trim($(this).text());
				var curr = $(this).parents(".parameter-list-main").find(".parameter-select");
				var currValue = $.trim(curr.text());
				curr.removeClass("parameter-select");
				if (currValue != text) {
					$(this).addClass("parameter-select");
				} else {
					text = null;
				}
				var dataType = getClickType($(this));
				//处理当前选中属性
				dealSpec(text, dataType);
			});
    		chooseDefualtSpec();
    	});
    	
    	//判断点击的类型
    	function getClickType(obj) {
    		var o = obj.parent().parent().parent();
    		if (o.hasClass("parameter-color")) {
    			return 'color';
    		} else if (o.hasClass("parameter-size")) {
    			return 'size';
    		} else if (o.hasClass("parameter-type")) {
    			return 'type';
    		}
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
				specColor = $.trim($(".parameter-color .parameter-select").text());	
				specSize = $.trim($(".parameter-size .parameter-select").text());	
			} else if (dataType == "color") {
				specColor = text;
				specType = $.trim($(".parameter-type .parameter-select").text());	
				specSize = $.trim($(".parameter-size .parameter-select").text());	
			} else if(dataType == "size") {
				specSize = text;
				specType = $.trim($(".parameter-type .parameter-select").text());	
				specColor = $.trim($(".parameter-color .parameter-select").text());	
			}
			var flag = false;//是否找到规格标示
			for(var i=0; i<specJson.length; i++) {
				var spec = specJson[i];
				//禁用没有选择属性的参数
				if (isCurrentSpec(specType, specColor, specSize, spec)) {
					setPrice(spec.specPrice);
					setSpec(spec.id);
					setMinSell(spec.minSell);
					switchCartClass("active");
					setSpecStr(spec);
					flag = true;
				}
			}
			//找不到设置默认初始价格
			if(!flag) {
				var price = ${productVo.product.nowPrice};
				setPrice(price);
				setSpec(null);
				switchCartClass("disable");
				//设置选中规格为空
				$("#choose").text("请选择规格");
				$(".parameter .choose-specs").text("");
				$(".parameter .choose-amount").text("");
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
			$(".parameter-list-main .parameter-disable").removeClass("parameter-disable");
			$(".parameter-list-main .parameter-div").each(function() {
				var _text = $.trim($(this).text());
				var _dataType = getClickType($(this));
				
				if (_dataType == "type") {
					for(var j=0; j<activeType.length; j++) {
						if($.inArray(_text, activeType) == -1) {
							$(this).removeClass("parameter-select").addClass("parameter-disable");
						}
					}
				} else if (_dataType == "color") {
					for(var j=0; j<activeColor.length; j++) {
						if($.inArray(_text, activeColor) == -1) {
							$(this).removeClass("parameter-select").addClass("parameter-disable");
						}
					}
				} else if (_dataType == "size") {
					for(var j=0; j<activeSize.length; j++) {
						if($.inArray(_text, activeSize) == -1) {
							$(this).removeClass("parameter-select").addClass("parameter-disable");
						}
					}
				}
			});
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
    	
    	function switchCartClass(action) {
    		var o = $("#addCartButton");
    		if (action == "active") {
    			o.removeClass("addCartDisable");
    			o.addClass("addCart");
    			o.removeAttr("diabled", "disabled")
    		} else if (action == "disable") {
    			o.removeClass("addCart");
    			o.addClass("addCartDisable");
    			o.attr("diabled", "disabled")
    		}
    	}
    	function setPrice(price) {
    		if (price != null && '' != price) {
    			$(".product-price-big").text(price);
    		}
    	}
    	
    	function setSpec(specId) {
   			$("#specId").val(specId);
    	}
    	
    	function setMinSell(minSell) {
    		if (minSell != null && '' != minSell) {
    		    var curNum = $("#number").val();
    		    if (parseInt(curNum) < parseInt(minSell)) {
    		    	$("#number").val(minSell);
    		    	$("#minSell").val(minSell);
    		    	$("#minSellNum").val(minSell);
    		    }
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
    	function chooseDefualtSpec() {
    		var specJson = ${productVo.specJsonString};
			if(isEmpty(specJson)) {
				$(".choose-amount").text($("#number").val() + $(".parameter-unit .parameter-unit-div").text())
				return;
			}
			var spec = specJson[0];
			if (!isEmpty(spec.specType)) {
				$(".parameter-type .parameter-div").each(function() {
					if ($.trim($(this).text()) == $.trim(spec.specType)) {
						$(this).addClass("parameter-select");
						var cloneObj = $(this).clone(true);
						$(this).remove();
						$(".parameter-type .parameter-list-main").prepend(cloneObj);
					}
				});
			}
			if (!isEmpty(spec.specColor)) {
				$(".parameter-color .parameter-div").each(function() {
					if ($.trim($(this).text()) == $.trim(spec.specColor)) {
						$(this).addClass("parameter-select");
						var cloneObj = $(this).clone(true);
						$(this).remove();
						$(".parameter-color .parameter-list-main").prepend(cloneObj);
					}
				});
			}
			if (!isEmpty(spec.specSize)) {
				$(".parameter-size .parameter-div").each(function() {
					if ($.trim($(this).text()) == $.trim(spec.specSize)) {
						$(this).addClass("parameter-select");
						var cloneObj = $(this).clone(true);
						$(this).remove();
						$(".parameter-size .parameter-list-main").prepend(cloneObj);
					}
				});
			}
			
			//设置信息
    		if (!isEmpty(spec.specPrice)) {
    			$(".product-price .product-price-big").text(spec.specPrice);
    			if (!isEmpty(spec.minSell)) {
    				$("#number").val(spec.minSell);
    				$("#minSell").val(spec.minSell);
    				$("#minSellNum").val(spec.minSell);
    			}
    		}
    		setSpecStr(spec);
    		$("#specId").val(spec.id);
    	}
    	
    	function setSpecStr(spec) {
    		var specs = '';
    		if (!isEmpty(spec.specType)) {
    			specs += spec.specType + " ";
    		}
    		if (!isEmpty(spec.specSize)) {
    			specs += spec.specSize + " ";
    		}
    		if (!isEmpty(spec.specColor)) {
    			specs += spec.specColor;
    		}
    		$("#choose").text("已选");
    		$(".choose-specs").text(specs);
    		$(".choose-amount").text($("#number").val() + $(".parameter-unit .parameter-unit-div").text())
    	}
    </script>
    <script type="text/javascript" src="${ctxMS }/js/phone-detail.js"></script>
  </body>
</html>