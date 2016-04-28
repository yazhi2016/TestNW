package com.xmzlb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data12 {

    @SerializedName("oder_sn")
    @Expose
    private String oderSn;
    @Expose
    private String title;
    @Expose
    private double money;
    @Expose
    private String msg;

    /**
     * 
     * @return
     *     The oderSn
     */
    public String getOderSn() {
        return oderSn;
    }

    /**
     * 
     * @param oderSn
     *     The oder_sn
     */
    public void setOderSn(String oderSn) {
        this.oderSn = oderSn;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The money
     */
    public double getMoney() {
        return money;
    }

    /**
     * 
     * @param money
     *     The money
     */
    public void setMoney(double money) {
        this.money = money;
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
