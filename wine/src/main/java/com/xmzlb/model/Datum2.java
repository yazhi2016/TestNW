package com.xmzlb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum2 {

    @SerializedName("rec_id")
    @Expose
    private String recId;
    @SerializedName("goods_id")
    @Expose
    private String goodsId;
    @SerializedName("goods_number")
    @Expose
    private String goodsNumber;
    @SerializedName("goods_attr_id")
    @Expose
    private String goodsAttrId;
    @SerializedName("is_on_sale")
    @Expose
    private String isOnSale;
    @SerializedName("is_delete")
    @Expose
    private String isDelete;
    @SerializedName("goods_name")
    @Expose
    private String goodsName;
    @SerializedName("original_img")
    @Expose
    private String originalImg;
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
    private Object promotion;
    @SerializedName("one_num")
    @Expose
    private Object oneNum;
    @SerializedName("shop_price")
    @Expose
    private String shopPrice;

    /**
     * 
     * @return
     *     The recId
     */
    public String getRecId() {
        return recId;
    }

    /**
     * 
     * @param recId
     *     The rec_id
     */
    public void setRecId(String recId) {
        this.recId = recId;
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
     *     The goodsAttrId
     */
    public String getGoodsAttrId() {
        return goodsAttrId;
    }

    /**
     * 
     * @param goodsAttrId
     *     The goods_attr_id
     */
    public void setGoodsAttrId(String goodsAttrId) {
        this.goodsAttrId = goodsAttrId;
    }

    /**
     * 
     * @return
     *     The isOnSale
     */
    public String getIsOnSale() {
        return isOnSale;
    }

    /**
     * 
     * @param isOnSale
     *     The is_on_sale
     */
    public void setIsOnSale(String isOnSale) {
        this.isOnSale = isOnSale;
    }

    /**
     * 
     * @return
     *     The isDelete
     */
    public String getIsDelete() {
        return isDelete;
    }

    /**
     * 
     * @param isDelete
     *     The is_delete
     */
    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
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
     *     The isOne
     */
    public String getIsOne() {
        return isOne;
    }

    /**
     * 
     * @param isOne
     *     The is_one
     */
    public void setIsOne(String isOne) {
        this.isOne = isOne;
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
    public Object getPromotion() {
        return promotion;
    }

    /**
     * 
     * @param promotion
     *     The promotion
     */
    public void setPromotion(Object promotion) {
        this.promotion = promotion;
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
     *     The shopPrice
     */
    public String getShopPrice() {
        return shopPrice;
    }

    /**
     * 
     * @param shopPrice
     *     The shop_price
     */
    public void setShopPrice(String shopPrice) {
        this.shopPrice = shopPrice;
    }

}
