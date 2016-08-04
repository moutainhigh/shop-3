package com.tuisitang.modules.shop.api.web.listener;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;
import com.tuisitang.common.bo.AdvertType;
import com.tuisitang.common.service.ServiceException;
import com.tuisitang.modules.shop.entity.Advert;
import com.tuisitang.modules.shop.entity.AliyunOSS;
import com.tuisitang.modules.shop.entity.Catalog;
import com.tuisitang.modules.shop.entity.Hotquery;
import com.tuisitang.modules.shop.entity.Oss;
import com.tuisitang.modules.shop.entity.QiniuOSS;
import com.tuisitang.modules.shop.entity.Role;
import com.tuisitang.modules.shop.entity.SystemSetting;
import com.tuisitang.modules.shop.entity.User;
import com.tuisitang.modules.shop.service.CommonService;
import com.tuisitang.modules.shop.service.ProductService;
import com.tuisitang.modules.shop.service.SystemService;
import com.tuisitang.modules.shop.utils.Global;

/**    
 * @{#} SystemInitiationListener.java   
 * 
 * 系统初始化侦听
 *
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class SystemInitiationListener implements ServletContextListener {
	
	private static final Logger logger = LoggerFactory.getLogger(SystemInitiationListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		logger.info("System Initiation Begin");
		ServletContext sc = sce.getServletContext();
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
//		sc.setAttribute(Global.IMAGE_ROOT_PATH, Global.getImageRootPath());
		CommonService commonService = ctx.getBean(CommonService.class);
		SystemService systemFrontService = ctx.getBean(SystemService.class);
		SystemSetting systemSetting = commonService.getSystemSetting();
		logger.info("SystemSetting {}", systemSetting);
		sc.setAttribute(Global.SYSTEM_SETTING, systemSetting);
		ProductService productFrontService = ctx.getBean(ProductService.class);
		{
			List<Catalog> list = productFrontService.getAllCatalog(Catalog.catalog_type_p);
			sc.setAttribute(Global.PRODUCT_CATALOG_LIST, list);
		}
//		Oss oss = commonService.getOss(Oss.code_aliyun);
//		logger.info("OSS {}", oss);
//		if (oss == null || StringUtils.isBlank(oss.getOssJsonInfo())) {
//			logger.error("OSS配置为空");;
//			throw new ServiceException("OSS配置为空");
//		}
//		AliyunOSS aliyunOSS = JSON.parseObject(oss.getOssJsonInfo(), AliyunOSS.class);
//		logger.info("AliyunOSS {}", aliyunOSS);
//		sc.setAttribute(Global.ALIYUN_OSS_CONFIG, aliyunOSS);
		
		Oss oss = commonService.getOss(Oss.code_qiniu);
		logger.info("OSS {}", oss);
		if (oss == null || StringUtils.isBlank(oss.getOssJsonInfo())) {
			logger.error("OSS配置为空");;
			throw new ServiceException("OSS配置为空");
		}
		
		QiniuOSS qiniuOSS = JSON.parseObject(oss.getOssJsonInfo(), QiniuOSS.class);
		logger.info("qiniuOSS {}", qiniuOSS);
		sc.setAttribute(Global.QINIU_OSS_CONFIG, qiniuOSS);
		sc.setAttribute(Global.IMAGE_ROOT_PATH, qiniuOSS.getDomain() + "/");
		
		Advert advert = systemFrontService.getAdvert(AdvertType.IndexTop.getType());// 1. 加载顶部广告
		if (advert != null && "y".equals(advert.getStatus())) {
			sc.setAttribute(Global.INDEX_TOP_AD, advert);
		}
		
		{
			List<Hotquery> list = systemFrontService.findHotquery(Hotquery.TYPE_HOTQUERY);// 2.1 hotqueryList
			if (list != null) {
				sc.setAttribute(Global.HOTQUERY_LIST, list);
			}
		}
		{
			List<Hotquery> list = systemFrontService.findHotquery(Hotquery.TYPE_SEARCHLIST);// 2.2 searchList
			if (list != null) {
				sc.setAttribute(Global.SEARCH_LIST, list);
			}
		}
		List<User> userList = systemFrontService.findUser();
		for (User user : userList) {
			logger.info("user {}", user);
		}
		List<Role> roleList = systemFrontService.findRole();
		for (Role role : roleList) {
			logger.info("role {}", role);
		}
		logger.info("System Initiation End");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

}
