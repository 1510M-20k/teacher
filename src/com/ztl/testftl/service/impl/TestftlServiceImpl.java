package com.ztl.testftl.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ztl.base.PageModel;
import com.ztl.common.PageData;
import com.ztl.testftl.dao.TestftlDao;
import com.ztl.testftl.service.TestftlService;

@Service
public class TestftlServiceImpl implements TestftlService {

	@Autowired
	private TestftlDao testftlDao;
	
	/**
	 * 新增
	 */
	@Override
	public int insert(PageData pd) {
		return testftlDao.insert(pd);
	}
	
	/**
	 * 修改
	 */
	@Override
	public int update(PageData pd) {
		return testftlDao.update(pd);
	}
	
	/**
	 * 根据ID删除
	 */
	@Override
	public int delete(String id) {
		return testftlDao.delete(id);
	}
	
	/**
	 * 根据ID查询单条详情
	 */
	@Override
	public PageData getById(String id) {
		return testftlDao.getById(id);
	}
	
	/**
	 * 查询分页列表
	 */
	@Override
	public List<PageData> getPagelist(PageModel pageModel) {
		return testftlDao.getPagelist(pageModel);
	}
	
	/**
	 * 删除所有
	 */
	@Override
	public int deleteAll(String[] id) {
		return testftlDao.deleteAll(id);
	}

}