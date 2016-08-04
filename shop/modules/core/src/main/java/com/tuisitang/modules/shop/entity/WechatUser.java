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
public class WechatUser extends UserInfo implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Long id;// 编号
	protected String status;// 状态
	protected Date createTime;// 创建日期
	private String unionid;
	private String mobile;// 绑定的手机号
	
	public WechatUser() {
		if (StringUtils.isBlank(this.status))
			this.status = "0";
		if (this.createTime == null)
			this.createTime = new Date();
	}

	public WechatUser(int subscribe, String openid, String nickname, int sex,
			String city, String province, String country, String language,
			String headimgurl, Long subscribe_time, String unionid) {
		this();
		this.subscribe = subscribe;
		this.openid = openid;
		this.nickname = nickname;
		this.sex = sex;
		this.city = city;
		this.province = province;
		this.country = country;
		this.language = language;
		this.headimgurl = headimgurl;
		this.subscribe_time = subscribe_time;
		this.unionid = unionid;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getSubscribe_time() {
		return subscribe_time;
	}

	public void setSubscribe_time(Long subscribe_time) {
		this.subscribe_time = subscribe_time;
	}
	
	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.openid).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WechatUser other = (WechatUser) obj;
		return new EqualsBuilder().append(openid, other.getOpenid()).isEquals();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}