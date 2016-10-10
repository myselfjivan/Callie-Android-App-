package com.status.callie.Model.Response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jivan.ghadage on 9/29/2016.
 */
public class LoginResponse {
    public String getToken() {
        return token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("expires_in")
    @Expose
    private String expires_in;
}
