package com.util;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String对象通用类
 *
 * @author mir37
 *
 */
public class StringUtils {
    /**
     * 判断字符串是否为空字符串（null,""," "）
     *
     * @param text
     *            要判断的字符串
     * @return 布尔值
     */
    public static boolean isNullOrWhiteSpace(String text) {
        if (text == null)
            return true;

        if (text.length() == 0)
            return true;

        if (text.replaceAll(" ", "").length() == 0)
            return true;

        return false;

    } // end public static Boolean isNullOrWhiteSpace(String text)

    /**
     * 判断字符串是否为数字
     *
     * @param text
     *            要判断的字符串
     * @return 布尔值
     */
    public static boolean isNumeric(String text) {
        if (isNullOrWhiteSpace(text))
            return false;

        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(text);

        return isNum.matches();

    } // end public static boolean isNumeric(String text)

    /**
     * String转换为int类型
     *
     * @param text
     *            要转换的字符串
     * @return int值，转换失败返回0
     */
    public static int toInt(String text) {
        return toInt(text, 0);
    }

    /**
     * String转换为int类型
     *
     * @param text
     *            要转换的字符串
     * @param defaultValue
     *            默认值
     * @return int值，转换失败返回默认值
     */
    public static int toInt(String text, int defaultValue) {
        int val = defaultValue;

        try {
            val = Integer.valueOf(text).intValue();
        } catch (NumberFormatException e) {

        }

        return val;
    }

    /**
     * 将URL以UTF-8进行编码
     *
     * @param text
     *            要编码的字符串
     * @return 编码后的字符串
     */
    public static String urlEncoder(String text) {
        return urlEncoder(text, "UTF-8");
    }

    /**
     * 将URL以指定编码格式进行编码
     *
     * @param text
     *            要编码的字符串
     * @param encoding
     *            编码格式
     * @return 编码后的字符串
     */
    public static String urlEncoder(String text, String encoding) {
        String str = "";

        try {
            str = URLEncoder.encode(text, encoding).replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return str;
    }

} // end public class StringUtils