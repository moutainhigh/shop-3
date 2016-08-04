package com.tuisitang.projects.pm.modules.shop;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.gson.util.HttpKit;
import com.tuisitang.common.mapper.JsonMapper;
import com.tuisitang.common.service.ServiceException;
import com.tuisitang.common.test.SpringTransactionalContextTests;
import com.tuisitang.projects.pm.modules.shop.dao.CatalogDao;
import com.tuisitang.projects.pm.modules.shop.entity.Catalog;

public class CatalogDaoTest extends SpringTransactionalContextTests {
	
	private static final Logger logger = LoggerFactory.getLogger(CatalogDaoTest.class);

	@Autowired
	private CatalogDao catalogDao;
	
	/**
	 * 初始化一级目录
	 */
//	@Test
//	@Rollback(false)
	public void initLevel1() {
		String url = "http://127.0.0.1:8080/shop-api/api/product/categories";
		Map<String, String> params = Maps.newHashMap();
		JsonMapper mapper = JsonMapper.getInstance();
		try {
			String json = HttpKit.post(url, params);
			Map<String, Object> map = mapper.fromJson(json, Map.class);
			List<Map<String, Object>> categories = (List<Map<String, Object>>) map.get("categories");
			Catalog root = catalogDao.findOne(1L);

			List<Catalog> list = Lists.newArrayList();

			for (Map<String, Object> category : categories) {
				logger.info("category {}", category);
				String code = (String) category.get("code");
				String name = (String) category.get("name");
				Integer sort = (Integer) category.get("sort");

				Catalog catalog = new Catalog();
				catalog.setCode(code);
				catalog.setName(name);
				catalog.setSort(10 * sort);

				catalog.setParent(root);
				catalog.setParentIds(root.getParentIds() + root.getId() + ",");

				list.add(catalog);
			}
			catalogDao.save(list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	/**
	 * 初始化二级级目录
	 */
//	@Test
//	@Rollback(false)
	public void initLevel2() {
		String url = "http://127.0.0.1:8080/shop-api/api/product/categories";
		Map<String, String> params = Maps.newHashMap();
		JsonMapper mapper = JsonMapper.getInstance();
		try {
			String json = HttpKit.post(url, params);
			Map<String, Object> map = mapper.fromJson(json, Map.class);
			List<Map<String, Object>> categories = (List<Map<String, Object>>) map.get("categories");

			List<Catalog> list = Lists.newArrayList();

			for (Map<String, Object> category : categories) {
				String code = (String) category.get("code");
				Catalog parent = catalogDao.findByCode(code);
				logger.info("parent {}", category);

				if (parent == null) {
					throw new ServiceException("父目录为空");
				}

				List<Map<String, Object>> children = (List<Map<String, Object>>) category.get("children");

				if (children == null || children.isEmpty()) {
					throw new ServiceException("子目录为空");
				}
				
				for (Map<String, Object> c : children) {
					logger.info("category {}", c);
					Catalog catalog = new Catalog();
					catalog.setCode((String) c.get("code"));
					catalog.setName((String) c.get("name"));
					Integer sort = ((Integer) c.get("sort")) == null ? 0 : ((Integer) c.get("sort"));
					catalog.setSort(10 * sort);

					catalog.setParent(parent);
					catalog.setParentIds(parent.getParentIds() + parent.getId()
							+ ",");

					list.add(catalog);
				}
			}
			catalogDao.save(list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	
	@Test
	@Rollback(false)
	public void initLevel3() {
		String url = "http://127.0.0.1:8080/shop-api/api/product/categories";
		Map<String, String> params = Maps.newHashMap();
		JsonMapper mapper = JsonMapper.getInstance();
		try {
			String json = HttpKit.post(url, params);
			Map<String, Object> map = mapper.fromJson(json, Map.class);
			List<Map<String, Object>> categories = (List<Map<String, Object>>) map.get("categories");

			List<Catalog> list = Lists.newArrayList();

			for (Map<String, Object> category : categories) {
				List<Map<String, Object>> children = (List<Map<String, Object>>) category.get("children");
				if (children == null || children.isEmpty()) {
					throw new ServiceException("子目录为空");
				}
				
				for (Map<String, Object> m : children) {
					List<Map<String, Object>> childList = (List<Map<String, Object>>) m
							.get("children");
					if (childList == null || childList.isEmpty())
						continue;
					String code = (String) m.get("code");
					Catalog parent = catalogDao.findByCode(code);
					logger.info("parent {}", category);

					if (parent == null) {
						throw new ServiceException("父目录为空");
					}

					for (Map<String, Object> c : childList) {
						logger.info("category {}", c);
						Catalog catalog = new Catalog();
						catalog.setCode((String) c.get("code"));
						catalog.setName((String) c.get("name"));
						Integer sort = ((Integer) c.get("sort")) == null ? 0
								: ((Integer) c.get("sort"));
						catalog.setSort(10 * sort);

						catalog.setParent(parent);
						catalog.setParentIds(parent.getParentIds()
								+ parent.getId() + ",");

						list.add(catalog);
					}
				}
			}
			catalogDao.save(list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
}
