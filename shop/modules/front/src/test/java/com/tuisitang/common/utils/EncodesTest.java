package com.tuisitang.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncodesTest {

	private static final Logger logger = LoggerFactory.getLogger(EncodesTest.class);
			
	public static void main(String[] args) {
//		String input = "ZnJvbV9jb2RlPUVIUEFPQiZ1bmFtZT1qaWtlXzA4NjA4NzcmY2hhbm5lbD1pbnZpdGVfMTAwd19zaGFyZWJ1dHRvbl9jb3B5MQ==";
//		
//		System.out.println(new String(Encodes.decodeBase64(input)));
//		
//		String s = "from_code=Z1JO84&from_mobile=18080860877&channel=invite_from_web_copy";
//		System.out.println(Encodes.encodeBase64(s.getBytes()));
		String key = "ZnJvbV9jb2RlPVoxSk84NCZmcm9tX21vYmlsZT0xODA4MDg2MDg3NyZjaGFubmVsPWludml0ZV9mcm9tX3dlYl9jb3B5";
		
		String s = Identities.decodeBase64(key);
		logger.info("{}", s);
		String[] params = s.split("&");
		String fromCode = null;
		String fromMobile = null;
		String channel = null;
		if (params.length == 3) {
			for (int i = 0; i < params.length; i++) {
				String[] ss = params[i].split("=");
				if (ss.length == 2) {
					if ("from_code".equals(ss[0])) {
						fromCode = ss[1];
					} else if ("from_mobile".equals(ss[0])) {
						fromMobile = ss[1]; 	
					} else if ("channel".equals(ss[0])) {
						channel = ss[1]; 	
					}
				}
			}
		}
		logger.info("fromCode {}, fromMobile {}, channel {}", fromCode, fromMobile, channel);
	}

}
