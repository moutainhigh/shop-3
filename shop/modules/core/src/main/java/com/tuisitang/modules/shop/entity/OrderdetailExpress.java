package com.tuisitang.modules.shop.entity;

import java.io.Serializable;

import net.jeeshop.core.dao.page.PagerModel;

/**    
 * @{#} OrderdetailExpress.java   
 *    
 * 订单详情 - 物流信息 Entity  
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class OrderdetailExpress extends PagerModel<OrderdetailExpress> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;// 编号
	private Long orderdetailId;// 订单详情编号
	private String mode;// 模式：0 省际 1 省内
	private String expressType;// 物流/快递方式：物流，快递 t_express 表中
	private String expressCompanyCode;// 物流/快递公司编码， t_express_company 表中 code
	private String expressCompanyName;// 物流/快递公司， t_express_company 表中 name
	private String expressNo;// 物流/快递单号
	private double expressFee;// 物流/快递费用
	private String fromCityCode;//
	private String fromCityName;
	private String toCityCode;
	private String toCityName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getOrderdetailId() {
		return orderdetailId;
	}

	public void setOrderdetailId(Long orderdetailId) {
		this.orderdetailId = orderdetailId;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getExpressType() {
		return expressType;
	}

	public void setExpressType(String expressType) {
		this.expressType = expressType;
	}

	public String getExpressCompanyCode() {
		return expressCompanyCode;
	}

	public void setExpressCompanyCode(String expressCompanyCode) {
		this.expressCompanyCode = expressCompanyCode;
	}

	public String getExpressCompanyName() {
		return expressCompanyName;
	}

	public void setExpressCompanyName(String expressCompanyName) {
		this.expressCompanyName = expressCompanyName;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public double getExpressFee() {
		return expressFee;
	}

	public void setExpressFee(double expressFee) {
		this.expressFee = expressFee;
	}

	public String getFromCityCode() {
		return fromCityCode;
	}

	public void setFromCityCode(String fromCityCode) {
		this.fromCityCode = fromCityCode;
	}

	public String getFromCityName() {
		return fromCityName;
	}

	public void setFromCityName(String fromCityName) {
		this.fromCityName = fromCityName;
	}

	public String getToCityCode() {
		return toCityCode;
	}

	public void setToCityCode(String toCityCode) {
		this.toCityCode = toCityCode;
	}

	public String getToCityName() {
		return toCityName;
	}

	public void setToCityName(String toCityName) {
		this.toCityName = toCityName;
	}

}
