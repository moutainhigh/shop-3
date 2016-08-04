package com.tuisitang.modules.shop.dao;

import java.util.List;

/**    
 * @{#} BaseDao.java  
 * 
 * BaseDao
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public interface BaseDao<E extends Object> {

	/**
	 * 1. 添加Entity
	 * 
	 * @param e
	 * @return
	 */
	void insert(E e);

	/**
	 * 2. 删除Entity
	 * 
	 * @param e
	 * @return
	 */
	void delete(E e);

	/**
	 * 3. 修改Entity
	 * 
	 * @param e
	 * @return
	 */
	void update(E e);

	/**
	 * 4. 根据Entity查询一条记录
	 * 
	 * @param e
	 * @return
	 */
	E selectOne(E e);

	/**
	 * 5. 分页查询Entity
	 * 
	 * @param e
	 * @return
	 */
	List<E> selectPageList(E e);

	/**
	 * 6. 根据条件查询Entity
	 * 
	 * @return
	 */
	List<E> selectList(E e);

	/**
	 * 7. 根据ID来删除一条记录
	 * 
	 * @param id
	 */
	void deleteById(long id);

	/**
	 * 8. 根据ID查询一条记录
	 * 
	 * @param id
	 * @return
	 */
	E selectById(Long id);

	/**
	 * 9. 分页查询Entity
	 * 
	 * @param e
	 * @return
	 */
	int selectPageCount(E e);
	
	/**
	 * 10. 根据keywork查询
	 * 
	 * @param e
	 * @return
	 */
	E selectByKeyword(String keyword);

}
