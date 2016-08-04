package com.tuisitang.modules.shop.entity;

import java.io.Serializable;
import java.util.Date;

import net.jeeshop.core.dao.QueryModel;

/**    
 * @{#} BookingOrderLog.java  
 * 
 * 预订单/预约单日志 Entity
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class BookingOrderLog extends QueryModel<BookingOrderLog> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String ACTION_NEW = "new";// 新建
	public static final String ACTION_AUDIT = "audit";// 审核
	public static final String ACTION_DISPATCH = "dispatch";// 派单
	public static final String ACTION_COMPLETE = "complete";// 结束
	public static final String ACTION_CANCEL = "cancel";// 取消
	
	public static final String OPERATE_TYPE_FRONT = "0";// 0 前台 
	public static final String OPERATE_TYPE_BACK = "1";// 1 后台

	protected Long id;// 编号
	private Long bookingOrderId;//
	private Date createTime;// 创建时间
	private String action;// 动作 new 新建 audit 已审核 dispatch 派单 complete 结束
	private String description;// 描述
	private Long operateId;// 操作人员编号
	private String operateName;// 操作人员名称
	private String operateType;// 操作人员类型：0 前台 1 后台

	public BookingOrderLog() {
		super();
	}

	public BookingOrderLog(Long bookingOrderId, String action,
			String description, Long operateId, String operateName,
			String operateType) {
		super();
		this.bookingOrderId = bookingOrderId;
		this.action = action;
		this.description = description;
		this.operateId = operateId;
		this.operateName = operateName;
		this.operateType = operateType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getBookingOrderId() {
		return bookingOrderId;
	}

	public void setBookingOrderId(Long bookingOrderId) {
		this.bookingOrderId = bookingOrderId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getOperateId() {
		return operateId;
	}

	public void setOperateId(Long operateId) {
		this.operateId = operateId;
	}

	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

}
