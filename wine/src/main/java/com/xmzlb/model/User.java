package com.xmzlb.model;

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
