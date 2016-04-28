package com.xmzlb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderNum {

    @SerializedName("await_pay")
    @Expose
    private String awaitPay;
    @SerializedName("await_ship")
    @Expose
    private String awaitShip;
    @Expose
    private String shipped;
    @Expose
    private String finished;

    /**
     * 
     * @return
     *     The awaitPay
     */
    public String getAwaitPay() {
        return awaitPay;
    }

    /**
     * 
     * @param awaitPay
     *     The await_pay
     */
    public void setAwaitPay(String awaitPay) {
        this.awaitPay = awaitPay;
    }

    /**
     * 
     * @return
     *     The awaitShip
     */
    public String getAwaitShip() {
        return awaitShip;
    }

    /**
     * 
     * @param awaitShip
     *     The await_ship
     */
    public void setAwaitShip(String awaitShip) {
        this.awaitShip = awaitShip;
    }

    /**
     * 
     * @return
     *     The shipped
     */
    public String getShipped() {
        return shipped;
    }

    /**
     * 
     * @param shipped
     *     The shipped
     */
    public void setShipped(String shipped) {
        this.shipped = shipped;
    }

    /**
     * 
     * @return
     *     The finished
     */
    public String getFinished() {
        return finished;
    }

    /**
     * 
     * @param finished
     *     The finished
     */
    public void setFinished(String finished) {
        this.finished = finished;
    }

}
