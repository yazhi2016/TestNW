package com.xmzlb.wine;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.xmzlb.base.BaseActivity;

public class AllOrderActivity extends BaseActivity {

	private TextView text_topbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_all_order);
		initViews();
		init();
		initEvents();
//		initData("no_pay");
	}
	

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	protected void initViews() {
		text_topbar = (TextView) findViewById(R.id.text_topbar);
	}

	@Override
	protected void initEvents() {
		findViewById(R.id.back).setOnClickListener(this);
	}

	@Override
	protected void init() {
		text_topbar.setText("全部订单");
	}

}