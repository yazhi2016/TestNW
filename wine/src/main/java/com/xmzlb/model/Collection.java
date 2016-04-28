package com.xmzlb.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Collection {

    @Expose
    private List<Datum7> data = new ArrayList<Datum7>();
    @Expose
    private Status status;
    @Expose
    private Paginated paginated;

    /**
     * 
     * @return
     *     The data
     */
    public List<Datum7> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<Datum7> data) {
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

    /**
     * 
     * @return
     *     The paginated
     */
    public Paginated getPaginated() {
        return paginated;
    }

    /**
     * 
     * @param paginated
     *     The paginated
     */
    public void setPaginated(Paginated paginated) {
        this.paginated = paginated;
    }

}
