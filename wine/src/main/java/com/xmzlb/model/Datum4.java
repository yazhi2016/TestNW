package com.xmzlb.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum4 {

    @SerializedName("cat_id")
    @Expose
    private String catId;
    @SerializedName("parent_id")
    @Expose
    private String parentId;
    @SerializedName("cat_name")
    @Expose
    private String catName;
    @Expose
    private List<Child> child = new ArrayList<Child>();

    /**
     * 
     * @return
     *     The catId
     */
    public String getCatId() {
        return catId;
    }

    /**
     * 
     * @param catId
     *     The cat_id
     */
    public void setCatId(String catId) {
        this.catId = catId;
    }

    /**
     * 
     * @return
     *     The parentId
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * 
     * @param parentId
     *     The parent_id
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * 
     * @return
     *     The catName
     */
    public String getCatName() {
        return catName;
    }

    /**
     * 
     * @param catName
     *     The cat_name
     */
    public void setCatName(String catName) {
        this.catName = catName;
    }

    /**
     * 
     * @return
     *     The child
     */
    public List<Child> getChild() {
        return child;
    }

    /**
     * 
     * @param child
     *     The child
     */
    public void setChild(List<Child> child) {
        this.child = child;
    }

}
