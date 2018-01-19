package com.ztl.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ztl.common.ValidCodeUtils;

/**
 * 加载验证码
 * <p> 内容描述 : TODO</p> 
 * <p> 修改日期： 2016年7月1日 下午8:07:23 </p>
 * @author yuewangh
 * @version V1.0
 */
public class ValidCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ValidCodeUtils.getImage(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
