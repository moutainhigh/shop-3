package com.tuisitang.common.utils;

import net.jeeshop.core.kuaidi.Kuaidi100Info;
import net.jeeshop.core.kuaidi.Kuaidi100Item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gson.util.HttpKit;
import com.tuisitang.common.mapper.JsonMapper;

public class ShippingKit {
	private static final Logger logger = LoggerFactory.getLogger(ShippingKit.class);

	public static final String BASE_KUAIDI100_URL = "http://www.kuaidi100.com/query?type=%s&postid=%s&id=1&valicode=&temp=%d";

	public static Kuaidi100Info getShippingInfo(String type, String no) {
		String url = String.format(BASE_KUAIDI100_URL, type, no, System.currentTimeMillis());
		try {
			String body = HttpKit.get(url);
			Kuaidi100Info info = JsonMapper.getInstance().fromJson(body, Kuaidi100Info.class);
			return info;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("{}", e.getMessage());
			return null;
		}
	}

	public static void main(String[] args) throws Exception {
		String url = "http://www.kuaidi100.com/query?type=zhongtong&postid=358517732038&id=1&valicode=&temp=0.3815007843077183";
		String body = HttpKit.get(url);
		System.out.println(body);
		String type = "zhongtong";
		String no = "358573935590";
		Kuaidi100Info info = ShippingKit.getShippingInfo(type, no);
		logger.info("{}", info);
		if("ok".equals(info.getMessage())) {
			for(Kuaidi100Item item : info.getData()){
				logger.info("{}", item);
			}
		}
	}
}
