package com.tuisitang.modules.shop.front.event.listener;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.tuisitang.common.bo.NotifyType;
import com.tuisitang.common.utils.DateUtils;
import com.tuisitang.common.utils.FreeMarkers;
import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.entity.BookingOrder;
import com.tuisitang.modules.shop.entity.BookingOrderLog;
import com.tuisitang.modules.shop.entity.NotifyTemplate;
import com.tuisitang.modules.shop.front.event.BookingOrderCreateEvent;
import com.tuisitang.modules.shop.front.service.SystemFrontService;
import com.tuisitang.modules.shop.service.OrderService;

/**    
 * @{#} BookingOrderCreateListener.java  
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@Service
public class BookingOrderCreateListener extends AbstractApplicationListener implements ApplicationListener<BookingOrderCreateEvent> {
	
	private static final Logger logger = LoggerFactory.getLogger(BookingOrderCreateListener.class);
	
	@Autowired
	private OrderService orderService;
	@Autowired
	private SystemFrontService systemFrontService;

	@Override
	public void onApplicationEvent(BookingOrderCreateEvent event) {
		Account account = event.getAccount();
		BookingOrder bookingOrder = event.getBookingOrder();
		Map<String, Object> requestMap = event.getRequestMap();
		logger.info("account {}, bookingOrder {}, requestMap {}", account, bookingOrder, requestMap);
		String type = NotifyType.BookingSuccess.getType();
		String template = systemFrontService.getNotifyTemplate(NotifyTemplate.TYPE_SMS, type);// 预约后通知用户
		Map<String, String> m = Maps.newHashMap();
		if (!StringUtils.isBlank(template)) {
			m.put("bookingOrderNo", bookingOrder.getNo());
			String content = FreeMarkers.renderString(template, m);
			sendSms(account,account.getMobile(),type,content);
		}
		BookingOrderLog bookingOrderLog = new BookingOrderLog(bookingOrder.getId(), BookingOrderLog.ACTION_NEW, "【新预约单】时间："
						+ DateUtils.formatDate(bookingOrder.getBookingTime(), "yyyyMMdd HH:mm") + "，地点："
						+ bookingOrder.getAddress(), account.getId(), account.getNickname(), BookingOrderLog.OPERATE_TYPE_FRONT);
		orderService.saveBookingOrderLog(bookingOrderLog);
	}

}
