/**
 * Copyright &copy; 2012-2013 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.tuisitang.projects.pm.modules.sys.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tuisitang.projects.pm.common.persistence.BaseDao;
import com.tuisitang.projects.pm.common.persistence.BaseDaoImpl;
import com.tuisitang.projects.pm.modules.sys.entity.Area;

/**
 * 
insert into sys_area(id, parent_id, parent_ids, code, name, type, create_by, create_date, update_by, update_date, remarks, del_flag)
select t.id,
       t.parent_id,
       t.parent_ids,
       t.code,
       t.name,
       t.type,
       1,
       now(),
       1,
       now(),
       null,
       '0'
  from t_area t
  
 * 区域DAO接口
 * @author ThinkGem
 * @version 2013-01-15
 */
public interface AreaDao extends AreaDaoCustom, CrudRepository<Area, Long> {

	@Modifying
	@Query("update Area set delFlag='" + Area.DEL_FLAG_DELETE + "' where id = ?1 or parentIds like ?2")
	public int deleteById(Long id, String likeParentIds);
	
	public List<Area> findByParentIdsLike(String parentIds);

	@Query("from Area where delFlag='" + Area.DEL_FLAG_NORMAL + "' order by code")
	public List<Area> findAllList();
	
	@Query("from Area where (id=?1 or parent.id=?1 or parentIds like ?2) and delFlag='" + Area.DEL_FLAG_NORMAL + "' order by code")
	public List<Area> findAllChild(Long parentId, String likeParentIds);
	
	@Query("from Area where parent.id=?1 and delFlag='" + Area.DEL_FLAG_NORMAL + "' order by code")
	List<Area> findByParentId(Long parentId);
	
	@Query("from Area where code=?1 and delFlag='" + Area.DEL_FLAG_NORMAL + "' order by code")
	Area findByCode(String code);
	
}

/**
 * DAO自定义接口
 * @author ThinkGem
 */
interface AreaDaoCustom extends BaseDao<Area> {

}

/**
 * DAO自定义接口实现
 * @author ThinkGem
 */
@Repository
class AreaDaoImpl extends BaseDaoImpl<Area> implements AreaDaoCustom {

}
