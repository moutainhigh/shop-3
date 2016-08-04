<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!doctype html> 
<html>
<head>
    <meta name="decorator" content="cart_default_new"/>
    <title>订单确认</title>
    <link href="${ctxAssets }/stylesheets/shopcar1.css" rel="stylesheet" type="text/css">
    <link href="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/css/select2.min.css" rel="stylesheet" />
</head>
<body>
<div id="confirm-order">
	<form method="post" action="${ctx }/order/prepay">
	<div class="add-address">
	  <div class="title">1&nbsp;地址选择</div>
	  <div class="chose-add">
	    <c:forEach items="${addresses }" var="a">
	    <div class="address-div <c:if test="${a.isDefault eq 'y' }">address-select</c:if>" data-id="${a.id }" onclick="selectAddr(this);">
	      <div class="address-div-main" data-id="${a.id }">
	        <span class="addr_name">${a.name }</span>
	        <span class="addr_con">${a.provinceName }-${a.cityName }-${a.countyName } ${a.address }</span>
	        <span class="addr_num">${a.getMaskMobile() }</span>
	        <span class="btnEditAddress_new" style="<c:if test="${a.isDefault != 'y' }">display:none</c:if>" title="修改地址" data-id="${a.id }"><a href="javascript:void(0);" data-id="${a.id }" onclick="modifyAddr(this, event);">修改</a></span>
	        <span class="btnEditAddress_del" style="<c:if test="${a.isDefault != 'y' }">display:none</c:if>" title="删除地址" data-id="${a.id }"><a href="javascript:void(0);" data-id="${a.id }" onclick="delAddr(this, event);">删除</a></span>
	      </div>
	    </div>
	    </c:forEach>
	    <div class="clear"></div>
	  </div>
	  <div id="add-new-address-wrap" class="add-address-from" <c:if test="${not empty addresses }">style="display:none;"</c:if>>
	    <div class="add-address-list">
	      <div class="receiver-name clearfix-n">
	        <div class="input-name fl">
	          <label>收件人：<span class="tips">*</span></label>
	        </div>
	        <div class="input-box fl">
	          <input type="text" class="input" autocomplete="off" placeholder="请输入收件人" name="receiver_name" maxlength="20">
	        </div>
	      </div>
	      <p class="add-address-list-err"><img alt="" src="${ctxAssets }/image/warning.png">收件人不能为空</p>
	    </div>
	    <div class="add-address-list">
	      <div class="receiver-address clearfix-n">
	        <div class="input-name fl">
	          <label>收货地址：<span class="tips">*</span></label>
	        </div>
	        <select name="provinceId" class="select2" autocomplete="off" style="width:180px;">
			  <option value="">--请选择--</option>
			  <c:forEach items="${provinces }" var="p"><option value="${p.id }">${p.name }</option></c:forEach>
			</select>
			<select name="cityId" autocomplete="off" class="select2" style="width:180px;"><option value="">--请选择--</option></select>
			<select name="countyId" autocomplete="off" class="select2" style="width:180px;"><option value="">--请选择--</option></select>
	      </div>
	      <p class="add-address-list-err"><img alt="" src="${ctxAssets }/image/warning.png">请选择完整的地址信息</p>
	    </div>
	    <div class="add-address-list">
	      <div class="receiver-xiangxidizi clearfix-n">
	        <div class="input-name fl">
	          <label>详细地址：<span class="tips">*</span></label>
	        </div>
	        <div class="input-box fl">
	          <input type="text" name="addressDetail"autocomplete="off"  maxlength="20" placeholder="请填写详细地址">
	        </div>
	      </div>
	      <p class="add-address-list-err"><img alt="" src="${ctxAssets }/image/warning.png">详细地址不能为空</p>
	    </div>
	    <div class="add-address-list">
	      <div class="receiver-phone clearfix-n">
	        <div class="input-name fl">
	          <label>手机号码：<span class="tips">*</span></label>
	        </div>
	        <div class="input-box fl cellphone-box">
	          <input type="text" name="mobile" autocomplete="off" maxlength="20" >
	        </div>
	        <div class="input-name guding-name fl">
	          <label class="">或固定电话：</label>
	        </div>
	        <div class="input-box fl guding-quhao">
	          <input type="text" name="telArea" autocomplete="off" maxlength="20" placeholder="区号">
	        </div>
	        <div class="fl" style="margin: 0 5px;">-</div>
	        <div class="input-box fl guding-num">
	          <input type="text" name="telNumber" autocomplete="off"  maxlength="20" placeholder="电话号码">
	        </div>
	        <div class="fl" style="margin: 0 5px;">-</div>
	        <div class="input-box fl guding-fenji">
	          <input type="text" name="telExt" autocomplete="off"  maxlength="20" placeholder="分机">
	        </div>
	      </div>
	      <p class="add-address-list-err"><img alt="" src="${ctxAssets }/image/warning.png">请填写11位手机号码</p>
	    </div>
	    <div class="clearfix-n" style="margin-left: 75px;padding-top: 10px;">
	      <a href="javascript:void(0);" data-type="1" class="submit_btn">确定</a>
	    </div>
	  </div>
	  <div class="add-new-address" <c:if test="${empty addresses }">style="display:none;"</c:if>>
	  	<a href="javascript:;" class="add-new-address-btn">+ 使用新的地址</a>
	  	<div class="add-new-pop">
	  	</div>
  		<div id="add-new-address-prop" class="add-address-from add-new-pop-main">
  			<div class="add-new-pop-main-title">添加新的地址</div>
           <div class="add-address-list">
             <div class="receiver-name clearfix-n">
               <div class="input-name fl">
                 <label>收件人：<span class="tips">*</span></label>
               </div>
               <div class="input-box fl">
                 <input id="id" name="id" value="" type="hidden">
                 <input type="text" class="input" autocomplete="off" placeholder="请输入收件人" name="receiver_name" maxlength="20">
               </div>
             </div>
             <p class="add-address-list-err"><img alt="" src="${ctxAssets }/image/warning.png">收件人不能为空</p>
           </div>
           <div class="add-address-list">
		      <div class="receiver-address clearfix-n">
		        <div class="input-name fl">
		          <label>收货地址：<span class="tips">*</span></label>
		        </div>
		        <select name="provinceId" autocomplete="off" class="select2" style="width:140px;">
				  <option value="">--请选择--</option>
				  <c:forEach items="${provinces }" var="p"><option value="${p.id }">${p.name }</option></c:forEach>
				</select>
				<select name="cityId" autocomplete="off" class="select2" style="width:140px;"><option value="">--请选择--</option></select>
				<select name="countyId" autocomplete="off" class="select2" style="width:140px;"><option value="">--请选择--</option></select>
		      </div>
		      <p class="add-address-list-err"><img alt="" src="${ctxAssets }/image/warning.png">请选择完整的地址信息</p>
		   </div>
		   <div class="add-address-list">
		      <div class="receiver-xiangxidizi clearfix-n">
		        <div class="input-name fl">
		          <label>详细地址：<span class="tips">*</span></label>
		        </div>
		        <div class="input-box fl">
		          <input type="text" autocomplete="off" name="addressDetail" maxlength="20" placeholder="请填写详细地址">
		        </div>
		      </div>
		      <p class="add-address-list-err"><img alt="" src="${ctxAssets }/image/warning.png">详细地址不能为空</p>
		   </div>
           <div class="add-address-list">
             <div class="receiver-phone clearfix-n">
               <div class="input-name fl">
                 <label>手机号码：<span class="tips">*</span></label>
               </div>
               <div class="input-box fl cellphone-box">
                 <input type="text" autocomplete="off" name="mobile" maxlength="20">
               </div>
               <div class="input-name guding-name fl">
                 <label class="">或固定电话：</label>
               </div>
               <div class="input-box fl guding-quhao">
                 <input type="text"  autocomplete="off" name="telArea" maxlength="20" placeholder="区号">
               </div>
               <div class="fl" style="margin: 0 5px;">-</div>
               <div class="input-box fl guding-num">
                 <input type="text" autocomplete="off" name="telNumber" maxlength="20" placeholder="电话号码">
               </div>
               <div class="fl" style="margin: 0 5px;">-</div>
               <div class="input-box fl guding-fenji">
                 <input type="text" name="telExt" autocomplete="off" maxlength="20" placeholder="分机">
               </div>
             </div>
             <p class="add-address-list-err"><img alt="" src="${ctxAssets }/image/warning.png">请填写11位手机号码</p>
           </div>
           <div class="clearfix-n" style="margin-left: 75px;padding-top: 10px;">
             <a href="javascript:;" data-type="2" class="submit_btn">确定</a>
             <a href="javascript:;" class="cancel_btn">取消</a>
           </div>
         </div>
	  </div>
	</div>
	<div class="cart_left">
	  <div class="option" id="prefer_delivery_day">
	    <div class="title">
	      2&nbsp;送货时间 
	      <span style="color:#666666;font-size:12px;font-weight:normal;font-family:'宋体';">送货时间仅作参考，快递公司会尽量满足您的要求</span>
	    </div>
	    <div class="content">
	      <c:forEach items="${ddList }" var="dd" varStatus="s">
	      <div class="option_box <c:if test="${s.index == 0 }">selected</c:if>">
	        <input id="delivery_day_${dd.code }" name="prefer_delivery_day" type="radio" value="${dd.code }" <c:if test="${s.index == 0 }">checked="checked"</c:if>>
	        <label for="delivery_day_${dd.code }">${dd.name }</label>
	        <div class="clear"></div>
	      </div>
	      </c:forEach>
	      <div class="clb"></div>
	    </div>
	  </div>
	</div>
	<div class="cart_left relative" style="*z-index: 5;">
	  <div class="option cart_products">
	    <div class="title">
	      3&nbsp;商品清单
	    </div>
	    <c:forEach items="${carts }" var="item">
		    <div class="product-table-list">
		    	<div class="product-table-left">
		    		<p>商家:&nbsp;${item.value[0].sellerName }</p>
<!-- 		    		<c:forEach items="${expressList }" var="e" varStatus="status"> -->
<!-- 			            <div class="option_box"> -->
<!-- 			              <input id="express_${e.code }" type="radio" class="choose_delivery J_Express" name="express" value="${e.fee }" data-id="${e.id }" data-code="${e.code }" <c:if test="${status.index == 0 }">checked="checked"</c:if>> -->
<!-- 			              <label for="express_${e.code }" id="label_express_${e.code }" class="J_label_express">${e.name }（${e.fee }元）</label> -->
<!-- 			              <div class="clear"></div> -->
<!-- 			            </div> -->
<!-- 		            </c:forEach> -->
			        <%-- <span class="express_num">¥<span id="expressFee" class="exp_num J_Final_Delivery">${expressList[0].fee }</span></span>
			        <span class="express_tit">运费：</span> --%>
		    	</div>
		    	<table class="product-table table table-bordered">
	<!-- 		      <thead>
			        <tr class="active">
			          <th width="400" class="text-left">商品</th>
			          <th width="110">数量</th>
			          <th width="140">单价</th>
			          <th width="140">小计</th>
			        </tr>
			      </thead> -->
			      <tbody>
			        <c:forEach items="${item.value }" var="cart">
			        <tr>
			        <td width="200" class="text-left pro-list-td">
			            <img alt="" src="${IMAGE_ROOT_PATH }/${cart.productImage}">
			          </td>
			          <td width="400" class="text-left pro-list-td">
			            <%-- <p>${cart.productName }<!-- <em>型号：T028.210.11.057.00 容量：其他</em> --></p> --%>
			            <p>${cart.productName }
			            <c:if test="${cart.activityType eq 'c'}">
						<span style="display: inline-block;border-radius: 3px;background-color: #ED415B;color: #F5F5F5;padding: 0 2px;">${cart.activityName }</span>
						</c:if>
			            <br/><em>${cart.specName }</em></p>
			          </td>
			          
			          <td width="140" class="danjia">
			            <p><em>¥</em>${cart.nowPrice }</p>
			          </td>	
			          <td width="110" class="pro-list-num">x${cart.quantity }</td>
			          <%-- <td width="140" class="xiaoji">
			            <p><em>¥</em>${cart.nowPrice * cart.quantity }</p>
			          </td> --%>
			          <c:if test="${cart.activityId != null }">
			          <td width="300" class="xiaoji">
			            <p><em>¥</em>${cart.nowPrice * cart.quantity }</p>
			            <span style="display: inline-block;padding: 0 2px;background-color: #FF811A;border-radius: 3px;color: white; margin-bottom: 3px;">免运费</span><br>
			            <span style="display: inline-block;padding: 0 2px;background-color: #FF811A;border-radius: 3px;color: white;">促销</span>
			          </td>
			          </c:if>
			          
			          <c:if test="${cart.activityId == null }">
			          <td width="140" class="xiaoji">
			            <p><em>¥</em>${cart.nowPrice * cart.quantity }</p>
			            <p class="discount" style="display: inline-block;padding: 0 3px; font-size: 12px;border-radius: 3px;background-color: #FF811A;color: white;">会员折扣<em>¥</em>${cart.discountPrice * cart.quantity }</p>
			            <p class="fee" style="display: inline-block;padding: 0 3px; font-size: 12px;border-radius: 3px;background-color: #FF811A;color: white; margin-top: 3px;">运费<em>¥</em>${cart.fee }</p>
			          </td>
			          </c:if>
			          
			        </tr>
			        </c:forEach>
			        <!-- <tr>
			          <td colspan="4" class="text-left pro-list-td">
			            
			          </td>
			        </tr> -->
			        <!-- <tr style="">
			          <td colspan="4" class="count" style="padding:10px 0 10px 30px;">
			              <div class="option_box express_wrap">
			                <span class="express_num" >-¥<b class="J_Delivery_reduction">5</b></span>
			                <span class="express_tit"><span>运费减免</span><b class="blue" style="font-size: 12px;font-weight: normal;">（已享受新用户首单全场满39元包邮）</b>：</span>
			              </div>d
			          </td>
			        </tr> -->
			      </tbody>
			    </table>
			    <div class="clear"></div>
		    </div>
		</c:forEach>
		<div class="product-table-list">
	    	<div class="product-table-left">
	    		<p>备&nbsp&nbsp&nbsp&nbsp注:</p>
	    	</div>
	    	<div>
	    		<textarea id="remark" name="remark" rows="2" style="width: 70%;"></textarea>
	    	</div>
		</div>
	    
	    <div class="total-price">
	      <p style="padding-top: 7px;">总商品金额：<em style="font-size: 16px;">¥${totalPrice }</em></p> 
	      <c:if test="${totalDiscount != '0.00'}">
	      	<p style="padding-top: 7px;">${account.accountRank.name }折扣金额：<em style="font-size: 16px;">¥${totalDiscount }</em></p> 
	      </c:if>
	      <p style="padding-top: 7px;">总运费：<em style="font-size: 16px; ">¥${totalFreight }</em></p> 
	      <p>应付总额：<em>¥${totalPay }</em></p> <%-- <p>运费：<em>¥${totalPrice }</em></p> --%>
	      
	      <div class="clear"></div>
	    </div>
	  </div>  
	</div>
	<div class="cart_left relative cart_left_border" style="*z-index: 5;">
	  <div class="title">4&nbsp;支付方式</div>
	  <div class="zhifufangshi">
	    <ul>
	      <c:forEach items="${payMap }" var="p" varStatus="status">
	      <li class="getways getopen">
	        <input type="radio" name="pay" id="pay_${p.key }" checked="checked">
	        <label class="tit" for="pay_${p.key }"><c:if test="${p.key eq 'COD' }">货到付款</c:if><c:if test="${p.key eq 'ONLINE' }">在线支付</c:if></label>
	        <ul class="third_ul clearfix g_ul">
	          <c:forEach items="${p.value }"  var="pay">
	          <li>
	            <div class="bd_wrap">
	                <input type="radio" value="${pay.code }" checked="<c:if test="${p.value.size()==1 }">checked</c:if>" name="gateway" id="check-${pay.code }">
	                <label class="bg ${pay.code }" for="check-${pay.code }"></label>
	            </div>
	          </li>
	          </c:forEach>
	        </ul>
	      </li>
	      </c:forEach>
	    </ul>
	  </div>
	</div>
	<div class="sure_payinfo_wrap">
	  <div class="confirm_pay_box">
	    <div class="confirm_pay clearfix">
	      <div class="confirm_left">
	        <a href="${ctx }/cart" class="btn_grey_small">返回修改购物车</a>
	      </div>
	      <div class="confirm_right">
	        <div class="clearfix">
	          <input type="submit" id="btn_confirm_pay" class="btn_pink_big" value="确认交易" onclick="return check_pay();">
	          <div class="price_sum">
	            应付总额：<span class="total_count">¥<span id="cart_total">${totalPay }</span></span>
	          </div>
	        </div>
	      </div>
	      <div class="clear"></div>
	    </div>
	  </div>
	</div>
	
	<input type="hidden" id="addressId" name="addressId" value="${addressId }"/>
	<input type="hidden" id="expressCode" name="expressCode" value="${expressCode }"/>
	<input type="hidden" id="payCode" name="payCode" value="${pay.code }"/>
	<input type="hidden" id="deliveryDayCode" name="deliveryDayCode" value="${deliveryDayCode }">
	<input type="hidden" id="totalPrice" name="totalPrice" value="${totalPrice }"/>
	<input type="hidden" id="token" name="token" value="${token }"/>
	<input type="hidden" id="_token" name="_token" value="${_token }"/>
	<input type="hidden" id="ctx" name="ctx" value="${ctx }"/>
	</form>
</div>
<content tag="local_script">
<script src="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/js/select2.min.js"></script>
<script type="text/javascript" src="${ctxAssetsJS }/javascripts/shopcar1.js"></script>
<script type="text/javascript">
</script>
</content>
</body>
</html>