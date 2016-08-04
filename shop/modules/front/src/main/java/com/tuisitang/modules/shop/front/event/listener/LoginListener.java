package com.tuisitang.modules.shop.front.event.listener;

import java.io.File;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSException;
import com.gson.util.HttpKit;
import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.rs.PutPolicy;
import com.tuisitang.common.bo.CustomerBehaviorAction;
import com.tuisitang.common.cache.memcached.MemcachedObjectType;
import com.tuisitang.common.cache.memcached.SpyMemcachedClient;
import com.tuisitang.common.service.ServiceException;
import com.tuisitang.common.utils.Identities;
import com.tuisitang.common.utils.QRCodeEncoder;
import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.entity.InviteCode;
import com.tuisitang.modules.shop.entity.Oss;
import com.tuisitang.modules.shop.entity.QiniuOSS;
import com.tuisitang.modules.shop.front.event.LoginEvent;
import com.tuisitang.modules.shop.front.security.FrontUserAuthorizingRealm.Principal;
import com.tuisitang.modules.shop.front.service.SystemFrontService;
import com.tuisitang.modules.shop.front.utils.AccountUtils;
import com.tuisitang.modules.shop.service.CommonService;
import com.tuisitang.modules.shop.service.OrderService;

/**    
 * @{#} LoginListener.java  
 * 
 * 用户登录侦听
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@Service
public class LoginListener extends AbstractApplicationListener implements ApplicationListener<LoginEvent> {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginListener.class);
	
	@Autowired
	private SystemFrontService systemFrontService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private SpyMemcachedClient memcachedClient;

	@Override
	public void onApplicationEvent(LoginEvent event) {
		Date sysTime = new Date(System.currentTimeMillis());
		Account account = event.getAccount();
		Map<String, Object> requestMap = event.getRequestMap();
		String base = (String) requestMap.get("BasePath");
		String openid = (String) requestMap.get("openid");
		logger.info("登录成功，更新Account {}, BasePath {}, openid {}", account, base, openid);
		
		//TODO Push 
		String userAgentString = (String) requestMap.get("UserAgentString");
		String actionDetail = userAgentString == null ? "" : userAgentString;
		HttpKit.push(account.getId(), CustomerBehaviorAction.Login.getAction(), sysTime, actionDetail);
		
		// 1. 更新登录IP和登录时间
		if (StringUtils.isBlank(account.getInviteCode()) || StringUtils.isBlank(account.getTwoDimensionUrl())) {// 如果inviteCode为空，则
			String inviteCode = StringUtils.isBlank(account.getInviteCode()) ? Identities
					.toSerialCode(account.getId(), 6) : account.getInviteCode();
			logger.info("InviteCode {}", inviteCode);
			Account act = null;
			if (StringUtils.isBlank(account.getInviteCode())) {
				systemFrontService.getAccountByInviteCode(inviteCode);
			}
			logger.info("act {}", act);
			if (act == null) {
				String qrcodeUrl = "";
				try {
					String param = "from_code=" + inviteCode + "&from_mobile=" + account.getMobile() + "&channel=1";
					String content = "http://www.baoxiliao.com/regist?" + Identities.encodeBase64(param);
					String newFileName = Identities.uuid2() + ".png";
					File dir = new File(base + account.getId() + "/");
					if (!dir.exists()) {
						dir.mkdirs();
					}
					File file = QRCodeEncoder.createQRCode(content, base + account.getId() + "/" + newFileName, QRCodeEncoder.DEFAULT_SIZE);
					
//					Oss oss = commonService.getOss(Oss.code_aliyun);
//					logger.info("OSS {}", oss);
//					if (oss == null || StringUtils.isBlank(oss.getOssJsonInfo())) {
//						logger.error("OSS配置为空");;
//						throw new ServiceException("OSS配置为空");
//					}
//					AliyunOSS aliyunOSS = JSON.parseObject(oss.getOssJsonInfo(), AliyunOSS.class);
//					logger.info("AliyunOSS {}", aliyunOSS);
//					
//					String accessKeyId = aliyunOSS.getACCESS_ID();
//					String endpoint = "http://oss.aliyuncs.com/";
//					String accessKeySecret = aliyunOSS.getACCESS_KEY();
//					String bucketName = aliyunOSS.getBucketName();
//					OSSClient client = OSSKit.getOSSClient(endpoint, accessKeyId, accessKeySecret);
//					client.createBucket(bucketName);
//			        client.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
//					
//					String basePath = "upload/i/" + account.getId() + "/qrcode/";
//					
//					String key = basePath + newFileName ;
//					// 创建上传Object的Metadata
//					ObjectMetadata meta = new ObjectMetadata();
//					// 必须设置ContentLength
//					meta.setContentLength(file.length());
//					meta.setContentType("image/png");
//					meta.setContentEncoding("utf-8");
//					// 上传Object.
//					PutObjectResult result = client.putObject(bucketName, key, new FileInputStream(file), meta);
//					qrcodeUrl = Global.getImageRootPath() + "/" + key;//"http://" + bucketName+".oss-cn-hangzhou.aliyuncs.com/" + key;
					
					Oss oss = commonService.getOss(Oss.code_qiniu);
					if (oss == null || StringUtils.isBlank(oss.getOssJsonInfo())) {
						logger.error("OSS配置为空");;
						throw new ServiceException("OSS配置为空");
					}
					
					QiniuOSS qiniuOss = JSON.parseObject(oss.getOssJsonInfo(), QiniuOSS.class);
					String bucketName = qiniuOss.getBucketName();
					
					String basePath = "upload/i/" + account.getId() + "/qrcode/";
					qrcodeUrl = basePath + newFileName;
					
					Config.ACCESS_KEY = qiniuOss.getACCESS_KEY();
			        Config.SECRET_KEY = qiniuOss.getSECRET_KEY();
			        Mac mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);

			        PutPolicy putPolicy = new PutPolicy(bucketName);
			        String uptoken = putPolicy.token(mac);
			        PutExtra extra = new PutExtra();
			        PutRet ret = IoApi.putFile(uptoken, qrcodeUrl, file, extra);
			        
					account.setTwoDimensionUrl(qrcodeUrl);
				} catch (OSSException e) {
					e.printStackTrace();
				} catch (ClientException e) {
					e.printStackTrace();
				} catch (ServiceException e) {
					e.printStackTrace();
				} catch (AuthException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				
				systemFrontService.updateAccountInviteCodeById(account.getId(), inviteCode, qrcodeUrl);
				
				commonService.saveInviteCode(new InviteCode(inviteCode, account.getId(), account.getMobile()));
			}
		}
		// 如果account为空或者account和mobile相同，更新account
		if (StringUtils.isBlank(account.getAccount()) || account.getMobile().equals(account.getAccount())) {
			String s = "BXL" + account.getMobile().substring(0, 3)
					+ Identities.toSerialCode(Long.parseLong(account.getMobile().substring(3, 7)), 4)
					+ account.getMobile().substring(account.getMobile().length() - 4);
			
			systemFrontService.updateAccountUsernameById(account.getId(), s);
		}
		if (StringUtils.isBlank(account.getOpenId()) && !StringUtils.isBlank(openid)) {
			systemFrontService.updateAccountOpenidById(account.getId(), openid);
		}
		systemFrontService.updateUserLoginInfo(account.getId(), (String) requestMap.get("Host"));
		account = updateAccountCache(event.getPrincipal(), account.getId());
		// 2. 获得sessionKey
		String sessionId = event.getSessionId();
		logger.info("sessionId {}", sessionId);
		// 3. 根据sessionKey和Account.id更新：1）购物车；2）我的收藏
		if (!StringUtils.isBlank(sessionId)) {
			systemFrontService.updateSession(sessionId, account.getId());
//			AccountRank ar = commonService.getAccountRankByCode(account.getRank());
			orderService.updateCart(sessionId, account.getId());
		}
		if (Account.DIFF_AREA_LOGIN_YES.equals(account.getDiffAreaLogin())) {// 如果是异地登录，需要进行提醒
			// 1. Inbox 2. Email 3. SMS
			//TODO
		}
	}
	
	private Account updateAccountCache(Principal principal, Long id) {
		Account account = null;
		memcachedClient.delete(Account.getKey(id));
		account = systemFrontService.getAccountById(id);
		if (account != null) {// 如果Account不为空，则缓存至Memcached
			memcachedClient.safeSet(Account.getKey(account.getMobile()), MemcachedObjectType.Account.getExpiredTime(), account);
			memcachedClient.safeSet(Account.getKey(id), MemcachedObjectType.Account.getExpiredTime(), account);
			if (!StringUtils.isBlank(account.getOpenId())) {
				memcachedClient.safeSet(Account.getKeyByOpenid(account.getOpenId()), MemcachedObjectType.Account.getExpiredTime(), account);
			}
		}
		AccountUtils.setAccount(principal, account);
		return account;
	}

}
