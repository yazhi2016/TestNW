package com.xmzlb.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data6 {

    @Expose
    private String tel;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @Expose
    private String birthday;
    @SerializedName("shop_name")
    @Expose
    private String shopName;
    @Expose
    private String country;
    @Expose
    private String province;
    @Expose
    private String city;
    @Expose
    private String district;
    @Expose
    private String address;
    @Expose
    private String licence;

    /**
     * 
     * @return
     *     The tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * 
     * @param tel
     *     The tel
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 
     * @return
     *     The userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 
     * @param userName
     *     The user_name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 
     * @return
     *     The birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * 
     * @param birthday
     *     The birthday
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * 
     * @return
     *     The shopName
     */
    public String getShopName() {
        return shopName;
    }

    /**
     * 
     * @param shopName
     *     The shop_name
     */
    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    /**
     * 
     * @return
     *     The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * 
     * @param country
     *     The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * 
     * @return
     *     The province
     */
    public String getProvince() {
        return province;
    }

    /**
     * 
     * @param province
     *     The province
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * 
     * @return
     *     The city
     */
    public String getCity() {
        return city;
    }

    /**
     * 
     * @param city
     *     The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 
     * @return
     *     The district
     */
    public String getDistrict() {
        return district;
    }

    /**
     * 
     * @param district
     *     The district
     */
    public void setDistrict(String district) {
        this.district = district;
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
     *     The licence
     */
    public String getLicence() {
        return licence;
    }

    /**
     * 
     * @param licence
     *     The licence
     */
    public void setLicence(String licence) {
        this.licence = licence;
    }

}
