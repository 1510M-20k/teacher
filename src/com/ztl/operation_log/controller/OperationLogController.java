package com.ztl.operation_log.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ztl.base.BaseController;
import com.ztl.base.PageModel;
import com.ztl.common.Const;
import com.ztl.common.PageData;
import com.ztl.operation_log.enumeration.LogType;
import com.ztl.operation_log.service.OperationLogService;

@Controller
@RequestMapping("/center/oprlog")
public class OperationLogController extends BaseController {

	@Autowired
	private OperationLogService operationLogService;

	@RequestMapping("/log_list")
	@ResponseBody
	public ModelAndView log_list(HttpServletRequest request,PageModel pageModel) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("center/operationlog/log_list");
		PageData pd = new PageData();
		pd = this.getPageData();
		String login_name = (String) request.getSession().getAttribute(Const.USER_NAME);
		pd.put("loginname", login_name);
		pageModel.setPd(pd);
		List<PageData> log_list = operationLogService.getPagelist(pageModel);
		request.setAttribute("log_list", log_list);
		request.setAttribute("pd", pd);
		return new ModelAndView("testftl/testftl_list");
	}

	// 进入编辑页面
	@RequestMapping("/look")
	@ResponseBody
	public ModelAndView edit(HttpServletRequest request,String id) {
		ModelAndView mv = new ModelAndView();
		PageData operationLog = operationLogService.getById(id);
		List<Map<String,Object>> maplist = LogType.getListMap();
		mv.addObject("maplist", maplist);
		mv.addObject("operationLog", operationLog);
		mv.setViewName("center/operationlog/logdetail");
		return mv;
	}
}