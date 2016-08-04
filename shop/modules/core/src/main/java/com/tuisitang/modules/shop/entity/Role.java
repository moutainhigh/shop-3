package com.tuisitang.modules.shop.entity;

import java.io.Serializable;

import net.jeeshop.core.dao.page.PagerModel;

/**    
 * @{#} Role.java
 * 
 * 角色Entity
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class Role extends PagerModel<Role> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String ROLE_STATUS_Y = "y";// 启用
	public static final String ROLE_STATUS_N = "n";// 禁用

	protected Long id;
	private String roleName;// 角色名称
	private String roleDesc;// 角色描述
	private String roleDbPrivilege;// 数据库权限
	private String status;// 角色状态，如果角色被禁用，则该角色下的所有的账号都不能使用，除非角色被解禁。
	
	public Role() {
		super();
	}

	public Role(String roleName, String roleDesc, String roleDbPrivilege,
			String status) {
		super();
		this.roleName = roleName;
		this.roleDesc = roleDesc;
		this.roleDbPrivilege = roleDbPrivilege;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public String getRoleDbPrivilege() {
		return roleDbPrivilege;
	}

	public void setRoleDbPrivilege(String roleDbPrivilege) {
		this.roleDbPrivilege = roleDbPrivilege;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
