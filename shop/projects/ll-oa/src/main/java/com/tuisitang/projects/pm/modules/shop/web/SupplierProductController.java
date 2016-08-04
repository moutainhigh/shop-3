package com.tuisitang.projects.pm.modules.shop.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tuisitang.common.mapper.JsonMapper;
import com.tuisitang.modules.shop.utils.Global;
import com.tuisitang.projects.pm.common.persistence.Page;
import com.tuisitang.projects.pm.common.web.BaseController;
import com.tuisitang.projects.pm.modules.shop.entity.SpecPrice;
import com.tuisitang.projects.pm.modules.shop.entity.Supplier;
import com.tuisitang.projects.pm.modules.shop.entity.SupplierProduct;
import com.tuisitang.projects.pm.modules.shop.service.SupplierService;
import com.tuisitang.projects.pm.modules.sys.entity.Dict;
import com.tuisitang.projects.pm.modules.sys.service.DictService;
import com.tuisitang.projects.pm.modules.sys.utils.DictUtils;

/**
 * 供应商-商品Controller
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/sp")
public class SupplierProductController extends BaseController {

	@Autowired
	private SupplierService supplierService;
	@Autowired
	private DictService dictService;

	@ModelAttribute
	public SupplierProduct get(@RequestParam(required = false) Long id) {
		if (id != null) {
			return supplierService.getSupplierProduct(id);
		} else {
			return new SupplierProduct();
		}
	}

	@RequiresPermissions("shop:sp:view")
	@RequestMapping(value = { "list", "" })
	public String list(SupplierProduct sp, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SupplierProduct> page = supplierService.findSupplierProduct(new Page<SupplierProduct>(
				request, response), sp);
		List<Supplier> supplierList = supplierService.findAllSupplier();
		model.addAttribute("supplierList", supplierList);
		model.addAttribute("page", page);
		return "modules/shop/spList";
	}

	@RequiresPermissions("shop:sp:view")
	@RequestMapping(value = "form")
	public String form(SupplierProduct sp, Model model) {
		logger.info("supplier.id {}", sp.getSupplier() == null ? null : sp.getSupplier().getId());
		if (sp.getSupplier() != null && sp.getSupplier().getId() != null) {
			Supplier supplier = supplierService.getSupplier(sp.getSupplier().getId());
			logger.info("supplier {}", supplier);
			sp.setSupplier(supplier);
		} else {
			sp.setSupplier(new Supplier());
		}
		List<Supplier> supplierList = supplierService.findAllSupplier();
		model.addAttribute("supplierList", supplierList);
		String length = sp.getLength();
		String width = sp.getWidth();
		String height = sp.getHeight();
		if (!StringUtils.isBlank(length)) {
			model.addAttribute("lengths", (Double[]) ConvertUtils.convert(StringUtils.split(length, ","), Double.class));
		}
		if (!StringUtils.isBlank(length)) {
			model.addAttribute("widths", (Double[]) ConvertUtils.convert(StringUtils.split(width, ","), Double.class));
		}
		if (!StringUtils.isBlank(length)) {
			model.addAttribute("heights", (Double[]) ConvertUtils.convert(StringUtils.split(height, ","), Double.class));
		}
		if (!StringUtils.isBlank(sp.getJson())) {
			JsonMapper mapper = JsonMapper.getInstance();
			model.addAttribute("ppList", mapper.fromJson(sp.getJson(), List.class));
		}
		model.addAttribute("sp", sp);
		return "modules/shop/spForm";
	}

	@RequiresPermissions("shop:sp:edit")
	@RequestMapping(value = "save")
	public String save(SupplierProduct sp, HttpServletRequest request, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + Global.getAdminPath() + "/shop/sp/?repage";
		}
		if (!beanValidator(model, sp)) {
			return form(sp, model);
		}
		String[] lengths = request.getParameterValues("l");
		String[] widths = request.getParameterValues("w");
		String[] heights = request.getParameterValues("h");
		String[] colors = request.getParameterValues("color");
		String[] sizes = request.getParameterValues("size");
		String[] specs = request.getParameterValues("spec");
		String length = "";
		String width = "";
		String height = "";
		if (lengths != null && lengths.length > 0 && widths != null
				&& widths.length > 0 && heights != null && heights.length > 0) {
			for (int i = 0; i < lengths.length; i++) {
				length += lengths[i] + ",";
				width += widths[i] + ",";
				height += heights[i] + ",";
			}
		}
		String color = "";
		if (colors != null && colors.length > 0) {
			for (String s : colors) {
				color += s + ",";
			}
		}
		String size = "";
		if (sizes != null && sizes.length > 0) {
			for (String s : sizes) {
				size += s + ",";
			}
		}
		String spec = "";
		if (specs != null && specs.length > 0) {
			for (String s : specs) {
				spec += s + ",";
			}
		}
		// JSON格式的价格
		String[] pcs = request.getParameterValues("pc");// 颜色
		String[] pss = request.getParameterValues("ps");// 尺寸
		String[] pts = request.getParameterValues("pt");// 规格/类型
		String[] pps = request.getParameterValues("pp");// 价格
		String[] ppm = request.getParameterValues("pm");// 最少购买数
		List<SpecPrice> spList = Lists.newArrayList();
		if (pps != null) {
			for (int i = 0; i < pps.length; i++) {
				if (Double.parseDouble(pps[i]) > 0) {
					SpecPrice specPrice = new SpecPrice();
					if (pcs != null)
						specPrice.setColor(pcs[i]);
					if (pss != null)
						specPrice.setSize(pss[i]);
					if (pts != null)
						specPrice.setType(pts[i]);
					specPrice.setMinSale(StringUtils.isBlank(ppm[i]) ? 0 : Integer.parseInt(ppm[i]));
					specPrice.setPrice(Double.parseDouble(pps[i]));
					spList.add(specPrice);
				}
			}
		}
		
		sp.setColor(color);
		sp.setSize(size);
		sp.setSpec(spec);
		sp.setLength(length);
		sp.setWidth(width);
		sp.setHeight(height);
		sp.setJson(JsonMapper.getInstance().toJson(spList));
		supplierService.saveSupplierProduct(sp);
		addMessage(redirectAttributes, "保存供应商-商品'" + sp.getProductName() + "'成功");
		return "redirect:" + Global.getAdminPath() + "/shop/sp/?repage";
	}

	@RequiresPermissions("shop:sp:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + Global.getAdminPath() + "/shop/sp/?repage";
		}
		supplierService.deleteSupplierProduct(id);
		addMessage(redirectAttributes, "删除供应商-商品成功");
		return "redirect:" + Global.getAdminPath() + "/shop/sp/?repage";
	}

	@RequiresPermissions("shop:sp:edit")
	@RequestMapping(value = "spec")
	public @ResponseBody Map<String, Object> spec(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> returnMap = Maps.newHashMap();
		returnMap.put("isSuccess", true);
		returnMap.put("msg", "");
		try {
			String type = request.getParameter("type");
			String color = request.getParameter("color");
			String size = request.getParameter("size");
			String spec = request.getParameter("spec");
			logger.info("type {}, color {}, size {}, spec {}", type, color, size, spec);
			String[] colors = null;
			String[] sizes = null;
			String[] specs = null;
			if (StringUtils.isNotBlank(color)) {
				colors = color.split(",");
			}
			if (StringUtils.isNotBlank(size)) {
				sizes = size.split(",");
			}
			if (StringUtils.isNotBlank(spec)) {
				specs = spec.split(",");
			}
			
			List<List<String>> l = Lists.newArrayList();
			StringBuffer head = new StringBuffer();
			Map<Integer, String> m = Maps.newHashMap();
//			sb.append("<table class=\"table table-striped table-bordered table-condensed\"><thead><tr>");
			if (colors != null && colors.length >= 0) {
//				returnMap.put("colors", Lists.newArrayList(colors));
				m.put(l.size(), "pc");
				l.add(Lists.newArrayList(colors));
				head.append("<th>颜色</th>");
				if ("color".equals(type)) {
					List<Dict> dictList = DictUtils.getDictList("shop_product_color");
					for (String s : colors) {
						boolean isNew = true;
						for (Dict dict : dictList) {
							if (s.equals(dict.getValue())) {
								isNew = false;
								break;
							}
						}
						if (isNew) {
							dictService.save(new Dict(s, s, "shop_product_color", s, 100));
						}
					}
				}
			}
			if (sizes != null && sizes.length >= 0) {
//				returnMap.put("sizes", Lists.newArrayList(sizes));
				m.put(l.size(), "ps");
				l.add(Lists.newArrayList(sizes));
				head.append("<th>大小</th>");
				if ("size".equals(type)) {
					List<Dict> dictList = DictUtils.getDictList("shop_product_size");
					for (String s : sizes) {
						boolean isNew = true;
						for (Dict dict : dictList) {
							if (s.equals(dict.getValue())) {
								isNew = false;
								break;
							}
						}
						if (isNew) {
							dictService.save(new Dict(s, s, "shop_product_size", s, 100));
						}
					}
				}
			}
			if (specs != null && specs.length >= 0) {
//				returnMap.put("specs", Lists.newArrayList(specs));
				m.put(l.size(), "pt");
				l.add(Lists.newArrayList(specs));
				head.append("<th>规格</th>");
				if ("spec".equals(type)) {
					List<Dict> dictList = DictUtils.getDictList("shop_product_spec");
					for (String s : specs) {
						boolean isNew = true;
						for (Dict dict : dictList) {
							if (s.equals(dict.getValue())) {
								isNew = false;
								break;
							}
						}
						if (isNew) {
							dictService.save(new Dict(s, s, "shop_product_spec", s, 100));
						}
					}
				}
			}
			head.append("<th>最少购买数</th><th>价格</th>").append("<th>操作</th>");
			
			List<String> result = Lists.newArrayList();
			decare(l, result , 0, "");
			StringBuffer body = new StringBuffer();
			for (String s : result) {
				logger.info("{}", s);
				if (s.startsWith("|"))
					s = s.substring(1);
				String[] ss = s.split("\\|");
				body.append("<tr>");
				StringBuffer hidden = new StringBuffer();
				for (int j=0;j<ss.length;j++) {
					hidden.append("<input type='hidden' name='" + m.get(j) + "' value='" + ss[j] + "' />");
					body.append("<td>" + ss[j] + "</td>");
				}
				body.append("<td><input type='number' name='pm' value='0' class='required' /></td>")
					.append("<td><input type='number' name='pp' value='0.00' class='required' />")
						.append(hidden)
						.append("</td>")
						.append("<td><a href='javascript:void(0);'>删除</a></td>");
				body.append("</tr>");
			}
			
			String table = "<table class=\"table table-striped table-bordered table-condensed\"><thead><tr>" + head +"</tr></thead><tbody>" + body + "</tbody></table>";
			logger.info("table {}", table);
			returnMap.put("table", table);
		} catch (Exception e) {
			e.printStackTrace();
			returnMap.put("isSuccess", false);
			returnMap.put("msg", e.getMessage());
		}
		return returnMap;
	}
	
	public static void decare(List<List<String>> dimvalue, List<String> result, int layer, String curstring) {
		// 大于一个集合时：
		if (layer < dimvalue.size() - 1) {
			// 大于一个集合时，第一个集合为空
			if (dimvalue.get(layer).size() == 0)
				decare(dimvalue, result, layer + 1, curstring);
			else {
				for (int i = 0; i < dimvalue.get(layer).size(); i++) {
					StringBuilder s1 = new StringBuilder();
					s1.append(curstring + "|");
					s1.append(dimvalue.get(layer).get(i));
					decare(dimvalue, result, layer + 1, s1.toString());
				}
			}
		}
		// 只有一个集合时：
		else if (layer == dimvalue.size() - 1) {
			// 只有一个集合，且集合中没有元素
			if (dimvalue.get(layer).size() == 0) {
				result.add(curstring);
			} else {// 只有一个集合，且集合中有元素时：其笛卡尔积就是这个集合元素本身
				for (int i = 0; i < dimvalue.get(layer).size(); i++) {
					result.add(curstring + "|" + dimvalue.get(layer).get(i));
				}
			}
		}
	}
}
