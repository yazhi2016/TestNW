package com.xmzlb.model;

import com.google.gson.annotations.Expose;

public class StatusSignIn {

    @Expose
    private Integer succeed;

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

}
