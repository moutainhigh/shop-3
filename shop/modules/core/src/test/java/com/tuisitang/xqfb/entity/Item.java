package com.tuisitang.xqfb.entity;

import java.util.List;

import com.google.common.collect.Lists;
import com.tuisitang.common.mapper.JsonMapper;

/**
 * 1 人才 2 商品 3 供应商 4 婚庆公司 9 其它
 * 
 * 人才：图片，姓名，套餐，案例，关注人数，评分
 * 婚纱店：图片，名称，价格，风格，地区，关注人数，评分
 * 酒店：图片，名称，价格（每桌），容纳（容纳：50桌），星级，地区
 * 旅行社：图片，名称，关注人数，评分
 * 婚庆公司：图片，名称，关注人数，评分
 */
public class Item implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;// ID编号
	private String type;// 类型：1 人才 2 酒店 3 婚纱店 4 旅行社 5 婚庆公司
	private String name;// 名称
	private String image;// 图片
	private String price;// 价格
	private int watch;// 关注
	private double score;// 评分
	private int num;// 桌数
	private String style;// 风格
	private String area;// 地区
	private String star;// 星级
	private String pkg;// 套餐
	private String cases;// 案例
	
	public Item() {
		super();
	}
	
	/**
	 * 旅行社
	 */
	public Item(Long id, String type, String name, String image, int watch,
			double score) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.image = image;
		this.watch = watch;
		this.score = score;
	}
	
	/**
	 * 婚纱
	 */
	public Item(Long id, String name, String image, String price, int watch,
			double score, String style, String area) {
		super();
		this.id = id;
		this.type = "3";
		this.name = name;
		this.image = image;
		this.price = price;
		this.watch = watch;
		this.score = score;
		this.style = style;
		this.area = area;
	}
	
	/**
	 * 酒店
	 */
	public Item(Long id, String name, String image, String price, int num,
			String star, String area) {
		super();
		this.id = id;
		this.type = "2";
		this.name = name;
		this.image = image;
		this.price = price;
		this.num = num;
		this.star = star;
		this.area = area;
	}
	
	/**
	 * 人才
	 */
	public Item(Long id, String name, String image, String cases, String pkg) {
		super();
		this.id = id;
		this.type = "1";
		this.name = name;
		this.image = image;
		this.cases = cases;
		this.pkg = pkg;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getWatch() {
		return watch;
	}

	public void setWatch(int watch) {
		this.watch = watch;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getStar() {
		return star;
	}

	public void setStar(String star) {
		this.star = star;
	}
	
	public String getPkg() {
		return pkg;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	public String getCases() {
		return cases;
	}

	public void setCases(String cases) {
		this.cases = cases;
	}

	public static void main(String[] args) {
		JsonMapper mapper = JsonMapper.getInstance();
		/**
		 * 1. 旅行社
		 */
		{
			int pageNo = 0;
			int pageSize = 20;
			Page<Item> page = new Page<Item>(pageNo, pageSize);
			List<Item> list = Lists.newArrayList();
			{
				list.add(new Item(1L, "4", "中青旅", "http://ecmb.bdimg.com/tam-ogel/702492d8de53372aad36c4a5a9d1bb31_121_121.jpg", 100, 5.0));
				list.add(new Item(2L, "4", "中青旅", "http://ecmb.bdimg.com/tam-ogel/702492d8de53372aad36c4a5a9d1bb31_121_121.jpg", 100, 5.0));
				list.add(new Item(3L, "4", "中青旅", "http://ecmb.bdimg.com/tam-ogel/702492d8de53372aad36c4a5a9d1bb31_121_121.jpg", 100, 5.0));
				list.add(new Item(4L, "4", "中青旅", "http://ecmb.bdimg.com/tam-ogel/702492d8de53372aad36c4a5a9d1bb31_121_121.jpg", 100, 5.0));
			}
			page.setCount(104);
			page.setList(list);
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
			System.out.println(mapper.toJson(page));
		}
		/**
		 * 2. 酒店
		 */
		{
			int pageNo = 0;
			int pageSize = 20;
			Page<Item> page = new Page<Item>(pageNo, pageSize);
			List<Item> list = Lists.newArrayList();
			{
				list.add(new Item(1L, "香港铜锣湾皇悦酒店", "data/files/store_1/goods_78/201506100311185291.png", "1000-4000", 50, "五星级", "青羊区"));
				list.add(new Item(2L, "香港铜锣湾皇悦酒店", "data/files/store_1/goods_78/201506100311185291.png", "1000-4000", 50, "五星级", "青羊区"));
				list.add(new Item(3L, "香港铜锣湾皇悦酒店", "data/files/store_1/goods_78/201506100311185291.png", "1000-4000", 50, "五星级", "青羊区"));
				list.add(new Item(4L, "香港铜锣湾皇悦酒店", "data/files/store_1/goods_78/201506100311185291.png", "1000-4000", 50, "五星级", "青羊区"));
			}
			page.setCount(104);
			page.setList(list);
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
			System.out.println(mapper.toJson(page));
		}
		/**
		 * 3. 婚纱
		 * Long id, String name, String image, String price, int watch, double score, String style, String area
		 */
		{
			int pageNo = 0;
			int pageSize = 20;
			Page<Item> page = new Page<Item>(pageNo, pageSize);
			List<Item> list = Lists.newArrayList();
			{
				list.add(new Item(1L, "天长地久婚纱摄影", "data/files/store_1/goods_143/201506100242231272.jpg", "1000-4000", 300, 4.5, "欧式", "青羊区"));
				list.add(new Item(2L, "天长地久婚纱摄影", "data/files/store_1/goods_143/201506100242231272.jpg", "1000-4000", 300, 4.5, "欧式", "青羊区"));
				list.add(new Item(3L, "天长地久婚纱摄影", "data/files/store_1/goods_143/201506100242231272.jpg", "1000-4000", 300, 4.5, "欧式", "青羊区"));
				list.add(new Item(4L, "天长地久婚纱摄影", "data/files/store_1/goods_143/201506100242231272.jpg", "1000-4000", 300, 4.5, "欧式", "青羊区"));
				list.add(new Item(5L, "天长地久婚纱摄影", "data/files/store_1/goods_143/201506100242231272.jpg", "1000-4000", 300, 4.5, "欧式", "青羊区"));
				list.add(new Item(6L, "天长地久婚纱摄影", "data/files/store_1/goods_143/201506100242231272.jpg", "1000-4000", 300, 4.5, "欧式", "青羊区"));
				list.add(new Item(7L, "天长地久婚纱摄影", "data/files/store_1/goods_143/201506100242231272.jpg", "1000-4000", 300, 4.5, "欧式", "青羊区"));
				list.add(new Item(8L, "天长地久婚纱摄影", "data/files/store_1/goods_143/201506100242231272.jpg", "1000-4000", 300, 4.5, "欧式", "青羊区"));
				list.add(new Item(9L, "天长地久婚纱摄影", "data/files/store_1/goods_143/201506100242231272.jpg", "1000-4000", 300, 4.5, "欧式", "青羊区"));
				list.add(new Item(10L, "天长地久婚纱摄影", "data/files/store_1/goods_143/201506100242231272.jpg", "1000-4000", 300, 4.5, "欧式", "青羊区"));
			}
			page.setCount(104);
			page.setList(list);
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
			System.out.println(mapper.toJson(page));
		}
		/**
		 * 4. 人才
		 * Long id, String name, String image, String cases, String pkg
		 */
		{
			int pageNo = 0;
			int pageSize = 20;
			Page<Item> page = new Page<Item>(pageNo, pageSize);
			List<Item> list = Lists.newArrayList();
			{
				list.add(new Item(1L, "美女1", "themes/mall/xiangmaijie/styles/default/images/display/person4.jpg", "案例1", "套餐1"));
				list.add(new Item(2L, "美女2", "themes/mall/xiangmaijie/styles/default/images/display/person4.jpg", "案例1", "套餐1"));
				list.add(new Item(3L, "美女3", "themes/mall/xiangmaijie/styles/default/images/display/person4.jpg", "案例1", "套餐1"));
				list.add(new Item(4L, "美女4", "themes/mall/xiangmaijie/styles/default/images/display/person4.jpg", "案例1", "套餐1"));
				list.add(new Item(5L, "美女5", "themes/mall/xiangmaijie/styles/default/images/display/person4.jpg", "案例1", "套餐1"));
			}
			page.setCount(104);
			page.setList(list);
			page.setPageNo(pageNo);
			page.setPageSize(pageSize);
			System.out.println(mapper.toJson(page));
		}
	}
}
