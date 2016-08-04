package com.tuisitang.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

/**    
 * @{#} OSSKit.java  
 * 
 * 阿里云oss工具类
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class OSSKit {
	
	/**
	 * 1. 获得OSSClient
	 * 
	 * @param endpoint
	 * @param accessKeyId
	 * @param accessKeySecret
	 * @return
	 */
	public static OSSClient getOSSClient(String endpoint, String accessKeyId, String accessKeySecret) {
		ClientConfiguration config = new ClientConfiguration();
		return new OSSClient(endpoint, accessKeyId, accessKeySecret, config);
	}
	
	

	public static void main(String[] args) throws Exception {
		String accessKeyId = "ixKUt71RhBgBi9BN";
		String endpoint = "http://oss.aliyuncs.com/";
		String accessKeySecret = "0ZPAIKhaiYd2J5tjv7oeaHpLEqGlEf";
		String bucketName = "wedding-shop";
		// 可以使用ClientConfiguration对象设置代理服务器、最大重试次数等参数。
		ClientConfiguration config = new ClientConfiguration();
		OSSClient client = new OSSClient(endpoint, accessKeyId,
				accessKeySecret, config);
		client.createBucket(bucketName);
		client.setBucketAcl(bucketName, CannedAccessControlList.PublicReadWrite);
		// 获取指定bucket下的所有Object信息
		ObjectListing listing = client.listObjects(bucketName);

		// 遍历所有Object
		for (OSSObjectSummary objectSummary : listing.getObjectSummaries()) {
			System.out.println(objectSummary.getKey());
			// 获取Object，返回结果为OSSObject对象
		    OSSObject object = client.getObject(bucketName, objectSummary.getKey());

		    // 获取Object的输入流
		    InputStream objectContent = object.getObjectContent();
		    // 处理Object

		    // 关闭流
		    objectContent.close();
		}
		
		String filePath = "/Users/xubin/Documents/170*170.jpg";
		String key = "attached/image/" + Identities.uuid2() + ".jpg";
		File file = new File(filePath);
	    InputStream content = new FileInputStream(file);

	    // 创建上传Object的Metadata
	    ObjectMetadata meta = new ObjectMetadata();

	    // 必须设置ContentLength
	    meta.setContentLength(file.length());

	    // 上传Object.
	    PutObjectResult result = client.putObject(bucketName, key, content, meta);

	    // 打印ETag
	    System.out.println(result.getETag());
	}

}
