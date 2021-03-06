package com.status.callie.model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by om on 2/10/16.
 */
public class StatusRequest {
    public void setStatus(String status) {
        this.status = status;
    }

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("token")
    @Expose
    private String token;
}
