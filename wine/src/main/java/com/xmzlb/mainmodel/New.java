package com.xmzlb.mainmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class New {

    @SerializedName("ad_link")
    @Expose
    private String adLink;
    @SerializedName("ad_code")
    @Expose
    private String adCode;

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

}
