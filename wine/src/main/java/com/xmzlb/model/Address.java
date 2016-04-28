package com.xmzlb.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Address {

    @Expose
    private List<Datum> data = new ArrayList<Datum>();
    @Expose
    private Status status;
    
    /**
     * 
     * @return
     *     The data
     */
    public List<Datum> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<Datum> data) {
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
