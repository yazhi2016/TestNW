package com.xmzlb.wine.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.wine.MainActivity;
import com.xmzlb.wine.R;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, GlobalVariable.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.errCode == -1) {
            Toast.makeText(this, "微信支付失败", Toast.LENGTH_SHORT).show();
            finish();
        } else if (resp.errCode == -2) {
            Toast.makeText(this, "微信支付已取消", Toast.LENGTH_SHORT).show();
            finish();
        } else if (resp.errCode == 0){
            // 支付成功返回待发货界面
            Intent intent3 = new Intent(this, MainActivity.class);
            intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent3.putExtra("tabhost", 5);
            intent3.putExtra("item", 1); // 跳转待收货界面
            startActivity(intent3);
            Toast.makeText(this, "微信支付成功", Toast.LENGTH_SHORT).show();
        }

    }

}