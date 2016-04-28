package com.xmzlb.model;

import java.io.Serializable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Serializable {

    @Expose
    private String id;
    @Expose
    private String consignee;
    @Expose
    private String address;
    @Expose
    private String mobile;
    @SerializedName("country_name")
    @Expose
    private String countryName;
    @SerializedName("province_name")
    @Expose
    private String provinceName;
    @SerializedName("city_name")
    @Expose
    private String cityName;
    @SerializedName("district_name")
    @Expose
    private String districtName;
    @SerializedName("default_address")
    @Expose
    private Integer defaultAddress;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The consignee
     */
    public String getConsignee() {
        return consignee;
    }

    /**
     * 
     * @param consignee
     *     The consignee
     */
    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    /**
     * 
     * @return
     *     The address
     */
    public String getAddress() {
        return address;
    }

    /**
     * 
     * @param address
     *     The address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 
     * @return
     *     The mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 
     * @param mobile
     *     The mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * 
     * @return
     *     The countryName
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * 
     * @param countryName
     *     The country_name
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * 
     * @return
     *     The provinceName
     */
    public String getProvinceName() {
        return provinceName;
    }

    /**
     * 
     * @param provinceName
     *     The province_name
     */
    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    /**
     * 
     * @return
     *     The cityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * 
     * @param cityName
     *     The city_name
     */
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    /**
     * 
     * @return
     *     The districtName
     */
    public String getDistrictName() {
        return districtName;
    }

    /**
     * 
     * @param districtName
     *     The district_name
     */
    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    /**
     * 
     * @return
     *     The defaultAddress
     */
    public Integer getDefaultAddress() {
        return defaultAddress;
    }

    /**
     * 
     * @param defaultAddress
     *     The default_address
     */
    public void setDefaultAddress(Integer defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

}
