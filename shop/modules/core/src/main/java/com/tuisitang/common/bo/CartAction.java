package com.tuisitang.common.bo;

/**    
 * @{#} CartAction.java  
 * 
 * 购物车的操作
 * -1 减少N个
 * 0 更新成N个
 * 1 增加N个
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public enum CartAction {
	Add(1, "新增"), 
	Sub(-1, "减少"),
	Set(0, "设置"),
	;

	private int action;
	private String name;

	CartAction(int action, String name) {
		this.action = action;
		this.name = name;
	}

	public int getAction() {
		return action;
	}

	public String getName() {
		return name;
	}
 
}
