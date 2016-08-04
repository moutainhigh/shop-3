package com.tuisitang.modules.shop.entity;

import java.io.Serializable;

import net.jeeshop.core.dao.page.PagerModel;

/**    
 * @{#} Lable.java  
 * 
 * Lable Entity
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class Lable extends PagerModel<Lable> implements Serializable {
	private static final long serialVersionUID = 1L;
	protected Long id;
	private String name;
	private String description;
	private int type;
	private int lableIDSum;
	private int typeSum;
	private int sort;

	@Override
	public void clear() {
		super.clear();
		id = null;
		name = null;
		description = null;
		type = 0;
		lableIDSum = 0;
		typeSum = 0;
		sort = 0;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getLableIDSum() {
		return lableIDSum;
	}

	public void setLableIDSum(int lableIDSum) {
		this.lableIDSum = lableIDSum;
	}

	public int getTypeSum() {
		return typeSum;
	}

	public void setTypeSum(int typeSum) {
		this.typeSum = typeSum;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
