package com.tuisitang.modules.shop.front.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tuisitang.modules.shop.entity.Area;
import com.tuisitang.modules.shop.service.CommonService;

/**    
 * @{#} CartController.java   
 * 
 * 购物车Controller
 * 
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {

	@Autowired
	private CommonService commonService;
	
	@RequestMapping(value = { "findCities" }, method = RequestMethod.POST)
	public @ResponseBody
	List<Area> findCities(Long provinceId, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("findCities provinceId {}", provinceId);
		return commonService.findCities(provinceId);
	}
	
	@RequestMapping(value = { "findCounties" }, method = RequestMethod.POST)
	public @ResponseBody
	List<Area> findCounties(Long cityId, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("findCounties cityId {}", cityId);
		return commonService.findCounties(cityId);
	}
}
