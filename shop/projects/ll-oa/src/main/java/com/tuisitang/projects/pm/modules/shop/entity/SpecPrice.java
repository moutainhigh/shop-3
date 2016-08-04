package com.tuisitang.projects.pm.modules.shop.entity;

public class SpecPrice implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String SPEC_COLOR = "color";

	public static final String SPEC_SIZE = "size";

	public static final String SPEC_TYPE = "type";

	private String color;// 颜色
	private String size;// 大、小等
	private String type;// 类型：统称
	private double price;// 价格
	private int minSale;// 最少购买数

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getMinSale() {
		return minSale;
	}

	public void setMinSale(int minSale) {
		this.minSale = minSale;
	}

}
