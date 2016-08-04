package net.jeeshop.core.system.bean;

import net.jeeshop.core.dao.page.PagerModel;

/**
 * 权限
 * @author huangf
 *
 */
public class Privilege extends PagerModel<Privilege>{
	private Long rid;
	private Long mid;

	public Long getRid() {
		return rid;
	}

	public void setRid(Long rid) {
		this.rid = rid;
	}

	public Long getMid() {
		return mid;
	}

	public void setMid(Long mid) {
		this.mid = mid;
	}

	public void clear() {
		this.id = null;
		this.mid = null;
		this.rid = null;
	}

}
