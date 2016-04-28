package com.xmzlb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data10 {

    @SerializedName("order_sn")
    @Expose
    private String orderSn;
    @Expose
    private String consignee;
    @Expose
    private String mobile;
    @Expose
    private String province;
    @Expose
    private String city;
    @Expose
    private String district;
    @Expose
    private String address;
    @SerializedName("original_img")
    @Expose
    private String originalImg;
    @SerializedName("goods_id")
    @Expose
    private String goodsId;
    @SerializedName("goods_name")
    @Expose
    private String goodsName;
    @Expose
    private String bottle;
    @SerializedName("goods_number")
    @Expose
    private String goodsNumber;
    @SerializedName("one_num")
    @Expose
    private Object oneNum;
    @SerializedName("full_money")
    @Expose
    private Integer fullMoney;
    @SerializedName("sub_money")
    @Expose
    private Integer subMoney;
    @Expose
    private String promotion;
    @Expose
    private String money;
    @Expose
    private Integer status;
    @SerializedName("logistice_shop_name")
    @Expose
    private String logisticeShopName;
    @SerializedName("logistice_real_name")
    @Expose
    private String logisticeRealName;
    @SerializedName("logistice_mobile_phone")
    @Expose
    private String logisticeMobilePhone;
    @SerializedName("logistice_province")
    @Expose
    private String logisticeProvince;
    @SerializedName("logistice_city")
    @Expose
    private String logisticeCity;
    @SerializedName("logistice_district")
    @Expose
    private String logisticeDistrict;
    @SerializedName("logistice_address")
    @Expose
    private String logisticeAddress;

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
     *     The province
     */
    public String getProvince() {
        return province;
    }

    /**
     * 
     * @param province
     *     The province
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 
     * @return
     *     The city
     */
    public String getCity() {
        return city;
    }

    /**
     * 
     * @param city
     *     The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 
     * @return
     *     The district
     */
    public String getDistrict() {
        return district;
    }

    /**
     * 
     * @param district
     *     The district
     */
    public void setDistrict(String district) {
        this.district = district;
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
     *     The originalImg
     */
    public String getOriginalImg() {
        return originalImg;
    }

    /**
     * 
     * @param originalImg
     *     The original_img
     */
    public void setOriginalImg(String originalImg) {
        this.originalImg = originalImg;
    }

    /**
     * 
     * @return
     *     The goodsId
     */
    public String getGoodsId() {
        return goodsId;
    }

    /**
     * 
     * @param goodsId
     *     The goods_id
     */
    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
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
     *     The oneNum
     */
    public Object getOneNum() {
        return oneNum;
    }

    /**
     * 
     * @param oneNum
     *     The one_num
     */
    public void setOneNum(Object oneNum) {
        this.oneNum = oneNum;
    }

    /**
     * 
     * @return
     *     The fullMoney
     */
    public Integer getFullMoney() {
        return fullMoney;
    }

    /**
     * 
     * @param fullMoney
     *     The full_money
     */
    public void setFullMoney(Integer fullMoney) {
        this.fullMoney = fullMoney;
    }

    /**
     * 
     * @return
     *     The subMoney
     */
    public Integer getSubMoney() {
        return subMoney;
    }

    /**
     * 
     * @param subMoney
     *     The sub_money
     */
    public void setSubMoney(Integer subMoney) {
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
     *     The money
     */
    public String getMoney() {
        return money;
    }

    /**
     * 
     * @param money
     *     The money
     */
    public void setMoney(String money) {
        this.money = money;
    }

    /**
     * 
     * @return
     *     The status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The logisticeShopName
     */
    public String getLogisticeShopName() {
        return logisticeShopName;
    }

    /**
     * 
     * @param logisticeShopName
     *     The logistice_shop_name
     */
    public void setLogisticeShopName(String logisticeShopName) {
        this.logisticeShopName = logisticeShopName;
    }

    /**
     * 
     * @return
     *     The logisticeRealName
     */
    public String getLogisticeRealName() {
        return logisticeRealName;
    }

    /**
     * 
     * @param logisticeRealName
     *     The logistice_real_name
     */
    public void setLogisticeRealName(String logisticeRealName) {
        this.logisticeRealName = logisticeRealName;
    }

    /**
     * 
     * @return
     *     The logisticeMobilePhone
     */
    public String getLogisticeMobilePhone() {
        return logisticeMobilePhone;
    }

    /**
     * 
     * @param logisticeMobilePhone
     *     The logistice_mobile_phone
     */
    public void setLogisticeMobilePhone(String logisticeMobilePhone) {
        this.logisticeMobilePhone = logisticeMobilePhone;
    }

    /**
     * 
     * @return
     *     The logisticeProvince
     */
    public String getLogisticeProvince() {
        return logisticeProvince;
    }

    /**
     * 
     * @param logisticeProvince
     *     The logistice_province
     */
    public void setLogisticeProvince(String logisticeProvince) {
        this.logisticeProvince = logisticeProvince;
    }

    /**
     * 
     * @return
     *     The logisticeCity
     */
    public String getLogisticeCity() {
        return logisticeCity;
    }

    /**
     * 
     * @param logisticeCity
     *     The logistice_city
     */
    public void setLogisticeCity(String logisticeCity) {
        this.logisticeCity = logisticeCity;
    }

    /**
     * 
     * @return
     *     The logisticeDistrict
     */
    public String getLogisticeDistrict() {
        return logisticeDistrict;
    }

    /**
     * 
     * @param logisticeDistrict
     *     The logistice_district
     */
    public void setLogisticeDistrict(String logisticeDistrict) {
        this.logisticeDistrict = logisticeDistrict;
    }

    /**
     * 
     * @return
     *     The logisticeAddress
     */
    public String getLogisticeAddress() {
        return logisticeAddress;
    }

    /**
     * 
     * @param logisticeAddress
     *     The logistice_address
     */
    public void setLogisticeAddress(String logisticeAddress) {
        this.logisticeAddress = logisticeAddress;
    }

}
