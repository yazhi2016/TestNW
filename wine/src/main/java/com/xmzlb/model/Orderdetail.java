package com.xmzlb.model;

import com.google.gson.annotations.Expose;

public class Orderdetail {

    @Expose
    private Data10 data;
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public Data10 getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Data10 data) {
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
