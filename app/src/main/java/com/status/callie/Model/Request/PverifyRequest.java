package com.status.callie.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jivan.ghadage on 8/31/2016.
 */
public class PverifyRequest {

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getCountry_code() {
        return country_code;
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

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @SerializedName("access_token")
    @Expose
    private String access_token;

    @SerializedName("country_code")
    @Expose
    private String country_code;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("otp")
    @Expose
    private String otp;

    @SerializedName("mac")
    @Expose
    private String mac;
}
