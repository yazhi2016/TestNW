package com.xmzlb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data7 {

	@Expose
	private String headimg;
	@SerializedName("shop_name")
	@Expose
	private String shopName;
	@SerializedName("real_name")
	@Expose
	private String realName;
	@SerializedName("mobile_phone")
	@Expose
	private String mobilePhone;
	@Expose
	private String address;
	@SerializedName("all_order")
	@Expose
	private String allOrder;
	@SerializedName("all_money")
	@Expose
	private Double allMoney;
	@SerializedName("month_order")
	@Expose
	private String monthOrder;
	@SerializedName("month_money")
	@Expose
	private Double monthMoney;

	/**
	 * 
	 * @return The headimg
	 */
	public String getHeadimg() {
		return headimg;
	}

	/**
	 * 
	 * @param headimg
	 *            The headimg
	 */
	public void setHeadimg(String headimg) {
		this.headimg = headimg;
	}

	/**
	 * 
	 * @return The shopName
	 */
	public String getShopName() {
		return shopName;
	}

	/**
	 * 
	 * @param shopName
	 *            The shop_name
	 */
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	/**
	 * 
	 * @return The realName
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * 
	 * @param realName
	 *            The real_name
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * 
	 * @return The mobilePhone
	 */
	public String getMobilePhone() {
		return mobilePhone;
	}

	/**
	 * 
	 * @param mobilePhone
	 *            The mobile_phone
	 */
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	/**
	 * 
	 * @return The address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 
	 * @param address
	 *            The address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 
	 * @return The allOrder
	 */
	public String getAllOrder() {
		return allOrder;
	}

	/**
	 * 
	 * @param allOrder
	 *            The all_order
	 */
	public void setAllOrder(String allOrder) {
		this.allOrder = allOrder;
	}

	/**
	 * 
	 * @return The allMoney
	 */
	public Double getAllMoney() {
		return allMoney;
	}

	/**
	 * 
	 * @param allMoney
	 *            The all_money
	 */
	public void setAllMoney(Double allMoney) {
		this.allMoney = allMoney;
	}

	/**
	 * 
	 * @return The monthOrder
	 */
	public String getMonthOrder() {
		return monthOrder;
	}

	/**
	 * 
	 * @param monthOrder
	 *            The month_order
	 */
	public void setMonthOrder(String monthOrder) {
		this.monthOrder = monthOrder;
	}

	/**
	 * 
	 * @return The monthMoney
	 */
	public Double getMonthMoney() {
		return monthMoney;
	}

	/**
	 * 
	 * @param monthMoney
	 *            The month_money
	 */
	public void setMonthMoney(Double monthMoney) {
		this.monthMoney = monthMoney;
	}

}
