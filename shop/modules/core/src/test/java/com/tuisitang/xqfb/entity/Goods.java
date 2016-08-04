package com.tuisitang.xqfb.entity;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;
import com.tuisitang.common.mapper.JsonMapper;

public class Goods implements Serializable {

	private Long id;// 商品编号
	private String name;// 商品名称
	private double price;// 商品价格
	private String image;// 商品图片
	private int salesVolume;// 销售量
	
	public Goods() {
		super();
	}
	
	public Goods(Long id, String name, double price, String image,
			int salesVolume) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.image = image;
		this.salesVolume = salesVolume;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public int getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(int salesVolume) {
		this.salesVolume = salesVolume;
	}
	
	public static void main(String[] args) {
		JsonMapper mapper = JsonMapper.getInstance();
		int pageNo = 0;
		int pageSize = 20;
		Page<Goods> page = new Page<Goods>(pageNo, pageSize);
		List<Goods> list = Lists.newArrayList();
		{
			list.add(new Goods(1L, "52°五粮液(股份)唐王宴珍品750ml", 119.00, "data/files/store_1/goods_15/small_201506011620159148.jpg", 100));
			list.add(new Goods(2L, "52°五粮液(股份)唐王宴珍品750ml", 119.00, "data/files/store_1/goods_15/small_201506011620159148.jpg", 100));
			list.add(new Goods(3L, "52°五粮液(股份)唐王宴珍品750ml", 119.00, "data/files/store_1/goods_15/small_201506011620159148.jpg", 100));
			list.add(new Goods(4L, "52°五粮液(股份)唐王宴珍品750ml", 119.00, "data/files/store_1/goods_15/small_201506011620159148.jpg", 100));
			list.add(new Goods(5L, "52°五粮液(股份)唐王宴珍品750ml", 119.00, "data/files/store_1/goods_15/small_201506011620159148.jpg", 100));
			list.add(new Goods(6L, "52°五粮液(股份)唐王宴珍品750ml", 119.00, "data/files/store_1/goods_15/small_201506011620159148.jpg", 100));
			list.add(new Goods(7L, "52°五粮液(股份)唐王宴珍品750ml", 119.00, "data/files/store_1/goods_15/small_201506011620159148.jpg", 100));
			list.add(new Goods(8L, "52°五粮液(股份)唐王宴珍品750ml", 119.00, "data/files/store_1/goods_15/small_201506011620159148.jpg", 100));
			list.add(new Goods(9L, "52°五粮液(股份)唐王宴珍品750ml", 119.00, "data/files/store_1/goods_15/small_201506011620159148.jpg", 100));
			list.add(new Goods(10L, "52°五粮液(股份)唐王宴珍品750ml", 119.00, "data/files/store_1/goods_15/small_201506011620159148.jpg", 100));
			list.add(new Goods(11L, "52°五粮液(股份)唐王宴珍品750ml", 119.00, "data/files/store_1/goods_15/small_201506011620159148.jpg", 100));
			list.add(new Goods(12L, "52°五粮液(股份)唐王宴珍品750ml", 119.00, "data/files/store_1/goods_15/small_201506011620159148.jpg", 100));
			list.add(new Goods(13L, "52°五粮液(股份)唐王宴珍品750ml", 119.00, "data/files/store_1/goods_15/small_201506011620159148.jpg", 100));
			list.add(new Goods(14L, "52°五粮液(股份)唐王宴珍品750ml", 119.00, "data/files/store_1/goods_15/small_201506011620159148.jpg", 100));
			list.add(new Goods(15L, "52°五粮液(股份)唐王宴珍品750ml", 119.00, "data/files/store_1/goods_15/small_201506011620159148.jpg", 100));
			list.add(new Goods(16L, "52°五粮液(股份)唐王宴珍品750ml", 119.00, "data/files/store_1/goods_15/small_201506011620159148.jpg", 100));
		}
		page.setCount(116);
		page.setList(list);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		System.out.print(mapper.toJson(page));
	}

}
