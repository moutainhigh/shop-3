package com.tuisitang.modules.shop.entity;

import java.io.Serializable;

import net.jeeshop.core.dao.QueryModel;

/**    
 * @{#} Region.java  
 * 
 * Region Entity
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class Region extends QueryModel<Area> implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Long id;
	private String code;
	private String name;
	private String pcode;
	private String description;

	public Region() {
	}

	public Region(String code, String name, String pcode) {
		this.code = code;
		this.name = name;
		this.pcode = pcode;
	}

	public void clear() {
		super.clear();
		id = null;
		code = null;
		name = null;
		pcode = null;
		description = null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getPcode() {
		return pcode;
	}

	public void setPcode(String pcode) {
		this.pcode = pcode;
	}

	@Override
	public String toString() {
		return "Area [id=" + id + ", code=" + code + ", name=" + name
				+ ", pcode=" + pcode + ", description=" + description + "]";
	}

}
