package com.xmzlb.util;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.xmzlb.wine.MainActivity;
import com.xmzlb.wine.R;
import com.xmzlb.wine.UserActivity;

/**
 * Created by zyz on 2016/4/7 0007.
 * QQ:344100167
 */
public class baseUI extends Activity {

    private PopupWindow popupWindowMenu; // 菜单弹窗
    View popMenu;
    Activity currentActivity;
    private LinearLayout linearLayout;
    private View bar;
    private TextView textTopbar;

    protected void showShortToast(String str) {
        Toast.makeText(currentActivity, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentActivity = this;
        initContentView();
    }

    public void initContentView() {
        ViewGroup content = (ViewGroup) findViewById(android.R.id.content);
        content.removeAllViews();
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        content.addView(linearLayout);
        LayoutInflater.from(this).inflate(R.layout.activity_base_activity_top_and_bottom, linearLayout, true);

//        // 菜单
//        popMenu = currentActivity.getLayoutInflater().inflate(R.layout.include_menu, null);
//        popupWindowMenu = new PopupWindow(popMenu, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, true);
//        // 设置点击窗体以外区域关闭窗体
//        BitmapDrawable b2 = new BitmapDrawable();
//        popupWindowMenu.setBackgroundDrawable(b2);
//        popupWindowMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {
//
//            @Override
//            public void onDismiss() {
//
//            }
//        });
//
//        bar = findViewById(R.id.bar);
        textTopbar = (TextView) linearLayout.findViewById(R.id.text_topbar);
    }

    public void setBatText(String str) {
        textTopbar.setText(str);
    }

    @Override
    public void setContentView(int layoutResID) {
        showShortToast("test");
        LayoutInflater.from(this).inflate(layoutResID, linearLayout, true);
    }

    protected void initViews() {
        // 菜单
        popMenu = currentActivity.getLayoutInflater().inflate(R.layout.include_menu, null);
        popupWindowMenu = new PopupWindow(popMenu, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, true);
        // 设置点击窗体以外区域关闭窗体
        BitmapDrawable b2 = new BitmapDrawable();
        popupWindowMenu.setBackgroundDrawable(b2);
        popupWindowMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {

            }
        });

        bar = findViewById(R.id.bar);
        View view = currentActivity.getLayoutInflater().inflate(R.layout.activity_base_activity_top_and_bottom, null);
        textTopbar = (TextView) view.findViewById(R.id.text_topbar);
    }

    public void back(View v) {
        finish();
    }

    public void menu(View v) {
        popupWindowMenu.showAsDropDown(bar);
    }

    public void tab1(View v) {
//        popupWindowMenu.dismiss();
        Intent intent_menu1 = new Intent(currentActivity, MainActivity.class);
        intent_menu1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent_menu1.putExtra("tabhost", 0);
        startActivity(intent_menu1);
    }

    public void tab2(View v) {
        popupWindowMenu.dismiss();
        Intent intent_menu2 = new Intent(currentActivity, MainActivity.class);
        intent_menu2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent_menu2.putExtra("tabhost", 1);
        startActivity(intent_menu2);
    }

    public void tab3(View v) {
        popupWindowMenu.dismiss();
        Intent intent_menu3 = new Intent(currentActivity, MainActivity.class);
        intent_menu3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent_menu3.putExtra("tabhost", 2);
        startActivity(intent_menu3);
    }

    public void tab4(View v) {
        Intent intent_tab4 = new Intent(currentActivity, MainActivity.class);
        intent_tab4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent_tab4.putExtra("tabhost", 3);
        startActivity(intent_tab4);
    }

    public void tab5(View v) {
        Intent intent_tab5 = new Intent(currentActivity, MainActivity.class);
        intent_tab5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent_tab5.putExtra("tabhost", 4);
        startActivity(intent_tab5);
    }

    public void menu1(View v) {
        popupWindowMenu.dismiss();
        Intent intent_menu1 = new Intent(currentActivity, MainActivity.class);
        intent_menu1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent_menu1.putExtra("tabhost", 0);
        startActivity(intent_menu1);
    }
    public void menu2(View v) {
        popupWindowMenu.dismiss();
        Intent intent_menu2 = new Intent(currentActivity, MainActivity.class);
        intent_menu2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent_menu2.putExtra("tabhost", 1);
        startActivity(intent_menu2);
    }
    public void menu3(View v) {
        popupWindowMenu.dismiss();
        Intent intent_menu2 = new Intent(currentActivity, MainActivity.class);
        intent_menu2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent_menu2.putExtra("tabhost", 2);
        startActivity(intent_menu2);
    }
    public void menu4(View v) {
        popupWindowMenu.dismiss();
        Intent intent_menu4 = new Intent(currentActivity, UserActivity.class);
        startActivity(intent_menu4);
    }
    public void menuempty(View v) {
        popupWindowMenu.dismiss();
    }

}
