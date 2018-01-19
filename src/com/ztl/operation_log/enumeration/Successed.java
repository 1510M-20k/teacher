package com.ztl.operation_log.enumeration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Successed {
	SUCCESSED("SUCCESSED", "成功"), 
	FAILED("FAILED", "失败");
	public String value;
	public String displayCode;

	Successed(String value, String displayCode) {
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
