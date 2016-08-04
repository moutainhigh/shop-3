package com.tuisitang.modules.shop.api.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jeeshop.core.FrontContainer;
import net.jeeshop.core.dao.page.PagerModel;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tuisitang.common.mapper.JsonMapper;
import com.tuisitang.modules.shop.entity.Activity;
import com.tuisitang.modules.shop.entity.Catalog;
import com.tuisitang.modules.shop.entity.Product;
import com.tuisitang.modules.shop.entity.ProductAttribute;
import com.tuisitang.modules.shop.entity.Spec;
import com.tuisitang.modules.shop.service.ProductService;
import com.tuisitang.modules.shop.utils.Global;

/**    
 * @{#} ProductApiController.java   
 * 
 * 商品 Api Controller
 * 
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: irtone</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 *  
 */
@Controller
@RequestMapping(value = "/api/product")
public class ProductApiController extends BaseApiController {
	
	@Autowired
	private ProductService productService;

	/**
	 * 1. Bubu3141592653
	 */
	@RequestMapping(value = "categories")
	public @ResponseBody String categories(@RequestParam(value = "callback", required = false) String callback,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> returnMap = Maps.newHashMap();
//		JsonMapper mapper = JsonMapper.nonEmptyMapper();
		JsonMapper mapper = JsonMapper.getInstance();
		String imageRootPath = (String) request.getSession().getServletContext().getAttribute(Global.IMAGE_ROOT_PATH);

		List<Catalog> list = (List<Catalog>) request.getSession().getServletContext().getAttribute(Global.PRODUCT_CATALOG_LIST);
		for (Catalog c : list) {
			processCatalog(imageRootPath, c);
		}
		returnMap.put("s", "0");
		returnMap.put("categories", list);
		if (StringUtils.isBlank(callback)) {
			return mapper.toJson(returnMap);
		} else {
			return mapper.toJsonp(callback, returnMap);
		}
	}
	
	@RequestMapping(value = "detail")
	public @ResponseBody String productDetail(@RequestParam(value = "goodsId", required = true) Long productId, @RequestParam(required=false) Long catalogId, @RequestParam(value = "callback", required = false) String callback,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> returnMap = Maps.newHashMap();
//		JsonMapper mapper = JsonMapper.getInstance();
		JsonMapper mapper = JsonMapper.alwaysMapper();
		String imageRootPath = (String) request.getSession().getServletContext().getAttribute(Global.IMAGE_ROOT_PATH);
		
		Product product = productService.getProductById(productId);
//		//发布更新浏览记录事件
//		publishEvent(new ProductTraceEvent(this, productId, product.getName(), sessionId, account, HttpServletRequestUtils.getHttpServletRequestMap(request)));
		
		/**
		 * 设置商品规格
		 */
		List<Spec> specs = productService.getProductSpec(productId);
	
		returnMap.put("specs", specs);
		/**
		 * 组装商品详情页的图片地址
		 */
		StringBuilder imagesBuff = new StringBuilder();
		if(StringUtils.isNotBlank(product.getImages())){
			imagesBuff.append(product.getImages());
		}else {
			imagesBuff.append(product.getPicture() + FrontContainer.PRODUCT_IMAGES_SPLIDER);
		}	
		String imagesStr = imagesBuff.toString();
		
		String[] images = imagesStr.split(FrontContainer.PRODUCT_IMAGES_SPLIDER);
		List<String> imageList = Lists.newArrayList();
		for (String image : images) {
			if (!StringUtils.isBlank(image)) {
				imageList.add(imageRootPath + image);
			}
		}
		returnMap.put("imageList", imageList);

		
//		/**
//		 * 如果商品绑定了赠品，则读取绑定的赠品信息
//		 */
//		if(product.getGiftID() != null){
//			Gift gift = productFrontService.getGiftById(product.getGiftID());
//		}
		//加载商品参数
		List<ProductAttribute> attribute = productService.selectParameterList(product.getId());
		returnMap.put("attributes", attribute);

		/**
		 * 检查，如果此商品是活动商品，则加载相应的活动信息。
		 */
		logger.error("e.getActivityID() = "+product.getActivityID());
		if(product.getActivityID() != null){
			Activity activity = productService.getActivityById(product.getActivityID());
			/**
			 * 计算产品最终价格
			 */
			product.setNowPrice(productService.caclFinalPrice(product, activity));
			product.setActivity(activity);
		}
		
//		/**
//		 * 统计产品收藏数量
//		 */
//		int countFavor = productService.countFavoriteByProductId(product.getId());
		
		//加载热门推荐商品
		List<Product> hotRecommendProduct = productService.getHotRecommendProduct(catalogId == null ? null : catalogId);
		returnMap.put("recommends", productListToMapList(request, hotRecommendProduct));
		returnMap.put("product", product);
		returnMap.put("root", imageRootPath);
		returnMap.put("s", "0");
		
		if (StringUtils.isBlank(callback)) {
			return mapper.toJson(returnMap);
		} else {
			return mapper.toJsonp(callback, returnMap);
		}
	}
	
	@RequestMapping(value = {"","list"})
	public @ResponseBody String list(@RequestParam(value = "callback", required = false) String callback,
			@RequestParam(value = "catalogId", required = false) Long catalogId,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> returnMap = buildReturnMap(SUCCESS, "");
		String imageRootPath = (String) request.getSession().getServletContext().getAttribute(Global.IMAGE_ROOT_PATH);

		Map<String, Object> params = Maps.newHashMap();
		if (catalogId != null) {
			List<Catalog> catalogs = (List<Catalog>) request.getSession().getServletContext().getAttribute(Global.PRODUCT_CATALOG_LIST);
			//查询当前所有子目录ID
			List<Long> subCatalogId = productService.getSubCatalogIds(catalogId, catalogs);
			if (subCatalogId == null) {
				subCatalogId = Lists.newArrayList();
			}
			subCatalogId.add(catalogId);
			params.put("catalogIds", subCatalogId);
		}
		
		//关键字搜索
		String keyword = request.getParameter("k");
		if (!StringUtils.isBlank(keyword)) {
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
//			model.addAttribute("price", priceRange);
		}
		//排序
		String orderKey = request.getParameter("orderKey");
		String orderValue = request.getParameter("orderValue");
		if (!StringUtils.isBlank(orderKey)) {
			if ("1".equals(orderKey)) {//最新
				params.put("isNew", "y");
//				model.addAttribute("orderKey", orderKey);
			} else if ("2".equals(orderKey)) {//销量
//				model.addAttribute("orderKey", orderKey);
//				model.addAttribute("orderValue", orderValue);
				if ("up".equals(orderValue)) {
					params.put("orderBy", "sellCountUp");
				} else {
					params.put("orderBy", "sellCountDown");
				}
			} else if ("3".equals(orderKey)) {//价格
//				model.addAttribute("orderKey", orderKey);
//				model.addAttribute("orderValue", orderValue);
				if ("up".equals(orderValue)) {
					params.put("orderBy", "priceUp");
				} else {
					params.put("orderBy", "priceDown");
				}
			} else if ("4".equals(orderKey)) {//特卖
//				model.addAttribute("orderKey", orderKey);
//				model.addAttribute("orderValue", orderValue);

				if ("up".equals(orderValue)) {
					params.put("orderBy", "hitUp");
				} else {
					params.put("orderBy", "hitDown");
				}
			} else if ("5".equals(orderKey)) {//特卖
				params.put("sale", "y");
//				model.addAttribute("orderKey", orderKey);
			}
		}
		int pageSize = StringUtils.isBlank(request.getParameter("pageSize")) ? 10 : new Integer(request.getParameter("pageSize"));
		int pageNo = StringUtils.isBlank(request.getParameter("pageNo")) ? 0 : new Integer(request.getParameter("pageNo"));;
		PagerModel<Product> page = productService.findPageProduct(params, pageNo, pageSize);
		returnMap.put("root", imageRootPath);
		returnMap.put("list", productListToMapList(request, page.getList()));
		return buildJson(callback, returnMap);
	}
	
	private void processCatalog(String imageRootPath, Catalog catelog) {
		logger.info("id {}, name {}, icon {}", catelog.getId(), catelog.getName(), catelog.getIcon());
		if (StringUtils.isNotBlank(catelog.getIcon()) && !catelog.getIcon().startsWith(imageRootPath))
			catelog.setIcon(imageRootPath + catelog.getIcon());
		if (catelog.getChildren() != null && !catelog.getChildren().isEmpty()) {
			for (Catalog c : catelog.getChildren()) {
				processCatalog(imageRootPath, c);
			}
		}
	}
	
	/**
	 * 新品上架
	 */
	@RequestMapping(value = "new")
	public @ResponseBody String newProduct(@RequestParam(value = "callback", required = false) String callback,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> returnMap = buildReturnMap(SUCCESS, "");
		String imageRootPath = (String) request.getSession().getServletContext().getAttribute(Global.IMAGE_ROOT_PATH);
		//加载最新商品
		Map<String, Object> params = Maps.newHashMap();
		int pageSize = StringUtils.isBlank(request.getParameter("pageSize")) ? 10 : new Integer(request.getParameter("pageSize"));
		int pageNo = StringUtils.isBlank(request.getParameter("pageNo")) ? 0 : new Integer(request.getParameter("pageNo"));
		params.put("isNew", "y");
		PagerModel<Product> page = productService.findPageProduct(params, pageNo, pageSize);
		returnMap.put("root", imageRootPath);
		returnMap.put("list", productListToMapList(request, page.getList()));
		return buildJson(callback, returnMap);
	}
	
	/**
	 * 特价商品
	 */
	@RequestMapping(value = "sale")
	public @ResponseBody String saleProduct(@RequestParam(value = "callback", required = false) String callback,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> returnMap = buildReturnMap(SUCCESS, "");
		String imageRootPath = (String) request.getSession().getServletContext().getAttribute(Global.IMAGE_ROOT_PATH);
		//加载特价商品
		Map<String, Object> params = Maps.newHashMap();
		int pageSize = StringUtils.isBlank(request.getParameter("pageSize")) ? 10 : new Integer(request.getParameter("pageSize"));
		int pageNo = StringUtils.isBlank(request.getParameter("pageNo")) ? 0 : new Integer(request.getParameter("pageNo"));
		params.put("sale", "y");
		PagerModel<Product> page = productService.findPageProduct(params, pageNo, pageSize);
		returnMap.put("root", imageRootPath);
		returnMap.put("list", productListToMapList(request, page.getList()));
		return buildJson(callback, returnMap);
	}
	
	/**
	 * 热卖榜
	 */
	@RequestMapping(value = "hot")
	public @ResponseBody String hotProduct(@RequestParam(value = "callback", required = false) String callback,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> returnMap = buildReturnMap(SUCCESS, "");
		String imageRootPath = (String) request.getSession().getServletContext().getAttribute(Global.IMAGE_ROOT_PATH);
		//加载热卖商品
		Map<String, Object> params = Maps.newHashMap();
		int pageSize = StringUtils.isBlank(request.getParameter("pageSize")) ? 10 : new Integer(request.getParameter("pageSize"));
		int pageNo = StringUtils.isBlank(request.getParameter("pageNo")) ? 0 : new Integer(request.getParameter("pageNo"));
		params.put("orderBy", "sellCountDown");
		PagerModel<Product> page = productService.findPageProduct(params, pageNo, pageSize);
		returnMap.put("root", imageRootPath);
		returnMap.put("list", productListToMapList(request, page.getList()));
		return buildJson(callback, returnMap);
	}
	
}
