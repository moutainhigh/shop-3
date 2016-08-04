//package com.tuisitang.projects.pm.modules.shop.entity;
//
//import java.util.Date;
//
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//import org.hibernate.annotations.Cache;
//import org.hibernate.annotations.CacheConcurrencyStrategy;
//import org.hibernate.annotations.DynamicInsert;
//import org.hibernate.annotations.DynamicUpdate;
//
//import com.tuisitang.projects.pm.common.persistence.BaseEntity;
//
//@Entity
//@Table(name = "shop_mq")
//@DynamicInsert @DynamicUpdate
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
//public class MessageQueue extends BaseEntity {
//
//	private Long id;
//	private Long customerId;
//	private String action;
//	private String msg;// JSON 格式
//	private Date operateTime;
//	private String delFlag;// 删除标识：0  1
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
////	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_sys_log")
////	@SequenceGenerator(name = "seq_sys_log", sequenceName = "seq_sys_log")
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public Long getCustomerId() {
//		return customerId;
//	}
//
//	public void setCustomerId(Long customerId) {
//		this.customerId = customerId;
//	}
//
//	public String getAction() {
//		return action;
//	}
//
//	public void setAction(String action) {
//		this.action = action;
//	}
//
//	public String getMsg() {
//		return msg;
//	}
//
//	public void setMsg(String msg) {
//		this.msg = msg;
//	}
//
//	public Date getOperateTime() {
//		return operateTime;
//	}
//
//	public void setOperateTime(Date operateTime) {
//		this.operateTime = operateTime;
//	}
//
//	public String getDelFlag() {
//		return delFlag;
//	}
//
//	public void setDelFlag(String delFlag) {
//		this.delFlag = delFlag;
//	}
//
//}
