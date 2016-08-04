package com.tuisitang.modules.shop.entity;

import java.io.Serializable;

import net.jeeshop.core.dao.page.PagerModel;

/**    
 * @{#} News.java  
 * 
 * News Entity
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class News extends PagerModel<News> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String STATUS_YES = "y";// 显示
	public static final String STATUS_NO = "n";// 不显示

	/*
	 * 文章类型
	 */
	public static String TYPE_NOTICE = "notice";// 通知文章
	public static String TYPE_HELP = "help";// 帮助文章
	
	protected Long id;
	private String title;
	private String subtitle;
	private String code;
	private String content;
	private String createtime;
	private String createtimeEnd;// 页面查询条件
	private String updatetime;
	private int readerCount;
	private String status;// 文章是否显示到门户。y:显示；n：不显示；默认是n
	private Long catalogID;// 目录ID
	private String lableID;// 标签ID
	private String createAccount;// 创建人账号
	private int sort;// 顺序
	private String type;// 通知：notice；帮助：help

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCatalogID() {
		return catalogID;
	}

	public void setCatalogID(Long catalogID) {
		this.catalogID = catalogID;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getCreatetimeEnd() {
		return createtimeEnd;
	}

	public void setCreatetimeEnd(String createtimeEnd) {
		this.createtimeEnd = createtimeEnd;
	}

	public String getCreateAccount() {
		return createAccount;
	}

	public void setCreateAccount(String createAccount) {
		this.createAccount = createAccount;
	}

	public int getReaderCount() {
		return readerCount;
	}

	public void setReaderCount(int readerCount) {
		this.readerCount = readerCount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLableID() {
		return lableID;
	}

	public void setLableID(String lableID) {
		this.lableID = lableID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "News [id=" + id + ", title=" + title + ", subtitle=" + subtitle
				+ ", code=" + code + ", content=" + content + ", createtime="
				+ createtime + ", createtimeEnd=" + createtimeEnd
				+ ", updatetime=" + updatetime + ", readerCount=" + readerCount
				+ ", status=" + status + ", catalogID=" + catalogID
				+ ", lableID=" + lableID + ", createAccount=" + createAccount
				+ ", sort=" + sort + ", type=" + type + "]";
	}

}
