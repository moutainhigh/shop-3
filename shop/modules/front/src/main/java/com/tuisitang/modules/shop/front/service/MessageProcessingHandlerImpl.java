package com.tuisitang.modules.shop.front.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gson.WeChat;
import com.gson.bean.InMessage;
import com.gson.bean.OutMessage;
import com.gson.bean.TextOutMessage;
import com.gson.bean.UserInfo;
import com.gson.inf.MessageProcessingHandler;
import com.gson.oauth.User;
import com.tuisitang.common.cache.memcached.SpyMemcachedClient;
import com.tuisitang.modules.shop.entity.WechatUser;
import com.tuisitang.modules.shop.front.service.task.WechatTokenTask;

/**    
 * @{#} MessageProcessingHandlerImpl.java   
 *    
 * 微信消息处理器
 * 
 * 菜单：
 * 预约购物
 *   - 在线预约
 *   - 在线购物
 *   - 批量采购
 *   - 关于我们
 * 热门活动
 *   - 注册送3D软件
 *   - 邀请得套图
 * 自助服务
 *   - 绑定账号
 *   - 我的邀请码
 *   - 我的预约单
 *   - 我的订单
 *   
 * webot send --token 123 --des http://127.0.0.1:8080/shop-front/wx/123 --user admin
 *
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@Service
public class MessageProcessingHandlerImpl implements MessageProcessingHandler {

	private static final Logger logger = LoggerFactory.getLogger(MessageProcessingHandlerImpl.class);
	
	@Autowired
	private SystemFrontService systemFrontService;
	@Autowired
	private SpyMemcachedClient memcachedClient;
	
	private OutMessage outMessage;
	
	@Override
	public void allType(InMessage msg) {
		logger.info("allType {}", msg);
	}

	@Override
	public void textTypeMsg(InMessage msg) {
		logger.info("textTypeMsg {}", msg);
	}

	@Override
	public void locationTypeMsg(InMessage msg) {
		logger.info("locationTypeMsg {}", msg);
	}

	@Override
	public void imageTypeMsg(InMessage msg) {
		logger.info("imageTypeMsg {}", msg);
	}

	@Override
	public void videoTypeMsg(InMessage msg) {
		logger.info("videoTypeMsg {}", msg);
	}

	@Override
	public void linkTypeMsg(InMessage msg) {
		logger.info("linkTypeMsg {}", msg);
	}

	@Override
	public void voiceTypeMsg(InMessage msg) {
		logger.info("voiceTypeMsg {}", msg);
	}

	@Override
	public void verifyTypeMsg(InMessage msg) {
		logger.info("verifyTypeMsg {}", msg);
	}

	@Override
	public void eventTypeMsg(InMessage msg) {
		logger.info("eventTypeMsg {}", msg);
		try {
			String event = msg.getEvent();
			String key = msg.getEventKey();
			String ticket = msg.getTicket();
			String fromUserName = msg.getFromUserName();
			logger.info("eventTypeMsg event = {}, key = {}, ticket = {}, fromUserName = {}",
					event, key, ticket, fromUserName);
			if ("subscribe".equals(event)) {// 订阅
				User user = new User();
				String accessToken = WeChat.getAccessToken(memcachedClient);
				logger.info("accessToken {}, fromUserName {}", accessToken, fromUserName);
				UserInfo userInfo = user.getUserInfo(accessToken, fromUserName);
				systemFrontService.saveWechatUser(userInfo.getOpenid(),
						userInfo.getSubscribe(), userInfo.getNickname(),
						userInfo.getSex(), userInfo.getLanguage(),
						userInfo.getCity(), userInfo.getProvince(),
						userInfo.getCountry(), userInfo.getHeadimgurl(),
						userInfo.getSubscribe_time(), "");
				setOutMessage(buildHelpTextOutMessage());
			} else if ("unsubscribe".equals(event)) {// 取消订阅
				systemFrontService.deleteWechatUserByOpenid(fromUserName);
			} else if ("VIEW".equals(event.toUpperCase())) {
				if ("VIEW_REGIST".equals(key)) {// 注册
					
				} else if ("VIEW_INVITE".equals(key)) {// 邀请
					
				}
			} else if ("LOCATION".equals(event.toUpperCase())) {
				String latitude = msg.getLatitude();
				String longitude = msg.getLongitude();
				String precision = msg.getPrecision();
				logger.info("latitude {}, longitude {}, precision {}", latitude, longitude, precision);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{}", e);
		}
	}

	@Override
	public void afterProcess(InMessage inMsg, OutMessage outMsg) {
		logger.info("afterProcess inMsg {}, outMsg {}", inMsg, outMsg);
	}

	@Override
	public void setOutMessage(OutMessage outMessage) {
		this.outMessage = outMessage;
	}

	@Override
	public OutMessage getOutMessage() {
		return outMessage;
	}
	
	/**
	 * 转换成WechatUser
	 * 
	 * @param userInfo
	 * @return
	 */
	private static WechatUser transform(UserInfo userInfo) {
		WechatUser wu = new WechatUser();
		wu.setSubscribe(userInfo.getSubscribe());
		wu.setOpenid(userInfo.getOpenid());
		wu.setNickname(userInfo.getNickname());
		wu.setLanguage(userInfo.getLanguage());
		wu.setCity(userInfo.getCity());
		wu.setProvince(userInfo.getProvince());
		wu.setCountry(userInfo.getCountry());
		wu.setSex(userInfo.getSex());
		wu.setHeadimgurl(userInfo.getHeadimgurl());
		return wu;
	}
	
	/**
	 * 组建TextOutMessage
	 * 
	 * @param content
	 * @return
	 */
	private TextOutMessage buildTextOutMessage(String content) {
		TextOutMessage out = new TextOutMessage();
		out.setContent(content);
		return out;
	}
	
	/**
	 * 组建帮助信息的TextOutMessage
	 * 
	 * @return
	 */
	private TextOutMessage buildHelpTextOutMessage() {
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎来到报喜了。\n");
		TextOutMessage out = new TextOutMessage();
		out.setContent(sb.toString());
		return out;
	}
	
}
