package com.tuisitang.modules.shop.entity;

import java.io.Serializable;
import java.util.Date;

import net.jeeshop.core.dao.QueryModel;

/**    
 * @{#} Cart.java  
 * 
 * 购物车 Entity
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class Cart extends QueryModel<Cart> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Long id;
	private Long accountId;// Account编号
	private String sessionId;// Session编号
	private Long productId;// 商品编号
	private Long specId; //商品规格
	private String specName; //商品规格
	private Long sellerId;//销售商
	private String sellerName;// 销售商名称
	private String productName;// 商品名称
	private String productImage;// 商品图片
	private int quantity;// 商品数量
	private double marketPrice;// 市场价，取之Product的price
	private double nowPrice;// 当前价，取之Product的nowPrice
	private double discountPrice;// 折扣的价格
	private double fee;// 物流费用
	private Long activityId;// 活动ID
	private String activityName;// 活动名称
	private int activityStatus;// 活动状态 0 没有活动 1 参加活动 2 活动还没开始 3 活动已结束 4 不在会员等级范围内
	private String activityType;// 活动类型 普通 c:促销活动；j:积分兑换；t:团购活动
	private String discountType;// 打折类型 "r" 减免 "d" 折扣 "s" 双倍积分
	private double discount;// 折扣
	private double requiredIntegral;// 所需积分
	private double presentIntegral;// 赠送积分
	private Date createTime; // 选购时间
	private String status = "checked";// 状态：checked  选择  未选中

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(double marketPrice) {
		this.marketPrice = marketPrice;
	}

	public double getNowPrice() {
		return nowPrice;
	}

	public void setNowPrice(double nowPrice) {
		this.nowPrice = nowPrice;
	}
	
	public double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(double discountPrice) {
		this.discountPrice = discountPrice;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public int getActivityStatus() {
		return activityStatus;
	}

	public void setActivityStatus(int activityStatus) {
		this.activityStatus = activityStatus;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getRequiredIntegral() {
		return requiredIntegral;
	}

	public void setRequiredIntegral(double requiredIntegral) {
		this.requiredIntegral = requiredIntegral;
	}

	public double getPresentIntegral() {
		return presentIntegral;
	}

	public void setPresentIntegral(double presentIntegral) {
		this.presentIntegral = presentIntegral;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isChecked() {
		return "checked".equals(status);
	}

	public Long getSpecId() {
		return specId;
	}

	public void setSpecId(Long specId) {
		this.specId = specId;
	}

	public String getSpecName() {
		return specName;
	}

	public void setSpecName(String specName) {
		this.specName = specName;
	}

}
