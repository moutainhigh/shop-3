<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="Cache-Control" content="no-store" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <title>
        <sitemesh:title/> - 报喜了
    </title>
    <link href="${ctxStatic }/css/usercenter.css" rel="stylesheet" type="text/css">
    <link href="${ctxStatic }/css/style.css" rel="stylesheet" type="text/css">
    <link href="${ctxStatic }/css/all.css" rel="stylesheet" type="text/css">
    <link href="${ctxStatic }/css/common.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="${ctxStaticJS }/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${ctxStaticJS }/js/common.js"></script>
    <sitemesh:head/>
</head>
<body class="full">
<div id="Head">
    <div id="headerTopArea" class="headerTopAreaBox">
        <div class="headerTopArea">
            <div class="headerTop">
                <div class="headerTopLeft">
                    <div class="loginArea">
                        <div id="append_parent">
                        </div>
                        <shiro:guest>欢迎光临本店！
                        <span><a href="${ctx }/login" class="track">&nbsp;请登录&nbsp;</a><a href="${ctx }/regist" class="track">
                            &nbsp;快速注册&nbsp;</a></span>
                        </shiro:guest>
                        <shiro:user>欢迎你，
                        <span><a href="${ctx }/i/order" class="track">&nbsp;<shiro:principal property="username"/>&nbsp;</a>[<a href="${ctx }/logout" class="track">
                            &nbsp;退出&nbsp;</a>]</span>
                        </shiro:user>
                        
                    </div>
                </div>
                <div class="headerTopRight">

                    <div id="vanclMap">
                        <a href="${ctx }/i/favorite" class="mapDropTitle track mapTitle">我的收藏</a>
                    </div>
                    <div id="vanclMap">
                        <a href="${ctx }/i/order" class="mapDropTitle track mapTitle">订单查询</a>
                    </div>
                    <div id="myVancl" class="active">
                        <a class="mapDropTitle track" href="javascript:void(0);" target="_blank">我的婚庆<i></i></a>
                        <div class="mapDropListBox">
                            <ul class="mapDropList">
                                <li><a rel="nofollow" href="${ctx }/i/order" class="track">我的订单</a></li>
                                <li><a rel="nofollow" href="${ctx }/i/membership" class="track">我的会员</a></li>
                                <li><a rel="nofollow" href="${ctx }/i/favorite" class="track">我的收藏</a></li>
                                <li><a href="${ctx }/i/subscribe" class="track">我的订阅</a></li>
                            </ul>
                        </div>
                    </div>
                    <div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="LogoSearchBar" id="logoArea">
        <div class="logoSearchSubnavArea">
            <h1 class="logoArea">
                <a href="#" class="track"><img src="${ctxStatic }/images/logo.gif"></a>
            </h1>

            <div class="searchAreaBlock">
                <div class="menuTopRight">
                    <div id="shoppingCarNone" class="active">
                        <p>
                            <a class="shopborder track" href="#" id="shoppingcar_link"><i><img
                                    src="${ctxStatic }/images/cart.gif"></i>去购物车结算<span
                                    car_product_total="shoppingCar_product_totalcount">0</span></a><s></s>
                        </p>
                        <script>
                            var num = 0;
                            $("#nums").text(num);
                        </script>
                        <div class="shopDropList">
                            <div class="shopnopru">
                                <div class="SCtotalpageno">
                                    购物车中还没有商品，<br>
                                    快去挑选心爱的商品吧
                                </div>
                                <div class="SCtotalpageBottom">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        
        </div>
    </div>
    <div class="nav_wrap">
        <div class="mainNavBox" id="mainNavBox">
            <div class="header_bottom">
                <div class="channel_nav_box">
                    <div class="channel_nav_list_wrap">
                        <ul class="channel_nav_list">
                            <li class="current"><a href="#" class="home">首页</a></li>
                            <li class="haitao_li"><a href="#">道具商城</a></li>
                            <!-- <li class="gif_301_wrap"><a target="_blank" href="#">团购</a></li>
                            <li class=""><a href="#">积分商城<b></b></a></li> -->
                        </ul>
                        <div style="display: none;" class="header_pop_subAtc box-shadow" id="header_pop_subAct">
                            <div class="mz_imglist">
                                <a target="_blank" href="#"></a>
                                <a target="_blank" href="#"></a>
                            </div>
                        </div>
                        <!--导航icon-->
                        <ul id="icon_wrap" class="icon_Wrap">
                            <li>
                                <div class="divlist divlist01">
                                    <a href="#"><span class=""></span><b>品牌防伪码</b></a>
                                </div>
                            </li>
                            <li>
                                <div class="divlist divlist02">
                                    <a href="#"><span class="th"></span><b>美妆30天无理由退货</b></a>
                                </div>
                            </li>
                            <li>
                                <div class="divlist divlist03">
                                    <a href="#"><span class="by"></span><b>美妆满2件或299包邮</b></a>
                                </div>
                            </li>
                        </ul>
                        <!--导航icon end-->
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--设置账户信息-->
<div class="profile">
    <div class="nav">
        <div class="notice">
        	<c:if test="${account.smallPicture != null and not empty account.smallPicture }">
			<a href="${ctx }/i/account/modifyPic" target="_blank" style="border-bottom: 0;padding:0;"><img src="${IMAGE_ROOT_PATH }/${account.smallPicture }" alt=""></a>
			</c:if>
			<c:if test="${account.smallPicture == null or empty account.smallPicture }">
			<a href="${ctx }/i/account/modifyPic" target="_blank" style="border-bottom: 0;padding:0;"><img src="${ctxStatic }/images/avatar_small.png" alt=""></a>
			</c:if>
            <p class="nickname"><a style="padding-left:0;line-height: 1.8;color:#ed145b" href="#" target="_blank"><shiro:principal property="username"/></a></p>
            <p><a style="padding-left:0;line-height: normal;" href="#" target="_blank">普通会员</a></p>
            <p class="uid">用户ID: <shiro:principal property="id"/></p>
        </div>
        <h2><b></b>我的婚庆</h2>
        <a href="${ctx }/i/order" class="list"><b></b>我的订单</a>
        <a href="${ctx }/i/product/favorite" class="fav"><b></b>我的收藏</a>
<!--         <a href="${ctx }/i/membership" class="member"><b></b>我的会员等级</a> -->
        <a href="${ctx }/i/account/subscribe" class="subscribe"><b></b>订阅邮件短信</a>
        <h2><b></b>管理个人账户</h2>
        <a href="${ctx }/i/account/balance" class="balance"><b></b>我的余额</a>
        <a href="${ctx }/i/account/profile" class="settings"><b></b>设置账户信息</a>
        <a href="${ctx }/i/account/password" class="password"><b></b>修改密码</a>
        <a href="${ctx }/i/account/mobile" class="bind"><b></b>绑定手机</a>
        <a href="${ctx }/i/account/addresses" class="addr"><b></b>管理收货地址</a>
        <script type="text/javascript">
        $(function(){
        	var _self = window.location.href;
        	$('.nav a').each(function(){
        		var _this = $(this);
        		//log.info(_this.attr('href'));
        		if(_self.indexOf(_this.attr('href')) >= 0) {
        			_this.addClass('selected');
        		}
        	});
        })
        </script>
    </div>
    <h1 id="title">${accountTitle }</h1>
    <div class="content"><sitemesh:body/></div>
</div>
</div>
<div id="footer" class="footer">
    <div id="footer_textarea">
        <div class="footer_top">
            <div class="footer_con footer_credit" id="footer_credit">
                <a href="#" class="foot_link mostmall" target="_blank" rel="nofollow">
                    <span class="corn"></span><span class="con"><b>最大美妆电商</b></span>四千万用户信赖
                </a>
                <a href="#" class="foot_link quality" target="_blank" rel="nofollow">
                    <span class="corn"></span><span class="con"><b>成功在美上市</b></span>股票代码NYSE:JMEI
                </a>
                <a href="#" class="foot_link back" rel="nofollow" target="_blank">
                    <span class="corn"></span><span class="con"><b>品牌防伪码</b></span>支持品牌官网验真
                </a>
                <a href="#" class="foot_link depot" target="_blank" rel="nofollow">
                    <span class="corn"></span><span class="con"><b>30天无理由退货</b></span>只要不满意无理由退货
                </a>
                <a href="#" class="foot_link consignment" target="_blank" rel="nofollow">
                    <span class="corn"></span><span class="con"><b>百万用户口碑</b></span>帮你只选对的,不选贵的
                </a>
                <a href="#" class="foot_link packaging" target="_blank" rel="nofollow">
                    <span class="corn"></span><span class="con"><b>订单闪电发货</b></span>1.5小时订单急速出库
                </a>
                <a href="#" class="foot_link confide" target="_blank" rel="nofollow">
                    <span class="corn"></span><span class="con"><b>大牌明星热荐</b></span>打造精致明星脸
                </a>
            </div>
        </div>
        <div class="footer_top_sen">
            <div class="footer_con footer_links" id="footer_links">
                <ul class="linksa">
                    <li class="links">服务保障</li>
                    <li><a href="#" target="_blank" rel="nofollow">品牌真品防伪码</a></li>
                    <li><a href="#" target="_blank" rel="nofollow">100%正品保证</a></li>
                    <li><a href="#" target="_blank" rel="nofollow">30天无条件退货</a></li>
                    <li><a href="#" target="_blank" rel="nofollow">7×24小时客服服务</a></li>
                    <li><span class="footer_zcemail">总裁邮箱ceo@jumei.com</span></li>
                </ul>
                <ul class="linksb">
                    <li class="links">使用帮助</li>
                    <li><a href="#" target="_blank" rel="nofollow">新手指南</a></li>
                    <li><a href="#" target="_blank" rel="nofollow">常见问题</a></li>
                    <li><a href="#" target="_blank" rel="nofollow">帮助中心</a></li>
                    <li><a href="#" target="_blank" rel="nofollow">用户协议</a></li>
                    <li><a href="#" target="_blank" rel="nofollow">企业用户团购</a></li>
                </ul>
                <ul class="linksc">
                    <li class="links">支付方式</li>
                    <li><a href="#" target="_blank" rel="nofollow">在线支付</a></li>
                </ul>
                <ul class="linksd">
                    <li class="links">配送方式</li>
                    <li><a href="#" target="_blank" rel="nofollow">包邮政策</a></li>
                    <li><a href="#" target="_blank" rel="nofollow">配送说明</a></li>
                    <li><a href="#" target="_blank" rel="nofollow">运费说明</a></li>
                    <li><a href="#" target="_blank" rel="nofollow">验货签收</a></li>
                </ul>
                <ul class="linkse">
                    <li class="links">售后服务</li>
                    <li><a href="#" target="_blank" rel="nofollow">正品承诺</a></li>
                    <li><a href="#" target="_blank" rel="nofollow">售后咨询</a></li>
                    <li><a href="#" target="_blank" rel="nofollow">退货政策</a></li>
                    <li><a href="#" target="_blank" rel="nofollow">退货办理</a></li>
                </ul>
                <div class="links_er_box">
                    <ul class="linksf">
                        <li class="links">手机我们</li>
                        <li><span class="link_bottom_pic"></span></li>
                        <li>下载移动客户端</li>
                    </ul>
                    <ul class="linksg">
                        <li class="links">我们微信</li>
                        <li><span class="link_bottom_pic"></span></li>
                        <li>我们官方微信</li>
                    </ul>
                </div>
            </div>
        </div>
        <div class="footer_dy" id="footer_dy">
            <div class="play_box">
                <span class="play"></span>
            </div>
        </div>
        <div class="footer_center">
            <div class="footer_con" id="footer_link">
                <a href="#" target="_blank" rel="nofollow">关于</a>
                <a href="#" target="_blank" rel="nofollow">INVESTOR RELATIONS</a>
                <a href="#" target="_blank" rel="nofollow">商家入驻</a>
                <a href="#" target="_blank" rel="nofollow">获取更新</a>
                <a href="#" target="_blank" rel="nofollow">加入我们</a>
                <a href="#" target="_blank" rel="nofollow">品牌合作专区</a>
                <a href="#" target="_blank" rel="nofollow">网站联盟</a>
                <a href="#" target="_blank" rel="nofollow">媒体报道</a>
                <a href="#" target="_blank" rel="nofollow">商务合作</a>
            </div>
        </div>
        <div id="footer_copyright" class="footer_copyright">
            <div class="footer_con">
                <p class="footer_copy_con">
                    <div class="copyRight">Copyright 2015-2015 成都雷立风行电子商务有限公司  保留一切权利。蜀ICP备15020915号 客服热线：400-6883-520</div>
                </p>
                <p>
                    <a href="#" class="footer_copy_logo logo01" rel="nofollow"></a>
                    <a href="#" target="_blank" class="footer_copy_logo logo02" rel="nofollow"></a>
                    <a href="#" class="footer_copy_logo logo03" rel="nofollow"></a>
                    <a href="#" class="footer_copy_logo logo04" rel="nofollow"></a>
                    <a href="#" target="_blank" class="footer_copy_logo logo05" rel="nofollow"></a>
                </p>
            </div>
        </div>
    </div>
</div>

<!--loading-->
<div class="loading">
	<div align="center"><img src="${ctxStatic }/images/loading.gif"></div>
	<div class="loading_text">努力加载中......</div>
</div>
</body>
</html>