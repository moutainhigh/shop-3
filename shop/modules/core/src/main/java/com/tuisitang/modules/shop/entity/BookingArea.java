package com.tuisitang.modules.shop.entity;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gson.bean.UserInfo;

/**    
 * @{#} WechatUser.java   
 * 
 * 微信用户 Entity
 *
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: tst</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class BookingArea extends UserInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Long id;// 编号
	private Long provinceId;// 省份编号
	private String provinceName;// 省份名称
	private Long cityId;// 城市编号
	private String cityName;// 城市名称

	public BookingArea() {
		super();
	}

	public BookingArea(Long provinceId, String provinceName, Long cityId,
			String cityName) {
		super();
		this.provinceId = provinceId;
		this.provinceName = provinceName;
		this.cityId = cityId;
		this.cityName = cityName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Long getCityId() {
		return cityId;
	}

	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

}