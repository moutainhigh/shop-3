package com.tuisitang.modules.shop.dao;

import com.tuisitang.modules.shop.entity.Express;
import com.tuisitang.modules.shop.entity.ExpressCompany;

/**    
 * @{#} ExpressCompanyDao.java 
 * 
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface ExpressCompanyDao extends BaseDao<ExpressCompany> {
	
	/**
	 * 10. 根据code获得ExpressCompany
	 * 
	 * @param code
	 * @return
	 */
	ExpressCompany getByCode(String code);
	
}
