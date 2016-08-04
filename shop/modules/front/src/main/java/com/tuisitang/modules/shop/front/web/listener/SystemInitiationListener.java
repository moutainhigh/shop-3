package com.tuisitang.modules.shop.front.web.listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;
import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.resumableio.ResumeableIoApi;
import com.qiniu.api.rs.BatchCallRet;
import com.qiniu.api.rs.EntryPath;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rs.RSClient;
import com.qiniu.api.rsf.ListItem;
import com.qiniu.api.rsf.ListPrefixRet;
import com.qiniu.api.rsf.RSFClient;
import com.qiniu.api.rsf.RSFEofException;
import com.tuisitang.common.bo.AdvertType;
import com.tuisitang.common.service.ServiceException;
import com.tuisitang.modules.shop.entity.Advert;
import com.tuisitang.modules.shop.entity.Catalog;
import com.tuisitang.modules.shop.entity.Hotquery;
import com.tuisitang.modules.shop.entity.Oss;
import com.tuisitang.modules.shop.entity.QiniuOSS;
import com.tuisitang.modules.shop.entity.Role;
import com.tuisitang.modules.shop.entity.SystemSetting;
import com.tuisitang.modules.shop.entity.User;
import com.tuisitang.modules.shop.front.service.ProductFrontService;
import com.tuisitang.modules.shop.front.service.SystemFrontService;
import com.tuisitang.modules.shop.service.CommonService;
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
		CommonService commonService = ctx.getBean(CommonService.class);
		SystemFrontService systemFrontService = ctx.getBean(SystemFrontService.class);
		SystemSetting systemSetting = commonService.getSystemSetting();
		logger.info("SystemSetting {}", systemSetting);
		sc.setAttribute(Global.SYSTEM_SETTING, systemSetting);
		ProductFrontService productFrontService = ctx.getBean(ProductFrontService.class);
		{
			List<Catalog> list = productFrontService.getAllCatalog(Catalog.catalog_type_p);
			sc.setAttribute(Global.PRODUCT_CATALOG_LIST, list);
		}
//		Oss oss = commonService.getOss(Oss.code_aliyun);
		Oss oss = commonService.getOss(Oss.code_qiniu);
		logger.info("OSS {}", oss);
		if (oss == null || StringUtils.isBlank(oss.getOssJsonInfo())) {
			logger.error("OSS配置为空");;
			throw new ServiceException("OSS配置为空");
		}
//		AliyunOSS aliyunOSS = JSON.parseObject(oss.getOssJsonInfo(), AliyunOSS.class);
//		logger.info("AliyunOSS {}", aliyunOSS);
//		sc.setAttribute(Global.ALIYUN_OSS_CONFIG, aliyunOSS);
		
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
		
		if (false) {
			String version = "v1";
			Mac mac = new Mac(qiniuOSS.getACCESS_KEY(), qiniuOSS.getSECRET_KEY());
			String bucketName = "ll-wedding-shop-resource";
			
			{
				RSFClient client = new RSFClient(mac);
				String marker = "";
	
				List<ListItem> all = new ArrayList<ListItem>();
				ListPrefixRet ret = null;
				while (true) {
					ret = client.listPrifix(bucketName, "", marker, 10);
					marker = ret.marker;
					all.addAll(ret.results);
					if (!ret.ok()) {
						// no more items or error occurs
						break;
					}
				}
				if (ret.exception.getClass() != RSFEofException.class) {
					// error handler
				}
	
				RSClient rs = new RSClient(mac);
				List<EntryPath> entries = new ArrayList<EntryPath>();
	
				for (ListItem li : all) {
					EntryPath e = new EntryPath();
					e.bucket = bucketName;
					e.key = li.key;
					entries.add(e);
				}
				BatchCallRet bret = rs.batchDelete(entries);
			}
			
			// 同步静态资源 static assets
			{
				String basePath = sce.getServletContext().getRealPath("/static");
				logger.info("basePath {}", basePath);
				File file = new File(basePath);
				File[] fs = file.listFiles();
	
				for (File f : fs) {
					String prefix = "static/" + version + "/" + f.getName() + "/";
					uploadFile(bucketName, f, prefix, mac);
				}
	
			}
			{
				String basePath = sce.getServletContext().getRealPath("/assets");
				logger.info("basePath {}", basePath);
				File file = new File(basePath);
				File[] fs = file.listFiles();
	
				for (File f : fs) {
					String prefix = "assets/" + version + "/" + f.getName() + "/";
					uploadFile(bucketName, f, prefix, mac);
				}
			}
			
			{
				String basePath = sce.getServletContext().getRealPath("/ms");
				logger.info("basePath {}", basePath);
				File file = new File(basePath);
				File[] fs = file.listFiles();
	
				for (File f : fs) {
					String prefix = "ms/" + version + "/" + f.getName() + "/";
					uploadFile(bucketName, f, prefix, mac);
				}
			}
		
		}
		logger.info("System Initiation End");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
	
	private void uploadFile(String bucketName, File file, String prefix, Mac mac) {
		try {
			String mimeType = "image/jpeg";
			if (file.isDirectory()) {
				File[] fs = file.listFiles(new FilenameFilter() {

					@Override
					public boolean accept(File dir, String name) {
						return name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg")
							|| name.toLowerCase().endsWith(".gif") || name.toLowerCase().endsWith(".css");
					}

				});

				for (File f : fs) {
					if (f.isDirectory())
						continue;
					if (f.getName().toLowerCase().endsWith(".png")) {
						mimeType = "image/png";
					} else if (f.getName().toLowerCase().endsWith(".jpg")) {
						mimeType = "image/jpeg";
					} else if (f.getName().toLowerCase().endsWith(".gif")) {
						mimeType = "image/gif";
					} else if (f.getName().toLowerCase().endsWith(".css")) {
						mimeType = "text/css";
					} else if (f.getName().toLowerCase().endsWith(".js")) {
						mimeType = "text/javascript";
					}

					PutPolicy p = new PutPolicy(bucketName);
					String upToken = p.token(mac);
					FileInputStream fis = new FileInputStream(f);
					String key = prefix + f.getName();
					PutRet ret = ResumeableIoApi.put(fis, upToken, key, mimeType);
				}
			}

		} catch (AuthException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
