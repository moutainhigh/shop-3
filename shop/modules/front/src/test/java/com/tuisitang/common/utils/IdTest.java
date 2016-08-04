package com.tuisitang.common.utils;

import com.tuisitang.modules.shop.utils.Global;

public class IdTest {

	public static void main(String[] args) {
//		System.out.println(Identities.randomBase62(32));
		
		String appKey = "djIrX8wcDfc82ULV1evHqN7BMYdiC8k5rhdbAhAUmZpJy1p0yIXdsZ9aa1gX2swm578pdhc8OhaWZIjvWzQIitHQOqgkBH4QcmGz7ZICXzWzZPQoz1k31JGIQ3WnB3UG";
		System.out.println(appKey);
		
		String s = Encodes.encodeBase64(Encodes.rc4("2088021153521909", appKey).getBytes());
		System.out.println(s);
		System.out.println(Encodes.rc4(new String(Encodes.decodeBase64(s)), appKey));
		
		s = Encodes.encodeBase64(Encodes.rc4("1877534934@qq.com", appKey).getBytes());
		System.out.println(s);
		System.out.println(Encodes.rc4(new String(Encodes.decodeBase64(s)), appKey));
		
		s = Encodes.encodeBase64(Encodes.rc4("fzqt7ebdo6p0nlb5c97ud0phktm1vvre", appKey).getBytes());
		System.out.println(s);
		System.out.println(Encodes.rc4(new String(Encodes.decodeBase64(s)), appKey));
		
		// ---
		/**
		 * smsSerialKey=vip_xqfb
smsSerialPassword=Tch123456
smsSendSMSURL=http\://222.73.117.158/msg//HttpBatchSendSM
		 */
		
		String key = Identities.randomBase62(32);
		
		System.out.println(key);
		
		s = Encodes.encodeBase64(Encodes.rc4("vip_xqfb", appKey).getBytes());
		System.out.println(s);
		System.out.println(Encodes.rc4(new String(Encodes.decodeBase64(s)), appKey));
		
		s = Encodes.encodeBase64(Encodes.rc4("Tch123456", appKey).getBytes());
		System.out.println(s);
		System.out.println(Encodes.rc4(new String(Encodes.decodeBase64(s)), appKey));
		
		s = Encodes.encodeBase64(Encodes.rc4("http://222.73.117.158/msg//HttpBatchSendSM", appKey).getBytes());
		System.out.println(s);
		System.out.println(Encodes.rc4(new String(Encodes.decodeBase64(s)), appKey));
		
		s = Encodes.encodeBase64(Encodes.rc4("VIP_llfx", appKey).getBytes());
		System.out.println(s);
		System.out.println(Encodes.rc4(new String(Encodes.decodeBase64(s)), appKey));
		
		s = Encodes.encodeBase64(Encodes.rc4("Llfx19871016", appKey).getBytes());
		System.out.println(s);
		System.out.println(Encodes.rc4(new String(Encodes.decodeBase64(s)), appKey));
		
		s = Encodes.encodeBase64(Encodes.rc4("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAN42SAKg8WUmJyo7i1ImUv5MumEJHqZMJZBmLSdqZUjfqJ31RQrUmblE6alFsbw+S/qRYPciYZCDoExfGrvLbqqGpOOwIpqsnlvLOzvTVndbUbmP8CACu7EAWdP8acEsnmQf2Qsp5IrFD+Ix9HKwj7J10GOb92pXahPcZBy4ab8tAgMBAAECgYB///aVBQ5oPIT879W+GXNfJuWJdc5g7qYIusKx3LjBNIyViK4fof122jpallneXGg5yrp3QSfALgINXA8zM+jnYMpWQAWvAVmcTeKT8j0XyXJVGXXMP8K0ckFPn2M6OzaO17N1TzZ8wRB4+5DYWrkyXLFLAmPDpvCD1djr6tnpZQJBAPVf6jpw4hECvSCDrHrn282SuqqBFNmetWu+P0mzXex8KZAAXROhTEht0TqGQxVqPN42FMzQ7Qq0tGoLfN5As5sCQQDn1Zk17G0sNSnv994xTcd/3usRtZyN3qeUwjG78xbpI1xg3RnyBmAlNs52V9sS1QaqMU1CJ9M4bmv/EO11azjXAkEA0GMW328C7wNub3CkSPUkXSAh27AobvE0jj5SLVthhvejrKJCaetwh4qMSLHBwywoaWdwnslIpvD0z6LV90LnxQJASg2aoPTicoqQ2MwuN0WHAjtsjv2aAl8q3IOja0vc95+J9I7rqLmMoJZ/IKIzojJVrKuO6CDcgVwUNydrEmSrhQJBAJ8xV5uEhWVdZ2BJwDf1R/LdGiK1hE2+tmtJkThm7ov2ltuCGBn25Mor8rmcvBW2sMtfnK5iCFC0nZYNs10uz1k=", appKey).getBytes());
		System.out.println(s);
		System.out.println(Encodes.rc4(new String(Encodes.decodeBase64(s)), appKey));
		
	}

}
