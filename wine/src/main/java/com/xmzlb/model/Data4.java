package com.xmzlb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data4 {

    @SerializedName("goods_price")
    @Expose
    private String goodsPrice;
    @SerializedName("market_price")
    @Expose
    private String marketPrice;
    @Expose
    private String saving;
    @SerializedName("save_rate")
    @Expose
    private Integer saveRate;
    @SerializedName("goods_amount")
    @Expose
    private Double goodsAmount;
    @SerializedName("real_goods_count")
    @Expose
    private Integer realGoodsCount;
    @SerializedName("virtual_goods_count")
    @Expose
    private Integer virtualGoodsCount;

    /**
     * 
     * @return
     *     The goodsPrice
     */
    public String getGoodsPrice() {
        return goodsPrice;
    }

    /**
     * 
     * @param goodsPrice
     *     The goods_price
     */
    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    /**
     * 
     * @return
     *     The marketPrice
     */
    public String getMarketPrice() {
        return marketPrice;
    }

    /**
     * 
     * @param marketPrice
     *     The market_price
     */
    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }

    /**
     * 
     * @return
     *     The saving
     */
    public String getSaving() {
        return saving;
    }

    /**
     * 
     * @param saving
     *     The saving
     */
    public void setSaving(String saving) {
        this.saving = saving;
    }

    /**
     * 
     * @return
     *     The saveRate
     */
    public Integer getSaveRate() {
        return saveRate;
    }

    /**
     * 
     * @param saveRate
     *     The save_rate
     */
    public void setSaveRate(Integer saveRate) {
        this.saveRate = saveRate;
    }

    /**
     * 
     * @return
     *     The goodsAmount
     */
    public Double getGoodsAmount() {
        return goodsAmount;
    }

    /**
     * 
     * @param goodsAmount
     *     The goods_amount
     */
    public void setGoodsAmount(Double goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    /**
     * 
     * @return
     *     The realGoodsCount
     */
    public Integer getRealGoodsCount() {
        return realGoodsCount;
    }

    /**
     * 
     * @param realGoodsCount
     *     The real_goods_count
     */
    public void setRealGoodsCount(Integer realGoodsCount) {
        this.realGoodsCount = realGoodsCount;
    }

    /**
     * 
     * @return
     *     The virtualGoodsCount
     */
    public Integer getVirtualGoodsCount() {
        return virtualGoodsCount;
    }

    /**
     * 
     * @param virtualGoodsCount
     *     The virtual_goods_count
     */
    public void setVirtualGoodsCount(Integer virtualGoodsCount) {
        this.virtualGoodsCount = virtualGoodsCount;
    }

}
