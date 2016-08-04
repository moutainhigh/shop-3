package com.tuisitang.modules.shop.front.service;

import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tuisitang.common.cache.memcached.SpyMemcachedClient;
import com.tuisitang.common.mapper.JsonMapper;
import com.tuisitang.modules.shop.dao.ActivityDao;
import com.tuisitang.modules.shop.dao.AttributeDao;
import com.tuisitang.modules.shop.dao.CatalogDao;
import com.tuisitang.modules.shop.dao.FavoriteDao;
import com.tuisitang.modules.shop.dao.GiftDao;
import com.tuisitang.modules.shop.dao.IndexImgDao;
import com.tuisitang.modules.shop.dao.KeyvalueDao;
import com.tuisitang.modules.shop.dao.ProductDao;
import com.tuisitang.modules.shop.dao.ProductHitDao;
import com.tuisitang.modules.shop.dao.ProductHitTraceDao;
import com.tuisitang.modules.shop.dao.SpecDao;
import com.tuisitang.modules.shop.entity.Activity;
import com.tuisitang.modules.shop.entity.Spec;
import com.tuisitang.modules.shop.front.vo.ProductVo;

/**    
 * @{#} ProductService.java  
 * 
 * 产品Service
 *    
 * <p>Copyright: Copyright(c) 2013 </p> 
 * <p>Company: TST</p>
 * @version 1.0
 * @author stephen   
 */
@Service
public class ProductFrontService extends com.tuisitang.modules.shop.service.ProductService {
	private static final Logger logger = LoggerFactory.getLogger(SystemFrontService.class);
	@Autowired
	private SpecDao specDao;
	/**
	 * 保存产品规格
	 */
	public void setProductSpec(ProductVo productVo, Activity activity) {
		Spec spec = new Spec();
		spec.setSpecStatus(Spec.spec_specStatus_y);
		spec.setProductID(productVo.getProduct().getId());
		List<Spec> specList = specDao.selectList(spec);
		if(specList!=null && specList.size()>0){
			if(productVo.getSpecType()==null){
				productVo.setSpecType(new HashSet<String>());
			}
			if(productVo.getSpecColor()==null){
				productVo.setSpecColor(new HashSet<String>());
			}
			if(productVo.getSpecSize()==null){
				productVo.setSpecSize(new HashSet<String>());
			}
			//分离商品的类型 ,尺寸和颜色
			for(int i=0;i<specList.size();i++){
				Spec specItem = specList.get(i);
				specItem.setSpecPrice(caclSpecFinalPrice(specItem, activity));
				if (!Spec.spec_specStatus_y.equals(specItem.getSpecStatus())) {
					continue;
				}
				if(StringUtils.isNotBlank(specItem.getSpecType())){
					productVo.getSpecType().add(specItem.getSpecType());
				}
				if(StringUtils.isNotBlank(specItem.getSpecColor())){
					productVo.getSpecColor().add(specItem.getSpecColor());
				}
				if(StringUtils.isNotBlank(specItem.getSpecSize())){
					productVo.getSpecSize().add(specItem.getSpecSize());
				}
			}
			productVo.setSpecJsonString(JsonMapper.getInstance().toJson(specList));
			logger.info("product.setSpecJsonString = " + productVo.getSpecJsonString());
		}else {
			productVo.setSpecJsonString("[]");
		}
	}
}
