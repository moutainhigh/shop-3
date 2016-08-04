package com.tuisitang.generate;

import java.util.Map;

import com.google.common.collect.Maps;

public class AreaMap implements java.io.Serializable {
	private Map<String, AreaData> map = Maps.newHashMap();

	public Map<String, AreaData> getMap() {
		return map;
	}

	public void setMap(Map<String, AreaData> map) {
		this.map = map;
	}

	@Override
	public String toString() {
		return "AreaMap [map=" + map + "]";
	}

}
