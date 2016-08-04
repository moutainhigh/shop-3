<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page
	import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="decorator" content="account_default_new" />
<title>${accountTitle }</title>
</head>
<body style="background: white; width: 960px; margin: 0 auto;">
	<div class="insider_content">
		<c:if test="${account.smallPicture != null and not empty account.smallPicture }">
		<a href="${ctx }/i/account/modifyPic"><img class="insider_pic" src="${IMAGE_ROOT_PATH }/${account.smallPicture }"></a>
		</c:if>
		<c:if test="${account.smallPicture == null or empty account.smallPicture }">
		<a href="${ctx }/i/account/modifyPic"><img class="insider_pic" src="${ctxStatic }/images/avatar.png"></a>
		</c:if>
		<div class="insider_name">${account.mobile }</div>
		<div class="insider_member" style="line-height: 26px; font-size: 16px; font-weight: bold;">${account.account }</div>
		<div class="insider_useful">
			升级到黄金会员，可享受 <span class="insider_leftover">满100返5元券、换购特惠、试用专属产品</span>等5项特权
		</div>
	</div>
	<div class="insider_mostly insider_space" style="background: #fff;">
		<div class="insider_title">升级进度</div>
		<div class="insider_content">
			<div class="insider_left">
				<div class="insider_left_con">
					<div class="insider_keep">
						<span class="insider_arrowtop"></span> 升级到黄金会员
					</div>
					<div class="insider_keep_con">
						只需继续在购物 <span class="bold">399 元</span>, 即可拥有黄金会员资格
					</div>
				</div>
				<div class="insider_vessel">
					<span class="insider_leftTop"></span> 
					<span class="insider_rightTop"></span> 
					<span class="insider_leftBottom"></span>
					<span class="insider_rightBottom"></span> 
					<span style="display: inline-block;">升级到黄金会员 后立享以下5大特权：</span>
					<ul class="insider_sort">
						<li>满100返5元现金券</li>
						<li>黄金会员专享优惠专场</li>
						<li>黄金会员专享换购特惠</li>
						<li>优先体验部分试用产品</li>
						<li>订购专属</li>
					</ul>
				</div>
			</div>
			<div class="insider_right">
				<div class="insider_addUp">累计购物</div>
				<div class="insider_price">¥0</div>
				<div class="insider_useful">
					付款生效30天后金额计入 <br> 您的累计购物额度（不包括快递/红包/现金券/退货相应金额） 
				</div>
			</div>
		</div>
	</div>
	<div class="insider_mostly insider_space" style="background: #fdfdfe;">
		<div class="insider_title">会员升降级规则</div>
		<div class="insider_privilege">
			<ul class="insider_priUl">
				<li>
					<div class="insider_addUp">有哪些会员等级？</div>
					<div class="insider_explain">目前设有普通会员、黄金会员、白金会员、钻石会员四个等级。</div>
				</li>
				<li>
					<div class="insider_addUp">会员等级的标准是什么？</div>
					<div class="insider_explain" style="padding-top: 5px;">
						普通会员：注册即可获得； <br> 
						黄金会员：累计在有效消费（注，下同）399元即可获得； <br>
						白金会员：累计在有效消费1299元即可获得。 <br> 
						钻石会员：累计在有效消费9999元即可获得。 <br>
						注：快递费、红包、现金券、退货费，不包括在累计购物金额中。由于承诺30天无条件退货，订单付款成功（货到付款为发货后）30天后才会被计入累计购物额中。
					</div>
				</li>
				<li style="border: none;">
					<div class="insider_addUp">不同的会员等级都有什么优惠？</div>
					<div class="insider_explain">
						<table class="insider_tier">
							<tbody>
								<tr>
									<td width="20%">普通会员</td>
									<td width="23%">黄金会员</td>
									<td width="23%">白金会员</td>
									<td width="34%">钻石会员</td>
								</tr>
								<tr>
									<td>- 享受每日超值优惠产品；</td>
									<td>- 满100返5元现金券（无启用金额；买越多，返越多） <br>
										- 黄金会员专享优惠专场 <br>
										- 黄金会员专享换购特惠 <br> 
										- 优先体验部分试用产品 <br> 
										- 订购专属 <br>
										- 最多短信订阅10种商品 </td>
									<td>- 满100返10元现金券（无启用金额；买越多，返越多） <br> 
										- 白金会员专享优惠专场 <br>
										- 白金会员专享换购特惠 <br> 
										- 优先体验部分试用产品 <br> 
										- 订购专属 <br>
										- 最多短信订阅20种商品 </td>
									<td>- 全场包邮（除海淘直邮，海淘直邮只减5元运费） <br> 
										- 满100返10元现金券（无启用金额；买越多，返越多） <br> 
										- 钻石会员专享优惠专场 <br> 
										- 钻石会员专享换购特惠 <br> 
										- 优先体验所有试用产品 <br> 
										- 订购专属 <br>
										- 实体钻石会员卡 <br> 
										- 生日特权礼包 <br> 
										- 线下活动邀请 <br>
										- 最多短信订阅50种商品 </td>
								</tr>
							</tbody>
						</table>
					</div>
				</li>
				<li style="border: none;">
					<div class="insider_addUp">会员的保级/降级规则</div>
					<div class="insider_explain">
						<div style="padding-top: 5px;">
							普通会员： <br>
							<p class="rule_section">- 成为普通会员，终身有效</p>
						</div>
						<div style="padding-top: 5px;">
							黄金会员： <br>
							<p class="rule_section">
								- 保级：每个自然月在购物一次 <br> 
								- 降级：当月未在购物，下月1日由黄金会员降级为普通会员 
							</p>
						</div>
						<div style="padding-top: 5px;">
							白金会员： <br>
							<p class="rule_section">
								- 保级：每个自然月保持购物100元，且累计退货款金额不超过累计购物金额的10% <br> 
								- 降级：当月未在购物满100元，下月1日由白金会员降级为黄金会员；当累计退货款金额超过累计购物金额10%，次日将会由白金会员降级为黄金会员
							</p>
						</div>
						<div style="padding-top: 5px;">
							钻石会员： <br>
							<p class="rule_section">
								- 保级：每个自然月保持购物500元，且累计退货款金额不超过累计购物金额的10% <br> 
								- 降级：当月未在购物满500元，下月1日由钻石会员降级为白金会员；当累计退货款金额超过累计购物金额10%，次日将会由钻石会员降级为白金会员
							</p>
						</div>
					</div>
				</li>
			</ul>
			<a class="btn_mid_pink insider_button" href="#" target="_blank">查看更多会员常见问题</a>
		</div>
	</div>
	</div>
	<!--会员等级-->
</body>
</html>