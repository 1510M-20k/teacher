package com.ztl.common;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;



/**
 * 权限处理
 * @author:fh
*/
public class Jurisdiction {

	/**
	 * 访问权限及初始化按钮权限(控制按钮的显示)
	 * @param menuUrl  菜单路径
	 * @return
	 */
	public static boolean hasJurisdiction(HttpServletRequest request,String menuUrl){
		//判断是否拥有当前点击菜单的权限（内部过滤,防止通过url进入跳过菜单权限）
		/**
		 * 根据点击的菜单的xxx.do去菜单中的URL去匹配，当匹配到了此菜单，判断是否有此菜单的权限，没有的话跳转到404页面
		 * 根据按钮权限，授权按钮(当前点的菜单和角色中各按钮的权限匹对)
		 */
		List<TreeModel> menuList = (List<TreeModel>)request.getSession().getAttribute(Const.SESSION_ALLMENULIST); //获取菜单列表
		for(int i=0;i<menuList.size();i++){
			TreeModel treeModel = menuList.get(i);
			for(int j=0;j<treeModel.getNodes().size();j++){
				TreeModel sunModel = treeModel.getNodes().get(j);
				if(sunModel.getMenu_url().split(".action")[0].equals(menuUrl.split(".action")[0])){
					if(!sunModel.getChecked()){				//判断有无此菜单权限
						return false;
					}else{																//按钮判断
						Map<String, String> map = (Map<String, String>)request.getSession().getAttribute(Const.SESSION_QX);//按钮权限
						String MENU_ID =  sunModel.getId();
						String USERNAME = request.getSession().getAttribute(Const.USER_NAME).toString();	//获取当前登录者loginname
						Boolean isAdmin = "admin".equals(USERNAME);
						map.put("add", (RightsHelper.testRights(map.get("adds"), MENU_ID)) || isAdmin?"1":"0");
						map.put("del", RightsHelper.testRights(map.get("dels"), MENU_ID) || isAdmin?"1":"0");
						map.put("edit", RightsHelper.testRights(map.get("edits"), MENU_ID) || isAdmin?"1":"0");
						map.put("cha", RightsHelper.testRights(map.get("chas"), MENU_ID) || isAdmin?"1":"0");
						map.put("sh", RightsHelper.testRights(map.get("shs"), MENU_ID) || isAdmin?"1":"0");
						map.put("by1", RightsHelper.testRights(map.get("by1s"), MENU_ID) || isAdmin?"1":"0");
						map.put("b2", RightsHelper.testRights(map.get("by2s"), MENU_ID) || isAdmin?"1":"0");
						map.put("by3", RightsHelper.testRights(map.get("by3s"), MENU_ID) || isAdmin?"1":"0");
						request.getSession().setAttribute(Const.SESSION_QX, map);	//重新分配按钮权限
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * 按钮权限(方法中校验)
	 * @param menuUrl  菜单路径
	 * @param type  类型(add、del、edit、cha)
	 * @return
	 */
	public static boolean buttonJurisdiction(HttpServletRequest request,String menuUrl, String type){
		//判断是否拥有当前点击菜单的权限（内部过滤,防止通过url进入跳过菜单权限）
		/**
		 * 根据点击的菜单的xxx.do去菜单中的URL去匹配，当匹配到了此菜单，判断是否有此菜单的权限，没有的话跳转到404页面
		 * 根据按钮权限，授权按钮(当前点的菜单和角色中各按钮的权限匹对)
		 */
		List<TreeModel> menuList = (List<TreeModel>)request.getSession().getAttribute(Const.SESSION_ALLMENULIST); //获取菜单列表
		
		for(int i=0;i<menuList.size();i++){
			TreeModel treeModel = menuList.get(i);
			for(int j=0;j<treeModel.getNodes().size();j++){
				TreeModel sunModel = treeModel.getNodes().get(j);
				if(sunModel.getMenu_url().split(".action")[0].equals(menuUrl.split(".action")[0])){
					if(!sunModel.getChecked()){				//判断有无此菜单权限
						return false;
					}else{																//按钮判断
						Map<String, String> map = (Map<String, String>)request.getSession().getAttribute(Const.SESSION_QX);//按钮权限
						String MENU_ID =  sunModel.getId();
						String USERNAME = request.getSession().getAttribute(Const.USER_NAME).toString();	//获取当前登录者loginname
						Boolean isAdmin = "admin".equals(USERNAME);
						if("add".equals(type)){
							return ((RightsHelper.testRights(map.get("adds"), MENU_ID)) || isAdmin);
						}else if("del".equals(type)){
							return ((RightsHelper.testRights(map.get("dels"), MENU_ID)) || isAdmin);
						}else if("edit".equals(type)){
							return ((RightsHelper.testRights(map.get("edits"), MENU_ID)) || isAdmin);
						}else if("cha".equals(type)){
							return ((RightsHelper.testRights(map.get("chas"), MENU_ID)) || isAdmin);
						}else if("sh".equals(type)){
							return ((RightsHelper.testRights(map.get("shs"), MENU_ID)) || isAdmin);
						}else if("by1".equals(type)){
							return ((RightsHelper.testRights(map.get("by1s"), MENU_ID)) || isAdmin);
						}else if("by2".equals(type)){
							return ((RightsHelper.testRights(map.get("by2s"), MENU_ID)) || isAdmin);
						}else if("by3".equals(type)){
							return ((RightsHelper.testRights(map.get("by3s"), MENU_ID)) || isAdmin);
						}
					}
				}
			}
		}
		return true;
	}
	
}
