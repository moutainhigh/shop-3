package com.tuisitang.modules.shop.entity;

import java.io.Serializable;

/**    
 * @{#} ProductCatalogRela.java   
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class ProductCatalogRela implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long productId;
	private Long catalogId;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(Long catalogId) {
		this.catalogId = catalogId;
	}

}