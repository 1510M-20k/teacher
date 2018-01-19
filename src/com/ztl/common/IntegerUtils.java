package com.ztl.common;


public class IntegerUtils {
	/**
	 * 两个string相加
	 * 返回string
	 */
	public static String add(String value1, String value2) {
		value1 = StringTools.nullto0(value1);
		value2 = StringTools.nullto0(value2);
		return (Integer.parseInt(value1)+Integer.parseInt(value2))+"";
	}
	/**
	 * 两个string相加
	 * 返回int
	 */
	public static int addtoInt(String value1, String value2) {
		value1 = StringTools.nullto0(value1);
		value2 = StringTools.nullto0(value2);
		return Integer.parseInt(value1)+Integer.parseInt(value2);
	}
	/**
	 * 两个string相减
	 * 返回int
	 */
	public static int subtoInt(String value1, String value2) {
		value1 = StringTools.nullto0(value1);
		value2 = StringTools.nullto0(value2);
		return Integer.parseInt(value1)-Integer.parseInt(value2);
	}
	/**
	 * 两个string相减
	 * 返回string
	 */
	public static String sub(String value1, String value2) {
		value1 = StringTools.nullto0(value1);
		value2 = StringTools.nullto0(value2);
		return (Integer.parseInt(value1)-Integer.parseInt(value2))+"";
	}
}
