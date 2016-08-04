package com.tuisitang.generate;

import java.io.File;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tuisitang.common.mapper.JsonMapper;
import com.tuisitang.modules.shop.dao.AreaDao;
import com.tuisitang.modules.shop.entity.Area;

public class AreaDataGenerate {

	private static final Logger logger = LoggerFactory.getLogger(AreaDataGenerate.class);
	
	private static final String FILE_NAME = "/Users/xubin/Documents/work2015/shop/modules/front/src/main/resources/__area.json";
	
	private static AreaDao areaDao;
	
	public static void main(String[] args) throws Exception {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] { "applicationContext.xml",
						"applicationContext-shiro.xml" });
		JsonMapper mapper = JsonMapper.getInstance();
		String s = FileUtils.readFileToString(new File(FILE_NAME), "UTF-8");
		
		areaDao = ctx.getBean(AreaDao.class);
		Area country = areaDao.selectById(1L);
		
		Map<String, Object> map = mapper.fromJson(s, Map.class);
		logger.info("{}", map);
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			AreaData areaData = mapper.fromJson(mapper.toJson(entry.getValue()), AreaData.class);
			logger.info("areaData {}", areaData);
			Area province = new Area();
			province.setCode(areaData.getCode());
			province.setName(areaData.getName());
			province.setParent(country);
			province.setParentIds(country.getParentIds() + country.getId() + ",");
			province.setType("2");
			areaDao.insert(province);
			for (AreaData cityData : areaData.getChildren()) {
				Area city = new Area();
				city.setCode(cityData.getCode());
				city.setName(cityData.getName());
				city.setParent(province);
				city.setParentIds(province.getParentIds() + province.getId() + ",");
				city.setType("3");
				areaDao.insert(city);
				for (AreaData countyData : cityData.getChildren()) {
					Area county = new Area();
					county.setCode(countyData.getCode());
					county.setName(countyData.getName());
					county.setParent(city);
					county.setParentIds(city.getParentIds() + city.getId() + ",");
					county.setType("4");
					areaDao.insert(county);
				}
			}
		}
	}

}
