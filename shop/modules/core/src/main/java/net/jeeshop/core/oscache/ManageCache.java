package net.jeeshop.core.oscache;

import java.util.LinkedList;
import java.util.List;

import net.jeeshop.core.ManageContainer;
import net.jeeshop.core.TaskManager;
import net.jeeshop.core.front.SystemManager;
import net.jeeshop.services.manage.area.AreaService;
import net.jeeshop.services.manage.catalog.CatalogService;
import net.jeeshop.services.manage.comment.CommentService;
import net.jeeshop.services.manage.order.OrderService;
import net.jeeshop.services.manage.order.bean.OrdersReport;
import net.jeeshop.services.manage.oss.OssService;

import com.tuisitang.common.mapper.JsonMapper;
import com.tuisitang.modules.shop.entity.AliyunOSS;
import com.tuisitang.modules.shop.entity.Oss;
import com.tuisitang.modules.shop.entity.QiniuOSS;

import net.jeeshop.services.manage.product.ProductService;
import net.jeeshop.services.manage.systemSetting.SystemSettingService;
import net.jeeshop.services.manage.task.TaskService;

import com.tuisitang.modules.shop.entity.Task;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.tuisitang.modules.shop.entity.Catalog;
import com.tuisitang.modules.shop.entity.SystemSetting;

/**
 * 缓存管理器。 后台项目可以通过接口程序通知该类重新加载部分或全部的缓存
 * 
 * @author huangf
 * 
 */
public class ManageCache {
	private static final Logger logger = LoggerFactory.getLogger(ManageCache.class);
	
	/**
	 * manage后台
	 */
	private OrderService orderService;
	private ProductService productService;
	private CommentService commentService;
	private TaskService taskService;
	private OssService ossService;
	private AreaService areaService;
	
	private SystemSettingService systemSettingService;
	private CatalogService catalogService;
	
	public void setOssService(OssService ossService) {
		this.ossService = ossService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}
	
	public void setSystemSettingService(SystemSettingService systemSettingService) {
		this.systemSettingService = systemSettingService;
	}
	
	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	/**
	 * 加载订单报表
	 */
	public void loadOrdersReport(){
		SystemManager.ordersReport = orderService.loadOrdersReport();
		if(SystemManager.ordersReport==null){
			SystemManager.ordersReport = new OrdersReport();
		}
		//加载缺货商品数
		SystemManager.ordersReport.setOutOfStockProductCount(productService.selectOutOfStockProductCount());

		//加载吐槽评论数
		SystemManager.ordersReport.setNotReplyCommentCount(commentService.selectNotReplyCount());
		
		logger.info("SystemManager.ordersReport = " + SystemManager.ordersReport.toString());
	}
	
	/**
	 * 加载云存储配置信息
	 */
	public void loadOSS() {
		Oss oss = new Oss();
		oss.setStatus(Oss.oss_status_y);
		oss.setCode(Oss.code_qiniu);
		
		oss = ossService.selectOne(oss);
		if(oss!=null){
			if(oss.getCode().equals(Oss.code_aliyun)){
				if(StringUtils.isBlank(oss.getOssJsonInfo())){
					throw new NullPointerException("阿里云存储配置不能为空！");
				}
				AliyunOSS aliyunOSS = JSON.parseObject(oss.getOssJsonInfo(), AliyunOSS.class);
				if(aliyunOSS==null){
					throw new NullPointerException("阿里云配置不正确，请检查！");
				}
				SystemManager.aliyunOSS = aliyunOSS;
			} else if(oss.getCode().equals(Oss.code_qiniu)){
				if(StringUtils.isBlank(oss.getOssJsonInfo())){
					throw new NullPointerException("七牛存储配置不能为空！");
				}
				QiniuOSS qiniuOSS = JSON.parseObject(oss.getOssJsonInfo(), QiniuOSS.class);
				if(qiniuOSS==null){
					throw new NullPointerException("七牛配置不正确，请检查！");
				}
				SystemManager.qiniuOSS = qiniuOSS;
			}
		}else{
			SystemManager.aliyunOSS = null;
			SystemManager.qiniuOSS = null;
		}
	}
	
	/**
	 * 加载地区数据
	 */
	public void loadArea() {
		SystemManager.topArea = areaService.getTopArea();
	}
	
	/**
	 * 加载定时任务列表
	 */
	public void loadTask(){
		List<Task> list = taskService.selectList(new Task());
		if(list!=null){
			TaskManager.taskPool.clear();
			for(int i=0;i<list.size();i++){
				Task item = list.get(i);
				TaskManager.taskPool.put(item.getCode(),item);
			}
		}
	}
	
	/**
	 * 加载系统配置信息
	 */
	public void loadSystemSetting() {
		SystemManager.systemSetting = systemSettingService.selectOne(new SystemSetting());
		if(SystemManager.systemSetting==null){
			throw new NullPointerException("未设置本地环境变量，请管理员在后台进行设置");
		}
		
		//从环境变量中分解出图集来。
		if(StringUtils.isNotBlank(SystemManager.systemSetting.getImages())){
			String[] images = SystemManager.systemSetting.getImages().split(ManageContainer.PRODUCT_IMAGES_SPLIDER);
			if(SystemManager.systemSetting.getImagesList()==null){
				SystemManager.systemSetting.setImagesList(new LinkedList<String>());
			}else{
				SystemManager.systemSetting.getImagesList().clear();
			}
			
			for(int i=0;i<images.length;i++){
				SystemManager.systemSetting.getImagesList().add(images[i]);
			}
		}
		
	}
	
	/**
	 * 加载Catalog数据
	 */
	private void loadCatalogs() {
//		public static Map<Long,Catalog> catalogsMap = new HashMap<Long,Catalog>();
//		
//		/**
//		 * 目录的MAP形式，具有层级关系。key：主类别code，value：主类别对象，可以通过该对象的getChildren()方法获取该主类别的所有的子类别集合
//		 */
//		public static Map<String,Catalog> catalogsCodeMap = new HashMap<String,Catalog>();
		SystemManager.productCatalogJsonStr = null;
		SystemManager.articleCatalogJsonStr = null;
		{
			Catalog e = new Catalog(Catalog.catalog_type_p, 0L);
			SystemManager.catalogs = catalogService.selectList(e);
			for (Catalog c : SystemManager.catalogs) {
				SystemManager.catalogsMap.put(c.getId(), c);
				SystemManager.catalogsCodeMap.put(c.getCode(), c);
				c.setChildren(catalogService.selectList(new Catalog(Catalog.catalog_type_p, c.getId())));
				if (c.getChildren() != null && !c.getChildren().isEmpty()) {
					for (Catalog catalog : c.getChildren()) {
						SystemManager.catalogsMap.put(catalog.getId(), catalog);
						SystemManager.catalogsCodeMap.put(catalog.getCode(), catalog);
						catalog.setChildren(catalogService.selectList(new Catalog(Catalog.catalog_type_p, catalog.getId())));
						for (Catalog c3 : catalog.getChildren()) {
							SystemManager.catalogsMap.put(c3.getId(), c3);
							SystemManager.catalogsCodeMap.put(c3.getCode(), c3);
						}
					}
				}
			}

			String json = JsonMapper.getInstance().toJson(SystemManager.catalogs);
			logger.info("Product Catalog JSON {}", json);
		}
		{
			Catalog e = new Catalog(Catalog.catalog_type_a, 0L);
			SystemManager.catalogsArticle = catalogService.selectList(e);
			for (Catalog c : SystemManager.catalogsArticle) {
				c.setChildren(catalogService.selectList(new Catalog(Catalog.catalog_type_a, c.getId())));
			}

			String json = JsonMapper.getInstance().toJson(SystemManager.catalogs);
			logger.info("Article Catalog JSON {}", json);
		}
	}
	
	/**
	 * 加载全部的缓存数据
	 * @throws Exception 
	 */
	public void loadAllCache() throws Exception {
		logger.info("ManageCache.loadAllCache...");
		loadOrdersReport();
		loadTask();
		loadOSS();
		loadSystemSetting();
		loadCatalogs();
		loadArea();
		logger.info("后台缓存加载完毕!");
	}

}
