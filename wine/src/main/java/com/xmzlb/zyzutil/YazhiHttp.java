package com.xmzlb.zyzutil;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

import com.xinbo.utils.GsonUtils;
import com.xmzlb.model.Succeed;
import com.xmzlb.wine.UserLogInActivity;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.net.UnknownHostException;

/**
 * Created by zyz on 2016/4/12 0012.
 * QQ:344100167
 */
public class YazhiHttp {
    public MyListener listener;
    String url;
    RequestParams requestParams;
    Context context;
    Context applicationContext;

    public interface MyListener { //访问后的回调方法
        void onSuccess(String arg0);

        void onError();
    }

    public YazhiHttp(String url) {
        this.url = url;
        requestParams = new RequestParams(url);
    }

    /**
     * 使用getApplicationContext()，避免内存泄露
     *
     * @param context 传入上下文，可在访问网络错误时弹窗提醒
     * @param url
     */
    public YazhiHttp(Context context, String url) {
        this.url = url;
        this.context = context;
        this.applicationContext = context.getApplicationContext();
        requestParams = new RequestParams(url);
    }

    /**
     * 为post网络访问添加键值
     *
     * @param key
     * @param value
     */
    public void addParams(String key, String value) {
        requestParams.addParameter(key, value);
    }

    public void post(final MyListener listener) {
        this.listener = listener;
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String arg0) {
                if(arg0.contains("succeed")) {
                    Integer succeed = GsonUtils.parseJSON(arg0, Succeed.class).getStatus().getSucceed();
                    if (succeed == 2) { //账号已过期
                        context.startActivity(new Intent(context, UserLogInActivity.class));
                    }else {
                        listener.onSuccess(arg0);
                    }
                } else {
                    listener.onSuccess(arg0);
                }
            }

            @Override
            public void onError(Throwable throwable, boolean b) {
                if (applicationContext != null) {
                    if (throwable instanceof UnknownHostException) {
                        Toast toast = Toast.makeText(applicationContext, "请检查网络连接", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
                listener.onError();
            }

            @Override
            public void onCancelled(CancelledException e) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public static void toLogIn() { //账号过期，跳到登陆界面
    }
}

