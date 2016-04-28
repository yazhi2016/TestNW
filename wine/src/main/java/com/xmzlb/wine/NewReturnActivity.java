package com.xmzlb.wine;

import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.xinbo.utils.GsonUtils;
import com.xmzlb.base.BaseActivity;
import com.xmzlb.model.Like;
import com.xmzlb.model.Status;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.zyzutil.YazhiHttp;

/**
 * 新增退换货管理
 *
 */
public class NewReturnActivity extends BaseActivity {

	View popMenu;
	private PopupWindow popupWindowMenu; // 菜单弹窗
	private View bar;
	private EditText edit_smsnum_register; // 商品名
	private EditText edit_num;
	private EditText edit_reason;
	int backType = 0; // 0退货，1换货
	private TextView text_topbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_return);
		initViews();
		init();
		initEvents();
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
			Intent intent_menu1 = new Intent(NewReturnActivity.this, MainActivity.class);
			intent_menu1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent_menu1.putExtra("tabhost", 0);
			startActivity(intent_menu1);
			break;
		case R.id.rela_menu2:
		case R.id.tab_item2: // 点击底栏购物车
			popupWindowMenu.dismiss();
			Intent intent_menu2 = new Intent(NewReturnActivity.this, MainActivity.class);
			intent_menu2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent_menu2.putExtra("tabhost", 1);
			startActivity(intent_menu2);
			break;
		case R.id.rela_menu3:
		case R.id.tab_item3: // 点击底栏订单中心
			popupWindowMenu.dismiss();
			Intent intent_menu3 = new Intent(NewReturnActivity.this, MainActivity.class);
			intent_menu3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent_menu3.putExtra("tabhost", 2);
			startActivity(intent_menu3);
			break;
		case R.id.rela_menu4:
			popupWindowMenu.dismiss();
			Intent intent_menu4 = new Intent(NewReturnActivity.this, UserActivity.class);
			intent_menu4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent_menu4);
			finish();
			break;
		case R.id.tab_item4: // 点击底栏商场指南
			Intent intent_tab4 = new Intent(NewReturnActivity.this, MainActivity.class);
			intent_tab4.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent_tab4.putExtra("tabhost", 3);
			startActivity(intent_tab4);
			break;
		case R.id.tab_item5: // 点击底栏我要供货
			Intent intent_tab5 = new Intent(NewReturnActivity.this, MainActivity.class);
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
		case R.id.radio0: // 退货
			backType = 0;
			break;
		case R.id.radio1: // 换货
			backType = 1;
			break;
		case R.id.btn_login_mefra: // 提交
			String name = edit_smsnum_register.getText().toString();
			if (name.isEmpty()) {
				edit_smsnum_register.setError("请填写商品名");
				return;
			}
			String number = edit_num.getText().toString();
			if (number.isEmpty()) {
				edit_num.setError("请填写数量");
				return;
			}
			String reason = edit_reason.getText().toString();
			if (reason.isEmpty()) {
				edit_reason.setError("请填写退换理由");
				return;
			}
			String url = GlobalVariable.URL.NEWBACK;
			YazhiHttp yazhiHttp = new YazhiHttp(this, url);
			yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
			yazhiHttp.addParams("goodname", name);
			yazhiHttp.addParams("number", number);
			yazhiHttp.addParams("why", reason);
			yazhiHttp.addParams("type", backType + "");
			yazhiHttp.post(new YazhiHttp.MyListener() {
				@Override
				public void onSuccess(String arg0) {
					Like parseJSON = GsonUtils.parseJSON(arg0, Like.class);
					Status status = parseJSON.getStatus();
					if (status.getSucceed() == 1) {
						showShortToast(parseJSON.getData().getMsg());
						finish();
					} else {
						showShortToast(status.getError_desc());
						if (status.getSucceed() == 2) {
							Intent intentLogIn = new Intent(NewReturnActivity.this, UserLogInActivity.class);
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
			break;
		default:
			break;
		}
	}

	@Override
	protected void initViews() {
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

		text_topbar = (TextView) findViewById(R.id.text_topbar);
		edit_smsnum_register = (EditText) findViewById(R.id.edit_smsnum_register);
		edit_num = (EditText) findViewById(R.id.edit_num);
		edit_reason = (EditText) findViewById(R.id.edit_reason);
	}

	@Override
	protected void initEvents() {
		findViewById(R.id.back).setOnClickListener(this);
		findViewById(R.id.radio0).setOnClickListener(this);
		findViewById(R.id.radio1).setOnClickListener(this);
		findViewById(R.id.btn_login_mefra).setOnClickListener(this);

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
	}

}
