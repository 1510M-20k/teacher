package com.ztl.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ztl.base.BaseController;
import com.ztl.common.Const;
import com.ztl.common.DateUtil;
import com.ztl.common.Encoding;
import com.ztl.common.GeyUserIP;
import com.ztl.common.PageData;
import com.ztl.common.StringTools;
import com.ztl.common.Tools;
import com.ztl.common.TreeModel;
import com.ztl.menu.service.MenuService;
import com.ztl.role.service.RoleService;
import com.ztl.user.service.UserService;


@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private MenuService menuService;

	/**
	 * 进入登录页面
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/tologin")
	public ModelAndView tologin(HttpServletRequest request) {
		request.getSession().setAttribute("user_namesjmcode", this.get32UUID());
		request.getSession().setAttribute("passwordsjmcode", this.get32UUID());
		return new ModelAndView("admin/login");
	}
	/**
	 * 请求登录
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("login")
	@ResponseBody
	public Map<String, Object> login(HttpServletRequest request)throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		HttpSession session = request.getSession();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keydata[] = pd.getString("keydata").replaceAll((String)session.getAttribute("user_namesjmcode"), "").replaceAll((String)session.getAttribute("passwordsjmcode"), "").split(",wy,");
		if(null != keydata && keydata.length == 3){
			String sessionCode = (String)session.getAttribute(Const.LOGIN_YZM_CODE);		//获取session中的验证码
			String code = keydata[2];
			String user_name = keydata[0];
			String password  =Encoding.md5Encoding(keydata[1]);
			if(Tools.notEmpty(sessionCode) && sessionCode.equalsIgnoreCase(code)){
				PageData user = userService.getByUserName(user_name);
				if(user != null){
					if(password.equals(user.getString("password"))){
						PageData newUser = new PageData();
						newUser.put("lastlogin", DateUtil.getTime());
						newUser.put("ip", GeyUserIP.getIpAddr(request));
						newUser.put("id", user.getString("id"));
						userService.update(newUser);
						session.setAttribute(Const.SESSION_USER, user);
						session.setAttribute(Const.USER_NAME, user_name);
						map.put("success", true);
					} else{
						map.put("success", false);
						map.put("errcode", "3"); //用户名或密码错误
					}
				} else{
					map.put("success", false);
					map.put("errcode", "3");//用户名或密码错误
				}
			} else{
				map.put("success", false);
				map.put("errcode", "2");//验证码输入有误
			}
		} else{
			map.put("success", false);
			map.put("errcode", "1");//参数错误，登录失败
		}
		return map;
	}
	/**
	 * 登录后进入主页面
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/tomain")
	public ModelAndView tomain(HttpServletRequest request) {
		HttpSession session = request.getSession();
		PageData user = (PageData)session.getAttribute(Const.SESSION_USER);
		if (user != null) {
			//查询角色信息，放入session
			PageData role = roleService.getById(user.getString("role_id"));
			session.setAttribute(Const.SESSION_ROLE,role); 
			List<PageData> menuList = menuService.getAlllist(new PageData());
			//菜单权限放到session中
			String roleRights = role.getString("rights");
			List<TreeModel> zTreeNodes = TreeModel.getTreeList(menuList, roleRights);
			session.setAttribute(Const.SESSION_ALLMENULIST, zTreeNodes);
			//按钮权限放到session中
			session.setAttribute(Const.SESSION_QX, this.getUQX(session,role));	
			return new ModelAndView("admin/main");
		}else {
			request.getSession().setAttribute("user_namesjmcode", this.get32UUID());
			request.getSession().setAttribute("passwordsjmcode", this.get32UUID());
			return new ModelAndView("admin/login");
		}
		
	}
	/**
	 * 获取角色按钮权限
	 */
	public Map<String, String> getUQX(HttpSession session,PageData role){
		Map<String, String> map = new HashMap<String, String>();
		map.put("adds", StringTools.nullto0(role.getString("add_qx")));
		map.put("dels", StringTools.nullto0(role.getString("del_qx")));
		map.put("edits",StringTools.nullto0(role.getString("edit_qx")));
		map.put("chas", StringTools.nullto0(role.getString("cha_qx")));
		map.put("shs", StringTools.nullto0(role.getString("sh_qx")));
		map.put("by1s", StringTools.nullto0(role.getString("by1_qx")));
		map.put("by2s", StringTools.nullto0(role.getString("by2_qx")));
		map.put("by3s", StringTools.nullto0(role.getString("by3_qx")));
		return map;
	}
	/**
	 * 进入tab标签
	 * @return
	 */
	@RequestMapping("/tab")
	public ModelAndView tab(){
		return new ModelAndView("admin/tab");
	}
	/**
	 * 保存皮肤
	 */
	@RequestMapping(value="/setSKIN")
	@ResponseBody
	public Map<String, Object> setSKIN(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pd = new PageData();
		pd = this.getPageData();
		String SKIN= pd.getString("SKIN");
		request.getSession().setAttribute("ADMINSKIN", SKIN);
		map.put("success", true);
		return map;
	}
	/**
	 * 退出登录
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		Object obj = session.getAttribute(Const.SESSION_USER);
		if (obj != null) {
			session.invalidate();// 清除当前用户的所有session，会触发CountLineListener的sessionDestroyed方法。
		}
		request.getSession().setAttribute("user_namesjmcode", this.get32UUID());
		request.getSession().setAttribute("passwordsjmcode", this.get32UUID());
		return new ModelAndView("admin/login");
	}
}