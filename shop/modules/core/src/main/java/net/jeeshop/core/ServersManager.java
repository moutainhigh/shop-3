package net.jeeshop.core;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tuisitang.common.cache.memcached.MemcachedObjectType;
import com.tuisitang.common.cache.memcached.SpyMemcachedClient;
import com.tuisitang.modules.shop.entity.NotifyTemplate;

import net.jeeshop.core.dao.page.PagerModel;

/**    
 * @{#} ServersManager.java   
 * 
 * 后端Service的基类
 * 
 * 1. insert 新增
 * 2. delete 删除
 * 3. update 修改
 * 
 * 增删改时更新memcached的缓存信息
 *
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@SuppressWarnings("rawtypes")
public class ServersManager<E extends PagerModel> implements Services<E> {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	private DaoManager<E> dao;
	protected SpyMemcachedClient spyMemcachedClient;

	public DaoManager<E> getDao() {
		return dao;
	}

	public void setDao(DaoManager<E> dao) {
		this.dao = dao;
	}
	
	public SpyMemcachedClient getSpyMemcachedClient() {
		return spyMemcachedClient;
	}

	public void setSpyMemcachedClient(SpyMemcachedClient spyMemcachedClient) {
		this.spyMemcachedClient = spyMemcachedClient;
	}

	/**
	 * 添加
	 * 
	 * @param e
	 * @return
	 */
	public void insert(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		String className = e.getClass().getName();
		logger.info("insert className {}", className);
		dao.insert(e);
	}

	/**
	 * 删除
	 * 
	 * @param e
	 * @return
	 */
	public int delete(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		String className = e.getClass().getName();
		logger.info("delete className {}", className);
		return dao.delete(e);
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	public int deletes(Long[] ids) {
		if (ids == null || ids.length == 0) {
			throw new NullPointerException("id不能全为空！");
		}
		
		for (int i = 0; i < ids.length; i++) {
			if (ids[i] == null) {
				throw new NullPointerException("id不能为空！");
			}
			dao.deleteById(ids[i]);
		}
		return 0;
	}

	/**
	 * 修改
	 * 
	 * @param e
	 * @return
	 */
	public void update(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		String className = e.getClass().getName();
		logger.info("update className {}", className);
		dao.update(e);
		if (spyMemcachedClient != null) {
			if (e instanceof NotifyTemplate) {
				NotifyTemplate notifyTemplate = (NotifyTemplate) e;
				String key = NotifyTemplate.getKey(notifyTemplate.getType(), notifyTemplate.getCode());
				boolean b = spyMemcachedClient.safeSet(key,
						MemcachedObjectType.NotifyTemplate.getExpiredTime(),
						notifyTemplate.getTemplate());
				logger.info("NotifyTemplate safeSet {}, key {}", b, key);
			}
		}
	}

	/**
	 * 查询一条记录
	 * 
	 * @param e
	 * @return
	 */
	public E selectOne(E e) {
		return dao.selectOne(e);
	}

	/**
	 * 分页查询
	 * 
	 * @param e
	 * @return
	 */
	public PagerModel selectPageList(E e) {
		return dao.selectPageList(e);
	}

	public List<E> selectList(E e) {
		return dao.selectList(e);
	}

	@Override
	public E selectById(Long id) {
		return dao.selectById(id);
	}
}
