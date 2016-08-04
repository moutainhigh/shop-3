package net.jeeshop.services.manage.area.service;

import java.util.List;

import net.jeeshop.services.manage.area.AreaService;

import com.tuisitang.modules.shop.entity.Area;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.common.mapper.JsonMapper;
import com.tuisitang.common.test.SpringTransactionalContextTests;

public class AreaServiceTest extends SpringTransactionalContextTests {

	@Autowired
	private AreaService areaService;
	
	@Test
	public void getTopArea() {
		Assert.assertNotNull(areaService);
		Area top = areaService.getTopArea();
		String json = JsonMapper.getInstance().toJson(top);
		logger.info("{}", json);
	}
}
