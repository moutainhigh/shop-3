package com.tuisitang.common.mapper;

import com.tuisitang.common.mapper.JsonMapper;

import net.jeeshop.core.system.bean.MenuItem;

public class JsonMapperTest {

	public static void main(String[] args) {
		MenuItem mi = new MenuItem();
		System.out.println(mi.getTarget());
		JsonMapper mapper = JsonMapper.nonEmptyMapper();//JsonMapper.getInstance();
		
		System.out.println(mapper.toJson(mi));
	}

}
