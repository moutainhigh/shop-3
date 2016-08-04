package com.tuisitang.modules.shop.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import net.jeeshop.core.dao.QueryModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.tuisitang.common.cache.memcached.MemcachedObjectType;

/**    
 * @{#} BookingOrder.java  
 * 
 * 预订单/预约单 Entity
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class BookingOrder extends QueryModel<BookingOrder> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int STATUS_NEW = 0;// 新建，已预订
	public static final int STATUS_AUDITED = 1;// 已收单
	public static final int STATUS_DISPATCHED = 2;// 已派单，已安排
	public static final int STATUS_COMPLETE = 3;// 已派单，已安排
	public static final int STATUS_COMMENT = 4;// 已评价
	public static final int STATUS_CANCEL = 9;// 已取消
	
	public static final int TYPE_ALL = 0;// 所有预约单
	public static final int TYPE_BOOKING = 1;// 预约中：新建、已收单、已派单
	public static final int TYPE_COMPLETE = 2;// 完成：已完成、已评价
	public static final int TYPE_CANCEL = 3;// 已取消

	protected Long id;// 编号
	private String no;// 预约单号
	private String sessionId;// session编号
	private Long accountId;// 账号编号
	private String name;// 姓名
	private String mobile;// 手机号
	private Date createTime;// 创建时间
	private Date bookingTime;// 预约时间
	private Long provinceId;// 省份编号
	private String provinceName;// 省份名称
	private Long cityId;// 城市编号
	private String cityName;// 城市名称
	private Long countyId;// 乡镇编号
	private String countyName;// 乡镇名称
	private String address;// 详细地址
	private double longitude;// 经度
	private double latitude;// 维度
	private String demand;// 详细需求
	private int status;// 状态 0 新建，已预订 1 已收单 2 已派单，已安排 3 已完成 4 已评价 9 已取消
	private String reasonId;// 取消原因编号
	private String reason;// 取消原因
	private String remark;// 备注
	
	private List<BookingOrderLog> bookingOrderLogList = Lists.newArrayList();
	
	public BookingOrder() {
		
	}
	
	public BookingOrder(String sessionId, Long accountId, String name,
			String mobile, Date createTime, Date bookingTime, Long provinceId,
			String provinceName, Long cityId, String cityName, Long countyId,
			String countyName, String address, double longitude,
			double latitude, String demand, int status) {
		this.sessionId = sessionId;
		this.accountId = accountId;
		this.name = name;
		this.mobile = mobile;
		this.createTime = createTime;
		this.bookingTime = bookingTime;
		this.provinceId = provinceId;
		this.provinceName = provinceName;
		this.cityId = cityId;
		this.cityName = cityName;
		this.countyId = countyId;
		this.countyName = countyName;
		this.address = address;
		this.longitude = longitude;
		this.latitude = latitude;
		this.demand = demand;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getBookingTime() {
		return bookingTime;
	}

	public void setBookingTime(Date bookingTime) {
		this.bookingTime = bookingTime;
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

	public Long getCountyId() {
		return countyId;
	}

	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getDemand() {
		return demand;
	}

	public void setDemand(String demand) {
		this.demand = demand;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReasonId() {
		return reasonId;
	}

	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<BookingOrderLog> getBookingOrderLogList() {
		return bookingOrderLogList;
	}

	public void setBookingOrderLogList(List<BookingOrderLog> bookingOrderLogList) {
		this.bookingOrderLogList = bookingOrderLogList;
	}

	@JsonIgnore
	public static String getKey() {
		return MemcachedObjectType.Order.getPrefix() + "BO";
	}
}
