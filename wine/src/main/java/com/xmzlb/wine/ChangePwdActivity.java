package com.xmzlb.wine;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xmzlb.base.BaseActivity;
import com.xmzlb.model.Changepwd;
import com.xmzlb.model.Data5;
import com.xmzlb.model.Status;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.zyzutil.YazhiHttp;
import com.xinbo.utils.GsonUtils;

import java.util.HashMap;
import java.util.Map;

public class ChangePwdActivity extends BaseActivity {

    private TextView text_changepwd_title;
    private TextView text_uilocation;
    private EditText edit_oldpwd;
    private EditText edit_newpwd;
    private EditText edit_newpwd2;

    View popMenu;
    private PopupWindow popupWindowMenu; // 菜单弹窗
    private View bar;
    private TextView text_topbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);

        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra("changepwd");

        initViews();
        init();
        initEvents();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_login_mefra: //确认修改
                String old = edit_oldpwd.getText().toString();
                if (old.isEmpty()) {
                    edit_oldpwd.setError("旧密码不能为空");
                    return;
                }
                String new1 = edit_newpwd.getText().toString();
                if (new1.isEmpty()) {
                    edit_newpwd.setError("新密码不能为空");
                    return;
                }
                String new2 = edit_newpwd2.getText().toString();
                if (new2.isEmpty()) {
                    edit_newpwd2.setError("新密码不能为空");
                    return;
                }

                if (!new1.equals(new2)) { //如果两次密码不相等
                    edit_newpwd2.setError("两次密码不相等");
                    return;
                }

                String url = GlobalVariable.URL.CHANGEPWD;
                Map<String, String> params = new HashMap<String, String>();
                YazhiHttp yazhiHttp = new YazhiHttp(this, url);
                yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
                yazhiHttp.addParams("old_password", old);
                yazhiHttp.addParams("new_password", new2);
                yazhiHttp.post(new YazhiHttp.MyListener() {
                    @Override
                    public void onSuccess(String arg0) {
                        Changepwd parseJSON = GsonUtils.parseJSON(arg0, Changepwd.class);
                        Status status = parseJSON.getStatus();
                        if (status.getSucceed() == 1) {
                            Data5 data = parseJSON.getData();
                            showShortToast(data.getOk());

                            //修改密码后注销
                            GlobalVariable.getInstance().setSpSid("");
                            Intent intentSignOut = new Intent(ChangePwdActivity.this, UserLogInActivity.class);
                            intentSignOut.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intentSignOut);
                            finish();
                        } else {
                            showShortToast(status.getError_desc());
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });
                break;
            case R.id.rela_menu1:
            case R.id.tab_item1: // 点击底栏首页
                popupWindowMenu.dismiss();
                Intent intent_menu1 = new Intent(ChangePwdActivity.this, MainActivity.class);
                intent_menu1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_menu1.putExtra("tabhost", 0);
                startActivity(intent_menu1);
                break;
            case R.id.rela_menu2:
            case R.id.tab_item2: // 点击底栏购物车
                popupWindowMenu.dismiss();
                Intent intent_menu2 = new Intent(ChangePwdActivity.this, MainActivity.class);
                intent_menu2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_menu2.putExtra("tabhost", 1);
                startActivity(intent_menu2);
                break;
            case R.id.rela_menu3:
            case R.id.tab_item3: // 点击底栏订单中心
                popupWindowMenu.dismiss();
                Intent intent_menu3 = new Intent(ChangePwdActivity.this, MainActivity.class);
                intent_menu3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_menu3.putExtra("tabhost", 2);
                startActivity(intent_menu3);
                break;
            case R.id.rela_menu4:
                popupWindowMenu.dismiss();
                finish();
                break;
            case R.id.tab_item4: // 点击底栏商场指南
                Intent intent_tab4 = new Intent(ChangePwdActivity.this, MainActivity.class);
                intent_tab4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_tab4.putExtra("tabhost", 3);
                startActivity(intent_tab4);
                break;
            case R.id.tab_item5: // 点击底栏我要供货
                Intent intent_tab5 = new Intent(ChangePwdActivity.this, MainActivity.class);
                intent_tab5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_tab5.putExtra("tabhost", 4);
                startActivity(intent_tab5);
                break;
            case R.id.rela_empty_meun:
                popupWindowMenu.dismiss();
                break;
            case R.id.rele_menu_detailact: // 点击顶栏菜单
                popupWindowMenu.showAsDropDown(bar);
                break;
            default:
                break;
        }
    }

    @Override
    protected void initViews() {
        text_uilocation = (TextView) findViewById(R.id.text_uilocation);
        text_topbar = (TextView) findViewById(R.id.text_topbar);

        edit_oldpwd = (EditText) findViewById(R.id.edit_oldpwd);
        edit_newpwd = (EditText) findViewById(R.id.edit_newpwd);
        edit_newpwd2 = (EditText) findViewById(R.id.edit_newpwd2);

        bar = findViewById(R.id.bar);
        // 菜单
        popMenu = getLayoutInflater().inflate(R.layout.include_menu, null);
        popupWindowMenu = new PopupWindow(popMenu, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, true);
        // 设置点击窗体以外区域关闭窗体
        BitmapDrawable b2 = new BitmapDrawable();
        popupWindowMenu.setBackgroundDrawable(b2);
        popupWindowMenu.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {

            }
        });
    }

    @Override
    protected void initEvents() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.btn_login_mefra).setOnClickListener(this);
        findViewById(R.id.textView1).setOnClickListener(this);

        // 底栏tab
        findViewById(R.id.tab_item1).setOnClickListener(this);
        findViewById(R.id.tab_item2).setOnClickListener(this);
        findViewById(R.id.tab_item3).setOnClickListener(this);
        findViewById(R.id.tab_item4).setOnClickListener(this);
        findViewById(R.id.tab_item5).setOnClickListener(this);
        // 点击菜单
        findViewById(R.id.rele_menu_detailact).setOnClickListener(this);
        // 点击菜单中按钮
        popMenu.findViewById(R.id.rela_menu1).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_menu2).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_menu3).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_menu4).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_empty_meun).setOnClickListener(this);
    }

    @Override
    protected void init() {
        text_topbar.setText("用户中心");
        text_uilocation.setText("用户中心 > 登陆密码修改");
    }

}
