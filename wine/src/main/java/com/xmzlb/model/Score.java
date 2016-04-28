package com.xmzlb.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Score {

    @Expose
    private List<Datum8> data = new ArrayList<Datum8>();
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public List<Datum8> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<Datum8> data) {
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
