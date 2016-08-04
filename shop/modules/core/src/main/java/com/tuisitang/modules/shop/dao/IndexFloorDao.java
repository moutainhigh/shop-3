package com.tuisitang.modules.shop.dao;

import java.util.List;

import com.tuisitang.modules.shop.entity.IndexFloor;
/**    
 * 
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author <a href="mailto:paninxb@gmail.com">panin</a>   
 */
@MyBatisRepository
public interface IndexFloorDao extends BaseDao<IndexFloor> {
	/**
	 *  根据floor查询IndexFloor
	 * 
	 * @param e
	 * @return
	 */
	List<IndexFloor> getIndexImgByFloor(String floor);
}
