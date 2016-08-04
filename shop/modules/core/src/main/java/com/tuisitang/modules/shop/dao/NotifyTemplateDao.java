package com.tuisitang.modules.shop.dao;

import org.apache.ibatis.annotations.Param;

import com.tuisitang.modules.shop.entity.NotifyTemplate;

/**    
 * @{#} NotifyTemplateDao.java  
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface NotifyTemplateDao extends BaseDao<NotifyTemplate> {
	
	/**
	 * 10. 根据type code获得模板
	 * 
	 * @param type
	 * @param code
	 * @return
	 */
	String getByTypeAndCode(@Param("type") String type, @Param("code") String code);
	
}
