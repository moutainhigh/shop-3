package com.tuisitang.common.utils;

import java.util.Date;

public class StringTest {

	public static void main(String[] args) {
		Date systemTime = new Date(System.currentTimeMillis());
		int num = 1;
		String no = DateUtils.formatDate(systemTime, "yyyyMMddHHmmss") + String.format("%06d", num);
		System.out.println(no);
	}

}
