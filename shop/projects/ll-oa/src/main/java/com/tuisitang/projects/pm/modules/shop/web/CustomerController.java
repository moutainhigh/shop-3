package com.tuisitang.projects.pm.modules.shop.web;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.tuisitang.common.ds.SchemaContextHolder;
import com.tuisitang.common.utils.DateUtils;
import com.tuisitang.common.utils.SpringContextHolder;
import com.tuisitang.common.utils.ValidateUtils;
import com.tuisitang.modules.shop.utils.Global;
import com.tuisitang.projects.pm.common.persistence.Page;
import com.tuisitang.projects.pm.common.util.excel.ExportExcel;
import com.tuisitang.projects.pm.common.web.BaseController;
import com.tuisitang.projects.pm.modules.shop.entity.Customer;
import com.tuisitang.projects.pm.modules.shop.service.CustomerService;
import com.tuisitang.projects.pm.modules.sys.entity.Area;
import com.tuisitang.projects.pm.modules.sys.service.AreaService;
import com.tuisitang.projects.pm.modules.sys.service.SystemService;
import com.tuisitang.projects.pm.modules.sys.utils.UserUtils;
import com.tuisitang.projects.pm.modules.wedding.entity.WeddingCompany;

/**
 * 客户Controller
 * @author ThinkGem
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/shop/customer")
public class CustomerController extends BaseController {

	@Autowired
	private CustomerService customerService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private SystemService systemService;

	@ModelAttribute
	public Customer get(@RequestParam(required = false) Long id) {
		if (id != null) {
			return customerService.getCustomer(id);
		} else {
			return new Customer();
		}
	}

	@RequiresPermissions("shop:customer:view")
	@RequestMapping(value = { "list", "" })
	public String list(Customer customer, HttpServletRequest request,
			HttpServletResponse response, Model model) {
		Page<Customer> page = customerService.findCustomer(new Page<Customer>(
				request, response), customer);
		model.addAttribute("provinces", UserUtils.getProvinceList());
		if (StringUtils.isNotEmpty(customer.getProvinceCode())) {
			Area province = areaService.get(customer.getProvinceCode());
			model.addAttribute("cities",
					areaService.findByParentId(province.getId()));
		}
		model.addAttribute("page", page);
		return "modules/shop/customerList";
	}

	@RequiresPermissions("shop:customer:view")
	@RequestMapping(value = "form")
	public String form(Customer customer, Model model) {
		model.addAttribute("customer", customer);
		List<Area> provinces = UserUtils.getProvinceList();
		model.addAttribute("provinces", provinces);
		if (StringUtils.isNotEmpty(customer.getProvinceCode())) {
			Area province = areaService.get(customer.getProvinceCode());
			model.addAttribute("cities", areaService.findByParentId(province.getId()));

			if (StringUtils.isNotEmpty(customer.getCityCode())) {
				Area city = areaService.get(customer.getCityCode());
				model.addAttribute("counties", areaService.findByParentId(city.getId()));
			}
		} else {
			if (!StringUtils.isBlank(customer.getMobile()) && ValidateUtils.isMobile(customer.getMobile())) {
				String url = "http://shouji.51240.com/" + customer.getMobile() + "__shouji/";
				try {
					Document doc = Jsoup.parse(new URL(url), 60 * 1000);
					Elements elements = doc.getElementsByClass("kuang_biaoge");
					if (elements != null && elements.size() > 0) {
						Element e = elements.get(0);
						String json = e.text();
						if (StringUtils.isNotBlank(json)) {
							// 号码 18284000566 归属地 四川 广元 卡类型 中国移动
							String[] ss = json.split(" ");
							String province = ss[3];
							String city = ss[4];
							logger.info("province {}, city {}", province, city);
							for (Area area : provinces) {
								if (area.getName().indexOf(province) >= 0) {
									customer.setProvince(area.getName());
									customer.setProvinceCode(area.getCode());

									List<Area> cities = areaService.findByParentId(area.getId());
									model.addAttribute("cities", cities);
									if (!cities.isEmpty()) {
										for (Area a : cities) {
											if (a.getName().indexOf(city) >= 0) {
												customer.setCity(a.getName());
												customer.setCityCode(a.getCode());
												model.addAttribute("counties", areaService.findByParentId(a.getId()));
											}
										}
									}
								}
							}
						}
						
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		model.addAttribute("salesmans", systemService.findUserByOfficeId(4L));
		return "modules/shop/customerForm";
	}

	@RequiresPermissions("shop:customer:edit")
	@RequestMapping(value = "save")
	public String save(Customer customer, Model model,
			RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + Global.getAdminPath() + "/shop/customer?repage";
		}
		if (!beanValidator(model, customer)) {
			return form(customer, model);
		}
		customerService.saveCustomer(customer);
		addMessage(redirectAttributes, "保存客户'" + customer.getName() + "'成功");
		return "redirect:" + Global.getAdminPath() + "/shop/customer?repage";
	}

	@RequiresPermissions("shop:customer:edit")
	@RequestMapping(value = "delete")
	public String delete(Long id, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + Global.getAdminPath()
					+ "/shop/customer?repage";
		}
		customerService.deleteCustomer(id);
		addMessage(redirectAttributes, "删除客户成功");
		return "redirect:" + Global.getAdminPath() + "/shop/customer?repage";
	}
	
	@RequiresPermissions("shop:customer:edit")
    @RequestMapping(value = "sync", method=RequestMethod.POST)
    public String sync(WeddingCompany weddingCompany, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			SchemaContextHolder.setSchema(SchemaContextHolder.SCHEMA_PRODUCTION);
			DataSource dataSource = SpringContextHolder.getBean("shopDataSource");
			final JdbcTemplate jdbc = new JdbcTemplate(dataSource);
			
			String sql = "SELECT a.*, pa.cardName as name, pa.cardNo as idNo, pa.authPicture as personAuthPicture, ea.cardName as company, ea.cardNo as enterpriseNo, ea.authPicture as enterpriseAuthPicture FROM t_account a left join t_authentication pa on pa.accountId = a.id and pa.type = 1 and pa.status <> 4 left join t_authentication ea on ea.accountId = a.id and ea.type = 2 and ea.status <> 4 ";
			final List<Customer> list = Lists.newArrayList();
			final List<String> mobiles = Lists.newArrayList();
			jdbc.query(sql, new RowMapper<Customer>() {

				@Override
				public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
					Long id = rs.getLong("id");
					Customer customer = new Customer();
					customer.setUid(id);
					customer.setMobile(rs.getString("mobile"));
					customer.setName(rs.getString("name"));
					customer.setCompany(rs.getString("company"));
					customer.setPassword(rs.getString("password"));
					customer.setEmail(rs.getString("email"));
					customer.setPicture(rs.getString("picture"));
					customer.setIdNo(rs.getString("idNo"));
					customer.setEnterpriseNo(rs.getString("enterpriseNo"));
					customer.setPersonAuthPicture(rs.getString("personAuthPicture"));
					customer.setEnterpriseAuthPicture(rs.getString("enterpriseAuthPicture"));
					
					customer.setAuthStatus(rs.getString("authStatus"));
					customer.setProvince(rs.getString("province"));
//					customer.setProvinceCode(rs.getString("provinceCode"));
					customer.setCity(rs.getString("city"));
//					customer.setCityCode(rs.getString("cityCode"));
					customer.setAddress(rs.getString("address"));
					customer.setRegistDate(rs.getDate("regeistDate"));
					customer.setLoginIp(rs.getString("lastLoginIp"));
					customer.setLoginDate(rs.getDate("lastLoginTime"));
					customer.setLoginArea(rs.getString("lastLoginArea"));
					
					mobiles.add(rs.getString("mobile"));
					list.add(customer);
					return customer;
				}
				
			});
			
			List<Customer> customers = customerService.findCustomer(mobiles);
			List<Customer> l = Lists.newArrayList();
			for (Customer c : list) {
				boolean exist = false;
				for (Customer customer : customers) {
					if (c.getMobile().equals(customer.getMobile())) {
						customer.setUid(c.getUid());
						customer.setMobile(c.getMobile());
						customer.setName(c.getName());
						customer.setCompany(c.getCompany());
						customer.setPassword(c.getPassword());
						customer.setEmail(c.getEmail());
						customer.setPicture(c.getPicture());
						customer.setIdNo(c.getIdNo());
						customer.setEnterpriseNo(c.getEnterpriseNo());
						customer.setPersonAuthPicture(c.getPersonAuthPicture());
						customer.setEnterpriseAuthPicture(c
								.getEnterpriseAuthPicture());
						customer.setAuthStatus(c.getAuthStatus());
						if (!StringUtils.isBlank(c.getProvince()))
							customer.setProvince(c.getProvince());
						if (!StringUtils.isBlank(c.getCity()))
							customer.setCity(c.getCity());
						customer.setAddress(c.getAddress());
						customer.setRegistDate(c.getRegistDate());
						customer.setLoginIp(c.getLoginIp());
						customer.setLoginDate(c.getLoginDate());
						customer.setLoginArea(c.getLoginArea());
						exist = true;
						l.add(customer);
					}
				}
				if (!exist) {
					l.add(c);
				}
			}
			
			logger.info("{}", l.size());
			customerService.saveCustomer(l);
		} catch (Exception e) {
			addMessage(redirectAttributes, "同步客户失败！失败信息："+e.getMessage());
		} catch (Throwable e) {
			addMessage(redirectAttributes, "同步客户失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/customer/?repage";
    }
	
	
	@RequiresPermissions("shop:customer:edit")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(WeddingCompany weddingCompany, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "客户数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx"; 
    		List<Customer> list = customerService.findAllCustomer();
    		new ExportExcel("客户数据", Customer.class).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出客户失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/shop/customer/?repage";
    }

}
