package com.ztl.menu.service;

import java.util.List;

import com.ztl.base.PageModel;
import com.ztl.common.PageData;

public interface MenuService {

	// 新增
	public int insert(PageData pd);

	// 修改
	public int update(PageData pd);

	// 删除
	public int delete(String id);

	// 根据Id查询单条记录
	public PageData getById(String id);

	// 分页查询列表
	public List<PageData> getPagelist(PageModel pageModel);

	// 批量删除
	public int deleteAll(String[] id);

	// 列表(全部)
	public List<PageData> getAlllist(PageData pd);
}