package com.tuisitang.xqfb.entity;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

public class Ad implements Serializable {

	private Long id;
	private String title;
	private String image;
	private String url;
	
	
	public Ad() {
		super();
	}
	
	public Ad(Long id, String title, String image, String url) {
		super();
		this.id = id;
		this.title = title;
		this.image = image;
		this.url = url;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public static void main(String [] args) {
		List<Ad> list = Lists.newArrayList();
		{
			list.add(new Ad(1L, "广告1", "https://ss2.baidu.com/-vo3dSag_xI4khGko9WTAnF6hhy/super/whfpf%3D425%2C260%2C50/sign=a4b3d7085dee3d6d2293d48b252b5910/0e2442a7d933c89524cd5cd4d51373f0830200ea.jpg", "http://www.baidu.com"));
			list.add(new Ad(2L, "广告2", "https://ss0.baidu.com/-Po3dSag_xI4khGko9WTAnF6hhy/super/whfpf%3D425%2C260%2C50/sign=a41eb338dd33c895a62bcb3bb72e47c2/5fdf8db1cb134954a2192ccb524e9258d1094a1e.jpg", "http://www.baidu.com"));
			list.add(new Ad(3L, "广告3", "http://c.hiphotos.baidu.com/image/w%3D400/sign=c2318ff84334970a4773112fa5c8d1c0/b7fd5266d0160924c1fae5ccd60735fae7cd340d.jpg", "http://www.baidu.com"));
			list.add(new Ad(4L, "广告4", "http://wedding-test2.oss-cn-hangzhou.aliyuncs.com/shop-test/%25692%7DDIQ%254%25KB1VS%25I0%29R1K.jpg", "http://www.xqfb888.com"));
		}
	}
}
