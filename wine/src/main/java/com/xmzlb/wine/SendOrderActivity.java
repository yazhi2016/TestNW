package com.xmzlb.wine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xmzlb.base.BaseActivity;
import com.xmzlb.model.Datum10;
import com.xmzlb.model.Sellerorder;
import com.xmzlb.model.Status;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.zyzutil.YazhiHttp;
import com.xinbo.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 配送商：待配送、待收货订单等显示界面
 */
public class SendOrderActivity extends BaseActivity {

    private ListView lv_sendcenter;
    private LvAdapter lvAdapter;
    private String type; // 点击什么进入
    private TextView textView2;
    ArrayList<Datum10> list = new ArrayList<Datum10>();
    private TextView text_nothing;
    private RelativeLayout rele_menu_detailact;
    private RelativeLayout rela1;
    private RelativeLayout rela2;
    private RelativeLayout rela3;
    private RelativeLayout rela4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_center);
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        initViews();
        initEvents();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        text_nothing.setVisibility(View.GONE);
        String url = GlobalVariable.URL.SELLERORDER;
        YazhiHttp yazhiHttp = new YazhiHttp(this, url);
        yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
        yazhiHttp.addParams("type", type);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Sellerorder parseJSON = GsonUtils.parseJSON(arg0, Sellerorder.class);
                Status status = parseJSON.getStatus();
                if (status.getSucceed() == 1) {
                    List<Datum10> data = parseJSON.getData();
                    if (data.size() == 0) {
                        text_nothing.setVisibility(View.VISIBLE);
                    } else {
                        text_nothing.setVisibility(View.GONE);
                    }
                    list.clear();
                    list.addAll(data);
                    lvAdapter.notifyDataSetChanged();
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
            case R.id.btn_confirm:
                finish();
                break;
            case R.id.rela1:
                type = "shipping_no";
                initData();
                textView2.setText("配送中心 > 待配送");
                rela1.setBackgroundResource(R.color.top);
                rela2.setBackgroundResource(R.color.white);
                rela3.setBackgroundResource(R.color.white);
                rela4.setBackgroundResource(R.color.white);
                break;
            case R.id.rela2:
                type = "confirm_no";
                initData();
                rela1.setBackgroundResource(R.color.white);
                rela2.setBackgroundResource(R.color.top);
                rela3.setBackgroundResource(R.color.white);
                rela4.setBackgroundResource(R.color.white);
                textView2.setText("配送中心 > 待收货");
                break;
            case R.id.rela3:
                type = "settlement_no";
                initData();
                rela1.setBackgroundResource(R.color.white);
                rela2.setBackgroundResource(R.color.white);
                rela3.setBackgroundResource(R.color.top);
                rela4.setBackgroundResource(R.color.white);
                textView2.setText("配送中心 > 待结算");
                break;
            case R.id.rela4:
                type = "settlement";
                rela1.setBackgroundResource(R.color.white);
                rela2.setBackgroundResource(R.color.white);
                rela3.setBackgroundResource(R.color.white);
                rela4.setBackgroundResource(R.color.top);
                initData();
                textView2.setText("配送中心 > 已结算");
                break;
            case R.id.back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        if (arg0 == 1) {
            if (arg1 == 1) {
                type = arg2.getStringExtra("finishFrom");
                if (type.equals("shipping_no")) {
                    textView2.setText("配送中心 > 待配送");
                    rela1.setBackgroundResource(R.color.top);
                    rela2.setBackgroundResource(R.color.white);
                    rela3.setBackgroundResource(R.color.white);
                    rela4.setBackgroundResource(R.color.white);
                } else if (type.equals("confirm_no")) {
                    textView2.setText("配送中心 > 待收货");
                    rela1.setBackgroundResource(R.color.white);
                    rela2.setBackgroundResource(R.color.top);
                    rela3.setBackgroundResource(R.color.white);
                    rela4.setBackgroundResource(R.color.white);
                } else if (type.equals("settlement_no")) {
                    textView2.setText("配送中心 > 待结算");
                    rela1.setBackgroundResource(R.color.white);
                    rela2.setBackgroundResource(R.color.white);
                    rela3.setBackgroundResource(R.color.top);
                    rela4.setBackgroundResource(R.color.white);
                } else {
                    textView2.setText("配送中心 > 已结算");
                    rela1.setBackgroundResource(R.color.white);
                    rela2.setBackgroundResource(R.color.white);
                    rela3.setBackgroundResource(R.color.white);
                    rela4.setBackgroundResource(R.color.top);
                }
                initData();
            }
        }
    }

    @Override
    protected void initViews() {
        lv_sendcenter = (ListView) findViewById(R.id.lv_sendcenter);
        lvAdapter = new LvAdapter();
        lv_sendcenter.setAdapter(lvAdapter);
        textView2 = (TextView) findViewById(R.id.textView2);
        text_nothing = (TextView) findViewById(R.id.text_nothing);

        lv_sendcenter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(SendOrderActivity.this, SendActivity.class);
                intent.putExtra("order_sn", list.get(arg2).getOrderSn());
                startActivityForResult(intent, 1);
            }
        });

        rele_menu_detailact = (RelativeLayout) findViewById(R.id.rele_menu_detailact);
        rela1 = (RelativeLayout) findViewById(R.id.rela1);
        rela2 = (RelativeLayout) findViewById(R.id.rela2);
        rela3 = (RelativeLayout) findViewById(R.id.rela3);
        rela4 = (RelativeLayout) findViewById(R.id.rela4);
    }

    @Override
    protected void initEvents() {
        findViewById(R.id.rela1).setOnClickListener(this);
        findViewById(R.id.rela2).setOnClickListener(this);
        findViewById(R.id.rela3).setOnClickListener(this);
        findViewById(R.id.rela4).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.btn_confirm).setOnClickListener(this);
    }

    @Override
    protected void init() {
        switch (type) {
            case "shipping_no":
                textView2.setText("配送中心 > 待配送");
                rela1.setBackgroundResource(R.color.top);
                rela2.setBackgroundResource(R.color.white);
                rela3.setBackgroundResource(R.color.white);
                rela4.setBackgroundResource(R.color.white);
                break;
            case "confirm_no":
                textView2.setText("配送中心 > 待收货");
                textView2.setText("配送中心 > 待收货");
                rela1.setBackgroundResource(R.color.white);
                rela2.setBackgroundResource(R.color.top);
                rela3.setBackgroundResource(R.color.white);
                rela4.setBackgroundResource(R.color.white);
                break;
            case "settlement_no":
                textView2.setText("配送中心 > 待结算");
                rela1.setBackgroundResource(R.color.white);
                rela2.setBackgroundResource(R.color.white);
                rela3.setBackgroundResource(R.color.top);
                rela4.setBackgroundResource(R.color.white);
                break;
            case "settlement":
                textView2.setText("配送中心 > 已结算");
                rela1.setBackgroundResource(R.color.white);
                rela2.setBackgroundResource(R.color.white);
                rela3.setBackgroundResource(R.color.white);
                rela4.setBackgroundResource(R.color.top);
                break;

            default:
                break;
        }

        rele_menu_detailact.setVisibility(View.GONE);
    }

    class LvAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
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
            TextView text_orderNum;
            TextView text_title;
            TextView text_num;

            if (convertView == null) {
                view = getLayoutInflater().inflate(R.layout.item_lv_sendcenter, null);
                text_orderNum = (TextView) view.findViewById(R.id.text_orderNum);
                text_title = (TextView) view.findViewById(R.id.text_title);
                text_num = (TextView) view.findViewById(R.id.text_num);
                view.setTag(new MyTag(text_orderNum, text_title, text_num));
            } else {
                view = convertView;
                MyTag tag2 = (MyTag) view.getTag();
                text_orderNum = tag2.text_orderNum;
                text_title = tag2.text_title;
                text_num = tag2.text_num;
            }

            Datum10 datum10 = list.get(position);
            text_orderNum.setText("订单号：" + datum10.getOrderSn());
            text_title.setText("商品：" + datum10.getGoodsName());
            if (datum10.getBottle().equals("1")) {
                text_num.setText("数量：" + datum10.getGoodsNumber() + "瓶");
            } else {
                text_num.setText("数量：" + datum10.getGoodsNumber() + "件");
            }
            return view;
        }
    }

    class MyTag {
        TextView text_orderNum;
        TextView text_title;
        TextView text_num;

        public MyTag(TextView text_orderNum, TextView text_title, TextView text_num) {
            super();
            this.text_orderNum = text_orderNum;
            this.text_title = text_title;
            this.text_num = text_num;
        }
    }

}
