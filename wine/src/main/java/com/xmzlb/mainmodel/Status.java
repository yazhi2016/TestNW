package com.xmzlb.mainmodel;

import com.google.gson.annotations.Expose;

public class Status {

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
