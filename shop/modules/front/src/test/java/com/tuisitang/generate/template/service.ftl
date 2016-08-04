package com.tuisitang.modules.shop.front.service;


import java.util.List;

import net.jeeshop.core.dao.page.PagerModel;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ${packageName}.${moduleName}.entity.${ClassName};

/**    
 * @{#} ${ClassName}ServiceTest.java  
 * 
 * ${ClassName} Service测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: ${classCompany}</p>
 * @version ${classVersion}
 * @author <a href="mailto:${classAuthor}@gmail.com">${classAuthor}</a>    
 */
public class ${ClassName}ServiceTest extends BaseServiceTest<${ClassName}> {

	@Autowired
	private ${ClassName}Service ${className}Service;

	private ${ClassName} ${className};

	@Before
	public void setUp() {
		${className} = new ${ClassName}();

		Assert.assertNotNull(${className}Service);
	}

	@After
	public void tearDown() {

	}

	/**
	 * 1. 添加${ClassName}
	 */
	@Test
	@Override
	public void insert() {
		// TODO
		logger.info("添加${ClassName} {}", ${className});
		${className}Service.insert(${className});
		Assert.assertNotNull(${className}.getId());
	}

	/**
	 * 2. 删除${ClassName}
	 */
	@Test
	@Override
	public void delete() {
		// TODO
		logger.info("删除${ClassName} {}", ${className});
		Assert.assertNotNull(${className});
		Long id = ${className}.getId();
		Assert.assertNotNull(id);
		${className}Service.delete(${className});
		Assert.assertNull(${className}Service.selectById(id));
	}
	
	/**
	 * 3. 根据ids批量删除${ClassName}
	 */
	@Override
	public void deletes() {
		Long[] ids = {};
		// TODO
		logger.info("根据ids批量删除Entity {}", ids);
		Assert.assertNotNull(ids);
		${className}Service.deletes(ids);
	}

	/**
	 * 4. 修改${ClassName}
	 */
	@Test
	@Override
	public void update() {
		// TODO
		logger.info("修改${ClassName} {}", ${className});
		Assert.assertNotNull(${className});
		Long id = ${className}.getId();
		Assert.assertNotNull(id);
		${className}Service.update(${className});
		Assert.assertEquals("", "");
	}

	/**
	 * 5. 根据${ClassName}查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		// TODO
		logger.info("根据${ClassName}查询一条记录 {}", ${className});
		Assert.assertNotNull(${className});
		Assert.assertNotNull(${className}Service.selectOne(${className}));
	}
	
	/**
	 * 6. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		Long id = null;
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		${ClassName} entity = ${className}Service.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 7. 分页查询${ClassName}
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		logger.info("分页查询${ClassName} {}", ${className});
		Assert.assertNotNull(${className});
		PagerModel<${ClassName}> page = ${className}Service.selectPageList(${className});
		Assert.assertNotNull(page);
	}

	/**
	 * 8. 根据条件查询${ClassName}
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询${ClassName} {}", ${className});
		Assert.assertNotNull(${className});
		List<${ClassName}> list = ${className}Service.selectList(${className});
		Assert.assertNull(list);
	}

}