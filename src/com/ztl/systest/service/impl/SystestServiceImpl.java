package com.ztl.systest.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ztl.base.PageModel;
import com.ztl.common.PageData;
import com.ztl.systest.dao.SystestDao;
import com.ztl.systest.service.SystestService;

@Service
public class SystestServiceImpl implements SystestService {

	@Autowired
	private SystestDao systestDao;
	
	/**
	 * 新增
	 */
	@Override
	public int insert(PageData pd) {
		return systestDao.insert(pd);
	}
	
	/**
	 * 修改
	 */
	@Override
	public int update(PageData pd) {
		return systestDao.update(pd);
	}
	
	/**
	 * 根据ID删除
	 */
	@Override
	public int delete(String id) {
		return systestDao.delete(id);
	}
	
	/**
	 * 根据ID查询单条详情
	 */
	@Override
	public PageData getById(String id) {
		return systestDao.getById(id);
	}
	
	/**
	 * 查询分页列表
	 */
	@Override
	public List<PageData> getPagelist(PageModel pageModel) {
		return systestDao.getPagelist(pageModel);
	}
	
	/**
	 * 删除所有
	 */
	@Override
	public int deleteAll(String[] id) {
		return systestDao.deleteAll(id);
	}

}