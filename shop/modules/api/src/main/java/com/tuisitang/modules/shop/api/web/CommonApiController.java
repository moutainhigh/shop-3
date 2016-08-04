package com.tuisitang.modules.shop.api.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Maps;
import com.tuisitang.common.mapper.JsonMapper;

/**    
 * @{#} CommonApiController.java   
 * 
 * 通用 Api Controller
 * 
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: irtone</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 *  
 */
@Controller
@RequestMapping(value = "/api/common")
public class CommonApiController extends BaseApiController {

	/**
	 * 1. test
	 */
	@RequestMapping(value = "test")
	public @ResponseBody String test(@RequestParam(required = false) String callback, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String,Object> returnMap = Maps.newHashMap();
		JsonMapper mapper = JsonMapper.getInstance();
		returnMap.put("s", "0");
		if (StringUtils.isBlank(callback)) {
			return mapper.toJson(returnMap);
		} else {
			return mapper.toJsonp(callback, returnMap);
		}
	}
	
}
