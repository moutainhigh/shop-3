package com.tuisitang.projects.pm.modules.shop.web.api;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuisitang.common.utils.SpringContextHolder;
import com.tuisitang.projects.pm.common.web.BaseController;
import com.tuisitang.projects.pm.modules.shop.entity.Customer;
import com.tuisitang.projects.pm.modules.shop.event.CustomerBehaviorEvent;
import com.tuisitang.projects.pm.modules.shop.service.CustomerService;

@Controller
@RequestMapping(value = "/api/shop")
public class HomeApiController extends BaseController {
	
	@Autowired
	private CustomerService customerService;

	@RequestMapping(value = "push")
	public @ResponseBody Map<String, Object> push(HttpServletRequest request, HttpServletResponse response) {
		Long customerId = StringUtils.isBlank(request.getParameter("customerId")) ? null 
				: new Long(request.getParameter("customerId"));
		if (customerId == null) {
			return buildReturnMap(FAILURE, "客户信息为空");
		}
		String action = request.getParameter("action");
		if (StringUtils.isBlank(action)) {
			return buildReturnMap(FAILURE, "动作为空");
		}
		String actionDate = request.getParameter("actionDate");
		String actionDetail = request.getParameter("actionDetail");
		SpringContextHolder.publishEvent(new CustomerBehaviorEvent(this, customerId, action, actionDate, actionDetail));
		return buildReturnMap(SUCCESS, "");
	}
	

}
