package com.tuisitang.projects.pm.modules.shop.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.json.JSONException;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.resumableio.ResumeableIoApi;
import com.qiniu.api.rs.PutPolicy;
import com.tuisitang.common.test.SpringTransactionalContextTests;
import com.tuisitang.modules.shop.utils.Global;
import com.tuisitang.projects.pm.modules.shop.dao.ProductDao;
import com.tuisitang.projects.pm.modules.shop.dao.SupplierDao;
import com.tuisitang.projects.pm.modules.shop.dao.SupplierProductDao;
import com.tuisitang.projects.pm.modules.shop.entity.Product;
import com.tuisitang.projects.pm.modules.shop.entity.Supplier;
import com.tuisitang.projects.pm.modules.shop.entity.SupplierProduct;

public class SupplierProductImageServiceTest extends SpringTransactionalContextTests {
	
	private static final Logger logger = LoggerFactory.getLogger(SupplierProductImageServiceTest.class);

	@Autowired
	private SupplierDao supplierDao;
	@Autowired
	private SupplierProductDao supplierProductDao;
	@Autowired
	private ProductDao productDao;
	
	@Test
	@Rollback(false)
	public void processImage() {

//		String basePath = "/Users/xubin/Documents/Documents/雷立风行/上线/一批上线图片_2015-9-9/";
		String basePath = "/Users/xubin/Documents/Documents/雷立风行/上线/一批上线图片_2015-9-10/";
		List<Supplier> supplierList = supplierDao.findAllList();
		Mac mac = new Mac(Global.getQiniuAccessKey(), Global.getQiniuSecretKey());
		String bucketName = Global.getQiniuBucketName();

		for (Supplier supplier : supplierList) {
			DetachedCriteria dc = supplierProductDao.createDetachedCriteria();
			dc.createAlias("supplier", "supplier");
			dc.add(Restrictions.eq("supplier.delFlag", Supplier.DEL_FLAG_NORMAL));
			dc.add(Restrictions.eq("supplier.id", supplier.getId()));
			dc.add(Restrictions.eq(Product.DEL_FLAG, Product.DEL_FLAG_NORMAL));
			dc.addOrder(Order.asc("id"));
			
			List<SupplierProduct> spList = supplierProductDao.find(dc);
			
			String path = basePath + supplier.getName();
			File file = new File(path);
			if (file.exists()) {
//				logger.info("id {}, name {}, products.size = {}", supplier.getId(), supplier.getName(), spList.size());
				for (SupplierProduct sp : spList) {
					Product product = productDao.findBySourceId(sp.getId());
					if (product == null) {
//						throw new ServiceException("产品为空！");
						continue;
					}
//					if (StringUtils.isNotBlank(product.getImages())) {
//						continue;
//					}
					String prefix = "attached/images/" + product.getId() + "/";
					
					file = new File(path + "/image/" + sp.getProductCode());
					if (file.exists()) {
						uploadFile(bucketName, product, file, prefix, mac, productDao);
					} else {
						file = new File(path + "/image/" + sp.getProductCode() + ".jpg");
						if (!file.exists()) {
							logger.warn("id {}, name {}, product.name = {}, product.code = {}, 产品图片不存在！",
									supplier.getId(), supplier.getName(), product.getName(),product.getSourceCode());
						} else {
							uploadFile(bucketName, product, file, prefix, mac, productDao);
						}
					}
				}
			} else {
				logger.warn("id {}, name {}, products.size = {}, 图片不存在！", supplier.getId(), supplier.getName(), spList.size());
			}
		}

	}
	
	private void uploadFile(String bucketName, Product product, File file, String prefix, Mac mac, ProductDao productDao) {
		try {
			String mimeType = "image/jpeg";
			if (file.isFile()) {
				if (file.getName().toLowerCase().endsWith("png")) {
					mimeType = "image/png";
				}
				PutPolicy p = new PutPolicy(bucketName);
				String upToken = p.token(mac);
				FileInputStream fis = new FileInputStream(file);
				String key = prefix + file.getName();
				PutRet ret = ResumeableIoApi.put(fis, upToken, key, mimeType);
				product.setImages(key);
				productDao.save(product);
			} else {
				File[] fs = file.listFiles(new FilenameFilter() {
					@Override
					public boolean accept(File dir, String name) {
						return (name.toLowerCase().endsWith("png") || name
								.toLowerCase().endsWith("jpg"));
					}
				});
				
				String images = "";
				for(File f : fs){
					if (f.getName().toUpperCase().endsWith("png")) {
						mimeType = "image/png";
					}
					PutPolicy p = new PutPolicy(bucketName);
					String upToken = p.token(mac);
					FileInputStream fis = new FileInputStream(f);
					String key = prefix + f.getName();
					PutRet ret = ResumeableIoApi.put(fis, upToken, key, mimeType);
					images += key + ",";
				}
				product.setImages(images);
				productDao.save(product);
			}

		} catch (AuthException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
