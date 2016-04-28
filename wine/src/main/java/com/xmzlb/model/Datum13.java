package com.xmzlb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum13 {

    @SerializedName("rank_id")
    @Expose
    private String rankId;
    @SerializedName("rank_name")
    @Expose
    private String rankName;
    @SerializedName("min_points")
    @Expose
    private String minPoints;
    @SerializedName("max_points")
    @Expose
    private String maxPoints;

    /**
     * 
     * @return
     *     The rankId
     */
    public String getRankId() {
        return rankId;
    }

    /**
     * 
     * @param rankId
     *     The rank_id
     */
    public void setRankId(String rankId) {
        this.rankId = rankId;
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
     *     The minPoints
     */
    public String getMinPoints() {
        return minPoints;
    }

    /**
     * 
     * @param minPoints
     *     The min_points
     */
    public void setMinPoints(String minPoints) {
        this.minPoints = minPoints;
    }

    /**
     * 
     * @return
     *     The maxPoints
     */
    public String getMaxPoints() {
        return maxPoints;
    }

    /**
     * 
     * @param maxPoints
     *     The max_points
     */
    public void setMaxPoints(String maxPoints) {
        this.maxPoints = maxPoints;
    }

}
