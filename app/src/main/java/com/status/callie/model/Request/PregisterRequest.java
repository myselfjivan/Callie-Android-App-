package com.status.callie.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jivan.ghadage on 8/26/2016.
 */
public class PregisterRequest {

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @SerializedName("access_token")
    @Expose
    private String accessToken;


    @SerializedName("country_code")
    @Expose
    private String country_code;

    @SerializedName("mobile")
    @Expose
    private String mobile;

}
