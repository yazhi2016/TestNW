package com.xmzlb.wine;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xmzlb.base.BaseActivity;
import com.xmzlb.model.Collection;
import com.xmzlb.model.Datum7;
import com.xmzlb.model.Like;
import com.xmzlb.model.Status;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.zyzutil.YazhiHttp;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.UILUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionActivity extends BaseActivity {

    private GridView gv_collection;
    private GvAdapter gvAdapter;
    ArrayList<Datum7> list = new ArrayList<Datum7>();

    View popMenu;
    private PopupWindow popupWindowMenu; // 菜单弹窗
    private View bar;
    private TextView mTextNothing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
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
        mTextNothing.setVisibility(View.GONE);
        String url = GlobalVariable.URL.MYCOLLECT;
        Map<String, String> params = new HashMap<String, String>();
        YazhiHttp yazhiHttp = new YazhiHttp(this, url);
        yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Collection parseJSON = GsonUtils.parseJSON(arg0, Collection.class);
                Status status = parseJSON.getStatus();
                if (status.getSucceed() == 1) {
                    List<Datum7> data = parseJSON.getData();
                    list.clear();
                    list.addAll(data);
                    if (data.size() == 0) {
                        mTextNothing.setVisibility(View.VISIBLE);
                    }
                    gvAdapter.notifyDataSetChanged();
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
            case R.id.back:
                finish();
                break;
            case R.id.rela_menu1:
            case R.id.tab_item1: // 点击底栏首页
                popupWindowMenu.dismiss();
                Intent intent_menu1 = new Intent(CollectionActivity.this, MainActivity.class);
                intent_menu1.putExtra("tabhost", 0);
                startActivity(intent_menu1);
                break;
            case R.id.rela_menu2:
            case R.id.tab_item2: // 点击底栏购物车
                popupWindowMenu.dismiss();
                Intent intent_menu2 = new Intent(CollectionActivity.this, MainActivity.class);
                intent_menu2.putExtra("tabhost", 1);
                startActivity(intent_menu2);
                break;
            case R.id.rela_menu3:
            case R.id.tab_item3: // 点击底栏订单中心
                popupWindowMenu.dismiss();
                Intent intent_menu3 = new Intent(CollectionActivity.this, MainActivity.class);
                intent_menu3.putExtra("tabhost", 2);
                startActivity(intent_menu3);
                break;
            case R.id.rela_menu4:
                popupWindowMenu.dismiss();
                finish();
                break;
            case R.id.tab_item4: // 点击底栏商场指南
                Intent intent_tab4 = new Intent(CollectionActivity.this, MainActivity.class);
                intent_tab4.putExtra("tabhost", 3);
                startActivity(intent_tab4);
                break;
            case R.id.tab_item5: // 点击底栏我要供货
                Intent intent_tab5 = new Intent(CollectionActivity.this, IWantSupply.class);
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
        gv_collection = (GridView) findViewById(R.id.gv_collection);

        gvAdapter = new GvAdapter();
        gv_collection.setAdapter(gvAdapter);


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

        mTextNothing = (TextView) findViewById(R.id.text_nothing);
    }

    @Override
    protected void initEvents() {
        findViewById(R.id.back).setOnClickListener(this);
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

    class GvAdapter extends BaseAdapter {

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
            ImageView imageView1;
            ImageView ima_cancel;
            TextView text_title;
            TextView text_price;
            if (convertView == null) {
                view = getLayoutInflater().inflate(R.layout.item_gv_collection, null);
                imageView1 = (ImageView) view.findViewById(R.id.imageView1);
                text_title = (TextView) view.findViewById(R.id.text_title);
                text_price = (TextView) view.findViewById(R.id.text_price);
                ima_cancel = (ImageView) view.findViewById(R.id.ima_cancel);
                view.setTag(new MyTag(imageView1, text_title, text_price, ima_cancel));
            } else {
                view = convertView;
                MyTag tag2 = (MyTag) view.getTag();
                imageView1 = tag2.imageView1;
                text_title = tag2.text_title;
                text_price = tag2.text_price;
                ima_cancel = tag2.ima_cancel;
            }

            Datum7 datum7 = list.get(position);
            UILUtils.displayImageNoAnim(datum7.getOriginalImg(), imageView1);
            text_title.setText(datum7.getName() + "");
            text_price.setText("￥" + datum7.getShopPrice() + "");

            final String goodsId = datum7.getGoodsId();
            ima_cancel.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    // 删除收藏
                    String url = null;
                    url = GlobalVariable.URL.DISCOLLECT;
                    YazhiHttp yazhiHttp = new YazhiHttp(CollectionActivity.this, url);
                    yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
                    yazhiHttp.addParams("goods_id", goodsId);
                    yazhiHttp.post(new YazhiHttp.MyListener() {
                        @Override
                        public void onSuccess(String arg0) {
                            Like parseJSON = GsonUtils.parseJSON(arg0, Like.class);
                            com.xmzlb.model.Status status = parseJSON.getStatus();
                            if (status.getSucceed() == 1) {
                                showShortToast(parseJSON.getData().getMsg());
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

            });

            //点击进入商品详情
            imageView1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(CollectionActivity.this, NewDetailActivity.class);
                    intent.putExtra("goods_id", goodsId);
                    startActivity(intent);
                }
            });

            return view;
        }

    }

    class MyTag {
        ImageView imageView1;
        ImageView ima_cancel;
        TextView text_title;
        TextView text_price;

        public MyTag(ImageView imageView1, TextView text_title, TextView text_price, ImageView ima_cancel) {
            super();
            this.imageView1 = imageView1;
            this.text_title = text_title;
            this.text_price = text_price;
            this.ima_cancel = ima_cancel;
        }

    }
}
