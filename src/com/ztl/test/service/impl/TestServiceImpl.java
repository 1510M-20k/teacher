package com.ztl.test.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ztl.base.PageModel;
import com.ztl.common.PageData;
import com.ztl.test.dao.TestDao;
import com.ztl.test.service.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private TestDao testDao;
	
	/**
	 * 新增
	 */
	@Override
	public int insert(PageData pd) {
		return testDao.insert(pd);
	}
	
	/**
	 * 修改
	 */
	@Override
	public int update(PageData pd) {
		return testDao.update(pd);
	}
	
	/**
	 * 根据ID删除
	 */
	@Override
	public int delete(String id) {
		return testDao.delete(id);
	}
	
	/**
	 * 根据ID查询单条详情
	 */
	@Override
	public PageData getById(String id) {
		return testDao.getById(id);
	}
	
	/**
	 * 查询分页列表
	 */
	@Override
	public List<PageData> getPagelist(PageModel pageModel) {
		return testDao.getPagelist(pageModel);
	}
	
	/**
	 * 删除所有
	 */
	@Override
	public int deleteAll(String[] id) {
		return testDao.deleteAll(id);
	}

}