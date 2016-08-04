package net.jeeshop.services.manage.area.dao;

import net.jeeshop.services.manage.area.dao.AreaDao;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.common.test.SpringTransactionalContextTests;

public class AreaDaoTest extends SpringTransactionalContextTests {

	@Autowired
	private AreaDao areaDao;
	
	@Test
	public void selectListByParentId() {
		Assert.assertNotNull(areaDao);
		areaDao.selectListByParentId(-1L);
	}
}
