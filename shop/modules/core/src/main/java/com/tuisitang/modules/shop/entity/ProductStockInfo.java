package com.tuisitang.modules.shop.entity;

import java.io.Serializable;

import net.jeeshop.core.dao.QueryModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tuisitang.common.cache.memcached.MemcachedObjectType;
import com.tuisitang.common.cache.memcached.SpyMemcachedClient;

/**    
 * @{#} ProductStockInfo.java
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class ProductStockInfo extends QueryModel<Product> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Long id;
	private Long productId;// 商品ID
	private volatile int stock;// 商品库存
	private boolean changeStock;// 库存是否有所改变，false:库存未改变。true：库存已经改变
	private int score;// 赠送积分
	private String activityID;// 活动ID

	public static final String CACHE_PROPERTY_ID = "productId";// 缓存id属性

	// List<Spec>
	public ProductStockInfo() {
		super();
	}

	public ProductStockInfo(Long productId, int stock) {
		super();
		this.productId = productId;
		this.stock = stock;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getStock() {
		return stock;
	}

	public synchronized void setStock(int stock) {
		this.stock = stock;
	}

	public boolean isChangeStock() {
		return changeStock;
	}

	public void setChangeStock(boolean changeStock) {
		this.changeStock = changeStock;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getActivityID() {
		return activityID;
	}

	public void setActivityID(String activityID) {
		this.activityID = activityID;
	}

	@Override
	public String toString() {
		return "ProductStockInfo [id=" + id + ", stock=" + stock
				+ ", changeStock=" + changeStock + ", score=" + score
				+ ", activityID=" + activityID + "]";
	}

	/**
	 * 根据id获得memcached key
	 * 
	 * @param id
	 * @return
	 */
	@JsonIgnore
	public static String getKey(Long id) {
		return MemcachedObjectType.ProductStock.getPrefix()
				+ ProductStockInfo.CACHE_PROPERTY_ID + SpyMemcachedClient.SEP
				+ id;
	}

}