package com.xmzlb.model;

import com.google.gson.annotations.Expose;

public class Data11 {

    @Expose
    private Integer versioncode;
    @Expose
    private String url;

    /**
     * 
     * @return
     *     The versioncode
     */
    public Integer getVersioncode() {
        return versioncode;
    }

    /**
     * 
     * @param versioncode
     *     The versioncode
     */
    public void setVersioncode(Integer versioncode) {
        this.versioncode = versioncode;
    }

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
