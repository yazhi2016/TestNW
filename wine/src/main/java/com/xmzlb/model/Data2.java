package com.xmzlb.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data2 {

    @SerializedName("goods_id")
    @Expose
    private String goodsId;
    @SerializedName("goods_img")
    @Expose
    private List<String> goodsImg = new ArrayList<String>();
    @SerializedName("goods_name")
    @Expose
    private String goodsName;
    @SerializedName("is_one")
    @Expose
    private String isOne;
    @Expose
    private String money;
    @Expose
    private List<Member> member = new ArrayList<Member>();
    @Expose
    private Integer integral;
    @SerializedName("goods_desc")
    @Expose
    private String goodsDesc;

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
     *     The member
     */
    public List<Member> getMember() {
        return member;
    }

    /**
     * 
     * @param member
     *     The member
     */
    public void setMember(List<Member> member) {
        this.member = member;
    }

    /**
     * 
     * @return
     *     The integral
     */
    public Integer getIntegral() {
        return integral;
    }

    /**
     * 
     * @param integral
     *     The integral
     */
    public void setIntegral(Integer integral) {
        this.integral = integral;
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
