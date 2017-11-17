package com.pay.alipay;


import com.util.JsonUtils;
import com.util.UrlUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 生成支付宝支付订单数据
 *
 * @author 周光兵
 */
public class OrderInfoUtils {

    /**
     * 构造支付订单参数列表
     *
     * @param content 支付信息
     * @return
     */
    public static Map<String, String> buildOrderParamMap(Map<String, String> content) {
        Map<String, String> keyValues = new HashMap<>();

        keyValues.put("app_id", AlipayParamConfig.APPID); // 支付宝分配给开发者的应用ID
        keyValues.put("biz_content", JsonUtils.toJson(content)); // 业务请求参数集合
        keyValues.put("notify_url", AlipayParamConfig.URL_PAY_SUCCESS_NOTIFY);
        keyValues.put("charset", "utf-8"); // 编码格式
        keyValues.put("method", "alipay.trade.app.pay"); // 接口名称
        keyValues.put("sign_type", "RSA2"); // 签名类型：RSA2
        keyValues.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); // 发送请求的时间
        keyValues.put("version", "1.0");  // 调用的接口版本

        return keyValues;

    } // end public static Map<String, String> buildOrderParamMap(String, boolean)

    /**
     * 构造支付订单参数信息
     *
     * @param map 支付订单参数
     * @return 支付订单参数信息
     */
    public static String buildOrderParam(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();

        List<String> keys = new ArrayList<>(map.keySet());
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);

            sb.append(buildKeyValue(key, value, true));
            sb.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        sb.append(buildKeyValue(tailKey, tailValue, true));

        return sb.toString();

    } // end public static String buildOrderParam(Map<String, String>)

    /**
     * 拼接键值对
     *
     * @param key      键
     * @param value    值
     * @param isEncode 是否需要编码
     * @return 拼接后的字符串
     */
    public static String buildKeyValue(String key, String value, boolean isEncode) {
        StringBuffer sb = new StringBuffer();
        sb.append(key);
        sb.append("=");
        sb.append(isEncode ? UrlUtils.encoder(value) : value);

        return sb.toString();

    } // end public static String buildKeyValue (String, String, boolean)

    /**
     * 对支付参数信息进行签名
     *
     * @param map    待签名授权信息
     * @param rsaKey 私钥
     * @return 签名字符串
     */
    public static String getSign(Map<String, String> map, String rsaKey) {
        String str;

        List<String> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys);

        StringBuilder authInfo = new StringBuilder();
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = map.get(key);

            authInfo.append(buildKeyValue(key, value, false));
            authInfo.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = map.get(tailKey);
        authInfo.append(buildKeyValue(tailKey, tailValue, false));

        String oriSign = SignUtils.sign(authInfo.toString(), rsaKey);
        str = "sign=" + UrlUtils.encoder(oriSign);

        return str;

    } // end public static String getSign(Map<String, String>, String, boolean)

} // end public class OrderInfoUtils