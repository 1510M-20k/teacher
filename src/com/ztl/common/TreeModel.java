package com.ztl.common;

import java.util.ArrayList;
import java.util.List;

public class TreeModel {
	private String id;// 菜单id
	
	private String pId;// 菜单父id
	
	private String name;// 菜单名称
	
	private String menu_url;// 菜单名称
	
	private String menu_icon;// 菜单名称
	
	private Boolean open = false;// 是否展开
	
	private Boolean checked = false;// 是否选中（是否有权限）
	
	List<TreeModel> nodes;// 子菜单
	
	public String getMenu_url() {
		return menu_url;
	}

	public void setMenu_url(String menu_url) {
		this.menu_url = menu_url;
	}

	public String getMenu_icon() {
		return menu_icon;
	}

	public void setMenu_icon(String menu_icon) {
		this.menu_icon = menu_icon;
	}

	public List<TreeModel> getNodes() {
		return nodes;
	}

	public void setNodes(List<TreeModel> nodes) {
		this.nodes = nodes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getOpen() {
		return open;
	}

	public void setOpen(Boolean open) {
		this.open = open;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	/**
	 * 组合菜单列表
	 * @param menuList
	 * @param roleRights
	 * @return
	 */
	public static List<TreeModel> getTreeList(List<PageData> menuList, String roleRights) {
		List<TreeModel> treelist = new ArrayList<TreeModel>();
		for(PageData menu : menuList){
			if("0".equals(menu.getString("pid"))){//如果是一级菜单
				TreeModel trm = new TreeModel();
				trm.setId(menu.get("id").toString());
				trm.setpId(menu.getString("pid"));
				trm.setName(menu.getString("menu_name"));
				trm.setMenu_url(menu.getString("menu_url"));
				trm.setMenu_icon(menu.getString("menu_icon"));
				trm.setOpen(false);
				if(Tools.notEmpty(roleRights)){
					Boolean hasMenu = RightsHelper.testRights(roleRights, (int)menu.get("id"));
					trm.setChecked(hasMenu);
				}
				treelist.add(trm);
			}
		}
		for(TreeModel fmenu : treelist){
			List<TreeModel> nodes = new ArrayList<TreeModel>();
			for(PageData menu : menuList){
				String pid = menu.getString("pid");
				if(fmenu.getId().equals(pid)){
					TreeModel trm = new TreeModel();
					trm.setId(menu.get("id").toString());
					trm.setpId(pid);
					trm.setName(menu.getString("menu_name"));
					trm.setMenu_url(menu.getString("menu_url"));
					trm.setMenu_icon(menu.getString("menu_icon"));
					trm.setOpen(false);
					if(Tools.notEmpty(roleRights)){
						Boolean hasMenu = RightsHelper.testRights(roleRights, (int)menu.get("id"));
						trm.setChecked(hasMenu);
					}
					nodes.add(trm);
				}
			}
			fmenu.setNodes(nodes);
		}
		return treelist;
	}
}
