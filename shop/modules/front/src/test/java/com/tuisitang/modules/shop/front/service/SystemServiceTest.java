package com.tuisitang.modules.shop.front.service;

public class SystemServiceTest {

	public static void main(String[] args) {
		//8e44cfac2faa3787f4775068feb5d19d1584796dace414e9547230e0
		//02a3f0772fcca9f415adc990734b45c6f059c7d33ee28362c4852032558d6919e60
		System.out.println(SystemFrontService.entryptPassword("admin"));
		System.out.println(SystemFrontService.validatePassword("admin", "362b690bf2235d4b45debd27c6c4ff5f67fe1a7d4904663f5ec8af8e"));
	}

}
