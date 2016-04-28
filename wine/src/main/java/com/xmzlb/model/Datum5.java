package com.xmzlb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum5 {

    @SerializedName("goods_id")
    @Expose
    private String goodsId;
    @SerializedName("goods_name")
    @Expose
    private String goodsName;
    @SerializedName("original_img")
    @Expose
    private String originalImg;
    @SerializedName("shop_price")
    @Expose
    private String shopPrice;
    @SerializedName("click_count")
    @Expose
    private String clickCount;
    @Expose
    private Integer count;

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

    /**
     * 
     * @return
     *     The clickCount
     */
    public String getClickCount() {
        return clickCount;
    }

    /**
     * 
     * @param clickCount
     *     The click_count
     */
    public void setClickCount(String clickCount) {
        this.clickCount = clickCount;
    }

    /**
     * 
     * @return
     *     The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 
     * @param count
     *     The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

}
