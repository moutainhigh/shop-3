package com.tuisitang.modules.shop.admin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;

public class ResourceTest {
	
	private static final Logger logger = LoggerFactory.getLogger(ResourceTest.class);

	/**
	 * 
2015-07-29 14:02:15,623 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/artDialog4.1.5/artDialog.js
2015-07-29 14:02:15,624 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/artDialog4.1.5/skins/chrome.css
2015-07-29 14:02:15,624 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/artDialog5.0/source/iframeTools.source.js
2015-07-29 14:02:15,624 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/bootstrap/js/bootstrap.min.js
2015-07-29 14:02:15,624 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/bootstrap3.0.0/js/bootstrap.min.js
2015-07-29 14:02:15,624 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/css/base.css
2015-07-29 14:02:15,624 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/jquery-easyui-1.3.4/demo/demo.css
2015-07-29 14:02:15,624 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/jquery-easyui-1.3.4/jquery.easyui.min.js
2015-07-29 14:02:15,624 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/jquery-easyui-1.3.4/themes/default/easyui.css
2015-07-29 14:02:15,624 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/jquery-easyui-1.3.4/themes/icon.css
2015-07-29 14:02:15,624 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/js/jquery-1.9.1.min.js
2015-07-29 14:02:15,625 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/jquery-jquery-ui/themes/base/jquery.ui.all.css
2015-07-29 14:02:15,625 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/jquery-jquery-ui/ui/jquery.ui.core.js
2015-07-29 14:02:15,625 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/jquery-jquery-ui/ui/jquery.ui.tabs.js
2015-07-29 14:02:15,625 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/jquery-jquery-ui/ui/jquery.ui.widget.js
2015-07-29 14:02:15,625 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/js/jquery-1.9.1.min.js
2015-07-29 14:02:15,625 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/js/jquery-1.9.1.min.js
2015-07-29 14:02:15,625 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/js/jquery-1.9.1.min.js
2015-07-29 14:02:15,625 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/js/jquery.blockUI.js
2015-07-29 14:02:15,625 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/js/jquery.lazyload.min.js
2015-07-29 14:02:15,625 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/js/manage.js
2015-07-29 14:02:15,625 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/js/superMenu/js/new.js
2015-07-29 14:02:15,625 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/kindeditor-4.1.7/kindeditor-all.js
2015-07-29 14:02:15,625 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/kindeditor-4.1.7/kindeditor-min.js
2015-07-29 14:02:15,625 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/kindeditor-4.1.7/lang/zh_CN.js
2015-07-29 14:02:15,625 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/kindeditor-4.1.7/themes/default/default.css
2015-07-29 14:02:15,626 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/select2/select2.css
2015-07-29 14:02:15,626 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/select2/select2.js
2015-07-29 14:02:15,626 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/select2/select2_locale_zh-CN.js
2015-07-29 14:02:15,626 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/uploadify/jquery.uploadify.min.js
2015-07-29 14:02:15,626 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/zTree3.1/css/zTreeStyle/zTreeStyle.css
2015-07-29 14:02:15,626 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/zTree3.1/js/jquery.ztree.all-3.1.min.js
2015-07-29 14:02:15,626 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/zTree3.1/js/jquery.ztree.core-3.1.js
2015-07-29 14:02:15,626 [main] INFO  [com.tuisitang.modules.shop.admin.ResourceTest] - /resource/zTree3.1/js/jquery.ztree.excheck-3.1.js

	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String path = "/Users/xubin/Desktop/FindResults.txt";

		File file = new File(path);
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(file)));
		Set<String> set = Sets.newHashSet();
		String line = null;
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			if (line.indexOf(".jsp") < 0) {
				if (line.indexOf(":") >= 0) {
					line = line.substring(line.indexOf(":") + 1).trim();
					if (!line.startsWith("<%--")
					&& !line.startsWith("//")
					&& line.indexOf("/resource/") >= 0
					&& (line.startsWith("<script") || line.startsWith("<link")))
						if (line.indexOf("%>") >= 0) {
							line = line.substring(line.indexOf("%>") + 2);
							line = line.substring(0, line.indexOf("\""));
//							logger.info("{}", line);
							set.add(line);
						}
				}
			}
		}
		reader.close();
		
		List<String> list = new ArrayList<String>(set);
		Collections.sort(list);
		
		for(String s : list){
//			logger.info("{}", s);
			System.out.println(s);
		}
	}

}
