package com.tuisitang.common.test;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;

/**    
 * @{#} SpringTransactionalContextTests.java  
 * 
 * Spring 单元测试基类
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@ActiveProfiles("production")
@ContextConfiguration(locations = { "/applicationContext.xml" })
@TransactionConfiguration(defaultRollback = false) 
public class SpringTransactionalContextTests extends AbstractTransactionalJUnit4SpringContextTests {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected DataSource dataSource;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
		this.dataSource = dataSource;
	}

}
