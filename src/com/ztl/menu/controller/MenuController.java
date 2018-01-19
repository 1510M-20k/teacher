package com.ztl.menu.controller;

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
import com.ztl.common.PageData;
import com.ztl.common.Tools;
import com.ztl.menu.service.MenuService;
import com.ztl.operation_log.annotation.Log;
import com.ztl.operation_log.enumeration.LogType;
/**
 * 菜单
 * @author wy
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {

	@Autowired
	private MenuService menuService;

	/**
	 * 进入编辑页面（新增、修改）
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/toedit")
	public ModelAndView edit(HttpServletRequest request, String id, String otype) {
		ModelAndView mv = this.getModelAndView();
		PageData menu = new PageData();
		if (Tools.notEmpty(id)) {
			menu = menuService.getById(id);
		}
		PageData pd = new PageData();
		pd.put("pid", "0");
		List<PageData> menuList = menuService.getAlllist(pd);
		mv.addObject("menuList",menuList);
		mv.setViewName("menu/menu_edit");
		mv.addObject("otype",otype);
		mv.addObject("pd", menu);
		return mv;
	}
	/**
	 * 进入编辑页面（新增、修改）
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/toeditIcon")
	public ModelAndView toeditIcon(HttpServletRequest request, String id) {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("menu/menu_icon");
		mv.addObject("id",id);
		return mv;
	}

	/**
	 * 保存
	 * @param request
	 * @return
	 */
	@RequestMapping("/save")
	@Log(type=LogType.INSERT,content="进行了新增菜单操作")
	@ResponseBody
	public Map<String, Object> insert(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String id=pd.get("id").toString();
		if(Tools.isEmpty(id)){
			map.put("logtype", LogType.INSERT.toString());
			map.put("content", "进行了新增测试操作");
			menuService.insert(pd);
		} else{
			map.put("logtype", LogType.UPDATE.toString());
			map.put("content", "进行了修改测试操作");
			menuService.update(pd);
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
	@Log(type=LogType.DELETE,content="进行了删除菜单操作")
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		menuService.delete(id);
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
	@Log(type=LogType.DELETEMORE,content="进行了批量删除菜单操作")
	@ResponseBody
	public Map<String, Object> deleteAll(HttpServletRequest request, String ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] id = ids.split(",");
		menuService.deleteAll(id);
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
	public ModelAndView list(HttpServletRequest request) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> varlist = menuService.getAlllist(pd);
		mv.setViewName("menu/menu_list");
		mv.addObject("varlist",varlist);
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
	public List<PageData> getJsonlist(HttpServletRequest request) {
		PageData pd = new PageData();
		pd = this.getPageData();
		List<PageData> varlist = menuService.getAlllist(pd);
		return varlist;
	}
}