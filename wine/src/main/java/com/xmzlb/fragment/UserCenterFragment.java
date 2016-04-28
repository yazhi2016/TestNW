package com.xmzlb.fragment;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RadioButton;

import com.xmzlb.model.Guide;
import com.xmzlb.model.Status;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.wine.MainActivity;
import com.xmzlb.wine.R;
import com.xmzlb.wine.UserActivity;
import com.xmzlb.zyzutil.YazhiHttp;
import com.xmzlb.zyzutil.YazhiUtils;
import com.xinbo.utils.GsonUtils;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * 首页底栏 商场指南
 */
public class UserCenterFragment extends Fragment implements OnClickListener {

    private View view;
    LayoutInflater inflater;
    String type = "0"; // 0注册帮助，1下单帮助，2,会员积分，3物流配送
    private RadioButton radio0;
    private ImageView imageView3;
    private PopupWindow popupWindowMenu; // 菜单弹窗
    private View bar;
    private View popMenu;
    ImageOptions imageOptions;

    public UserCenterFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            this.inflater = inflater;
            view = inflater.inflate(R.layout.fragment_user_center, container, false);

            imageOptions = new ImageOptions.Builder()
                    // .setRadius(DensityUtil.dip2px(5))
                    // 如果ImageView的大小不是定义为wrap_content, 不要crop.
                    .setCrop(true) // 很多时候设置了合适的scaleType也不需要它.
                    // 加载中或错误图片的ScaleType
                    // .setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                    .setImageScaleType(ImageView.ScaleType.CENTER_CROP).setLoadingDrawableId(R.drawable.ic_stub)
                    .setFailureDrawableId(R.drawable.ic_error)
                    // 设置使用缓存
                    .setUseMemCache(false).build();

            imageView3 = (ImageView) view.findViewById(R.id.imageView3);
            initData(type);
            radio0 = (RadioButton) view.findViewById(R.id.radio0);

            bar = view.findViewById(R.id.bar);
            // 菜单
            popMenu = inflater.inflate(R.layout.include_menu, null);
            popupWindowMenu = new PopupWindow(popMenu, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, true);
            // 设置点击窗体以外区域关闭窗体
            BitmapDrawable b = new BitmapDrawable();
            popupWindowMenu.setBackgroundDrawable(b);
            popupWindowMenu.setOnDismissListener(new OnDismissListener() {

                @Override
                public void onDismiss() {

                }
            });

            initEvent();
        }
        return view;
    }

    private void initEvent() {
        radio0.setOnClickListener(this);
        view.findViewById(R.id.radio1).setOnClickListener(this);
        view.findViewById(R.id.radio2).setOnClickListener(this);
        view.findViewById(R.id.radio3).setOnClickListener(this);
        view.findViewById(R.id.back).setOnClickListener(this);
        view.findViewById(R.id.back).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.menu).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_menu1).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_menu2).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_menu3).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_menu4).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_empty_meun).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.radio0: // 注册帮助
                type = "0";
                break;
            case R.id.radio1:
                type = "1";
                break;
            case R.id.radio2:
                type = "2";
                break;
            case R.id.radio3:
                type = "3";
                break;
            case R.id.back:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("tabhost", 0);
                startActivity(intent);
                break;
            case R.id.menu:
                popupWindowMenu.showAsDropDown(bar);
                break;
            case R.id.rela_empty_meun:
                popupWindowMenu.dismiss();
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
                Intent intent3 = new Intent(getActivity(), MainActivity.class);
                intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent3.putExtra("tabhost", 2);
                startActivity(intent3);
                break;
            case R.id.rela_menu4:
                popupWindowMenu.dismiss();
                Intent intent4 = new Intent(getActivity(), UserActivity.class);
                startActivity(intent4);
                break;
            default:
                break;
        }
        initData(type);
    }

    private void initData(String type2) {
        String url = GlobalVariable.URL.GUIDE;
        YazhiHttp yazhiHttp = new YazhiHttp(url);
        yazhiHttp.addParams("type", type);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                final Guide parseJSON = GsonUtils.parseJSON(arg0, Guide.class);
                Status status = parseJSON.getStatus();
                if (status.getSucceed() == 1) {
                    x.image().loadDrawable(parseJSON.getData().get(0).getAdCode(), imageOptions, new Callback.CacheCallback<Drawable>() {
                        @Override
                        public boolean onCache(Drawable drawable) {
                            YazhiUtils.enlargeImgHeight(drawable, imageView3);
                            return true;
                        }

                        @Override
                        public void onSuccess(Drawable drawable) {
                            YazhiUtils.enlargeImgHeight(drawable, imageView3);
                        }


                        @Override
                        public void onError(Throwable throwable, boolean b) {
                        }

                        @Override
                        public void onCancelled(CancelledException e) {
                        }

                        @Override
                        public void onFinished() {
                        }
                    });
                } else {

                }
            }

            @Override
            public void onError() {

            }
        });

    }


}
