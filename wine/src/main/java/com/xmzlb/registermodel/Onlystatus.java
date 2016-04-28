package com.xmzlb.registermodel;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Onlystatus {

    @Expose
    private List<Object> data = new ArrayList<Object>();
    @Expose
    private Status status;

    /**
     * 
     * @return
     *     The data
     */
    public List<Object> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<Object> data) {
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
