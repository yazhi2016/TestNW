package com.xmzlb.registermodel;

import com.google.gson.annotations.Expose;

public class Signup {

    @Expose
    private Data3 data;
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public Data3 getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(Data3 data) {
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
