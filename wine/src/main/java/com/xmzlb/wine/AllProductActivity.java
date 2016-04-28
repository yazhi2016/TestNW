package com.xmzlb.wine;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.xinbo.utils.GsonUtils;
import com.xinbo.widget.ListView4ScrollView;
import com.xmzlb.base.BaseActivity;
import com.xmzlb.model.Allbrand;
import com.xmzlb.model.Allproduct;
import com.xmzlb.model.Category;
import com.xmzlb.model.Child2;
import com.xmzlb.model.Datum15;
import com.xmzlb.model.Datum4;
import com.xmzlb.model.Datum5;
import com.xmzlb.model.Status;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.zyzutil.YazhiHttp;
import com.xmzlb.zyzutil.YazhiUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by zyz on 2016/4/7 0007.
 * QQ:344100167
 */

/**
 * 全部商品
 */
public class AllProductActivity extends BaseActivity {

    private ListView lv_left;
    private GridView gv_right;
    private LvLeftAdapter lvLeftAdapter;
    private GvAdapter gvAdapter;
    private PopupWindow popupWindow; // 筛选弹窗
    private RelativeLayout rela_brand;
    private LinearLayout linear_sort; // 筛选里的筛选
    private LinearLayout linear_price;
    private View pop_sort;
    private RelativeLayout rela_nothing;
    ArrayList<Child2> listOne = new ArrayList<Child2>(); // listview显示的个数
    ArrayList<Child2> listAll = new ArrayList<Child2>(); // listview显示的个数
    ArrayList<Child2> listShow = new ArrayList<Child2>(); // 父类
    ArrayList<Datum4> listChild = new ArrayList<Datum4>(); // 子类
    ArrayList<Datum5> productList = new ArrayList<Datum5>();
    ArrayList<Datum15> allBrandList = new ArrayList<>();
    ArrayList<Boolean> isShow = new ArrayList<>();
    View popMenu;
    Map<Integer, Datum15> choosedList = new HashMap<>();
    Map<Integer, String> priceChoosedList = new HashMap<>();

    int editNum;
    int childNu;
    String showMode = ""; // 从多到少
    String cateId = "";
    ImageOptions imageOptions;
    private TextView text_nothing;
    private TextView text_topbar;
    private TextView text_default;
    private TextView text_sale;
    private TextView text_price;
    private TextView text_people;
    private ImageView ima_num;
    private ImageView ima_priceup_header6_homefra;
    private ImageView ima_pricedown_header6_homefra;
    private PopupWindow popupWindowMenu; // 菜单弹窗
    private View bar;
    private ListView4ScrollView lv_brand;
    private LvBrandAdapter lvBrandAdapter;
    String brand = "";
    String money = "";
    private CheckBox radioButton21;
    private CheckBox radioButton01;
    private CheckBox radioButton22;
    private CheckBox radioButton23;
    private CheckBox radioButton24;
    private CheckBox radioButton25;
    private CheckBox radioButton26;
    private GlobalVariable instance;
    boolean check_clear = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);
        instance = GlobalVariable.getInstance();

        imageOptions = new ImageOptions.Builder().setSize(DensityUtil.dip2px(200), DensityUtil.dip2px(200))
                .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP).setLoadingDrawableId(R.drawable.ic_stub)
                .setFailureDrawableId(R.drawable.ic_error)
                // 设置使用缓存
                .setUseMemCache(true).build();

        initViews();
        init();
        initEvents();
        initAllBrand();
        // initData();
        lv_left.setOnItemClickListener(new OnItemClickListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (isShow.get(arg2)) { // 如果展开过，点击收起
                    isShow.set(arg2, false);
                    for (int i = 0; i < listShow.size(); i++) { // 遍历现在在显示的，把属于子类的都收起
                        String parentId = listShow.get(i).getParentId();
                        if (!parentId.equals("0")) { // 有printed即是子类
                            isShow.remove(i);
                            listShow.remove(i);
                            i--;
                        }
                    }
                    for (int i = 0; i < isShow.size(); i++) {
                        isShow.set(i, false);
                    }
                } else { // 没展开过，点击展开，把子类添加进来
                    List<Child2> child = listShow.get(arg2).getChild();
                    isShow.set(arg2, true);
//                    if (child.size() != 0 && listShow.get(arg2).getCatName().contains("按")) {
                    if (listShow.get(arg2).getCatName().contains("按")) {
                        for (int i = 0; i < child.size(); i++) {
                            listShow.add(arg2 + i + 1, child.get(i));
                            isShow.add(arg2 + i + 1, false);
                        }
                    } else { // 如果没有子类
                        isShow.set(arg2, false);
                    }
                }
                lvLeftAdapter.notifyDataSetChanged();
                if (!listShow.get(arg2).getParentId().equals("0")) { // 不为0说明可点击
                    cateId = listShow.get(arg2).getCatId();
                    showMode = "";
                    brand = "";
                    money = "";

                    text_default.setTextColor(getResources().getColor(R.color.orange));
                    text_sale.setTextColor(getResources().getColor(R.color.deepgray));
                    text_price.setTextColor(getResources().getColor(R.color.deepgray));
                    text_people.setTextColor(getResources().getColor(R.color.deepgray));
                    ima_num.setImageDrawable(getResources().getDrawable(R.drawable.price_down));
                    ima_priceup_header6_homefra.setImageDrawable(getResources().getDrawable(R.drawable.price_down));
                    ima_pricedown_header6_homefra.setImageDrawable(getResources().getDrawable(R.drawable.price_down));

                    initItemProduct();
                }
            }
        });
    }

    public void initAllBrand() {
        String url = GlobalVariable.URL.ALLBRAND;
        YazhiHttp yazhiHttp = new YazhiHttp(url);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Allbrand parseJSON = GsonUtils.parseJSON(arg0, Allbrand.class);
                Status status = parseJSON.getStatus();
                if (status.getSucceed() == 1) {
                    List<Datum15> data = parseJSON.getData();
                    allBrandList.clear();
                    allBrandList.addAll(data);
                    lvBrandAdapter.notifyDataSetChanged();
                } else {
                    showShortToast(status.getError_desc());
                    if (status.getSucceed() == 2) {
                        Intent intentLogIn = new Intent(AllProductActivity.this, UserLogInActivity.class);
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

    @Override
    protected void onResume() {
        super.onResume();

        Intent intent = getIntent();
        if (intent.getStringExtra("category") != null) {
//            cateId = intent.getStringExtra("category");
            brand = intent.getStringExtra("brand");
            initItemProduct();
        } else {
            initAllProduct();
        }
        if (listShow.size() == 0) {
            initDataFirst();
        }
    }

    private void initDataFirst() {
        String url = GlobalVariable.URL.CATEGORY;
        Map<String, String> params = new HashMap<String, String>();
        YazhiHttp yazhiHttp = new YazhiHttp(this, url);
        yazhiHttp.addParams("sid", instance.getSpSid("empty"));
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Category parseJSON = GsonUtils.parseJSON(arg0, Category.class);
                Status status = parseJSON.getStatus();
                if (status.getSucceed() == 1) {
                    List<Child2> data = parseJSON.getData();
                    List<Child2> child = data.get(0).getChild();
                    List<Child2> child2 = child.get(0).getChild();
                    listAll.clear();
                    listAll.addAll(data);
                    listOne.add(listAll.get(0));
                    listOne.add(listAll.get(2));
                    listOne.add(listAll.get(3));
                    isShow.add(false);
                    isShow.add(false);
                    isShow.add(false);
                    listShow.addAll(listOne);
                    lvLeftAdapter.notifyDataSetChanged();
                    gvAdapter.notifyDataSetChanged();
                } else {
                    showShortToast(status.getError_desc());
                    if (status.getSucceed() == 2) {
                        Intent intentLogIn = new Intent(AllProductActivity.this, UserLogInActivity.class);
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

    private void initAllProduct() {
        text_nothing.setVisibility(View.GONE);
        String url = GlobalVariable.URL.ALLPRODUCT;
        YazhiHttp yazhiHttp = new YazhiHttp(this, url);
        yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
        yazhiHttp.addParams("condition", showMode);
        yazhiHttp.addParams("category", cateId);
        yazhiHttp.addParams("brand", brand);
        yazhiHttp.addParams("money", money);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Allproduct parseJSON = GsonUtils.parseJSON(arg0, Allproduct.class);
                Status status = parseJSON.getStatus();
                if (status.getSucceed() == 1) {
                    List<Datum5> data = parseJSON.getData();
                    productList.clear();
                    productList.addAll(data);
                    gvAdapter.notifyDataSetChanged();
                    if (data.size() == 0) {
                        text_nothing.setVisibility(View.VISIBLE);
                    }
                } else {
                    showShortToast(status.getError_desc());
                    if (status.getSucceed() == 2) {
                        Intent intentLogIn = new Intent(AllProductActivity.this, UserLogInActivity.class);
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

    private void initItemProduct() {
        text_nothing.setVisibility(View.GONE);
        String url = GlobalVariable.URL.ALLPRODUCT;
        YazhiHttp yazhiHttp = new YazhiHttp(this, url);
        yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
        yazhiHttp.addParams("condition", showMode);
        yazhiHttp.addParams("category", cateId);
        yazhiHttp.addParams("brand", brand);
        yazhiHttp.addParams("money", money);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Allproduct parseJSON = GsonUtils.parseJSON(arg0, Allproduct.class);
                Status status = parseJSON.getStatus();
                if (status.getSucceed() == 1) {
                    List<Datum5> data = parseJSON.getData();
                    productList.clear();
                    productList.addAll(data);
                    gvAdapter.notifyDataSetChanged();
                    if (data.size() == 0) {
                        text_nothing.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (status.getSucceed() == 2) {
                        Intent intentLogIn = new Intent(AllProductActivity.this, UserLogInActivity.class);
                        intentLogIn.putExtra("from", 0);
                        intentLogIn.putExtra("fromStatus", 0);
                        startActivity(intentLogIn);
                    }
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
            case R.id.radioButton01: // 选中全部
                if (radioButton01.isChecked()) {
                    priceChoosedList.put(0, "-1");
                    radioButton21.setChecked(true);
                    radioButton22.setChecked(true);
                    radioButton23.setChecked(true);
                    radioButton24.setChecked(true);
                    radioButton25.setChecked(true);
                    radioButton26.setChecked(true);
                } else {
                    radioButton21.setChecked(false);
                    radioButton22.setChecked(false);
                    radioButton23.setChecked(false);
                    radioButton24.setChecked(false);
                    radioButton25.setChecked(false);
                    radioButton26.setChecked(false);
                    for (int i = 0; i < 7; i++) {
                        priceChoosedList.remove(i);
                    }
                }
                break;
            case R.id.radioButton21:
                if (radioButton21.isChecked()) {
                    priceChoosedList.put(1, "0-10");
                } else {
                    priceChoosedList.remove(1);
                }
                break;
            case R.id.radioButton22:
                if (radioButton22.isChecked()) {
                    priceChoosedList.put(2, "10-30");
                } else {
                    priceChoosedList.remove(2);
                }
                break;
            case R.id.radioButton23:
                if (radioButton23.isChecked()) {
                    priceChoosedList.put(3, "30-50");
                } else {
                    priceChoosedList.remove(3);
                }
                break;
            case R.id.radioButton24:
                if (radioButton24.isChecked()) {
                    priceChoosedList.put(4, "50-100");
                } else {
                    priceChoosedList.remove(4);
                }
                break;
            case R.id.radioButton25:
                if (radioButton25.isChecked()) {
                    priceChoosedList.put(5, "100-200");
                } else {
                    priceChoosedList.remove(5);
                }
                break;
            case R.id.radioButton26:
                if (radioButton26.isChecked()) {
                    priceChoosedList.put(6, "200");
                } else {
                    priceChoosedList.remove(6);
                }
                break;

            case R.id.rela_menu1:
            case R.id.tab_item1: // 点击底栏首页
                popupWindowMenu.dismiss();
                Intent intent_menu1 = new Intent(AllProductActivity.this, MainActivity.class);
                intent_menu1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_menu1.putExtra("tabhost", 0);
                startActivity(intent_menu1);
                break;
            case R.id.rela_menu2:
            case R.id.tab_item2: // 点击底栏购物车
                popupWindowMenu.dismiss();
                Intent intent_menu2 = new Intent(AllProductActivity.this, MainActivity.class);
                intent_menu2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_menu2.putExtra("tabhost", 1);
                startActivity(intent_menu2);
                break;
            case R.id.rela_menu3:
            case R.id.tab_item3: // 点击底栏订单中心
                popupWindowMenu.dismiss();
                Intent intent_menu3 = new Intent(AllProductActivity.this, MainActivity.class);
                intent_menu3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_menu3.putExtra("tabhost", 2);
                startActivity(intent_menu3);
                break;
            case R.id.rela_menu4:
                popupWindowMenu.dismiss();
                Intent intent_menu4 = new Intent(AllProductActivity.this, UserActivity.class);
                startActivity(intent_menu4);
                break;
            case R.id.tab_item4: // 点击底栏商场指南
                Intent intent_tab4 = new Intent(AllProductActivity.this, MainActivity.class);
                intent_tab4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_tab4.putExtra("tabhost", 3);
                startActivity(intent_tab4);
                break;
            case R.id.tab_item5: // 点击底栏我要供货
                Intent intent_tab5 = new Intent(AllProductActivity.this, MainActivity.class);
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

            case R.id.back:
                finish();
                break;
            case R.id.text_default: // 默认销量多到少
                showMode = "";
                text_default.setTextColor(getResources().getColor(R.color.orange));
                text_sale.setTextColor(getResources().getColor(R.color.deepgray));
                text_price.setTextColor(getResources().getColor(R.color.deepgray));
                text_people.setTextColor(getResources().getColor(R.color.deepgray));
                ima_num.setImageDrawable(getResources().getDrawable(R.drawable.price_down));
                ima_priceup_header6_homefra.setImageDrawable(getResources().getDrawable(R.drawable.price_down));
                ima_pricedown_header6_homefra.setImageDrawable(getResources().getDrawable(R.drawable.price_down));
                initAllProduct();
                break;
            case R.id.text_sale:
                text_default.setTextColor(getResources().getColor(R.color.deepgray));
                text_sale.setTextColor(getResources().getColor(R.color.orange));
                text_price.setTextColor(getResources().getColor(R.color.deepgray));
                text_people.setTextColor(getResources().getColor(R.color.deepgray));
                if (showMode.equals("sales_max")) { // 本来是销量多到少，点击变少到多
                    showMode = "sales_min";
                    ima_num.setImageDrawable(getResources().getDrawable(R.drawable.price_up));
                } else { // 本来是其他排序
                    showMode = "sales_max";
                    ima_num.setImageDrawable(getResources().getDrawable(R.drawable.price_down));
                }
                ima_priceup_header6_homefra.setImageDrawable(getResources().getDrawable(R.drawable.price_down));
                ima_pricedown_header6_homefra.setImageDrawable(getResources().getDrawable(R.drawable.price_down));
                initAllProduct();
                break;
            case R.id.text_price:
                text_default.setTextColor(getResources().getColor(R.color.deepgray));
                text_sale.setTextColor(getResources().getColor(R.color.deepgray));
                text_price.setTextColor(getResources().getColor(R.color.orange));
                text_people.setTextColor(getResources().getColor(R.color.deepgray));
                if (showMode.equals("money_max")) { // 本来是销量多到少，点击变少到多
                    showMode = "money_min";
                    ima_priceup_header6_homefra.setImageDrawable(getResources().getDrawable(R.drawable.price_up));
                } else { // 本来是其他排序
                    showMode = "money_max";
                    ima_priceup_header6_homefra.setImageDrawable(getResources().getDrawable(R.drawable.price_down));
                }
                ima_num.setImageDrawable(getResources().getDrawable(R.drawable.price_down));
                ima_pricedown_header6_homefra.setImageDrawable(getResources().getDrawable(R.drawable.price_down));
                initAllProduct();
                break;
            case R.id.text_people: // 人气
                text_default.setTextColor(getResources().getColor(R.color.deepgray));
                text_sale.setTextColor(getResources().getColor(R.color.deepgray));
                text_price.setTextColor(getResources().getColor(R.color.deepgray));
                text_people.setTextColor(getResources().getColor(R.color.orange));
                if (showMode.equals("like_max")) { // 本来是销量多到少，点击变少到多
                    showMode = "like_min";
                    ima_pricedown_header6_homefra.setImageDrawable(getResources().getDrawable(R.drawable.price_up));
                } else { // 本来是其他排序
                    showMode = "like_max";
                    ima_pricedown_header6_homefra.setImageDrawable(getResources().getDrawable(R.drawable.price_down));
                }
                ima_num.setImageDrawable(getResources().getDrawable(R.drawable.price_down));
                ima_priceup_header6_homefra.setImageDrawable(getResources().getDrawable(R.drawable.price_down));
                initAllProduct();
                break;
            case R.id.btn_clear: // 筛选中点击清空
                brand = "";
                money = "";
                check_clear = true;
                lvBrandAdapter.notifyDataSetChanged(); // 清空品牌
                radioButton01.setChecked(false);
                radioButton21.setChecked(false);
                radioButton22.setChecked(false);
                radioButton23.setChecked(false);
                radioButton24.setChecked(false);
                radioButton25.setChecked(false);
                radioButton26.setChecked(false);
                break;
            case R.id.rela_sort: // 筛选
                // 重新进时清空筛选条件
                brand = "";
                money = "";
                // 点击跳出popwindow
                popupWindow.showAtLocation(lv_left, Gravity.CENTER, 0, 0);
                break;
            case R.id.text_cancel: // 筛选弹窗的取消
                popupWindow.dismiss();
                break;
            case R.id.rela_brand: // 筛选中点击品牌
                if (lv_brand.getVisibility() == View.VISIBLE) {
                    lv_brand.setVisibility(View.GONE);
                } else {
                    lv_brand.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rela_price: // 筛选中点击单瓶进价
                if (linear_price.getVisibility() == View.VISIBLE) {
                    linear_price.setVisibility(View.GONE);
                } else {
                    linear_price.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.ima_closepop: // 筛选中点击关闭
                popupWindow.dismiss();
                break;
            case R.id.btn_comfirm: // 筛选中点击确定
            case R.id.text_comfirm: // 筛选中点击确定
                // rela_nothing.setVisibility(View.VISIBLE);
                // linear_sort.setVisibility(View.GONE);

                text_nothing.setVisibility(View.GONE);
                String url = GlobalVariable.URL.ALLPRODUCT;
                YazhiHttp yazhiHttp = new YazhiHttp(this, url);
                yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
                if (priceChoosedList.size() != 0) {
                    money = "";
                    int moneyNum = 0;
                    for (int i = 0; i < 7; i++) { // 固定7个
                        if (priceChoosedList.get(i) != null) {
                            if (i == 0) {
                                money = "-1"; // 选中全部
                                break;
                            }
                            String string = priceChoosedList.get(i);
                            money += string;
                            if ((moneyNum + 1) != priceChoosedList.size()) {
                                money += ",";
                                moneyNum++;
                            }
                        }
                    }
                }
                if (choosedList.size() != 0) {
                    brand = "";
                    int brandNum = 0;
                    for (int i = 0; i < allBrandList.size(); i++) {
                        if (choosedList.get(i) != null) {
                            Datum15 datum15 = choosedList.get(i);
                            String brandId = datum15.getBrandId();
                            brand += brandId;
                            if ((brandNum + 1) != choosedList.size()) {
                                brand += ",";
                                brandNum++;
                            }
                        }
                    }
                }
                yazhiHttp.addParams("condition", showMode);
                yazhiHttp.addParams("category", cateId);
                yazhiHttp.addParams("brand", brand);
                yazhiHttp.addParams("money", money);
                yazhiHttp.post(new YazhiHttp.MyListener() {
                    @Override
                    public void onSuccess(String arg0) {
                        Allproduct parseJSON = GsonUtils.parseJSON(arg0, Allproduct.class);
                        Status status = parseJSON.getStatus();
                        if (status.getSucceed() == 1) {
                            List<Datum5> data = parseJSON.getData();
                            productList.clear();
                            productList.addAll(data);
                            gvAdapter.notifyDataSetChanged();
                            if (data.size() == 0) {
                                text_nothing.setVisibility(View.VISIBLE);
                            }
                            popupWindow.dismiss();
                            // 弹窗消失时清空筛选
                            brand = "";
                            money = "";
                            check_clear = true;
                            lvBrandAdapter.notifyDataSetChanged(); // 清空品牌
                            radioButton01.setChecked(false);
                            radioButton21.setChecked(false);
                            radioButton22.setChecked(false);
                            radioButton23.setChecked(false);
                            radioButton24.setChecked(false);
                            radioButton25.setChecked(false);
                            radioButton26.setChecked(false);
                            choosedList.clear();
                            priceChoosedList.clear();
                        } else {
                            showShortToast(status.getError_desc());
                            if (status.getSucceed() == 2) {
                                Intent intentLogIn = new Intent(AllProductActivity.this, UserLogInActivity.class);
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
                break;
            default:
                break;
        }
    }

    @Override
    protected void initViews() {
        lv_left = (ListView) findViewById(R.id.lv_left);
        gv_right = (GridView) findViewById(R.id.gv_right);

        lvLeftAdapter = new LvLeftAdapter();
        lv_left.setAdapter(lvLeftAdapter);

        gvAdapter = new GvAdapter();
        gv_right.setAdapter(gvAdapter);

        gv_right.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(AllProductActivity.this, NewDetailActivity.class);
                intent.putExtra("goods_id", productList.get(arg2).getGoodsId());
                startActivity(intent);
            }
        });

        pop_sort = getLayoutInflater().inflate(R.layout.pop_sort, null);
        radioButton01 = (CheckBox) pop_sort.findViewById(R.id.radioButton01);
        radioButton21 = (CheckBox) pop_sort.findViewById(R.id.radioButton21);
        radioButton22 = (CheckBox) pop_sort.findViewById(R.id.radioButton22);
        radioButton23 = (CheckBox) pop_sort.findViewById(R.id.radioButton23);
        radioButton24 = (CheckBox) pop_sort.findViewById(R.id.radioButton24);
        radioButton25 = (CheckBox) pop_sort.findViewById(R.id.radioButton25);
        radioButton26 = (CheckBox) pop_sort.findViewById(R.id.radioButton26);
        radioButton01.setOnClickListener(this);
        radioButton21.setOnClickListener(this);
        radioButton22.setOnClickListener(this);
        radioButton23.setOnClickListener(this);
        radioButton24.setOnClickListener(this);
        radioButton25.setOnClickListener(this);
        radioButton26.setOnClickListener(this);

        rela_brand = (RelativeLayout) pop_sort.findViewById(R.id.rela_brand);
        linear_price = (LinearLayout) pop_sort.findViewById(R.id.linear_price);
        popupWindow = new PopupWindow(pop_sort, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);

        // 设置点击窗体以外区域关闭窗体
        BitmapDrawable b = new BitmapDrawable();
        popupWindow.setBackgroundDrawable(b);
        popupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
            }
        });

        linear_sort = (LinearLayout) pop_sort.findViewById(R.id.linear_sort);
        // 筛选--品牌栏目
        lv_brand = (ListView4ScrollView) linear_sort.findViewById(R.id.lv1);
        lvBrandAdapter = new LvBrandAdapter();
        lv_brand.setAdapter(lvBrandAdapter);

        text_nothing = (TextView) findViewById(R.id.textView1);
        text_topbar = (TextView) findViewById(R.id.text_topbar);

        text_default = (TextView) findViewById(R.id.text_default);
        text_sale = (TextView) findViewById(R.id.text_sale);
        text_price = (TextView) findViewById(R.id.text_price);
        text_people = (TextView) findViewById(R.id.text_people);
        ima_num = (ImageView) findViewById(R.id.ima_num);
        ima_priceup_header6_homefra = (ImageView) findViewById(R.id.ima_priceup_header6_homefra);
        ima_pricedown_header6_homefra = (ImageView) findViewById(R.id.ima_pricedown_header6_homefra);

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

    class LvBrandAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return allBrandList.size();
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
            TextView text;
            final CheckBox radioButton1;
            if (convertView == null) {
                view = getLayoutInflater().inflate(R.layout.item_lvbrand, null);
                text = (TextView) view.findViewById(R.id.text_brand);
                radioButton1 = (CheckBox) view.findViewById(R.id.radioButton1);
                view.setTag(new LvBrandTag(text, radioButton1));
            } else {
                view = convertView;
                LvBrandTag tag2 = (LvBrandTag) view.getTag();
                text = tag2.text;
                radioButton1 = tag2.radioButton1;
            }

            if (check_clear) { // 为真时进入
                radioButton1.setChecked(false);
            }

            final Datum15 datum15 = allBrandList.get(position);
            text.setText(datum15.getBrandName());
            radioButton1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    boolean checked = radioButton1.isChecked();
                    if (checked) {
                        choosedList.put(position, datum15); // 如果选中，就把该品牌加入数组中
                    } else {
                        choosedList.remove(position);
                    }
                }
            });

            return view;
        }
    }

    class LvBrandTag {
        TextView text;
        CheckBox radioButton1;

        public LvBrandTag(TextView text, CheckBox radioButton1) {
            super();
            this.text = text;
            this.radioButton1 = radioButton1;
        }
    }

    @Override
    protected void initEvents() {
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

        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.rela_sort).setOnClickListener(this);
        pop_sort.findViewById(R.id.rela_price).setOnClickListener(this);
        pop_sort.findViewById(R.id.text_comfirm).setOnClickListener(this);
        pop_sort.findViewById(R.id.rela_brand).setOnClickListener(this);
        pop_sort.findViewById(R.id.ima_closepop).setOnClickListener(this);
        pop_sort.findViewById(R.id.text_cancel).setOnClickListener(this);
        pop_sort.findViewById(R.id.btn_comfirm).setOnClickListener(this);
        pop_sort.findViewById(R.id.btn_clear).setOnClickListener(this);
        text_people.setOnClickListener(this);
        text_price.setOnClickListener(this);
        text_default.setOnClickListener(this);
        text_sale.setOnClickListener(this);
    }

    @Override
    protected void init() {
        text_topbar.setText("全部商品");
    }

    class LvLeftAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listShow.size();
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
            TextView text_title;
            ImageView imageView1;
            if (convertView == null) {
                view = getLayoutInflater().inflate(R.layout.item_lvleft_allproduct, null);
                imageView1 = (ImageView) view.findViewById(R.id.imageView1);
                text_title = (TextView) view.findViewById(R.id.text_title);
                view.setTag(new MyTag(text_title, imageView1));
            } else {
                view = convertView;
                MyTag tag2 = (MyTag) view.getTag();
                text_title = tag2.text_title;
                imageView1 = tag2.imageView1;
            }

            Child2 datum4 = listShow.get(position);
            text_title.setText(datum4.getCatName());

            //设置样式，父类有箭头
            if (datum4.getChild().size() == 0 && !datum4.getCatName().contains("按")) {
                imageView1.setVisibility(View.GONE);
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.white));
                text_title.setTextColor(Color.BLACK);
            } else {
                imageView1.setVisibility(View.VISIBLE);
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.deepgray2));
                text_title.setTextColor(Color.WHITE);
            }
            if (position < isShow.size()) {
                if (isShow.get(position)) { // 如果为真，就是展开状态
                    imageView1.setVisibility(View.VISIBLE);
                    imageView1.setBackgroundDrawable(getResources().getDrawable(R.drawable.up));
                    view.setBackgroundDrawable(getResources().getDrawable(R.drawable.white));
                    text_title.setTextColor(getResources().getColor(R.color.deepgray2));
                } else {
                    imageView1.setBackgroundDrawable(getResources().getDrawable(R.drawable.down));
                }
            }

            return view;
        }

    }

    class MyTag {
        TextView text_title;
        ImageView imageView1;

        public MyTag(TextView text_title, ImageView imageView1) {
            super();
            this.text_title = text_title;
            this.imageView1 = imageView1;
        }

    }

    class GvAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return productList.size();
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
            TextView text_title;
            SimpleDraweeView imageView1;
            TextView text_price;
            if (convertView == null) {
                view = getLayoutInflater().inflate(R.layout.item_gv_allproduct, null);
                imageView1 = (SimpleDraweeView) view.findViewById(R.id.imageView1);
                text_title = (TextView) view.findViewById(R.id.text_title);
                text_price = (TextView) view.findViewById(R.id.text_price);
                view.setTag(new GvTag(text_title, imageView1, text_price));
            } else {
                view = convertView;
                GvTag tag2 = (GvTag) view.getTag();
                text_title = tag2.text_title;
                imageView1 = tag2.imageView1;
                text_price = tag2.text_price;
            }

            Datum5 datum5 = productList.get(position);
            text_title.setText(datum5.getGoodsName());
            text_price.setText("￥" + datum5.getShopPrice());
            YazhiUtils.setImage(datum5.getOriginalImg(), imageView1);
            return view;
        }

    }

    class GvTag {
        TextView text_title;
        SimpleDraweeView imageView1;
        TextView text_price;

        public GvTag(TextView text_title, SimpleDraweeView imageView1, TextView text_price) {
            super();
            this.text_title = text_title;
            this.imageView1 = imageView1;
            this.text_price = text_price;
        }

    }
}
