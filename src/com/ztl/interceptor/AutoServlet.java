package com.ztl.interceptor;



import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * 启动时自动执行的脚本
 * <p> 内容描述 : TODO</p> 
 * <p> 修改日期： 2016年7月1日 下午7:58:06 </p>
 * @author yuewangh
 * @version V1.0
 */
public class AutoServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4007195369050749902L;

	public void init() throws ServletException {
		super.init();
		try {
			ServletContext context = this.getServletContext();
			WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(context); 
			//SystematicSetService systematicSetService=(SystematicSetService) wac.getBean("systematicSetServiceImpl");
			//context.setAttribute("wcy_app_ip",wcy_app_ip);
		} catch (Exception e) {
			System.out.println("===============数据字典缓存加载失败："+ e.getMessage());
		}
	}
	
}