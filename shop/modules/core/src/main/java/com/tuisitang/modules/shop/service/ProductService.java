package com.tuisitang.modules.shop.service;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.jeeshop.core.dao.page.PagerModel;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.tuisitang.common.bo.PictureSize;
import com.tuisitang.common.cache.memcached.MemcachedObjectType;
import com.tuisitang.common.cache.memcached.SpyMemcachedClient;
import com.tuisitang.common.service.ServiceException;
import com.tuisitang.modules.shop.dao.ActivityDao;
import com.tuisitang.modules.shop.dao.AttributeDao;
import com.tuisitang.modules.shop.dao.CatalogDao;
import com.tuisitang.modules.shop.dao.FavoriteDao;
import com.tuisitang.modules.shop.dao.GiftDao;
import com.tuisitang.modules.shop.dao.IndexFloorDao;
import com.tuisitang.modules.shop.dao.IndexImgDao;
import com.tuisitang.modules.shop.dao.KeyvalueDao;
import com.tuisitang.modules.shop.dao.ProductDao;
import com.tuisitang.modules.shop.dao.ProductHitDao;
import com.tuisitang.modules.shop.dao.ProductHitTraceDao;
import com.tuisitang.modules.shop.dao.ProductSellCountDao;
import com.tuisitang.modules.shop.dao.SellerDao;
import com.tuisitang.modules.shop.dao.SpecDao;
import com.tuisitang.modules.shop.entity.Account;
import com.tuisitang.modules.shop.entity.Activity;
import com.tuisitang.modules.shop.entity.Attribute;
import com.tuisitang.modules.shop.entity.Catalog;
import com.tuisitang.modules.shop.entity.Favorite;
import com.tuisitang.modules.shop.entity.Gift;
import com.tuisitang.modules.shop.entity.IndexFloor;
import com.tuisitang.modules.shop.entity.IndexImg;
import com.tuisitang.modules.shop.entity.Product;
import com.tuisitang.modules.shop.entity.ProductAttribute;
import com.tuisitang.modules.shop.entity.ProductHit;
import com.tuisitang.modules.shop.entity.ProductHitTrace;
import com.tuisitang.modules.shop.entity.ProductSellCount;
import com.tuisitang.modules.shop.entity.Seller;
import com.tuisitang.modules.shop.entity.Spec;
import com.tuisitang.modules.shop.utils.Global;

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
public class ProductService {
	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
	@Autowired(required = false)
	private SpyMemcachedClient memcachedClient;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private GiftDao giftDao;
	@Autowired
	private CatalogDao categoryDao;
	@Autowired
	private SpecDao specDao;
	@Autowired
	private ActivityDao activityDao;
	@Autowired
	private CatalogDao catalogDao;
	@Autowired
	private KeyvalueDao keyvalueDao;
	@Autowired
	private FavoriteDao favoriteDao;
	@Autowired
	private IndexImgDao indexImgDao;
	@Autowired
	private AttributeDao attributeDao;
	@Autowired
	private ProductHitDao productHitDao;
	@Autowired
	private ProductHitTraceDao productHitTraceDao;
	@Autowired
	private SellerDao sellerDao;
	@Autowired
	private IndexFloorDao indexFloorDao;
	@Autowired
	private ProductSellCountDao productSellCountDao;
	
	
	/**
	 * 根据id获得Product
	 * 
	 * @param id
	 * @return
	 */
	public Product getProductById(Long id) {
		logger.debug("getProductById id = {}", id);
		Product product = null;
//		if (memcachedClient != null) {
//			product = memcachedClient.get(Product.getKey(id));
//			if (product != null) {
//				return product;
//			}
//		}
		product = productDao.selectById(id);
		if (product != null && memcachedClient != null) {// 如果Product不为空，则缓存至Memcached
			memcachedClient.safeSet(Product.getKey(id), 
					MemcachedObjectType.Product.getExpiredTime(), product);
		}
		return product;
	}
	
	/**
	 * 根据产品Id查询产品属性
	 */
	public List<ProductAttribute> selectParameterList(Long id) {
		logger.debug("selectParameterList id = {}", id);
		List<ProductAttribute> productAttribute = null;
		if (memcachedClient != null) {
			productAttribute = memcachedClient.get(Product.getProductAttributeKey(id));
			if (productAttribute != null && !productAttribute.isEmpty()) {
				return productAttribute;
			}
		}
		productAttribute = productDao.selectParameterList(id);
		if (productAttribute != null && memcachedClient != null) {// 如果Product不为空，则缓存至Memcached
			memcachedClient.safeSet(Product.getProductAttributeKey(id),
					MemcachedObjectType.PRODUCT_ATTRIBTE.getExpiredTime(), productAttribute);
		}
		return productAttribute;
	}
	
	/**
	 * 更新点击数
	 */
	public void updateHit(Long id) {
		productDao.updateHit(id);
	}
	
	/**
	 * 更新产品缓存
	 * 
	 * @param id
	 */
	private void updateProductCache(Long id) {
		if (memcachedClient != null) {
			Product product = productDao.selectById(id);
			if (product != null) {// 如果Account不为空，则缓存至Memcached
				memcachedClient.safeSet(Account.getKey(id), 
						MemcachedObjectType.Product.getExpiredTime(), product);
			}
		}
	}
	
	/**
	 * 根据id获得Gift
	 * 
	 * @param id
	 * @return
	 */
	public Gift getGiftById(Long id) {
		logger.debug("getGiftById id = {}", id);
		Gift gift = null;
		if (memcachedClient != null) {
			gift = memcachedClient.get(Gift.getKey(id));
			if (gift != null) {
				return gift;
			}
		}
		gift = giftDao.selectById(id);
		if (gift != null && memcachedClient != null) {// 如果Product不为空，则缓存至Memcached
			memcachedClient.safeSet(Gift.getKey(id), 
					MemcachedObjectType.Gift.getExpiredTime(), gift);
		}
		return gift;
	}
	
	/**
	 * 根据id获得商品活动
	 * 
	 * @param id
	 * @return
	 */
	public Activity getActivityById(Long id) {
		logger.debug("getActivityById id = {}", id);
		Activity activity = null;
		if (memcachedClient != null) {
			activity = memcachedClient.get(Activity.getKey(id));
			if (activity != null) {
				return activity;
			}
		}
		activity = activityDao.selectById(id);
		if (activity != null && memcachedClient != null) {
			memcachedClient.safeSet(Activity.getKey(id), 
					MemcachedObjectType.Activity.getExpiredTime(), activity);
		}
		return activity;
	}
	
	/**
	 * 根据id获得Catalog
	 * 
	 * @param id
	 * @return
	 */
	public Catalog getCatalogById(Long id) {
		logger.debug("getCatalogById id = {}", id);
		Catalog catalog = null;
		if (memcachedClient != null) {
			catalog = memcachedClient.get(Catalog.getKey(id));
			if (catalog != null) {
				return catalog;
			}
		}
		catalog = categoryDao.selectById(id);
		if (catalog != null && memcachedClient != null) {
			memcachedClient.safeSet(Catalog.getKey(id), 
					MemcachedObjectType.Catalog.getExpiredTime(), catalog);
		}
		return catalog;
	}
	
	/**
	 * 根据产品id获得其目录
	 * 
	 * @param id
	 * @return
	 */
	public List<Catalog> getCatalogByProduct(Long productId) {
		logger.debug("getCatalogByProduct id = {}", productId);
		List<Catalog> catalogList = null;
		if (memcachedClient != null) {
			catalogList = memcachedClient.get(Catalog.getProductKey(productId));
			if (catalogList != null && !catalogList.isEmpty()) {
				return catalogList;
			}
		}
		catalogList = categoryDao.getCatalogByProduct(productId);
		if (catalogList != null && memcachedClient != null) {
			memcachedClient.safeSet(Catalog.getProductKey(productId), 
					MemcachedObjectType.Catalog.getExpiredTime(),catalogList);
		}
		return catalogList;
	}
	
	/**
	 * 获取指定目录下所有子目录
	 * 如果类别ID是是主类别，则返回该主类别的下面所有子类别
	 * @param catalogID
	 * @return
	 */
//	public List<Catalog> getCatalogChildren(String catalogId) {
//		
//	}
	
	
	/**
	 * 根据产品Id获得产品收藏总数
	 */
	public int countFavoriteByProductId(Long id) {
		logger.debug("countFavoriteByProductId id = {}", id);
		Integer count = 0;
		if (memcachedClient != null) {
			count = memcachedClient.get(Favorite.getCountProductKey(id));
			if (count != null && count != 0) {
				return count;
			}
		}
		count = favoriteDao.selectCountByProductId(id);
		if (count != 0 && memcachedClient != null) {// 如果Product不为空，则缓存至Memcached
			memcachedClient.safeSet(Favorite.getCountProductKey(id), 
					MemcachedObjectType.Favorite.getExpiredTime(), count);
		}
		return count;
	}
	
	/**
	 * 添加产品到收藏
	 */
	public void saveFavorite(Favorite favorite) {
		favoriteDao.insert(favorite);
	}
	
	/**
	 * 统计产品是否已加入到此用户收藏列表
	 */
	public boolean isSavedInFavorite(Favorite favorite) {
		int count = favoriteDao.selectCount(favorite.getAccountId(), favorite.getProductId());
		if (count > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 根据类型获取所有目录
	 */
	public List<Catalog> getAllCatalog(String type) {
		logger.debug("getAllCatalog type = {}", type);
		List<Catalog> catalogList = null;
		if (memcachedClient != null) {
			catalogList = memcachedClient.get(Catalog.getCatalogTypeKey(type));
			if (catalogList != null && !catalogList.isEmpty()) {
				return catalogList;
			}
		}
		catalogList = loadCatalogsByType(type);
		if (catalogList != null && !catalogList.isEmpty() && memcachedClient != null) {// 
			memcachedClient.safeSet(Catalog.getCatalogTypeKey(type),
					MemcachedObjectType.CATALOG_TYPE_LIST.getExpiredTime(), catalogList);
		}
		return catalogList;
	}
	
	/**
	 * 获取目录子目录
	 * @param catalogId
	 * @return
	 */
	public List<Catalog> getSubCatalog(Long catalogId) {
		List<Catalog> catalogList = null;
		if (memcachedClient != null) {
			catalogList = memcachedClient.get(Catalog.getSubCatalogKey(catalogId));
			if (catalogList != null && !catalogList.isEmpty()){
				return catalogList;
			}
		}
		catalogList = catalogDao.getSubCatalog(catalogId);
		if (catalogList != null && !catalogList.isEmpty() && memcachedClient != null) {
			memcachedClient.safeSet(Catalog.getSubCatalogKey(catalogId),
					MemcachedObjectType.CATALOG_SUB_LIST.getExpiredTime(), catalogList);
		}
		return catalogList;
	}
	
	/**
	 * 获取指定目录所有子目录Id
	 * @param catalogId
	 * @return
	 */
	public List<Long> getSubCatalogIds(Long catalogId, List<Catalog> catalogs) {
		for (Catalog c1 : catalogs) {
			if (c1.getId().longValue() == catalogId.longValue()) {
				if (c1.getChildren() == null || c1.getChildren().isEmpty()) {
					return null;
				} 
				return getSubCatalogIds(c1.getChildren());
			} else {
				if (c1.getChildren() != null && !c1.getChildren().isEmpty()) {
					List<Long> result = getSubCatalogIds(catalogId, c1.getChildren());
					if (result != null) {
						return result;
					}
				}
			}
		}
		return null;
	} 
	
	/**
	 * 递归遍历到指定目录并返回其所有子目录的Id
	 * @param children
	 * @return
	 */
	private List<Long> getSubCatalogIds(List<Catalog> catalogs) {
		List<Long> resultIds = Lists.newLinkedList();
		for (Catalog catalog : catalogs) {
			resultIds.add(catalog.getId());
			if (catalog.getChildren() != null && catalog.getChildren().isEmpty()) {
				List<Long> subCatalogIds = getSubCatalogIds(catalog.getChildren());
				resultIds.addAll(subCatalogIds);
			}
		}
		return resultIds;
	}

	/**
	 * 递归遍历到指定目录并返回其所有子目录的Id
	 * @param catalog
	 * @param catalogs
	 * @param isLoad
	 * @return
	 */
	public List<Long> getSubCatalogIds(Long catalogId, List<Catalog> catalogs, boolean isLoad) {
		List<Long> resultIds = Lists.newLinkedList();
		for (Catalog c1 : catalogs) {
			if (isLoad) {}
			if (c1.getId().longValue() == catalogId.longValue()) {
				if (c1.getChildren() == null || c1.getChildren().isEmpty()) {
					return null;
				} else {
					//遍历获取目录id
					for (Catalog c2 : c1.getChildren()) {
						resultIds.add(c2.getId());
					}
					List<Long> subIds = getSubCatalogIds(catalogId, c1.getChildren());
					if (subIds != null) {
						resultIds.addAll(subIds);
					}
				}
			} else {
				return getSubCatalogIds(catalogId, c1.getChildren());
			}
		}
		return resultIds;
	}
	
	/**
	 * 原来递归的方式修改为非递归方式。
	 * 非递归方法查询商品/文章目录结构，并且自动排序。
	 * @param type
	 * @param catalogs
	 */
	private List<Catalog> loadCatalogsByType(String type){
		Catalog cc = new Catalog();
		cc.setType(type);
		List<Catalog> catalogsList = catalogDao.selectList(cc);
		List<Catalog> catalogs = Lists.newLinkedList();
		if(catalogsList!=null){
			Map<Long, Catalog> map = new HashMap<Long, Catalog>();
			//生成一级目录Map数据
			for(Iterator<Catalog> it = catalogsList.iterator();it.hasNext();){
				Catalog item = it.next();
				if(Catalog.isMainCatalog(item.getPid())){
					map.put(item.getId(), item);
					it.remove();
				}
			}
			//生成二级目录map数据
			for(Iterator<Catalog> it = catalogsList.iterator();it.hasNext();){
				Catalog item = it.next();
				Catalog rootItem = map.get(item.getPid());
				if(rootItem!=null){
					if(rootItem.getChildren()==null){
						rootItem.setChildren(new LinkedList<Catalog>());
					}
					//获取推荐目录数据,设置在一级目录下
					if(rootItem.getRecommend() == null) {
						rootItem.setRecommend(new LinkedList<Catalog>());
					}
					if(Catalog.CATALOG_RECOMMEND_Y.equals(item.getIsRecommend())) {
						rootItem.getRecommend().add(item);
					}
					//生成三级目录map数据
					for(Iterator<Catalog> it1 = catalogsList.iterator();it1.hasNext();){
						Catalog item1 = it1.next();
						if(item.getId().longValue() == item1.getPid().longValue()) {
							if(item.getChildren()==null){
								item.setChildren(new LinkedList<Catalog>());
							}
							if(Catalog.CATALOG_RECOMMEND_Y.equals(item1.getIsRecommend())) {
								rootItem.getRecommend().add(item1);
							}
							item.getChildren().add(item1);
						}
					}
					rootItem.getChildren().add(item);
				}
			}
			
			//获取根目录数据
			for(Iterator<Entry<Long, Catalog>> it = map.entrySet().iterator();it.hasNext();){
				catalogs.add(it.next().getValue());
			}
			
			//对主类别和子类别进行排序
			Collections.sort(catalogs, new Comparator<Catalog>() {
				public int compare(Catalog o1, Catalog o2) {
					if (o1.getSort() > o2.getSort()) {
						return 1;
					} else if (o1.getSort() < o2.getSort()) {
						return -1;
					}
					return 0;
				}
			});
			
			for(int i=0;i<catalogs.size();i++){
				if(catalogs.get(i).getChildren()==null || catalogs.get(i).getChildren().isEmpty()){
					continue;
				}
				//二级目录排序
				Collections.sort(catalogs.get(i).getChildren(), new Comparator<Catalog>() {
					public int compare(Catalog o1, Catalog o2) {
						if (o1.getSort() > o2.getSort()) {
							return 1;
						} else if (o1.getSort() < o2.getSort()) {
							return -1;
						}
						return 0;
					}
				});
				
				//三级目录排序
				List<Catalog> secondCatalogList = catalogs.get(i).getChildren();
				for (Catalog secondCatalog : secondCatalogList) {
					if (secondCatalog.getChildren() == null || secondCatalog.getChildren().isEmpty()) {
						continue;
					}
					Collections.sort(secondCatalog.getChildren(), new Comparator<Catalog>() {
						public int compare(Catalog o1, Catalog o2) {
							if (o1.getSort() > o2.getSort()) {
								return 1;
							} else if (o1.getSort() < o2.getSort()) {
								return -1;
							}
							return 0;
						}
					});
				}
			}
		}
		return catalogs;
	}
	
	/**
	 * 
	 */
	
	
	/**
	 * 加载热门推荐商品
	 */
	public List<Product> getHotRecommendProduct(Long catalogId) {
		logger.debug("getHotRecommendProduct");
		List<Product> productList = null;
		if (memcachedClient != null) {
			productList = memcachedClient.get(Product.getProductHotRecommnedKey(catalogId));
			if (productList != null && !productList.isEmpty()) {
				return productList;
			}
		}
		productList = productDao.selectPageLeftHotProducts(catalogId, 12);
		if (productList != null && !productList.isEmpty() && memcachedClient != null) {// 如果Product不为空，则缓存至Memcached
			memcachedClient.safeSet(Product.getProductHotRecommnedKey(catalogId),
					MemcachedObjectType.PRODUCT_HOT_RECOMMEND.getExpiredTime(), productList);
		}
		return productList;
	}
//	/**
//	 * 处理图片尺寸,生成各种规格尺寸
//	 * @param productList
//	 * @return
//	 */
//	public List<Product> dealPictureSize(List<Product> productList) {
//		for (Product product : productList) {
//			generatePictureSize(product);
//		}
//		return productList;
//	}
	
//	/**
//	 * 生成多种图片规格地址数据
//	 */
//	public void generatePictureSize(Product product) {
//		//组装多种规格图片地址
//		String[] picture = product.getPicture().split("\\.");
//		for (PictureSize size : PictureSize.values()) {
//			String prefix = picture[0];
//			String suffix = picture[1];
//			String newPrefix = (prefix + size.getSize());
//			String newName = newPrefix + "." +suffix;
//			
//			if (PictureSize.Picture_350_350.getSize().equals(size.getSize())) {
//				product.setPicture350_350(newName);
//			} else if (PictureSize.Picture_556_360.getSize().equals(size.getSize())) {
//				product.setPicture556_360(newName);
//			}
//		}
//	}
	/**
	 * 加载热门商品
	 */
	public List<Product> getHotProduct() {
		logger.debug("getHotProduct");
		List<Product> productList = null;
		if (memcachedClient != null) {
			productList = memcachedClient.get(Product.getProductHotKey());
			if (productList != null && !productList.isEmpty()) {
				return productList;
			}
		}
		productList = productDao.selectHotProducts(12);
		if (productList != null && !productList.isEmpty() && memcachedClient != null) {// 如果Product不为空，则缓存至Memcached
			memcachedClient.safeSet(Product.getProductHotKey(),
					MemcachedObjectType.PRODUCT_HOT.getExpiredTime(), productList);
		}
		return productList;
	}
	
	/**
	 * 加载特价商品
	 */
	public List<Product> getSaleProduct() {
		logger.debug("getSaleProduct");
		List<Product> productList = null;
		if (memcachedClient != null) {
			productList = memcachedClient.get(Product.getProductSaleKey());
			if (productList != null && !productList.isEmpty()) {
				return productList;
			}
		}
		productList = productDao.selectSaleProducts(12);
		if (productList != null && !productList.isEmpty() && memcachedClient != null) {// 如果Product不为空，则缓存至Memcached
			memcachedClient.safeSet(Product.getProductSaleKey(),
					MemcachedObjectType.PRODUCT_SALE.getExpiredTime(), productList);
		}
		return productList;
	}
	
	/**
	 * 加载最新商品
	 */
	public List<Product> getNewProduct() {
		logger.debug("getNewProduct");
		List<Product> productList = null;
//		if (memcachedClient != null) {
//			productList = memcachedClient.get(Product.getProductNewKey());
//			if (productList != null && !productList.isEmpty()) {
//				return productList;
//			}
//		}
		productList = productDao.selectNewProducts(12);
		if (productList != null && !productList.isEmpty() && memcachedClient != null) {// 如果Product不为空，则缓存至Memcached
			memcachedClient.safeSet(Product.getProductNewKey(),
					MemcachedObjectType.PRODUCT_NEW.getExpiredTime(), productList);
		}
		return productList;
	}
	
	/**
	 * 加载首页滚动图片
	 */
	public List<IndexImg> getIndexImgByType(String type) {
		logger.debug("getIndexImgByType type= {}", type);
		List<IndexImg> indexImgList = null;
		if (memcachedClient != null) {
			indexImgList = memcachedClient.get(IndexImg.getIndexImgTypeKey(type));
			if (indexImgList != null && !indexImgList.isEmpty()) {
				return indexImgList;
			}
		}
		indexImgList = indexImgDao.getIndexImgByType(type);
		if (indexImgList != null && !indexImgList.isEmpty() && memcachedClient != null) {
			memcachedClient.safeSet(IndexImg.getIndexImgTypeKey(type),
					MemcachedObjectType.INDEX_IMG_TYPE.getExpiredTime(), indexImgList);
		}
		return indexImgList;
	}
	
	/**
	 * 加载所有首页楼层数据
	 */
	public List<IndexFloor> getIndexFloorByFloor(String floor) {
		List<IndexFloor> indexFloorList = null;
		if (memcachedClient != null) {
			indexFloorList = memcachedClient.get(IndexFloor.getIndexFloorKey(floor));
			if (indexFloorList != null && !indexFloorList.isEmpty()) {
				return indexFloorList;
			}
		}
		indexFloorList = indexFloorDao.getIndexImgByFloor(floor);
		if (indexFloorList != null && !indexFloorList.isEmpty() && memcachedClient != null) {
			memcachedClient.safeSet(IndexFloor.getIndexFloorKey(floor),
					MemcachedObjectType.INDEX_FLOOR.getExpiredTime(), indexFloorList);
		}
		return indexFloorList;
	}
	
	/**
	 * 根据目录获取目录属性
	 * 
	 */
	public List<Attribute> getAttributeByCatalog(Long catalogId) {
		logger.debug("getAttributeByCatalog catalogId= {}", catalogId);
		List<Attribute> attributeList = null;
		if (memcachedClient != null) {
			attributeList = memcachedClient.get(Attribute.getAttrubuteCatalogKey(catalogId));
			if (attributeList != null && !attributeList.isEmpty()) {
				return attributeList;
			}
		}
		attributeList = attributeDao.selectAttributeByCatalog(catalogId);
		if (attributeList != null && !attributeList.isEmpty() && memcachedClient != null) {
			memcachedClient.safeSet(Attribute.getAttrubuteCatalogKey(catalogId),
					MemcachedObjectType.ATTRIBUTE_CATALOG.getExpiredTime(), attributeList);
		}
		return attributeList;
	}
	
	/**
	 * 获取目录路径
	 * @param catalogId
	 * @return
	 */
	public List<Catalog> getCatalogPath(Catalog catalog) {
		List<Catalog> catalogPath = Lists.newArrayList();
		getParentCatalog(catalog, catalogPath);
		Collections.reverse(catalogPath);
		return catalogPath;
	}
	
	/**
	 * 递归指定目录路径
	 * @param catalog
	 * @param catalogPath
	 */
	public void getParentCatalog(Catalog catalog, List<Catalog> catalogPath) {
		catalogPath.add(catalog);
		if (Catalog.isMainCatalog(catalog.getPid())) {
			return;
		}
		Catalog parent = this.getCatalogById(catalog.getPid());
		getParentCatalog(parent, catalogPath);
	}
	
	/**
	 * 查询目录子类别
	 * 如果当前目录有子目录返回子目录,无则返回同级目录
	 * @param catalog
	 * @param catalogs 
	 * @return
	 */
	public List<Catalog> getCatalogClass(Catalog catalog, List<Catalog> catalogs) {
		for (Catalog c1 : catalogs) {
			if(c1.getId().longValue() == catalog.getId().longValue()) {
				if (c1.getChildren() == null || c1.getChildren().isEmpty()) {
					return null;
				}
				return c1.getChildren();
			} else  {
				if (c1.getChildren() != null && !c1.getChildren().isEmpty()) {
					List<Catalog> result = getCatalogClass(catalog, c1.getChildren());
					if (result!=null) {
						return result;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 分页查询产品列表
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PagerModel<Product> findPageProduct(Map<String, Object> params, int pageNo, int pageSize) {
		int total = productDao.countProduct(params);
		int offset = ((pageNo - 1) < 0 ? 0 : pageNo) * pageSize;
		params.put("offset", offset);
		params.put("pageSize", pageSize);
		List<Product> list = productDao.findPageProduct(params);
		PagerModel<Product> page = new PagerModel<Product>();
		page.setList(list);
		page.setTotal(total);
		page.setOffset(offset);
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		page.setPagerSize((int) Math.ceil((double)total/(double)pageSize));
		return page;
	}
	
	/**
	 * 根据id获得Product
	 * 
	 * @param id
	 * @return
	 */
	public Product getProductWithActivity(Long id) {
		return productDao.getWithActivity(id);
	}
	
	/**
	 * 根据规格获取产品库存 价格
	 */
	public Product getProductSpecInfo(Product product) {
		Spec spec = product.getActiveSpec();
		if (spec == null) {
			logger.info("未指定产品规格,直接返回产品!");
			return product;
		}
//		String stock = spec.getSpecStock();
		Double price = spec.getSpecPrice();
		if (price == null) {
			throw new ServiceException("产品规格 " + spec.getId() + " 配置错误!");
		}
		product.setNowPrice(Double.valueOf(price));
//		product.setStock(Integer.valueOf(stock));
		return product;
	}
	
	
	/**
	 * 根据产品获取规格
	 */
	public List<Spec> getProductSpec(Long productId) {
		Spec spec = new Spec();
		spec.setSpecStatus(Spec.spec_specStatus_y);
		spec.setProductID(productId);
		List<Spec> specList = specDao.selectList(spec);
		if (specList == null) {
			specList = Lists.newArrayList();
		}
		return specList;
	}
	
	
	/**
	 * 计算活动商品的最终结算价。如果此商品不是活动商品，则直接返回此商品的现价
	 * @return
	 */
	public double caclFinalPrice(Product product, Activity activity ){
		if(activity == null || activity.getId() == null 
		|| !Activity.activity_status_y.equals(activity.getStatus())
		|| (activity.getStartDate().getTime() > System.currentTimeMillis())
		|| (activity.getEndDate().getTime() < System.currentTimeMillis())){
			return product.getNowPrice();
		}
		
		double finalPrice = 0d;
		if(activity.getActivityType().equals(Activity.activity_activityType_c)){
			String discountType = activity.getDiscountType();
			if(discountType.equals(Activity.activity_discountType_r)){
				finalPrice = Double.valueOf(product.getNowPrice()) - Double.valueOf(activity.getDiscount());
				if (activity.getMinprice() !=null && (finalPrice < activity.getMinprice().doubleValue())) {
					finalPrice = activity.getMinprice();
				}
			}else if(discountType.equals(Activity.activity_discountType_d)){
				finalPrice = Double.valueOf(product.getNowPrice()) * Double.valueOf(activity.getDiscount()) / 100d;
				if (activity.getMinprice() !=null && (finalPrice < activity.getMinprice().doubleValue())) {
					finalPrice = activity.getMinprice();
				}
			}else if(discountType.equals(Activity.activity_discountType_s)){
				//双倍积分的商品价格上不做优惠
				finalPrice = Double.valueOf(product.getNowPrice());
			}
			DecimalFormat df = new DecimalFormat("0.00");
			return new Double(df.format(finalPrice));
		} else if(activity.getActivityType().equals(Activity.activity_activityType_t)){
			finalPrice = activity.getTuanPrice();
			if (activity.getMinprice() !=null && (finalPrice < activity.getMinprice().doubleValue())) {
				finalPrice = activity.getMinprice();
			}
		}
		return product.getNowPrice();
	}
	
	public double caclSpecFinalPrice(Spec spec, Activity activity ){
		if(activity == null || activity.getId() == null 
		|| !Activity.activity_status_y.equals(activity.getStatus())
		|| (activity.getStartDate().getTime() > System.currentTimeMillis())
		|| (activity.getEndDate().getTime() < System.currentTimeMillis())){
			return spec.getSpecPrice();
		}
		double finalPrice = 0d;
		if(activity.getActivityType().equals(Activity.activity_activityType_c)){
			String discountType = activity.getDiscountType();
			if(discountType.equals(Activity.activity_discountType_r)){
				finalPrice = Double.valueOf(spec.getSpecPrice()) - Double.valueOf(activity.getDiscount());
				if (activity.getMinprice() !=null && (finalPrice < activity.getMinprice().doubleValue())) {
					finalPrice = activity.getMinprice();
				}
			}else if(discountType.equals(Activity.activity_discountType_d)){
				finalPrice = Double.valueOf(spec.getSpecPrice()) * Double.valueOf(activity.getDiscount()) / 100d;
				if (activity.getMinprice() !=null && (finalPrice < activity.getMinprice().doubleValue())) {
					finalPrice = activity.getMinprice();
				}
			}else if(discountType.equals(Activity.activity_discountType_s)){
				//双倍积分的商品价格上不做优惠
				finalPrice = Double.valueOf(spec.getSpecPrice());
			}
			DecimalFormat df = new DecimalFormat("0.00");
			return new Double(df.format(finalPrice));
		}
		return spec.getSpecPrice();
	} 
	
	/**
	 * 根据活动类型查询活动商品
	 */
	public List<Product> getActivityProductByType(String type) {
		logger.debug("getActivityProductByType type:{}", type);
		List<Product> productList = null;
		if (memcachedClient != null) {
			productList = memcachedClient.get(Product.getProductActivityTypeKey(type));
			if (productList != null && !productList.isEmpty()) {
				return productList;
			}
		}
		productList = productDao.getActivityProductByType(type);
		//活动业务逻辑
		for (Product product : productList) {
			Activity activity = product.getActivity();
			if (activity != null) {
				product.setNowPrice(caclFinalPrice(product, activity));
			}
		}
		if (productList != null && !productList.isEmpty() && memcachedClient != null) {
			memcachedClient.safeSet(Product.getProductActivityTypeKey(type),
					MemcachedObjectType.PRODUCT_ACTIVITY.getExpiredTime(), productList);
		}
		return productList;
	}

	// -- ProductHit & ProductHitTrace
	/**
	 * 1. 根据productId统计产品的点击数
	 * 
	 * @param productId
	 * @return
	 */
	@Transactional(readOnly = false)
	public int countProductHit(Long productId) {
		Integer hit = productHitDao.getHitByProductId(productId);
		logger.info("hit {}", hit);
		if (hit == null) {
			productHitDao.insert(new ProductHit(productId, 1));
			return 1;
		}
		return hit;
	}
	
	/**
	 * 2. 增加商品的点击数
	 * 
	 * @param productId
	 * @param sessionId
	 * @param accountId
	 * @param productName
	 */
	@Transactional(readOnly = false)
	public void incrProductHit(Long productId, String sessionId, Long accountId, String productName) {
		productHitDao.incr(productId);
		productHitTraceDao.insert(new ProductHitTrace(productId, productName, sessionId, accountId));
	}
	
	/**
	 * 查询产品规格
	 */
	public Spec getProductSpec(Long productId, Long specId) {
		Spec s = new Spec();
		s.setProductID(productId);
		s.setId(specId);
		return specDao.selectOne(s);
	}
	
	/**
	 * 获取销售商
	 */
	public Seller getSellerById(Long sellerId) {
		return sellerDao.selectById(sellerId);
	}
	
	/**
	 * 增加产品销售数据
	 */
	public void incrSellCount(Long productId) {
		synchronized (Global.class) {
			Integer sellCount = productSellCountDao.countProductSellCount(productId);
			if (sellCount == null) {
				productSellCountDao.insert(new ProductSellCount(productId, 1));
			} else {
				productSellCountDao.incr(productId);
			}
		};
	}
}
