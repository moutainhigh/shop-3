package com.tuisitang.common.utils;

public class PriceTest {

	public static void main(String[] args) {
		System.out.println(calculateFreight(120.00));
		System.out.println(calculateFreight(160.00));
	}

	public static double calculateFreight(Double nowPrice) {
		double freight = 0d;
		if (nowPrice < 500 && nowPrice > 100) {
			Double factor = Math.ceil(nowPrice / 100);
			freight = factor * 10;
		} else if (nowPrice <= 100){
			freight = 20d;
		} 
		return freight;
	}
	
}
