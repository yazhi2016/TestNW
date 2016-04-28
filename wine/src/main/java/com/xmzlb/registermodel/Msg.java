package com.xmzlb.registermodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Msg {

    @SerializedName("goods_name")
    @Expose
    private String goodsName;
    @SerializedName("original_img")
    @Expose
    private String originalImg;
    @SerializedName("shop_price")
    @Expose
    private String shopPrice;
    @SerializedName("is_one")
    @Expose
    private String isOne;

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

}
