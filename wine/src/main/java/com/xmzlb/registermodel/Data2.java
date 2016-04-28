package com.xmzlb.registermodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data2 {

    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("rank_points")
    @Expose
    private String rankPoints;
    @SerializedName("pay_points")
    @Expose
    private String payPoints;
    @SerializedName("rank_name")
    @Expose
    private String rankName;
    @Expose
    private String headimg;
    @SerializedName("order_num")
    @Expose
    private OrderNum2 orderNum;

    /**
     * 
     * @return
     *     The userName
     */
    public String getUserName() {
        return "用户名：" +userName;
    }

    /**
     * 
     * @param userName
     *     The user_name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 
     * @return
     *     The rankPoints
     */
    public String getRankPoints() {
        return "积分：" +rankPoints;
    }

    /**
     * 
     * @param rankPoints
     *     The rank_points
     */
    public void setRankPoints(String rankPoints) {
        this.rankPoints = rankPoints;
    }

    /**
     * 
     * @return
     *     The payPoints
     */
    public String getPayPoints() {
        return "￥" + payPoints;
    }

    /**
     * 
     * @param payPoints
     *     The pay_points
     */
    public void setPayPoints(String payPoints) {
        this.payPoints = payPoints;
    }

    /**
     * 
     * @return
     *     The rankName
     */
    public String getRankName() {
        return "等级：" +rankName;
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
     *     The orderNum
     */
    public OrderNum2 getOrderNum() {
        return orderNum;
    }

    /**
     * 
     * @param orderNum
     *     The order_num
     */
    public void setOrderNum(OrderNum2 orderNum) {
        this.orderNum = orderNum;
    }

}
