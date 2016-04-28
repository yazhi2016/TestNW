package com.xmzlb.mainmodel;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @Expose
    private List<Ad> ad = new ArrayList<Ad>();
    @Expose
    private Promote promote;
    @Expose
    private List<Hot> hot = new ArrayList<Hot>();
    @SerializedName("new")
    @Expose
    private List<New> _new = new ArrayList<New>();
    @SerializedName("1F")
    @Expose
    private List<_1F> _1F = new ArrayList<_1F>();
    @SerializedName("2F")
    @Expose
    private List<_2F> _2F = new ArrayList<_2F>();
    @SerializedName("3F")
    @Expose
    private List<_3F> _3F = new ArrayList<_3F>();
    @SerializedName("4F")
    @Expose
    private List<_4F> _4F = new ArrayList<_4F>();

    /**
     * 
     * @return
     *     The ad
     */
    public List<Ad> getAd() {
        return ad;
    }

    /**
     * 
     * @param ad
     *     The ad
     */
    public void setAd(List<Ad> ad) {
        this.ad = ad;
    }

    /**
     * 
     * @return
     *     The promote
     */
    public Promote getPromote() {
        return promote;
    }

    /**
     * 
     * @param promote
     *     The promote
     */
    public void setPromote(Promote promote) {
        this.promote = promote;
    }

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
     *     The _1F
     */
    public List<_1F> get1F() {
        return _1F;
    }

    /**
     * 
     * @param _1F
     *     The 1F
     */
    public void set1F(List<_1F> _1F) {
        this._1F = _1F;
    }

    /**
     * 
     * @return
     *     The _2F
     */
    public List<_2F> get2F() {
        return _2F;
    }

    /**
     * 
     * @param _2F
     *     The 2F
     */
    public void set2F(List<_2F> _2F) {
        this._2F = _2F;
    }

    /**
     * 
     * @return
     *     The _3F
     */
    public List<_3F> get3F() {
        return _3F;
    }

    /**
     * 
     * @param _3F
     *     The 3F
     */
    public void set3F(List<_3F> _3F) {
        this._3F = _3F;
    }

    /**
     * 
     * @return
     *     The _4F
     */
    public List<_4F> get4F() {
        return _4F;
    }

    /**
     * 
     * @param _4F
     *     The 4F
     */
    public void set4F(List<_4F> _4F) {
        this._4F = _4F;
    }

}
