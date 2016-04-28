package com.xmzlb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum10 {

    @SerializedName("order_sn")
    @Expose
    private String orderSn;
    @SerializedName("goods_name")
    @Expose
    private String goodsName;
    @SerializedName("goods_number")
    @Expose
    private String goodsNumber;
    @Expose
    private String bottle;

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

}
