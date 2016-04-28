package com.xmzlb.registermodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum2 {

    @SerializedName("goods_id")
    @Expose
    private String goodsId;
    @SerializedName("exchange_integral")
    @Expose
    private String exchangeIntegral;
    @Expose
    private Msg msg;

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
     *     The exchangeIntegral
     */
    public String getExchangeIntegral() {
        return exchangeIntegral;
    }

    /**
     * 
     * @param exchangeIntegral
     *     The exchange_integral
     */
    public void setExchangeIntegral(String exchangeIntegral) {
        this.exchangeIntegral = exchangeIntegral;
    }

    /**
     * 
     * @return
     *     The msg
     */
    public Msg getMsg() {
        return msg;
    }

    /**
     * 
     * @param msg
     *     The msg
     */
    public void setMsg(Msg msg) {
        this.msg = msg;
    }

}
