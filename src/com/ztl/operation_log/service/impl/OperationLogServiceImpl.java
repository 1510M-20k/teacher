package com.ztl.operation_log.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ztl.base.PageModel;
import com.ztl.common.PageData;
import com.ztl.operation_log.dao.OperationLogDAO;
import com.ztl.operation_log.service.OperationLogService;



@Service
public class OperationLogServiceImpl implements OperationLogService {
	
	@Autowired
	private OperationLogDAO operationLogDao;

	@Override
	public void saveLog(PageData pd) {
		operationLogDao.saveLog(pd);
	}

	@Override
	public PageData getById(String id) {
		return operationLogDao.getById(id);
	}

	@Override
	public List<PageData> getPagelist(PageModel pageModel) {
		return operationLogDao.getPagelist(pageModel);
	}

	@Override
	public void createLogTable() {
		operationLogDao.createLogTable();
	}

	
}
