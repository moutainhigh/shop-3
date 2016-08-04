package com.tuisitang.projects.pm.modules.shop.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.tuisitang.modules.shop.utils.Global;
import com.tuisitang.projects.pm.common.persistence.Page;
import com.tuisitang.projects.pm.common.web.BaseController;
import com.tuisitang.projects.pm.modules.shop.entity.Attr;
import com.tuisitang.projects.pm.modules.shop.entity.Catalog;
import com.tuisitang.projects.pm.modules.shop.entity.Product;
import com.tuisitang.projects.pm.modules.shop.entity.ProductAttr;
import com.tuisitang.projects.pm.modules.shop.entity.ProductSpec;
import com.tuisitang.projects.pm.modules.shop.entity.Supplier;
import com.tuisitang.projects.pm.modules.shop.service.ProductService;
import com.tuisitang.projects.pm.modules.shop.service.SupplierService;

@Controller
@RequestMapping(value = "${adminPath}/shop/product")
public class ProductController extends BaseController {

	@Autowired
	private ProductService productService;
	@Autowired
	private SupplierService supplierService;
	
	@ModelAttribute("product")
	public Product get(@RequestParam(required=false) Long id) {
		if (id != null){
			return productService.getProduct(id);
		}else{
			return new Product();
		}
	}

	@RequiresPermissions("shop:product:view")
	@RequestMapping(value = {"list", ""})
	public String list(Product product, @RequestParam(value = "supplierId", required = false) Long supplierId, 
			HttpServletRequest request, HttpServletResponse response, Model model) {
		model.addAttribute("supplierList", supplierService.findAllSupplier());
		model.addAttribute("supplierId", supplierId);
		Page<Product> page = productService.findProduct(new Page<Product>(request, response), product, supplierId);
        model.addAttribute("page", page);
		return "modules/shop/productList";
	}

	@RequiresPermissions("shop:product:view")
	@RequestMapping(value = "form")
	public String form(Product product, Model model) {
		List<Catalog> catalogList = productService.findAllCatalog();
		List<Attr> attrList = productService.findAllAttr();
		List<Supplier> supplierList = supplierService.findAllSupplier();
//		model.addAttribute(attributeName, productService.findProductAttr(productId))
		model.addAttribute("catalogList", catalogList);
		model.addAttribute("supplierList", supplierList);
		model.addAttribute("attrList", attrList);
		model.addAttribute("product", product);
		model.addAttribute("paList", productService.findProductAttr(product.getId()));
		model.addAttribute("psList", productService.findProductSpec(product.getId()));
		return "modules/shop/productForm";
	}
	
	@RequiresPermissions("shop:product:edit")
	@RequestMapping(value = "save")
	public String save(Product product, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:"+Global.getAdminPath()+"/shop/product/";
		}
		if (!beanValidator(model, product)){
			return form(product, model);
		}
		List<ProductSpec> psList = product.getPsList();
		for (ProductSpec ps : psList) {
			logger.info("color {}, size {}, type {}, price {}", ps.getColor(),
					ps.getSize(), ps.getType(), ps.getPrice());
		}
		String domain = Global.getQiniuDomain() + "/";
		if (!StringUtils.isBlank(product.getImages())) {
			product.setImages(product.getImages().replaceAll(domain, ""));
		}
		if (!StringUtils.isBlank(product.getDetails())) {
			product.setDetails(product.getDetails().replaceAll(domain, ""));
		}
		productService.saveProduct(product);
		if (StringUtils.isNotBlank(request.getParameter("type"))) {
			if ("images".equals(request.getParameter("type"))) {
				addMessage(redirectAttributes, "保存产品'" + product.getName() + "'主图成功");
			} else {
				addMessage(redirectAttributes, "保存产品'" + product.getName() + "'主图成功");
			}
			return "redirect:" + Global.getAdminPath() + "/shop/pv/image?id=" + product.getId() + "&type=" + request.getParameter("type");
		} else {
			addMessage(redirectAttributes, "保存产品'" + product.getName() + "'成功");
			return "redirect:" + Global.getAdminPath() + "/shop/product/";
		}
	}
	
	@RequiresPermissions("shop:product:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:"+Global.getAdminPath()+"/shop/product/";
		}
		productService.deleteProduct(id);
		addMessage(redirectAttributes, "删除产品成功");
		return "redirect:"+Global.getAdminPath()+"/shop/product/";
	}
	
	/**
	 * 保存ProdSpec
	 */
	@RequiresPermissions("shop:product:edit")
	@RequestMapping(value = "ps/save")
	public @ResponseBody Map<String, Object> saveProductSpec(@RequestParam(value = "productId", required = true) Long productId,
			@RequestParam(value = "color", required = false) String color,
			@RequestParam(value = "size", required = false) String size,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "factoryPrice", required = false) double factoryPrice,
			@RequestParam(value = "nowPrice", required = false) double nowPrice,
			@RequestParam(value = "minSale", required = false, defaultValue = "0") int minSale,
			HttpServletRequest request) {
		Map<String, Object> returnMap = Maps.newHashMap();
		boolean isSuccess = true;
		String msg = "";
		try {
			Long id = StringUtils.isBlank(request.getParameter("productSpecId")) ? null
					: new Long(request.getParameter("productSpecId"));
			ProductSpec ps = null;
			if (id != null) {
				ps = productService.getProductSpec(id);
			}
			if (ps == null) {
				ps = new ProductSpec();
				ps.setProduct(new Product(productId));
			}
			ps.setColor(color);
			ps.setSize(size);
			ps.setType(type);
			ps.setPrice(factoryPrice);
			ps.setNowPrice(nowPrice);
			ps.setMinSale(minSale);
			productService.saveProductSpec(ps);
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
			msg = e.getMessage();
		}
		returnMap.put("isSuccess", isSuccess);
		returnMap.put("msg", msg);
		return returnMap;
	}
	
	@RequiresPermissions("shop:product:edit")
	@RequestMapping(value = "ps/delete/{productSpecId}")
	public @ResponseBody Map<String, Object> deleteProductSpec(@PathVariable("productSpecId") Long productSpecId, HttpServletRequest request) {
		Map<String, Object> returnMap = Maps.newHashMap();
		boolean isSuccess = true;
		String msg = "";
		try {
			productService.deleteProductSpec(productSpecId);
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
			msg = e.getMessage();
		}
		returnMap.put("isSuccess", isSuccess);
		returnMap.put("msg", msg);
		return returnMap;
	}
	
	/**
	 * 保存ProductAttr
	 */
	@RequiresPermissions("shop:product:edit")
	@RequestMapping(value = "pa/save")
	public @ResponseBody Map<String, Object> saveProductAttr(@RequestParam(value = "productId", required = true) Long productId, 
			@RequestParam(value = "attrName", required = true) String attrName, 
			@RequestParam(value = "attrVal", required = true) String attrVal, HttpServletRequest request) {
		Map<String, Object> returnMap = Maps.newHashMap();
		boolean isSuccess = true;
		String msg = "";
		try {
			Long id = StringUtils.isBlank(request.getParameter("productAttrId")) ? null : new Long(request.getParameter("productAttrId"));
			Attr attr = productService.getAttr(attrName);
			ProductAttr pa = null;
			if (id != null) {
				pa = productService.getProductAttr(id);
			}
			if (pa == null) {
				pa = new ProductAttr();
				pa.setProduct(new Product(productId));
			}
			pa.setAttr(attr);
			pa.setAttrVal(attrVal);
			productService.saveProductAttr(pa);
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
			msg = e.getMessage();
		}
		returnMap.put("isSuccess", isSuccess);
		returnMap.put("msg", msg);
		return returnMap;
	}
	
	@RequiresPermissions("shop:product:edit")
	@RequestMapping(value = "pa/delete/{productAttrId}")
	public @ResponseBody
	Map<String, Object> deleteProductAttr(@PathVariable("productAttrId") Long productAttrId, HttpServletRequest request) {
		Map<String, Object> returnMap = Maps.newHashMap();
		boolean isSuccess = true;
		String msg = "";
		try {
			productService.deleteProductAttr(productAttrId);
		} catch (Exception e) {
			e.printStackTrace();
			isSuccess = false;
			msg = e.getMessage();
		}
		returnMap.put("isSuccess", isSuccess);
		returnMap.put("msg", msg);
		return returnMap;
	}
	
}
