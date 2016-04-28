package com.xmzlb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum3 {

	@SerializedName("order_id")
	@Expose
	private String orderId;
	@SerializedName("order_sn")
	@Expose
	private String orderSn;
	@SerializedName("goods_amount")
	@Expose
	private String goodsAmount;
	@SerializedName("original_img")
	@Expose
	private String originalImg;
	@SerializedName("goods_name")
	@Expose
	private String goodsName;
	@SerializedName("goods_number")
	@Expose
	private String goodsNumber;
	@Expose
	private String bottle;
	@SerializedName("is_one")
	@Expose
	private String isOne;
	@SerializedName("full_money")
	@Expose
	private String fullMoney;
	@SerializedName("sub_money")
	@Expose
	private String subMoney;
	@Expose
	private String promotion;
	@SerializedName("goods_price")
	@Expose
	private String goodsPrice;
	@SerializedName("one_num")
	@Expose
	private Object oneNum;

	String goods_id;

	/**
	 * 
	 * @return The orderId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * 
	 * @param orderId
	 *            The order_id
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * 
	 * @return The orderSn
	 */
	public String getOrderSn() {
		return orderSn;
	}

	/**
	 * 
	 * @param orderSn
	 *            The order_sn
	 */
	public void setOrderSn(String orderSn) {
		this.orderSn = orderSn;
	}

	/**
	 * 
	 * @return The goodsAmount
	 */
	public String getGoodsAmount() {
		return goodsAmount;
	}

	/**
	 * 
	 * @param goodsAmount
	 *            The goods_amount
	 */
	public void setGoodsAmount(String goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	/**
	 * 
	 * @return The originalImg
	 */
	public String getOriginalImg() {
		return originalImg;
	}

	/**
	 * 
	 * @param originalImg
	 *            The original_img
	 */
	public void setOriginalImg(String originalImg) {
		this.originalImg = originalImg;
	}

	/**
	 * 
	 * @return The goodsName
	 */
	public String getGoodsName() {
		return goodsName;
	}

	/**
	 * 
	 * @return The goodsName
	 */
	public String getGoodsNumber() {
		return goodsNumber;
	}

	/**
	 * 
	 * @param goodsName
	 *            The goods_name
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 * 
	 * @return The bottle
	 */
	public String getBottle() {
		return bottle;
	}

	/**
	 * 
	 * @param bottle
	 *            The bottle
	 */
	public void setBottle(String bottle) {
		this.bottle = bottle;
	}

	/**
	 * 
	 * @return The isOne
	 */
	public String getIsOne() {
		return isOne;
	}

	/**
	 * 
	 * @param isOne
	 *            The is_one
	 */
	public void setIsOne(String isOne) {
		this.isOne = isOne;
	}

	/**
	 * 
	 * @return The fullMoney
	 */
	public String getFullMoney() {
		return fullMoney;
	}

	/**
	 * 
	 * @param fullMoney
	 *            The full_money
	 */
	public void setFullMoney(String fullMoney) {
		this.fullMoney = fullMoney;
	}

	/**
	 * 
	 * @return The subMoney
	 */
	public String getSubMoney() {
		return subMoney;
	}

	/**
	 * 
	 * @param subMoney
	 *            The sub_money
	 */
	public void setSubMoney(String subMoney) {
		this.subMoney = subMoney;
	}

	/**
	 * 
	 * @return The promotion
	 */
	public String getPromotion() {
		return promotion;
	}

	/**
	 * 
	 * @param promotion
	 *            The promotion
	 */
	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

	public String getGoodsId() {
		return goods_id;
	}

	public void setGoodsId(String goods_id) {
		this.goods_id = goods_id;
	}

	/**
	 * 
	 * @return The goodsPrice
	 */
	public String getGoodsPrice() {
		return goodsPrice;
	}

	/**
	 * 
	 * @param goodsPrice
	 *            The goods_price
	 */
	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	/**
	 * 
	 * @return The oneNum
	 */
	public Object getOneNum() {
		return oneNum;
	}

	/**
	 * 
	 * @param oneNum
	 *            The one_num
	 */
	public void setOneNum(Object oneNum) {
		this.oneNum = oneNum;
	}

}
