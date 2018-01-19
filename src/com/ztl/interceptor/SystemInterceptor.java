package com.ztl.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.ztl.common.Const;
import com.ztl.common.Jurisdiction;

/**
 * 拦截器
 * <p> 内容描述 : 
 * <p> 修改日期： 2016年7月1日 下午8:00:51 </p>
 * @author yuewangh
 * @version V1.0
 */
@Repository
public class SystemInterceptor extends HandlerInterceptorAdapter {
	/*
	 * 
	 * @see
	 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		String path = request.getServletPath();
		if(path.matches(Const.NO_INTERCEPTOR_PATH)){
			return true;
		}else{
			Object obj =  request.getSession().getAttribute(Const.SESSION_USER);
			if(obj!=null){
				path = path.substring(1, path.length());
				//访问权限及初始化按钮权限(控制按钮的显示)
				boolean b = Jurisdiction.hasJurisdiction(request, path);
				if(!b){
					response.sendRedirect(request.getContextPath() + "/admin/tologin.action");
				}
				return b;
			}else{
				//登陆过滤
				response.sendRedirect(request.getContextPath()+ "/admin/tologin.action");
				return false;		
			}
		}
	}
}