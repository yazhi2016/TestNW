package com.xmzlb.wine;

import android.support.v7.app.ActionBarActivity;

import com.xmzlb.util.Content;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

public class ChangeNameActivity extends ActionBarActivity implements OnClickListener {

	private EditText edit_name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_name);
		
		initUI();
	}

	private void initUI() {
		findViewById(R.id.around_back).setOnClickListener(this);
		findViewById(R.id.checkbox_shopfra_edit).setOnClickListener(this);
		edit_name = (EditText) findViewById(R.id.edit_name);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.around_back:
			finish();
			break;
		case R.id.checkbox_shopfra_edit:
			String name = edit_name.getText().toString();
			if(name.equals("")) {
				edit_name.setError("昵称不能为空");
			} else {
				Intent intent = new Intent();
				intent.putExtra(Content.EXTRA_NAME.CHANGENAME, name);
				setResult(Content.RESULTCODE.CHANGENAME, intent);
				finish();
			}
			break;
		default:
			break;
		}
	}
	
}
