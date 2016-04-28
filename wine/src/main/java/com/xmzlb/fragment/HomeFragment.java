package com.xmzlb.fragment;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.pgyersdk.update.PgyUpdateManager;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.UILUtils;
import com.xinbo.widget.GridView4ScrollView;
import com.xmzlb.homemodel.AllProduct;
import com.xmzlb.homemodel.Hot;
import com.xmzlb.mainmodel.Ad;
import com.xmzlb.mainmodel.Main;
import com.xmzlb.mainmodel._1F;
import com.xmzlb.mainmodel._2F;
import com.xmzlb.mainmodel._3F;
import com.xmzlb.mainmodel._4F;
import com.xmzlb.model.Allproduct;
import com.xmzlb.model.Brand;
import com.xmzlb.model.Category;
import com.xmzlb.model.Child2;
import com.xmzlb.model.Child3;
import com.xmzlb.model.Data;
import com.xmzlb.model.Datum14;
import com.xmzlb.model.Datum5;
import com.xmzlb.model.HomeBanner;
import com.xmzlb.model.Player;
import com.xmzlb.model.Search;
import com.xmzlb.util.Content;
import com.xmzlb.util.RushBuyCountDownTimerView;
import com.xmzlb.util.SquareImageView;
import com.xmzlb.util.ViewPagerIndicator2;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.wheelcity.AbstractWheelTextAdapter;
import com.xmzlb.wheelcity.AddressData;
import com.xmzlb.wheelcity.ArrayWheelAdapter;
import com.xmzlb.wheelcity.OnWheelChangedListener;
import com.xmzlb.wheelcity.WheelView;
import com.xmzlb.wheelview.MyAlertDialog;
import com.xmzlb.wine.AllProductActivity;
import com.xmzlb.wine.NewDetailActivity;
import com.xmzlb.wine.R;
import com.xmzlb.wine.SellerActivity;
import com.xmzlb.wine.UserActivity;
import com.xmzlb.wine.UserLogInActivity;
import com.xmzlb.zyzutil.YazhiHttp;
import com.xmzlb.zyzutil.YazhiUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements OnClickListener {


    int bannerNum = 4;
    private View view;
    protected boolean isDrag;
    private Handler mHandler = new Handler();
    private ViewPager pagerBanner;
    private TextView text_more_itemtitle_homefra;
    LayoutInflater inflater;
    private PopupWindow popupWindow;
    private PopupWindow popupWindow_location;
    private RelativeLayout rela_top_search_homefra;
    private ListView lv_popbrand; // 品牌
    private LvPopAdapter lvPopAdapter; // 品牌导航--品类--适配器
    ArrayList<Datum14> brandList = new ArrayList<>();
    private GridView gv_popbrand;
    private GvPopAdapter gvPopAdapter; // 品牌导航右边
    ArrayList<Child3> brandDetailList = new ArrayList<>();
    int editLine = 0;
    ViewPagerIndicator2 indicator;
    ArrayList<ImageView> imageList = new ArrayList<ImageView>();
    ArrayList<Player> imaList = new ArrayList<Player>();
    private GridView gv_location;
    private GvAdapter gvAdapter;
    private RelativeLayout rela_loacation;
    private RelativeLayout rela_baritem_homefra2;

    ListView lv;
    private PullToRefreshScrollView mPullToRefreshScrollView;

    int mode = 0; // 0全部商品，1地区特供
    private LinearLayout bar;
    private SimpleDraweeView ima_time; // 热销图片
    private SimpleDraweeView ima_promote1; // 热销图片1
    private SimpleDraweeView ima_promote2; // 热销图片2
    private SimpleDraweeView ima_promote3; // 热销图片3
    String ima_time1;
    String ima_promote11;
    String ima_promote21;
    String ima_promote31;

    ArrayList<_1F> list1 = new ArrayList<_1F>();
    ArrayList<_2F> list2 = new ArrayList<_2F>();
    ArrayList<_3F> list3 = new ArrayList<_3F>();
    ArrayList<_4F> list4 = new ArrayList<_4F>();
    private SimpleDraweeView ima_new1;
    private SimpleDraweeView ima_new2;
    private SimpleDraweeView ima_new3;
    private SimpleDraweeView ima_new4;
    String ima_new11;
    String ima_new21;
    String ima_new31;
    String ima_new41;
    private GridView4ScrollView gv1;
    private GridView4ScrollView gv2;
    private GridView4ScrollView gv3;
    private GridView4ScrollView gv4;
    private Gv1Adapter gv1Adapter;
    private Gv1Adapter gv2Adapter;
    private Gv1Adapter gv3Adapter;
    private Gv1Adapter gv4Adapter;
    ImageOptions imageOptions;
    private TextView editText1;
    private TextView editText2;
    private TextView editText3;
    private TextView editText4;

    private int ima_size = 1;
    private EditText edit_search;
    ArrayList<Datum5> searchList = new ArrayList<>();

    String id1 = "363"; // 选中的省的id，为empty则说明没选
    String id2 = "360";
    String id3 = "empty";
    String id4 = "empty";
    int position1 = 0; // 选中省的行号,如果等-1，说明还未选择，则不能选择市
    int position2 = 0;
    int position3 = -1;
    int position4 = -1;
    int editPosition1;
    int editPosition2;
    int editPosition3;
    int editPosition4;
    String[] shen;
    int locationMode = 1; // 选择的模式：1省，2市，3区,4镇
    String province;
    String cityStr;
    String countyStr;
    String zhenStr;
    private Child2 location;
    private TextView textView7;
    private TextView textView_nothing;
    ArrayList<Ad> adList = new ArrayList<Ad>();
    int currentView = 0;
    int BANNER_COUNT = 4 * 100000;
    private PagerBannerAdapter pagerBannerAdapter;
    boolean finish = false;
    private RushBuyCountDownTimerView mClockView;
    private String mEndTime = "";

    public HomeFragment() {
    }

    @Override
    public void onPause() {
        super.onPause();
        finish = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        finish = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home2, container, false);
            this.inflater = inflater;

            PgyUpdateManager.register(getActivity());

            imageOptions = new ImageOptions.Builder().setSize(DensityUtil.dip2px(300), DensityUtil.dip2px(300))
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
            initData2();
            initEvent();
            autoScroll();
        }
        return view;
    }

    private void initLocationSpecial() {
        String url = GlobalVariable.URL.CATEGORY;
        YazhiHttp yazhiHttp = new YazhiHttp(getActivity(),url);
        yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Category parseJSON = GsonUtils.parseJSON(arg0, Category.class);
                com.xmzlb.model.Status status = parseJSON.getStatus();
                if (status.getSucceed() == 1) {
                    location = parseJSON.getData().get(1);
                } else {
                    Toast.makeText(getActivity(), status.getError_desc(), Toast.LENGTH_SHORT).show();
                    if (status.getSucceed() == 2) { // 账号过期
                        Intent intentLogIn = new Intent(getActivity(), UserLogInActivity.class);
//						intentLogIn.putExtra("from", 0);
//						intentLogIn.putExtra("fromStatus", 0);
                        intentLogIn.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentLogIn);
                    }
                }
            }

            @Override
            public void onError() {
            }
        });
    }

    private void initEvent() {
        ima_time.setOnClickListener(this);
        view.findViewById(R.id.rela_ten).setOnClickListener(this);
        view.findViewById(R.id.rela_hundred).setOnClickListener(this);
        view.findViewById(R.id.rela_share).setOnClickListener(this);
        view.findViewById(R.id.rela_foreignwine).setOnClickListener(this);
        view.findViewById(R.id.rela_otherwine).setOnClickListener(this);
        view.findViewById(R.id.rela_milk).setOnClickListener(this);
        view.findViewById(R.id.rela_food).setOnClickListener(this);
        view.findViewById(R.id.more1).setOnClickListener(this);
        view.findViewById(R.id.more2).setOnClickListener(this);
        view.findViewById(R.id.more3).setOnClickListener(this);
        view.findViewById(R.id.more4).setOnClickListener(this);
    }

    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            // Simulates a background job.
            try {
                Thread.sleep(500);
                initData2();
            } catch (InterruptedException e) {
            }
            return new String[]{"", ""};
        }

        @Override
        protected void onPostExecute(String[] result) {
            mPullToRefreshScrollView.onRefreshComplete();
            super.onPostExecute(result);
        }
    }

    private void initData2() {
        String url = GlobalVariable.URL.MAIN;
        YazhiHttp yazhiHttp = new YazhiHttp(url);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Main parseJSON = GsonUtils.parseJSON(arg0, Main.class);
                Integer succeed = parseJSON.getStatus().getSucceed();
                if (succeed == 1) { // 返回数据成功
                    com.xmzlb.mainmodel.Data data = parseJSON.getData();

                    adList.clear();
                    List<Ad> ad = data.getAd();
                    adList.addAll(ad);
                    ima_size = ad.size();
                    indicator.changeNum(ima_size);
                    addDynamicView(ima_size);

                    imageList.clear();
                    for (int i = 0; i < ad.size(); i++) {
                        ImageView imageView = new ImageView(getActivity());
                        imageView.setScaleType(ScaleType.FIT_XY);
                        imageList.add(imageView);
                        UILUtils.displayImageNoAnim(ad.get(i).getAdCode(), imageList.get(i));
                    }
                    pagerBanner.setCurrentItem(BANNER_COUNT / 2 + 400);
                    // text_title_banner_homefra.setText(lists1.get(0).getTitle());

                    // 热卖促销
//                    UILUtils.displayImageNoAnim(data.getPromote().getAdCode(), ima_time);

                    YazhiUtils.setImage(data.getPromote().getAdCode(), ima_time);
                    mEndTime = data.getPromote().getEndTime();

                    YazhiUtils.calcuTime(mEndTime, mClockView);

                    YazhiUtils.setImage(data.getHot().get(0).getAdCode(), ima_promote1);
                    YazhiUtils.setImage(data.getHot().get(1).getAdCode(), ima_promote2);
                    YazhiUtils.setImage(data.getHot().get(2).getAdCode(), ima_promote3);
//                    UILUtils.displayImageNoAnim(data.getHot().get(0).getAdCode(), ima_promote1);
//                    UILUtils.displayImageNoAnim(data.getHot().get(1).getAdCode(), ima_promote2);
//                    UILUtils.displayImageNoAnim(data.getHot().get(2).getAdCode(), ima_promote3);
                    // x.image().bind(ima_promote1,
                    // data.getHot().get(0).getAdCode(), imageOptions);
                    // x.image().bind(ima_promote2,
                    // data.getHot().get(1).getAdCode(), imageOptions);
                    // x.image().bind(ima_promote3,
                    // data.getHot().get(2).getAdCode(), imageOptions);
                    ima_time1 = data.getPromote().getAdLink();
                    ima_promote11 = data.getHot().get(0).getAdLink();
                    ima_promote21 = data.getHot().get(1).getAdLink();
                    ima_promote31 = data.getHot().get(2).getAdLink();

                    // 新品上市
                    YazhiUtils.setImage(data.getNew().get(0).getAdCode(), ima_new1);
                    YazhiUtils.setImage(data.getNew().get(1).getAdCode(), ima_new2);
                    YazhiUtils.setImage(data.getNew().get(2).getAdCode(), ima_new3);
                    YazhiUtils.setImage(data.getNew().get(3).getAdCode(), ima_new4);
//                    UILUtils.displayImageNoAnim(data.getNew().get(0).getAdCode(), ima_new1);
//                    UILUtils.displayImageNoAnim(data.getNew().get(1).getAdCode(), ima_new2);
//                    UILUtils.displayImageNoAnim(data.getNew().get(2).getAdCode(), ima_new3);
//                    UILUtils.displayImageNoAnim(data.getNew().get(3).getAdCode(), ima_new4);
                    // x.image().bind(ima_new3,
                    // data.getNew().get(2).getAdCode(), imageOptions);
                    // x.image().bind(ima_new4,
                    // data.getNew().get(3).getAdCode(), imageOptions);
                    ima_new11 = data.getNew().get(0).getAdLink();
                    ima_new21 = data.getNew().get(1).getAdLink();
                    ima_new31 = data.getNew().get(2).getAdLink();
                    ima_new41 = data.getNew().get(3).getAdLink();

                    List<_1F> get1f = data.get1F();
                    list1.clear();
                    list1.addAll(get1f);
                    List<_2F> get2f = data.get2F();
                    list2.clear();
                    list2.addAll(get2f);
                    List<_3F> get3f = data.get3F();
                    list3.clear();
                    list3.addAll(get3f);
                    List<_4F> get4f = data.get4F();
                    list4.clear();
                    list4.addAll(get4f);
                    gv1Adapter.notifyDataSetChanged();
                    gv2Adapter.notifyDataSetChanged();
                    gv3Adapter.notifyDataSetChanged();
                    gv4Adapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(getActivity(), "服务器返回数据错误", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void initUI() {
        mPullToRefreshScrollView = (PullToRefreshScrollView) view.findViewById(R.id.scrollviiew);
        // 监听下拉事件
        mPullToRefreshScrollView.setOnRefreshListener(new OnRefreshListener<ScrollView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                new GetDataTask().execute();
            }
        });

        bar = (LinearLayout) view.findViewById(R.id.bar);

        // 广告轮播
        pagerBanner = (ViewPager) view.findViewById(R.id.pager_banner);
        FragmentManager fm = getChildFragmentManager();
        pagerBannerAdapter = new PagerBannerAdapter(fm);
        pagerBanner.setAdapter(pagerBannerAdapter);
        pagerBanner.setCurrentItem(BANNER_COUNT / 2);
        pagerBanner.setOnPageChangeListener(new MyPageChangeListener());

        // 热卖促销图片
        ima_time = (SimpleDraweeView) view.findViewById(R.id.ima_time);
        ima_promote1 = (SimpleDraweeView) view.findViewById(R.id.ima_promote1);
        ima_promote2 = (SimpleDraweeView) view.findViewById(R.id.ima_promote2);
        ima_promote3 = (SimpleDraweeView) view.findViewById(R.id.ima_promote3);
        ima_time.setOnClickListener(this);
        ima_promote1.setOnClickListener(this);
        ima_promote2.setOnClickListener(this);
        ima_promote3.setOnClickListener(this);

        //倒计时
        mClockView = (RushBuyCountDownTimerView) view.findViewById(R.id.rushBuyCountDownTimerView);
//        mClockView.setTime(0, 0, 10);
//        mClockView.start();
//        //倒计时停止的回调接口
//        mClockView.setOnStopListener(new RushBuyCountDownTimerView.OnStopListener() {
//            @Override
//            public void onStoped() {
//                mClockView.setTime(0,0,10);
//                mClockView.start();
//            }
//        });

        ima_new1 = (SimpleDraweeView) view.findViewById(R.id.ima_new1);
        ima_new2 = (SimpleDraweeView) view.findViewById(R.id.ima_new2);
        ima_new3 = (SimpleDraweeView) view.findViewById(R.id.ima_new3);
        ima_new4 = (SimpleDraweeView) view.findViewById(R.id.ima_new4);
        ima_new1.setOnClickListener(this);
        ima_new2.setOnClickListener(this);
        ima_new3.setOnClickListener(this);
        ima_new4.setOnClickListener(this);

        gv1 = (GridView4ScrollView) view.findViewById(R.id.gv1);
        gv2 = (GridView4ScrollView) view.findViewById(R.id.gv2);
        gv3 = (GridView4ScrollView) view.findViewById(R.id.gv3);
        gv4 = (GridView4ScrollView) view.findViewById(R.id.gv4);
        gv1Adapter = new Gv1Adapter(1);
        gv1.setAdapter(gv1Adapter);
        gv2Adapter = new Gv1Adapter(2);
        gv2.setAdapter(gv2Adapter);
        gv3Adapter = new Gv1Adapter(3);
        gv3.setAdapter(gv3Adapter);
        gv4Adapter = new Gv1Adapter(4);
        gv4.setAdapter(gv4Adapter);

        gv1.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 < list1.size()) {
                    Intent intent = new Intent(getActivity(), NewDetailActivity.class);
                    intent.putExtra("goods_id", list1.get(arg2).getGoodsId());
                    startActivity(intent);
                }
            }
        });
        gv2.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 < list2.size()) {
                    Intent intent = new Intent(getActivity(), NewDetailActivity.class);
                    intent.putExtra("goods_id", list2.get(arg2).getGoodsId());
                    startActivity(intent);
                }
            }
        });
        gv3.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 < list3.size()) {
                    Intent intent = new Intent(getActivity(), NewDetailActivity.class);
                    intent.putExtra("goods_id", list3.get(arg2).getGoodsId());
                    startActivity(intent);
                }
            }
        });
        gv4.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (arg2 < list4.size()) {
                    Intent intent = new Intent(getActivity(), NewDetailActivity.class);
                    intent.putExtra("goods_id", list4.get(arg2).getGoodsId());
                    startActivity(intent);
                }
            }
        });

        view.findViewById(R.id.rela_baritem_homefra4).setOnClickListener(this);
        rela_baritem_homefra2 = (RelativeLayout) view.findViewById(R.id.rela_baritem_homefra2);
        rela_baritem_homefra2.setOnClickListener(this);
        view.findViewById(R.id.rela_baritem_homefra5).setOnClickListener(this);
        view.findViewById(R.id.rela_baritem_homefra1).setOnClickListener(this);
        view.findViewById(R.id.rela_grape).setOnClickListener(this);
        indicator = (ViewPagerIndicator2) view.findViewById(R.id.home_headertype_pagerindicator);
        rela_top_search_homefra = (RelativeLayout) view.findViewById(R.id.rela_top_search_homefra);

        View pop_brand = inflater.inflate(R.layout.pop_brand_homefra, null);
        pop_brand.findViewById(R.id.rela_popbrand_packup).setOnClickListener(this);
        popupWindow = new PopupWindow(pop_brand, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
        BitmapDrawable b = new BitmapDrawable();
        popupWindow.setBackgroundDrawable(b);
        popupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                editLine = 0;
            }
        });

        lv_popbrand = (ListView) pop_brand.findViewById(R.id.lv_popbrand);
        lvPopAdapter = new LvPopAdapter();
        lv_popbrand.setAdapter(lvPopAdapter);
        lv_popbrand.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                editLine = arg2;
                lvPopAdapter.notifyDataSetChanged();
                List<Child3> child = brandList.get(arg2).getChild();
                brandDetailList.clear();
                brandDetailList.addAll(child);
                gvPopAdapter.notifyDataSetChanged();
            }
        });

        gv_popbrand = (GridView) pop_brand.findViewById(R.id.gv_popbrand);
        gvPopAdapter = new GvPopAdapter();
        gv_popbrand.setAdapter(gvPopAdapter);

        View pop_location = inflater.inflate(R.layout.popo_location, null);
        pop_location.findViewById(R.id.confirm_poplocation).setOnClickListener(this);
        pop_location.findViewById(R.id.rela_popbrand_packup_homefra).setOnClickListener(this);
        pop_location.findViewById(R.id.empty_poplocation).setOnClickListener(this);
        editText1 = (TextView) pop_location.findViewById(R.id.editText1);
        editText2 = (TextView) pop_location.findViewById(R.id.editText2);
        editText3 = (TextView) pop_location.findViewById(R.id.editText3);
        editText4 = (TextView) pop_location.findViewById(R.id.editText4);
        editText1.setOnClickListener(this);
        editText2.setOnClickListener(this);
        editText3.setOnClickListener(this);
        editText4.setOnClickListener(this);

        popupWindow_location = new PopupWindow(pop_location, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
                true);
        BitmapDrawable b2 = new BitmapDrawable();
        popupWindow_location.setBackgroundDrawable(b2);
        popupWindow_location.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
            }
        });

        // 地区特供
        rela_loacation = (RelativeLayout) view.findViewById(R.id.rela_loacation);

        gv_location = (GridView) view.findViewById(R.id.gridView1);
        gvAdapter = new GvAdapter();
        gv_location.setAdapter(gvAdapter);

        // initData();
        view.findViewById(R.id.text_search).setOnClickListener(this);
        edit_search = (EditText) view.findViewById(R.id.edit_search);

        textView7 = (TextView) view.findViewById(R.id.textView7);
        textView_nothing = (TextView) view.findViewById(R.id.textView_nothing);
    }

    class GvAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return searchList.size();
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
            TextView text_name_25_homefra;
            TextView text_price_25_homefra;
            SquareImageView ima_25_homefra;
            if (convertView == null) {
                view = inflater.inflate(R.layout.item_gv_location, null);
                ima_25_homefra = (SquareImageView) view.findViewById(R.id.ima_25_homefra);
                text_name_25_homefra = (TextView) view.findViewById(R.id.text_name_25_homefra);
                text_price_25_homefra = (TextView) view.findViewById(R.id.text_price_25_homefra);
                view.setTag(new MyTag2(text_name_25_homefra, text_price_25_homefra, ima_25_homefra));
            } else {
                view = convertView;
                MyTag2 tag = (MyTag2) view.getTag();
                text_name_25_homefra = tag.text_name_25_homefra;
                text_price_25_homefra = tag.text_price_25_homefra;
                ima_25_homefra = tag.ima_25_homefra;
            }

            Datum5 datum5 = searchList.get(position);
            x.image().bind(ima_25_homefra, datum5.getOriginalImg(), imageOptions);
            text_name_25_homefra.setText(datum5.getGoodsName());
            text_name_25_homefra.setText(datum5.getGoodsName());
            text_price_25_homefra.setText(datum5.getShopPrice());

            final String goodsId = datum5.getGoodsId();
            ima_25_homefra.setOnClickListener(new OnClickListener() {

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

    class PagerBannerAdapter extends FragmentPagerAdapter {
        public PagerBannerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            if (adList.size() == 0) {
                return new BannerItemFragment2("", "");
            } else if (adList.size() == 1) {
                String adCode = adList.get(0).getAdCode();
                return new BannerItemFragment2(adCode, adList.get(0).getAdLink());
            } else {
                position %= adList.size();
                return new BannerItemFragment2(adList.get(position).getAdCode(), adList.get(position).getAdLink());
            }
        }

        @Override
        public int getCount() {
            return BANNER_COUNT;
        }
    }

    class MyTag2 {
        TextView text_name_25_homefra;
        TextView text_price_25_homefra;
        SquareImageView ima_25_homefra;

        public MyTag2(TextView text_name_25_homefra, TextView text_price_25_homefra, SquareImageView ima_25_homefra) {
            super();
            this.text_name_25_homefra = text_name_25_homefra;
            this.text_price_25_homefra = text_price_25_homefra;
            this.ima_25_homefra = ima_25_homefra;
        }

    }

    class Gv1Adapter extends BaseAdapter {

        int num;

        public Gv1Adapter(int num) {
            super();
            this.num = num;
        }

        @Override
        public int getCount() {
            return 6;
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
            TextView text_price;
            SimpleDraweeView imageview1;
            if (convertView == null) {
                view = inflater.inflate(R.layout.item_gv_home, null);
                imageview1 = (SimpleDraweeView) view.findViewById(R.id.imageView1);
                text_title = (TextView) view.findViewById(R.id.text_title);
                text_price = (TextView) view.findViewById(R.id.text_price);
                view.setTag(new MyTag(text_title, text_price, imageview1));
            } else {
                view = convertView;
                MyTag tag = (MyTag) view.getTag();
                imageview1 = tag.imageview1;
                text_title = tag.text_title;
                text_price = tag.text_price;
            }

            int position1 = position;
            if (list1.size() != 0) {
                if (num == 1) {
                    if (position < list1.size()) {
                        _1F _1f = list1.get(position);
                        YazhiUtils.setImage(_1f.getOriginalImg(), imageview1);
//                        x.image().bind(imageview1, _1f.getOriginalImg(), imageOptions);
                        // UILUtils.displayImageNoAnim(_1f.getOriginalImg(),
                        // imageview1);
                        text_title.setText(_1f.getGoodsName());
                        text_price.setText("￥" + _1f.getShopPrice());
                    }
                }
            }

            if (list2.size() != 0) {
                if (num == 2) {
                    if (position < list2.size()) {
                        _2F _2f = list2.get(position);
                        YazhiUtils.setImage(_2f.getOriginalImg(), imageview1);
//                        x.image().bind(imageview1, _2f.getOriginalImg(), imageOptions);
                        // UILUtils.displayImageNoAnim(_2f.getOriginalImg(),
                        // imageview1);
                        text_title.setText(_2f.getGoodsName());
                        text_price.setText("￥" + _2f.getShopPrice());

                    }
                }
            }
            if (list3.size() != 0) {
                if (num == 3) {
                    if (position < list3.size()) {
                        _3F _3f = list3.get(position);
                        YazhiUtils.setImage(_3f.getOriginalImg(), imageview1);
//                        x.image().bind(imageview1, _3f.getOriginalImg(), imageOptions);
                        // UILUtils.displayImageNoAnim(_3f.getOriginalImg(),
                        // imageview1);
                        text_title.setText(_3f.getGoodsName());
                        text_price.setText("￥" + _3f.getShopPrice());

                    }
                }
            }
            if (list4.size() != 0) {
                if (num == 4) {
                    if (position < list4.size()) {
                        _4F _4f = list4.get(position);
                        YazhiUtils.setImage(_4f.getOriginalImg(), imageview1);
//                        x.image().bind(imageview1, _4f.getOriginalImg(), imageOptions);
                        // UILUtils.displayImageNoAnim(_4f.getOriginalImg(),
                        // imageview1);
                        text_title.setText(_4f.getGoodsName());
                        text_price.setText("￥" + _4f.getShopPrice());

                    }
                }
            }

            return view;
        }

    }

    class MyTag {
        TextView text_title;
        TextView text_price;
        SimpleDraweeView imageview1;

        public MyTag(TextView text_title, TextView text_price, SimpleDraweeView imageview1) {
            super();
            this.text_title = text_title;
            this.text_price = text_price;
            this.imageview1 = imageview1;
        }

    }

    private void addDynamicView(int num) {
        imageList.clear();
        // 动态空白banne图片
        // 初始化图片资源
        for (int i = 0; i < num; i++) {
            ImageView imageView = new ImageView(getActivity());
            // 异步加载图片
            imageView.setScaleType(ScaleType.CENTER_CROP);
            imageList.add(imageView);
        }

        // myAdapter.notifyDataSetChanged();
    }

    private void initData() {
        // banner图片
        String url = Content.URL.PHOTOBANNER; // 地址
        YazhiHttp yazhiHttp = new YazhiHttp(url);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                HomeBanner homebanner = GsonUtils.parseJSON(arg0, HomeBanner.class);
                Data data = homebanner.getData();
                List<Player> player = data.getPlayer();
                imaList.addAll(player);
                Intent intent = new Intent();
                intent.setAction("banner");
                getActivity().sendBroadcast(intent);
            }

            @Override
            public void onError() {

            }
        });

        // 全部商品
        String url_allproduct = Content.URL.ALLPRODUCT;
        YazhiHttp yazhiHttp1 = new YazhiHttp(url_allproduct);
        yazhiHttp1.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                AllProduct allProduct = GsonUtils.parseJSON(arg0, AllProduct.class);
                com.xmzlb.homemodel.Data data = allProduct.getData();
                List<Hot> hot = data.getHot();
            }

            @Override
            public void onError() {

            }
        });
    }

    class LvPopAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return brandList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @SuppressLint("NewApi")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            SquareImageView ima_item_lvpopbrand_homefra;
            TextView text_name;
            if (convertView == null) {
                view = inflater.inflate(R.layout.item_lv_popbrand_homefra, null);
                text_name = (TextView) view.findViewById(R.id.text_name);
                ima_item_lvpopbrand_homefra = (SquareImageView) view.findViewById(R.id.ima_item_lvpopbrand_homefra);
                view.setTag(new MyBrandTag(ima_item_lvpopbrand_homefra, text_name));
            } else {
                view = convertView;
                MyBrandTag tag = (MyBrandTag) view.getTag();
                ima_item_lvpopbrand_homefra = tag.ima_item_lvpopbrand_homefra;
                text_name = tag.text_name;
            }
            Datum14 datum14 = brandList.get(position);
            text_name.setText(datum14.getCatName());
            switch (position) {
                case 0:
                    ima_item_lvpopbrand_homefra.setImageDrawable(getResources().getDrawable(R.drawable.home1));
                    break;
                case 1:
                    ima_item_lvpopbrand_homefra.setImageDrawable(getResources().getDrawable(R.drawable.home2));
                    break;
                case 2:
                    ima_item_lvpopbrand_homefra.setImageDrawable(getResources().getDrawable(R.drawable.home3));
                    break;
                case 3:
                    ima_item_lvpopbrand_homefra.setImageDrawable(getResources().getDrawable(R.drawable.home4));
                    break;
                case 4:
                    ima_item_lvpopbrand_homefra.setImageDrawable(getResources().getDrawable(R.drawable.delete31));
                    break;
                case 5:
                    ima_item_lvpopbrand_homefra.setImageDrawable(getResources().getDrawable(R.drawable.delete41));
                    break;
                case 6:
                    ima_item_lvpopbrand_homefra.setImageDrawable(getResources().getDrawable(R.drawable.delete51));
                    break;
                case 7:
                    ima_item_lvpopbrand_homefra.setImageDrawable(getResources().getDrawable(R.drawable.delete61));
                    break;

                default:
                    break;
            }
            if (position == editLine) {
                view.setBackground(getResources().getDrawable(R.drawable.halflucency3));
            } else {
                view.setBackground(getResources().getDrawable(R.drawable.halflucency2));
            }
            return view;
        }

    }

    class MyBrandTag {
        SquareImageView ima_item_lvpopbrand_homefra;
        TextView text_name;

        public MyBrandTag(SquareImageView ima_item_lvpopbrand_homefra, TextView text_name) {
            super();
            this.ima_item_lvpopbrand_homefra = ima_item_lvpopbrand_homefra;
            this.text_name = text_name;
        }

    }

    class GvPopAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return brandDetailList.size();
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
            SimpleDraweeView ima_item_gvpopbrand_homefra;
            TextView text_name;

            if (convertView == null) {
                view = inflater.inflate(R.layout.item_gv_popbrand_homefra, null);
                ima_item_gvpopbrand_homefra = (SimpleDraweeView) view.findViewById(R.id.ima_item_gvpopbrand_homefra);
                text_name = (TextView) view.findViewById(R.id.text_name);
                view.setTag(new MyGvTag(ima_item_gvpopbrand_homefra, text_name));
            } else {
                view = convertView;
                MyGvTag tag = (MyGvTag) view.getTag();
                ima_item_gvpopbrand_homefra = tag.ima_item_gvpopbrand_homefra;
                text_name = tag.text_name;
            }
            Child3 child3 = brandDetailList.get(position);
            text_name.setText(child3.getBrandName());
            YazhiUtils.setImage(child3.getBrandLogo(), ima_item_gvpopbrand_homefra);

            final String brandId = child3.getBrandId();
            ima_item_gvpopbrand_homefra.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent2 = new Intent(getActivity(), AllProductActivity.class);
                    intent2.putExtra("category", position + 1 + "");
                    intent2.putExtra("brand", brandId);
                    startActivity(intent2);
//                    popupWindow.dismiss();
                }
            });
            return view;
        }
    }

    class MyGvTag {
        SimpleDraweeView ima_item_gvpopbrand_homefra;
        TextView text_name;

        public MyGvTag(SimpleDraweeView ima_item_gvpopbrand_homefra, TextView text_name) {
            super();
            this.ima_item_gvpopbrand_homefra = ima_item_gvpopbrand_homefra;
            this.text_name = text_name;
        }
    }

    class MyPageChangeListener implements OnPageChangeListener {
        public void onPageScrollStateChanged(int state) {
            switch (state) {
                case ViewPager.SCROLL_STATE_DRAGGING:
                    isDrag = true;
                    break;
                case ViewPager.SCROLL_STATE_IDLE:
                    isDrag = false;
                    break;
                case ViewPager.SCROLL_STATE_SETTLING:
                    isDrag = false;
                    break;
                default:
                    break;
            }
        }

        public void onPageScrolled(int position, float arg1, int arg2) {
            indicator.move(arg1, position %= ima_size);
        }

        public void onPageSelected(int position) {
        }
    }

    private void autoScroll() {
        // 自动滚动Pager
        mHandler.postDelayed(new Runnable() {
            public void run() {
                int item = pagerBanner.getCurrentItem();
                // 设置自动滚动，可以设置是否有动画
                if (!isDrag && (imageList.size() != 0)) {
                    // 设置ViewPager滑动
                    if (finish) {
                        return;
                    }
                    pagerBanner.setCurrentItem(item + 1);
                }
                mHandler.postDelayed(this, 5000);
            }
        }, 5000);
    }

    @Override
    public void onClick(View v) {
        Intent intent2 = new Intent(getActivity(), AllProductActivity.class);
        switch (v.getId()) {
            case R.id.editText1: // 地区特供省
                mode = 1;
                final List<Child2> child = location.getChild();
                shen = new String[child.size()];
                for (int i = 0; i < child.size(); i++) { // 遍历每个省
                    shen[i] = child.get(i).getCatName();
                }

                View view = dialogm();
                final MyAlertDialog dialog1 = new MyAlertDialog(getActivity()).builder().setTitle("修改地区").setView(view)
                        .setNegativeButton("取消", new OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                dialog1.setPositiveButton("保存", new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (child.size() != 0) {
                            if (child.size() == 1) {
                                editText1.setText(child.get(0).getCatName());
                                position1 = 0;
                                id1 = child.get(0).getCatId();
                            } else {
                                editText1.setText(province);
                                position1 = editPosition1;
                                id1 = child.get(position1).getCatId();
                            }
                        }

                        // 如果重选了省，则市，区都要清空
                        position2 = -1;
                        position3 = -1;
                        id2 = "empty";
                        id3 = "empty";
                        id4 = "empty";
                        editText2.setText("请选择");
                        editText3.setText("请选择");
                        editText4.setText("请选择");
                    }
                });
                dialog1.show();
                break;

            case R.id.editText2: // 选择市
                if (position1 == -1) { // 还没选择省
                    Toast.makeText(getActivity(), "请先选择省份！", Toast.LENGTH_SHORT).show();
                } else {
                    mode = 2;
                    final List<Child2> child1 = location.getChild().get(position1).getChild();
                    shen = new String[child1.size()];
                    for (int i = 0; i < child1.size(); i++) { // 遍历选中省的每个市
                        shen[i] = child1.get(i).getCatName();
                    }

                    View view2 = dialogm();
                    final MyAlertDialog dialog2 = new MyAlertDialog(getActivity()).builder().setTitle("修改地区").setView(view2)
                            .setNegativeButton("取消", new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                    dialog2.setPositiveButton("保存", new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (child1.size() != 0) {
                                if (child1.size() == 1) {
                                    editText2.setText(child1.get(0).getCatName());
                                    position2 = 0;
                                    id2 = child1.get(0).getCatId();
                                } else {
                                    editText2.setText(cityStr);
                                    position2 = editPosition2;
                                    id2 = child1.get(position2).getCatId();
                                }
                            }

                            // 选择了市，则要重新选区
                            position3 = -1;
                            id3 = "empty";
                            id4 = "empty";
                            editText3.setText("请选择");
                            editText4.setText("请选择");
                        }
                    });
                    dialog2.show();
                }
                break;
            case R.id.editText3: // 选择区
                if (position2 == -1) { // 还没选择区
                    Toast.makeText(getActivity(), "请先选择所在市！", Toast.LENGTH_SHORT).show();
                } else {
                    mode = 3;
                    final List<Child2> child2 = location.getChild().get(position1).getChild().get(position2).getChild();
                    shen = new String[child2.size()];
                    for (int i = 0; i < child2.size(); i++) { // 遍历选中省的每个市
                        shen[i] = child2.get(i).getCatName();
                    }

                    View view3 = dialogm();
                    final MyAlertDialog dialog3 = new MyAlertDialog(getActivity()).builder().setTitle("修改现居地")
                            .setView(view3).setNegativeButton("取消", new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                    dialog3.setPositiveButton("保存", new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (child2.size() != 0) {
                                if (child2.size() == 1) {
                                    editText3.setText(child2.get(0).getCatName());
                                    position3 = 0;
                                    id3 = child2.get(0).getCatId();
                                } else {
                                    editText3.setText(countyStr);
                                    position3 = editPosition3;
                                    String x = child2.get(position3).getCatId();
                                    id3 = child2.get(position3).getCatId();
                                }
                            }

                            // 选择了区，则要重新选镇
                            id4 = "empty";
                            editText4.setText("请选择");
                        }
                    });
                    dialog3.show();
                }
                break;
            case R.id.editText4: // 选择镇
                if (position3 == -1) { // 还没选择区
                    Toast.makeText(getActivity(), "请先选择所在区！", Toast.LENGTH_SHORT).show();
                } else {
                    mode = 4;
                    final List<Child2> child3 = location.getChild().get(position1).getChild().get(position2).getChild()
                            .get(position3).getChild();
                    shen = new String[child3.size()];
                    for (int i = 0; i < child3.size(); i++) { // 遍历选中省的每个市
                        shen[i] = child3.get(i).getCatName();
                    }

                    View view3 = dialogm();
                    final MyAlertDialog dialog3 = new MyAlertDialog(getActivity()).builder().setTitle("修改现居地")
                            .setView(view3).setNegativeButton("取消", new OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                    dialog3.setPositiveButton("保存", new OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if (child3.size() != 0) {
                                if (child3.size() == 1) {
                                    editText4.setText(child3.get(0).getCatName());
                                    position4 = 0;
                                    id4 = child3.get(0).getCatId();
                                } else {
                                    editText4.setText(zhenStr);
                                    position4 = editPosition4;
                                    id4 = child3.get(position4).getCatId();
                                }
                            }
                        }
                    });
                    dialog3.show();
                }
                break;
            case R.id.ima_time:
                Intent intent = new Intent(getActivity(), NewDetailActivity.class);
                intent.putExtra("goods_id", ima_time1);
                startActivity(intent);
                break;
            case R.id.ima_promote1:
                Intent ima_promote1 = new Intent(getActivity(), NewDetailActivity.class);
                ima_promote1.putExtra("goods_id", ima_promote11);
                startActivity(ima_promote1);
                break;
            case R.id.ima_promote2:
                Intent ima_promote2 = new Intent(getActivity(), NewDetailActivity.class);
                ima_promote2.putExtra("goods_id", ima_promote21);
                startActivity(ima_promote2);
                break;
            case R.id.ima_promote3:
                Intent ima_promote3 = new Intent(getActivity(), NewDetailActivity.class);
                ima_promote3.putExtra("goods_id", ima_promote31);
                startActivity(ima_promote3);
                break;
            case R.id.ima_new1:
                Intent ima_new1 = new Intent(getActivity(), NewDetailActivity.class);
                ima_new1.putExtra("goods_id", ima_new11);
                startActivity(ima_new1);
                break;
            case R.id.ima_new2:
                Intent ima_new2 = new Intent(getActivity(), NewDetailActivity.class);
                ima_new2.putExtra("goods_id", ima_new21);
                startActivity(ima_new2);
                break;
            case R.id.ima_new3:
                Intent ima_new3 = new Intent(getActivity(), NewDetailActivity.class);
                ima_new3.putExtra("goods_id", ima_new31);
                startActivity(ima_new3);
                break;
            case R.id.ima_new4:
                Intent ima_new4 = new Intent(getActivity(), NewDetailActivity.class);
                ima_new4.putExtra("goods_id", ima_new41);
                startActivity(ima_new4);
                break;
            case R.id.confirm_poplocation: // 地区特供弹窗选择地区后点确定
                if (id1.equals("empty")) { // 没选择省
                    Toast.makeText(getActivity(), "请先选择所在省份！", Toast.LENGTH_SHORT).show();
                    return;
                }
                mode = 1;
                rela_top_search_homefra.setVisibility(View.GONE);
                mPullToRefreshScrollView.setVisibility(View.GONE);
                rela_loacation.setVisibility(View.VISIBLE);
                textView7.setVisibility(View.VISIBLE);
                popupWindow_location.dismiss();
                rela_baritem_homefra2.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_selected));

                textView_nothing.setVisibility(View.GONE);
                locationSpacial();
                break;
            case R.id.rela_baritem_homefra1: // 全部商品
                textView_nothing.setVisibility(View.GONE);
                if (mode == 1) { // 正在展示地区特供状态
                    rela_top_search_homefra.setVisibility(View.VISIBLE);
                    mPullToRefreshScrollView.setVisibility(View.VISIBLE);
                    rela_loacation.setVisibility(View.GONE);
                    textView7.setVisibility(View.GONE);
                    rela_baritem_homefra2.setBackgroundDrawable(getResources().getDrawable(R.drawable.tab_bg));
                    mode = 0;
                } else {
                    startActivity(new Intent(getActivity(), AllProductActivity.class));
                }
                break;
            case R.id.rela_baritem_homefra4: // 品牌导航
                initBrand();
                break;
            case R.id.more1: // 1F更多
                initMore(1);
                break;
            case R.id.more2: // 2F更多
                initMore(4);
                break;
            case R.id.more3: // 3F更多
                initMore(2);
                break;
            case R.id.more4: // 4F更多
                initMore(5);

                break;
            case R.id.rela_popbrand_packup: // 点击pop收起
                popupWindow.dismiss();
                break;
            case R.id.rela_baritem_homefra2: // 地区特供
                textView_nothing.setVisibility(View.GONE);
                initLocationSpecial();
                if (mode == 0) {
                    // 搜索栏目存在
                    popupWindow_location.showAsDropDown(rela_top_search_homefra);
                } else {
                    popupWindow_location.showAsDropDown(bar);
                }
                break;
            case R.id.rela_popbrand_packup_homefra: // 点击pop收起
            case R.id.empty_poplocation: // poplocation空白
                popupWindow_location.dismiss();
                break;
            case R.id.rela_baritem_homefra5: // 用户中心
                String spLoginState = GlobalVariable.getInstance().getSpLoginState();
                if (spLoginState.equals("0")) {
                    startActivity(new Intent(getActivity(), UserActivity.class));
                } else {
                    startActivity(new Intent(getActivity(), SellerActivity.class));
                }

                break;
            case R.id.rela_grape: // 点击葡萄酒
                intent2.putExtra("category", "1");
                startActivity(intent2);
                break;
            case R.id.rela_ten: // 点击白酒
                intent2.putExtra("category", "2");
                startActivity(intent2);
                break;
            case R.id.rela_hundred: // 点击啤酒
                intent2.putExtra("category", "3");
                startActivity(intent2);
                break;
            case R.id.rela_share: // 点击饮料
                intent2.putExtra("category", "4");
                startActivity(intent2);
                break;
            case R.id.rela_foreignwine: // 点击洋酒
                intent2.putExtra("category", "5");
                startActivity(intent2);
                break;
            case R.id.rela_otherwine: // 点击其他酒
                intent2.putExtra("category", "6");
                startActivity(intent2);
                break;
            case R.id.rela_milk: // 点击牛奶
                intent2.putExtra("category", "7");
                startActivity(intent2);
                break;
            case R.id.rela_food: // 点击其他
                intent2.putExtra("category", "8");
                startActivity(intent2);
                break;
            case R.id.text_search: // 搜索
                textView_nothing.setVisibility(View.GONE);

                String text_search = edit_search.getText().toString();
                if (text_search.isEmpty()) {
                    edit_search.setError("关键词不能为空");
                    return;
                }

                String url = GlobalVariable.URL.SEARCH;
                YazhiHttp yazhiHttp = new YazhiHttp(getActivity(), url);
                yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
                yazhiHttp.addParams("keywords", text_search);
                yazhiHttp.post(new YazhiHttp.MyListener() {
                    @Override
                    public void onSuccess(String arg0) {
                        Search parseJSON = GsonUtils.parseJSON(arg0, Search.class);
                        com.xmzlb.model.Status status = parseJSON.getStatus();
                        if (status.getSucceed() == 1) {
                            List<Datum5> data = parseJSON.getData();
                            searchList.clear();
                            searchList.addAll(data);
                            gvAdapter.notifyDataSetChanged();
                            if (data.size() == 0) {
                                textView_nothing.setVisibility(View.VISIBLE);
                            }
                            // 隐藏首页，显示搜索/地区单独的gridview
                            mode = 1;
                            rela_top_search_homefra.setVisibility(View.GONE);
                            mPullToRefreshScrollView.setVisibility(View.GONE);
                            rela_loacation.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(getActivity(), status.getError_desc(), Toast.LENGTH_SHORT).show();
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

    private void locationSpacial() {
        textView7.setVisibility(view.VISIBLE);
        String url = GlobalVariable.URL.ALLPRODUCT;
        YazhiHttp yazhiHttp = new YazhiHttp(getActivity(),url);
        yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
        String cateId = "";
        if (id2.equals("empty")) {
            cateId = id1;
        } else {
            if (id3.equals("empty")) {
                cateId = id2;
            } else {
                if (id4.equals("empty")) {
                    cateId = id3;
                } else {
                    cateId = id4;
                }
            }
        }
        yazhiHttp.addParams("category", cateId);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Allproduct parseJSON = GsonUtils.parseJSON(arg0, Allproduct.class);
                com.xmzlb.model.Status status = parseJSON.getStatus();
                if (status.getSucceed() == 1) {
                    List<Datum5> data = parseJSON.getData();
                    searchList.clear();
                    searchList.addAll(data);
                    gvAdapter.notifyDataSetChanged();
                    if (data.size() == 0) {
                        textView_nothing.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (status.getSucceed() == 2) {
                        Intent intentLogIn = new Intent(getActivity(), UserLogInActivity.class);
//						intentLogIn.putExtra("from", 0);
//						intentLogIn.putExtra("fromStatus", 0);
                        intentLogIn.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentLogIn);
                    }
                }
            }

            @Override
            public void onError() {

            }
        });

    }

    private void initMore(final int num) {
        String url = GlobalVariable.URL.BRAND;
        YazhiHttp yazhiHttp = new YazhiHttp(url);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Brand parseJSON = GsonUtils.parseJSON(arg0, Brand.class);
                com.xmzlb.model.Status status = parseJSON.getStatus();
                if (status.getSucceed() == 1) {
                    List<Datum14> data = parseJSON.getData();
                    List<Child3> child = data.get(0).getChild();
                    brandList.clear();
                    brandList.addAll(data);
                    editLine = num;
                    lv_popbrand.smoothScrollToPosition(editLine);
                    lvPopAdapter.notifyDataSetChanged();
                    List<Child3> child2 = brandList.get(num).getChild();
                    brandDetailList.clear();
                    brandDetailList.addAll(child2);
                    gvPopAdapter.notifyDataSetChanged();
                    popupWindow.showAsDropDown(rela_top_search_homefra);
                } else {
                    Toast.makeText(getActivity(), status.getError_desc(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private void initBrand() {
        String url = GlobalVariable.URL.BRAND;
        YazhiHttp yazhiHttp = new YazhiHttp(url);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Brand parseJSON = GsonUtils.parseJSON(arg0, Brand.class);
                com.xmzlb.model.Status status = parseJSON.getStatus();
                if (status.getSucceed() == 1) {
                    List<Datum14> data = parseJSON.getData();
                    List<Child3> child = data.get(0).getChild();
                    brandList.clear();
                    brandList.addAll(data);
                    brandDetailList.clear();
                    brandDetailList.addAll(child);
                    lvPopAdapter.notifyDataSetChanged();
                    gvPopAdapter.notifyDataSetChanged();
                    popupWindow.showAsDropDown(rela_top_search_homefra);
                } else {
                    Toast.makeText(getActivity(), status.getError_desc(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError() {

            }
        });
    }

    private View dialogm() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.wheelcity_cities_layout, null);
        final WheelView country = (WheelView) contentView.findViewById(R.id.wheelcity_country);
        country.setVisibleItems(3);
        country.setViewAdapter(new CountryAdapter(getActivity()));

        final String cities[][] = AddressData.CITIES;
        final String ccities[][][] = AddressData.COUNTIES;
        final WheelView city = (WheelView) contentView.findViewById(R.id.wheelcity_city);
        city.setVisibleItems(0);

        // 地区选择
        final WheelView ccity = (WheelView) contentView.findViewById(R.id.wheelcity_ccity);
        ccity.setVisibleItems(0);// 不限城市

        country.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updateCities(city, cities, newValue);
                switch (mode) {
                    case 1:
                        editPosition1 = country.getCurrentItem();
                        province = shen[country.getCurrentItem()];
                        break;
                    case 2:
                        editPosition2 = country.getCurrentItem();
                        cityStr = shen[country.getCurrentItem()];
                        break;
                    case 3:
                        editPosition3 = country.getCurrentItem();
                        countyStr = shen[country.getCurrentItem()];
                        break;
                    case 4:
                        editPosition4 = country.getCurrentItem();
                        zhenStr = shen[country.getCurrentItem()];
                        break;

                    default:
                        break;
                }
            }
        });

        city.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
                updatecCities(ccity, ccities, country.getCurrentItem(), newValue);
            }
        });

        ccity.addChangingListener(new OnWheelChangedListener() {
            public void onChanged(WheelView wheel, int oldValue, int newValue) {
            }
        });

        country.setCurrentItem(1);// 设置北京
        city.setCurrentItem(1);
        ccity.setCurrentItem(1);
        return contentView;
    }

    public class CountryAdapter extends AbstractWheelTextAdapter {
        // Countries names
        // private String countries[] = AddressData.PROVINCES;
        private String countries[] = shen;

        /**
         * Constructor
         */
        protected CountryAdapter(Context context) {
            super(context, R.layout.wheelcity_country_layout, NO_RESOURCE);
            setItemTextResource(R.id.wheelcity_country_name);
        }

        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            View view = super.getItem(index, cachedView, parent);
            return view;
        }

        @Override
        public int getItemsCount() {
            return countries.length;
        }

        @Override
        protected CharSequence getItemText(int index) {
            return countries[index];
        }
    }

    private void updateCities(WheelView city, String cities[][], int index) {
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(getActivity(), cities[index]);
        adapter.setTextSize(18);
        city.setViewAdapter(adapter);
        city.setCurrentItem(0);
    }

    private void updatecCities(WheelView city, String ccities[][][], int index, int index2) {
        ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(getActivity(), ccities[index][index2]);
        adapter.setTextSize(18);
        city.setViewAdapter(adapter);
        city.setCurrentItem(0);
    }

}
