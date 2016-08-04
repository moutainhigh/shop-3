/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.tuisitang.projects.pm.modules.shop.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresUser;
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
import com.tuisitang.modules.shop.utils.Global;
import com.tuisitang.projects.pm.common.web.BaseController;
import com.tuisitang.projects.pm.modules.shop.entity.Catalog;
import com.tuisitang.projects.pm.modules.shop.service.ProductService;

@Controller
@RequestMapping(value = "${adminPath}/shop/catalog")
public class CatalogController extends BaseController {

	@Autowired
	private ProductService productService;
	
	@ModelAttribute("catalog")
	public Catalog get(@RequestParam(required=false) Long id) {
		if (id != null){
			return productService.getCatalog(id);
		}else{
			return new Catalog();
		}
	}

	@RequiresPermissions("shop:catalog:view")
	@RequestMapping(value = {"list", ""})
	public String list(Model model) {
		List<Catalog> list = Lists.newArrayList();
		List<Catalog> sourcelist = productService.findAllCatalog();
		Catalog.sortList(list, sourcelist, 1L);
        model.addAttribute("list", list);
		return "modules/shop/catalogList";
	}

	@RequiresPermissions("shop:catalog:view")
	@RequestMapping(value = "form")
	public String form(Catalog catalog, Model model) {
		if (catalog.getParent()==null||catalog.getParent().getId()==null){
			catalog.setParent(new Catalog(1L));
		}
		catalog.setParent(productService.getCatalog(catalog.getParent().getId()));
		model.addAttribute("catalog", catalog);
		return "modules/shop/catalogForm";
	}
	
	@RequiresPermissions("shop:catalog:edit")
	@RequestMapping(value = "save")
	public String save(Catalog catalog, Model model, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:"+Global.getAdminPath()+"/shop/catalog/";
		}
		if (!beanValidator(model, catalog)){
			return form(catalog, model);
		}
		productService.saveCatalog(catalog);
		addMessage(redirectAttributes, "保存栏目'" + catalog.getName() + "'成功");
		return "redirect:"+Global.getAdminPath()+"/shop/catalog/";
	}
	
	@RequiresPermissions("shop:catalog:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		if(Global.isDemoMode()){
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:"+Global.getAdminPath()+"/shop/catalog/";
		}
		if (Catalog.isRoot(id)){
			addMessage(redirectAttributes, "删除栏目失败, 不允许删除顶级栏目或编号为空");
		}else{
			productService.deleteCatalog(id);
			addMessage(redirectAttributes, "删除栏目成功");
		}
		return "redirect:"+Global.getAdminPath()+"/shop/catalog/";
	}

	/**
	 * 批量修改栏目排序
	 */
	@RequiresPermissions("shop:catalog:edit")
	@RequestMapping(value = "updateSort")
	public String updateSort(Long[] ids, Integer[] sorts, RedirectAttributes redirectAttributes) {
    	int len = ids.length;
    	Catalog[] entitys = new Catalog[len];
    	for (int i = 0; i < len; i++) {
    		entitys[i] = productService.getCatalog(ids[i]);
    		entitys[i].setSort(sorts[i]);
    		productService.saveCatalog(entitys[i]);
    	}
    	addMessage(redirectAttributes, "保存栏目排序成功!");
		return "redirect:"+Global.getAdminPath()+"/shop/catalog/";
	}
	
	@RequiresUser
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(String module, @RequestParam(required=false) Long extId, HttpServletResponse response) {
		response.setContentType("application/json; charset=UTF-8");
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Catalog> list = productService.findAllCatalog();
		for (int i=0; i<list.size(); i++){
			Catalog e = list.get(i);
			if (extId == null || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParent()!=null?e.getParent().getId():0);
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
}
