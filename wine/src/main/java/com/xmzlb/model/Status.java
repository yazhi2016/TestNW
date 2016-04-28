package com.xmzlb.model;

import com.google.gson.annotations.Expose;

public class Status {

    @Expose
    private Integer succeed;
    @Expose
    private String error_desc;

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
     *     The error_desc
     */
    public String getError_desc() {
    	return error_desc;
    }
    
    /**
     * 
     * @param error_desc
     *     The error_desc
     */
    public void setError_desc(String error_desc) {
    	this.error_desc = error_desc;
    }

}
