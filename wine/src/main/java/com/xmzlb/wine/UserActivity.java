package com.xmzlb.wine;

import android.app.ActionBar.LayoutParams;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;

import com.android.volley.VolleyError;
import com.king.photo.activity.AlbumActivity;
import com.king.photo.util.Bimp;
import com.king.photo.util.Res;
import com.pgyersdk.update.PgyUpdateManager;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.UILUtils;
import com.xinbo.utils.VolleyListener;
import com.xmzlb.base.BaseActivity;
import com.xmzlb.model.Like;
import com.xmzlb.registermodel.Data2;
import com.xmzlb.registermodel.OrderNum2;
import com.xmzlb.registermodel.Status;
import com.xmzlb.registermodel.Userinfo;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.wine.databinding.ActivityUserBinding;
import com.xmzlb.zyzutil.YazhiHttp;

import org.xutils.common.Callback.CommonCallback;
import org.xutils.common.util.DensityUtil;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserActivity extends BaseActivity {

    private ImageView imageView3;
    private PopupWindow popupWindowMenu; // 菜单弹窗
    private PopupWindow popupWindowChoosePic; // 照片弹窗
    private View popMenu;
    private RelativeLayout bar;
    String picPath = "";
    ImageOptions imageOptions;

    private Intent lastIntent;
    private ActivityUserBinding mBanding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBanding = DataBindingUtil.setContentView(this, R.layout.activity_user);

        Res.init(this);
        lastIntent = new Intent();

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

        PgyUpdateManager.register(this);

        GlobalVariable instance = GlobalVariable.getInstance();
        String spLoginState = instance.getSpLoginState();
        if (!spLoginState.equals("0")) { // 如果登陆状态是配送商，则finish，
            finish();
        }

        initViews();
        initEvents();
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String spLoginState = GlobalVariable.getInstance().getSpLoginState();
        if (!spLoginState.equals("0")) { // 如果登陆状态是配送商，则finish，
            finish();
        }
        if (Bimp.tempSelectBitmap.size() == 1) {
            int size = Bimp.tempSelectBitmap.size();
            picPath = Bimp.tempSelectBitmap.get(0).getImagePath();
            doCropPhoto(new File(picPath));
            // imageView3.setImageBitmap(Bimp.tempSelectBitmap.get(0).getBitmap());
            // changeImg();
        }

        initData();
    }

    public void initData() {
        String url = GlobalVariable.URL.USERCENTER;
        YazhiHttp yazhiHttp = new YazhiHttp(this, url);
        String spSid = GlobalVariable.getInstance().getSpSid("empty");
        yazhiHttp.addParams("sid", spSid);
        yazhiHttp.post(new YazhiHttp.MyListener() {
            @Override
            public void onSuccess(String arg0) {
                Userinfo parseJSON = GsonUtils.parseJSON(arg0, Userinfo.class);
                Status status = parseJSON.getStatus();
                if (status.getSucceed() == 1) { // 得到数据
                    Data2 data = parseJSON.getData();

                    mBanding.setData(data);

                    if (data.getHeadimg().equals("http://zjt.yiyuanjg.com/")) { //刚注册时设定默认头像
                        imageView3.setBackgroundResource(R.drawable.uermoima);
                    } else {
                        UILUtils.displayImageNoAnim(data.getHeadimg(), imageView3);
                    }

                    OrderNum2 orderNum = data.getOrderNum();
                    mBanding.setOrderNum2(orderNum);

                } else { // 接受数据失败
                    if (status.getSucceed() == 2) {
                        Intent intentLogIn = new Intent(UserActivity.this, UserLogInActivity.class);
                        intentLogIn.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intentLogIn);
                        finish();
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
            case R.id.btn_signOut: // 注销登陆,将本地密码清空
                GlobalVariable.getInstance().setSpSid("");
                GlobalVariable.getInstance().setOldPwd("empty");
                Intent intentSignOut = new Intent(UserActivity.this, UserLogInActivity.class);
                intentSignOut.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentSignOut);
                finish();
                break;
            case R.id.rele_menu_detailact: // 菜单
                popupWindowMenu.showAsDropDown(bar);
                break;
            case R.id.rela1: // 待付款
                Intent intent11 = new Intent(UserActivity.this, MainActivity.class);
                intent11.putExtra("tabhost", 5);
                intent11.putExtra("item", 0);
                startActivity(intent11);
                break;
            case R.id.rela2: // 待发货
                Intent intent12 = new Intent(UserActivity.this, MainActivity.class);
                intent12.putExtra("tabhost", 6);
                intent12.putExtra("item", 1);
                startActivity(intent12);
                break;
            case R.id.rela3:
                Intent intent13 = new Intent(UserActivity.this, MainActivity.class);
                intent13.putExtra("tabhost", 5);
                intent13.putExtra("item", 1);
                startActivity(intent13);
                break;
            case R.id.rela4:
                Intent intent14 = new Intent(UserActivity.this, MainActivity.class);
                intent14.putExtra("tabhost", 5);
                intent14.putExtra("item", 0);
                startActivity(intent14);
                break;
            case R.id.back:
                if (isTaskRoot()) {
                    Intent intent = new Intent(UserActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("tabhost", 0);
                    startActivity(intent);
                } else {
                    finish();
                }
                break;
            case R.id.rela_editinfo: // 编辑用户信息
                startActivity(new Intent(UserActivity.this, EditUserInfoActivity.class));
                break;
            case R.id.rela_addressmanager: // 收货地址管理
                startActivity(new Intent(UserActivity.this, AddressActivity.class));
                break;
            case R.id.rela_complaint: // 收货投诉建议
                startActivity(new Intent(UserActivity.this, ComplaintActivity.class));
                break;
            case R.id.rela_score: // 积分记录
                startActivity(new Intent(UserActivity.this, ScoreActivity.class));
                break;
            case R.id.rela_changePwd: // 修改密码
                startActivity(new Intent(UserActivity.this, ChangePwdActivity.class));
                break;
            case R.id.rela_aftersale: // 退换货管理
                startActivity(new Intent(UserActivity.this, AfterSaleActivity.class));
                break;
            case R.id.rela_collection: // 收藏
                startActivity(new Intent(UserActivity.this, CollectionActivity.class));
                break;
            case R.id.rela_scorecity: // 积分商城
                startActivity(new Intent(UserActivity.this, ScoreCityActivity.class));
                break;
            case R.id.relativeLayout2: // 全部订单
                // startActivity(new Intent(UserActivity.this,
                // AllOrderActivity.class));
                Intent intent3 = new Intent(UserActivity.this, MainActivity.class);
                intent3.putExtra("tabhost", 2);
                startActivity(intent3);
                break;
            case R.id.tab_item1: // 点击底栏首页
            case R.id.rela_menu1:
                Intent intent = new Intent(UserActivity.this, MainActivity.class);
                intent.putExtra("tabhost", 0);
                startActivity(intent);
                break;
            case R.id.tab_item2: // 点击底栏购物车
            case R.id.rela_menu2:
                Intent intent2 = new Intent(UserActivity.this, MainActivity.class);
                intent2.putExtra("tabhost", 1);
                startActivity(intent2);
                break;
            case R.id.tab_item3: // 点击底栏订单中心
            case R.id.rela_menu3:
                Intent intent0 = new Intent(UserActivity.this, MainActivity.class);
                intent0.putExtra("tabhost", 2);
                startActivity(intent0);
                break;
            case R.id.rela_menu4:
            case R.id.rela_empty_meun:
                popupWindowMenu.dismiss();
                break;
            case R.id.tab_item4: // 点击底栏商场指南
                Intent intent4 = new Intent(UserActivity.this, MainActivity.class);
                intent4.putExtra("tabhost", 3);
                startActivity(intent4);
                break;
            case R.id.tab_item5: // 点击底栏我要供货
                Intent intent5 = new Intent(UserActivity.this, IWantSupply.class);
//                intent5.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent5.putExtra("tabhost", 4);
                startActivity(intent5);
                break;
            case R.id.imageView3: //
                startActivityForResult(new Intent(UserActivity.this, ChoosePicActivity.class), TO_SELECT_PHOTO);
                break;
            case R.id.btn_changeIma: // 更改头像
                popupWindowChoosePic.showAtLocation(popMenu, Gravity.CENTER, 0, 0);
                // startActivityForResult(new Intent(UserActivity.this,
                // ChoosePicActivity.class), TO_SELECT_PHOTO);
                break;
            case R.id.btn_cancel: // 取消
            case R.id.empty_bottom:
            case R.id.text_empty: // 取消
                popupWindowChoosePic.dismiss();
                break;
            case R.id.btn_choose: // 从相册选择
                Intent intent_0 = new Intent(UserActivity.this, AlbumActivity.class);
                startActivity(intent_0);
                popupWindowChoosePic.dismiss();
                break;
            case R.id.btn_takephoto: // 拍照
                String status = Environment.getExternalStorageState();
                if (status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡
                    doTakePhoto();// 用户点击了从照相机获取
                }
                popupWindowChoosePic.dismiss();
                // String status = Environment.getExternalStorageState();
                // if (status.equals(Environment.MEDIA_MOUNTED)) {// 判断是否有SD卡
                // doTakePhoto();// 用户点击了从照相机获取
                // }
                break;

            default:
                break;
        }
    }

    public Intent getTakePickIntent(File f) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
        return intent;
    }

    /**
     * 照相机拍照得到的图片
     */
    protected File mCurrentPhotoFile;

    /**
     * 用来标识请求照相功能的请求码
     */
    protected static final int CAMERA_WITH_DATA = 3023;

    /**
     * 拍照获取图片
     */
    protected void doTakePhoto() {
        try {
            // Launch camera to take photo for selected contact
            PHOTO_DIR.mkdirs();// 创建照片的存储目录
            mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());// 给新照的照片文件命名
            final Intent intent = getTakePickIntent(mCurrentPhotoFile);
            startActivityForResult(intent, CAMERA_WITH_DATA);
        } catch (ActivityNotFoundException e) {
        }
    }

    /**
     * 用当前时间给取得的图片命名
     */
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

    /**
     * 拍照的照片存储位置
     */
    private static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");

    private void changeImg() {
        String url = GlobalVariable.URL.CHANGEIMG;
        RequestParams params = new RequestParams(url);
        params.setMultipart(true);
        params.addBodyParameter("sid", GlobalVariable.getInstance().getSpSid("empty"));
        params.addBodyParameter("headimg", new File(picPath));

        x.http().post(params, new CommonCallback<String>() {

            @Override
            public void onCancelled(CancelledException arg0) {
            }

            @Override
            public void onError(Throwable arg0, boolean arg1) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onSuccess(String arg0) {
                Like parseJSON = GsonUtils.parseJSON(arg0, Like.class);
                com.xmzlb.model.Status status = parseJSON.getStatus();
                if (status.getSucceed() == 1) {
                    showShortToast(parseJSON.getData().getMsg());
                    initData();
                    Bimp.tempSelectBitmap.clear();
                    picPath = "";
                } else {
                    showShortToast(status.getError_desc());
                }

            }
        });
    }

    @Override
    protected void initViews() {
        imageView3 = (ImageView) findViewById(R.id.imageView3);

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
        popMenu.findViewById(R.id.rela_menu1).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_menu2).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_menu3).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_menu4).setOnClickListener(this);
        bar = (RelativeLayout) findViewById(R.id.bar);

        View viewChoosePic = getLayoutInflater().inflate(R.layout.activity_choose_pic, null);
        popupWindowChoosePic = new PopupWindow(viewChoosePic, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
                true);
        // 设置点击窗体以外区域关闭窗体
        BitmapDrawable b22 = new BitmapDrawable();
        popupWindowChoosePic.setBackgroundDrawable(b22);
        popupWindowChoosePic.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {

            }
        });
        viewChoosePic.findViewById(R.id.btn_takephoto).setOnClickListener(this);
        viewChoosePic.findViewById(R.id.btn_choose).setOnClickListener(this);
        viewChoosePic.findViewById(R.id.btn_cancel).setOnClickListener(this);
        viewChoosePic.findViewById(R.id.empty_bottom).setOnClickListener(this);
        viewChoosePic.findViewById(R.id.text_empty).setOnClickListener(this);

    }

    @Override
    protected void initEvents() {
        findViewById(R.id.rela_addressmanager).setOnClickListener(this);
        findViewById(R.id.rela_complaint).setOnClickListener(this);
        findViewById(R.id.rela_score).setOnClickListener(this);
        findViewById(R.id.btn_changeIma).setOnClickListener(this);
        findViewById(R.id.rela_changePwd).setOnClickListener(this);
        findViewById(R.id.rela_aftersale).setOnClickListener(this);
        findViewById(R.id.rela_collection).setOnClickListener(this);
        findViewById(R.id.rela_editinfo).setOnClickListener(this);
        findViewById(R.id.rela_scorecity).setOnClickListener(this);
        findViewById(R.id.relativeLayout2).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.rele_menu_detailact).setOnClickListener(this);
        findViewById(R.id.btn_signOut).setOnClickListener(this);

        // 底栏tab
        findViewById(R.id.tab_item1).setOnClickListener(this);
        findViewById(R.id.tab_item2).setOnClickListener(this);
        findViewById(R.id.tab_item3).setOnClickListener(this);
        findViewById(R.id.tab_item4).setOnClickListener(this);
        findViewById(R.id.tab_item5).setOnClickListener(this);

        findViewById(R.id.rela1).setOnClickListener(this);
        findViewById(R.id.rela2).setOnClickListener(this);
        findViewById(R.id.rela3).setOnClickListener(this);
        findViewById(R.id.rela4).setOnClickListener(this);
        findViewById(R.id.btn_changeIma).setOnClickListener(this);
        popMenu.findViewById(R.id.rela_empty_meun).setOnClickListener(this);
        imageView3.setOnClickListener(this);
    }

    @Override
    protected void init() {
        // TODO Auto-generated method stub
    }

    public static final int TO_SELECT_PHOTO = 3;
    private static final int TAKE_PICTURE = 0x000001;
    /**
     * 用来标识请求gallery的请求码
     */
    protected static final int PHOTO_PICKED_WITH_DATA = 3021;

    /**
     * 当前加载的图片地址
     */
    protected Uri curPhotoPath;

    /**
     * 从Intent获取图片路径的KEY
     */
    public static final String KEY_PHOTO_PATH = "photo_path";

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);
        switch (arg0) {
            case PHOTO_PICKED_WITH_DATA: // 调用Gallery返回的
                Bimp.tempSelectBitmap.clear();
                String path = curPhotoPath.getPath();
                if (TextUtils.isEmpty(path)) {
                    finish();
                    return;
                }
                x.image().bind(imageView3, path, imageOptions);
                picPath = path;
                changeImg();
                break;
            case CAMERA_WITH_DATA: // 照相机程序返回的,再次调用图片剪辑程序去修剪图片
                doCropPhoto(mCurrentPhotoFile);
                break;
        }
    }

    // 启动gallery去剪辑这个照片
    protected void doCropPhoto(File f) {
        try {
            final Intent intent = getCropImageIntent(Uri.fromFile(f));
            startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
        } catch (Exception e) {
            // Log.e("", "");
        }
    }

    /**
     * Constructs an intent for image cropping. 调用图片剪辑程序
     */
    public Intent getCropImageIntent(Uri photoUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(photoUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 600);
        intent.putExtra("outputY", 600);
        intent.putExtra("return-data", false);
        intent.putExtra("output", curPhotoPath = Uri.fromFile(new File(PHOTO_DIR, getPhotoFileName())));
        return intent;
    }

    VolleyListener imageuplistener = new VolleyListener() {

        @Override
        public void onResponse(String arg0) {
            showShortToast("修改成功");
        }

        @Override
        public void onErrorResponse(VolleyError arg0) {

        }
    };

}
