package com.xmzlb.wine;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.pay.demo.AliPay;
import com.alipay.sdk.pay.demo.PayListener;
import com.xmzlb.base.BaseActivity;
import com.xmzlb.model.Address;
import com.xmzlb.model.Data12;
import com.xmzlb.model.Datum;
import com.xmzlb.model.Datum2;
import com.xmzlb.model.PayDetail;
import com.xmzlb.model.Shopcar;
import com.xmzlb.model.Status;
import com.xmzlb.model.WXparams;
import com.xmzlb.registermodel.Userinfo;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.zyzutil.YazhiHttp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.UILUtils;
import com.xinbo.widget.ListView4ScrollView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ComfirmOrderActivity extends BaseActivity {

    private TextView text_topbar;
    private TextView text_num;
    private RelativeLayout rela_beforeConfirm;
    private LinearLayout rela_Confirm;
    private ListView4ScrollView lv_confirmorder;
    private LvAdapter lvAdapter;
    private String fromWhere; // 0 从详情界面点击立刻购买进入，1从购物车点击提交订单进入
    String goods_id;
    ArrayList<String> shopId = new ArrayList<String>();
    ArrayList<Datum2> list = new ArrayList<Datum2>(); // 购物车列表
    ArrayList<Datum> addressList = new ArrayList<Datum>(); // 地址列表
    String rec_id;
    String address_id; // 地址ID
    private TextView text_name;
    private TextView text_phone;
    private TextView text_address;
    ArrayList<Datum2> showOrderList = new ArrayList<Datum2>(); // list显示的
    Map<Integer, goodNum> goodNumMap = new HashMap<Integer, goodNum>(); // 存储每行的数量和购物ID和单价
    private ArrayList<Integer> choosedPosition;
    private TextView text_money;
    private TextView text_num2;
    double allMoney;
    int payMode; // 0普通会员 线下先付，1金卡银卡，货到付款
    private TextView textvie6;
    boolean haveAddress = false;

    int payMoneyMode; // 0支付宝，1银联, 2微信

    View popMenu;
    private PopupWindow popupWindowMenu; // 菜单弹窗
    private View bar;
    private int buyNum;

    boolean needAliPay = false;
    private TextView text_money2; // 需要支付的价格

    private String oderSn = "";
    private String msg;
    private String title;
    private double money;
    private EditText edit_message;


    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_comfirm_order);
        Intent intent = getIntent();
        goods_id = intent.getStringExtra("goods_id");
        fromWhere = intent.getStringExtra("loadorder");
        if (fromWhere.equals("1")) { // 如果是从购物车进入的，则会带有提交的购物车列表中选中要付款的行号的数组
            choosedPosition = intent.getIntegerArrayListExtra("choosedPosition");
        }
        buyNum = intent.getIntExtra("buyNum", -1);

        initViews();
        init();
        initEvents();

        //将应用appID注册到微信
        api = WXAPIFactory.createWXAPI(this, GlobalVariable.APP_ID, true);
        api.registerApp(GlobalVariable.APP_ID);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initShopList();
        initAddressList();
        initUserInfo();
    }

    private void initUserInfo() {
        String url = GlobalVariable.URL.USERCENTER;
        String spSid = GlobalVariable.getInstance().getSpSid("empty");
        YazhiHttp yazhiHttp = new YazhiHttp(this, url);
        yazhiHttp.addParams("sid", spSid);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Userinfo parseJSON = GsonUtils.parseJSON(arg0, Userinfo.class);
                com.xmzlb.registermodel.Status status = parseJSON.getStatus();
                if (status.getSucceed() == 1) { // 得到数据
                    String rankName = parseJSON.getData().getRankName();
                    if (rankName.contains("普通会员")) { // 普通会员，小于1000是线下先付
                        payMode = 0;
                        if (allMoney > 1000) {
                            textvie6.setText("物流保证金 10%");
                        } else {
                            textvie6.setText("线下先付");
                        }
                    } else { // 金卡银卡货到付款
                        payMode = 1;
                        textvie6.setText("货到付款");
                    }
                } else { // 接受数据失败
                    showShortToast(status.getError_desc());
                    if (status.getSucceed() == 2) {
                        Intent intentLogIn = new Intent(ComfirmOrderActivity.this, UserLogInActivity.class);
                        intentLogIn.putExtra("from", 0);
                        intentLogIn.putExtra("fromStatus", 0);
                        startActivity(intentLogIn);
                    }
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void initNumAndMoney(String strNum, String price) {
        if (fromWhere.equals("0")) { // 详情页进来只有一个
            text_num2.setText("1");
            text_money.setText(Double.parseDouble(price) * Integer.parseInt(strNum) + "");
        } else {
            allMoney += Double.parseDouble(price) * Integer.parseInt(strNum);
        }
    }

    private void initAddressList() {

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
                    if (data.size() == 0) { // 如果没有地址，则跳到地址列表界面
                        showShortToast("请添加收货地址!");
                        haveAddress = false;
                        return;
                    }
                    for (int i = 0; i < addressList.size(); i++) {
                        Datum datum = addressList.get(i);
                        if (datum.getDefaultAddress() == 1) { // 默认地址
                            address_id = datum.getId();
                            text_name.setText(datum.getConsignee());
                            text_phone.setText(datum.getMobile());
                            text_address.setText(datum.getProvinceName() + datum.getCityName() + datum.getDistrictName()
                                    + datum.getAddress());
                            haveAddress = true;
                            return;
                        }
                    }
                }
            }

            @Override
            public void onError() {

            }
        });

    }

    private void initShopList() {
        String url = GlobalVariable.URL.SHOPCAR;
        YazhiHttp yazhiHttp = new YazhiHttp(this, url);
        yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Shopcar parseJSON = GsonUtils.parseJSON(arg0, Shopcar.class);
                Status status = parseJSON.getStatus();
                if (status.getSucceed() == 1) {
                    List<Datum2> data2 = parseJSON.getData();
                    if (fromWhere.equals("0")) { // 从详情界面进入，只有一个
                        for (int i = 0; i < data2.size(); i++) {
                            Datum2 datum2 = data2.get(i);
                            if (datum2.getGoodsId().equals(goods_id)) {
                                rec_id = datum2.getRecId();
                                showOrderList.clear();
                                showOrderList.add(datum2);
                                lvAdapter.notifyDataSetChanged();

                                if (buyNum == -1) {
                                    allMoney = Double.parseDouble(datum2.getShopPrice());
                                } else {
                                    allMoney = Double.parseDouble(datum2.getShopPrice()) * buyNum;
                                }
                                initNumAndMoney(buyNum + "", datum2.getShopPrice());
                                goodNumMap.put(0, new goodNum(rec_id, Integer.parseInt(datum2.getGoodsNumber()),
                                        Double.parseDouble(datum2.getShopPrice())));
                                return;
                            }
                        }
                    } else { // 从购物车界面进入
                        showOrderList.clear();
                        for (int i = 0; i < choosedPosition.size(); i++) {
                            Integer integer = choosedPosition.get(i);
                            showOrderList.add(data2.get(integer));
                        }
                        lvAdapter.notifyDataSetChanged();
                        allMoney = 0;
                        for (int i = 0; i < showOrderList.size(); i++) {
                            Datum2 datum2 = showOrderList.get(i);
                            initNumAndMoney(datum2.getGoodsNumber(), datum2.getShopPrice());
                        }
                        text_money.setText(allMoney + "");
                        text_num2.setText(showOrderList.size() + "");
                        return;
                    }
                } else {
                    Toast.makeText(ComfirmOrderActivity.this, status.getError_desc(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void changeNum(int position) {
        String url = GlobalVariable.URL.CHANGENUM;
        YazhiHttp yazhiHttp = new YazhiHttp(this, url);
        yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
        goodNum goodNum = goodNumMap.get(position);
        yazhiHttp.addParams("rec_id", goodNum.goodId);
        yazhiHttp.addParams("new_number", goodNum.num + "");
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {

            }

            @Override
            public void onError() {

            }
        });
    }


    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        if (arg0 == 0 && arg1 == 0) {
            addressList.clear();
            //点击地址返回的，选中点击的那个
            if (arg2.getStringExtra("addressId") != null) {
                String addressId = arg2.getStringExtra("addressId");
                address_id = addressId;
            }
            if (address_id.isEmpty()) {
                showShortToast("请添加收货地址!");
                haveAddress = false;
                text_name.setText("");
                text_phone.setText("");
                text_address.setText("");
            } else {
                getAllAddress();
            }
        }

    }

    private void getAllAddress() {

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
                    addressList.addAll(data);
                    if (addressList.size() == 1) { //本来没地址，添加了一个点击返回，默认选中唯一一个
                        address_id = addressList.get(0).getId();
                        Datum datum = addressList.get(0);
                        text_name.setText(datum.getConsignee());
                        text_phone.setText(datum.getMobile());
                        text_address.setText(datum.getProvinceName() + datum.getCityName() + datum.getDistrictName()
                                + datum.getAddress());
                        haveAddress = true;
                    } else {
                        for (int i = 0; i < addressList.size(); i++) {
                            Datum datum = addressList.get(i);
                            String id = datum.getId();
                            if (id.equals(address_id)) {
                                text_name.setText(datum.getConsignee());
                                text_phone.setText(datum.getMobile());
                                text_address.setText(datum.getProvinceName() + datum.getCityName() + datum.getDistrictName()
                                        + datum.getAddress());
                                haveAddress = true;
                            }
                        }
                    }
                    if (data.size() == 0) { // 如果没有地址，则跳到地址列表界面
                        showShortToast("请添加收货地址!");
                        haveAddress = false;
                        return;
                    }
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
            case R.id.text_guide: //跳转商城指南
                Intent intent12 = new Intent(ComfirmOrderActivity.this, MainActivity.class);
                intent12.putExtra("tabhost", 6);
                intent12.putExtra("item", 1);
                startActivity(intent12);
                break;
            case R.id.radio0:
                payMoneyMode = 0;
                break;
            case R.id.radio2:
                payMoneyMode = 1;
                break;
            case R.id.radio1:
                payMoneyMode = 2;
                break;
            case R.id.rela_menu1:
            case R.id.tab_item1: // 点击底栏首页
                popupWindowMenu.dismiss();
                Intent intent_menu1 = new Intent(ComfirmOrderActivity.this, MainActivity.class);
                intent_menu1.putExtra("tabhost", 0);
                startActivity(intent_menu1);
                break;
            case R.id.rela_menu2:
            case R.id.tab_item2: // 点击底栏购物车
                popupWindowMenu.dismiss();
                Intent intent_menu2 = new Intent(ComfirmOrderActivity.this, MainActivity.class);
                intent_menu2.putExtra("tabhost", 1);
                startActivity(intent_menu2);
                break;
            case R.id.rela_menu3:
            case R.id.tab_item3: // 点击底栏订单中心
                popupWindowMenu.dismiss();
                Intent intent_menu3 = new Intent(ComfirmOrderActivity.this, MainActivity.class);
                intent_menu3.putExtra("tabhost", 2);
                startActivity(intent_menu3);
                break;
            case R.id.rela_menu4:
                popupWindowMenu.dismiss();
                Intent intent_menu4 = new Intent(ComfirmOrderActivity.this, UserActivity.class);
                startActivity(intent_menu4);
                break;
            case R.id.tab_item4: // 点击底栏商场指南
                Intent intent_tab4 = new Intent(ComfirmOrderActivity.this, MainActivity.class);
                intent_tab4.putExtra("tabhost", 3);
                startActivity(intent_tab4);
                break;
            case R.id.tab_item5: // 点击底栏我要供货
                Intent intent_tab5 = new Intent(ComfirmOrderActivity.this, IWantSupply.class);
                startActivity(intent_tab5);
                break;
            case R.id.rela_empty_meun:
                popupWindowMenu.dismiss();
                break;
            case R.id.rele_menu_detailact: // 点击顶栏菜单
                popupWindowMenu.showAsDropDown(bar);
                break;
            case R.id.text_pay: // 提交订单

                if (!haveAddress) { // 没有地址
                    showShortToast("请添加地址！");
                    return;
                }
                // 提交数量修改
                for (int i = 0; i < goodNumMap.size(); i++) {
                    changeNum(i);
                }

                String orderRecid = "";
                for (int i = 0; i < goodNumMap.size(); i++) {
                    goodNum goodNum = goodNumMap.get(i);
                    orderRecid += goodNum.goodId;
                    if ((i + 1) != goodNumMap.size()) {
                        orderRecid += ",";
                    }
                }
                // 提交订单
                if (payMode == 1) { // 普通会员以上都是直接提交
                    payNow(orderRecid);
                } else { // 普通会员，且订单金额1000以上要支付10%
                    double allMoeny = Double.parseDouble(text_money.getText().toString());
                    if (allMoeny > 1000) {
                        if (oderSn.isEmpty()) {
                            needAliPay = true;
                            payNow(orderRecid);
                        } else {
                            rela_Confirm.setVisibility(View.VISIBLE);
                        }
                    } else {
                        payNow(orderRecid);
                    }
                }

                // text_num.setVisibility(View.VISIBLE);
                // rela_beforeConfirm.setVisibility(View.GONE);
                // rela_beforeConfirm.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_confirm: // 需要额外付款时确认付款
                if (payMoneyMode == 0) { //支付宝支付
                    new AliPay(ComfirmOrderActivity.this, listener).pay(title, money + "", oderSn);
                } else if (payMoneyMode == 2) { //微信支付
                    //访问接口获得订单相关信息
                    getWXOrder(title, oderSn, (int) (money * 100) + ""); //乘以100.微信支付单位是分
                } else { //银联支付

                }
                break;
            case R.id.ima_close: // 关闭额外付款弹窗
            case R.id.pay_empty: // 关闭额外付款弹窗
                rela_Confirm.setVisibility(View.GONE);
                break;
            case R.id.back:
                finish();
                break;
            case R.id.text_changeAddress: // 更改地址
                Intent intent = new Intent(ComfirmOrderActivity.this, AddressActivity.class);
                intent.putExtra("comfirmorder", 1);
                startActivityForResult(intent, 0);
                break;
            default:
                break;
        }

    }

    private void getWXOrder(String title, String orderRecid, String money) {
        YazhiHttp yazhiHttp = new YazhiHttp(GlobalVariable.URL.WXPAY);
        yazhiHttp.addParams("package_detail", title);
        yazhiHttp.addParams("order_id", orderRecid);
        yazhiHttp.addParams("total_pay", money);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                WXparams wXparams = GsonUtils.parseJSON(arg0, WXparams.class);
                WXparams.ResultBean result = wXparams.getResult();
                if (api != null) {
                    PayReq req = new PayReq();
                    req.appId = GlobalVariable.APP_ID;
                    req.nonceStr = result.getNoncestr();
                    req.packageValue = result.getPackageX();
                    req.partnerId = result.getPartnerid();
                    req.prepayId = result.getPrepayid();
                    req.timeStamp = String.valueOf(result.getTimestamp());
                    req.sign = result.getSign();

                    api.sendReq(req);
                }

            }

            @Override
            public void onError() {
            }
        });
    }

    PayListener listener = new PayListener() {
        @Override
        public void payResult(String pays) {
            switch (pays) {
                case "9000":// 成功
                    // 支付成功返回待发货界面
                    Intent intent3 = new Intent(ComfirmOrderActivity.this, MainActivity.class);
                    intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent3.putExtra("tabhost", 5);
                    intent3.putExtra("item", 1); // 跳转待收货界面
                    startActivity(intent3);
                    showShortToast("支付宝支付成功");
                    // setResult(ResultCode.payusback, new Intent());
                    // finish();
                    break;
                case "8000":// 支付中
                    showShortToast("支付结果确认中");
                    break;
                default:// 支付失败
                    showShortToast("支付宝支付失败");
                    break;
            }
        }
    };
    private TextView btn_confirm;

    private void payNow(String orderRecid) {
        String url = GlobalVariable.URL.LOADORDER;
        YazhiHttp yazhiHttp = new YazhiHttp(this, url);
        yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
        yazhiHttp.addParams("rec_id", orderRecid);
        yazhiHttp.addParams("address_id", address_id);
        String message = edit_message.getText().toString();
        if (!message.isEmpty()) { //如果买家有留言
            yazhiHttp.addParams("postscript", message);
        }
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                PayDetail parseJSON = GsonUtils.parseJSON(arg0, PayDetail.class);
                Status status = parseJSON.getStatus();
                if (status.getSucceed() == 1) {
                    Data12 data = parseJSON.getData();
                    if (needAliPay) { // 需要支付宝支付
                        rela_Confirm.setVisibility(View.VISIBLE);
                        oderSn = data.getOderSn();
                        msg = data.getMsg();
                        title = data.getTitle();
                        money = data.getMoney();
                        text_money2.setText("￥" + money);
                    } else {
                        showLongToast(parseJSON.getData().getMsg());
                        Intent intent3 = new Intent(ComfirmOrderActivity.this, MainActivity.class);
                        intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent3.putExtra("tabhost", 5);
                        intent3.putExtra("item", 0); // 跳转待付款界面
                        startActivity(intent3);
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
    protected void initViews() {
        edit_message = (EditText) findViewById(R.id.edit_message);
        text_money2 = (TextView) findViewById(R.id.text_money2);
        btn_confirm = (TextView) findViewById(R.id.btn_confirm);
        text_topbar = (TextView) findViewById(R.id.text_topbar);
        text_num = (TextView) findViewById(R.id.text_num);
        rela_beforeConfirm = (RelativeLayout) findViewById(R.id.rela_beforeConfirm);
        rela_Confirm = (LinearLayout) findViewById(R.id.rela_Confirm);

        lv_confirmorder = (ListView4ScrollView) findViewById(R.id.lv_confirmorder);
        lvAdapter = new LvAdapter();
        lv_confirmorder.setAdapter(lvAdapter);

        text_name = (TextView) findViewById(R.id.text_name);
        text_phone = (TextView) findViewById(R.id.text_phone);
        text_address = (TextView) findViewById(R.id.text_address);

        text_money = (TextView) findViewById(R.id.text_money);
        text_num2 = (TextView) findViewById(R.id.text_num2);

        textvie6 = (TextView) findViewById(R.id.textvie6); // 支付方式

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

        findViewById(R.id.text_guide).setOnClickListener(this);
    }

    class LvAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return showOrderList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view;
            ImageView imageView1;
            ImageView ima_addt_detailact;
            ImageView ima_substract_detailact;
            TextView text_title;
            TextView textView3;
            TextView textView2;
            TextView textView1;
            TextView textView6;
            final TextView editText1;
            if (convertView == null) {
                view = getLayoutInflater().inflate(R.layout.item_lv_confirmorder, null);
                imageView1 = (ImageView) view.findViewById(R.id.imageView1);
                ima_addt_detailact = (ImageView) view.findViewById(R.id.ima_addt_detailact);
                ima_substract_detailact = (ImageView) view.findViewById(R.id.ima_substract_detailact);
                text_title = (TextView) view.findViewById(R.id.text_title);
                textView3 = (TextView) view.findViewById(R.id.textView3);
                textView2 = (TextView) view.findViewById(R.id.textView2);
                textView1 = (TextView) view.findViewById(R.id.textView1);
                editText1 = (TextView) view.findViewById(R.id.editText1);
                textView6 = (TextView) view.findViewById(R.id.textView6);
                view.setTag(new MyTag(imageView1, ima_addt_detailact, ima_substract_detailact, text_title, textView3,
                        textView2, textView1, editText1, textView6));
            } else {
                view = convertView;
                MyTag tag2 = (MyTag) view.getTag();
                imageView1 = tag2.imageView1;
                ima_addt_detailact = tag2.ima_addt_detailact;
                ima_substract_detailact = tag2.ima_substract_detailact;
                text_title = tag2.text_title;
                textView3 = tag2.textView3;
                textView2 = tag2.textView2;
                textView1 = tag2.textView1;
                editText1 = tag2.editText1;
                textView6 = tag2.textView6;
            }
            Datum2 datum2 = showOrderList.get(position);
            UILUtils.displayImageNoAnim(datum2.getOriginalImg(), imageView1);
            text_title.setText(datum2.getGoodsName());
            if (datum2.getIsOne().equals("1")) {
                textView3.setText("商品规格:单瓶起批");
                textView2.setText("组合形式：无");

            } else {
                textView3.setText("商品规格:整组起批");
                textView2.setText("组合形式：" + datum2.getOneNum() + "瓶/组");
            }
            textView1.setText("促销信息：满" + datum2.getFullMoney() + "减" + datum2.getSubMoney());
            textView6.setText("￥" + datum2.getShopPrice());

            if (fromWhere.equals("0")) { // 从详情界面进入，只有一个
                if (buyNum == -1) {
                    editText1.setText("1");
                } else {
                    editText1.setText(buyNum + "");
                }
                // 把物品的购物车ID和数量写进数组
                goodNumMap.put(position, new goodNum(datum2.getRecId(), Integer.parseInt(datum2.getGoodsNumber()),
                        Double.parseDouble(datum2.getShopPrice())));
            } else {
                editText1.setText(datum2.getGoodsNumber());
                goodNumMap.put(position, new goodNum(datum2.getRecId(), Integer.parseInt(datum2.getGoodsNumber()),
                        Double.parseDouble(datum2.getShopPrice())));
            }

            final String recId2 = datum2.getRecId();
            final String shopPrice = datum2.getShopPrice();
            final double parseDouble = Double.parseDouble(datum2.getShopPrice());
            // 数量减
            ima_substract_detailact.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    int parseInt;
                    String string = editText1.getText().toString();
                    if (string.equals("1")) {
                        return;
                    } else {
                        parseInt = Integer.parseInt(string);
                        parseInt--;
                        editText1.setText(parseInt + "");
                        allMoney -= Double.parseDouble(shopPrice);
                        text_money.setText(allMoney + "");
                    }
                    goodNumMap.put(position, new goodNum(recId2, parseInt, parseDouble));

                    if (payMode == 0) {
                        if (allMoney > 1000) {
                            textvie6.setText("选择支付方式");
                        } else {
                            textvie6.setText("线下支付");
                        }
                    } else {
                        textvie6.setText("线下支付");
                    }
                }
            });

            // 数量加
            ima_addt_detailact.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    String string = editText1.getText().toString();
                    int parseInt = Integer.parseInt(string);
                    parseInt++;
                    editText1.setText(parseInt + "");
                    goodNumMap.put(position, new goodNum(recId2, parseInt, parseDouble));
                    allMoney += Double.parseDouble(shopPrice);
                    text_money.setText(allMoney + "");

                    if (payMode == 0) {
                        if (allMoney > 1000) {
                            textvie6.setText("选择支付方式");
                        } else {
                            textvie6.setText("线下支付");
                        }
                    } else {
                        textvie6.setText("线下支付");
                    }
                }
            });

            return view;
        }

    }

    class MyTag {
        ImageView imageView1;
        ImageView ima_addt_detailact;
        ImageView ima_substract_detailact;
        TextView text_title;
        TextView textView3;
        TextView textView2;
        TextView textView1;
        TextView editText1;
        TextView textView6;

        public MyTag(ImageView imageView1, ImageView ima_addt_detailact, ImageView ima_substract_detailact,
                     TextView text_title, TextView textView3, TextView textView2, TextView textView1, TextView editText1,
                     TextView textView6) {
            super();
            this.imageView1 = imageView1;
            this.ima_addt_detailact = ima_addt_detailact;
            this.ima_substract_detailact = ima_substract_detailact;
            this.text_title = text_title;
            this.textView3 = textView3;
            this.textView2 = textView2;
            this.textView1 = textView1;
            this.editText1 = editText1;
            this.textView6 = textView6;
        }

    }

    @Override
    protected void initEvents() {
        findViewById(R.id.text_pay).setOnClickListener(this);
        findViewById(R.id.pay_empty).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.text_changeAddress).setOnClickListener(this);
        findViewById(R.id.btn_confirm).setOnClickListener(this);
        findViewById(R.id.ima_close).setOnClickListener(this);
        findViewById(R.id.radio2).setOnClickListener(this);
        findViewById(R.id.radio1).setOnClickListener(this);
        findViewById(R.id.radio0).setOnClickListener(this);

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
        text_topbar.setText("提交订单");
    }

    // 存储购物车id 和数量用于修改购物车数量
    class goodNum {
        String goodId;
        int num;
        double price;

        public goodNum(String goodId, int num, double price) {
            super();
            this.goodId = goodId;
            this.num = num;
            this.price = price;
        }

    }

}
