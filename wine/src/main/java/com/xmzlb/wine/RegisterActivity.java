package com.xmzlb.wine;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.xmzlb.base.BaseActivity;
import com.xmzlb.model.Like;
import com.xmzlb.model.Status;
import com.xmzlb.registermodel.Child;
import com.xmzlb.registermodel.Child_;
import com.xmzlb.registermodel.Child__;
import com.xmzlb.registermodel.Citiesid;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.wheelcity.AbstractWheelTextAdapter;
import com.xmzlb.wheelcity.AddressData;
import com.xmzlb.wheelcity.ArrayWheelAdapter;
import com.xmzlb.wheelcity.OnWheelChangedListener;
import com.xmzlb.wheelcity.WheelView;
import com.xmzlb.wheelview.MyAlertDialog;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.HTTPUtils;
import com.xinbo.utils.RegexValidateUtil;
import com.xinbo.utils.VolleyListener;
import com.xmzlb.zyzutil.YazhiHttp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisterActivity extends BaseActivity {

    private TextView textview_province;
    private TextView textview_city;
    private TextView textview_county;
    String province;
    String cityStr;
    String countyStr;
    ArrayList<Child> citieList = new ArrayList<Child>();
    String id1 = "empty"; // 选中的省的id，为empty则说明没选
    String id2 = "empty";
    String id3 = "empty";
    int position1 = -1; // 选中省的行号,如果等-1，说明还未选择，则不能选择市
    int position2 = -1;
    int position3 = -1;
    int editPosition1;
    int editPosition2;
    int editPosition3;
    String[] shen;
    int mode = 1; // 选择的模式：1省，2市，3区
    private CheckBox checkbox;
    private EditText edit_phone; //手机号
    private EditText edit_username_register; //用户名
    private EditText edit_pwd1_register;
    private EditText edit_pwd2_register;
    private EditText edit_birthyear_register;
    private EditText edit_birthmonth_register;
    private EditText edit_birthday_register;
    private EditText edit_storeaddress_register;
    private EditText edit_sellNum_register;
    private EditText edit_sellPeople_register;
    private EditText edit_storename_register;
    private EditText edit_realname;
    int Sms;
    private EditText edit_smsnum_register;
    private EditText edit_snNum_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getCitiesId();

        initViews();
        init();
        initEvents();
    }

    private void getCitiesId() { // 得到省市ID编号
        String url = GlobalVariable.URL.CITIESID; // 地址
        HTTPUtils.post(RegisterActivity.this, url, null, new VolleyListener() {

            @Override
            public void onResponse(String arg0) {
                Citiesid parseJSON = GsonUtils.parseJSON(arg0, Citiesid.class);
                List<Child> child = parseJSON.getData().get(0).getChild();
                citieList.addAll(child);
            }

            @Override
            public void onErrorResponse(VolleyError arg0) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_getSMS: //短信验证
                String phone2 = edit_phone.getText().toString();
                if (phone2.isEmpty()) {
                    edit_phone.setError("手机号不能为空");
                    return;
                }
                boolean checkMobileNumber = RegexValidateUtil.checkMobileNumber(phone2);
                if (!checkMobileNumber) {
                    edit_phone.setError("请填写正确的手机号");
                    return;
                }
                Map<String, String> params2 = new HashMap<>();
                params2.put("tel", phone2);
                HTTPUtils.post(RegisterActivity.this, GlobalVariable.URL.SENDSMS, params2, new VolleyListener() {

                    @Override
                    public void onResponse(String arg0) {
                        com.xmzlb.model.Sms parseJSON = GsonUtils.parseJSON(arg0, com.xmzlb.model.Sms.class);
                        if (parseJSON.getStatus() == 1) {
                            Sms = parseJSON.getCode();
                            showShortToast("验证码已发送");
                        } else {
                            showShortToast("验证码发送失败");
                        }

                    }

                    @Override
                    public void onErrorResponse(VolleyError arg0) {

                    }
                });
                break;
            case R.id.rela_shen: // 点击省
                mode = 1;
                shen = new String[citieList.size()];
                for (int i = 0; i < citieList.size(); i++) { // 遍历每个省
                    shen[i] = citieList.get(i).getRegionName();
                }

                View view = dialogm();
                final MyAlertDialog dialog1 = new MyAlertDialog(RegisterActivity.this).builder().setTitle("修改现居地")
                        .setView(view).setNegativeButton("取消", new OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                dialog1.setPositiveButton("保存", new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        textview_province.setText(province);
                        position1 = editPosition1;
                        id1 = citieList.get(position1).getRegionId();

                        //如果重选了省，则市，区都要清空
                        position2 = -1;
                        id2 = "empty";
                        id3 = "empty";
                        textview_city.setText("请选择");
                        textview_county.setText("请选择");
                    }
                });
                dialog1.show();
                break;
            case R.id.rela_shi:
                if (position1 == -1) { // 还没选择省
                    showShortToast("请先选择省份！");
                } else {
                    mode = 2;
                    final List<Child_> child = citieList.get(position1).getChild();
                    shen = new String[child.size()];
                    for (int i = 0; i < child.size(); i++) { // 遍历选中省的每个市
                        shen[i] = child.get(i).getRegionName();
                    }

                    View view2 = dialogm();
                    final MyAlertDialog dialog2 = new MyAlertDialog(RegisterActivity.this).builder().setTitle("修改现居地")
                            .setView(view2).setNegativeButton("取消", new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                    dialog2.setPositiveButton("保存", new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (child.size() == 1) {
                                textview_city.setText(child.get(0).getRegionName());
                                position2 = 0;
                                id2 = citieList.get(position1).getChild().get(0).getRegionId();
                            } else {
                                textview_city.setText(cityStr);
                                position2 = editPosition2;
                                id2 = citieList.get(position1).getChild().get(position2).getRegionId();
                            }

                            //选择了市，则要重新选区
                            id3 = "empty";
                            textview_county.setText("请选择");
                        }
                    });
                    dialog2.show();
                }
                break;
            case R.id.rela_xian:
                if (position2 == -1) { // 还没选择区
                    showShortToast("请先选择所在市！");
                } else {
                    mode = 3;
                    List<Child__> child = citieList.get(position1).getChild().get(position2).getChild();
                    shen = new String[child.size()];
                    for (int i = 0; i < child.size(); i++) { // 遍历选中省的每个市
                        shen[i] = child.get(i).getRegionName();
                    }

                    View view3 = dialogm();
                    final MyAlertDialog dialog3 = new MyAlertDialog(RegisterActivity.this).builder().setTitle("修改现居地")
                            .setView(view3).setNegativeButton("取消", new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                    dialog3.setPositiveButton("保存", new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            textview_county.setText(countyStr);
                            position3 = editPosition3;
                            id3 = citieList.get(position1).getChild().get(position2).getChild().get(position3).getRegionId();
                        }
                    });
                    dialog3.show();
                }
                break;
            case R.id.btn_login_mefra: //确认提交
                if (id1.equals("empty")) { //没选择省
                    showShortToast("请选择所在省份！");
                    return;
                }
                if (id2.equals("empty")) { //没选择省
                    showShortToast("请选择所在市！");
                    return;
                }
                if (id3.equals("empty")) { //没选择省
                    showShortToast("请选择所在县区！");
                    return;
                }
                if (!checkbox.isChecked()) {
                    showShortToast("请接受用户协议！");
                    return;
                }
                String phone = edit_phone.getText().toString();
                if (phone.isEmpty()) {
                    edit_phone.setError("手机号不能为空");
                    return;
                }
                String name = edit_username_register.getText().toString();
                if (name.isEmpty()) {
                    edit_username_register.setError("用户名不能为空");
                    return;
                }
                String pwd1 = edit_pwd1_register.getText().toString();
                if (pwd1.isEmpty()) {
                    edit_pwd1_register.setError("密码不能为空");
                    return;
                }
                String pwd2 = edit_pwd2_register.getText().toString();
                if (pwd2.isEmpty()) {
                    edit_pwd2_register.setError("密码不能为空");
                    return;
                }
                if (!pwd1.equals(pwd2)) {
                    edit_pwd2_register.setError("两次密码不相同");
                    return;
                }
                String year = edit_birthyear_register.getText().toString();
                if (year.isEmpty()) {
                    edit_birthyear_register.setError("年份不能为空");
                    return;
                }
                String month = edit_birthmonth_register.getText().toString();
                if (month.isEmpty()) {
                    edit_birthmonth_register.setError("月份不能为空");
                    return;
                }
                String day = edit_birthday_register.getText().toString();
                if (day.isEmpty()) {
                    edit_birthday_register.setError("日期不能为空");
                    return;
                }
                String shopName = edit_storename_register.getText().toString();
                if (shopName.isEmpty()) {
                    edit_storename_register.setError("店铺名称不能为空");
                    return;
                }
                String address = edit_storeaddress_register.getText().toString();
                if (address.isEmpty()) {
                    edit_storeaddress_register.setError("详细地址不能为空");
                    return;
                }
                String sellMan = edit_sellPeople_register.getText().toString();
                if (sellMan.isEmpty()) {
                    edit_sellPeople_register.setError("注册业务员不能为空");
                    return;
                }
                String realname = edit_realname.getText().toString();
                if (realname.isEmpty()) {
                    edit_sellPeople_register.setError("用户姓名不能为空");
                    return;
                }
                String smsCode = edit_smsnum_register.getText().toString();
                if (smsCode.isEmpty()) {
                    edit_smsnum_register.setError("请填写短信验证码");
                    return;
                }
                String snNum = edit_snNum_register.getText().toString();
//                if (!smsCode.equals(Sms + "")) {
//                    showShortToast("验证码不正确！");
//                    return;
//                }
                String url = GlobalVariable.URL.SIGNUP;
                YazhiHttp yazhiHttp = new YazhiHttp(url);
                yazhiHttp.addParams("tel", phone);
                yazhiHttp.addParams("user_name", name);
                yazhiHttp.addParams("pwd", pwd2);
                yazhiHttp.addParams("birthday", year + "-" + month + "-" + day);
                yazhiHttp.addParams("shop_name", shopName);
                yazhiHttp.addParams("country", "1");
                yazhiHttp.addParams("province", id1);
                yazhiHttp.addParams("city", id2);
                yazhiHttp.addParams("district", id3);
                yazhiHttp.addParams("address", address);
                String sellNum = edit_sellNum_register.getText().toString();
                if (!address.isEmpty()) {
                    yazhiHttp.addParams("licence", sellNum);
                }
                yazhiHttp.addParams("salesman", sellMan);
                yazhiHttp.addParams("real_name", realname);
                if (!snNum.isEmpty()) {
                    yazhiHttp.addParams("sn", snNum);
                }
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

            default:
                break;
        }
    }

    @Override
    protected void initViews() {
        textview_province = (TextView) findViewById(R.id.textview_province);
        textview_city = (TextView) findViewById(R.id.textview_city);
        textview_county = (TextView) findViewById(R.id.textview_county);
        checkbox = (CheckBox) findViewById(R.id.checkbox);
        edit_birthyear_register = (EditText) findViewById(R.id.edit_birthyear_register);
        edit_birthmonth_register = (EditText) findViewById(R.id.edit_birthmonth_register);
        edit_birthday_register = (EditText) findViewById(R.id.edit_birthday_register);
        edit_pwd2_register = (EditText) findViewById(R.id.edit_pwd2_register);
        edit_pwd1_register = (EditText) findViewById(R.id.edit_pwd1_register);
        edit_username_register = (EditText) findViewById(R.id.edit_username_register);
        edit_phone = (EditText) findViewById(R.id.edit_phone);
        edit_storename_register = (EditText) findViewById(R.id.edit_storename_register);
        edit_storeaddress_register = (EditText) findViewById(R.id.edit_storeaddress_register);
        edit_sellNum_register = (EditText) findViewById(R.id.edit_sellNum_register);
        edit_sellPeople_register = (EditText) findViewById(R.id.edit_sellPeople_register);
        edit_realname = (EditText) findViewById(R.id.edit_realname);
        edit_smsnum_register = (EditText) findViewById(R.id.edit_smsnum_register);
        edit_snNum_register = (EditText) findViewById(R.id.edit_snNum_register);
    }

    @Override
    protected void initEvents() {
        findViewById(R.id.rela_shen).setOnClickListener(this);
        findViewById(R.id.rela_shi).setOnClickListener(this);
        findViewById(R.id.rela_xian).setOnClickListener(this);
        findViewById(R.id.btn_login_mefra).setOnClickListener(this);
        findViewById(R.id.text_getSMS).setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    private View dialogm() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.wheelcity_cities_layout, null);
        final WheelView country = (WheelView) contentView.findViewById(R.id.wheelcity_country);
        country.setVisibleItems(3);
        country.setViewAdapter(new CountryAdapter(this));

        final String cities[][] = AddressData.CITIES;
        final String ccities[][][] = AddressData.COUNTIES;
        final WheelView city = (WheelView) contentView.findViewById(R.id.wheelcity_city);
        city.setVisibleItems(0);

        // 地区选择
        final WheelView ccity = (WheelView) contentView.findViewById(R.id.wheelcity_ccity);
        ccity.setVisibleItems(0);// 不限城市

        country.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateCities(city, cities, newValue);
                switch (mode) {
                    case 1:
                        editPosition1 = country.getCurrentItem();
                        province = shen[country.getCurrentItem()];
                        break;
                    case 2:
                        editPosition2 = country.getCurrentItem();
                        cityStr = shen[country.getCurrentItem()];
                        break;
                    case 3:
                        editPosition3 = country.getCurrentItem();
                        countyStr = shen[country.getCurrentItem()];
                        break;

                    default:
                        break;
                }
            }
        });

        city.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updatecCities(ccity, ccities, country.getCurrentItem(), newValue);
            }
        });

        ccity.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
            }
        });

        country.setCurrentItem(1);// 设置北京
        city.setCurrentItem(1);
        ccity.setCurrentItem(1);
        return contentView;
    }

    public class CountryAdapter extends AbstractWheelTextAdapter {
        // Countries names
        // private String countries[] = AddressData.PROVINCES;
        private String countries[] = shen;

        /**
         * Constructor
         */
        protected CountryAdapter(Context context) {
            super(context, R.layout.wheelcity_country_layout, NO_RESOURCE);
            setItemTextResource(R.id.wheelcity_country_name);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return countries.length;
        }

        @Override
        protected CharSequence getItemText(int index) {
            return countries[index];
        }
    }

    private void updateCities(WheelView city, String cities[][], int index) {
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this, cities[index]);
        adapter.setTextSize(18);
        city.setViewAdapter(adapter);
        city.setCurrentItem(0);
    }

    private void updatecCities(WheelView city, String ccities[][][], int index, int index2) {
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this, ccities[index][index2]);
        adapter.setTextSize(18);
        city.setViewAdapter(adapter);
        city.setCurrentItem(0);
    }

}
