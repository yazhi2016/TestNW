package com.xmzlb.wine;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.UILUtils;
import com.xmzlb.base.BaseActivity;
import com.xmzlb.model.Datum8;
import com.xmzlb.model.Like;
import com.xmzlb.model.Score;
import com.xmzlb.model.Status;
import com.xmzlb.registermodel.Data2;
import com.xmzlb.registermodel.Userinfo;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.wine.databinding.ActivityScoreBinding;
import com.xmzlb.zyzutil.YazhiHttp;

import org.xutils.common.Callback.CommonCallback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 增减分记录
 */
public class ScoreActivity extends BaseActivity {

	private TextView text_topbar;
	private TextView text_uilocation;
	private RelativeLayout rela2;
	private RelativeLayout rela1;
	private RelativeLayout rela3;
	private ListView lv_addscore; // 增分记录
	private ListView lv_losescore; // 减分记录
	private LvAddAdapter lvAddAdapter;
	private LvLoseAdapter lvLoseAdapter;
	private ImageView imageView3;
	String type = "1"; // 1增分记录，0减分记录
	private String spSid;
	ArrayList<Datum8> list = new ArrayList<Datum8>();

	View popMenu;
	private PopupWindow popupWindowMenu; // 菜单弹窗
	private View bar;
	String picPath = "";
	public static final int TO_SELECT_PHOTO = 3;
    private ActivityScoreBinding mBinding;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_score);
		initViews();
		initEvents();
		init();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		initData();
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

	
	private void initData() {
		String url = GlobalVariable.URL.USERCENTER;
		YazhiHttp yazhiHttp = new YazhiHttp(this, url);
		spSid = GlobalVariable.getInstance().getSpSid("empty");
		yazhiHttp.addParams("sid", spSid);
		yazhiHttp.post(new YazhiHttp.MyListener() {
			@Override
			public void onSuccess(String arg0) {
				Userinfo parseJSON = GsonUtils.parseJSON(arg0, Userinfo.class);
				com.xmzlb.registermodel.Status status = parseJSON.getStatus();
				if (status.getSucceed() == 1) { // 得到数据
					Data2 data = parseJSON.getData();
                    mBinding.setData(data);

					UILUtils.displayImageNoAnim(data.getHeadimg(), imageView3);

					if (!picPath.isEmpty()) { // 如果不为空
						changeImg();
					}
				} else { // 接受数据失败
					showShortToast(status.getError_desc());
					if (status.getSucceed() == 2) {
						Intent intentLogIn = new Intent(ScoreActivity.this, UserLogInActivity.class);
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

	class LvAddAdapter extends BaseAdapter {

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
			TextView text_time;
			TextView textView2;
			TextView text_score;
			if (convertView == null) {
				view = getLayoutInflater().inflate(R.layout.item_lv_addscore, null);
				text_time = (TextView) view.findViewById(R.id.text_time);
				textView2 = (TextView) view.findViewById(R.id.textView2);
				text_score = (TextView) view.findViewById(R.id.text_score);
				view.setTag(new AddTag(text_time, textView2, text_score));
			} else {
				view = convertView;
				AddTag tag2 = (AddTag) view.getTag();
				text_time = tag2.text_time;
				textView2 = tag2.textView2;
				text_score = tag2.text_score;
			}
			Datum8 datum8 = list.get(position);
			text_time.setText(datum8.getChangeTime());
			textView2.setText(datum8.getChangeDesc());
			text_score.setText(datum8.getRankPoints() + "分");
			return view;
		}
	}

	class AddTag {
		TextView text_time;
		TextView textView2;
		TextView text_score;

		public AddTag(TextView text_time, TextView textView2, TextView text_score) {
			super();
			this.text_time = text_time;
			this.textView2 = textView2;
			this.text_score = text_score;
		}

	}

	class LoseTag {
		TextView text_time;
		TextView textView2;
		TextView text_score;

		public LoseTag(TextView text_time, TextView textView2, TextView text_score) {
			super();
			this.text_time = text_time;
			this.textView2 = textView2;
			this.text_score = text_score;
		}

	}

	class LvLoseAdapter extends BaseAdapter {

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
			TextView text_time;
			TextView textView2;
			TextView text_score;
			if (convertView == null) {
				view = getLayoutInflater().inflate(R.layout.item_lv_losescore, null);
				text_time = (TextView) view.findViewById(R.id.text_time);
				textView2 = (TextView) view.findViewById(R.id.textView2);
				text_score = (TextView) view.findViewById(R.id.text_score);
				view.setTag(new LoseTag(text_time, textView2, text_score));
			} else {
				view = convertView;
				LoseTag tag2 = (LoseTag) view.getTag();
				text_time = tag2.text_time;
				textView2 = tag2.textView2;
				text_score = tag2.text_score;
			}
			Datum8 datum8 = list.get(position);
			text_time.setText(datum8.getChangeTime());
			textView2.setText(datum8.getChangeDesc());
			text_score.setText(datum8.getRankPoints() + "分");
			return view;
		}
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

			imageView3.setImageBitmap(bitmap);
			// ima1.setMaxHeight(350);

		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_changeIma: //设置头像
			startActivityForResult(new Intent(ScoreActivity.this, ChoosePicActivity.class), TO_SELECT_PHOTO);
			break;
		case R.id.radio0: // 客户等级
			rela1.setVisibility(View.VISIBLE);
			rela2.setVisibility(View.GONE);
			rela3.setVisibility(View.GONE);
			break;
		case R.id.radio1: // 增分记录
			type = "1";
			rela1.setVisibility(View.GONE);
			rela2.setVisibility(View.VISIBLE);
			rela3.setVisibility(View.GONE);
			initScore(type);
			break;
		case R.id.radio2: // 减分记录
			type = "0";
			rela1.setVisibility(View.GONE);
			rela2.setVisibility(View.GONE);
			rela3.setVisibility(View.VISIBLE);
			initScore(type);
			break;
		case R.id.back:
			finish();
			break;
		case R.id.rela_menu1:
		case R.id.tab_item1: // 点击底栏首页
			popupWindowMenu.dismiss();
			Intent intent_menu1 = new Intent(ScoreActivity.this, MainActivity.class);
			intent_menu1.putExtra("tabhost", 0);
			startActivity(intent_menu1);
			break;
		case R.id.rela_menu2:
		case R.id.tab_item2: // 点击底栏购物车
			popupWindowMenu.dismiss();
			Intent intent_menu2 = new Intent(ScoreActivity.this, MainActivity.class);
			intent_menu2.putExtra("tabhost", 1);
			startActivity(intent_menu2);
			break;
		case R.id.rela_menu3:
		case R.id.tab_item3: // 点击底栏订单中心
			popupWindowMenu.dismiss();
			Intent intent_menu3 = new Intent(ScoreActivity.this, MainActivity.class);
			intent_menu3.putExtra("tabhost", 2);
			startActivity(intent_menu3);
			break;
		case R.id.rela_menu4:
			popupWindowMenu.dismiss();
			Intent intent_menu4 = new Intent(ScoreActivity.this, UserActivity.class);
			startActivity(intent_menu4);
			break;
		case R.id.tab_item4: // 点击底栏商场指南
			Intent intent_tab4 = new Intent(ScoreActivity.this, MainActivity.class);
			intent_tab4.putExtra("tabhost", 3);
			startActivity(intent_tab4);
			break;
		case R.id.tab_item5: // 点击底栏我要供货
			Intent intent_tab5 = new Intent(ScoreActivity.this, IWantSupply.class);
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

	private void initScore(String type2) {
		String url = GlobalVariable.URL.ADDANDLOSESCORE;
		YazhiHttp yazhiHttp = new YazhiHttp(url);
		yazhiHttp.addParams("sid", spSid);
		yazhiHttp.addParams("type", type);
		yazhiHttp.post(new YazhiHttp.MyListener() {
			@Override
			public void onSuccess(String arg0) {
				Score parseJSON = GsonUtils.parseJSON(arg0, Score.class);
				Status status = parseJSON.getStatus();
				if (status.getSucceed() == 1) {
					List<Datum8> data = parseJSON.getData();
					list.clear();
					list.addAll(data);
					if (type.equals("1")) { // 增分记录
						lvAddAdapter.notifyDataSetChanged();
					} else {
						lvLoseAdapter.notifyDataSetChanged();
					}
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
		text_topbar = (TextView) findViewById(R.id.text_topbar);
		text_uilocation = (TextView) findViewById(R.id.text_uilocation);
		rela1 = (RelativeLayout) findViewById(R.id.rela1);
		rela2 = (RelativeLayout) findViewById(R.id.rela2);
		rela3 = (RelativeLayout) findViewById(R.id.rela3);
		lv_addscore = (ListView) findViewById(R.id.lv_addscore);
		lv_losescore = (ListView) findViewById(R.id.lv_losescore);

		lvAddAdapter = new LvAddAdapter();
		lv_addscore.setAdapter(lvAddAdapter);
		lvLoseAdapter = new LvLoseAdapter();
		lv_losescore.setAdapter(lvLoseAdapter);

		imageView3 = (ImageView) findViewById(R.id.imageView3);

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
		findViewById(R.id.radio0).setOnClickListener(this);
		findViewById(R.id.radio1).setOnClickListener(this);
		findViewById(R.id.radio2).setOnClickListener(this);
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.btn_changeIma).setOnClickListener(this);

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
		text_topbar.setText("用户中心");
		text_uilocation.setText("用户中心 > 积分记录");
	}

}
