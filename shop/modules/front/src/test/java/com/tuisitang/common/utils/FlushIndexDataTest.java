package com.tuisitang.common.utils;

import java.io.File;

import org.json.JSONException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;
import com.tuisitang.common.test.SpringTransactionalContextTests;
import com.tuisitang.modules.shop.dao.IndexFloorDao;
import com.tuisitang.modules.shop.entity.IndexFloor;

public class FlushIndexDataTest extends SpringTransactionalContextTests {
	@Autowired
	private IndexFloorDao indexFloorDao;
	
	@Test
	public void flush() {
		 String path="G:\\工作文件\\首页图\\0911完成";
		 File file=new File(path);
		 File[] tempList = file.listFiles();
		 System.out.println("该目录下对象个数："+tempList.length);
		 for (int i = 0; i < tempList.length; i++) {
			 if (tempList[i].isFile()) {
				 System.out.println("文     件："+tempList[i]);
				 File tempFile = tempList[i];
				 String name = tempFile.getName();
				 if (name.startsWith("1")) {
					dealFloor(tempFile, "1", "n");
				 }else if (name.startsWith("banner1")){
					 dealFloor(tempFile, "1", "y");
				 }else if (name.startsWith("2")) {
					 dealFloor(tempFile, "2", "n");
				 }else if (name.startsWith("banner2")){
					 dealFloor(tempFile, "2", "y");
				 }else if (name.startsWith("3")) {
					 dealFloor(tempFile, "3", "n");
				 }else if (name.startsWith("banner3")){
					 dealFloor(tempFile, "3", "y");
				 }else if (name.startsWith("4")) {
					 dealFloor(tempFile, "4", "n");
				 }else if (name.startsWith("banner4")){
					 dealFloor(tempFile, "4", "y");
				 }else if (name.startsWith("5")) {
					 dealFloor(tempFile, "5", "n");
				 }else if (name.startsWith("banner5")){
					 dealFloor(tempFile, "5", "y");
				 }
			 }
		 }
	}
	public void dealFloor(File file, String f, String banner) {
		 IndexFloor floor = new IndexFloor();
		 floor.setTitle(file.getName());
		 floor.setFloor(f);
		 floor.setBanner(banner);
		 floor.setSort(0);
		 
	 	String key = "indexProd/images/" + file.getName();
	 	floor.setPicture(key);
		
		Config.ACCESS_KEY = "eYqZ9a8m_ROveL0r8asqzRD24GhQVdzM5AHwleSN";
        Config.SECRET_KEY = "MQV-JKhxpRIxC6laeAqjdKYfvuCC8PiAKBtKZN-p";
        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);

        PutPolicy putPolicy = new PutPolicy("ll-oa");
        String uptoken = null;
		try {
			uptoken = putPolicy.token(mac);
		} catch (AuthException e) {
		} catch (JSONException e) {
		}
        PutExtra extra = new PutExtra();
        PutRet ret = IoApi.putFile(uptoken, key, file, extra);
        indexFloorDao.insert(floor);
	}
}
