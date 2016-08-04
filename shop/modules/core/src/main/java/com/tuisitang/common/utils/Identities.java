package com.tuisitang.common.utils;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * 封装各种生成唯一性ID算法的工具类.
 * @author calvin
 * @version 2013-01-15
 */
public class Identities {

	private static SecureRandom random = new SecureRandom();

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间有-分割.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
	 */
	public static String uuid2() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 使用SecureRandom随机生成Long. 
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	/**
	 * 基于Base62编码的SecureRandom随机生成bytes.
	 */
	public static String randomBase62(int length) {
		byte[] randomBytes = new byte[length];
		random.nextBytes(randomBytes);
		return Encodes.encodeBase62(randomBytes);
	}
	
	/**
	 * 生成随机6位验证码
	 */
	public static String randomValidateCode() {
		String code = String.format("%06d", random.nextInt(999999));
		return code;
	}
	
	/**
	 * 生成随机邀请码
	 */
	public static String getInviteKey(int length) {
		String val = "";
		for (int i = 0; i < length; i++) {
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

			if ("char".equalsIgnoreCase(charOrNum)) {// 字符串
				int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
				val += (char) (choice + random.nextInt(26));
			} else if ("num".equalsIgnoreCase(charOrNum)) { // 数字
				val += String.valueOf(random.nextInt(10));
			}
		}

		return val;
	}
	
	public static String decryptionRC4(byte[] data, String key) {
		if (data == null || key == null) {
			return null;
		}
		return new String(RC4Base(data, key));
	}

	public static byte[] encryptionRC4Byte(String data, String key) {
		if (data == null || key == null) {
			return null;
		}
		byte b_data[] = null;
		try {
			b_data = data.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return RC4Base(b_data, key);
	}

	private static byte[] initKey(String aKey) {
		byte[] b_key = null;
		try {
			b_key = aKey.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		byte state[] = new byte[256];
		for (int i = 0; i < 256; i++) {
			state[i] = (byte) i;
		}
		int index1 = 0;
		int index2 = 0;
		if (b_key == null || b_key.length == 0) {
			return null;
		}
		for (int i = 0; i < 256; i++) {
			index2 = ((b_key[index1] & 0xff) + (state[i] & 0xff) + index2) & 0xff;
			byte tmp = state[i];
			state[i] = state[index2];
			state[index2] = tmp;
			index1 = (index1 + 1) % b_key.length;
		}
		return state;
	}

	private static byte[] RC4Base(byte[] input, String mKkey) {
		int x = 0;
		int y = 0;
		byte key[] = initKey(mKkey);
		int xorIndex;
		byte[] result = new byte[input.length];
		for (int i = 0; i < input.length; i++) {
			x = (x + 1) & 0xff;
			y = ((key[x] & 0xff) + y) & 0xff;
			byte tmp = key[x];
			key[x] = key[y];
			key[y] = tmp;
			xorIndex = ((key[x] & 0xff) + (key[y] & 0xff)) & 0xff;
			result[i] = (byte) (input[i] ^ key[xorIndex]);
		}
		return result;
	}
	
	/**
	 * 生成SessionKey，唯一值
	 */
	public static String genSessionKey() {
		return randomBase62(128);
	}
	
	/**
	 * 
	 */
	public static String encodeBase64(String data) {
		try {
			return new String(Base64.encodeBase64(data.getBytes("UTF-8")), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 */
	public static String decodeBase64(String input) {
		return new String(Base64.decodeBase64(input));
	}
	

	/**
	 * 邀请码生成器，算法原理：<br/>
	 * 1) 获取id: 1127738 <br/>
	 * 2) 使用自定义进制转为：gpm6 <br/>
	 * 3) 转为字符串，并在后面加'o'字符：gpm6o <br/>
	 * 4）在后面随机产生若干个随机数字字符：gpm6o7 <br/>
	 * 转为自定义进制后就不会出现o这个字符，然后在后面加个'o'，这样就能确定唯一性。最后在后面产生一些随机字符进行补全。<br/>
	 */
	/** 自定义进制(0,1没有加入,容易与o,l混淆) */
//	private static final char[] r = new char[] { 'q', 'w', 'e', '8', 'a', 's',
//			'2', 'd', 'z', 'x', '9', 'c', '7', 'p', '5', 'i', 'k', '3', 'm',
//			'j', 'u', 'f', 'r', '4', 'v', 'y', 'l', 't', 'n', '6', 'b', 'g',
//			'h' };
	
//	private static final char[] r = new char[] { 'J', '4', 'Z', 'E', 'N', 'D',
//			'5', 'O', 'L', 'M', 'P', 'T', '1', 'C', 'W', 'F', 'S', 'Y', 'Q',
//			'A', 'U', 'V', '3', 'I', '7', 'X', '9', 'R', 'K', '2', '6', '8',
//			'0', 'B', 'G', 'H' };
	
	private static final char[] r = new char[] { 'J', 'Z', 'E', 'N', 'D', 'O',
			'L', 'M', 'P', 'T', 'C', 'W', 'F', 'S', 'Y', 'Q', 'A', 'U', 'V',
			'I', 'X', 'R', 'K', 'B', 'G', 'H' };

	/** (不能与自定义进制有重复) */
	private static final char b = 'O';

	/** 进制长度 */
	private static final int binLen = r.length;

	/** 序列最小长度 */
//	private static final int s = 6;
 
	/**
	 * 根据ID生成六位随机码
	 * 
	 * @param id
	 *            ID
	 * @return 随机码
	 */
	public static String toSerialCode(long id, int len) {
		char[] buf = new char[32];
		int charPos = 32;

		while ((id / binLen) > 0) {
			int ind = (int) (id % binLen);
			buf[--charPos] = r[ind];
			id /= binLen;
		}
		buf[--charPos] = r[(int) (id % binLen)];
		// System.out.println(num + "-->" + num % binLen);
		String str = new String(buf, charPos, (32 - charPos));
		// 不够长度的自动随机补全
		if (str.length() < len) {
			StringBuilder sb = new StringBuilder();
			sb.append(b);
			for (int i = 1; i < len - str.length(); i++) {
				sb.append(r[random.nextInt(binLen)]);
			}
			str += sb.toString();
		}
		return str;
	}
	
	public static String toSerialCode2(long id, int len) {
		char[] buf = new char[32];
		int charPos = 32;

		while ((id / binLen) > 0) {
			int ind = (int) (id % binLen);
			buf[--charPos] = r[ind];
			id /= binLen;
		}
		buf[--charPos] = r[(int) (id % binLen)];
		// System.out.println(num + "-->" + num % binLen);
		String str = new String(buf, charPos, (32 - charPos));
		// 不够长度的自动随机补全
		if (str.length() < len) {
			StringBuilder sb = new StringBuilder();
			sb.append(b);
			for (int i = 1; i < len - str.length(); i++) {
				sb.append(r[random.nextInt(binLen)]);
			}
			str += sb.toString();
		}
		return str;
	}

	public static long codeToId(String code) {
		char chs[] = code.toCharArray();
		long res = 0L;
		for (int i = 0; i < chs.length; i++) {
			int ind = 0;
			for (int j = 0; j < binLen; j++) {
				if (chs[i] == r[j]) {
					ind = j;
					break;
				}
			}
			if (chs[i] == b) {
				break;
			}
			if (i > 0) {
				res = res * binLen + ind;
			} else {
				res = ind;
			}
		}
		return res;
	}
	
	public static void main(String[] args) throws Exception {
		String code = toSerialCode(8086, 4);
		System.out.println(code);
//		System.out.println(codeToId(code));
//		char a = 'A';
//		char z = 'Z';
//		List<Character> l = Lists.newArrayList();
//		for (char c = a; c <= z; c++) {
//			System.out.print(c);
//			l.add(c);
//		}
//		for (char c = '0'; c <= '9'; c++) {
//			System.out.print(c);
//			l.add(c);
//		}
//		System.out.println();
//		for (char c : l) {
//			System.out.print(c);
//		}
//		System.out.println();
//		int size = l.size();
//		for (int i = 0; i < size; i++) {
//			int len = size - i;
//			int n = random.nextInt(len);
//			char c = l.get(n);
//			l.remove(n);
//			System.out.print("'" + c + "',");
//		}
//		System.out.println();
		Set<String> set = Sets.newHashSet();
		for (int i = 1; i < 1000000; i++) {
			set.add(toSerialCode(i, 6));
		}
		System.out.println(set.size());
		
		String mobile = "18080860877";
		System.out.println("BXL" + mobile.substring(0, 3) + Identities.toSerialCode(Long.parseLong(mobile.substring(3, 7)), 4) + mobile.substring(mobile.length() - 4));
	}
	
}
