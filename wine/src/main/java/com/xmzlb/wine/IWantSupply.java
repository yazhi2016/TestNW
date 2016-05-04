package com.xmzlb.wine;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.xinbo.utils.GsonUtils;
import com.xmzlb.model.Like;
import com.xmzlb.model.Status;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.zyzutil.YazhiHttp;

public class IWantSupply extends AppCompatActivity implements View.OnClickListener {

    private EditText editText1;
    private EditText editText2;
    private EditText editText3;
    private EditText editText4;
    private EditText editText5;
    private EditText editText6;
    private RelativeLayout mRela_out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iwant_supply);

        initViews();
        initEvents();

    }

    @Override
    public void onClick(View v) {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        switch (v.getId()) {
            case R.id.bottom_popsale:
                imm.hideSoftInputFromWindow(mRela_out.getWindowToken(), 0);
                break;
            case R.id.empty_popsale:
                imm.hideSoftInputFromWindow(mRela_out.getWindowToken(), 0);
                break;
            case R.id.rela_bottom_popsale:
            case R.id.rela_popbrand_packup_homefra:
                imm.hideSoftInputFromWindow(mRela_out.getWindowToken(), 0);
                finish();
                break;
            case R.id.button1:
                String name = editText1.getText().toString();
                if (name.isEmpty()) {
                    editText1.setError("商品名称不能为空");
                    return;
                }
                String num = editText2.getText().toString();
                if (num.isEmpty()) {
                    editText2.setError("可供货数量不能为空");
                    return;
                }
                String price = editText3.getText().toString();
                if (price.isEmpty()) {
                    editText3.setError("商城批发价不能为空");
                    return;
                }
                String area = editText4.getText().toString();
                if (area.isEmpty()) {
                    editText4.setError("区域不能为空");
                    return;
                }
                String man = editText5.getText().toString();
                if (man.isEmpty()) {
                    editText5.setError("联系人不能为空");
                    return;
                }
                String phone = editText6.getText().toString();
                if (phone.isEmpty()) {
                    editText6.setError("联系电话不能为空");
                    return;
                }

                String url = GlobalVariable.URL.IWANTSUPPLY;
                YazhiHttp yazhiHttp = new YazhiHttp(this, url);
                yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
                yazhiHttp.addParams("goods_name", name);
                yazhiHttp.addParams("goods_num", num);
                yazhiHttp.addParams("goods_money", price);
                yazhiHttp.addParams("area", area);
                yazhiHttp.addParams("user", man);
                yazhiHttp.addParams("tel", phone);
                yazhiHttp.post(new YazhiHttp.MyListener() {
                    @Override
                    public void onSuccess(String arg0) {
                        Like parseJSON = GsonUtils.parseJSON(arg0, Like.class);
                        Status status = parseJSON.getStatus();
                        if (status.getSucceed() == 1) {
                            Toast.makeText(IWantSupply.this, parseJSON.getData().getMsg(), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(IWantSupply.this, status.getError_desc(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });
                break;

            default:
                break;
        }
    }

    protected void initViews() {
        editText1 = (EditText) findViewById(R.id.editText1); //名称
        editText2 = (EditText) findViewById(R.id.editText2); //可供数量
        editText3 = (EditText) findViewById(R.id.editText3); //批发价
        editText4 = (EditText) findViewById(R.id.editText4); //经销区域
        editText5 = (EditText) findViewById(R.id.editText5); //联系人
        editText6 = (EditText) findViewById(R.id.editText6); //电话
        mRela_out = (RelativeLayout) findViewById(R.id.rela_out);
    }

    protected void initEvents() {
        findViewById(R.id.button1).setOnClickListener(this);
        findViewById(R.id.rela_bottom_popsale).setOnClickListener(this);
        findViewById(R.id.empty_popsale).setOnClickListener(this);
        findViewById(R.id.rela_popbrand_packup_homefra).setOnClickListener(this);
        findViewById(R.id.bottom_popsale).setOnClickListener(this);
    }

}
