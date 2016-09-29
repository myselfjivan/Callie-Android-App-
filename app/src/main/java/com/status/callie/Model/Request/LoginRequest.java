package com.status.callie.Model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jivan.ghadage on 9/29/2016.
 */
public class LoginRequest {
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @SerializedName("access_token")
    @Expose
    private String accessToken;


    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("password")
    @Expose
    private String password;
}
