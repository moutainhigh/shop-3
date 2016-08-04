package com.tuisitang.common.utils;

import java.util.ArrayList;
import java.util.List;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rsf.ListItem;
import com.qiniu.api.rsf.ListPrefixRet;
import com.qiniu.api.rsf.RSFClient;
import com.qiniu.api.rsf.RSFEofException;

public class QiniuTest {
	
	public static void main(String[] args) throws Exception {
		ListPrefix l = new ListPrefix();
		// key:icon12.png hash:FsIlul1XpBDBFi501luw8hcYzNAV fsize:27692 putTime:14415881843923038 mimeType:image/png endUser:null

		String bucketName = "wedding-shop-test";
		l.list(bucketName, "");
	}
	
    public static void main1(String[] args) throws Exception {
        Config.ACCESS_KEY = "eYqZ9a8m_ROveL0r8asqzRD24GhQVdzM5AHwleSN";
        Config.SECRET_KEY = "MQV-JKhxpRIxC6laeAqjdKYfvuCC8PiAKBtKZN-p";
        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
        // 请确保该bucket已经存在
        String bucketName = "wedding-shop-test";
        PutPolicy putPolicy = new PutPolicy(bucketName);
        String uptoken = putPolicy.token(mac);
        PutExtra extra = new PutExtra();
        String key = "attached/images/test";
        String localFile = "G:\\工作文件\\350_350\\D-1-350-350.jpg";
        PutRet ret = IoApi.putFile(uptoken, key, localFile, extra);
        System.out.println(ret.getKey());
    }
}

class ListPrefix {

    public void list(String bucketName, String prifix) {
    	Config.ACCESS_KEY = "eYqZ9a8m_ROveL0r8asqzRD24GhQVdzM5AHwleSN";
        Config.SECRET_KEY = "MQV-JKhxpRIxC6laeAqjdKYfvuCC8PiAKBtKZN-p";
        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
        
        RSFClient client = new RSFClient(mac);
        String marker = "";
            
        List<ListItem> all = new ArrayList<ListItem>();
        ListPrefixRet ret = null;
        while (true) {
            ret = client.listPrifix(bucketName, prifix, marker, 10);
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
        
        for(ListItem li : all){
        	System.out.println(li);
        }
    }
}