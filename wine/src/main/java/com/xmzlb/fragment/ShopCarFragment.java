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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xinbo.utils.GsonUtils;
import com.xmzlb.model.Datum2;
import com.xmzlb.model.Shopcar;
import com.xmzlb.model.Status;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.wine.ComfirmOrderActivity;
import com.xmzlb.wine.MainActivity;
import com.xmzlb.wine.NewDetailActivity;
import com.xmzlb.wine.R;
import com.xmzlb.wine.UserActivity;
import com.xmzlb.wine.UserLogInActivity;
import com.xmzlb.zyzutil.YazhiHttp;
import com.xmzlb.zyzutil.YazhiUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShopCarFragment extends Fragment implements OnClickListener {

    ArrayList<Datum2> list = new ArrayList<Datum2>();
    private View view;
    LayoutInflater inflater;
    private ListView lv_shop;
    private LvAdapter lvAdapter;
    private RelativeLayout rela_nothing;
    private PopupWindow popupWindow;
    View pop_deleteaddress;
    String editShopCarId;
    private CheckBox allCheck;
    boolean isAllCheck;
    Map<Integer, goodNum> goodNumMap = new HashMap<Integer, ShopCarFragment.goodNum>(); // 存储每行的数量和购物ID
    Map<Integer, Boolean> checkMap = new HashMap<Integer, Boolean>(); // 存储checkbox的选中状态的数组
    ArrayList<Integer> choosedPosition = new ArrayList<Integer>();
    double allMoney;
    private TextView text_num;
    private TextView text_money;
    ImageOptions imageOptions;
    private View bar;
    private View popMenu;
    private PopupWindow popupWindowMenu; // 菜单弹窗
    private TextView text_topbar;
    private int goodNumMapSize;

    public ShopCarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            this.inflater = inflater;
            view = inflater.inflate(R.layout.fragment_shop_car, container, false);
            imageOptions = new ImageOptions.Builder().setSize(DensityUtil.dip2px(200), DensityUtil.dip2px(200))
                    // .setRadius(DensityUtil.dip2px(5))
                    // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                    .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                    // 加载中或错误图片的ScaleType
                    // .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP).setLoadingDrawableId(R.drawable.ic_stub)
                    .setFailureDrawableId(R.drawable.ic_error)
                    // 设置使用缓存
                    .setUseMemCache(true).build();

            initUI();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        isAllCheck = false;
        allCheck.setChecked(false);
        list.clear();
        goodNumMap.clear();
        checkMap.clear();
        choosedPosition.clear();
        lvAdapter.notifyDataSetChanged();
        allMoney = 0;
        text_num.setText("0");
        text_money.setText("0");
        calculateMoney();

        initData();
    }

    private void initData() {
        String url = GlobalVariable.URL.SHOPCAR;
        YazhiHttp yazhiHttp = new YazhiHttp(getActivity(), url);
        yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Shopcar parseJSON = GsonUtils.parseJSON(arg0, Shopcar.class);
                Status status = parseJSON.getStatus();
                if (status.getSucceed() == 1) {
                    List<Datum2> data = parseJSON.getData();
                    list.clear();
                    goodNumMap.clear();
                    for (int i = 0; i < data.size(); i++) {
                        // 把物品的购物车ID和数量写进数组
                        Datum2 datum2 = data.get(i);
                        goodNumMap.put(i, new goodNum(datum2.getRecId(), Integer.parseInt(datum2.getGoodsNumber())));

                    }
                    list.addAll(data);
                    checkMap.clear();
                    lvAdapter.notifyDataSetChanged();
                    if (list.size() == 0) { // 购物车为空
                        lv_shop.setVisibility(View.INVISIBLE);
                        rela_nothing.setVisibility(View.VISIBLE);
                        return;
                    } else {
                        lv_shop.setVisibility(View.VISIBLE);
                        rela_nothing.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(getActivity(), status.getError_desc(), Toast.LENGTH_SHORT).show();
                    if (status.getSucceed() == 2) { // 账号过期
                        Intent intentLogIn = new Intent(getActivity(), UserLogInActivity.class);
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

    private void initUI() {
        lv_shop = (ListView) view.findViewById(R.id.lv_shop);
        lvAdapter = new LvAdapter();
        lv_shop.setAdapter(lvAdapter);

        rela_nothing = (RelativeLayout) view.findViewById(R.id.rela_nothing);
        view.findViewById(R.id.text_pay).setOnClickListener(this);

        pop_deleteaddress = inflater.inflate(R.layout.pop_deletaaddress, null);
        TextView text_tip = (TextView) pop_deleteaddress.findViewById(R.id.text_tip);
        text_tip.setText("确定删除该商品吗？");
        pop_deleteaddress.findViewById(R.id.text_comfirm).setOnClickListener(this);
        pop_deleteaddress.findViewById(R.id.text_cancel).setOnClickListener(this);
        pop_deleteaddress.findViewById(R.id.rela_empty2).setOnClickListener(this);
        pop_deleteaddress.findViewById(R.id.rela_bottom).setOnClickListener(this);
        popupWindow = new PopupWindow(pop_deleteaddress, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        BitmapDrawable b = new BitmapDrawable();
        popupWindow.setBackgroundDrawable(b);
        popupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
            }
        });

        allCheck = (CheckBox) view.findViewById(R.id.checkBox1);
        allCheck.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isAllCheck) { // 本来是全选，点击变全不选
                    isAllCheck = false;
                    text_num.setText("0");
                    text_money.setText("￥0");
                } else {
                    isAllCheck = true;
                    text_num.setText(list.size() + "");
                    allMoney = 0;
                    for (int i = 0; i < list.size(); i++) {
                        goodNum goodNum = goodNumMap.get(i);
                        allMoney += goodNum.num * Double.parseDouble(list.get(i).getShopPrice());
                        text_money.setText(allMoney + "");
                    }
                }
                lvAdapter.notifyDataSetChanged();
            }
        });

        text_money = (TextView) view.findViewById(R.id.text_money);
        text_num = (TextView) view.findViewById(R.id.text_num);

        calculateMoney();

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

        view.findViewById(R.id.rele_menu_detailact).setOnClickListener(this);
        view.findViewById(R.id.back).setOnClickListener(this);
        view.findViewById(R.id.back).setVisibility(View.INVISIBLE);
        popMenu.findViewById(R.id.rela_menu1).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_menu2).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_menu3).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_menu4).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_empty_meun).setOnClickListener(this);

        lv_shop.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

            }
        });

        text_topbar = (TextView) view.findViewById(R.id.text_topbar);
        text_topbar.setText("购物车");
    }

    private void calculateMoney() {
        allMoney = 0;
        for (int i = 0; i < list.size(); i++) {
            Boolean boolean1 = checkMap.get(i);
            if (boolean1) { // 如果是选中，则计算一次价钱
                goodNum goodNum = goodNumMap.get(i);
                allMoney += goodNum.num * Double.parseDouble(list.get(i).getShopPrice());
            }
        }
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            View view;
            final TextView editText1;
            final CheckBox checkBox1;
            SimpleDraweeView imageView1;
            ImageView ima_substract_detailact;
            ImageView ima_addt_detailact;
            TextView text_title;
            TextView text_price;
            TextView delete;
            TextView textView2;
            TextView textView1;
            if (convertView == null) {
                view = inflater.inflate(R.layout.item_lv_shopcar, null);
                checkBox1 = (CheckBox) view.findViewById(R.id.checkBox1);
                imageView1 = (SimpleDraweeView) view.findViewById(R.id.imageView1);
                ima_substract_detailact = (ImageView) view.findViewById(R.id.ima_substract_detailact);
                ima_addt_detailact = (ImageView) view.findViewById(R.id.ima_addt_detailact);
                text_title = (TextView) view.findViewById(R.id.text_title);
                text_price = (TextView) view.findViewById(R.id.text_price);
                delete = (TextView) view.findViewById(R.id.delete);
                textView2 = (TextView) view.findViewById(R.id.textView2);
                textView1 = (TextView) view.findViewById(R.id.textView1);
                editText1 = (TextView) view.findViewById(R.id.editText1);
                view.setTag(new MyTag(editText1, checkBox1, imageView1, ima_substract_detailact, ima_addt_detailact,
                        text_title, text_price, delete, textView2, textView1));
            } else {
                view = convertView;
                MyTag tag = (MyTag) view.getTag();
                editText1 = tag.editText1;
                checkBox1 = tag.checkBox1;
                imageView1 = tag.imageView1;
                ima_substract_detailact = tag.ima_substract_detailact;
                ima_addt_detailact = tag.ima_addt_detailact;
                text_title = tag.text_title;
                text_price = tag.text_price;
                delete = tag.delete;
                textView2 = tag.textView2;
                textView1 = tag.textView1;
            }

            Datum2 datum2 = list.get(position);
            final String goodsId = datum2.getGoodsId();
            imageView1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), NewDetailActivity.class);
                    intent.putExtra("goods_id", goodsId);
                    startActivity(intent);
                }
            });

//            x.image().bind(imageView1, datum2.getOriginalImg(), imageOptions);
            YazhiUtils.setImage(datum2.getOriginalImg(), imageView1);
            // 注释
            text_title.setText(datum2.getGoodsName());
            text_price.setText("￥" + datum2.getShopPrice());

            if (goodNumMap.size() != 0) {
                editText1.setText(goodNumMap.get(position).num + "");
            }

            // editText1.setText(datum2.getGoodsNumber());
            if (datum2.getFullMoney().equals("") || datum2.getFullMoney().equals("0")) {
                textView2.setText("促销信息：无");
            } else {
                textView2.setText("促销信息：满" + datum2.getFullMoney() + "减" + datum2.getSubMoney());
            }
            if (datum2.getIsOne().equals("1")) {
                textView1.setText("单瓶起批");
            } else {
                textView1.setText("整组起批，" + datum2.getOneNum() + "瓶/组");
            }
            final String recId = datum2.getRecId();
            // 删除
            delete.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    // 删除之前把每个改变的数量都上传
                    for (int i = 0; i < list.size(); i++) {
                        changeNum(i);
                    }

                    editShopCarId = recId;
                    popupWindow.showAtLocation(lv_shop, Gravity.TOP, 0, 0);
                }
            });

            checkBox1.setChecked(isAllCheck); // 如果全选勾中，则打勾
            checkMap.put(position, checkBox1.isChecked());
            final String recId2 = datum2.getRecId();
            final String shopPrice = datum2.getShopPrice();

            checkBox1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    boolean isChecked = checkBox1.isChecked();
                    checkMap.put(position, isChecked);// 勾选改变状态，就刷新一下checkmap中的状态
                    if (!isChecked) { // 如果有一个取消选中，全选按钮就不是选中状态
                        isAllCheck = false;
                        allCheck.setChecked(false);
                        String string = text_num.getText().toString();
                        text_num.setText(Integer.parseInt(string) - 1 + "");
                        allMoney -= goodNumMap.get(position).num * Double.parseDouble(shopPrice);
                        text_money.setText(allMoney + "");
                    } else {
                        String string = text_num.getText().toString();
                        text_num.setText(Integer.parseInt(string) + 1 + "");
                        allMoney += goodNumMap.get(position).num * Double.parseDouble(shopPrice);
                        text_money.setText(allMoney + "");

                        for (int i = 0; i < checkMap.size(); i++) {
                            if (!checkMap.get(i)) {
                                isAllCheck = false;
                                return;
                            }
                        }

                        isAllCheck = true; // 如果全部都是选中，则全选打勾
                        allCheck.setChecked(true);
                    }

                }
            });

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
                        goodNumMap.put(position, new goodNum(recId2, parseInt));
                        if (checkMap.get(position)) { // 是选中的
                            allMoney -= Double.parseDouble(shopPrice);
                            text_money.setText(allMoney + "");
                        }
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
                    if (checkMap.get(position)) { // 是选中的
                        allMoney += Double.parseDouble(shopPrice);
                        text_money.setText(allMoney + "");
                    }
                    goodNumMap.put(position, new goodNum(recId2, parseInt));
                }
            });

            return view;
        }

    }

    class MyTag {
        TextView editText1;
        CheckBox checkBox1;
        SimpleDraweeView imageView1;
        ImageView ima_substract_detailact;
        ImageView ima_addt_detailact;
        TextView text_title;
        TextView text_price;
        TextView delete;
        TextView textView2;
        TextView textView1;

        public MyTag(TextView editText1, CheckBox checkBox1, SimpleDraweeView imageView1, ImageView ima_substract_detailact,
                     ImageView ima_addt_detailact, TextView text_title, TextView text_price, TextView delete,
                     TextView textView2, TextView textView1) {
            super();
            this.editText1 = editText1;
            this.checkBox1 = checkBox1;
            this.imageView1 = imageView1;
            this.ima_substract_detailact = ima_substract_detailact;
            this.ima_addt_detailact = ima_addt_detailact;
            this.text_title = text_title;
            this.text_price = text_price;
            this.delete = delete;
            this.textView2 = textView2;
            this.textView1 = textView1;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rele_menu_detailact:
                popupWindowMenu.showAsDropDown(bar);
                break;
            case R.id.rela_empty_meun:
                popupWindowMenu.dismiss();
                break;
            case R.id.back:
                Intent intent0 = new Intent(getActivity(), MainActivity.class);
                intent0.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent0.putExtra("tabhost", 0);
                startActivity(intent0);
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
                break;
            case R.id.rela_menu3:
                popupWindowMenu.dismiss();
                Intent intent21 = new Intent(getActivity(), MainActivity.class);
                intent21.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent21.putExtra("tabhost", 2);
                startActivity(intent21);
                break;
            case R.id.rela_menu4:
                popupWindowMenu.dismiss();
                Intent intent4 = new Intent(getActivity(), UserActivity.class);
                startActivity(intent4);
                break;
            case R.id.text_pay: // 结算
                choosedPosition.clear();
                for (int i = 0; i < list.size(); i++) {
                    Boolean boolean1 = checkMap.get(i);
                    if (boolean1) { // 如果是选中状态
                        choosedPosition.add(i);
                    }
                }
                if (choosedPosition.size() == 0) {
                    Toast.makeText(getActivity(), "请选择需要结算的商品！", Toast.LENGTH_SHORT).show();
                    return;
                }

                goodNumMapSize = goodNumMap.size();
                // 先存储数量
                for (int i = 0; i < goodNumMap.size(); i++) {
                    changeShopNum(i);
                }

                break;
            case R.id.text_comfirm:
                delete();
                break;
            case R.id.text_cancel:
            case R.id.rela_bottom:
            case R.id.rela_empty:
                popupWindow.dismiss();
                break;
            default:
                break;
        }
    }

    // 修改购物车数量
    private void changeShopNum(final int i) {
        String url = GlobalVariable.URL.CHANGENUM;
        YazhiHttp yazhiHttp = new YazhiHttp(getActivity(), url);
        yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
        goodNum goodNum = goodNumMap.get(i);
        yazhiHttp.addParams("rec_id", goodNum.goodId);
        yazhiHttp.addParams("new_number", goodNum.num + "");
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                if (i == (goodNumMapSize - 1)) { // 数量全部上传后
                    Intent intent = new Intent(getActivity(), ComfirmOrderActivity.class);
                    intent.putIntegerArrayListExtra("choosedPosition", choosedPosition);
                    intent.putExtra("loadorder", "1");
                    startActivity(intent);
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    // 删除购物车
    private void delete() {
        String url = GlobalVariable.URL.DELETESHOPCAR;
        YazhiHttp yazhiHttp = new YazhiHttp(getActivity(), url);
        yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
        yazhiHttp.addParams("rec_id", editShopCarId);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                // 删除成功，重新刷新购物车
                initData();
                popupWindow.dismiss();
            }

            @Override
            public void onError() {

            }
        });
    }

    class goodNum {
        String goodId;
        int num;

        public goodNum(String goodId, int num) {
            super();
            this.goodId = goodId;
            this.num = num;
        }

    }

    // 退出时存储数量
    @Override
    public void onPause() {
        super.onPause();
        // 把每个改变的数量都上传
        for (int i = 0; i < list.size(); i++) {
            changeNum(i);
        }

//        String url = GlobalVariable.URL.CHANGENUM2;
//        RequestParams requestParams = new RequestParams(url);
//        Map<String, String> data = new HashMap<>();
//        JSONObject jsonObject = new JSONObject();
//
//        for (int i = 0; i < list.size(); i++) {
//            Datum2 datum2 = list.get(i);
////            data.put();
//            try {
//                jsonObject.put(datum2.getGoodsId(), 2);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//        HTTPUtils.get(getActivity(), url + "?sid=" + GlobalVariable.getInstance().getSpSid("empty") + "&data=" + "data[\"268\":\"5\"]", new VolleyListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//
//            }
//
//            @Override
//            public void onResponse(String s) {
//
//            }
//        });
//        requestParams.addBodyParameter("data", "{" + "\"data\":{\"268\":\"5\"}");
//        requestParams.addBodyParameter("sid", GlobalVariable.getInstance().getSpSid("empty"));
//        requestParams.addBodyParameter("data", "data[\"268\":\"5\"]");
//        x.http().get(requestParams, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String strng) {
//
//            }
//
//            @Override
//            public void onError(Throwable throwable, boolean b) {
//
//            }
//
//            @Override
//            public void onCancelled(CancelledException e) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
    }

    private void changeNum(int position) {
        String url = GlobalVariable.URL.CHANGENUM;
        YazhiHttp yazhiHttp = new YazhiHttp(getActivity(), url);
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

}
