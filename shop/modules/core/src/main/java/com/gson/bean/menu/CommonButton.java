package com.gson.bean.menu;

public class CommonButton extends Button {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String type;
	private String key;
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}