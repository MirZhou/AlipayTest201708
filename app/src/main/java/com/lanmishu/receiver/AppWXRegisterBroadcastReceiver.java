package com.lanmishu.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.pay.wx.WXPayConfig;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class AppWXRegisterBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final IWXAPI api = WXAPIFactory.createWXAPI(context,null);

        // 将该APP注册到微信
        api.registerApp(WXPayConfig.APP_ID);
    }
}