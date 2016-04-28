package com.xmzlb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum16 {

    @SerializedName("return_id")
    @Expose
    private String returnId;
    @Expose
    private String uid;
    @Expose
    private String goodname;
    @Expose
    private String number;
    @Expose
    private String why;
    @Expose
    private String type;
    @SerializedName("u_status")
    @Expose
    private String uStatus;
    @SerializedName("s_status")
    @Expose
    private String sStatus;
    @Expose
    private String addtime;

    /**
     * 
     * @return
     *     The returnId
     */
    public String getReturnId() {
        return returnId;
    }

    /**
     * 
     * @param returnId
     *     The return_id
     */
    public void setReturnId(String returnId) {
        this.returnId = returnId;
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

    /**
     * 
     * @return
     *     The goodname
     */
    public String getGoodname() {
        return goodname;
    }

    /**
     * 
     * @param goodname
     *     The goodname
     */
    public void setGoodname(String goodname) {
        this.goodname = goodname;
    }

    /**
     * 
     * @return
     *     The number
     */
    public String getNumber() {
        return number;
    }

    /**
     * 
     * @param number
     *     The number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * 
     * @return
     *     The why
     */
    public String getWhy() {
        return why;
    }

    /**
     * 
     * @param why
     *     The why
     */
    public void setWhy(String why) {
        this.why = why;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 
     * @return
     *     The uStatus
     */
    public String getUStatus() {
        return uStatus;
    }

    /**
     * 
     * @param uStatus
     *     The u_status
     */
    public void setUStatus(String uStatus) {
        this.uStatus = uStatus;
    }

    /**
     * 
     * @return
     *     The sStatus
     */
    public String getSStatus() {
        return sStatus;
    }

    /**
     * 
     * @param sStatus
     *     The s_status
     */
    public void setSStatus(String sStatus) {
        this.sStatus = sStatus;
    }

    /**
     * 
     * @return
     *     The addtime
     */
    public String getAddtime() {
        return addtime;
    }

    /**
     * 
     * @param addtime
     *     The addtime
     */
    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

}
