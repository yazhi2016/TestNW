package com.xmzlb.registermodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @Expose
    private String id;
    @Expose
    private String name;
    @SerializedName("identity_id")
    @Expose
    private String identityId;
    @SerializedName("rank_name")
    @Expose
    private String rankName;
    @SerializedName("rank_level")
    @Expose
    private Integer rankLevel;
    @SerializedName("collection_num")
    @Expose
    private String collectionNum;
    @Expose
    private String email;
    @SerializedName("real_name")
    @Expose
    private String realName;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @Expose
    private String city;
    @Expose
    private String headimg;
    @Expose
    private String card;
    @Expose
    private String licence;
    @SerializedName("order_num")
    @Expose
    private OrderNum orderNum;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The identityId
     */
    public String getIdentityId() {
        return identityId;
    }

    /**
     * 
     * @param identityId
     *     The identity_id
     */
    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    /**
     * 
     * @return
     *     The rankName
     */
    public String getRankName() {
        return rankName;
    }

    /**
     * 
     * @param rankName
     *     The rank_name
     */
    public void setRankName(String rankName) {
        this.rankName = rankName;
    }

    /**
     * 
     * @return
     *     The rankLevel
     */
    public Integer getRankLevel() {
        return rankLevel;
    }

    /**
     * 
     * @param rankLevel
     *     The rank_level
     */
    public void setRankLevel(Integer rankLevel) {
        this.rankLevel = rankLevel;
    }

    /**
     * 
     * @return
     *     The collectionNum
     */
    public String getCollectionNum() {
        return collectionNum;
    }

    /**
     * 
     * @param collectionNum
     *     The collection_num
     */
    public void setCollectionNum(String collectionNum) {
        this.collectionNum = collectionNum;
    }

    /**
     * 
     * @return
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * @return
     *     The realName
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 
     * @param realName
     *     The real_name
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 
     * @return
     *     The shopName
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 
     * @param shopName
     *     The shop_name
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
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
     *     The headimg
     */
    public String getHeadimg() {
        return headimg;
    }

    /**
     * 
     * @param headimg
     *     The headimg
     */
    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    /**
     * 
     * @return
     *     The card
     */
    public String getCard() {
        return card;
    }

    /**
     * 
     * @param card
     *     The card
     */
    public void setCard(String card) {
        this.card = card;
    }

    /**
     * 
     * @return
     *     The licence
     */
    public String getLicence() {
        return licence;
    }

    /**
     * 
     * @param licence
     *     The licence
     */
    public void setLicence(String licence) {
        this.licence = licence;
    }

    /**
     * 
     * @return
     *     The orderNum
     */
    public OrderNum getOrderNum() {
        return orderNum;
    }

    /**
     * 
     * @param orderNum
     *     The order_num
     */
    public void setOrderNum(OrderNum orderNum) {
        this.orderNum = orderNum;
    }

}
