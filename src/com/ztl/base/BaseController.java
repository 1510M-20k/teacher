package com.ztl.base;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.ztl.common.GetUUID;
import com.ztl.common.Logger;
import com.ztl.common.PageData;


public class BaseController {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	/**
	 * 得到PageData
	 */
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	
	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	
	/**
	 * 得到32位的uuid
	 * @return
	 */
	public String get32UUID(){
		return GetUUID.get32UUID();
	}
	
	public static void logBefore(Logger logger, String interfaceName){
		logger.debug("");
		logger.debug("start");
		logger.debug(interfaceName);
	}
	
	public static void logAfter(Logger logger){
		logger.debug("end");
		logger.debug("");
	}
	
}
