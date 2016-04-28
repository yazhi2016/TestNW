package com.xmzlb.model;

import com.google.gson.annotations.Expose;

public class Member {

    @Expose
    private String rank;
    @Expose
    private Integer integral;

    /**
     * 
     * @return
     *     The rank
     */
    public String getRank() {
        return rank;
    }

    /**
     * 
     * @param rank
     *     The rank
     */
    public void setRank(String rank) {
        this.rank = rank;
    }

    /**
     * 
     * @return
     *     The integral
     */
    public Integer getIntegral() {
        return integral;
    }

    /**
     * 
     * @param integral
     *     The integral
     */
    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

}
