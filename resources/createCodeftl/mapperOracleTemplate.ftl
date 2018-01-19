<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${objectName}Mapper">
	
	
	<!-- 新增-->
	<insert id="insert" parameterType="pd">
		insert into "${tablename}"(
	<#list fieldList as var>
		<#if var_has_next>
			"${var[0]}",	
		<#else>
		    "${var[0]}"
		</#if>
	</#list>
		) values (
	<#list fieldList as var>
		<#if var_has_next>
			${r"#{"}${var[0]}${r"}"},	
		<#else>
		    ${r"#{"}${var[0]}${r"}"}
		</#if>			
	</#list>
		)
	</insert>
	
	
	<!-- 删除-->
	<delete id="delete" parameterType="pd">
		delete from "${tablename}" where  "id" = ${r"#{"}id${r"}"}
	</delete>
	
	
	<!-- 修改 -->
	<update id="update" parameterType="pd">
		update  "${tablename}"
	    	 <set> 
		<#list fieldList as var>
			<#if var_has_next &&  var[3] == "是">
				<if test="${var[0]} != null and ${var[0]} != '' ">
					"${var[0]}" = ${r"#{"}${var[0]}${r"}"},
				</if>
			<#elseif var[3] == "是">
			 	<if test="${var[0]} != null and ${var[0]} != '' ">
					"${var[0]}" = ${r"#{"}${var[0]}${r"}"}
				</if>
			</#if>
		</#list>
			</set>
		where  "id" = ${r"#{"}id${r"}"}
	</update>
	
	
	<!-- 通过ID获取数据 -->
	<select id="getById" resultType="pd" parameterType="String">
		select 
	<#list fieldList as var>
		<#if var_has_next>
			"${var[0]}",	
		<#else>
		    "${var[0]}"
		</#if>
	</#list>
		from  "${tablename}" where "id" = ${r"#{"}id${r"}"}
	</select>
	
	
	<!-- 列表(全部) -->
	<select id="getPagelist" resultType="pd" parameterType="pageModel">
		select
	<#list fieldList as var>
		<#if var_has_next>
			<#if var[1] == 'Date'>
			   DATE_FORMAT( a."${var[0]}",'%Y-%m-%d %H:%i:%s')  ${var[0]},
			<#else>
			   "${var[0]}",
		 	</#if>
		<#else>
			<#if var[1] == 'Date'>
			   DATE_FORMAT( a."${var[0]}",'%Y-%m-%d %H:%i:%s')  ${var[0]}
			<#else>
			   "${var[0]}"
		 	</#if>
		</#if>
	</#list>
		from 
				"${tablename}" a
		where 1=1
		order by "id" desc		
	</select>
	
	<!-- 批量删除 -->
	<delete id="deleteAll" parameterType="String">
		delete from "${tablename}"
		where 
			"id" in
		<foreach item="ids" index="index" collection="array" open="(" separator="," close=")">
                 ${r"#{item}"}
		</foreach>
	</delete>
	
</mapper>