package com.xmzlb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum8 {

    @SerializedName("change_time")
    @Expose
    private String changeTime;
    @SerializedName("change_desc")
    @Expose
    private String changeDesc;
    @SerializedName("rank_points")
    @Expose
    private String rankPoints;

    /**
     * 
     * @return
     *     The changeTime
     */
    public String getChangeTime() {
        return changeTime;
    }

    /**
     * 
     * @param changeTime
     *     The change_time
     */
    public void setChangeTime(String changeTime) {
        this.changeTime = changeTime;
    }

    /**
     * 
     * @return
     *     The changeDesc
     */
    public String getChangeDesc() {
        return changeDesc;
    }

    /**
     * 
     * @param changeDesc
     *     The change_desc
     */
    public void setChangeDesc(String changeDesc) {
        this.changeDesc = changeDesc;
    }

    /**
     * 
     * @return
     *     The rankPoints
     */
    public String getRankPoints() {
        return rankPoints;
    }

    /**
     * 
     * @param rankPoints
     *     The rank_points
     */
    public void setRankPoints(String rankPoints) {
        this.rankPoints = rankPoints;
    }

}
