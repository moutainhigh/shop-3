package com.tuisitang.modules.shop.entity;

import java.io.Serializable;

/**    
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class Seller implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String logo;
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
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	
}