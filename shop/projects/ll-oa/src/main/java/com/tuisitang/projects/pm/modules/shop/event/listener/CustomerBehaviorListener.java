package com.tuisitang.projects.pm.modules.shop.event.listener;

import net.jeeshop.core.util.DateUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.tuisitang.common.bo.CustomerBehaviorAction;
import com.tuisitang.common.bo.SmsPlatformType;
import com.tuisitang.common.mapper.JsonMapper;
import com.tuisitang.common.sms.SMSClientFactory;
import com.tuisitang.modules.shop.entity.Order;
import com.tuisitang.projects.pm.modules.shop.entity.Customer;
import com.tuisitang.projects.pm.modules.shop.entity.CustomerBehavior;
import com.tuisitang.projects.pm.modules.shop.event.CustomerBehaviorEvent;
import com.tuisitang.projects.pm.modules.shop.service.CustomerService;
import com.tuisitang.projects.pm.modules.sys.event.LoginEvent;
import com.tuisitang.projects.pm.modules.sys.event.listener.AbstractApplicationListener;
import com.tuisitang.projects.pm.modules.sys.service.SystemService;

/**    
 * @{#} LoginListener.java  
 * 
 * 用户登录侦听
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@Service
public class CustomerBehaviorListener extends AbstractApplicationListener implements ApplicationListener<CustomerBehaviorEvent> {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerBehaviorListener.class);
	
	@Autowired
	private SystemService systemService;
	@Autowired
	private CustomerService customerService;

	@Override
	public void onApplicationEvent(CustomerBehaviorEvent event) {
		Long customerId = event.getCustomerId();
		String action = event.getAction();
		String actionDate = event.getActionDate();
		String actionDetail = event.getActionDetail();
		if (customerId == null) {
			logger.error("CustomerId is Null");
			return;
		}
		Customer customer = customerService.findCustomer(customerId);
		if (customer == null) {
			logger.error("customer is Null");
			return;
		}
		String content = "客户:" + customer.getMobile() + "于" + actionDate + ""
				+ action + "。<br/>操作详情如下：" + actionDetail;
		CustomerBehavior customerBehavior = new CustomerBehavior();
		customerBehavior.setAction(action);
		customerBehavior.setActionDate(DateUtils.parseDate(actionDate));
		customerBehavior.setCustomer(customer);
		customerBehavior.setDetail(actionDetail);
		customerService.saveCustomerBehavior(customerBehavior);
		if (CustomerBehaviorAction.Login.getAction().equals(action)) {// 客户登录
			sendEmail(action, "247913008@qq.com", content);
		} else if (CustomerBehaviorAction.Browse.getAction().equals(action)) {// 客户浏览商品

		} else if (CustomerBehaviorAction.Search.getAction().equals(action)) {// 客户查询商品

		} else if (CustomerBehaviorAction.Cart.getAction().equals(action)) {// 客户购买商品

		} else if (CustomerBehaviorAction.Order.getAction().equals(action)) {// 客户下单

		} else if (CustomerBehaviorAction.Pay.getAction().equals(action)) {// 客户支付完成
			Order order = JsonMapper.getInstance().fromJson(actionDetail, Order.class);
			String message = "客户:" + customer.getMobile() + "于" + actionDate + "完成支付，订单号：" + order.getNo();
//			sendEmail(action, "247913008@qq.com", message);
			sendEmail(action, "admin@baoxiliao.com", message);
			SMSClientFactory.getClient(SmsPlatformType.CL.getType()).sendSMS("18020260877,18080860877", message);
		}
		sendEmail(action, "admin@baoxiliao.com", content);
	}
	
}
