package com.xmzlb.model;

import com.google.gson.annotations.Expose;

public class SignInSuccess {

    @Expose
    private DataSignIn data;
    @Expose
    private StatusFail status;

    /**
     * 
     * @return
     *     The data
     */
    public DataSignIn getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(DataSignIn data) {
        this.data = data;
    }

    /**
     * 
     * @return
     *     The status
     */
    public StatusFail getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(StatusFail status) {
        this.status = status;
    }

}
