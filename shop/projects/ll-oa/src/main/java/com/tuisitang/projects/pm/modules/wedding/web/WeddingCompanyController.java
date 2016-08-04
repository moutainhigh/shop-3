package com.tuisitang.projects.pm.modules.wedding.web;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.tuisitang.common.utils.DateUtils;
import com.tuisitang.modules.shop.utils.Global;
import com.tuisitang.projects.pm.common.persistence.Page;
import com.tuisitang.projects.pm.common.util.excel.ExportExcel;
import com.tuisitang.projects.pm.common.web.BaseController;
import com.tuisitang.projects.pm.modules.sys.entity.Area;
import com.tuisitang.projects.pm.modules.sys.entity.User;
import com.tuisitang.projects.pm.modules.sys.service.AreaService;
import com.tuisitang.projects.pm.modules.sys.utils.UserUtils;
import com.tuisitang.projects.pm.modules.wedding.entity.WeddingCompany;
import com.tuisitang.projects.pm.modules.wedding.service.WeddingCompanyService;

/**
 * 婚庆公司Controller
 * @author ThinkGem
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/wedding/company")
public class WeddingCompanyController extends BaseController {

	@Autowired
	private WeddingCompanyService weddingCompanyService;
	@Autowired
	private AreaService areaService;

	@ModelAttribute
	public WeddingCompany get(@RequestParam(required = false) Long id) {
		if (id != null) {
			return weddingCompanyService.getWeddingCompany(id);
		} else {
			return new WeddingCompany();
		}
	}

	@RequiresPermissions("wedding:company:view")
	@RequestMapping(value = { "list", "" })
	public String list(WeddingCompany weddingCompany, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<WeddingCompany> page = weddingCompanyService.findWeddingCompany(new Page<WeddingCompany>(
				request, response), weddingCompany);
		model.addAttribute("provinces", UserUtils.getProvinceList());
		if (StringUtils.isNotEmpty(weddingCompany.getProvinceCode())) {
			Area province = areaService.get(weddingCompany.getProvinceCode());
			model.addAttribute("cities", areaService.findByParentId(province.getId()));
		}
		model.addAttribute("page", page);
		return "modules/wedding/companyList";
	}

	@RequiresPermissions("wedding:company:view")
	@RequestMapping(value = "form")
	public String form(WeddingCompany weddingCompany, Model model) {
		model.addAttribute("weddingCompany", weddingCompany);
		model.addAttribute("provinces", UserUtils.getProvinceList());
		if (StringUtils.isNotEmpty(weddingCompany.getProvinceCode())) {
			Area province = areaService.get(weddingCompany.getProvinceCode());
			model.addAttribute("cities", areaService.findByParentId(province.getId()));
			
			if (StringUtils.isNotEmpty(weddingCompany.getCityCode())) {
				Area city = areaService.get(weddingCompany.getCityCode());
				model.addAttribute("counties", areaService.findByParentId(city.getId()));
			}
		}
		return "modules/wedding/companyForm";
	}

	@RequiresPermissions("wedding:company:edit")
	@RequestMapping(value = "save")
	public String save(WeddingCompany weddingCompany, Model model,
			RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + Global.getAdminPath() + "/wedding/company?repage";
		}
		if (!beanValidator(model, weddingCompany)) {
			return form(weddingCompany, model);
		}
		weddingCompanyService.saveWeddingCompany(weddingCompany);
		addMessage(redirectAttributes, "保存婚庆公司'" + weddingCompany.getName() + "'成功");
		return "redirect:" + Global.getAdminPath() + "/wedding/company?repage";
	}

	@RequiresPermissions("wedding:company:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + Global.getAdminPath() + "/wedding/company?repage";
		}
		weddingCompanyService.deleteWeddingCompany(id);
		addMessage(redirectAttributes, "删除婚庆公司成功");
		return "redirect:" + Global.getAdminPath() + "/wedding/company?repage";
	}
	
	@RequiresPermissions("wedding:company:edit")
	@RequestMapping(value = "sync")
	public @ResponseBody Map<String, Object> sync() {
		try {
			List<Area> provinces = UserUtils.getProvinceList();
			List<WeddingCompany> list = weddingCompanyService.findAllWeddingCompany();
			for (WeddingCompany wc : list) {
				if (StringUtils.isBlank(wc.getProvince()) || StringUtils.isBlank(wc.getCity())) {
					if (StringUtils.isNotBlank(wc.getJson())) {
						// 号码 18284000566 归属地 四川 广元 卡类型 中国移动
						String[] ss = wc.getJson().split(" ");
						String province = ss[3];
						String city = ss[4];
						logger.info("province {}, city {}", province, city);
						for (Area area : provinces) {
							if (area.getName().indexOf(province) >= 0) {
								wc.setProvince(area.getName());
								wc.setProvinceCode(area.getCode());

								List<Area> cities = areaService.findByParentId(area.getId());
								if (!cities.isEmpty()) {
									for (Area a : cities) {
										if (a.getName().indexOf(city) >= 0) {
											wc.setCity(a.getName());
											wc.setCityCode(a.getCode());
										}
									}
								}
							}
						}
					}
				}
			}

			weddingCompanyService.saveWeddingCompany(list);
			return buildReturnMap(SUCCESS, "");
		} catch (Exception e) {
			e.printStackTrace();
			return buildReturnMap(FAILURE, e.getMessage());
		}
	}
	
	@RequiresPermissions("wedding:company:edit")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(WeddingCompany weddingCompany, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "婚庆公司数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx"; 
    		List<WeddingCompany> list = weddingCompanyService.findAllWeddingCompany();
    		new ExportExcel("婚庆公司数据", WeddingCompany.class).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出婚庆公司失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/wedding/company/?repage";
    }

}
