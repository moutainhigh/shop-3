package net.jeeshop.services.front.product.bean;

import java.util.List;

import com.tuisitang.modules.shop.entity.Spec;

/**
 * 商品库存对象
 * 
 * @author huangf
 * 
 */
public class ProductStockInfo {
	private Long id;// 商品ID
	private volatile int stock;// 商品库存
	private boolean changeStock;// 库存是否有所改变，false:库存未改变。true：库存已经改变
	private int score;//赠送积分
	private Long activityID;//活动ID
	
//	List<Spec>
	public ProductStockInfo() {
		super();
	}

	public ProductStockInfo(Long id, int stock) {
		super();
		this.id = id;
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Long getActivityID() {
		return activityID;
	}

	public void setActivityID(Long activityID) {
		this.activityID = activityID;
	}

	@Override
	public String toString() {
		return "ProductStockInfo [id=" + id + ", stock=" + stock
				+ ", changeStock=" + changeStock + ", score=" + score
				+ ", activityID=" + activityID + "]";
	}

}
