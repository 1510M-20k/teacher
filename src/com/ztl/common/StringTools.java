package com.ztl.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class StringTools {
	/**
	 * 去除所有空白符
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 2位状态状态增加
	 * @param state
	 * @return
	 */
	public static String nextState(String state) {
		int m = Integer.parseInt(state) + 1;
		if (m < 10) {
			state = "0" + m;
		} else {
			state = String.valueOf(m);
		}
		return state;
	}

	/**
	 * String null转0
	 * @param val
	 * @return
	 */
	public static String nullto0(String val) {
		if (val == null || val.equals("")) {
			val = "0";
		}
		return val;
	}

	/**
	 * String转int
	 * 
	 * @param val
	 * @return
	 */
	public static int str2Int(String val) {
		try {
			return Integer.parseInt(val);
		} catch (NumberFormatException e) {
			return 0;
		}
	}
	/**
	 * String转int
	 * 
	 * @param val
	 * @return
	 */
	public static String substr(String val,int len) {
		if((val.length() > len)){
			val = val.substring(0, len);
		}
		return val;
	}

	/**
	 * ie,chrom,firfox下处理文件名显示乱码
	 * @param request
	 * @param fileNames
	 * @return
	 */
	public static String processFileName(HttpServletRequest request,String fileNames) {
		String codedfilename = null;
		try {
			String agent = request.getHeader("USER-AGENT");
			if (null != agent && -1 != agent.indexOf("MSIE") || null != agent && -1 != agent.indexOf("Trident")) {// ie
				String name = java.net.URLEncoder.encode(fileNames, "UTF8");
				codedfilename = name;
			} else if (null != agent && -1 != agent.indexOf("Mozilla")) {// 火狐,chrome等
				codedfilename = new String(fileNames.getBytes("UTF-8"),"iso-8859-1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return codedfilename;
	}
	public static void main(String[] args) {
		
	}
}
