package com.xmzlb.wine;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xmzlb.base.BaseActivity;
import com.xmzlb.model.Data7;
import com.xmzlb.model.Like;
import com.xmzlb.model.Sellerinfo;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.zyzutil.YazhiHttp;
import com.pgyersdk.update.PgyUpdateManager;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.UILUtils;

import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * 配送商登陆后的界面
 */
public class SellerActivity extends BaseActivity {

	private TextView textView51;
	private TextView textView3;
	private TextView textView4;
	private TextView textView61;
	private TextView text_allmoney;
	private TextView text_allNum;
	private TextView text_monthMoney;
	private TextView text_thisMonthNum;
	// private TextView textView61;
	// private TextView textView61;
	// private TextView textView61;
	private ImageView ima_user;
	private RelativeLayout rele_menu_detailact;
	private RelativeLayout back;
	private TextView text_topbar;
	public static final int TO_SELECT_PHOTO = 3;
	String picPath = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seller);

		PgyUpdateManager.register(this);

		String spLoginState = GlobalVariable.getInstance().getSpLoginState();
		if (!spLoginState.equals("1")) { // 如果登陆状态是配送商，则finish，
			finish();
		}

		initViews();
		init();
		initEvents();
	}

	public void initData() {
		String url = GlobalVariable.URL.SELLERCENTER;
		String spSid = GlobalVariable.getInstance().getSpSid("empty");
		YazhiHttp yazhiHttp = new YazhiHttp(this, url);
		yazhiHttp.addParams("sid", spSid);
		yazhiHttp.post(new YazhiHttp.MyListener() {
			@Override
			public void onSuccess(String arg0) {
				Sellerinfo parseJSON = GsonUtils.parseJSON(arg0, Sellerinfo.class);
				com.xmzlb.model.Status status = parseJSON.getStatus();
				if (status.getSucceed() == 1) { // 得到数据
					Data7 data = parseJSON.getData();
					textView3.setText("配送商：" + data.getShopName());
					textView4.setText("联系人：" + data.getRealName());
					textView51.setText(data.getMobilePhone());
					textView61.setText(data.getAddress());
					text_allmoney.setText(data.getAllMoney() + "");
					text_allNum.setText(data.getAllOrder() + "");
					text_monthMoney.setText(data.getMonthMoney() + "");
					text_thisMonthNum.setText(data.getMonthOrder() + "");
					UILUtils.displayImageNoAnim(data.getHeadimg(), ima_user);
					if (!picPath.isEmpty()) { // 如果不为空
						changeImg();
					}
					// text_allmoney.setText(data.getAllMoney() + "");
					// text_allmoney.setText(data.getAllMoney() + "");
					// text_allmoney.setText(data.getAllMoney() + "");
				} else {
					if (status.getSucceed() == 2) {
						Intent intentLogIn = new Intent(SellerActivity.this, UserLogInActivity.class);
						intentLogIn.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intentLogIn);
						// intentLogIn.putExtra("from", 0);
						// intentLogIn.putExtra("fromStatus", 1);
					}
				}
			}

			@Override
			public void onError() {

			}
		});
	}

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
					picPath = "";
					initData();
				} else {
					showShortToast(status.getError_desc());
				}

			}
		});
	}

	@Override
	protected void onResume() {
		super.onResume();
		String spLoginState = GlobalVariable.getInstance().getSpLoginState();
		if (!spLoginState.equals("1")) { // 如果登陆状态是客户，则finish，
			finish();
		}
		initData();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 带字符串标示从哪里进入，分别为1234
		case R.id.rela_waitSend: // 点击待配送
			startActivity(SendOrderActivity.class, "type", "shipping_no");
			break;
		case R.id.rela_waitReceive: // 点击待收货
			startActivity(SendOrderActivity.class, "type", "confirm_no");
			break;
		case R.id.rela_waitCalculate: // 点击待结算
			startActivity(SendOrderActivity.class, "type", "settlement_no");
			break;
		case R.id.rela_waitCount: // 点击已结算
			startActivity(SendOrderActivity.class, "type", "settlement");
			break;
		case R.id.ima_user: // 切换到配送中心界面
			startActivity(new Intent(SellerActivity.this, SendActivity.class));
			break;
		case R.id.btn_signOut: // 注销
			GlobalVariable.getInstance().setSpSid("");
			GlobalVariable.getInstance().setOldPwd("empty");
			Intent intent = new Intent(SellerActivity.this, UserLogInActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			finish();
			break;
		case R.id.btn_changeIma: // 设置头像
			startActivityForResult(new Intent(SellerActivity.this, ChoosePicActivity.class), TO_SELECT_PHOTO);
			break;

		default:
			break;
		}

	}

	@Override
	protected void initViews() {
		textView3 = (TextView) findViewById(R.id.textView3); // 配送商
		textView4 = (TextView) findViewById(R.id.textView4); // 联系人
		textView51 = (TextView) findViewById(R.id.textView51); // 手机号
		textView61 = (TextView) findViewById(R.id.textView61); // 地址
		text_allmoney = (TextView) findViewById(R.id.text_allmoney); // 累计配送金额
		text_allNum = (TextView) findViewById(R.id.text_allNum); // 累计配送订单
		text_monthMoney = (TextView) findViewById(R.id.text_monthMoney); // 当月配送金额
		text_thisMonthNum = (TextView) findViewById(R.id.text_thisMonthNum); // 当月配送订单
		ima_user = (ImageView) findViewById(R.id.ima_user);
		rele_menu_detailact = (RelativeLayout) findViewById(R.id.rele_menu_detailact);
		rele_menu_detailact.setVisibility(View.GONE);
		back = (RelativeLayout) findViewById(R.id.back);
		back.setVisibility(View.GONE);

		text_topbar = (TextView) findViewById(R.id.text_topbar);
	}

	@Override
	protected void initEvents() {
		findViewById(R.id.rela_waitSend).setOnClickListener(this);
		findViewById(R.id.rela_waitReceive).setOnClickListener(this);
		findViewById(R.id.rela_waitCalculate).setOnClickListener(this);
		findViewById(R.id.rela_waitCount).setOnClickListener(this);
		findViewById(R.id.ima_user).setOnClickListener(this);
		findViewById(R.id.btn_signOut).setOnClickListener(this);
		findViewById(R.id.btn_changeIma).setOnClickListener(this);
	}

	@Override
	protected void init() {
		text_topbar.setText("配送中心");
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		super.onActivityResult(arg0, arg1, arg2);
		if (arg1 == Activity.RESULT_OK && arg0 == TO_SELECT_PHOTO) {
			picPath = arg2.getStringExtra("photo_path");
			if (picPath.equals(""))
				return;
			// 缩放图片, width, height 按相同比例缩放图片
			BitmapFactory.Options options = new BitmapFactory.Options();
			// options 设为true时，构造出的bitmap没有图片，只有一些长宽等配置信息，但比较快，设为false时，才有图片
			options.inJustDecodeBounds = true;
			Bitmap bitmap = BitmapFactory.decodeFile(picPath, options);
			int scale = (int) (options.outWidth / (float) 300);
			if (scale <= 0)
				scale = 1;
			options.inSampleSize = scale;
			options.inJustDecodeBounds = false;
			bitmap = BitmapFactory.decodeFile(picPath, options);

			ima_user.setImageBitmap(bitmap);
			// ima1.setMaxHeight(350);

		}
	}
}
