package com.util;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Number对象通用类
 * 
 * @author 周光兵
 *
 */
public class NumberUtils {
	/**
	 * 整型转换为RMB表示
	 * 
	 * @param value
	 * @return
	 */
	public static String toCurrencyCN(Integer value) {
		String str = "";

		if (value == null)
			value = 0;

		NumberFormat numberFormat = NumberFormat.getCurrencyInstance(Locale.CHINA);
		numberFormat.setMaximumFractionDigits(2);
		str = numberFormat.format(value.intValue() / 100.0);

		return str;

	} // end public static String toCurrencyCN(Integer value)

	/**
	 * 整型转换为RMB表示（不含标志）
	 * @param value
	 * @return
	 */
	public static String toCurrencyCNNoMark(Integer value) {
		String str = "";

		if (value == null)
			value = 0;

		str = String.format("%.2f", value / 100.0);

		return str;
	}

} // end public class NumberUtils