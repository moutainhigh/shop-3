package com.tuisitang.modules.shop.entity;

/**    
 * @{#} AliyunOSS.java   
 *    
 *七牛OSS Entity  
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class QiniuOSS {
	private String ACCESS_KEY;
	private String SECRET_KEY;
	private String domain;
	private String bucketName;

	public String getACCESS_KEY() {
		return ACCESS_KEY;
	}

	public void setACCESS_KEY(String aCCESS_KEY) {
		ACCESS_KEY = aCCESS_KEY;
	}


	public String getBucketName() {
		return bucketName;
	}

	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public void clear() {
		ACCESS_KEY = null;
		SECRET_KEY = null;
		bucketName = null;
		domain = null;
	}

	public String getSECRET_KEY() {
		return SECRET_KEY;
	}

	public void setSECRET_KEY(String sECRET_KEY) {
		SECRET_KEY = sECRET_KEY;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

}
