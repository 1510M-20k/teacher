package com.ztl.role.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ztl.base.PageModel;
import com.ztl.common.PageData;
import com.ztl.role.dao.RoleDao;
import com.ztl.role.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	
	/**
	 * 新增
	 */
	@Override
	public int insert(PageData pd) {
		return roleDao.insert(pd);
	}
	
	/**
	 * 修改
	 */
	@Override
	public int update(PageData pd) {
		return roleDao.update(pd);
	}
	
	/**
	 * 根据ID删除
	 */
	@Override
	public int delete(String id) {
		return roleDao.delete(id);
	}
	
	/**
	 * 根据ID查询单条详情
	 */
	@Override
	public PageData getById(String id) {
		return roleDao.getById(id);
	}

	/**
	 * 查询列表
	 */
	@Override
	public List<PageData> getByPid(String pid) {
		return roleDao.getByPid(pid);
	}

}