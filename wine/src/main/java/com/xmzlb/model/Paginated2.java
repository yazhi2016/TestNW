package com.xmzlb.model;

import com.google.gson.annotations.Expose;

public class Paginated2 {

    @Expose
    private Integer total;
    @Expose
    private Integer count;
    @Expose
    private Integer more;

    /**
     * 
     * @return
     *     The total
     */
    public Integer getTotal() {
        return total;
    }

    /**
     * 
     * @param total
     *     The total
     */
    public void setTotal(Integer total) {
        this.total = total;
    }

    /**
     * 
     * @return
     *     The count
     */
    public Integer getCount() {
        return count;
    }

    /**
     * 
     * @param count
     *     The count
     */
    public void setCount(Integer count) {
        this.count = count;
    }

    /**
     * 
     * @return
     *     The more
     */
    public Integer getMore() {
        return more;
    }

    /**
     * 
     * @param more
     *     The more
     */
    public void setMore(Integer more) {
        this.more = more;
    }

}
