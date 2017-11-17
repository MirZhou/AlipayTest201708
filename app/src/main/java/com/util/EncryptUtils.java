package com.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 字符串加密通用类
 * 
 * @author mir37
 *
 */
public class EncryptUtils {
	/**
	 * 使用MD5加密字符串
	 *
	 * @param text
	 *            要加密的字符串
	 * @return 加密后的字符串
	 */
	public static String MD5(String text) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(text.getBytes());
			byte[] bytes = md.digest();

			int i;

			StringBuilder buf = new StringBuilder("");
			for (int offset = 0; offset < bytes.length; offset++) {
				i = bytes[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");

				buf.append(Integer.toHexString(i));

			} // end for (int offset = 0...

			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}

	} // end public static String MD5(String text)

} // end public class EncryptUtils