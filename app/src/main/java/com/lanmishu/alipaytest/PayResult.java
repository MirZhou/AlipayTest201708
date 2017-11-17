package com.lanmishu.alipaytest;

import android.text.TextUtils;

import java.util.Map;

/**
 * 支付结果
 */
public class PayResult {
    private String resultStatus;
    private String result;

    public PayResult(Map<String, String> rawResult) {
        if (rawResult == null)
            return;

        for (String key : rawResult.keySet()) {
            if (TextUtils.equals(key, "resultStatus")) {
                resultStatus = rawResult.get(key);
            } else if (TextUtils.equals(key, "result")) {
                result = rawResult.get(key);
            }
        }
    }

    @Override
    public String toString() {
        return "resultStatus={" + this.resultStatus + "};result={" + this.result + "}";
    }

    public String getResultStatus() {
        return resultStatus;
    }

    public String getResult() {
        return result;
    }
} // end public class PayResult