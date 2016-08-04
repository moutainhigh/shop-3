package com.tuisitang.modules.shop.front.event.listener;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.gson.util.HttpKit;
import com.tuisitang.common.bo.CustomerBehaviorAction;
import com.tuisitang.common.mapper.JsonMapper;
import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.entity.Order;
import com.tuisitang.modules.shop.entity.Orderdetail;
import com.tuisitang.modules.shop.entity.Product;
import com.tuisitang.modules.shop.entity.Spec;
import com.tuisitang.modules.shop.front.event.OrderPaymentCompleteEvent;
import com.tuisitang.modules.shop.front.service.ProductFrontService;

/**    
 * @{#} OrderPaymentCompleteListener.java  
 * 
 * 用户登出侦听
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@Service
public class OrderPaymentCompleteListener extends AbstractApplicationListener implements ApplicationListener<OrderPaymentCompleteEvent> {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderPaymentCompleteListener.class);

//	@Autowired
//	private SystemFrontService systemFrontService;
////	@Autowired
////	private OrderService orderService;
	@Autowired
	private ProductFrontService productFrontService;

	@Override
	public void onApplicationEvent(OrderPaymentCompleteEvent event) {
		Account account = event.getAccount();
		Order order = event.getOrder();
		Date systemTime = event.getSystemTime();
		
		logger.info("odList {}", order.getOdList());
		logger.info("order {}", order);
		logger.info("systemTime {}", systemTime);
		for (Orderdetail od : order.getOdList()) {
			long productId = od.getProductID();
			Long specId = od.getSpecId();
			int number = od.getNumber();
			if (specId != null && specId != 0L) {
				Spec spec = productFrontService.getProductSpec(productId, specId);
				if (spec != null) {
					spec.setSpecStock((new Integer(spec.getSpecStock()) - number) + "");
				}
			} else {
				Product product = productFrontService.getProductById(productId);
			}
		}
		JsonMapper mapper = JsonMapper.getInstance();
		String actionDetail = mapper.toJson(order);
		HttpKit.push(account.getId(), CustomerBehaviorAction.Pay.getAction(), systemTime, actionDetail);
	}

}
