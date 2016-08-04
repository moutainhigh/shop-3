package com.tuisitang.projects.pm.modules.stock.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.tuisitang.projects.pm.common.persistence.DataEntity;
import com.tuisitang.projects.pm.modules.shop.entity.Product;

@Entity
@Table(name = "stock_order_detail")
@DynamicInsert @DynamicUpdate
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class StockOrderDetail extends DataEntity {

	private Long id;
	private StockOrder stockOrder;
	private Product product;
	private int number;// 数量
	private double factoryPrice;// 出厂价
	private double fee;// 物流费

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_cms_category")
//	@SequenceGenerator(name = "seq_cms_category", sequenceName = "seq_cms_category")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="order_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@NotNull
	public StockOrder getStockOrder() {
		return stockOrder;
	}

	public void setStockOrder(StockOrder stockOrder) {
		this.stockOrder = stockOrder;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="product_id")
	@NotFound(action = NotFoundAction.IGNORE)
	@NotNull
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getFactoryPrice() {
		return factoryPrice;
	}

	public void setFactoryPrice(double factoryPrice) {
		this.factoryPrice = factoryPrice;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

}
