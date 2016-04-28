package com.xmzlb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum6 {

    @SerializedName("ad_code")
    @Expose
    private String adCode;

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
