package com.xmzlb.wine;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinbo.utils.GsonUtils;
import com.xmzlb.base.BaseActivity;
import com.xmzlb.registermodel.Datum2;
import com.xmzlb.registermodel.Msg;
import com.xmzlb.registermodel.Scorecity;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.zyzutil.YazhiHttp;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class ScoreCityActivity extends BaseActivity {

    private GridView gv_scorecity;
    private GvAdapter gvAdapter;
    ArrayList<Datum2> list = new ArrayList<Datum2>();
    ImageOptions imageOptions;
    private PopupWindow popupWindowMenu; // 菜单弹窗
    private TextView text_topbar;
    private RelativeLayout bar;
    private View popMenu;
    private TextView text_nothing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_city);
        initViews();
        initEvents();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData("new");
    }

    private void initData(String condition) {
        text_nothing.setVisibility(View.GONE);
        String url = GlobalVariable.URL.SCORECITY;
        YazhiHttp yazhiHttp = new YazhiHttp(this, url);
        yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
        yazhiHttp.addParams("condition", condition);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Scorecity parseJSON = GsonUtils.parseJSON(arg0, Scorecity.class);
                List<Datum2> data = parseJSON.getData();
                list.clear();
                list.addAll(data);
                gvAdapter.notifyDataSetChanged();
                if (data.size() == 0) {
                    text_nothing.setVisibility(View.VISIBLE);
                } else {
                    text_nothing.setVisibility(View.GONE);
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
            case R.id.radio1: // 最新
                initData("new");
                break;
            case R.id.radio2: // 人气
                initData("hot");
                break;
            case R.id.radio3: // 积分up
                initData("integral_min");
                break;
            case R.id.radio4: // 积分down
                initData("integral_max");
                break;
            case R.id.tab_item1: // 点击底栏首页
            case R.id.rela_menu1:
                Intent intent = new Intent(ScoreCityActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("tabhost", 0);
                startActivity(intent);
                break;
            case R.id.tab_item2: // 点击底栏购物车
            case R.id.rela_menu2:
                Intent intent2 = new Intent(ScoreCityActivity.this, MainActivity.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent2.putExtra("tabhost", 1);
                startActivity(intent2);
                break;
            case R.id.tab_item3: // 点击底栏订单中心
            case R.id.rela_menu3:
                Intent intent3 = new Intent(ScoreCityActivity.this, MainActivity.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent3.putExtra("tabhost", 2);
                startActivity(intent3);
                break;
            case R.id.rela_menu4:
                Intent intent6 = new Intent(ScoreCityActivity.this, UserActivity.class);
                startActivity(intent6);
                break;
            case R.id.rele_menu_detailact: // 菜单
                popupWindowMenu.showAsDropDown(bar);
                break;
            case R.id.tab_item4: // 点击底栏商场指南
                Intent intent4 = new Intent(ScoreCityActivity.this, MainActivity.class);
                intent4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent4.putExtra("tabhost", 3);
                startActivity(intent4);
                break;
            case R.id.tab_item5: // 点击底栏我要供货
                Intent intent5 = new Intent(ScoreCityActivity.this, MainActivity.class);
                intent5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent5.putExtra("tabhost", 4);
                startActivity(intent5);
                break;
            case R.id.rela_empty_meun:
                popupWindowMenu.dismiss();
                break;
            default:
                break;
        }
    }

    @Override
    protected void initViews() {
        bar = (RelativeLayout) findViewById(R.id.bar);

        text_nothing = (TextView) findViewById(R.id.text_nothing);
        text_topbar = (TextView) findViewById(R.id.text_topbar);

        gv_scorecity = (GridView) findViewById(R.id.gv_scorecity);

        gvAdapter = new GvAdapter();
        gv_scorecity.setAdapter(gvAdapter);

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

        // 菜单
        popMenu = getLayoutInflater().inflate(R.layout.include_menu, null);
        popupWindowMenu = new PopupWindow(popMenu, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        // 设置点击窗体以外区域关闭窗体
        BitmapDrawable b = new BitmapDrawable();
        popupWindowMenu.setBackgroundDrawable(b);
        popupWindowMenu.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {

            }
        });
        popMenu.findViewById(R.id.rela_empty_meun).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_menu1).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_menu2).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_menu3).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_menu4).setOnClickListener(this);

    }

    @Override
    protected void initEvents() {
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.radio1).setOnClickListener(this);
        findViewById(R.id.radio2).setOnClickListener(this);
        findViewById(R.id.radio3).setOnClickListener(this);
        findViewById(R.id.radio4).setOnClickListener(this);
        findViewById(R.id.rele_menu_detailact).setOnClickListener(this);

        // 底栏tab
        findViewById(R.id.tab_item1).setOnClickListener(this);
        findViewById(R.id.tab_item2).setOnClickListener(this);
        findViewById(R.id.tab_item3).setOnClickListener(this);
        findViewById(R.id.tab_item4).setOnClickListener(this);
        findViewById(R.id.tab_item5).setOnClickListener(this);
    }

    @Override
    protected void init() {
        text_topbar.setText("积分商城");
    }

    class GvAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
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
            Button btn_exchange;
            TextView text_score;
            TextView text_num;
            TextView text_price;
            TextView text_title;
            ImageView imageView1;

            if (convertView == null) {
                view = getLayoutInflater().inflate(R.layout.item_gv_scorecity, null);
                imageView1 = (ImageView) view.findViewById(R.id.imageView1);
                text_title = (TextView) view.findViewById(R.id.text_title);
                text_price = (TextView) view.findViewById(R.id.text_price);
                text_num = (TextView) view.findViewById(R.id.text_num);
                text_score = (TextView) view.findViewById(R.id.text_score);
                btn_exchange = (Button) view.findViewById(R.id.btn_exchange);
                view.setTag(new MyTag(btn_exchange, text_score, text_num, text_price, text_title, imageView1));
            } else {
                view = convertView;
                MyTag tag2 = (MyTag) view.getTag();
                btn_exchange = tag2.btn_exchange;
                text_score = tag2.text_score;
                text_num = tag2.text_num;
                text_price = tag2.text_price;
                text_title = tag2.text_title;
                imageView1 = tag2.imageView1;
            }

            Datum2 datum2 = list.get(position);
            text_score.setText(datum2.getExchangeIntegral() + "分");
            Msg msg = datum2.getMsg();
            if (msg.getIsOne().equals("0")) {
                text_num.setText("1件");
            } else {
                text_num.setText("1瓶");
            }
            text_price.setText("价值：￥" + msg.getShopPrice());
            text_title.setText(msg.getGoodsName());

            // UILUtils.displayImageNoAnim(msg.getOriginalImg(), imageView1);
            x.image().bind(imageView1, msg.getOriginalImg(), imageOptions);

            final String goodsId = datum2.getGoodsId();
            imageView1.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    startActivity(ScoreDetailActivity.class, "goods_id", goodsId);
                }
            });

            // 兑换
            btn_exchange.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    startActivity(ScoreDetailActivity.class, "goods_id", goodsId);
                }

            });

            return view;
        }
    }

    class MyTag {
        Button btn_exchange;
        TextView text_score;
        TextView text_num;
        TextView text_price;
        TextView text_title;
        ImageView imageView1;

        public MyTag(Button btn_exchange, TextView text_score, TextView text_num, TextView text_price,
                     TextView text_title, ImageView imageView1) {
            super();
            this.btn_exchange = btn_exchange;
            this.text_score = text_score;
            this.text_num = text_num;
            this.text_price = text_price;
            this.text_title = text_title;
            this.imageView1 = imageView1;
        }

    }

}
