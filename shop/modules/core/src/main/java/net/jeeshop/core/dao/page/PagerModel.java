package net.jeeshop.core.dao.page;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import net.jeeshop.core.ManageContainer;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

/**    
 * @{#} PagerModel.java   
 *    
 * 分页模型，也是所有实体类的基类 Entity  
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class PagerModel<T> implements ClearBean, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int total; // 总数
	private List<T> list = Lists.newArrayList(); // 分页集合列表
	private int pageSize = ManageContainer.PAGE_SIZE;// 每页显示记录数
	private int offset; // 偏移量，偏移量计算 = pageSize * ( pageNo - 1 )
	private int pageNo = 1;// 默认为1
	private int pagerSize;// 总页数
	protected String pagerUrl;// 分页标签需要访问的ACTION地址
	protected Long id;
	
	private int first;// 首页索引
	private int last;// 尾页索引
	private int prev;// 上一页索引
	private int next;// 下一页索引
	
	private boolean firstPage;//是否是第一页
	private boolean lastPage;//是否是最后一页
	
	/**
	 * 初始化参数
	 */
	public void initialize(){
				
		//1
		this.first = 1;
		
		this.last = (int)(total / (this.pageSize < 1 ? 20 : this.pageSize) + first - 1);
		
		if (this.total % this.pageSize != 0 || this.last == 0) {
			this.last++;
		}

		if (this.last < this.first) {
			this.last = this.first;
		}
		
		if (this.pageNo <= 1) {
			this.pageNo = this.first;
			this.firstPage=true;
		}

		if (this.pageNo >= this.last) {
			this.pageNo = this.last;
			this.lastPage=true;
		}

		if (this.pageNo < this.last - 1) {
			this.next = this.pageNo + 1;
		} else {
			this.next = this.last;
		}

		if (this.pageNo > 1) {
			this.prev = this.pageNo - 1;
		} else {
			this.prev = this.first;
		}
		
		//2
		if (this.pageNo < this.first) {// 如果当前页小于首页
			this.pageNo = this.first;
		}

		if (this.pageNo > this.last) {// 如果当前页大于尾页
			this.pageNo = this.last;
		}
		
	}

	@JsonIgnore
	public String getPagerUrl() {
		return pagerUrl;
	}

	public void setPagerUrl(String pagerUrl) {
		this.pagerUrl = pagerUrl;
	}

	@JsonIgnore
	public int getPagerSize() {
		return pagerSize;
	}

	public void setPagerSize(int pagerSize) {
		this.pagerSize = pagerSize;
	}

	@JsonIgnore
	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	@JsonIgnore
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	@JsonIgnore
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	@JsonIgnore
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<T> getList() {
		return list == null ? new LinkedList<T>() : list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.jeeshop.common.page.ClearBean#clear()
	 */
	public void clear() {
		total = 0; // 总数
		list = null; // 分页集合列表
		offset = 0; // 偏移量
		pagerSize = 0;// 总页数
		// pagerUrl = null;//分页标签需要访问的ACTION地址

		id = null;
	}

	public String trim(String str) {
		if (str == null) {
			return null;
		}
		return str.trim();
	}

	public void clearStringList(List<String> list) {
		if (list == null || list.size() == 0) {
			return;
		}
		list.clear();
		list = null;
	}
	
	public void clearList(List<Long> list) {
		if (list == null || list.size() == 0) {
			return;
		}
		list.clear();
		list = null;
	}

	public void clearSet(Set<String> set) {
		if (set == null || set.size() == 0) {
			return;
		}
		set.clear();
		set = null;
	}

	public void clearListBean(List<PagerModel<?>> list) {
		if (list == null || list.size() == 0) {
			return;
		}
		for (int i = 0; i < list.size(); i++) {
			ClearBean item = list.get(i);
			item.clear();
			item = null;
		}
		list.clear();
		list = null;
	}

	public void clearArray(String[] arr) {
		if (arr == null || arr.length == 0) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			arr[i] = null;
		}
		arr = null;
	}
	
	/**
	 * 首页索引
	 * @return
	 */
	@JsonIgnore
	public int getFirst() {
		return first;
	}

	/**
	 * 尾页索引
	 * @return
	 */
	@JsonIgnore
	public int getLast() {
		return last;
	}
	
	/**
	 * 获取页面总数
	 * @return getLast();
	 */
	@JsonIgnore
	public int getTotalPage() {
		return getLast();
	}

	/**
	 * 是否为第一页
	 * @return
	 */
	@JsonIgnore
	public boolean isFirstPage() {
		return firstPage;
	}

	/**
	 * 是否为最后一页
	 * @return
	 */
	@JsonIgnore
	public boolean isLastPage() {
		return lastPage;
	}
	
	/**
	 * 上一页索引值
	 * @return
	 */
	@JsonIgnore
	public int getPrev() {
		if (isFirstPage()) {
			return pageNo;
		} else {
			return pageNo - 1;
		}
	}

	/**
	 * 下一页索引值
	 * @return
	 */
	@JsonIgnore
	public int getNext() {
		if (isLastPage()) {
			return pageNo;
		} else {
			return pageNo + 1;
		}
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
