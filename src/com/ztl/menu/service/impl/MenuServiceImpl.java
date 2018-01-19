package com.ztl.menu.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ztl.base.PageModel;
import com.ztl.common.PageData;
import com.ztl.menu.dao.MenuDao;
import com.ztl.menu.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;
	/**
	 * 新增
	 */
	@Override
	public int insert(PageData pd) {
		return menuDao.insert(pd);
	}

	/**
	 * 修改
	 */
	@Override
	public int update(PageData pd) {
		return menuDao.update(pd);
	}
	/**
	 * 删除本身及子菜单
	 */
	@Override
	public int delete(String id) {
		PageData pd = new PageData();
		pd.put("pid", id);
		List<PageData> varlist = menuDao.getAlllist(pd);
		int size =varlist.size();
		if(size>0){
			String[] ids = new String[size];
			for(int i=0;i<size;i++){
				ids[i]=varlist.get(i).get("id").toString();
			}
			menuDao.deleteAll(ids);
		}
		menuDao.delete(id);
		return size+1;
	}
	/**
	 * 根据ID查询单条详情
	 */
	@Override
	public PageData getById(String id) {
		return menuDao.getById(id);
	}
	/**
	 * 查询分页列表
	 */
	@Override
	public List<PageData> getPagelist(PageModel pageModel) {
		return menuDao.getPagelist(pageModel);
	}
	/**
	 * 删除所有
	 */
	@Override
	public int deleteAll(String[] id) {
		return menuDao.deleteAll(id);
	}
	/**
	 * 根据pid查询，pid为null，查询所有
	 */
	@Override
	public List<PageData> getAlllist(PageData pd) {
		return menuDao.getAlllist(pd);
	}

}