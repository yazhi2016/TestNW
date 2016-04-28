package com.xmzlb.wine;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.xmzlb.base.BaseActivity;
import com.xmzlb.model.Status;
import com.xmzlb.model.Welcome;
import com.xmzlb.registermodel.Userinfo;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.zyzutil.YazhiHttp;
import com.igexin.sdk.PushManager;
import com.umeng.analytics.MobclickAgent;
import com.xinbo.utils.GsonUtils;

import org.xutils.common.Callback.CommonCallback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.net.UnknownHostException;

public class StartActivity extends BaseActivity {

    private ImageView imageView1;
    ImageOptions imageOptions;
    Handler handler = new Handler();
    String savePath = Environment.getExternalStorageDirectory() + "/wine-";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        // 友盟上传错误日志
        MobclickAgent.setCatchUncaughtExceptions(true);

        imageOptions = new ImageOptions.Builder().setSize(DensityUtil.dip2px(200), DensityUtil.dip2px(400))
                .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP).setLoadingDrawableId(R.drawable.ic_stub)
                .setFailureDrawableId(R.drawable.ic_error)
                // 设置使用缓存
                .setUseMemCache(true).build();

        // 进入app就设置sp
        GlobalVariable.getInstance().setSp(getSharedPreferences(GlobalVariable.spName, 0));
        initViews();
        initData();
        finishStart();

        //开启友盟推送
//		PushAgent mPushAgent = PushAgent.getInstance(this);
//		mPushAgent.enable();
//		PushAgent.getInstance(this).onAppStart();

        PushManager.getInstance().initialize(this.getApplicationContext());
    }

    private void finishStart() {
        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                isLogIn();
            }

        }, 2000);
    }

    private void isLogIn() {
        String url = GlobalVariable.URL.USERCENTER;
        YazhiHttp yazhiHttp = new YazhiHttp(this, url);
        String spSid = GlobalVariable.getInstance().getSpSid("empty");
        yazhiHttp.addParams("sid", spSid);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Userinfo parseJSON = GsonUtils.parseJSON(arg0, Userinfo.class);
                if (parseJSON.getStatus().getSucceed() == 1) { // 登陆成功
                    if (!GlobalVariable.getInstance().getSpLoginState().equals("0")) { // 如果是配送商
                        Intent intent = new Intent(StartActivity.this, SellerActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    } else {
                        startActivity(MainActivity.class);
                        finish();
                    }
                } else { // 如果还未登陆
                    Intent intent = new Intent(StartActivity.this, UserLogInActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onError() {
                Intent intent = new Intent(StartActivity.this, UserLogInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    private void initData() {
        String url = GlobalVariable.URL.STARTIMA;
        RequestParams requestParams = new RequestParams(url);
        x.http().post(requestParams, new CommonCallback<String>() {
            @Override
            public void onSuccess(String s) {
                Welcome parseJSON = GsonUtils.parseJSON(s, Welcome.class);
                Status status = parseJSON.getStatus();
                if (status.getSucceed() == 1) {
                    x.image().bind(imageView1, parseJSON.getData().getAdCode());
                } else {
                    showShortToast(status.getError_desc());
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                if (throwable instanceof UnknownHostException) {
                    showMidToast("请检查网络连接");
                }
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    protected void initViews() {
        imageView1 = (ImageView) findViewById(R.id.imageView1);
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void init() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 友盟页面统计
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        MobclickAgent.onPause(this);
    }

    // 得到版本号
    private int getVersionCode() {
        PackageManager manager = getPackageManager();
        int versionCode = 0;
        try {
            PackageInfo packageInfo = manager.getPackageInfo(getPackageName(), 0);
            versionCode = packageInfo.versionCode;
            String name = packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }
}
