package com.jdyx.app.util;

import java.util.regex.Pattern;

public class StringUtil {
	public static boolean isEmpty(String s) {
		// s == null | s.equals(""); //位与,逻辑与区别,非空字符串放置在前面,避免空指针
		return s == null || "".equals(s);
	}

	public static boolean isNotEmpty(String s) {
		return !isEmpty(s);
	}


	/**
	 * 校验手机号
	 *
	 * @param mobile
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean isMobile(String mobile) {
		return Pattern.matches(Const.REGEX_MOBILE, mobile);
	}

}
