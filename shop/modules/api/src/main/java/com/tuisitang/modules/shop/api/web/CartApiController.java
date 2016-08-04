package com.tuisitang.modules.shop.api.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tuisitang.common.bo.CartAction;
import com.tuisitang.common.mapper.JsonMapper;
import com.tuisitang.modules.shop.api.bo.Authorization;
import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.entity.Cart;
import com.tuisitang.modules.shop.entity.Device;
import com.tuisitang.modules.shop.entity.Product;
import com.tuisitang.modules.shop.entity.Spec;
import com.tuisitang.modules.shop.service.OrderService;
import com.tuisitang.modules.shop.service.ProductService;
import com.tuisitang.modules.shop.service.SystemService;
import com.tuisitang.modules.shop.utils.Global;

/**    
 * @{#} CartApiController.java   
 * 
 * 购物车 Api Controller
 * 
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: irtone</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 *  
 */
@Controller
@RequestMapping(value = "/api/cart")
public class CartApiController extends BaseApiController {
	@Autowired
	private ProductService productFrontService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private OrderService orderFrontService;
	/**
	 * 1. test
	 */
	@RequestMapping(value = "test")
	public @ResponseBody String test(@RequestParam(required = false) String callback, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,Object> returnMap = Maps.newHashMap();
		JsonMapper mapper = JsonMapper.getInstance();
		returnMap.put("s", "0");
		if (StringUtils.isBlank(callback)) {
			return mapper.toJson(returnMap);
		} else {
			return mapper.toJsonp(callback, returnMap);
		}
	}
	
	@RequestMapping(value = "list", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String list(@RequestHeader(value = "Authorization", required = true) String authorization, 
									@RequestParam(required = false) String callback,
									HttpServletRequest request, HttpServletResponse response) {
		logger.info("购物车");
		Authorization auth = getAuthorization(authorization);
		if ("1".equals(auth.getErrorCode())) {
			return buildJson(callback, buildReturnMap(auth.getErrorCode(), auth.getErrorMsg()));
		}
		Account account = systemService.getAccountByMobile(auth.getMobile());
		List<Cart> carts = orderFrontService.findCart(null, account.getId());
		//购物车按照销售商分组
		Map<Long, List<Map<String, Object>>> group = Maps.newHashMap();
		for (Cart cart : carts) {
			Map<String, Object> m = cartListToMapList(request, cart);
			m.put("minSell", getCartMinSell(cart));
			
			if (!group.containsKey(cart.getSellerId())) {
				List<Map<String, Object>> list = Lists.newArrayList();
				list.add(m);
				group.put(cart.getSellerId(), list);
			} else {
				List<Map<String, Object>> list = group.get(cart.getSellerId());
				list.add(m);
			}
		}
		Map<String, Object> returnMap = buildReturnMap(SUCCESS, "");
		String imageRootPath = (String) request.getSession().getServletContext().getAttribute(Global.IMAGE_ROOT_PATH);
		returnMap.put("root", imageRootPath);
		returnMap.put("list", group);
		return buildJson(callback, returnMap);
	}
	
	public int getCartMinSell(Cart cart) {
		//获取最小起拍数
		int minSell = 0;
		if (cart.getSpecId() == null) {
			Product product = productFrontService.getProductById(cart.getProductId());
			minSell = product.getMinSell();
		} else {
			Spec spec = productFrontService.getProductSpec(cart.getProductId(), cart.getSpecId());
			minSell = spec.getMinSell();
		}
		return minSell;
	}
	
	/**
	 * 2. 加入购物车
	/**
	 * @param productId
	 * @param quantity
	 * @param specId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "add", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String add(@RequestParam(value = "goodsId", required = true)Long productId, 
			@RequestParam(value = "quantity", required = true)int quantity, 
			@RequestParam(value = "specId", required = true, defaultValue = "0") Long specId, 
			@RequestHeader(value = "Authorization", required = true) String authorization, 
			@RequestParam(required = false) String callback, HttpServletRequest request, HttpServletResponse response) {
		logger.info("加入购物车 productId {}", productId);
		
		Authorization auth = getAuthorization(authorization);
		if ("1".equals(auth.getErrorCode())) {
			return buildJson(callback, buildReturnMap(auth.getErrorCode(), auth.getErrorMsg()));
		}
		
		Account account = systemService.getAccountByMobile(auth.getMobile());;
		
		Product product = productFrontService.getProductWithActivity(productId);
		if (product == null) {
			logger.info("商品为空");
			return buildJson(callback, buildReturnMap("1", "商品不能为空"));
		}
		if (account == null) {
			logger.info("账户为空");
			return buildJson(callback, buildReturnMap("1", "账户不能为空"));
		}
		Spec spec = null;
		if (specId != null && specId != 0L) {
			spec = productFrontService.getProductSpec(productId, specId);
			if (spec == null) {
				return buildJson(callback, buildReturnMap("1", "商品规格配置错误"));
			}
			product.setActiveSpec(spec);
			
			if (spec.getMinSell() != 0 && quantity < spec.getMinSell()) {
				logger.info("商品最少{}起拍", spec.getMinSell());
				String unit = StringUtils.isBlank(product.getUnit()) ? "个" : product.getUnit();
				return buildJson(callback, buildReturnMap("1", "该商品至少需订购" + spec.getMinSell() + unit));
			}
		} else if (product.getMinSell() != 0 && quantity < product.getMinSell()) {
			logger.info("商品最少{}起拍", product.getMinSell());
			String unit = StringUtils.isBlank(product.getUnit()) ? "个" : product.getUnit();
			return buildJson(callback, buildReturnMap("1", "该商品至少需订购" + product.getMinSell() + unit));
		} 
		try {
			orderFrontService.saveCart(product, specId, account, auth.getToken(), quantity, CartAction.Add.getAction());
			return buildJson(callback, buildReturnMap("0", ""));
		} catch (Exception e) {
			e.printStackTrace();
			return buildJson(callback, buildReturnMap("1", e.getMessage()));
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
	@RequestMapping(value = "update",  method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String update(
			@RequestHeader(value = "Authorization", required = true) String authorization, 
			@RequestParam(value = "goodsId", required = true) Long productId, 
			@RequestParam(value = "specId", required = false) Long specId, 
			@RequestParam(value = "quantity", required = true) int quantity, 
			@RequestParam(value = "action", required = true) int action, 
			@RequestParam(required = false) String callback,
			HttpServletRequest request, HttpServletResponse response) {
		
		Authorization auth = getAuthorization(authorization);
		if ("1".equals(auth.getErrorCode())) {
			return buildJson(callback, buildReturnMap(auth.getErrorCode(), auth.getErrorMsg()));
		}
		
		Account account = systemService.getAccountByMobile(auth.getMobile());;
		
		logger.info("加入购物车 productId {}", productId);
		Product product = productFrontService.getProductWithActivity(productId);
		if (product == null) {
			logger.info("商品为空");
			return buildJson(callback, buildReturnMap("1", "商品为空"));
		}
		if (action != CartAction.Add.getAction() && action != CartAction.Sub.getAction() && action != CartAction.Set.getAction()) {
			logger.info("非法操作");
			return buildJson(callback, buildReturnMap("1", "非法操作"));
		}
		
		try {
			orderFrontService.saveCart(product, specId, account, auth.getToken(), quantity, action);
			int total = 0;
			Map<String, Object> data = Maps.newHashMap();
			List<Cart> carts = orderFrontService.findCart(auth.getToken(), account.getId());
			for (Cart cart : carts) {
				total += cart.getQuantity();
			}
			data.put("total", total);
			data.put("s", "0");
			return  buildJson(callback, data);
		} catch (Exception e) {
			e.printStackTrace();
			return buildJson(callback, buildReturnMap("1", e.getMessage()));
		}
		
	}
	
}
