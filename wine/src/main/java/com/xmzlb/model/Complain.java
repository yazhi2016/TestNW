package com.xmzlb.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Complain {

    @Expose
    private List<Datum9> data = new ArrayList<Datum9>();
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public List<Datum9> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<Datum9> data) {
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
