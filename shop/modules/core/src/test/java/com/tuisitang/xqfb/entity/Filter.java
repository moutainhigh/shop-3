package com.tuisitang.xqfb.entity;

import java.util.List;

import com.google.common.collect.Lists;
import com.tuisitang.common.mapper.JsonMapper;

public class Filter implements java.io.Serializable {

	private String id;
	private String type;// 类型：1 人才 2 酒店 3 婚纱店 4 旅行社 5 婚庆公司
	private int action;// 0 筛选 1 排序
	private String name;
	private List<Filter> list = Lists.newArrayList();
	
	public Filter(String id, String type, int action, String name) {
		super();
		this.id = id;
		this.type = type;
		this.action = action;
		this.name = name;
	}

	public Filter() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Filter> getList() {
		return list;
	}

	public void setList(List<Filter> list) {
		this.list = list;
	}
	
	public void addFilter(Filter f) {
		this.list.add(f);
	}
	
	public static void main(String[] args) {
		JsonMapper mapper = JsonMapper.getInstance();
		List<Filter> l = Lists.newArrayList();
		{
			Filter filter = new Filter("1", "3", 0, "全部商圈");
			filter.addFilter(new Filter("11", "3", 0, "全部"));
			filter.addFilter(new Filter("11", "3", 0, "青羊"));
			filter.addFilter(new Filter("11", "3", 0, "武侯"));
			l.add(filter);
		}
		{
			Filter filter = new Filter("2", "3", 0, "所有风格");
			filter.addFilter(new Filter("21", "3", 0, "全部"));
			filter.addFilter(new Filter("21", "3", 0, "欧式"));
			filter.addFilter(new Filter("21", "3", 0, "中式"));
			l.add(filter);
		}
		{
			Filter filter = new Filter("3", "3", 1, "智能排序");
			filter.addFilter(new Filter("31", "3", 1, "智能排序"));
			filter.addFilter(new Filter("31", "3", 1, "价格降序"));
			filter.addFilter(new Filter("31", "3", 1, "价格升序"));
			l.add(filter);
		}
		System.out.print(mapper.toJson(l));
	}

}
