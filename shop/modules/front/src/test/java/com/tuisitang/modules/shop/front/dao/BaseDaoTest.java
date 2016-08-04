package com.tuisitang.modules.shop.front.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tuisitang.common.test.SpringTransactionalContextTests;

/**    
 * @{#} BaseDaoTest.java  
 * 
 * Dao测试基类
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public abstract class BaseDaoTest<E> extends SpringTransactionalContextTests {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 1. 添加Entity
	 */
	public abstract void insert();

	/**
	 * 2. 删除Entity
	 */
	public abstract void delete();

	/**
	 * 3. 修改Entity
	 */
	public abstract void update();

	/**
	 * 4. 根据Entity查询一条记录
	 */
	public abstract void selectOne();

	/**
	 * 5. 分页查询Entity
	 * 
	 * @param e
	 */
	public abstract void selectPageList();

	/**
	 * 6. 根据条件查询Entity
	 */
	public abstract void selectList();

	/**
	 * 7. 根据ID来删除一条记录
	 */
	public abstract void deleteById();

	/**
	 * 8. 根据ID查询一条记录
	 */
	public abstract void selectById();

	/**
	 * 9. 分页查询Entity
	 */
	public abstract void selectPageCount();

}
