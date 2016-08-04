package com.tuisitang.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.gson.bean.Attachment;
import com.gson.util.HttpKit;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.rs.GetPolicy;
import com.qiniu.api.rs.URLUtils;
import com.qiniu.api.rsf.ListItem;
import com.qiniu.api.rsf.ListPrefixRet;
import com.qiniu.api.rsf.RSFClient;
import com.qiniu.api.rsf.RSFEofException;

public class QiniuImageMoveTest {

	public static void main(String[] args) throws Exception {
		
		String basePath = "/Users/xubin/Tools/qiniu/resources/ll-oa/";
		
		Config.ACCESS_KEY = "eYqZ9a8m_ROveL0r8asqzRD24GhQVdzM5AHwleSN";
        Config.SECRET_KEY = "MQV-JKhxpRIxC6laeAqjdKYfvuCC8PiAKBtKZN-p";
        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
        String bucketName = "ll-oa";
        
        RSFClient client = new RSFClient(mac);
        String marker = "";
            
        List<ListItem> list = new ArrayList<ListItem>();
        ListPrefixRet ret = null;
        while (true) {
            ret = client.listPrifix(bucketName, "", marker, 10);
            marker = ret.marker;
            list.addAll(ret.results);
            if (!ret.ok()) {
                // no more items or error occurs
                break;
            }
        }
        if (ret.exception.getClass() != RSFEofException.class) {
            // error handler
        } 
        System.out.println(list.size());
		for (ListItem li : list) {
			try {
				String key = li.key;
				if (new File(basePath, key).exists()) {
					continue;
				}
				System.out.println(key);
				String dir = key.substring(0, key.lastIndexOf("/"));
				String name = key.substring(key.lastIndexOf("/") + 1);
				System.out.println(dir);
				System.out.println(name);
	
				File file = new File(basePath + dir);
				if (!file.exists()) {
					file.mkdirs();
				}
	
				FileOutputStream fos = new FileOutputStream(new File(basePath, key));
				String baseUrl = URLUtils.makeBaseUrl(
						"7xlmuf.com2.z0.glb.qiniucdn.com", key);
				GetPolicy getPolicy = new GetPolicy();
				String downloadUrl = getPolicy.makeRequest(baseUrl, mac);
	
				System.out.println(downloadUrl);
			
				Attachment attachment = HttpKit.download(downloadUrl);
				BufferedInputStream fis = attachment.getFileStream();
				byte b[] = new byte[1024];
				while (fis.read(b) != -1) {
					fos.write(b);
				}
				fis.close();
				fos.close();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
        
    }

}
