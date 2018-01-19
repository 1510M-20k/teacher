package com.ztl.${packageName}.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ztl.base.PageModel;
import com.ztl.common.PageData;
import com.ztl.${packageName}.dao.${objectName}Dao;
import com.ztl.${packageName}.service.${objectName}Service;

@Service
public class ${objectName}ServiceImpl implements ${objectName}Service {

	@Autowired
	private ${objectName}Dao ${objectNameLower}Dao;
	
	/**
	 * 新增
	 */
	@Override
	public int insert(PageData pd) {
		return ${objectNameLower}Dao.insert(pd);
	}
	
	/**
	 * 修改
	 */
	@Override
	public int update(PageData pd) {
		return ${objectNameLower}Dao.update(pd);
	}
	
	/**
	 * 根据ID删除
	 */
	@Override
	public int delete(String id) {
		return ${objectNameLower}Dao.delete(id);
	}
	
	/**
	 * 根据ID查询单条详情
	 */
	@Override
	public PageData getById(String id) {
		return ${objectNameLower}Dao.getById(id);
	}
	
	/**
	 * 查询分页列表
	 */
	@Override
	public List<PageData> getPagelist(PageModel pageModel) {
		return ${objectNameLower}Dao.getPagelist(pageModel);
	}
	
	/**
	 * 删除所有
	 */
	@Override
	public int deleteAll(String[] id) {
		return ${objectNameLower}Dao.deleteAll(id);
	}

}