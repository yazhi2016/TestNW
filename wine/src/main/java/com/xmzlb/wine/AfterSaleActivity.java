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
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.xmzlb.base.BaseActivity;
import com.xmzlb.model.Backlist;
import com.xmzlb.model.Datum16;
import com.xmzlb.model.Like;
import com.xmzlb.model.Status;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.zyzutil.YazhiHttp;
import com.xinbo.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

public class AfterSaleActivity extends BaseActivity {

	private TextView text_uilocation;
	private ListView lv_aftersale;
	private LvAdapter lvAdapter;
	ArrayList<Datum16> list = new ArrayList<>();

	View popMenu;
	private PopupWindow popupWindowMenu; // 菜单弹窗
	private View bar;
	private TextView text_topbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_asfter_sale);
		initViews();
		init();
		initEvents();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		initBackListData();
	}

	private void initBackListData() {
		String url = GlobalVariable.URL.BACKLIST;
		YazhiHttp yazhiHttp = new YazhiHttp(this, url);
		yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
		yazhiHttp.post(new YazhiHttp.MyListener() {
			@Override
			public void onSuccess(String arg0) {
				Backlist parseJSON = GsonUtils.parseJSON(arg0, Backlist.class);
				Status status = parseJSON.getStatus();
				if (status.getSucceed() == 1) {
					List<Datum16> data = parseJSON.getData();
					list.clear();
					list.addAll(data);
					lvAdapter.notifyDataSetChanged();
				} else {
					showShortToast(status.getError_desc());
					if (status.getSucceed() == 2) {
						Intent intentLogIn = new Intent(AfterSaleActivity.this, UserLogInActivity.class);
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

	class LvAdapter extends BaseAdapter {

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
			View view = null;
			Button btn_complaint;
			Button btn_done;
			TextView text_num;
			TextView text_title;
			TextView text_orderid;
			TextView text_time;
			if (view == null) {
				view = getLayoutInflater().inflate(R.layout.item_lv_aftersale, null);
				text_time = (TextView) view.findViewById(R.id.text_time);
				text_orderid = (TextView) view.findViewById(R.id.text_orderid);
				text_title = (TextView) view.findViewById(R.id.text_title);
				text_num = (TextView) view.findViewById(R.id.text_num);
				btn_done = (Button) view.findViewById(R.id.btn_done);
				btn_complaint = (Button) view.findViewById(R.id.btn_complaint);
				view.setTag(new MyTag(btn_complaint, btn_done, text_num, text_title, text_orderid, text_time));
			} else {
				view = convertView;
				MyTag tag2 = (MyTag) view.getTag();
				text_time = tag2.text_time;
				text_orderid = tag2.text_orderid;
				text_title = tag2.text_title;
				text_num = tag2.text_num;
				btn_done = tag2.btn_done;
				btn_complaint = tag2.btn_complaint;
			}

			Datum16 datum16 = list.get(position);
			text_num.setText(datum16.getNumber());
			text_title.setText(datum16.getGoodname());
			String addtime = datum16.getAddtime();
			String replace = addtime.replace(" ", "\n");
			text_time.setText(replace);
			text_orderid.setText(datum16.getReturnId());

			final String returnId = datum16.getReturnId();
			btn_done.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String url = GlobalVariable.URL.BACKFINISH;
					YazhiHttp yazhiHttp = new YazhiHttp(AfterSaleActivity.this, url);
					yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
					yazhiHttp.addParams("return_id", returnId);
					yazhiHttp.post(new YazhiHttp.MyListener() {
						@Override
						public void onSuccess(String arg0) {
							Like parseJSON = GsonUtils.parseJSON(arg0, Like.class);
							Status status = parseJSON.getStatus();
							if (status.getSucceed() == 1) {
								showShortToast(parseJSON.getData().getMsg());
								initBackListData();
							} else {
								showShortToast(status.getError_desc());
								if (status.getSucceed() == 2) {
									Intent intentLogIn = new Intent(AfterSaleActivity.this, UserLogInActivity.class);
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
			});

			btn_complaint.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					startActivity(new Intent(AfterSaleActivity.this, ComplaintActivity.class));
				}
			});
			return view;
		}

	}

	class MyTag {
		Button btn_complaint;
		Button btn_done;
		TextView text_num;
		TextView text_title;
		TextView text_orderid;
		TextView text_time;

		public MyTag(Button btn_complaint, Button btn_done, TextView text_num, TextView text_title,
				TextView text_orderid, TextView text_time) {
			super();
			this.btn_complaint = btn_complaint;
			this.btn_done = btn_done;
			this.text_num = text_num;
			this.text_title = text_title;
			this.text_orderid = text_orderid;
			this.text_time = text_time;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.btn_changeIma: // 新增申请
			startActivity(new Intent(AfterSaleActivity.this, NewReturnActivity.class));
			break;
		case R.id.rela_menu1:
		case R.id.tab_item1: // 点击底栏首页
			popupWindowMenu.dismiss();
			Intent intent_menu1 = new Intent(AfterSaleActivity.this, MainActivity.class);
			intent_menu1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent_menu1.putExtra("tabhost", 0);
			startActivity(intent_menu1);
			break;
		case R.id.rela_menu2:
		case R.id.tab_item2: // 点击底栏购物车
			popupWindowMenu.dismiss();
			Intent intent_menu2 = new Intent(AfterSaleActivity.this, MainActivity.class);
			intent_menu2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent_menu2.putExtra("tabhost", 1);
			startActivity(intent_menu2);
			break;
		case R.id.rela_menu3:
		case R.id.tab_item3: // 点击底栏订单中心
			popupWindowMenu.dismiss();
			Intent intent_menu3 = new Intent(AfterSaleActivity.this, MainActivity.class);
			intent_menu3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent_menu3.putExtra("tabhost", 2);
			startActivity(intent_menu3);
			break;
		case R.id.rela_menu4:
			popupWindowMenu.dismiss();
			finish();
			break;
		case R.id.tab_item4: // 点击底栏商场指南
			Intent intent_tab4 = new Intent(AfterSaleActivity.this, MainActivity.class);
			intent_tab4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent_tab4.putExtra("tabhost", 3);
			startActivity(intent_tab4);
			break;
		case R.id.tab_item5: // 点击底栏我要供货
			Intent intent_tab5 = new Intent(AfterSaleActivity.this, MainActivity.class);
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
		default:
			break;
		}
	}

	@Override
	protected void initViews() {
		text_uilocation = (TextView) findViewById(R.id.text_uilocation);
		lv_aftersale = (ListView) findViewById(R.id.lv_aftersale);
		text_topbar = (TextView) findViewById(R.id.text_topbar);

		lvAdapter = new LvAdapter();
		lv_aftersale.setAdapter(lvAdapter);

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
		text_uilocation.setText("用户中心 > 退换货管理");
		text_topbar.setText("用户中心");
	}
}
