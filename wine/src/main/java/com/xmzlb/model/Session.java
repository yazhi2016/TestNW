package com.xmzlb.model;

import com.google.gson.annotations.Expose;

public class Session {

    @Expose
    private String sid;
    @Expose
    private String uid;

    /**
     * 
     * @return
     *     The sid
     */
    public String getSid() {
        return sid;
    }

    /**
     * 
     * @param sid
     *     The sid
     */
    public void setSid(String sid) {
        this.sid = sid;
    }

    /**
     * 
     * @return
     *     The uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * 
     * @param uid
     *     The uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

}
