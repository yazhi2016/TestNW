package com.xmzlb.mainmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ad {

    @SerializedName("ad_link")
    @Expose
    private String adLink;
    @SerializedName("ad_code")
    @Expose
    private String adCode;
    @SerializedName("end_time")
    @Expose
    private String endTime;

    /**
     * 
     * @return
     *     The adLink
     */
    public String getAdLink() {
        return adLink;
    }

    /**
     * 
     * @param adLink
     *     The ad_link
     */
    public void setAdLink(String adLink) {
        this.adLink = adLink;
    }

    /**
     * 
     * @return
     *     The adCode
     */
    public String getAdCode() {
        return adCode;
    }

    /**
     * 
     * @param adCode
     *     The ad_code
     */
    public void setAdCode(String adCode) {
        this.adCode = adCode;
    }

    /**
     * 
     * @return
     *     The endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * 
     * @param endTime
     *     The end_time
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

}
