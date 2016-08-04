package com.tuisitang.xqfb.entity;

import java.util.List;

import com.google.common.collect.Lists;
import com.tuisitang.common.mapper.JsonMapper;

public class Category implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;//
	private String type;// 分类类型：1 人才 2 商品 3 供应商 4 婚庆公司 9 其它
	private String name;
	private String image;// 分类图片地址：data/files/mall/catsimage/201507021712011614.png
							// 需要加上www.xqfb888.com/
	private int sort;// 排序
	private List<Category> children = Lists.newArrayList();// 子分类

	public Category() {

	}

	public Category(Long id, String type, String name, String image, int sort) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.image = image;
		this.sort = sort;
	}
	
	public Category(Long id, String type, String name, int sort) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.sort = sort;
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

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public List<Category> getChildren() {
		return children;
	}

	public void setChildren(List<Category> children) {
		this.children = children;
	}

	public void addChild(Category c) {
		this.children.add(c);
	}

	public static void main(String[] args) {
		JsonMapper mapper = JsonMapper.getInstance();
		List<Category> list = Lists.newArrayList();
		{
			Category c = new Category(1L, "1", "婚庆演艺", null, 10);
			list.add(c);
			c.addChild(new Category(11L, "1", "花艺师",
					"data/files/mall/catsimage/201507021712011614.png", 10));
			c.addChild(new Category(12L, "1", "监导师",
					"data/files/mall/catsimage/201507021712011614.png", 20));
			c.addChild(new Category(13L, "1", "摄影师",
					"data/files/mall/catsimage/201507021712011614.png", 30));
			c.addChild(new Category(14L, "1", "布场师",
					"data/files/mall/catsimage/201507021712011614.png", 40));
			c.addChild(new Category(15L, "1", "策划师",
					"data/files/mall/catsimage/201507021712011614.png", 50));
			c.addChild(new Category(16L, "1", "化妆师",
					"data/files/mall/catsimage/201507021712011614.png", 60));
			c.addChild(new Category(17L, "1", "摄影师",
					"data/files/mall/catsimage/201507021712011614.png", 70));
			c.addChild(new Category(18L, "1", "主持人",
					"data/files/mall/catsimage/201507021712011614.png", 80));
			c.addChild(new Category(19L, "1", "音控师",
					"data/files/mall/catsimage/201507021712011614.png", 90));
		}

		{
			Category c = new Category(2L, "2", "供应商品", null, 20);
			list.add(c);
			c.addChild(new Category(21L, "2", "摄影器材",
					"data/files/mall/catsimage/201507021712011614.png", 10));
			c.addChild(new Category(22L, "2", "灯光",
					"data/files/mall/catsimage/201507021712011614.png", 20));
			c.addChild(new Category(23L, "2", "婚车",
					"data/files/mall/catsimage/201507021712011614.png", 30));
		}

		{
			Category c = new Category(3L, "2", "喜烟喜酒", null, 30);
			list.add(c);
			c.addChild(new Category(31L, "2", "喜酒",
					"data/files/mall/catsimage/201507021712011614.png", 10));
			c.addChild(new Category(31L, "2", "喜糖",
					"data/files/mall/catsimage/201507021712011614.png", 20));
		}

		{
			Category c = new Category(4L, "3", "婚礼大师", null, 40);
			list.add(c);
			c.addChild(new Category(41L, "3", "喜酒供应商",
					"data/files/mall/catsimage/201507021712011614.png", 10));
			c.addChild(new Category(41L, "3", "喜糖供应商",
					"data/files/mall/catsimage/201507021712011614.png", 20));
		}

		{
			Category c = new Category(5L, "9", "专家授课", null, 50);
			list.add(c);
		}

		{
			Category c = new Category(6L, "4", "婚庆公司", null, 60);
			list.add(c);
		}

		{
			Category c = new Category(7L, "9", "全息婚礼", null, 70);
			list.add(c);
		}
		System.out.println(mapper.toJson(list));
		
		{
			list = Lists.newArrayList();
			list.add(new Category(31L, "2", "喜酒", 10));
			list.add(new Category(32L, "2", "喜糖", 20));
			list.add(new Category(33L, "2", "珠宝", 30));
			list.add(new Category(34L, "2", "家纺", 40));
			list.add(new Category(35L, "2", "喜庆用品", 50));
			list.add(new Category(36L, "2", "其它", 60));
			
			System.out.println(mapper.toJson(list));
		}
	}
}
