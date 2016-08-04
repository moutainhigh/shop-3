package com.tuisitang.projects.pm.modules.stock.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tuisitang.modules.shop.utils.Global;
import com.tuisitang.projects.pm.common.persistence.Page;
import com.tuisitang.projects.pm.common.web.BaseController;
import com.tuisitang.projects.pm.modules.stock.entity.StockOrder;
import com.tuisitang.projects.pm.modules.stock.service.StockOrderService;
import com.tuisitang.projects.pm.modules.sys.service.AreaService;
import com.tuisitang.projects.pm.modules.sys.service.SystemService;

/**
 * 库存订单Controller
 */
@Controller
@RequestMapping(value = "${adminPath}/stock/order")
public class StockOrderController extends BaseController {

	@Autowired
	private StockOrderService stockOrderService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private SystemService systemService;

	@ModelAttribute
	public StockOrder get(@RequestParam(required = false) Long id) {
		if (id != null) {
			return stockOrderService.getStockOrder(id);
		} else {
			return new StockOrder();
		}
	}

	@RequiresPermissions("stock:order:view")
	@RequestMapping(value = { "list", "" })
	public String list(StockOrder stockOrder, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<StockOrder> page = stockOrderService.findStockOrder(new Page<StockOrder>(
				request, response), stockOrder);
		model.addAttribute("page", page);
		return "modules/stock/orderList";
	}

	@RequiresPermissions("stock:order:view")
	@RequestMapping(value = "form")
	public String form(StockOrder stockOrder, Model model) {
		model.addAttribute("stockOrder", stockOrder);
		return "modules/stock/orderForm";
	}

	@RequiresPermissions("stock:order:edit")
	@RequestMapping(value = "save")
	public String save(StockOrder stockOrder, Model model,
			RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + Global.getAdminPath() + "/stock/order?repage";
		}
		if (!beanValidator(model, stockOrder)) {
			return form(stockOrder, model);
		}
		stockOrderService.saveStockOrder(stockOrder);
		addMessage(redirectAttributes, "保存库存订单'" + stockOrder.getNo() + "'成功");
		return "redirect:" + Global.getAdminPath() + "/stock/order?repage";
	}

	@RequiresPermissions("stock:order:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + Global.getAdminPath()
					+ "/stock/order?repage";
		}
		stockOrderService.deleteStockOrder(id);
		addMessage(redirectAttributes, "删除库存订单成功");
		return "redirect:" + Global.getAdminPath() + "/stock/order?repage";
	}
	
}
