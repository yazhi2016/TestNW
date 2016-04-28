package com.xmzlb.model;

import com.google.gson.annotations.Expose;

public class Sellerinfo {

    @Expose
    private Data7 data;
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public Data7 getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Data7 data) {
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
