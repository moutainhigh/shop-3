package com.tuisitang.modules.shop.entity;

import net.jeeshop.core.dao.page.ClearBean;

/**    
 * @{#} ProductImageInfo.java   
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class ProductImageInfo implements ClearBean {

	private String image1;// 最小图
	private String image2;// 最小图
	private String image3;// 最大图 ==》原图

	public ProductImageInfo() {
		super();
	}

	public ProductImageInfo(String image1, String image2, String image3) {
		super();
		this.image1 = image1;
		this.image2 = image2;
		this.image3 = image3;
	}

	@Override
	public void clear() {
		this.image1 = null;
		this.image2 = null;
		this.image3 = null;
	}

	public String getImage1() {
		return image1;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	public String getImage3() {
		return image3;
	}

	public void setImage3(String image3) {
		this.image3 = image3;
	}

	public String getImage2() {
		return image2;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

}