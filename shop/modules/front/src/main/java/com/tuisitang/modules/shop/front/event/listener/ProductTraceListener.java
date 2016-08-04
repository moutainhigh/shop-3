package com.tuisitang.modules.shop.front.event.listener;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.gson.util.HttpKit;
import com.tuisitang.common.bo.CustomerBehaviorAction;
import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.front.event.ProductTraceEvent;
import com.tuisitang.modules.shop.front.service.ProductFrontService;
import com.tuisitang.modules.shop.utils.Global;

/**    
 * @{#} ProductTraceListener.java  
 * 
 * 商品浏览跟踪侦听
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@Service
public class ProductTraceListener extends AbstractApplicationListener implements ApplicationListener<ProductTraceEvent> {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductTraceListener.class);
	
	@Autowired
	private ProductFrontService productFrontService;

	@Override
	public void onApplicationEvent(ProductTraceEvent event) {
		Date sysTime = new Date(System.currentTimeMillis());
		Long productId = event.getProductId();
		String productName = event.getProductName();
		String sessionId = event.getSessionId();
		Account account = event.getAccount();
		logger.info("商品浏览跟踪 productId {}, sessionId {}, Account {}", productId, sessionId, account);
		synchronized (Global.class) {
			int hit = productFrontService.countProductHit(productId);
			logger.info("当前点击数 {}", hit);
			productFrontService.incrProductHit(productId, sessionId, account.getId(), productName);
		};
		if (account != null && account.getId() != null) {
			String name = StringUtils.isBlank(account.getTrueName()) ? account.getMobile() : account.getTrueName();
			String actionDetail = "用户" + name + "(" + account.getMobile() + ")浏览产品" + productName + "(" + productId + ")";
			HttpKit.push(account.getId(),
					CustomerBehaviorAction.Browse.getAction(), sysTime,
					actionDetail);
		}
	}

}
