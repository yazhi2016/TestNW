package com.xmzlb.wine;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.xmzlb.base.BaseActivity;
import com.xmzlb.model.Like;
import com.xmzlb.model.Status;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.zyzutil.YazhiHttp;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.RegexValidateUtil;
import com.xinbo.utils.VolleyListener;

import java.util.HashMap;
import java.util.Map;

public class ReturnPwdActivity extends BaseActivity {

    private RelativeLayout rele_menu_detailact;
    private EditText edit_phone;
    private EditText edit_oldpwd; // 验证码
    private EditText edit_newpwd;
    private EditText edit_newpwd2;
    int Sms;
    String editPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_pwd);
        initViews();
        initEvents();
        init();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.btn_login_mefra: // 确认修改
                String smsCode = edit_oldpwd.getText().toString();
                if (!smsCode.equals(Sms + "")) {
                    edit_oldpwd.setError("验证码不正确");
                    return;
                }
                String newPwd = edit_newpwd.getText().toString();
                if (newPwd.isEmpty()) {
                    edit_newpwd.setError("请输入密码");
                    return;
                }
                String newPwd2 = edit_newpwd2.getText().toString();
                if (newPwd2.isEmpty()) {
                    edit_newpwd2.setError("请输入密码");
                    return;
                }
                if (!newPwd.equals(newPwd2)) {
                    edit_newpwd2.setError("两次密码不一致");
                    return;
                }
                String phoneTwo = edit_phone.getText().toString();
                if (!phoneTwo.equals(editPhone)) {
                    showShortToast("手机号已变更，请重新验证手机");
                    return;
                }
                YazhiHttp yazhiHttp = new YazhiHttp(GlobalVariable.URL.FORGETPWD);
                yazhiHttp.addParams("tel", editPhone);
                yazhiHttp.addParams("pwd", newPwd);
                yazhiHttp.post(new YazhiHttp.MyListener() {
                    @Override
                    public void onSuccess(String arg0) {
                        Like parseJSON = GsonUtils.parseJSON(arg0, Like.class);
                        Status status = parseJSON.getStatus();
                        if (status.getSucceed() == 1) {
                            showShortToast(parseJSON.getData().getMsg());
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
            case R.id.btn_sendsms_mefra: // 发送验证码
                final String phone = edit_phone.getText().toString();
                if (phone.isEmpty()) {
                    edit_phone.setError("请输入手机号");
                    return;
                }
                if (!RegexValidateUtil.checkMobileNumber(phone)) {
                    edit_phone.setError("请输入正确的手机号");
                    return;
                }
                Map<String, String> params2 = new HashMap<>();
                params2.put("tel", phone);
                HTTPUtils.post(ReturnPwdActivity.this, GlobalVariable.URL.SENDSMS, params2, new VolleyListener() {

                    @Override
                    public void onResponse(String arg0) {
                        com.xmzlb.model.Sms parseJSON = GsonUtils.parseJSON(arg0, com.xmzlb.model.Sms.class);
                        if (parseJSON.getStatus() == 1) {
                            Sms = parseJSON.getCode();
                            showShortToast("验证码已发送");
                            editPhone = phone;
                        } else {
                            showShortToast("验证码发送失败");
                        }
                    }

                    @Override
                    public void onErrorResponse(VolleyError arg0) {

                    }
                });
                break;

            default:
                break;
        }
    }

    @Override
    protected void initViews() {
        rele_menu_detailact = (RelativeLayout) findViewById(R.id.rele_menu_detailact);

        edit_phone = (EditText) findViewById(R.id.edit_phone);
        edit_oldpwd = (EditText) findViewById(R.id.edit_oldpwd);
        edit_newpwd = (EditText) findViewById(R.id.edit_newpwd);
        edit_newpwd2 = (EditText) findViewById(R.id.edit_newpwd2);
    }

    @Override
    protected void initEvents() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.btn_sendsms_mefra).setOnClickListener(this);
        findViewById(R.id.btn_login_mefra).setOnClickListener(this);
    }

    @Override
    protected void init() {
        rele_menu_detailact.setVisibility(View.INVISIBLE);
    }

}
