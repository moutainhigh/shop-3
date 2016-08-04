package com.tuisitang.modules.shop.front.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tuisitang.common.bo.CartAction;
import com.tuisitang.common.bo.Status;
import com.tuisitang.common.utils.CookieUtils;
import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.entity.Cart;
import com.tuisitang.modules.shop.entity.Product;
import com.tuisitang.modules.shop.entity.Spec;
import com.tuisitang.modules.shop.front.interceptor.LogInterceptor;
import com.tuisitang.modules.shop.front.service.ProductFrontService;
import com.tuisitang.modules.shop.front.utils.AccountUtils;
import com.tuisitang.modules.shop.service.AccountService;
import com.tuisitang.modules.shop.service.OrderService;

/**    
 * @{#} CartController.java   
 * 
 * 购物车Controller
 * 
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {
	@Autowired
	private AccountService accountService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ProductFrontService productFrontService;

	/**
	 * 1. 购物车首页
	 */
	@RequestMapping(value = { "/", "" }, method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("购物车");
		Account account = AccountUtils.getAccount();
		if (AccountUtils.isUserLogin()){
			// 更新下会员的等级，避免认证过没有刷新会员信息
			account = accountService.getAccountById(account.getId());
			AccountUtils.setAccount(account);
		}
		String sessionId = CookieUtils.getCookie(request, LogInterceptor.SESSION_NAME);
		List<Cart> carts = orderService.findCart(sessionId, account.getId());
		double totalPrice = 0.00;
		int totalIntegral = 0;
		int total = 0;
		for (Cart cart : carts) {
			logger.info("Cart {}", cart);
			totalPrice += cart.getNowPrice() * cart.getQuantity();
			totalIntegral += cart.getRequiredIntegral() * cart.getQuantity();
			total += cart.getQuantity();
		}
		
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
		model.addAttribute("account", account);
		model.addAttribute("carts", group);
		model.addAttribute("totalPrice", df.format(totalPrice));
		model.addAttribute("totalIntegral", totalIntegral);
		model.addAttribute("total", total);
		model.addAttribute("step", "1");
		return "modules/front/cart";
	}
	
	/**
	 * 2. 加入购物车
	 */
	@RequestMapping(value = "add", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Map<String, Object> add(Long productId, int quantity, Long specId, HttpServletRequest request, HttpServletResponse response) {
		logger.info("加入购物车 productId {}", productId);
		Product product = productFrontService.getProductWithActivity(productId);
		if (product == null) {
			logger.info("商品为空");
			return buildReturnMap(Status.Failure, "商品不能为空");
		}
		Spec spec = null;
		if (specId != null) {
			spec = productFrontService.getProductSpec(productId, specId);
			if (spec == null) {
				return buildReturnMap(Status.Failure, "商品规格配置错误");
			}
			product.setActiveSpec(spec);
		}
		
		if(specId != null) {
			if (spec.getMinSell() != 0 && quantity < spec.getMinSell()) {
				logger.info("商品最少{}起拍", spec.getMinSell());
				String unit = StringUtils.isBlank(product.getUnit()) ? "个" : product.getUnit();
//				return buildReturnMap(Status.Failure, "该商品至少需订购" + spec.getMinSell() + unit + ",且必须为" + spec.getMinSell() + "倍数");
				return buildReturnMap(Status.Failure, "该商品至少需订购" + spec.getMinSell() + unit);
			}
		} else if (product.getMinSell() != 0 && quantity < product.getMinSell()) {
			logger.info("商品最少{}起拍", product.getMinSell());
			String unit = StringUtils.isBlank(product.getUnit()) ? "个" : product.getUnit();
//			return buildReturnMap(Status.Failure, "该商品至少需订购" + product.getMinSell() + unit + ",且必须为" + product.getMinSell() + "倍数");
			return buildReturnMap(Status.Failure, "该商品至少需订购" + product.getMinSell() + unit);
		} 
		
		String sessionId = CookieUtils.getCookie(request, LogInterceptor.SESSION_NAME);
		Account account = AccountUtils.getAccount();
		try {
			orderService.saveCart(product, null, account, sessionId, quantity, CartAction.Add.getAction());
			return buildReturnMap(Status.Success, "");
		} catch (Exception e) {
			e.printStackTrace();
			return buildReturnMap(Status.Failure, e.getMessage());
		}
	}
	
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
	@RequestMapping(value = "update", method = { RequestMethod.GET, RequestMethod.POST })
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
		
		String sessionId = CookieUtils.getCookie(request, LogInterceptor.SESSION_NAME);
		Account account = AccountUtils.getAccount();
		try {
			orderService.saveCart(product, specId, account, sessionId, quantity, action);
			Map<String, Object> data = Maps.newHashMap();
			List<Cart> carts = orderService.findCart(sessionId, account.getId());
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
						data.put("itemTotalPrice", df.format(cart.getNowPrice() * cart.getQuantity()));
						data.put("itemSavedPrice", df.format(cart.getMarketPrice() * cart.getQuantity() - cart.getNowPrice() * cart.getQuantity()));
					}
				} else {
					if (cart.getProductId().equals(productId)) {
						data.put("quantity", cart.getQuantity());
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
	 * 根据Id删除购物车
	 * @param cartId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Map<String, Object> delteCart(@RequestParam(required = false, value = "cartId")Long cartId, HttpServletRequest request, HttpServletResponse response) {
		try {
			orderService.deleteCartById(cartId);
			return buildReturnMap(Status.Success, "");
		} catch (Exception e) {
			e.printStackTrace();
			return buildReturnMap(Status.Failure, e.getMessage());
		}
	}
	
	/**
	 * 3.1  批量删除购物车产品
	 * 
	 * @param productId
	 * @param quantity
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteBatch", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Map<String, Object> delteBatch(@RequestParam(required = false, value = "cartIds", defaultValue = "")String cartIds, HttpServletRequest request, HttpServletResponse response) {
		String sessionId = CookieUtils.getCookie(request, LogInterceptor.SESSION_NAME);
		Account account = AccountUtils.getAccount();
		try {
			Long[] ids = (Long[]) ConvertUtils.convert(cartIds.split(","), Long.class);	
			Map<String, Object> data = Maps.newHashMap();
			orderService.deleteCartById(ids);
			
			List<Cart> carts = orderService.findCart(sessionId, account.getId());
			double totalPrice = 0.00;
			int totalIntegral = 0;
			int total = 0;
			for (Cart cart : carts) {
				logger.info("Cart {}", cart);
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
			return buildReturnMap(Status.Failure, e.getMessage());
		}
	}
	
	/**
	 * 4. 清空购物车
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "clear", method = { RequestMethod.GET, RequestMethod.POST })
	public String clear(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("清空购物车");
		String sessionId = CookieUtils.getCookie(request, LogInterceptor.SESSION_NAME);
		Account account = AccountUtils.getAccount();
		orderService.deleteCart(sessionId, account.getId());
		return "redirect:/cart";
	} 
	
	/**
	 * 5. 购物车提交时检查登录状态
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "check", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Map<String, Object> check(HttpServletRequest request, HttpServletResponse response) {
		logger.info("购物车结算时检查登录状态");
		String sessionId = CookieUtils.getCookie(request, LogInterceptor.SESSION_NAME);
		Account account = AccountUtils.getAccount();
		logger.info("sessionId {}, account {}", sessionId, account);
		if (account == null || account.getId() == null) {
			return buildReturnMap(Status.Failure, "用户还未登陆");
		} else {
			return buildReturnMap(Status.Success, "");
		}
	}
	
	/**
	 * 6. 提交购物车
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "submit", method = { RequestMethod.POST })
	public String submit(HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		logger.info("提交购物车,检查登录状态");
		String[] ids = request.getParameterValues("id");
		if (ids == null || ids.length == 0) {
			addMessage(redirectAttributes, "请选择商品");
			return "redirect:/cart";
		}
		for (String id : ids) {
			logger.info("id {}", id);
		}
		String sessionId = CookieUtils.getCookie(request, LogInterceptor.SESSION_NAME);
		Account account = AccountUtils.getAccount();
		logger.info("sessionId {}, account {}", sessionId, account);
		if (account == null || account.getId() == null) {
			logger.info("用户未登录，跳转到登录页面");
			return "redirect:/login";
		} else {
			logger.info("跳转到订单确认页面");
			return "redirect:/order/confirm";
		}
	}
	
	/**
	 * 7. 更新Cart状态
	 * 
	 * @param ids
	 * @param status
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "status", method = { RequestMethod.POST })
	public @ResponseBody Map<String, Object> updateStatus(@RequestParam(required = false, value = "ids", defaultValue = "") String ids,
			@RequestParam("status") boolean status, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("更新Cart状态");
		String sessionId = CookieUtils.getCookie(request, LogInterceptor.SESSION_NAME);
		Account account = AccountUtils.getAccount();
		logger.info("sessionId {}, account {}, ids {}, status {}", sessionId, account, ids, status);
		try {
			Map<String, Object> data = Maps.newHashMap();
			orderService.updateCartStatus(ids, status ? "checked" : "");
			List<Cart> carts = orderService.findCart(sessionId, account.getId());
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
	 * 8. 异步加载购物车信息
	 */
	@RequestMapping(value = "load", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> ajaxLoadCart(HttpServletRequest request, HttpServletResponse response) {
		logger.info("购物车");
		Account account = AccountUtils.getAccount();
		String sessionId = CookieUtils.getCookie(request, LogInterceptor.SESSION_NAME);
		List<Cart> carts = orderService.findCart(sessionId, account.getId());
		Map<String, Object> data = Maps.newHashMap();
		data.put("carts", carts);
		int proNum = 0;
		double totalPrice = 0d;
		for (Cart cart : carts) {
			proNum += cart.getQuantity();
			totalPrice += cart.getNowPrice();
		}
		data.put("proNum", proNum);
		data.put("totalPrice", df.format(totalPrice));
		return buildReturnMap(Status.Success, "", data);
	}
	
}
