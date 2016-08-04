/**
 * 2012-7-8
 * jqsl2012@163.com
 */
package net.jeeshop.services.manage.indexFloor.dao.impl;

import java.util.List;

import com.tuisitang.modules.shop.entity.IndexFloor;

import net.jeeshop.core.dao.BaseDao;
import net.jeeshop.core.dao.page.PagerModel;
import net.jeeshop.services.manage.indexFloor.dao.IndexFloorDao;


/**
 * @author huangf
 */
public class IndexFloorDaoImpl implements IndexFloorDao {
	private BaseDao dao;

	public void setDao(BaseDao dao) {
		this.dao = dao;
	}

	public PagerModel selectPageList(IndexFloor e) {
		return dao.selectPageList("manage.indexFloor.selectPageList",
				"manage.indexFloor.selectPageCount", e);
	}

	public List selectList(IndexFloor e) {
		return dao.selectList("manage.indexFloor.selectList", e);
	}

	public IndexFloor selectOne(IndexFloor e) {
		return (IndexFloor) dao.selectOne("manage.indexFloor.selectOne", e);
	}

	public int delete(IndexFloor e) {
		return dao.delete("manage.indexFloor.delete", e);
	}

	public int update(IndexFloor e) {
		return  dao.update("manage.indexFloor.update", e);
	}

	/**
	 * 批量删除用户
	 * 
	 * @param ids
	 */
	public int deletes(Long[] ids) {
		IndexFloor e = new IndexFloor();
		for (int i = 0; i < ids.length; i++) {
			e.setId(ids[i]);
			delete(e);
		}
		return 0;
	}

	public int insert(IndexFloor e) {
		return  dao.insert("manage.indexFloor.insert", e);
	}

	/**
	 * @param bInfo
	 */
	public List<IndexFloor> getLoseList(IndexFloor bInfo) {
		return dao.selectList("manage.indexFloor.getLoseList", bInfo);
	}
	
	@Override
	public int deleteById(long id) {
		return dao.delete("manage.indexFloor.deleteById",id);
	}

	public IndexFloor selectById(Long id) {
		return (IndexFloor) dao.selectOne("manage.indexFloor.selectById",id);
	}
}
