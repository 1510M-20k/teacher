package com.ztl.systest.controller;

import java.util.HashMap;
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
import com.ztl.common.PageData;
import com.ztl.common.Tools;
import com.ztl.operation_log.annotation.Log;
import com.ztl.operation_log.enumeration.LogType;
import com.ztl.systest.service.SystestService;
/**
 * 系统测试
 * @author wy
 */
@Controller
@RequestMapping("/systest")
public class SystestController extends BaseController {

	@Autowired
	private SystestService systestService;
	
	/*字段注释
	id (String	注解ID)
	name (String	姓名)
	age (Integer	年龄)
	create_time (Date	创建时间)
	last_update_time (Date	最后修改时间)
	*/
	/**
	 * 进入编辑页面（新增、修改）
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/toedit")
	public ModelAndView edit(HttpServletRequest request, String id, String otype) {
		ModelAndView mv = this.getModelAndView();
		PageData systest = new PageData();
		if (Tools.notEmpty(id)) {
			systest = systestService.getById(id);
		}
		mv.setViewName("systest/systest_edit");
		mv.addObject("otype",otype);
		mv.addObject("pd", systest);
		return mv;
	}

	/**
	 * 保存
	 * @param request
	 * @return
	 */
	@RequestMapping("/save")
	@Log(type=LogType.INSERT,content="进行了新增系统测试操作")
	@ResponseBody
	public Map<String, Object> insert(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String id=pd.getString("id");
		if(Tools.isEmpty(id)){
			id=this.get32UUID();
			pd.put("id",id);
			map.put("logtype", LogType.INSERT.toString());
			map.put("content", "进行了新增系统测试操作");
			systestService.insert(pd);
		} else{
			map.put("logtype", LogType.UPDATE.toString());
			map.put("content", "进行了修改系统测试操作");
			systestService.update(pd);
		}
		map.put("success", true);
		map.put("bid", id);
		return map;
	}
	/**
	 * 删除
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@Log(type=LogType.DELETE,content="进行了删除系统测试操作")
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		systestService.delete(id);
		map.put("success", true);
		map.put("bid", id);
		return map;
	}

	/**
	 * 批量删除
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteAll")
	@Log(type=LogType.DELETEMORE,content="进行了批量删除系统测试操作")
	@ResponseBody
	public Map<String, Object> deleteAll(HttpServletRequest request, String ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] id = ids.split(",");
		systestService.deleteAll(id);
		map.put("success", true);
		map.put("content", ids);
		map.put("bid", id[0]);
		return map;
	}

	/**
	 * 分页查询列表，跳转页面
	 * @param request
	 * @param pageModel
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request, PageModel pageModel) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pageModel.setPd(pd);
		List<PageData> varlist = systestService.getPagelist(pageModel);
		mv.setViewName("systest/systest_list");
		mv.addObject("varlist",varlist);
		mv.addObject("pd", pd);
		return mv;
	}

	/**
	 * 分页查询列表，返回json
	 * @param request
	 * @param pageModel
	 * @return
	 */
	@RequestMapping("/getJsonlist")
	@ResponseBody
	public List<PageData> getJsonlist(HttpServletRequest request,PageModel pageModel) {
		PageData pd = new PageData();
		pd = this.getPageData();
		pageModel.setPd(pd);
		List<PageData> systestlist = systestService.getPagelist(pageModel);
		return systestlist;
	}
}