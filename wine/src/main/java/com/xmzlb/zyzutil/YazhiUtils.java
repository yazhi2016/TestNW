package com.xmzlb.zyzutil;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xmzlb.util.RushBuyCountDownTimerView;

import java.util.Date;

/**
 * Created by zyz on 2016/4/12 0012.
 * QQ:344100167
 */
public class YazhiUtils {

    /**
     * 将图片宽度设为屏幕同宽，高度等比放大，imageview要要设置scaleType="centerCrop"
     *
     * @param drawable  要显示的图片
     * @param imageview 显示图片的控件
     */
    public static void enlargeImgHeight(Drawable drawable, ImageView imageview) {
        int drawableHeight = drawable.getMinimumHeight();
        int drawableWidth = drawable.getMinimumWidth();
        int width = imageview.getWidth();
        float v = (float) width / drawableWidth;
        int height = (int) (v * drawableHeight);
//		if (height > drawableHeight) { //如果图片高度小于屏幕，则设置为图片高度
//            height = drawableHeight;
//        }
        imageview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
        imageview.setImageDrawable(drawable);
    }

    public static void hasInternet(Context context) {
//        ConnectivityManager cm = (ConnectivityManager) context
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (cm != null) {
//            //如果仅仅是用来判断网络连接
//             //则可以使用 cm.getActiveNetworkInfo().isAvailable();
//            boolean available = cm.getActiveNetworkInfo().isAvailable();
//            Log.e("==", available + "asdfasdf");
//        }

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isAvailable()) {
            //当前有可用网络
        } else {
            //当前无可用网络
            Log.e("==", "asdfasdf");
        }
    }


    /**
     * 传入结束的时间戳，即可在倒计时控件上显示距离的时间
     * 如果时间已过则不倒计时
     *
     * @param mEndTime   //结束的时间戳
     * @param mClockView //展示倒计时的控件
     */
    public static void calcuTime(String mEndTime, RushBuyCountDownTimerView mClockView) {
        long nowTime = new Date().getTime(); //单位是毫秒
        long endTime = Long.parseLong(mEndTime + "000"); //拼接三个0转为毫秒
        long time = endTime - nowTime;
        if (time > 0) {
            long day = time / (24 * 60 * 60 * 1000);
            long hour = (time / (60 * 60 * 1000) - day * 24);
            long min = ((time / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long sec = (time / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            int h = Integer.parseInt(String.valueOf(day * 24 + hour));
            int m = Integer.parseInt(String.valueOf(min));
            int s = Integer.parseInt(String.valueOf(sec));
            if (h > 99) {
                h = 99;
            }
            mClockView.setTime(h, m, s);
            mClockView.start();
        }
    }

    public static void setImage(String url, SimpleDraweeView view) {
        Uri uri = Uri.parse(url);
        view.setImageURI(uri);
    }

}
