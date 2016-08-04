package com.tuisitang.projects.pm.modules.shop.web;

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
import com.tuisitang.projects.pm.modules.shop.entity.Supplier;
import com.tuisitang.projects.pm.modules.shop.service.SupplierService;
import com.tuisitang.projects.pm.modules.sys.utils.UserUtils;

/**
 * 供应商Controller
 * @author ThinkGem
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/supplier")
public class SupplierController extends BaseController {

	@Autowired
	private SupplierService supplierService;

	@ModelAttribute
	public Supplier get(@RequestParam(required = false) Long id) {
		if (id != null) {
			return supplierService.getSupplier(id);
		} else {
			return new Supplier();
		}
	}

	@RequiresPermissions("shop:supplier:view")
	@RequestMapping(value = { "list", "" })
	public String list(Supplier supplier, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<Supplier> page = supplierService.findSupplier(new Page<Supplier>(
				request, response), supplier);
		model.addAttribute("provinces", UserUtils.getProvinceList());
		model.addAttribute("page", page);
		return "modules/shop/supplierList";
	}

	@RequiresPermissions("shop:supplier:view")
	@RequestMapping(value = "form")
	public String form(Supplier supplier, Model model) {
		model.addAttribute("supplier", supplier);
		model.addAttribute("provinces", UserUtils.getProvinceList());
		return "modules/shop/supplierForm";
	}

	@RequiresPermissions("shop:supplier:edit")
	@RequestMapping(value = "save")
	public String save(Supplier supplier, Model model,
			RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + Global.getAdminPath() + "/shop/supplier/?repage";
		}
		if (!beanValidator(model, supplier)) {
			return form(supplier, model);
		}
		supplierService.saveSupplier(supplier);
		addMessage(redirectAttributes, "保存供应商'" + supplier.getName() + "'成功");
		return "redirect:" + Global.getAdminPath() + "/shop/supplier/?repage";
	}

	@RequiresPermissions("shop:supplier:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + Global.getAdminPath() + "/shop/supplier/?repage";
		}
		supplierService.deleteSupplier(id);
		addMessage(redirectAttributes, "删除供应商成功");
		return "redirect:" + Global.getAdminPath() + "/shop/supplier/?repage";
	}

}
