package com.xmzlb.wine;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.xmzlb.base.BaseActivity;
import com.xmzlb.model.Datum;
import com.xmzlb.model.Like;
import com.xmzlb.model.Status;
import com.xmzlb.registermodel.Child;
import com.xmzlb.registermodel.Child_;
import com.xmzlb.registermodel.Child__;
import com.xmzlb.registermodel.Citiesid;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.wheelcity.AbstractWheelTextAdapter;
import com.xmzlb.wheelcity.AddressData;
import com.xmzlb.wheelcity.ArrayWheelAdapter;
import com.xmzlb.wheelcity.OnWheelChangedListener;
import com.xmzlb.wheelcity.WheelView;
import com.xmzlb.wheelview.MyAlertDialog;
import com.xmzlb.zyzutil.YazhiHttp;
import com.xinbo.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

public class AddAddressActivity extends BaseActivity {

	private EditText edit_smsnum_register;
	private EditText edit_smsnum_register2;
	private EditText edit_storeaddress_register;
	private CheckBox checkbox;


	String province;
	String cityStr;
	String countyStr;
	ArrayList<Child> citieList = new ArrayList<Child>();
	String id1 = "empty"; // 选中的省的id，为empty则说明没选
	String id2 = "empty";
	String id3 = "empty";
	int position1 = -1; // 选中省的行号,如果等-1，说明还未选择，则不能选择市
	int position2 = -1;
	int position3 = -1;
	int editPosition1;
	int editPosition2;
	int editPosition3;
	String[] shen;
	String[] shi;
	String[] xian;
	int mode = 1; // 选择的模式：1省，2市，3区
	private TextView textview_province;
	private TextView textview_city;
	private TextView textview_county;
	private TextView text_uilocation;
	private Datum data;
	private String stringExtra;
	private EditText edit_phone;
	private Button btn_login_mefra;
	private String address_id;
	private int isZero; //如果为1，说明添加地址之前没有地址，则添加的唯一一个地址则为默认地址

	View popMenu;
	private PopupWindow popupWindowMenu; // 菜单弹窗
	private View bar;
	private TextView text_nothing;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_add_address);

		text_uilocation = (TextView) findViewById(R.id.text_uilocation);
		stringExtra = getIntent().getStringExtra("from");
		if (stringExtra.equals("edit")) {
			text_uilocation.setText("用户中心 > 收货地址管理 > 修改收货地址");
			data = (Datum) getIntent().getSerializableExtra("data");
		} else {
			text_uilocation.setText("用户中心 > 收货地址管理 > 添加收货地址");
		}
		isZero = getIntent().getIntExtra("isZero", -1);
		getCitiesId();
		initViews();
		initEvents();
		init();
	}

	private void getCitiesId() { // 得到省市ID编号
		String url = GlobalVariable.URL.CITIESID; // 地址
		YazhiHttp yazhiHttp = new YazhiHttp(url);
		yazhiHttp.post(new YazhiHttp.MyListener() {
			@Override
			public void onSuccess(String arg0) {
				Citiesid parseJSON = GsonUtils.parseJSON(arg0, Citiesid.class);
				List<Child> child = parseJSON.getData().get(0).getChild();
				citieList.clear();
				citieList.addAll(child);
				if (stringExtra.equals("edit")) {
					initLocationId();
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
		case R.id.btn_login_mefra: // 确认添加
			if (id1.equals("empty")) { // 没选择省
				showShortToast("请选择所在省份！");
				return;
			}
			if (id2.equals("empty")) { // 没选择省
				showShortToast("请选择所在市！");
				return;
			}
			if (id3.equals("empty")) { // 没选择省
				showShortToast("请选择所在县区！");
				return;
			}
			String name = edit_smsnum_register.getText().toString();
			if (name.isEmpty()) {
				edit_smsnum_register.setError("收货人不能为空");
				return;
			}
			String phone = edit_phone.getText().toString();
			if (phone.isEmpty()) {
				edit_phone.setError("手机号不能为空");
				return;
			}
			String detail = edit_storeaddress_register.getText().toString();
			if (detail.isEmpty()) {
				edit_storeaddress_register.setError("详细地址不能为空");
				return;
			}
			String default_address = null;
			if (checkbox.isChecked()) {
				default_address = "1";
			} else {
				default_address = "0";
			}

			if(isZero == 1) {
				default_address = "1";
			}

			String url = null;
			if (stringExtra.equals("edit")) {
				url = GlobalVariable.URL.EDITADDRESS;
			} else {
				url = GlobalVariable.URL.ADDADDRESS;
			}
			YazhiHttp yazhiHttp = new YazhiHttp(this,url);
			yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
			if (stringExtra.equals("edit")) { //如果是编辑也要传地址id
				yazhiHttp.addParams("address_id", address_id);
			}
			yazhiHttp.addParams("name", name);
			yazhiHttp.addParams("country", "1");
			yazhiHttp.addParams("province", id1);
			yazhiHttp.addParams("city", id2);
			yazhiHttp.addParams("district", id3);
			yazhiHttp.addParams("address", detail);
			yazhiHttp.addParams("mobile", phone);
			if (stringExtra.equals("edit")) { //如果是编辑也要传地址id
				yazhiHttp.addParams("default_address", default_address);
			} else {
				yazhiHttp.addParams("default", default_address);
			}
			yazhiHttp.post(new YazhiHttp.MyListener() {
				@Override
				public void onSuccess(String arg0) {
					Like parseJSON = GsonUtils.parseJSON(arg0, Like.class);
					Status status = parseJSON.getStatus();
					if (status.getSucceed() == 1) {
						showShortToast(parseJSON.getData().getMsg());
						finish();
					} else {
						showShortToast("操作失败！");
					}
				}

				@Override
				public void onError() {

				}
			});

			break;
		case R.id.rela_shen: // 点击省
			mode = 1;

			shen = new String[citieList.size()];
			for (int i = 0; i < citieList.size(); i++) { // 遍历每个省
				shen[i] = citieList.get(i).getRegionName();
			}

			View view = dialogm();
			final MyAlertDialog dialog1 = new MyAlertDialog(AddAddressActivity.this).builder().setTitle("修改现居地")
					.setView(view).setNegativeButton("取消", new OnClickListener() {
						@Override
						public void onClick(View v) {

						}
					});
			dialog1.setPositiveButton("保存", new OnClickListener() {
				@Override
				public void onClick(View v) {
					textview_province.setText(province);
					position1 = editPosition1;
					id1 = citieList.get(position1).getRegionId();

					// 如果重选了省，则市，区都要清空
					position2 = -1;
					id2 = "empty";
					id3 = "empty";
					textview_city.setText("请选择");
					textview_county.setText("请选择");
				}
			});
			dialog1.show();
			break;
		case R.id.rela_shi:
			if (position1 == -1) { // 还没选择省
				showShortToast("请先选择省份！");
			} else {
				mode = 2;
				final List<Child_> child = citieList.get(position1).getChild();
				shen = new String[child.size()];
				for (int i = 0; i < child.size(); i++) { // 遍历选中省的每个市
					shen[i] = child.get(i).getRegionName();
				}

				View view2 = dialogm();
				final MyAlertDialog dialog2 = new MyAlertDialog(AddAddressActivity.this).builder().setTitle("修改现居地")
						.setView(view2).setNegativeButton("取消", new OnClickListener() {
							@Override
							public void onClick(View v) {

							}
						});
				dialog2.setPositiveButton("保存", new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(child.size() == 1) {
							textview_city.setText(child.get(0).getRegionName());
							position2 = 0;
							id2 = citieList.get(position1).getChild().get(0).getRegionId();
						} else {
							textview_city.setText(cityStr);
							position2 = editPosition2;
							id2 = citieList.get(position1).getChild().get(position2).getRegionId();
						}

						// 选择了市，则要重新选区
						id3 = "empty";
						textview_county.setText("请选择");
					}
				});
				dialog2.show();
			}
			break;
		case R.id.rela_xian:
			if (position2 == -1) { // 还没选择区
				showShortToast("请先选择所在市！");
			} else {
				mode = 3;
				List<Child__> child = citieList.get(position1).getChild().get(position2).getChild();
				shen = new String[child.size()];
				for (int i = 0; i < child.size(); i++) { // 遍历选中省的每个市
					shen[i] = child.get(i).getRegionName();
				}

				View view3 = dialogm();
				final MyAlertDialog dialog3 = new MyAlertDialog(AddAddressActivity.this).builder().setTitle("修改现居地")
						.setView(view3).setNegativeButton("取消", new OnClickListener() {
							@Override
							public void onClick(View v) {

							}
						});
				dialog3.setPositiveButton("保存", new OnClickListener() {
					@Override
					public void onClick(View v) {
						textview_county.setText(countyStr);
						position3 = editPosition3;
						id3 = citieList.get(position1).getChild().get(position2).getChild().get(position3)
								.getRegionId();
					}
				});
				dialog3.show();
			}
			break;
			case R.id.rela_menu1:
			case R.id.tab_item1: // 点击底栏首页
				popupWindowMenu.dismiss();
				Intent intent_menu1 = new Intent(AddAddressActivity.this, MainActivity.class);
				intent_menu1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent_menu1.putExtra("tabhost", 0);
				startActivity(intent_menu1);
				break;
			case R.id.rela_menu2:
			case R.id.tab_item2: // 点击底栏购物车
				popupWindowMenu.dismiss();
				Intent intent_menu2 = new Intent(AddAddressActivity.this, MainActivity.class);
				intent_menu2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent_menu2.putExtra("tabhost", 1);
				startActivity(intent_menu2);
				break;
			case R.id.rela_menu3:
			case R.id.tab_item3: // 点击底栏订单中心
				popupWindowMenu.dismiss();
				Intent intent_menu3 = new Intent(AddAddressActivity.this, MainActivity.class);
				intent_menu3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent_menu3.putExtra("tabhost", 2);
				startActivity(intent_menu3);
				break;
			case R.id.rela_menu4:
				popupWindowMenu.dismiss();
				finish();
				break;
			case R.id.tab_item4: // 点击底栏商场指南
				Intent intent_tab4 = new Intent(AddAddressActivity.this, MainActivity.class);
				intent_tab4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent_tab4.putExtra("tabhost", 3);
				startActivity(intent_tab4);
				break;
			case R.id.tab_item5: // 点击底栏我要供货
				Intent intent_tab5 = new Intent(AddAddressActivity.this, MainActivity.class);
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
		edit_smsnum_register = (EditText) findViewById(R.id.edit_smsnum_register);
		edit_smsnum_register2 = (EditText) findViewById(R.id.edit_smsnum_register);
		edit_phone = (EditText) findViewById(R.id.edit_phone);
		edit_storeaddress_register = (EditText) findViewById(R.id.edit_storeaddress_register);
		checkbox = (CheckBox) findViewById(R.id.checkbox);

		textview_province = (TextView) findViewById(R.id.textview_province);
		textview_city = (TextView) findViewById(R.id.textview_city);
		textview_county = (TextView) findViewById(R.id.textview_county);
		btn_login_mefra = (Button) findViewById(R.id.btn_login_mefra);

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
	}

	@Override
	protected void initEvents() {
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.rela_shen).setOnClickListener(this);
		findViewById(R.id.rela_shi).setOnClickListener(this);
		findViewById(R.id.rela_xian).setOnClickListener(this);
		btn_login_mefra.setOnClickListener(this);

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
		if (stringExtra.equals("edit")) { // 编辑联系人
			edit_smsnum_register.setText(data.getConsignee());
			edit_phone.setText(data.getMobile());
			textview_province.setText(data.getProvinceName());
			textview_city.setText(data.getCityName());
			textview_county.setText(data.getDistrictName());
			edit_storeaddress_register.setText(data.getAddress());
			if (data.getDefaultAddress() == 1) { // 默认地址
				checkbox.setChecked(true);
			} else {
				checkbox.setChecked(false);
			}
			btn_login_mefra.setText("确认修改");
			address_id = data.getId();
		} else {
			btn_login_mefra.setText("确认添加");
		}
	}

	private void initLocationId() {
		int province = 0;
		int city = 0;
		for (int i = 0; i < citieList.size(); i++) {
			Child child2 = citieList.get(i);
			if (child2.getRegionName().equals(data.getProvinceName())) {
				id1 = child2.getRegionId();
				province = i;
				break;
			}
		}
		List<Child_> child = citieList.get(province).getChild();
		for (int i = 0; i < child.size(); i++) {
			Child_ child_ = child.get(i);
			if (child_.getRegionName().equals(data.getCityName())) {
				id2 = child_.getRegionId();
				city = i;
				break;
			}
		}
		List<Child__> child2 = child.get(city).getChild();
		for (int i = 0; i < child2.size(); i++) {
			Child__ child__ = child2.get(i);
			if (child__.getRegionName().equals(data.getDistrictName())) {
				id3 = child__.getRegionId();
				break;
			}
		}
		Log.e("==", id1 + id2 + id3);
	}

	private View dialogm() {
		View contentView = LayoutInflater.from(this).inflate(R.layout.wheelcity_cities_layout, null);
		final WheelView country = (WheelView) contentView.findViewById(R.id.wheelcity_country);
		country.setVisibleItems(3);
		country.setViewAdapter(new CountryAdapter(this));

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
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this, cities[index]);
		adapter.setTextSize(18);
		city.setViewAdapter(adapter);
		city.setCurrentItem(0);
	}

	private void updatecCities(WheelView city, String ccities[][][], int index, int index2) {
		ArrayWheelAdapter<String> adapter = new ArrayWheelAdapter<String>(this, ccities[index][index2]);
		adapter.setTextSize(18);
		city.setViewAdapter(adapter);
		city.setCurrentItem(0);
	}

}
