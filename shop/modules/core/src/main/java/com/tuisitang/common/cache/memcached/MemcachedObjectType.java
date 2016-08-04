package com.tuisitang.common.cache.memcached;

/**
 * 统一定义Memcached中存储的各种对象的Key前缀和超时时间.
 */
public enum MemcachedObjectType {
	DEFAULT("app-default:", 0),
    ADMIN("app-admin:", 60 * 60 * 1),
    ADMIN_ROLE("app-admin-role:", 60 * 60 * 1),
    SHIRO_AUTHENTICATION("app-shiro-authentication:", 30 * 60 * 1),
    SHIRO_AUTHORIZATION("app-shiro-authorization:", 30 * 60 * 1),
	PRODUCT_ATTRIBTE("ProductAttribute:", 7 * 24 * 60 * 60 * 1),// 产品属性：一周
	PRODUCT_HOT_RECOMMEND("ProductHotRecommend:", 7 * 24 * 60 * 60 * 1),// 产品热门推荐：一周
	PRODUCT_HOT("ProductHot:", 7 * 24 * 60 * 60 * 1),// 热门产品：一周
	PRODUCT_SALE("ProductSale:", 7 * 24 * 60 * 60 * 1),// 特价产品：一周
	PRODUCT_NEW("ProductNew:", 7 * 24 * 60 * 60 * 1),// 最新产品：一周
	PRODUCT_ACTIVITY("ProductActivity:", 7 * 24 * 60 * 60 * 1),// 活动产品：一周
	INDEX_IMG_TYPE("IndexImgType:", 7 * 24 * 60 * 60 * 1),// 最新产品：一周
	INDEX_FLOOR("IndexFloor:", 7 * 24 * 60 * 60 * 1),// 楼层数据：一周
	ATTRIBUTE_CATALOG("CatalogAttribute:", 7 * 24 * 60 * 60 * 1),// 目录属性：一周
	
	CATALOG_TYPE_LIST("CatalogTypeList:", 7 * 24 * 60 * 60 * 1), //目录列表：一周
	CATALOG_SUB_LIST("CatalogSubList:", 7 * 24 * 60 * 60 * 1), //子目录列表：一周
	Account("Account:", 7 * 24 * 60 * 60 * 1), // 会员账号：一周
	Product("Product:", 7 * 24 * 60 * 60 * 1),// 产品：一周
	ProductStock("ProductStock:", 7 * 24 * 60 * 60 * 1),// 产品：一周
	Gift("Gift:", 7 * 24 * 60 * 60 * 1),// 赠品：一周
	Activity("Activity:", 7 * 24 * 60 * 60 * 1),// 活动：一周
	Spec("Spec:", 7 * 24 * 60 * 60 * 1),// 商品规格：一周
	Catalog("Catalog:", 7 * 24 * 60 * 60 * 1),// 目录：一周
	KeyValue("KeyValue:", 30 * 24 * 60 * 60 * 1),// 键值：30天
	NotifyTemplate("NotifyTemplate:", 30 * 24 * 60 * 60 * 1),// 通知模板：30天
	MCODE("MCODE:", 60 * 10),// 验证码：10分钟
	Advert("Advert:", 30 * 24 * 60 * 60 * 1),// 广告：30天
	
	Session("Session:", 30 * 24 * 60 * 60 * 1),// Session：30天
	SystemSetting("SystemSetting:", 30 * 24 * 60 * 60 * 1),// SystemSetting：30天
	
	Hotquery("Hotquery:", 30 * 24 * 60 * 60 * 1),// Hotquery：30天
	Favorite("Favorite:", 30 * 24 * 60 * 60 * 1),// 收藏 Favorite：30天
	FavoriteCountProduct("FavoriteCountProduct:", 1 * 24 * 60 * 60 * 1),// 产品收藏统计 Favorite：1天
	Cart("Cart:", 30 * 24 * 60 * 60 * 1),// 购物车 Cart：30天
	
	Address("Address:", 30 * 24 * 60 * 60 * 1),// Address：30天
	Area("Area:", 30 * 24 * 60 * 60 * 1),// Area：30天
	Express("Express:", 30 * 24 * 60 * 60 * 1),// Express：30天
	ExpressCompany("ExpressCompany:", 30 * 24 * 60 * 60 * 1),// ExpressCompany：30天
	Pay("Pay:", 30 * 24 * 60 * 60 * 1),// Pay：30天
	DeliveryDay("DeliveryDay:", 30 * 24 * 60 * 60 * 1),// DeliveryDay：30天
	
	InviteCode("InviteCode:", 30 * 24 * 60 * 60 * 1),// InviteCode：30天
	
	Order("Order:", 1 * 24 * 60 * 60 * 1),// Order：1天
	
	Device("Device:", 30 * 24 * 60 * 60 * 1),// InviteCode：30天
	
	VerifyCode("VerifyCode:", 60 * 10),// 验证码：10分钟
	
	AccountRank("AccountRank:", 30 * 24 * 60 * 60 * 1),// AccountRank：30天
	;

	private String prefix;
	private int expiredTime;

	MemcachedObjectType(String prefix, int expiredTime) {
		this.prefix = prefix;
		this.expiredTime = expiredTime;
	}

	public String getPrefix() {
		return prefix;
	}

	public int getExpiredTime() {
		return expiredTime;
	}

}
