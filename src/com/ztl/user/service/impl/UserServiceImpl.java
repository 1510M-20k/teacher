package com.ztl.user.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ztl.base.PageModel;
import com.ztl.common.PageData;
import com.ztl.user.dao.UserDao;
import com.ztl.user.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public int insert(PageData pd) {
		return userDao.insert(pd);
	}

	@Override
	public int update(PageData pd) {
		return userDao.update(pd);
	}

	@Override
	public int delete(String id) {
		return userDao.delete(id);
	}

	@Override
	public PageData getById(String id) {
		return userDao.getById(id);
	}

	@Override
	public List<PageData> getPagelist(PageModel pageModel) {
		return userDao.getPagelist(pageModel);
	}
	@Override
	public int deleteAll(String[] id) {
		return userDao.deleteAll(id);
	}

	@Override
	public PageData getByUserName(String user_name) {
		return userDao.getByUserName(user_name);
	}

}