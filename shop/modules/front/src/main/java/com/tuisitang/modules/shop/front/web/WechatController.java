package com.tuisitang.modules.shop.front.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.jeeshop.core.FrontContainer;
import net.jeeshop.core.dao.page.PagerModel;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gson.WeChat;
import com.gson.oauth.Oauth;
import com.gson.oauth.WxPay;
import com.gson.util.Tools;
import com.tuisitang.common.bo.ActivityStatus;
import com.tuisitang.common.bo.CartAction;
import com.tuisitang.common.bo.LoginType;
import com.tuisitang.common.bo.Status;
import com.tuisitang.common.cache.memcached.SpyMemcachedClient;
import com.tuisitang.common.mapper.JsonMapper;
import com.tuisitang.common.service.ServiceException;
import com.tuisitang.common.utils.AddressUtils;
import com.tuisitang.common.utils.CookieUtils;
import com.tuisitang.common.utils.DateUtils;
import com.tuisitang.common.utils.HttpServletRequestUtils;
import com.tuisitang.common.utils.Identities;
import com.tuisitang.common.utils.SpringContextHolder;
import com.tuisitang.common.utils.StringUtils;
import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.entity.AccountRank;
import com.tuisitang.modules.shop.entity.Activity;
import com.tuisitang.modules.shop.entity.Address;
import com.tuisitang.modules.shop.entity.Cart;
import com.tuisitang.modules.shop.entity.Catalog;
import com.tuisitang.modules.shop.entity.DeliveryDay;
import com.tuisitang.modules.shop.entity.Express;
import com.tuisitang.modules.shop.entity.Gift;
import com.tuisitang.modules.shop.entity.IndexImg;
import com.tuisitang.modules.shop.entity.Order;
import com.tuisitang.modules.shop.entity.Orderdetail;
import com.tuisitang.modules.shop.entity.Pay;
import com.tuisitang.modules.shop.entity.Product;
import com.tuisitang.modules.shop.entity.Spec;
import com.tuisitang.modules.shop.front.interceptor.LogInterceptor;
import com.tuisitang.modules.shop.front.security.CaptchaException;
import com.tuisitang.modules.shop.front.security.MobileCaptchaException;
import com.tuisitang.modules.shop.front.security.UsernamePasswordToken;
import com.tuisitang.modules.shop.front.service.ProductFrontService;
import com.tuisitang.modules.shop.front.service.SystemFrontService;
import com.tuisitang.modules.shop.front.utils.AccountUtils;
import com.tuisitang.modules.shop.front.vo.ProductVo;
import com.tuisitang.modules.shop.service.AccountService;
import com.tuisitang.modules.shop.service.OrderService;
import com.tuisitang.modules.shop.utils.Global;

/**    
 * @{#} WechatController.java   
 * 
 * 微信服务号
 * 
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@Controller
@RequestMapping("/wx")
public class WechatController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(WechatController.class);
	
	@Autowired
	private SpyMemcachedClient memcachedClient;
	@Autowired
	private SystemFrontService systemFrontService;
	@Autowired 
	private ProductFrontService productFrontService;
	@Autowired
	private AccountService accountFrontService;
	@Autowired
	private OrderService orderFrontService;
	

	/**
	 * 1. 校验签名
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "{token}", method = RequestMethod.GET)
	public @ResponseBody String validate(@PathVariable String token, HttpServletRequest request, HttpServletResponse response) {
//		logger.info("before decode token {}", token);
//		token = new String(Encodes.decodeBase64(token));
//		logger.info("after decode token {}", token);
		String path = request.getServletPath();
//		String pathInfo = path.substring(path.lastIndexOf("/"));
		logger.info("path = {}", path);
		String outPut = "error";
//		if (pathInfo != null) {
		String signature = request.getParameter("signature");// 微信加密签名
		String timestamp = request.getParameter("timestamp");// 时间戳
		String nonce = request.getParameter("nonce");// 随机数
		String echostr = request.getParameter("echostr");//
		// 验证
		if (WeChat.checkSignature(token, signature, timestamp, nonce)) {
			outPut = echostr;
		}
//		}
		return outPut;
	}
	
	/**
	 * 2. 处理消息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "{token}", method = RequestMethod.POST)
	public @ResponseBody String process(@PathVariable String token, HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/xml");
		String xml = "";
		try {
			ServletInputStream in = request.getInputStream();
			String xmlMsg = Tools.inputStream2String(in);
			logger.info("输入消息:[{}]", xmlMsg);
			xml = WeChat.processing(xmlMsg, SpringContextHolder.getApplicationContext());
			logger.info("输出消息:[{}]", xml);
		} catch (ServiceException e) {
			e.printStackTrace();
			logger.error("ServiceException {}", e);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			logger.error("UnsupportedEncodingException {}", e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("IOException {}", e);
		}
		return xml;
	}
	
	
	/**
	 * 1. 首页
	 */
	@RequestMapping(value = { "index"}, method = RequestMethod.GET)
	public String home(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("home");
		//首页导航图片
		List<IndexImg> indexImgList = productFrontService.getIndexImgByType(IndexImg.MOBILE_IMG_TYPE_MOBILE);
		model.addAttribute("indexImgList", indexImgList);
		
		//加载热门推荐商品
		List<Product> hotList = productFrontService.getHotRecommendProduct(null);
		//手机端只需6个
		int index = 0;
		List<Product> hotRecommendProduct = Lists.newArrayList();
		for (Product product : hotList) {
			if (index >= 6) {
				break;
			}
			hotRecommendProduct.add(product);
			index ++;
		}
		
		model.addAttribute("hotRecommendProduct", hotRecommendProduct);
		return "mobile/front/index";
	}
	
	
	/**
	 * 新品上架
	 */
	@RequestMapping(value = "new")
	public String newProduct(HttpServletRequest request, HttpServletResponse response, Model model) {
		//加载最新商品
		Map<String, Object> params = Maps.newHashMap();
		int pageSize = StringUtils.isBlank(request.getParameter("pageSize")) ? 10 : new Integer(request.getParameter("pageSize"));
		int pageNo = StringUtils.isBlank(request.getParameter("pageNo")) ? 0 : new Integer(request.getParameter("pageNo"));
		params.put("isNew", "y");
		PagerModel<Product> page = productFrontService.findPageProduct(params, pageNo, pageSize);
		model.addAttribute("list", page.getList());
		return "modules/front/index";
	}
	
	/**
	 * 特价商品
	 */
	@RequestMapping(value = "sale")
	public String saleProduct(HttpServletRequest request, HttpServletResponse response, Model model) {
		//加载特价商品
		Map<String, Object> params = Maps.newHashMap();
		int pageSize = StringUtils.isBlank(request.getParameter("pageSize")) ? 10 : new Integer(request.getParameter("pageSize"));
		int pageNo = StringUtils.isBlank(request.getParameter("pageNo")) ? 0 : new Integer(request.getParameter("pageNo"));
		params.put("sale", "y");
		PagerModel<Product> page = productFrontService.findPageProduct(params, pageNo, pageSize);
		model.addAttribute("list", page.getList());
		return "modules/front/index";
	}
	
	/**
	 * 热卖榜
	 */
	@RequestMapping(value = "hot")
	public String hotProduct(HttpServletRequest request, HttpServletResponse response, Model model) {
		//加载热卖商品
		Map<String, Object> params = Maps.newHashMap();
		int pageSize = StringUtils.isBlank(request.getParameter("pageSize")) ? 10 : new Integer(request.getParameter("pageSize"));
		int pageNo = StringUtils.isBlank(request.getParameter("pageNo")) ? 0 : new Integer(request.getParameter("pageNo"));
		params.put("orderBy", "sellCountDown");
		PagerModel<Product> page = productFrontService.findPageProduct(params, pageNo, pageSize);
		model.addAttribute("list", page.getList());
		return "modules/front/index";
	}
	
	/**
	 * 根据产品Id查询产品详情
	 */
	@RequestMapping(value="product/detail/{productId}")
	public String productDetail(@PathVariable("productId") Long productId, @RequestParam(required=false) Long catalogId,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		Product product = productFrontService.getProductById(productId);
//		//发布更新浏览记录事件
//		publishEvent(new ProductTraceEvent(this, productId, product.getName(), sessionId, account, HttpServletRequestUtils.getHttpServletRequestMap(request)));
		/**
		 * 生成展示Vo
		 */
		ProductVo productVo = generateProductVo(product);
		
		//加载热门推荐商品
		List<Product> hotList = productFrontService.getHotRecommendProduct(catalogId == null ? null : catalogId);
		int index = 0;
		List<Product> hotRecommendProduct = Lists.newArrayList();
		for (Product prod : hotList) {
			if (index >= 6) {
				break;
			}
			hotRecommendProduct.add(prod);
			index ++;
		}
		model.addAttribute("hotRecommendProduct", hotRecommendProduct);
		model.addAttribute("productVo", productVo);
		return "mobile/front/product/productDetail";
	}
	
	/**
	 * 根据产品生成产品视图对象
	 * @param product
	 */
	private ProductVo generateProductVo(Product product) {
		ProductVo productVo = new ProductVo();
		productVo.setProduct(product);
		/**
		 * 设置商品规格
		 */
		productFrontService.setProductSpec(productVo, null);
		
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
		
//		//如果商品已上架并且商品的库存数小于等于0，则更新商品为已下架
//		if(product.getStatus()==Product.Product_status_y){
//			if(product.getStock()<=0){
//				productVo.setProductSorryStr("抱歉，商品已售卖完。");
//				productVo.setOutOfStock(true);
//			}
//		}
		//加载商品参数
		productVo.setParameterList(productFrontService.selectParameterList(product.getId()));
		
		/**
		 * 检查，如果此商品是活动商品，则加载相应的活动信息。
		 */
		logger.error("e.getActivityID() = "+product.getActivityID());
		if(product.getActivityID() != null){
			Activity activity = productFrontService.getActivityById(product.getActivityID());
			/**
			 * 计算产品最终价格
			 */
			productVo.setFinalPrice(df.format(productFrontService.caclFinalPrice(product, activity)));
			product.setActivity(activity);
		}
		
		return productVo;
	}
	
	/**
	 * 查询所有分类
	 */
	@RequestMapping(value="categories")
	public String categories(
			HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Catalog> catalogs = getCatalogCacheList();
		String imageRootPath = (String) request.getSession().getServletContext().getAttribute(Global.IMAGE_ROOT_PATH);
		for (Catalog c : catalogs) {
			processCatalog(imageRootPath, c);
		}
		model.addAttribute("categories", catalogs);
		return "mobile/front/product/categories";
	}
	

	/**
	 * 根据一级分类获取子分类
	 */
	@RequestMapping(value="subCategories")
	public @ResponseBody Map<String, Object> getSubCatalog(@RequestParam(required = true, value = "catalogId") Long catalogId,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		List<Catalog> catalogs = getCatalogCacheList();
		List<Catalog> subCatalogs = Lists.newArrayList();
		for (Catalog catalog : catalogs) {
			if (catalogId.longValue() == catalog.getId().longValue()) {
				subCatalogs = catalog.getChildren();
			}
		}
		String imageRootPath = (String) request.getSession().getServletContext().getAttribute(Global.IMAGE_ROOT_PATH);
		//判断是否有三级分类标示
		boolean flag = false;
		for (Catalog c : subCatalogs) {
			if (c.getChildren() != null && c.getChildren().size() > 0) {
				flag = true;
			}
			processCatalog(imageRootPath, c);
		}
		
		Map<String, Object> returnMap = Maps.newHashMap();
		returnMap.put("s", "0");
		returnMap.put("flag", flag);
		returnMap.put("categories", subCatalogs);
		return returnMap;
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
	
	@RequestMapping(value = "oauth2")
	public String oauth2(@RequestParam(required = true, value = "code") String code,
			@RequestParam(required = true, value = "state") String state,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> parameterMap = request.getParameterMap();
		for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
			logger.info("Key {}, Value {}", entry.getKey(), entry.getValue());
		}
		Oauth oauth = new Oauth();
		try {
			String json = oauth.getToken(code);
			logger.info("openid {}", json);
			String accessToken = WeChat.getAccessToken(memcachedClient);
			Map<String, Object> map = JSONObject.parseObject(json);
	        String openid = map.get("openid").toString();
			logger.info("openid {}, accessToken {}, json {}", openid, accessToken, json);
			if (!StringUtils.isBlank(openid)) {
//				com.gson.oauth.User user = new com.gson.oauth.User();
//				UserInfo userInfo = user.getUserInfo(accessToken, openid);
//				logger.info("UserInfo {}", userInfo);
				if ("login".equals(state)) {
					return "redirect:/login?openid=" + openid;
				} else if ("regist".equals(state)) {
					return "redirect:/regist?openid=" + openid;
				} else {
					Subject subject = SecurityUtils.getSubject();
					if (!subject.isAuthenticated()) {
						String host = AddressUtils.getHost(request);
						UsernamePasswordToken token = new UsernamePasswordToken(openid, 
								openid.toCharArray(), true, host, LoginType.Wechat.getType(),
								"", "", false, openid);
						try {
							subject.login(token);
						} catch (UnknownAccountException e) {
							e.printStackTrace();
							logger.error("账号错误 : {}", e.getMessage());
						} catch (IncorrectCredentialsException e) {
							e.printStackTrace();
							logger.error("密码错误 : {}", e.getMessage());
						} catch (LockedAccountException e) {
							e.printStackTrace();
							logger.error("账号已被锁定，请与管理员联系 : {}", e.getMessage());
						} catch(CaptchaException e) {
							e.printStackTrace();
							logger.error("验证码错误 {}", e.getMessage());
						} catch(MobileCaptchaException e) {
							e.printStackTrace();
							logger.error("手机验证码错误 {}", e.getMessage());
						} catch (AuthenticationException e) {
							e.printStackTrace();
							logger.error("你还未授权: {}", e.getMessage());
						}
					}
					return "redirect:/" + state + "?openid=" + openid;
				}
			} else {
				return "redirect:/login";
			}
		} catch (Exception e) {
			e.printStackTrace();
			memcachedClient.delete(WeChat.KEY);
			return "redirect:/login";
		}
	}
	
//	
//	/**
//	 * 2. 加入购物车
//	 */
//	@RequestMapping(value = "cartAdd", method = { RequestMethod.GET, RequestMethod.POST })
//	public @ResponseBody Map<String, Object> add(Long productId, int quantity, Long specId, HttpServletRequest request, HttpServletResponse response) {
//		logger.info("加入购物车 productId {}", productId);
//		Product product = productFrontService.getProductWithActivity(productId);
//		if (product == null) {
//			logger.info("商品为空");
//			return buildReturnMap(Status.Failure, "商品不能为空");
//		}
//		Spec spec = null;
//		if (specId != null) {
//			spec = productFrontService.getProductSpec(productId, specId);
//			if (spec == null) {
//				return buildReturnMap(Status.Failure, "商品规格配置错误");
//			}
//			product.setActiveSpec(spec);
//		}
//		
//		if(specId != null) {
//			if (spec.getMinSell() != 0 && quantity < spec.getMinSell()) {
//				logger.info("商品最少{}起拍", spec.getMinSell());
//				String unit = StringUtils.isBlank(product.getUnit()) ? "个" : product.getUnit();
//				return buildReturnMap(Status.Failure, "该商品至少需订购" + spec.getMinSell() + unit);
//			}
//		} else if (product.getMinSell() != 0 && quantity < product.getMinSell()) {
//			logger.info("商品最少{}起拍", product.getMinSell());
//			String unit = StringUtils.isBlank(product.getUnit()) ? "个" : product.getUnit();
//			return buildReturnMap(Status.Failure, "该商品至少需订购" + product.getMinSell() + unit);
//		} 
//		String sessionId = CookieUtils.getCookie(request, LogInterceptor.SESSION_NAME);
//		Account account = AccountUtils.getAccount();
//		try {
//			orderFrontService.saveCart(product, null, account, sessionId, quantity, CartAction.Add.getAction());
//			return buildReturnMap(Status.Success, "");
//		} catch (Exception e) {
//			e.printStackTrace();
//			return buildReturnMap(Status.Failure, e.getMessage());
//		}
//	}	
//	/**
//	 * 购物车列表
//	 * @param request
//	 * @param response
//	 * @param model
//	 * @return
//	 */
//	@RequiresUser
//	@RequestMapping(value = "cart/list", method = RequestMethod.GET)
//	public String cartList(HttpServletRequest request, HttpServletResponse response, Model model) {
//		logger.info("购物车");
//		Account account = AccountUtils.getAccount();
//		if (AccountUtils.isUserLogin()){
//			// 更新下会员的等级，避免认证过没有刷新会员信息
//			account = accountFrontService.getAccountById(account.getId());
//			AccountUtils.setAccount(account);
//		}
//		String sessionId = CookieUtils.getCookie(request, LogInterceptor.SESSION_NAME);
//		List<Cart> carts = orderFrontService.findCart(sessionId, account.getId());
//		double totalPrice = 0.00;
//		int totalIntegral = 0;
//		int total = 0;
//		for (Cart cart : carts) {
//			logger.info("Cart {}", cart);
//			totalPrice += cart.getNowPrice() * cart.getQuantity();
//			totalIntegral += cart.getRequiredIntegral() * cart.getQuantity();
//			total += cart.getQuantity();
//		}
//		
//		//购物车按照销售商分组
//		Map<Long, List<Cart>> group = Maps.newHashMap();
//		for (Cart cart : carts) {
//			if (!group.containsKey(cart.getSellerId())) {
//				List<Cart> list = Lists.newArrayList();
//				list.add(cart);
//				group.put(cart.getSellerId(), list);
//			} else {
//				List<Cart> list = group.get(cart.getSellerId());
//				list.add(cart);
//			}
//		}
//		model.addAttribute("account", account);
//		model.addAttribute("carts", group);
//		model.addAttribute("totalPrice", df.format(totalPrice));
//		model.addAttribute("totalIntegral", totalIntegral);
//		model.addAttribute("total", total);
//		model.addAttribute("step", "1");
//		return "mobile/front/cart";
//	}
	
	/**
	 * 3. 更新购物车 
	 * 如果quantity等于0表示删除整个商品
	 * 
	 * @param productId
	 * @param quantity
	 * @param request
	 * @param response
	 * @return
	 */
	@RequiresUser
	@RequestMapping(value = "cart/update", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Map<String, Object> update(Long productId, Long specId, int quantity, int action, HttpServletRequest request, HttpServletResponse response) {
		logger.info("加入购物车 productId {}", productId);

		Product product = productFrontService.getProductWithActivity(productId);
		if (product == null) {
			logger.info("商品为空");
			return buildReturnMap(Status.Failure, "商品为空");
		}
		if (action != CartAction.Add.getAction() && action != CartAction.Sub.getAction() && action != CartAction.Set.getAction()) {
			logger.info("非法操作");
			return buildReturnMap(Status.Failure, "非法操作");
		}
		
		Account account = AccountUtils.getAccount();
		try {
			orderFrontService.saveCart(product, specId, account, account.getOpenId(), quantity, action);
			Map<String, Object> data = Maps.newHashMap();
			List<Cart> carts = orderFrontService.findCart(account.getOpenId(), account.getId());
			double totalPrice = 0.00;
			int totalIntegral = 0;
			int total = 0;
			if (action == CartAction.Set.getAction() && quantity == 0) {
				data.put("del", true);
			} else {
				data.put("del", false);
			}
			for (Cart cart : carts) {
				logger.info("Cart {}", cart);
				if (!cart.isChecked())
					continue;
				totalPrice += cart.getNowPrice() * cart.getQuantity();
				totalIntegral += cart.getRequiredIntegral() * cart.getQuantity();
				total += cart.getQuantity();
				if (specId != null && specId != 0L) {
					if(cart.getProductId().equals(productId) && specId.equals(cart.getSpecId())) {
						data.put("quantity", cart.getQuantity());
						Spec spec = productFrontService.getProductSpec(productId, specId);
						if (spec != null) {
							if (cart.getQuantity() <= spec.getMinSell() || cart.getQuantity() <= 1) {
								data.put("disableFlag", true);
							} else {
								data.put("disableFlag", false);
							}
						}
						data.put("itemTotalPrice", df.format(cart.getNowPrice() * cart.getQuantity()));
						data.put("itemSavedPrice", df.format(cart.getMarketPrice() * cart.getQuantity() - cart.getNowPrice() * cart.getQuantity()));
					}
				} else {
					if (cart.getProductId().equals(productId)) {
						data.put("quantity", cart.getQuantity());
						if (cart.getQuantity() <= product.getMinSell() || cart.getQuantity() <= 1) {
							data.put("disableFlag", true);
						} else {
							data.put("disableFlag", false);
						}
						data.put("itemTotalPrice", df.format(cart.getNowPrice() * cart.getQuantity()));
						data.put("itemSavedPrice", df.format(cart.getMarketPrice() * cart.getQuantity() - cart.getNowPrice() * cart.getQuantity()));
					}
				}
			}
			data.put("totalPrice", df.format(totalPrice));
			data.put("totalIntegral", totalIntegral);
			data.put("total", total);
			return buildReturnMap(Status.Success, "", data);
		} catch (Exception e) {
			e.printStackTrace();
			return buildReturnMap(Status.Failure, e.getMessage());
		}
	}
	
	/**
	 *  更新Cart状态
	 * 
	 * @param ids
	 * @param status
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "cart/status", method = { RequestMethod.POST })
	public @ResponseBody Map<String, Object> updateStatus(@RequestParam(required = false, value = "ids", defaultValue = "") String ids,
			@RequestParam("status") boolean status, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("更新Cart状态");
		String sessionId = CookieUtils.getCookie(request, LogInterceptor.SESSION_NAME);
		Account account = AccountUtils.getAccount();
		logger.info("sessionId {}, account {}, ids {}, status {}", sessionId, account, ids, status);
		try {
			Map<String, Object> data = Maps.newHashMap();
			orderFrontService.updateCartStatus(ids, status ? "checked" : "");
			List<Cart> carts = orderFrontService.findCart(sessionId, account.getId());
			double totalPrice = 0.00;
			int totalIntegral = 0;
			int total = 0;
			for (Cart cart : carts) {
				logger.info("Cart {}", cart);
				if (!cart.isChecked())
					continue;
				totalPrice += cart.getNowPrice() * cart.getQuantity();
				totalIntegral += cart.getRequiredIntegral() * cart.getQuantity();
				total += cart.getQuantity();
			}
			data.put("totalPrice", df.format(totalPrice));
			data.put("totalIntegral", totalIntegral);
			data.put("total", total);
			return buildReturnMap(Status.Success, "", data);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{}", e);
			return buildReturnMap(Status.Failure, e.getMessage());
		}
	}
	
	/**
	 * 1. 购物车提交 - 订单确认
	 */
	@RequiresUser
	@RequestMapping(value = { "order/confirm" })
	public String confirm(@RequestParam(required = true, value = "ids") String ids,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		Date sysTime = new Date(System.currentTimeMillis());
		logger.info("订单确认");
		List<DeliveryDay> ddList = commonService.findAllDeliveryDay();// 获得所有的日期
		List<Express> expressList = commonService.getAllExpress();// 获得所有的快递方式
		List<Pay> payList = commonService.getAllPay();// 获得所有的支付方式
		Map<String, List<Pay>> payMap = Maps.newHashMap();
		for (Pay pay : payList) {
			String type = pay.getType();
			List<Pay> l = payMap.get(type);
			if (l == null || l.isEmpty()) {
				l = Lists.newArrayList();
				payMap.put(type, l);
			}
			l.add(pay);
		}
		Account account = AccountUtils.getAccount();
		String sessionId = CookieUtils.getCookie(request, LogInterceptor.SESSION_NAME);
		Long[] idss = (Long[]) ConvertUtils.convert(ids.split(","), Long.class);
//		List<Cart> carts = orderService.findCart(sessionId, account.getId());
		List<Cart> carts = orderFrontService.findCart(sessionId, account.getId(), idss);
		double totalPrice = 0.00;//商品总金额
		double totalFreight = 0.00;//总运费
		double totalDiscount = 0.00;//会员等级折扣
		double totalPay = 0.00;//总支付金额
		
		int totalIntegral = 0;
		int total = 0;
		String s = "";
		for (Cart cart : carts) {
			logger.info("Cart {}", cart);
//			if (!cart.isChecked())
//				continue;
			Product product = productFrontService.getProductById(cart.getProductId());
			if (cart.getSpecId() != null && cart.getSpecId() != 0L) {
				Spec activeSpec = productFrontService.getProductSpec(product.getId(), cart.getSpecId());
				product.setActiveSpec(activeSpec);
				productFrontService.getProductSpecInfo(product);
			}
			Activity activity = product.getActivity();
			if (activity == null) {// 该商品没有参加任何活动
				logger.info("该商品【{}({})】没有参加活动", product.getName(), product.getId());
				if (StringUtils.isNotBlank(account.getRank())) {
					AccountRank ar = commonService.getAccountRankByCode(account.getRank());
					if (ar != null) {
						cart.setDiscount(ar.getDiscount() * 100);
					} else {
						cart.setDiscount(100);
					}
					cart.setDiscountPrice((100 - cart.getDiscount()) * cart.getNowPrice() / 100);
				} else {// 未登录情况
					cart.setDiscount(100);
					cart.setDiscountPrice(0);
				}
				
				cart.setFee(orderFrontService.calculateFreight(cart.getNowPrice() * cart.getQuantity()));
				cart.setActivityStatus(ActivityStatus.NONE.getStatus());
			} else {
				double finalPrice = product.getNowPrice();
				String activityType = activity.getActivityType();
				String discountType = activity.getDiscountType();
				String accountRange = activity.getAccountRange();
				String []ss = accountRange.split(",");
				List<String> ranks = Lists.newArrayList();
				for (String s1 : ss) {
					if (!StringUtils.isBlank(s1.trim()))
						ranks.add(s1.trim());
				}
				logger.info("该商品【{}({})】参加活动，活动类型【{}】，折扣类型【{}】", product.getName(), product.getId(), activityType, discountType);
				logger.info("活动开始时间 {}, 活动结束时间 {}, 当前时间 {}, ",
						DateUtils.formatDate(activity.getStartDate(), "yyyy-MM-dd HH:mm:ss"), 
						DateUtils.formatDate(activity.getEndDate(), "yyyy-MM-dd HH:mm:ss"),
						DateUtils.formatDate(sysTime, "yyyy-MM-dd HH:mm:ss"));
				logger.info("参加活动的会员等级 {}, 当前会员等级 {}", accountRange, account.getRank());
				// 活动类型 c:促销活动；j:积分兑换；t:团购活动
				if (activity.getStartDate().getTime() > sysTime.getTime()) {
					logger.info("活动还未开始");
					cart.setActivityStatus(ActivityStatus.NOSTART.getStatus());
				} else if (activity.getEndDate().getTime() < sysTime.getTime()) {
					logger.info("活动已经结束");
					cart.setActivityStatus(ActivityStatus.ISOVER.getStatus());
				} else if (ranks.size() != 4 && !StringUtils.isBlank(accountRange) && !ranks.contains(account.getRank())) {
					logger.info("没有权限");
					cart.setActivityStatus(ActivityStatus.NOAUTH.getStatus());
				} else {
					if (activityType.equals(Activity.activity_activityType_c)) {// 1.促销活动
						if (discountType.equals(Activity.activity_discountType_r)) {// 减免
							finalPrice = product.getNowPrice() - activity.getDiscount();
							if (activity.getMinprice() !=null && (finalPrice < activity.getMinprice().doubleValue())) {
								finalPrice = activity.getMinprice();
							}
						} else if (discountType.equals(Activity.activity_discountType_d)) {// 折扣
							finalPrice = product.getNowPrice() * activity.getDiscount() / 100d;
							if (activity.getMinprice() !=null && (finalPrice < activity.getMinprice().doubleValue())) {
								finalPrice = activity.getMinprice();
							}
						} else if (discountType.equals(Activity.activity_discountType_s)) {// 双倍积分 双倍积分的商品价格上不做优惠
							finalPrice = product.getNowPrice();
							cart.setPresentIntegral(product.getScore() * 2);// 获得的积分*2
						}
					} else if (activityType.equals(Activity.activity_activityType_j)) {// 2.积分活动
						finalPrice = 0.00;// 最终价格等于0
						cart.setRequiredIntegral(activity.getExchangeScore());
					} else if (activityType.equals(Activity.activity_activityType_t)) {// 3.团购活动
						finalPrice = activity.getTuanPrice();// 最终价格等于团购价
					}
					cart.setActivityId(activity.getId());
					cart.setActivityName(activity.getName());
					cart.setDiscountType(Activity.activity_discountType_r);
					cart.setDiscount(activity.getDiscount());
					cart.setActivityType(activityType);
				}
				cart.setNowPrice(finalPrice);
			}
			orderFrontService.updateCart(cart);
			totalPrice += cart.getNowPrice() * cart.getQuantity();
			totalIntegral += cart.getRequiredIntegral() * cart.getQuantity();
			total += cart.getQuantity();
			s += cart.getId() + ",";
			
			//计算运费
////			Product prod = productFrontService.getProductById(cart.getProductId());
////			double freight = orderService.calculateFreight(prod.getNowPrice());
//			double freight = orderService.calculateFreight(cart.getNowPrice() * cart.getQuantity());
			totalFreight += cart.getFee();
//			totalDiscount += cart.getDiscount() * cart.getQuantity();
			totalDiscount += cart.getDiscountPrice() * cart.getQuantity();
		}
//		//2999全场免运费
//		if (totalPrice > 2999) {
//			totalFreight = 0;
//		}
//		//计算会员等级折扣
//		model.addAttribute("discount", 1);
//		if (account.getRank() != null && !account.getRank().equals(AccountRank.RANK_CODE_R1)) {
//			AccountRank rank = accountService.getAccountRankByCode(account.getRank());
//			if (rank != null) {
//				totalDiscount = (1-rank.getDiscount()) * totalPrice;
//				account.setAccountRank(rank);
//				model.addAttribute("discount", rank.getDiscount());
//			}
//		}
		totalPay = (totalPrice - totalDiscount + totalFreight);
		
		//购物车按照销售商分组
		Map<Long, List<Cart>> group = Maps.newHashMap();
		for (Cart cart : carts) {
			if (!group.containsKey(cart.getSellerId())) {
				List<Cart> list = Lists.newArrayList();
				list.add(cart);
				group.put(cart.getSellerId(), list);
			} else {
				List<Cart> list = group.get(cart.getSellerId());
				list.add(cart);
			}
		}
				
		List<Address> addresses = accountFrontService.findAddressByAccountId(account.getId());
		Address defaultAddress = null;
		for (Address address : addresses) {
			if ("y".equals(address.getIsDefault())) {
				defaultAddress = address;
			}
		}
		java.text.DecimalFormat df = new java.text.DecimalFormat("#0.00");  
		model.addAttribute("account", account);
		model.addAttribute("carts", group);
		model.addAttribute("totalPrice", df.format(totalPrice));
		model.addAttribute("totalIntegral", df.format(totalIntegral));
		model.addAttribute("totalFreight", df.format(totalFreight));
		model.addAttribute("totalDiscount", df.format(totalDiscount));
		model.addAttribute("totalPay", df.format(totalPay));
		model.addAttribute("total", total);
		model.addAttribute("ddList", ddList);
		model.addAttribute("expressList", expressList);
		model.addAttribute("payMap", payMap);
		model.addAttribute("addresses", addresses);
		model.addAttribute("provinces", commonService.findProvinces());
		model.addAttribute("defaultAddress", defaultAddress);
		model.addAttribute("expressCode", expressList.get(0).getCode());
		model.addAttribute("payCode", null);
		model.addAttribute("ids", ids);
		model.addAttribute("step", "2");
		request.getSession().setAttribute("token", Identities.encodeBase64(s));
		return "mobile/front/order/confirm";
	}
	

	/**
	 * 2. 准备支付
	 * 生成订单，准备支付
	 */
	@RequestMapping(value = { "prepay" })
	public String prepay(@RequestParam("ids") String ids, 
			@RequestParam("addressId") Long addressId, 
			@RequestParam(value = "expressCode", required = false, defaultValue = "LOGISTICS") String expressCode, 
			@RequestParam(value = "payCode", required = true) String payCode,
			@RequestParam(value = "deliveryDayCode", required = false, defaultValue = "all") String deliveryDayCode,
			@RequestParam(required = false, value = "remark") String remark,
			HttpServletRequest request, HttpServletResponse response, Model model) {
			
		Account account = AccountUtils.getAccount();
		logger.info("支付,生成订单");
		logger.info("authorization {}, \naddressId {}, \nexpressCode {}, \npayCode {}, \ndeliveryDayCode {}, \nremark {}", 
				addressId, expressCode, payCode, deliveryDayCode, remark);
	
//			String password = Encodes.rc4(new String(Encodes.decodeBase64(ss[2]), "UTF-8"), device.getDeviceSecret());
		
		Express express = commonService.getExpress(expressCode);
		Pay pay = commonService.getPay(payCode);
		DeliveryDay deliveryDay = commonService.getDeliveryDay(deliveryDayCode);
		Address address = accountFrontService.getAddress(addressId);
		Order order = orderFrontService.createOrder(account, account.getOpenId(), ids, address, express, pay, deliveryDay, remark);
		
//		//跟新产品sellCount
//		List<Orderdetail> ods = order.getOdList();
//		for (Orderdetail orderdetail : ods) {
//			productService.incrSellCount(orderdetail.getProductID());
//		}
//		//设置当前地址为默认地址
//		accountService.setDefaultAddr(account.getId(), address);
		
		List<Orderdetail> ods = order.getOdList();
		StringBuffer sb = new StringBuffer();
		for (Orderdetail orderdetail : ods) {
			sb.append(orderdetail.getProductName() + ",");
		}
		String productName = sb.substring(0, sb.length() - 1).toString();
		String total_fee   = (order.getAmount() + order.getFee()) + "";
		String orderid     = order.getNo();
		
		// package 参数------------------------------  //
		Map<String, String> params = new HashMap<String, String>();
		// 对商品名截取, 去除空格 
		productName = productName.replaceAll(" ", "");
		productName = productName.length() > 17 ? productName.substring(0, 17) + "..." : productName;

		params.put("body", productName);	//商品描述。
		params.put("total_fee", total_fee);	//订单总金额
		params.put("fee_type", "1");	//现金支付币种,取值： 1 （人民币）
		params.put("out_trade_no", orderid); //商户系统内部的订单号
		params.put("spbill_create_ip", HttpServletRequestUtils.getIp(request)); // ip
		// 参数
		String timeStamp = System.currentTimeMillis() + "";
		
		String nonceStr = RandomStringUtils.random(8, "123456789"); // 8位随机数
		String packagestring = null,paySign = null;
		try {
			packagestring = WxPay.getPackage(params);
			paySign = WxPay.paySign(timeStamp, "", nonceStr, packagestring); // 构造签名
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// appId
		model.addAttribute("appid", Global.getConfig("AppId"));
		// timeStamp
		model.addAttribute("timeStamp", timeStamp);
		// nonceStr
		model.addAttribute("nonceStr", nonceStr);
		// package
		model.addAttribute("package", packagestring);
		// paySign
		model.addAttribute("paySign", paySign);
		// 判定微信
		model.addAttribute("isweixin", 1);
		
		return "mobile/front/order/pay";
	}
	
}
