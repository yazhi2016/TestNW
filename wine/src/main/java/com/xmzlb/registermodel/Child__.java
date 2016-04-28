package com.xmzlb.registermodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Child__ {

    @SerializedName("region_id")
    @Expose
    private String regionId;
    @SerializedName("parent_id")
    @Expose
    private String parentId;
    @SerializedName("region_name")
    @Expose
    private String regionName;
    @SerializedName("region_type")
    @Expose
    private String regionType;
    @SerializedName("agency_id")
    @Expose
    private String agencyId;

    /**
     * 
     * @return
     *     The regionId
     */
    public String getRegionId() {
        return regionId;
    }

    /**
     * 
     * @param regionId
     *     The region_id
     */
    public void setRegionId(String regionId) {
        this.regionId = regionId;
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
     *     The regionName
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * 
     * @param regionName
     *     The region_name
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    /**
     * 
     * @return
     *     The regionType
     */
    public String getRegionType() {
        return regionType;
    }

    /**
     * 
     * @param regionType
     *     The region_type
     */
    public void setRegionType(String regionType) {
        this.regionType = regionType;
    }

    /**
     * 
     * @return
     *     The agencyId
     */
    public String getAgencyId() {
        return agencyId;
    }

    /**
     * 
     * @param agencyId
     *     The agency_id
     */
    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

}
