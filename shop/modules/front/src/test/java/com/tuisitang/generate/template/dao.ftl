package ${packageName}.${moduleName}.dao;


import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import ${packageName}.${moduleName}.entity.${ClassName};

/**    
 * @{#} ${ClassName}DaoTest.java  
 * 
 * ${ClassName} Dao测试
 *    
 * <p>Copyright: Copyright(c) 2015 </p> 
 * <p>Company: ${classCompany}</p>
 * @version ${classVersion}
 * @author <a href="mailto:${classAuthor}@gmail.com">${classAuthor}</a>   
 *  
 */
public class ${ClassName}DaoTest extends BaseDaoTest<${ClassName}> {

	@Autowired
	private ${ClassName}Dao ${className}Dao;

	private ${ClassName} ${className};

	@Before
	public void setUp() {
		${className} = new ${ClassName}();

		Assert.assertNotNull(${className}Dao);
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
		${className}Dao.insert(${className});
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
		${className}Dao.delete(${className});
		Assert.assertNull(${className}Dao.selectById(id));
	}

	/**
	 * 3. 修改${ClassName}
	 */
	@Test
	@Override
	public void update() {
		// TODO
		logger.info("修改${ClassName} {}", ${className});
		Assert.assertNotNull(${className});
		Long id = ${className}.getId();
		Assert.assertNotNull(id);
		${className}Dao.update(${className});
		Assert.assertEquals("", "");
	}

	/**
	 * 4. 根据${ClassName}查询一条记录
	 */
	@Test
	@Override
	public void selectOne() {
		// TODO
		logger.info("根据${ClassName}查询一条记录 {}", ${className});
		Assert.assertNotNull(${className});
		Assert.assertNotNull(${className}Dao.selectOne(${className}));
	}

	/**
	 * 5. 分页查询${ClassName}
	 * 
	 * @param e
	 */
	@Test
	@Override
	public void selectPageList() {
		// TODO
		logger.info("分页查询${ClassName} {}", ${className});
		Assert.assertNotNull(${className});
		List<${ClassName}> list = ${className}Dao.selectPageList(${className});
		Assert.assertNotNull(list);
	}

	/**
	 * 6. 根据条件查询${ClassName}
	 */
	@Override
	public void selectList() {
		// TODO
		logger.info("根据条件查询${ClassName} {}", ${className});
		Assert.assertNotNull(${className});
		List<${ClassName}> list = ${className}Dao.selectList(${className});
		Assert.assertNull(list);
	}

	/**
	 * 7. 根据ID来删除一条记录
	 */
	@Override
	public void deleteById() {
		Long id = null;
		// TODO
		logger.info("根据ID来删除一条记录 {}", id);
		Assert.assertNotNull(id);
		${className}Dao.deleteById(id);
	}

	/**
	 * 8. 根据ID查询一条记录
	 */
	@Test
	@Override
	public void selectById() {
		Long id = null;
		// TODO
		logger.info("根据ID查询一条记录 {}", id);
		Assert.assertNotNull(id);
		${ClassName} entity = ${className}Dao.selectById(id);
		Assert.assertNotNull(entity);
		Assert.assertEquals(id, entity.getId());
	}

	/**
	 * 9. 分页Count ${ClassName}
	 */
	@Test
	@Override
	public void selectPageCount() {
		// TODO
		logger.info("分页Count ${ClassName} {}", ${className});
		Assert.assertNotNull(${className});
		int count = ${className}Dao.selectPageCount(${className});
	}

}


