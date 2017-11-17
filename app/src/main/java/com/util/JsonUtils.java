package com.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.util.Map;

/**
 * 
 * JSON通用方法类 依赖包：gson-{version}.jar
 * 
 * @author 周光兵
 *
 */
public class JsonUtils {
	private static Gson gson = new GsonBuilder().create();

	/**
	 * 将对象转换为JSON字符串
	 *
	 * @param obj
	 *            要转换的对象
	 * @return 转换后的JSON字符串
	 */
	public static String toJson(Object obj) {
		if (obj == null)
			return "";

		return gson.toJson(obj);
	}

	/**
	 * 将JSON字符串转换为对象
	 * 
	 * @param json
	 *            要转换的JSON字符
	 * @param classOfT
	 *            要转换的对象Class
	 * @return 转换后的对象
	 */
	public static <T> T fromJson(String json, Class<T> classOfT) {
		// TODO Auto-generated method stub
		if (StringUtils.isNullOrWhiteSpace(json))
			return null;

		return gson.fromJson(json, classOfT);
	}

	/**
	 * 将Map对象转换为指定对象
	 * 
	 * @param map
	 *            要转换的Map对象
	 * @param classOfT
	 *            要转换的对象Class
	 * @return 转换后的对象
	 */
	public static <T> T fromJson(Map<String, Object> map, Class<T> classOfT) {
		JsonElement jsonElement = gson.toJsonTree(map);
		return gson.fromJson(jsonElement, classOfT);
	}
}
