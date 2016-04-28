package com.xmzlb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StatusFail {

    @Expose
    private Integer succeed;
    @SerializedName("error_code")
    @Expose
    private Integer errorCode;
    @SerializedName("error_desc")
    @Expose
    private String errorDesc;

    /**
     * 
     * @return
     *     The succeed
     */
    public Integer getSucceed() {
        return succeed;
    }

    /**
     * 
     * @param succeed
     *     The succeed
     */
    public void setSucceed(Integer succeed) {
        this.succeed = succeed;
    }

    /**
     * 
     * @return
     *     The errorCode
     */
    public Integer getErrorCode() {
        return errorCode;
    }

    /**
     * 
     * @param errorCode
     *     The error_code
     */
    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 
     * @return
     *     The errorDesc
     */
    public String getErrorDesc() {
        return errorDesc;
    }

    /**
     * 
     * @param errorDesc
     *     The error_desc
     */
    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

}
