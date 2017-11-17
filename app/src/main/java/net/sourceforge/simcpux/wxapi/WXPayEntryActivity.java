package net.sourceforge.simcpux.wxapi;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseResp;

public class WXPayEntryActivity extends AppCompatActivity {
    private static final String TAG = "System.out";

    public void onResp(BaseResp resp) {
        Log.v(TAG,resp.errStr);
        Log.v(TAG,resp.errCode +"");
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            Log.d(TAG, "=====onPayFinish,errCode" + resp.errCode);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("微信支付");
        }
    }
}
