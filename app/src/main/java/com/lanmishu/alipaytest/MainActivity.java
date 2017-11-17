package com.lanmishu.alipaytest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.lanmishu.service.CheckUpdateService;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.util.JsonUtils;
import com.util.NetUtils;
import com.util.StringUtils;

import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "System.out";

    private static final String SERVER = "/";
    private static final String ORDER_ID = "";

    IWXAPI api;

    // Button:Use AliPay pay test.
    private Button btnAliPay = null;
    private Button btnWXPay = null;

    private Button btnCheckUpdate = null;

    private EditText editNum = null;

    private TextView txtNum = null;

    private Handler aliPayHandler = new Handler() {
        public void handleMessage(Message msg) {
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);

            String resultInfo = payResult.getResult(); // 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus(); // 支付返回的状态码
            Log.v(TAG, resultInfo);

            Looper.prepare();
            if (TextUtils.equals(resultStatus, "9000")) {
                final String response = NetUtils.post("", "orderId=" + ORDER_ID + "&info=" + StringUtils.urlEncoder(resultInfo)); // 向服务器确认支付状态
                Log.v(TAG, StringUtils.urlEncoder(resultInfo));
                Log.v(TAG, response);

                Toast.makeText(MainActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
            }

            Looper.loop();
        }
    };

    private View.OnClickListener btnAliPayClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Runnable payRunnable = new Runnable() {
                @Override
                public void run() {
                    final String strJsonResult = NetUtils.post(SERVER + "api/alipay/orderPay", "orderId=" + ORDER_ID); // 向服务器请求订单信息
                    // http://192.168.0.21:8080/api/alipay/orderPay
                    // http://192.168.0.21:8080/api/alipay/orderPay

                    JsonResult jsonResult = new Gson().fromJson(strJsonResult, JsonResult.class);
                    if (jsonResult.isSuccess()) {
                        final String orderInfo = jsonResult.getRows().toString();
                        PayTask aliPay = new PayTask(MainActivity.this);
                        Map<String, String> result = aliPay.payV2(orderInfo, true);

                        Message msg = new Message();
                        msg.obj = result;

                        aliPayHandler.handleMessage(msg);
                    } else {
                        Looper.prepare();
                        Toast.makeText(MainActivity.this, jsonResult.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }

                }
            };

            // 异步启动
            Thread payThread = new Thread(payRunnable);
            payThread.start();
        }
    };

    // 微信支付
    private View.OnClickListener btnWXPayClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Runnable payRunnable = new Runnable() {
                @Override
                public void run() {
                    final String response = NetUtils.post(SERVER + "api/wx/pay", "orderId=" + ORDER_ID); // 从服务器请求支付
                    Log.v(TAG, response);

                    JsonResult result = JsonUtils.fromJson(response, JsonResult.class);
                    if (result.isSuccess()) {
                        Map<String, String> map = (Map) result.getRows();

                        PayReq request = new PayReq();
                        request.appId = map.get("appid");
                        request.partnerId = map.get("partnerid");
                        request.prepayId = map.get("prepayid");
                        request.packageValue = map.get("package");
                        request.nonceStr = map.get("noncestr");
                        request.timeStamp = map.get("timestamp");
                        request.sign = map.get("sign");

                        api.sendReq(request);

                    } else {
                        Looper.prepare();
                        Toast.makeText(MainActivity.this, "支付请求失败。" + result.getMsg(), Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }
            };

            // 异步启动
            Thread thread = new Thread(payRunnable);
            thread.start();
        }
    };

    private View.OnClickListener btnCheckUpdateListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, CheckUpdateService.class);
            startService(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        api = WXAPIFactory.createWXAPI(this, null);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.btnAliPay = (Button) this.findViewById(R.id.btnAliPay);
        this.btnAliPay.setOnClickListener(this.btnAliPayClickListener);

        this.btnWXPay = (Button) this.findViewById(R.id.btnWXPay);
        this.btnWXPay.setOnClickListener(this.btnWXPayClickListener);

        this.btnCheckUpdate = (Button) this.findViewById(R.id.btnCheckUpdate);
        this.btnCheckUpdate.setOnClickListener(this.btnCheckUpdateListener);

        this.txtNum = (TextView) this.findViewById(R.id.txtNum);

        this.editNum = (EditText) this.findViewById(R.id.editNum);
        this.editNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                txtNum.setText(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
} // end public class MainActivity