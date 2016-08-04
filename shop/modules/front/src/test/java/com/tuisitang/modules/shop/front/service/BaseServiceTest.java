package com.tuisitang.modules.shop.front.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tuisitang.common.test.SpringTransactionalContextTests;

/**    
 * @{#} BaseServiceTest.java  
 * 
 * Service测试基类
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public abstract class BaseServiceTest<E> extends
		SpringTransactionalContextTests {

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
	 * 3. 根据ids批量删除Entity
	 */
	public abstract void deletes();

	/**
	 * 4. 修改Entity
	 */
	public abstract void update();

	/**
	 * 5. 根据Entity查询一条记录
	 */
	public abstract void selectOne();

	/**
	 * 6. 根据ID查询一条记录
	 */
	public abstract void selectById();

	/**
	 * 7. 分页查询Entity
	 * 
	 * @param e
	 */
	public abstract void selectPageList();

	/**
	 * 8. 根据条件查询Entity
	 */
	public abstract void selectList();

}
