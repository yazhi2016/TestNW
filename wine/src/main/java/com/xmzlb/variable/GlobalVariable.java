package com.xmzlb.variable;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

/*
 * 全局变量
 */
public final class GlobalVariable {

    //微信
    public static final String APP_ID = "wxc7833b9c07bc2437";

    public static class URL {
        // public static final String HOST = "112.74.207.149/APPAPI/?url=/";

        // http://zjt.yiyuanjg.com/admin/index.php
        public static final String HOST = "http://zjt.yiyuanjg.com/APPAPI/?url=/";
        public static final String CITIESID = HOST + "new/region";
        public static final String LOGIN = HOST + "user/signin";
        public static final String USERCENTER = HOST + "user/info";
        public static final String MAIN = HOST + "main"; // 不包含banner
        public static final String ADDRESS = HOST + "address/list"; // 收货地址
        public static final String DELETEADDRESS = HOST + "address/delete"; // 删除收货地址
        public static final String EDITADDRESS = HOST + "address/update"; // 编辑收货地址
        public static final String SIGNUP = HOST + "new/signup"; // 注册
        public static final String SCORECITY = HOST + "new/integral"; // 积分商城
        public static final String SCOREDETAIL = HOST + "new/integral_goods"; // 积分商品详情
        public static final String ADDADDRESS = HOST + "address/add"; // 添加收货地址
        public static final String DETAIL = HOST + "new/goods"; // 商品详情
        public static final String SHOPCAR = HOST + "new/cartlist"; // 购物车
        public static final String ADDTOSHOPCAR = HOST + "new/cartcreate"; // 添加到购物车
        public static final String DELETESHOPCAR = HOST + "cart/delete"; // 删除购物车
        public static final String ALLORDER = HOST + "new/order"; // 全部订单
        public static final String DELETEORDER = HOST + "new/order_delete"; // 取消订单
        public static final String CATEGORY = HOST + "new/category"; // 商品分类
        public static final String ALLPRODUCT = HOST + "new/category_goods"; // 全部商品
        public static final String LIKE = HOST + "new/like_goods"; // 点赞
        public static final String DISLIKE = HOST + "new/like_goods_no"; // 取消点赞
        public static final String COLLECT = HOST + "user/collect/create"; // 收藏
        public static final String DISCOLLECT = HOST + "user/collect/delete"; // 取消收藏
        public static final String DELETEORDER2 = HOST + "new/order_delete1"; // 删除订单
        public static final String CHANGENUM = HOST + "cart/update"; // 修改购物车数量
        public static final String CHANGENUM2 = HOST + "cart/updates"; // 修改购物车数量
        public static final String CHANGEPWD = HOST + "user/edit_password"; // 修改密码
        public static final String GUIDE = HOST + "new/guide"; // 商城指南
        public static final String MYCOLLECT = HOST + "user/collect/list"; // 我的收藏
        public static final String ADDANDLOSESCORE = HOST + "new/querylotusrecord"; // 增减分记录
        public static final String LOADORDER = HOST + "new/add_order"; // 提交订单
        public static final String CONFIRMGET = HOST + "new/confirm_goods"; // 确认收货
        public static final String EDITUSERINFO = HOST + "new/edit_data"; // 用户信息修改
        public static final String EXCHANGESCORE = HOST + "new/exchange_goods"; // 积分兑换
        public static final String USERINFODETAIL = HOST + "new/edit_data_msg"; // 用户详细信息
        public static final String IWANTSUPPLY = HOST + "new/supplier"; // 我要供货
        public static final String IWANTCOMPLAIN = HOST + "new/complaints"; // 我要投诉
        public static final String COMPLAIN = HOST + "new/handle_complaints"; // 投诉处理
        public static final String SELLERCENTER = HOST + "logistics/info"; // 配送商登陆
        public static final String SELLERORDER = HOST + "logistics/order"; // 配送中心-订单状态
        public static final String SELLORDERDETAIL = HOST + "logistics/order_info"; // 配送订单详情
        public static final String SENDGOOD = HOST + "logistics/order_shipping"; // 点击发货
        public static final String STARTIMA = HOST + "new/welcome"; // 欢迎页
        public static final String SEARCH = HOST + "new/search"; // 搜索
        public static final String RANK = HOST + "new/membership"; // 会员等级
        public static final String CHANGEIMG = HOST + "new/headimg"; // 修改头像
        public static final String BRAND = HOST + "new/brand"; // 品牌
        public static final String ALLBRAND = HOST + "new/brands"; // 筛选-所有品牌
        public static final String ORDERDETAIL = HOST + "new/orderdetail"; // 订单详情
        public static final String BACKLIST = HOST + "new/goods_return_list"; // 退换货列表
        public static final String BACKFINISH = HOST + "new/goods_return_status"; // 退换货-完结
        public static final String NEWBACK = HOST + "new/goods_return"; // 退换货-新增
        public static final String FORGETPWD = HOST + "new/edit_pwd"; // 忘记密码
        public static final String WXPAY = "http://zjt.yiyuanjg.com/pay/wx/example/makeorder.php"; // 微信支付


        public static final String SENDSMS = "http://zjt.yiyuanjg.com/dx/msg.php"; // 短信
    }

//	public static void writeToLocal(String arg0, Context activity) {
//		try {
//			FileOutputStream openFileOutput = activity.openFileOutput("123.txt", 0); // 0代表MODE_PRIVATE
//			openFileOutput.write(arg0.getBytes());
//			openFileOutput.close();
//			Log.e("==", "writeSuccess" + arg0);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

    // 是否触发了被登陆
    public static boolean ISlogin = false;
    // 微信支付全局变量
    public static int wxpaycode = 10;
    // 注册成功判定
    public static boolean ISregisterus = true;
    // sp name
    public String spname = "com.togethermarried";
    // UID KEY
    public String SPUIDKEY = "login_uid";
    // 存储SID
    public SharedPreferences sp;
    // SharedPreferences的id
    public static String spName = "sid";
    // 用户积分
    String userScore;

    private static GlobalVariable objectClient = new GlobalVariable();

    public static GlobalVariable getInstance() {
        return objectClient;
    }

    public void setSp(SharedPreferences sp) {
        this.sp = sp;
    }

    public String getSpValue(String key, String defValue) {
        return sp.getString(key, defValue);
    }

    // sp存储用户sid
    public void setSpSid(String sid) {
        Editor edit = sp.edit();
        edit.putString("sid", sid);
        edit.commit();
    }

    // sp存储用户上次登陆名
    public void setOldName(String name) {
        Editor edit = sp.edit();
        edit.putString("oldName", name);
        edit.commit();
    }

    // 得到sp存储的登陆名
    public String getOldName(String defValue) {

        return sp.getString("oldName", defValue);
    }
    // sp存储用户上次登陆密码
    public void setOldPwd(String name) {
        Editor edit = sp.edit();
        edit.putString("oldPwd", name);
        edit.commit();
    }

    // 得到sp存储的登陆密码
    public String getOldPwd(String defValue) {
        return sp.getString("oldPwd", defValue);
    }

    // 得到sp存储的sid
    public String getSpSid(String defValue) {
        return sp.getString("sid", defValue);
    }

    // sp存储用户状态，0为客户，1为配送商，登陆后点击用户中心就默认进入该页面
    public void setSpLoginState(String loginState) {
        Editor edit = sp.edit();
        edit.putString("loginState", loginState);
        edit.commit();
    }

    // 得到sp存储的用户默认进入的页面，默认进入客户
    public String getSpLoginState() {
        if (sp == null) {
            return "0";
        }
        return sp.getString("loginState", "0");
    }

    // 被登陆后是否重新的登录
    Boolean ISRESTART = false;

    public Boolean getISRESTART() {
        return ISRESTART;
    }

    public void setISRESTART(Boolean iSRESTART) {
        ISRESTART = iSRESTART;
    }

    // 登录获得的uid
    String uid;
    // 是否需要wifi才下载图片
    Boolean iswifidome;

    public String getUid() {
        if (TextUtils.isEmpty(uid)) {
            return "";
        }
        return uid;
    }

    public Boolean getIswifidome() {
        if (iswifidome == null) {
            return false;
        }
        return iswifidome;
    }

    public void setIswifidome(Boolean iswifidome) {
        this.iswifidome = iswifidome;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    // 定位的经纬度
    private double Latitude;
    private double longitude;

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    // 是否自己选中城市
    boolean isseleortcity = false;

    public boolean getIsseleortcity() {
        return isseleortcity;
    }

    public void setIsseleortcity(boolean isseleortcity) {
        this.isseleortcity = isseleortcity;
    }

    // 选中的城市
    private String mycity = "厦门市";

    public String getMycity() {
        return mycity;
    }

    public void setMycity(String mycity) {
        this.mycity = mycity;
    }

}
