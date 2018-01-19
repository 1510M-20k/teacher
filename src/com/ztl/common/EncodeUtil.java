package com.ztl.common;

import java.io.UnsupportedEncodingException;

public class EncodeUtil {

	public static String encode(String source, String sourceCharset, String destCharset) {
		String result = "";
		try {
			if (null == source) return result;
			result = new String(source.getBytes(sourceCharset), destCharset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new RuntimeException("编码错误:" + e.getMessage());
		}
		return result;
	}
}
