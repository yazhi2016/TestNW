package com.xmzlb.base;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Toast;

import com.xmzlb.variable.GlobalVariable;
import com.xmzlb.wine.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class BaseActivity extends FragmentActivity implements
		OnClickListener {
	protected BaseApplication mApplication;
	// protected NetWorkUtils mNetWorkUtils;
//	protected FlippingLoadingDialog mLoadingDialog;
	/**
	 * 屏幕的宽度、高度、密度
	 */
	protected int mScreenWidth;
	protected int mScreenHeight;
	protected float mDensity;

	protected List<AsyncTask<Void, Void, Object>> mAsyncTasks = new ArrayList<AsyncTask<Void, Void, Object>>();

	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		mApplication = (BaseApplication) getApplication();
//		mLoadingDialog = new FlippingLoadingDialog(this, "请求提交中");
		DisplayMetrics metric = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metric);
		mScreenWidth = metric.widthPixels;
		mScreenHeight = metric.heightPixels;
		mDensity = metric.density;
	}

	@Override
	protected void onDestroy() {
		clearAsyncTask();
		super.onDestroy();
	}

	/** 初始化视图 **/
	protected abstract void initViews();

	/** 初始化事件 **/
	protected abstract void initEvents();

	/** 初始化数据 **/
	protected abstract void init();

	protected void putAsyncTask(AsyncTask<Void, Void, Object> asyncTask) {
		mAsyncTasks.add(asyncTask.execute());
	}

	protected void clearAsyncTask() {
		Iterator<AsyncTask<Void, Void, Object>> iterator = mAsyncTasks
				.iterator();
		while (iterator.hasNext()) {
			AsyncTask<Void, Void, Object> asyncTask = iterator.next();
			if (asyncTask != null && !asyncTask.isCancelled()) {
				asyncTask.cancel(true);
			}
		}
		mAsyncTasks.clear();
	}

//	protected void showLoadingDialog(String text) {
//		if (text != null) {
//			mLoadingDialog.setText(text);
//		}
//		mLoadingDialog.show();
//	}
//
//	protected void dismissLoadingDialog() {
//		if (mLoadingDialog.isShowing()) {
//			mLoadingDialog.dismiss();
//		}
//	}

	/**
	 * 在屏幕中部显示弹窗
	 * @param str
     */
	public void showMidToast(String str) {
		Toast toast = Toast.makeText(this, str, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	/** 短暂显示Toast提示(来自res) **/
	protected void showShortToast(int resId) {
		Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show();
	}

	/** 短暂显示Toast提示(来自String) **/
	protected void showShortToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}

	/** 长时间显示Toast提示(来自res) **/
	protected void showLongToast(int resId) {
		Toast.makeText(this, getString(resId), Toast.LENGTH_LONG).show();
	}

	/** 长时间显示Toast提示(来自String) **/
	protected void showLongToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}

	protected void showLog(String tag, String msg) {
		Log.i(tag, msg);
	}

	/** Debug输出Log日志 **/
	protected void showLogDebug(String tag, String msg) {   
		Log.d(tag, msg);
	}

	/** Error输出Log日志 **/
	protected void showLogError(String tag, String msg) {
		Log.e(tag, msg);
	}

	/** 通过Class跳转界面 **/
	protected void startActivity(Class<?> cls) {
		startActivity(cls, null);
	}

	/** 含有Bundle通过Class跳转界面 **/
	protected void startActivity(Class<?> cls, Bundle bundle) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	/** 带有字符串通过Class跳转界面 **/
	protected void startActivity(Class<?> cls, String key, String str) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		if (str != null && key != null) {
			intent.putExtra(key, str);
		}
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}
	/** 带有int通过Class跳转界面 **/
	protected void startActivity(Class<?> cls, String key, int value) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		if (key != null) {
			intent.putExtra(key, value);
		}
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	/** 含有Bundle带返回结果通过Class跳转界面 **/
	public void startActivityForResult(Class<?> cls, Bundle bundle,
			int requestCode) {
		Intent intent = new Intent();
		intent.setClass(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivityForResult(intent, requestCode);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	/** 通过Action跳转界面 **/
	protected void startActivity(String action) {
		startActivity(action, null);
	}

	/** 含有Bundle通过Action跳转界面 **/
	protected void startActivity(String action, Bundle bundle) {
		Intent intent = new Intent();
		intent.setAction(action);
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
		overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
	}

	/** 带有右进右出动画的退出 **/
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
	}

	/** 默认退出 **/
	protected void defaultFinish() {
		super.finish();
	}
//	/**Dialogs1**/
//	public void PromptDialogs(String meg, View.OnClickListener cl) {
//		new AlertDialogEx(this).setBuilder(new Builder(this).setMessage(meg)
//				.setNegativeButton("是", cl, true)
//				.setPositiveButton("否", null, true).show());
//	}
//	/**Dialogs2**/
//	public void PromptDialogs(String meg, View.OnClickListener cl,View.OnClickListener cl1) {
//		new AlertDialogEx(this).setBuilder(new Builder(this).setMessage(meg)
//				.setNegativeButton("是", cl, true)
//				.setPositiveButton("否", cl1, true).show());
//	}
//	/**Dialogs3**/
//	public void PromptDialogs(String meg,String yesstr,String nostr, View.OnClickListener cl,View.OnClickListener cl1) {
//		new AlertDialogEx(this).setBuilder(new Builder(this).setMessage(meg)
//				.setNegativeButton(yesstr, cl, true)
//				.setPositiveButton(nostr, cl1, true).show());
//	}

	/*
	 * 图片是否wifi下才下载
	 */
	public boolean Imagedown() {
		Boolean iswifidome = GlobalVariable.getInstance().getIswifidome();
		return iswifidome;
	}

	// 获取uid
	public String GetUid() {
		String uid = GlobalVariable.getInstance().getUid();
		if (TextUtils.isEmpty(uid)) {
			return "";
		}
		return uid;
	}

	// 获取spname
	public String GetSPname() {
		return GlobalVariable.getInstance().spname;
	}

	// 判断是否登录过
	public Boolean Islogin() {
		if (TextUtils.isEmpty(GetUid()) && GetUid() != null) {
			return true;
		}
//		startActivity(LoginActivity.class);  
		return false;
	}
	
	//被登陆后
	public void Unlogin() {
		GlobalVariable.getInstance().setUid("");
//		SpUtils.saveStringSP(this, GetSPname(), 
//				GlobalVariable.getInstance().SPUIDKEY,"");
	}
	
	//是否需要重新登录
	public Boolean Isrestart(){
		Boolean isrestart = GlobalVariable.getInstance().getISRESTART();
		return isrestart;
	}  
	
	// 判断是否登录过
	public Boolean IsloginTO() {  
		if (GetUid() == null || GetUid() == "") {
				return true;
		}
		return false;
	}
	
	//保存 获取的经纬度和城市名
	public void SavaCitys(double latitude,double longitude,String cityname){
		GlobalVariable.getInstance().setLatitude(latitude);
		GlobalVariable.getInstance().setLongitude(longitude);
		GlobalVariable.getInstance().setMycity(cityname);
	}
	
}
