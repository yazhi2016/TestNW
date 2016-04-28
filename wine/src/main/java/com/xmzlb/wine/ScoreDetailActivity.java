package com.xmzlb.wine;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xmzlb.base.BaseActivity;
import com.xmzlb.model.Data2;
import com.xmzlb.model.Datum13;
import com.xmzlb.model.Like;
import com.xmzlb.model.Member;
import com.xmzlb.model.Rank;
import com.xmzlb.model.Scoredetail;
import com.xmzlb.model.Status;
import com.xmzlb.registermodel.Userinfo;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.zyzutil.YazhiHttp;
import com.xinbo.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

public class ScoreDetailActivity extends BaseActivity {

    ArrayList<ImageView> imaList = new ArrayList<ImageView>();
    private PopupWindow popupWindow; // 兑换弹窗
    private View pop;
    private RelativeLayout rela_comfirm;
    private RelativeLayout rela_scoreNotEnough;
    private RelativeLayout rela_success;
    private String goods_id;
    private TextView text_name_detailact;
    private TextView text_groupway_detailact;
    private TextView text_isgroupway_detailact;
    private TextView text_score_gold;
    private TextView text_pricegroup_detailact;
    private TextView text_pricesingle_detailact2;
    private WebView webView1;
    private TextView text_score;
    private TextView textView6;
    private String rankName; // 等级
    private String rankPoints; // 积分
    private TextView nowPoint;
    private TextView nowRank;
    private TextView afterPoint;
    private TextView afterRank;

    private int max1; // 普通会员最高分
    private int max2; // 银卡最高分
    private int max3; // 金卡最低分
    private int min2;
    private int min3;
    private Integer point1; // 普通兑换需要的积分
    private Integer point2; // 银卡
    private Integer point3;
    Integer needPosint; // 兑换所需积分

    boolean canExchange = false;
    private TextView editText1;
    int Number = 1;
    private TextView textView3;
    private TextView textView4;

    View popMenu;
    private PopupWindow popupWindowMenu; // 菜单弹窗
    private View bar;

    ArrayList<String> adList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_detail);
        goods_id = getIntent().getStringExtra("goods_id");
        initViews();
        init();
        initEvents();
        initRank();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
        initUserScore();
    }

    // 会员等级划分
    private void initRank() {
        String url = GlobalVariable.URL.RANK;
        YazhiHttp yazhiHttp = new YazhiHttp(url);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Rank parseJSON = GsonUtils.parseJSON(arg0, Rank.class);
                Status status = parseJSON.getStatus();
                if (status.getSucceed() == 1) {
                    List<Datum13> data = parseJSON.getData();
                    max1 = Integer.parseInt(data.get(0).getMaxPoints());
                    max2 = Integer.parseInt(data.get(1).getMaxPoints());
                    min2 = Integer.parseInt(data.get(1).getMinPoints());
                    max3 = Integer.parseInt(data.get(2).getMaxPoints());
                    min3 = Integer.parseInt(data.get(2).getMinPoints());

                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void initUserInfo() {
        String url = GlobalVariable.URL.USERCENTER;
        YazhiHttp yazhiHttp = new YazhiHttp(this, url);
        String spSid = GlobalVariable.getInstance().getSpSid("empty");
        yazhiHttp.addParams("sid", spSid);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Userinfo parseJSON = GsonUtils.parseJSON(arg0, Userinfo.class);
                if (parseJSON.getStatus().getSucceed() == 1) { // 得到数据
                    com.xmzlb.registermodel.Data2 data = parseJSON.getData();
                    rankName = data.getRankName();
                    rankPoints = data.getRankPoints();

                    if (rankName.equals("普通会员")) {
                        needPosint = point1 * Number;
                    } else if (rankName.equals("银卡会员")) {
                        needPosint = point2 * Number;
                    } else {
                        needPosint = point3 * Number;
                    }
                    textView6.setText("所需积分:" + needPosint);
                    nowPoint.setText("当前积分：" + rankPoints);
                    nowRank.setText("当前积分：" + rankName);

                    int pointAfter = Integer.parseInt(rankPoints) - needPosint;
                    afterPoint.setText("兑后积分：" + pointAfter);
                    if (pointAfter < max1) {
                        afterRank.setText("兑后等级：" + "普通会员");
                    } else if (pointAfter > max1 && pointAfter < max2) {
                        afterRank.setText("兑后等级：" + "银卡会员");
                    } else {
                        afterRank.setText("兑后等级：" + "金卡会员");
                    }

                    if (Integer.parseInt(rankPoints) < needPosint) { // 当前积分小于所需积分，即不能兑换
                        canExchange = false;
                    } else {
                        canExchange = true;
                    }

                    textView3.setText("所需积分：" + needPosint);
                    textView4.setText("您的积分：" + rankPoints);

                    // 登陆成功，跳出popwindow
                    popupWindow.showAtLocation(pop, Gravity.CENTER, 0, 0);
                } else {
                    showShortToast(parseJSON.getStatus().getError_desc());
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void initUserScore() {
        String url = GlobalVariable.URL.USERCENTER;
        String spSid = GlobalVariable.getInstance().getSpSid("empty");
        YazhiHttp yazhiHttp = new YazhiHttp(this, url);
        yazhiHttp.addParams("sid", spSid);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Userinfo parseJSON = GsonUtils.parseJSON(arg0, Userinfo.class);
                if (parseJSON.getStatus().getSucceed() == 1) { // 得到数据
                    com.xmzlb.registermodel.Data2 data = parseJSON.getData();
                    text_score.setText(data.getRankPoints());
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void initData() {
        String url = GlobalVariable.URL.SCOREDETAIL;
        YazhiHttp yazhiHttp = new YazhiHttp(this, url);
        yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
        yazhiHttp.addParams("goods_id", goods_id);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Scoredetail parseJSON = GsonUtils.parseJSON(arg0, Scoredetail.class);
                Integer succeed = parseJSON.getStatus().getSucceed();
                if (succeed == 1) {
                    Data2 data = parseJSON.getData();
                    List<String> goodsImg = data.getGoodsImg();
                    adList.clear();
                    adList.addAll(goodsImg);

                    getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new PagerFragment(adList))
                            .commit();

                    text_name_detailact.setText(data.getGoodsName());
                    text_pricegroup_detailact.setText(data.getMoney());
                    if (data.getIsOne().equals("1")) {
                        text_pricesingle_detailact2.setText("1瓶");
                    } else {
                        text_pricesingle_detailact2.setText("1件");
                    }
                    List<Member> member = data.getMember();
                    point1 = member.get(0).getIntegral();
                    point2 = member.get(1).getIntegral();
                    point3 = member.get(2).getIntegral();
                    text_groupway_detailact.setText(point1 + ""); // 普通会员积分
                    text_isgroupway_detailact.setText(member.get(1).getIntegral() + ""); // 银卡积分
                    text_score_gold.setText(member.get(2).getIntegral() + ""); // 金卡积分

                    String goodsDesc = data.getGoodsDesc();
                    webView1.loadDataWithBaseURL(null, goodsDesc, null, "utf-8", null);

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
            case R.id.rela_menu1:
            case R.id.tab_item1: // 点击底栏首页
                popupWindowMenu.dismiss();
                Intent intent_menu1 = new Intent(ScoreDetailActivity.this, MainActivity.class);
                intent_menu1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_menu1.putExtra("tabhost", 0);
                startActivity(intent_menu1);
                break;
            case R.id.rela_menu2:
            case R.id.tab_item2: // 点击底栏购物车
                popupWindowMenu.dismiss();
                Intent intent_menu2 = new Intent(ScoreDetailActivity.this, MainActivity.class);
                intent_menu2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_menu2.putExtra("tabhost", 1);
                startActivity(intent_menu2);
                break;
            case R.id.rela_menu3:
            case R.id.tab_item3: // 点击底栏订单中心
                popupWindowMenu.dismiss();
                Intent intent_menu3 = new Intent(ScoreDetailActivity.this, MainActivity.class);
                intent_menu3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_menu3.putExtra("tabhost", 2);
                startActivity(intent_menu3);
                break;
            case R.id.rela_menu4:
                popupWindowMenu.dismiss();
                Intent intent_menu4 = new Intent(ScoreDetailActivity.this, UserActivity.class);
                intent_menu4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent_menu4);
                break;
            case R.id.tab_item4: // 点击底栏商场指南
                Intent intent_tab4 = new Intent(ScoreDetailActivity.this, MainActivity.class);
                intent_tab4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent_tab4.putExtra("tabhost", 3);
                startActivity(intent_tab4);
                break;
            case R.id.tab_item5: // 点击底栏我要供货
                Intent intent_tab5 = new Intent(ScoreDetailActivity.this, MainActivity.class);
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
            case R.id.text_exchange: // 点击兑换
                initUserInfo();
                break;
            case R.id.btn_comfirm: // 兑换pop点击确认兑换
                if (canExchange) { // 可以兑换
                    String url = GlobalVariable.URL.EXCHANGESCORE;
                    YazhiHttp yazhiHttp = new YazhiHttp(this, url);
                    yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
                    yazhiHttp.addParams("goods_id", goods_id);
                    String num = Number + "";
                    yazhiHttp.addParams("goods_number", num);
                    yazhiHttp.post(new YazhiHttp.MyListener() {
                        @Override
                        public void onSuccess(String arg0) {
                            Like parseJSON = GsonUtils.parseJSON(arg0, Like.class);
                            Status status = parseJSON.getStatus();
                            if (status.getSucceed() == 1) {
                                rela_comfirm.setVisibility(View.GONE);
                                rela_success.setVisibility(View.VISIBLE);
                                showShortToast(parseJSON.getData().getMsg());
                            } else {
                                showShortToast(status.getError_desc());
                            }
                        }

                        @Override
                        public void onError() {

                        }
                    });
                } else { // 积分不足
                    rela_comfirm.setVisibility(View.GONE);
                    rela_scoreNotEnough.setVisibility(View.VISIBLE);
                }

                break;
            case R.id.btn_close: // 兑换pop点击关闭
            case R.id.btn_close2: // 兑换pop点击关闭
            case R.id.btn_cancel: // 兑换pop点击取消
            case R.id.ima_close:
                rela_comfirm.setVisibility(View.VISIBLE);
                rela_scoreNotEnough.setVisibility(View.GONE);
                rela_success.setVisibility(View.GONE);
                popupWindow.dismiss();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.ima_addt_detailact: // 加
                Number++;
                editText1.setText(Number + "");
                break;
            case R.id.ima_substract_detailact: // 减
                if (Number == 1) {
                    return;
                }
                Number--;
                editText1.setText(Number + "");
                break;
            default:
                break;
        }
    }

    @Override
    protected void initViews() {
        pop = getLayoutInflater().inflate(R.layout.pop_exchangexcore, null);
        popupWindow = new PopupWindow(pop, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);

        // 设置点击窗体以外区域关闭窗体
        BitmapDrawable b = new BitmapDrawable();
        popupWindow.setBackgroundDrawable(b);
        popupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {

            }
        });

        rela_comfirm = (RelativeLayout) pop.findViewById(R.id.rela_comfirm);
        rela_scoreNotEnough = (RelativeLayout) pop.findViewById(R.id.rela_scoreNotEnough);
        rela_success = (RelativeLayout) pop.findViewById(R.id.rela_success);

        text_name_detailact = (TextView) findViewById(R.id.text_name_detailact);
        text_groupway_detailact = (TextView) findViewById(R.id.text_groupway_detailact);
        text_isgroupway_detailact = (TextView) findViewById(R.id.text_isgroupway_detailact);
        text_score_gold = (TextView) findViewById(R.id.text_score_gold);
        text_pricegroup_detailact = (TextView) findViewById(R.id.text_pricegroup_detailact);
        text_pricesingle_detailact2 = (TextView) findViewById(R.id.text_pricesingle_detailact2);
        text_score = (TextView) findViewById(R.id.text_score);
        webView1 = (WebView) findViewById(R.id.webView1);

        WebSettings settings = webView1.getSettings();
        settings.setJavaScriptEnabled(true);
        // settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        textView6 = (TextView) pop.findViewById(R.id.textView6);
        nowPoint = (TextView) pop.findViewById(R.id.nowPoint);
        nowRank = (TextView) pop.findViewById(R.id.nowRank);
        afterPoint = (TextView) pop.findViewById(R.id.afterPoint);
        afterRank = (TextView) pop.findViewById(R.id.afterRank);

        textView3 = (TextView) pop.findViewById(R.id.textView3);
        textView4 = (TextView) pop.findViewById(R.id.textView4);

        editText1 = (TextView) findViewById(R.id.editText1);

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
        findViewById(R.id.ima_substract_detailact).setOnClickListener(this);
        findViewById(R.id.ima_addt_detailact).setOnClickListener(this);
        findViewById(R.id.text_exchange).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        pop.findViewById(R.id.btn_comfirm).setOnClickListener(this);
        pop.findViewById(R.id.btn_cancel).setOnClickListener(this);
        pop.findViewById(R.id.ima_close).setOnClickListener(this);
        pop.findViewById(R.id.btn_close).setOnClickListener(this);
        pop.findViewById(R.id.btn_close2).setOnClickListener(this);

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
