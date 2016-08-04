package com.tuisitang.modules.shop.entity;

import java.io.Serializable;
import java.util.Date;

import net.jeeshop.core.dao.page.PagerModel;

/**    
 * @{#} User.java
 * 
 * 用户Entity
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class User extends PagerModel<User> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String USER_STATUS_Y = "y";// 启用
	public static final String USER_STATUS_N = "n";// 禁用

	protected Long id;
	private String username;// 用户名
	private String password;// 密码 MD5加密
	private String name;// 姓名
	private String mobile;// 手机号
	private String email;// 邮箱
	private String nickname;// 昵称
	private Long rid;// 角色ID
	private Date createtTime;// 创建时间
	private Date updateTime;// 最后修改时间
	private String createAccount;// 创建人
	private String updateAccount;// 最后修改人
	private String status;// y:启用,n:禁用；默认y
	private Role role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public Date getCreatetTime() {
		return createtTime;
	}

	public void setCreatetTime(Date createtTime) {
		this.createtTime = createtTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getCreateAccount() {
		return createAccount;
	}

	public void setCreateAccount(String createAccount) {
		this.createAccount = createAccount;
	}

	public String getUpdateAccount() {
		return updateAccount;
	}

	public void setUpdateAccount(String updateAccount) {
		this.updateAccount = updateAccount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
