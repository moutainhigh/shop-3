package com.tuisitang.modules.shop.admin.web.listener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.jeeshop.core.KeyValueHelper;
import net.jeeshop.core.TaskManager;
import net.jeeshop.core.front.SystemManager;
import net.jeeshop.core.oscache.ManageCache;
import net.spy.memcached.MemcachedClient;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.alibaba.fastjson.JSON;
import com.tuisitang.common.bo.AdvertType;
import com.tuisitang.common.cache.memcached.SpyMemcachedClient;
import com.tuisitang.common.service.ServiceException;
import com.tuisitang.modules.shop.entity.Advert;
import com.tuisitang.modules.shop.entity.AliyunOSS;
import com.tuisitang.modules.shop.entity.Area;
import com.tuisitang.modules.shop.entity.Catalog;
import com.tuisitang.modules.shop.entity.Hotquery;
import com.tuisitang.modules.shop.entity.Keyvalue;
import com.tuisitang.modules.shop.entity.Oss;
import com.tuisitang.modules.shop.entity.QiniuOSS;
import com.tuisitang.modules.shop.entity.SystemSetting;
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
		
		try {
			logger.info("System Initiation Begin");
			ServletContext sc = sce.getServletContext();
			WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
	//		sc.setAttribute(Global.IMAGE_ROOT_PATH, Global.getImageRootPath());
			
			SystemManager.getInstance();
			ManageCache manageCache = ctx.getBean(ManageCache.class);
			manageCache.loadAllCache();
			TaskManager taskManager = ctx.getBean(TaskManager.class);
			taskManager.start();
			
			CommonService commonService = ctx.getBean(CommonService.class);
			SystemService systemFrontService = ctx.getBean(SystemService.class);
			SpyMemcachedClient spyMemcachedClient = ctx.getBean(SpyMemcachedClient.class);
			MemcachedClient memcachedClient = spyMemcachedClient.getMemcachedClient();
			Map<SocketAddress, Map<String, String>> map = memcachedClient.getStats();
			for(Map.Entry<SocketAddress, Map<String, String>>entry : map.entrySet()){
				logger.info("{} \n {}", entry.getKey().getClass(), entry.getValue());
				InetSocketAddress isa = (InetSocketAddress)entry.getKey();
				String[] keys = allkeys(isa.getHostName(), isa.getPort()).split("\n");
		        Arrays.sort(keys);
		        for(String s : keys){
		            logger.info("key {}", s);
	//	            memcachedClient.delete(s);
		        }
			}
			
			SystemSetting systemSetting = commonService.getSystemSetting();
			logger.info("SystemSetting {}", systemSetting);
			sc.setAttribute(Global.SYSTEM_SETTING, systemSetting);
			ProductService productFrontService = ctx.getBean(ProductService.class);
			{
				List<Catalog> list = productFrontService.getAllCatalog(Catalog.catalog_type_p);
				sc.setAttribute(Global.PRODUCT_CATALOG_LIST, list);
			}
			{
				Oss oss = commonService.getOss(Oss.code_aliyun);
				logger.info("OSS {}", oss);
				if (oss == null || StringUtils.isBlank(oss.getOssJsonInfo())) {
					logger.error("OSS配置为空");;
					throw new ServiceException("OSS配置为空");
				}
				AliyunOSS aliyunOSS = JSON.parseObject(oss.getOssJsonInfo(), AliyunOSS.class);
				logger.info("AliyunOSS {}", aliyunOSS);
				sc.setAttribute(Global.ALIYUN_OSS_CONFIG, aliyunOSS);
				SystemManager.aliyunOSS = aliyunOSS;
			}
			
			{
				Oss oss = commonService.getOss(Oss.code_qiniu);
				logger.info("OSS {}", oss);
				if (oss == null || StringUtils.isBlank(oss.getOssJsonInfo())) {
					logger.error("OSS配置为空");;
					throw new ServiceException("OSS配置为空");
				}
				
				QiniuOSS qiniuOSS = JSON.parseObject(oss.getOssJsonInfo(), QiniuOSS.class);
				logger.info("qiniuOSS {}", qiniuOSS);
				sc.setAttribute(Global.QINIU_OSS_CONFIG, qiniuOSS);
				sc.setAttribute(Global.IMAGE_ROOT_PATH, qiniuOSS.getDomain());
			}
			
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
			
			List<Area> provinces = commonService.findProvinces();
			sc.setAttribute(Global.AREA_PROVINCE_ALL, provinces);
			
			{
				List<Keyvalue> keyvalues = systemFrontService.findKeyvalue();
				KeyValueHelper.load(keyvalues);
			}
			
			logger.info("System Initiation End");
		} catch (Throwable e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	public static String allkeys(String host, int port) {
		StringBuffer r = new StringBuffer();
		try {
			Socket socket = new Socket(host, port);
			PrintWriter os = new PrintWriter(socket.getOutputStream());
			BufferedReader is = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			os.println("stats items");
			os.flush();
			String l;
			while (!(l = is.readLine()).equals("END")) {
				r.append(l).append("\n");
			}
			String rr = r.toString();
			Set<String> ids = new HashSet<String>();
			if (rr.length() > 0) {
				r = new StringBuffer();// items
				rr.replace("STAT items", "");
				for (String s : rr.split("\n")) {
					ids.add(s.split(":")[1]);
				}
				if (ids.size() > 0) {
					r = new StringBuffer();//
					for (String s : ids) {
						os.println("stats cachedump " + s + " 0");
						os.flush();
						while (!(l = is.readLine()).equals("END")) {
							r.append(l.split(" ")[1]).append("\n");
						}
					}
				}
			}
			os.close();
			is.close();
			socket.close();
		} catch (Exception e) {
			System.out.println("Error" + e);
		}
		return r.toString();
	}
	
	public static String telnet(String host, int port, String cmd) {
		StringBuffer r = new StringBuffer();
		try {
			Socket socket = new Socket(host, port);
			PrintWriter os = new PrintWriter(socket.getOutputStream());
			BufferedReader is = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			os.println(cmd);
			os.flush();
			String l;
			while (!(l = is.readLine()).equals("END")) {
				r.append(l).append("\n");
			}
			os.close();
			is.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{}", e.getMessage());
		}
		return r.toString();
	}
}
