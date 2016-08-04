/**
 * 2012-7-8
 * jqsl2012@163.com
 */
package net.jeeshop.services.manage.indexFloor.impl;

import java.util.List;

import net.jeeshop.core.ServersManager;
import net.jeeshop.services.manage.indexFloor.IndexFloorService;
import net.jeeshop.services.manage.indexFloor.dao.IndexFloorDao;

import com.tuisitang.modules.shop.entity.IndexFloor;


/**
 * @author huangf
 */
public class IndexFloorServiceImpl extends ServersManager<IndexFloor> implements
		IndexFloorService {

	private IndexFloorDao indexImgDao;

	public IndexFloorDao getIndexFloorDao() {
		return indexImgDao;
	}

	public void setIndexFloorDao(IndexFloorDao indexImgDao) {
		this.indexImgDao = indexImgDao;
	}

}
