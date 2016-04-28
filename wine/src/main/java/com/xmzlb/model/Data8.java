package com.xmzlb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data8 {

    @SerializedName("order_sn")
    @Expose
    private String orderSn;
    @Expose
    private String consignee;
    @Expose
    private String mobile;
    @Expose
    private String address;
    @SerializedName("goods_img")
    @Expose
    private String goodsImg;
    @SerializedName("goods_name")
    @Expose
    private String goodsName;
    @Expose
    private String bottle;
    @SerializedName("one_num")
    @Expose
    private String oneNum;
    @SerializedName("full_money")
    @Expose
    private String fullMoney;
    @SerializedName("sub_money")
    @Expose
    private String subMoney;
    @Expose
    private String promotion;
    @SerializedName("goods_number")
    @Expose
    private String goodsNumber;
    @SerializedName("order_money")
    @Expose
    private String orderMoney;
    @SerializedName("pay_money")
    @Expose
    private String payMoney;
    @SerializedName("no_pay")
    @Expose
    private Double noPay;
    @SerializedName("shipping_status")
    @Expose
    private String shippingStatus;
    @SerializedName("distribution_money")
    @Expose
    private String distributionMoney;
    @SerializedName("logistice_money")
    @Expose
    private String logisticeMoney;
    @SerializedName("all_money")
    @Expose
    private String allMoney;
    @Expose
    private String settlement;

    /**
     * 
     * @return
     *     The orderSn
     */
    public String getOrderSn() {
        return orderSn;
    }

    /**
     * 
     * @param orderSn
     *     The order_sn
     */
    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    /**
     * 
     * @return
     *     The consignee
     */
    public String getConsignee() {
        return consignee;
    }

    /**
     * 
     * @param consignee
     *     The consignee
     */
    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    /**
     * 
     * @return
     *     The mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 
     * @param mobile
     *     The mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 
     * @return
     *     The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * @return
     *     The goodsImg
     */
    public String getGoodsImg() {
        return goodsImg;
    }

    /**
     * 
     * @param goodsImg
     *     The goods_img
     */
    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    /**
     * 
     * @return
     *     The goodsName
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * 
     * @param goodsName
     *     The goods_name
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * 
     * @return
     *     The bottle
     */
    public String getBottle() {
        return bottle;
    }

    /**
     * 
     * @param bottle
     *     The bottle
     */
    public void setBottle(String bottle) {
        this.bottle = bottle;
    }

    /**
     * 
     * @return
     *     The oneNum
     */
    public String getOneNum() {
        return oneNum;
    }

    /**
     * 
     * @param oneNum
     *     The one_num
     */
    public void setOneNum(String oneNum) {
        this.oneNum = oneNum;
    }

    /**
     * 
     * @return
     *     The fullMoney
     */
    public String getFullMoney() {
        return fullMoney;
    }

    /**
     * 
     * @param fullMoney
     *     The full_money
     */
    public void setFullMoney(String fullMoney) {
        this.fullMoney = fullMoney;
    }

    /**
     * 
     * @return
     *     The subMoney
     */
    public String getSubMoney() {
        return subMoney;
    }

    /**
     * 
     * @param subMoney
     *     The sub_money
     */
    public void setSubMoney(String subMoney) {
        this.subMoney = subMoney;
    }

    /**
     * 
     * @return
     *     The promotion
     */
    public String getPromotion() {
        return promotion;
    }

    /**
     * 
     * @param promotion
     *     The promotion
     */
    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    /**
     * 
     * @return
     *     The goodsNumber
     */
    public String getGoodsNumber() {
        return goodsNumber;
    }

    /**
     * 
     * @param goodsNumber
     *     The goods_number
     */
    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    /**
     * 
     * @return
     *     The orderMoney
     */
    public String getOrderMoney() {
        return orderMoney;
    }

    /**
     * 
     * @param orderMoney
     *     The order_money
     */
    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    /**
     * 
     * @return
     *     The payMoney
     */
    public String getPayMoney() {
        return payMoney;
    }

    /**
     * 
     * @param payMoney
     *     The pay_money
     */
    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    /**
     * 
     * @return
     *     The noPay
     */
    public Double getNoPay() {
        return noPay;
    }

    /**
     * 
     * @param noPay
     *     The no_pay
     */
    public void setNoPay(Double noPay) {
        this.noPay = noPay;
    }

    /**
     * 
     * @return
     *     The shippingStatus
     */
    public String getShippingStatus() {
        return shippingStatus;
    }

    /**
     * 
     * @param shippingStatus
     *     The shipping_status
     */
    public void setShippingStatus(String shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    /**
     * 
     * @return
     *     The distributionMoney
     */
    public String getDistributionMoney() {
        return distributionMoney;
    }

    /**
     * 
     * @param distributionMoney
     *     The distribution_money
     */
    public void setDistributionMoney(String distributionMoney) {
        this.distributionMoney = distributionMoney;
    }

    /**
     * 
     * @return
     *     The logisticeMoney
     */
    public String getLogisticeMoney() {
        return logisticeMoney;
    }

    /**
     * 
     * @param logisticeMoney
     *     The logistice_money
     */
    public void setLogisticeMoney(String logisticeMoney) {
        this.logisticeMoney = logisticeMoney;
    }

    /**
     * 
     * @return
     *     The allMoney
     */
    public String getAllMoney() {
        return allMoney;
    }

    /**
     * 
     * @param allMoney
     *     The all_money
     */
    public void setAllMoney(String allMoney) {
        this.allMoney = allMoney;
    }

    /**
     * 
     * @return
     *     The settlement
     */
    public String getSettlement() {
        return settlement;
    }

    /**
     * 
     * @param settlement
     *     The settlement
     */
    public void setSettlement(String settlement) {
        this.settlement = settlement;
    }

}
