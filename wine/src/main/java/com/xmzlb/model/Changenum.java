package com.xmzlb.model;

import com.google.gson.annotations.Expose;

public class Changenum {

    @Expose
    private Data4 data;
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public Data4 getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Data4 data) {
        this.data = data;
    }

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
