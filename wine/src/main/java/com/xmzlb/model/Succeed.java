package com.xmzlb.model;

import com.google.gson.annotations.Expose;

public class Succeed {

    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

}
