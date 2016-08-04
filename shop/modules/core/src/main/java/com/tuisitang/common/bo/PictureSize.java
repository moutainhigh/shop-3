package com.tuisitang.common.bo;

/**    
 * @{#} LoginType.java  
 * 
 * 图片尺寸
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public enum PictureSize {
	Picture_221_170("-221-170"), //今日推荐图片尺寸
	Picture_556_360("-556-360"),//团购图片尺寸 
	Picture_350_350("-350-350"),//首页产品列表图片尺寸
	;
	private String size;

	PictureSize(String size) {
		this.size = size;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

}
