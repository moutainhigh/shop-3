package com.tuisitang.modules.shop.front.web;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tuisitang.common.bo.ErrorMsg;
import com.tuisitang.common.bo.Status;
import com.tuisitang.common.utils.SpringContextHolder;
import com.tuisitang.modules.shop.entity.Catalog;
import com.tuisitang.modules.shop.entity.SystemSetting;
import com.tuisitang.modules.shop.service.CommonService;
import com.tuisitang.modules.shop.utils.Global;

/**    
 * @{#} BaseController.java   
 * 
 * 前台基础Controller
 * 
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public abstract class BaseController {
	@Autowired
	protected CommonService commonService;
	@Autowired  
	private  HttpServletRequest request;
	
	protected java.text.DecimalFormat  df = new java.text.DecimalFormat("#0.00");  

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	private static String[] parsePatterns = { "yyyy-MM-dd",
			"yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy/MM/dd",
			"yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm" };

	/**
	 * 添加Model消息
	 * 
	 * @param message
	 */
	protected void addMessage(Model model, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages) {
			sb.append(message).append(messages.length > 1 ? "<br/>" : "");
		}
		model.addAttribute("message", sb.toString());
	}

	/**
	 * 添加Flash消息
	 * 
	 * @param message
	 */
	protected void addMessage(RedirectAttributes redirectAttributes,
			String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages) {
			sb.append(message).append(messages.length > 1 ? "<br/>" : "");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
	}

	/**
	 * 初始化数据绑定 1. 将所有传递进来的String进行HTML编码，防止XSS攻击 2. 将字段中Date类型转换为String类型
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		// String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(text == null ? null : StringEscapeUtils
						.escapeHtml4(text.trim()));
			}

			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(parseDate(text));
			}
		});
	}

	public static Date parseDate(Object str) {
		if (str == null) {
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date parseDate(String str, String[] parsePatterns)
			throws ParseException {
		if (str == null || parsePatterns == null) {
			throw new IllegalArgumentException(
					"Date and Patterns must not be null");
		}

		SimpleDateFormat parser = null;
		ParsePosition pos = new ParsePosition(0);
		for (int i = 0; i < parsePatterns.length; i++) {
			if (i == 0) {
				parser = new SimpleDateFormat(parsePatterns[0]);
			} else {
				parser.applyPattern(parsePatterns[i]);
			}
			pos.setIndex(0);
			Date date = parser.parse(str, pos);
			if (date != null && pos.getIndex() == str.length()) {
				return date;
			}
		}
		throw new ParseException("Unable to parse the date: " + str, -1);
	}
	
	/**
	 * 创建返回对象
	 * s status
	 * c errCode
	 * m errMsg
	 * 
	 * @param status
	 * @param msg
	 * @return
	 */
	protected Map<String, Object> buildReturnMap(Status status, ErrorMsg msg) {
		Map<String, Object> returnMap = Maps.newHashMap();
		returnMap.put("s", status.getStatus());
		returnMap.put("c", msg.getErrCode());
		returnMap.put("m", msg.getErrMsg());
		return returnMap;
	}
	
	/**
	 * 创建返回对象
	 * 
	 * @param status
	 * @param msg
	 * @return
	 */
	protected Map<String, Object> buildReturnMap(Status status, String msg) {
		Map<String, Object> returnMap = Maps.newHashMap();
		returnMap.put("s", status.getStatus());
		returnMap.put("m", msg);
		return returnMap;
	}
	
	/**
	 * 创建返回对象
	 * 
	 * @param status
	 * @param msgs
	 * @return
	 */
	protected static Map<String, Object> buildReturnMap(Status status, List<ErrorMsg> msgs) {
		Map<String, Object> returnMap = Maps.newHashMap();
		returnMap.put("s", status.getStatus());
		List<Map<Integer, String>> ms = Lists.newArrayList();
		for (ErrorMsg msg : msgs) {
			Map<Integer, String> m = Maps.newHashMap();
			m.put(msg.getErrCode(), msg.getErrMsg());
			ms.add(m);
		}
		returnMap.put("ms", ms);
		return returnMap;
	}
	
	/**
	 * 创建返回对象
	 * 
	 * @param status
	 * @param msg
	 * @param data 返回对象
	 * @return
	 */
	protected Map<String, Object> buildReturnMap(Status status, String msg, Map<String, Object> data) {
		Map<String, Object> returnMap = Maps.newHashMap();
		returnMap.put("s", status.getStatus());
		returnMap.put("m", msg);
		returnMap.put("d", data);
		return returnMap;
	}

	/**
	 * 获取系统设定
	 * @param args
	 */
	public SystemSetting getSystemSetting(){
		return commonService.getSystemSetting();
	}
	
	/**
	 * 从上下文获取对象
	 * @param key
	 * @return
	 */
	public Object getContextObject(String key) {
		return getContext().getAttribute(key);
	}
	
	private ServletContext getContext() {
		return request.getSession().getServletContext();
	}
	
	@SuppressWarnings("unchecked")
	public List<Catalog> getCatalogCacheList() {
		return (List<Catalog>) getContextObject(Global.PRODUCT_CATALOG_LIST);
	}
	
	
	/**
	 * 发布事件异步操作
	 */
	public void publishEvent(ApplicationEvent event) {
		SpringContextHolder.getApplicationContext().publishEvent(event);
	}
}