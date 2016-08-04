package com.tuisitang.modules.shop.front.vo;

import java.util.List;
import java.util.Set;

import net.jeeshop.services.front.product.bean.ProductImageInfo;

import com.tuisitang.modules.shop.entity.Activity;
import com.tuisitang.modules.shop.entity.Catalog;
import com.tuisitang.modules.shop.entity.Gift;
import com.tuisitang.modules.shop.entity.Product;
import com.tuisitang.modules.shop.entity.ProductAttribute;
import com.tuisitang.modules.shop.entity.Spec;

public class ProductVo {
	private Product product;
	/**
	 * 规格相关
	 */
	private String specJsonString;//规格JSON字符串
	private Set<String> specType;//类型
	private Set<String> specColor;//颜色 
	private Set<String> specSize;//尺寸
	
	private Spec currentSpec; //当前规格
	
	/**
	 * 产品目录相关
	 */
	private String mainCatalogName;//主类别名称
	private String childrenCatalogName;//子类别名称
	private String childrenCatalogCode;//子类别code
	private List<Catalog> catalogClassList; //产品分类列表
	
	private int productFavoriteCount;//产品收藏统计
	private boolean favorite; //是否已收藏

	private String productSorryStr; //产品抱歉文本
	private boolean outOfStock;//是否已售完
	private List<ProductImageInfo> productImageList;//商品详情页面显示的商品图片列表
	private List<ProductAttribute> parameterList;//商品参数列表
	private List<String> imageList; //图片列表
	private List<Catalog> catalogPath;
	
	/**
	 * 活动的属性，页面显示用的
	 */
	private Activity activity;
	private String finalPrice;//根据规则，计算出来的最终给活动价格
	
	
	private Gift gift;//商品赠品信息
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public String getSpecJsonString() {
		return specJsonString;
	}
	public void setSpecJsonString(String specJsonString) {
		this.specJsonString = specJsonString;
	}
	public Set<String> getSpecColor() {
		return specColor;
	}
	public void setSpecColor(Set<String> specColor) {
		this.specColor = specColor;
	}
	public Set<String> getSpecSize() {
		return specSize;
	}
	public void setSpecSize(Set<String> specSize) {
		this.specSize = specSize;
	}
	public String getMainCatalogName() {
		return mainCatalogName;
	}
	public void setMainCatalogName(String mainCatalogName) {
		this.mainCatalogName = mainCatalogName;
	}
	public String getChildrenCatalogName() {
		return childrenCatalogName;
	}
	public void setChildrenCatalogName(String childrenCatalogName) {
		this.childrenCatalogName = childrenCatalogName;
	}
	public String getChildrenCatalogCode() {
		return childrenCatalogCode;
	}
	public void setChildrenCatalogCode(String childrenCatalogCode) {
		this.childrenCatalogCode = childrenCatalogCode;
	}
	
	public String getProductSorryStr() {
		return productSorryStr;
	}
	public void setProductSorryStr(String productSorryStr) {
		this.productSorryStr = productSorryStr;
	}
	public Gift getGift() {
		return gift;
	}
	public void setGift(Gift gift) {
		this.gift = gift;
	}
	public List<ProductImageInfo> getProductImageList() {
		return productImageList;
	}
	public void setProductImageList(List<ProductImageInfo> productImageList) {
		this.productImageList = productImageList;
	}
	public List<ProductAttribute> getParameterList() {
		return parameterList;
	}
	public void setParameterList(List<ProductAttribute> parameterList) {
		this.parameterList = parameterList;
	}
	public Spec getCurrentSpec() {
		return currentSpec;
	}
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	public String getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(String finalPrice) {
		this.finalPrice = finalPrice;
	}
	public Set<String> getSpecType() {
		return specType;
	}
	public void setSpecType(Set<String> specType) {
		this.specType = specType;
	}
	public List<Catalog> getCatalogPath() {
		return catalogPath;
	}
	public void setCatalogPath(List<Catalog> catalogPath) {
		this.catalogPath = catalogPath;
	}
	public int getProductFavoriteCount() {
		return productFavoriteCount;
	}
	public void setProductFavoriteCount(int productFavoriteCount) {
		this.productFavoriteCount = productFavoriteCount;
	}
	public List<String> getImageList() {
		return imageList;
	}
	public void setImageList(List<String> imageList) {
		this.imageList = imageList;
	}
	public boolean isOutOfStock() {
		return outOfStock;
	}
	public void setOutOfStock(boolean outOfStock) {
		this.outOfStock = outOfStock;
	}
	public boolean getFavorite() {
		return favorite;
	}
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	
}
