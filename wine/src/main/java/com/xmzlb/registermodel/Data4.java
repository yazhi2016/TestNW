package com.xmzlb.registermodel;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data4 {

    @SerializedName("goods_id")
    @Expose
    private String goodsId;
    @SerializedName("goods_img")
    @Expose
    private List<String> goodsImg = new ArrayList<String>();
    @SerializedName("goods_name")
    @Expose
    private String goodsName;
    @SerializedName("shop_price")
    @Expose
    private String shopPrice;
    @SerializedName("one_price")
    @Expose
    private String onePrice;
    @SerializedName("more_price")
    @Expose
    private String morePrice;
    @SerializedName("is_one")
    @Expose
    private String isOne;
    @SerializedName("more_num")
    @Expose
    private String moreNum;
    @SerializedName("one_num")
    @Expose
    private String oneNum;
    @Expose
    private String integration;
    @SerializedName("more_inventory")
    @Expose
    private String moreInventory;
    @Expose
    private String promotion;
    @SerializedName("full_money")
    @Expose
    private String fullMoney;
    @SerializedName("sub_money")
    @Expose
    private String subMoney;
    @SerializedName("click_count")
    @Expose
    private String clickCount;
    @Expose
    private String brand;
    @Expose
    private String category;
    @SerializedName("category_name")
    @Expose
    private String categoryName;
    @Expose
    private String degree;
    @Expose
    private String capacity;
    @Expose
    private String fragrance;
    @Expose
    private String origin;
    @Expose
    private String manufacturer;
    @SerializedName("shelf_life")
    @Expose
    private String shelfLife;
    @Expose
    private String packaging;
    @Expose
    private String collection;
    @Expose
    private String like;
    @Expose
    private String other;
    @SerializedName("goods_desc")
    @Expose
    private String goodsDesc;

    /**
     * 
     * @return
     *     The like
     */
    public String getLike() {
    	return like;
    }

    /**
     * 
     * @param like
     *     The like
     */
    public void setLike(String like) {
    	this.like = like;
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
     *     The goodsImg
     */
    public List<String> getGoodsImg() {
        return goodsImg;
    }

    /**
     * 
     * @param goodsImg
     *     The goods_img
     */
    public void setGoodsImg(List<String> goodsImg) {
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
     *     The onePrice
     */
    public String getOnePrice() {
        return onePrice;
    }

    /**
     * 
     * @param onePrice
     *     The one_price
     */
    public void setOnePrice(String onePrice) {
        this.onePrice = onePrice;
    }

    /**
     * 
     * @return
     *     The morePrice
     */
    public String getMorePrice() {
        return morePrice;
    }

    /**
     * 
     * @param morePrice
     *     The more_price
     */
    public void setMorePrice(String morePrice) {
        this.morePrice = morePrice;
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
     *     The moreNum
     */
    public String getMoreNum() {
        return moreNum;
    }

    /**
     * 
     * @param moreNum
     *     The more_num
     */
    public void setMoreNum(String moreNum) {
        this.moreNum = moreNum;
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
     *     The integration
     */
    public String getIntegration() {
        return integration;
    }

    /**
     * 
     * @param integration
     *     The integration
     */
    public void setIntegration(String integration) {
        this.integration = integration;
    }

    /**
     * 
     * @return
     *     The moreInventory
     */
    public String getMoreInventory() {
        return moreInventory;
    }

    /**
     * 
     * @param moreInventory
     *     The more_inventory
     */
    public void setMoreInventory(String moreInventory) {
        this.moreInventory = moreInventory;
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
     *     The brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * 
     * @param brand
     *     The brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * 
     * @return
     *     The category
     */
    public String getCategory() {
        return category;
    }

    /**
     * 
     * @param category
     *     The category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 
     * @return
     *     The categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * 
     * @param categoryName
     *     The category_name
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * 
     * @return
     *     The degree
     */
    public String getDegree() {
        return degree;
    }

    /**
     * 
     * @param degree
     *     The degree
     */
    public void setDegree(String degree) {
        this.degree = degree;
    }

    /**
     * 
     * @return
     *     The capacity
     */
    public String getCapacity() {
        return capacity;
    }

    /**
     * 
     * @param capacity
     *     The capacity
     */
    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    /**
     * 
     * @return
     *     The fragrance
     */
    public String getFragrance() {
        return fragrance;
    }

    /**
     * 
     * @param fragrance
     *     The fragrance
     */
    public void setFragrance(String fragrance) {
        this.fragrance = fragrance;
    }

    /**
     * 
     * @return
     *     The origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * 
     * @param origin
     *     The origin
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }

    /**
     * 
     * @return
     *     The manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * 
     * @param manufacturer
     *     The manufacturer
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * 
     * @return
     *     The shelfLife
     */
    public String getShelfLife() {
        return shelfLife;
    }

    /**
     * 
     * @param shelfLife
     *     The shelf_life
     */
    public void setShelfLife(String shelfLife) {
        this.shelfLife = shelfLife;
    }

    /**
     * 
     * @return
     *     The packaging
     */
    public String getPackaging() {
        return packaging;
    }
    /**
     * 
     * @return
     *     The collection
     */
    public String getCollection() {
    	return collection;
    }

    /**
     * 
     * @param collection
     *     The collection
     */
    public void setCollection(String collection) {
    	this.collection = collection;
    }
    /**
     * 
     * @param packaging
     *     The packaging
     */
    public void setPackaging(String packaging) {
        this.packaging = packaging;
    }

    /**
     * 
     * @return
     *     The other
     */
    public String getOther() {
        return other;
    }

    /**
     * 
     * @param other
     *     The other
     */
    public void setOther(String other) {
        this.other = other;
    }

    /**
     * 
     * @return
     *     The goodsDesc
     */
    public String getGoodsDesc() {
        return goodsDesc;
    }

    /**
     * 
     * @param goodsDesc
     *     The goods_desc
     */
    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

}
