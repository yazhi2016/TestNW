package com.xmzlb.wine;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xmzlb.base.BaseActivity;
import com.xmzlb.model.Complain;
import com.xmzlb.model.Datum9;
import com.xmzlb.model.Like;
import com.xmzlb.model.Status;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.zyzutil.YazhiHttp;
import com.xinbo.utils.GsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComplaintActivity extends BaseActivity {

    private RelativeLayout rela_newComplaint;
    private RelativeLayout rela_stillComplaint;
    private RelativeLayout rela_doneComplaint;
    private ListView lv_stillComplaint; // 处理中投诉listview
    private ListView lv_doneComplaint; // 已完结投诉listview
    private LvStillAdapter lvStillAdapter;
    private LvDoneAdapter lvDoneAdapter;
    String type = "0"; // 0.处理中，1，已处理
    ArrayList<Datum9> unDoneList = new ArrayList<Datum9>();
    ArrayList<Datum9> doneList = new ArrayList<Datum9>();
    private TextView text_nothing;
    private EditText edit_why;
    private EditText edit_require;

    View popMenu;
    private PopupWindow popupWindowMenu; // 菜单弹窗
    private View bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        initViews();
        init();
        initEvents();
    }

    private void initData() {
        String url = GlobalVariable.URL.COMPLAIN;
        YazhiHttp yazhiHttp = new YazhiHttp(this, url);
        yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
        yazhiHttp.addParams("type", type);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Complain parseJSON = GsonUtils.parseJSON(arg0, Complain.class);
                Status status = parseJSON.getStatus();
                if (status.getSucceed() == 1) {
                    List<Datum9> data = parseJSON.getData();
                    if (type.equals("0")) { // 未处理
                        unDoneList.clear();
                        unDoneList.addAll(data);
                        lvStillAdapter.notifyDataSetChanged();
                        if (unDoneList.size() == 0) {
                            text_nothing.setVisibility(View.VISIBLE);
                        }
                    } else {
                        doneList.clear();
                        doneList.addAll(data);
                        lvDoneAdapter.notifyDataSetChanged();
                        if (doneList.size() == 0) {
                            text_nothing.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    showShortToast(status.getError_desc());
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radio0: // 新增投诉
                rela_newComplaint.setVisibility(View.VISIBLE);
                rela_stillComplaint.setVisibility(View.GONE);
                rela_doneComplaint.setVisibility(View.GONE);
                text_nothing.setVisibility(View.GONE);
                break;
            case R.id.radio1: // 处理中投诉
                type = "0";
                rela_newComplaint.setVisibility(View.GONE);
                rela_stillComplaint.setVisibility(View.VISIBLE);
                rela_doneComplaint.setVisibility(View.GONE);
                text_nothing.setVisibility(View.GONE);
                initData();
                break;
            case R.id.radio2: // 已完结投诉
                type = "1";
                rela_newComplaint.setVisibility(View.GONE);
                rela_stillComplaint.setVisibility(View.GONE);
                rela_doneComplaint.setVisibility(View.VISIBLE);
                text_nothing.setVisibility(View.GONE);
                initData();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.btn_login_mefra: // 我要投诉
                String why = edit_why.getText().toString();
                if (why.isEmpty()) {
                    edit_why.setError("投诉理由不能为空");
                    return;
                }
                String require = edit_require.getText().toString();
                if (require.isEmpty()) {
                    edit_require.setError("处理要求不能为空");
                    return;
                }
                String url = GlobalVariable.URL.IWANTCOMPLAIN;
                Map<String, String> params = new HashMap<>();
                YazhiHttp yazhiHttp = new YazhiHttp(this, url);
                yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
                yazhiHttp.addParams("why", why);
                yazhiHttp.addParams("requirements", require);
                yazhiHttp.post(new YazhiHttp.MyListener() {
                    @Override
                    public void onSuccess(String arg0) {
                        Like parseJSON = GsonUtils.parseJSON(arg0, Like.class);
                        Status status = parseJSON.getStatus();
                        if (status.getSucceed() == 1) {
                            showShortToast(parseJSON.getData().getMsg());
                            // 清空输入框
                            edit_why.setText("");
                            edit_require.setText("");
                            // 跳转到处理中投诉
                            type = "0";
                            rela_newComplaint.setVisibility(View.GONE);
                            rela_stillComplaint.setVisibility(View.VISIBLE);
                            rela_doneComplaint.setVisibility(View.GONE);
                            text_nothing.setVisibility(View.GONE);
                            initData();
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
                Intent intent_menu1 = new Intent(ComplaintActivity.this, MainActivity.class);
                intent_menu1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_menu1.putExtra("tabhost", 0);
                startActivity(intent_menu1);
                break;
            case R.id.rela_menu2:
            case R.id.tab_item2: // 点击底栏购物车
                popupWindowMenu.dismiss();
                Intent intent_menu2 = new Intent(ComplaintActivity.this, MainActivity.class);
                intent_menu2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_menu2.putExtra("tabhost", 1);
                startActivity(intent_menu2);
                break;
            case R.id.rela_menu3:
            case R.id.tab_item3: // 点击底栏订单中心
                popupWindowMenu.dismiss();
                Intent intent_menu3 = new Intent(ComplaintActivity.this, MainActivity.class);
                intent_menu3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_menu3.putExtra("tabhost", 2);
                startActivity(intent_menu3);
                break;
            case R.id.rela_menu4:
                popupWindowMenu.dismiss();
                Intent intent_menu4 = new Intent(ComplaintActivity.this, UserActivity.class);
                intent_menu4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent_menu4);
                finish();
                break;
            case R.id.tab_item4: // 点击底栏商场指南
                Intent intent_tab4 = new Intent(ComplaintActivity.this, MainActivity.class);
                intent_tab4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_tab4.putExtra("tabhost", 3);
                startActivity(intent_tab4);
                break;
            case R.id.tab_item5: // 点击底栏我要供货
                Intent intent_tab5 = new Intent(ComplaintActivity.this, MainActivity.class);
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
        rela_newComplaint = (RelativeLayout) findViewById(R.id.rela_newComplaint);
        rela_stillComplaint = (RelativeLayout) findViewById(R.id.rela_stillComplaint);
        rela_doneComplaint = (RelativeLayout) findViewById(R.id.rela_doneComplaint);

        lv_stillComplaint = (ListView) findViewById(R.id.lv_stillComplaint);
        lv_doneComplaint = (ListView) findViewById(R.id.lv_doneComplaint);

        lvStillAdapter = new LvStillAdapter();
        lv_stillComplaint.setAdapter(lvStillAdapter);
        lvDoneAdapter = new LvDoneAdapter();
        lv_doneComplaint.setAdapter(lvDoneAdapter);

        text_nothing = (TextView) findViewById(R.id.text_nothing);

        edit_why = (EditText) findViewById(R.id.edit_why);
        edit_require = (EditText) findViewById(R.id.edit_require);

        bar = findViewById(R.id.bar);
        // 菜单
        popMenu = getLayoutInflater().inflate(R.layout.include_menu, null);
        popupWindowMenu = new PopupWindow(popMenu, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        // 设置点击窗体以外区域关闭窗体
        BitmapDrawable b2 = new BitmapDrawable();
        popupWindowMenu.setBackgroundDrawable(b2);
        popupWindowMenu.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {

            }
        });
    }

    class LvStillAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return unDoneList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            TextView text_time;
            TextView text_orderid;
            TextView text_reason;
            TextView text_state;

            if (convertView == null) {
                view = getLayoutInflater().inflate(R.layout.item_lvstill_complaint, null);
                text_time = (TextView) view.findViewById(R.id.text_time);
                text_orderid = (TextView) view.findViewById(R.id.text_orderid);
                text_reason = (TextView) view.findViewById(R.id.text_reason);
                text_state = (TextView) view.findViewById(R.id.text_state);
                view.setTag(new MyTag(text_time, text_orderid, text_reason, text_state));
            } else {
                view = convertView;
                MyTag tag2 = (MyTag) view.getTag();
                text_time = tag2.text_time;
                text_orderid = tag2.text_orderid;
                text_reason = tag2.text_reason;
                text_state = tag2.text_state;
            }

            Datum9 datum9 = unDoneList.get(position);
            text_time.setText(datum9.getTime());
            text_orderid.setText(datum9.getMsgTitle());
            text_reason.setText(datum9.getMsgContent());
            return view;
        }

    }

    class MyTag {
        TextView text_time;
        TextView text_orderid;
        TextView text_reason;
        TextView text_state;

        public MyTag(TextView text_time, TextView text_orderid, TextView text_reason, TextView text_state) {
            super();
            this.text_time = text_time;
            this.text_orderid = text_orderid;
            this.text_reason = text_reason;
            this.text_state = text_state;
        }

    }

    class MyTagTwo {
        TextView text_time;
        TextView text_orderid;
        TextView text_reason;
        TextView text_state;

        public MyTagTwo(TextView text_time, TextView text_orderid, TextView text_reason, TextView text_state) {
            super();
            this.text_time = text_time;
            this.text_orderid = text_orderid;
            this.text_reason = text_reason;
            this.text_state = text_state;
        }

    }

    class LvDoneAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return doneList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            TextView text_time;
            TextView text_orderid;
            TextView text_reason;
            TextView text_state;

            if (convertView == null) {
                view = getLayoutInflater().inflate(R.layout.item_lvdone_complaint, null);
                text_time = (TextView) view.findViewById(R.id.text_time);
                text_orderid = (TextView) view.findViewById(R.id.text_orderid);
                text_reason = (TextView) view.findViewById(R.id.text_reason);
                text_state = (TextView) view.findViewById(R.id.text_state);
                view.setTag(new MyTag(text_time, text_orderid, text_reason, text_state));
            } else {
                view = convertView;
                MyTag tag2 = (MyTag) view.getTag();
                text_time = tag2.text_time;
                text_orderid = tag2.text_orderid;
                text_reason = tag2.text_reason;
                text_state = tag2.text_state;
            }

            Datum9 datum9 = doneList.get(position);
            text_time.setText(datum9.getTime());
            text_orderid.setText(datum9.getMsgTitle());
            text_reason.setText(datum9.getMsgContent());
            return view;
        }

    }

    @Override
    protected void initEvents() {
        findViewById(R.id.radio0).setOnClickListener(this);
        findViewById(R.id.radio1).setOnClickListener(this);
        findViewById(R.id.radio2).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.btn_login_mefra).setOnClickListener(this);

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
