package com.ztl.role.controller;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ztl.base.BaseController;
import com.ztl.base.PageModel;
import com.ztl.common.Jurisdiction;
import com.ztl.common.PageData;
import com.ztl.common.RightsHelper;
import com.ztl.common.Tools;
import com.ztl.common.TreeModel;
import com.ztl.menu.service.MenuService;
import com.ztl.operation_log.annotation.Log;
import com.ztl.operation_log.enumeration.LogType;
import com.ztl.role.service.RoleService;
/**
 * 菜单
 * @author wy
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {
	
	String menuUrl = "role/list.action"; //菜单地址(权限用)
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private MenuService menuService;
	
	/*字段注释
	id (String	主键ID)
	role_name (String	角色名称)
	rights (String	权限)
	pid (String	父ID)
	add_qx (String	新增权限0没有1有)
	del_qx (String	删除权限0没有1有)
	edit_qx (String	编辑权限0没有1有)
	cha_qx (String	查询权限0没有1有)
	sh_qx (String	审核权限0没有1有)
	by1_qx (String	备用1权限)
	by2_qx (String	备用2权限)
	by3_qx (String	备用3权限)
	*/
	/**
	 * 进入编辑页面（新增、修改）
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/toedit")
	public ModelAndView edit(HttpServletRequest request,String id,String pid,String otype) {
		ModelAndView mv = this.getModelAndView();
		PageData role = new PageData();
		if (Tools.notEmpty(id)) {
			role = roleService.getById(id);
		}
		mv.setViewName("role/edit_name");
		mv.addObject("id",id);
		mv.addObject("pid",pid);
		mv.addObject("otype",otype);
		mv.addObject("pd", role);
		return mv;
	}
	
	/**
	 * 请求角色授权页面
	 */
	@RequestMapping(value="/toeditright")
	@ResponseBody
	public ModelAndView button(String id,String msg)throws Exception{
		ModelAndView mv = this.getModelAndView();
		List<PageData> menuList = menuService.getAlllist(new PageData());
		PageData role = roleService.getById(id);
		String roleRights =  role.getString(msg);
		List<TreeModel> treelist = TreeModel.getTreeList(menuList, roleRights);
		JSONArray arr = JSONArray.fromObject(treelist);
		String json = arr.toString();
		mv.addObject("zTreeNodes", json);
		mv.addObject("id", id);
		mv.addObject("msg", msg);
		mv.setViewName("role/edit_right");
		return mv;
	}
	/**
	 * 保存角色权限
	 */
	@RequestMapping("saveright")
	@Log(type=LogType.UPDATE,content="进行了保存角色按钮权限的操作")
	@ResponseBody
	public Map<String, Object> orleButton(HttpServletRequest request,String id,String menuIds,String msg)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		if(Jurisdiction.buttonJurisdiction(request,menuUrl, "edit")){
			if(null != menuIds && !"".equals(menuIds.trim())){
				BigInteger rights = RightsHelper.sumRights(Tools.str2StrArray(menuIds));
				pd.put(msg,rights.toString());
			}else{
				pd.put(msg,"");
			}
			pd.put("id", id);
			roleService.update(pd);
			map.put("success", true);
		} else{
			map.put("success", false);
			map.put("content", "进行了保存角色按钮权限的操作,但没有权限，操作没成功");
		}
		map.put("bid", id);
		return map;
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
		String id=pd.getString("id");
		if(Tools.isEmpty(id)){
			id=this.get32UUID();
			pd.put("id",id);
			map.put("logtype", LogType.INSERT.toString());
			map.put("content", "进行了新增菜单操作");
			roleService.insert(pd);
		} else{
			map.put("logtype", LogType.UPDATE.toString());
			map.put("content", "进行了修改菜单操作");
			roleService.update(pd);
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
		roleService.delete(id);
		map.put("success", true);
		map.put("bid", id);
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
		List<PageData> roleList = roleService.getByPid("0");
		String pid = pd.getString("pid");
		if(Tools.isEmpty(pid) &&  roleList.size()>0){
			pid=roleList.get(0).getString("id");
			pd.put("pid", pid);
		} 
		List<PageData> varlist = roleService.getByPid(pid);
		mv.addObject("varlist",varlist);
		mv.setViewName("role/role_list");
		mv.addObject("roleList",roleList);
		mv.addObject("pd", pd);
		return mv;
	}
}