package com.xmzlb.wine;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.UILUtils;
import com.xmzlb.base.BaseActivity;
import com.xmzlb.model.Data10;
import com.xmzlb.model.Orderdetail;
import com.xmzlb.model.Status;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.zyzutil.YazhiHttp;

public class OrderDetailActivity extends BaseActivity {

	private TextView text_topbar;
	private String order_id;
	private TextView textView10; // 促销信息
	private TextView textView2; // 组合形式
	private TextView text_num;
	private TextView textView3; // 商品规格
	private TextView textView1; // 订单状态
	private TextView text_price;
	private RelativeLayout rela_address2; // 配送商家信息
	private TextView text_view; // 配送状态信息
	private TextView text_orderId2; // 配送商
	private TextView text_name2; // 配送联系人
	private TextView text_phone22; // 配送手机号
	private TextView text_address2; // 配送商地址
	private TextView text_orderId;
	private TextView text_name;
	private TextView text_phone;
	private TextView text_address;
	private ImageView imageView1;
	private TextView text_title;
	private TextView henxian5;
	
	View popMenu;
	private PopupWindow popupWindowMenu; // 菜单弹窗
	private View bar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail);

		order_id = getIntent().getStringExtra("order_id");

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
		String url = GlobalVariable.URL.ORDERDETAIL;
		YazhiHttp yazhiHttp = new YazhiHttp(this, url);
		yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
		yazhiHttp.addParams("order_id", order_id);
		yazhiHttp.post(new YazhiHttp.MyListener() {
			@Override
			public void onSuccess(String arg0) {
				Orderdetail parseJSON = GsonUtils.parseJSON(arg0, Orderdetail.class);
				Status status = parseJSON.getStatus();
				if (status.getSucceed() == 1) {
					Data10 data = parseJSON.getData();
					Integer status2 = data.getStatus();
					if (status2 == 0) {
						textView1.setText("待付款");
						rela_address2.setVisibility(View.GONE);
						text_view.setVisibility(View.GONE);
						henxian5.setVisibility(View.GONE);
					} else if (status2 == 1) { // 待配送未分配
						textView1.setText("待配送");
						text_view.setText("系统未分配配送商");
						rela_address2.setVisibility(View.GONE);
						text_view.setVisibility(View.VISIBLE);
						henxian5.setVisibility(View.VISIBLE);
					} else { // 已分配配送商
						if (status2 == 2) {
							textView1.setText("待配送");
							text_view.setText("配送商信息");
						} else if (status2 == 3) { // 待收货
							textView1.setText("待收货");
							text_view.setText("配送商信息");
						} else if (status2 == 4) {
							textView1.setText("已完成");
							text_view.setText("配送商信息");
						}
						text_view.setVisibility(View.VISIBLE);
						henxian5.setVisibility(View.VISIBLE);
						rela_address2.setVisibility(View.VISIBLE);
						text_orderId2.setText(data.getLogisticeShopName());
						text_name2.setText(data.getLogisticeRealName());
						text_phone22.setText(data.getLogisticeMobilePhone());
						text_address2.setText(data.getLogisticeProvince() + data.getLogisticeCity()
								+ data.getLogisticeDistrict() + data.getLogisticeAddress());
					}

					text_price.setText("￥" + data.getMoney());
					text_orderId.setText(data.getOrderSn());
					text_name.setText(data.getConsignee());
					text_phone.setText(data.getMobile());
					text_address.setText(data.getProvince() + data.getCity() + data.getDistrict() + data.getAddress());
					UILUtils.displayImageNoAnim(data.getOriginalImg(), imageView1);
					text_title.setText(data.getGoodsName());
					text_orderId.setText(data.getOrderSn());
					String bottle = data.getBottle();
					if (bottle.equals("1")) {
						textView3.setText("商品规格：单瓶起批");
						textView3.setText("组合形式：无");
					} else {
						textView3.setText("商品规格：单件起批");
						textView3.setText("组合形式：" + data.getOneNum() + "瓶子1件*1组");
					}
					text_num.setText("X" + data.getGoodsNumber());
					if (data.getPromotion().isEmpty()) {
						textView10.setText("促销信息：无");
					} else {
						textView10.setText("促销信息：" + data.getPromotion() + "满" + data.getFullMoney() + "减" + data.getSubMoney());
					}
				} else {
					showShortToast(status.getError_desc());
					if(status.getSucceed() == 2){
						Intent intentLogIn = new Intent(OrderDetailActivity.this, UserLogInActivity.class);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.rela_menu1:
		case R.id.tab_item1: // 点击底栏首页
			popupWindowMenu.dismiss();
			Intent intent_menu1 = new Intent(OrderDetailActivity.this, MainActivity.class);
			intent_menu1.putExtra("tabhost", 0);
			startActivity(intent_menu1);
			break;
		case R.id.rela_menu2:
		case R.id.tab_item2: // 点击底栏购物车
			popupWindowMenu.dismiss();
			Intent intent_menu2 = new Intent(OrderDetailActivity.this, MainActivity.class);
			intent_menu2.putExtra("tabhost", 1);
			startActivity(intent_menu2);
			break;
		case R.id.rela_menu3:
		case R.id.tab_item3: // 点击底栏订单中心
			popupWindowMenu.dismiss();
			Intent intent_menu3 = new Intent(OrderDetailActivity.this, MainActivity.class);
			intent_menu3.putExtra("tabhost", 2);
			startActivity(intent_menu3);
			break;
		case R.id.rela_menu4:
			popupWindowMenu.dismiss();
			Intent intent_menu4 = new Intent(OrderDetailActivity.this, UserActivity.class);
			startActivity(intent_menu4);
			break;
		case R.id.tab_item4: // 点击底栏商场指南
			Intent intent_tab4 = new Intent(OrderDetailActivity.this, MainActivity.class);
			intent_tab4.putExtra("tabhost", 3);
			startActivity(intent_tab4);
			break;
		case R.id.tab_item5: // 点击底栏我要供货
			Intent intent_tab5 = new Intent(OrderDetailActivity.this, IWantSupply.class);
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
		text_topbar = (TextView) findViewById(R.id.text_topbar);
		text_orderId = (TextView) findViewById(R.id.text_orderId);
		text_name = (TextView) findViewById(R.id.text_name);
		text_phone = (TextView) findViewById(R.id.text_phone);
		text_address = (TextView) findViewById(R.id.text_address);
		imageView1 = (ImageView) findViewById(R.id.ima);
		text_title = (TextView) findViewById(R.id.text_title);
		textView10 = (TextView) findViewById(R.id.textView10);
		textView2 = (TextView) findViewById(R.id.textView2);
		text_num = (TextView) findViewById(R.id.text_num);
		textView3 = (TextView) findViewById(R.id.textView3);
		text_price = (TextView) findViewById(R.id.text_price);
		textView1 = (TextView) findViewById(R.id.textView1);
		rela_address2 = (RelativeLayout) findViewById(R.id.rela_address2);
		text_view = (TextView) findViewById(R.id.text_view);
		text_orderId2 = (TextView) findViewById(R.id.text_orderId2);
		text_name2 = (TextView) findViewById(R.id.text_name2);
		text_phone22 = (TextView) findViewById(R.id.text_phone22);
		text_address2 = (TextView) findViewById(R.id.text_address2);
		henxian5 = (TextView) findViewById(R.id.henxian5);
		
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
		text_topbar.setText("订单中心");
	}
}
