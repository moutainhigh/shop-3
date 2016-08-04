/**
 * 2012-7-8
 * jqsl2012@163.com
 */
package net.jeeshop.services.manage.indexImg.dao;

import java.util.List;

import net.jeeshop.core.DaoManager;
import com.tuisitang.modules.shop.entity.IndexImg;


public interface IndexImgDao extends DaoManager<IndexImg> {

	/**
	 * @param i
	 * @return
	 */
	List<IndexImg> getImgsShowToIndex(int i);

}
