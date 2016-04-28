package com.xmzlb.wine;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.xinbo.utils.GsonUtils;
import com.xmzlb.base.BaseActivity;
import com.xmzlb.model.Like;
import com.xmzlb.registermodel.Data4;
import com.xmzlb.registermodel.Detail;
import com.xmzlb.registermodel.Status;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.zyzutil.YazhiHttp;

import java.util.ArrayList;
import java.util.List;

public class NewDetailActivity extends BaseActivity {

    private String goods_id;
    ArrayList<ImageView> imaList = new ArrayList<ImageView>();
    boolean isDrag;
    private TextView text_name_detailact;
    private TextView text_pricesingle_detailact;
    private TextView text_pricegroup_detailact;
    private TextView text_isgroupway_detailact;
    private TextView text_pricevip_detailact;
    private TextView text_score_detailact;
    private TextView text_groupnum_detailact;
    private TextView text_singleleft_detailact;
    private TextView text_promotion_detailact;
    private TextView text_love;
    private TextView textView2;
    private TextView textView18;
    private TextView textView19;
    private TextView textView20;
    private TextView textView26;
    private TextView textView27;
    private TextView textView28;
    private TextView textView29;
    private TextView textView33;
    private TextView textView34;
    private TextView textView35;
    private WebView webView1;
    private EditText editText1;
    private TextView editText12;
    private TextView text_groupway_detailact;

    int buyNum = 1;
    private RadioButton radio0;
    private RadioButton radio1;
    private ImageView imageView3;
    private ImageView ima_like_detailact;

    boolean isLike; // 点赞状态
    boolean isCollect; // 收藏状态
    int collectNum;
    private int ima_size = 1;
    View popMenu;
    private PopupWindow popupWindowMenu; // 菜单弹窗
    private View bar;
    ArrayList<String> adList = new ArrayList<>();
    private ScrollView scrollView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_detail2);
        goods_id = getIntent().getStringExtra("goods_id");
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
        String url = GlobalVariable.URL.DETAIL;
        YazhiHttp yazhiHttp = new YazhiHttp(this, url);
        try {
            yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
            yazhiHttp.addParams("goods_id", goods_id);
        } catch (Exception e) {
            if (e instanceof NullPointerException) {
                return;
            }
        }
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                if (arg0.isEmpty()) {
                    showShortToast("服务器返回错误");
                    return;
                }
                Detail parseJSON = GsonUtils.parseJSON(arg0, Detail.class);
                Status status = parseJSON.getStatus();
                adList.clear();
                if (status.getSucceed() == 1) {
                    Data4 data = parseJSON.getData();
                    List<String> goodsImg = data.getGoodsImg();
                    ima_size = goodsImg.size();
                    adList.addAll(goodsImg);

                    getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new PagerFragment(adList))
                            .commit();

                    text_name_detailact.setText(data.getGoodsName());
                    text_pricesingle_detailact.setText(data.getShopPrice() + "元");
                    text_pricegroup_detailact.setText(data.getOnePrice() + "元");
                    text_groupway_detailact.setText(data.getMorePrice() + "元");
                    if (data.getIsOne().equals("1")) {
                        text_isgroupway_detailact.setText("是");
                        radio1.setEnabled(true);
                    } else {
                        text_isgroupway_detailact.setText("否");
                        radio1.setEnabled(false);
                    }
                    text_pricevip_detailact.setText(data.getMoreNum() + "组"); // 组合形式
                    text_score_detailact.setText(data.getOneNum() + "瓶"); // 单组瓶数
                    text_groupnum_detailact.setText(data.getIntegration() + "分"); // 捐赠积分
                    text_singleleft_detailact.setText(data.getMoreInventory()); // 库存
                    if (data.getFullMoney().isEmpty()) {
                        text_promotion_detailact.setText(data.getPromotion()); // 如果满0，则不显示
                    } else {
                        text_promotion_detailact.setText(
                                data.getPromotion() + "(满" + data.getFullMoney() + "送" + data.getSubMoney() + ")"); // 促销信息
                    }
                    collectNum = Integer.parseInt(data.getClickCount());
                    text_love.setText("点击收藏（" + data.getClickCount() + "人气）"); // 人气
                    textView2.setText(data.getBrand()); // 品牌
                    textView18.setText(data.getDegree()); // 度数
                    textView19.setText(data.getOrigin()); // 产地
                    textView20.setText(data.getShelfLife()); // 保质期
                    textView26.setText(data.getCategory()); // 品类
                    textView27.setText(data.getCapacity()); // 净含量
                    textView28.setText(data.getManufacturer()); // 厂家
                    textView29.setText(data.getPackaging()); // 包装规格
                    textView33.setText(data.getCategoryName()); // 品名
                    textView34.setText(data.getFragrance()); // 香味
                    textView35.setText(data.getOther());
                    String goodsDesc = data.getGoodsDesc();
                    webView1.loadDataWithBaseURL(null, goodsDesc, null, "utf-8", null);

                    String like = data.getLike();
                    if (like.equals("1")) { // 已点过赞
                        isLike = true;
                        ima_like_detailact.setImageDrawable(getResources().getDrawable(R.drawable.liked));
                    } else {
                        isLike = false;
                        ima_like_detailact.setImageDrawable(getResources().getDrawable(R.drawable.like));
                    }

                    String collection = data.getCollection();
                    if (collection.equals("1")) { // 已收藏
                        isCollect = true;
                        imageView3.setImageDrawable(getResources().getDrawable(R.drawable.collected1));
                    } else {
                        isCollect = false;
                        imageView3.setImageDrawable(getResources().getDrawable(R.drawable.collect1));
                    }

                    scrollView1.smoothScrollTo(0, 0);
                } else {
                    showLongToast(status.getError_desc());
                    if (status.getError_desc().equals("该商品不存在")) {
                        finish();
                    }
                    if (status.getSucceed() == 2) {
                        Intent intentLogIn = new Intent(NewDetailActivity.this, UserLogInActivity.class);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab_item1: // 点击底栏首页
                popupWindowMenu.dismiss();
                Intent intent_menu11 = new Intent(NewDetailActivity.this, MainActivity.class);
                intent_menu11.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_menu11.putExtra("tabhost", 0);
                startActivity(intent_menu11);
                break;
            case R.id.tab_item2: // 点击底栏购物车
                popupWindowMenu.dismiss();
                Intent intent_menu22 = new Intent(NewDetailActivity.this, MainActivity.class);
                intent_menu22.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_menu22.putExtra("tabhost", 1);
                startActivity(intent_menu22);
                break;
            case R.id.tab_item3: // 点击底栏订单中心
                popupWindowMenu.dismiss();
                Intent intent_menu33 = new Intent(NewDetailActivity.this, MainActivity.class);
                intent_menu33.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_menu33.putExtra("tabhost", 2);
                startActivity(intent_menu33);
                break;
            case R.id.tab_item4: // 点击底栏商场指南
                Intent intent_tab44 = new Intent(NewDetailActivity.this, MainActivity.class);
                intent_tab44.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_tab44.putExtra("tabhost", 3);
                startActivity(intent_tab44);
                break;
            case R.id.tab_item5: // 点击底栏我要供货
                Intent intent_tab55 = new Intent(NewDetailActivity.this, MainActivity.class);
                intent_tab55.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_tab55.putExtra("tabhost", 4);
                startActivity(intent_tab55);
                break;
            case R.id.rela_menu1:
                popupWindowMenu.dismiss();
                Intent intent_menu1 = new Intent(NewDetailActivity.this, MainActivity.class);
                intent_menu1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_menu1.putExtra("tabhost", 0);
                startActivity(intent_menu1);
                break;
            case R.id.rela_menu2:
                popupWindowMenu.dismiss();
                Intent intent_menu2 = new Intent(NewDetailActivity.this, MainActivity.class);
                intent_menu2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_menu2.putExtra("tabhost", 1);
                startActivity(intent_menu2);
                break;
            case R.id.rela_menu3:
                popupWindowMenu.dismiss();
                Intent intent_menu3 = new Intent(NewDetailActivity.this, MainActivity.class);
                intent_menu3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_menu3.putExtra("tabhost", 2);
                startActivity(intent_menu3);
                break;
            case R.id.rela_menu4:
                popupWindowMenu.dismiss();
                Intent intent_menu4 = new Intent(NewDetailActivity.this, UserActivity.class);
                startActivity(intent_menu4);
                break;

            case R.id.text_buynow: // 立刻购买
                // 先加入购物车，以获得购物车id
                String url = GlobalVariable.URL.ADDTOSHOPCAR;
                YazhiHttp yazhiHttp = new YazhiHttp(this, url);
                yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
                yazhiHttp.addParams("goods_id", goods_id);
                yazhiHttp.addParams("goods_num", buyNum + "");
                String isOne = null;
                if (radio0.isChecked()) { // 选中整组起批
                    isOne = "0";
                } else { // 选择单瓶起批
                    isOne = "1";
                }
                yazhiHttp.addParams("bottle", isOne);
                yazhiHttp.post(new YazhiHttp.MyListener() {
                    @Override
                    public void onSuccess(String arg0) {
                        Like parseJSON = GsonUtils.parseJSON(arg0, Like.class);
                        com.xmzlb.model.Status status = parseJSON.getStatus();
                        if (status.getSucceed() == 0) {
                        } else {
                        }
                    }

                    @Override
                    public void onError() {

                    }
                });

                Intent intent = new Intent(NewDetailActivity.this, ComfirmOrderActivity.class);
                intent.putExtra("goods_id", goods_id);
                intent.putExtra("loadorder", "0");
                intent.putExtra("buyNum", buyNum);
                startActivity(intent);

                break;
            case R.id.rela_goToScoreCity: // 前往积分商城
                startActivity(ScoreCityActivity.class);
                break;
            case R.id.rela_addToCar: // 加入购物车
                addToShopCar();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.rele_menu_detailact: // 点击bar 菜单
                popupWindowMenu.showAsDropDown(bar);
                break;
            case R.id.rela_empty_meun:
                popupWindowMenu.dismiss();
                break;
            case R.id.ima_addt_detailact: // 增加
                buyNum++;
                editText12.setText(buyNum + "");
                break;
            case R.id.ima_substract_detailact: // 减少
                if (buyNum == 1) {
                    return;
                } else {
                    buyNum--;
                    editText12.setText(buyNum + "");
                }
                break;
            case R.id.ima_like_detailact: // 点赞
                like();
                break;
            case R.id.imageView3: // 收藏
                collect();
            default:
                break;
        }
    }

    private void addToShopCar() {
        String url = GlobalVariable.URL.ADDTOSHOPCAR;
        YazhiHttp yazhiHttp = new YazhiHttp(this, url);
        yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
        yazhiHttp.addParams("goods_id", goods_id);
        yazhiHttp.addParams("goods_num", buyNum + "");
        String isOne = null;
        if (radio0.isChecked()) { // 选中整组起批
            isOne = "0";
        } else { // 选择单瓶起批
            isOne = "1";
        }
        yazhiHttp.addParams("bottle", isOne);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Like parseJSON = GsonUtils.parseJSON(arg0, Like.class);
                com.xmzlb.model.Status status = parseJSON.getStatus();
                if (status.getSucceed() == 0) { // 收藏失败
                    showShortToast(status.getError_desc());
                } else {
                    showShortToast(parseJSON.getData().getMsg());
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void collect() {
        String url = null;
        if (isCollect) { // 收藏过，点击取消收藏
            url = GlobalVariable.URL.DISCOLLECT;
        } else {
            url = GlobalVariable.URL.COLLECT;
        }
        YazhiHttp yazhiHttp = new YazhiHttp(this, url);
        yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
        yazhiHttp.addParams("goods_id", goods_id);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Like parseJSON = GsonUtils.parseJSON(arg0, Like.class);
                com.xmzlb.model.Status status = parseJSON.getStatus();
                if (status.getSucceed() == 1) {
                    if (isCollect) { // 收藏过，点击取消收藏
                        collectNum--;
                        text_love.setText("点击收藏（" + collectNum + "人气）");
                        imageView3.setImageDrawable(getResources().getDrawable(R.drawable.collect1));
                    } else {
                        collectNum++;
                        text_love.setText("点击收藏（" + collectNum + "人气）");
                        imageView3.setImageDrawable(getResources().getDrawable(R.drawable.collected1));
                    }
                    showShortToast(parseJSON.getData().getMsg());
                    isCollect = !isCollect;
                } else {
                    showShortToast(status.getError_desc());
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    // 点赞和取消点赞
    private void like() {
        String url = null;
        if (isLike) { // 点过赞，点击取消点赞
            url = GlobalVariable.URL.DISLIKE;
        } else {
            url = GlobalVariable.URL.LIKE;
        }
        YazhiHttp yazhiHttp = new YazhiHttp(this, url);
        yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
        yazhiHttp.addParams("goods_id", goods_id);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Like parseJSON = GsonUtils.parseJSON(arg0, Like.class);
                com.xmzlb.model.Status status = parseJSON.getStatus();
                if (status.getSucceed() == 1) {
                    showShortToast(parseJSON.getData().getMsg());
                    if (isLike) {
                        ima_like_detailact.setImageDrawable(getResources().getDrawable(R.drawable.like));
                    } else {
                        ima_like_detailact.setImageDrawable(getResources().getDrawable(R.drawable.liked));
                    }
                    isLike = !isLike;
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
        scrollView1 = (ScrollView) findViewById(R.id.scrollView1);
        text_name_detailact = (TextView) findViewById(R.id.text_name_detailact);
        text_pricesingle_detailact = (TextView) findViewById(R.id.text_pricesingle_detailact);
        text_pricegroup_detailact = (TextView) findViewById(R.id.text_pricegroup_detailact);
        text_isgroupway_detailact = (TextView) findViewById(R.id.text_isgroupway_detailact);
        text_pricevip_detailact = (TextView) findViewById(R.id.text_pricevip_detailact);
        text_score_detailact = (TextView) findViewById(R.id.text_score_detailact);
        text_groupnum_detailact = (TextView) findViewById(R.id.text_groupnum_detailact);
        text_singleleft_detailact = (TextView) findViewById(R.id.text_singleleft_detailact);
        text_promotion_detailact = (TextView) findViewById(R.id.text_promotion_detailact);
        text_love = (TextView) findViewById(R.id.text_love);
        textView2 = (TextView) findViewById(R.id.textView2);
        textView18 = (TextView) findViewById(R.id.textView18);
        textView19 = (TextView) findViewById(R.id.textView19);
        textView20 = (TextView) findViewById(R.id.textView20);
        textView26 = (TextView) findViewById(R.id.textView26);
        textView27 = (TextView) findViewById(R.id.textView27);
        textView28 = (TextView) findViewById(R.id.textView28);
        textView29 = (TextView) findViewById(R.id.textView29);
        textView33 = (TextView) findViewById(R.id.textView33);
        textView34 = (TextView) findViewById(R.id.textView34);
        textView35 = (TextView) findViewById(R.id.textView35);
        text_groupway_detailact = (TextView) findViewById(R.id.text_groupway_detailact);
        webView1 = (WebView) findViewById(R.id.webView1);
        WebSettings settings = webView1.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setJavaScriptEnabled(true);
        //设定后太小
//        settings.setUseWideViewPort(true);
//        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);

        editText12 = (TextView) findViewById(R.id.editText1);
        radio0 = (RadioButton) findViewById(R.id.radio0);
        radio1 = (RadioButton) findViewById(R.id.radio1);

        imageView3 = (ImageView) findViewById(R.id.imageView3); // 收藏
        ima_like_detailact = (ImageView) findViewById(R.id.ima_like_detailact); // 点赞

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

    @Override
    protected void initEvents() {
        findViewById(R.id.rele_menu_detailact).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.rela_goToScoreCity).setOnClickListener(this);
        findViewById(R.id.rela_addToCar).setOnClickListener(this);
        findViewById(R.id.ima_addt_detailact).setOnClickListener(this);
        findViewById(R.id.ima_substract_detailact).setOnClickListener(this);
        findViewById(R.id.ima_like_detailact).setOnClickListener(this);
        findViewById(R.id.imageView3).setOnClickListener(this);
        findViewById(R.id.text_buynow).setOnClickListener(this);

        // 点击菜单中按钮
        popMenu.findViewById(R.id.rela_menu1).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_menu2).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_menu3).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_menu4).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_empty_meun).setOnClickListener(this);

        // 底栏tab
        findViewById(R.id.tab_item1).setOnClickListener(this);
        findViewById(R.id.tab_item2).setOnClickListener(this);
        findViewById(R.id.tab_item3).setOnClickListener(this);
        findViewById(R.id.tab_item4).setOnClickListener(this);
        findViewById(R.id.tab_item5).setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    // private void addDynamicView(int num) {
    // imaList.clear();
    // // 动态空白banne图片
    // // 初始化图片资源
    // for (int i = 0; i < num; i++) {
    // ImageView imageView = new ImageView(NewDetailActivity.this);
    // // 异步加载图片
    // imageView.setScaleType(ScaleType.CENTER_CROP);
    // imaList.add(imageView);
    // }
    // }
}
