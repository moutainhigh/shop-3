/**
 * 2012-7-8
 * jqsl2012@163.com
 */
package net.jeeshop.web.action.manage.news;

import java.io.IOException;
import java.util.List;

import net.jeeshop.core.BaseAction;
import net.jeeshop.core.ManageContainer;
import net.jeeshop.core.system.bean.User;
import net.jeeshop.services.manage.catalog.CatalogService;
import net.jeeshop.services.manage.indexImg.IndexImgService;
import net.jeeshop.services.manage.news.NewsService;
import com.tuisitang.modules.shop.entity.News;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 文章管理
 * @author huangf
 * 
 */
public class NewsAction extends BaseAction<News> {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(NewsAction.class);
	private NewsService newsService;
	private IndexImgService indexImgService;
	private CatalogService catalogService;
//	private String type;//文章类型。通知：notice；帮助：help
	
	private List<News> news;// 门户新闻列表
	
//	public String getType() {
//		return type;
//	}
//
//	public void setType(String type) {
//		this.type = type;
//	}

	public CatalogService getCatalogService() {
		return catalogService;
	}

	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	public NewsService getNewsService() {
		return newsService;
	}

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	public IndexImgService getIndexImgService() {
		return indexImgService;
	}

	public void setIndexImgService(IndexImgService indexImgService) {
		this.indexImgService = indexImgService;
	}

	public List<News> getNews() {
		return news;
	}

	public void setNews(List<News> news) {
		this.news = news;
	}

	@Override
	public News getE() {
		return this.e;
	}

	@Override
	public void prepare() throws Exception {
		if (this.e == null) {
			this.e = new News();
		}
		super.initPageSelect();
//		insertAfter(e);
	}

	@Override
	public void insertAfter(News e) {
		e.clear();
		
//		String type = e.getType();
//		e.clear();
//		e.setType(type);
	}
	
	/**
	 * 新增或者修改后文章的状态要重新设置为未审核状态
	 */
	@Override
	public String insert() throws Exception {
		logger.error("NewsAction code = " + e.getCode());
		User user = (User) getSession().getAttribute(ManageContainer.manage_session_user_info);
		getE().setCreateAccount(user.getUsername());
		getE().setStatus(News.STATUS_NO);//未审核
		
		getServer().insert(getE());
		
		getSession().setAttribute("insertOrUpdateMsg", "添加成功！");
		getResponse().sendRedirect(getEditUrl(e.getId().toString()));
		return null;
	}
	
	/**
	 * 修改文章
	 */
	@Override
	public String update() throws Exception {
		logger.error("NewsAction code = ");
		logger.error("NewsAction code = " + e.getCode()+",id="+e.getId());
//		getE().setStatus(News.STATUS_NO);//未审核
		
		getServer().update(getE());
		
		getSession().setAttribute("insertOrUpdateMsg", "更新成功！");
		getResponse().sendRedirect(getEditUrl(e.getId().toString()));
		return null;
	}
	
	//列表页面点击 编辑商品
	public String toEdit() throws Exception {
		getSession().setAttribute("insertOrUpdateMsg", "");
		return toEdit0();
	}
	
	/**
	 * 添加或编辑商品后程序回转编辑
	 * @return
	 * @throws Exception
	 */
	public String toEdit2() throws Exception {
		return toEdit0();
	}
	
	private String toEdit0() throws Exception {
		return super.toEdit();
	}
	
//	/**
//	 * 审核文章，审核通过后文章会显示在门户上
//	 */
//	public String shenhe() throws Exception {
//		String id = e.getId();
//		e.clear();
//		e.setId(id);
//		e.setStatus(News.STATUS_YES);//已审核
//		return super.update();
//	}
	
	/**
	 * 设置为自己
	 */
	@Deprecated
	private void settyMy() {
		User user = (User) getSession().getAttribute(ManageContainer.manage_session_user_info);
		if(!user.getRid().equals("1")){
			//只针对非管理员,管理员可以看到所有的文章
			getE().setCreateAccount(user.getUsername());
		}
	}

	/**
	 * 同步缓存内的新闻
	 * 审核通过，记录将会出现在门户上
	 * @return
	 * @throws Exception
	 */
	public String updateStatusY() throws Exception {
		newsService.updateStatus(getIds(),News.STATUS_YES);
		return super.selectList();
	}

	/**
	 * 显示指定的文章
	 * @return
	 * @throws Exception
	 */
	public String up() throws Exception {
		return updateDownOrUp0(News.STATUS_YES);
	}

	/**
	 * 不显示指定的文章
	 * @return
	 * @throws Exception
	 */
	public String down() throws Exception {
		return updateDownOrUp0(News.STATUS_NO);
	}
	
	private String updateDownOrUp0(String status) throws Exception {
		if(e.getId()==null){
			throw new NullPointerException("参数不能为空！");
		}
		
		News news = new News();
		news.setId(e.getId());
		news.setStatus(status);
		newsService.updateDownOrUp(news);
		getResponse().sendRedirect(getEditUrl(e.getId().toString()));
		return null;
	}
	
	private String getEditUrl(String id){
		return "news!toEdit2.action?e.id="+id;
	}
	
	/**
	 * 审核未通过,记录将不会出现在门户上
	 * @return
	 * @throws Exception
	 */
	public String updateStatusN() throws Exception {
		newsService.updateStatus(getIds(),News.STATUS_NO);
		return super.selectList();
	}

	@Override
	public String selectList() throws Exception {
//		logger.error("NewsAction.selectList.type="+type);
		super.selectList();
		return toList;
	}
	
	@Override
	protected void setParamWhenInitQuery() {
		super.setParamWhenInitQuery();
		String type = getRequest().getParameter("type");
		if(StringUtils.isNotBlank(type)){
			e.setType(type);
		}
	}
	
	/**
	 * 公共的分页方法
	 * 
	 * @return
	 * @throws Exception
	 */
//	public String selectList0() throws Exception {
//		/**
//		 * 由于prepare方法不具备一致性，加此代码解决init=y查询的时候条件不被清除干净的BUG
//		 */
//		this.initPageSelect();
//		e.setType(this.type);/////////////////
//		
//		int offset = 0;//分页偏移量
//		if (getRequest().getParameter("pager.offset") != null) {
//			offset = Integer
//					.parseInt(getRequest().getParameter("pager.offset"));
//		}
//		if (offset < 0)
//			offset = 0;
//		((PagerModel) getE()).setOffset(offset);
//		pager = getServer().selectPageList(getE());
//		if(pager==null)pager = new PagerModel();
//		// 计算总页数
//		pager.setPagerSize((pager.getTotal() + pager.getPageSize() - 1)
//				/ pager.getPageSize());
//		
//		selectListAfter();
//		
//		return toList;
//	}
	
	/**
	 * 检查文章code的唯一性
	 * @return
	 * @throws IOException
	 */
	public String unique() throws IOException{
		
		logger.error("检查文章code的唯一性");
		if(StringUtils.isBlank(e.getCode())){
			throw new NullPointerException("参数不能为空！");
		}
//		logger.error("wait...10s");
//		try {
//			Thread.sleep(10*1000L);
//		} catch (InterruptedException e1) {
//			e1.printStackTrace();
//		}
		
		int c = newsService.selectCount(e);
		getResponse().setCharacterEncoding("utf-8");
		if(e.getId()==null){
			if(c==0){
				getResponse().getWriter().write("{\"ok\":\"文章code可以使用!\"}");
			}else{
				getResponse().getWriter().write("{\"error\":\"文章code已经被占用!\"}");
			}
		}else{
			News news = newsService.selectById(e.getId());
			if(news.getCode().equals(e.getCode()) || c==0){
				getResponse().getWriter().write("{\"ok\":\"文章code可以使用!\"}");
			}else{
				getResponse().getWriter().write("{\"error\":\"文章code已经被占用!\"}");
			}
		}
		
		return null;
	}

	@Override
	public String deletes() throws Exception {
//		return super.deletes();
		logger.error("1..type="+e.getType());
		getServer().deletes(getIds());
		logger.error("2..type="+e.getType());
		return selectList();
	}
	
	@Override
	public String toAdd() throws Exception {
		String type = getRequest().getParameter("type");
		
		e.clear();
		e.setType(type);
		return toAdd;
	}
	
	@Override
	protected void selectListAfter() {
		pager.setPagerUrl("news!selectList.action");
	}
	
	
	@Override
	public void addActionError(String anErrorMessage) {
		super.addActionError(anErrorMessage);
		
		throw new RuntimeException("addActionError");
	}
	
	@Override
	public void addActionMessage(String aMessage) {
		super.addActionMessage(aMessage);
		throw new RuntimeException("addActionMessage");
	}
	
	@Override
	public void addFieldError(String fieldName, String errorMessage) {
		super.addFieldError(fieldName, errorMessage);
		throw new RuntimeException("addFieldError");
	}
}
