package com.xmzlb.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class Search {

    @Expose
    private List<Datum5> data = new ArrayList<Datum5>();
    @Expose
    private Status status;
    @Expose
    private Paginated2 paginated;

    /**
     * 
     * @return
     *     The data
     */
    public List<Datum5> getData() {
        return data;
    }

    /**
     * 
     * @param data
     *     The data
     */
    public void setData(List<Datum5> data) {
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
    public Paginated2 getPaginated() {
        return paginated;
    }

    /**
     * 
     * @param paginated
     *     The paginated
     */
    public void setPaginated(Paginated2 paginated) {
        this.paginated = paginated;
    }

}
