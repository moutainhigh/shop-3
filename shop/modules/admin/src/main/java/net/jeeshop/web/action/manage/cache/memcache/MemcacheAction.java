package net.jeeshop.web.action.manage.cache.memcache;

import org.apache.struts2.ServletActionContext;

import net.jeeshop.services.manage.cache.memcache.MemcacheService;

public class MemcacheAction{
	private MemcacheService memcacheService;
	/**
	 * 对应页面上的选中的复选框，提单到后台的时候会自动进入该数组
	 */
	protected int[] ids;
	
	public String getAllMemcacheObj() {
		ServletActionContext.getRequest().setAttribute("memcacheIdList", memcacheService.getAllMemcacheIds()); 
		return "toListIds";
	}
	
	public String toFlushMemcache() {
		return "toFlushCache";
	}
	
	public String flushMemcache() {
		int[] selectIds = getIds();
		for (int i : selectIds) {
			flush(i);
		}
		
		return "toFlushCache";
	}

	private void flush(int i) {
		switch (i) {
		case 1:
			flushProduct();
			break;
		case 2:
			flushActivityProduct();
			break;
		case 3:
			flushProductAttribute();
			break;
		case 4:
			flushProductParam();
			break;
		case 5:
			flushHotRecommend();
			break;
		case 6:
			flushHot();
			break;
		case 7:
			flushNew();
			break;
		case 8:
			break;
		case 9:
			break;
		case 10:
			break;
		case 11:
			break;
		case 12:
			break;
		case 13:
			break;
		case 14:
			break;
		case 15:
			break;
		default:
			break;
		}
	}

	private void flushNew() {
		// TODO Auto-generated method stub
		
	}

	private void flushHot() {
		// TODO Auto-generated method stub
		
	}

	private void flushHotRecommend() {
		// TODO Auto-generated method stub
		
	}

	private void flushProductParam() {
		// TODO Auto-generated method stub
		
	}

	private void flushProductAttribute() {
		// TODO Auto-generated method stub
		
	}

	private void flushActivityProduct() {
		// TODO Auto-generated method stub
		
	}

	private void flushProduct() {
		
	}

	public void setMemcacheService(MemcacheService memcacheService) {
		this.memcacheService = memcacheService;
	}

	public int[] getIds() {
		return ids;
	}

	public void setIds(int[] ids) {
		this.ids = ids;
	}
}
