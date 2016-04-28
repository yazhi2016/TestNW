package com.xmzlb.wine;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class SafeCenterActivity extends ActionBarActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_safe_center);
		
		findViewById(R.id.signin_pwd).setOnClickListener(this);
		findViewById(R.id.pay_pwd).setOnClickListener(this);
		findViewById(R.id.around_back).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.signin_pwd: //登陆密码
			Intent intent = new Intent(SafeCenterActivity.this, ChangePwdActivity.class);
			intent.putExtra("changepwd", "修改登陆密码");
			startActivity(intent);
			break;
		case R.id.pay_pwd: //支付密码
			Intent intent2 = new Intent(SafeCenterActivity.this, ChangePwdActivity.class);
			intent2.putExtra("changepwd", "修改支付密码");
			startActivity(intent2);
			break;
		case R.id.around_back:
			finish();
			break;

		default:
			break;
		}
	}

}
