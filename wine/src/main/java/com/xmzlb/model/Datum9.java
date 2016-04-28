package com.xmzlb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum9 {

    @Expose
    private String time;
    @SerializedName("msg_title")
    @Expose
    private String msgTitle;
    @SerializedName("msg_content")
    @Expose
    private String msgContent;
    @SerializedName("msg_status")
    @Expose
    private String msgStatus;

    /**
     * 
     * @return
     *     The time
     */
    public String getTime() {
        return time;
    }

    /**
     * 
     * @param time
     *     The time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * 
     * @return
     *     The msgTitle
     */
    public String getMsgTitle() {
        return msgTitle;
    }

    /**
     * 
     * @param msgTitle
     *     The msg_title
     */
    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    /**
     * 
     * @return
     *     The msgContent
     */
    public String getMsgContent() {
        return msgContent;
    }

    /**
     * 
     * @param msgContent
     *     The msg_content
     */
    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    /**
     * 
     * @return
     *     The msgStatus
     */
    public String getMsgStatus() {
        return msgStatus;
    }

    /**
     * 
     * @param msgStatus
     *     The msg_status
     */
    public void setMsgStatus(String msgStatus) {
        this.msgStatus = msgStatus;
    }

}
