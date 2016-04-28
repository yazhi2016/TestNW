package com.xmzlb.wine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xmzlb.base.BaseActivity;
import com.xmzlb.model.Data8;
import com.xmzlb.model.Like;
import com.xmzlb.model.Sellerorderdetail;
import com.xmzlb.model.Status;
import com.xmzlb.util.SquareImageView;
import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.zyzutil.YazhiHttp;
import com.xinbo.utils.GsonUtils;
import com.xinbo.utils.UILUtils;

/**
 * 配送中心：配送商点击发货等界面
 */
public class SendActivity extends BaseActivity {

	private TextView text_state1;
	private TextView text_state2;
	private TextView text_state3;
	private TextView text_state4;
	private SquareImageView ima_user;
	private TextView text_orderNum;
	private TextView text_name;
	private TextView text_phone;
	private String order_sn;
	private TextView text_address;
	private TextView text_title;
	private TextView textView3;
	private TextView textView;
	private TextView textView1;
	private TextView textView6;
	private TextView textView9;
	private TextView textView12;
	private TextView textView16;
	private TextView textView23;
	private TextView textView122;
	private TextView textView26;
	private RelativeLayout rele_menu_detailact;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send);
		order_sn = getIntent().getStringExtra("order_sn");
		initViews();
		initEvents();
		init();
	}

	@Override
	protected void onResume() {
		super.onResume();
		initData();
	}

	private void initData() {
		String url = GlobalVariable.URL.SELLORDERDETAIL;
		YazhiHttp yazhiHttp = new YazhiHttp(this, url);
		yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
		yazhiHttp.addParams("order_sn", order_sn);
		yazhiHttp.post(new YazhiHttp.MyListener() {
			@Override
			public void onSuccess(String arg0) {
				Sellerorderdetail parseJSON = GsonUtils.parseJSON(arg0, Sellerorderdetail.class);
				Status status = parseJSON.getStatus();
				if (status.getSucceed() == 1) {
					Data8 data = parseJSON.getData();
					UILUtils.displayImageNoAnim(data.getGoodsImg(), ima_user);
					text_orderNum.setText(order_sn);
					text_name.setText(data.getConsignee());
					text_phone.setText(data.getMobile());
					text_address.setText(data.getAddress());
					text_title.setText(data.getGoodsName());
					if (data.getBottle().equals("1")) {
						textView3.setText("商品规格：单瓶起批");
					} else {
						textView3.setText("商品规格：整组起批");
					}
					if (data.getOneNum().equals("0")) {
						textView.setText("组合形式：无");
					} else {
						textView.setText("组合形式：" + data.getOneNum() + "瓶1件*1组");
					}
					if (data.getPromotion().isEmpty()) {
						textView1.setText("促销信息：无");
					} else {
						textView1.setText("促销信息：" + data.getPromotion());
					}
					textView6.setText(data.getOrderMoney());
					textView9.setText(data.getPayMoney());
					textView6.setText(data.getOrderMoney());
					textView12.setText(data.getNoPay() + "");
					textView16.setText(data.getDistributionMoney());
					textView122.setText(data.getLogisticeMoney() + "");
					textView23.setText(data.getAllMoney() + "");
					textView26.setText(data.getSettlement() + "");

					// 订单状态
					String shippingStatus = data.getShippingStatus();
					if (shippingStatus.equals("0")) { // 待配送
						text_state1.setVisibility(View.VISIBLE);
						text_state2.setVisibility(View.INVISIBLE);
						text_state3.setVisibility(View.INVISIBLE);
						text_state4.setVisibility(View.INVISIBLE);

					} else if (shippingStatus.equals("1")) { // 待收货
						text_state1.setBackgroundResource(R.drawable.done3);
//						text_state1.setBackground(getResources().getDrawable(R.drawable.done3));
						text_state2.setVisibility(View.VISIBLE);
						text_state3.setVisibility(View.INVISIBLE);
						text_state4.setVisibility(View.INVISIBLE);

					} else if (shippingStatus.equals("2")) { // 待结算
						text_state1.setBackgroundResource(R.drawable.done3);
						text_state2.setBackgroundResource(R.drawable.done3);
						text_state2.setVisibility(View.VISIBLE);
						text_state3.setVisibility(View.VISIBLE);
						text_state4.setVisibility(View.INVISIBLE);
					} else { // 已结算
						text_state1.setBackgroundResource(R.drawable.done3);
						text_state2.setBackgroundResource(R.drawable.done3);
						text_state3.setBackgroundResource(R.drawable.done3);
						text_state2.setVisibility(View.VISIBLE);
						text_state3.setVisibility(View.VISIBLE);
						text_state4.setVisibility(View.VISIBLE);
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

	@SuppressLint("NewApi")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rela1: // 待配送
			Intent intent = new Intent();
			intent.putExtra("finishFrom", "shipping_no");
			setResult(1, intent);
			finish();
			break;
		case R.id.rela2: // 待收货
			Intent intent2 = new Intent();
			intent2.putExtra("finishFrom", "confirm_no");
			setResult(1, intent2);
			finish();
			break;
		case R.id.rela3: // 待结算
			Intent intent3 = new Intent();
			intent3.putExtra("finishFrom", "settlement_no");
			setResult(1, intent3);
			finish();
			break;
		case R.id.rela4: // 已结算
			Intent intent4 = new Intent();
			intent4.putExtra("finishFrom", "settlement");
			setResult(1, intent4);
			finish();
			break;
		case R.id.text_state1: // 点击发货
			String url = GlobalVariable.URL.SENDGOOD;
			YazhiHttp yazhiHttp = new YazhiHttp(this, url);
			yazhiHttp.addParams("sid", GlobalVariable.getInstance().getSpSid("empty"));
			yazhiHttp.addParams("order_sn", order_sn);
			yazhiHttp.post(new YazhiHttp.MyListener() {
				@Override
				public void onSuccess(String arg0) {
					Like parseJSON = GsonUtils.parseJSON(arg0, Like.class);
					Status status = parseJSON.getStatus();
					if (status.getSucceed() == 1) {
						showShortToast(parseJSON.getData().getMsg());
						// 发货完刷新商品状态
						initData();
					} else {
						showShortToast(status.getError_desc());
					}
				}

				@Override
				public void onError() {

				}
			});
			break;
		case R.id.back: 
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	protected void initViews() {
		text_state1 = (TextView) findViewById(R.id.text_state1);
		text_state2 = (TextView) findViewById(R.id.text_state2);
		text_state3 = (TextView) findViewById(R.id.text_state3);
		text_state4 = (TextView) findViewById(R.id.text_state4);

		ima_user = (SquareImageView) findViewById(R.id.ima_user);
		text_orderNum = (TextView) findViewById(R.id.text_orderNum);
		text_name = (TextView) findViewById(R.id.text_name);
		text_phone = (TextView) findViewById(R.id.text_phone);
		text_address = (TextView) findViewById(R.id.text_address);
		text_title = (TextView) findViewById(R.id.text_title);
		textView3 = (TextView) findViewById(R.id.textView3);
		textView = (TextView) findViewById(R.id.textView);
		textView1 = (TextView) findViewById(R.id.textView1);
		textView6 = (TextView) findViewById(R.id.textView6);
		textView9 = (TextView) findViewById(R.id.textView9);
		textView12 = (TextView) findViewById(R.id.textView12);
		textView16 = (TextView) findViewById(R.id.textView16);
		textView23 = (TextView) findViewById(R.id.textView23);
		textView122 = (TextView) findViewById(R.id.textView122);
		textView26 = (TextView) findViewById(R.id.textView26);
		
		findViewById(R.id.back).setOnClickListener(this);
		rele_menu_detailact = (RelativeLayout) findViewById(R.id.rele_menu_detailact);
	}

	@Override
	protected void initEvents() {
		findViewById(R.id.rela1).setOnClickListener(this);
		findViewById(R.id.rela2).setOnClickListener(this);
		findViewById(R.id.rela3).setOnClickListener(this);
		findViewById(R.id.rela4).setOnClickListener(this);
		text_state1.setOnClickListener(this);
	}

	@Override
	protected void init() {
		rele_menu_detailact.setVisibility(View.GONE);
	}

}
