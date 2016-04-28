package com.xmzlb.model;

import com.google.gson.annotations.Expose;

public class Userinfodetail {

    @Expose
    private Data6 data;
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public Data6 getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Data6 data) {
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
