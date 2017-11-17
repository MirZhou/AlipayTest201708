package com.lanmishu.alipaytest.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.pay.wx.WXPayConfig;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private static final String TAG = "System.out";

    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.v(TAG,"=======================WXPayEntryActivity");
        super.onCreate(savedInstanceState);
        Log.v(TAG,"=======================WXPayEntryActivity onCreate");

        api = WXAPIFactory.createWXAPI(this, WXPayConfig.APP_ID);
        api.handleIntent(getIntent(),this);
        Log.v(TAG,"=======================WXPayEntryActivity api");
    }

    @Override
    public void onReq(BaseReq baseReq) {
        Log.v(TAG,"=======================WXPayEntryActivity onReq");

    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int errCode = resp.errCode;
            Log.v(TAG, "onPayFinish,errCode" + resp.errCode);
            if(errCode ==0){
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("订单支付成功。");
                builder.show();
            }else{
                if(errCode==-1){
                    Log.v(TAG,"支付发生异常，未完成支付");
                }else{
                    Log.v(TAG,"取消支付");
                }
            }

        }
        Log.v(TAG,"=======================WXPayEntryActivity onResp");
    }
}
