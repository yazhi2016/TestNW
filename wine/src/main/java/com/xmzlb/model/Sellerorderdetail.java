package com.xmzlb.model;

import com.google.gson.annotations.Expose;

public class Sellerorderdetail {

    @Expose
    private Data8 data;
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public Data8 getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Data8 data) {
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
