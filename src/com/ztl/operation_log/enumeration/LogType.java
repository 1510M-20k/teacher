package com.ztl.operation_log.enumeration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 业务类型
 * UNITREGISTER 单位注册  （提交、审核退回、审核通过、修改邮箱、停用账号、启用账号）
 * ISSUEREPORT 课题申报（创建申报课题、提交管理员、管理员退回，提交水办、水办退回、通过形式审查）
 * UNITLOGIN 单位登录
 * USERLOGIN 填报用户登录
 * SHUIBANLOGIN 水办用户登录
 * NEXTUSER 二级用户（增加、修改、删除）
 * @author issuser
 *
 */
public enum LogType {
	INSERT("INSERT", "保存"), 
	UPDATE("UPDATE", "修改"),
	DELETEMORE("DELETEMORE", "批删"),
	DELETE("DELETE", "删除"),
	QUERY("QUERY", "查询"),
	LOGIN("LOGIN", "登录");
	
	public String value;
	public String displayCode;

	LogType(String value, String displayCode) {
		this.value = value;
		this.displayCode = displayCode;
	}
	public static List<Map<String,Object>> getListMap(){
		List<Map<String,Object>> maplist = new ArrayList<Map<String,Object>>();
		for (LogType s : LogType.values()) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("id",s.value);
			map.put("name", s.displayCode);
			maplist.add(map);
		}
		return maplist;
	}
}
