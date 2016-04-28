package com.xmzlb.fragment;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.pay.demo.AliPay;
import com.alipay.sdk.pay.demo.PayListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xinbo.utils.GsonUtils;
import com.xmzlb.model.Datum3;
import com.xmzlb.model.Ordercenter;
import com.xmzlb.model.Status;
import com.xmzlb.model.WXparams;
import com.xmzlb.registermodel.Deleteorder;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.wine.MainActivity;
import com.xmzlb.wine.NewDetailActivity;
import com.xmzlb.wine.OrderDetailActivity;
import com.xmzlb.wine.R;
import com.xmzlb.wine.UserActivity;
import com.xmzlb.zyzutil.YazhiHttp;
import com.xmzlb.zyzutil.YazhiUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 首页底栏 订单中心
 */
public class AllProductFragment extends Fragment implements OnClickListener {

    private View view;
    private ListView lv_allorder;
    LayoutInflater inflater;
    int mode = 0; // 0待付款，1代收货，2已完成订单，3已取消订单
    private LvAdapter lvAdapter;
    String loadMode = "no_pay";
    ArrayList<Datum3> list = new ArrayList<Datum3>();
    private PopupWindow popupWindow;
    View pop_deleteaddress;
    String editId;
    private RadioButton radio0;
    int confirmMode = 0; // 操作状态，1取消订单，2删除订单，3确认收货
    private TextView text_empty;
    ImageOptions imageOptions;
    private TextView text_tip;
    private RadioButton radio1;
    private RadioButton radio2;
    private RadioButton radio3;
    private TextView text_topbar;
    private PopupWindow popupWindowMenu; // 菜单弹窗
    private View bar;
    private View popMenu;
    private LinearLayout mRela_confirm;
    private TextView mText_money2;
    private int payMoneyMode;  // 0支付宝，1银联, 2微信
    String payGoodsName;
    double payPrice;
    String payOrderSn;
    private IWXAPI api;
    private TextView mAllMoney;

    public AllProductFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            this.inflater = inflater;
            view = inflater.inflate(R.layout.fragment_all_product, container, false);

            imageOptions = new ImageOptions.Builder().setSize(DensityUtil.dip2px(200), DensityUtil.dip2px(200))
                    .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP).setLoadingDrawableId(R.drawable.ic_stub)
                    .setFailureDrawableId(R.drawable.ic_error)
                    // 设置使用缓存
                    .setUseMemCache(true).build();

            initUI();
            initEvent();
//            initData(loadMode);

            //访问接口获得订单相关信息
            //将应用appID注册到微信
            api = WXAPIFactory.createWXAPI(getActivity(), GlobalVariable.APP_ID, true);
            api.registerApp(GlobalVariable.APP_ID);
        }
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        list.clear();
        lvAdapter.notifyDataSetChanged();
        mRela_confirm.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        // radio0.setChecked(true);
        // mode = 0;
        // loadMode = "no_pay";
        Bundle arguments = getArguments();
        if (arguments.getInt("item") != -1) {
            if (arguments.getInt("item") == 1) {
                radio1.setChecked(true);
                mode = 1;
                initData("shipping");
                lvAdapter.notifyDataSetChanged();
            } else if (arguments.getInt("item") == 0) {
                radio0.setChecked(true);
                mode = 0;
                initData("no_pay");
                lvAdapter.notifyDataSetChanged();
            }
        } else { //默认进入未付款
            radio0.setChecked(true);
            mode = 0;
            initData("no_pay");
            lvAdapter.notifyDataSetChanged();
        }

    }

    private void initData(String type) {
        text_empty.setVisibility(View.GONE);
        String url = GlobalVariable.URL.ALLORDER;
        YazhiHttp yazhiHttp = new YazhiHttp(getActivity(), url);
        yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
        yazhiHttp.addParams("type", type);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Ordercenter parseJSON = GsonUtils.parseJSON(arg0, Ordercenter.class);
                Status status = parseJSON.getStatus();
                if (status.getSucceed() == 1) {
                    List<Datum3> data = parseJSON.getData();
                    if (data.size() == 0) {
                        text_empty.setVisibility(View.VISIBLE);
                        mAllMoney.setText("￥" + 0);
                    } else {
                        text_empty.setVisibility(View.GONE);
                        double allMoney = 0;
                        for (int i = 0; i < data.size(); i++) {
                            double v = Double.parseDouble(data.get(i).getGoodsAmount());
                            allMoney += v;
                        }
                        mAllMoney.setText("￥" + allMoney);
                    }
                    list.clear();
                    list.addAll(data);
                    lvAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getActivity(), status.getError_desc(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void initUI() {
        lv_allorder = (ListView) view.findViewById(R.id.lv_allorder);
        lvAdapter = new LvAdapter();
        lv_allorder.setAdapter(lvAdapter);

        lv_allorder.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                String orderId = list.get(arg2).getOrderId();
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                intent.putExtra("order_id", orderId);
                startActivity(intent);
            }
        });

        pop_deleteaddress = inflater.inflate(R.layout.pop_deletaaddress, null);
        text_tip = (TextView) pop_deleteaddress.findViewById(R.id.text_tip);
        popupWindow = new PopupWindow(pop_deleteaddress, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        BitmapDrawable b = new BitmapDrawable();
        popupWindow.setBackgroundDrawable(b);
        popupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
            }
        });

        radio0 = (RadioButton) view.findViewById(R.id.radio0);
        radio1 = (RadioButton) view.findViewById(R.id.radio1);
        radio2 = (RadioButton) view.findViewById(R.id.radio2);
        radio3 = (RadioButton) view.findViewById(R.id.radio3);
        text_empty = (TextView) view.findViewById(R.id.text_empty);
        text_topbar = (TextView) view.findViewById(R.id.text_topbar);
        text_topbar.setText("订单中心");

        // 菜单
        popMenu = inflater.inflate(R.layout.include_menu, null);
        popupWindowMenu = new PopupWindow(popMenu, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        // 设置点击窗体以外区域关闭窗体
        BitmapDrawable b2 = new BitmapDrawable();
        popupWindowMenu.setBackgroundDrawable(b2);
        popupWindowMenu.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {

            }
        });
        bar = view.findViewById(R.id.bar);

        mRela_confirm = (LinearLayout) view.findViewById(R.id.rela_Confirm);
        mText_money2 = (TextView) view.findViewById(R.id.text_money2);
        mAllMoney = (TextView) view.findViewById(R.id.text_money);

    }

    private void initEvent() {
        pop_deleteaddress.findViewById(R.id.text_comfirm).setOnClickListener(this);
        pop_deleteaddress.findViewById(R.id.text_cancel).setOnClickListener(this);
        pop_deleteaddress.findViewById(R.id.rela_empty2).setOnClickListener(this);
        pop_deleteaddress.findViewById(R.id.rela_bottom).setOnClickListener(this);

        radio0.setOnClickListener(this);
        radio1.setOnClickListener(this);
        radio2.setOnClickListener(this);
        radio3.setOnClickListener(this);

        view.findViewById(R.id.rele_menu_detailact).setOnClickListener(this);
        view.findViewById(R.id.back).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.back).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_menu1).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_menu2).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_menu3).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_menu4).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_empty_meun).setOnClickListener(this);

        view.findViewById(R.id.radio02).setOnClickListener(this);
        view.findViewById(R.id.radio12).setOnClickListener(this);
        view.findViewById(R.id.radio22).setOnClickListener(this);
        view.findViewById(R.id.btn_confirm).setOnClickListener(this);
        view.findViewById(R.id.ima_close).setOnClickListener(this);
        view.findViewById(R.id.pay_empty).setOnClickListener(this);
    }

    class LvAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
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
            TextView text_ordernum;
            TextView text_success;
            TextView text_state;
            TextView text_title;
            TextView text_standard;
            TextView text_groupway;
            TextView text_off;
            TextView text_num;
            TextView text_price;
            TextView text_pay;
            TextView text_cancel;
            TextView text_confirm;
            TextView text_delete;
            SimpleDraweeView imageView1;

            if (convertView == null) {
                view = inflater.inflate(R.layout.item_lv_ordercenter, null);
                imageView1 = (SimpleDraweeView) view.findViewById(R.id.imageView1);
                text_ordernum = (TextView) view.findViewById(R.id.text_ordernum);
                text_success = (TextView) view.findViewById(R.id.text_success);
                text_state = (TextView) view.findViewById(R.id.text_state);
                text_title = (TextView) view.findViewById(R.id.text_title);
                text_standard = (TextView) view.findViewById(R.id.text_standard);
                text_groupway = (TextView) view.findViewById(R.id.text_groupway);
                text_off = (TextView) view.findViewById(R.id.text_off);
                text_num = (TextView) view.findViewById(R.id.text_num);
                text_price = (TextView) view.findViewById(R.id.text_price);
                text_pay = (TextView) view.findViewById(R.id.text_pay);
                text_cancel = (TextView) view.findViewById(R.id.text_cancel);
                text_confirm = (TextView) view.findViewById(R.id.text_confirm);
                text_delete = (TextView) view.findViewById(R.id.text_delete);
                view.setTag(new MyTag(text_ordernum, text_success, text_state, text_title, text_standard, text_groupway,
                        text_off, text_num, text_price, text_pay, text_cancel, text_confirm, text_delete, imageView1));
            } else {
                view = convertView;
                MyTag tag = (MyTag) view.getTag();
                text_ordernum = tag.text_ordernum;
                text_success = tag.text_success;
                text_state = tag.text_state;
                text_title = tag.text_title;
                text_standard = tag.text_standard;
                text_groupway = tag.text_groupway;
                text_off = tag.text_off;
                text_num = tag.text_num;
                text_pay = tag.text_pay;
                text_price = tag.text_price;
                text_cancel = tag.text_cancel;
                text_confirm = tag.text_confirm;
                text_delete = tag.text_delete;
                imageView1 = tag.imageView1;
            }
            switch (mode) {
                case 0: // 待付款
                    text_state.setText("待付款");
                    text_success.setVisibility(View.GONE);
                    text_pay.setVisibility(View.VISIBLE);
                    text_cancel.setVisibility(View.VISIBLE);
                    text_confirm.setVisibility(View.GONE);
                    text_delete.setVisibility(View.GONE);
                    break;
                case 1: // 待收货
                    text_state.setText("待收货");
                    text_success.setVisibility(View.GONE);
                    text_pay.setVisibility(View.GONE);
                    text_cancel.setVisibility(View.GONE);
                    text_confirm.setVisibility(View.VISIBLE);
                    text_delete.setVisibility(View.GONE);

                    break;
                case 2: // 已完成
                    text_state.setVisibility(View.GONE);
                    text_success.setVisibility(View.VISIBLE);
                    text_pay.setVisibility(View.GONE);
                    text_cancel.setVisibility(View.GONE);
                    text_confirm.setVisibility(View.GONE);
                    text_delete.setVisibility(View.VISIBLE);

                    break;
                case 3: // 已取消
                    text_state.setText("已取消");
                    text_success.setVisibility(View.GONE);
                    text_pay.setVisibility(View.GONE);
                    text_cancel.setVisibility(View.GONE);
                    text_confirm.setVisibility(View.GONE);
                    text_delete.setVisibility(View.GONE);

                    break;

                default:
                    break;
            }

            Datum3 datum3 = list.get(position);
//            x.image().bind(imageView1, datum3.getOriginalImg(), imageOptions);
            // UILUtils.displayImageNoAnim(datum3.getOriginalImg(), imageView1);
            YazhiUtils.setImage(datum3.getOriginalImg(), imageView1);
            text_title.setText(datum3.getGoodsName());
            text_price.setText("￥" + datum3.getGoodsAmount());
            String isOne = datum3.getBottle();
            if (isOne.equals("0")) { // 单件起批
                text_standard.setText("商品规格：单件起批");
                text_groupway.setText("组合规格：" + datum3.getOneNum() + "瓶1件*1组");
            } else {
                text_standard.setText("商品规格：单瓶起批");
                text_groupway.setText("组合规格：无");
            }
            String orimotionStr = "促销信息：";
            if (!datum3.getPromotion().isEmpty()) {
                orimotionStr += datum3.getPromotion();
            }
            if (!datum3.getFullMoney().isEmpty()) {
                orimotionStr += "满" + datum3.getFullMoney() + "减" + datum3.getSubMoney();
            }
            if (datum3.getPromotion().isEmpty() && datum3.getFullMoney().isEmpty()) {
                orimotionStr = "促销信息：无";
            }
            text_off.setText(orimotionStr);
            text_num.setText("x" + datum3.getGoodsNumber()); // 数量
            final String orderId = datum3.getOrderId();
            text_ordernum.setText("订单号：" + datum3.getOrderSn());

            // 取消订单
            text_cancel.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    editId = orderId;
                    confirmMode = 1;
                    text_tip.setText("确定取消该订单吗？");
                    popupWindow.showAtLocation(lv_allorder, Gravity.TOP, 0, 0);
                }
            });

            final String goodsAmount = datum3.getGoodsAmount(); // 总价
            final String orderSn = datum3.getOrderSn();
            final String goodsName = datum3.getGoodsName();
            // 付款
            text_pay.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (Double.parseDouble(goodsAmount) > 1000.0) { // 大于1000，付款10%
                        // new AliPay(getActivity(), listener).pay(goodsName,
                        // Double.parseDouble(goodsAmount) * 0.1 + "",
                        // orderSn);

                        mRela_confirm.setVisibility(View.VISIBLE);

                        double d = Double.parseDouble(goodsAmount) * 0.1;
                        BigDecimal b = new BigDecimal(d);
                        double df = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                        payGoodsName = goodsName;
                        payPrice = df;
                        payOrderSn = orderSn;
                        mText_money2.setText("￥" + payPrice);

                    } else {
                        Toast toast = Toast.makeText(getActivity(), "请线下付款", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }
                }
            });

            // 删除
            text_delete.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    editId = orderId;
                    confirmMode = 2;
                    text_tip.setText("确定删除该订单吗？");
                    popupWindow.showAtLocation(lv_allorder, Gravity.TOP, 0, 0);
                }

            });

            // 确认收货
            text_confirm.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    editId = orderId;
                    confirmMode = 3;
                    text_tip.setText("确定收货吗？");
                    popupWindow.showAtLocation(lv_allorder, Gravity.TOP, 0, 0);
                }
            });

            final String goodsId = datum3.getGoodsId();
            // 点击图片进入商品详情
            imageView1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), NewDetailActivity.class);
                    intent.putExtra("goods_id", goodsId);
                    startActivity(intent);
                }
            });
            return view;
        }

    }

    PayListener listener = new PayListener() {
        @Override
        public void payResult(String pays) {
            switch (pays) {
                case "9000":// 成功
                    // 支付成功返回待发货界面
                    Intent intent3 = new Intent(getActivity(), MainActivity.class);
                    intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent3.putExtra("tabhost", 5);
                    intent3.putExtra("item", 1); // 跳转待收货界面
                    startActivity(intent3);
                    Toast.makeText(getActivity(), "支付宝支付成功", Toast.LENGTH_SHORT).show();
                    // setResult(ResultCode.payusback, new Intent());
                    // finish();
                    break;
                case "8000":// 支付中
                    Toast.makeText(getActivity(), "支付结果确认中", Toast.LENGTH_SHORT).show();
                    break;
                default:// 支付失败
                    Toast.makeText(getActivity(), "支付宝支付失败", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    class MyTag {

        TextView text_ordernum;
        TextView text_success;
        TextView text_state;
        TextView text_title;
        TextView text_standard;
        TextView text_groupway;
        TextView text_off;
        TextView text_num;
        TextView text_price;
        TextView text_pay;
        TextView text_cancel;
        TextView text_confirm;
        TextView text_delete;
        SimpleDraweeView imageView1;

        public MyTag(TextView text_ordernum, TextView text_success, TextView text_state, TextView text_title,
                     TextView text_standard, TextView text_groupway, TextView text_off, TextView text_num,
                     TextView text_price, TextView text_pay, TextView text_cancel, TextView text_confirm,
                     TextView text_delete, SimpleDraweeView imageView1) {
            super();
            this.text_ordernum = text_ordernum;
            this.text_success = text_success;
            this.text_state = text_state;
            this.text_title = text_title;
            this.text_standard = text_standard;
            this.text_groupway = text_groupway;
            this.text_off = text_off;
            this.text_num = text_num;
            this.text_price = text_price;
            this.text_pay = text_pay;
            this.text_cancel = text_cancel;
            this.text_confirm = text_confirm;
            this.text_delete = text_delete;
            this.imageView1 = imageView1;
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                if (payMoneyMode == 0) { //支付宝支付
                    new AliPay(getActivity(), listener).pay(payGoodsName, payPrice + "", payOrderSn);
                } else if (payMoneyMode == 2) { //微信支付
                    getWXOrder(payGoodsName, payOrderSn, (int) (payPrice * 100) + ""); //乘以100.微信支付单位是分
                } else { //银联支付

                }
                break;
            case R.id.radio02:
                payMoneyMode = 0;
                break;
            case R.id.radio22:
                payMoneyMode = 1;
                break;
            case R.id.radio12:
                payMoneyMode = 2;
                break;
            case R.id.ima_close: //关闭付款弹窗
            case R.id.pay_empty:
                mRela_confirm.setVisibility(View.GONE);
                break;
            case R.id.radio0: // 待付款
                mode = 0;
                list.clear();
                initData("no_pay");
                lvAdapter.notifyDataSetChanged();
                break;
            case R.id.radio1: // 待收货
                mRela_confirm.setVisibility(View.GONE);
                mode = 1;
                list.clear();
                initData("shipping");
                lvAdapter.notifyDataSetChanged();
                break;
            case R.id.radio2: // 已完成
                mRela_confirm.setVisibility(View.GONE);
                mode = 2;
                list.clear();
                initData("complete");
                lvAdapter.notifyDataSetChanged();
                break;
            case R.id.radio3: // 已取消
                mRela_confirm.setVisibility(View.GONE);
                mode = 3;
                list.clear();
                initData("cancel");
                lvAdapter.notifyDataSetChanged();
                break;
            case R.id.text_comfirm: // 点击弹窗确认
                deleteOrder();
                break;
            case R.id.text_cancel: // 关闭弹窗
            case R.id.rela_empty2:
            case R.id.rela_bottom:
                popupWindow.dismiss();
                break;
            case R.id.rele_menu_detailact:
                popupWindowMenu.showAsDropDown(bar);
                break;
            case R.id.rela_empty_meun:
                popupWindowMenu.dismiss();
                break;
            case R.id.back:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("tabhost", 0);
                startActivity(intent);
                break;
            case R.id.rela_menu1:
                popupWindowMenu.dismiss();
                Intent intent2 = new Intent(getActivity(), MainActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent2.putExtra("tabhost", 0);
                startActivity(intent2);
                break;
            case R.id.rela_menu2:
                popupWindowMenu.dismiss();
                Intent intent21 = new Intent(getActivity(), MainActivity.class);
                intent21.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent21.putExtra("tabhost", 1);
                startActivity(intent21);
                break;
            case R.id.rela_menu3:
                popupWindowMenu.dismiss();
                break;
            case R.id.rela_menu4:
                popupWindowMenu.dismiss();
                Intent intent4 = new Intent(getActivity(), UserActivity.class);
                startActivity(intent4);
                break;
            default:
                break;
        }
    }

    private void deleteOrder() {
        String url = null;
        if (confirmMode == 1) { // 取消订单
            url = GlobalVariable.URL.DELETEORDER;
        } else if (confirmMode == 2) { // 删除订单
            url = GlobalVariable.URL.DELETEORDER2;
        } else if (confirmMode == 3) {
            url = GlobalVariable.URL.CONFIRMGET;
        }
        YazhiHttp yazhiHttp = new YazhiHttp(getActivity(), url);
        yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
        yazhiHttp.addParams("order_id", editId);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Deleteorder parseJSON = GsonUtils.parseJSON(arg0, Deleteorder.class);
                com.xmzlb.registermodel.Status status = parseJSON.getStatus();
                if (status.getSucceed() == 1) {
                    if (confirmMode == 1) { // 取消订单
                        Toast.makeText(getActivity(), "取消成功！", Toast.LENGTH_SHORT).show();
                    } else if (confirmMode == 2) { // 删除订单
                        Toast.makeText(getActivity(), "删除成功！", Toast.LENGTH_SHORT).show();
                    } else if (confirmMode == 3) { // 确认收货
                        Toast.makeText(getActivity(), "确认成功！", Toast.LENGTH_SHORT).show();
                    }
                    popupWindow.dismiss();
                    switch (mode) {
                        case 0:
                            loadMode = "no_pay";
                            break;
                        case 1:
                            loadMode = "shipping";
                            break;
                        case 2:
                            loadMode = "complete";
                            break;
                        case 3:
                            loadMode = "cancel";
                            break;
                        default:
                            break;
                    }
                    initData(loadMode);
                } else {
                    Toast.makeText(getActivity(), "操作失败！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {

            }
        });
    }

}
