package com.tuisitang.generate;

import java.util.List;

import com.google.common.collect.Lists;

public class AreaData implements java.io.Serializable {
	private String id;
	private String code;
	private String name;
	private int offset;
	private int pageSize;
	private int pagerSize;
	private String pcode;
	private int total;
	private List<AreaData> children = Lists.newArrayList();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPagerSize() {
		return pagerSize;
	}

	public void setPagerSize(int pagerSize) {
		this.pagerSize = pagerSize;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<AreaData> getChildren() {
		return children;
	}

	public void setChildren(List<AreaData> children) {
		this.children = children;
	}

	@Override
	public String toString() {
		return "AreaData [id=" + id + ", code=" + code + ", name=" + name
				+ ", offset=" + offset + ", pageSize=" + pageSize
				+ ", pagerSize=" + pagerSize + ", pcode=" + pcode + ", total="
				+ total + ", children=" + children + "]";
	}

}
