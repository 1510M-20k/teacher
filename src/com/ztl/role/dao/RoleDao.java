package com.ztl.role.dao;

import java.util.List;

import com.ztl.common.PageData;

public interface RoleDao {

	//新增
	public int insert(PageData pd);

	//修改
	public int update(PageData pd);

	//删除
	public int delete(String id);

	//根据Id查询单条记录
	public PageData getById(String id);

	//根据PID查询单条记录
	public  List<PageData> getByPid(String pid);
}