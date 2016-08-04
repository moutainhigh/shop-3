package com.tuisitang.modules.shop.front.web;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.jeeshop.core.FrontContainer;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.core.util.LRULinkedHashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tuisitang.common.bo.Status;
import com.tuisitang.common.utils.HttpServletRequestUtils;
import com.tuisitang.common.utils.SpringContextHolder;
import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.entity.Activity;
import com.tuisitang.modules.shop.entity.Catalog;
import com.tuisitang.modules.shop.entity.Favorite;
import com.tuisitang.modules.shop.entity.Gift;
import com.tuisitang.modules.shop.entity.Product;
import com.tuisitang.modules.shop.front.event.ProductTraceEvent;
import com.tuisitang.modules.shop.front.service.ProductFrontService;
import com.tuisitang.modules.shop.front.utils.AccountUtils;
import com.tuisitang.modules.shop.front.vo.ProductVo;

/**    
 * @{#} ProductController.java   
 * 
 * 产品Controller
 * 
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@Controller
@RequestMapping("/product")
public class ProductController extends BaseController {
	@Autowired
	private ProductFrontService productFrontService;
	@Autowired
	public static final Object INSERT_FAVORITE_LOCK = new Object();//收藏夹锁.防止添加重复记录
	private static DecimalFormat df = new DecimalFormat("0.00");
	private static final int PRODUCT_PAGE_SIZE = 24;
	
	/**
	 * 根据产品Id查询产品详情
	 */
	@RequestMapping("/{productId}")
	public String getProductDetail(@PathVariable("productId") Long productId,@RequestParam(required=false) Long catalogId,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		String sessionId = AccountUtils.getSessionId(request);
		Account account = AccountUtils.getAccount();
		//是否已登录
		boolean isLogin = AccountUtils.isUserLogin();
		model.addAttribute("isLogin", isLogin);
		
		Product product = productFrontService.getProductById(productId);
		if (product == null)
			return "redirect:/mall";
		
		//发布更新浏览记录事件
		publishEvent(new ProductTraceEvent(this, productId, product.getName(), sessionId, account, HttpServletRequestUtils.getHttpServletRequestMap(request)));
		/**
		 * 保存用户浏览的商品信息
		 */
		saveProductHistory(product, request.getSession());

		/**
		 * 是否需要显示到货通知的按钮
		 */
//		if(product.getStock()<=0){
//			Account account = AccountUtils.getAccount();
//			if(account!=null){
//				//如果用户之前没有填写过到货通知的申请，则可以提示用户填写。
//				EmailNotifyProduct ep = new EmailNotifyProduct();
//				ep.setAccountId(account.getId());
//				ep.setProductID(product.getId());
//				if(emailNotifyProductService.selectCount(ep)<=0){
//					e.setShowEmailNotifyProductInput(true);
//				}
//			}
//		}
		
		/**
		 * 生成展示Vo
		 */
		ProductVo productVo = generateProductVo(product);
		
		//设置目录路径
		Catalog catalog = getCatalog(productId, catalogId);
		
		if (catalog != null) {
			List<Catalog> catalogPath = productFrontService.getCatalogPath(catalog);
			productVo.setCatalogPath(catalogPath);
		}
		//加载热门推荐商品
		List<Product> hotRecommendProduct = productFrontService.getHotRecommendProduct(catalog == null ? null : catalog.getId());
		model.addAttribute("hotRecommendProduct", hotRecommendProduct);

		/*
		 * 如果商品是活动商品，则查看商品明细页的时候自动选择导航菜单li
		 */
//		Activity activity = product.getActivity();
//		if (activity != null) {
//			if(activity.getActivityType().equals(Activity.activity_activityType_c)){
//				request.setAttribute("selectMenu","activity");
//			}else if(activity.getActivityType().equals(Activity.activity_activityType_j)){
//				request.setAttribute("selectMenu","score");
//			}else if(activity.getActivityType().equals(Activity.activity_activityType_t)){
//				request.setAttribute("selectMenu","tuan");
//			}
//		}
		
		model.addAttribute("productVo", productVo);
		
		return "modules/front/product/productDetail";
	}
	
	/**
	 * 设置目录路径
	 * @param productVo
	 * @param catalogId
	 */
	private Catalog getCatalog(Long productId, Long catalogId) {
		//由于一个产品可以挂在多个目录,如果传了目录Id则设置目录Id,未传则取第一个目录
		Catalog catalog = null;
		if (catalogId == null) {
			List<Catalog> catalogList = productFrontService.getCatalogByProduct(productId);
			//未指定,默认取第一个
			if (catalogList != null && !catalogList.isEmpty()) {
				catalog = catalogList.get(0);
			}
		} else {
			catalog = productFrontService.getCatalogById(catalogId);
		}
		return catalog;
	}

	private void saveProductHistory(Product product, HttpSession session) {
		@SuppressWarnings("unchecked")
		LinkedHashMap<Long, Product> historyMap = (LinkedHashMap<Long, Product>) session.getAttribute(Product.PRODUCT_HISTORY_LIST);
		if(historyMap==null){
			historyMap = new LRULinkedHashMap<Long, Product>(16, 0.75f, true);
			session.setAttribute(Product.PRODUCT_HISTORY_LIST, historyMap);
		}else {
			Product pro = new Product();
			pro.setId(product.getId());
			pro.setName(product.getName());
			pro.setPrice(product.getPrice());
			pro.setNowPrice(product.getNowPrice());
			pro.setPicture(product.getPicture());
			historyMap.put(product.getId(), pro);
			session.setAttribute(Product.PRODUCT_HISTORY_LIST, historyMap);
		}
	}

	/**
	 * 根据产品生成产品视图对象
	 * @param product
	 */
	private ProductVo generateProductVo(Product product) {
		ProductVo productVo = new ProductVo();
		productVo.setProduct(product);
//		/**
//		 * 设置商品规格
//		 */
//		productFrontService.setProductSpec(productVo);
		
		/**
		 * 组装商品详情页的图片地址
		 */
		productImagesBiz(productVo);
		
		/**
		 * 如果商品绑定了赠品，则读取绑定的赠品信息
		 */
		if(product.getGiftID() != null){
			Gift gift = productFrontService.getGiftById(product.getGiftID());
			productVo.setGift(gift);
		}
		
		//如果商品已上架并且商品的库存数小于等于0，则更新商品为已下架
		if(product.getStatus()==Product.Product_status_y){
			if(product.getStock()<=0){
				productVo.setProductSorryStr("抱歉，商品已售卖完。");
				productVo.setOutOfStock(true);
			}
		}
		//加载商品参数
		productVo.setParameterList(productFrontService.selectParameterList(product.getId()));
		
		/**
		 * 检查，如果此商品是活动商品，则加载相应的活动信息。
		 */
		logger.error("e.getActivityID() = "+product.getActivityID());
//		if(product.getActivityID() != null){
//			Activity activity = productFrontService.getActivityById(product.getActivityID());
//			/**
//			 * 计算产品最终价格
//			 */
//			productVo.setFinalPrice(df.format(productFrontService.caclFinalPrice(product, activity)));
//			product.setActivity(activity);
//		}
		/**
		 * Edit By Bin
		 * 加入活动，根据活动计算Product的nowPrice
		 * Spec的price
		 */
		if(product.getActivity() != null){
			/**
			 * 计算产品最终价格
			 */
			productVo.getProduct().setPrice(productVo.getProduct().getNowPrice());
			productVo.getProduct().setNowPrice(productFrontService.caclFinalPrice(product, product.getActivity()));
		}
		
		/**
		 * 设置商品规格
		 */
		productFrontService.setProductSpec(productVo, product.getActivity());
		
		/**
		 * 统计产品收藏数量
		 */
		productVo.setProductFavoriteCount(productFrontService.countFavoriteByProductId(product.getId()));
		
		/**
		 * 当前产品是否已收藏
		 */
		if (AccountUtils.isUserLogin()) {
			Account account = AccountUtils.getAccount();
			Favorite favorite = new Favorite(product.getId(), account.getId());
			productVo.setFavorite(productFrontService.isSavedInFavorite(favorite));
		}
		return productVo;
	}


	/**
	 * 添加产品到收藏
	 */
	@RequestMapping(value = "favorite/{productId}", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> addToFavorite(@PathVariable("productId") Long productId,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		Account account = AccountUtils.getAccount();
		if (!AccountUtils.isUserLogin()) {
			return buildReturnMap(Status.Unknow, "未登录");
		}
		Favorite favorite = new Favorite(productId, account.getId());
		if (productFrontService.isSavedInFavorite(favorite)) {
			return buildReturnMap(Status.Success, "该商品已经被收藏");
		} else {
			Product product = productFrontService.getProductById(productId);
			favorite.setProductName(product.getName());
			favorite.setCreateTime(new Date());
			productFrontService.saveFavorite(favorite);
			return buildReturnMap(Status.Success, "添加收藏成功");
		}
	}
	
	public String getCookieNameByProductId(Long productId) {
		return FrontContainer.PRODUCT_FAVORITE_COOKIE_KEY + "_" + productId.toString();
	}
	
	/**
	 * 商品详情页面，图片列表的处理
	 */
	private void productImagesBiz(ProductVo productVo) {
		Product product = productVo.getProduct();
		StringBuilder imagesBuff = new StringBuilder();
		if(StringUtils.isNotBlank(product.getImages())){
			imagesBuff.append(product.getImages());
		}else {
			imagesBuff.append(product.getPicture() + FrontContainer.PRODUCT_IMAGES_SPLIDER);
		}	
		String imagesStr = imagesBuff.toString();
		if(StringUtils.isBlank(imagesStr)){
			return;
		}
		
		String[] images = imagesStr.split(FrontContainer.PRODUCT_IMAGES_SPLIDER);
		List<String> imageList = Lists.newArrayList();
		for (String image : images) {
			if (!StringUtils.isBlank(image)) {
				imageList.add(image);
			}
		}
		productVo.setImageList(imageList);
	}
	
	/**
	 * 产品列表
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public String productList(@RequestParam(required=false) Long catalogId, HttpServletRequest request, HttpServletResponse response, Model model) {
		Map<String, Object> params = Maps.newHashMap();
		List<Catalog> catalogs = super.getCatalogCacheList();
		List<Catalog> catalogClass = null;
		if (catalogId != null) {
			Catalog catalog = productFrontService.getCatalogById(catalogId);
			model.addAttribute("catalogId", catalog.getId());
			//目录路径
			List<Catalog> catalogPath = productFrontService.getCatalogPath(catalog);
			model.addAttribute("catalogPath", catalogPath);
			//分类列表
			catalogClass = productFrontService.getCatalogClass(catalog, catalogs);
			model.addAttribute("catalogClass", catalogClass);
			//属性列表
//			List<Attribute> attributeList = productService.getAttributeByCatalog(catalogId);
//			model.addAttribute("attributeList", attributeList);
			//查询当前所有子目录ID
			List<Long> subCatalogId = productFrontService.getSubCatalogIds(catalogId, catalogs);
			if (subCatalogId == null) {
				subCatalogId = Lists.newArrayList();
			}
			subCatalogId.add(catalogId);
			params.put("catalogIds", subCatalogId);
		} else {
			//无目录信获取一级分类列表
			catalogClass = getFirstCatalog(catalogs);
			model.addAttribute("catalogClass", catalogClass);
		}
		//关键字搜索
		String keyword = request.getParameter("k");
		if (!StringUtils.isBlank(keyword)) {
			model.addAttribute("keyword", keyword);
			params.put("keyword", keyword);
		}
		//产品价格
		String priceRange = request.getParameter("price");
		if (!StringUtils.isBlank(priceRange)) {
			String[] range = priceRange.split("-");
			params.put("startPrice", range[0]);
			if (range.length == 2) {
				params.put("endPrice", range[1]);
			}
			model.addAttribute("price", priceRange);
		}
		//排序
		String orderKey = request.getParameter("orderKey");
		String orderValue = request.getParameter("orderValue");
		if (!StringUtils.isBlank(orderKey)) {
			if ("1".equals(orderKey)) {//最新
				params.put("isNew", "y");
				params.put("orderBy", "newSort");
				model.addAttribute("orderKey", orderKey);
			} else if ("2".equals(orderKey)) {//销量
				model.addAttribute("orderKey", orderKey);
				model.addAttribute("orderValue", orderValue);
				if ("up".equals(orderValue)) {
					params.put("orderBy", "sellCountUp");
				} else {
					params.put("orderBy", "sellCountDown");
				}
			} else if ("3".equals(orderKey)) {//价格
				model.addAttribute("orderKey", orderKey);
				model.addAttribute("orderValue", orderValue);
				if ("up".equals(orderValue)) {
					params.put("orderBy", "priceUp");
				} else {
					params.put("orderBy", "priceDown");
				}
			} else if ("4".equals(orderKey)) {//点击数
				model.addAttribute("orderKey", orderKey);
				model.addAttribute("orderValue", orderValue);

				if ("up".equals(orderValue)) {
					params.put("orderBy", "hitUp");
				} else {
					params.put("orderBy", "hitDown");
				}
			} else if ("5".equals(orderKey)) {//特卖
				params.put("sale", "y");
				model.addAttribute("orderKey", orderKey);
			}
		}
		int pageSize = StringUtils.isBlank(request.getParameter("pageSize")) ? PRODUCT_PAGE_SIZE : new Integer(request.getParameter("pageSize"));
		int pageNo = StringUtils.isBlank(request.getParameter("pageNo")) ? 0 : new Integer(request.getParameter("pageNo"));;
		PagerModel<Product> page = productFrontService.findPageProduct(params, pageNo, pageSize);
//		page.setPagerUrl("/product/list/" + catalog.getId());
		model.addAttribute("page", page);
		return "modules/front/product/productList";
	}
	
	/**
	 * 获取一级目录
	 * @param catalogs
	 */
	private List<Catalog> getFirstCatalog(List<Catalog> catalogs) {
		List<Catalog> catalogClass = Lists.newArrayList();
		for (Catalog catalog : catalogs) {
			catalogClass.add(catalog);
		}
		return catalogClass;
	}
	
	/**
	 * 团购产品列表
	 */
//	@RequestMapping(value = "group/productList/{catalogId}", method = { RequestMethod.GET, RequestMethod.POST })
//	public String groupProductList(@PathVariable("catalogId") Long catalogId, HttpServletRequest request, HttpServletResponse response, Model model) {
//		
//		
//	}
	
//	/**
//	 * 获取账户所有收藏产品
//	 */
//	@RequestMapping(value = "/favorate", method = { RequestMethod.GET, RequestMethod.POST })
//	public String findFavorate(@RequestParam(required=false) Long catalogId, HttpServletRequest request, HttpServletResponse response, Model model) {
	
	
}
