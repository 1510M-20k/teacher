package com.ztl.user.controller;

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
import com.ztl.common.Const;
import com.ztl.common.Encoding;
import com.ztl.common.PageData;
import com.ztl.common.Tools;
import com.ztl.operation_log.annotation.Log;
import com.ztl.operation_log.enumeration.LogType;
import com.ztl.user.service.UserService;
/**
 * 用户
 * @author wy
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	@Autowired
	private UserService userService;

	/**
	 * 进入编辑页面（新增、修改）
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/toedit")
	public ModelAndView edit(HttpServletRequest request, String id, String otype) {
		PageData user = new PageData();
		if (Tools.notEmpty(id)) {
			user = userService.getById(id);
		}
		request.setAttribute("otype", otype);
		request.setAttribute("pd", user);
		return new ModelAndView("user/user_edit");
	}

	/**
	 * 新增
	 * @param request
	 * @return
	 */
	@RequestMapping("/save")
	@Log(type=LogType.INSERT,content="进行了新增用户操作")
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
			map.put("content", "进行了新增测试操作");
			userService.insert(pd);
		} else{
			map.put("logtype", LogType.UPDATE.toString());
			map.put("content", "进行了修改测试操作");
			userService.update(pd);
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
	@Log(type=LogType.DELETE,content="进行了删除用户操作")
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request, String id) {
		Map<String, Object> map = new HashMap<String, Object>();
		userService.delete(id);
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
	@Log(type=LogType.DELETEMORE,content="进行了批量删除用户操作")
	@ResponseBody
	public Map<String, Object> deleteAll(HttpServletRequest request, String ids) {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] id = ids.split(",");
		userService.deleteAll(id);
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
		PageData pd = new PageData();
		pd = this.getPageData();
		pageModel.setPd(pd);
		List<PageData> varlist = userService.getPagelist(pageModel);
		request.setAttribute("varlist", varlist);
		request.setAttribute("pd", pd);
		return new ModelAndView("user/user_list");
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
		List<PageData> userlist = userService.getPagelist(pageModel);
		return userlist;
	}
	/**
	 * 进入修改密码页面）
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/edit_pwd")
	public ModelAndView edit_pwd(HttpServletRequest request) {
		return new ModelAndView("user/edit_pwd");
	}
	/**
	 * 新增
	 * @param request
	 * @return
	 */
	@RequestMapping("/savepwd")
	@Log(type=LogType.UPDATE,content="进行了修改密码的操作")
	@ResponseBody
	public Map<String, Object> savepwd(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData user = (PageData)request.getSession().getAttribute(Const.SESSION_USER);
		PageData pd = new PageData();
		pd = this.getPageData();
		String password  =Encoding.md5Encoding(pd.getString("password"));//新密码
		String oldpassword  =Encoding.md5Encoding(pd.getString("oldpassword"));//旧密码
		String id=user.getString("id");
		if(oldpassword.equals(user.getString("password"))){
			pd.put("password", password);
			pd.put("id", id);
			userService.update(pd);
			user.put("password", password);
			request.getSession().setAttribute(Const.SESSION_USER,user);
			map.put("success", true);
			map.put("bid", user);
		} else{
			map.put("success", false);
			map.put("errcode", "原密码错误"); 
		}
		map.put("bid", user.getString("id"));
		return map;
	}
}