package com.xmzlb.registermodel;

import com.google.gson.annotations.Expose;

public class Data5 {

    @Expose
    private Integer succeed;
    @Expose
    private String msg;

    /**
     * 
     * @return
     *     The succeed
     */
    public Integer getSucceed() {
        return succeed;
    }

    /**
     * 
     * @param succeed
     *     The succeed
     */
    public void setSucceed(Integer succeed) {
        this.succeed = succeed;
    }

    /**
     * 
     * @return
     *     The msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 
     * @param msg
     *     The msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

}
