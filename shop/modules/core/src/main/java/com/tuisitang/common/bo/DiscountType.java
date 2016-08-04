package com.tuisitang.common.bo;

/**    
 * @{#} DiscountType.java  
 * 
 * 活动期间商品的折扣类型
 * 如果discountType=r，则此处是减免的金额；
 * 如果discountType=d则此处是折扣，5折就是50 ； 5.5折就是55
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public enum DiscountType {
	Reduction("r", "减免金额"), Discount("d", "折扣");

	private String type;
	private String name;

	DiscountType(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

}
