package com.xmzlb.wine;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.xinbo.utils.GsonUtils;
import com.xmzlb.base.BaseActivity;
import com.xmzlb.registermodel.Login;
import com.xmzlb.registermodel.Status;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.zyzutil.YazhiHttp;

public class UserLogInActivity extends BaseActivity {

    String type = "0"; // 标识是客户登陆还是配送上登陆，0客户，1配送商
    private EditText edit_name_mefra;
    private EditText edit_pwd;
    int fromWhere = -1; // 如果是0，则登陆完finish
    int fromStatus = -1; // 标识进入之前是哪个状态。0客户，1配送商,2初次进来，从未登陆
    private RadioButton radio0;
    private RadioButton radio1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_log_in);
        Intent intent = getIntent();
        if (intent != null) { // 刚才从哪个界面进的，用来判断登陆后是finish还是跳转
            fromWhere = intent.getIntExtra("from", -1);
            fromStatus = intent.getIntExtra("fromStatus", -1);
        }
        initViews();
        initEvents();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 默认是客户
        radio0.setChecked(true);
        // isSignIn();
        String spLoginState = GlobalVariable.getInstance().getSpLoginState();
        type = spLoginState;
        if (spLoginState.equals("0")) {
            radio0.setChecked(true);
        } else {
            radio1.setChecked(true);
        }

        haveLogin(); //如果是因为sid失效则自动登陆
    }

    private void haveLogin() {
        GlobalVariable instance = GlobalVariable.getInstance();
        String oldName = instance.getOldName("empty");
        String oldPwd = instance.getOldPwd("empty");
        if(!oldPwd.equals("empty")) {
            String url = GlobalVariable.URL.LOGIN;
            YazhiHttp yazhiHttp = new YazhiHttp(getApplicationContext(), url);
            yazhiHttp.addParams("name", oldName);
            yazhiHttp.addParams("password", oldPwd);
            yazhiHttp.addParams("type", type);
            yazhiHttp.post(new YazhiHttp.MyListener() {
                @Override
                public void onSuccess(String arg0) {
                    Login parseJSON = GsonUtils.parseJSON(arg0, Login.class);
                    Status status = parseJSON.getStatus();
                    Integer succeed = status.getSucceed();
                    if (succeed == 1) { // 登陆成功
                        String sid = parseJSON.getData().getSession().getSid();
                        GlobalVariable instance = GlobalVariable.getInstance();
                        instance.setSpLoginState(type);
                        instance.setSpSid(sid); // sp写进本地
                        finish();
                    } else { // 登陆失败
                        showShortToast(status.getError_desc());
                    }
                }

                @Override
                public void onError() {

                }
            });
        } else {
            if(!oldName.equals("empty")) {
                edit_name_mefra.setText(oldName);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textView4: //忘记密码
                startActivity(new Intent(UserLogInActivity.this, ReturnPwdActivity.class));
                break;
            case R.id.text_register: // 点击注册
                startActivity(new Intent(UserLogInActivity.this, RegisterActivity.class));
                break;
            case R.id.btn_login_mefra: // 点击登陆
                String name = edit_name_mefra.getText().toString();
                String pwd = edit_pwd.getText().toString();
                if (name.isEmpty()) {
                    edit_name_mefra.setError("账户不能为空");
                }
                if (pwd.isEmpty()) {
                    edit_pwd.setError("密码不能为空");
                }
                GlobalVariable.getInstance().setOldName(name);
                GlobalVariable.getInstance().setOldPwd(pwd);
                String url = GlobalVariable.URL.LOGIN;
                YazhiHttp yazhiHttp = new YazhiHttp(getApplicationContext(), url);
                yazhiHttp.addParams("name", name);
                yazhiHttp.addParams("password", pwd);
                yazhiHttp.addParams("type", type);
                yazhiHttp.post(new YazhiHttp.MyListener() {
                    @Override
                    public void onSuccess(String arg0) {
                        Login parseJSON = GsonUtils.parseJSON(arg0, Login.class);
                        Status status = parseJSON.getStatus();
                        Integer succeed = status.getSucceed();
                        if (succeed == 1) { // 登陆成功
                            String sid = parseJSON.getData().getSession().getSid();
                            GlobalVariable instance = GlobalVariable.getInstance();
                            instance.setSpLoginState(type);
                            instance.setSpSid(sid); // sp写进本地
                            if (fromWhere == 0) { // 登陆完要finish
                                if (type.equals("0")) { // 选择客户登陆
                                    if (fromStatus == 0) { // 并且之前也是客户
                                        finish();
                                        return;
                                    } else {
                                        Intent intent = new Intent(UserLogInActivity.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                        return;
                                    }
                                } else { // 选择配送商登陆
                                    if (fromStatus == 1) { // 并且之前也是配送商
                                        finish();
                                        return;
                                    } else {
                                        Intent intent = new Intent(UserLogInActivity.this, SellerActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                        return;
                                    }
                                }
                            } else { // 没有设定登录完要finish的(或者是什么都没有传的)
                                if (type.equals("0")) {
                                    Intent intent = new Intent(UserLogInActivity.this, MainActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(UserLogInActivity.this, SellerActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                            }

                        } else { // 登陆失败
                            showShortToast(status.getError_desc());
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });

                break;
            case R.id.back: // 返回则回到主页
                Intent intent = new Intent(UserLogInActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("tabhost", 0);
                startActivity(intent);
                break;
            case R.id.radio0: // 客户登陆
                type = "0";
                GlobalVariable.getInstance().setSpLoginState(type);
                break;
            case R.id.radio1: // 配送商登陆
                type = "1";
                GlobalVariable.getInstance().setSpLoginState(type);
                break;

            default:
                break;
        }

    }

    @Override
    protected void initViews() {
        edit_name_mefra = (EditText) findViewById(R.id.edit_name_mefra);
        edit_pwd = (EditText) findViewById(R.id.edit_pwd);
        radio0 = (RadioButton) findViewById(R.id.radio0);
        radio1 = (RadioButton) findViewById(R.id.radio1);
        findViewById(R.id.back).setVisibility(View.GONE);
    }

    @Override
    protected void initEvents() {
        findViewById(R.id.text_register).setOnClickListener(this);
        findViewById(R.id.btn_login_mefra).setOnClickListener(this);
        findViewById(R.id.textView4).setOnClickListener(this);
        radio0.setOnClickListener(this);
        radio1.setOnClickListener(this);
    }

    @Override
    protected void init() {
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            finish();
//			System.exit(0);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
