package com.tuisitang.common.bo;

/**    
 * @{#} Status.java  
 * 
 * 状态 
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public enum Status {
	Success(0), // 成功
	Failure(1), // 失败
	Unknow(9)// 未知
	;

	private int status;

	Status(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

}
