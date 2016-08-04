package com.tuisitang.modules.shop.api.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.tuisitang.common.mapper.JsonMapper;
import com.tuisitang.modules.shop.entity.Area;
import com.tuisitang.modules.shop.service.CommonService;

/**    
 * @{#} DemoApiController.java   
 * 
 * Demo Api Controller
 * 
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: irtone</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 *  
 */
@Controller
@RequestMapping(value = "/api/demo")
public class DemoApiController extends BaseApiController {
	@Autowired
	private CommonService commonService;
	/**
	 * 1. test
	 */
	@RequestMapping(value = "test")
	public @ResponseBody String test(@RequestParam(required = false) String callback, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,Object> returnMap = Maps.newHashMap();
		JsonMapper mapper = JsonMapper.getInstance();
		List<Area> privinces = commonService.findProvinces();
		returnMap.put("privinces", privinces);
		returnMap.put("s", "0");
		if (StringUtils.isBlank(callback)) {
			return mapper.toJson(returnMap);
		} else {
			return mapper.toJsonp(callback, returnMap);
		}
	}
	
}
