/**
 * 微信公众平台开发模式(JAVA) SDK
 * (c) 2012-2013 ____′↘夏悸 <wmails@126.cn>, MIT Licensed
 * http://www.jeasyuicn.com/wechat
 */
package com.gson.oauth;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.alibaba.fastjson.JSONObject;
import com.gson.util.HttpKit;
import com.gson.util.SHA1;
import com.tuisitang.modules.shop.utils.Global;

/**
 * 支付相关方法
 * @author L.cm
 * email: 596392912@qq.com
 * site:  http://www.dreamlu.net
 *
 */
public class WxPay {

    // 发货通知接口
    private static final String DELIVERNOTIFY_URL = "https://api.weixin.qq.com/pay/delivernotify?access_token=";
    //获取预支付id的接口url
    private static final String CREATE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 参与 paySign 签名的字段包括：appid、timestamp、noncestr、package 以及 appkey。
     * 这里 signType 并不参与签名微信的Package参数
     * @param params
     * @return
     * @throws UnsupportedEncodingException 
     */
	public static String getPackage(Map<String, String> params) throws UnsupportedEncodingException {
        String partnerKey = Global.getConfig("partnerKey");
        String partnerId = Global.getConfig("partnerId");
        String notifyUrl = Global.getConfig("notify_url");
        // 公共参数
        params.put("bank_type", "WX");
        params.put("attach", "yongle");
        params.put("partner", partnerId);
        params.put("notify_url", notifyUrl);
        params.put("input_charset", "UTF-8");
        return packageSign(params, partnerKey);
    }

    /**
     * 构造签名
     * @param params
     * @param encode
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String createSign(Map<String, String> params, boolean encode) throws UnsupportedEncodingException {
        Set<String> keysSet = params.keySet();
        Object[] keys = keysSet.toArray();
        Arrays.sort(keys);
        StringBuffer temp = new StringBuffer();
        boolean first = true;
        for (Object key : keys) {
            if (first) {
                first = false;
            } else {
                temp.append("&");
            }
            temp.append(key).append("=");
            Object value = params.get(key);
            String valueString = "";
            if (null != value) {
                valueString = value.toString();
            }
            if (encode) {
                temp.append(URLEncoder.encode(valueString, "UTF-8"));
            } else {
                temp.append(valueString);
            }
        }
        return temp.toString();
    }

    /**
     * 构造package, 这是我见到的最草蛋的加密，尼玛文档还有错
     * @param params
     * @param paternerKey
     * @return
     * @throws UnsupportedEncodingException 
     */
    private static String packageSign(Map<String, String> params, String paternerKey) throws UnsupportedEncodingException {
        String string1 = createSign(params, false);
        String stringSignTemp = string1 + "&key=" + paternerKey;
        String signValue = DigestUtils.md5Hex(stringSignTemp).toUpperCase();
        String string2 = createSign(params, true);
        return string2 + "&sign=" + signValue;
    }
    

    public static String getSign(Map<String, String> params, String paternerKey) throws UnsupportedEncodingException {
    	String string1 = createSign(params, false);
        String stringSignTemp = string1 + "&key=" + paternerKey;
        return DigestUtils.md5Hex(stringSignTemp).toUpperCase();
    }
    
    /**
     * 支付签名
     * @param timestamp
     * @param noncestr
     * @param packages
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static String paySign(String timestamp, String prepayId, String noncestr,String packages) throws UnsupportedEncodingException {
        Map<String, String> paras = new HashMap<String, String>();
        paras.put("appid", Global.getConfig("AppId"));
        paras.put("partnerid", Global.getConfig("partnerId"));
        paras.put("prepayid", prepayId);
        paras.put("package", packages);
        paras.put("noncestr", noncestr);
        paras.put("timestamp", timestamp);
        // appid、timestamp、noncestr、package。
        String sign = getSign(paras, Global.getConfig("partnerKey"));
        return sign;
    }
    
    
    
    /**
     * 支付回调校验签名
     * @param timestamp
     * @param noncestr
     * @param openid
     * @param issubscribe
     * @param appsignature
     * @return
     * @throws UnsupportedEncodingException 
     */
    public static boolean verifySign(long timestamp,
            String noncestr, String openid, int issubscribe, String appsignature) throws UnsupportedEncodingException {
        Map<String, String> paras = new HashMap<String, String>();
        paras.put("appid", Global.getConfig("AppId"));
        paras.put("appkey", Global.getConfig("paySignKey"));
        paras.put("timestamp", String.valueOf(timestamp));
        paras.put("noncestr", noncestr);
        paras.put("openid", openid);
        paras.put("issubscribe", String.valueOf(issubscribe));
        // appid、appkey、productid、timestamp、noncestr、openid、issubscribe
        String string1 = createSign(paras, false);
        String paySign = DigestUtils.sha1Hex(string1);
        return paySign.equalsIgnoreCase(appsignature);
    }
    
    /**
     * 发货通知签名
     * @param paras
     * @return
     * @throws UnsupportedEncodingException
     * 
     * @参数 appid、appkey、openid、transid、out_trade_no、deliver_timestamp、deliver_status、deliver_msg；
     */
    private static String deliverSign(Map<String, String> paras) throws UnsupportedEncodingException {
        paras.put("appkey", Global.getConfig("paySignKey"));
        String string1 = createSign(paras, false);
        String paySign = DigestUtils.sha1Hex(string1);
        return paySign;
    }
    
    
    /**
     * 发货通知
     * @param access_token
     * @param openid
     * @param transid
     * @param out_trade_no
     * @return
     * @throws IOException 
     * @throws NoSuchProviderException 
     * @throws NoSuchAlgorithmException 
     * @throws KeyManagementException 
     * @throws InterruptedException 
     * @throws ExecutionException 
     */

    public static boolean delivernotify(String access_token, String openid, String transid, String out_trade_no) throws IOException, ExecutionException, InterruptedException {
        Map<String, String> paras = new HashMap<String, String>();
        paras.put("appid", Global.getConfig("AppId"));
        paras.put("openid", openid);
        paras.put("transid", transid);
        paras.put("out_trade_no", out_trade_no);
        paras.put("deliver_timestamp", (System.currentTimeMillis() / 1000) + "");
        paras.put("deliver_status", "1");
        paras.put("deliver_msg", "ok");
        // 签名
        String app_signature = deliverSign(paras);
        paras.put("app_signature", app_signature);
        paras.put("sign_method", "sha1");
        String json = HttpKit.post(DELIVERNOTIFY_URL.concat(access_token), JSONObject.toJSONString(paras));
        if (StringUtils.isNotBlank(json)) {
            JSONObject object = JSONObject.parseObject(json);
            if (object.containsKey("errcode")) {
                int errcode = object.getIntValue("errcode");
                return errcode == 0;
            }
        }
        return false;
    }
    
    /**
     * 获取prepay_id
     * @param xml
     * @return
     * @throws Exception 
     */
	public static String getPrepayId(String xml) throws Exception {
		String prepay_id = "";
		String jsonStr = HttpKit.post(CREATE_ORDER_URL, xml);
		if(jsonStr.indexOf("FAIL")!=-1){
		    	return prepay_id;
	    }
	    Map map = doXMLParse(jsonStr);
	    prepay_id  = (String) map.get("prepay_id");
		return prepay_id;
	}
	
	/**
	 * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
	 * @param strxml
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static Map doXMLParse(String strxml) throws Exception {
		if(null == strxml || "".equals(strxml)) {
			return null;
		}
		
		Map m = new HashMap();
		InputStream in = String2Inputstream(strxml);
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while(it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if(children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = getChildrenText(children);
			}
			
			m.put(k, v);
		}
		
		//关闭流
		in.close();
		
		return m;
	}
	/**
	 * 获取子结点的xml
	 * @param children
	 * @return String
	 */
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if(!children.isEmpty()) {
			Iterator it = children.iterator();
			while(it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if(!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		
		return sb.toString();
	}
	
	public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}
	public static void main(String[] args) throws UnsupportedEncodingException {
		  Map<String, String> paras = new HashMap<String, String>();
	        paras.put("appid", "wx6628747815d755b8");
	        paras.put("partnerid", "1272468701");
	        paras.put("prepayid", "wx201510281825337b1d590c0b0204805666");
	        paras.put("package", "Sign=WXPay");
	        paras.put("noncestr", "7837469273");
	        paras.put("timestamp", "1446027930669");
	        // appid、timestamp、noncestr、package。
	        String string1 = createSign(paras, false);
	        String stringSignTemp = string1 + "&key=" + "dfoigsadfonxmkvclmkxzcv753vkblp2";
	        String sign = DigestUtils.md5Hex(stringSignTemp).toUpperCase();
	        System.out.println(sign);
	}
}
