package com.xmzlb.wine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentTabHost;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.xmzlb.base.BaseActivity;
import com.xmzlb.fragment.AllProductFragment;
import com.xmzlb.fragment.HomeFragment;
import com.xmzlb.fragment.ShopCarFragment;
import com.xmzlb.fragment.UserCenterFragment;

import java.lang.ref.WeakReference;

public class MainActivity extends BaseActivity implements OnClickListener {

    public FragmentTabHost mTabHost;
    public LayoutInflater inflater;
    private int nowTabHost;
    private Bundle bundle;
    private Bundle bundle2;

    MyHandler mMyHandler = new MyHandler(this);

    //定义为静态防止内存泄露
    private static class MyHandler extends Handler {
        //弱应用防止内存泄露
        private WeakReference mWeakReference;

        public MyHandler(Context context) {
            //使用主线程的looper
            super(Looper.getMainLooper());
            mWeakReference = new WeakReference(context);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        Intent intent = getIntent();
        if (intent.getIntExtra("tabhost", -1) != -1) {
            if (intent.getIntExtra("tabhost", -1) == 4) { //传入tabhost=4，则弹出我要供货
                nowTabHost = 0;
                mTabHost.setCurrentTab(nowTabHost);
                startActivity(new Intent(MainActivity.this, SupplyActivity.class));
            } else if (intent.getIntExtra("tabhost", -1) == 5) { // 进入订单中心中相应版块
                int intExtra = intent.getIntExtra("item", -1);
                bundle.putInt("item", intExtra);
                mTabHost.setCurrentTab(2);

                mMyHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bundle.putInt("item", 0);
                    }
                }, 500); //0.5秒后将item清空
            } else if(intent.getIntExtra("tabhost", -1) == 6) { // 进入商场指南中相应版块
                int intExtra = intent.getIntExtra("item", -1);
                bundle2.putInt("item", intExtra);
                mTabHost.setCurrentTab(3);

                mMyHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bundle2.putInt("item", 0);
                    }
                }, 500); //0.5秒后将item清空
            } else { //出入tabhost值在0-3之间，则进入相应的版块
                nowTabHost = intent.getIntExtra("tabhost", -1);
                mTabHost.setCurrentTab(nowTabHost);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void addTab(String title, int drawableRes, Class fragmentClass) {
        View tabItem1 = inflater.inflate(R.layout.tab_item, null);
        TextView textView = (TextView) tabItem1.findViewById(R.id.item_name);
        textView.setText(title);
        ImageView imageview = (ImageView) tabItem1.findViewById(R.id.item_img);
        imageview.setImageResource(drawableRes);
        mTabHost.addTab(mTabHost.newTabSpec(title).setIndicator(tabItem1), fragmentClass, null);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initViews() {
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        bundle = new Bundle();
        bundle2 = new Bundle();
        inflater = getLayoutInflater();
        addTab("首页", R.drawable.home, HomeFragment.class);
        addTab("购物车", R.drawable.shopcar, ShopCarFragment.class);
        // addTab("订单中心", R.drawable.ordercenter, AllProductFragment.class,
        // bundle);

        View tabItem1 = inflater.inflate(R.layout.tab_item, null);
        TextView textView = (TextView) tabItem1.findViewById(R.id.item_name);
        textView.setText("订单中心");
        ImageView imageview = (ImageView) tabItem1.findViewById(R.id.item_img);
        imageview.setImageResource(R.drawable.ordercenter);
        mTabHost.addTab(mTabHost.newTabSpec("订单中心").setIndicator(tabItem1), AllProductFragment.class, bundle);

        View tabItem2 = inflater.inflate(R.layout.tab_item, null);
        TextView textView2 = (TextView) tabItem2.findViewById(R.id.item_name);
        textView2.setText("商场指南");
        ImageView imageview2 = (ImageView) tabItem2.findViewById(R.id.item_img);
        imageview2.setImageResource(R.drawable.guide);
        mTabHost.addTab(mTabHost.newTabSpec("商场指南").setIndicator(tabItem2), UserCenterFragment.class, bundle2);

//        addTab("商场指南", R.drawable.guide, UserCenterFragment.class);
        addTab("我要供货", R.drawable.sale, UserCenterFragment.class);
        mTabHost.getTabWidget().getChildTabViewAt(4).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IWantSupply.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initEvents() {

    }

    @Override
    protected void init() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0 && this.isTaskRoot()) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void mainCall() {
        mTabHost.getTabWidget().getChildTabViewAt(0).callOnClick();
    }
    public void shopCall() {
       mTabHost.getTabWidget().getChildTabViewAt(1).callOnClick();
    }
    public void orderCenterCall() {
       mTabHost.getTabWidget().getChildTabViewAt(2).callOnClick();
    }

}
