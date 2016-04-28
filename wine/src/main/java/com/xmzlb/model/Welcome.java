package com.xmzlb.model;

import com.google.gson.annotations.Expose;

public class Welcome {

    @Expose
    private Data9 data;
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public Data9 getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Data9 data) {
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
