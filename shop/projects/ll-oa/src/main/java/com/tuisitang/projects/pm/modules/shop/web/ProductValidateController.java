package com.tuisitang.projects.pm.modules.shop.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.tuisitang.common.mapper.JsonMapper;
import com.tuisitang.projects.pm.common.persistence.Page;
import com.tuisitang.projects.pm.common.web.BaseController;
import com.tuisitang.projects.pm.modules.shop.entity.Product;
import com.tuisitang.projects.pm.modules.shop.entity.ProductSpec;
import com.tuisitang.projects.pm.modules.shop.entity.SpecPrice;
import com.tuisitang.projects.pm.modules.shop.entity.SupplierProduct;
import com.tuisitang.projects.pm.modules.shop.service.ProductService;
import com.tuisitang.projects.pm.modules.shop.service.SupplierService;

@Controller
@RequestMapping(value = "${adminPath}/shop/pv")
public class ProductValidateController extends BaseController {

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

	@RequiresPermissions("shop:pv:view")
	@RequestMapping(value = { "list", "" })
	public String list(Product product, @RequestParam(value = "supplierId", required = false) Long supplierId,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Product> page = productService.findProduct(new Page<Product>(request, response), product, supplierId);
		model.addAttribute("supplierList", supplierService.findAllSupplier());
		model.addAttribute("supplierId", supplierId);
		model.addAttribute("page", page);
		return "modules/shop/pvList";
	}
	
	@RequiresPermissions("shop:pv:view")
	@RequestMapping(value = "image")
	public String image(Product product, @RequestParam(value = "type", required = true) String type,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("product {}, id {}", product, type);
		model.addAttribute("type", type);
		model.addAttribute("product", product);
		return "modules/shop/pvImage";
	}
	
	@RequiresPermissions("shop:pv:edit")
	@RequestMapping(value = "{id}/price/modify")
	public @ResponseBody
	boolean modifyPrice(@PathVariable("id") Long id, @RequestParam("value") double price, HttpServletRequest request,
			HttpServletResponse response) {
		Product product = productService.getProduct(id);
		SupplierProduct sp = supplierService.getSupplierProduct(product.getSourceId());
		product.setFactoryPrice(price);
		if (product.getIncreaseRate() != null) {
			product.setPrice((1 + product.getIncreaseRate()) * product.getFactoryPrice());
		}
		sp.setPrice(price);
		productService.saveProduct(product);
		supplierService.saveSupplierProduct(sp);
		return true;
	}
	
	@RequiresPermissions("shop:pv:edit")
	@RequestMapping(value = "{productId}/{id}/price/modify")
	public @ResponseBody
	boolean modifyPrice(@PathVariable("productId") Long productId, @PathVariable("id") Long id, @RequestParam("value") double price, HttpServletRequest request,
			HttpServletResponse response) {
		ProductSpec ps = productService.getProductSpec(id);
		SupplierProduct sp = supplierService.getSupplierProduct(ps.getProduct().getSourceId());

		ps.setPrice(price);
		if (ps.getProduct().getIncreaseRate() != null) {
			ps.setNowPrice((1 + ps.getProduct().getIncreaseRate()) * price);
		}
		productService.saveProductSpec(ps);
		
		List<SpecPrice> list = Lists.newArrayList();
		List<ProductSpec> l = productService.findProductSpec(ps.getProduct()
				.getId());
		for (ProductSpec productSpec : l) {
			SpecPrice specPrice = new SpecPrice();
			specPrice.setColor(productSpec.getColor());
			specPrice.setSize(productSpec.getSize());
			specPrice.setType(productSpec.getType());
			specPrice.setPrice(productSpec.getPrice());
			list.add(specPrice);
		}
		String json = JsonMapper.getInstance().toJson(list);
		logger.info("{}", json);
		sp.setJson(json);
		
		supplierService.saveSupplierProduct(sp);
		return true;
	}
	
	@RequiresPermissions("shop:pv:edit")
	@RequestMapping(value = "{id}/modifyIncreaseRate")
	public @ResponseBody
	boolean modifyIncreaseRate(@PathVariable("id") Long id, @RequestParam("value") double value, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("modifyIncreaseRate id {}, value {}", id, value);
		Product product = productService.getProduct(id);
		product.setIncreaseRate(value);
		product.setPrice((1 + value) * product.getFactoryPrice());
		productService.saveProduct(product);
		List<ProductSpec> psList = productService.findProductSpec(id);
		for (ProductSpec ps : psList) {
			ps.setNowPrice((1 + value) * ps.getPrice());
			productService.saveProductSpec(ps);
		}
		return true;
	}
	
	@RequiresPermissions("shop:pv:edit")
	@RequestMapping(value = "{id}/modifyExpressPrice")
	public @ResponseBody
	boolean modifyExpressPrice(@PathVariable("id") Long id, @RequestParam("value") double value, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("modifyExpressPrice id {}, value {}", id, value);
		Product product = productService.getProduct(id);
		product.setExpressPrice(value);
		productService.saveProduct(product);
		return true;
	}
	
	
	@RequiresPermissions("shop:pv:edit")
	@RequestMapping(value = "{id}/delete")
	public @ResponseBody boolean delete(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
		logger.info("delete id {}", id);
		productService.deleteProduct(id);
		return true;
	}

}
