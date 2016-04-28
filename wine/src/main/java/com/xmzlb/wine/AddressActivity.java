package com.xmzlb.wine;

import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xmzlb.base.BaseActivity;
import com.xmzlb.model.Address;
import com.xmzlb.model.Datum;
import com.xmzlb.model.Status;
import com.xmzlb.registermodel.Onlystatus;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.zyzutil.YazhiHttp;
import com.xinbo.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends BaseActivity {

    private ListView lv;
    private MyAdapter myAdapter;
    private PopupWindow popupWindow;
    private RelativeLayout back;
    ArrayList<Datum> addressList = new ArrayList<Datum>();
    String addressId;
    View pop_deleteaddress;
    private int fromWhere;

    View popMenu;
    private PopupWindow popupWindowMenu; // 菜单弹窗
    private View bar;
    private TextView text_nothing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

        fromWhere = getIntent().getIntExtra("comfirmorder", -1);

        initViews();
        init();
        initEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        text_nothing.setVisibility(View.GONE);
        String url = GlobalVariable.URL.ADDRESS;
        YazhiHttp yazhiHttp = new YazhiHttp(this, url);
        yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Address parseJSON = GsonUtils.parseJSON(arg0, Address.class);
                Status status = parseJSON.getStatus();
                if (status.getSucceed() == 0) {
                    showShortToast(status.getError_desc());
                } else {
                    List<Datum> data = parseJSON.getData();
                    addressList.clear();
                    addressList.addAll(data);
                    if (addressList.size() == 0) {
                        text_nothing.setVisibility(View.VISIBLE);
                    }
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    protected void initViews() {
        lv = (ListView) findViewById(R.id.adress_lv);
        myAdapter = new MyAdapter();
        lv.setAdapter(myAdapter);

        back = (RelativeLayout) findViewById(R.id.back);
        back.setOnClickListener(this);

        text_nothing = (TextView) findViewById(R.id.text_nothing);

        pop_deleteaddress = getLayoutInflater().inflate(R.layout.pop_deletaaddress, null);
        popupWindow = new PopupWindow(pop_deleteaddress, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        BitmapDrawable b = new BitmapDrawable();
        popupWindow.setBackgroundDrawable(b);
        popupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
            }
        });

        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (fromWhere == 1) { // 确认订单界面进来，点击就返回
                    Datum datum = addressList.get(arg2);
                    String id = datum.getId();
                    Intent intent = new Intent(AddressActivity.this, ComfirmOrderActivity.class);
                    intent.putExtra("addressId", id);
                    setResult(0, intent);
                    finish();
                }
            }
        });

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

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return addressList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            TextView text_address_name;
            TextView text_address_phone;
            TextView text_address_location;
            TextView textView1; // 默认地址标签
            if (convertView == null) {
                view = getLayoutInflater().inflate(R.layout.item_lv_address, null);
                text_address_name = (TextView) view.findViewById(R.id.text_address_name);
                text_address_phone = (TextView) view.findViewById(R.id.text_address_phone);
                text_address_location = (TextView) view.findViewById(R.id.text_address_location);
                textView1 = (TextView) view.findViewById(R.id.textView1);
                view.setTag(new MyTag(text_address_name, text_address_phone, text_address_location, textView1));
            } else {
                view = convertView;
                MyTag tag2 = (MyTag) view.getTag();
                text_address_name = tag2.text_address_name;
                text_address_phone = tag2.text_address_phone;
                text_address_location = tag2.text_address_location;
                textView1 = tag2.textView1;
            }

            final Datum datum = addressList.get(position);
            text_address_name.setText(datum.getConsignee());
            text_address_phone.setText(datum.getMobile());
            text_address_location.setText(
                    datum.getProvinceName() + datum.getCityName() + datum.getDistrictName() + datum.getAddress());

            if (datum.getDefaultAddress() == 1) { // 默认地址
                textView1.setVisibility(View.VISIBLE);
            } else {
                textView1.setVisibility(View.GONE);
            }

            final String addressId = datum.getId();
            view.findViewById(R.id.text_delete).setOnClickListener(new OnClickListener() {
                // 删除
                @Override
                public void onClick(View v) {
                    AddressActivity.this.addressId = addressId;
                    popupWindow.showAtLocation(back, Gravity.TOP, 0, 0);
                }

            });
            view.findViewById(R.id.text_edit).setOnClickListener(new OnClickListener() {
                // 修改
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
                    intent.putExtra("from", "edit");
                    intent.putExtra("data", datum);
                    startActivity(intent);
                }
            });
            return view;
        }
    }

    private void deleteAddress(String addressId) {
        String url = GlobalVariable.URL.DELETEADDRESS;
        YazhiHttp yazhiHttp = new YazhiHttp(this,url);
        yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
        yazhiHttp.addParams("address_id", addressId);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Onlystatus parseJSON = GsonUtils.parseJSON(arg0, Onlystatus.class);
                com.xmzlb.registermodel.Status status = parseJSON.getStatus();
                if (status.getSucceed() == 1) {
                    showShortToast("删除成功！");
                    initData();
                } else {
                    showShortToast(status.getError_desc());
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    class MyTag {
        TextView text_address_name;
        TextView text_address_phone;
        TextView text_address_location;
        TextView textView1;

        public MyTag(TextView text_address_name, TextView text_address_phone, TextView text_address_location,
                     TextView textView1) {
            super();
            this.text_address_name = text_address_name;
            this.text_address_phone = text_address_phone;
            this.text_address_location = text_address_location;
            this.textView1 = textView1;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_address: // 新增收货地址
                Intent intent = new Intent(this, AddAddressActivity.class);
                intent.putExtra("from", "add");
                if (addressList.size() == 0) { //收货地址为空，添加的唯一一个就设为默认地址
                    intent.putExtra("isZero", 1);
                }
                startActivity(intent);
                break;
            case R.id.back:
                if (fromWhere == 1) { //如果是从确认订单点击添加收货地址进来的
                    if (addressList.size() == 1) { //本来没有，添加了一个，点击返回默认是选择这个
                        String id = addressList.get(0).getId();
                        Intent intent_finish = new Intent(AddressActivity.this, ComfirmOrderActivity.class);
                        intent_finish.putExtra("addressId", id);
                        setResult(0, intent_finish);
                        finish();
                    } else if (addressList.size() > 1) { //有多个地址，点击返回则选择默认地址
                        for (Datum data : addressList) {
                            if (data.getDefaultAddress() == 1) {
                                String id = data.getId();
                                Intent intent_finish = new Intent(AddressActivity.this, ComfirmOrderActivity.class);
                                intent_finish.putExtra("addressId", id);
                                setResult(0, intent_finish);
                            }
                        }
                        finish();
                    } else if (addressList.size() == 0) {
                        showShortToast("请添加收货地址!");
                        Intent intent_finish = new Intent(AddressActivity.this, ComfirmOrderActivity.class);
                        intent_finish.putExtra("addressId", "");
                        setResult(0, intent_finish);
                        finish();
                    }
                } else {
                    finish();
                }
                break;
            case R.id.text_comfirm:
                deleteAddress(addressId);
                popupWindow.dismiss();
                break;
            case R.id.text_cancel:
                popupWindow.dismiss();
                break;
            case R.id.rela_menu1:
            case R.id.tab_item1: // 点击底栏首页
                popupWindowMenu.dismiss();
                Intent intent_menu1 = new Intent(AddressActivity.this, MainActivity.class);
                intent_menu1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_menu1.putExtra("tabhost", 0);
                startActivity(intent_menu1);
                break;
            case R.id.rela_menu2:
            case R.id.tab_item2: // 点击底栏购物车
                popupWindowMenu.dismiss();
                Intent intent_menu2 = new Intent(AddressActivity.this, MainActivity.class);
                intent_menu2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_menu2.putExtra("tabhost", 1);
                startActivity(intent_menu2);
                break;
            case R.id.rela_menu3:
            case R.id.tab_item3: // 点击底栏订单中心
                popupWindowMenu.dismiss();
                Intent intent_menu3 = new Intent(AddressActivity.this, MainActivity.class);
                intent_menu3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_menu3.putExtra("tabhost", 2);
                startActivity(intent_menu3);
                break;
            case R.id.rela_menu4:
                popupWindowMenu.dismiss();
                finish();
                break;
            case R.id.tab_item4: // 点击底栏商场指南
                Intent intent_tab4 = new Intent(AddressActivity.this, MainActivity.class);
                intent_tab4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_tab4.putExtra("tabhost", 3);
                startActivity(intent_tab4);
                break;
            case R.id.tab_item5: // 点击底栏我要供货
                Intent intent_tab5 = new Intent(AddressActivity.this, MainActivity.class);
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
    protected void initEvents() {
        findViewById(R.id.add_address).setOnClickListener(this);
        pop_deleteaddress.findViewById(R.id.text_comfirm).setOnClickListener(this);
        pop_deleteaddress.findViewById(R.id.text_cancel).setOnClickListener(this);

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

    }
}
