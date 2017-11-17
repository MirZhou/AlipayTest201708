package com.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * URL general method package.
 */
public class UrlUtils {
    /**
     * 将URL以UTF-8进行编码
     *
     * @param text 要编码的字符串
     * @return 编码后的字符串
     */
    public static String encoder(String text) {
        return encoder(text, "UTF-8");
    }

    /**
     * 将URL以指定编码格式进行编码
     *
     * @param text     要编码的字符串
     * @param encoding 编码格式
     * @return 编码后的字符串
     */
    public static String encoder(String text, String encoding) {
        String str = "";

        try {
            str = URLEncoder.encode(text, encoding).replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return str;
    }

    /**
     * 将URL以UTF-8格式解码
     *
     * @param text 要解码的字符串
     * @return 解码后的字符串
     */
    public static String decoder(String text) throws UnsupportedEncodingException {
        return decoder(text, "UTF-8");
    }

    /**
     * 将URL以指定编码格式进行解码
     *
     * @param text     要解码的字符串
     * @param encoding 解码格式
     * @return 解码后的字符串
     */
    public static String decoder(String text, String encoding) throws UnsupportedEncodingException {
        String str;

        try {
            text = text.replace("&amp;", "&"); // Replace the special label

            str = URLDecoder.decode(text, encoding);
        } catch (UnsupportedEncodingException e) {
            throw e;
        }

        return str;
    }

    /**
     * Get the URL parameter list
     *
     * @param url url address
     * @return Map object
     */
    public static Map<String, String> getParams(String url) {
        Map<String, String> keyValues = new HashMap<>();

        try {
            url = decoder(url);

            Pattern pattern = Pattern.compile("[\\?\\&]([^\\?\\&]+)");

            Matcher matcher = pattern.matcher(url);

            while (matcher.find()) {
                String str = matcher.group(1);
                int index = str.indexOf("=");
                String key = str.substring(0, index);
                String value = str.substring(index + 1);

                keyValues.put(key, value);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return keyValues;
    }
}