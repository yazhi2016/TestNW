package com.xmzlb.homemodel;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @Expose
    private List<Hot> hot = new ArrayList<Hot>();
    @SerializedName("new")
    @Expose
    private List<New> _new = new ArrayList<New>();
    @Expose
    private List<Liquor> liquor = new ArrayList<Liquor>();
    @Expose
    private List<Foreign> foreign = new ArrayList<Foreign>();
    @Expose
    private List<Object> beer = new ArrayList<Object>();
    @Expose
    private List<Other> other = new ArrayList<Other>();

    /**
     * 
     * @return
     *     The hot
     */
    public List<Hot> getHot() {
        return hot;
    }

    /**
     * 
     * @param hot
     *     The hot
     */
    public void setHot(List<Hot> hot) {
        this.hot = hot;
    }

    /**
     * 
     * @return
     *     The _new
     */
    public List<New> getNew() {
        return _new;
    }

    /**
     * 
     * @param _new
     *     The new
     */
    public void setNew(List<New> _new) {
        this._new = _new;
    }

    /**
     * 
     * @return
     *     The liquor
     */
    public List<Liquor> getLiquor() {
        return liquor;
    }

    /**
     * 
     * @param liquor
     *     The liquor
     */
    public void setLiquor(List<Liquor> liquor) {
        this.liquor = liquor;
    }

    /**
     * 
     * @return
     *     The foreign
     */
    public List<Foreign> getForeign() {
        return foreign;
    }

    /**
     * 
     * @param foreign
     *     The foreign
     */
    public void setForeign(List<Foreign> foreign) {
        this.foreign = foreign;
    }

    /**
     * 
     * @return
     *     The beer
     */
    public List<Object> getBeer() {
        return beer;
    }

    /**
     * 
     * @param beer
     *     The beer
     */
    public void setBeer(List<Object> beer) {
        this.beer = beer;
    }

    /**
     * 
     * @return
     *     The other
     */
    public List<Other> getOther() {
        return other;
    }

    /**
     * 
     * @param other
     *     The other
     */
    public void setOther(List<Other> other) {
        this.other = other;
    }

}
