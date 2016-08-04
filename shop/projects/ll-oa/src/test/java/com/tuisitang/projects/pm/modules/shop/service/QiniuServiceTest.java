package com.tuisitang.projects.pm.modules.shop.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.rs.BatchCallRet;
import com.qiniu.api.rs.EntryPath;
import com.qiniu.api.rs.RSClient;
import com.qiniu.api.rsf.ListItem;
import com.qiniu.api.rsf.ListPrefixRet;
import com.qiniu.api.rsf.RSFClient;
import com.qiniu.api.rsf.RSFEofException;
import com.tuisitang.modules.shop.utils.Global;

public class QiniuServiceTest {

	@Test
	public void batchDelete() {
		Mac mac = new Mac(Global.getQiniuAccessKey(), Global.getQiniuSecretKey());
		String bucketName = Global.getQiniuBucketName();
		
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
	}
}
