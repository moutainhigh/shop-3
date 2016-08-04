package com.tuisitang.modules.shop.front.service;

import net.jeeshop.core.dao.page.PagerModel;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tuisitang.common.test.SpringTransactionalContextTests;
import com.tuisitang.modules.shop.entity.Order;
import com.tuisitang.modules.shop.entity.Orderdetail;
import com.tuisitang.modules.shop.service.AccountService;

/**    
 * @{#} AccountFrontServiceTest.java  
 * 
 * AccountFrontService测试
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
public class AccountServiceTest extends SpringTransactionalContextTests {

	@Autowired
	private AccountService accountService;

	@Test
	public void findPageOrder() {
		Long accountId = 64L;
		int pageSize = 200;
		int pageNo = 1;
		int type = 0;
		PagerModel<Order> page = accountService.findPageOrder(accountId, type,
				pageSize, pageNo);
		logger.info("total = {}, list = {}", page.getTotal(), page.getList());
		for (Order order : page.getList()) {
			logger.info("Ordership {}, odList.size {}", order.getOrdership(), order.getOdList().size());
			for (Orderdetail od : order.getOdList()) {
				logger.info("Orderdetail {}", od.getId());
			}
		}
	}

}
