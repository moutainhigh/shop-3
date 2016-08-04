package com.tuisitang.common.utils;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.gson.WeChat;
import com.gson.bean.menu.Button;
import com.gson.bean.menu.CommonButton;
import com.gson.bean.menu.ComplexButton;
import com.gson.bean.menu.Menu;

public class WechatTest {

	/**
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
	 * @param args
	 */
	@Test
	public void createMenu() {
//		String appId="wx381a5ef4971886b7";
//		String appSecret="eace469d2a58443325ca6663110542e7";
		String appId="wx3883c77e4926ec7a";
		String appSecret="50df30cdf2ea320adc15824852146b51";
		
//		https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx18128b9d7ac6d25b&redirect_uri=http%3A%2F%2Fwww.daojiajinrong.com:8080&response_type=code&scope=snsapi_base&state=123#wechat_redirect
			
		Menu menu = new Menu();
        Button[] bs = new Button[3];
        menu.setButton(bs);
		{
			ComplexButton mainBtn = new ComplexButton();
			mainBtn.setName("预约购物");
			bs[0] = mainBtn;
			CommonButton[] buttons = new CommonButton[4];
			mainBtn.setSub_button(buttons);
			{
				buttons[3] = new CommonButton();
				buttons[3].setName("在线预约");
				buttons[3].setType("view");
				buttons[3].setUrl(String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=booking#wechat_redirect",
										appId, Encodes.urlEncode("http://www.baoxiliao.com/wx/oauth2")));
//				buttons[3].setUrl("http://www.baoxiliao.com/booking");
			}
			{
				buttons[2] = new CommonButton();
				buttons[2].setName("在线购物");
				buttons[2].setType("view");
//				buttons[2].setUrl(String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=#wechat_redirect",
//										appId, Encodes.urlEncode("http://www.baidu.com")));
				buttons[2].setUrl(String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=mall#wechat_redirect",
						appId, Encodes.urlEncode("http://www.baoxiliao.com/wx/oauth2")));
//				buttons[2].setUrl("http://www.baoxiliao.com/mall");
			}
			{
				buttons[1] = new CommonButton();
				buttons[1].setName("批量采购");
				buttons[1].setType("view");
//				buttons[1].setUrl(String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=#wechat_redirect",
//										appId, Encodes.urlEncode("http://www.baidu.com")));
				buttons[1].setUrl(String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=mall#wechat_redirect",
						appId, Encodes.urlEncode("http://www.baoxiliao.com/wx/oauth2")));
//				buttons[1].setUrl("http://www.baoxiliao.com/mall");
			}
			{
				buttons[0] = new CommonButton();
				buttons[0].setName("关于我们");
				buttons[0].setType("view");
//				buttons[0].setUrl(String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=#wechat_redirect",
//										appId, Encodes.urlEncode("http://www.baidu.com")));
				buttons[0].setUrl(String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=#wechat_redirect",
						appId, Encodes.urlEncode("http://www.baoxiliao.com/wx/oauth2")));
//				buttons[0].setUrl("http://www.baoxiliao.com");
			}
		}
		
		{
			ComplexButton mainBtn = new ComplexButton();
			mainBtn.setName("热门活动");
			bs[1] = mainBtn;
			CommonButton[] buttons = new CommonButton[2];
			mainBtn.setSub_button(buttons);
			{
//				buttons[1] = new CommonButton();
//				buttons[1].setName("注册送3D软件");
//				buttons[1].setType("click");
//				buttons[1].setKey("VIEW_REGIST");
				
				buttons[1] = new CommonButton();
				buttons[1].setName("注册送3D软件");
				buttons[1].setType("view");
//				buttons[1].setUrl(String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=#wechat_redirect",
//										appId, Encodes.urlEncode("http://www.baidu.com")));
				buttons[1].setUrl(String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=regist#wechat_redirect",
						appId, Encodes.urlEncode("http://www.baoxiliao.com/wx/oauth2")));
			}
			{
				buttons[0] = new CommonButton();
				buttons[0].setName("邀请得套图");
				buttons[0].setType("click");
				buttons[0].setKey("VIEW_INVITE");
			}
		}
		
		{
			ComplexButton mainBtn = new ComplexButton();
			mainBtn.setName("自助服务");
			bs[2] = mainBtn;
			CommonButton[] buttons = new CommonButton[4];
			mainBtn.setSub_button(buttons);
			{
				buttons[0] = new CommonButton();
				buttons[0].setName("绑定账号");
				buttons[0].setType("view");
//				buttons[0].setUrl(String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=#wechat_redirect",
//										appId, Encodes.urlEncode("http://www.baidu.com")));
				buttons[0].setUrl(String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=login#wechat_redirect",
						appId, Encodes.urlEncode("http://www.baoxiliao.com/wx/oauth2")));
//				buttons[0].setUrl("http://www.baoxiliao.com/login");
			}
			{
				buttons[1] = new CommonButton();
				buttons[1].setName("我的邀请码");
				buttons[1].setType("view");
//				buttons[1].setUrl(String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=#wechat_redirect",
//										appId, Encodes.urlEncode("http://www.baidu.com")));
				buttons[1].setUrl(String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=i/invite#wechat_redirect",
						appId, Encodes.urlEncode("http://www.baoxiliao.com/wx/oauth2")));
//				buttons[1].setUrl("www.baoxiliao.com/i/invite");
			}
			{
				buttons[2] = new CommonButton();
				buttons[2].setName("我的预约单");
				buttons[2].setType("view");
//				buttons[2].setUrl(String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=#wechat_redirect",
//										appId, Encodes.urlEncode("http://www.baidu.com")));
				buttons[2].setUrl(String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=i/booking#wechat_redirect",
						appId, Encodes.urlEncode("http://www.baoxiliao.com/wx/oauth2")));
//				buttons[2].setUrl("http://www.baoxiliao.com/i/booking");
			}
			{
				buttons[3] = new CommonButton();
				buttons[3].setName("我的订单");
				buttons[3].setType("view");
//				buttons[3].setUrl(String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=#wechat_redirect",
//										appId, Encodes.urlEncode("http://www.baidu.com")));
				buttons[3].setUrl(String.format("https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=i/order#wechat_redirect",
						appId, Encodes.urlEncode("http://www.baoxiliao.com/wx/oauth2")));
//				buttons[3].setUrl("http://www.baoxiliao.com/i/order");
			}
		}
		
		System.out.println(JSONObject.toJSONString(menu));

		com.gson.oauth.Menu m = new com.gson.oauth.Menu();
		try {
			System.out.println(m.createMenu(WeChat.getAccessToken(appId, appSecret), JSONObject.toJSONString(menu)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
