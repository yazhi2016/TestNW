package com.xmzlb.registermodel;

import com.google.gson.annotations.Expose;

public class Userinfo {

    @Expose
    private Data2 data;
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public Data2 getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Data2 data) {
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
