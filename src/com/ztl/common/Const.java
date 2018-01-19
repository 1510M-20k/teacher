package com.ztl.common;

/**
 * @author:wbb
 * 
*/
public class Const {
	//不对匹配该值的访问路径拦截（正则）
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(tologin)|(tomain)|(createCode)).*";	
	
	public static final String PAGESIZE = "config/PAGESIZE.txt";//每页条数
	public static final String UEUPLOADFILE = "config/UEUPLOADFILE.txt";//邮箱服务器配置路径
	public static final String SYSNAME = "config/SYSNAME.txt";//页面显示系统title名称
	
	public static final String SESSION_USER = "session_user";//seeion用户
	public static final String SESSION_ROLE = "session_role";//seeion用户
	public static final String USER_NAME = "user_name";//seeion用户
	public static final String LOGIN_YZM_CODE = "login_yzm_code";//登录验证码
	public static final String SESSION_ALLMENULIST = "allmenuList";//所有菜单
	public static final String SESSION_QX = "QX";//按钮权限
	
}
