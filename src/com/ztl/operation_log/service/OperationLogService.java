package com.ztl.operation_log.service;

import java.util.List;

import com.ztl.base.PageModel;
import com.ztl.common.PageData;

public interface OperationLogService {

	public void saveLog(PageData pd);

	public PageData getById(String id);

	public List<PageData> getPagelist(PageModel pageModel);

	public void createLogTable();

}
