package com.pay.wx;

import com.util.EncryptUtils;
import com.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WXOrderPayUtils {
    public static String createdSign(Map<String, String> params) {
        String sign;

        String stringSignTemp = buildOrderParam(params, false);
        stringSignTemp += "&key=" + WXPayConfig.PAY_SIGN_KEY;
        sign = EncryptUtils.MD5(stringSignTemp).toUpperCase();

        return sign;
    }

    public static String toXmlString(Map<String, String> map) {
        StringBuilder sb = new StringBuilder();

        sb.append("<xml>");

        for (String key : map.keySet()) {
            sb.append("<" + key + ">");
            sb.append(map.get(key));
            sb.append("</" + key + ">");
        }

        sb.append("</xml>");

        return sb.toString();
    }

    public static PrepayOrder getPrepayOrder(String strXml) {
        PrepayOrder entity = new PrepayOrder();

        return entity;

    } // end public static PrepayOrder getPrepayOrder(String strXml)

    public static String buildOrderParam(Map<String, String> params) {
        return buildOrderParam(params, true);
    }

    public static String buildOrderParam(Map<String, String> params, boolean isEncode) {
        StringBuilder sb = new StringBuilder();

        List<String> keys = new ArrayList<>(params.keySet());
        for (int i = 0; i < keys.size() - 1; i++) {
            String key = keys.get(i);
            String value = params.get(key);

            sb.append(buildKeyValue(key, value, isEncode));
            sb.append("&");
        }

        String tailKey = keys.get(keys.size() - 1);
        String tailValue = params.get(tailKey);
        sb.append(buildKeyValue(tailKey, tailValue, isEncode));

        return sb.toString();
    }

    public static String buildKeyValue(String key, String value, boolean isEncode) {
        StringBuilder sb = new StringBuilder();
        sb.append(key);
        sb.append("=");
        sb.append(isEncode ? StringUtils.urlEncoder(value) : value);

        return sb.toString();
    }
}
