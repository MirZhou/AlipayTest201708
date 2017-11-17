package com.util;

import android.accounts.NetworkErrorException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 网络HTTP请求工具类
 */
public class NetUtils {
    /**
     * 发送POST请求
     *
     * @param url     请求地址
     * @param content 参数
     * @return 响应结果
     */
    public static String post(String url, String content) {
        String result = "";

        HttpURLConnection conn = null;

        try {
            URL mUrl = new URL(url); // 创建URL对象

            conn = (HttpURLConnection) mUrl.openConnection();

            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(10000);
            conn.setDoOutput(true);

            String data = content;
            OutputStream out = conn.getOutputStream();
            out.write(data.getBytes("UTF-8"));
            out.flush();
            out.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream is = conn.getInputStream();

                result = getStringFromInputStream(is);

            } else {
                throw new NetworkErrorException("Response status is " + responseCode);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    } // end public static String post(String, String)

    /**
     * 发送GET请求
     *
     * @param url 请求地址
     * @return 响应结果
     */
    public static String get(String url) {
        HttpURLConnection conn = null;
        try {
            // 利用string url构建URL对象
            URL mURL = new URL(url);
            conn = (HttpURLConnection) mURL.openConnection();

            conn.setRequestMethod("GET");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(10000);

            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                InputStream is = conn.getInputStream();
                String response = getStringFromInputStream(is);
                return response;
            } else {
                throw new NetworkErrorException("response status is " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (conn != null) {
                conn.disconnect();
            }
        }

        return null;

    } // end public static String get(String)

    private static String getStringFromInputStream(InputStream is) throws IOException {
        String result = "";

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        while ((len = is.read(buffer)) != -1) {
            os.write(buffer, 0, len);
        }

        is.close();
        result = os.toString();
        os.close();

        return result;
    }

} // end public class NetUtils