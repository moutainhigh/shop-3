<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.tuisitang.modules.shop.dao.${className}Dao">

	<!-- 1. 添加${className} -->
	<insert id="insert" parameterType="${className}" useGeneratedKeys="true" keyProperty="id">
		insert into ${tableName}
		(id
        ${insertColumn}
		)
		values
		(
		id
		${insertValue}
		)
	</insert>

	<!-- 2. 删除${className} -->
	<delete id="delete" parameterType="${className}">
		delete from ${tableName} where id = ${id}
	</delete>

	<!-- 3. 修改${className} -->
	<update id="update" parameterType="${className}">
		update ${tableName}
		set id= ${id}
		${updateColumn}		
		where id = ${id}
	</update>

	<!-- 4. 根据${className}查询一条记录 -->
	<select id="selectOne" parameterType="${className}" resultType="${className}">
		select t.* from ${tableName} t where t.id = ${id}
	</select>

	<!-- 5. 分页查询${className} -->
	<select id="selectPageList" parameterType="${className}" resultType="${className}">
		select t.* from ${tableName} t where 1 = 1
		${whereCondition}
		order by id
		limit ${offset}, ${pageSize}
	</select>

	<!-- 6. 根据条件查询${className} -->
	<select id="selectList" parameterType="${className}" resultType="${className}">
		select t.* from ${tableName} t where 1 = 1 
		${whereCondition}
		order by id
	</select>

	<!-- 7. 根据ID来删除一条记录 -->
	<delete id="deleteById" parameterType="long">
		delete from ${tableName} where id = ${id}
	</delete>

	<!-- 8. 根据ID查询一条记录 -->
	<select id="selectById" parameterType="long" resultType="${className}">
		select t.* from ${tableName} t where t.id = ${id}
	</select>

	<!-- 9. 分页查询${className} -->
	<select id="selectPageCount" parameterType="${className}" resultType="int">
		select count(1) from ${tableName} t where 1 = 1
		${whereCondition}
	</select>

</mapper>
