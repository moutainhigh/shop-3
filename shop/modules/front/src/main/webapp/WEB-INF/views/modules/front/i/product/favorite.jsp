<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="decorator" content="account_default_new" />
<title>${accountTitle }</title>
<content tag="local_script">
<script type="text/javascript">
$(function(){
	$('.fav_mall').mouseover(function(){
		$(this).find('.p_like').show();
	}).mouseout(function(){
		$(this).find('.p_like').hide();
	});
	$('.btnunlike').click(function(){
		var t = window.confirm("是否从我的收藏中删除?");
		if(t == true) {
			var id = $(this).attr('data-id');
			$.post('${ctx}/i/favorite/delete', {
				id: id
			}, function(data){
				if(data.s == 0) {
					window.location.reload();
				} else {
					alert(data.m);
				}
			});
		}
	});
	$('.hp_prev,.hp_next').click(function(){
		var pageNo = $("#pageNo").val();
		var first = '${page.first}';
		var last = '${page.last}';
		var next = $(this).hasClass('hp_next');
		if((next && Number(pageNo) < Number(last)) || (!next && Number(pageNo) > Number(first))) {
			if(next) {
				$('#pageNo').val(Number(pageNo) + 1);
			} else {
				$('#pageNo').val(Number(pageNo) - 1);
			}
			document.getElementById('searchForm').submit();
		} 
	});
});
</script>
</content>
</head>
<body style="background: white; width: 960px; margin: 0 auto;">
	<div class="notice_content">收藏心仪的产品或品牌，方便你随时找到它们，也有助于根据你的收藏给你更加贴心的推荐</div>
	<!-- <div class="filter">
		<a href="#" class="curr">我收藏的产品</a> <a href="#">我收藏的品牌</a>
	</div> -->
	<form id="searchForm" action="${ctx }/i/favorite" style="display: none;">
		<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize }"/>
		<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo }"/>
	</form>
	<c:if test="${empty page.list }"><div class="null_info"><h2>您还没有收藏任何产品喔！</h2></div></c:if>
	<c:if test="${not empty page.list }">
	<div class="filter">
		<a class="curr" href="javascript:void(0);">我收藏的产品</a>
	</div>
	<div id="fav_product_list">
		<div class="fav_product_container faved">
			<div class="fav_product_head">
				<span class="fph_tit"> 您共收藏了 <label class="pink">${page.total }</label>个产品</span>
				<div class="head_page">
					<div style="float: left; display: inline; margin-right: 5px;">
						<span class="pink">${page.pageNo }</span>/${page.last }页
					</div>
					<a href="javascript:void(0);" class="hp_prev"></a> <a href="javascript:void(0);" class="hp_next"></a>
				</div>
			</div>
			<div class="fav_product_list">
				<ul>
					<c:forEach items="${page.list }" var='f'>
					<li class="fav_mall" style="border: 3px solid #FFF;">
						<a href="${ctx }/product/${f.productId}" class="img_wrap" target="_blank"> 
							<img src="${IMAGE_ROOT_PATH}/${f.product.picture}-px350"/>
						</a>
						<p class="pro_tit">
							<a title="${f.product.name }" href="${ctx }/product/${f.productId}" target="_blank"> <em style="color: red"> 4.9折/</em> ${f.product.name }</a>
						</p> 
						<a class="pro_price" href="${ctx }/product/${f.productId}" target="_blank"> 
							<strong><span>¥</span>${f.product.nowPrice }</strong> <del>￥${f.product.price }</del>
							<c:if test="${f.product.stock == 0 }"><label style="background:#999999;">卖光了</label></c:if>
							<c:if test="${f.product.stock > 0 }"><label>商城在售</label></c:if>
						</a>
						<p class="p_like" style="display: none;">
							<a class="btnlike liked"> <span class="ilike_text">已收藏</span>
								<span style="color: #999;">(<span class="ilike_num">${f.product.favNum }</span>)
							</span>
							</a> <a class="btnunlike" href="javascript:;" data-id="${f.id }">取消收藏</a>
						</p>
					</li>
					</c:forEach>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
	</div>
	</c:if>
</body>
</html>